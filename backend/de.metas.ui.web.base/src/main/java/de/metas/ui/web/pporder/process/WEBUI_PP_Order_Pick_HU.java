/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.pporder.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.OnOverDelivery;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.requests.PickRequest;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.order.OrderLineId;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.product.ProductId;
import de.metas.ui.web.handlingunits.process.WEBUI_M_HU_Pick_ParametersFiller;
import de.metas.ui.web.picking.husToPick.HUsToPickViewFactory;
import de.metas.ui.web.pporder.PPOrderLineRow;
import de.metas.ui.web.pporder.PPOrderLineType;
import de.metas.ui.web.pporder.PPOrderLinesView;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class WEBUI_PP_Order_Pick_HU extends WEBUI_PP_Order_Template implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private static final Logger logger = LogManager.getLogger(WEBUI_PP_Order_Pick_HU.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final PickingCandidateService pickingCandidateService = SpringContextHolder.instance.getBean(PickingCandidateService.class);

	@Param(parameterName = I_M_PickingSlot.COLUMNNAME_M_PickingSlot_ID, mandatory = true)
	private PickingSlotId pickingSlotId;

	@Param(parameterName = I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, mandatory = true)
	private ShipmentScheduleId shipmentScheduleId;

	@Param(parameterName = "IsTakeWholeHU", mandatory = true)
	private boolean isTakeWholeHU;

	@Nullable
	private OrderLineId getSalesOrderLineId()
	{
		final IView view = getView();
		if (view instanceof PPOrderLinesView)
		{
			final PPOrderLinesView ppOrderLinesView = PPOrderLinesView.cast(view);
			return ppOrderLinesView.getSalesOrderLineId();
		}
		else
		{
			return null;
		}
	}

	@ProcessParamLookupValuesProvider(//
			parameterName = WEBUI_M_HU_Pick_ParametersFiller.PARAM_M_PickingSlot_ID, //
			dependsOn = WEBUI_M_HU_Pick_ParametersFiller.PARAM_M_ShipmentSchedule_ID, //
			numericKey = true, //
			lookupSource = DocumentLayoutElementFieldDescriptor.LookupSource.lookup)
	private LookupValuesList getPickingSlotValues(final LookupDataSourceContext context)
	{
		final WEBUI_M_HU_Pick_ParametersFiller filler = WEBUI_M_HU_Pick_ParametersFiller
				.pickingSlotFillerBuilder()
				.shipmentScheduleId(shipmentScheduleId)
				.build();

		return filler.getPickingSlotValues(context);
	}


	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final ProcessPreconditionsResolution eligibleView = checkEligibleView();
		if (!eligibleView.isAccepted())
		{
			return eligibleView;
		}

		final Set<HuId> distinctHuIds = retrieveSelectedHuIds();
		if (distinctHuIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final Set<HuId> distinctHuIds = retrieveSelectedHuIds();

		// TODO refine to a better picking logic OR  implement a best matching HUs strategy

		for (final HuId huId : distinctHuIds)
		{
			// get shipment schedule & compare Quantity Ordered / Picked (stock UOM) ; based on same UOM
			final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleBL.getById(shipmentScheduleId);
			final BigDecimal qtyOrdered = shipmentSchedule.getQtyToDeliver();
			final BigDecimal qtyPicked = shipmentSchedule.getQtyPickList();
			if(qtyPicked.compareTo(qtyOrdered) < 0)
			{
				pickHU(huId);
			}
		}

		// invalidate view in order to be refreshed
		getView().invalidateAll();

		return MSG_OK;
	}

	private void pickHU(final HuId huId)
	{
		pickingCandidateService.pickHU(PickRequest.builder()
											   .shipmentScheduleId(shipmentScheduleId)
											   .pickFrom(PickFrom.ofHuId(huId))
											   .pickingSlotId(pickingSlotId)
											   .build());
		// NOTE: we are not moving the HU to shipment schedule's locator.
		pickingCandidateService.processForHUIds(ImmutableSet.of(huId),
												shipmentScheduleId,
												OnOverDelivery.ofTakeWholeHUFlag(isTakeWholeHU),
												getView().getPpOrderId());
	}

	private Set<HuId> retrieveSelectedHuIds()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();

		final ImmutableList<PPOrderLineRow> selectedRows = getView()
				.streamByIds(selectedRowIds)
				.collect(ImmutableList.toImmutableList());

		final Set<HuId> huIds = new HashSet<>();

		for (final PPOrderLineRow row : selectedRows)
		{
			final PPOrderLineType type = row.getType();
			if (type.isMainProduct() && row.isReceipt())
			{
				final ImmutableList<PPOrderLineRow> includedRows = row.getIncludedRows();
				includedRows.stream()
						.filter(ppOrderLineRow -> ppOrderLineRow.getType().isHUOrHUStorage())
						.map(PPOrderLineRow::getHuId)
						.filter(Objects::nonNull)
						.filter(huId -> isEligibleHU(huId))
						.forEach(huIds::add);
			}
			else if (type.isHUOrHUStorage())
			{
				final HuId huId = row.getHuId();
				if (huId != null && isMainProductHu(huId))
				{
					huIds.add(huId);
				}
			}
		}
		return huIds;
	}

	private boolean isMainProductHu(final HuId huId)
	{
		return getView().streamByIds(DocumentIdsSelection.ALL)
				.filter(row -> row.getType().isMainProduct() || row.isReceipt())
				.flatMap(row -> row.getIncludedRows().stream())
				.filter(row -> row.getType().isHUOrHUStorage())
				.map(PPOrderLineRow::getHuId)
				.anyMatch(huId::equals);
	}

	private Set<ProductId> retrieveSelectedProductIDs()
	{
		final ImmutableList<PPOrderLineRow> selectedRows = getView()
				.streamByIds(getSelectedRowIds())
				.collect(ImmutableList.toImmutableList());

		final Set<ProductId> productIds = new HashSet<>();
		for (final PPOrderLineRow row : selectedRows)
		{
			productIds.add(row.getProductId());
		}
		return productIds;
	}


	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (Objects.equals(I_M_Product.COLUMNNAME_M_Product_ID, parameter.getColumnName()))
		{
			return retrieveSelectedProductIDs().stream().findFirst().orElse(null);
		}
		return DEFAULT_VALUE_NOTAVAILABLE;
	}

	private ProcessPreconditionsResolution checkEligibleView()
	{
		final WindowId windowId = getView().getViewId().getWindowId();
		if (WindowId.equals(windowId, HUsToPickViewFactory.WINDOW_ID))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not needed in HUsToPick view");
		}
		else if (getView() instanceof PPOrderLinesView)
		{
			final PPOrderLinesView ppOrderView = (PPOrderLinesView)getView();
			return ppOrderView.getDocBaseType().isManufacturingOrder()
					? ProcessPreconditionsResolution.accept()
					: ProcessPreconditionsResolution.rejectWithInternalReason("not needed for PPOrderLinesView view");
		}
		else
		{
			return ProcessPreconditionsResolution.accept();
		}
	}

	private boolean isEligibleHU(final HuId huId)
	{
		final I_M_HU hu = handlingUnitsBL.getById(huId);
		// Multi product HUs are not allowed - see https://github.com/metasfresh/metasfresh/issues/6709
		return huContextFactory
				.createMutableHUContext()
				.getHUStorageFactory()
				.getStorage(hu)
				.isSingleProductStorage();
	}

}
