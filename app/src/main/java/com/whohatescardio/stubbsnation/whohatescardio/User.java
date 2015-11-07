package com.whohatescardio.stubbsnation.whohatescardio;

/**
 * Created by peter on 07/11/15.
 */
import java.util.HashMap;

public class User {
    private String name;
    private char   sex;
    private float  age;
    private float  weight;
    private float  activityLevelFactor;

    private HashMap<String,Float> alf;

    public User(){
        this.alf = new HashMap<>();
        alf.put("sedentary",(float)1.2);
        alf.put("lightlyActive",(float)1.375);
        alf.put("moderatelyActive",(float)1.55);
        alf.put("veryActive",(float)1.725);
        alf.put("extremelyActive",(float)1.9);
    }

    public User(String name, char sex, int age, float weight, String activityLevelFactor){
        this.alf = new HashMap<>();
        alf.put("sedentary",(float)1.2);
        alf.put("lightlyActive",(float)1.375);
        alf.put("moderatelyActive",(float)1.55);
        alf.put("veryActive",(float)1.725);
        alf.put("extremelyActive",(float)1.9);

        setName(name);
        setSex(sex);
        setAge(age);
        setWeight(weight);
        setActivityLevelFactor(activityLevelFactor);
    }

    private String getName(){
        return this.name;
    }
    private char getSex(){
        return this.sex;
    }
    private float getAge(){
        return this.age;
    }
    private float getWeight(){
        return this.weight;
    }
    private float getActivity(){
        return this.activityLevelFactor;
    }

    private void setName(String n){
        this.name = n;
    }
    private void setSex(char s){
        this.sex = s;
    }
    private void setAge(int a){
        this.age = a;
    }
    private void setWeight(float w){
        this.weight = w;
    }
    private void setActivityLevelFactor(String act){
        float alf = this.alf.get(act);
        setActivityLevelFactor(alf);

    }
    private void setActivityLevelFactor(float n){
        this.activityLevelFactor = n;
    }





}
