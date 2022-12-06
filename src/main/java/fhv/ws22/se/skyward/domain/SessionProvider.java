package fhv.ws22.se.skyward.domain;

import com.google.inject.Provider;

import java.rmi.Naming;

public class SessionProvider implements Provider<SessionService> {
    @Override
    public SessionService get() {
        SessionService session = null;
        try {
            session = (SessionService) Naming.lookup("rmi://localhost/SkywardDomainSession");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return session;
    }
}
