package fhv.ws22.se.skyward.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceProviderService extends Remote {
    void registerService(String serviceName, Object service) throws RemoteException;
    Object getService(String serviceName) throws RemoteException;
}
