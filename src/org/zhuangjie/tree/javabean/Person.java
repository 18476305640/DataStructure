package org.zhuangjie.tree.javabean;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && Objects.equals(age, person.age);
    }

    @Override
    public int hashCode() {
        int hash = Integer.hashCode(age);
        hash = 31 * hash + (name==null?0:name.hashCode());
        return hash; // hash == Objects.hash(name, age);
    }
}
