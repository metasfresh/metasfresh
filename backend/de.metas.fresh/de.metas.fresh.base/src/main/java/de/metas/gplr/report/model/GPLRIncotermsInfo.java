package de.metas.gplr.report.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class GPLRIncotermsInfo
{
	@NonNull String code;
	@Nullable String location;
}
