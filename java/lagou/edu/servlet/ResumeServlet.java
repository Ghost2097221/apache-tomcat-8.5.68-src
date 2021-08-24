package lagou.edu.servlet;

import org.apache.catalina.core.StandardContext;
import org.eclipse.jdt.internal.compiler.ast.TrueLiteral;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ResumeServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        System.out.println("11111111111");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("111111111111111");
        System.out.println("========================================================");
        System.out.println(request.getServletContext().getAttribute("servletname"));
        System.out.println(request.getAttribute("requestname"));
        System.out.println("========================================================");
        System.out.println(request.getServletContext());
        try {
            Class<?> aClass = Class.forName("org.apache.catalina.core.ApplicationDispatcher");
            Field wrap_same_object = aClass.getDeclaredField("WRAP_SAME_OBJECT");
            Field modifiers = wrap_same_object.getClass().getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(wrap_same_object, wrap_same_object.getModifiers() & ~Modifier.FINAL);
            wrap_same_object.setAccessible(true);
            if (!wrap_same_object.getBoolean(null)) {
                wrap_same_object.setBoolean(null, true); //该变量时static final类型变量，需要使用上述方法修改。
            }//避免重复设置
            else {  //此时已经可以成功获取值了
                Class<?> aClass1 = Class.forName("org.apache.catalina.core.ApplicationFilterChain");
                Field lastServicedRequest = aClass1.getDeclaredField("lastServicedRequest");
                lastServicedRequest.setAccessible(true);
                ThreadLocal t = (ThreadLocal) lastServicedRequest.get(null);
                ServletRequest s = (ServletRequest) t.get();
                ServletContext servletContext = s.getServletContext();
                System.out.println("111111111");


            }

            //将lastServicedRequest和lastServicedResponse两个变量存储位request对象和response对象
            Class<?> aClass1 = Class.forName("org.apache.catalina.core.ApplicationFilterChain");
            Field lastServicedRequest = aClass1.getDeclaredField("lastServicedRequest");
            lastServicedRequest.setAccessible(true);
            Field modifiers1 = lastServicedRequest.getClass().getDeclaredField("modifiers");
            modifiers1.setAccessible(true);
            modifiers1.setInt(lastServicedRequest, lastServicedRequest.getModifiers() & ~Modifier.FINAL);
            if (lastServicedRequest.get(null) == null) {
                lastServicedRequest.set(null, new ThreadLocal<>());
            }

            Field lastServicedResponse = aClass1.getDeclaredField("lastServicedResponse");
            Field modifiers2 = lastServicedResponse.getClass().getDeclaredField("modifiers");
            modifiers2.setAccessible(true);
            modifiers2.setInt(lastServicedResponse, lastServicedResponse.getModifiers() & ~Modifier.FINAL);
            lastServicedResponse.setAccessible(true);
            if (lastServicedResponse.get(null) == null) {
                lastServicedResponse.set(null, new ThreadLocal<>());
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
