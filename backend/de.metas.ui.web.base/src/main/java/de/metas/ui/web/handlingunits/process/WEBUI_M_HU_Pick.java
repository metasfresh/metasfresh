package de.metas.ui.web.handlingunits.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.requests.PickRequest;
import de.metas.handlingunits.picking.requests.ProcessPickingRequest;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderLineId;
import de.metas.picking.api.PickingSlotId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.picking.husToPick.HUsToPickViewFactory;
import de.metas.ui.web.pporder.PPOrderLinesView;
import de.metas.ui.web.pporder.util.HURow;
import de.metas.ui.web.pporder.util.WEBUI_PP_Order_ProcessHelper;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Stream;

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

public class WEBUI_M_HU_Pick extends ViewBasedProcessTemplate implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final PickingCandidateService pickingCandidateService = SpringContextHolder.instance.getBean(PickingCandidateService.class);

	@Param(parameterName = WEBUI_M_HU_Pick_ParametersFiller.PARAM_M_PickingSlot_ID, mandatory = true)
	private PickingSlotId pickingSlotId;

	@Param(parameterName = WEBUI_M_HU_Pick_ParametersFiller.PARAM_M_ShipmentSchedule_ID, mandatory = true)
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

		if (!WEBUI_PP_Order_ProcessHelper.isEligibleHU(firstRows.get(0)))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no eligible HU rows found");
		}

		return ProcessPreconditionsResolution.accept();
	}

	private ProcessPreconditionsResolution checkEligibleView()
	{
		final IView view = getView();
		final WindowId windowId = view.getViewId().getWindowId();
		if (WindowId.equals(windowId, HUsToPickViewFactory.WINDOW_ID))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not needed in HUsToPick view");
		}
		else if (view instanceof PPOrderLinesView)
		{
			final PPOrderLinesView ppOrderView = (PPOrderLinesView)view;
			return ppOrderView.getDocBaseType().isManufacturingOrder()
					&& ppOrderView.getDocStatus().isCompleted()
					? ProcessPreconditionsResolution.accept()
					: ProcessPreconditionsResolution.rejectWithInternalReason("not needed for PPOrderLinesView view");
		}
		else
		{
			return ProcessPreconditionsResolution.accept();
		}
	}

	private Stream<HURow> streamHURows()
	{
		return streamSelectedRows()
				.map(row -> WEBUI_PP_Order_ProcessHelper.toHURowOrNull(row))
				.filter(Objects::nonNull)
				.filter(HURow::isTopLevelHU)
				.filter(HURow::isHuStatusActive);
	}

	private HURow getSingleHURow()
	{
		return streamHURows()
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("only one selected row was expected")));
	}

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		return createNewDefaultParametersFiller().getDefaultValue(parameter);
	}

	@ProcessParamLookupValuesProvider(//
			parameterName = WEBUI_M_HU_Pick_ParametersFiller.PARAM_M_ShipmentSchedule_ID, //
			numericKey = true, //
			lookupSource = LookupSource.lookup)
	private LookupValuesPage getShipmentScheduleValues(final LookupDataSourceContext context)
	{
		return createNewDefaultParametersFiller().getShipmentScheduleValues(context);
	}

	private WEBUI_M_HU_Pick_ParametersFiller createNewDefaultParametersFiller()
	{
		final HURow row = getSingleHURow();
		return WEBUI_M_HU_Pick_ParametersFiller.defaultFillerBuilder()
				.huId(row.getHuId())
				.salesOrderLineId(getSalesOrderLineId())
				.build();
	}

	@ProcessParamLookupValuesProvider(//
			parameterName = WEBUI_M_HU_Pick_ParametersFiller.PARAM_M_PickingSlot_ID, //
			dependsOn = WEBUI_M_HU_Pick_ParametersFiller.PARAM_M_ShipmentSchedule_ID, //
			numericKey = true, //
			lookupSource = LookupSource.lookup)
	private LookupValuesList getPickingSlotValues(final LookupDataSourceContext context)
	{
		final WEBUI_M_HU_Pick_ParametersFiller filler = WEBUI_M_HU_Pick_ParametersFiller
				.pickingSlotFillerBuilder()
				.shipmentScheduleId(shipmentScheduleId)
				.build();

		return filler.getPickingSlotValues(context);
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

	@Override
	protected String doIt()
	{
		final HURow row = getSingleHURow();
		pickHU(row);

		// invalidate view in order to be refreshed
		getView().invalidateAll();

		return MSG_OK;
	}

	private void pickHU(@NonNull final HURow row)
	{
		final HuId huId = row.getHuId();

		final PickRequest pickRequest = PickRequest.builder()
				.shipmentScheduleId(shipmentScheduleId)
				.pickFrom(PickFrom.ofHuId(huId))
				.pickingSlotId(pickingSlotId)
				.build();

		pickingCandidateService.pickHU(pickRequest);

		final ProcessPickingRequest.ProcessPickingRequestBuilder pickingRequestBuilder = ProcessPickingRequest.builder()
				.huIds(ImmutableSet.of(huId))
				.shipmentScheduleId(shipmentScheduleId)
				.shouldSplitHUIfOverDelivery(!isTakeWholeHU);

		final IView view = getView();
		if (view instanceof PPOrderLinesView)
		{
			final PPOrderLinesView ppOrderView = PPOrderLinesView.cast(view);
			pickingRequestBuilder.ppOrderId(ppOrderView.getPpOrderId());
		}

		WEBUI_PP_Order_ProcessHelper.pickAndProcessSingleHU(pickRequest, pickingRequestBuilder.build());
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

}
