package org.adempiere.ad.service;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ADSystemInfo
{
	String dbVersion;
	String lastBuildInfo;
	boolean failOnMissingModelValidator;
}
