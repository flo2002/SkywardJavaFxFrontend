package fhv.ws22.se.skyward.domain.service;

import java.rmi.Remote;

public interface ServiceProviderService extends Remote {
    void registerService(String serviceName, Object service);
    Object getService(String serviceName);
}
