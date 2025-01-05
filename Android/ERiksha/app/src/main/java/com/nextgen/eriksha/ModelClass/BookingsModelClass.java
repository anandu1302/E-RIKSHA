package com.nextgen.eriksha.ModelClass;

public class BookingsModelClass {

    String id;
    String start;
    String destination;
    String amount;
    String date;
    String status;
    String driverId;
    String driverName;

    public BookingsModelClass(String id,String start,String destination,String amount,String date,String status,String driverId,String driverName) {
        this.id = id;
        this.start = start;
        this.destination = destination;
        this.amount = amount;
        this.date = date;
        this.status = status;
        this.driverId = driverId;
        this.driverName = driverName;
    }

    public String getId(){
        return id;
    }

    public String getStart(){
        return start;
    }

    public String getDestination(){
        return destination;
    }

    public String getAmount(){
        return amount;
    }

    public String getDate(){
        return date;
    }

    public String getStatus(){
        return status;
    }

    public String getDriverId(){
        return driverId;
    }

    public String getDriverName(){
        return driverName;
    }
}
