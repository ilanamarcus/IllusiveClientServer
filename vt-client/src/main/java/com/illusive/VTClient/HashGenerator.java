package com.illusive.VTClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {
	private byte[] getDigest(FileInputStream file) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] buffer = new byte[1024];
			
			int numProcessed = 0;

			do {
				numProcessed = file.read(buffer);
				if (numProcessed > 0) {
					digest.update(buffer, 0, numProcessed);
				}
			} while (numProcessed != -1);
			
			return digest.digest();
		} catch (NoSuchAlgorithmException e) {
			System.out.println(new StringBuilder("Error: ").append(e.getMessage()).toString());
		} catch (IOException e) {
			System.out.println(new StringBuilder("Error: ").append(e.getMessage().toString()));
		}
		return null;
	}
	
	private String bytesToHexString(byte[] digest) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<digest.length; i++) {
			sb.append(String.format("%02x", digest[i]));
		}
		return sb.toString();
	}
	
	public String hashFile(FileInputStream file) {
		return bytesToHexString(getDigest(file));
	}
}
