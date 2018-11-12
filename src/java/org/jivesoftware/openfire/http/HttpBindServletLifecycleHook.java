package org.jivesoftware.openfire.http;

import javax.servlet.AsyncContext;

/**
 * A simple interface for hooking into lifecycle events of the {@link HttpBindServlet}
 */
public interface HttpBindServletLifecycleHook {

    /**
     * Called when an {@link HttpSession} is created for a given http-bind web request
     *
     * @param context The {@link AsyncContext} containing the request and response
     * @param session The newly created {@link HttpSession}
     */
    void postSessionCreated(AsyncContext context, HttpSession session);
}
