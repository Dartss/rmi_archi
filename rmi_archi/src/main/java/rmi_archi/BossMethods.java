package rmi_archi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

public interface BossMethods extends Remote {
	public void registerGenerator(Generator generator) throws RemoteException;
	
	public void registerDBManager(Pusher manager) throws RemoteException;
	
	public void isManagerReady(boolean isReady) throws RemoteException;
	
	public void onGenerationDone(LinkedList<Person> persons) throws RemoteException;
}
