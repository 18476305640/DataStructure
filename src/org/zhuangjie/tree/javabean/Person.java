package org.zhuangjie.tree.javabean;

public class Person implements Comparable<Person>{
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Person e2) {
        return this.age - e2.age;
    }

    @Override
    public String toString() {
        return ""+this.age;
    }
}
