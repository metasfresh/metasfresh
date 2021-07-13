package de.metas.ui.web.picking.husToPick;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IPackagingDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.Packageable;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.handlingunits.HUEditorViewBuilder;
import de.metas.ui.web.handlingunits.HUEditorViewFactoryTemplate;
import de.metas.ui.web.handlingunits.SqlHUEditorViewRepository.SqlHUEditorViewRepositoryBuilder;
import de.metas.ui.web.order.sales.hu.reservation.HUReservationDocumentFilterService;
import de.metas.ui.web.picking.packageable.filters.ProductBarcodeFilterData;
import de.metas.ui.web.picking.pickingslot.PickingSlotRowId;
import de.metas.ui.web.picking.pickingslot.PickingSlotView;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper.ClassViewColumnOverrides;
import de.metas.ui.web.view.json.JSONFilterViewRequest;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.MediaType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@ViewFactory(windowId = HUsToPickViewFactory.WINDOW_ID_STRING, viewTypes = { JSONViewDataType.grid, JSONViewDataType.includedView })
public class HUsToPickViewFactory extends HUEditorViewFactoryTemplate
{
	static final String WINDOW_ID_STRING = "husToPick";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	private final HUReservationDocumentFilterService huReservationDocumentFilterService;
	private final IPackagingDAO packagingDAO = Services.get(IPackagingDAO.class);
	private final IShipmentScheduleBL shipmentScheduleBL;

	public HUsToPickViewFactory(
			@NonNull final HUReservationDocumentFilterService huReservationDocumentFilterService,
			@NonNull final IShipmentScheduleBL shipmentScheduleBL)
	{
		super(ImmutableList.of());

		this.huReservationDocumentFilterService = huReservationDocumentFilterService;
		this.shipmentScheduleBL = shipmentScheduleBL;
	}

	public CreateViewRequest createViewRequest(
			@NonNull final ViewId pickingSlotViewId,
			@NonNull final PickingSlotRowId pickingSlotRowId,
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@Nullable final ProductBarcodeFilterData barcodeFilterData)
	{
		final ShipmentAllocationBestBeforePolicy bestBeforePolicy = shipmentScheduleBL.getBestBeforePolicy(shipmentScheduleId);

		final ArrayList<DocumentFilter> stickyFilters = new ArrayList<>();

		final Packageable packageable = packagingDAO.getByShipmentScheduleId(shipmentScheduleId);
		stickyFilters.add(huReservationDocumentFilterService.createDocumentFilterIgnoreAttributes(packageable));

		if (barcodeFilterData != null && barcodeFilterData.getHuId() != null)
		{
			stickyFilters.add(DocumentFilter.equalsFilter(I_M_HU.COLUMNNAME_M_HU_ID, barcodeFilterData.getHuId()));
		}

		return CreateViewRequest.builder(WINDOW_ID, JSONViewDataType.includedView)
				.setParentViewId(pickingSlotViewId)
				.setParentRowId(pickingSlotRowId.toDocumentId())
				.setParameter(HUsToPickViewFilters.PARAM_CurrentShipmentScheduleId, shipmentScheduleId)
				.setParameter(HUsToPickViewFilters.PARAM_BestBeforePolicy, bestBeforePolicy)
				//
				.setStickyFilters(stickyFilters)
				.setFilters(DocumentFilterList.of(HUsToPickViewFilters.createHUIdsFilter(true))) // https://github.com/metasfresh/metasfresh-webui-api/issues/1067
				//
				.build();
	}

	@Override
	protected DocumentFilterDescriptorsProvider createFilterDescriptorsProvider()
	{
		return ImmutableDocumentFilterDescriptorsProvider.builder()
				.addDescriptors(HUsToPickViewFilters.createFilterDescriptors())
				.addDescriptors(super.createFilterDescriptorsProvider())
				.build();
	}

	@Override
	protected List<SqlDocumentFilterConverter> createFilterConverters()
	{
		return ImmutableList.<SqlDocumentFilterConverter>builder()
				.addAll(super.createFilterConverters())
				.addAll(HUsToPickViewFilters.createFilterConverters())
				.build();
	}

