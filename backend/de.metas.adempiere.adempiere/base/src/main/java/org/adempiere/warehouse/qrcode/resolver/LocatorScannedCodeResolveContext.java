package org.adempiere.warehouse.qrcode.resolver;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.qrcode.LocatorQRCode;

@Value
@Builder
public class LocatorScannedCodeResolveContext
{
	public static final LocatorScannedCodeResolveContext NO_CONTEXT = builder().build();

	@NonNull @Singular ImmutableSet<LocatorId> eligibleLocatorIds;

	public boolean isMatching(@NonNull LocatorId locatorId)
	{
		return (eligibleLocatorIds.isEmpty() || eligibleLocatorIds.contains(locatorId));
	}

	public boolean isMatching(@NonNull LocatorQRCode locatorQRCode)
	{
		return isMatching(locatorQRCode.getLocatorId());
	}
}
