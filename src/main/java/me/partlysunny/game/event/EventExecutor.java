package me.partlysunny.game.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventExecutor {

    private static final List<GameListener> listeners = new ArrayList<>();

    public static void register(final GameListener listener) {
        listeners.add(listener);
    }

    public static void invoke(Event event) {
        for (final GameListener listener : listeners) {
            Method[] declaredMethods = listener.getClass().getDeclaredMethods();
            for (final Method method : declaredMethods) {
                Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
                GameEventHandler eventAnnotation = null;
                for (final Annotation annotation : declaredAnnotations) {
                    if (annotation.annotationType() == GameEventHandler.class) {
                        eventAnnotation = (GameEventHandler) annotation;
                    }
                }
                if (eventAnnotation == null) {
                    continue;
                }
                if (event.getClass().equals(eventAnnotation.value())) {
                    try {
                        method.invoke(listener, event);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new IllegalStateException("Could not invoke method " + method, e);
                    }
                }
            }
        }
    }

}
