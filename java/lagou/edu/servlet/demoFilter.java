package lagou.edu.servlet;

import javax.servlet.*;
import java.io.IOException;

public class demoFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filter init.....");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Filter执行了");
        //考虑是否放行
        //放行
        chain.doFilter(request,response);

        System.out.println("filter返回了");
    }

    @Override
    public void destroy() {
        System.out.println("Filter destroy.....");
    }
}
