package de.metas.ui.web.material.cockpit.forecast;

import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.IViewZoomIntoFieldSupport;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.model.lookup.zoom_into.DocumentZoomIntoInfo;
import lombok.NonNull;

import java.util.List;

/**
 * In-memory custom view backing the {@code Sprung zu Prognose} overlay. It lists the forecast documents matching the
 * cockpit row (product / warehouse / org / ASI) together with the product-scoped {@code Menge}, and supports zooming
 * from the {@code Name} column into the forecast document via {@link IViewZoomIntoFieldSupport}.
 */
public class ForecastOverlayView
		extends AbstractCustomView<ForecastOverlayRow>
		implements IViewZoomIntoFieldSupport
{
	public ForecastOverlayView(
			@NonNull final ViewId viewId,
			@NonNull final List<ForecastOverlayRow> rows)
	{
		super(viewId,
				TranslatableStrings.empty(),
				ForecastOverlayRowsData.of(rows),
				NullDocumentFilterDescriptorsProvider.instance);
	}

	/**
	 * @return {@code null}: each row is assembled from a forecast header plus a computed quantity, so it is not backed
	 * by a single AD table.
	 */
	@Override
	public String getTableNameOrNull(final DocumentId documentId)
	{
		return null;
	}

	@Override
	public DocumentZoomIntoInfo getZoomIntoInfo(@NonNull final DocumentId rowId, @NonNull final String fieldName)
	{
		return getById(rowId).getZoomIntoInfo(fieldName);
	}
}
