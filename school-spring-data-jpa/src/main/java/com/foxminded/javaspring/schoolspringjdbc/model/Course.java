package com.foxminded.javaspring.schoolspringjdbc.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "courses", schema = "school")
public class Course {
	
	@Id
	@Column (name = "course_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer courseID;
	
	@Column(name = "course_name")
	private String courseName;
	
	@Column(name = "course_description")
	private String courseDescription;
	
	@ManyToMany(mappedBy = "courses")
	private Set<Student> students = new HashSet<>();
	
	public Course(int courseID, String courseName) {
		this.courseID = courseID;
		this.courseName = courseName;
	}
	
	public Course(String courseName) {
		this.courseName = courseName;
	}
    	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseName, course.courseName);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(courseName);
    }

}
