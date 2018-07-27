import sun.misc.BASE64Encoder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.security.MessageDigest;
import java.util.Random;

@WebServlet("/test/servlet/form/*")
public class SessionPreventSubmit extends HttpServlet {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        switch(req.getPathInfo()){
            case "/token": makeToken(req,resp);break;
            case "/submit": handleForm(req,resp);break;
            default: ;
        }
    }

    private void makeToken(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        String token = Tokenproccessor.getInstance().makeToken();
        req.getSession().setAttribute("token",token);
        resp.setContentType("text/plain;charset=utf-8");
        resp.getWriter().write(token);
    }

    private void handleForm(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        Writer out = resp.getWriter();
        if(isValidForm(req)){
            try {
                Thread.sleep(300);
            } catch (Exception e) {
                //TODO: handle exception
            }
            String toke_s = (String) req.getSession().getAttribute("token");
            out.write("submit by token "+ toke_s);
            System.out.println("submit by token "+ toke_s);
            req.getSession().removeAttribute("token");
        }else{
            out.write("don't submit; check token");
            System.out.println("don't submit; check token");
        }
        out.close();
    }

    private boolean isValidForm(HttpServletRequest req){
        String toke_c = req.getParameter("token");
        if( null == toke_c ) {
            return false;
        }
        String toke_s = (String) req.getSession().getAttribute("token");
        
        if( null == toke_s ){
            return false;
        }

        if(toke_s.equals(toke_c)){
            return true;
        }
        return false;
    }
}

class Tokenproccessor{

    private Tokenproccessor(){}

    private static final Tokenproccessor INSTANCE = new Tokenproccessor();

    public static Tokenproccessor getInstance(){
        return INSTANCE;
    }

    public String makeToken(){
        String token = (System.currentTimeMillis()+new Random().nextInt(9999999))+"";

        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] md5 = md.digest(token.getBytes());

            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(md5);
        } catch (Exception e) {
            //TODO: handle exception
        }
        return null;
    }
}