package fhv.ws22.se.skyward.domain.service;

import java.rmi.Remote;

public interface SessionService extends Remote {
    ServiceProviderService getServiceProvider();
}
