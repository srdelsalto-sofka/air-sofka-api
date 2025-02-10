package ec.com.airsofka.generics.interfaces;

import ec.com.airsofka.generics.utils.Request;
import org.reactivestreams.Publisher;

public interface IUseEmptyCase<R> {
    Publisher<R> execute();
}
