package org.eevolution.api.impl;

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

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_M_Product;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductLowLevelUpdater;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/*package */class ProductLowLevelUpdater implements IProductLowLevelUpdater
{
	// services
	private final transient Logger logger = LogManager.getLogger(getClass());
	private final transient IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	private IContextAware _context;
	private int count_ok = 0;
	private int count_err = 0;
	private boolean failOnFirstError = false;

	public ProductLowLevelUpdater()
	{
		super();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public IProductLowLevelUpdater setContext(final IContextAware context)
	{
		this._context = context;
		return this;
	}

	private final IContextAware getContext()
	{
		Check.assumeNotNull(_context, "_context not null");
		return _context;
	}

	@Override
	public IProductLowLevelUpdater update()
	{
		final Iterator<I_M_Product> products = retrieveProductsToUpdate();
		while (products.hasNext())
		{
			final I_M_Product product = products.next();
			update(product);
		}

		return this;
	}

	private final void update(final I_M_Product product)
	{
		try
		{
			final int lowlevel = productBOMBL.calculateProductLowestLevel(product.getM_Product_ID());
			product.setLowLevel(lowlevel);
			InterfaceWrapperHelper.save(product);
			count_ok++;
		}
		catch (Exception e)
		{
			final AdempiereException ex = new AdempiereException("Error while updating product: " + product.getName(), e);
			if (failOnFirstError)
			{
				throw ex;
			}

			logger.error(ex.getLocalizedMessage(), ex);
			count_err++;
		}
	}

	private Iterator<I_M_Product> retrieveProductsToUpdate()
	{
		final IQueryBuilder<I_M_Product> queryBuilder = queryBL
				.createQueryBuilder(I_M_Product.class, getContext())
				.addOnlyContextClient();

		queryBuilder.orderBy()
				.addColumn(I_M_Product.COLUMNNAME_M_Product_ID);

		final Iterator<I_M_Product> products = queryBuilder.create().iterate(I_M_Product.class);
		return products;
	}

	@Override
	public int getUpdatedCount()
	{
		return count_ok;
	}

	@Override
	public int getErrorsCount()
	{
		return count_err;
	}

	@Override
	public ProductLowLevelUpdater setFailOnFirstError(final boolean failOnFirstError)
	{
		this.failOnFirstError = failOnFirstError;
		return this;
	}
}
