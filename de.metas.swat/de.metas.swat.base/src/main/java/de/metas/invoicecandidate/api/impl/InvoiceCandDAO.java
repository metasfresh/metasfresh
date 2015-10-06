package de.metas.invoicecandidate.api.impl;

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


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ICurrencyConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.MSequence;
import org.compiere.model.Query;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.aggregation.model.I_C_Aggregation;
import de.metas.invoicecandidate.api.IInvoiceCandRecomputeTagger;
import de.metas.invoicecandidate.api.IInvoiceCandidateQuery;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Recompute;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.invoicecandidate.model.I_M_ProductGroup;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;

public class InvoiceCandDAO extends AbstractInvoiceCandDAO
{
	private final CLogger logger = CLogger.getCLogger(InvoiceCandDAO.class);

	@Override
	public List<I_C_Invoice_Candidate> retrieveIcForIl(final I_C_InvoiceLine invoiceLine)
	{
		final IQueryBuilder<I_C_Invoice_Line_Alloc> ilaQueryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_Invoice_Line_Alloc.class)
				.setContext(invoiceLine)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Line_Alloc.COLUMN_C_InvoiceLine_ID, invoiceLine.getC_InvoiceLine_ID());

		final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder = ilaQueryBuilder
				.andCollect(I_C_Invoice_Line_Alloc.COLUMN_C_Invoice_Candidate_ID)
				.addOnlyActiveRecordsFilter();

		icQueryBuilder.orderBy()
				.addColumn(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID);

		return icQueryBuilder
				.create()
				.list();
	}

	@Override
	public Iterator<I_C_Invoice_Candidate> retrieveForHeaderAggregationKey(final Properties ctx, final String headerAggregationKey, final String trxName)
	{
		return new Query(ctx, I_C_Invoice_Candidate.Table_Name, I_C_Invoice_Candidate.COLUMNNAME_HeaderAggregationKey + "=?", trxName)
				.setParameters(headerAggregationKey)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID)
				.setOption(Query.OPTION_IteratorBufferSize, 100) // 50 is the default, but there might be orders with more than 50 lines
				.iterate(I_C_Invoice_Candidate.class,
						true); // guaranteed=true, we can assume there won't be more than a some hundreds of invoice candidates with the same headerAggregationKey
	}

	@Override
	public List<I_C_InvoiceLine> retrieveIlForIc(final I_C_Invoice_Candidate invoiceCand)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoiceCand);
		final String trxName = InterfaceWrapperHelper.getTrxName(invoiceCand);

		final String wc = org.compiere.model.I_C_InvoiceLine.COLUMNNAME_C_InvoiceLine_ID + " IN ("
				+ "   select ila." + I_C_Invoice_Line_Alloc.COLUMNNAME_C_InvoiceLine_ID
				+ "   from " + I_C_Invoice_Line_Alloc.Table_Name + " ila "
				+ "   where ila." + I_C_Invoice_Line_Alloc.COLUMNNAME_C_Invoice_Candidate_ID + "=?"
				+ ")";

		return new Query(ctx, org.compiere.model.I_C_InvoiceLine.Table_Name, wc, trxName)
				.setParameters(invoiceCand.getC_Invoice_Candidate_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(org.compiere.model.I_C_InvoiceLine.COLUMNNAME_C_InvoiceLine_ID)
				.list(I_C_InvoiceLine.class);
	}

	@Override
	public List<I_C_Invoice_Line_Alloc> retrieveIlaForIc(final I_C_Invoice_Candidate invoiceCand)
	{
		final IQueryBuilder<I_C_Invoice_Line_Alloc> ilaQueryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_Invoice_Line_Alloc.class)
				.setContext(invoiceCand)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Line_Alloc.COLUMNNAME_C_Invoice_Candidate_ID, invoiceCand.getC_Invoice_Candidate_ID());

		ilaQueryBuilder.orderBy()
				.addColumn(I_C_Invoice_Line_Alloc.COLUMN_C_Invoice_Line_Alloc_ID);

		return ilaQueryBuilder.create().list();
	}

	@Override
	public List<I_C_Invoice_Line_Alloc> retrieveIlaForIl(final I_C_InvoiceLine il)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(il);
		final String trxName = InterfaceWrapperHelper.getTrxName(il);

		final String wc = I_C_Invoice_Line_Alloc.COLUMNNAME_C_InvoiceLine_ID + "=?";

		return new Query(ctx, I_C_Invoice_Line_Alloc.Table_Name, wc, trxName)
				.setParameters(il.getC_InvoiceLine_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_Invoice_Line_Alloc.COLUMNNAME_C_Invoice_Line_Alloc_ID)
				.list(I_C_Invoice_Line_Alloc.class);
	}

	/**
	 * Adds a record to I_C_Invoice_Candidate_Recompute.Table_Name to mark the given invoice candidate as invalid. This insertion doesn't interfere with other transactions. It's no problem if two of
	 * more concurrent transactions insert a record for the same invoice candidate.
	 *
	 * @param ic
	 */
	@Override
	public void invalidateCand(final I_C_Invoice_Candidate ic)
	{
		Check.assume(ic.getC_Invoice_Candidate_ID() > 0, "ic has an ID>0; ic={0}", ic);

		final String sql = "INSERT INTO " + I_C_Invoice_Candidate_Recompute.Table_Name + " (C_Invoice_Candidate_ID) VALUES (?)";

		final int c_Invoice_Candidate_ID = ic.getC_Invoice_Candidate_ID();

		final String trxName = InterfaceWrapperHelper.getTrxName(ic);
		final int count = DB.executeUpdateEx(sql, new Object[] { c_Invoice_Candidate_ID }, trxName);
		logger.log(Level.INFO, "Invalidated {0} C_Invoice_Candidates C_Invoice_Candidate_ID={1}", new Object[] { count, c_Invoice_Candidate_ID });
	}

	@Override
	public void invalidateCandsForProductGroup(final I_M_ProductGroup pg)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(pg);
		final Properties ctx = InterfaceWrapperHelper.getCtx(pg);
		final int referingAggregators = DB.getSQLValue(trxName,
				"SELECT count(1) "
						+ " FROM " + I_C_Invoice_Candidate_Agg.Table_Name
						+ " WHERE " + I_C_Invoice_Candidate_Agg.COLUMNNAME_M_ProductGroup_ID + "=?",
				pg.getM_ProductGroup_ID());

		if (referingAggregators > 0)
		{
			// Note: we invalidate *every* candidate, so there is no need to use the different IInvoiceCandidateHandler
			// implementations.
			invalidateAllCands(ctx, trxName);
		}
	}

	@Override
	public void invalidateCandsForHeaderAggregationKey(final Properties ctx, final String headerAggregationKey, final String trxName)
	{
		final String sql =
				"INSERT INTO " + I_C_Invoice_Candidate_Recompute.Table_Name + " (C_Invoice_Candidate_ID) " +
						"\n SELECT " + I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID +
						"\n FROM " + I_C_Invoice_Candidate.Table_Name +
						"\n WHERE " + I_C_Invoice_Candidate.COLUMNNAME_HeaderAggregationKey + "=?" +
						"\n   AND " + I_C_Invoice_Candidate.COLUMNNAME_Processed + "='N'";

		final int count = DB.executeUpdateEx(sql, new Object[] { headerAggregationKey }, trxName);
		logger.log(Level.INFO, "Invalidated {0} C_Invoice_Candidates for HeaderAggregationKey={1}", new Object[] { count, headerAggregationKey });
	}

	@Override
	public void invalidateCandsWithSameReference(final I_C_Invoice_Candidate ic)
	{
		final String sql =
				"INSERT INTO " + I_C_Invoice_Candidate_Recompute.Table_Name + " (C_Invoice_Candidate_ID) " +
						"\n SELECT " + I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID +
						"\n FROM " + I_C_Invoice_Candidate.Table_Name +
						"\n WHERE " + I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID + "=? AND " + I_C_Invoice_Candidate.COLUMNNAME_Record_ID + "=? " +
						"\n   AND " + I_C_Invoice_Candidate.COLUMNNAME_Processed + "='N'";

		final int ad_Table_ID = ic.getAD_Table_ID();
		final int record_ID = ic.getRecord_ID();
		final String trxName = InterfaceWrapperHelper.getTrxName(ic);

		final int count = DB.executeUpdateEx(sql, new Object[] { ad_Table_ID, record_ID }, trxName);
		logger.log(Level.INFO, "Invalidated {0} C_Invoice_Candidates for AD_Table_ID={1} and Record_ID={2}", new Object[] { count, ad_Table_ID, record_ID });
	}

	@Override
	public void invalidateCandsForBPartner(final I_C_BPartner bPartner)
	{
		final int count = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.setContext(bPartner)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Processed, false)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Bill_BPartner_ID, bPartner.getC_BPartner_ID())
				//
				.create()
				.insertDirectlyInto(I_C_Invoice_Candidate_Recompute.class)
				.mapColumn(I_C_Invoice_Candidate_Recompute.COLUMNNAME_C_Invoice_Candidate_ID, I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID)
				.execute();

		logger.log(Level.INFO, "Invalidated {0} C_Invoice_Candidates for bPartner={1}", new Object[] { count, bPartner });
	}

	@Override
	public void invalidateCandsForAggregationBuilder(final I_C_Aggregation aggregation)
	{
		if (aggregation == null)
		{
			return;
		}
		final int aggregationId = aggregation.getC_Aggregation_ID();
		if (aggregationId <= 0)
		{
			return;
		}

		//
		// Make sure the aggregation is about C_Invoice_Candidate table
		final int invoiceCandidateTableId = InterfaceWrapperHelper.getTableId(I_C_Invoice_Candidate.class);
		final int adTableId = aggregation.getAD_Table_ID();
		final I_C_Aggregation aggregationOld = InterfaceWrapperHelper.createOld(aggregation, I_C_Aggregation.class);
		final int adTableIdOld = aggregationOld.getAD_Table_ID();
		if (adTableId != invoiceCandidateTableId && adTableIdOld != invoiceCandidateTableId)
		{
			return;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(aggregation);
		final String trxName = InterfaceWrapperHelper.getTrxName(aggregation);

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.setContext(ctx, trxName)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Processed, false);

		//
		// Add Header/Line AggregationKeyBuilder_ID filter
		{
			icQueryBuilder.addCompositeQueryFilter()
					.setJoinOr()
					.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_HeaderAggregationKeyBuilder_ID, aggregationId)
					.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_LineAggregationKeyBuilder_ID, aggregationId);
		}

		final int count = icQueryBuilder.create()
				.insertDirectlyInto(I_C_Invoice_Candidate_Recompute.class)
				.mapColumn(I_C_Invoice_Candidate_Recompute.COLUMNNAME_C_Invoice_Candidate_ID, I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID)
				.execute();
		logger.log(Level.INFO, "Invalidated {0} C_Invoice_Candidates for aggregation={1}", new Object[] { count, aggregation });
	}

	@Override
	public void invalidateCandsForBPartnerInvoiceRule(final I_C_BPartner bPartner)
	{
		final String sql =
				"INSERT INTO " + I_C_Invoice_Candidate_Recompute.Table_Name + " (C_Invoice_Candidate_ID) " +
						"\n SELECT " + I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID +
						"\n FROM " + I_C_Invoice_Candidate.Table_Name +
						"\n WHERE " + I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID + "=? " +
						"\n   AND COALESCE(" + I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule_Override + "," + I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule + ")" +
						" = '" + X_C_Invoice_Candidate.INVOICERULE_EFFECTIVE_KundenintervallNachLieferung + "'" +
						"\n   AND " + I_C_Invoice_Candidate.COLUMNNAME_Processed + "='N'";

		final String trxName = InterfaceWrapperHelper.getTrxName(bPartner);

		final int count = DB.executeUpdateEx(sql, new Object[] { bPartner.getC_BPartner_ID() }, trxName);
		logger.log(Level.INFO, "Invalidated {0} C_Invoice_Candidates for bPartner={1}", new Object[] { count, bPartner });
	}

	@Override
	public void invalidateAllCands(final Properties ctx, final String trxName)
	{
		final String sql = "INSERT INTO " + I_C_Invoice_Candidate_Recompute.Table_Name + " (C_Invoice_Candidate_ID) " +
				"\n SELECT " + I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID +
				"\n FROM " + I_C_Invoice_Candidate.Table_Name +
				"\n WHERE " + I_C_Invoice_Candidate.COLUMNNAME_Processed + "='N'";

		final int updCnt = DB.executeUpdate(sql, trxName);
		logger.fine("Invalidated " + updCnt + " records");
	}

	protected void invalidateCandsForSelection(final int adPInstanceId, final String trxName)
	{
		final String sql = "INSERT INTO " + I_C_Invoice_Candidate_Recompute.Table_Name + " (C_Invoice_Candidate_ID) "
				+ "\n SELECT " + I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID
				+ "\n FROM " + I_C_Invoice_Candidate.Table_Name
				+ "\n WHERE " + I_C_Invoice_Candidate.COLUMNNAME_Processed + "='N'"
				+ "\n AND EXISTS (SELECT 1 FROM T_Selection s WHERE s.AD_PInstance_ID = ? AND s.T_Selection_ID = C_Invoice_Candidate_ID)";

		final int count = DB.executeUpdateEx(sql, new Object[] { adPInstanceId }, trxName);
		logger.log(Level.INFO, "Invalidated {0} C_Invoice_Candidates for AD_PInstance_ID={1}", new Object[] { count, adPInstanceId });
	}

	@Cached
	@Override
	public List<I_C_Invoice_Candidate> fetchInvoiceCandidates(@CacheCtx final Properties ctx, final String tableName, final int recordId, @CacheTrx final String trxName)
	{
		Check.assume(!Check.isEmpty(tableName), "Param 'tableName' is not empty");
		Check.assume(recordId > 0, "Param 'recordId' is > 0");

		final String whereClause =
				I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID + "=? AND " + I_C_Invoice_Candidate.COLUMNNAME_Record_ID + "=?";

		return new Query(ctx, I_C_Invoice_Candidate.Table_Name, whereClause, trxName)
				.setParameters(Services.get(IADTableDAO.class).retrieveTableId(tableName), recordId)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID)
				.list(I_C_Invoice_Candidate.class);
	}

	@Override
	public Iterator<I_C_Invoice_Candidate> fetchInvalidInvoiceCandidates(final Properties ctx, final int AD_PInstance_ID, final String trxName)
	{
		final String whereClause = I_C_Invoice_Candidate.COLUMNNAME_AD_Client_ID + "=?"
				+ " AND EXISTS ("
				+ "   select 1 from " + I_C_Invoice_Candidate_Recompute.Table_Name + " icr "
				+ "   where icr." + I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID + "=" + I_C_Invoice_Candidate.Table_Name
				+ "." + I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID
				+ "      and icr.AD_Pinstance_ID=?"
				+ ")";

		// we need to return the not-manual invoice candidates first, because their NetAmtToInvoice is required when we evaluate the manual candidates
		final String orderBy = I_C_Invoice_Candidate.COLUMNNAME_IsManual + "," + I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID;

		// 03968: performance tweak that is necessary when updating around 70.000 candidates at once:
		// don't use a 'guaranteed' iterator; *we don't need it* and selecting/ordering joining between
		// C_Invoice_Candidate and T_Query_Selection is a performance-killer (at least on a not so fast system)
		return new Query(ctx, I_C_Invoice_Candidate.Table_Name, whereClause.toString(), trxName)
				.setParameters(
						Env.getAD_Client_ID(ctx),
						AD_PInstance_ID)
				.setOnlyActiveRecords(true)
				.setOrderBy(orderBy)
				.setOption(Query.OPTION_IteratorBufferSize, 500)
				.iterate(
						I_C_Invoice_Candidate.class,
						false); // guaranteed = false
	}

	@Override
	protected int nextRecomputeId()
	{
		return MSequence.getNextID(Env.CTXVALUE_AD_Client_ID_System, I_AD_PInstance.Table_Name);
	}

	@Override
	public final IInvoiceCandRecomputeTagger tagToRecompute()
	{
		return new InvoiceCandRecomputeTagger(this);
	}

	protected void tagAllToRecompute(final InvoiceCandRecomputeTagger tagRequest)
	{
		Check.assumeNotNull(tagRequest, "tagRequest not null");

		final int adPInstanceId = tagRequest.getRecomputeTag();
		final String trxName = tagRequest.getTrxName();

		final List<Object> sqlParams = new ArrayList<>();
		final StringBuilder sqlUpdate = new StringBuilder(" UPDATE " + I_C_Invoice_Candidate_Recompute.Table_Name)
				.append(" SET ")
				.append(I_C_Invoice_Candidate_Recompute.COLUMNNAME_AD_PInstance_ID + "=?");
		sqlParams.add(adPInstanceId);

		sqlUpdate.append(" WHERE 1=1 ");

		//
		// Append not locked where clause
		final LockedByOrNotLockedAtAllFilter lockFilter = new LockedByOrNotLockedAtAllFilter(tagRequest.getLockedBy());
		sqlUpdate.append(lockFilter.getSql());
		sqlParams.addAll(lockFilter.getSqlParams(tagRequest.getCtx()));

		final int result = DB.executeUpdateEx(sqlUpdate.toString(), sqlParams.toArray(), trxName);

		logger.info("Marked " + result + " entries for AD_Pinstance_ID=" + adPInstanceId);
	}

	protected void tagToRecomputeChunk(final InvoiceCandRecomputeTagger tagRequest, final Iterator<I_C_Invoice_Candidate> candidates)
	{
		Check.assumeNotNull(tagRequest, "tagRequest not null");
		Check.assumeNotNull(candidates, "candidates not null");

		// If there are no candidates to tag => do nothing
		if (!candidates.hasNext())
		{
			return; // nothing to do
		}

		final int adPInstanceId = tagRequest.getRecomputeTag();

		final List<Object> sqlParams = new ArrayList<>();
		final StringBuilder sqlUpdate = new StringBuilder(" UPDATE " + I_C_Invoice_Candidate_Recompute.Table_Name)
				.append(" SET ")
				.append(I_C_Invoice_Candidate_Recompute.COLUMNNAME_AD_PInstance_ID + "=?");
		sqlParams.add(adPInstanceId);

		//
		// Append C_Invoice_Candidate_ID IN (candidates ids...)
		sqlUpdate.append(" WHERE " + I_C_Invoice_Candidate_Recompute.COLUMNNAME_C_Invoice_Candidate_ID + " IN (");
		boolean first = true;
		int counter = 0;

		while (candidates.hasNext())
		{
			if (first)
			{
				first = false;
			}
			else
			{
				sqlUpdate.append(", ");
			}

			final int invoiceCandidateId = candidates.next().getC_Invoice_Candidate_ID();
			sqlUpdate.append("?");
			sqlParams.add(invoiceCandidateId);

			counter++;
			if (counter == 50)
			{
				break;
			}
		}
		sqlUpdate.append(")");

		//
		// Append not locked where clause
		final LockedByOrNotLockedAtAllFilter lockFilter = new LockedByOrNotLockedAtAllFilter(tagRequest.getLockedBy());
		sqlUpdate.append(lockFilter.getSql());
		sqlParams.addAll(lockFilter.getSqlParams(tagRequest.getCtx()));

		final String trxName = tagRequest.getTrxName();
		final int updated = DB.executeUpdateEx(sqlUpdate.toString(), sqlParams.toArray(), trxName);
		logger.log(Level.FINE, "Updated {0} {1} records", new Object[] { updated, I_C_Invoice_Candidate_Recompute.Table_Name });
	}

	/**
	 * Deletes those records from table I_C_Invoice_Candidate_Recompute.Table_Name that were formerly tagged with the given AD_Pinstance_ID.
	 *
	 * @param adPInstance
	 * @param trxName Note: MPInstance doesn't support transactions and thus doesn't store the trxName. Therefore we can't get it from the given <code>adPInstance</code>.
	 */
	@Override
	public void deleteRecomputeMarkers(final int AD_PInstance_ID, final String trxName)
	{
		final String sql = "DELETE FROM " + I_C_Invoice_Candidate_Recompute.Table_Name + " WHERE AD_Pinstance_ID=?";

		final PreparedStatement pstmt = DB.prepareStatement(sql, trxName);
		try
		{
			pstmt.setInt(1, AD_PInstance_ID);
			final int result = pstmt.executeUpdate();
			logger.fine("Deleted " + result + " " + I_C_Invoice_Candidate_Recompute.Table_Name + " entries for AD_Pinstance_ID=" + AD_PInstance_ID);
		}
		catch (final SQLException e)
		{
			throw new DBException(e);
		}
		finally
		{
			DB.close(pstmt);
		}
	}

	@Override
	public List<I_C_Invoice_Candidate> retrieveForBillPartner(final I_C_BPartner bPartner)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(bPartner);
		final String trxName = InterfaceWrapperHelper.getTrxName(bPartner);

		final String wc = I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID + "=?";
		return new Query(ctx, I_C_Invoice_Candidate.Table_Name, wc, trxName)
				.setParameters(bPartner.getC_BPartner_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID)
				.list(I_C_Invoice_Candidate.class);
	}

	@Override
	public List<I_C_Invoice_Candidate> retrieveForInvoiceSchedule(final I_C_InvoiceSchedule invoiceSchedule)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoiceSchedule);
		final String trxName = InterfaceWrapperHelper.getTrxName(invoiceSchedule);

		final String wc = I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID
				+ " IN ("
				+ "   select bp." + I_C_BPartner.COLUMNNAME_C_BPartner_ID
				+ "   from " + I_C_BPartner.Table_Name + " bp "
				+ "   where bp." + I_C_BPartner.COLUMNNAME_C_InvoiceSchedule_ID + "=?"
				+ " ) "
				+ " AND "
				+ " COALESCE ("
				+ I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule_Override + ", " + I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule
				+ ") ='" + X_C_Invoice_Candidate.INVOICERULE_KundenintervallNachLieferung + "'";

		return new Query(ctx, I_C_Invoice_Candidate.Table_Name, wc, trxName)
				.setParameters(invoiceSchedule.getC_InvoiceSchedule_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID)
				.list(I_C_Invoice_Candidate.class);
	}

	@Override
	public final <ValueType> void updateColumnForSelection(
			final String invoiceCandidateColumnName,
			final ValueType value,
			final boolean updateOnlyIfNull,
			final int selectionId,
			final String trxName)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		//
		// Create the selection which we will need to update
		final Properties ctx = Env.getCtx();
		final IQueryBuilder<I_C_Invoice_Candidate> selectionQueryBuilder = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.setContext(ctx, trxName)
				.setOnlySelection(selectionId)
				.addNotEqualsFilter(invoiceCandidateColumnName, value) // skip those which have our value set
		;
		if (updateOnlyIfNull)
		{
			selectionQueryBuilder.addEqualsFilter(invoiceCandidateColumnName, null);
		}
		final int selectionToUpdateId = selectionQueryBuilder.create().createSelection();
		if (selectionToUpdateId <= 0)
		{
			// nothing to update
			return;
		}

		//
		// Update our new selection
		queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.setContext(ctx, trxName)
				.setOnlySelection(selectionToUpdateId)
				.create()
				.updateDirectly()
				.addSetColumnValue(invoiceCandidateColumnName, value)
				.execute();

		//
		// Invalidate the candidates which we updated
		invalidateCandsForSelection(selectionToUpdateId, trxName);
	}

	@Override
	public final void updateDateInvoiced(final Timestamp dateInvoiced, final int ADPinstance_ID, final String trxName)
	{
		updateColumnForSelection(
				I_C_Invoice_Candidate.COLUMNNAME_DateInvoiced, // invoiceCandidateColumnName
				dateInvoiced, // value
				false, // updateOnlyIfNull
				ADPinstance_ID, // selectionId
				trxName // trxName
		);
	}

	@Override
	public final void updateDateAcct(Timestamp dateAcct, int ADPinstance_ID, String trxName)
	{
		updateColumnForSelection(
				I_C_Invoice_Candidate.COLUMNNAME_DateAcct, // invoiceCandidateColumnName
				dateAcct, // value
				false, // updateOnlyIfNull
				ADPinstance_ID, // selectionId
				trxName // trxName
		);
	}

	@Override
	public final void updatePOReference(final String poReference, final int ADPinstance_ID, final String trxName)
	{
		updateColumnForSelection(
				I_C_Invoice_Candidate.COLUMNNAME_POReference, // invoiceCandidateColumnName
				poReference, // value
				false, // updateOnlyIfNull
				ADPinstance_ID, // selectionId
				trxName // trxName
		);
	}

	@Override
	// @Cached
	public BigDecimal retrieveInvoicableAmount(final @CacheCtx Properties ctx,
			final IInvoiceCandidateQuery query,
			final int targetCurrencyId,
			final int adClientId,
			final int adOrgId,
			final String amountColumnName,
			@CacheTrx final String trxName)
	{
		final StringBuilder whereClause = new StringBuilder("1=1");
		final List<Object> params = new ArrayList<Object>();

		// Bill BPartner
		if (query.getBill_BPartner_ID() > 0)
		{
			whereClause.append(" AND ").append(I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID).append("=?");
			params.add(query.getBill_BPartner_ID());
		}

		// DateToInvoice
		if (query.getDateToInvoice() != null)
		{
			whereClause.append(" AND ")
					.append(" COALESCE(" + I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice_Override + "," + I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice + ")").append("<=?");
			params.add(TimeUtil.getDay(query.getDateToInvoice()));
		}

		// Filter HeaderAggregationKey
		if (query.getHeaderAggregationKey() != null)
		{
			whereClause.append(" AND ").append(I_C_Invoice_Candidate.COLUMNNAME_HeaderAggregationKey).append("=?");
			params.add(query.getHeaderAggregationKey());
		}

		// Exclude C_Invoice_Candidate
		if (query.getExcludeC_Invoice_Candidate_ID() > 0)
		{
			whereClause.append(" AND ").append(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID).append("<>?");
			params.add(query.getExcludeC_Invoice_Candidate_ID());
		}
		if (query.getMaxManualC_Invoice_Candidate_ID() > 0)
		{
			// either the candidate is *not* manual, or its ID is less or equal than MaxManualC_Invoice_Candidate_ID
			whereClause.append(" AND (")
					.append(I_C_Invoice_Candidate.COLUMNNAME_IsManual + "=? OR ")
					.append(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID).append("<=?");
			whereClause.append(")");
			params.add(false);
			params.add(query.getMaxManualC_Invoice_Candidate_ID());
		}

		// Processed
		if (query.getProcessed() != null)
		{
			whereClause.append(" AND ").append(I_C_Invoice_Candidate.COLUMNNAME_Processed).append("=?");
			params.add(query.getProcessed().booleanValue());
		}

		// Exclude those with errors
		whereClause.append(" AND ").append(I_C_Invoice_Candidate.COLUMNNAME_IsError).append("=?");
		params.add(false);

		// Filter by AD_Client_ID
		whereClause.append(" AND ").append(I_C_Invoice_Candidate.COLUMNNAME_AD_Client_ID).append("=?");
		params.add(adClientId);

		final String sql =
				"SELECT "
						+ I_C_Invoice_Candidate.COLUMNNAME_C_Currency_ID
						+ ", " + I_C_Invoice_Candidate.COLUMNNAME_C_ConversionType_ID
						+ ", SUM(" + amountColumnName + ") as NetAmt"
						+ " FROM " + I_C_Invoice_Candidate.Table_Name
						+ " WHERE " + whereClause
						+ " GROUP BY "
						+ I_C_Invoice_Candidate.COLUMNNAME_C_Currency_ID + ","
						+ I_C_Invoice_Candidate.COLUMNNAME_C_ConversionType_ID;

		final Map<Integer, Map<Integer, BigDecimal>> currencyId2conversion2Amt = new HashMap<Integer, Map<Integer, BigDecimal>>();

		final PreparedStatement pstmt = DB.prepareStatement(sql, trxName);
		ResultSet rs = null;
		try
		{
			DB.setParameters(pstmt, params);

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final BigDecimal netAmt = rs.getBigDecimal("NetAmt");
				if (rs.wasNull())
				{
					continue;
				}
				final int currencyId = rs.getInt(I_C_Invoice_Candidate.COLUMNNAME_C_Currency_ID);
				final int conversionTypeId = rs.getInt(I_C_Invoice_Candidate.COLUMNNAME_C_ConversionType_ID);
				Map<Integer, BigDecimal> conversion2Amt = currencyId2conversion2Amt.get(currencyId);
				if (conversion2Amt == null)
				{
					conversion2Amt = new HashMap<Integer, BigDecimal>();
					currencyId2conversion2Amt.put(currencyId, conversion2Amt);
				}
				conversion2Amt.put(conversionTypeId, netAmt);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		// Conversion date to be used on currency conversion
		final Timestamp dateConv = SystemTime.asTimestamp();

		BigDecimal result = BigDecimal.ZERO;
		for (final Integer currencyId : currencyId2conversion2Amt.keySet())
		{
			final Map<Integer, BigDecimal> conversion2Amt = currencyId2conversion2Amt.get(currencyId);

			for (final Integer conversionTypeId : conversion2Amt.keySet())
			{
				final BigDecimal amt = conversion2Amt.get(conversionTypeId);
				final BigDecimal amtConverted = Services.get(ICurrencyConversionBL.class).convert(ctx,
						amt,
						currencyId, // CurFrom_ID,
						targetCurrencyId, // CurTo_ID,
						dateConv, // ConvDate,
						conversionTypeId,
						adClientId, adOrgId);
				result = result.add(amtConverted);
			}
		}

		return result;
	}

	@Override
	public List<I_M_InOutLine> retrieveInOutLines(final Properties ctx, final int C_OrderLine_ID, final String trxName)
	{
		final String whereClause = I_M_InOutLine.COLUMNNAME_C_OrderLine_ID + "=?";
		return new Query(ctx, I_M_InOutLine.Table_Name, whereClause, trxName)
				.setParameters(C_OrderLine_ID)
				.list(I_M_InOutLine.class);
	}

	@Override
	protected final void saveErrorToDB(final I_C_Invoice_Candidate ic)
	{
		final String sql = "UPDATE " + I_C_Invoice_Candidate.Table_Name + " SET "
				+ " " + I_C_Invoice_Candidate.COLUMNNAME_SchedulerResult + "=?"
				+ "," + I_C_Invoice_Candidate.COLUMNNAME_IsError + "=?"
				+ "," + I_C_Invoice_Candidate.COLUMNNAME_ErrorMsg + "=?"
				+ "," + I_C_Invoice_Candidate.COLUMNNAME_AD_Note_ID + "=?"
				+ " WHERE " + I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID + "=?";
		final Object[] sqlParams = new Object[] {
				ic.getSchedulerResult()
				, ic.isError()
				, ic.getErrorMsg()
				, ic.getAD_Note_ID()
				, ic.getC_Invoice_Candidate_ID()
		};

		final boolean ignoreError = false;
		final String trxName = InterfaceWrapperHelper.getTrxName(ic);
		DB.executeUpdate(sql, sqlParams, ignoreError, trxName);
	}

	@Override
	public void invalidateCands(final Collection<I_C_Invoice_Candidate> ics, final String trxName)
	{
		if (ics == null || ics.isEmpty())
		{
			return; // nothing to do for us
		}

		final Set<Integer> icIds = new HashSet<>();
		for (final I_C_Invoice_Candidate ic : ics)
		{
			if (ics == null)
			{
				continue;
			}
			icIds.add(ic.getC_Invoice_Candidate_ID());
		}

		final List<Object> sqlParams = new ArrayList<Object>();
		final String sqlInWhereClause = DB.buildSqlList(icIds, sqlParams); // creates the string and fills the sqlParams list

		final String sql = "INSERT INTO " + I_C_Invoice_Candidate_Recompute.Table_Name + " (" + I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID + ") "
				+ " SELECT " + I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID
				+ " FROM " + I_C_Invoice_Candidate.Table_Name
				+ " WHERE "
				// Only our shipment schedule Ids
				+ I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID + " IN " + sqlInWhereClause
				// Only those which were not already added (technically not necessary, but shall reduce unnecessary bloat)
				+ "   AND NOT EXISTS (select 1 from " + I_C_Invoice_Candidate_Recompute.Table_Name + " e where e.AD_PInstance_ID is NULL and e.C_Invoice_Candidate_ID="
				+ I_C_Invoice_Candidate.Table_Name + "." + I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID + ")";

		final int count = DB.executeUpdateEx(sql, sqlParams.toArray(), trxName);
		logger.log(Level.INFO, "Invalidated {0} shipment schedules for M_ShipmentSchedule_IDs={1}", new Object[] { count, icIds });
	}

	@Override
	public boolean isToRecompute(final I_C_Invoice_Candidate ic)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(ic);

		// note: we don't care about how many recompute records we have, so we just get one row (it there is any)
		final String sql = "SELECT 1 FROM " + I_C_Invoice_Candidate_Recompute.Table_Name + " r WHERE r.C_Invoice_Candidate_ID=? LIMIT 1";

		final int result = DB.getSQLValueEx(trxName, sql, ic.getC_Invoice_Candidate_ID());
		return result == 1;
	}
}
