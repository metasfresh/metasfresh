package de.metas.ui.web.ddorder.process;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import org.adempiere.util.Services;
import org.compiere.model.I_M_MovementLine;
import org.eevolution.model.I_DD_OrderLine;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.ddorder.api.IHUDDOrderBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

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
	private final IHUDDOrderBL huDDOrderBL = Services.get(IHUDDOrderBL.class);

	private static final String PARAM_M_HU_ID = I_M_HU.COLUMNNAME_M_HU_ID;
	@Param(parameterName = PARAM_M_HU_ID, mandatory = true)
	private int p_M_HU_ID;

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
			return row.getFieldJsonValueAsInt(I_DD_OrderLine.COLUMNNAME_M_LocatorTo_ID, -1);
		}
		else
		{
			return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		final int ddOrderLineId = getSingleSelectedRow().getId().toInt();
		final I_DD_OrderLine ddOrderLine = load(ddOrderLineId, I_DD_OrderLine.class);

		final I_M_HU hu = load(p_M_HU_ID, I_M_HU.class);

		huDDOrderBL.createMovements()
				.setDDOrderLines(ImmutableList.of(ddOrderLine))
				.setLocatorToIdOverride(p_M_LocatorTo_ID)
				.setDoDirectMovements(true)
				.setFailIfCannotAllocate(true)
				.allocateHU(hu)
				.process();

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
