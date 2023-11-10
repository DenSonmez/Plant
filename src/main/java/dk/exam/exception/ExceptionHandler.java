package dk.exam.exception;

import io.javalin.http.Context;

import java.time.LocalDateTime;


public class ExceptionHandler {
    //I en typisk scenarie bruger du apiExceptionHandler til at håndtere undtagelser,
    // der er specifikke for applikationen og definerer tilpassede statuskoder og beskeder,
    public void apiExceptionHandler(ApiException e, Context ctx) {
        ctx.status(e.getStatusCode());
        ctx.json(new Message(e.getStatusCode(), e.getMessage(), LocalDateTime.now().toString()));
    }

    public void exceptionHandler(Exception e, Context ctx) {
        ctx.status(500);
        ctx.json(new Message(500, e.getMessage(), LocalDateTime.now().toString()));
    }
}
//exceptionHandler bruges som en fallback for at håndtere alle andre uventede undtagelser
// og returnere en generel fejlmeddelelse med statuskode 500.