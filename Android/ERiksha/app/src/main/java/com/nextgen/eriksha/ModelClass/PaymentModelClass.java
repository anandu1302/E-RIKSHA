package com.nextgen.eriksha.ModelClass;

public class PaymentModelClass {

    String id;
    String amount;
    String date;
    String cardnumber;
    String userName;
    String userNumber;

    public PaymentModelClass(String id,String amount,String date,String cardnumber,String userName,String userNumber) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.cardnumber = cardnumber;
        this.userName = userName;
        this.userNumber = userNumber;
    }

    public String getId(){
        return id;
    }

    public String getAmount(){
        return amount;
    }

    public String getDate(){
        return date;
    }

    public String getCardnumber(){
        return cardnumber;
    }

    public String getUserName(){
        return userName;
    }

    public String getUserNumber(){
        return userNumber;
    }
}
