package fhv.ws22.se.skyward.domain;

import com.google.inject.Provider;

import java.rmi.Naming;

public class ServiceGuiceProvider implements Provider<ServiceProviderService> {
    @Override
    public ServiceProviderService get() {
        try {
            return (ServiceProviderService) Naming.lookup("rmi://localhost:1099/SkywardDomainService");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
