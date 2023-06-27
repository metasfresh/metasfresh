package de.metas.gplr.model;

import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class GPLRReportNote
{
	@Nullable String sourceDocument;
	@Nullable String text;
}
