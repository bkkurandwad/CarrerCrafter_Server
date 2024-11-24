package com.majorproject.CareerforIT.Context;

public class RequestContext {

    private static final ThreadLocal<String> jwtToken = new ThreadLocal<>();

    // Set the JWT token for the current thread
    public static void setJwtToken(String token) {
        jwtToken.set(token);
    }

    // Get the JWT token for the current thread
    public static String getJwtToken() {
        return jwtToken.get();
    }

    // Clear the JWT token for the current thread
    public static void clear() {
        jwtToken.remove();
    }
}
