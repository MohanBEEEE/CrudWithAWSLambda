package com.mohantech.lambda.utility;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohantech.lambda.entity.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utility {
    public static Map<String, String> createHeaders(){
        Map<String, String > headers = new HashMap();
        headers.put("Context-Type","application/json");
        headers.put("X-amazon-author","Lipsa");
        headers.put("X-amazon-apiVersion","v1");
        return headers;
    }

    // TO CONVERT OBJECT TO STRING
    public static String convertObjToString(Employee employee, Context context) {
        String jsonBody = null;
        try {
            jsonBody = new ObjectMapper().writeValueAsString(employee);
        } catch (JsonProcessingException e) {
            context.getLogger().log("Error while converting Employee Object to String : "+e.getMessage());
        }
        return jsonBody;
    }

    // TO CONVERT STRING TO OBJECT
    public static Employee convertStringToObject(String jsonBody, Context context){
        Employee employee = null;
        try {
            employee = new ObjectMapper().readValue(jsonBody, Employee.class);
        } catch (JsonProcessingException e) {
            context.getLogger().log("Error while converting String to Employee Object : "+e.getMessage());
        }
        return employee;
    }

    // TO CONVERT LIST OF OBJECTS TO STRING
    public static String convertListOfObjToString(List<Employee> employees, Context context){
        String jsonBody = null;
        try {
            jsonBody = new ObjectMapper().writeValueAsString(employees);
        } catch (JsonProcessingException e) {
            context.getLogger().log("Error while converting List of Employees Object to String : "+e.getMessage());
        }
        return jsonBody;
    }
}
