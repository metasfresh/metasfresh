/**
 *
 */
package de.metas.adempiere.process;

/*
 * #%L
 * de.metas.adempiere.adempiere.serverRoot.base
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

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.I_AD_Scheduler;
import org.compiere.model.MScheduler;
import org.compiere.server.Scheduler;

import de.metas.process.RunOutOfTrx;
import de.metas.process.JavaProcess;

/**
 * @author tsa
 *
 */
public class SchedulerRunNow extends JavaProcess
{
	private int p_AD_Scheduler_ID = -1;

	@Override
	protected void prepare()
	{
		if (I_AD_Scheduler.Table_Name.equals(getTableName()))
		{
			p_AD_Scheduler_ID = getRecord_ID();
		}
	}
	
	@Override
	// NOTE: always run out of transaction and let the scheduler manage the transaction
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		if (p_AD_Scheduler_ID <= 0)
		{
			throw new FillMandatoryException("AD_Scheduler_ID");
		}
		final MScheduler schedulerModel = new MScheduler(getCtx(), p_AD_Scheduler_ID, ITrx.TRXNAME_None);
		Scheduler scheduler = new Scheduler(schedulerModel);
		scheduler.runNow();

		return "Ok";
	}
}
