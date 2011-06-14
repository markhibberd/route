package io.mth.route.j;

public interface Handlers {
    <A> Handler<A> is(Method method, String path, A a);
    <A> Handler<A> is(Method method, String path, BaseCase<A> a);
    <A> Handler<A> id(Method method, String path, F<String, A> a);
}
