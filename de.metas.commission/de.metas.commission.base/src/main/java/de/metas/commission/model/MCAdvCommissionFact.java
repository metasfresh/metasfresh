package de.metas.commission.model;

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
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_Period;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import de.metas.adempiere.util.CacheCtx;
import de.metas.commission.service.ICommissionFactBL;
import de.metas.commission.service.ICommissionFactCandBL;
import de.metas.commission.service.IFieldAccessBL;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Provisionsberechnung_%282009_0023_G106%29'>(2009 0023 G106)</a>"
 */
public class MCAdvCommissionFact extends X_C_AdvCommissionFact
{

	private static final Logger logger = LogManager.getLogger(MCAdvCommissionFact.class);

	public static final String MSG_COMMISISON_CALCULATION = "CommissionCalculation";

	public static final String MSG_COMMISSION_CHANGE = "CommissionChange";

	public static final String MSG_COMMISSISON_INVOICE = "CommissionInvoice";

	public static final String MSG_COMMISSISON_PAYMENT = "CommissionPayment";

	public static final String MSG_NEW_COMMISSION_PO_LINE = "CommissionNewPoLine";

	/**
	 * 
	 */
	private static final long serialVersionUID = 5098316253804898644L;

	static final String SQL_COMMISSIONTERM = " ( 0=? OR C_AdvCommissionInstance_ID IN ( SELECT C_AdvCommissionInstance_ID FROM C_AdvCommissionInstance WHERE C_AdvCommissionTerm_ID=?) ) ";

	static final String SQL_WHERE =
			I_C_AdvCommissionFact.COLUMNNAME_AD_Table_ID + "=? AND "
					+ I_C_AdvCommissionFact.COLUMNNAME_Record_ID + "=? AND "
					+ MCAdvCommissionFact.SQL_COMMISSIONTERM;

	final static String WHERE_FACTS = //
			"  C_AdvCommissionInstance_ID in (  " //
					+ "      select C_AdvCommissionInstance_ID " //
					+ "      from C_AdvCommissionInstance i " //
					+ "      where " //
					+ "        i.C_Sponsor_Customer_ID=? " //
					+ "    ) " //
					+ "  AND DateDoc>=? AND DateDoc<=? " //
	;

	/**
	 * Creates a new fact for the given po. Ctx, AD_Client_ID, AD_Org_ID and AD_Table_ID are taken from the PO.
	 * <ul>
	 * <li>Only sets <code>Record_ID</code> and <code>AD_Table_ID</code>.</li>
	 * <li>Doesn't save the new instance</li>
	 * </ul>
	 * 
	 * @param po
	 * @param cand
	 * @param trxName
	 * @return
	 */
	public static MCAdvCommissionFact createNew(final Object po, final I_C_AdvCommissionFactCand cand, final String trxName)
	{
		final MCAdvCommissionFact newFact = new MCAdvCommissionFact(InterfaceWrapperHelper.getCtx(po, true), 0, trxName);

		newFact.setAD_Table_ID(MTable.getTable_ID(InterfaceWrapperHelper.getModelTableName(po)));
		newFact.setRecord_ID(InterfaceWrapperHelper.getId(po));

		if (cand != null)
		{
			newFact.setC_AdvCommissionFactCand_ID(cand.getC_AdvCommissionFactCand_ID());
			final I_C_AdvCommissionFactCand cause = Services.get(ICommissionFactBL.class).getCause(cand);
			newFact.setDateFact(cause.getDateAcct());

			// setting document date for po
			// Note1: we can't use cand.retrievePO() so get the headerPO, because 'po' might be a different po. Example:
			// This might be an order line's minus fact. Then po is an order line, while cand might refer to an invoice.
			// Note2: if cand==null, we assume that retrieveDateDocOfPO would not work. That's why we do this call
			// inside this if block,
			final IFieldAccessBL faBL = Services.get(IFieldAccessBL.class);
			final Object headerPO = faBL.retrieveHeader(po);
			final Timestamp dateDoc = Services.get(ICommissionFactCandBL.class).retrieveDateDocOfPO(headerPO);
			newFact.setDateDoc(dateDoc);
		}
		return newFact;
	}

