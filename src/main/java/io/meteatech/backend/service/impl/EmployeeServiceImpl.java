package io.meteatech.backend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.meteatech.backend.exception.ResourceNotFoundException;
import io.meteatech.backend.model.Employee;
import io.meteatech.backend.repository.EmployeeRepository;
import io.meteatech.backend.service.EmployeeService;

//@Repository is not necessery spring 
@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	private EmployeeRepository employeeRepository;
	
	
	//@Autowired is not nececary spring
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		
		return employeeRepository.save(employee);
	}
	
	@Override
	public List<Employee> getAllEmployee() {
		
		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(long id) {
		
		/*
		 * Optional<Employee> employee=employeeRepository.findById(id);
		 * 
		 * if(employee.isPresent()) { return employee.get(); } else { throw new
		 * ResourceNotFoundException("Employee","Id",id); }
		 */
		
		return employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee","Id",id));
		
	}

	@Override
	public Employee updateEmployee(Employee employee, long id) {
		
		//we need to check whether employee with given id is exist in DB
		Employee employeeToUpdate=employeeRepository.findById(id).orElseThrow(
				()->new ResourceNotFoundException("Employee","Id",id));
		
		employeeToUpdate.setFirstName(employee.getFirstName());
		employeeToUpdate.setLastName(employee.getLastName());
		employeeToUpdate.setEmail(employee.getEmail());
		
		//save existing employee in database
		employeeRepository.save(employeeToUpdate);
		
		return employeeToUpdate;
		
	}

	@Override
	public void deleteEmployee(long id) {
		
		//check whether a employee exist in DB or not
		employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee","Id",id));
		
		employeeRepository.deleteById(id);
		
	}
	
}
