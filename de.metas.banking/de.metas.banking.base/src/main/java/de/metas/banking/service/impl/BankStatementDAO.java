package de.metas.banking.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwares;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/*
 * #%L
 * de.metas.banking.base
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

import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_Fact_Acct;

import com.google.common.collect.ImmutableSet;

import de.metas.banking.model.BankStatementAndLineAndRefId;
import de.metas.banking.model.BankStatementId;
import de.metas.banking.model.BankStatementLineId;
import de.metas.banking.model.BankStatementLineRefId;
import de.metas.banking.model.BankStatementLineReference;
import de.metas.banking.model.BankStatementLineReferenceList;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.banking.service.BankStatementLineRefCreateRequest;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.IDocument;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.payment.PaymentId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;

public class BankStatementDAO implements IBankStatementDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	@Deprecated
	public I_C_BankStatement getById(int id)
	{
		return load(id, I_C_BankStatement.class);
	}

	@Override
	public I_C_BankStatement getById(@NonNull final BankStatementId bankStatementId)
	{
		return load(bankStatementId, I_C_BankStatement.class);
	}

	@Override
	public I_C_BankStatementLine getLineById(@NonNull final BankStatementLineId lineId)
	{
		return load(lineId, I_C_BankStatementLine.class);
	}

	@Override
	public List<I_C_BankStatementLine> getLineByIds(@NonNull final Set<BankStatementLineId> lineIds)
	{
		return loadByRepoIdAwares(lineIds, I_C_BankStatementLine.class);
	}

	@NonNull
	@Override
	public ImmutableSet<PaymentId> getLinesPaymentIds(@NonNull final BankStatementId bankStatementId)
	{
		final I_C_BankStatement bankStatement = getById(bankStatementId);
		final List<I_C_BankStatementLine> lines = retrieveLines(bankStatement);
		return lines.stream()
				.map(l -> PaymentId.ofRepoIdOrNull(l.getC_Payment_ID()))
				.filter(Objects::nonNull)
				.collect(GuavaCollectors.toImmutableSet());
	}

	@Override
	public void save(final @NonNull I_C_BankStatement bankStatement)
	{
		InterfaceWrapperHelper.save(bankStatement);
	}

	@Override
	public void save(final @NonNull I_C_BankStatementLine bankStatementLine)
	{
		InterfaceWrapperHelper.save(bankStatementLine);
	}

	@Override
	public List<I_C_BankStatementLine> retrieveLines(final I_C_BankStatement bankStatement)
	{
		return retrieveLinesQuery(bankStatement)
				.create()
				.list();
	}

	private IQueryBuilder<I_C_BankStatementLine> retrieveLinesQuery(final I_C_BankStatement bankStatement)
	{
		return queryBL
				.createQueryBuilder(I_C_BankStatementLine.class, bankStatement)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BankStatementLine.COLUMNNAME_C_BankStatement_ID, bankStatement.getC_BankStatement_ID())
				//
				.orderBy()
				.addColumn(I_C_BankStatementLine.COLUMNNAME_Line)
				.addColumn(I_C_BankStatementLine.COLUMNNAME_C_BankStatementLine_ID)
				.endOrderBy();
	}

	@Override
	public BankStatementLineReferenceList retrieveLineReferences(@NonNull final BankStatementLineId bankStatementLineId)
	{
		return retrieveLineReferences(ImmutableSet.of(bankStatementLineId));
	}

	@Override
	public BankStatementLineReferenceList retrieveLineReferences(@NonNull final Collection<BankStatementLineId> bankStatementLineIds)
	{
		if (bankStatementLineIds.isEmpty())
		{
			return BankStatementLineReferenceList.EMPTY;
		}

		return queryBL
				.createQueryBuilder(I_C_BankStatementLine_Ref.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_BankStatementLine_Ref.COLUMNNAME_C_BankStatementLine_ID, bankStatementLineIds)
				.orderBy(I_C_BankStatementLine_Ref.COLUMNNAME_C_BankStatementLine_ID)
				.orderBy(I_C_BankStatementLine_Ref.COLUMNNAME_Line)
				.orderBy(I_C_BankStatementLine_Ref.COLUMNNAME_C_BankStatementLine_Ref_ID)
				.create()
				.stream(I_C_BankStatementLine_Ref.class)
				.map(record -> toBankStatementLineReference(record))
				.collect(BankStatementLineReferenceList.collector());
	}

	private static BankStatementLineReference toBankStatementLineReference(final I_C_BankStatementLine_Ref record)
	{
		return BankStatementLineReference.builder()
				.id(extractBankStatementAndLineAndRefId(record))
				.lineNo(record.getLine())
				.bpartnerId(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID()))
				.paymentId(PaymentId.ofRepoIdOrNull(record.getC_Payment_ID()))
				.trxAmt(Money.of(record.getTrxAmt(), CurrencyId.ofRepoId(record.getC_Currency_ID())))
				.build();
	}

	@Override
	public void deleteReferencesByIds(@NonNull final Collection<BankStatementLineRefId> lineRefIds)
	{
		if (lineRefIds.isEmpty())
		{
			return;
		}

		queryBL.createQueryBuilder(I_C_BankStatementLine_Ref.class)
				.addInArrayFilter(I_C_BankStatementLine_Ref.COLUMNNAME_C_BankStatementLine_Ref_ID, lineRefIds)
				.create()
				.delete();
	}

	@Override
	public boolean isPaymentOnBankStatement(final I_C_Payment payment)
	{
		final int paymentId = payment.getC_Payment_ID();

		//
		// Check if payment is on any bank statement line reference, processed or not
		final boolean hasBankStatementLineRefs = queryBL.createQueryBuilder(I_C_BankStatementLine_Ref.class, payment)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BankStatementLine_Ref.COLUMNNAME_C_Payment_ID, paymentId)
				.create()
				.anyMatch();
		if (hasBankStatementLineRefs)
		{
			return true;
		}

		//
		// Check if payment is on any bank statement line, processed or not
		final boolean hasBankStatementLines = queryBL.createQueryBuilder(I_C_BankStatementLine.class, payment)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BankStatementLine.COLUMNNAME_C_Payment_ID, paymentId)
				.create()
				.anyMatch();
		if (hasBankStatementLines)
		{
			return true;
		}

		return false;
	}

	@Override
	public List<I_C_BankStatement> retrievePostedWithoutFactAcct(final Properties ctx, final Date startTime)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		// Exclude the entries that have trxAmt = 0. These entries will produce 0 in posting
		final IQueryBuilder<I_C_BankStatementLine> queryBuilder = queryBL.createQueryBuilder(I_C_BankStatementLine.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_C_BankStatementLine.COLUMNNAME_TrxAmt, BigDecimal.ZERO);

		// Check if there are fact accounts created for each document
		final IQueryBuilder<I_Fact_Acct> factAcctQuery = queryBL.createQueryBuilder(I_Fact_Acct.class, ctx, trxName)
				.addEqualsFilter(I_Fact_Acct.COLUMN_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_C_BankStatement.class));

		// query Builder for the bank statement

		final IQueryBuilder<I_C_BankStatement> bankStatementQuery = queryBuilder
				.andCollect(I_C_BankStatement.COLUMN_C_BankStatement_ID, I_C_BankStatement.class);

		// Only the documents created after the given start time
		if (startTime != null)
		{
			bankStatementQuery.addCompareFilter(I_C_BankStatement.COLUMNNAME_Created, Operator.GREATER_OR_EQUAL, startTime);
		}

		return bankStatementQuery.addEqualsFilter(I_C_BankStatement.COLUMNNAME_Posted, true) // Posted
				.addEqualsFilter(I_C_BankStatement.COLUMNNAME_Processed, true) // Processed
				.addInArrayOrAllFilter(I_C_BankStatement.COLUMNNAME_DocStatus, IDocument.STATUS_Closed, IDocument.STATUS_Completed) // DocStatus in ('CO', 'CL')
				.addNotInSubQueryFilter(I_C_BankStatement.COLUMNNAME_C_BankStatement_ID, I_Fact_Acct.COLUMNNAME_Record_ID, factAcctQuery.create()) // has no accounting
				.create()
				.list(I_C_BankStatement.class);
	}

	@Override
	public BankStatementLineReference createBankStatementLineRef(@NonNull final BankStatementLineRefCreateRequest request)
	{
		final I_C_BankStatementLine_Ref record = InterfaceWrapperHelper.newInstance(I_C_BankStatementLine_Ref.class);

		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setC_BankStatement_ID(request.getBankStatementId().getRepoId());
		record.setC_BankStatementLine_ID(request.getBankStatementLineId().getRepoId());
		record.setLine(request.getLineNo());

		record.setC_BPartner_ID(BPartnerId.toRepoId(request.getBpartnerId()));
		record.setC_Payment_ID(PaymentId.toRepoId(request.getPaymentId()));
		record.setC_Invoice_ID(InvoiceId.toRepoId(request.getInvoiceId()));

		// we store the psl's discount amount, because if we create a payment from this line, then we don't want the psl's Discount to end up as a mere underpayment.
		record.setC_Currency_ID(request.getTrxAmt().getCurrencyId().getRepoId());
		record.setTrxAmt(request.getTrxAmt().toBigDecimal());
		record.setDiscountAmt(BigDecimal.ZERO);
		record.setWriteOffAmt(BigDecimal.ZERO);
		record.setIsOverUnderPayment(false);
		record.setOverUnderAmt(BigDecimal.ZERO);
		saveRecord(record);

		return toBankStatementLineReference(record);
	}

	@Override
	public void save(@NonNull final BankStatementLineReference lineRef)
	{
		final I_C_BankStatementLine_Ref record = load(lineRef.getBankStatementLineRefId(), I_C_BankStatementLine_Ref.class);

		record.setAD_Org_ID(lineRef.getOrgId().getRepoId());
		record.setC_BankStatement_ID(lineRef.getBankStatementId().getRepoId());
		record.setC_BankStatementLine_ID(lineRef.getBankStatementLineId().getRepoId());
		record.setLine(lineRef.getLineNo());

		record.setC_BPartner_ID(BPartnerId.toRepoId(lineRef.getBpartnerId()));
		record.setC_Payment_ID(PaymentId.toRepoId(lineRef.getPaymentId()));
		record.setC_Invoice_ID(InvoiceId.toRepoId(lineRef.getInvoiceId()));

		// we store the psl's discount amount, because if we create a payment from this line, then we don't want the psl's Discount to end up as a mere underpayment.
		record.setC_Currency_ID(lineRef.getTrxAmt().getCurrencyId().getRepoId());
		record.setTrxAmt(lineRef.getTrxAmt().toBigDecimal());
		record.setDiscountAmt(BigDecimal.ZERO);
		record.setWriteOffAmt(BigDecimal.ZERO);
		record.setIsOverUnderPayment(false);
		record.setOverUnderAmt(BigDecimal.ZERO);
		saveRecord(record);
	}

	private void saveRecord(final @NonNull de.metas.banking.model.I_C_BankStatementLine_Ref lineOrRef)
	{
		InterfaceWrapperHelper.saveRecord(lineOrRef);
	}

	private static BankStatementAndLineAndRefId extractBankStatementAndLineAndRefId(@NonNull final I_C_BankStatementLine_Ref record)
	{
		return BankStatementAndLineAndRefId.ofRepoIds(
				record.getC_BankStatement_ID(),
				record.getC_BankStatementLine_ID(),
				record.getC_BankStatementLine_Ref_ID());
	}

	@Override
	public void updateBankStatementLinesProcessedFlag(@NonNull final BankStatementId bankStatementId, final boolean processed)
	{
		queryBL.createQueryBuilder(I_C_BankStatementLine.class)
				.addEqualsFilter(I_C_BankStatementLine.COLUMNNAME_C_BankStatement_ID, bankStatementId)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_C_BankStatementLine.COLUMNNAME_Processed, processed)
				.execute();
	}
}
