package de.metas.contracts.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.cache.CacheMgt;
import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheTrx;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateDataId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermRequest.ModularFlatrateTermQuery;
import de.metas.contracts.FlatrateTermStatus;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Data;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.contracts.model.I_C_Flatrate_Matching;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.I_C_Invoice_Clearing_Alloc;
import de.metas.contracts.model.I_ModCntr_Log;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.model.I_ModCntr_Settings;
import de.metas.contracts.model.I_ModCntr_Type;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_DataEntry;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.settings.ModularContractSettingsId;
import de.metas.costing.ChargeId;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.logging.LogManager;
import de.metas.order.OrderLineId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StreamUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_M_Product;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static de.metas.contracts.model.X_C_Flatrate_Term.CONTRACTSTATUS_Quit;
import static de.metas.contracts.model.X_C_Flatrate_Term.CONTRACTSTATUS_Voided;
import static de.metas.contracts.model.X_C_Flatrate_Term.DOCSTATUS_Completed;
import static de.metas.contracts.model.X_C_Flatrate_Term.TYPE_CONDITIONS_InterimInvoice;
import static org.adempiere.model.InterfaceWrapperHelper.getCtx;
import static org.adempiere.model.InterfaceWrapperHelper.getTrxName;
import static org.adempiere.model.InterfaceWrapperHelper.load;

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

public class FlatrateDAO implements IFlatrateDAO
{
	private static final AdMessageKey MSP_DATA_ENTRY_ERROR_INVOICE_CAND_PROCESSED_3P = AdMessageKey.of("DataEntry_Error_InvoiceCand_Processed");
	private static final Logger logger = LogManager.getLogger(FlatrateDAO.class);

	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final CCache<ModularFlatrateTermQuery, ImmutableList<I_C_Flatrate_Term>> modularContractQuery2CachedResult = CCache.<ModularFlatrateTermQuery, ImmutableList<I_C_Flatrate_Term>>builder()
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(1000)
			.tableName(I_C_Flatrate_Term.Table_Name)
			.additionalTableNamesToResetFor(ImmutableSet.of(I_C_Flatrate_Conditions.Table_Name, I_ModCntr_Settings.Table_Name))
			.build();

	private final AdTableId tableId = tableDAO.retrieveAdTableId(I_C_Flatrate_Term.Table_Name);

	@Override
	public I_C_Flatrate_Term getById(final int flatrateTermId)
	{
		Check.assumeGreaterThanZero(flatrateTermId, "flatrateTermId");
		return load(flatrateTermId, I_C_Flatrate_Term.class);
	}

	@Override
	public I_C_Flatrate_Term getById(@NonNull final FlatrateTermId flatrateTermId)
	{
		return load(flatrateTermId, I_C_Flatrate_Term.class);
	}

	@Override
	@NonNull
	public ImmutableMap<FlatrateTermId, I_C_Flatrate_Term> getByIds(@NonNull final Set<FlatrateTermId> flatrateTermIds)
	{
		return InterfaceWrapperHelper
				.loadByRepoIdAwares(flatrateTermIds, I_C_Flatrate_Term.class)
				.stream()
				.collect(ImmutableMap.toImmutableMap(term -> FlatrateTermId.ofRepoId(term.getC_Flatrate_Term_ID()),
						Function.identity()));
	}

	@Override
	public List<I_C_Flatrate_Term> retrieveTerms(final I_C_Invoice_Candidate ic)
	{
		final IProductDAO productDAO = Services.get(IProductDAO.class);

		final Properties ctx = getCtx(ic);
		final String trxName = getTrxName(ic);

		final int bill_BPartner_ID = ic.getBill_BPartner_ID();
		final Timestamp dateOrdered = ic.getDateOrdered();

		final I_M_Product product = productDAO.getById(ic.getM_Product_ID());
		final int m_Product_Category_ID = product.getM_Product_Category_ID();
		final int m_Product_ID = ic.getM_Product_ID();
		final int c_Charge_ID = ic.getC_Charge_ID();

		return retrieveTerms(ctx, OrgId.ofRepoId(ic.getAD_Org_ID()), bill_BPartner_ID, dateOrdered, m_Product_Category_ID, m_Product_ID, c_Charge_ID, trxName);
	}

