package org.compiere.process;

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
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.user.api.IUserSortPrefDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_User_SortPref_Line;
import org.compiere.model.I_AD_User_SortPref_Line_Product;
import org.compiere.util.TrxRunnable;

import de.metas.process.ProcessInfo;
import de.metas.process.JavaProcess;

/**
 * Recalculate for {@link I_AD_User_SortPref_Line}
 *
 * @author al
 */
public class AD_User_SortPref_Line_RecalculateSeqNo extends JavaProcess
{
	@Override
	protected void prepare()
	{
		// nothing to do
	}

	/**
	 * Recalculates SeqNo in AD_User_SortPref_Line_Product in 10-steps for the current AD_User_SortPref_Line, keeping the order they already had
	 */
	@Override
	protected String doIt() throws Exception
	{
		//
		// Services
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final IUserSortPrefDAO userSortPrefDAO = Services.get(IUserSortPrefDAO.class);

		final Properties ctx = getCtx();

		trxManager.run(new TrxRunnable()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				int seqNumber = 10;

				final ProcessInfo processInfo = getProcessInfo();
				final int recordId = processInfo.getRecord_ID();
				final I_AD_User_SortPref_Line sortPreferenceLine = InterfaceWrapperHelper.create(ctx, recordId, I_AD_User_SortPref_Line.class, localTrxName);

				final Iterator<I_AD_User_SortPref_Line_Product> sortPreferenceLineProducts = userSortPrefDAO.retrieveSortPreferenceLineProducts(sortPreferenceLine).iterator();
				while (sortPreferenceLineProducts.hasNext())
				{
					final I_AD_User_SortPref_Line_Product sortPreferenceLineProduct = sortPreferenceLineProducts.next();
					sortPreferenceLineProduct.setSeqNo(seqNumber);
					InterfaceWrapperHelper.save(sortPreferenceLineProduct);

					seqNumber = seqNumber + 10;
				}
			}
		});

		return "@SeqNoRecalculated@";
	}
}
