package dk.exam.routes;
import dk.exam.exception.ApiException;
import dk.exam.exception.ExceptionHandler;
import dk.exam.routes.Del1.MockRoutes;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

    private final ExceptionHandler exceptionController = new ExceptionHandler();
    private int count = 0;

    private final Logger LOGGER = LoggerFactory.getLogger(Routes.class);

    private final PlantRoutes plantRoutes = new PlantRoutes();
    private final MockRoutes mockRoutes = new MockRoutes();

    private void requestInfoHandler(Context ctx) {
        String requestInfo = ctx.req().getMethod() + " " + ctx.req().getRequestURI();
        ctx.attribute("requestInfo", requestInfo);
    }

    public EndpointGroup getRoutes(Javalin app) {
        return () -> {
            app.before(this::requestInfoHandler);

            app.routes(() -> {
               // path("/", mockRoutes.getMockRoutes());
               path("/", plantRoutes.getRoutes());

            });

            app.after(ctx -> LOGGER.info(" Request {} - {} was handled with status code {}", count++, ctx.attribute("requestInfo"), ctx.status()));

            //til at håndtere undtagelser disse vil bive sendt til exceptionHandler
            app.exception(ApiException.class, exceptionController::apiExceptionHandler);
            app.exception(Exception.class, exceptionController::exceptionHandler);
        };
    }
}