	@Override
	public List<I_C_Flatrate_Term> retrieveTerms(@NonNull final TermsQuery query)
	{
		final ImmutableList<Integer> bPartnerIds = query.getBillPartnerIds().stream().map(BPartnerId::getRepoId).collect(ImmutableList.toImmutableList());
		final Timestamp dateOrdered = TimeUtil.asTimestamp(query.getDateOrdered());
		final int m_Product_Category_ID = ProductCategoryId.toRepoId(query.getProductCategoryId());
		final int m_Product_ID = ProductId.toRepoId(query.getProductId());
		final int c_Charge_ID = ChargeId.toRepoId(query.getChargeId());

		return retrieveTerms(Env.getCtx(), query.getOrgId(), bPartnerIds, dateOrdered, m_Product_Category_ID, m_Product_ID, c_Charge_ID, ITrx.TRXNAME_None);
	}

	@Override
	public List<I_C_Flatrate_Term> retrieveTerms(
			final @CacheCtx Properties ctx,
			@NonNull final OrgId orgId,
			final int bill_BPartner_ID,
			final Timestamp dateOrdered,
			final int m_Product_Category_ID,
			final int m_Product_ID,
			final int c_Charge_ID,
			final @CacheTrx String trxName)
	{
		return retrieveTerms(ctx, orgId, ImmutableList.of(bill_BPartner_ID), dateOrdered, m_Product_Category_ID, m_Product_ID, c_Charge_ID, trxName);
	}

