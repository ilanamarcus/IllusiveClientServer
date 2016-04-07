package com.illusive.VTCommon.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ProcessInfo {
	@JsonProperty("localPid")
	private int localPid;
	
	@JsonProperty("hashSHA256")
	private String hashSHA256;
}
