package io.mth.route.j;

import io.mth.route.RequestSignature;
import scala.Function1;
import scala.Option;

public interface Handler<A> extends Function1<RequestSignature, Option<A>> {}

