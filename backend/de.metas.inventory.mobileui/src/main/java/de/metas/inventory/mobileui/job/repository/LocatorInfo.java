package de.metas.inventory.mobileui.job.repository;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;

@Value
@Builder
class LocatorInfo
{
	@NonNull LocatorId locatorId;
	@NonNull String locatorName;
}
