package de.metas.invoicecandidate.housekeeping.sqi.impl;

import org.adempiere.ad.housekeeping.spi.IStartupHouseKeepingTask;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Loggables;
import org.compiere.util.DB;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
/**
 * Cleans up stale <code>C_Invoice_Candidate_Recompute</code> records that might prevent ICs from getting updated.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 * @task https://github.com/metasfresh/metasfresh/issues/251
 */
public class Reset_C_Invoice_Candidate_Recompute implements IStartupHouseKeepingTask
{
	@Override
	public void executeTask()
	{
		final int no = DB.getSQLValue(ITrx.TRXNAME_None, "select de_metas_invoicecandidate.Reset_C_Invoice_Candidate_Recompute();");
		Loggables.get().addLog("Cleaned up " + no + " stale C_Invoice_Candidate_Recompute records");
	}
}
