import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String timezone = req.getParameter("timezone");
        resp.getWriter().write(getTime(timezone));
        resp.getWriter().close();
    }

    private String getTime(String timeZone) {
        String z;
        if (timeZone == null || timeZone.equals("UTC-0") || timeZone.equals("UTC+0")) {
            z = "";
        } else if (timeZone.substring(3, 4).equals("-")) {
            z = timeZone.substring(3);
        } else {
            z = "+" + timeZone.substring(4);
        }
        LocalDateTime currentTime1 = LocalDateTime.now(ZoneId.of("UTC" + z));
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'" + z);
        String formattedTime = currentTime1.format(formatter1);
        return formattedTime;
    }
}
