package de.metas.invoice.service.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheTrx;
import de.metas.common.util.CoalesceUtil;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.InvoiceQuery;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.money.CurrencyId;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import de.metas.util.time.InstantInterval;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

import static de.metas.util.Check.assumeNotNull;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwares;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Implements those methods from {@link IInvoiceDAO} that are DB decoupled.
 *
 * @author ts
 */
public abstract class AbstractInvoiceDAO implements IInvoiceDAO
{

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	@Override
	public void save(@NonNull final org.compiere.model.I_C_Invoice invoice)
	{
		saveRecord(invoice);
	}

	@Override
	public void delete(@NonNull final org.compiere.model.I_C_Invoice invoice)
	{
		InterfaceWrapperHelper.delete(invoice);
	}

	@Override
	public void save(@NonNull final org.compiere.model.I_C_InvoiceLine invoiceLine)
	{
		saveRecord(invoiceLine);
	}

	@Override
	public List<I_C_Invoice> getInvoicesForOrderIds(@NonNull final List<OrderId> orderIds)
	{
		return queryBL
				.createQueryBuilder(I_C_Invoice.class)
				.addInArrayFilter(I_C_Order.COLUMNNAME_C_Order_ID, orderIds)
				.create()
				.list();
	}

	@Override
	public Map<OrderId, InvoiceId> getInvoiceIdsForOrderIds(@NonNull final List<OrderId> orderIds)
	{
		final Map<OrderId, InvoiceId> orderIdInvoiceIdMap = new HashMap<>();
		final List<I_C_Invoice> invoices = getInvoicesForOrderIds(orderIds);
		for (final I_C_Invoice invoice : invoices)
		{
			if (invoice != null && invoice.getC_Order_ID() > 0)
			{
				orderIdInvoiceIdMap.put(OrderId.ofRepoId(invoice.getC_Order_ID()), InvoiceId.ofRepoId(invoice.getC_Invoice_ID()));
			}
		}
		return orderIdInvoiceIdMap;
	}

	@Override
	public I_C_InvoiceLine retrieveLineById(final InvoiceLineId invoiceLineId)
	{
		return load(invoiceLineId, I_C_InvoiceLine.class);
	}

