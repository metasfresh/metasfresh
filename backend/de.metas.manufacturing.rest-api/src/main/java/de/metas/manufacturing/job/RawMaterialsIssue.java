package de.metas.manufacturing.job;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class RawMaterialsIssue
{
	@NonNull ImmutableList<RawMaterialsIssueLine> lines;
}
