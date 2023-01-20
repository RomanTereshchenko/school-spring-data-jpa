package com.foxminded.javaspring.schoolspringjdbc.utils;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScannerUtil {

	private Scanner scan;

	@Autowired
	public ScannerUtil(Scanner scan) {
		this.scan = scan;
	}

	public int scanInt() {
		int scannedInt = scan.nextInt();
		scan.nextLine(); // nextInt() does not consume the last newline character,
//		and thus that newline is consumed in the next call to nextLine()
		return scannedInt;
	}

	public String scanString() {
		return scan.nextLine();
	}

	public void closeScan() {
		scan.close();
	}
}
