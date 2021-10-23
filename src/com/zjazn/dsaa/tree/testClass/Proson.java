package com.zjazn.dsaa.tree.testClass;

public class Proson implements Comparable{
    private String name;
    private int age;

    public Proson(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }



    @Override
    public String toString() {
        return "Proson{age=" + age +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return this.age - ((Proson)o).getAge();
    }
}
