package de.metas.inout;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class InOutLineQuery
{
	@NonNull InOutQuery headerQuery;
	int flatrateTermId;
}
