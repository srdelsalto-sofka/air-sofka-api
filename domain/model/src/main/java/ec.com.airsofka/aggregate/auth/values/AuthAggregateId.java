package ec.com.airsofka.aggregate.auth.values;


import ec.com.airsofka.generics.utils.Identity;

public class AuthAggregateId extends Identity {

    public AuthAggregateId() {
        super();
    }

    public AuthAggregateId(String id) {
        super(id);
    }

    public static AuthAggregateId of(String id) {
        return new AuthAggregateId(id);
    }

}