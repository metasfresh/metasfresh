package de.metas.acct.open_items.handlers;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.AccountConceptualName;
import de.metas.acct.open_items.FAOpenItemKey;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.acct.open_items.FAOpenItemTrxInfoComputeRequest;
import de.metas.acct.open_items.FAOpenItemsHandler;
import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.PaymentAllocationLineId;
import de.metas.banking.accounting.BankAccountAcctType;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_Payment;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
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
					.map(paymentId -> FAOpenItemTrxInfo.clearing(FAOpenItemKey.ofTableAndRecord(I_C_Payment.Table_Name, paymentId)));
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
			return Optional.of(FAOpenItemTrxInfo.opening(FAOpenItemKey.ofTableAndRecord(I_C_Payment.Table_Name, paymentId)));
		}
		else if (accountConceptualName.isAnyOf(B_InTransit_Acct))
		{
			final I_C_Payment payment = paymentBL.getById(paymentId);
			final int bankStatementId = payment.getC_BankStatement_ID();
			final int bankStatementLineId = payment.getC_BankStatementLine_ID();
			if (bankStatementLineId > 0)
			{
				return Optional.of(FAOpenItemTrxInfo.clearing(FAOpenItemKey.ofTableRecordLineAndSubLineId(
						I_C_BankStatement.Table_Name,
						bankStatementId,
						bankStatementLineId,
						payment.getC_BankStatementLine_Ref_ID()
				)));
			}
			else
			{
				return Optional.of(FAOpenItemTrxInfo.clearing(FAOpenItemKey.ofTableAndRecord(I_C_Payment.Table_Name, paymentId)));
			}
		}
		else
		{
			return Optional.empty();
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
			return Optional.of(FAOpenItemTrxInfo.opening(FAOpenItemKey.ofTableRecordLineAndSubLineId(
					I_C_BankStatement.Table_Name,
					request.getRecordId(),
					request.getLineId(),
					request.getSubLineId()
			)));
		}
		else
		{
			return Optional.empty();
		}
	}

}
