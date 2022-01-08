package application;

public class ClockReport {
	private int employee_ID;
	private String employee_name,job_type,time_sum;
	private double hourly_rate,salary;
	//employee_ID,employee_name,job_type,time_sum, hourly_rate,salary
	public ClockReport(int employee_ID, String employee_name, String job_type, String time_sum, double hourly_rate) {
		super();
		this.employee_ID = employee_ID;
		this.employee_name = employee_name;
		this.job_type = job_type;
		this.time_sum = time_sum;
		this.hourly_rate = hourly_rate;
	}
	public ClockReport() {
		super();
	}
	public ClockReport(int employee_ID, String employee_name, String job_type, String time_sum, double hourly_rate,
			double salary) {
		super();
		this.employee_ID = employee_ID;
		this.employee_name = employee_name;
		this.job_type = job_type;
		this.time_sum = time_sum;
		this.hourly_rate = hourly_rate;
		this.salary = salary;
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
	public String getTime_sum() {
		return time_sum;
	}
	public void setTime_sum(String time_sum) {
		this.time_sum = time_sum;
	}
	public double getHourly_rate() {
		return hourly_rate;
	}
	public void setHourly_rate(double hourly_rate) {
		this.hourly_rate = hourly_rate;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	@Override
	public String toString() {
		return "Clock [employee_ID=" + employee_ID + ", employee_name=" + employee_name + ", job_type=" + job_type
				+ ", time_sum=" + time_sum + ", hourly_rate=" + hourly_rate + ", salary=" + salary + "]\n";
	}
	
	
}
