package de.metas.procurement.base.impl;

import java.util.Date;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.procurement.base.IPMMProductDAO;
import de.metas.procurement.base.model.I_PMM_Product;

/*
 * #%L
 * de.metas.procurement.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class PMMProductDAO implements IPMMProductDAO
{
	@Override
	public IQueryBuilder<I_PMM_Product> retrieveAllPMMProductsValidOnDateQuery(final Date date)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_PMM_Product> queryBuilder = queryBL.createQueryBuilder(I_PMM_Product.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_PMM_Product.COLUMN_M_HU_PI_Item_Product_ID, null)
				.addNotEqualsFilter(I_PMM_Product.COLUMN_M_Warehouse_ID, null);

		//
		// ValidFrom
		queryBuilder.addCompositeQueryFilter()
				.setJoinOr()
				.addCompareFilter(I_PMM_Product.COLUMN_ValidFrom, Operator.LESS_OR_EQUAL, date)
				.addEqualsFilter(I_PMM_Product.COLUMN_ValidFrom, null);

		//
		// ValidTo
		queryBuilder.addCompositeQueryFilter()
				.setJoinOr()
				.addCompareFilter(I_PMM_Product.COLUMN_ValidTo, Operator.GREATER_OR_EQUAL, date)
				.addEqualsFilter(I_PMM_Product.COLUMN_ValidTo, null);

		return queryBuilder;
	}

	@Override
	public List<I_PMM_Product> retrieveByHUPIItemProduct(final I_M_HU_PI_Item_Product huPIItemProduct)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_PMM_Product.class, huPIItemProduct)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PMM_Product.COLUMN_M_HU_PI_Item_Product_ID, huPIItemProduct.getM_HU_PI_Item_Product_ID())
				.create()
				.list();
	}

	@Override
	public List<I_PMM_Product> retrieveByProduct(final I_M_Product product)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_PMM_Product.class, product)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PMM_Product.COLUMN_M_Product_ID, product.getM_Product_ID())
				.create()
				.list();
	}

	@Override
	public List<I_PMM_Product> retrieveByBPartner(final I_C_BPartner bpartner)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_PMM_Product.class, bpartner)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PMM_Product.COLUMN_C_BPartner_ID, bpartner.getC_BPartner_ID())
				.create()
				.list();
	}

	@Override
	public List<I_PMM_Product> retrieveForDateAndProduct(final Date date, final int productId, final int partnerId, final int huPIPId)
	{
		return retrieveAllPMMProductsValidOnDateQuery(date)
				.addInArrayFilter(I_PMM_Product.COLUMNNAME_C_BPartner_ID, partnerId, null) // for the given partner or Not bound to a particular partner (i.e. C_BPartner_ID is null)
				.addEqualsFilter(I_PMM_Product.COLUMN_M_Product_ID, productId)
				.addEqualsFilter(I_PMM_Product.COLUMN_M_HU_PI_Item_Product_ID, huPIPId)
				.orderBy()
				.addColumn(I_PMM_Product.COLUMNNAME_ValidFrom, Direction.Descending, Nulls.Last)
				.endOrderBy()
				.create()
				.list();

	}
}
