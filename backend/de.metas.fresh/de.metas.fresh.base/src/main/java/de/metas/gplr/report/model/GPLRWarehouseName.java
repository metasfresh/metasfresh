package de.metas.gplr.report.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class GPLRWarehouseName
{
	@NonNull String code;
	@NonNull String name;
	@Nullable String externalId;
}
