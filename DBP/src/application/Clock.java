package application;

import java.time.LocalDate;

public class Clock {
// C.employee_ID,E.employee_name,E.job_type,C.entry_time,C.out_time,entry_date
	private int employee_ID;
	private String employee_name;
	private String job_type;
	private String entry_time;
	private String out_time;
	private LocalDate entry_date;

	// private double salary_hourly_rate,salary_per_month;
	public Clock(int employee_ID, String employee_name, String job_type, String entry_time, String out_time,
			LocalDate entry_date) {
		this.employee_ID = employee_ID;
		this.employee_name = employee_name;
		this.job_type = job_type;
		this.entry_time = entry_time;
		this.out_time = out_time;
		this.entry_date = entry_date;
	}

	public Clock() {
		super();
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

	public String getJob_type() {
		return job_type;
	}

	public void setJob_type(String job_type) {
		this.job_type = job_type;
	}

	public String getEntry_time() {
		return entry_time;
	}

	public void setEntry_time(String entry_time) {
		this.entry_time = entry_time;
	}

	public String getOut_time() {
		return out_time;
	}

	public void setOut_time(String out_time) {
		this.out_time = out_time;
	}

	public LocalDate getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(LocalDate entry_date) {
		this.entry_date = entry_date;
	}

}
