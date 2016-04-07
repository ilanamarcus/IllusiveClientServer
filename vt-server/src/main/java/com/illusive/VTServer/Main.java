package com.illusive.VTServer;

import static spark.Spark.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.illusive.VTCommon.json.ProcessInfo;
import com.illusive.VTCommon.json.ProcessStatus;
import com.illusive.VTCommon.json.VirusTotalData;
import com.illusive.VTServer.DImodules.MainModule;
import com.illusive.VTServer.vtservice.VirusTotalService;
import com.illusive.VTServer.vtservice.VirusTotalServiceDefault;
import com.kanishka.virustotalv2.VirusTotalConfig;

import spark.Request;
import spark.Response;

public class Main {

	public static void main(String[] args) {
		//set up DI injection
		Injector injector = Guice.createInjector(new MainModule());
		VirusTotalConfig.getConfigInstance().setVirusTotalAPIKey("db9efeb51c9e9364fb29b17cb4f5474177d0bffc5c27ff53958e1ba01ed4c76e");
		VirusTotalService vts = injector.getInstance(VirusTotalServiceDefault.class);
		post("/examineProcesses", "application/json", (req, res)-> processReq(req, res, vts));
	}
	
	private static String processReq(Request req, Response res, VirusTotalService t) {
		ObjectMapper om = new ObjectMapper();
		try {
			List<ProcessInfo> processes = om.readValue(req.body(), new TypeReference<List<ProcessInfo>>(){});
			List<ProcessStatus> statuses = t.examineProcesses(processes.subList(0, 4));
			VirusTotalData data = new VirusTotalData();
			data.setStatuses(statuses);
			
			om.enable(SerializationFeature.INDENT_OUTPUT);
			StringWriter sw = new StringWriter();
			om.writeValue(sw, data);
			System.out.println(sw.toString());
			res.status(200);
			res.type("application/json");
			return sw.toString();
			
		} catch (IOException e) {
			//log json parse exception
			res.status(400);
			res.body("bad json" + e.getMessage());
			return "bad json" + e.getMessage();
		}
		
	}

}
