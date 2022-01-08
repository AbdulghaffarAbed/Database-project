drop database DB_project;
create database DB_project;
use DB_project;
CREATE TABLE employee ( employee_ID int NOT NULL ,employee_name varchar(35) not null,date_of_birth date not null
						,job_type varchar(20) not null, experience varchar(30) not null
                        ,salary_hourly_rate double not null, salary_per_month double not null
                        ,PRIMARY KEY (employee_ID));  

CREATE TABLE sections (model_ID int NOT NULL,employee_ID int NOT NULL
					   ,current_stage varchar(20) NOT NULL,next_stage varchar(20) NOT NULL
					   ,start_time time DEFAULT NULL,finish_time time DEFAULT NULL
                       ,date_worked date not null 
                       
                       ,PRIMARY KEY (model_ID,current_stage,next_stage,employee_ID)
                       ,FOREIGN KEY (employee_ID) REFERENCES employee (employee_ID) on delete cascade on update no action ,
                       CONSTRAINT checktime CHECK (start_time<finish_time),
                       
                       CONSTRAINT checkCstage CHECK (current_stage="Gypsum" or current_stage="Photograph" 
                       or current_stage="Manufacturing"or current_stage="Finishing" ),
                       
                       CONSTRAINT checkRstage CHECK (next_stage="Delivery" or next_stage="Photograph" 
                       or next_stage="Manufacturing"or next_stage="Finishing" )
                       ) ;
                       
                   
CREATE TABLE clock (employee_ID int NOT NULL,entry_date date not null,
					entry_time time DEFAULT NULL,out_time time DEFAULT NULL
                    ,PRIMARY KEY (employee_ID,entry_date)
                    ,FOREIGN KEY (employee_ID) REFERENCES employee (employee_ID) ON DELETE CASCADE on update no action
                    ,CONSTRAINT checktimeE CHECK (entry_time<out_time)
                    );



INSERT INTO employee(employee_ID,employee_name,date_of_birth,job_type,experience,salary_hourly_rate,salary_per_month) 
values  (1, 'abdulghaffar Abed', '2001-04-03', "Gypsum", 'SR', 16, 2500)
	   ,(2,"Amro jabaren",'2002-7-2','Photograph','JR',14.5,1800)
       ,(19,"Loai ashawy",'1992-11-17','Manufacturing','SR',18,3500)
       ,(22,"Nahed omran",'1983-1-5',"Photograph",'SR',22,4200)
       ,(11,"Adam naser",'1994-8-8',"Finishing",'JR',17,2800)
       ,(20,'Raed ali','1982-7-3',"Finishing",'SR',27.5,5350)
       ,(5,'shadi zaher','2000-9-20',"Delivery",'JR',15,2000)
       ,(8,'Saed harb','1997-12-30','Gypsum','SR',17.5,3000);

INSERT INTO employee(employee_ID,employee_name,date_of_birth,job_type,experience,salary_hourly_rate,salary_per_month) 
value  (99, 'Ahmad hamed', '2000-09-03', "Gypsum", 'SR', 17, 2600);
INSERT INTO sections(model_ID,employee_ID,current_stage,next_stage,start_time,finish_time,date_worked) 
values  (11,2, "Gypsum", 'Photograph','080300', '120000','2020-12-12')
	   ,(123,19,"Manufacturing","Finishing",'144500','162301','2020-9-9')
       ,(79,11,'Finishing','Delivery','112132','155715','2020-7-5')
       ,(77,20,"Gypsum","Manufacturing",'84523','105300','2020-4-3');


INSERT INTO clock(employee_ID,entry_date,entry_time,out_time)
value (1,'2020-7-2',80000,160900)
	 ,(19,'2020-7-2',84300,163508)
     ,(11,'2020-7-2',93217,152841)
     ,(20,'2020-7-2',80310,172453)
     ,(1,'2020-7-4',92412,162314);

delete from employee e 
where e.employee_ID=2;

select * from employee;
update employee  Set  employee_ID=4,employee_name=' shadi zaher',date_of_birth='2000-09-20',job_type='Delivery',experience='JR',salary_hourly_rate=15.0,salary_per_month=5.0
where employee_ID=5;



select distinct *
from employee e , clock c 
where e.employee_id=c.employee_id;

insert into employee(employee_ID,employee_name,date_of_birth,job_type,experience,salary_hourly_rate,salary_per_month)
 	  values(25,'b','2000-02-23','finishing','SR',15.0,20.0);

select *
from employee e 
where e.job_type='Gypsum'
order by e.employee_id ;
