import org.apache.catalina.connector.Connector;
import org.apache.catalina.connector.Response;
import org.apache.catalina.core.ApplicationContext;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardService;
import org.apache.coyote.RequestInfo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class TomcatEcho_ {
    public static org.apache.catalina.connector.Response response=null;
    public  TomcatEcho_() {
        org.apache.catalina.loader.WebappClassLoaderBase webappClassLoaderBase = (org.apache.catalina.loader.WebappClassLoaderBase) Thread.currentThread().getContextClassLoader();
        StandardContext standardContext = (StandardContext) webappClassLoaderBase.getResources().getContext();
        try {
            Field context = Class.forName("org.apache.catalina.core.StandardContext").getDeclaredField("context");
            context.setAccessible(true);
            ApplicationContext ApplicationContext = (ApplicationContext)context.get(standardContext);
            Field service = Class.forName("org.apache.catalina.core.ApplicationContext").getDeclaredField("service");
            service.setAccessible(true);
            StandardService standardService = (StandardService)service.get(ApplicationContext);
            Field connectors = Class.forName("org.apache.catalina.core.StandardService").getDeclaredField("connectors");
            connectors.setAccessible(true);
            Connector[] connector = (Connector[])connectors.get(standardService);
            Field protocolHandler = Class.forName("org.apache.catalina.connector.Connector").getDeclaredField("protocolHandler");
            protocolHandler.setAccessible(true);
            Class<?>[] AbstractProtocol_list = Class.forName("org.apache.coyote.AbstractProtocol").getDeclaredClasses();
            for (Class<?> aClass : AbstractProtocol_list) {
                if (aClass.getName().length()==52){
                    java.lang.reflect.Method getHandlerMethod = org.apache.coyote.AbstractProtocol.class.getDeclaredMethod("getHandler",null);
                    getHandlerMethod.setAccessible(true);
                    Field globalField = aClass.getDeclaredField("global");
                    globalField.setAccessible(true);
                    org.apache.coyote.RequestGroupInfo requestGroupInfo = (org.apache.coyote.RequestGroupInfo) globalField.get(getHandlerMethod.invoke(connector[0].getProtocolHandler(), null));
                    Field processors = Class.forName("org.apache.coyote.RequestGroupInfo").getDeclaredField("processors");
                    processors.setAccessible(true);
                    java.util.List<RequestInfo> RequestInfo_list = (java.util.List<RequestInfo>) processors.get(requestGroupInfo);
                    Field req = Class.forName("org.apache.coyote.RequestInfo").getDeclaredField("req");
                    req.setAccessible(true);
                    for (RequestInfo requestInfo : RequestInfo_list) {
                        org.apache.coyote.Request request1 = (org.apache.coyote.Request )req.get(requestInfo);
                        org.apache.catalina.connector.Request request2 = ( org.apache.catalina.connector.Request)request1.getNote(1);
                        response = request2.getResponse();
                        String host = request2.getHeader("Host");
                        System.out.println(host);
                    }
                }
            }
        } catch (NoSuchFieldException | InvocationTargetException | IllegalAccessException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