	/**
	 * Similar to {@link #createNew(PO, String)}, but also TrxName is take from po.
	 * 
	 * @param po
	 * @param cand
	 * @return
	 */
	public static MCAdvCommissionFact createNew(final Object po, final I_C_AdvCommissionFactCand cand)
	{
		return createNew(po, cand, InterfaceWrapperHelper.getTrxName(po));
	}

	/**
	 * Convenience method that checks (using {@link FactQuery}) if at least one fact exists that refers to the goven PO.
	 * 
	 * @param po
	 * @return
	 */
	public static boolean isFactExists(final PO po)
	{
		return !mkQuery(po.getCtx(), po.get_TrxName())
				.setTableId(po.get_Table_ID())
				.setRecordId(po.get_ID())
				.list()
				.isEmpty();
	}

	/**
	 * 
	 * @param instanceId
	 * @param po
	 * @param includeCounterEntries
	 * @return
	 * @deprecated use MCAdvCommissionFact.query() instead
	 */
	@Deprecated
	public static List<MCAdvCommissionFact> retrieveFacts(
			final int instanceId,
			final PO po,
			final boolean includeCounterEntries)
	{
		return retrieveFacts(instanceId, po, null, includeCounterEntries);
	}

	/**
	 * Returns all facts of the given instance that refer to a certain table and PO and (optionally) have a certain dateDoc.
	 * 
	 * @param instance
	 * @param adTableId
	 * @param dateDoc may be null
	 * @return
	 * @deprecated use MCAdvCommissionFact.query() instead
	 */
	@Deprecated
	public static List<MCAdvCommissionFact> retrieveFacts(final int instanceId,
			final PO po, final Timestamp dateDoc,
			final boolean includeCounterEntries)
	{
		final FactQuery query = MCAdvCommissionFact.mkQuery(po.getCtx(), po.get_TrxName())
				.setAdvCommissionInstanceId(instanceId)
				.setTableId(po.get_Table_ID())
				.setRecordId(po.get_ID())
				.setDateDoc(dateDoc);

		if (!includeCounterEntries)
		{
			query.setCounterRecord(includeCounterEntries);
		}
		return query.list();
	}

	/**
	 * Returns all facts of the given instance that refer to a certain table.
	 * 
	 * @param instance
	 * @param adTableId
	 * @return
	 * @deprecated use MCAdvCommissionFact.query() instead
	 */
	@Deprecated
	public static List<MCAdvCommissionFact> retrieveFacts(
			final MCAdvCommissionInstance instance, final int adTableId,
			final boolean includeCounterEntries)
	{
		final FactQuery query = MCAdvCommissionFact.mkQuery(instance.getCtx(), instance.get_TrxName())
				.setAdvCommissionInstanceId(instance.get_ID())
				.setTableId(adTableId);

		if (!includeCounterEntries)
		{
			query.setCounterRecord(includeCounterEntries);
		}
		return query.list();
	}

	/**
	 * Returns all facts that belong to the given po
	 * 
	 * @param po
	 * @return
	 */
	public static List<I_C_AdvCommissionFact> retrieveFacts(final PO po, final int commissionTermId)
	{
		final Properties ctx = po.getCtx();
		final String trxName = po.get_TrxName();

		final int tableId = po.get_Table_ID();
		final int recordId = po.get_ID();

		return retrieveFacts(ctx, tableId, recordId, commissionTermId, trxName);
	}

	public static List<MCAdvCommissionFact> retrieveFacts(final I_C_Period period, final int commissionTermId)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(period);
		final String trxName = InterfaceWrapperHelper.getTrxName(period);

		final String wc =
				I_C_AdvCommissionFact.COLUMNNAME_DateDoc + ">=? AND " + I_C_AdvCommissionFact.COLUMNNAME_DateDoc + " <=? AND" + MCAdvCommissionFact.SQL_COMMISSIONTERM;

		final Object[] params = {
				period.getStartDate(),
				period.getEndDate(),
				commissionTermId,
				commissionTermId };

