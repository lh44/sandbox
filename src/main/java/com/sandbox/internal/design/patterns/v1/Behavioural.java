package com.sandbox.internal.design.patterns.v1;

public class Behavioural {

    //Servlet Filters are an example from the JEE ecosystem that works in this way.
    static class ChainOfResponsibility {

        /*public class AuthenticatingFilter implements Filter {
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                    throws IOException, ServletException {
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                if (!"MyAuthToken".equals(httpRequest.getHeader("X-Auth-Token")) {
                    return;
                }
                chain.doFilter(request, response);
            }
        }*/
    }
}
