package com.foxminded.javaspring.schoolspringjdbc.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students", schema = "school")
public class Student {

	@Id
	@Column(name = "student_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer studentID;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", insertable = false, updatable = false)
	private Group group;

	@Column(name = "group_id")
	private Integer groupID;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "students_courses", schema = "school", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private Set<Course> courses = new HashSet<>();

	public Student(int studentID, String firstName, String lastName) {
		this.studentID = studentID;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Student(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Student(int studentID, int groupID) {
		this.studentID = studentID;
		this.groupID = groupID;
	}
	

	public Student(Integer studentID, Integer groupID, String firstName, String lastName) {
		this.studentID = studentID;
		this.groupID = groupID;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public void addCourse (Course course) {
		courses.add(course);
		course.getStudents().add(this);
	}
	
	public void removeCourse (Course course) {
		courses.remove(course);
		course.getStudents().remove(this);
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        return studentID != null && studentID.equals(((Student) o).getStudentID());
    }
 
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
