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

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PickingCandidateService;
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
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.handlingunits.process.WEBUI_M_HU_Pick_ParametersFiller;
import de.metas.ui.web.picking.husToPick.HUsToPickViewFactory;
import de.metas.ui.web.pporder.PPOrderLinesView;
import de.metas.ui.web.pporder.util.HURow;
import de.metas.ui.web.pporder.util.WEBUI_PPOrder_PickingContext;
import de.metas.ui.web.pporder.util.WEBUI_PP_Order_HURowHelper;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.util.Services;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Stream;

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

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final ProcessPreconditionsResolution eligibleView = checkEligibleView();
		if (!eligibleView.isAccepted())
		{
			return eligibleView;
		}

		if (!getView().getPlanningStatus().isComplete())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("PPOrder is not complete");
		}

		if (!getSingleSelectedRow().getType().isMainProduct()
				&& !getSingleSelectedRow().isReceipt()
				&& !existsActiveHU())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no active HU found");
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
	@RunOutOfTrx
	protected String doIt()
	{
		getHURowsFromIncludedRows()
				.filter(huRow -> isEligibleHU(huRow))
				.forEach(huRow -> pickHU(huRow.getHuId()));

		// invalidate view in order to be refreshed
		getView().invalidateAll();

		return MSG_OK;
	}

	private void pickHU(final HuId huId)
	{
		final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleBL.getById(shipmentScheduleId);
		final BigDecimal qtyOrdered = shipmentSchedule.getQtyOrdered();
		final BigDecimal qtyPicked = shipmentSchedule.getQtyPickList();
		if (qtyPicked.compareTo(qtyOrdered) < 0)
		{
			WEBUI_PP_Order_HURowHelper.pickHU(WEBUI_PPOrder_PickingContext.builder()
													  .ppOrderId(getView().getPpOrderId())
													  .huId(huId)
													  .shipmentScheduleId(shipmentScheduleId)
													  .pickingSlotId(pickingSlotId)
													  .isTakeWholeHU(isTakeWholeHU)
													  .build());
		}
	}


	private boolean existsActiveHU()
	{
		return getSingleSelectedRow().getIncludedRows().stream()
				.filter(ppOrderLineRow -> ppOrderLineRow.getType().isHUOrHUStorage())
				.anyMatch(ppOrderLineRow -> ppOrderLineRow.isHUStatusActive());
	}

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

	// @ProcessParamLookupValuesProvider(//
	// 		parameterName = WEBUI_M_HU_Pick_ParametersFiller.PARAM_M_ShipmentSchedule_ID, //
	// 		numericKey = true, //
	// 		lookupSource = DocumentLayoutElementFieldDescriptor.LookupSource.lookup)
	// private LookupValuesPage getShipmentScheduleValues(final LookupDataSourceContext context)
	// {
	// 	return createNewDefaultParametersFiller().getShipmentScheduleValues(context);
	// }

	// private WEBUI_M_HU_Pick_ParametersFiller createNewDefaultParametersFiller()
	// {
	// 	final HURow row = toHURowOrNull(getSingleSelectedRow());
	// 	return WEBUI_M_HU_Pick_ParametersFiller.defaultFillerBuilder()
	// 			.huId(row.getHuId())
	// 			.salesOrderLineId(getSalesOrderLineId())
	// 			.build();
	// }

	private Stream<HURow> getHURowsFromIncludedRows()
	{
		return getSingleSelectedRow().getIncludedRows().stream()
				.map(WEBUI_PP_Order_HURowHelper::toHURowOrNull)
				.filter(Objects::nonNull)
				.filter(HURow::isTopLevelHU)
				.filter(HURow::isHuStatusActive);
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		return DEFAULT_VALUE_NOTAVAILABLE;
	}

	private boolean isEligibleHU(final HURow row)
	{
		final I_M_HU hu = handlingUnitsBL.getById(row.getHuId());

		// Multi product HUs are not allowed - see https://github.com/metasfresh/metasfresh/issues/6709
		return huContextFactory
				.createMutableHUContext()
				.getHUStorageFactory()
				.getStorage(hu)
				.isSingleProductStorage();
	}

}
