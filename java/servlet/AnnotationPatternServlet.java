import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@WebServlet("/AnnotationPatternServlet")
public class AnnotationPatternServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = "AnnotationPatternServlet";
        Writer out = resp.getWriter();
        out.write(msg);
        System.out.println(msg);
        out.close();
    }
}
