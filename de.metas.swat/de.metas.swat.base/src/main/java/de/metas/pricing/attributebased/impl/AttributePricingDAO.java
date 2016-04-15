package de.metas.pricing.attributebased.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import de.metas.adempiere.model.I_M_ProductPrice;
import de.metas.pricing.attributebased.IAttributePricingDAO;
import de.metas.pricing.attributebased.I_M_ProductPrice_Attribute;
import de.metas.pricing.attributebased.I_M_ProductPrice_Attribute_Line;

public class AttributePricingDAO implements IAttributePricingDAO
{

	private final static transient Logger logger = LogManager.getLogger(AttributePricingDAO.class);

	private final ICompositeQueryFilter<I_M_ProductPrice_Attribute> priceAttributeFilters = Services.get(IQueryBL.class).createCompositeQueryFilter(I_M_ProductPrice_Attribute.class);

	@Override
	public void registerQueryFilter(final IQueryFilter<I_M_ProductPrice_Attribute> filter)
	{
		priceAttributeFilters.addFilter(filter);
	}

	@Override
	public List<I_M_ProductPrice_Attribute> retrieveAllPriceAttributes(final I_M_ProductPrice productPrice)
	{
		return retrieveWithFilter(productPrice, null);
	}

	@Override
	public List<I_M_ProductPrice_Attribute> retrievePriceAttributes(final I_M_ProductPrice productPrice)
	{
		// task 08804
		final de.metas.pricing.attributebased.I_M_ProductPrice productPriceEx = InterfaceWrapperHelper.create(productPrice, de.metas.pricing.attributebased.I_M_ProductPrice.class);
		if (!productPriceEx.isAttributeDependant())
		{
			return Collections.emptyList();
		}

		final ICompositeQueryFilter<I_M_ProductPrice_Attribute> filter = Services.get(IQueryBL.class).createCompositeQueryFilter(I_M_ProductPrice_Attribute.class);
		filter.addOnlyActiveRecordsFilter();

		return retrieveWithFilter(productPrice, filter);
	}

	@Override
	public List<I_M_ProductPrice_Attribute> retrieveFilteredPriceAttributes(final I_M_ProductPrice productPrice)
	{
		// task 08804
		final de.metas.pricing.attributebased.I_M_ProductPrice productPriceEx = InterfaceWrapperHelper.create(productPrice, de.metas.pricing.attributebased.I_M_ProductPrice.class);
		if (!productPriceEx.isAttributeDependant())
		{
			return Collections.emptyList();
		}

		return retrieveWithFilter(productPrice, priceAttributeFilters);
	}

	private List<I_M_ProductPrice_Attribute> retrieveWithFilter(final I_M_ProductPrice productPrice, final IQueryFilter<I_M_ProductPrice_Attribute> filter)
	{
		final ICompositeQueryFilter<I_M_ProductPrice_Attribute> compositeFilter = Services.get(IQueryBL.class).createCompositeQueryFilter(I_M_ProductPrice_Attribute.class);
		compositeFilter.addEqualsFilter(I_M_ProductPrice_Attribute.COLUMNNAME_M_ProductPrice_ID, productPrice.getM_ProductPrice_ID());
		if (null != filter)
		{
			compositeFilter.addFilter(filter);
		}
		final IQueryOrderBy orderBy = Services.get(IQueryBL.class).createQueryOrderByBuilder(I_M_ProductPrice_Attribute.class)
				.addColumn(I_M_ProductPrice_Attribute.COLUMNNAME_SeqNo)
				.createQueryOrderBy();

		return Services.get(IQueryBL.class).createQueryBuilder(I_M_ProductPrice_Attribute.class, productPrice)
				.filter(compositeFilter)
				.create()
				.setOrderBy(orderBy)
				.list(I_M_ProductPrice_Attribute.class);
	}

