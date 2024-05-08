package org.example;


import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        Executor executor = new Executor();
        try {
            String output = executor.executeCommand(" mvn dependency:tree -DoutputFile=${PWD}/mavenDependency.txt -DoutputType=text -B");
            System.out.println(output);
        }catch (IOException|InterruptedException e) {
            e.printStackTrace();
        }
    }
}
