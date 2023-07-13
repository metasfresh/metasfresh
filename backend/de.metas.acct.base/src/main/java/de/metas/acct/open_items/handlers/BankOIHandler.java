package de.metas.acct.open_items.handlers;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.AccountConceptualName;
import de.metas.acct.api.FactAcctQuery;
import de.metas.acct.api.IFactAcctDAO;
import de.metas.acct.open_items.FAOpenItemKey;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.acct.open_items.FAOpenItemTrxInfoComputeRequest;
import de.metas.acct.open_items.FAOpenItemsHandler;
import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.PaymentAllocationLineId;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.BankStatementLineRefId;
import de.metas.banking.accounting.BankAccountAcctType;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_Payment;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@VisibleForTesting
public class BankOIHandler implements FAOpenItemsHandler
{
	private static final AccountConceptualName B_PaymentSelect_Acct = BankAccountAcctType.B_PaymentSelect_Acct.getAccountConceptualName();
	private static final AccountConceptualName B_UnallocatedCash_Acct = BankAccountAcctType.B_UnallocatedCash_Acct.getAccountConceptualName();
	private static final AccountConceptualName B_InTransit_Acct = BankAccountAcctType.B_InTransit_Acct.getAccountConceptualName();

	private final IAllocationBL allocationBL = Services.get(IAllocationBL.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

	@Override
	public @NonNull Set<AccountConceptualName> getHandledAccountConceptualNames()
	{
		return ImmutableSet.of(B_PaymentSelect_Acct, B_UnallocatedCash_Acct, B_InTransit_Acct);
	}

	@Override
	public Optional<FAOpenItemTrxInfo> computeTrxInfo(final FAOpenItemTrxInfoComputeRequest request)
	{
		switch (request.getTableName())
		{
			case I_C_AllocationHdr.Table_Name:
				return computeTrxInfoFromAllocation(request);
			case I_C_Payment.Table_Name:
				return computeTrxInfoFromPayment(request);
			case I_C_BankStatement.Table_Name:
				return computeTrxInfoFromBankStatement(request);
			default:
				return Optional.empty();
		}
	}

	private Optional<FAOpenItemTrxInfo> computeTrxInfoFromAllocation(final FAOpenItemTrxInfoComputeRequest request)
	{
		final AccountConceptualName accountConceptualName = request.getAccountConceptualName();

		if (accountConceptualName == null)
		{
			return Optional.empty();
		}
		else if (accountConceptualName.isAnyOf(B_PaymentSelect_Acct, B_UnallocatedCash_Acct))
		{
			final PaymentAllocationLineId paymentAllocationLineId = PaymentAllocationLineId.ofRepoId(request.getRecordId(), request.getLineId());

			return allocationBL.getPaymentId(paymentAllocationLineId)
					.map(paymentId -> FAOpenItemTrxInfo.clearing(FAOpenItemKey.payment(paymentId, accountConceptualName)));
		}
		else
		{
			return Optional.empty();
		}
	}

	private Optional<FAOpenItemTrxInfo> computeTrxInfoFromPayment(final FAOpenItemTrxInfoComputeRequest request)
	{
		final PaymentId paymentId = PaymentId.ofRepoId(request.getRecordId());
		final AccountConceptualName accountConceptualName = request.getAccountConceptualName();

		if (accountConceptualName == null)
		{
			return Optional.empty();
		}
		else if (accountConceptualName.isAnyOf(B_PaymentSelect_Acct, B_UnallocatedCash_Acct))
		{
			return Optional.of(FAOpenItemTrxInfo.opening(FAOpenItemKey.payment(paymentId, accountConceptualName)));
		}
		else if (accountConceptualName.isAnyOf(B_InTransit_Acct))
		{
			final I_C_Payment payment = paymentBL.getById(paymentId);
			return Optional.of(FAOpenItemTrxInfo.clearing(computeOpenItemKey_B_InTransit(payment)));
		}
		else
		{
			return Optional.empty();
		}
	}

	private static FAOpenItemKey computeOpenItemKey_B_InTransit(final I_C_Payment payment)
	{
		final BankStatementLineId bankStatementLineId = BankStatementLineId.ofRepoIdOrNull(payment.getC_BankStatementLine_ID());
		if (bankStatementLineId != null)
		{
			final BankStatementId bankStatementId = BankStatementId.ofRepoId(payment.getC_BankStatement_ID());
			final BankStatementLineRefId bankStatementLineRefId = BankStatementLineRefId.ofRepoIdOrNull(payment.getC_BankStatementLine_Ref_ID());
			return FAOpenItemKey.bankStatementLine(bankStatementId, bankStatementLineId, bankStatementLineRefId, B_InTransit_Acct);
		}
		else
		{
			return FAOpenItemKey.payment(PaymentId.ofRepoId(payment.getC_Payment_ID()), B_InTransit_Acct);
		}
	}

	private Optional<FAOpenItemTrxInfo> computeTrxInfoFromBankStatement(final FAOpenItemTrxInfoComputeRequest request)
	{
		final AccountConceptualName accountConceptualName = request.getAccountConceptualName();

		if (accountConceptualName == null)
		{
			return Optional.empty();
		}
		else if (accountConceptualName.isAnyOf(B_InTransit_Acct))
		{
			final BankStatementId bankStatementId = BankStatementId.ofRepoId(request.getRecordId());
			final BankStatementLineId bankStatementLineId = BankStatementLineId.ofRepoId(request.getLineId());
			final BankStatementLineRefId bankStatementLineRefId = BankStatementLineRefId.ofRepoIdOrNull(request.getSubLineId());
			return Optional.of(FAOpenItemTrxInfo.opening(FAOpenItemKey.bankStatementLine(bankStatementId, bankStatementLineId, bankStatementLineRefId, accountConceptualName)));
		}
		else
		{
			return Optional.empty();
		}
	}

	//
	//
	//

	@Component
	@Interceptor(I_C_Payment.class)
	public static class C_Payment_Interceptor
	{
		private final IFactAcctDAO factAcctDAO = Services.get(IFactAcctDAO.class);

		public C_Payment_Interceptor()
		{
			System.out.println("AAAA");
		}

		@ModelChange(
				timings = ModelValidator.TYPE_AFTER_CHANGE,
				ifColumnsChanged = {
						I_C_Payment.COLUMNNAME_C_BankStatement_ID,
						I_C_Payment.COLUMNNAME_C_BankStatementLine_ID,
						I_C_Payment.COLUMNNAME_C_BankStatementLine_Ref_ID
				})
		void updateOpenItemKey(final I_C_Payment payment)
		{
			final FAOpenItemKey openItemKey = computeOpenItemKey_B_InTransit(payment);
			factAcctDAO.setOpenItemKey(openItemKey, FactAcctQuery.builder()
					.tableName(I_C_Payment.Table_Name)
					.recordId(payment.getC_Payment_ID())
					.accountConceptualName(B_InTransit_Acct)
					.build());
		}
	}
}