	@Override
	public List<I_M_ProductPrice_Attribute_Line> retrieveAttributeLines(final I_M_ProductPrice_Attribute productPriceAttribute)
	{
		return Services
				.get(IQueryBL.class)
				.createQueryBuilder(I_M_ProductPrice_Attribute_Line.class, productPriceAttribute)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ProductPrice_Attribute_Line.COLUMNNAME_M_ProductPrice_Attribute_ID, productPriceAttribute.getM_ProductPrice_Attribute_ID())
				.create()
				.list(I_M_ProductPrice_Attribute_Line.class);
	}

	@Override
	public I_M_ProductPrice_Attribute retrieveDefaultProductPriceAttribute(final I_M_ProductPrice productPrice, final boolean strictDefault)
	{
		Check.assumeNotNull(productPrice, "Param 'productPrice' is not null");

		// task 08804
		final de.metas.pricing.attributebased.I_M_ProductPrice productPriceEx = InterfaceWrapperHelper.create(productPrice, de.metas.pricing.attributebased.I_M_ProductPrice.class);
		if (!productPriceEx.isAttributeDependant())
		{
			logger.debug("M_ProductPrice {}  is not attribute dependant; returning null", productPriceEx);
			return null;
		}

		final IQueryBuilder<I_M_ProductPrice_Attribute> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ProductPrice_Attribute.class, productPrice);

		final ICompositeQueryFilter<I_M_ProductPrice_Attribute> filter = queryBuilder.getFilters();
		filter.addOnlyActiveRecordsFilter();
		filter.addEqualsFilter(I_M_ProductPrice_Attribute.COLUMNNAME_M_ProductPrice_ID, productPrice.getM_ProductPrice_ID());
		if (strictDefault)
		{
			filter.addEqualsFilter(I_M_ProductPrice_Attribute.COLUMNNAME_IsDefault, true);
		}

		queryBuilder.orderBy()
				// Take records with IsDefault=Y first
				.addColumn(I_M_ProductPrice_Attribute.COLUMNNAME_IsDefault, Direction.Descending, Nulls.Last)
				// Take records with lowest SeqNo first
				.addColumn(I_M_ProductPrice_Attribute.COLUMNNAME_SeqNo, Direction.Ascending, Nulls.Last);

		if (strictDefault)
		{
			final I_M_ProductPrice_Attribute strictDefaultFirstTry = queryBuilder.create()
					.firstOnly(I_M_ProductPrice_Attribute.class);
			if (strictDefaultFirstTry != null)
			{
				logger.debug(
						"Returning M_ProductPrice_Attribute {} with IsDefault='Y' for M_ProductPrice {} (param 'strictDefault'==true).",
						new Object[] { strictDefaultFirstTry, productPriceEx });
				return strictDefaultFirstTry;
			}
		}
		else
		{
			// NOTE: we can have more then one result; we take the first one
			final I_M_ProductPrice_Attribute nonStrictTry = queryBuilder
					.create()
					.first(I_M_ProductPrice_Attribute.class);
			logger.debug(
					"Returning M_ProductPrice_Attribute {} for M_ProductPrice {} (param 'strictDefault'==false).",
					new Object[] { nonStrictTry, productPriceEx });
			return nonStrictTry;
		}

		// task 09051: strictDefault = true, but there is no M_ProductPrice_Attribute with IsDefault='Y'
		// However, if there is just *one* single M_ProductPrice_Attribute, we can safely return that one as the default, even if it does not have the flag set.
		// It turned out that this is also consistent with the customer's intuition, so it will save us training/explaining effort :-)
		final I_M_ProductPrice_Attribute strictDefaultSecondTry = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ProductPrice_Attribute.class, productPrice)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ProductPrice_Attribute.COLUMNNAME_M_ProductPrice_ID, productPrice.getM_ProductPrice_ID())
				.create()
				.firstOnlyOrNull(I_M_ProductPrice_Attribute.class);
		logger.debug(
				"Returning *the only active* M_ProductPrice_Attribute {} for M_ProductPrice {} as default despite it has IsDefault='N' (param 'strictDefault'==true).",
				new Object[] { strictDefaultSecondTry, productPriceEx });
		return strictDefaultSecondTry;

	}
}
