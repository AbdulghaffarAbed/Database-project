package application;

import java.time.LocalDate;

public class Employee {
	private int employee_ID;
	private String employee_name;
	private LocalDate dateOfBirth;
	private String jobType;
	private String experience;
	private double salaryPerHour;
	private double salaryPerMonth;

	public Employee(int employee_ID, String employee_name, LocalDate dateOfBirth, String jobType, String experience,
			double salaryPerHour, double salaryPerMonth) {
		this.employee_ID = employee_ID;
		this.employee_name = employee_name;
		this.dateOfBirth = dateOfBirth;
		this.jobType = jobType;
		this.experience = experience;
		this.salaryPerHour = salaryPerHour;
		this.salaryPerMonth = salaryPerMonth;
	}

	public int getEmployee_ID() {
		return employee_ID;
	}

	public void setEmployee_ID(int employee_ID) {
		this.employee_ID = employee_ID;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public double getSalaryPerHour() {
		return salaryPerHour;
	}

	public void setSalaryPerHour(double salaryPerHour) {
		this.salaryPerHour = salaryPerHour;
	}

	public double getSalaryPerMonth() {
		return salaryPerMonth;
	}

	public void setSalaryPerMonth(double salaryPerMonth) {
		this.salaryPerMonth = salaryPerMonth;
	}

	@Override
	public String toString() {
		return " " + employee_ID + "  " + employee_name + " " + dateOfBirth + "  " + jobType + " " + experience + " "
				+ salaryPerHour + " " + salaryPerMonth;
	}

}
