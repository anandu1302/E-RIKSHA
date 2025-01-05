package com.nextgen.eriksha.ModelClass;

public class MyRidesModelClass {

    String id;
    String start;
    String destination;
    String amount;
    String date;
    String status;
    String name;
    String number;

    public MyRidesModelClass(String id, String start,String destination, String amount, String date, String status, String name, String number) {
        this.id = id;
        this.start = start;
        this.destination = destination;
        this.amount = amount;
        this.date = date;
        this.status = status;
        this.name = name;
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public String getStart() {
        return start;
    }

    public String getDestination() {
        return destination;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate(){
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getNumber(){
        return number;
    }
}
