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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.ReferencingPOFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Period;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.adempiere.model.IPOReferenceAware;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionFact;
import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.MCAdvCommissionFact;
import de.metas.commission.model.MCAdvCommissionPayrollLine;
import de.metas.commission.model.X_C_AdvCommissionFact;
import de.metas.commission.service.ICommissionInstanceDAO;
import de.metas.commission.service.ICommissionTermDAO;
import de.metas.commission.service.ISponsorBL;

public abstract class AbstractCommissionInstanceDAO implements ICommissionInstanceDAO
{

	private static final CLogger logger = CLogger.getCLogger(CommissionInstanceDAO.class);

	public static final String WHERE_TOCALC_UNPROCESSED =
			"  (0=? OR " + IAdvComInstance.COLUMNNAME_C_AdvComSystem_Type_ID + "=?) "

					// Only considering unclosed instances. Note: if the instance also has an unwound commission
					// calculation,
					// this will be considered elsewhere separately.
					+ " AND " + IAdvComInstance.COLUMNNAME_IsClosed + "='N'"

					// ?=Y or forecast level differs from calculation level
					+ "  AND ('N'=? OR " + IAdvComInstance.COLUMNNAME_LevelForecast + "!=" + IAdvComInstance.COLUMNNAME_LevelCalculation + ")"

					// instances must have at least one fact with the following properties
					+ "  AND EXISTS ( "
					+ "    select 1 "
					+ "    from C_AdvCommissionFact f  "
					+ "    where f.C_AdvcommissionInstance_ID=C_AdvcommissionInstance.C_AdvcommissionInstance_ID "
					+ "      and f.Status='" + X_C_AdvCommissionFact.STATUS_ZuBerechnen + "'"

					// facts must be unprocessed
					+ "      and f." + I_C_AdvCommissionFact.COLUMNNAME_Processed + "='N'"

					// facts have not been created in a commission calculation
					// (there can be minus facts with status 'CP' from older calculations)
					+ "      and COALESCE(f." + I_C_AdvCommissionFact.COLUMNNAME_FactType + ",'')!='" + X_C_AdvCommissionFact.FACTTYPE_Provisionsberechnung + "'"

					+ "      and f." + I_C_AdvCommissionFact.COLUMNNAME_DateFact + "<=?"
					+ " ) ";

	// public PO retrievePO()
	// {
	// return retrievePO(this);
	// }

	@Override
	public PO retrievePO(final IAdvComInstance instance)
	{
		return InterfaceWrapperHelper.getPO(retrievePO(instance, Object.class));
	}

	@Override
	public <T> T retrievePO(final IAdvComInstance instance, final Class<T> clazz)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(instance);
		final String trxName = InterfaceWrapperHelper.getTrxName(instance);

		final int ad_Table_ID = instance.getAD_Table_ID();
		final int record_ID = instance.getRecord_ID();

