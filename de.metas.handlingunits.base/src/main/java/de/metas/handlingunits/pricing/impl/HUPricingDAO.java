package de.metas.handlingunits.pricing.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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


import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.adempiere.model.I_M_ProductPrice;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ProductPrice_Attribute;
import de.metas.handlingunits.pricing.IHUPricingDAO;

public class HUPricingDAO implements IHUPricingDAO
{
	@Override
	public I_M_ProductPrice_Attribute retrieveHUProductPriceAttribute(final I_M_ProductPrice productPrice, final I_M_HU_PI_Item_Product pip)
	{
		Check.assumeNotNull(productPrice, "Param 'productPrice' is not null");
		Check.assumeNotNull(pip, "Param 'pip' is not null");

		if(!InterfaceWrapperHelper.create(productPrice, de.metas.pricing.attributebased.I_M_ProductPrice.class).isAttributeDependant())
		{
			return null; // task 08804
		}

		final IQueryBuilder<I_M_ProductPrice_Attribute> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_ProductPrice_Attribute.class)
				.setContext(productPrice);

		final ICompositeQueryFilter<I_M_ProductPrice_Attribute> filter = queryBuilder.getFilters();
		filter.addOnlyActiveRecordsFilter();
		filter.addFilter(new EqualsQueryFilter<I_M_ProductPrice_Attribute>(de.metas.pricing.attributebased.I_M_ProductPrice_Attribute.COLUMNNAME_M_ProductPrice_ID, productPrice.getM_ProductPrice_ID()));
		filter.addFilter(new EqualsQueryFilter<I_M_ProductPrice_Attribute>(I_M_ProductPrice_Attribute.COLUMNNAME_IsHUPrice, true));
		filter.addFilter(new EqualsQueryFilter<I_M_ProductPrice_Attribute>(I_M_ProductPrice_Attribute.COLUMNNAME_M_HU_PI_Item_Product_ID, pip.getM_HU_PI_Item_Product_ID()));

		queryBuilder.orderBy()
				// Take records with IsDefault=Y first
				.addColumn(de.metas.pricing.attributebased.I_M_ProductPrice_Attribute.COLUMNNAME_IsDefault, Direction.Descending, Nulls.Last)
				// Take records with lowest SeqNo first
				.addColumn(de.metas.pricing.attributebased.I_M_ProductPrice_Attribute.COLUMNNAME_SeqNo, Direction.Ascending, Nulls.Last);

		// NOTE: we can have more then one result; we take the first one
		return queryBuilder.create()
				.first(I_M_ProductPrice_Attribute.class);
	}
}
