package io.mth.route.j;

import java.util.List;

public interface Routes {
    <A> Route<A> build(List<Handler<A>> handlers, A basecase);
    <A> Route<A> build(List<Handler<A>> handlers, BaseCase<A> basecase);
}
