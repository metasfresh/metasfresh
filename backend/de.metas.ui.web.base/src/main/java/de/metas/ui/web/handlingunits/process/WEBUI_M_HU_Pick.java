package de.metas.ui.web.handlingunits.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.requests.PickRequest;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.logging.LogManager;
import de.metas.order.OrderLineId;
import de.metas.picking.api.PickingSlotId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.picking.husToPick.HUsToPickViewFactory;
import de.metas.ui.web.pporder.PPOrderLineRow;
import de.metas.ui.web.pporder.PPOrderLinesView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

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
	private static final Logger logger = LogManager.getLogger(WEBUI_M_HU_Pick.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final PickingCandidateService pickingCandidateService = SpringContextHolder.instance.getBean(PickingCandidateService.class);

	@Param(parameterName = WEBUI_M_HU_Pick_ParametersFiller.PARAM_M_PickingSlot_ID, mandatory = true)
	private PickingSlotId pickingSlotId;

	@Param(parameterName = WEBUI_M_HU_Pick_ParametersFiller.PARAM_M_ShipmentSchedule_ID, mandatory = true)
	private ShipmentScheduleId shipmentScheduleId;

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

		if (!isEligibleHU())
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
					? ProcessPreconditionsResolution.accept()
					: ProcessPreconditionsResolution.rejectWithInternalReason("not needed for PPOrderLinesView view");
		}
		else
		{
			return ProcessPreconditionsResolution.accept();
		}
	}

	private boolean isEligibleHU()
	{
		final HURow row = getSingleHURow();
		final I_M_HU hu = handlingUnitsBL.getById(row.getHuId());

		// Multi product HUs are not allowed - see https://github.com/metasfresh/metasfresh/issues/6709
		return huContextFactory
				.createMutableHUContext()
				.getHUStorageFactory()
				.getStorage(hu)
				.isSingleProductStorage();
	}

	private Stream<HURow> streamHURows()
	{
		return streamSelectedRows()
				.map(WEBUI_M_HU_Pick::toHURowOrNull)
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
	private LookupValuesList getShipmentScheduleValues(final LookupDataSourceContext context)
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

		return MSG_OK;
	}

	private void pickHU(final HURow row)
	{
		final HuId huId = row.getHuId();
		pickingCandidateService.pickHU(PickRequest.builder()
				.shipmentScheduleId(shipmentScheduleId)
				.pickFrom(PickFrom.ofHuId(huId))
				.pickingSlotId(pickingSlotId)
				.build());
		// NOTE: we are not moving the HU to shipment schedule's locator.

		pickingCandidateService.processForHUIds(ImmutableSet.of(huId), shipmentScheduleId);
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

	@Nullable
	private static HURow toHURowOrNull(final IViewRow row)
	{
		if (row instanceof HUEditorRow)
		{
			final HUEditorRow huRow = HUEditorRow.cast(row);
			return HURow.builder()
					.huId(huRow.getHuId())
					.topLevelHU(huRow.isTopLevel())
					.huStatusActive(huRow.isHUStatusActive())
					.build();
		}
		else if (row instanceof PPOrderLineRow)
		{
			final PPOrderLineRow ppOrderLineRow = PPOrderLineRow.cast(row);

			// this process does not apply to source HUs
			if (ppOrderLineRow.isSourceHU())
			{
				return null;
			}

			if (!ppOrderLineRow.getType().isHUOrHUStorage())
			{
				return null;
			}
			return HURow.builder()
					.huId(ppOrderLineRow.getHuId())
					.topLevelHU(ppOrderLineRow.isTopLevelHU())
					.huStatusActive(ppOrderLineRow.isHUStatusActive())
					.build();
		}
		else
		{
			//noinspection ThrowableNotThrown
			new AdempiereException("Row type not supported: " + row).throwIfDeveloperModeOrLogWarningElse(logger);
			return null;
		}
	}

	@Value
	@Builder
	private static class HURow
	{
		HuId huId;
		boolean topLevelHU;
		boolean huStatusActive;
	}
}
