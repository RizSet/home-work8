import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    private TemplateEngine engine;

    @Override
    public void init() throws ServletException {
        engine = new TemplateEngine();
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("D:\\IT\\Workspace\\IDEA\\home-work8\\templates\\");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String timezone = req.getParameter("timezone");
        Cookie[] cookies = req.getCookies();
        String time = getTime(timezone, cookies);
        Context context = new Context(
                req.getLocale(),
                Map.of("time", time)
        );
        engine.process("time", context, resp.getWriter());
        resp.getWriter().close();
    }

    private String getTime(String timeZone, Cookie[] cookies) {
        String z = "";

        if (timeZone == null && cookies != null) {
            timeZone = cookies[0].getValue();
        }
        if (timeZone != null) {
            timeZone = timeZone.replace(" ", "+");
            z = timeZone.substring(3);
        }

        LocalDateTime currentTime1 = LocalDateTime.now(ZoneId.of("UTC" + z));
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'" + z);
        String formattedTime = currentTime1.format(formatter1);
        return formattedTime;
    }
}
