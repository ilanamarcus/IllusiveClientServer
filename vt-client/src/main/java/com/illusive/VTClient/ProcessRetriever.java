package com.illusive.VTClient;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.illusive.VTCommon.json.ProcessInfo;

public class ProcessRetriever {
	private HashGenerator hasher;
	
	public ProcessRetriever() {
		hasher = new HashGenerator();
	}
	
	public List<ProcessInfo> getProcesses() {
		List<ProcessInfo> processes = new ArrayList<ProcessInfo>();
		try {
			Process p = new ProcessBuilder("./outScript/processes.sh").start();
			InputStream is = p.getInputStream();
		    BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    String line = null;
		    while ((line = br.readLine()) != null) {
		      String[] parts = line.split(" ");
		      ProcessInfo pi = new ProcessInfo();
		      pi.setLocalPid(Integer.parseInt(parts[0]));
		      
		      try {
			      FileInputStream f = new FileInputStream(parts[1]);
			      String hash = hasher.hashFile(f);
			      pi.setHashSHA256(hash);
			      processes.add(pi);
		      } catch (Exception e) {
		    	  System.out.println("problem with process " + parts[1] + ": " + e.getMessage());
		      }
		    }
		} catch (IOException e1) {
			System.out.println("couldn't get running processes");
			System.out.println(e1.getMessage());
		}
		return processes;		
	}
}
