package com.example.shang.indexbar;

/**
 * Created by Shang on 2017/10/16.
 */
public class Person implements Comparable<Person> {

    private String name;
    private String pinyin;

    public Person(String name) {
        this.name = name;
        this.pinyin = Utils.getPinyin(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }


    @Override
    public int compareTo(Person o) {
        return this.pinyin.compareTo(o.getPinyin());
    }
}
