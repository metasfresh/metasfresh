package de.metas.ui.web.handlingunits.process;

import java.util.OptionalInt;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.GuavaCollectors;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.logging.LogManager;
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
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import lombok.Builder;
import lombok.Value;

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

	@Autowired
	private PickingCandidateService pickingCandidateService;

	@Param(parameterName = WEBUI_M_HU_Pick_ParametersFiller.PARAM_M_PickingSlot_ID, mandatory = true)
	private int pickingSlotId;

	@Param(parameterName = WEBUI_M_HU_Pick_ParametersFiller.PARAM_M_ShipmentSchedule_ID, mandatory = true)
	private int shipmentScheduleId;

	private WEBUI_M_HU_Pick_ParametersFiller _parametersFiller; // lazy

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (HUsToPickViewFactory.WINDOW_ID.equals(getWindowId()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not needed in HUsToPick view");
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

		return ProcessPreconditionsResolution.accept();
	}

	private Stream<HURow> streamHURows()
	{
		return streamSelectedRows()
				.map(row -> toHURowOrNull(row))
				.filter(Predicates.notNull())
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
		return getParametersFiller().getDefaultValue(parameter);
	}

	@ProcessParamLookupValuesProvider(parameterName = WEBUI_M_HU_Pick_ParametersFiller.PARAM_M_ShipmentSchedule_ID, numericKey = true, lookupSource = LookupSource.lookup)
	private LookupValuesList getShipmentScheduleValues(final LookupDataSourceContext context)
	{
		return getParametersFiller().getShipmentScheduleValues(context);
	}

	@ProcessParamLookupValuesProvider(parameterName = WEBUI_M_HU_Pick_ParametersFiller.PARAM_M_PickingSlot_ID, numericKey = true, lookupSource = LookupSource.lookup)
	private LookupValuesList getPickingSlotValues(final LookupDataSourceContext context)
	{
		return getParametersFiller().getPickingSlotValues(context);
	}

	private WEBUI_M_HU_Pick_ParametersFiller getParametersFiller()
	{
		if (_parametersFiller == null)
		{
			final HURow row = getSingleHURow();
			_parametersFiller = WEBUI_M_HU_Pick_ParametersFiller.builder()
					.huId(row.getHuId())
					.salesOrderLineId(getSalesOrderLineId())
					.build();
		}
		return _parametersFiller;
	}

	private int getSalesOrderLineId()
	{
		final IView view = getView();
		if (view instanceof PPOrderLinesView)
		{
			final PPOrderLinesView ppOrderLinesView = PPOrderLinesView.cast(view);
			return ppOrderLinesView.getSalesOrderLineId();
		}
		else
		{
			return -1;
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		final HURow row = getSingleHURow();
		pickHU(row);

		return MSG_OK;
	}

	private void pickHU(final HURow row)
	{
		final int huId = row.getHuId();
		pickingCandidateService.addHUToPickingSlot(huId, pickingSlotId, shipmentScheduleId);
		// NOTE: we are not moving the HU to shipment schedule's locator.

		pickingCandidateService.processForHUIds(ImmutableList.of(huId), pickingSlotId, OptionalInt.of(shipmentScheduleId));
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

	private static final HURow toHURowOrNull(final IViewRow row)
	{
		if (row instanceof HUEditorRow)
		{
			final HUEditorRow huRow = HUEditorRow.cast(row);
			return HURow.builder()
					.huId(huRow.getM_HU_ID())
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
					.huId(ppOrderLineRow.getM_HU_ID())
					.topLevelHU(ppOrderLineRow.isTopLevelHU())
					.huStatusActive(ppOrderLineRow.isHUStatusActive())
					.build();
		}
		else
		{
			new AdempiereException("Row type not supported: " + row).throwIfDeveloperModeOrLogWarningElse(logger);
			return null;
		}
	}

	@Value
	@Builder
	private static final class HURow
	{
		private final int huId;
		private final boolean topLevelHU;
		private final boolean huStatusActive;
	}
}
