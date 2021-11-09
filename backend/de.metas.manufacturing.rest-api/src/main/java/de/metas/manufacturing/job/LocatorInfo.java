package de.metas.manufacturing.job;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;

@Value
@Builder
public class LocatorInfo
{
	@NonNull LocatorId id;
	@NonNull String caption;
}
