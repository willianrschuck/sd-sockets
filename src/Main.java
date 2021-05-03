import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import socket.ApiMethod;
import socket.Methods;

public class Main {

	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		List<Method> methodsAnnotatedWith = getMethodsAnnotatedWith(Methods.class);
		for (Method method : methodsAnnotatedWith) {
			Methods methods = new Methods();
			System.out.println(method.getName());
			method.invoke(methods);
		}
		
	}
	
	public static List<Method> getMethodsAnnotatedWith(final Class<?> type) {
	    final List<Method> methods = new ArrayList<Method>();
	    Class<?> klass = type;
	    while (klass != Object.class) {
	        for (final Method method : klass.getDeclaredMethods()) {
	            if (method.isAnnotationPresent(ApiMethod.class)) {
	            	ApiMethod annotInstance = method.getAnnotation(ApiMethod.class);	
	                System.out.println(annotInstance.value());
	                methods.add(method);
	            }
	        }
	        klass = klass.getSuperclass();
	    }
	    return methods;
	}
	
}
