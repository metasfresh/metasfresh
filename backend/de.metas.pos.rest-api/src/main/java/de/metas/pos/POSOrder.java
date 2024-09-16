package de.metas.pos;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class POSOrder
{
	@NonNull ImmutableList<POSOrderLine> lines;
}
