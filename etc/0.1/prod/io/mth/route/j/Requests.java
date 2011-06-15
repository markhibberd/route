package io.mth.route.j;

import io.mth.route.Path;
import io.mth.route.RequestSignature;

public interface Requests {
    RequestSignature request(String method, Path path);
    RequestSignature request(Method method, Path path);
}
