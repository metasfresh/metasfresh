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

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.TrxRunnable;

import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfo;

// TODO: consider removing it
@Deprecated
public class M_PriceList_Version_RecalculateSeqNo extends JavaProcess
{
	@Override
	protected void prepare()
	{
		// nothing to do
	}

	/**
	 * Recalculates SeqNo in M_ProductPrice in 10-steps for the current M_PriceList_Version, keeping the order they already had
	 */
	@Override
	protected String doIt() throws Exception
	{
		//
		// Services
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

		final Properties ctx = getCtx();

		trxManager.run(new TrxRunnable()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				int seqNumber = 10;

				final ProcessInfo processInfo = getProcessInfo();
				final int recordId = processInfo.getRecord_ID();
				final I_M_PriceList_Version plv = InterfaceWrapperHelper.create(ctx, recordId, I_M_PriceList_Version.class, ITrx.TRXNAME_None);

				final Iterator<I_M_ProductPrice> productPrices = priceListDAO.retrieveAllProductPricesOrderedBySeqNOandProductName(plv);
				while (productPrices.hasNext())
				{
					final I_M_ProductPrice pp = productPrices.next();
					pp.setSeqNo(seqNumber);
					InterfaceWrapperHelper.save(pp);

					seqNumber = seqNumber + 10;
				}
			}
		});

		return "@SeqNoRecalculated@";
	}
}
