package rmi_getter;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ManagerMethods extends Remote {
	public void search(String name) throws RemoteException;
}
