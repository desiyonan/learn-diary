import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/test/session/auth")
public class SessionAuthServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuilder strBd = new StringBuilder();

        appendS(strBd,
                "Test Session Auth~~~\r\n",
                "===============\r\n");

        HttpSession ss = req.getSession(true);

        String auth = (String) ss.getAttribute("auth");

        if( null != auth){
            appendS(strBd,"authed by ",auth,"\r\n");
        }else{
            appendS(strBd,"didn't auth \r\n");
            String username = (String) req.getParameter("username");
            String password = (String) req.getParameter("password");
            if (null != username && null != password && DB.get(username).equals(password)) {
                appendS(strBd, "authing by ", username,":",password,"\r\n");
                ss.setAttribute("auth",username);
            }else{
                appendS(strBd,"can't auth by ",username,":",password,"\r\n");
            }
        }
        appendS(strBd,"===============\r\n");
        resp.getWriter().write(strBd.toString());
    }

    public static void appendS(StringBuilder strBd, String ...strs){
        Arrays.stream(strs).forEach(strBd::append);
    }

}

class DB{
    private final static Map<String,String> user;

    static {
        user = new HashMap<>();
        user.put("laowang","gebi");
        user.put("xiaozheng","lingju");
        user.put("test","12345");
    }

    public static String get(String name){
        return user.get(name);
    }
}