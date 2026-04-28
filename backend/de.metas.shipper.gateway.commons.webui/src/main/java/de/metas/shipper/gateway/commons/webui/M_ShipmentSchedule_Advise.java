/*
 * #%L
 * de.metas.shipper.gateway.commons.webui
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipper.gateway.commons.webui;

import com.google.common.collect.ImmutableSet;

import de.metas.inout.ShipmentScheduleId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.shipper.gateway.commons.process.CarrierAdviseProcessService;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;

public class M_ShipmentSchedule_Advise extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@NonNull private final CarrierAdviseProcessService helper = SpringContextHolder.instance.getBean(CarrierAdviseProcessService.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private final QueryLimit rowsLimit = QueryLimit.ofInt(sysConfigBL.getPositiveIntValue("M_ShipmentSchedule_Advise.rowsLimit", 1000));

	private static final String PARAM_IsIncludeCarrierAdviseManual = "IsIncludeCarrierAdviseManual";
	@Param(parameterName = PARAM_IsIncludeCarrierAdviseManual, mandatory = true)
	private boolean p_IsIncludeCarrierAdviseManual;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds = getSelectedShipmentScheduleIds();
		if (shipmentScheduleIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds = getSelectedShipmentScheduleIds();
		helper.requestCarrierAdvises(shipmentScheduleIds, p_IsIncludeCarrierAdviseManual);
		return MSG_OK;
	}

	private ImmutableSet<ShipmentScheduleId> getSelectedShipmentScheduleIds()
	{
		return getSelectedIds(ShipmentScheduleId::ofRepoId, rowsLimit);
	}
}
