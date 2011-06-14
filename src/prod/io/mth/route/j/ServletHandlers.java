package io.mth.route.j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ServletHandlers {
    <A> Handler<A> is(Method method, String path, F<HttpServletRequest, A> in, F2<A, HttpServletResponse, Void> out);
    <A> Handler<A> is(Method method, String path, BaseCase<A> a);
    <A> Handler<A> id(Method method, String path, F<String, A> a);
}
