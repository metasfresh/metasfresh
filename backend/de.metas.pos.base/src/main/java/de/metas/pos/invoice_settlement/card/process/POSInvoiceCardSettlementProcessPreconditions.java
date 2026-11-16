package de.metas.pos.invoice_settlement.card.process;

import com.google.common.collect.ImmutableSet;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementId;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementService;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementStatus;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 * The common precondition of the back-office processes of the card settlement window: one settlement is selected,
 * and its status is one the process applies to. Further rules of a process are checked when it runs.
 */
@UtilityClass
class POSInvoiceCardSettlementProcessPreconditions
{
	static ProcessPreconditionsResolution acceptIfStatusIn(
			@NonNull final IProcessPreconditionsContext context,
			@NonNull final POSInvoiceCardSettlementService settlementService,
			@NonNull final ImmutableSet<POSInvoiceCardSettlementStatus> applicableStatuses)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final POSInvoiceCardSettlementId id = POSInvoiceCardSettlementId.ofRepoId(context.getSingleSelectedRecordId());
		final POSInvoiceCardSettlementStatus status = settlementService.getById(id).getStatus();
		if (!applicableStatuses.contains(status))
		{
			// internal: the process is hidden for this row, not shown greyed out
			return ProcessPreconditionsResolution.rejectWithInternalReason(POSInvoiceCardSettlementService.MSG_NotPossibleForStatus);
		}

		return ProcessPreconditionsResolution.accept();
	}
}
