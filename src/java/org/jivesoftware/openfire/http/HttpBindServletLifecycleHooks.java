package org.jivesoftware.openfire.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Allows plugins to register {@link HttpBindServlet} lifecycle hooks.
 */
public class HttpBindServletLifecycleHooks {
    private static final Logger Log = LoggerFactory.getLogger(HttpBindServletLifecycleHooks.class);
    private static Map<String, HttpBindServletLifecycleHook> hooks = new ConcurrentHashMap<>();

    private HttpBindServletLifecycleHooks() {
        //Static utility class
    }

    /**
     * Registers a {@link HttpBindServletLifecycleHook} to be run on {@link HttpBindServlet} lifecycle events.  Will replace an
     * existing hook if one exists with the same name
     *
     * @param name The name of the hook (used for unregistering)
     * @param hook The {@link HttpBindServletLifecycleHook}
     */
    public static void registerHook(String name, HttpBindServletLifecycleHook hook) {
        if (name == null) {
            throw new NullPointerException("hook name cannot be null");
        }
        hooks.put(name, hook);
    }

    /**
     * Called when the {@link HttpSession} has been created for a given web request
     *
     * @param context The {@link AsyncContext}
     * @param session The newly created {@link HttpSession}
     */
    public static void postSessionCreated(AsyncContext context, HttpSession session) {
        Log.debug("HttpSession created - running hooks");
        Set<Map.Entry<String, HttpBindServletLifecycleHook>> hooks = HttpBindServletLifecycleHooks.hooks.entrySet();
        for (Map.Entry<String, HttpBindServletLifecycleHook> hook : hooks) {
            Log.debug("Running postSessionCreated hook: " + hook.getKey());
            hook.getValue().postSessionCreated(context, session);
        }
    }

    /**
     * Unregisters the named {@link HttpBindServletLifecycleHook}
     *
     * @param name The name of the hook to unregister
     */
    public static void unregisterHook(String name) {
        hooks.remove(name);
    }
}