	@Override
	@Nullable
	public final I_C_Invoice retrieveInvoiceByInvoiceNoAndBPartnerID(final Properties ctx, final String invoiceNo, final BPartnerId bpartnerId)
	{
		if (bpartnerId == null)
		{
			return null;
		}
		if (invoiceNo == null)
		{
			return null;
		}

		return queryBL
				.createQueryBuilderOutOfTrx(I_C_Invoice.class)
				.addOnlyContextClient()
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_DocumentNo, invoiceNo)
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.create()
				.firstOnly(I_C_Invoice.class);
	}

	@Override
	public final Iterator<I_C_Invoice> retrieveOpenInvoicesByOrg(final I_AD_Org adOrg)
	{
		final int adOrgID = adOrg.getAD_Org_ID();
		Check.assume(adOrgID > 0, "Valid transactional Org: {}", adOrg);

		return queryBL
				.createQueryBuilder(I_C_Invoice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_AD_Org_ID, adOrgID)
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_IsPaid, false)
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, false)
				.iterate(I_C_Invoice.class);
	}

	@Override
	public Amount retrieveOpenAmt(final InvoiceId invoiceId)
	{
		final org.compiere.model.I_C_Invoice invoice = getByIdInTrx(invoiceId);
		final BigDecimal openAmt = retrieveOpenAmt(invoice);

		final CurrencyRepository currencyRepository = SpringContextHolder.instance.getBean(CurrencyRepository.class);
		final CurrencyCode currencyCode = currencyRepository.getCurrencyCodeById(CurrencyId.ofRepoId(invoice.getC_Currency_ID()));
		return Amount.of(openAmt, currencyCode);
	}

	@Override
	@Deprecated
	public BigDecimal retrieveOpenAmt(final org.compiere.model.I_C_Invoice invoice)
	{
		return Services.get(IAllocationDAO.class).retrieveOpenAmt(invoice, true);
	}

	@Override
	public List<I_C_InvoiceLine> retrieveLines(final org.compiere.model.I_C_Invoice invoice, final String trxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);
		final int invoiceId = invoice.getC_Invoice_ID();
		return retrieveLines(ctx, invoiceId, trxName);
	}

	@Override
	public List<I_C_InvoiceLine> retrieveLines(final org.compiere.model.I_C_Invoice invoice)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);
		final String trxName = InterfaceWrapperHelper.getTrxName(invoice);
		final int invoiceId = invoice.getC_Invoice_ID();
		return retrieveLines(ctx, invoiceId, trxName);
	}

	@Override
	public List<I_C_InvoiceLine> retrieveLines(@NonNull final InvoiceId invoiceId)
	{
		return retrieveLines(Env.getCtx(), invoiceId.getRepoId(), ITrx.TRXNAME_ThreadInherited);
	}

	@Cached(cacheName = I_C_InvoiceLine.Table_Name + "#By#C_Invoice_ID")
	protected List<I_C_InvoiceLine> retrieveLines(@CacheCtx final Properties ctx, final int invoiceId, @CacheTrx final String trxName)
	{
		return retrieveLinesQuery(ctx, invoiceId, trxName)
				.create()
				.list(I_C_InvoiceLine.class);
	}

	private IQueryBuilder<I_C_InvoiceLine> retrieveLinesQuery(final Properties ctx, final int invoiceId, final String trxName)
	{
		return queryBL.createQueryBuilder(I_C_InvoiceLine.class, ctx, trxName)
				// FIXME find out if this needs to return *all* lines or just active ones
				.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, invoiceId)
				//
				.orderBy()
				.addColumn(I_C_InvoiceLine.COLUMNNAME_Line)
				.endOrderBy()
				//
				;
	}

	@Override
	public IQueryBuilder<I_C_InvoiceLine> retrieveLinesQuery(final org.compiere.model.I_C_Invoice invoice)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);
		final String trxName = InterfaceWrapperHelper.getTrxName(invoice);
		final int invoiceId = invoice.getC_Invoice_ID();
		return retrieveLinesQuery(ctx, invoiceId, trxName);
	}

	@Nullable
	@Override
	public I_C_InvoiceLine retrieveReversalLine(final I_C_InvoiceLine line, final int reversalInvoiceId)
	{
		return queryBL.createQueryBuilder(I_C_InvoiceLine.class, line)
				.filter(new EqualsQueryFilter<>(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, reversalInvoiceId))
				.filter(new EqualsQueryFilter<>(I_C_InvoiceLine.COLUMNNAME_Line, line.getLine()))
				.create()
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.firstOnly(I_C_InvoiceLine.class);
	}

	@Override
	public List<I_C_InvoiceLine> retrieveLines(final I_M_InOutLine inoutLine)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(inoutLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(inoutLine);

		final IQueryBuilder<I_C_InvoiceLine> queryBuilder = queryBL.createQueryBuilder(I_C_InvoiceLine.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.filter(new EqualsQueryFilter<>(I_C_InvoiceLine.COLUMNNAME_M_InOutLine_ID, inoutLine.getM_InOutLine_ID()));

		queryBuilder.orderBy()
				.addColumn(I_C_InvoiceLine.COLUMNNAME_Line);

		return queryBuilder.create().list(I_C_InvoiceLine.class);
	}

	@Override
	public List<I_C_Invoice> retrievePostedWithoutFactAcct(final Properties ctx, final Date startTime)
	{
		final IQueryBL queryBL = this.queryBL;

		final String trxName = ITrx.TRXNAME_ThreadInherited;

		final IQueryBuilder<I_C_Invoice> queryBuilder = queryBL.createQueryBuilder(I_C_Invoice.class, ctx, trxName)
				.addOnlyActiveRecordsFilter();

		queryBuilder
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_Posted, true) // Posted
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_Processed, true) // Processed
				.addInArrayOrAllFilter(I_C_Invoice.COLUMNNAME_DocStatus, IDocument.STATUS_Closed, IDocument.STATUS_Completed); // DocStatus in ('CO', 'CL')

		// Exclude the entries that don't have either GrandTotal or TotalLines. These entries will produce 0 in posting
		final ICompositeQueryFilter<I_C_Invoice> nonZeroFilter = queryBL.createCompositeQueryFilter(I_C_Invoice.class).setJoinOr()
				.addNotEqualsFilter(I_C_Invoice.COLUMNNAME_GrandTotal, BigDecimal.ZERO)
				.addNotEqualsFilter(I_C_Invoice.COLUMNNAME_TotalLines, BigDecimal.ZERO);

		queryBuilder.filter(nonZeroFilter);

		// Only the documents created after the given start time
		if (startTime != null)
		{
			queryBuilder.addCompareFilter(I_C_Invoice.COLUMNNAME_Created, Operator.GREATER_OR_EQUAL, startTime);
		}

		// Check if there are fact accounts created for each document
		final IQueryBuilder<I_Fact_Acct> factAcctQuery = queryBL.createQueryBuilder(I_Fact_Acct.class, ctx, trxName)
				.addEqualsFilter(I_Fact_Acct.COLUMN_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_C_Invoice.class));

		queryBuilder
				.addNotInSubQueryFilter(I_C_Invoice.COLUMNNAME_C_Invoice_ID, I_Fact_Acct.COLUMNNAME_Record_ID, factAcctQuery.create()) // has no accounting
		;

		return queryBuilder
				.create()
				.list();

	}

	@Override
	public Iterator<I_C_Invoice> retrieveCreditMemosForInvoice(final I_C_Invoice invoice)
	{
		final List<I_C_Invoice> creditMemos = new ArrayList<>();

		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

		final Iterator<I_C_Invoice> referencesForInvoice = retrieveReferencesForInvoice(invoice);

		while (referencesForInvoice.hasNext())
		{
			final I_C_Invoice referencedInvoice = referencesForInvoice.next();

			if (invoiceBL.isCreditMemo(referencedInvoice))
			{
				creditMemos.add(referencedInvoice);
			}
		}

		return creditMemos.iterator();
	}

	@Override
	public Iterator<I_C_Invoice> retrieveAdjustmentChargesForInvoice(final I_C_Invoice invoice)
	{
		final List<I_C_Invoice> adjustmentCharges = new ArrayList<>();

		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

		final Iterator<I_C_Invoice> referencesForInvoice = retrieveReferencesForInvoice(invoice);

		while (referencesForInvoice.hasNext())
		{
			final I_C_Invoice referencedInvoice = referencesForInvoice.next();

			if (invoiceBL.isAdjustmentCharge(referencedInvoice))
			{
				adjustmentCharges.add(referencedInvoice);
			}
		}

		return adjustmentCharges.iterator();
	}

	@Override
	public boolean isReferencedInvoiceReversed(final I_C_Invoice invoice)
	{
		final org.compiere.model.I_C_Invoice referencedInvoice = getReferencedInvoice(invoice);
		final DocStatus originalInvoiceDocStatus;
		if (referencedInvoice != null)
		{
			originalInvoiceDocStatus = DocStatus.ofCode(referencedInvoice.getDocStatus());
		}
		else
		{
			originalInvoiceDocStatus = DocStatus.ofCode(invoice.getDocStatus());
		}
		return originalInvoiceDocStatus.isReversed();
	}

	@Nullable
	private org.compiere.model.I_C_Invoice getReferencedInvoice(final I_C_Invoice invoice)
	{
		if (!invoiceBL.isInvoice(invoice))
		{
			final InvoiceId invoiceId = InvoiceId.ofRepoIdOrNull(invoice.getRef_Invoice_ID());
			if (invoiceId != null)
			{
				return getByIdInTrx(invoiceId);
			}
		}
		return null;
	}

	private Iterator<I_C_Invoice> retrieveReferencesForInvoice(final I_C_Invoice invoice)
	{
		// services
		final IQueryBL queryBL = this.queryBL;

		return queryBL.createQueryBuilder(I_C_Invoice.class, invoice)
				.filterByClientId()
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_Ref_Invoice_ID, invoice.getC_Invoice_ID())

				.create()
				.iterate(I_C_Invoice.class);
	}

	@Override
	public org.compiere.model.I_C_Invoice getByIdInTrx(@NonNull final InvoiceId invoiceId)
	{
		return load(invoiceId, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public List<? extends org.compiere.model.I_C_Invoice> getByIdsInTrx(@NonNull final Collection<InvoiceId> invoiceIds)
	{
		return loadByRepoIdAwares(ImmutableSet.copyOf(invoiceIds), org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public List<org.compiere.model.I_C_Invoice> getByIdsOutOfTrx(@NonNull final Collection<InvoiceId> invoiceIds)
	{
		return loadByRepoIdAwaresOutOfTrx(ImmutableSet.copyOf(invoiceIds), org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public Stream<InvoiceId> streamInvoiceIdsByBPartnerId(@NonNull final BPartnerId bpartnerId)
	{
		return queryBL
				.createQueryBuilder(I_C_Invoice.class)
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.create()
				.listIds(InvoiceId::ofRepoId)
				.stream();
	}

	@Override
	public ImmutableMap<InvoiceId, String> getDocumentNosByInvoiceIds(@NonNull final Collection<InvoiceId> invoiceIds)
	{
		return getByIdsInTrx(invoiceIds)
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						record -> InvoiceId.ofRepoId(record.getC_Invoice_ID()),
						org.compiere.model.I_C_Invoice::getDocumentNo));
	}

	@Override
	public org.compiere.model.I_C_InvoiceLine getByIdOutOfTrx(@NonNull final InvoiceLineId invoiceLineId)
	{
		return loadOutOfTrx(invoiceLineId, I_C_InvoiceLine.class);
	}

	@Override
	@NonNull
	public ImmutableSet<InvoiceId> retainReferencingCompletedInvoices(@NonNull final Collection<InvoiceId> invoiceIds, @Nullable final DocBaseAndSubType targetDocType)
	{
		if (invoiceIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return queryBL.createQueryBuilder(I_C_Invoice.class)
				.addInArrayFilter(I_C_Invoice.COLUMNNAME_Ref_Invoice_ID, invoiceIds)
				.addInArrayFilter(I_C_Invoice.COLUMNNAME_DocStatus, DocStatus.Completed, DocStatus.Closed)
				.create()
				.stream()
				.filter(invoice -> matchesDocType(invoice, targetDocType))
				.map(I_C_Invoice::getRef_Invoice_ID)
				.map(InvoiceId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public List<I_C_InvoiceLine> retrieveReferringLines(@NonNull final InvoiceLineId invoiceLineId)
	{
		final IQueryBL queryBL = this.queryBL;
		return queryBL.createQueryBuilder(I_C_InvoiceLine.class)
				.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_Ref_InvoiceLine_ID, invoiceLineId)
				.create()
				.list();
	}

	public Optional<InvoiceId> retrieveIdByInvoiceQuery(@NonNull final InvoiceQuery query)
	{
		if (query.getInvoiceId() != null)
		{
			return Optional.of(InvoiceId.ofRepoId(query.getInvoiceId()));
		}
		if (query.getExternalId() != null)
		{
			return getInvoiceIdByExternalIdIfExists(query);
		}
		if (!Check.isEmpty(query.getDocType()))
		{
			return getInvoiceIdByDocumentIdIfExists(query);
		}
		return Optional.empty();
	}

	@Override
	public <T extends org.compiere.model.I_C_Invoice> List<T> getByDocumentNo(final String documentNo, final OrgId orgId, final Class<T> modelClass)
	{
		return queryBL
				.createQueryBuilder(modelClass)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_DocumentNo, documentNo)
				.addEqualsFilter(org.compiere.model.I_C_Invoice.COLUMNNAME_AD_Org_ID, orgId)
				.create()
				.list(modelClass);
	}

	private Optional<InvoiceId> getInvoiceIdByDocumentIdIfExists(@NonNull final InvoiceQuery query)
	{
		final String documentNo = assumeNotNull(query.getDocumentNo(), "Param query needs to have a non-null docId; query={}", query);
		final OrgId orgId = assumeNotNull(query.getOrgId(), "Param query needs to have a non-null orgId; query={}", query);
		final DocBaseAndSubType docType = assumeNotNull(query.getDocType(), "Param query needs to have a non-null docType; query={}", query);
		final String docBaseType = assumeNotNull(docType.getDocBaseType(), "Param query needs to have a non-null docBaseType; query={}", query);
		final String docSubType = docType.getDocSubType();

		final IQueryBuilder<I_C_DocType> docTypeQueryBuilder = createQueryBuilder(I_C_DocType.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_DocType.COLUMNNAME_DocBaseType, docBaseType);
		if (Check.isNotBlank(docSubType))
		{
			docTypeQueryBuilder.addEqualsFilter(I_C_DocType.COLUMNNAME_DocSubType, docSubType);
		}

		final IQueryBuilder<I_C_Invoice> queryBuilder = createQueryBuilder(I_C_Invoice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_AD_Org_ID, orgId)
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_DocumentNo, documentNo)
				.addInSubQueryFilter(I_C_Invoice.COLUMNNAME_C_DocType_ID, I_C_DocType.COLUMNNAME_C_DocType_ID, docTypeQueryBuilder.create());

		final int invoiceRepoId = queryBuilder.create().firstIdOnly();
		return Optional.ofNullable(InvoiceId.ofRepoIdOrNull(invoiceRepoId));
	}

	private Optional<InvoiceId> getInvoiceIdByExternalIdIfExists(@NonNull final InvoiceQuery query)
	{
		final ExternalId externalId = assumeNotNull(query.getExternalId(), "Param query needs to have a non-null externalId; query={}", query);
		final OrgId orgId = assumeNotNull(query.getOrgId(), "Param query needs to have a non-null orgId; query={}", query);

		final IQueryBuilder<I_C_Invoice> queryBuilder = createQueryBuilder(I_C_Invoice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_AD_Org_ID, orgId)
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_ExternalId, externalId.getValue());

		final int invoiceRepoId = queryBuilder.create().firstIdOnly();
		return Optional.ofNullable(InvoiceId.ofRepoIdOrNull(invoiceRepoId));
	}

	private <T> IQueryBuilder<T> createQueryBuilder(
			@NonNull final Class<T> modelClass)
	{
		return queryBL
				.createQueryBuilderOutOfTrx(modelClass)
				// .addOnlyActiveRecordsFilter() // don't generally rule out inactive partners
				.orderByDescending(I_AD_Org.COLUMNNAME_AD_Org_ID); // prefer "more specific" AD_Org_ID > 0;
	}

	@Override
	public List<I_C_Invoice> retrieveBySalesrepPartnerId(@NonNull final BPartnerId salesRepBPartnerId, @NonNull final InstantInterval invoicedDateInterval)
	{
		final Timestamp from = TimeUtil.asTimestamp(invoicedDateInterval.getFrom());
		final Timestamp to = TimeUtil.asTimestamp(invoicedDateInterval.getTo());

		return queryBL.createQueryBuilder(I_C_Invoice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_C_BPartner_SalesRep_ID, salesRepBPartnerId.getRepoId())
				.addBetweenFilter(I_C_Invoice.COLUMNNAME_DateInvoiced, from, to)
				.create()
				.list();
	}

	@Override
	public List<I_C_Invoice> retrieveSalesInvoiceByPartnerId(@NonNull final BPartnerId bpartnerId, @NonNull final InstantInterval invoicedDateInterval)
	{
		final Timestamp from = TimeUtil.asTimestamp(invoicedDateInterval.getFrom());
		final Timestamp to = TimeUtil.asTimestamp(invoicedDateInterval.getTo());

		return queryBL.createQueryBuilder(I_C_Invoice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_IsSOTrx, true)
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_C_BPartner_ID, bpartnerId.getRepoId())
				.addBetweenFilter(I_C_Invoice.COLUMNNAME_DateInvoiced, from, to)
				.create()
				.list();
	}

	@Override
	public Collection<InvoiceLineId> getInvoiceLineIds(final InvoiceId id)
	{
		return queryBL.createQueryBuilder(I_C_InvoiceLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, id)
				.create()
				.listIds(lineId -> InvoiceLineId.ofRepoId(id, lineId));
	}

	private boolean matchesDocType(@NonNull final I_C_Invoice serviceFeeInvoiceCandidate, @Nullable final DocBaseAndSubType targetDocType)
	{
		if (targetDocType == null)
		{
			return true;
		}

		final DocTypeId docTypeId = CoalesceUtil.coalesceNotNull(
				DocTypeId.ofRepoIdOrNull(serviceFeeInvoiceCandidate.getC_DocType_ID()),
				DocTypeId.ofRepoId(serviceFeeInvoiceCandidate.getC_DocTypeTarget_ID()));

		final I_C_DocType docTypeRecord = docTypeDAO.getById(docTypeId);

		final DocBaseAndSubType docBaseAndSubType = DocBaseAndSubType.of(docTypeRecord.getDocBaseType(), docTypeRecord.getDocSubType());

		return docBaseAndSubType.equals(targetDocType);
	}
}