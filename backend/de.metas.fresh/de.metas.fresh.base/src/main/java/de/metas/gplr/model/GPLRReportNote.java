package de.metas.gplr.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class GPLRReportNote
{
	@NonNull String sourceDocument;
	@Nullable String text;
}
