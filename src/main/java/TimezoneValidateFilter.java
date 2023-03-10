import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String timeZone = req.getParameter("timezone");
        if (timeZone != null) {
            try {
                timeZone = timeZone.replace(" ", "+");
                ZoneId.of(timeZone);
                res.addCookie(new Cookie("timezone", timeZone));
            } catch (Exception e) {
                res.getWriter().write("Invalid timezone");
                res.setStatus(400);
                res.getWriter().close();
            }
        }
        chain.doFilter(req, res);
    }
}
