package com.illusive.VTServer.vtservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.illusive.VTCommon.json.ProcessInfo;
import com.illusive.VTCommon.json.ProcessStatus;
import com.illusive.VTServer.DImodules.Test;
import com.kanishka.virustotal.dto.FileScanReport;
import com.kanishka.virustotal.exception.QuotaExceededException;
import com.kanishka.virustotal.exception.UnauthorizedAccessException;
import com.kanishka.virustotalv2.VirustotalPublicV2;

public class VirusTotalServiceDefault implements VirusTotalService {
	private Test t;
	private List<ProcessStatus> statuses;
	private VirustotalPublicV2 vtClient;
	
	@Inject
	public VirusTotalServiceDefault(VirustotalPublicV2 vtClient) {
		this.vtClient = vtClient;
		statuses = new ArrayList<ProcessStatus>();
	}

	@Override
	public List<ProcessStatus> examineProcesses(List<ProcessInfo> processes) {
		processes.forEach(process -> statuses.add(getProcessStatus(process)));
		return statuses;
	}
	
	private ProcessStatus getProcessStatus(ProcessInfo info) {
		ProcessStatus status = new ProcessStatus();
		status.setInfo(info);
		try {
			FileScanReport report = vtClient.getScanReport(info.getHashSHA256());
			status.setReport(report);
		} catch (IOException | UnauthorizedAccessException | QuotaExceededException e) {
			System.out.println(e.getMessage());
		} 
		return status;
	}

}
