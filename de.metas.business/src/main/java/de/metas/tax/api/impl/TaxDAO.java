package de.metas.tax.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

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

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.TaxNoExemptFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.Query;

import de.metas.bpartner.BPartnerId;
import de.metas.cache.annotation.CacheCtx;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.organization.OrgId;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.tax.model.I_C_VAT_SmallBusiness;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

public class TaxDAO implements ITaxDAO
{

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_C_Tax getTaxById(final int taxRepoId)
	{
		Check.assumeGreaterThanZero(taxRepoId, "taxRepoId");
		return loadOutOfTrx(taxRepoId, I_C_Tax.class);
	}

	@Override
	public I_C_Tax getTaxByIdOrNull(final int taxRepoId)
	{
		if (taxRepoId <= 0)
		{
			return null;
		}

		return loadOutOfTrx(taxRepoId, I_C_Tax.class);
	}

	@Override
	@Cached(cacheName = I_C_VAT_SmallBusiness.Table_Name + "#By#C_BPartner_ID#Date")
	public boolean retrieveIsTaxExemptSmallBusiness(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final Timestamp date)
	{
		return queryBL.createQueryBuilder(I_C_VAT_SmallBusiness.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_VAT_SmallBusiness.COLUMNNAME_C_BPartner_ID, bPartnerId)
				.addCompareFilter(I_C_VAT_SmallBusiness.COLUMNNAME_ValidFrom, Operator.LESS_OR_EQUAL, date)
				.addCompareFilter(I_C_VAT_SmallBusiness.COLUMNNAME_ValidTo, Operator.GREATER_OR_EQUAL, date)
				.create()
				.anyMatch();
	}

	@Override
	public TaxId retrieveExemptTax(@NonNull final OrgId orgId)
	{
		int C_Tax_ID = queryBL.createQueryBuilder(I_C_Tax.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Tax.COLUMNNAME_IsTaxExempt, true)
				.addEqualsFilter(I_C_Tax.COLUMNNAME_AD_Org_ID, orgId)
				.orderByDescending(I_C_Tax.COLUMNNAME_Rate)
				.create()
				.firstId();
		if (C_Tax_ID <= 0)
		{
			throw new TaxNoExemptFoundException(orgId.getRepoId());
		}
		return TaxId.ofRepoId(C_Tax_ID);
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
		return queryBL.createQueryBuilder(I_C_Tax.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Tax.COLUMNNAME_C_Tax_ID, C_TAX_ID_NO_TAX_FOUND)
				.create()
				.firstOnlyNotNull(I_C_Tax.class);
	}

	@Override
	@Cached(cacheName = I_C_TaxCategory.Table_Name + "#NoTaxFound")
	public I_C_TaxCategory retrieveNoTaxCategoryFound(@CacheCtx final Properties ctx)
	{
		return queryBL.createQueryBuilder(I_C_TaxCategory.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_TaxCategory.COLUMNNAME_C_TaxCategory_ID, TaxCategoryId.NOT_FOUND)
				.create()
				.firstOnlyNotNull(I_C_TaxCategory.class);
	}

	@Override
	public int findTaxCategoryId(@NonNull final TaxCategoryQuery query)
	{
		final IQueryBL queryBL = this.queryBL;

		final IQueryBuilder<I_C_TaxCategory> queryBuilder = queryBL.createQueryBuilder(I_C_TaxCategory.class);

		final IQuery<I_C_Tax> querytaxes = queryBL.createQueryBuilder(I_C_Tax.class)
				.addInArrayFilter(I_C_Tax.COLUMNNAME_C_Country_ID, null, query.getCountryId())
				.create();

		return queryBuilder
				.addInArrayFilter(I_C_TaxCategory.COLUMN_VATType, query.getType().getValue(), null)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addInSubQueryFilter(I_C_TaxCategory.COLUMNNAME_C_TaxCategory_ID, I_C_Tax.COLUMNNAME_C_TaxCategory_ID, querytaxes)
				.orderBy(I_C_TaxCategory.COLUMNNAME_Name)
				.create()
				.firstId();
	}

	@Override
	public I_C_TaxCategory getTaxCategoryById(@NonNull final TaxCategoryId id)
	{
		return loadOutOfTrx(id, I_C_TaxCategory.class);
	}

	@Override
	public ITranslatableString getTaxCategoryNameById(@Nullable final TaxCategoryId id)
	{
		if (id == null)
		{
			return TranslatableStrings.anyLanguage("?");
		}

		final I_C_TaxCategory taxCategory = getTaxCategoryById(id);
		if (taxCategory == null)
		{
			return TranslatableStrings.anyLanguage("<" + id.getRepoId() + ">");
		}

		return InterfaceWrapperHelper.getModelTranslationMap(taxCategory)
				.getColumnTrl(I_C_TaxCategory.COLUMNNAME_Name, taxCategory.getName());
	}

	@Override
	public Optional<TaxCategoryId> getTaxCategoryIdByName(@NonNull final String name)
	{
		final TaxCategoryId taxCategoryId = queryBL.createQueryBuilder(I_C_TaxCategory.class)
				.addEqualsFilter(I_C_TaxCategory.COLUMN_Name, name)
				.create()
				.firstId(TaxCategoryId::ofRepoIdOrNull);

		return Optional.ofNullable(taxCategoryId);
	}
}
