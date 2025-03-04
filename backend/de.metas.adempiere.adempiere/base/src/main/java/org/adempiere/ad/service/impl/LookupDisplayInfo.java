package org.adempiere.ad.service.impl;

import com.google.common.collect.ImmutableList;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.element.api.AdWindowId;

import java.util.List;

@Value
public class LookupDisplayInfo
{
	@NonNull ImmutableList<LookupDisplayColumn> lookupDisplayColumns;
	AdWindowId zoomWindow;
	AdWindowId zoomWindowPO;
	boolean translated;

	@Builder
	private LookupDisplayInfo(
			@NonNull final List<LookupDisplayColumn> lookupDisplayColumns,
			final AdWindowId zoomWindow,
			final AdWindowId zoomWindowPO,
			final boolean translated)
	{
		Check.assumeNotEmpty(lookupDisplayColumns, "lookupDisplayColumns not empty");
		this.lookupDisplayColumns = ImmutableList.copyOf(lookupDisplayColumns);

		this.zoomWindow = zoomWindow;
		this.zoomWindowPO = zoomWindowPO;
		this.translated = translated;
	}
}
