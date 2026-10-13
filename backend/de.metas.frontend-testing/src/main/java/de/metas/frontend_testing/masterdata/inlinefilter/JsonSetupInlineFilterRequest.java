package de.metas.frontend_testing.masterdata.inlinefilter;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/**
 * Switches the inline filter of one column on or off and optionally sets the widget size the filter box gets in the given window.
 * <p>
 * {@code showInline} and {@code widgetSize}: {@code null} means unchanged. The response returns the previous values for information only.
 * {@code windowId} is always required, because the column's UI element in that window's main tab is always resolved (also when only {@code showInline} is set)
 * and a column without such an element is rejected.
 * <p>
 * This only writes AD data: the caller must reset the webapi caches ({@code GET /cache/reset}) afterwards.
 * <p>
 * Addition of this release line: there is no counterpart in the later (new_dawn-based) releases.
 */
@Value
@Builder
@Jacksonized
public class JsonSetupInlineFilterRequest
{
	/** {@code AD_Table.TableName} of the column's table, e.g. {@code "C_Invoice"}. */
	@NonNull String tableName;

	/** {@code AD_Column.ColumnName}, e.g. {@code "C_BPartner_ID"}. */
	@NonNull String columnName;

	/** New value of {@code AD_Column.IsShowFilterInline}. {@code null} leaves it unchanged. */
	@Nullable Boolean showInline;

	/** {@code AD_Window_ID} whose main tab holds the column's field; only used to find the {@code AD_UI_Element} for {@code widgetSize}. */
	int windowId;

	/** New {@code AD_UI_Element.WidgetSize}: a value of the WidgetSize reference list, e.g. {@code "S"}, {@code "M"}, {@code "L"}. {@code null} leaves it unchanged. */
	@Nullable String widgetSize;
}
