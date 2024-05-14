package com.mohantech.lambda.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Employee")
public class Employee {

    @DynamoDBHashKey(attributeName = "empId")
    private int empId;
    @DynamoDBAttribute(attributeName = "empName")
    private String empName;

    @DynamoDBAttribute(attributeName = "empEmail")
    private String empEmail;
    @DynamoDBAttribute(attributeName = "empPhoneNumber")
    private String empPhoneNumber;
    @DynamoDBAttribute(attributeName = "empAddress")
    private String empAddress;

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getEmpPhoneNumber() {
        return empPhoneNumber;
    }

    public void setEmpPhoneNumber(String empPhoneNumber) {
        this.empPhoneNumber = empPhoneNumber;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }
}
