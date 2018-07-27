
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LifeCycleServlet extends HttpServlet{
    // Servlet 接口中定义的三个生命周期方法
    /**
     * 创建 Servlet 对象后，对其进行初始化
     */
    @Override
    public void init() throws ServletException{
        System.out.println("init()");
    }

    /**
     * 处理客户端(包括浏览器)发来的请求，在HttpServlet中具体的实现被划分到 doGet、doPost中，在此处的作用是判断请求方式以调用
     * @Params req 
     *          客户端的请求对象
     * @Params resp
     *          服务器的响应对象
     */
    @Override
    public void service(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        System.out.println("service()");
        super.service(req,resp);
    }
    
    /**
     * 销毁，代表 Servlet 生命周期的结束，只被调用一次，通常是用来关闭数据库连接，后台线程，或者外部资源处理
     */
    @Override
    public void destroy() {
        System.out.println("destroy()");
    }

    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet()");
    }    
    //...

}