package com.example.demo;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
@Controller
public class ConsumerControllerClient {
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private LoadBalancerClient loadBalancer;
	
	public void getEmployee() throws RestClientException, IOException {
		
		//With Discovery Client
		/*List<ServiceInstance> instances=discoveryClient.getInstances("employee-producer");
			ServiceInstance serviceInstance = loadBalancer.choose("employee-producer");
			ServiceInstance serviceInstance=instances.get(0);
		*/
		
		//With client side load balancer
		/*ServiceInstance serviceInstance = loadBalancer.choose("employee-producer");*/
		
		//With Zuul Gate-way and Eureka Server
		List<ServiceInstance> instances = discoveryClient.getInstances("EMPLOYEE-ZUUL-SERVICE");
		ServiceInstance serviceInstance = instances.get(0);

		String baseUrl = serviceInstance.getUri().toString();
		
		
		//baseUrl=baseUrl+"/employee";
		baseUrl=baseUrl+"/producer/employee";
		
		System.out.println("baseUrl : Beofre Replacement "+baseUrl);
		baseUrl = baseUrl.replace(serviceInstance.getHost(), "localhost");
		System.out.println("baseUrl : After Replacement "+baseUrl);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response=null;
		try{
		response=restTemplate.exchange(baseUrl,
				HttpMethod.GET, getHeaders(),String.class);
		}catch (Exception ex)
		{
			System.out.println(ex);
		}
		System.out.println(response.getBody());
	}

	private static HttpEntity<?> getHeaders() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}
}