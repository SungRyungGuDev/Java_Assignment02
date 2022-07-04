package Assignment02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Assignment02 {

	public static void main(String[] args) throws FileNotFoundException {
		
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter employee filename (full path): ");
		String filePath = sc.nextLine();
		System.out.print("Enter employee salary filename (full path): ");
		String salaryFile = sc.nextLine();
		System.out.print("Enter employee last name: ");
		String lastName = sc.nextLine();
		System.out.print("Enter employee first name: ");
		String firstName = sc.nextLine();
			
		lookupEmployeeID(filePath, lastName, firstName);
		String empID = lookupEmployeeID(filePath, lastName, firstName);

		
		if(empID != null) {
			printEmployeeSalary(salaryFile, lastName, firstName, empID);
			
		}else {
			System.out.printf("No Employee information found for: %s %s", lastName, firstName);

		}


	}
	

	private static void printEmployeeSalary(String salaryFile, String lastName, String firstName, String empID) throws FileNotFoundException {
		
		String checkID = null;
		double monthlySalary = 0;
		double fedTax = 0;
		double abTax = 0;
		double cpp = 0;
		double ei = 0;
		File file = new File(salaryFile);
		Scanner reader = new Scanner(file);
		
		while(reader.hasNextLine()) {
			
			String line = reader.nextLine();
			String[] data = line.split(",");
			checkID = data[0];
			if(checkID.equalsIgnoreCase(empID)) {
			monthlySalary = Double.parseDouble(data[1]);
			fedTax = Double.parseDouble(data[2]);
			abTax = Double.parseDouble(data[3]);
			cpp = Double.parseDouble(data[4]);
			ei = Double.parseDouble(data[5]);
			}
		}
		
		printPaySchedule(lastName, firstName, empID, monthlySalary, fedTax, abTax, cpp, ei);
		
	}


	private static void printPaySchedule(String lastName, String firstName, String empID, double monthlySalary, double fedTax, double abTax, double cpp, double ei) {
		
		System.out.printf("Salary schedule for %s %s (%s)\n", firstName.toUpperCase(), lastName.toUpperCase(), empID);
		System.out.println("             Gross                                         Net");
		System.out.println("Month       Salary         Tax            CPP               EI            Salary");
		System.out.println("--------------------------------------------------------------------------------");
		
		double totGrossSal = 0.0;
		double totTax = 0.0;
		double totCpp = 0.0;
		double totEi = 0.0;
		double totNetSal = 0.0;
		
		for(int month = 1; month <= 12; month++) {
			if((totCpp + cpp) > 3166.45) {
				cpp = 3166.45 - totCpp;
			}
			if ((totEi + ei) > 889.54) {
				ei = 889.54 - totEi;
			}
			double tax = fedTax + abTax;
			double netSal = monthlySalary - (tax + cpp + ei);
			
			totGrossSal = totGrossSal + monthlySalary;
            totTax = totTax + tax;
            totCpp = totCpp + cpp;
            totEi = totEi + ei;
            totNetSal = totNetSal + netSal;
            
            System.out.printf("%5d %12.2f %12.2f %15.2f %15.2f %15.2f\n", month, monthlySalary, tax, cpp, ei, netSal);
            
			
		}
		System.out.printf("%s %18.2f %12.2f %15.2f %15.2f %15.2f\n","Total", totGrossSal, totTax, totCpp, totEi, totNetSal);
	}


	private static String lookupEmployeeID(String filePath, String lastName, String firstName) throws FileNotFoundException {

		String resultID = null;
		String empFirstName = null;
		String empLastName = null;
		String empDate = null;
		String empID = null;
		String serviceDate = null;
		File file = new File(filePath);
		Scanner reader = new Scanner(file);

		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			String[] data = line.split(",");
			empFirstName = data[0];
			empLastName = data[1];
			empDate = data[2];
			empID = data[3];
			serviceDate = data[4];
			if (lastName.equalsIgnoreCase(empLastName) && firstName.equalsIgnoreCase(empFirstName)) {
				resultID = empID;
			}
		}
		reader.close();
		return resultID;
	}

	}