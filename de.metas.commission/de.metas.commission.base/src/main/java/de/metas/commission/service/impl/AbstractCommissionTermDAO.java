package de.metas.commission.service.impl;

/*
 * #%L
 * de.metas.commission.base
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
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.ad.dao.impl.ActiveRecordQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_M_Product;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.CLogger;

import de.metas.adempiere.service.IParameterBL;
import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheIgnore;
import de.metas.commission.custom.type.ICommissionType;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.model.I_C_AdvCommissionType;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.X_C_Sponsor_SalesRep;
import de.metas.commission.service.ICommissionContext;
import de.metas.commission.service.ICommissionTermDAO;
import de.metas.commission.service.ICommissionTypeBL;
import de.metas.commission.service.ISponsorBL;

public class AbstractCommissionTermDAO implements ICommissionTermDAO
{
	private final CLogger logger = CLogger.getCLogger(getClass());

	@Override
	public List<I_C_AdvCommissionTerm> retrieveAll(final Properties ctx, final I_C_AdvComSystem_Type type, final int adOrgId, final String trxName)
	{
		final String whereClause = I_C_AdvCommissionTerm.COLUMNNAME_C_AdvComSystem_Type_ID + "=? AND AD_Org_ID in (0,?)";

		final String orderBy = I_C_AdvCommissionTerm.COLUMNNAME_C_AdvCommissionCondition_ID + "," + I_C_AdvCommissionTerm.COLUMNNAME_SeqNo;

		final Object[] parameters = { type.getC_AdvComSystem_Type_ID(), adOrgId };

		final List<I_C_AdvCommissionTerm> result = new Query(ctx, I_C_AdvCommissionTerm.Table_Name, whereClause, trxName)
				.setParameters(parameters)
				.setOrderBy(orderBy)
				.list(I_C_AdvCommissionTerm.class);

		logger.config("Retrieved " + result.size() + " record(s) for AD_Org_ID=" + adOrgId + " and " + type);
		return result;
	}

	@Override
	public List<I_C_AdvCommissionTerm> retrieveAll(final Properties ctx,
			final int orgId, final String trxName)
	{
		final String wc = I_C_AdvCommissionTerm.COLUMNNAME_AD_Org_ID + "=?";

		final List<I_C_AdvCommissionTerm> result = new Query(ctx, I_C_AdvCommissionTerm.Table_Name, wc, trxName)
				.setParameters(orgId)
				.setOnlyActiveRecords(true)
				.setOrderBy(I_C_AdvCommissionTerm.COLUMNNAME_SeqNo)
				.list(I_C_AdvCommissionTerm.class);

		logger.config("Retrieved " + result.size() + " record(s) for AD_Org_ID=" + orgId);

		return result;
	}

	@Override
	public List<I_C_AdvCommissionTerm> retrieveFor(final I_C_AdvCommissionCondition condition)
	{
		final String where = I_C_AdvCommissionTerm.COLUMNNAME_C_AdvCommissionCondition_ID + "=? ";

		final Object params = condition.getC_AdvCommissionCondition_ID();

		final Properties ctx = InterfaceWrapperHelper.getCtx(condition);
		final String trxName = InterfaceWrapperHelper.getTrxName(condition);
		final List<I_C_AdvCommissionTerm> result = new Query(ctx, I_C_AdvCommissionTerm.Table_Name, where, trxName)
				.setParameters(params)
				.setOrderBy(I_C_AdvCommissionTerm.COLUMNNAME_SeqNo)
				.list(I_C_AdvCommissionTerm.class);

		logger.config("Retrieved " + result.size() + " record(s) for " + condition);
		return result;
	}

	@Override
	public List<I_C_AdvCommissionTerm> retrieveForPayrollConcept(
			final Properties ctx, final int payrollConceptId,
			final String trxName)
	{

		final String whereClause = I_C_AdvCommissionTerm.COLUMNNAME_HR_Concept_ID + "=?";

		final Object parameter = payrollConceptId;

		return new Query(ctx, I_C_AdvCommissionTerm.Table_Name, whereClause, trxName)
				.setParameters(parameter)
				.setOnlyActiveRecords(true)
				.list(I_C_AdvCommissionTerm.class);
	}

	@Override
	public I_C_AdvCommissionTerm retrieveFor(
			final I_C_AdvCommissionCondition contract, final I_C_AdvComSystem_Type comSystemType)
	{
		Check.assume(
				contract.getC_AdvComSystem_ID() == comSystemType.getC_AdvComSystem_ID(),
				"contract=" + contract + "and comSystemType=" + comSystemType + " have the same C_AdvComSystem_ID");

		final int contractId = contract.getC_AdvCommissionCondition_ID();
		final Properties ctx = InterfaceWrapperHelper.getCtx(contract);
		final String trxName = InterfaceWrapperHelper.getTrxName(contract);

		return retrieveFor(ctx, comSystemType.getC_AdvComSystem_Type_ID(), contractId, trxName);
	}

	@Override
	public I_C_AdvCommissionTerm retrieveSalesRepFactCollector(
			final Properties ctx,
			final int contractId,
			final String trxName)
	{
		final String wc = I_C_AdvCommissionTerm.COLUMNNAME_C_AdvCommissionCondition_ID + "=? AND " + I_C_AdvCommissionTerm.COLUMNNAME_IsSalesRepFactCollector + "='Y'";

		return new Query(ctx, I_C_AdvCommissionTerm.Table_Name, wc, trxName)
				.setParameters(contractId)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.firstOnly(I_C_AdvCommissionTerm.class);
	}

	@Cached
	private I_C_AdvCommissionTerm retrieveFor(
			final @CacheCtx Properties ctx,
			final int comSystemTypeId,
			final int contractId,
			final @CacheIgnore String trxName)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		
		final IQueryFilter<I_C_AdvCommissionTerm> filter = queryBL.createCompositeQueryFilter(I_C_AdvCommissionTerm.class)
				.addFilter(ActiveRecordQueryFilter.getInstance(I_C_AdvCommissionTerm.class))
				.addEqualsFilter(I_C_AdvCommissionTerm.COLUMNNAME_C_AdvCommissionCondition_ID, contractId)
				.addEqualsFilter(I_C_AdvCommissionTerm.COLUMNNAME_C_AdvComSystem_Type_ID, comSystemTypeId);

		final IQueryBuilder<I_C_AdvCommissionTerm> queryBuilder = queryBL.createQueryBuilder(I_C_AdvCommissionTerm.class)
				.setContext(ctx, trxName)
				.filter(filter);
		queryBuilder.orderBy().addColumn(I_C_AdvCommissionTerm.COLUMNNAME_SeqNo);

		return queryBuilder
				.create()
				.firstOnly(I_C_AdvCommissionTerm.class);

		// final String whereClause = I_C_AdvCommissionTerm.COLUMNNAME_C_AdvCommissionCondition_ID + "=? AND " + I_C_AdvCommissionTerm.COLUMNNAME_C_AdvComSystem_Type_ID + "=?";
		//
		// final Object[] parameters = { contractId, comSystemTypeId };
		//
		// final String orderBy = I_C_AdvCommissionTerm.COLUMNNAME_SeqNo;
		//
		// return new Query(ctx, I_C_AdvCommissionTerm.Table_Name, whereClause, trxName)
		// .setParameters(parameters)
		// .setOnlyActiveRecords(true)
		// .setOrderBy(orderBy)
		// .firstOnly(I_C_AdvCommissionTerm.class);
	}

	@Override
	public void createParameters(final I_C_AdvCommissionTerm term)
	{
		deleteParameters(term);

		final Properties ctx = InterfaceWrapperHelper.getCtx(term);
		final String trxName = InterfaceWrapperHelper.getTrxName(term);

		final I_C_AdvComSystem_Type comSystemType = term.getC_AdvComSystem_Type();
		final I_C_AdvCommissionType typeDef = comSystemType.getC_AdvCommissionType();
		final ICommissionType type = Services.get(ICommissionTypeBL.class).getBusinessLogic(typeDef, comSystemType);

		final IParameterBL paramBL = Services.get(IParameterBL.class);

		final PO termPO = InterfaceWrapperHelper.getPO(term);
		paramBL.createParameters(termPO, type.getSponsorParams(ctx, term.getC_AdvCommissionCondition(), trxName), ICommissionTermDAO.PARAM_TABLE);
	}

	@Override
	public void deleteParameters(final I_C_AdvCommissionTerm term)
	{
		final IParameterBL paramBL = Services.get(IParameterBL.class);
		final PO termPO = InterfaceWrapperHelper.getPO(term);
		paramBL.deleteParameters(termPO, ICommissionTermDAO.PARAM_TABLE);
	}

	/**
	 * Filters for {@code C_AdvCommissionTerm}s that are part of a contract which a given sponsor has at a given date.
	 * 
	 * @author ts
	 * 
	 */
	private static final class ExistsSalesRepSSR implements IQueryFilter<I_C_AdvCommissionTerm>, ISqlQueryFilter
	{
		private final I_C_Sponsor sponsor;
		private final Timestamp date;

		private final String sql;
		private final List<Object> sqlParams;

		private ExistsSalesRepSSR(final I_C_Sponsor sponsor, final Timestamp date, final I_C_AdvComSystem comSystem)
		{
			this.sponsor = sponsor;
			this.date = date;

			sql = "EXISTS ( select 1 from " + I_C_Sponsor_SalesRep.Table_Name + " ssr "
					+ " where ssr." + I_C_Sponsor_SalesRep.COLUMNNAME_SponsorSalesRepType + " = ? "
					+ "   AND ssr." + I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_ID + " = ? "
					+ "   AND ssr." + I_C_Sponsor_SalesRep.COLUMNNAME_ValidFrom + " <= ? "
					+ "   AND ssr." + I_C_Sponsor_SalesRep.COLUMNNAME_ValidTo + " >= ? "
					+ "   AND ssr." + I_C_Sponsor_SalesRep.COLUMNNAME_C_AdvCommissionCondition_ID
					+ " = " + I_C_AdvCommissionTerm.Table_Name + "." + I_C_AdvCommissionTerm.COLUMNNAME_C_AdvCommissionCondition_ID
					+ " )";

			sqlParams = Arrays.asList(new Object[] {
					X_C_Sponsor_SalesRep.SPONSORSALESREPTYPE_VP,
					sponsor.getC_Sponsor_ID(),
					date,
					date });
		}

		@Override
		public String getSql()
		{
			return sql;
		}

		@Override
		public List<Object> getSqlParams(final Properties ctx)
		{
			return sqlParams;
		}

		@Override
		public boolean accept(final I_C_AdvCommissionTerm model)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(model);
			final String trxName = InterfaceWrapperHelper.getTrxName(model);

			final I_C_Sponsor_SalesRep ssr = Services.get(ISponsorBL.class).retrieveContractSSR(ctx, sponsor, date, trxName);

			return ssr != null
					&& ssr.getC_AdvCommissionCondition_ID() == model.getC_AdvCommissionCondition_ID();
		}

	}

	@Override
	public I_C_AdvCommissionTerm retrieveTermForSponsorAndProductAndSystemType(final ICommissionContext comCtx)
	{
		Check.assumeNotNull(comCtx.getC_Sponsor(), "comCtx.getC_Sponsor() is not null");
		Check.assumeNotNull(comCtx.getDate(), "comCtx.getDate() is not null");

		final IQueryBuilder<I_C_AdvCommissionTerm> queryBuilder = mkProductAndSystemTypeFilter(comCtx.getC_AdvComSystem_Type(), comCtx.getM_Product());

		return queryBuilder.filter(new ExistsSalesRepSSR(comCtx.getC_Sponsor(), comCtx.getDate(), comCtx.getC_AdvComSystem()))
				.create()
				.first(I_C_AdvCommissionTerm.class);
	}

	@Override
	public List<I_C_AdvCommissionTerm> retrieveTermsForProductAndSystemType(final I_C_AdvComSystem_Type type, final I_M_Product product)
	{
		final IQueryBuilder<I_C_AdvCommissionTerm> queryBuilder = mkProductAndSystemTypeFilter(type, product);

		return queryBuilder
				.create()
				.list(I_C_AdvCommissionTerm.class);
	}

	private IQueryBuilder<I_C_AdvCommissionTerm> mkProductAndSystemTypeFilter(final I_C_AdvComSystem_Type type, final I_M_Product product)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		
		final IQueryFilter<I_C_AdvCommissionTerm> typeFilter = queryBL.createCompositeQueryFilter(I_C_AdvCommissionTerm.class)
				.addFilter(ActiveRecordQueryFilter.getInstance(I_C_AdvCommissionTerm.class))
				.addEqualsFilter(I_C_AdvCommissionTerm.COLUMNNAME_C_AdvComSystem_Type_ID, type.getC_AdvComSystem_Type_ID());

		// product and category filter: either product or category must match, or the must be NULL
		final IQueryFilter<I_C_AdvCommissionTerm> productMatchFilter = queryBL.createCompositeQueryFilter(I_C_AdvCommissionTerm.class)
				.addEqualsFilter(I_C_AdvCommissionTerm.COLUMNNAME_M_Product_ID, product.getM_Product_ID());

		final IQueryFilter<I_C_AdvCommissionTerm> productCategoryMatchFilter = queryBL.createCompositeQueryFilter(I_C_AdvCommissionTerm.class)
				.addEqualsFilter(I_C_AdvCommissionTerm.COLUMNNAME_M_Product_ID, null)
				.setJoinAnd()
				.addEqualsFilter(I_C_AdvCommissionTerm.COLUMNNAME_M_Product_Category_ID, product.getM_Product_Category_ID());

		final IQueryFilter<I_C_AdvCommissionTerm> productNullFilter = queryBL.createCompositeQueryFilter(I_C_AdvCommissionTerm.class)
				.addEqualsFilter(I_C_AdvCommissionTerm.COLUMNNAME_M_Product_ID, null)
				.setJoinAnd()
				.addEqualsFilter(I_C_AdvCommissionTerm.COLUMNNAME_M_Product_Category_ID, null);

		// product and category filter: either product or category must match, or the must be NULL
		final IQueryFilter<I_C_AdvCommissionTerm> productFilter = queryBL.createCompositeQueryFilter(I_C_AdvCommissionTerm.class)
				.addFilter(productMatchFilter)
				.setJoinOr()
				.addFilter(productCategoryMatchFilter)
				.setJoinOr()
				.addFilter(productNullFilter);

		final IQueryFilter<I_C_AdvCommissionTerm> filter = queryBL.createCompositeQueryFilter(I_C_AdvCommissionTerm.class)
				.addFilter(typeFilter)
				.setJoinAnd()
				.addFilter(productFilter);

		final IQueryBuilder<I_C_AdvCommissionTerm> queryBuilder = queryBL.createQueryBuilder(I_C_AdvCommissionTerm.class)
				.setContext(type)
				.filter(filter);
		queryBuilder
				.orderBy()
				.addColumn(I_C_AdvCommissionTerm.COLUMNNAME_SeqNo)
				.addColumn(I_C_AdvCommissionTerm.COLUMNNAME_C_AdvComSystem_Type_ID)
				.addColumn(I_C_AdvCommissionTerm.COLUMNNAME_C_AdvCommissionTerm_ID);

		return queryBuilder;
	}

}
