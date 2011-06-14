package io.mth.route.j;

import io.mth.route.RequestSignature;

public interface Route<A> {
    A route(RequestSignature request);
}
