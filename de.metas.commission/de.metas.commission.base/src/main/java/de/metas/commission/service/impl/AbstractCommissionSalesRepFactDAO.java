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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Period;
import org.compiere.model.Query;

import de.metas.commission.model.I_C_AdvComFact_SalesRepFact;
import de.metas.commission.model.I_C_AdvComSalesRepFact;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvCommissionFact;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.service.ICommissionSalesRepFactDAO;

public abstract class AbstractCommissionSalesRepFactDAO implements ICommissionSalesRepFactDAO
{
	@Override
	public I_C_AdvComSalesRepFact createDontSave(final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final String name, final String status, final Timestamp dateAcct,
			final int periodId)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		return createDontSave(ctx, sponsor, comSystem, name, status, dateAcct, periodId, trxName);
	}

	@Override
	public I_C_AdvComSalesRepFact createDontSave(final Properties ctx,
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final String name,
			final String status,
			final Timestamp dateAcct,
			final int periodId,
			final String trxName)
	{
		Check.assume(sponsor.getC_Sponsor_ID() > 0, "Param " + sponsor + " has C_Sponsor_ID > 0");
		Check.assume(comSystem.getC_AdvComSystem_ID() > 0, "Param " + comSystem + " has C_AdvComSystem_ID > 0");

		final I_C_AdvComSalesRepFact newFact = InterfaceWrapperHelper.create(ctx, I_C_AdvComSalesRepFact.class, trxName);

		// TODO: shall we use the context from Sponsor?
		// if (sponsor instanceof PO)
		// {
		// newFact.setClientOrg((PO)sponsor);
		// }
		newFact.setAD_Org_ID(sponsor.getAD_Org_ID());

		newFact.setC_AdvComSystem_ID(comSystem.getC_AdvComSystem_ID());
		newFact.setC_Sponsor_ID(sponsor.getC_Sponsor_ID());
		newFact.setName(name);
		newFact.setStatus(status);
		newFact.setC_Period_ID(periodId);
		newFact.setDateAcct(dateAcct);

		return newFact;
	}

	@Override
	public I_C_AdvComSalesRepFact retrieveForDate(
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final String name,
			final String status,
			final Timestamp date)
	{
		assert date != null;

		final String whereClause = //
		I_C_AdvComSalesRepFact.COLUMNNAME_C_Sponsor_ID + "=? AND " //
				+ I_C_AdvComSalesRepFact.COLUMNNAME_C_AdvComSystem_ID + "=? AND " //
				+ I_C_AdvComSalesRepFact.COLUMNNAME_Name + "=? AND " //
				+ I_C_AdvComSalesRepFact.COLUMNNAME_Status + "=? AND " //
				+ I_C_AdvComSalesRepFact.COLUMNNAME_DateAcct + "=?";

		final Object[] params = { sponsor.getC_Sponsor_ID(), comSystem.getC_AdvComSystem_ID(), name, status, date };

		final String orderBy = I_C_AdvComSalesRepFact.COLUMNNAME_C_AdvComSalesRepFact_ID + " DESC";

		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		final I_C_AdvComSalesRepFact existingFact =
				new Query(ctx, I_C_AdvComSalesRepFact.Table_Name, whereClause, trxName)
						.setParameters(params)
						.setClient_ID()
						.setOnlyActiveRecords(true)
						.setOrderBy(orderBy)
						.first(I_C_AdvComSalesRepFact.class);
		return existingFact;
	}

	@Override
	public I_C_AdvComSalesRepFact retrieveForPeriod(
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final String name,
			final String status,
			final int periodId)
	{
		final String whereClause =
				I_C_AdvComSalesRepFact.COLUMNNAME_C_Sponsor_ID + "=? AND "
						+ I_C_AdvComSalesRepFact.COLUMNNAME_Status + "=? AND "
						+ I_C_AdvComSalesRepFact.COLUMNNAME_C_Period_ID + "=? AND "
						+ I_C_AdvComSalesRepFact.COLUMNNAME_C_AdvComSystem_ID + "=? AND "
						+ I_C_AdvComSalesRepFact.COLUMNNAME_Name + "=? ";

		final Object[] params = {
				sponsor.getC_Sponsor_ID(),
				status,
				periodId,
				comSystem.getC_AdvComSystem_ID(),
				name };

		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		final I_C_AdvComSalesRepFact existingFact =
				new Query(ctx, I_C_AdvComSalesRepFact.Table_Name, whereClause, trxName)
						.setParameters(params)
						.setOnlyActiveRecords(true)
						.setClient_ID()
						.firstOnly(I_C_AdvComSalesRepFact.class);
		return existingFact;
	}

	@Override
	public BigDecimal retrieveSumAt(
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final String name,
			final int periodId,
			final String... status)
	{
		final StringBuilder whereClause = new StringBuilder();

		whereClause.append(I_C_AdvComSalesRepFact.COLUMNNAME_C_Sponsor_ID + "=? AND ");
		whereClause.append(I_C_AdvComSalesRepFact.COLUMNNAME_Name + "=? AND ");
		whereClause.append(I_C_AdvComSalesRepFact.COLUMNNAME_C_Period_ID + "=? AND ");
		whereClause.append(I_C_AdvComSalesRepFact.COLUMNNAME_C_AdvComSystem_ID + "=?");

		appendStatus(whereClause, status);

		final Object[] parameters = {
				sponsor.getC_Sponsor_ID(),
				name,
				periodId,
				comSystem.getC_AdvComSystem_ID() };

		return retrieveSum(sponsor, whereClause.toString(), parameters);
	}

	@Override
	public BigDecimal retrieveSumUntil(
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final String name,
			final I_C_Period period,
			final String... status)
	{
		final StringBuilder wc = new StringBuilder();

		wc.append(I_C_AdvComSalesRepFact.COLUMNNAME_C_Sponsor_ID + "=? AND ");
		wc.append(I_C_AdvComSalesRepFact.COLUMNNAME_C_AdvComSystem_ID + "=? AND ");
		wc.append(I_C_AdvComSalesRepFact.COLUMNNAME_Name + "=? AND ");
		wc.append(I_C_AdvComSalesRepFact.COLUMNNAME_DateAcct + "<=? ");

		appendStatus(wc, status);

		final Object[] parameters = { sponsor.getC_Sponsor_ID(), comSystem.getC_AdvComSystem_ID(), name, period.getEndDate() };

		return retrieveSum(sponsor, wc.toString(), parameters);
	}

	@Override
	public List<I_C_AdvComSalesRepFact> retrieveFactsAt(
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final String name,
			final int periodId,
			final String... status)
	{
		final StringBuilder wc = new StringBuilder();

		wc.append(I_C_AdvComSalesRepFact.COLUMNNAME_C_Sponsor_ID + "=? AND ");
		wc.append(I_C_AdvComSalesRepFact.COLUMNNAME_C_AdvComSystem_ID + "=? AND ");
		wc.append(I_C_AdvComSalesRepFact.COLUMNNAME_Name + "=? AND ");
		wc.append(I_C_AdvComSalesRepFact.COLUMNNAME_C_Period_ID + "=?");

		appendStatus(wc, status);

		final Object[] parameters = { sponsor.getC_Sponsor_ID(), comSystem.getC_AdvComSystem_ID(), name, periodId };

		final String orderBy = I_C_AdvComSalesRepFact.COLUMNNAME_C_AdvComSalesRepFact_ID;

		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		return new Query(ctx, I_C_AdvComSalesRepFact.Table_Name, wc.toString(), trxName)
				.setParameters(parameters)
				.setOrderBy(orderBy)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.list(I_C_AdvComSalesRepFact.class);
	}

	@Override
	public List<I_C_AdvComSalesRepFact> retrieveFactsUntil(
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final String srfName,
			final I_C_Period period,
			final boolean excludeWithZeroValueNumber,
			final String... status)
	{
		final StringBuilder wc = new StringBuilder();

		wc.append(I_C_AdvComSalesRepFact.COLUMNNAME_C_Sponsor_ID + "=? AND ");
		wc.append(I_C_AdvComSalesRepFact.COLUMNNAME_C_AdvComSystem_ID + "=? AND ");
		wc.append(I_C_AdvComSalesRepFact.COLUMNNAME_Name + "=? AND ");
		wc.append(I_C_AdvComSalesRepFact.COLUMNNAME_DateAcct + "<=?");

		appendStatus(wc, status);

		if (excludeWithZeroValueNumber)
		{
			wc.append(" AND ");
			wc.append(I_C_AdvComSalesRepFact.COLUMNNAME_ValueNumber + " IS NOT NULL AND ");
			wc.append(I_C_AdvComSalesRepFact.COLUMNNAME_ValueNumber + ">0 ");
		}

		final Object[] params = { sponsor.getC_Sponsor_ID(), comSystem.getC_AdvComSystem_ID(), srfName, period.getEndDate() };

		final String orderBy = I_C_AdvComSalesRepFact.COLUMNNAME_C_AdvComSalesRepFact_ID;

		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		return new Query(ctx, I_C_AdvComSalesRepFact.Table_Name, wc.toString(), trxName)
				.setParameters(params)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setOrderBy(orderBy)
				.list(I_C_AdvComSalesRepFact.class);
	}

	protected void appendStatus(final StringBuilder whereClause, final String... status)
	{
		if (status.length > 0)
		{
			whereClause.append(" AND " + I_C_AdvComSalesRepFact.COLUMNNAME_Status + " in ( ");

			boolean first = true;
			for (final String currentStatus : status)
			{
				if (first)
				{
					first = false;
				}
				else
				{
					whereClause.append(" ,");
				}
				whereClause.append("'");
				whereClause.append(currentStatus);
				whereClause.append("'");
			}
			whereClause.append(")");
		}
	}

	private BigDecimal retrieveSum(final I_C_Sponsor sponsor,
			final String whereClause, final Object[] parameters)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		final Query query = new Query(ctx, I_C_AdvComSalesRepFact.Table_Name, whereClause, trxName)
				.setParameters(parameters)
				.setOnlyActiveRecords(true)
				.setClient_ID();

		return query.sum(I_C_AdvComSalesRepFact.COLUMNNAME_ValueNumber);
	}

	@Override
	public I_C_AdvComSalesRepFact retrieveLatestFor(
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final String factName)
	{
		final String whereClause =
				I_C_AdvComSalesRepFact.COLUMNNAME_Name + "=? AND "
						+ I_C_AdvComSalesRepFact.COLUMNNAME_C_AdvComSystem_ID + "=? AND "
						+ I_C_AdvComSalesRepFact.COLUMNNAME_C_Sponsor_ID + "=?";

		final Object[] params = { factName, comSystem.getC_AdvComSystem_ID(), sponsor.getC_Sponsor_ID() };

		final String orderBy = I_C_AdvComSalesRepFact.COLUMNNAME_Created + " DESC, " + I_C_AdvComSalesRepFact.COLUMNNAME_C_AdvComSalesRepFact_ID + " DESC";

		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);
		return new Query(ctx, I_C_AdvComSalesRepFact.Table_Name, whereClause, trxName)
				.setParameters(params)
				.setOnlyActiveRecords(true)
				.setOrderBy(orderBy)
				.first(I_C_AdvComSalesRepFact.class);
	}

	@Override
	public I_C_AdvComSalesRepFact retrieveLatestAtDate(
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final String factName,
			final Timestamp date,
			final String... status)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		return retrieveLatestAtDate(ctx, sponsor, comSystem, factName, date, trxName, status);
	}

	@Override
	public I_C_AdvComSalesRepFact retrieveLatestAtDate(
			final Properties ctx,
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final String factName,
			final Timestamp date,
			final String trxName,
			final String... status)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		
		final IQueryFilter<I_C_AdvComSalesRepFact> filter = queryBL.createCompositeQueryFilter(I_C_AdvComSalesRepFact.class)
				.addEqualsFilter(I_C_AdvComSalesRepFact.COLUMNNAME_C_Sponsor_ID, sponsor.getC_Sponsor_ID())
				.addEqualsFilter(I_C_AdvComSalesRepFact.COLUMNNAME_Name, factName)
				.addEqualsFilter(I_C_AdvComSalesRepFact.COLUMNNAME_C_AdvComSystem_ID, comSystem.getC_AdvComSystem_ID())
				.addCompareFilter(I_C_AdvComSalesRepFact.COLUMNNAME_DateAcct, Operator.LESS_OR_EQUAL, date)
				.addInArrayOrAllFilter(I_C_AdvComSalesRepFact.COLUMNNAME_Status, status)
				.addOnlyActiveRecordsFilter();

		final IQueryBuilder<I_C_AdvComSalesRepFact> queryBuilder = queryBL.createQueryBuilder(I_C_AdvComSalesRepFact.class, ctx, trxName)
				.filter(filter);

		queryBuilder.orderBy()
				.addColumn(I_C_AdvComSalesRepFact.COLUMNNAME_DateAcct, false)
				.addColumn(I_C_AdvComSalesRepFact.COLUMNNAME_C_AdvComSalesRepFact_ID, false);

		return queryBuilder
				.create()
				.first(I_C_AdvComSalesRepFact.class);
	}

	public static final String WHERE_COMMISSION_FACT = //
	I_C_AdvComSalesRepFact.COLUMNNAME_C_AdvComSalesRepFact_ID
			+ " in ( " //
			+ " select "
			+ I_C_AdvComSalesRepFact.COLUMNNAME_C_AdvComSalesRepFact_ID //
			+ " from "
			+ I_C_AdvComFact_SalesRepFact.Table_Name
			+ " cf_srf " //
			+ " where cf_srf.IsActive='Y' AND " //
			+ " cf_srf."
			+ I_C_AdvComFact_SalesRepFact.COLUMNNAME_C_AdvCommissionFact_ID
			+ "=?"//
			+ " ) ";

	@Override
	public List<I_C_AdvComSalesRepFact> retrieveForComFact(final I_C_AdvCommissionFact cf)
	{
		final int commissionFactId = cf.getC_AdvCommissionFact_ID();
		assert commissionFactId > 0 : cf;

		final Properties ctx = InterfaceWrapperHelper.getCtx(cf);
		final String trxName = InterfaceWrapperHelper.getTrxName(cf);
		return new Query(ctx, I_C_AdvComSalesRepFact.Table_Name, AbstractCommissionSalesRepFactDAO.WHERE_COMMISSION_FACT, trxName)
				.setParameters(commissionFactId)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_AdvComSalesRepFact.COLUMNNAME_C_AdvComSalesRepFact_ID)
				.list(I_C_AdvComSalesRepFact.class);
	}

}
