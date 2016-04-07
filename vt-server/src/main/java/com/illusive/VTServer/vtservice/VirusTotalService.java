package com.illusive.VTServer.vtservice;

import java.util.List;

import com.illusive.VTCommon.json.ProcessInfo;
import com.illusive.VTCommon.json.ProcessStatus;

public interface VirusTotalService {
	List<ProcessStatus> examineProcesses(List<ProcessInfo> processes);
}
