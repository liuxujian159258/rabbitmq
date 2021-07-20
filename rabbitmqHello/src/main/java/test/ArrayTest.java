package test;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ArrayTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();
        OutputStream outputStream = new FileOutputStream(new File("C:\\Users\\admin\\Desktop\\1.txt"));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        for (int i = 0; i < 1; i++) {
            String name = in.next();
            double score1 = in.nextDouble();
            double score2 = in.nextDouble();
            double score3 = in.nextDouble();
            students.add(new Student(name, score1,score2,score3));
            objectOutputStream.writeObject(students.get(i));
            System.out.println(students.get(i));
        }
        InputStream inputStream = new FileInputStream(new File("C:\\Users\\admin\\Desktop\\1.txt"));
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        System.out.println(objectInputStream.readObject());
    }
}
