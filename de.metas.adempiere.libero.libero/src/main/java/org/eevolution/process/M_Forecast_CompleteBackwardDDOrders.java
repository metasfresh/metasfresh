package org.eevolution.process;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Forecast;
import org.eevolution.api.IDDOrderBL;

import de.metas.process.JavaProcess;

public class M_Forecast_CompleteBackwardDDOrders extends JavaProcess
{

	private I_M_Forecast p_forecast;

	@Override
	protected void prepare()
	{
		// nothing
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_M_Forecast forecast = getM_Forecast();
		Services.get(IDDOrderBL.class).completeBackwardDDOrders(forecast);

		return MSG_OK;
	}

	private I_M_Forecast getM_Forecast()
	{
		if (p_forecast != null)
		{
			return p_forecast;
		}

		if (I_M_Forecast.Table_Name.equals(getTableName()) && getRecord_ID() > 0)
		{
			p_forecast = InterfaceWrapperHelper.create(getCtx(), getRecord_ID(), I_M_Forecast.class, get_TrxName());
		}

		if (p_forecast == null || p_forecast.getM_Forecast_ID() <= 0)
		{
			throw new FillMandatoryException(I_M_Forecast.COLUMNNAME_M_Forecast_ID);
		}

		return p_forecast;
	}

}
