package de.metas.frontend_testing.masterdata.inlinefilter;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/** The values before the request, so that a test can restore them. One response per request entry, in request order. */
@Value
@Builder
@Jacksonized
public class JsonSetupInlineFilterResponse
{
	/** {@code AD_Column.IsShowFilterInline} before the request. */
	boolean previousShowInline;

	/** {@code AD_UI_Element.WidgetSize} before the request; {@code null} if it was not set. */
	@Nullable String previousWidgetSize;
}