	@Override
	protected void customizeViewLayout(final ViewLayout.Builder viewLayoutBuilder, final JSONViewDataType viewDataType)
	{
		viewLayoutBuilder
				.clearElements()
				.addElementsFromViewRowClassAndFieldNames(HUEditorRow.class,
														  viewDataType,
														  ClassViewColumnOverrides.builder(HUEditorRow.FIELDNAME_HUCode).restrictToMediaType(MediaType.SCREEN).build(),
														  ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_Product),
														  ClassViewColumnOverrides.builder(HUEditorRow.FIELDNAME_HU_UnitType).restrictToMediaType(MediaType.SCREEN).build(),
														  ClassViewColumnOverrides.builder(HUEditorRow.FIELDNAME_PackingInfo).restrictToMediaType(MediaType.SCREEN).build(),
														  ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_QtyCU),
														  ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_UOM),
														  ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_WeightGross),
														  ClassViewColumnOverrides.builder(HUEditorRow.FIELDNAME_HUStatus).restrictToMediaType(MediaType.SCREEN).build(),
														  ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_BestBeforeDate),
														  ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_Locator));
	}

	@Override
	protected void customizeHUEditorViewRepository(final SqlHUEditorViewRepositoryBuilder huEditorViewRepositoryBuilder)
	{
		huEditorViewRepositoryBuilder
				.showBestBeforeDate(true)
				.showWeightGross(true);
	}

	@Override
	protected void customizeHUEditorView(@NonNull final HUEditorViewBuilder huViewBuilder)
	{
		huViewBuilder
				.addAdditionalRelatedProcessDescriptor(createProcessDescriptor(de.metas.ui.web.picking.husToPick.process.WEBUI_Picking_HUEditor_PickHU.class))
				.addAdditionalRelatedProcessDescriptor(createProcessDescriptor(de.metas.ui.web.picking.husToPick.process.WEBUI_HUsToPick_PickCU.class))
				.addAdditionalRelatedProcessDescriptor(createProcessDescriptor(de.metas.ui.web.picking.husToPick.process.WEBUI_Picking_HUEditor_Create_M_Source_HUs.class))
				.addAdditionalRelatedProcessDescriptor(createProcessDescriptor(de.metas.ui.web.picking.husToPick.process.WEBUI_HUsToPick_Weight.class))
				//
				.clearOrderBys()
				.orderBy(DocumentQueryOrderBy.builder().fieldName(HUEditorRow.FIELDNAME_IsReserved).ascending(false).nullsLast(true).build())
				.orderBy(createBestBeforeDateOrderBy(huViewBuilder.getParameter(HUsToPickViewFilters.PARAM_BestBeforePolicy)))
				.orderBy(DocumentQueryOrderBy.byFieldName(HUEditorRow.FIELDNAME_M_HU_ID));
	}

	private static DocumentQueryOrderBy createBestBeforeDateOrderBy(final ShipmentAllocationBestBeforePolicy bestBeforePolicy)
	{
		final ShipmentAllocationBestBeforePolicy bestBeforePolicyEffective = CoalesceUtil.coalesce(bestBeforePolicy, ShipmentAllocationBestBeforePolicy.Expiring_First);
		if (bestBeforePolicyEffective == ShipmentAllocationBestBeforePolicy.Expiring_First)
		{
			return DocumentQueryOrderBy.builder().fieldName(HUEditorRow.FIELDNAME_BestBeforeDate).ascending(true).nullsLast(true).build();
		}
		else if (bestBeforePolicyEffective == ShipmentAllocationBestBeforePolicy.Newest_First)
		{
			return DocumentQueryOrderBy.builder().fieldName(HUEditorRow.FIELDNAME_BestBeforeDate).ascending(false).nullsLast(true).build();
		}
		else
		{
			throw new AdempiereException("Unknown best before policy: " + bestBeforePolicyEffective);
		}
	}

	@Override
	public IView filterView(final IView view, final JSONFilterViewRequest filterViewRequest, final Supplier<IViewsRepository> viewsRepo)
	{
		final CreateViewRequest.Builder filterViewBuilder = CreateViewRequest.filterViewBuilder(view, filterViewRequest);

		if (view instanceof HUEditorView)
		{
			final HUEditorView huEditorView = HUEditorView.cast(view);
			filterViewBuilder.setParameters(huEditorView.getParameters());

			final ViewId parentViewId = huEditorView.getParentViewId();
			final IView parentView = viewsRepo.get().getView(parentViewId);
			if (parentView instanceof PickingSlotView)
			{
				final PickingSlotView pickingSlotView = PickingSlotView.cast(parentView);

				filterViewBuilder.setParameter(HUsToPickViewFilters.PARAM_CurrentShipmentScheduleId, pickingSlotView.getCurrentShipmentScheduleId());
			}
		}

		final CreateViewRequest createViewRequest = filterViewBuilder.build();

		return createView(createViewRequest);
	}
}
