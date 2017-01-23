/**
 * 
 */
package de.metas.purchasing.api.impl;

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

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.interfaces.I_C_BPartner_Product;
import de.metas.purchasing.api.IBPartnerProductDAO;

/**
 * @author cg
 * 
 */
public class BPartnerProductDAO implements IBPartnerProductDAO
{
	@Override
	public List<I_C_BPartner_Product> retrieveBPartnerForProduct(final Properties ctx, final int Vendor_ID, final int productId, final int orgId)
	{
		// the original was using table M_Product_PO instead of C_BPartner_Product

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ICompositeQueryFilter<org.compiere.model.I_C_BPartner_Product> queryFilters = queryBL.createCompositeQueryFilter(org.compiere.model.I_C_BPartner_Product.class);
		queryFilters.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_UsedForVendor, true);

		// FRESH-334 only the BP_Products of the given org or of the org 0 are eligible

		queryFilters.addInArrayOrAllFilter(I_C_BPartner_Product.COLUMNNAME_AD_Org_ID, orgId, 0);

		if (Vendor_ID > 0)
		{
			queryFilters.addEqualsFilter(org.compiere.model.I_C_BPartner_Product.COLUMNNAME_C_BPartner_ID, Vendor_ID);
		}
		else
		{
			queryFilters.addEqualsFilter(org.compiere.model.I_C_BPartner_Product.COLUMNNAME_IsCurrentVendor, true);
		}

		queryFilters.addEqualsFilter(I_C_BPartner_Product.COLUMN_M_Product_ID, productId);

		return queryBL
				.createQueryBuilder(org.compiere.model.I_C_BPartner_Product.class, ctx, ITrx.TRXNAME_None)
				.filter(queryFilters)
				// FRESH-334 order by orgID descending. The non 0 org has priority over *
				.orderBy()
				.addColumn(I_C_BPartner_Product.COLUMNNAME_AD_Org_ID, Direction.Descending, Nulls.Last)
				.endOrderBy()
				.create()
				.list(I_C_BPartner_Product.class);
	}

	@Override
	public I_C_BPartner_Product retrieveBPartnerProductAssociation(final I_C_BPartner partner, final I_M_Product product, final int orgId)
	{
		Check.assumeNotNull(partner, "partner not null");
		Check.assumeNotNull(product, "product not null");

		final Properties ctx = InterfaceWrapperHelper.getCtx(partner);
		final int bpartnerId = partner.getC_BPartner_ID();
		final int productId = product.getM_Product_ID();

		return retrieveBPartnerProductAssociation(ctx, bpartnerId, productId, orgId);
	}

	@Override
	public I_C_BPartner_Product retrieveBPartnerProductAssociation(@CacheCtx final Properties ctx, final int bpartnerId, final int productId, final int orgId)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		return retrieveBPartnerProductAssociation(ctx, bpartnerId, productId, orgId, trxName);
	}

	@Cached(cacheName = I_C_BPartner_Product.Table_Name + "#By#C_BPartner_ID#M_Product_ID", expireMinutes = 10)
	public I_C_BPartner_Product retrieveBPartnerProductAssociation(@CacheCtx final Properties ctx, final int bpartnerId, final int productId, final int orgId, @CacheTrx final String trxName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner_Product.class, ctx, trxName)
				//
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_M_Product_ID, productId)
				// FRESH-334 support the case when BP PRoduct is for org 0
				.addInArrayOrAllFilter(I_C_BPartner_Product.COLUMNNAME_AD_Org_ID, orgId, 0)
				// order by ord_id desc
				.orderBy()
				.addColumn(I_C_BPartner_Product.COLUMNNAME_AD_Org_ID, Direction.Descending, Nulls.Last)
				.endOrderBy()
				.create()
				.first(I_C_BPartner_Product.class);
	}

	@Override
	public I_C_BPartner_Product retrieveBPProductForCustomer(final I_C_BPartner partner, final I_M_Product product, final int orgId)
	{
		// query BL
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		// make sure we only pick from the BP product entries for the product given as parameter
		final ICompositeQueryFilter<I_C_BPartner_Product> productQueryFilter = queryBL.createCompositeQueryFilter(I_C_BPartner_Product.class)
				.addEqualsFilter(org.compiere.model.I_C_BPartner_Product.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
				.addInArrayOrAllFilter(I_C_BPartner_Product.COLUMNNAME_AD_Org_ID, orgId, 0);

		final ICompositeQueryFilter<I_C_BPartner_Product> customerQueryFilter = queryBL.createCompositeQueryFilter(I_C_BPartner_Product.class)
				.addEqualsFilter(org.compiere.model.I_C_BPartner_Product.COLUMNNAME_C_BPartner_ID, partner.getC_BPartner_ID())
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_UsedForCustomer, true)
				.addNotEqualsFilter(I_C_BPartner_Product.COLUMNNAME_C_BPartner_Vendor_ID, null);

		// the bp product entry must:
		// Either be used for customer, have the bp given as parameter and the BP vendor not null
		// OR have the isCurrentVendor on true
		final ICompositeQueryFilter<I_C_BPartner_Product> customerOrCurrentVendorQueryFilter = queryBL.createCompositeQueryFilter(I_C_BPartner_Product.class)
				.setJoinOr()
				.addFilter(customerQueryFilter)

		.addEqualsFilter(org.compiere.model.I_C_BPartner_Product.COLUMNNAME_IsCurrentVendor, true);

		final ICompositeQueryFilter<I_C_BPartner_Product> queryFilters = queryBL.createCompositeQueryFilter(I_C_BPartner_Product.class);
		queryFilters.addFilter(productQueryFilter);
		queryFilters.addFilter(customerOrCurrentVendorQueryFilter);

		final IQueryOrderBy bppOrderBy = queryBL.createQueryOrderByBuilder(I_C_BPartner_Product.class)
				.addColumn(org.compiere.model.I_C_BPartner_Product.COLUMNNAME_C_BPartner_Vendor_ID, Direction.Ascending, Nulls.Last)
				.addColumn(I_C_BPartner_Product.COLUMNNAME_AD_Org_ID, Direction.Descending, Nulls.Last)
				.createQueryOrderBy();

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner_Product.class, partner)
				.addOnlyActiveRecordsFilter()
				.filter(queryFilters)

		.create()
				.setOrderBy(bppOrderBy)
				.first(I_C_BPartner_Product.class);
	}
}
