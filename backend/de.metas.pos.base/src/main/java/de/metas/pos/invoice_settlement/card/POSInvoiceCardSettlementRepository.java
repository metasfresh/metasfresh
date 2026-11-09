package de.metas.pos.invoice_settlement.card;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.sumup.SumUpTransactionId;
import de.metas.payment.sumup.SumUpTransactionStatus;
import de.metas.payment.sumup.repository.model.I_SUMUP_Transaction;
import de.metas.pos.POSTerminalId;
import de.metas.pos.repository.model.I_C_POS_InvoiceCardSettlement;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ForUpdate;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class POSInvoiceCardSettlementRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	public POSInvoiceCardSettlement create(
			@NonNull final OrgId orgId,
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final InvoiceId invoiceId,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final UserId cashierId,
			@NonNull final Money amount)
	{
		final I_C_POS_InvoiceCardSettlement record = InterfaceWrapperHelper.newInstance(I_C_POS_InvoiceCardSettlement.class);
		record.setAD_Org_ID(orgId.getRepoId());
		record.setC_POS_ID(posTerminalId.getRepoId());
		record.setC_Invoice_ID(invoiceId.getRepoId());
		record.setC_BPartner_ID(bpartnerId.getRepoId());
		record.setCashier_ID(cashierId.getRepoId());
		record.setAmount(amount.toBigDecimal());
		record.setC_Currency_ID(amount.getCurrencyId().getRepoId());
		record.setStatus(POSInvoiceCardSettlementStatus.PENDING.getCode());
		record.setIsAbandoned(false);
		record.setBookingAttempts(0);
		InterfaceWrapperHelper.save(record);
		return fromRecord(record);
	}

	public POSInvoiceCardSettlement getById(@NonNull final POSInvoiceCardSettlementId id)
	{
		return fromRecord(InterfaceWrapperHelper.load(id, I_C_POS_InvoiceCardSettlement.class));
	}

	/**
	 * Loads the settlement under a {@code FOR NO KEY UPDATE} row lock (same lock as
	 * {@code POSInvoiceSettlementService.getByIdInTrxWithRowLock}); the settlement's primary key never changes, so the
	 * weaker lock suffices. Requires a transaction (asserted: outside one the
	 * lock would be released immediately).
	 */
	public POSInvoiceCardSettlement getByIdForUpdate(@NonNull final POSInvoiceCardSettlementId id)
	{
		trxManager.assertThreadInheritedTrxExists();
		final I_C_POS_InvoiceCardSettlement record = queryBL.createQueryBuilder(I_C_POS_InvoiceCardSettlement.class)
				.addEqualsFilter(I_C_POS_InvoiceCardSettlement.COLUMNNAME_C_POS_InvoiceCardSettlement_ID, id)
				.create()
				.setForUpdate(ForUpdate.FOR_NO_KEY_UPDATE)
				.firstOnlyNotNull(I_C_POS_InvoiceCardSettlement.class);
		return fromRecord(record);
	}

	/**
	 * Writes all columns of the given value object; it must be called under the row lock taken by
	 * {@link #getByIdForUpdate}, otherwise a stale value object can overwrite concurrent changes.
	 */
	public POSInvoiceCardSettlement save(@NonNull final POSInvoiceCardSettlement settlement)
	{
		final I_C_POS_InvoiceCardSettlement record = InterfaceWrapperHelper.load(settlement.getId(), I_C_POS_InvoiceCardSettlement.class);
		record.setC_POS_ID(settlement.getPosTerminalId().getRepoId());
		record.setC_Invoice_ID(settlement.getInvoiceId().getRepoId());
		record.setC_BPartner_ID(settlement.getBpartnerId().getRepoId());
		record.setCashier_ID(settlement.getCashierId().getRepoId());
		record.setAmount(settlement.getAmount().toBigDecimal());
		record.setC_Currency_ID(settlement.getAmount().getCurrencyId().getRepoId());
		record.setStatus(settlement.getStatus().getCode());
		record.setIsAbandoned(settlement.isAbandoned());
		record.setErrorMsg(settlement.getErrorMsg());
		record.setC_Payment_ID(PaymentId.toRepoId(settlement.getPaymentId()));
		record.setSUMUP_Transaction_ID(SumUpTransactionId.toRepoId(settlement.getSumUpTransactionId()));
		record.setBookingAttempts(settlement.getBookingAttempts());
		record.setNote(settlement.getNote());
		InterfaceWrapperHelper.save(record);
		return fromRecord(record);
	}

	/**
	 * Conditional status change: updates only if the current status equals {@code expected}.
	 * Lock + read + save, so it is atomic only inside a transaction (asserted).
	 *
	 * @return true if the status was changed, false if the current status was not {@code expected}
	 */
	public boolean changeStatus(
			@NonNull final POSInvoiceCardSettlementId id,
			@NonNull final POSInvoiceCardSettlementStatus expected,
			@NonNull final POSInvoiceCardSettlementStatus newStatus)
	{
		trxManager.assertThreadInheritedTrxExists();
		final I_C_POS_InvoiceCardSettlement record = queryBL.createQueryBuilder(I_C_POS_InvoiceCardSettlement.class)
				.addEqualsFilter(I_C_POS_InvoiceCardSettlement.COLUMNNAME_C_POS_InvoiceCardSettlement_ID, id)
				.addEqualsFilter(I_C_POS_InvoiceCardSettlement.COLUMNNAME_Status, expected.getCode())
				.create()
				.setForUpdate(ForUpdate.FOR_NO_KEY_UPDATE)
				.firstOnly(I_C_POS_InvoiceCardSettlement.class);
		if (record == null)
		{
			return false;
		}

		record.setStatus(newStatus.getCode());
		InterfaceWrapperHelper.save(record);
		return true;
	}

	/**
	 * @return all settlements of the invoice, whatever their status, oldest first
	 */
	public List<POSInvoiceCardSettlement> listByInvoiceId(@NonNull final InvoiceId invoiceId)
	{
		return queryBL.createQueryBuilder(I_C_POS_InvoiceCardSettlement.class)
				.addEqualsFilter(I_C_POS_InvoiceCardSettlement.COLUMNNAME_C_Invoice_ID, invoiceId)
				.orderBy(I_C_POS_InvoiceCardSettlement.COLUMNNAME_C_POS_InvoiceCardSettlement_ID)
				.create()
				.stream()
				.map(POSInvoiceCardSettlementRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public Optional<POSInvoiceCardSettlement> findBlockingByInvoiceId(@NonNull final InvoiceId invoiceId)
	{
		return queryBL.createQueryBuilder(I_C_POS_InvoiceCardSettlement.class)
				.addEqualsFilter(I_C_POS_InvoiceCardSettlement.COLUMNNAME_C_Invoice_ID, invoiceId)
				.addInArrayFilter(I_C_POS_InvoiceCardSettlement.COLUMNNAME_Status, blockingCandidateCodes())
				.addEqualsFilter(I_C_POS_InvoiceCardSettlement.COLUMNNAME_IsAbandoned, false)
				.orderBy(I_C_POS_InvoiceCardSettlement.COLUMNNAME_C_POS_InvoiceCardSettlement_ID)
				.create()
				.firstOnlyOptional(I_C_POS_InvoiceCardSettlement.class)
				.map(POSInvoiceCardSettlementRepository::fromRecord);
	}

	/**
	 * Sweep candidates: PE, UN, AP, plus ABANDONED settlements whose linked SumUp transaction is
	 * still PENDING, or SUCCESSFUL and not refunded ({@code RefundAmt = 0}). A refunded one stays abandoned and leaves the sweep set.
	 * The SumUp transaction is linked via the back-reference {@code SUMUP_Transaction.C_POS_InvoiceCardSettlement_ID}
	 * (set at send time); later steps must resolve the SumUp transaction the same way.
	 */
	public Stream<POSInvoiceCardSettlementId> streamSweepCandidateIds()
	{
		// live = still PENDING, or SUCCESSFUL and not (even partly) refunded; a refunded one stays abandoned
		final ICompositeQueryFilter<I_SUMUP_Transaction> pending = queryBL.createCompositeQueryFilter(I_SUMUP_Transaction.class)
				.addEqualsFilter(I_SUMUP_Transaction.COLUMNNAME_Status, SumUpTransactionStatus.PENDING.getCode());
		final ICompositeQueryFilter<I_SUMUP_Transaction> successfulNotRefunded = queryBL.createCompositeQueryFilter(I_SUMUP_Transaction.class)
				.addEqualsFilter(I_SUMUP_Transaction.COLUMNNAME_Status, SumUpTransactionStatus.SUCCESSFUL.getCode())
				.addEqualsFilter(I_SUMUP_Transaction.COLUMNNAME_RefundAmt, BigDecimal.ZERO);
		final IQuery<I_SUMUP_Transaction> liveSumUpTransactions = queryBL.createQueryBuilder(I_SUMUP_Transaction.class)
				.filter(queryBL.createCompositeQueryFilter(I_SUMUP_Transaction.class)
						.setJoinOr()
						.addFilter(pending)
						.addFilter(successfulNotRefunded))
				.create();

		final ICompositeQueryFilter<I_C_POS_InvoiceCardSettlement> openStatuses = queryBL.createCompositeQueryFilter(I_C_POS_InvoiceCardSettlement.class)
				.addInArrayFilter(I_C_POS_InvoiceCardSettlement.COLUMNNAME_Status,
						POSInvoiceCardSettlementStatus.PENDING,
						POSInvoiceCardSettlementStatus.UNKNOWN,
						POSInvoiceCardSettlementStatus.APPROVED);
		final ICompositeQueryFilter<I_C_POS_InvoiceCardSettlement> abandonedWithLiveTransaction = queryBL.createCompositeQueryFilter(I_C_POS_InvoiceCardSettlement.class)
				.addEqualsFilter(I_C_POS_InvoiceCardSettlement.COLUMNNAME_Status, POSInvoiceCardSettlementStatus.ABANDONED)
				.addInSubQueryFilter(
						I_C_POS_InvoiceCardSettlement.COLUMNNAME_C_POS_InvoiceCardSettlement_ID,
						I_SUMUP_Transaction.COLUMNNAME_C_POS_InvoiceCardSettlement_ID,
						liveSumUpTransactions);

		final IQueryBuilder<I_C_POS_InvoiceCardSettlement> queryBuilder = queryBL.createQueryBuilder(I_C_POS_InvoiceCardSettlement.class)
				.filter(queryBL.createCompositeQueryFilter(I_C_POS_InvoiceCardSettlement.class)
						.setJoinOr()
						.addFilter(openStatuses)
						.addFilter(abandonedWithLiveTransaction));

		return queryBuilder
				.orderBy(I_C_POS_InvoiceCardSettlement.COLUMNNAME_C_POS_InvoiceCardSettlement_ID)
				.create()
				.listIds(POSInvoiceCardSettlementId::ofRepoId)
				.stream();
	}

	private static String[] blockingCandidateCodes()
	{
		return Arrays.stream(POSInvoiceCardSettlementStatus.values())
				.filter(POSInvoiceCardSettlementStatus::isBlockingCandidate)
				.map(POSInvoiceCardSettlementStatus::getCode)
				.toArray(String[]::new);
	}

	private static POSInvoiceCardSettlement fromRecord(@NonNull final I_C_POS_InvoiceCardSettlement record)
	{
		return POSInvoiceCardSettlement.builder()
				.id(POSInvoiceCardSettlementId.ofRepoId(record.getC_POS_InvoiceCardSettlement_ID()))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(record.getAD_Client_ID(), record.getAD_Org_ID()))
				.posTerminalId(POSTerminalId.ofRepoId(record.getC_POS_ID()))
				.invoiceId(InvoiceId.ofRepoId(record.getC_Invoice_ID()))
				.bpartnerId(BPartnerId.ofRepoId(record.getC_BPartner_ID()))
				.cashierId(UserId.ofRepoId(record.getCashier_ID()))
				.amount(Money.of(record.getAmount(), CurrencyId.ofRepoId(record.getC_Currency_ID())))
				.status(POSInvoiceCardSettlementStatus.ofCode(record.getStatus()))
				.isAbandoned(record.isAbandoned())
				.errorMsg(record.getErrorMsg())
				.paymentId(PaymentId.ofRepoIdOrNull(record.getC_Payment_ID()))
				.sumUpTransactionId(SumUpTransactionId.ofRepoIdOrNull(record.getSUMUP_Transaction_ID()))
				.bookingAttempts(record.getBookingAttempts())
				.note(record.getNote())
				.created(record.getCreated().toInstant())
				.build();
	}
}
