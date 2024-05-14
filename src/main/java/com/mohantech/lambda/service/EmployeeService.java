package com.mohantech.lambda.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.mohantech.lambda.entity.Employee;
import com.mohantech.lambda.utility.Utility;

import java.util.List;
import java.util.Map;

public class EmployeeService {

    private DynamoDBMapper dynamoDBMapper;

    private void initDynamoDB(){
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        dynamoDBMapper = new DynamoDBMapper(client);
    }

    private String jsonBody = null;
    public APIGatewayProxyResponseEvent saveEmployee(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context){
        initDynamoDB();
        Employee employee = Utility.convertStringToObject(apiGatewayProxyRequestEvent.getBody(), context);
        dynamoDBMapper.save(employee);
        context.getLogger().log("Employee data saved Successfully..! With Employee Id : "+ String.valueOf(employee.getEmpId()));
        return createAPIResponse(String.valueOf(employee.getEmpId()), 201,Utility.createHeaders());
    }

    public APIGatewayProxyResponseEvent getEmployeeByID(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context contex){
        initDynamoDB();
        String empId = apiGatewayProxyRequestEvent.getPathParameters().get("empId");
        Employee employee = dynamoDBMapper.load(Employee.class, empId);
        if (employee != null){
            jsonBody = Utility.convertObjToString(employee, contex);
            contex.getLogger().log("Fetch Employee by EmployeeId Successfully");
            return createAPIResponse(jsonBody,200,Utility.createHeaders());
        }else{
            jsonBody = "Error while fetching Employee details for empId : "+empId;
            return createAPIResponse(jsonBody,400,Utility.createHeaders());
        }
    }

    public APIGatewayProxyResponseEvent getAllEmployees(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context contex){
        initDynamoDB();
        List<Employee> scan = dynamoDBMapper.scan(Employee.class, new DynamoDBScanExpression());
        jsonBody = Utility.convertListOfObjToString(scan, contex);
        contex.getLogger().log("Fetch All Employee data Successfully");
        return createAPIResponse(jsonBody,200,Utility.createHeaders());
    }

    public APIGatewayProxyResponseEvent deleteEmployeeByEmpId(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context contex){
        initDynamoDB();
        String empId = apiGatewayProxyRequestEvent.getPathParameters().get("empId");
        Employee emp = dynamoDBMapper.load(Employee.class, empId);
        if (emp != null){
            dynamoDBMapper.delete(emp);
            jsonBody = "Employee Data deleted Successfully with empId : "+empId;
            contex.getLogger().log("Employee Data deleted Successfully");
            return createAPIResponse(jsonBody,200,Utility.createHeaders());
        }else{
            jsonBody = "Employee Data Not Found with empId : "+empId;
            return createAPIResponse(jsonBody,404,Utility.createHeaders());
        }

    }

    private APIGatewayProxyResponseEvent createAPIResponse(String body, int httpStatusCode, Map<String, String> headers){
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setBody(body);
        responseEvent.setHeaders(headers);
        responseEvent.setStatusCode(httpStatusCode);
        return responseEvent;
    }
}
