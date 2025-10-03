package org.adempiere.warehouse.qrcode.resolver;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;

@Value
@Builder
public class LocatorScannedCodeResolveContext
{
	@NonNull @Builder.Default ImmutableSet<LocatorId> eligibleLocatorIds = ImmutableSet.of();

	public boolean isMatching(@NonNull LocatorId locatorId)
	{
		return (eligibleLocatorIds.isEmpty() || eligibleLocatorIds.contains(locatorId));
	}
}
