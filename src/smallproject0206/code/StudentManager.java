package smallproject0206.code;

import java.util.Scanner;

public class StudentManager extends StudentDBIO {

    Scanner scanner = new Scanner(System.in);


    public void run() {
        System.out.println("1. add student info");
        System.out.println("2. view student info");
        System.out.println("choice menu");

        int menu = scanner.nextInt();
        switch (menu) {
            case 1:
                inputStudent();
                break;
            case 2:
                outputStudent();
                break;
        }
    }

    @Override
    public void inputStudent() {
        System.out.println("add");
        System.out.print("sno: ");
        String sno = scanner.next();
        System.out.print("name: ");
        String name = scanner.next();
        System.out.print("korean: ");
        int korean = scanner.nextInt();
        System.out.print("english: ");
        int english = scanner.nextInt();
        System.out.print("math: ");
        int math = scanner.nextInt();
        System.out.print("science: ");
        int science = scanner.nextInt();

        Student student = new Student(sno, name, korean, english, math, science);
        students.add(student);
        System.out.println("success");
        run();
    }

    @Override
    public void outputStudent() {
            for (Student student : students) {
                System.out.println(student);
            }
            run();
    }
}

