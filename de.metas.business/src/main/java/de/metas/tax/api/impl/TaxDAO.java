package de.metas.tax.api.impl;

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


import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.Query;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.model.I_C_VAT_SmallBusiness;
import lombok.NonNull;

public class TaxDAO implements ITaxDAO
{
	private static int noTaxFoundID = 100;
	private static int noTaxCategoryFoundID = 100;

	@Override
	@Cached(cacheName = I_C_VAT_SmallBusiness.Table_Name + "#By#C_BPartner_ID#Date")
	public boolean retrieveIsTaxExempt(
			final @CacheCtx Properties ctx,
			final int bPartnerId,
			final Timestamp date,
			final @CacheTrx String trxName)
	{
		final Timestamp dateTrunc = TimeUtil.getDay(date);

		final String whereClause = I_C_VAT_SmallBusiness.COLUMNNAME_C_BPartner_ID + "=?"
				+ " AND " + I_C_VAT_SmallBusiness.COLUMNNAME_ValidFrom + "<=?"
				+ " AND " + I_C_VAT_SmallBusiness.COLUMNNAME_ValidTo + ">=?";

		boolean match = new Query(ctx, I_C_VAT_SmallBusiness.Table_Name, whereClause, trxName)
				.setParameters(bPartnerId, dateTrunc, dateTrunc)
				.setOnlyActiveRecords(true)
				.match();

		return match;
	}

	@Override
	public boolean retrieveIsTaxExempt(final I_C_BPartner bPartner, final Timestamp date)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(bPartner);
		final String trxName = InterfaceWrapperHelper.getTrxName(bPartner);
		final int bPartnerId = bPartner.getC_BPartner_ID();

		return retrieveIsTaxExempt(ctx, bPartnerId, date, trxName);
	}

	@Override
	public I_C_Tax getDefaultTax(final I_C_TaxCategory taxCategory)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(taxCategory);
		final String trxName = InterfaceWrapperHelper.getTrxName(taxCategory);
		I_C_Tax tax = InterfaceWrapperHelper.create(ctx, I_C_Tax.class, trxName);

		String whereClause = I_C_Tax.COLUMNNAME_C_TaxCategory_ID + "=? AND " + I_C_Tax.COLUMNNAME_IsDefault + "='Y'";
		List<I_C_Tax> list = new Query(ctx, I_C_Tax.Table_Name, whereClause, trxName)
				.setParameters(taxCategory.getC_TaxCategory_ID())
				.list(I_C_Tax.class);
		if (list.size() == 1)
		{
			tax = list.get(0);
		}
		else
		{
			// Error - should only be one default
			throw new AdempiereException("TooManyDefaults");
		}

		return tax;
	}

	@Override
	@Cached(cacheName = I_C_Tax.Table_Name + "#NoTaxFound")
	public I_C_Tax retrieveNoTaxFound(@CacheCtx final Properties ctx)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Tax.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Tax.COLUMNNAME_C_Tax_ID, noTaxFoundID)
				.create()
				.firstOnlyNotNull(I_C_Tax.class);
	}

	@Override
	@Cached(cacheName = I_C_TaxCategory.Table_Name + "#NoTaxFound")
	public I_C_TaxCategory retrieveNoTaxCategoryFound(@CacheCtx final Properties ctx)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_TaxCategory.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_TaxCategory.COLUMNNAME_C_TaxCategory_ID, noTaxCategoryFoundID)
				.create()
				.firstOnlyNotNull(I_C_TaxCategory.class);
	}

	@Override
	public int findTaxCategoryId(@NonNull final TaxCategoryQuery query)
	{
		final IQueryBuilder<I_C_TaxCategory> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_TaxCategory.class);

		return queryBuilder.addEqualsFilter(I_C_TaxCategory.COLUMN_VATType, query.getType().getValue())
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.orderBy(I_C_TaxCategory.COLUMNNAME_Name)
				.create()
				.firstId();
	}
}
