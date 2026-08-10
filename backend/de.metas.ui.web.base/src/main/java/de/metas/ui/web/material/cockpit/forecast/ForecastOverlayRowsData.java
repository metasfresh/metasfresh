package de.metas.ui.web.material.cockpit.forecast;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import java.util.List;
import java.util.Map;

/**
 * Eager, in-memory {@link IRowsData} for the forecast overlay: the row set is computed once at view-creation time
 * (there are only ever a handful of forecast documents per cockpit product), so there is nothing to invalidate.
 * Mirrors {@link de.metas.ui.web.material.cockpit.stockdetails.StockDetailsRowsData}.
 */
public class ForecastOverlayRowsData implements IRowsData<ForecastOverlayRow>
{
	public static ForecastOverlayRowsData of(@NonNull final List<ForecastOverlayRow> rows)
	{
		return new ForecastOverlayRowsData(rows);
	}

	private final Map<DocumentId, ForecastOverlayRow> documentId2Row;

	private ForecastOverlayRowsData(@NonNull final List<ForecastOverlayRow> rows)
	{
		this.documentId2Row = Maps.uniqueIndex(rows, ForecastOverlayRow::getId);
	}

	@Override
	public Map<DocumentId, ForecastOverlayRow> getDocumentId2TopLevelRows()
	{
		return ImmutableMap.copyOf(documentId2Row);
	}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs)
	{
		return DocumentIdsSelection.EMPTY;
	}

	@Override
	public void invalidateAll()
	{
	}
}
