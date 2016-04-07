package com.illusive.VTClient;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.trimou.Mustache;
import org.trimou.engine.MustacheEngineBuilder;
import org.trimou.gson.resolver.JsonElementResolver;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class ReportGenerator {
	private Mustache mustache;
	
	public ReportGenerator() {

		String reportTemplateString = "";
		try {
			reportTemplateString = new String(Files.readAllBytes(Paths.get("./outScript/reportTemplate.html")));

			mustache = MustacheEngineBuilder
				.newBuilder()
				.addResolver(new JsonElementResolver())
				.build()
				.compileMustache("reportTemplate", reportTemplateString);
		} catch (IOException e) {
			System.out.println("report template could not be read");
		}
	}
	
	public void generateReport(String json) {
		System.out.println("Generating report...");
		String reportContent = getReportContent(json);
		writeReport(reportContent);
	}
	
	private String getReportContent(String json) {
		System.out.println("Parsing json content");
		JsonElement jsonElement = new JsonParser().parse(json);
		System.out.println(jsonElement.toString());
		if (mustache != null) {
			String reportString = mustache.render(jsonElement);
			return reportString;
		} else {
			System.out.println("report template is null");
			return "";
		}
		
	}
	
	private void writeReport(String reportContent) {
		System.out.println("Writing report to file");
		File reportFile = new File("./report.html");
		
		try {
			if (!reportFile.exists()) {
				reportFile.createNewFile();
			}
				
			FileWriter writer = new FileWriter(reportFile.getAbsoluteFile());
			BufferedWriter buffWriter = new BufferedWriter(writer);
			buffWriter.write(reportContent);
			buffWriter.close();
			System.out.println(new StringBuilder("Report written to: ").append(reportFile.getAbsolutePath()).toString());
		} catch (IOException e) {
			System.out.println(new StringBuilder("Can't create report file: ").append(e.getMessage()).toString());
		}
		
	}
}
