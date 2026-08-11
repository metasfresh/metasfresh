package de.metas.ui.web.material.cockpit.forecast;

import com.google.common.collect.ImmutableList;
import de.metas.material.cockpit.QtyDemandQtySupply;
import de.metas.material.cockpit.QtyDemandQtySupplyId;
import de.metas.material.cockpit.QtyDemandSupplyRepository;
import de.metas.mforecast.IForecastDAO;
import de.metas.mforecast.impl.ForecastId;
import de.metas.mforecast.impl.ForecastQuery;
import de.metas.quantity.Quantity;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

import static de.metas.ui.web.view.SqlViewFactory.MSG_NO_RELATED_DOCS_FOUND;

/**
 * Launched-view factory for the {@code Sprung zu Prognose} overlay. Seeded (via
 * {@link de.metas.ui.web.material.cockpit.forecast.process.WEBUI_QtyDemandQtySupply_Forecast_Launcher}) with the
 * selected Material-Cockpit-v2 row, it rebuilds the forecast {@link ForecastQuery} used by today's jump and asks the
 * DAO for the product-scoped {@code Menge} per forecast document. Mirrors
 * {@link de.metas.ui.web.order.sales.purchasePlanning.view.SalesOrder2PurchaseViewFactory}.
 */
@ViewFactory(windowId = ForecastOverlayViewFactory.WINDOW_ID_STRING)
public class ForecastOverlayViewFactory implements IViewFactory
{
	public static final String WINDOW_ID_STRING = "forecastQtyOverlay";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	private final QtyDemandSupplyRepository demandSupplyRepository;
	private final ForecastOverlayRowLookups lookups;
	private final IForecastDAO forecastDAO = Services.get(IForecastDAO.class);

	public ForecastOverlayViewFactory(
			@NonNull final QtyDemandSupplyRepository demandSupplyRepository,
			@NonNull final ForecastOverlayRowLookups lookups)
	{
		this.demandSupplyRepository = demandSupplyRepository;
		this.lookups = lookups;
	}

	@Override
	public IView createView(@NonNull final CreateViewRequest request)
	{
		final QtyDemandQtySupplyId cockpitRowId = QtyDemandQtySupplyId.ofRepoId(request.getSingleFilterOnlyId());
		final QtyDemandQtySupply cockpitRow = demandSupplyRepository.getById(cockpitRowId);

		final ForecastQuery forecastQuery = ForecastQuery.builder()
				.warehouseId(cockpitRow.getWarehouseId())
				.orgId(cockpitRow.getOrgId())
				.productId(cockpitRow.getProductId())
				.attributesKey(cockpitRow.getAttributesKey())
				.onlyNonZeroQty(true)
				.build();

		// Iteration order is guaranteed stable by the DAO (DatePromised, then M_Forecast_ID), so the rows are rendered
		// in the order they arrive.
		final Map<ForecastId, Quantity> qtyByForecastId = forecastDAO.sumQtyByForecastId(forecastQuery);

		// Nothing forecast for this product: keep the pre-existing behaviour of the jump, which opened no window at all
		// when no forecast matched, rather than presenting an empty overlay. Same message and layer as the equivalent
		// no-results case in SqlViewFactory.
		if (qtyByForecastId.isEmpty())
		{
			throw new AdempiereException(MSG_NO_RELATED_DOCS_FOUND);
		}

		final List<ForecastOverlayRow> rows = qtyByForecastId.entrySet()
				.stream()
				.map(entry -> ForecastOverlayRow.of(forecastDAO.getById(entry.getKey()), entry.getValue(), lookups))
				.collect(ImmutableList.toImmutableList());

		return new ForecastOverlayView(request.getViewId(), rows);
	}

	@Override
	public ViewLayout getViewLayout(
			@NonNull final WindowId windowId,
			@NonNull final JSONViewDataType viewDataType,
			@Nullable final ViewProfileId profileId)
	{
		Check.errorUnless(WINDOW_ID.equals(windowId),
				"The parameter windowId needs to be {}, but is {} instead", WINDOW_ID, windowId);

		return ViewLayout.builder()
				.setWindowId(windowId)
				.addElementsFromViewRowClass(ForecastOverlayRow.class, viewDataType)
				.build();
	}
}
