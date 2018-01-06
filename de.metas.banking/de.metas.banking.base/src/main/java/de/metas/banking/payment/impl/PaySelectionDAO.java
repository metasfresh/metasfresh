package de.metas.banking.payment.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_PaySelection;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_PaySelectionLine;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.banking.payment.IPaySelectionDAO;

/**
 * @author al
 */
public class PaySelectionDAO implements IPaySelectionDAO
{
	@Override
	public <T extends I_C_PaySelectionLine> List<T> retrievePaySelectionLines(
			final I_C_PaySelection paySelection,
			final Class<T> clazz)
	{
		final IQueryBuilder<I_C_PaySelectionLine> queryBuilder = createQueryBuilder(paySelection);
		queryBuilder.orderBy().addColumn(I_C_PaySelectionLine.COLUMNNAME_Line, Direction.Ascending, Nulls.Last).endOrderBy();
		return queryBuilder.create().list(clazz);
	}

	@Override
	public int retrievePaySelectionLinesCount(final I_C_PaySelection paySelection)
	{
		final IQueryBuilder<I_C_PaySelectionLine> queryBuilder = createQueryBuilder(paySelection);
		return queryBuilder.create()
				.count();
	}

	@Override
	public int retrieveLastPaySelectionLineNo(final Properties ctx, final int paySelectionId, final String trxName)
	{
		final BigDecimal lastLineNo = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PaySelectionLine.class, ctx, trxName)
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
		final IQueryBuilder<I_C_PaySelectionLine> queryBuilder = createQueryBuilder(paySelection)
				.addEqualsFilter(org.compiere.model.I_C_PaySelectionLine.COLUMNNAME_C_Invoice_ID, invoice.getC_Invoice_ID());
		return queryBuilder
				.create()
				.list(I_C_PaySelectionLine.class);
	}

	@Override
	public List<de.metas.banking.model.I_C_PaySelectionLine> retrievePaySelectionLines(I_C_BankStatementLine bankStatementLine)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PaySelectionLine.class, bankStatementLine)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(de.metas.banking.model.I_C_PaySelectionLine.COLUMNNAME_C_BankStatementLine_ID, bankStatementLine.getC_BankStatementLine_ID())
				.create()
				.list(de.metas.banking.model.I_C_PaySelectionLine.class);
	}

	@Override
	public de.metas.banking.model.I_C_PaySelectionLine retrievePaySelectionLine(I_C_BankStatementLine_Ref bankStatementLineRef)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PaySelectionLine.class, bankStatementLineRef)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(de.metas.banking.model.I_C_PaySelectionLine.COLUMNNAME_C_BankStatementLine_Ref_ID, bankStatementLineRef.getC_BankStatementLine_Ref_ID())
				.create()
				.firstOnly(de.metas.banking.model.I_C_PaySelectionLine.class);

	}

	@Override
	public boolean isPaySelectionLineMatchInvoice(final I_C_PaySelection paySelection, final I_C_Invoice invoice)
	{
		final IQueryBuilder<I_C_PaySelectionLine> queryBuilder = createQueryBuilder(paySelection)
				.addEqualsFilter(org.compiere.model.I_C_PaySelectionLine.COLUMNNAME_C_Invoice_ID, invoice.getC_Invoice_ID());
		return queryBuilder.create()
				.match();
	}

	private final IQueryBuilder<I_C_PaySelectionLine> createQueryBuilder(final I_C_PaySelection paySelection)
	{
		final IQueryBuilder<I_C_PaySelectionLine> queryBuilder =
				Services.get(IQueryBL.class).createQueryBuilder(I_C_PaySelectionLine.class, paySelection);

		queryBuilder.addEqualsFilter(org.compiere.model.I_C_PaySelectionLine.COLUMNNAME_C_PaySelection_ID, paySelection.getC_PaySelection_ID())
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient(InterfaceWrapperHelper.getCtx(paySelection));
		return queryBuilder;
	}

	@Override
	public List<I_C_PaySelectionLine> retrievePaySelectionLines(final org.compiere.model.I_C_Invoice invoice)
	{
		Check.assumeNotNull(invoice, "Param 'invoice' is not null");

		final IQueryBuilder<I_C_PaySelectionLine> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_PaySelectionLine.class, invoice)
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
	public I_C_PaySelection retrievePaySelection(final org.compiere.model.I_C_Payment payment)
	{
		Check.assumeNotNull(payment, "payment not null");

		return Services.get(IQueryBL.class).createQueryBuilder(I_C_PaySelectionLine.class, payment)
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
	public de.metas.banking.model.I_C_PaySelectionLine retrievePaySelectionLineForPayment(final I_C_PaySelection paySelection, final int paymentId)
	{
		return createQueryBuilder(paySelection)
				.addEqualsFilter(I_C_PaySelectionLine.COLUMNNAME_C_Payment_ID, paymentId)
				.create()
				.firstOnly(de.metas.banking.model.I_C_PaySelectionLine.class);
	}
}
