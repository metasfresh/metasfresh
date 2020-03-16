package de.metas.banking.payment.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_PaySelection;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_PaySelectionLine;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
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
	public <T extends I_C_PaySelectionLine> List<T> retrievePaySelectionLines(
			final I_C_PaySelection paySelection,
			final Class<T> clazz)
	{
		return queryPaySelectionLines(paySelection)
				.orderBy(I_C_PaySelectionLine.COLUMNNAME_Line)
				.create()
				.list(clazz);
	}

	@Override
	public <T extends I_C_PaySelectionLine> List<T> retrievePaySelectionLines(
			@NonNull final PaySelectionId paySelectionId,
			@NonNull final Class<T> clazz)
	{
		return queryPaySelectionLines(paySelectionId)
				.orderBy(I_C_PaySelectionLine.COLUMNNAME_Line)
				.create()
				.list(clazz);
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
	public List<I_C_PaySelectionLine> retrievePaySelectionLinesMatchingInvoice(final I_C_PaySelection paySelection, final I_C_Invoice invoice)
	{
		final IQueryBuilder<I_C_PaySelectionLine> queryBuilder = queryPaySelectionLines(paySelection)
				.addEqualsFilter(org.compiere.model.I_C_PaySelectionLine.COLUMNNAME_C_Invoice_ID, invoice.getC_Invoice_ID());
		return queryBuilder
				.create()
				.list(I_C_PaySelectionLine.class);
	}

	@Override
	public List<de.metas.banking.model.I_C_PaySelectionLine> retrievePaySelectionLines(I_C_BankStatementLine bankStatementLine)
	{
		return queryBL
				.createQueryBuilder(I_C_PaySelectionLine.class, bankStatementLine)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(de.metas.banking.model.I_C_PaySelectionLine.COLUMNNAME_C_BankStatementLine_ID, bankStatementLine.getC_BankStatementLine_ID())
				.create()
				.list(de.metas.banking.model.I_C_PaySelectionLine.class);
	}

	@Override
	public de.metas.banking.model.I_C_PaySelectionLine retrievePaySelectionLine(I_C_BankStatementLine_Ref bankStatementLineRef)
	{
		return queryBL
				.createQueryBuilder(I_C_PaySelectionLine.class, bankStatementLineRef)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(de.metas.banking.model.I_C_PaySelectionLine.COLUMNNAME_C_BankStatementLine_Ref_ID, bankStatementLineRef.getC_BankStatementLine_Ref_ID())
				.create()
				.firstOnly(de.metas.banking.model.I_C_PaySelectionLine.class);

	}

	@Override
	public boolean isPaySelectionLineMatchInvoice(final I_C_PaySelection paySelection, final I_C_Invoice invoice)
	{
		final IQueryBuilder<I_C_PaySelectionLine> queryBuilder = queryPaySelectionLines(paySelection)
				.addEqualsFilter(org.compiere.model.I_C_PaySelectionLine.COLUMNNAME_C_Invoice_ID, invoice.getC_Invoice_ID());
		return queryBuilder.create()
				.anyMatch();
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
	public List<I_C_PaySelectionLine> retrievePaySelectionLines(@NonNull final org.compiere.model.I_C_Invoice invoice)
	{
		final IQueryBuilder<I_C_PaySelectionLine> queryBuilder = queryBL.createQueryBuilder(I_C_PaySelectionLine.class, invoice)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PaySelectionLine.COLUMNNAME_C_Invoice_ID, invoice.getC_Invoice_ID());

		queryBuilder.orderBy()
				.addColumn(I_C_PaySelectionLine.COLUMNNAME_C_PaySelection_ID)
				.addColumn(I_C_PaySelectionLine.COLUMNNAME_Line);

		return queryBuilder
				.create()
				.list();
	}

	@Override
	public I_C_PaySelection retrievePaySelection(@NonNull final org.compiere.model.I_C_Payment payment)
	{
		return queryBL.createQueryBuilder(I_C_PaySelectionLine.class, payment)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PaySelectionLine.COLUMNNAME_C_Payment_ID, payment.getC_Payment_ID())
				//
				// Collect pay selection
				.andCollect(I_C_PaySelectionLine.COLUMN_C_PaySelection_ID)
				.addEqualsFilter(I_C_PaySelection.COLUMN_Processed, true)
				//
				.create()
				.firstOnlyOrNull(I_C_PaySelection.class);
	}

	@Override
	public de.metas.banking.model.I_C_PaySelectionLine retrievePaySelectionLineForPayment(
			@NonNull final I_C_PaySelection paySelection,
			@NonNull final PaymentId paymentId)
	{
		return queryPaySelectionLines(paySelection)
				.addEqualsFilter(I_C_PaySelectionLine.COLUMNNAME_C_Payment_ID, paymentId)
				.create()
				.firstOnly(de.metas.banking.model.I_C_PaySelectionLine.class);
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
