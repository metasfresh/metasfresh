package de.metas.inventory.mobileui.rest_api.json;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.warehouse.qrcode.resolver.LocatorNotResolvedReason;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverResult;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonResolveLocatorResponse
{
	@Nullable Integer locatorId;
	@Nullable List<String> notFoundReasons;

	public static JsonResolveLocatorResponse of(final LocatorScannedCodeResolverResult result)
	{
		if (result.isFound())
		{
			return builder()
					.locatorId(result.getLocatorId().getRepoId())
					.build();
		}
		else
		{
			return builder()
					.notFoundReasons(result.getNotResolvedReasons()
							.stream()
							.map(LocatorNotResolvedReason::getSummary)
							.collect(ImmutableList.toImmutableList()))
					.build();
		}
	}
}
