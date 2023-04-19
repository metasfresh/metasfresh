package org.adempiere.ad.window.api;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;

@Value
@Builder
public class WindowCopyResult
{
	@NonNull AdWindowId sourceWindowId;
	@NonNull AdWindowId targetWindowId;
	@NonNull String targetEntityType;

	@NonNull @Singular ImmutableList<TabCopyResult> tabs;

	@Value
	@Builder
	public static class TabCopyResult
	{
		@NonNull AdTabId sourceTabId;
		@NonNull AdTabId targetTabId;
	}
}
