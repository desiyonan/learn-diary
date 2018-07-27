import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@WebServlet("/test/auth/")
public class HttpAuthorization extends HttpServlet {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain;charset=utf-8");

        String authorization = req.getHeader("Authorization");
        Writer out = resp.getWriter();
        if(null != authorization){
            out.write("authorization :"+authorization);
        }else{
            out.write("don't authorization");
        }

        out.close();
    }

}