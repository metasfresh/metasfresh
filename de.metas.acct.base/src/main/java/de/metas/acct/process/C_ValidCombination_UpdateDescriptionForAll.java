package de.metas.acct.process;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Iterator;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.JavaProcess;

import org.adempiere.acct.api.IAccountBL;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.util.DB;

/**
 * Calls {@link IAccountBL#setValueDescription(I_C_ValidCombination)} for all accounts defined in the system.
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/07546_Name_of_validcombination_%28104158977931%29
 */
public class C_ValidCombination_UpdateDescriptionForAll extends JavaProcess
{
	private final transient IAccountBL accountBL = Services.get(IAccountBL.class);

	@Override
	protected void prepare()
	{
		// nothing
	}

	@Override
	protected String doIt() throws Exception
	{
		final Iterator<I_C_ValidCombination> accounts = retriveAccounts();

		int countAll = 0;
		int countOK = 0;
		while (accounts.hasNext())
		{
			final I_C_ValidCombination account = accounts.next();
			final boolean updated = updateValueDescription(account);

			countAll++;
			if (updated)
			{
				countOK++;
			}
		}

		return "@Updated@/@Total@ #" + countOK + "/" + countAll;
	}

	private Iterator<I_C_ValidCombination> retriveAccounts()
	{
		final IQueryBuilder<I_C_ValidCombination> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_ValidCombination.class, getCtx(), ITrx.TRXNAME_None)
				.addOnlyContextClient()
				.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_C_ValidCombination.COLUMNNAME_C_ValidCombination_ID);

		final Iterator<I_C_ValidCombination> accounts = queryBuilder.create()
				.iterate(I_C_ValidCombination.class);
		return accounts;
	}

	/**
	 * 
	 * @param account
	 * @return true if updated; false if got errors (which will be logged)
	 */
	private boolean updateValueDescription(I_C_ValidCombination account)
	{
		DB.saveConstraints();
		try
		{
			DB.getConstraints().addAllowedTrxNamePrefix(ITrx.TRXNAME_PREFIX_LOCAL);
			
			accountBL.setValueDescription(account);
			InterfaceWrapperHelper.save(account);
			return true;
		}
		catch (Exception e)
		{
			addLog(account.getCombination() + ": " + e.getLocalizedMessage());
			log.warn(e.getLocalizedMessage(), e);
		}
		finally
		{
			DB.restoreConstraints();
		}

		return false;
	}

}
