/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.acct.process;

import de.metas.process.JavaProcess;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;

public class Rebuild_FactAcctSummary extends JavaProcess
{

	@Override
	protected String doIt() throws Exception
	{
		DB.executeFunctionCallEx(ITrx.TRXNAME_ThreadInherited, "select de_metas_acct.Fact_Acct_Summary_RebuildAll()", null);

		return MSG_OK;
	}

}
