package fhv.ws22.se.skyward.domain.service;

import java.rmi.Remote;

public interface ServiceProviderService extends Remote {
    Object getService(Class<? extends Remote> clazz);
}
