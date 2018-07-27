import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

@WebServlet("/test/request/*")
public class HttpRequestServlet extends HttpServlet{

    @Override
    public void init() throws ServletException{}

    @Override
    public void destroy() {}

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        getRequsetInfo(req,resp);
    }



    private void getRequsetInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/plain;charset=utf-8");
        Writer out = resp.getWriter();
        StringBuilder strBuilder = new StringBuilder();
        String
                method = req.getMethod(),
                contextPath = req.getContextPath(),
                servletPath = req.getServletPath(),
                pathInfo = req.getPathInfo(),
                qs = req.getQueryString() ,
                uri = contextPath + servletPath + pathInfo + (null == qs ? "":'?' + qs),
                protocol = req.getProtocol(),
                headerName = "";
        Enumeration<String>
                header = null,
                headerNames = req.getHeaderNames();
        Map<String,String[]> params = req.getParameterMap();

        strBuilder.append("Test Http Request ~~~\r\n");
        strBuilder.append("=====================\r\n");
        // 请求行
        strBuilder.append(method).append(' ').append(uri).append(' ').append(protocol).append("\r\n");

        // 请求头
        while( headerNames.hasMoreElements()) {
            headerName = headerNames.nextElement();
            strBuilder.append(headerName).append(':').append(' ');
            header = req.getHeaders(headerName);
            // headerVals = "";
            while( header.hasMoreElements() ){
                // headerVals += header.nextElement();
                strBuilder.append(header.nextElement());
                if( header.hasMoreElements() ){
                    // headerVals += ",";
                    strBuilder.append(',');
                }
            }
            strBuilder.append("\r\n");
            // strBuilder.appendS(headerName).appendS(':').appendS("\r\n")
        }
        strBuilder.append("\r\n");

        // 请求参数
        String[] vals ;
        if(!"get".equals(method.toLowerCase()))
        for(String key: params.keySet()){
            strBuilder.append(key).append(':').append(' ');
            vals = params.get(key);
            if(1 >= vals.length){
                strBuilder.append(vals[0]);
            }else{
                strBuilder.append(Arrays.toString(vals));
            }
            strBuilder.append("\r\n");
        }
        strBuilder.append("=====================\r\n");

        strBuilder.append("ContextPath: ").append(contextPath).append("\r\n");
        strBuilder.append("ServletPath: ").append(servletPath).append("\r\n");
        strBuilder.append("PathInfo: ").append(pathInfo).append("\r\n");
        strBuilder.append("QueryString: ").append(qs).append("\r\n");
        strBuilder.append("RequestURI: ").append(req.getRequestURI()).append("\r\n");
        strBuilder.append("RealPath: ").append(req.getServletContext().getRealPath(".")).append("\r\n");
        strBuilder.append("PathTranslated: ").append(req.getPathTranslated()).append("\r\n");

        strBuilder.append("\r\n");

        String test = null;
        switch(pathInfo){
            case "/get_cookies": test = getCookies(req);break;

            default: ;
        }

        strBuilder.append(test);
        print(out,strBuilder.toString());
        out.close();
    }

    private void println(Writer out, String str) throws IOException {
        print(out,str+"\r\n");
    }
    private void print(Writer out,String str) throws IOException{
        out.write(str);
        System.out.print(str);
    }

    private String getCookies(HttpServletRequest req){
        String res = "Test Http Cookies ~~~\r\n=====================\r\n";
        Cookie[] cookies = req.getCookies();
        res += "cookies: "+ Arrays.toString(Arrays.stream(cookies).map((Cookie cookie)->{return cookie.getName()+"="+cookie.getValue()+",";}).toArray()) + "\r\n";
        res += "=====================\r\n\r\n";

        return res;
    }
    private void SetCookies(HttpServletResponse resp,String name,String val){

    }
}