		return retrievePOCached(ctx, ad_Table_ID, record_ID, clazz, trxName);
	}

	protected abstract <T> T retrievePOCached(
			Properties ctx,
			int ad_Table_ID,
			int record_ID,
			Class<T> clazz,
			String trxName);

	@Override
	public IAdvComInstance createNew(final Object po)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(po, true);
		final String trxName = InterfaceWrapperHelper.getTrxName(po);

		final String tableName = InterfaceWrapperHelper.getModelTableName(po);
		final int tableId = MTable.getTable_ID(tableName);
		final int recordId = InterfaceWrapperHelper.getId(po);

		final IAdvComInstance instance = InterfaceWrapperHelper.create(ctx, IAdvComInstance.class, trxName);
		instance.setAD_Table_ID(tableId);
		instance.setRecord_ID(recordId);
		// instance.setClientOrg(po);

		return instance;
	}

	@Override
	@Cached
	public IAdvComInstance retriveFirstLine(final IAdvComInstance instance)
	{
		final PO instanceTriggerPO = InterfaceWrapperHelper.getPO(retrievePO(instance, Object.class));

		final List<IAdvComInstance> all = retrieveNonClosedForInstanceTrigger(instanceTriggerPO, instance.getC_AdvComSystem_Type().getC_AdvComSystem_ID());
		Check.assume(!all.isEmpty(), "");

		if (all.isEmpty())
		{
			AbstractCommissionInstanceDAO.logger.info("There is no comission instance for " + instanceTriggerPO);
			return null;
		}

		boolean foundInstance = false;
		for (final IAdvComInstance currentInstance : all)
		{
			if (foundInstance)
			{
				return currentInstance;
			}

			if (currentInstance.getC_AdvCommissionInstance_ID() == instance.getC_AdvCommissionInstance_ID())
			{
				// the next instance in the line will be the one to return
				foundInstance = true;
			}
		}
		AbstractCommissionInstanceDAO.logger.info(instance + " has no first line");
		return null;
	}

	@Override
	public List<IAdvComInstance> retrieveFor(final PO po, final I_C_AdvComSystem_Type comSystemSystemType)
	{
		final Object[] params = new Object[2];
		params[0] = po.get_Table_ID();
		params[1] = po.get_ID();

		final StringBuilder wc = new StringBuilder();

		wc.append("AD_Table_ID=? AND Record_ID=? AND " //
				+ IAdvComInstance.COLUMNNAME_C_AdvCommissionTerm_ID + " in (");

		boolean first = true;
		for (final I_C_AdvCommissionTerm term : Services.get(ICommissionTermDAO.class).retrieveAll(po.getCtx(), comSystemSystemType, po.getAD_Org_ID(), po.get_TrxName()))
		{
			if (first)
			{
				first = false;
			}
			else
			{
				wc.append(", ");
			}
			wc.append(term.getC_AdvCommissionTerm_ID());
		}
		wc.append(")");

		return new Query(po.getCtx(), IAdvComInstance.Table_Name, wc.toString(), po.get_TrxName())
				.setParameters(params)
				.setOnlyActiveRecords(true)
				.setOrderBy(IAdvComInstance.COLUMNNAME_LevelHierarchy)
				.setClient_ID()
				.list(IAdvComInstance.class);
	}

	@Override
	public List<IAdvComInstance> retrieveNonClosedForInstanceTrigger(final Object po, final int comSystemTypeId)
	{
		// final CompositeQueryFilter<I_C_AdvCommissionInstance> filter = new CompositeQueryFilter<I_C_AdvCommissionInstance>()
		// .addFilter(new ReferencingPOFilter<I_C_AdvCommissionInstance>(po))
		// .addOnlyActiveRecordsFilter();

		final Object[] params = { InterfaceWrapperHelper.getModelTableId(po), InterfaceWrapperHelper.getId(po), comSystemTypeId };

		final String whereClause =
				IAdvComInstance.COLUMNNAME_IsClosed + "='N' AND "
						+ IPOReferenceAware.COLUMNNAME_AD_Table_ID + "=? AND "
						+ IPOReferenceAware.COLUMNNAME_Record_ID + "=? AND "
						+ IAdvComInstance.COLUMNNAME_C_AdvComSystem_Type_ID + " IN ("
						+ "   SELECT C_AdvComSystem_Type_ID FROM C_AdvComSystem_Type WHERE " + I_C_AdvComSystem_Type.COLUMNNAME_C_AdvComSystem_ID + "=?"
						+ ")";

		final String orderBy = IAdvComInstance.COLUMNNAME_C_AdvCommissionTerm_ID + ", " + IAdvComInstance.COLUMNNAME_LevelHierarchy;

		return new Query(InterfaceWrapperHelper.getCtx(po), IAdvComInstance.Table_Name, whereClause, InterfaceWrapperHelper.getTrxName(po))
				.setParameters(params)
				.setOrderBy(orderBy)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.list(IAdvComInstance.class);
	}

	@Override
	public List<IAdvComInstance> retrieveAllFor(
			final Object po,
			final I_C_AdvComSystem_Type comSystemType)
	{
		final StringBuilder wc = new StringBuilder(
				"C_AdvCommissionInstance_ID IN ( "
						+ "	SELECT Distinct i.C_AdvCommissionInstance_ID "
						+ "	FROM C_AdvCommissionInstance i "
						+ "		LEFT JOIN C_AdvCommissionFact f ON i.C_AdvCommissionInstance_ID=f.C_AdvCommissionInstance_ID "
						+ "	WHERE ");

		final List<Object> parameters = new ArrayList<Object>();

		if (comSystemType != null)
		{
			wc.append("     " + IAdvComInstance.COLUMNNAME_C_AdvComSystem_Type_ID + "=? AND ");
			parameters.add(comSystemType.getC_AdvComSystem_Type_ID());
		}

		wc.append(
				"		( "
						+ "			(i.AD_Table_ID=? AND i.Record_ID=?) "
						+ "			OR "
						+ "			(f.AD_Table_ID=? AND f.Record_ID=?) "
						+ "		) "
						+ "	) ");

		parameters.add(InterfaceWrapperHelper.getModelTableId(po));
		parameters.add(InterfaceWrapperHelper.getId(po));

		parameters.add(InterfaceWrapperHelper.getModelTableId(po));
		parameters.add(InterfaceWrapperHelper.getId(po));

		final String orderBy = IAdvComInstance.COLUMNNAME_LevelHierarchy;

		return new Query(InterfaceWrapperHelper.getCtx(po), IAdvComInstance.Table_Name, wc.toString(), InterfaceWrapperHelper.getTrxName(po))
				.setParameters(parameters)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(orderBy)
				.list(IAdvComInstance.class);
	}

	@Override
	public Map<Integer, List<IAdvComInstance>> retrieveForCalculation(
			final Properties ctx,
			final I_C_Period period,
			final I_C_AdvComSystem_Type comSystemType,
			final String trxName)
	{
		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		//
		// get instances that have unprocessed facts
		final List<IAdvComInstance> toCalc =
				retrieveToCalcUnprocessed(ctx, period, comSystemType, false, trxName);

		//
		// map instances to sales rep BPartners
		final Map<Integer, List<IAdvComInstance>> result = new HashMap<Integer, List<IAdvComInstance>>();
		final Set<ArrayKey> instAlreadySeen = new HashSet<ArrayKey>();
		for (final IAdvComInstance instance : toCalc)
		{
			final I_C_Sponsor sponsorSalesRep = instance.getC_Sponsor_SalesRep();

			final I_C_BPartner salesRep = sponsorBL.retrieveSalesRepAt(ctx, instance.getDateDoc(), sponsorSalesRep, false, trxName);

			final int bPartnerId = salesRep == null ? 0 : salesRep.getC_BPartner_ID();
			if (salesRep == null)
			{
				AbstractCommissionInstanceDAO.logger.fine("SponsorNo " + sponsorSalesRep.getSponsorNo() + " has no sales rep at date " + instance.getDateDoc());
			}

			final ArrayKey key = Util.mkKey(bPartnerId, instance.getC_AdvCommissionInstance_ID());

			if (instAlreadySeen.contains(key))
			{
				continue;
			}

			addForBPartner(result, instance, bPartnerId);

			instAlreadySeen.add(key);
		}

		//
		// check for instances that need unwinding and add them, too if necessary.
		final List<MCAdvCommissionFact> toCorrect =
				MCAdvCommissionFact.mkQuery(ctx, trxName)
						.setCounterRecord(true)
						.setStatus(X_C_AdvCommissionFact.STATUS_Berechnet, false)
						.setProcessed(false)
						.setFactType(X_C_AdvCommissionFact.FACTTYPE_ProvisionsberRueckabwicklung, false)
						.setDateFactBefore(period.getEndDate())
						.list();

		for (final MCAdvCommissionFact fact : toCorrect)
		{
			final IAdvComInstance instance = InterfaceWrapperHelper.create(fact.getC_AdvCommissionInstance(), IAdvComInstance.class);
			if (!instance.isActive())
			{
				continue;
			}
			if (instance.getC_AdvComSystem_Type_ID() != comSystemType.getC_AdvComSystem_Type_ID())
			{
				continue;
			}

			final MCAdvCommissionPayrollLine cpl = (MCAdvCommissionPayrollLine)fact.retrievePO();

			final int bPartnerId = cpl.getParent().getC_BPartner_ID();
			final ArrayKey key = Util.mkKey(bPartnerId, fact.getC_AdvCommissionInstance_ID());

			if (instAlreadySeen.contains(key))
			{
				continue;
			}

			addForBPartner(result, instance, bPartnerId);

			instAlreadySeen.add(key);
		}
		AbstractCommissionInstanceDAO.logger.info("Retrieved " + toCalc.size() + " instances");
		return result;
	}

	private void addForBPartner(
			final Map<Integer, List<IAdvComInstance>> result,
			final IAdvComInstance instance,
			final int bPartnerId)
	{
		List<IAdvComInstance> instances = result.get(bPartnerId);
		if (instances == null)
		{
			instances = new ArrayList<IAdvComInstance>();
			result.put(bPartnerId, instances);
		}
		instances.add(instance);
	}

	@Override
	public List<IAdvComInstance> retrieveToCalcUnprocessed(
			final Properties ctx,
			final I_C_Period period,
			final I_C_AdvComSystem_Type comSystemType,
			final boolean onlyForReview,
			final String trxName)
	{
		final List<Object> params = new ArrayList<Object>();
		params.add(comSystemType.getC_AdvComSystem_Type_ID());
		params.add(comSystemType.getC_AdvComSystem_Type_ID());
		params.add(onlyForReview ? "Y" : "N");
		params.add(period.getEndDate());

		return new Query(ctx, IAdvComInstance.Table_Name, AbstractCommissionInstanceDAO.WHERE_TOCALC_UNPROCESSED, trxName)
				.setParameters(params)
				.setOnlyActiveRecords(true)
				.setOrderBy(IAdvComInstance.COLUMNNAME_Created)
				.list(IAdvComInstance.class);
	}

	@Override
	public List<IAdvComInstance> retrieveForSalesRep(
			final I_C_Sponsor salesRep,
			final Timestamp dateFrom,
			final Timestamp dateTo,
			final int commissionTermId)
	{
		final int salesRepId = salesRep == null ? 0 : salesRep.getC_Sponsor_ID();
		final Properties ctx = InterfaceWrapperHelper.getCtx(salesRep);
		final String trxName = InterfaceWrapperHelper.getTrxName(salesRep);

		return retrieveForSponsor(ctx, salesRepId, false, dateFrom, dateTo, commissionTermId, trxName);
	}

	@Override
	public List<IAdvComInstance> retrieveForCustomer(
			final I_C_Sponsor customer,
			final Timestamp dateFrom,
			final Timestamp dateTo,
			final int commissionTermId)
	{
		final int salesRepId = customer == null ? 0 : customer.getC_Sponsor_ID();
		final Properties ctx = InterfaceWrapperHelper.getCtx(customer);
		final String trxName = InterfaceWrapperHelper.getTrxName(customer);

		return retrieveForSponsor(ctx, salesRepId, true, dateFrom, dateTo, commissionTermId, trxName);
	}

	private List<IAdvComInstance> retrieveForSponsor(
			final Properties ctx,
			final int sponsorId, final boolean isCustomer,
			final Timestamp dateFrom, final Timestamp dateTo,
			final int commissionTermId,
			final String trxName)
	{
		final StringBuilder wc = new StringBuilder();

		wc.append(" EXISTS ( ");
		wc.append(" SELECT * FROM ");
		wc.append(I_C_AdvCommissionFact.Table_Name);
		wc.append(" f WHERE ");
		wc.append(" f.DateDoc>=? AND f.DateDoc<=? ");

		// wc.append(" AND f.").append(I_C_AdvCommissionFact.COLUMNNAME_Status);
		// wc.append("='").append(X_C_AdvCommissionFact.STATUS_ZuBerechnen);

		wc.append(" AND f.").append(
				I_C_AdvCommissionFact.COLUMNNAME_C_AdvCommissionInstance_ID);
		wc.append("=");
		wc.append(IAdvComInstance.Table_Name).append(".").append(IAdvComInstance.COLUMNNAME_C_AdvCommissionInstance_ID);

		wc.append(" ) ");

		final List<Object> params = new ArrayList<Object>();
		params.add(dateFrom);
		params.add(dateTo);

		if (sponsorId > 0)
		{
			wc.append(" AND ");
			if (isCustomer)
			{
				wc.append(IAdvComInstance.COLUMNNAME_C_Sponsor_Customer_ID);
			}
			else
			{
				wc.append(IAdvComInstance.COLUMNNAME_C_Sponsor_SalesRep_ID);
			}

			wc.append("=? ");
			params.add(sponsorId);
		}

		if (commissionTermId > 0)
		{
			wc.append(" AND ");
			wc.append(IAdvComInstance.COLUMNNAME_C_AdvCommissionTerm_ID);
			wc.append("=? ");

			params.add(commissionTermId);
		}

		final String orderBy = IAdvComInstance.COLUMNNAME_C_AdvCommissionInstance_ID;

		return new Query(ctx, IAdvComInstance.Table_Name, wc.toString(), trxName)
				.setParameters(params)
				.setOnlyActiveRecords(true)
				.setOrderBy(orderBy)
				.list(IAdvComInstance.class);
	}

	@Override
	public BigDecimal retrieveBalancePoints(final IAdvComInstance instance, final String status)
	{
		return retrieveAmt(instance, status, I_C_AdvCommissionFact.COLUMNNAME_CommissionPoints);
	}

	@Override
	public BigDecimal retrieveBalancePointsSum(final IAdvComInstance instance, final String status)
	{
		return retrieveAmt(instance, status, I_C_AdvCommissionFact.COLUMNNAME_CommissionPointsSum);
	}

	@Override
	public BigDecimal retrieveBalanceAmt(final IAdvComInstance instance, final String status)
	{
		return retrieveAmt(instance, status, I_C_AdvCommissionFact.COLUMNNAME_CommissionAmt);
	}

	@Override
	public BigDecimal retrieveBalanceQty(final IAdvComInstance instance, final String status)
	{
		return retrieveAmt(instance, status, I_C_AdvCommissionFact.COLUMNNAME_Qty);
	}

	/**
	 * Sums up up the values of the given colName for commission facts of the given status.
	 * 
	 * <b>Important:<b/> Returns null if there are no such facts.
	 * 
	 * @param status
	 * @param colName
	 * @return
	 */
	private BigDecimal retrieveAmt(final IAdvComInstance instance, final String status, final String colName)
	{
		final String sql =
				"SELECT sum( coalesce(" + colName + ", 0 ))" //
						+ " FROM " + I_C_AdvCommissionFact.Table_Name//
						+ " WHERE " + I_C_AdvCommissionFact.COLUMNNAME_C_AdvCommissionInstance_ID + "=? AND "
						+ I_C_AdvCommissionFact.COLUMNNAME_Status + "=?";

		final Object[] params = { instance.getC_AdvCommissionInstance_ID(), status };

		final String trxName = InterfaceWrapperHelper.getTrxName(instance);
		final BigDecimal amt = DB.getSQLValueBD(trxName, sql, params);
		if (amt == null)
		{
			return BigDecimal.ZERO;
		}
		return amt;
	}

	@Override
	public IAdvComInstance retrieveNonClosedFor(
			final Object poLine,
			final I_C_Sponsor customerSponsor,
			final I_C_Sponsor salesRepSponsor,
			final int termId)
	{
		final String whereClause =
				IAdvComInstance.COLUMNNAME_IsClosed + "='N' AND "
						+ IAdvComInstance.COLUMNNAME_C_Sponsor_Customer_ID + "=? AND "
						+ IAdvComInstance.COLUMNNAME_C_Sponsor_SalesRep_ID + "=? AND "
						+ IAdvComInstance.COLUMNNAME_C_AdvCommissionTerm_ID + "=? AND "
						+ IPOReferenceAware.COLUMNNAME_AD_Table_ID + "=? AND "
						+ IPOReferenceAware.COLUMNNAME_Record_ID + "=?";

		final Object[] params = {
				customerSponsor.getC_Sponsor_ID(),
				salesRepSponsor.getC_Sponsor_ID(),
				termId,
				InterfaceWrapperHelper.getModelTableId(poLine),
				InterfaceWrapperHelper.getId(poLine)
		};

		return new Query(InterfaceWrapperHelper.getCtx(poLine), IAdvComInstance.Table_Name, whereClause, InterfaceWrapperHelper.getTrxName(poLine))
				.setParameters(params)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.firstOnly(IAdvComInstance.class);
	}

	@Override
	public List<IAdvComInstance> retrieveFor(
			final Object poLine,
			final I_C_Sponsor customerSponsor,
			final I_C_Sponsor salesRepSponsor)
	{

		final ICompositeQueryFilter<IAdvComInstance> salesRepFilter = Services.get(IQueryBL.class).createCompositeQueryFilter(IAdvComInstance.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(IAdvComInstance.COLUMNNAME_C_Sponsor_Customer_ID, customerSponsor.getC_Sponsor_ID())
				.addEqualsFilter(IAdvComInstance.COLUMNNAME_C_Sponsor_SalesRep_ID, salesRepSponsor.getC_Sponsor_ID())
				.addFilter(new ReferencingPOFilter<IAdvComInstance>(poLine));

		final IQueryBuilder<IAdvComInstance> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(IAdvComInstance.class, poLine)
				.filter(salesRepFilter)
				.filterByClientId();

		queryBuilder.orderBy()
				.addColumn(IAdvComInstance.COLUMNNAME_C_AdvCommissionInstance_ID);

		return queryBuilder.create().list(IAdvComInstance.class);
	}
}
