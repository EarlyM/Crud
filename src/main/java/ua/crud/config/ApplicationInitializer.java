package ua.crud.config;

import org.apache.wicket.protocol.http.WicketFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import ua.crud.WicketApplication;

import javax.servlet.*;
import java.util.EnumSet;

public class ApplicationInitializer implements WebApplicationInitializer{
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context =  new AnnotationConfigWebApplicationContext();
        context.register(ApplicationConfig.class);
        context.register(SecurityConfig.class);

        FilterRegistration.Dynamic springSecurityFilterChain = servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
        springSecurityFilterChain.addMappingForUrlPatterns(EnumSet.of(DispatcherType.ERROR, DispatcherType.REQUEST), false, "/*");

        servletContext.addListener(new ContextLoaderListener(context));

        WicketFilter wicketFilter = new WicketFilter(new WicketApplication()) {
            @Override
            public void init(boolean isServlet, FilterConfig filterConfig) throws ServletException {
                setFilterPath("");
                super.init(isServlet, filterConfig);
            }
        };
        FilterRegistration.Dynamic wicketFilterReg = servletContext.addFilter("wicketFilter", wicketFilter);
        wicketFilterReg.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "*");
    }
}