		return new Query(ctx, I_C_AdvCommissionFact.Table_Name, wc, trxName)
				.setParameters(params)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_AdvCommissionFact.COLUMNNAME_C_AdvCommissionFact_ID)
				.list();
	}

	@Cached
	private static List<I_C_AdvCommissionFact> retrieveFacts(
			@CacheCtx final Properties ctx,
			final int tableId,
			final int recordId,
			final int commissionTermId,
			final String trxName)
	{
		final Object[] parameters = {
				tableId, recordId,
				commissionTermId, commissionTermId };

		final String whereClause = MCAdvCommissionFact.SQL_WHERE;

		return new Query(ctx, I_C_AdvCommissionFact.Table_Name, whereClause, trxName)
				.setParameters(parameters)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_AdvCommissionFact.COLUMNNAME_C_AdvCommissionFact_ID)
				.list(I_C_AdvCommissionFact.class);
	}

	/**
	 * Retrieves the commission facts that have the given instance, the given status and there DateDoc value within the given periods.
	 * 
	 * @param instance
	 * @param period if not null, only those facts that have their <code>DateAoc</code> value within this period are returned.
	 * @param status
	 * @return the instance's facts, ordered by <code>Created</code>
	 */
	@Cached
	public static List<MCAdvCommissionFact> retrieveForInstance(
			final MCAdvCommissionInstance instance, final I_C_Period period,
			final String status)
	{
		final StringBuilder wc = new StringBuilder();
		final List<Object> params = new ArrayList<Object>(4);

		wc.append(I_C_AdvCommissionFact.COLUMNNAME_C_AdvCommissionInstance_ID).append("=? AND ");
		params.add(instance.get_ID());

		if (period != null)
		{
			MCAdvCommissionFact.logger.debug("Filtering by " + period);

			wc.append(I_C_AdvCommissionFact.COLUMNNAME_DateFact).append("<=? AND ");
			params.add(period.getEndDate());

		}
		else
		{
			MCAdvCommissionFact.logger.debug("Not filtering by period");
		}

		wc.append(I_C_AdvCommissionFact.COLUMNNAME_Status).append("=? ");
		params.add(status);

		return new Query(instance.getCtx(), I_C_AdvCommissionFact.Table_Name, wc.toString(), instance.get_TrxName())
				.setParameters(params)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_AdvCommissionFact.COLUMNNAME_C_AdvCommissionFact_ID)
				.list();
	}

	public MCAdvCommissionFact(final Properties ctx, final int C_AdvCommissionFact_ID, final String trxName)
	{
		super(ctx, C_AdvCommissionFact_ID, trxName);
	}

	public MCAdvCommissionFact(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Cached(keyProperties = { "AD_Table_ID", "Record_ID" })
	public PO retrievePO()
	{
		return retrievePO(this);
	}

	public static PO retrievePO(final I_C_AdvCommissionFact fact)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(fact);
		final String trxName = InterfaceWrapperHelper.getTrxName(fact);

		final PO po = MTable.get(ctx, fact.getAD_Table_ID()).getPO(fact.getRecord_ID(), trxName);
		if (po == null)
		{
			throw new NullPointerException("Missing PO for " + fact);
		}
		return po;
	}

	public void addFollowUpInfo(final MCAdvCommissionFact followUpFact)
	{
		addFollowUpInfo(this, followUpFact);
	}

	public static void addFollowUpInfo(final I_C_AdvCommissionFact fact, final I_C_AdvCommissionFact followUpFact)
	{
		Check.assume(followUpFact.getC_AdvCommissionFact_ID() > 0, followUpFact + " has C_AdvCommissionFact_ID");

		final String oldValue = fact.getC_AdvComFact_IDs_FollowUp();
		final String newValue;

		if (Check.isEmpty(oldValue))
		{
			newValue = Integer.toString(followUpFact.getC_AdvCommissionFact_ID());
		}
		else
		{
			newValue = oldValue + "," + Integer.toString(followUpFact.getC_AdvCommissionFact_ID());
		}
		fact.setC_AdvComFact_IDs_FollowUp(newValue);
	}

	@Override
	public String toString()
	{
		final StringBuffer sb = new StringBuffer();
		sb.append("MAdvCommissionFact[Id=");
		sb.append(getC_AdvCommissionFact_ID());
		sb.append(", Instance_ID=");
		sb.append(getC_AdvCommissionInstance_ID());
		sb.append(", Table_ID=");
		sb.append(getAD_Table_ID());
		sb.append(", Record_ID=");
		sb.append(getRecord_ID());
		sb.append(", DateDoc=");
		sb.append(getDateDoc());
		sb.append(", Status=");
		sb.append(getStatus());
		sb.append(", Type=");
		sb.append(getFactType());

		sb.append(", PointsSum=");
		sb.append(getCommissionPointsSum());
		sb.append(", Points=");
		sb.append(getCommissionPoints());

		sb.append(", PayrollLine_ID=");
		sb.append(getC_AdvCommissionPayrollLine_ID());

		sb.append("]");

		return sb.toString();
	}

	/**
	 * Returns those facts that refer the given cpl with their <code>C_AdvCommissionPayrollLine_ID</code> column.
	 * 
	 * @param cpl
	 * @return
	 * @deprecated use MCAdvCommissionFact.query() instead
	 */
	@Deprecated
	public static List<MCAdvCommissionFact> retrieveForPayrollLine(
			final MCAdvCommissionPayrollLine cpl)
	{
		return mkQuery(cpl.getCtx(), cpl.get_TrxName())
				.setAdvCommissionPayrollLineId(cpl.get_ID())
				.list();
	}

	/*
	 * IMPORTANT: don't annotate with @Cached, because the result of this method is could to be different during successive calls with the same PO instance!
	 */
	// @Cached
	public static List<I_C_AdvCommissionFact> retrieveFollowUpFacts(final I_C_AdvCommissionFact fact)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(fact);
		final String trxName = InterfaceWrapperHelper.getTrxName(fact);

		final List<I_C_AdvCommissionFact> result = new ArrayList<I_C_AdvCommissionFact>();

		final String advComFactIDsFollowUp = fact.getC_AdvComFact_IDs_FollowUp();
		if (Check.isEmpty(advComFactIDsFollowUp))
		{
			return Collections.emptyList();
		}
		final String[] followUpFactIds = advComFactIDsFollowUp.split(",");

		for (final String followUpFactId : followUpFactIds)
		{
			final I_C_AdvCommissionFact followUpFact = new MCAdvCommissionFact(ctx, Integer.parseInt(followUpFactId), trxName);
			result.add(followUpFact);
		}
		return result;
	}

	public static List<MCAdvCommissionFact> retrieveFacts(
			final I_C_AdvCommissionInstance inst, final I_C_Period period,
			final String comFactStatus)
	{
		final String whereClause = //
		I_C_AdvCommissionFact.COLUMNNAME_C_AdvCommissionInstance_ID + "=? AND " //
				+ I_C_AdvCommissionFact.COLUMNNAME_DateFact + ">=? AND " //
				+ I_C_AdvCommissionFact.COLUMNNAME_DateFact + "<=? AND " //
				+ I_C_AdvCommissionFact.COLUMNNAME_Status + "=?";

		final Object[] params = {
				inst.getC_AdvCommissionInstance_ID(), period.getStartDate(),
				period.getEndDate(), comFactStatus
		};

		final Properties ctx = InterfaceWrapperHelper.getCtx(inst);
		final String trxName = InterfaceWrapperHelper.getTrxName(inst);
		return new Query(ctx, I_C_AdvCommissionFact.Table_Name, whereClause, trxName)
				.setParameters(params)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setOrderBy(I_C_AdvCommissionFact.COLUMNNAME_C_AdvCommissionFact_ID)
				.list();
	}

	public static List<MCAdvCommissionFact> retrieveFacts(final I_C_AdvComSalesRepFact srf)
	{
		final int srfId = srf.getC_AdvComSalesRepFact_ID();
		Check.assume(srfId > 0, "Param " + srf + " has ID>0");

		final Properties ctx = InterfaceWrapperHelper.getCtx(srf);
		final String trxName = InterfaceWrapperHelper.getTrxName(srf);
		return new Query(ctx, I_C_AdvCommissionFact.Table_Name, MCAdvCommissionFact.WHERE_SALES_REP_FACT, trxName)
				.setParameters(srfId)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_AdvCommissionFact.COLUMNNAME_C_AdvCommissionFact_ID)
				.list();
	}

	public static BigDecimal retrieveSum(final I_C_AdvComSalesRepFact srf)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(srf);
		final String trxName = InterfaceWrapperHelper.getTrxName(srf);
		final int salesRepFactId = srf.getC_AdvComSalesRepFact_ID();

		Check.assume(salesRepFactId > 0, "Param " + srf + " has ID>0");

		final BigDecimal sum =
				new Query(ctx, I_C_AdvCommissionFact.Table_Name, MCAdvCommissionFact.WHERE_SALES_REP_FACT, trxName)
						.setParameters(salesRepFactId)
						.setOnlyActiveRecords(true)
						.setClient_ID()
						.sum(I_C_AdvCommissionFact.COLUMNNAME_CommissionPointsSum);

		if (sum == null)
		{
			return BigDecimal.ZERO;
		}
		return sum;
	}

	public static final String WHERE_SALES_REP_FACT = //
	I_C_AdvCommissionFact.COLUMNNAME_C_AdvCommissionFact_ID
			+ " in ( " //
			+ "   select " + I_C_AdvCommissionFact.COLUMNNAME_C_AdvCommissionFact_ID //
			+ "   from " + I_C_AdvComFact_SalesRepFact.Table_Name + " cf_srf " //
			+ "   where cf_srf.IsActive='Y' " //
			+ "     AND cf_srf." + I_C_AdvComFact_SalesRepFact.COLUMNNAME_C_AdvComSalesRepFact_ID + "=?"//
			+ " ) ";

	public static FactQuery query;

	public static FactQuery mkQuery(final Properties ctx, final String trxName)
	{
		return new FactQuery(ctx, trxName);
	}

	public static class FactQuery
	{
		private Integer tableId;
		private Integer recordId;
		private Integer advCommissionInstanceId;
		private Integer advCommissionPayrollLineId;
		private Boolean counterRecord;

		private Timestamp dateDoc;

		private Timestamp dateFact;

		private String status;

		private boolean statusNot;

		private final Properties ctx;
		private String trxName;

		private String followUpSubstring;

		private String factType;

		private boolean factTypeNot;

		private Boolean processed;

		private Boolean includeInactive;

		private Timestamp dateFactBefore;

		private FactQuery(final Properties ctx, final String trxName)
		{
			this.ctx = ctx;
			this.trxName = trxName;
		}

		public FactQuery setTableId(final int tableId)
		{
			this.tableId = tableId;
			return this;
		}

		public FactQuery setRecordId(final int recordId)
		{
			this.recordId = recordId;
			return this;
		}

		public FactQuery setAdvCommissionInstanceId(final int advCommissionInstanceId)
		{
			this.advCommissionInstanceId = advCommissionInstanceId;
			return this;
		}

		public FactQuery setCounterRecord(final boolean counterRecords)
		{
			counterRecord = counterRecords;
			return this;
		}

		public FactQuery setDateDoc(final Timestamp dateDoc)
		{
			this.dateDoc = dateDoc;
			return this;
		}

		public FactQuery setDateFact(final Timestamp dateFact)
		{
			this.dateFact = dateFact;
			return this;
		}

		/**
		 * 
		 * @param status
		 * @param not if true, then 'status' is excluded
		 * @return
		 */
		public FactQuery setStatus(final String status, final boolean not)
		{
			this.status = status;
			statusNot = not;
			return this;
		}

		/**
		 * 
		 * @param factType
		 * @param not if true, then 'factType' is excluded
		 * @return
		 */
		public FactQuery setFactType(final String factType, final boolean not)
		{
			this.factType = factType;
			factTypeNot = not;
			return this;
		}

		public FactQuery setProcessed(final boolean processed)
		{
			this.processed = processed;
			return this;
		}

		public Integer getTableId()
		{
			return tableId;
		}

		public Integer getAdvCommissionInstanceId()
		{
			return advCommissionInstanceId;
		}

		public Integer getAdvCommissionPayrollLineId()
		{
			return advCommissionPayrollLineId;
		}

		public Boolean getCounterRecord()
		{
			return counterRecord;
		}

		public String getTrxName()
		{
			return trxName;
		}

		public void setTrxName(final String trxName)
		{
			this.trxName = trxName;
		}

		public FactQuery setAdvCommissionPayrollLineId(final int advCommissionPayrollLineId)
		{
			this.advCommissionPayrollLineId = advCommissionPayrollLineId;
			return this;
		}

		public String getFollowUpSubstring()
		{
			return followUpSubstring;
		}

		public FactQuery setFollowUpSubstring(final String followUpSubstring)
		{
			this.followUpSubstring = followUpSubstring;
			return this;
		}

		public FactQuery setDateFactBefore(final Timestamp dateFactBefore)
		{
			this.dateFactBefore = dateFactBefore;
			return this;
		}

		public FactQuery setIncludeInactive(final Boolean includeInactive)
		{
			this.includeInactive = includeInactive;
			return this;
		}

		public List<MCAdvCommissionFact> list()
		{
			final StringBuilder whereClause = new StringBuilder("TRUE");
			final List<Object> params = new ArrayList<Object>();

			mkWhereClause(whereClause, params);

			Query query = new Query(ctx, I_C_AdvCommissionFact.Table_Name, whereClause.toString(), trxName)
					.setParameters(params)
					.setOrderBy(I_C_AdvCommissionFact.COLUMNNAME_C_AdvCommissionFact_ID)
					.setClient_ID();

			if (includeInactive != null && includeInactive)
			{
				query = query.setOnlyActiveRecords(false);
			}
			else
			{
				query = query.setOnlyActiveRecords(true);
			}

			return query.list();
		}

		public BigDecimal sum(final String sqlExpression)
		{
			final StringBuilder whereClause = new StringBuilder("TRUE");
			final List<Object> params = new ArrayList<Object>();

			mkWhereClause(whereClause, params);

			Query query = new Query(ctx, I_C_AdvCommissionFact.Table_Name, whereClause.toString(), trxName)
					.setParameters(params)
					.setOnlyActiveRecords(true)
					.setClient_ID();

			if (includeInactive != null && includeInactive)
			{
				query = query.setOnlyActiveRecords(false);
			}
			else
			{
				query = query.setOnlyActiveRecords(true);
			}

			return query.sum(sqlExpression);
		}

		private void mkWhereClause(final StringBuilder whereClause, final List<Object> params)
		{
			if (tableId != null)
			{
				whereClause.append(" AND " + I_C_AdvCommissionFact.COLUMNNAME_AD_Table_ID + "=?");
				params.add(tableId);
			}
			if (recordId != null)
			{
				whereClause.append(" AND " + I_C_AdvCommissionFact.COLUMNNAME_Record_ID + "=?");
				params.add(recordId);
			}
			if (advCommissionInstanceId != null)
			{
				whereClause.append(" AND " + I_C_AdvCommissionFact.COLUMNNAME_C_AdvCommissionInstance_ID + "=?");
				params.add(advCommissionInstanceId);
			}
			if (counterRecord != null)
			{
				whereClause.append(" AND " + I_C_AdvCommissionFact.COLUMNNAME_IsCounterEntry + "=?");
				params.add(counterRecord);
			}
			if (dateDoc != null)
			{
				whereClause.append(" AND " + I_C_AdvCommissionFact.COLUMNNAME_DateDoc + " =?");
				params.add(dateDoc);
			}
			if (dateFact != null)
			{
				whereClause.append(" AND " + I_C_AdvCommissionFact.COLUMNNAME_DateFact + " =?");
				params.add(dateFact);
			}
			if (dateFactBefore != null)
			{
				whereClause.append(" AND " + I_C_AdvCommissionFact.COLUMNNAME_DateFact + " <=?");
				params.add(dateFactBefore);
			}
			if (status != null)
			{
				whereClause.append(" AND " + I_C_AdvCommissionFact.COLUMNNAME_Status);
				if (statusNot)
				{
					whereClause.append("!=?");
				}
				else
				{
					whereClause.append("=?");
				}
				params.add(status);
			}
			if (advCommissionPayrollLineId != null)
			{
				whereClause.append(" AND " + I_C_AdvCommissionFact.COLUMNNAME_C_AdvCommissionPayrollLine_ID + "=?");
				params.add(advCommissionPayrollLineId);
			}
			if (followUpSubstring != null)
			{
				whereClause.append(" AND " + I_C_AdvCommissionFact.COLUMNNAME_C_AdvComFact_IDs_FollowUp + " like '%" + followUpSubstring + "%'");
			}
			if (factType != null)
			{
				whereClause.append(" AND " + I_C_AdvCommissionFact.COLUMNNAME_FactType);
				if (factTypeNot)
				{
					whereClause.append("!=?");
				}
				else
				{
					whereClause.append("=?");
				}
				params.add(factType);
			}
			if (processed != null)
			{
				whereClause.append(" AND " + I_C_AdvCommissionFact.COLUMNNAME_Processed + "=?");
				params.add(processed);
			}
		}

	}
}
