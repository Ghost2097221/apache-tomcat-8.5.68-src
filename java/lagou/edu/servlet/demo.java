package lagou.edu.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class demo {
    public static void main(String[] args) throws IOException {
        String cmd="tasklist";
        Runtime runtime = Runtime.getRuntime();
        InputStream inputStream = runtime.exec(cmd).getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int a=-1;
        while ((a=inputStream.read(bytes))!=-1){
            outputStream.write(bytes,0,a);
        }
        System.out.println(new String(outputStream.toByteArray()));
    }
}
