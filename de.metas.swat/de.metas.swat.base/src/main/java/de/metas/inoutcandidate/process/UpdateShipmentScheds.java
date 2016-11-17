package de.metas.inoutcandidate.process;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.util.Services;

import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

public final class UpdateShipmentScheds extends JavaProcess
{
	public static final String PARAM_IsFullUpdate = "IsFullUpdate";
	private boolean p_IsFullUpdate = false;
	
	@Override
	protected String doIt() throws Exception
	{
		if (p_IsFullUpdate)
		{
			Services.get(IShipmentSchedulePA.class).invalidateAll(getCtx());
		}
		return updateNow(getCtx(), getProcessInfo().getAD_Client_ID(), getAD_User_ID(), getAD_PInstance_ID(), get_TrxName());
	}

	private static String updateNow(
			final Properties ctx,
			final int adClientId,
			final int adUserId,
			final int adPInstanceId,
			final String trxName)
	{
		final IShipmentScheduleUpdater shipmentScheduleUpdater = Services.get(IShipmentScheduleUpdater.class);

		final int result = shipmentScheduleUpdater.updateShipmentSchedule(ctx, adClientId, adUserId, adPInstanceId, trxName);

		return "Updated " + result + " shipment schedule entries";
	}

	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if (name.equals(PARAM_IsFullUpdate))
				p_IsFullUpdate = para.getParameterAsBoolean();
		}
	}
}
