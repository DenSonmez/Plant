package dk.exam;

import dk.exam.config.ApplicationConfig;
import io.javalin.Javalin;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ApplicationConfig
                .startServer(
                        Javalin.create(), 7070);
    }

}