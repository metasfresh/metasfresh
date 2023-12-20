package de.metas.banking.service.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankStatementAndLineAndRefId;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.BankStatementLineRefId;
import de.metas.banking.BankStatementLineReference;
import de.metas.banking.BankStatementLineReferenceList;
import de.metas.banking.importfile.BankStatementImportFileId;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.banking.service.BankStatementCreateRequest;
import de.metas.banking.service.BankStatementLineCreateRequest;
import de.metas.banking.service.BankStatementLineRefCreateRequest;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.costing.ChargeId;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.IOrgDAO;
import de.metas.payment.PaymentId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.banking.model.I_C_BankStatement;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_Fact_Acct;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwares;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

public class BankStatementDAO implements IBankStatementDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

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
	public List<I_C_BankStatementLine> getLinesByIds(@NonNull final Set<BankStatementLineId> lineIds)
	{
		return loadByRepoIdAwares(lineIds, I_C_BankStatementLine.class);
	}

	@NonNull
	@Override
	public ImmutableSet<PaymentId> getLinesPaymentIds(@NonNull final BankStatementId bankStatementId)
	{
		final List<I_C_BankStatementLine> lines = getLinesByBankStatementId(bankStatementId);
		return lines.stream()
				.map(line -> PaymentId.ofRepoIdOrNull(line.getC_Payment_ID()))
				.filter(Objects::nonNull)
				.collect(GuavaCollectors.toImmutableSet());
	}

	@Override
	public void save(final @NonNull org.compiere.model.I_C_BankStatement bankStatement)
	{
		InterfaceWrapperHelper.save(bankStatement);
	}

	@Override
	public void save(final @NonNull I_C_BankStatementLine bankStatementLine)
	{
		InterfaceWrapperHelper.save(bankStatementLine);
	}

	@Override
	public List<I_C_BankStatementLine> getLinesByBankStatementId(@NonNull final BankStatementId bankStatementId)
	{
		return retrieveLinesQuery(bankStatementId)
				.create()
				.list();
	}

	private IQueryBuilder<I_C_BankStatementLine> retrieveLinesQuery(final BankStatementId bankStatementId)
	{
		return queryBL
				.createQueryBuilder(I_C_BankStatementLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BankStatementLine.COLUMNNAME_C_BankStatement_ID, bankStatementId)
				.orderBy(I_C_BankStatementLine.COLUMNNAME_Line)
				.orderBy(I_C_BankStatementLine.COLUMNNAME_C_BankStatementLine_ID);
	}

	@Override
	public BankStatementLineReferenceList getLineReferences(@NonNull final BankStatementLineId bankStatementLineId)
	{
		return getLineReferences(ImmutableSet.of(bankStatementLineId));
	}

	@Override
	public BankStatementLineReferenceList getLineReferences(@NonNull final Collection<BankStatementLineId> bankStatementLineIds)
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
				.id(BankStatementAndLineAndRefId.ofRepoIds(
						record.getC_BankStatement_ID(),
						record.getC_BankStatementLine_ID(),
						record.getC_BankStatementLine_Ref_ID()))
				.lineNo(record.getLine())
				.bpartnerId(BPartnerId.ofRepoId(record.getC_BPartner_ID()))
				.paymentId(PaymentId.ofRepoId(record.getC_Payment_ID()))
				.invoiceId(InvoiceId.ofRepoIdOrNull(record.getC_Invoice_ID()))
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
				.delete(/* failIfProcessed */false); // delete even if Processed=Y
	}

	@Override
	public boolean isPaymentOnBankStatement(@NonNull final PaymentId paymentId)
	{
		//
		// Check if payment is on any bank statement line reference, processed or not
		final boolean hasBankStatementLineRefs = queryBL.createQueryBuilder(I_C_BankStatementLine_Ref.class)
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
		final boolean hasBankStatementLines = queryBL.createQueryBuilder(I_C_BankStatementLine.class)
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
	public List<I_C_BankStatement> getPostedWithoutFactAcct(final Properties ctx, final Date startTime)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		// Exclude the entries that have trxAmt = 0. These entries will produce 0 in posting
		final IQueryBuilder<I_C_BankStatementLine> queryBuilder = queryBL.createQueryBuilder(I_C_BankStatementLine.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_C_BankStatementLine.COLUMNNAME_TrxAmt, BigDecimal.ZERO);

		// Check if there are fact accounts created for each document
		final IQueryBuilder<I_Fact_Acct> factAcctQuery = queryBL.createQueryBuilder(I_Fact_Acct.class, ctx, trxName)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_C_BankStatement.class));

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
	public Optional<BankStatementId> getFirstIdMatching(
			@NonNull final BankAccountId orgBankAccountId,
			@NonNull final LocalDate statementDate,
			@NonNull final String name,
			@NonNull final DocStatus docStatus)
	{
		Check.assumeNotEmpty(name, "name is not empty");

		return Optional.ofNullable(queryBL.createQueryBuilder(I_C_BankStatement.class)
										   .addEqualsFilter(I_C_BankStatement.COLUMNNAME_C_BP_BankAccount_ID, orgBankAccountId)
										   .addEqualsFilter(I_C_BankStatement.COLUMNNAME_StatementDate, statementDate)
										   .addEqualsFilter(I_C_BankStatement.COLUMNNAME_Name, name)
										   .addEqualsFilter(I_C_BankStatement.COLUMNNAME_DocStatus, docStatus.getCode())
										   .create()
										   .firstId(BankStatementId::ofRepoIdOrNull));
	}

	@Override
	@NonNull
	public BankStatementId createBankStatement(@NonNull final BankStatementCreateRequest request)
	{
		final ZoneId timeZone = orgDAO.getTimeZone(request.getOrgId());
		
		final I_C_BankStatement record = newInstance(I_C_BankStatement.class);
		record.setC_BankStatement_Import_File_ID(BankStatementImportFileId.toRepoId(request.getBankStatementImportFileId()));
		record.setEndingBalance(BigDecimal.ZERO);
		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setC_BP_BankAccount_ID(request.getOrgBankAccountId().getRepoId());
		record.setName(request.getName());
		record.setDescription(request.getDescription());
		record.setStatementDate(TimeUtil.asTimestamp(request.getStatementDate(), timeZone));
		record.setDocStatus(DocStatus.Drafted.getCode());
		record.setDocAction(IDocument.ACTION_Complete);
		record.setBeginningBalance(request.getBeginningBalance());

		final BankStatementCreateRequest.ElectronicFundsTransfer eft = request.getEft();
		if (eft != null)
		{
			record.setEftStatementDate(TimeUtil.asTimestamp(eft.getStatementDate(), timeZone));
			record.setEftStatementReference(eft.getStatementReference());
		}

		save(record);

		return BankStatementId.ofRepoId(record.getC_BankStatement_ID());
	}

	@Override
	public BankStatementLineId createBankStatementLine(@NonNull final BankStatementLineCreateRequest request)
	{
		final I_C_BankStatementLine record = newInstance(I_C_BankStatementLine.class);
		record.setC_BankStatement_ID(request.getBankStatementId().getRepoId());
		record.setAD_Org_ID(request.getOrgId().getRepoId());

		record.setLine(request.getLineNo());
		if (request.getLineNo() > 0)
		{
			record.setLine(request.getLineNo());
		}
		else
		{
			final int maxLineNo = getLastLineNo(request.getBankStatementId());
			record.setLine(maxLineNo + 10);
		}

		record.setC_BPartner_ID(BPartnerId.toRepoId(request.getBpartnerId()));
		record.setImportedBillPartnerName(request.getImportedBillPartnerName());
		record.setImportedBillPartnerIBAN(request.getImportedBillPartnerIBAN());

		record.setReferenceNo(request.getReferenceNo());
		record.setDescription(request.getLineDescription());
		record.setMemo(request.getMemo());
		
		final ZoneId timeZone = orgDAO.getTimeZone(request.getOrgId());
		record.setStatementLineDate(TimeUtil.asTimestamp(request.getStatementLineDate(), timeZone));
		record.setDateAcct(TimeUtil.asTimestamp(request.getDateAcct(), timeZone));
		record.setValutaDate(TimeUtil.asTimestamp(request.getValutaDate(), timeZone));

		record.setIsUpdateAmountsFromInvoice(request.isUpdateAmountsFromInvoice());
		record.setC_Currency_ID(request.getStatementAmt().getCurrencyId().getRepoId());
		record.setStmtAmt(request.getStatementAmt().toBigDecimal());
		record.setTrxAmt(request.getTrxAmt().toBigDecimal());
		
		record.setBankFeeAmt(request.getBankFeeAmt().toBigDecimal());
		record.setChargeAmt(request.getChargeAmt().toBigDecimal());
		record.setInterestAmt(request.getInterestAmt().toBigDecimal());
		record.setC_Charge_ID(ChargeId.toRepoId(request.getChargeId()));
		record.setDebitorOrCreditorId(request.getDebtorOrCreditorId());
		record.setC_Invoice_ID(InvoiceId.toRepoId(request.getInvoiceId()));
		record.setCurrencyRate(request.getCurrencyRate());
		record.setIsMultiplePayment(request.isMultiPayment());

		final BankStatementLineCreateRequest.ElectronicFundsTransfer eft = request.getEft();
		if (eft != null)
		{
			record.setEftTrxID(eft.getTrxId());
			record.setEftTrxType(eft.getTrxType());
			record.setEftCheckNo(eft.getCheckNo());
			record.setEftReference(eft.getReference());
			record.setEftMemo(eft.getMemo());
			record.setEftPayee(eft.getPayee());
			record.setEftPayeeAccount(eft.getPayeeAccount());
			record.setEftStatementLineDate(TimeUtil.asTimestamp(eft.getStatementLineDate()));
			record.setEftValutaDate(TimeUtil.asTimestamp(eft.getValutaDate()));
			record.setEftCurrency(eft.getCurrency());
			record.setEftAmt(eft.getAmt());
		}

		save(record);

		return BankStatementLineId.ofRepoId(record.getC_BankStatementLine_ID());
	}

	@Override
	public BankStatementLineReference createBankStatementLineRef(@NonNull final BankStatementLineRefCreateRequest request)
	{
		final I_C_BankStatementLine_Ref record = newInstance(I_C_BankStatementLine_Ref.class);

		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setC_BankStatement_ID(request.getBankStatementId().getRepoId());
		record.setC_BankStatementLine_ID(request.getBankStatementLineId().getRepoId());
		record.setProcessed(request.isProcessed());
		record.setLine(request.getLineNo());

		record.setC_BPartner_ID(request.getBpartnerId().getRepoId());
		record.setC_Payment_ID(request.getPaymentId().getRepoId());
		record.setC_Invoice_ID(InvoiceId.toRepoId(request.getInvoiceId()));

		// we store the psl's discount amount, because if we create a payment from this line, then we don't want the psl's Discount to end up as a mere underpayment.
		record.setC_Currency_ID(request.getTrxAmt().getCurrencyId().getRepoId());
		record.setTrxAmt(request.getTrxAmt().toBigDecimal());

		InterfaceWrapperHelper.saveRecord(record);

		return toBankStatementLineReference(record);
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

		queryBL.createQueryBuilder(I_C_BankStatementLine_Ref.class)
				.addEqualsFilter(I_C_BankStatementLine_Ref.COLUMNNAME_C_BankStatement_ID, bankStatementId)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_C_BankStatementLine_Ref.COLUMNNAME_Processed, processed)
				.execute();
	}

	@Override
	public int getLastLineNo(@NonNull final BankStatementId bankStatementId)
	{
		return queryBL
				.createQueryBuilder(I_C_BankStatementLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BankStatementLine.COLUMNNAME_C_BankStatement_ID, bankStatementId)
				.create()
				.maxInt(I_C_BankStatementLine.COLUMNNAME_Line);
	}
}
