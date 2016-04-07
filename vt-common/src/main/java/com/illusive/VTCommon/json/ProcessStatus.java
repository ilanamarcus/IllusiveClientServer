package com.illusive.VTCommon.json;

import com.kanishka.virustotal.dto.FileScanReport;

import lombok.Data;

@Data
public class ProcessStatus {
	private ProcessInfo info;
	private FileScanReport report;
}
