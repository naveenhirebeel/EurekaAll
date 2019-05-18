package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaClient
@SpringBootApplication
public class EurekaClientProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClientProducerApplication.class, args);
	}
}


@RestController
class TestController
{

	@Autowired
	Environment environment;
	
    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public Employee firstPage()
    {

        Employee emp = new Employee();
        emp.setName("emp1");
        emp.setDesignation("manager");
        emp.setEmpId("1");
        emp.setSalary(3000);
        emp.setPort(environment.getProperty("local.server.port"));

        return emp;
    }
}

class Employee {
    private String empId;
    private String name;
    private String designation;
    private double salary;
    private String port;

    public Employee() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
    
}