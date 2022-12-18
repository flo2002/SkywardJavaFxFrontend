package fhv.ws22.se.skyward.domain.service;

import com.google.inject.Provider;

import java.rmi.Naming;

public class SessionServiceProvider implements Provider<SessionService> {
    @Override
    public SessionService get() {
        try {
            return (SessionService) Naming.lookup("rmi://localhost:1099/SkywardDomainService");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
