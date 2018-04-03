package de.metas.contracts.impl;

import static org.adempiere.model.InterfaceWrapperHelper.getCtx;
import static org.adempiere.model.InterfaceWrapperHelper.getTrxName;

/*
 * #%L
 * de.metas.contracts
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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Data;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.contracts.model.I_C_Flatrate_Matching;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.I_C_Invoice_Clearing_Alloc;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_DataEntry;
import de.metas.document.engine.IDocument;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.util.stream.StreamUtils;
import lombok.NonNull;

public class FlatrateDAO implements IFlatrateDAO
{

	private static final String MSP_DATA_ENTRY_ERROR_INVOICE_CAND_PROCESSED_3P = "DataEntry_Error_InvoiceCand_Processed";
	private static final Logger logger = LogManager.getLogger(FlatrateDAO.class);

	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public List<I_C_Flatrate_Term> retrieveTerms(final I_C_Invoice_Candidate ic)
	{
		final Properties ctx = getCtx(ic);
		final String trxName = getTrxName(ic);

		final int bill_BPartner_ID = ic.getBill_BPartner_ID();
		final Timestamp dateOrdered = ic.getDateOrdered();
		final int m_Product_Category_ID = ic.getM_Product().getM_Product_Category_ID();
		final int m_Product_ID = ic.getM_Product_ID();
		final int c_Charge_ID = ic.getC_Charge_ID();

		return retrieveTerms(ctx, bill_BPartner_ID, dateOrdered, m_Product_Category_ID, m_Product_ID, c_Charge_ID, trxName);
	}

	@Override
	@Cached
	public List<I_C_Flatrate_Term> retrieveTerms(
			final @CacheCtx Properties ctx,
			final int bill_BPartner_ID,
			final Timestamp dateOrdered,
			final int m_Product_Category_ID,
			final int m_Product_ID,
			final int c_Charge_ID,
			final @CacheTrx String trxName)
	{
		final IQueryBuilder<I_C_Flatrate_Matching> matchingQueryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_Flatrate_Matching.class, ctx, trxName)
				.addOnlyActiveRecordsFilter();
		if (m_Product_Category_ID > 0)
		{
			matchingQueryBuilder.addInArrayOrAllFilter(I_C_Flatrate_Matching.COLUMNNAME_M_Product_Category_Matching_ID, null, m_Product_Category_ID);
		}
		if (m_Product_ID > 0)
		{
			matchingQueryBuilder.addInArrayOrAllFilter(I_C_Flatrate_Matching.COLUMNNAME_M_Product_ID, null, m_Product_ID);
		}
		if (c_Charge_ID > 0)
		{
			matchingQueryBuilder.addInArrayOrAllFilter(I_C_Flatrate_Matching.COLUMNNAME_C_Charge_ID, null, c_Charge_ID);
		}

		final IQuery<I_C_Flatrate_Conditions> fcQuery = matchingQueryBuilder
				.andCollect(I_C_Flatrate_Conditions.COLUMN_C_Flatrate_Conditions_ID, I_C_Flatrate_Conditions.class)
				.addEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_DocStatus, IDocument.STATUS_Completed)
				.addNotEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_Type_Conditions, X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription)
				.addNotEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_Type_Conditions, X_C_Flatrate_Conditions.TYPE_CONDITIONS_HoldingFee)
				.create();

		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Flatrate_Term.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, bill_BPartner_ID)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, IDocument.STATUS_Completed)
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_StartDate, Operator.LESS_OR_EQUAL, dateOrdered)
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, Operator.GREATER_OR_EQUAL, dateOrdered)
				.addInSubQueryFilter(I_C_Flatrate_Term.COLUMN_C_Flatrate_Conditions_ID, I_C_Flatrate_Conditions.COLUMN_C_Flatrate_Conditions_ID, fcQuery)
				.orderBy().addColumn(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID).endOrderBy()
				.create()
				.list();
	}

	@Override
	public List<I_C_Flatrate_Matching> retrieveFlatrateMatchings(final I_C_Flatrate_Conditions conditions)
	{
		final Properties ctx = getCtx(conditions);
		final String trxName = getTrxName(conditions);
		final int flatrateConditionsId = conditions.getC_Flatrate_Conditions_ID();

		return retrieveFlatrateMatchings(ctx, flatrateConditionsId, trxName);
	}

	@Cached
	/* package */List<I_C_Flatrate_Matching> retrieveFlatrateMatchings(
			@CacheCtx final Properties ctx,
			final int flatrateConditionsId,
			final String trxName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Flatrate_Matching.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Flatrate_Matching.COLUMNNAME_C_Flatrate_Conditions_ID, flatrateConditionsId)
				.orderBy()
				.addColumn(I_C_Flatrate_Matching.COLUMN_SeqNo)
				.addColumn(I_C_Flatrate_Matching.COLUMN_C_Flatrate_Matching_ID)
				.endOrderBy()
				.create()
				.list(I_C_Flatrate_Matching.class);
	}

	@Override
	public List<I_C_Invoice_Clearing_Alloc> retrieveClearingAllocs(final I_C_Flatrate_DataEntry dataEntry)
	{
		final Properties ctx = getCtx(dataEntry);
		final String trxName = getTrxName(dataEntry);

		final String wc = I_C_Invoice_Clearing_Alloc.COLUMNNAME_C_Flatrate_DataEntry_ID + "=?";

		return new Query(ctx, I_C_Invoice_Clearing_Alloc.Table_Name, wc, trxName)
				.setParameters(dataEntry.getC_Flatrate_DataEntry_ID())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setOrderBy(I_C_Invoice_Clearing_Alloc.COLUMNNAME_C_Invoice_Clearing_Alloc_ID)
				.list(I_C_Invoice_Clearing_Alloc.class);
	}

	@Override
	public List<I_C_Invoice_Clearing_Alloc> retrieveAllClearingAllocs(final I_C_Invoice_Candidate invoiceCand)
	{
		final boolean retrieveAll = true;
		return retrieveClearingAllocs(invoiceCand, retrieveAll);
	}

	@Override
	public List<I_C_Invoice_Clearing_Alloc> retrieveClearingAllocs(final I_C_Invoice_Candidate invoiceCand)
	{
		final boolean retrieveAll = false;
		return retrieveClearingAllocs(invoiceCand, retrieveAll);
	}

	private List<I_C_Invoice_Clearing_Alloc> retrieveClearingAllocs(final I_C_Invoice_Candidate invoiceCand, final boolean retrieveAll)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ICompositeQueryFilter<I_C_Invoice_Clearing_Alloc> orFilter = queryBL.createCompositeQueryFilter(I_C_Invoice_Clearing_Alloc.class)
				.setJoinOr()
				.addEqualsFilter(I_C_Invoice_Clearing_Alloc.COLUMNNAME_C_Invoice_Cand_ToClear_ID, invoiceCand.getC_Invoice_Candidate_ID())
				.addEqualsFilter(I_C_Invoice_Clearing_Alloc.COLUMNNAME_C_Invoice_Candidate_ID, invoiceCand.getC_Invoice_Candidate_ID());

		final IQueryBuilder<I_C_Invoice_Clearing_Alloc> queryBuilder = queryBL.createQueryBuilder(I_C_Invoice_Clearing_Alloc.class, invoiceCand);
		if (!retrieveAll)
		{
			queryBuilder.addOnlyActiveRecordsFilter();
		}

		return queryBuilder
				.filter(orFilter)
				.orderBy().addColumn(I_C_Invoice_Clearing_Alloc.COLUMNNAME_C_Invoice_Clearing_Alloc_ID).endOrderBy()
				.create()
				.list();
	}

	@Override
	public List<I_C_Invoice_Clearing_Alloc> retrieveClearingAllocs(final I_C_Flatrate_Term term)
	{
		final Properties ctx = getCtx(term);
		final String trxName = getTrxName(term);

		final String wc = I_C_Invoice_Clearing_Alloc.COLUMNNAME_C_Flatrate_Term_ID + "=?";

		return new Query(ctx, I_C_Invoice_Clearing_Alloc.Table_Name, wc, trxName)
				.setParameters(term.getC_Flatrate_Term_ID())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setOrderBy(I_C_Invoice_Clearing_Alloc.COLUMNNAME_C_Invoice_Clearing_Alloc_ID)
				.list(I_C_Invoice_Clearing_Alloc.class);
	}

	@Override
	public I_C_Invoice_Clearing_Alloc retrieveClearingAllocOrNull(
			final I_C_Invoice_Candidate invoiceCandToClear,
			final I_C_Flatrate_DataEntry dataEntry)
	{
		final Properties ctx = getCtx(invoiceCandToClear);
		final String trxName = getTrxName(invoiceCandToClear);

		final String wc = I_C_Invoice_Clearing_Alloc.COLUMNNAME_C_Invoice_Cand_ToClear_ID + "=? AND "
				+ " COALESCE (" + I_C_Invoice_Clearing_Alloc.COLUMNNAME_C_Flatrate_DataEntry_ID + ",0)=?";

		return new Query(ctx, I_C_Invoice_Clearing_Alloc.Table_Name, wc, trxName)
				.setParameters(
						invoiceCandToClear.getC_Invoice_Candidate_ID(),
						dataEntry == null ? 0 : dataEntry.getC_Flatrate_DataEntry_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.firstOnly(I_C_Invoice_Clearing_Alloc.class);
	}

	@Override
	public List<I_C_Flatrate_DataEntry> retrieveDataEntries(
			final I_C_Flatrate_Conditions fc,
			final Timestamp dateOrdered,
			final String dataEntryType,
			final I_C_UOM uom,
			final boolean onlyNonSim)
	{
		return retrieveEntries(fc, null, dateOrdered, dataEntryType, uom, onlyNonSim);
	}

	@Override
	public List<I_C_Flatrate_DataEntry> retrieveEntries(
			final I_C_Flatrate_Conditions fc,
			final I_C_Flatrate_Term term,
			final Timestamp date,
			final String dataEntryType,
			final I_C_UOM uom,
			final boolean onlyNonSim)
	{
		final Properties ctx;
		final String trxName;

		// retrieve the flatrate terms that reference 'fc'
		final StringBuilder wc = new StringBuilder();
		final List<Object> params = new ArrayList<>();
		if (term == null)
		{
			ctx = getCtx(fc);
			trxName = getTrxName(fc);

			Check.assume(fc != null, "term is null, so fc is not null");
			wc.append(
					// entry's term must reference the given fc
					I_C_Flatrate_DataEntry.COLUMNNAME_C_Flatrate_Term_ID + " IN ("
							+ "     select t." + I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID
							+ "     from " + I_C_Flatrate_Term.Table_Name + " t "
							+ "     where t." + I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID + "=? "
							+ ") AND ");
			params.add(fc.getC_Flatrate_Conditions_ID());
		}
		else
		{
			ctx = getCtx(term);
			trxName = getTrxName(term);

			wc.append(
					// entry's term must reference the given fc
					I_C_Flatrate_DataEntry.COLUMNNAME_C_Flatrate_Term_ID + "=? AND ");
			params.add(term.getC_Flatrate_Term_ID());
		}
		if (date != null)
		{
			final Timestamp day = TimeUtil.truncToDay(date);
			wc.append(
					// 'date' must lie within entry's period
					I_C_Flatrate_DataEntry.COLUMNNAME_C_Period_ID + " IN ( "
							+ "     select p." + I_C_Period.COLUMNNAME_C_Period_ID
							+ "     from " + I_C_Period.Table_Name + " p "
							+ "     where p." + I_C_Period.COLUMNNAME_StartDate + "<=? AND "
							+ "           p." + I_C_Period.COLUMNNAME_EndDate + ">=? "
							+ ") AND ");
			params.add(day);
			params.add(day);
		}
		wc.append(
				// entry must have the given 'dataEntryType'
				I_C_Flatrate_DataEntry.COLUMNNAME_Type + "=? ");
		params.add(dataEntryType);

		if (uom != null)
		{
			wc.append(" AND " + I_C_Flatrate_DataEntry.COLUMNNAME_C_UOM_ID + "=? ");
			params.add(uom.getC_UOM_ID());
		}

		// Return the entries in the order of their UOM.
		// That way, concurrent processes with always process in the same order and thus avoid a deadlock
		// Note: Just ordering by C_Flatrate_DataEntry_ID might be enough, but I'm not 100% sure here.
		final String orderBy = I_C_Flatrate_DataEntry.COLUMNNAME_C_UOM_ID + "," + I_C_Flatrate_DataEntry.COLUMNNAME_C_Flatrate_DataEntry_ID;

		final List<I_C_Flatrate_DataEntry> resultAll = new Query(ctx, I_C_Flatrate_DataEntry.Table_Name, wc.toString(), trxName)
				.setParameters(params)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(orderBy)
				.list(I_C_Flatrate_DataEntry.class);
		if (!onlyNonSim)
		{
			return resultAll;
		}

		final List<I_C_Flatrate_DataEntry> resultNonSim = new ArrayList<>();
		for (final I_C_Flatrate_DataEntry de : resultAll)
		{
			if (de.isSimulation())
			{
				continue;
			}
			resultNonSim.add(de);
		}

		return resultNonSim;
	}

	@Override
	public List<I_C_Flatrate_DataEntry> retrieveDataEntries(
			final I_C_Flatrate_Term flatrateTerm,
			final Timestamp date,
			final String dataEntryType,
			final boolean onlyNonSim)
	{
		final I_C_Flatrate_Conditions fc = null;
		final I_C_UOM uom = null;

		return retrieveEntries(fc, flatrateTerm, date, dataEntryType, uom, onlyNonSim);
	}

	@Override
	public List<I_C_Flatrate_DataEntry> retrieveDataEntries(
			final I_C_Flatrate_Term term,
			final String dataEntryType,
			final I_C_UOM uom)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_C_Flatrate_DataEntry> queryBuilder = queryBL.createQueryBuilder(I_C_Flatrate_DataEntry.class, term)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_DataEntry.COLUMNNAME_C_Flatrate_Term_ID, term.getC_Flatrate_Term_ID())
				.addOnlyContextClient();

		if (uom != null)
		{
			queryBuilder.addEqualsFilter(I_C_Flatrate_DataEntry.COLUMNNAME_C_UOM_ID, uom.getC_UOM_ID());
		}

		if (!Check.isEmpty(dataEntryType, true))
		{
			queryBuilder.addEqualsFilter(I_C_Flatrate_DataEntry.COLUMNNAME_Type, dataEntryType);
		}

		return queryBuilder
				.orderBy().addColumn(I_C_Flatrate_DataEntry.COLUMNNAME_C_Flatrate_DataEntry_ID).endOrderBy()
				.create()
				.list();
	}

	@Override
	public List<I_C_Flatrate_Term> retrieveTerms(final I_C_Flatrate_Conditions fc)
	{
		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class, fc)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID, fc.getC_Flatrate_Conditions_ID())
				.addOnlyActiveRecordsFilter()
				.orderBy()
				.addColumn(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID)
				.endOrderBy()
				.create()
				.setClient_ID()
				.list(I_C_Flatrate_Term.class);

	}

	@Override
	public List<I_C_Flatrate_Term> retrieveTerms(
			final I_C_BPartner bPartner,
			final I_C_Flatrate_Conditions flatrateConditions)
	{
		final IQueryBuilder<I_C_Flatrate_Term> queryBuilder = queryBL.createQueryBuilder(I_C_Flatrate_Term.class, bPartner);

		queryBuilder
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_Bill_BPartner_ID, bPartner.getC_BPartner_ID())
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_C_Flatrate_Conditions_ID, flatrateConditions.getC_Flatrate_Conditions_ID())
				.filterByClientId();

		final IQueryOrderBy orderBy = queryBuilder.orderBy()
				.addColumn(I_C_Flatrate_Term.COLUMNNAME_StartDate)
				.createQueryOrderBy();

		return queryBuilder
				.create()
				.setOrderBy(orderBy)
				.list(I_C_Flatrate_Term.class);
	}

	@Override
	public I_C_Flatrate_DataEntry retrieveDataEntryOrNull(
			final I_C_Flatrate_Term flatrateTerm,
			final I_C_Period period,
			final String dataEntryType,
			final I_C_UOM uom)
	{
		final Properties ctx = getCtx(flatrateTerm);
		final String trxName = getTrxName(flatrateTerm);

		final String wc = I_C_Flatrate_DataEntry.COLUMNNAME_C_Flatrate_Term_ID + "=? AND "
				+ I_C_Flatrate_DataEntry.COLUMNNAME_C_Period_ID + "=? AND "
				+ I_C_Flatrate_DataEntry.COLUMNNAME_Type + "=? AND "
				+ I_C_Flatrate_DataEntry.COLUMNNAME_C_UOM_ID + "=?";

		return new Query(ctx, I_C_Flatrate_DataEntry.Table_Name, wc, trxName)
				.setParameters(
						flatrateTerm.getC_Flatrate_Term_ID(),
						period.getC_Period_ID(),
						dataEntryType,
						uom.getC_UOM_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.firstOnly(I_C_Flatrate_DataEntry.class);
	}

	@Override
	public List<I_C_Invoice_Clearing_Alloc> retrieveOpenClearingAllocs(final I_C_Flatrate_DataEntry dataEntry)
	{
		final Properties ctx = getCtx(dataEntry);
		final String trxName = getTrxName(dataEntry);

		final I_C_Period period = dataEntry.getC_Period();
		final Timestamp startDate = period.getStartDate();
		final Timestamp endDate = period.getEndDate();

		final String wc = " COALESCE (" + I_C_Invoice_Clearing_Alloc.COLUMNNAME_C_Flatrate_DataEntry_ID + ",0)=0 AND "
				+ I_C_Invoice_Clearing_Alloc.COLUMNNAME_C_Flatrate_Term_ID + "=? AND "
				+ "EXISTS ("
				+ "  select 1 from " + I_C_Invoice_Candidate.Table_Name + " ic "
				+ "  where "
				+ "     ic." + I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID + "=" + I_C_Invoice_Clearing_Alloc.Table_Name + "."
				+ I_C_Invoice_Clearing_Alloc.COLUMNNAME_C_Invoice_Cand_ToClear_ID
				+ "     and ic." + I_C_Invoice_Candidate.COLUMNNAME_DateOrdered + ">=?"
				+ "     and ic." + I_C_Invoice_Candidate.COLUMNNAME_DateOrdered + "<=?"
				+ ") ";

		return new Query(ctx, I_C_Invoice_Clearing_Alloc.Table_Name, wc, trxName)
				.setParameters(
						dataEntry.getC_Flatrate_Term_ID(),
						startDate,
						endDate)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setOrderBy(I_C_Invoice_Clearing_Alloc.COLUMNNAME_C_Invoice_Clearing_Alloc_ID)
				.list(I_C_Invoice_Clearing_Alloc.class);
	}

	@Override
	public final List<I_C_Flatrate_DataEntry> retrieveInvoicingEntries(
			final I_C_Flatrate_Term flatrateTerm,
			final Timestamp dateFrom, final Timestamp dateTo,
			final I_C_UOM uom)
	{
		final List<I_C_Flatrate_DataEntry> result = new ArrayList<>();

		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);
		final List<I_C_Flatrate_DataEntry> entriesToCorrect = flatrateDB.retrieveDataEntries(flatrateTerm, X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased, uom);

		for (final I_C_Flatrate_DataEntry entryToCorrect : entriesToCorrect)
		{
			final I_C_Period entryPeriod = entryToCorrect.getC_Period();
			if (entryPeriod.getEndDate().before(dateFrom) // entryPeriod ends before dateFrom
					|| entryPeriod.getStartDate().after(dateTo))      // entryPeriod begins after dateTo
			{
				continue;
			}
			result.add(entryToCorrect);
		}
		return result;
	}

	@Override
	public I_C_Flatrate_DataEntry retrieveDataEntryOrNull(final I_C_Invoice_Candidate ic)
	{
		final Properties ctx = getCtx(ic);
		final String trxName = getTrxName(ic);

		final String wc = I_C_Flatrate_DataEntry.COLUMNNAME_C_Invoice_Candidate_ID + "=?";

		return new Query(ctx, I_C_Flatrate_DataEntry.Table_Name, wc, trxName)
				.setParameters(ic.getC_Invoice_Candidate_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.firstOnly(I_C_Flatrate_DataEntry.class);
	}

	@Override
	public List<I_C_Flatrate_Term> retrieveTerms(final I_C_Flatrate_Data data)
	{

		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Flatrate_Term.class, data)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Data_ID, data.getC_Flatrate_Data_ID())
				.orderBy()
				.addColumn(I_C_Flatrate_Term.COLUMN_C_Flatrate_Term_ID)
				.endOrderBy()
				.create()
				.list(I_C_Flatrate_Term.class);
	}

	@Override
	public I_C_Flatrate_Term retrieveNonSimTermOrNull(final I_C_Invoice_Candidate ic)
	{
		final List<I_C_Flatrate_Term> terms = retrieveTerms(ic);

		I_C_Flatrate_Term result = null;

		for (final I_C_Flatrate_Term term : terms)
		{
			if (term.isSimulation())
			{
				continue;
			}
			Check.errorIf(result != null, "We can have only one non-sim term for {}, but we have the following terms with at least two being non-sim: {}", ic, terms);
			result = term;
		}
		return result;
	}

	@Override
	public List<I_M_Product> retrieveHoldingFeeProducts(final I_C_Flatrate_Conditions fc)
	{
		final Properties ctx = getCtx(fc);
		final String trxName = getTrxName(fc);

		final List<I_M_Product> result = new ArrayList<>();

		final String wc = I_C_Flatrate_Matching.COLUMNNAME_C_Flatrate_Conditions_ID + "=?";

		final List<I_C_Flatrate_Matching> matchings = new Query(ctx, I_C_Flatrate_Matching.Table_Name, wc, trxName)
				.setParameters(fc.getC_Flatrate_Conditions_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_Flatrate_Matching.COLUMNNAME_SeqNo + ", " + I_C_Flatrate_Matching.COLUMNNAME_C_Flatrate_Matching_ID)
				.list(I_C_Flatrate_Matching.class);

		for (final I_C_Flatrate_Matching matching : matchings)
		{
			Check.assume(matching.getM_Product_Category_Matching_ID() > 0, matching + " has M_Product_Category_Matching_ID>0");

			if (matching.getM_Product_ID() > 0)
			{
				result.add(InterfaceWrapperHelper.create(matching.getM_Product(), I_M_Product.class));
			}
			else
			{
				final List<I_M_Product> productsOfCategory = new Query(ctx, org.compiere.model.I_M_Product.Table_Name, org.compiere.model.I_M_Product.COLUMNNAME_M_Product_Category_ID + "=?", trxName)
						.setParameters(matching.getM_Product_Category_Matching_ID())
						.setOnlyActiveRecords(true)
						.setClient_ID()
						.setOrderBy(org.compiere.model.I_M_Product.COLUMNNAME_Value + "," + org.compiere.model.I_M_Product.COLUMNNAME_M_Product_ID)
						.list(I_M_Product.class);
				result.addAll(productsOfCategory);
			}
		}
		return result;
	}

	@Override
	public List<I_C_UOM> retrieveUOMs(
			final Properties ctx,
			final I_C_Flatrate_Term flatrateTerm,
			final String trxName)
	{
		final List<I_C_UOM> uoms = new Query(ctx, I_C_UOM.Table_Name, I_C_UOM.COLUMNNAME_UOMType + "=?", trxName)
				.setParameters(flatrateTerm.getUOMType())
				.setOnlyActiveRecords(true)
				.setOrderBy(I_C_UOM.COLUMNNAME_C_UOM_ID)
				.list(I_C_UOM.class);
		return uoms;
	}

	@Override
	public void updateQtyActualFromDataEntry(final I_C_Flatrate_DataEntry dataEntry)
	{
		final String sql = "UPDATE " + I_C_Flatrate_DataEntry.Table_Name
				+ " SET " + I_C_Flatrate_DataEntry.COLUMNNAME_ActualQty + "=? "
				+ " WHERE "
				+ I_C_Flatrate_DataEntry.COLUMNNAME_C_Flatrate_Term_ID + "=? AND "
				+ I_C_Flatrate_DataEntry.COLUMNNAME_Type + "=? AND "
				+ I_C_Flatrate_DataEntry.COLUMNNAME_C_Period_ID + "=? AND "
				+ I_C_Flatrate_DataEntry.COLUMNNAME_C_UOM_ID + "!=?";

		final String trxName = getTrxName(dataEntry);

		trxManager.run(trxName, (TrxRunnable)trxName1 -> {
			final PreparedStatement pstmt = DB.prepareStatement(sql, trxName1);

			try
			{
				pstmt.setBigDecimal(1, dataEntry.getActualQty());
				pstmt.setInt(2, dataEntry.getC_Flatrate_Term_ID());
				pstmt.setString(3, dataEntry.getType());
				pstmt.setInt(4, dataEntry.getC_Period_ID());
				pstmt.setInt(5, dataEntry.getC_UOM_ID());

				final int count = pstmt.executeUpdate();
				logger.debug("Updated " + count + " dataEntries for " + dataEntry);
			}
			catch (final SQLException e)
			{
				throw new DBException(e);
			}
		});
	}

	@Override
	public List<I_C_Invoice_Candidate> updateCandidates(final I_C_Flatrate_DataEntry dataEntry)
	{
		final Properties ctx = getCtx(dataEntry);
		final String trxName = getTrxName(dataEntry);

		final String wc = I_C_Invoice_Candidate.COLUMNNAME_DateOrdered + " >= ? AND "
				+ I_C_Invoice_Candidate.COLUMNNAME_DateOrdered + " <= ? AND "
				+ " NOT EXISTS ( "
				+ "     select 1 from " + I_C_Invoice_Clearing_Alloc.Table_Name + " ica "
				+ "     where ica." + I_C_Invoice_Clearing_Alloc.COLUMNNAME_C_Invoice_Cand_ToClear_ID + "="
				+ I_C_Invoice_Candidate.Table_Name + "." + I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID
				+ " )";

		final I_C_Period period = dataEntry.getC_Period();
		final List<I_C_Invoice_Candidate> cands = new Query(ctx, I_C_Invoice_Candidate.Table_Name, wc, trxName)
				.setParameters(
						period.getStartDate(), period.getEndDate())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID)
				.list(I_C_Invoice_Candidate.class);

		final StringBuffer processedCands = new StringBuffer();

		for (final I_C_Invoice_Candidate cand : cands)
		{
			final I_C_Flatrate_Term term = retrieveNonSimTermOrNull(cand);
			if (term == null || term.getC_Flatrate_Term_ID() != dataEntry.getC_Flatrate_Term_ID())
			{
				continue;
			}

			if (cand.isProcessed() && cand.getQtyInvoiced().signum() != 0)
			{
				if (processedCands.length() > 0)
				{
					processedCands.append(", ");
				}
				processedCands.append(cand.getC_Invoice_Candidate_ID());
				continue;
			}

			final I_C_Invoice_Clearing_Alloc ica = InterfaceWrapperHelper.create(ctx, I_C_Invoice_Clearing_Alloc.class, trxName);
			ica.setAD_Org_ID(cand.getAD_Org_ID());

			ica.setC_Invoice_Cand_ToClear_ID(cand.getC_Invoice_Candidate_ID());
			ica.setC_Flatrate_Term_ID(term.getC_Flatrate_Term_ID());
			ica.setC_Flatrate_DataEntry_ID(dataEntry.getC_Flatrate_DataEntry_ID());
			InterfaceWrapperHelper.save(ica);

			cand.setIsToClear(true);
			InterfaceWrapperHelper.save(cand);
		}

		if (processedCands.length() > 0)
		{
			throw new AdempiereException(
					Env.getAD_Language(ctx),
					MSP_DATA_ENTRY_ERROR_INVOICE_CAND_PROCESSED_3P,
					new Object[] { dataEntry.getC_UOM().getName(), dataEntry.getC_Period().getName(), processedCands.toString() });
		}

		return null;
	}

	@Override
	public List<I_C_Flatrate_Conditions> retrieveConditions(final Properties ctx)
	{
		return new Query(ctx, I_C_Flatrate_Conditions.Table_Name, null, ITrx.TRXNAME_None)
				.setClient_ID()
				.setOrderBy(I_C_Flatrate_Conditions.COLUMNNAME_Name)
				.list(I_C_Flatrate_Conditions.class);
	}

	@Override
	public List<I_C_Flatrate_Transition> retrieveTransitionsForCalendar(final I_C_Calendar calendar)
	{
		final Properties ctx = getCtx(calendar);
		final String trxName = getTrxName(calendar);

		final String whereClause = I_C_Flatrate_Transition.COLUMNNAME_C_Calendar_Contract_ID + " =?";

		return new Query(ctx, I_C_Flatrate_Transition.Table_Name, whereClause, trxName)
				.setParameters(calendar.getC_Calendar_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_Flatrate_Transition.COLUMNNAME_C_Flatrate_Transition_ID)
				.list(I_C_Flatrate_Transition.class);
	}

	@Override
	public List<I_C_Flatrate_DataEntry> retrieveDataEntriesForProduct(final org.compiere.model.I_M_Product product)
	{
		final Properties ctx = getCtx(product);
		final String trxName = getTrxName(product);
		final StringBuilder wc = new StringBuilder();
		final List<Object> params = new ArrayList<>();

		wc.append(I_C_Flatrate_DataEntry.COLUMNNAME_M_Product_DataEntry_ID + " =?");
		params.add(product.getM_Product_ID());

		return new Query(ctx, I_C_Flatrate_DataEntry.Table_Name, wc.toString(), trxName)
				.setParameters(params)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_Flatrate_DataEntry.COLUMNNAME_C_Period_ID)
				.list(I_C_Flatrate_DataEntry.class);
	}

	@Override
	public I_C_Flatrate_DataEntry retrieveRefundableDataEntry(final int bPartner_ID, final Timestamp movementDate, final org.compiere.model.I_M_Product product)
	{
		final Properties ctx = getCtx(product);
		final String trxName = getTrxName(product);
		final StringBuilder wc = new StringBuilder();
		final List<Object> params = new ArrayList<>();

		wc.append("( EXISTS (SELECT * FROM ")
				.append(I_C_Flatrate_Term.Table_Name + " ft INNER JOIN ")
				.append(I_C_Flatrate_Conditions.Table_Name + " fc ")
				.append(" ON fc." + I_C_Flatrate_Conditions.COLUMNNAME_C_Flatrate_Conditions_ID + " = ft." + I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID)
				.append(" INNER JOIN " + I_C_Period.Table_Name + " per ")
				.append(" ON per." + I_C_Period.COLUMNNAME_C_Period_ID + " = " + I_C_Flatrate_DataEntry.Table_Name + "." + I_C_Flatrate_DataEntry.COLUMNNAME_C_Period_ID)
				.append(" WHERE  ( ft." + I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID + " = " + I_C_Flatrate_DataEntry.Table_Name + "." + I_C_Flatrate_DataEntry.COLUMNNAME_C_Flatrate_Term_ID)
				.append(" AND COALESCE(ft." + I_C_Flatrate_Term.COLUMNNAME_DropShip_BPartner_ID + ", ft." + I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID + ") =?");

		params.add(bPartner_ID);

		wc.append(" AND fc." + I_C_Flatrate_Conditions.COLUMNNAME_Type_Conditions + " = '" + X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refundable + "'")
				.append(" AND	( ? >= per." + I_C_Period.COLUMNNAME_StartDate)
				.append(" AND ? <= per." + I_C_Period.COLUMNNAME_EndDate + "))) AND ");

		params.add(movementDate);
		params.add(movementDate);

		wc.append(I_C_Flatrate_DataEntry.COLUMNNAME_M_Product_DataEntry_ID + " = ? )");
		params.add(product.getM_Product_ID());

		return new Query(ctx, I_C_Flatrate_DataEntry.Table_Name, wc.toString(), trxName)
				.setParameters(params)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.firstOnly(I_C_Flatrate_DataEntry.class);
	}

	@Override
	public I_C_Flatrate_Data retriveOrCreateFlatrateData(final I_C_BPartner bPartner)
	{
		I_C_Flatrate_Data existingData = Services.get(IQueryBL.class).createQueryBuilder(I_C_Flatrate_Data.class, bPartner)
				.addEqualsFilter(I_C_Flatrate_Data.COLUMNNAME_C_BPartner_ID, bPartner.getC_BPartner_ID())
				.addOnlyActiveRecordsFilter()
				.filterByClientId()
				.create().firstOnly(I_C_Flatrate_Data.class);

		if (existingData == null)
		{
			existingData = InterfaceWrapperHelper.create(getCtx(bPartner),
					I_C_Flatrate_Data.class,
					getTrxName(bPartner));
			existingData.setAD_Org_ID(bPartner.getAD_Org_ID());
			existingData.setC_BPartner_ID(bPartner.getC_BPartner_ID());
			existingData.setHasContracts(false);
			InterfaceWrapperHelper.save(existingData);
		}
		return existingData;
	}

	@Override
	public I_C_Flatrate_Term retrieveAncestorFlatrateTerm(@NonNull final I_C_Flatrate_Term contract)
	{

		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_C_FlatrateTerm_Next_ID, contract.getC_Flatrate_Term_ID())
				.create()
				.firstOnly(I_C_Flatrate_Term.class);
	}

	@Override
	public List<I_C_Invoice> retrieveInvoicesForFlatrateTerm(@NonNull final I_C_Flatrate_Term contract)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		final List<I_C_Invoice_Candidate> icsForCurrentTerm = invoiceCandDAO
				.fetchInvoiceCandidates(
				getCtx(contract),
				I_C_Flatrate_Term.Table_Name,
				contract.getC_Flatrate_Term_ID(),
				getTrxName(contract));

		final List<I_C_Invoice> currentFlatRateTermInvoices = icsForCurrentTerm
				.stream()
				.flatMap(ic -> invoiceCandDAO.retrieveIlForIc(ic).stream())
				.filter(StreamUtils.distinctByKey(I_C_InvoiceLine::getC_Invoice_ID))
				.map(il -> il.getC_Invoice())
				.collect(ImmutableList.toImmutableList());

		return currentFlatRateTermInvoices;
	}
}
