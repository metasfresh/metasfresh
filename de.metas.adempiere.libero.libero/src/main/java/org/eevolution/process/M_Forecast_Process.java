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


import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Forecast;
import org.compiere.util.TrxRunnable;

import de.metas.process.SvrProcess;

public class M_Forecast_Process extends SvrProcess
{

	@Override
	protected void prepare()
	{
		// nothing to do
	}

	@Override
	protected String doIt() throws Exception
	{
		Services.get(ITrxManager.class).run(new TrxRunnable()
		{
			@Override
			public void run(String localTrxName) throws Exception
			{
				final I_M_Forecast forecast = InterfaceWrapperHelper.create(getCtx(), getProcessInfo().getRecord_ID(), I_M_Forecast.class, localTrxName);
				forecast.setProcessing(true);
				InterfaceWrapperHelper.save(forecast);
			}
		});

		return "@ForecastProcessed@";
	}

}
