/*
 * #%L
 * de.metas.shipper.gateway.commons
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

package de.metas.shipper.gateway.commons.process;

import com.google.common.collect.ImmutableSet;

import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class M_ShipmentSchedule_Advise extends JavaProcess implements IProcessPrecondition
{
	@NonNull private final CarrierAdviseProcessService helper = SpringContextHolder.instance.getBean(CarrierAdviseProcessService.class);

	private static final String PARAM_IsIncludeCarrierAdviseManual = "isIncludeCarrierAdviseManual";
	@Param(parameterName = PARAM_IsIncludeCarrierAdviseManual, mandatory = true)
	private boolean p_IsIncludeCarrierAdviseManual;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds = getSelectedIncludedRecordIds(I_M_ShipmentSchedule.class, ShipmentScheduleId::ofRepoId);
		helper.requestCarrierAdvises(shipmentScheduleIds, p_IsIncludeCarrierAdviseManual);
		return MSG_OK;
	}
}
