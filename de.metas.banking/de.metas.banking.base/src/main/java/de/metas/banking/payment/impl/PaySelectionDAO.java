package de.metas.banking.payment.impl;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;

import com.google.common.collect.ImmutableList;

import de.metas.banking.model.BankStatementAndLineAndRefId;
import de.metas.banking.model.BankStatementLineId;
import de.metas.banking.model.PaySelectionId;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.invoice.InvoiceId;
import de.metas.payment.PaymentId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * @author al
 */
public class PaySelectionDAO implements IPaySelectionDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public Optional<I_C_PaySelection> getById(@NonNull PaySelectionId paySelectionId)
	{
		// NOTE: use query by ID instead of load because we want to tolerate the case when we are asking for a pay selection which was not already saved by webui
		final I_C_PaySelection paySelectionRecord = queryBL.createQueryBuilder(I_C_PaySelection.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PaySelection.COLUMNNAME_C_PaySelection_ID, paySelectionId)
				.create()
				.firstOnlyOrNull(I_C_PaySelection.class);

		return Optional.ofNullable(paySelectionRecord);
	}

	@Override
	public void save(@NonNull final I_C_PaySelectionLine psl)
	{
		saveRecord(psl);
	}

	@Override
	public List<I_C_PaySelectionLine> retrievePaySelectionLines(@NonNull final I_C_PaySelection paySelection)
	{
		return queryPaySelectionLines(paySelection)
				.orderBy(I_C_PaySelectionLine.COLUMNNAME_Line)
				.create()
				.list();
	}

	@Override
	public List<I_C_PaySelectionLine> retrievePaySelectionLines(@NonNull final PaySelectionId paySelectionId)
	{
		return queryPaySelectionLines(paySelectionId)
				.orderBy(I_C_PaySelectionLine.COLUMNNAME_Line)
				.create()
				.list();
	}

	@Override
	public int retrievePaySelectionLinesCount(final I_C_PaySelection paySelection)
	{
		final IQueryBuilder<I_C_PaySelectionLine> queryBuilder = queryPaySelectionLines(paySelection);
		return queryBuilder.create()
				.count();
	}

	@Override
	public int retrieveLastPaySelectionLineNo(@NonNull final PaySelectionId paySelectionId)
	{
		final BigDecimal lastLineNo = queryBL
				.createQueryBuilder(I_C_PaySelectionLine.class)
				.addEqualsFilter(I_C_PaySelectionLine.COLUMNNAME_C_PaySelection_ID, paySelectionId)
				.addOnlyActiveRecordsFilter()
				.create()
				.aggregate(I_C_PaySelectionLine.COLUMNNAME_Line, Aggregate.MAX, BigDecimal.class);
		if (lastLineNo == null || lastLineNo.signum() <= 0)
		{
			return 0;
		}

		return lastLineNo.intValue();
	}

	@Override
	public List<I_C_PaySelectionLine> retrievePaySelectionLines(@NonNull final BankStatementLineId bankStatementLineId)
	{
		return queryBL.createQueryBuilder(I_C_PaySelectionLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PaySelectionLine.COLUMNNAME_C_BankStatementLine_ID, bankStatementLineId)
				.create()
				.list(I_C_PaySelectionLine.class);
	}

	@Override
	public Optional<I_C_PaySelectionLine> retrievePaySelectionLine(@NonNull final BankStatementAndLineAndRefId bankStatementLineAndRefId)
	{
		final I_C_PaySelectionLine psl = queryBL.createQueryBuilder(I_C_PaySelectionLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PaySelectionLine.COLUMNNAME_C_BankStatementLine_Ref_ID, bankStatementLineAndRefId.getBankStatementLineRefId())
				.create()
				.firstOnly(I_C_PaySelectionLine.class);

		return Optional.ofNullable(psl);
	}

	private final IQueryBuilder<I_C_PaySelectionLine> queryPaySelectionLines(@NonNull final I_C_PaySelection paySelection)
	{
		return queryBL.createQueryBuilder(I_C_PaySelectionLine.class, paySelection)
				.addEqualsFilter(I_C_PaySelectionLine.COLUMNNAME_C_PaySelection_ID, paySelection.getC_PaySelection_ID())
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient(InterfaceWrapperHelper.getCtx(paySelection));
	}

	private final IQueryBuilder<I_C_PaySelectionLine> queryPaySelectionLines(@NonNull final PaySelectionId paySelectionId)
	{
		return queryBL.createQueryBuilder(I_C_PaySelectionLine.class)
				.addEqualsFilter(I_C_PaySelectionLine.COLUMNNAME_C_PaySelection_ID, paySelectionId)
				.addOnlyActiveRecordsFilter();
	}

	@Override
	public List<I_C_PaySelectionLine> retrievePaySelectionLines(@NonNull final InvoiceId invoiceId)
	{
		return queryBL.createQueryBuilder(I_C_PaySelectionLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PaySelectionLine.COLUMNNAME_C_Invoice_ID, invoiceId)
				.orderBy(I_C_PaySelectionLine.COLUMNNAME_C_PaySelection_ID)
				.orderBy(I_C_PaySelectionLine.COLUMNNAME_Line)
				.create()
				.list();
	}

	@Override
	public Optional<I_C_PaySelection> retrieveProcessedPaySelectionContainingPayment(@NonNull final PaymentId paymentId)
	{
		final I_C_PaySelection paySelection = queryBL.createQueryBuilder(I_C_PaySelectionLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PaySelectionLine.COLUMNNAME_C_Payment_ID, paymentId)
				//
				// Collect pay selection
				.andCollect(I_C_PaySelectionLine.COLUMN_C_PaySelection_ID)
				.addEqualsFilter(I_C_PaySelection.COLUMN_Processed, true)
				//
				.create()
				.firstOnlyOrNull(I_C_PaySelection.class);

		return Optional.ofNullable(paySelection);
	}

	@Override
	public Optional<I_C_PaySelectionLine> retrievePaySelectionLineForPayment(
			@NonNull final I_C_PaySelection paySelection,
			@NonNull final PaymentId paymentId)
	{
		final I_C_PaySelectionLine paySelectionLine = queryPaySelectionLines(paySelection)
				.addEqualsFilter(I_C_PaySelectionLine.COLUMNNAME_C_Payment_ID, paymentId)
				.create()
				.firstOnly(I_C_PaySelectionLine.class);

		return Optional.ofNullable(paySelectionLine);
	}

	@Override
	public List<I_C_PaySelectionLine> retrievePaySelectionLines(@NonNull final Collection<PaymentId> paymentIds)
	{
		if (paymentIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_C_PaySelectionLine.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_PaySelectionLine.COLUMNNAME_C_Payment_ID, paymentIds)
				.orderBy(I_C_PaySelectionLine.COLUMNNAME_C_PaySelection_ID)
				.orderBy(I_C_PaySelectionLine.COLUMNNAME_Line)
				.create()
				.list();
	}

	@Override
	public IQuery<I_C_PaySelectionLine> queryActivePaySelectionLinesByInvoiceId(@NonNull final Set<InvoiceId> invoiceIds)
	{
		Check.assumeNotEmpty(invoiceIds, "invoiceIds is not empty");

		return queryBL
				.createQueryBuilder(I_C_PaySelectionLine.class)
				.addInArrayFilter(I_C_PaySelectionLine.COLUMNNAME_C_Invoice_ID, invoiceIds)
				.addOnlyActiveRecordsFilter()
				.create();
	}
}
