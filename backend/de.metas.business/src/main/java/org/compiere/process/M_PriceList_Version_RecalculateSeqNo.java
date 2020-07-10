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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Iterator;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_ProductPrice;

import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.process.JavaProcess;
import de.metas.util.Services;

// TODO: consider removing it
@Deprecated
public class M_PriceList_Version_RecalculateSeqNo extends JavaProcess
{
	// Services
	final ITrxManager trxManager = Services.get(ITrxManager.class);
	final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

	@Override
	protected void prepare()
	{
		// nothing to do
	}

	/**
	 * Recalculates SeqNo in M_ProductPrice in 10-steps for the current M_PriceList_Version, keeping the order they already had
	 */
	@Override
	protected String doIt()
	{
		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoId(getRecord_ID());

		int seqNumber = 10;

		final Iterator<I_M_ProductPrice> productPrices = priceListDAO.retrieveProductPricesOrderedBySeqNoAndProductIdAndMatchSeqNo(priceListVersionId);
		while (productPrices.hasNext())
		{
			final I_M_ProductPrice pp = productPrices.next();
			pp.setSeqNo(seqNumber);
			InterfaceWrapperHelper.save(pp);

			seqNumber = seqNumber + 10;
		}

		return "@SeqNoRecalculated@";
	}
}
