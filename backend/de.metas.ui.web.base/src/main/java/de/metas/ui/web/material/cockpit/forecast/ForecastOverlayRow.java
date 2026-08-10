package de.metas.ui.web.material.cockpit.forecast;

import com.google.common.collect.ImmutableList;
import de.metas.document.engine.DocStatus;
import de.metas.mforecast.impl.ForecastId;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.model.lookup.zoom_into.DocumentZoomIntoInfo;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.X_C_Order;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

/**
 * One row of the {@code Sprung zu Prognose} forecast overlay: a single {@link I_M_Forecast} document plus the
 * product-scoped forecast quantity ({@code Menge}) for the cockpit product. The {@code Name} column zooms into the
 * forecast document. Rendering mirrors
 * {@link de.metas.ui.web.material.cockpit.stockdetails.StockDetailsRow}.
 */
public final class ForecastOverlayRow implements IViewRow
{
	static final String FIELDNAME_Forecast = "forecast";

	@ViewColumn(fieldName = FIELDNAME_Forecast, captionKey = "Name", widgetType = DocumentFieldWidgetType.Lookup, seqNo = 10, zoomInto = true)
	private final LookupValue forecast;

	@ViewColumn(captionKey = "DocStatus", widgetType = DocumentFieldWidgetType.List, listReferenceId = X_C_Order.DOCSTATUS_AD_Reference_ID, seqNo = 20)
	private final DocStatus docStatus;

	@ViewColumn(captionKey = "DatePromised", widgetType = DocumentFieldWidgetType.LocalDate, seqNo = 30)
	private final LocalDate datePromised;

	@ViewColumn(captionKey = "AD_Org_ID", widgetType = DocumentFieldWidgetType.Lookup, seqNo = 40)
	private final LookupValue org;

	@ViewColumn(captionKey = "Qty", widgetType = DocumentFieldWidgetType.Quantity, seqNo = 50)
	private final Quantity qty;

	private final DocumentId id;
	private final ForecastId forecastId;
	private final ForecastOverlayRowLookups lookups;

	public static ForecastOverlayRow of(
			@NonNull final I_M_Forecast forecastRecord,
			@NonNull final Quantity qty,
			@NonNull final ForecastOverlayRowLookups lookups)
	{
		return new ForecastOverlayRow(forecastRecord, qty, lookups);
	}

	private ForecastOverlayRow(
			@NonNull final I_M_Forecast forecastRecord,
			@NonNull final Quantity qty,
			@NonNull final ForecastOverlayRowLookups lookups)
	{
		this.forecastId = ForecastId.ofRepoId(forecastRecord.getM_Forecast_ID());
		this.id = DocumentId.of(forecastId);
		this.lookups = lookups;

		this.forecast = lookups.lookupForecastById(forecastId);
		this.docStatus = DocStatus.ofCode(forecastRecord.getDocStatus());
		this.datePromised = TimeUtil.asLocalDate(forecastRecord.getDatePromised());
		this.org = lookups.lookupOrgById(OrgId.ofRepoId(forecastRecord.getAD_Org_ID()));
		this.qty = qty;
	}

	@Override
	public DocumentId getId()
	{
		return id;
	}

	@Override
	public boolean isProcessed()
	{
		return false;
	}

	@Nullable
	@Override
	public DocumentPath getDocumentPath()
	{
		return null;
	}

	@Override
	public Set<String> getFieldNames()
	{
		return ViewColumnHelper.extractFieldNames(this);
	}

	@Override
	public ViewRowFieldNameAndJsonValues getFieldNameAndJsonValues()
	{
		return ViewColumnHelper.extractJsonMap(this);
	}

	@Override
	public Collection<? extends IViewRow> getIncludedRows()
	{
		return ImmutableList.of();
	}

	public DocumentZoomIntoInfo getZoomIntoInfo(@NonNull final String fieldName)
	{
		if (FIELDNAME_Forecast.equals(fieldName))
		{
			return lookups.getForecastZoomInto(forecastId);
		}
		throw new AdempiereException("Field " + fieldName + " does not support zoom info");
	}
}
