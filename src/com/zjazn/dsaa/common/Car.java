package com.zjazn.dsaa.common;

public class Car {
    private String id;
    private String name;
    private String Description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "com.zx.data.common.Car{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }

    public Car(String id, String name, String description) {
        this.id = id;
        this.name = name;
        Description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o != null ) {
            Car j = (Car) o;
            if (id.equals(j.getId())) {
                return true;
            }
        }
       return false;
    }
    @Override
    public void finalize() {
        System.out.println("对象被销毁了！");
    }


}
