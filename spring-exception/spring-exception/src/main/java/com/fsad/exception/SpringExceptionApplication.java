package com.fsad.exception;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringExceptionApplication – Main Entry Point
 * FSAD Skill-9 | Global Exception Handling using @ControllerAdvice
 */
@SpringBootApplication
public class SpringExceptionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringExceptionApplication.class, args);

        System.out.println("\n╔═════════════════════════════════════════════════════╗");
        System.out.println(  "║   FSAD Skill-9 | Global Exception Handling          ║");
        System.out.println(  "╠═════════════════════════════════════════════════════╣");
        System.out.println(  "║  All Students : http://localhost:8080/student/all    ║");
        System.out.println(  "║  Valid ID     : http://localhost:8080/student/1      ║");
        System.out.println(  "║  404 Error    : http://localhost:8080/student/99     ║");
        System.out.println(  "║  400 Error    : http://localhost:8080/student/-1     ║");
        System.out.println(  "║  Type Error   : http://localhost:8080/student/abc    ║");
        System.out.println(  "╚═════════════════════════════════════════════════════╝\n");
    }
}
