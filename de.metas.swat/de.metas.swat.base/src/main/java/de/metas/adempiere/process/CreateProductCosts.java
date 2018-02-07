/**
 *
 */
package de.metas.adempiere.process;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Iterator;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.api.LoggerTrxItemExceptionHandler;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Product;

import de.metas.costing.ICurrentCostsRepository;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;

/**
 * @author tsa
 *
 */
public class CreateProductCosts extends JavaProcess
{
	private final ICurrentCostsRepository currentCostsRepository = Adempiere.getBean(ICurrentCostsRepository.class);

	private int count_all = 0;

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final Iterator<I_M_Product> products = retrieveProducts();
		Services.get(ITrxItemProcessorExecutorService.class)
				.<I_M_Product, Void> createExecutor()
				.setExceptionHandler(LoggerTrxItemExceptionHandler.instance)
				.setProcessor(this::process)
				.process(products);

		return "@Updated@ #" + count_all;
	}

	private void process(final I_M_Product product)
	{
		currentCostsRepository.createDefaultProductCosts(product);
		count_all++;
	}

	private Iterator<I_M_Product> retrieveProducts()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Product.class)
				.addEqualsFilter(I_M_Product.COLUMN_AD_Client_ID, getAD_Client_ID())
				.addOnlyActiveRecordsFilter()
				.orderBy(I_M_Product.COLUMN_M_Product_ID)
				.create()
				.iterate(I_M_Product.class);
	}
}
