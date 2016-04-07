package com.illusive.VTClient;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.illusive.VTCommon.json.ProcessInfo;

public class Main {
	public static void main(String[] args) {
		System.out.println("Retrieving running processes...");
		ProcessRetriever retriever = new ProcessRetriever();
		List<ProcessInfo> processes = retriever.getProcesses();
		
		
		ObjectMapper om = new ObjectMapper();
		om.enable(SerializationFeature.INDENT_OUTPUT);
		StringWriter sw = new StringWriter();
		
		try {
			om.writeValue(sw, processes);
			
			System.out.println("starting client");
			HttpClient client = HttpClients.createDefault();
			StringEntity requestEntity = new StringEntity(
					sw.toString(),
					ContentType.APPLICATION_JSON
					);
			HttpPost postMethod = new HttpPost("http://localhost:4567/examineProcesses");
			postMethod.setEntity(requestEntity);
		
			System.out.println("executing post");
			HttpResponse resp = client.execute(postMethod);
			System.out.println("retrieving data");
			String json = EntityUtils.toString(resp.getEntity());
			
			ReportGenerator rg = new ReportGenerator();
			rg.generateReport(json);
		} catch (IOException e) {
			System.out.println(new StringBuilder("Error retrieving report: ").append(e.getMessage()).toString());
		} 
	}
}
	
	
