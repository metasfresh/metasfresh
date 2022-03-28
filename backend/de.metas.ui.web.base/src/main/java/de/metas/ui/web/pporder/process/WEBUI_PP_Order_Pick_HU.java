/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
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
import de.metas.handlingunits.HuId;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.order.OrderLineId;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.quantity.Quantity;
import de.metas.ui.web.handlingunits.process.WEBUI_M_HU_Pick_ParametersFiller;
import de.metas.ui.web.picking.husToPick.HUsToPickViewFactory;
import de.metas.ui.web.pporder.PPOrderLinesView;
import de.metas.ui.web.pporder.util.HURow;
import de.metas.ui.web.pporder.util.WEBUI_PPOrder_PickingContext;
import de.metas.ui.web.pporder.util.WEBUI_PP_Order_HURowHelper;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WEBUI_PP_Order_Pick_HU extends WEBUI_PP_Order_Template implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private static final Logger logger = LogManager.getLogger(WEBUI_PP_Order_Pick_HU.class);

	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

	@Param(parameterName = I_M_PickingSlot.COLUMNNAME_M_PickingSlot_ID, mandatory = true)
	private PickingSlotId pickingSlotId;

	@Param(parameterName = I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, mandatory = true)
	private ShipmentScheduleId shipmentScheduleId;

	@Param(parameterName = "IsTakeWholeHU", mandatory = true)
	private boolean isTakeWholeHU;

	@Param(parameterName = "IsPickAllRecievedHUs", mandatory = true)
	private boolean isPickAllRecievedHUs;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final ProcessPreconditionsResolution eligibleView = checkEligibleView();
		if (!eligibleView.isAccepted())
		{
			return eligibleView;
		}

		final ImmutableList<HURow> firstRows = streamHURows().limit(2).collect(ImmutableList.toImmutableList());
		if (firstRows.isEmpty())
		{
			// NOTE: we decided to hide this action when there is not available,
			// because we want to cover the requirements of https://github.com/metasfresh/metasfresh-webui-api/issues/683,
			// were we need to hide the action for source HU lines... and does not worth the effort to handle particularly that case.
			return ProcessPreconditionsResolution.rejectWithInternalReason("no eligible HU rows found");
			// return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(WEBUI_M_HU_Messages.MSG_WEBUI_ONLY_TOP_LEVEL_HU));
		}

		if (firstRows.size() != 1)
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		if (!WEBUI_PP_Order_HURowHelper.isEligibleHU(getSingleHURow()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no eligible HU rows found");
		}

		return ProcessPreconditionsResolution.accept();
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
			return getView().getDocBaseType().isManufacturingOrder()
					? ProcessPreconditionsResolution.accept()
					: ProcessPreconditionsResolution.rejectWithInternalReason("not needed for PPOrderLinesView view");
		}
		else
		{
			return ProcessPreconditionsResolution.accept();
		}
	}

	@Override
	protected String doIt()
	{
		if (isPickAllRecievedHUs)
		{
			// Satisfy the shipment quantity over all the received HUs
			pickHUs();
		}
		else
		{
			pickHU();
		}
		// invalidate view in order to be refreshed
		getView().invalidateAll();

		return MSG_OK;
	}

	private void pickHU()
	{
		final HuId huId = getSingleHURow().getHuId();
		WEBUI_PP_Order_HURowHelper.pickHU(WEBUI_PPOrder_PickingContext.builder()
												  .ppOrderId(getView().getPpOrderId())
												  .huId(huId)
												  .shipmentScheduleId(shipmentScheduleId)
												  .pickingSlotId(pickingSlotId)
												  .isTakeWholeHU(isTakeWholeHU)
												  .build());
	}

	private void pickHUs()
	{
		final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleBL.getById(shipmentScheduleId);
		if (shipmentSchedule.getQtyToDeliver().compareTo(BigDecimal.ZERO) > 0)
		{
			final BigDecimal qtyToPick = shipmentSchedule.getQtyToDeliver();
			for (final Map.Entry<HuId, Quantity> e : calculateDistributionOfQuantityOverHUs(qtyToPick)
					.entrySet())
			{
				WEBUI_PP_Order_HURowHelper.pickHU(WEBUI_PPOrder_PickingContext.builder()
														  .ppOrderId(getView().getPpOrderId())
														  .huId(e.getKey())
														  .shipmentScheduleId(shipmentScheduleId)
														  .qtyToPick(e.getValue())
														  .pickingSlotId(pickingSlotId)
														  .isTakeWholeHU(e.getValue().qtyAndUomCompareToEquals(getHuIdsQuantitiesAsMap().get(e.getKey())))
														  .build());
			}
		}
	}

	private Map<HuId, Quantity> calculateDistributionOfQuantityOverHUs(final BigDecimal qtyToPick)
	{
		final Map<HuId, Quantity> map = getHuIdsQuantitiesAsMap();

		final Map<HuId, Quantity> distribution = new HashMap<>();
		BigDecimal neededQty = qtyToPick;
		for (final HuId huId : map.keySet())
		{
			final Quantity huQty = map.get(huId);
			if (huQty.toBigDecimal().compareTo(neededQty) >= 0)
			{
				distribution.put(huId, Quantity.of(neededQty, huQty.getUOM()));
				break;
			}
			else
			{
				// substract avalaible qty
				distribution.put(huId, huQty);
				neededQty = neededQty.subtract(huQty.toBigDecimal());
			}
		}
		return distribution;
	}

	private Stream<HURow> streamHURows()
	{
		return streamSelectedRows()
				.map(WEBUI_PP_Order_HURowHelper::toHURowOrNull)
				.filter(Objects::nonNull)
				.filter(HURow::isTopLevelHU)
				.filter(HURow::isHuStatusActive);
	}

	private Map<HuId, Quantity> getHuIdsQuantitiesAsMap()
	{
		return getHURowsFromIncludedRows()
				.filter(WEBUI_PP_Order_HURowHelper::isEligibleHU)
				.collect(Collectors.toMap(HURow::getHuId, HURow::getQty));
	}

	private Stream<HURow> getHURowsFromIncludedRows()
	{
		return getView().streamByIds(DocumentIdsSelection.ALL)
				.filter(row -> row.getType().isMainProduct() || row.isReceipt())
				.flatMap(row -> row.getIncludedRows().stream())
				.map(WEBUI_PP_Order_HURowHelper::toHURowOrNull)
				.filter(Objects::nonNull)
				.filter(HURow::isTopLevelHU)
				.filter(HURow::isHuStatusActive)
				.filter(WEBUI_PP_Order_HURowHelper::isEligibleHU);
	}

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		return createNewDefaultParametersFiller().getDefaultValue(parameter);
	}

	@ProcessParamLookupValuesProvider(//
			parameterName = WEBUI_M_HU_Pick_ParametersFiller.PARAM_M_ShipmentSchedule_ID, //
			numericKey = true, //
			lookupSource = DocumentLayoutElementFieldDescriptor.LookupSource.lookup)
	private LookupValuesPage getShipmentScheduleValues(final LookupDataSourceContext context)
	{
		return createNewDefaultParametersFiller().getShipmentScheduleValues(context);
	}


	private HURow getSingleHURow()
	{
		return streamHURows()
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("only one selected row was expected")));
	}

	@Nullable
	private OrderLineId getSalesOrderLineId()
	{
		if ( getView() != null)
		{
			return getView().getSalesOrderLineId();
		}
			return null;
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
	protected void postProcess(final boolean success)
	{
		if (!success)
		{
			return;
		}

		invalidateView();
	}

	private WEBUI_M_HU_Pick_ParametersFiller createNewDefaultParametersFiller()
	{
		final HURow row = getSingleHURow();
		return WEBUI_M_HU_Pick_ParametersFiller.defaultFillerBuilder()
				.huId(row.getHuId())
				.salesOrderLineId(getSalesOrderLineId())
				.build();
	}
}
