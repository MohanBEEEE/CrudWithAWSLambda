package com.mohantech.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.mohantech.lambda.service.EmployeeService;

public class LambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {


    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {
        EmployeeService employeeService = new EmployeeService();
        String httpMethod = apiGatewayProxyRequestEvent.getHttpMethod();
        if (httpMethod.equals("POST")) {
            return employeeService.saveEmployee(apiGatewayProxyRequestEvent, context);
        } else if (httpMethod.equals("GET")) {
            if (apiGatewayProxyRequestEvent.getPathParameters() != null) {
                return employeeService.getEmployeeByID(apiGatewayProxyRequestEvent, context);
            } else {
                return employeeService.getAllEmployees(apiGatewayProxyRequestEvent, context);
            }
        } else if (httpMethod.equals("DELETE")) {
            if (apiGatewayProxyRequestEvent.getPathParameters() != null) {
                return employeeService.deleteEmployeeByEmpId(apiGatewayProxyRequestEvent, context);
            }

            throw new RuntimeException("Unsupported Method.." + apiGatewayProxyRequestEvent.getHttpMethod());
        }
        throw new RuntimeException("Unsupported Method.." + apiGatewayProxyRequestEvent.getHttpMethod());
    }
}
