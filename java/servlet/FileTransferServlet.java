import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

import static test.utils.appendS;

@WebServlet("/test/file/*")
@MultipartConfig(location="./test/files/")
public class FileTransferServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        switch(req.getPathInfo()){
            case "/upload" : uploadFiles(req,resp);break;
            case "/download": ;break;
        }
    }

    private void uploadFiles(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/plain;charset=utf-8");

        Part part = req.getPart("file");
        String headerInfo = null;
        if( null != part){
            headerInfo = part.getHeader("content-disposition");
            String filename = headerInfo.substring(headerInfo.lastIndexOf("=")+2, headerInfo.length()-1);

            String path = req.getServletContext().getRealPath("/")+"/test/file/"+filename;
            
            part.write(path);
        }

        StringBuilder strBd = new StringBuilder();
        for(String name:part.getHeaderNames()){
            headerInfo = part.getHeader(name);
            appendS(strBd,name,":",headerInfo);
        }

    }
    private void downloadFile(HttpServletRequest req, HttpServletResponse resp){

    }
}