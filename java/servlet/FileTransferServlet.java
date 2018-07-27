import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static test.utils.appendS;
import static test.utils.print;

@WebServlet("/test/file/*")
@MultipartConfig
public class FileTransferServlet extends HttpServlet {
    private static String BASEDIR;

    @Override
    public void init(){
        BASEDIR = getServletContext().getRealPath("/test/file");
        File dir = new File(BASEDIR);
        if(!dir.exists()){
            dir.mkdirs();
        }
    }
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        switch(req.getPathInfo().intern()){
            case "/uploaded" : viewFile(req,resp);break;
            case "/upload" : uploadFiles(req,resp);break;
            case "/download": downloadFile(req,resp);break;
        }
    }

    private void uploadFiles(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/plain;charset=utf-8");
        StringBuilder strBd = new StringBuilder();

        Part part = req.getPart("file");
        
        String headerInfo = null;
        try {
        
            if( null != part){
                for(String name:part.getHeaderNames()){
                    headerInfo = part.getHeader(name);
                    appendS(strBd,name,":",headerInfo,"\r\n");
                }
                headerInfo = part.getHeader("content-disposition");
                String filename = headerInfo.substring(headerInfo.lastIndexOf("=")+2, headerInfo.length()-1);
                req.getSession().setAttribute("uped",filename);
                part.write(BASEDIR+File.separator+filename);
                print(resp.getWriter(),strBd.toString());
            }
            
        } catch (Exception e) {
            //TODO: handle exception
        }
    }
    public void downloadFile(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        getFile(req,resp,"attachment");
    }
    public void viewFile(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        getFile(req,resp,"inline");
    }
    public void getFile(HttpServletRequest req, HttpServletResponse resp,String method) throws IOException {
        String filename= (String) req.getSession().getAttribute("uped");
        
        File file = new File(BASEDIR+File.separator+filename);
        try {
            if (file.exists()) {
                resp.setContentType(getServletContext().getMimeType(filename));
                if ("attachment".equals(method))
                    resp.setHeader("content-disposition", method + "; filename=" + filename);
                resp.addHeader("Content-Length","" + file.length()); 

                FileInputStream fis = new FileInputStream(file);
                ServletOutputStream fos = resp.getOutputStream();

                byte[] buff = new byte[1024];
                for (int i = 0; -1 != (i = fis.read(buff)); ) {
                    fos.write(buff, 0, i);
                }
                fos.flush();
                fos.close();
            }
        }catch (Exception e){

        }
    }
}