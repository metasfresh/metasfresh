package de.metas.frontend_testing.masterdata.inlinefilter;

import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.element.api.AdFieldId;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdUIElementId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;

/**
 * Sets {@code AD_Column.IsShowFilterInline} and the {@code AD_UI_Element.WidgetSize} of the column's field in the window's main tab.
 * <p>
 * The element is resolved strictly: window, then main tab, then the field of the column in that tab, then the UI element of that field.
 * Nothing is created: a missing field or element, or more than one element, is an error.
 * Everything is checked before the first write, so a rejected request changes nothing.
 */
public class SetupInlineFilterCommand
{
	@NonNull private final InlineFilterRepository repository;
	@NonNull private final AdMetadataCacheInvalidator cacheInvalidator;
	@NonNull private final JsonSetupInlineFilterRequest request;

	@Builder
	private SetupInlineFilterCommand(
			@NonNull final InlineFilterRepository repository,
			@NonNull final AdMetadataCacheInvalidator cacheInvalidator,
			@NonNull final JsonSetupInlineFilterRequest request)
	{
		this.repository = repository;
		this.cacheInvalidator = cacheInvalidator;
		this.request = request;
	}

	public JsonSetupInlineFilterResponse execute()
	{
		final String widgetSize = request.getWidgetSize();
		if (widgetSize != null && !repository.isValidWidgetSize(widgetSize))
		{
			throw new AdempiereException("Invalid widgetSize `" + widgetSize + "`; it is not a value of the WidgetSize reference list");
		}

		final AdColumnId columnId = repository.getColumnId(request.getTableName(), request.getColumnName())
				.orElseThrow(() -> new AdempiereException("Unknown column " + request.getTableName() + "." + request.getColumnName()));
		final AdUIElementId elementId = resolveUIElementId(columnId);

		final JsonSetupInlineFilterResponse previousValues = JsonSetupInlineFilterResponse.builder()
				.previousShowInline(repository.isShowFilterInline(columnId))
				.previousWidgetSize(repository.getWidgetSize(elementId))
				.build();

		if (request.getShowInline() != null)
		{
			repository.setShowFilterInline(columnId, request.getShowInline());
		}
		if (widgetSize != null)
		{
			repository.setWidgetSize(elementId, widgetSize);
		}

		cacheInvalidator.invalidateWindowLayouts();

		return previousValues;
	}

	private AdUIElementId resolveUIElementId(final AdColumnId columnId)
	{
		final String column = request.getTableName() + "." + request.getColumnName();
		final String window = "window " + request.getWindowId();

		final AdTabId mainTabId = repository.getMainTabId(AdWindowId.ofRepoId(request.getWindowId()))
				.orElseThrow(() -> new AdempiereException("Unknown " + window + " or it has no main tab"));
		final AdFieldId fieldId = repository.getFieldId(mainTabId, columnId)
				.orElseThrow(() -> new AdempiereException("Column " + column + " has no field in the main tab of " + window));
		final List<AdUIElementId> elementIds = repository.getUIElementIds(mainTabId, fieldId);
		if (elementIds.isEmpty())
		{
			throw new AdempiereException("The field of column " + column + " has no UI element in the main tab of " + window + "; not creating one");
		}
		if (elementIds.size() > 1)
		{
			throw new AdempiereException("The field of column " + column + " has " + elementIds.size() + " UI elements in the main tab of " + window + "; refusing to guess");
		}
		return elementIds.get(0);
	}
}
