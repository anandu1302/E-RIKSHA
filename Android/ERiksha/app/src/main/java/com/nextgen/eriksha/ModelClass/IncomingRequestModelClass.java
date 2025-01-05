package com.nextgen.eriksha.ModelClass;

public class IncomingRequestModelClass {

    String id;
    String name;
    String number;
    String pickup;
    String dropoff;

    public IncomingRequestModelClass(String id,String name,String number,String pickup,String dropoff) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.pickup = pickup;
        this.dropoff = dropoff;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getNumber(){
        return number;
    }

    public String getPickup(){
        return pickup;
    }

    public String getDropoff(){
        return dropoff;
    }
}
