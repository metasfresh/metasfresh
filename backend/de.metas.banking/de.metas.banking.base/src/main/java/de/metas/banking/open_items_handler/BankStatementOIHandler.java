package de.metas.banking.open_items_handler;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.AccountConceptualName;
import de.metas.acct.gljournal_sap.SAPGLJournalLine;
import de.metas.acct.open_items.FAOpenItemKey;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.acct.open_items.FAOpenItemTrxInfoComputeRequest;
import de.metas.acct.open_items.FAOpenItemsHandler;
import de.metas.acct.open_items.FAOpenItemsHandlerMatchingKey;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.BankStatementLineRefId;
import de.metas.banking.accounting.BankAccountAcctType;
import de.metas.banking.service.IBankStatementBL;
import lombok.NonNull;
import org.compiere.model.I_C_BankStatement;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class BankStatementOIHandler implements FAOpenItemsHandler
{
	private static final AccountConceptualName B_InTransit_Acct = BankAccountAcctType.B_InTransit_Acct.getAccountConceptualName();

	private final IBankStatementBL bankStatementBL;

	public BankStatementOIHandler(
			@NonNull final IBankStatementBL bankStatementBL)
	{
		this.bankStatementBL = bankStatementBL;
	}

	@Override
	public @NonNull Set<FAOpenItemsHandlerMatchingKey> getMatchers()
	{
		return ImmutableSet.of(
				FAOpenItemsHandlerMatchingKey.of(B_InTransit_Acct, I_C_BankStatement.Table_Name)
		);
	}

	@Override
	public Optional<FAOpenItemTrxInfo> computeTrxInfo(final FAOpenItemTrxInfoComputeRequest request)
	{
		final AccountConceptualName accountConceptualName = request.getAccountConceptualName();
		if (accountConceptualName == null)
		{
			return Optional.empty();
		}

		if (I_C_BankStatement.Table_Name.equals(request.getTableName()))
		{
			if (accountConceptualName.isAnyOf(B_InTransit_Acct))
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
		else
		{
			return Optional.empty();
		}

	}

	@Override
	public void onGLJournalLineCompleted(final SAPGLJournalLine line)
	{
		final FAOpenItemTrxInfo openItemTrxInfo = line.getOpenItemTrxInfo();
		if (openItemTrxInfo == null)
		{
			// shall not happen
			return;
		}

		final AccountConceptualName accountConceptualName = openItemTrxInfo.getAccountConceptualName();
		if (accountConceptualName == null)
		{
			return;
		}

		if (accountConceptualName.isAnyOf(B_InTransit_Acct))
		{
			openItemTrxInfo.getKey().getBankStatementLineId()
					.ifPresent(bankStatementLineId -> bankStatementBL.markAsReconciledWithGLJournalLine(bankStatementLineId, line.getIdNotNull()));
		}
	}

	@Override
	public void onGLJournalLineReactivated(final SAPGLJournalLine line)
	{
		final FAOpenItemTrxInfo openItemTrxInfo = line.getOpenItemTrxInfo();
		if (openItemTrxInfo == null)
		{
			// shall not happen
			return;
		}

		final AccountConceptualName accountConceptualName = openItemTrxInfo.getAccountConceptualName();
		if (accountConceptualName == null)
		{
			return;
		}

		if (accountConceptualName.isAnyOf(B_InTransit_Acct))
		{
			openItemTrxInfo.getKey().getBankStatementLineId()
					.ifPresent(bankStatementBL::markAsNotReconciledAndDeleteReferences);
		}
	}

}
