package de.metas.ui.web.ddorder.process;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.i18n.TranslatableStrings;
import de.metas.printing.esb.base.util.Check;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.util.Services;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_MovementLine;
import de.metas.distribution.ddorder.DDOrderLineId;
import org.eevolution.model.I_DD_OrderLine;

import java.util.List;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class WEBUI_DD_OrderLine_MoveHU extends ViewBasedProcessTemplate implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	// services
	private final DDOrderService ddOrderService = SpringContextHolder.instance.getBean(DDOrderService.class);
	private final DDOrderMoveScheduleService ddOrderMoveScheduleService = SpringContextHolder.instance.getBean(DDOrderMoveScheduleService.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	private static final String PARAM_M_HU_ID = I_M_HU.COLUMNNAME_M_HU_ID;
	@Param(parameterName = PARAM_M_HU_ID, mandatory = true)
	private HuId p_M_HU_ID;

	private static final String PARAM_M_LocatorTo_ID = I_M_MovementLine.COLUMNNAME_M_LocatorTo_ID;
	@Param(parameterName = PARAM_M_LocatorTo_ID)
	private int p_M_LocatorTo_ID;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else if (selectedRowIds.isMoreThanOneDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		final String parameterName = parameter.getColumnName();
		if (PARAM_M_LocatorTo_ID.equals(parameterName))
		{
			final IViewRow row = getSingleSelectedRow();
			return row.getFieldValueAsInt(I_DD_OrderLine.COLUMNNAME_M_LocatorTo_ID, -1);
		}
		else if (PARAM_M_HU_ID.equals(parameterName))
		{
			final DDOrderLineId ddOrderLineId = DDOrderLineId.ofRepoId(getSingleSelectedRow().getId().toInt());

			final List<HuId> huIds = ddOrderMoveScheduleService.retrieveHUIdsScheduledButNotMovedYet(ddOrderLineId);
			if (Check.isEmpty(huIds))
			{
				return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
			}

			final HuId huId = huIds.iterator().next();
			final I_M_HU hu = handlingUnitsBL.getById(huId);

			return IntegerLookupValue.of(huId, TranslatableStrings.anyLanguage(hu.getValue()));
		}
		else
		{
			return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final DDOrderLineId ddOrderLineId = DDOrderLineId.ofRepoId(getSingleSelectedRow().getId().toInt());
		final I_DD_OrderLine ddOrderLine = ddOrderService.getLineById(ddOrderLineId);
		final I_M_HU hu = handlingUnitsBL.getById(p_M_HU_ID);

		ddOrderService.prepareAllocateFullHUsAndMove()
				.toDDOrderLine(ddOrderLine)
				.failIfCannotAllocate()
				.allocateHUAndPrepareGeneratingMovements(hu)
				.locatorToIdOverride(p_M_LocatorTo_ID > 0 ? warehouseBL.getLocatorIdByRepoId(p_M_LocatorTo_ID) : null)
				.generateDirectMovements();

		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (!success)
		{
			return;
		}

		getView().invalidateRowById(getSelectedRowIds().getSingleDocumentId());
	}
}