	@Cached(cacheName = I_C_Flatrate_Term.Table_Name + "#by#criteria")
	public List<I_C_Flatrate_Term> retrieveTerms(
			final @CacheCtx Properties ctx,
			@NonNull final OrgId orgId,
			final ImmutableList<Integer> bPartnerIds,
			final Timestamp dateOrdered,
			final int m_Product_Category_ID,
			final int m_Product_ID,
			final int c_Charge_ID,
			final @CacheTrx String trxName)
	{
		final boolean filterByProductCategory = m_Product_Category_ID > 0;
		final boolean filterByCharge = c_Charge_ID > 0;
		final boolean filterByProduct = m_Product_ID > 0;

		final boolean filterByMatchingRecord = filterByProductCategory || filterByProduct || filterByCharge;
		final IQueryBuilder<I_C_Flatrate_Conditions> fcQueryBuilder;
		if (filterByMatchingRecord)
		{
			final IQueryBuilder<I_C_Flatrate_Matching> matchingQueryBuilder = queryBL.createQueryBuilder(I_C_Flatrate_Matching.class, ctx, trxName)
					.addOnlyActiveRecordsFilter();
			if (filterByProductCategory)
			{
				matchingQueryBuilder.addInArrayOrAllFilter(I_C_Flatrate_Matching.COLUMNNAME_M_Product_Category_Matching_ID, null, m_Product_Category_ID);
			}
			if (filterByProduct)
			{
				matchingQueryBuilder.addInArrayOrAllFilter(I_C_Flatrate_Matching.COLUMNNAME_M_Product_ID, null, m_Product_ID);
			}
			if (filterByCharge)
			{
				matchingQueryBuilder.addInArrayOrAllFilter(I_C_Flatrate_Matching.COLUMNNAME_C_Charge_ID, null, c_Charge_ID);
			}
			fcQueryBuilder = matchingQueryBuilder
					.andCollect(I_C_Flatrate_Conditions.COLUMN_C_Flatrate_Conditions_ID, I_C_Flatrate_Conditions.class);

			if (m_Product_ID > 0)
			{ // make sure that we don't get a problem if the FC's products are in the same product-category as all the other products
				fcQueryBuilder
						.addNotEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_M_Product_Flatrate_ID, m_Product_ID)
						.addNotEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_M_Product_Correction_ID, m_Product_ID)
						.addNotEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_M_Product_Actual_ID, m_Product_ID);
			}
		}
		else
		{
			fcQueryBuilder = queryBL.createQueryBuilder(I_C_Flatrate_Conditions.class);
		}

		fcQueryBuilder
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_DocStatus, IDocument.STATUS_Completed)
				.addNotEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_Type_Conditions, X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription)
				.addNotEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_Type_Conditions, X_C_Flatrate_Conditions.TYPE_CONDITIONS_HoldingFee)
				.create();

		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_AD_Org_ID, orgId)
				.addInArrayFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, bPartnerIds)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, IDocument.STATUS_Completed)
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_StartDate, Operator.LESS_OR_EQUAL, dateOrdered)
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, Operator.GREATER_OR_EQUAL, dateOrdered)
				.addInSubQueryFilter(I_C_Flatrate_Term.COLUMN_C_Flatrate_Conditions_ID, I_C_Flatrate_Conditions.COLUMN_C_Flatrate_Conditions_ID, fcQueryBuilder.create())
				.orderBy(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID)
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
		return queryBL.createQueryBuilder(I_C_Flatrate_Matching.class, ctx, trxName)
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
	public List<I_C_Invoice_Clearing_Alloc> retrieveClearingAllocs(@NonNull final I_C_Flatrate_DataEntry dataEntry)
	{
		return queryBL.createQueryBuilder(I_C_Invoice_Clearing_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Clearing_Alloc.COLUMNNAME_C_Flatrate_DataEntry_ID, dataEntry.getC_Flatrate_DataEntry_ID())
				.orderBy(I_C_Invoice_Clearing_Alloc.COLUMNNAME_C_Invoice_Clearing_Alloc_ID)
				.create().list();
	}

	@Override
	public List<I_C_Invoice_Clearing_Alloc> retrieveAllClearingAllocs(@NonNull final I_C_Invoice_Candidate invoiceCand)
	{
		final boolean retrieveAll = true;
		return retrieveClearingAllocs(invoiceCand, retrieveAll);
	}

	@Override
	public List<I_C_Invoice_Clearing_Alloc> retrieveClearingAllocs(@NonNull final I_C_Invoice_Candidate invoiceCand)
	{
		final boolean retrieveAll = false;
		return retrieveClearingAllocs(invoiceCand, retrieveAll);
	}

	private List<I_C_Invoice_Clearing_Alloc> retrieveClearingAllocs(@NonNull final I_C_Invoice_Candidate invoiceCand, final boolean retrieveAll)
	{
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
	public List<I_C_Invoice_Clearing_Alloc> retrieveClearingAllocs(@NonNull final I_C_Flatrate_Term term)
	{
		return queryBL.createQueryBuilder(I_C_Invoice_Clearing_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Clearing_Alloc.COLUMNNAME_C_Flatrate_Term_ID, term.getC_Flatrate_Term_ID())
				.orderBy(I_C_Invoice_Clearing_Alloc.COLUMNNAME_C_Invoice_Clearing_Alloc_ID)
				.create()
				.list();
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
			final UomId uomId,
			final boolean onlyNonSim)
	{
		return retrieveEntries(fc, null, dateOrdered, dataEntryType, uomId, onlyNonSim);
	}

	@Override
	public List<I_C_Flatrate_DataEntry> retrieveEntries(
			final I_C_Flatrate_Conditions fc,
			final I_C_Flatrate_Term term,
			final Timestamp date,
			final String dataEntryType,
			final UomId uomId,
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

		if (uomId != null && UomId.toRepoId(uomId) > 0)
		{
			wc.append(" AND " + I_C_Flatrate_DataEntry.COLUMNNAME_C_UOM_ID + "=? ");
			params.add(uomId);
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
		final UomId uomId = null;

		return retrieveEntries(fc, flatrateTerm, date, dataEntryType, uomId, onlyNonSim);
	}

	@Override
	public List<I_C_Flatrate_DataEntry> retrieveDataEntries(
			final I_C_Flatrate_Term term,
			final String dataEntryType,
			final UomId uomId)
	{
		final IQueryBuilder<I_C_Flatrate_DataEntry> queryBuilder = queryBL.createQueryBuilder(I_C_Flatrate_DataEntry.class, term)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_DataEntry.COLUMNNAME_C_Flatrate_Term_ID, term.getC_Flatrate_Term_ID())
				.addOnlyContextClient();

		if (uomId != null)
		{
			queryBuilder.addEqualsFilter(I_C_Flatrate_DataEntry.COLUMNNAME_C_UOM_ID, uomId);
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
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, bPartner.getC_BPartner_ID())
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID, flatrateConditions.getC_Flatrate_Conditions_ID())
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
			@NonNull final UomId uomId)
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
						uomId.getRepoId())
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
			final @NonNull LocalDateAndOrgId dateFrom,
			final @NonNull LocalDateAndOrgId dateTo,
			final UomId uomId)
	{
		final List<I_C_Flatrate_DataEntry> result = new ArrayList<>();

		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);
		final List<I_C_Flatrate_DataEntry> entriesToCorrect = flatrateDB.retrieveDataEntries(flatrateTerm, X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased, uomId);

		for (final I_C_Flatrate_DataEntry entryToCorrect : entriesToCorrect)
		{
			final I_C_Period entryPeriod = entryToCorrect.getC_Period();
			final OrgId orgId = OrgId.ofRepoId(entryToCorrect.getAD_Org_ID());
			final LocalDateAndOrgId endDate = LocalDateAndOrgId.ofTimestamp(entryPeriod.getEndDate(), orgId, orgDAO::getTimeZone);
			final LocalDateAndOrgId startDate = LocalDateAndOrgId.ofTimestamp(entryPeriod.getEndDate(), orgId, orgDAO::getTimeZone);

			if (endDate.compareTo(dateFrom) < 0  // entryPeriod ends before dateFrom
					|| startDate.compareTo(dateTo) > 0)      // entryPeriod begins after dateTo
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
	public Iterable<I_C_Flatrate_Term> retrieveTerms(@NonNull final FlatrateDataId flatrateDataId)
	{
		return () -> getFlatrateTermQueryForFlatrateDataId(flatrateDataId)
				.iterate(I_C_Flatrate_Term.class);
	}

	@Override
	public ImmutableList<I_C_Flatrate_Term> retrieveTermsAsList(@NonNull final FlatrateDataId flatrateDataId)
	{
		return getFlatrateTermQueryForFlatrateDataId(flatrateDataId)
				.listImmutable(I_C_Flatrate_Term.class);
	}

	private IQuery<I_C_Flatrate_Term> getFlatrateTermQueryForFlatrateDataId(final FlatrateDataId flatrateDataId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Data_ID, flatrateDataId)
				.orderBy()
				.addColumn(I_C_Flatrate_Term.COLUMN_C_Flatrate_Term_ID)
				.endOrderBy()
				.create();
	}

	@Override
	public List<I_C_Flatrate_Term> retrieveTerms(final I_C_Flatrate_Data data)
	{
		final FlatrateDataId flatrateDataId = FlatrateDataId.ofRepoId(data.getC_Flatrate_Data_ID());

		return getFlatrateTermQueryForFlatrateDataId(flatrateDataId)
				.list(I_C_Flatrate_Term.class);
	}

	@Override
	public List<I_C_Flatrate_Term> retrieveTerms(@NonNull final Collection<FlatrateTermId> flatrateTermIds)
	{
		return InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx(flatrateTermIds, I_C_Flatrate_Term.class);
	}

	@Override
	public I_C_Flatrate_Term retrieveNonSimTermOrNull(@NonNull final I_C_Invoice_Candidate ic)
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
				result.add(InterfaceWrapperHelper.load(matching.getM_Product_ID(), I_M_Product.class));
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
			final ITranslatableString name = uomDAO.getName(UomId.ofRepoId(dataEntry.getC_UOM_ID()));
			throw new AdempiereException(
					MSP_DATA_ENTRY_ERROR_INVOICE_CAND_PROCESSED_3P,
					name, dataEntry.getC_Period().getName(), processedCands.toString());
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
	@NonNull
	public ImmutableMap<ConditionsId, I_C_Flatrate_Conditions> getTermConditionsByIds(@NonNull final Set<ConditionsId> conditionsIds)
	{
		return InterfaceWrapperHelper
				.loadByRepoIdAwares(conditionsIds, I_C_Flatrate_Conditions.class)
				.stream()
				.collect(ImmutableMap.toImmutableMap(conditions -> ConditionsId.ofRepoId(conditions.getC_Flatrate_Conditions_ID()),
						Function.identity()));
	}

	@Override
	public int getFlatrateConditionsIdByName(@NonNull final String name)
	{
		final int flatrateConditionsId = queryBL
				.createQueryBuilderOutOfTrx(I_C_Flatrate_Conditions.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Conditions.COLUMN_Name, name)
				.create()
				.firstIdOnly();

		if (flatrateConditionsId <= 0)
		{
			throw new AdempiereException("@NotFound@ @C_Flatrate_Conditions_ID@ (@Name@: " + name + ")");
		}

		return flatrateConditionsId;
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
	@Nullable
	public I_C_Flatrate_DataEntry retrieveRefundableDataEntry(
			final int bPartner_ID,
			@NonNull final Timestamp movementDate,
			@NonNull final org.compiere.model.I_M_Product product)
	{

		final IQuery<I_C_Period> periodQuery = queryBL
				.createQueryBuilder(I_C_Period.class)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(I_C_Period.COLUMNNAME_StartDate, Operator.LESS_OR_EQUAL, movementDate)
				.addCompareFilter(I_C_Period.COLUMNNAME_EndDate, Operator.GREATER_OR_EQUAL, movementDate)
				.create();

		return queryBL
				.createQueryBuilder(I_C_Flatrate_Conditions.class)
				.addOnlyActiveRecordsFilter() // new
				.addEqualsFilter(I_C_Flatrate_Conditions.COLUMN_Type_Conditions, X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refundable)
				.addEqualsFilter(I_C_Flatrate_Conditions.COLUMN_DocStatus, X_C_Flatrate_Conditions.DOCSTATUS_Completed) // new

				.andCollectChildren(I_C_Flatrate_Term.COLUMN_C_Flatrate_Conditions_ID)
				.addOnlyActiveRecordsFilter() // new
				.addCoalesceEqualsFilter(bPartner_ID, I_C_Flatrate_Term.COLUMNNAME_DropShip_BPartner_ID, I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_DocStatus, DOCSTATUS_Completed) // new
				.addNotInArrayFilter(I_C_Flatrate_Term.COLUMN_ContractStatus, ImmutableList.of(CONTRACTSTATUS_Quit, CONTRACTSTATUS_Voided)) // new
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_StartDate, Operator.LESS_OR_EQUAL, movementDate) // new
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, Operator.GREATER_OR_EQUAL, movementDate) // new

				.andCollectChildren(I_C_Flatrate_DataEntry.COLUMN_C_Flatrate_Term_ID)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_DataEntry.COLUMNNAME_M_Product_DataEntry_ID, product.getM_Product_ID())
				.addInSubQueryFilter(I_C_Flatrate_DataEntry.COLUMNNAME_C_Period_ID, I_C_Period.COLUMNNAME_C_Period_ID, periodQuery)
				.create()
				.firstOnly(I_C_Flatrate_DataEntry.class);
	}

	@Override
	public I_C_Flatrate_Data retrieveOrCreateFlatrateData(final I_C_BPartner bPartner)
	{
		I_C_Flatrate_Data existingData = queryBL.createQueryBuilder(I_C_Flatrate_Data.class, bPartner)
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
	@Nullable
	public I_C_Flatrate_Term retrieveAncestorFlatrateTerm(@NonNull final I_C_Flatrate_Term contract)
	{
		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
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
				.retrieveReferencing(TableRecordReference.of(contract));

		return icsForCurrentTerm
				.stream()
				.flatMap(ic -> invoiceCandDAO.retrieveIlForIc(ic).stream())
				.filter(StreamUtils.distinctByKey(I_C_InvoiceLine::getC_Invoice_ID))
				.map(il -> il.getC_Invoice())
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	@NonNull
	public I_C_Flatrate_Conditions getConditionsById(final int flatrateConditionsId)
	{
		return load(flatrateConditionsId, I_C_Flatrate_Conditions.class);
	}

	@Override
	public I_C_Flatrate_Conditions getConditionsById(final ConditionsId flatrateConditionsId)
	{
		return load(flatrateConditionsId, I_C_Flatrate_Conditions.class);
	}

	@Override
	public void save(@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		InterfaceWrapperHelper.save(flatrateTerm);
		CacheMgt.get().reset(I_C_Flatrate_Term.Table_Name, flatrateTerm.getC_Flatrate_Term_ID());
	}

	@Override
	@Nullable
	public I_C_Invoice_Candidate retrieveInvoiceCandidate(final I_C_Flatrate_Term term)
	{
		return queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID, tableId)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Record_ID, term.getC_Flatrate_Term_ID())
				.create()
				.firstOnly(I_C_Invoice_Candidate.class);
	}

	@Cached(cacheName = I_C_Flatrate_Term.Table_Name + "#by#bPartnerId#typeConditions")
	public List<I_C_Flatrate_Term> retrieveTerms(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final OrgId orgId,
			@NonNull final TypeConditions typeConditions)
	{
		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, bPartnerId.getRepoId())
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Type_Conditions, typeConditions.getCode())
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_AD_Org_ID, orgId.getRepoId())
				.create()
				.list(I_C_Flatrate_Term.class);
	}

	@Override
	@NonNull
	public Optional<I_C_Flatrate_Term> getByOrderLineId(@NonNull final OrderLineId orderLineId, @NonNull final TypeConditions typeConditions)
	{
		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_C_OrderLine_Term_ID, orderLineId)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Type_Conditions, typeConditions.getCode())
				.create()
				.firstOnlyOptional();
	}

	@Override
	public boolean hasOverlappingTerms(@NonNull final FlatrateTermOverlapCriteria flatrateTermOverlapCriteria)
	{
		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, flatrateTermOverlapCriteria.getBPartnerId())
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID, flatrateTermOverlapCriteria.getConditionsId())
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_AD_Org_ID, flatrateTermOverlapCriteria.getOrgId())
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_M_Product_ID, flatrateTermOverlapCriteria.getProductId())
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_StartDate, Operator.LESS_OR_EQUAL, flatrateTermOverlapCriteria.getDatePromised())
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, Operator.GREATER_OR_EQUAL, flatrateTermOverlapCriteria.getDatePromised())
				.create()
				.anyMatch();
	}

	@Override
	public Set<FlatrateTermId> retrieveAllRunningSubscriptionIds(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final Instant date,
			@NonNull final OrgId orgId)
	{
		return existingSubscriptionsQueryBuilder(orgId, bPartnerId, date)
				.create()
				.listIds(FlatrateTermId::ofRepoId);
	}

	private IQueryBuilder<I_C_Flatrate_Term> existingSubscriptionsQueryBuilder(@NonNull final OrgId orgId, @NonNull final BPartnerId bPartnerId, @NonNull final Instant date)
	{
		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_AD_Org_ID, orgId)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, bPartnerId)
				.addNotEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, FlatrateTermStatus.Quit.getCode())
				.addNotEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, FlatrateTermStatus.Voided.getCode())
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, Operator.GREATER, date);
	}

	@Override
	public boolean bpartnerHasExistingRunningTerms(@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		if (flatrateTerm.getC_Order_Term_ID() <= 0)
		{
			return true; // if this term has no C_Order_Term_ID, then it *is* one of those running terms
		}
		final Instant instant = TimeUtil.asInstant(flatrateTerm.getC_Order_Term().getDateOrdered());

		final IQueryBuilder<I_C_Flatrate_Term> queryBuilder = //
				existingSubscriptionsQueryBuilder(OrgId.ofRepoId(flatrateTerm.getAD_Org_ID()),
						BPartnerId.ofRepoId(flatrateTerm.getBill_BPartner_ID()),
						instant);

		queryBuilder.addNotEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_C_Order_Term_ID, flatrateTerm.getC_Order_Term_ID());

		return queryBuilder.create()
				.anyMatch();
	}

	@Override
	@Nullable
	public I_C_Flatrate_Term retrieveFirstFlatrateTerm(@NonNull final I_C_Invoice invoice)
	{
		return queryBL.createQueryBuilder(I_C_InvoiceLine.class)
				.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, invoice.getC_Invoice_ID())
				//collect related invoice line alloc
				.andCollectChildren(I_C_Invoice_Line_Alloc.COLUMN_C_InvoiceLine_ID)
				.addOnlyActiveRecordsFilter()
				//collect related invoice candidates
				.andCollect(I_C_Invoice_Line_Alloc.COLUMNNAME_C_Invoice_Candidate_ID, I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID, tableId)
				//collect flatrate terms
				.andCollect(I_C_Invoice_Candidate.COLUMNNAME_Record_ID, I_C_Flatrate_Term.class)
				.create()
				.first(I_C_Flatrate_Term.class); // could be more than one, but all belong to the same contract and have same billing infos

	}

	@NonNull
	@Override
	public ImmutableList<I_C_Flatrate_Term> getModularFlatrateTermsByQuery(@NonNull final ModularFlatrateTermQuery modularFlatrateTermQuery)
	{
		return modularContractQuery2CachedResult.getOrLoad(modularFlatrateTermQuery, this::retrieveByModularContractQuery);
	}

	@NonNull
	private ImmutableList<I_C_Flatrate_Term> retrieveByModularContractQuery(@NonNull final ModularFlatrateTermQuery modularFlatrateTermQuery)
	{
		final IQueryBuilder<I_C_Flatrate_Term> queryBuilder = queryBL.createQueryBuilder(I_C_Flatrate_Conditions.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_Type_Conditions, modularFlatrateTermQuery.getTypeConditions())
				.addEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_DocStatus, X_C_Flatrate_Conditions.DOCSTATUS_Completed)
				.addInSubQueryFilter(I_C_Flatrate_Conditions.COLUMNNAME_ModCntr_Settings_ID, I_ModCntr_Settings.COLUMNNAME_ModCntr_Settings_ID,
						buildModularContractSettingsQueryFilter(modularFlatrateTermQuery))
				.andCollectChildren(I_C_Flatrate_Term.COLUMN_C_Flatrate_Conditions_ID, I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, modularFlatrateTermQuery.getBPartnerId())
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Type_Conditions, modularFlatrateTermQuery.getTypeConditions())
				.addNotEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, X_C_Flatrate_Term.CONTRACTSTATUS_Voided)
				.addNotEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, X_C_Flatrate_Term.CONTRACTSTATUS_Quit);

		if (modularFlatrateTermQuery.getBPartnerId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, modularFlatrateTermQuery.getBPartnerId());
		}

		if (modularFlatrateTermQuery.getDateFromLessOrEqual() != null)
		{
			queryBuilder.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_StartDate, Operator.LESS_OR_EQUAL, modularFlatrateTermQuery.getDateFromLessOrEqual());
		}

		if (modularFlatrateTermQuery.getDateToGreaterOrEqual() != null)
		{
			queryBuilder.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, Operator.GREATER_OR_EQUAL, modularFlatrateTermQuery.getDateToGreaterOrEqual());
		}

		return queryBuilder.create()
				.listImmutable();
	}

	@NonNull
	private IQuery<I_ModCntr_Settings> buildModularContractSettingsQueryFilter(@NonNull final ModularFlatrateTermQuery request)
	{
		final IQueryBuilder<I_ModCntr_Settings> queryBuilder = queryBL.createQueryBuilder(I_ModCntr_Settings.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_IsSOTrx, request.getSoTrx().toBoolean());

		if (request.getYearId() != null)
		{
			queryBuilder.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_C_Year_ID, request.getYearId());
		}

		if (request.getProductId() != null)
		{
			queryBuilder.filter(queryBL.createCompositeQueryFilter(I_ModCntr_Settings.class)
					.setJoinOr()
					.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_M_Raw_Product_ID, request.getProductId())
					.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_M_Processed_Product_ID, request.getProductId()));
		}

		return queryBuilder.create();
	}

	@Override
	public boolean isExistsModularOrInterimContract(@NonNull final IQueryFilter<I_C_Flatrate_Term> flatrateTermFilter)
	{
		return getFlatrateTermQueryBuilder(flatrateTermFilter)
				.addInArrayFilter(I_C_Flatrate_Term.COLUMNNAME_Type_Conditions, X_C_Flatrate_Term.TYPE_CONDITIONS_ModularContract, X_C_Flatrate_Term.TYPE_CONDITIONS_InterimInvoice)
				.anyMatch();
	}

	@NonNull
	private IQueryBuilder<I_C_Flatrate_Term> getFlatrateTermQueryBuilder(@NonNull final IQueryFilter<I_C_Flatrate_Term> flatrateTermFilter)
	{
		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.filter(flatrateTermFilter);
	}

	@Override
	public IQuery<I_C_Flatrate_Term> createInterimContractQuery(@NonNull final IQueryFilter<I_C_Flatrate_Term> contractFilter)
	{
		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.filter(contractFilter)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Type_Conditions, TYPE_CONDITIONS_InterimInvoice)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, DocStatus.Completed)
				.create();
	}

	@Override
	public Stream<I_C_Flatrate_Term> stream(@NonNull final IQueryFilter<I_C_Flatrate_Term> filter)
	{
		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.filter(filter)
				.create()
				.iterateAndStream();
	}

	@Override
	public ImmutableList<I_C_Flatrate_Term> retrieveRunningTermsForDropShipPartnerAndProductCategory(@NonNull final BPartnerId bPartnerId, @NonNull final ProductCategoryId productCategoryId)
	{

		final IQuery<I_M_Product> subQuery_ProductCateg = queryBL
				.createQueryBuilder(I_M_Product.class)
				.addEqualsFilter(I_M_Product.COLUMNNAME_M_Product_Category_ID, productCategoryId)
				.create();

		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, FlatrateTermStatus.Running)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_DropShip_BPartner_ID, bPartnerId)
				.addInSubQueryFilter()
				.matchingColumnNames(I_C_Flatrate_Term.COLUMNNAME_M_Product_ID, I_M_Product.COLUMNNAME_M_Product_ID)
				.subQuery(subQuery_ProductCateg)
				.end()
				.addCompareFilter(I_C_Flatrate_Term.COLUMN_EndDate, Operator.GREATER_OR_EQUAL, SystemTime.asTimestamp())
				.create()
				.listImmutable(I_C_Flatrate_Term.class);
	}

	@NonNull
	public Stream<I_C_Flatrate_Conditions> streamCompletedConditionsBy(@NonNull final ModularContractSettingsId modularContractSettingsId)
	{
		return queryBL.createQueryBuilder(I_C_Flatrate_Conditions.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_ModCntr_Settings_ID, modularContractSettingsId)
				.addEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_DocStatus, DOCSTATUS_Completed)
				.stream();
	}

	@NonNull
	public ImmutableSet<FlatrateTermId> getReadyForFinalInvoicingModularContractIds(@NonNull final IQueryFilter<I_C_Flatrate_Term> queryFilter)
	{
		final IQueryBuilder<I_C_Flatrate_Term> queryBuilder = getFlatrateTermQueryBuilder(queryFilter);
		return createModularContractQuery(queryBuilder)
				.listIds(FlatrateTermId::ofRepoId);
	}

	@Override
	public ImmutableSet<FlatrateTermId> getReadyForDefinitiveInvoicingModularContractIds(@NonNull final IQueryFilter<I_C_Flatrate_Term> queryFilter)
	{
		return getContractsReadyForDefinitiveInvoice(queryFilter)
				.listIds(FlatrateTermId::ofRepoId);
	}

	@Override
	public void prepareForDefinitiveInvoice(@NonNull final Collection<FlatrateTermId> contractIds)
	{
		queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addInArrayFilter(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID, contractIds)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_IsReadyForDefinitiveInvoice, false)
				.create()
				.update(queryBL.createCompositeQueryUpdater(I_C_Flatrate_Term.class)
						.addSetColumnValue(I_C_Flatrate_Term.COLUMNNAME_IsReadyForDefinitiveInvoice, true));
	}

	@Override
	public boolean isInvoiceableModularContractExists(@NonNull final IQueryFilter<I_C_Flatrate_Term> filter)
	{
		return createModularContractQuery(getFlatrateTermQueryBuilder(filter)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_IsReadyForDefinitiveInvoice, false))
				.anyMatch();
	}

	@Override
	public boolean isDefinitiveInvoiceableModularContractExists(@NonNull final IQueryFilter<I_C_Flatrate_Term> filter)
	{
		final IQuery<I_C_Flatrate_Term> unprocessedDefinitiveInvoiceContractsQuery = getContractsReadyForDefinitiveInvoice(filter);
		return unprocessedDefinitiveInvoiceContractsQuery
				.anyMatch();
	}

	private IQuery<I_C_Flatrate_Term> getContractsReadyForDefinitiveInvoice(final @NonNull IQueryFilter<I_C_Flatrate_Term> filter)
	{
		final IQuery<I_C_Flatrate_Term> unprocessedFinalInvoiceSpecificLogsExist = queryBL.createQueryBuilder(I_ModCntr_Type.class)
				.addInArrayFilter(I_ModCntr_Type.COLUMNNAME_ModularContractHandlerType, ComputingMethodType.FINAL_INVOICE_SPECIFIC_METHODS)
				.andCollectChildren(I_ModCntr_Module.COLUMN_ModCntr_Type_ID)
				.andCollectChildren(I_ModCntr_Log.COLUMN_ModCntr_Module_ID)
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_IsBillable, true)
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_Processed, false)
				.andCollect(I_ModCntr_Log.COLUMN_C_Flatrate_Term_ID)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Type_Conditions, TypeConditions.MODULAR_CONTRACT.getCode())
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, DocStatus.Completed)
				.filter(filter)
				.create();

		return queryBL.createQueryBuilder(I_ModCntr_Type.class)
				.addInArrayFilter(I_ModCntr_Type.COLUMNNAME_ModularContractHandlerType, ComputingMethodType.DEFINITIVE_INVOICE_SPECIFIC_METHODS)
				.andCollectChildren(I_ModCntr_Module.COLUMN_ModCntr_Type_ID)
				.andCollectChildren(I_ModCntr_Log.COLUMN_ModCntr_Module_ID)
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_IsBillable, true)
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_Processed, false)
				.andCollect(I_ModCntr_Log.COLUMN_C_Flatrate_Term_ID)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Type_Conditions, TypeConditions.MODULAR_CONTRACT.getCode())
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, DocStatus.Completed)
				.filter(filter)
				.addNotInSubQueryFilter(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID, I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID, unprocessedFinalInvoiceSpecificLogsExist)
				.create();
	}

	@NonNull
	private IQuery<I_C_Flatrate_Term> createModularContractQuery(@NonNull final IQueryBuilder<I_C_Flatrate_Term> queryBuilder)
	{
		return queryBuilder
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Type_Conditions, TypeConditions.MODULAR_CONTRACT.getCode())
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, DocStatus.Completed)
				.create();
	}
}
