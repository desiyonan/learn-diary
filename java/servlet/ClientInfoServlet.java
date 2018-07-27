import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@WebServlet("/test/clientinfo")
public class ClientInfoServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        printClientInfo(req,resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        printClientInfo(req,resp);
    }
    
    private void printClientInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=utf-8");
        StringBuilder strBuilder = new StringBuilder();

        strBuilder.append("ip地址，RemoteAddr: ").append(req.getRemoteAddr()).append("\r\n");
        strBuilder.append("主机名，RemoteHost: ").append(req.getRemoteHost()).append("\r\n");
        strBuilder.append("RemoteUser: ").append(req.getRemoteUser()).append("\r\n");
        resp.setHeader("Content-Disposition","inline; filename=\"fname.ext\"");
        print(resp.getWriter(),strBuilder.toString());
    }

    private void print(Writer out, String str) throws IOException {
        out.write(str);
        System.out.print(str);
    }
}