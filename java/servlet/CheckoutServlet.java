import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/test/session/checkout/*")
public class CheckoutServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        switch(req.getPathInfo()){
            case "/makeimg": makeImg(req,resp);break;
            case "/main" : checkout(req,resp);break;
        }
    }

    private void checkout(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        if(check(req)){
            resp.getWriter().write("pass");
            req.getSession().removeAttribute("num");
        }else{
            resp.getWriter().write("dont pass");
        }
    }

    private void makeImg(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        BufferedImage bufferedImg = new BufferedImage(80,20,BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = (Graphics2D) bufferedImg.getGraphics();
        g2d.setColor(Color.white);
        g2d.fillRect(0,0,80,20);
        g2d.setFont(new Font(null,Font.BOLD,20));
        g2d.setColor(Color.RED);

        String num = makeNum();

        g2d.drawString(num,0,20);

        req.getSession().setAttribute("num",num);

        resp.setHeader("Pragma","no-cache");
        resp.setHeader("Cache-control","no-cache");
        resp.setDateHeader("Expires",0);
        resp.setHeader("Content-type","image/jpeg");
        ImageIO.write(bufferedImg,"jpg",resp.getOutputStream());
    }

    private boolean check(HttpServletRequest req){
        String num_c = req.getParameter("num");
        String num_s = (String)req.getSession().getAttribute("num");

        if( null != num_c && null != num_s && num_s.equals(num_c)){
            return true;
        }
        return false;
    }

    private String makeNum() {

        Random random = new Random();

        //生成0-6位的随机数
        int num = random.nextInt(999999);

        //验证码的数位全都要6位数，于是将该随机数转换成字符串，不够位数就添加
        String randomNum = String.valueOf(num);

        //使用StringBuffer来拼凑字符串
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 6 - randomNum.length(); i++) {
            stringBuffer.append("0");
        }

        return stringBuffer.append(randomNum).toString();
    }
}