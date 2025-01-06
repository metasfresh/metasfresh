package de.metas.allocation.api.impl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.allocation.api.InvoiceOpenRequest;
import de.metas.allocation.api.InvoiceOpenResult;
import de.metas.allocation.api.MoneyWithInvoiceFlags;
import de.metas.allocation.api.PaymentAllocationId;
import de.metas.allocation.api.PaymentAllocationLineId;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheTrx;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.PaymentDirection;
import de.metas.payment.PaymentId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_GL_Journal;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class AllocationDAO implements IAllocationDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public void save(@NonNull final I_C_AllocationHdr allocationHdr)
	{
		saveRecord(allocationHdr);
	}

	@Override
	public void save(@NonNull final I_C_AllocationLine allocationLine)
	{
		saveRecord(allocationLine);
	}

	@Override
	public final Money retrieveOpenAmtInInvoiceCurrency(
			@NonNull final I_C_Invoice invoice,
			final boolean isCreditMemoAdjust)
	{
		final CurrencyId invoiceCurrencyId = CurrencyId.ofRepoId(invoice.getC_Currency_ID());
		if (invoice.isPaid())
		{
			return Money.zero(invoiceCurrencyId);
		}

		final InvoiceOpenResult invoiceOpenResult = retrieveInvoiceOpen(InvoiceOpenRequest.builder()
				.invoiceId(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()))
				.dateColumn(InvoiceOpenRequest.DateColumn.DateTrx)
				.returnInCurrencyId(invoiceCurrencyId)
				.build());

		final Money grandTotal = invoiceOpenResult.getInvoiceGrandTotal().withoutAPAdjusted().withoutCMAdjusted().toMoney();
		final Money allocated = invoiceOpenResult.getAllocatedAmt().withoutAPAdjusted().withoutCMAdjusted().toMoney();

		// FIXME: figure out why we subtracting the absolute value of allocated amount
		// For now we are preverving as-is for regression.
		// That's the reason we are not using:
		// return invoiceOpenResult.getOpenAmt().withoutAPAdjusted().withCMAdjustedIf(isCreditMemoAdjust).toMoney();
		//
		final Money openAmt = grandTotal.subtract(allocated.abs()); // subtracting the absolute allocated amount
		return openAmt.negateIf(isCreditMemoAdjust && invoiceOpenResult.isCreditMemo());
	}

	@Override
	public final List<I_C_AllocationLine> retrieveAllocationLines(final I_C_Invoice invoice)
	{
		return queryBL.createQueryBuilder(I_C_AllocationLine.class, invoice)
				.addEqualsFilter(I_C_AllocationLine.COLUMN_C_Invoice_ID, invoice.getC_Invoice_ID())
				.addEqualsFilter(I_C_AllocationLine.COLUMNNAME_AD_Client_ID, invoice.getAD_Client_ID())
				.addOnlyActiveRecordsFilter()
				.orderBy(I_C_AllocationLine.COLUMN_C_AllocationLine_ID)
				.list();
	}

	@Override
	public final List<I_C_AllocationLine> retrieveLines(final I_C_AllocationHdr allocHdr)
	{
		final int allocationHdrId = allocHdr.getC_AllocationHdr_ID();
		final Properties ctx = InterfaceWrapperHelper.getCtx(allocHdr);
		final String trxName = InterfaceWrapperHelper.getTrxName(allocHdr);

		final boolean retrieveAll = false;
		return retrieveLines(ctx, allocationHdrId, retrieveAll, trxName);
	}

	@Override
	public final List<I_C_AllocationLine> retrieveAllLines(final I_C_AllocationHdr allocHdr)
	{
		final int allocationHdrId = allocHdr.getC_AllocationHdr_ID();
		final Properties ctx = InterfaceWrapperHelper.getCtx(allocHdr);
		final String trxName = InterfaceWrapperHelper.getTrxName(allocHdr);

		final boolean retrieveAll = true;
		return retrieveLines(ctx, allocationHdrId, retrieveAll, trxName);
	}

	@Cached(cacheName = I_C_AllocationLine.Table_Name + "#By#" + I_C_AllocationLine.COLUMNNAME_C_AllocationHdr_ID + "#retrieveAll")
		/* package */ List<I_C_AllocationLine> retrieveLines(final @CacheCtx Properties ctx,
															 final int allocationHdrId,
															 final boolean retrieveAll,
															 final @CacheTrx String trxName)
	{
		final IQueryBuilder<I_C_AllocationLine> builder = queryBL
				.createQueryBuilder(I_C_AllocationLine.class, ctx, trxName)
				.addEqualsFilter(I_C_AllocationLine.COLUMN_C_AllocationHdr_ID, allocationHdrId);

		if (!retrieveAll)
		{
			builder
					.addOnlyActiveRecordsFilter()
					.addOnlyContextClient();
		}
		return builder
				.orderBy()
				.addColumn(I_C_AllocationLine.COLUMN_C_AllocationLine_ID)
				.endOrderBy()
				.create()
				.list(I_C_AllocationLine.class);
	}

	@Override
	public final List<I_C_Payment> retrieveAvailablePaymentsToAutoAllocate(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final SOTrx invoiceSOTrx,
			@NonNull final ClientAndOrgId invoiceClientAndOrgId)
	{
		final PaymentDirection paymentDirection = PaymentDirection.ofSOTrx(invoiceSOTrx);

		return queryBL.createQueryBuilder(I_C_Payment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Payment.COLUMNNAME_AD_Client_ID, invoiceClientAndOrgId.getClientId())
				.addEqualsFilter(I_C_Payment.COLUMNNAME_AD_Org_ID, invoiceClientAndOrgId.getOrgId()) // consider only invoice's organization
				.addEqualsFilter(I_C_Payment.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addEqualsFilter(I_C_Payment.COLUMNNAME_DocStatus, DocStatus.Completed)
				.addEqualsFilter(I_C_Payment.COLUMNNAME_Processed, true)
				.addEqualsFilter(I_C_Payment.COLUMNNAME_IsReceipt, paymentDirection.isReceipt())
				.addEqualsFilter(I_C_Payment.COLUMNNAME_IsAutoAllocateAvailableAmt, true)
				.addEqualsFilter(I_C_Payment.COLUMNNAME_IsAllocated, false)
				.orderBy(I_C_Payment.COLUMNNAME_DateTrx)
				.orderBy(I_C_Payment.COLUMNNAME_C_Payment_ID)
				.create()
				.list(I_C_Payment.class);
	}

	@Override
	public BigDecimal retrieveAllocatedAmt(@NonNull final I_C_Invoice invoiceRecord)
	{
		return retrieveAllocatedAmtIgnoreGivenPaymentIDs(invoiceRecord, ImmutableSet.of());
	}

	@Override
	public BigDecimal retrieveAllocatedAmtIgnoreGivenPaymentIDs(@NonNull final I_C_Invoice invoice, @Nullable final Set<PaymentId> paymentIDsToIgnore)
	{
		final InvoiceOpenResult result = retrieveInvoiceOpen(InvoiceOpenRequest.builder()
				.invoiceId(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()))
				.dateColumn(InvoiceOpenRequest.DateColumn.DateTrx)
				.returnInCurrencyId(CurrencyId.ofRepoId(invoice.getC_Currency_ID())) // just to make it verbose
				.excludePaymentIds(paymentIDsToIgnore)
				.build());

		// backward compatibility: method is expected to return amounts which are not AP adjusted
		return result.getAllocatedAmt().withoutAPAdjusted().withoutCMAdjusted().toBigDecimal();
	}

	@Override
	public InvoiceOpenResult retrieveInvoiceOpen(@NonNull final InvoiceOpenRequest request)
	{
		final String sqlExcludePaymentIds = request.getExcludePaymentIds() != null && !request.getExcludePaymentIds().isEmpty()
				? DB.TO_ARRAY(request.getExcludePaymentIds())
				: "NULL";

		final String dateType;
		switch (request.getDateColumn())
		{
			case DateAcct:
				dateType = "A";
				break;
			case DateTrx:
			default:
				dateType = "T";
				break;
		}

		final String sql = "SELECT * FROM invoiceOpenToDate("
				+ "  p_c_invoice_id :=?"
				+ ", p_C_InvoicePaySchedule_ID := NULL"
				+ ", p_DateType := ?"
				+ ", p_Date := NULL"
				+ ", p_Result_Currency_ID := ?"
				+ ", p_Exclude_Payment_IDs := " + sqlExcludePaymentIds
				+ ", p_ReturnNullOnError := 'N'"
				+ ")";
		final List<Object> sqlParams = Arrays.asList(
				request.getInvoiceId(),
				dateType,
				request.getReturnInCurrencyId()
		);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Object[] resultParts = null;
		try
		{

			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			if (!rs.next()) // assume we always have a result
			{
				throw new AdempiereException("No result");
			}

			final BigDecimal grandTotalBD = rs.getBigDecimal("GrandTotal");
			final BigDecimal openAmtBD = rs.getBigDecimal("OpenAmt");
			final BigDecimal allocatedAmtBD = rs.getBigDecimal("PaidAmt");
			final CurrencyId currencyId = CurrencyId.ofRepoId(rs.getInt("C_Currency_ID"));
			final boolean hasAllocations = StringUtils.toBoolean(rs.getString("HasAllocations"));
			final InvoiceDocBaseType docBaseType = InvoiceDocBaseType.ofCode(rs.getString("InvoiceDocBaseType"));
			return InvoiceOpenResult.builder()
					.invoiceDocBaseType(docBaseType)
					.invoiceGrandTotal(MoneyWithInvoiceFlags.builder()
							.docBaseType(docBaseType)
							.value(Money.of(grandTotalBD, currencyId))
							.isAPAdjusted(false)
							.isCMAjusted(true)
							.build())
					.allocatedAmt(MoneyWithInvoiceFlags.builder()
							.docBaseType(docBaseType)
							.value(Money.of(allocatedAmtBD, currencyId))
							.isAPAdjusted(true)
							.isCMAjusted(false)
							.build())
					.openAmt(MoneyWithInvoiceFlags.builder()
							.docBaseType(docBaseType)
							.value(Money.of(openAmtBD, currencyId))
							.isAPAdjusted(true)
							.isCMAjusted(true)
							.build())
					.hasAllocations(hasAllocations)
					.build();
		}
		catch (SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams)
					.setParameter("resultParts", resultParts);
		}
		catch (Exception otherEx)
		{
			throw new AdempiereException("Cannot determine open amount for " + request, otherEx)
					.setParameter("sql", sql)
					.setParameter("sqlParams", sqlParams)
					.setParameter("resultParts", resultParts);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Override
	public BigDecimal retrieveWriteoffAmt(I_C_Invoice invoice)
	{
		final String sql = "select invoicewriteoff(?)";

		final BigDecimal amt = DB.getSQLValueBD(ITrx.TRXNAME_ThreadInherited, sql, invoice.getC_Invoice_ID());
		if (amt == null)
		{
			return BigDecimal.ZERO;
		}

		return amt;
	}

	@Override
	public List<I_C_AllocationHdr> retrievePostedWithoutFactAcct(final Properties ctx, final Date startTime)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		// Exclude the entries that don't have either Amount, DiscountAmt, WriteOffAmt or OverUnderAmt. These entries will produce 0 in posting
		final ICompositeQueryFilter<I_C_AllocationLine> nonZeroFilter = queryBL.createCompositeQueryFilter(I_C_AllocationLine.class).setJoinOr()
				.addNotEqualsFilter(I_C_AllocationLine.COLUMNNAME_Amount, BigDecimal.ZERO)
				.addNotEqualsFilter(I_C_AllocationLine.COLUMNNAME_DiscountAmt, BigDecimal.ZERO)
				.addNotEqualsFilter(I_C_AllocationLine.COLUMNNAME_WriteOffAmt, BigDecimal.ZERO)
				.addNotEqualsFilter(I_C_AllocationLine.COLUMNNAME_OverUnderAmt, BigDecimal.ZERO)
				.addNotEqualsFilter(I_C_AllocationLine.COLUMN_PaymentWriteOffAmt, BigDecimal.ZERO);

		// exclude credit memos, adjustment charges and reversals
		final IQuery<I_C_AllocationHdr> approvedAmtFilter = queryBL.createQueryBuilder(I_C_AllocationHdr.class, ctx, trxName)
				.addNotEqualsFilter(I_C_AllocationHdr.COLUMN_ApprovalAmt, BigDecimal.ZERO)
				.create();

		// the allocation header must have lines with the payment set or have a non 0 approvalAmt to be eligible for posting
		final ICompositeQueryFilter<I_C_AllocationLine> paymentFilter = queryBL.createCompositeQueryFilter(I_C_AllocationLine.class).setJoinOr()
				.addNotEqualsFilter(I_C_AllocationLine.COLUMNNAME_C_Payment_ID, null)
				.addInSubQueryFilter(I_C_AllocationLine.COLUMN_C_AllocationHdr_ID, I_C_AllocationHdr.COLUMN_C_AllocationHdr_ID, approvedAmtFilter);

		final IQueryBuilder<I_C_AllocationLine> queryBuilder = queryBL.createQueryBuilder(I_C_AllocationLine.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.filter(nonZeroFilter)
				.filter(paymentFilter);

		// Check if there are fact accounts created for each document
		final IQuery<I_Fact_Acct> factAcctQuery = queryBL.createQueryBuilder(I_Fact_Acct.class, ctx, trxName)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_C_AllocationHdr.class))
				.create();

		// Query builder for the allocation header
		final IQueryBuilder<I_C_AllocationHdr> allocationHdrQuery = queryBuilder
				.andCollect(I_C_AllocationHdr.COLUMN_C_AllocationHdr_ID, I_C_AllocationHdr.class);

		// Only the documents created after the given start time
		if (startTime != null)
		{
			allocationHdrQuery.addCompareFilter(I_C_AllocationHdr.COLUMNNAME_Created, Operator.GREATER_OR_EQUAL, startTime);
		}

		return allocationHdrQuery
				.addEqualsFilter(I_C_AllocationHdr.COLUMNNAME_Posted, true) // Posted
				.addEqualsFilter(I_C_AllocationHdr.COLUMNNAME_Processed, true) // Processed
				.addInArrayOrAllFilter(I_GL_Journal.COLUMNNAME_DocStatus, DocStatus.completedOrClosedStatuses())
				.addNotInSubQueryFilter(I_C_AllocationHdr.COLUMNNAME_C_AllocationHdr_ID, I_Fact_Acct.COLUMNNAME_Record_ID, factAcctQuery) // has no accounting
				.create()
				.list(I_C_AllocationHdr.class);

	}

	@Override
	public List<I_C_Payment> retrieveInvoicePayments(@NonNull final I_C_Invoice invoice)
	{
		// add invoice check in allocation line
		final IQuery<I_C_AllocationLine> invoiceAllocationLineFilter = queryBL.createQueryBuilder(I_C_AllocationLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_AllocationLine.COLUMN_C_Invoice_ID, invoice.getC_Invoice_ID())
				.create();

		final ICompositeQueryFilter<I_C_Payment> invoiceOrAllocFilter = queryBL.createCompositeQueryFilter(I_C_Payment.class)
				.setJoinOr()
				.addEqualsFilter(I_C_Payment.COLUMN_C_Invoice_ID, invoice.getC_Invoice_ID()) // add explicit invoice in payment
				.addInSubQueryFilter(I_C_Payment.COLUMNNAME_C_Payment_ID, I_C_AllocationLine.COLUMNNAME_C_Payment_ID, invoiceAllocationLineFilter);

		return queryBL.createQueryBuilder(I_C_Payment.class, invoice)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Payment.COLUMNNAME_C_BPartner_ID, invoice.getC_BPartner_ID())
				.addEqualsFilter(I_C_Payment.COLUMN_DocStatus, DocStatus.Completed)
				.addEqualsFilter(I_C_Payment.COLUMN_Processed, true)
				.addEqualsFilter(I_C_Payment.COLUMN_IsReceipt, invoice.isSOTrx())
				.addEqualsFilter(I_C_Payment.COLUMN_IsAllocated, true)
				.filter(invoiceOrAllocFilter)
				.orderBy().addColumn(I_C_Payment.COLUMN_DateTrx).endOrderBy()
				.create()
				.list();
	}

	@Override
	public SetMultimap<PaymentId, InvoiceId> retrieveInvoiceIdsByPaymentIds(@NonNull final Collection<PaymentId> paymentIds)
	{
		if (paymentIds.isEmpty())
		{
			return ImmutableSetMultimap.of();
		}

		final IQuery<I_C_AllocationHdr> completedAllocations = queryBL.createQueryBuilder(I_C_AllocationHdr.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_AllocationHdr.COLUMNNAME_DocStatus, DocStatus.Completed, DocStatus.Closed)
				.create();

		return queryBL.createQueryBuilder(I_C_AllocationLine.class)
				.addOnlyActiveRecordsFilter()
				.addNotNull(I_C_AllocationLine.COLUMNNAME_C_Invoice_ID)
				.addNotNull(I_C_AllocationLine.COLUMNNAME_C_Payment_ID)
				.addInArrayFilter(I_C_AllocationLine.COLUMNNAME_C_Payment_ID, paymentIds)
				.addInSubQueryFilter(I_C_AllocationLine.COLUMN_C_AllocationHdr_ID, I_C_AllocationHdr.COLUMN_C_AllocationHdr_ID, completedAllocations)
				.create()
				.list()
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						record -> PaymentId.ofRepoId(record.getC_Payment_ID()),
						record -> InvoiceId.ofRepoIdOrNull(record.getC_Invoice_ID())));
	}

	@Override
	public @NonNull I_C_AllocationHdr getById(@NonNull final PaymentAllocationId allocationId)
	{
		return InterfaceWrapperHelper.load(allocationId, I_C_AllocationHdr.class);
	}

	@Override
	public @NonNull I_C_AllocationLine getLineById(@NonNull final PaymentAllocationLineId lineId)
	{
		return queryBL.createQueryBuilder(I_C_AllocationLine.class)
				.addEqualsFilter(I_C_AllocationLine.COLUMNNAME_C_AllocationHdr_ID, lineId.getHeaderId())
				.addEqualsFilter(I_C_AllocationLine.COLUMNNAME_C_AllocationLine_ID, lineId.getRepoId())
				.create()
				.firstOnlyNotNull();
	}

	@Override
	@NonNull
	public List<I_C_AllocationLine> retrieveAllPaymentAllocationLines(final @NonNull PaymentId paymentId)
	{
		return queryBL.createQueryBuilder(I_C_AllocationLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_AllocationLine.COLUMNNAME_C_Payment_ID, paymentId)
				.create()
				.list();
	}
}
