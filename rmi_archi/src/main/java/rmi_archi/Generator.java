package rmi_archi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Generator extends Remote {
	public void generate(int amount) throws RemoteException;
}
