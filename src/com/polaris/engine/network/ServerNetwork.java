package com.polaris.engine.network;

import java.io.IOException;
import java.net.Socket;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.polaris.engine.Application;

public class ServerNetwork extends Network
{

	private Application server;
	private String uuid;

	public ServerNetwork(Application serverApplication, Socket clientSocket) throws IOException
	{
		server = serverApplication;
		connect(clientSocket);
		uuid = clientSocket.getRemoteSocketAddress().toString();
	}

	public String getUUID()
	{
		return uuid;
	}

	public Application getServer()
	{
		return server;
	}

	public void setAESKey(byte[] encoded)
	{	
		try
		{
			SecretKeySpec secretKeySpec = new SecretKeySpec(getServer().decryptRSA(encoded), "AES");

			encrypt = Cipher.getInstance("AES");
			encrypt.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			decrypt = Cipher.getInstance("AES");
			decrypt.init(Cipher.DECRYPT_MODE, secretKeySpec);
		}
		catch(GeneralSecurityException e)
		{
			e.printStackTrace();
		}
	}

}
