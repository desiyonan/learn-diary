import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

@WebServlet("/test/session")
public class SessionInfoServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        StringBuilder strBd = new StringBuilder();

        String sid = session.getId();
        Date createD = new Date(session.getCreationTime());
        boolean isNew = session.isNew();
        Date lastAsD = new Date(session.getLastAccessedTime());
        int maxInt = session.getMaxInactiveInterval();
        Enumeration<String> attrNames = session.getAttributeNames();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        append(strBd,"sessionId: ",sid);
        append(strBd,"createDate: ",sdf.format(createD));
        append(strBd,"isNew: ",isNew);
        append(strBd,"lastAccesssD: ",sdf.format(lastAsD));
        append(strBd,"maxInactiveInterval: ",maxInt);

        append(strBd,"Attributes:  ","");
        String attrN = null;
        String[] attrs= null;
        while(attrNames.hasMoreElements()){
            attrN = attrNames.nextElement();
            attrs = (String[]) session.getAttribute(attrN);
            append(strBd,attrN+": ", Arrays.toString(attrs));
        }

        append(strBd,"Parameters:  ","");
        Map<String,String[]> params = req.getParameterMap();
        String[] paramV = null;
        String[] temp = null;
        for(String paramN:params.keySet()){
            paramV = params.get(paramN);
            append(strBd,paramN+": ",Arrays.toString(paramV));

            attrs = (String[]) session.getAttribute(paramN);
            if(null == attrs) temp = paramV;
            else{
                temp = new String[attrs.length+paramV.length];
                System.arraycopy(attrs,0,temp,0,attrs.length);
                System.arraycopy(paramV,0,temp,attrs.length,paramV.length);
            }
            session.setAttribute(paramN,temp);
        }

        String res = strBd.toString();

        resp.getWriter().write(res);
        System.out.println(res);
    }

    private void append(StringBuilder strBd,String key,Object val){
        strBd.append(key).append(val).append("\r\n");
    }
}