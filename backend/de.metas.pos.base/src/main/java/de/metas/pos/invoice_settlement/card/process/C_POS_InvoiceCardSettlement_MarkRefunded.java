package de.metas.pos.invoice_settlement.card.process;

import com.google.common.collect.ImmutableSet;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementId;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementService;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementStatus;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import org.compiere.SpringContextHolder;

/**
 * Back office marks a card settlement's customer credit as refunded, see {@link POSInvoiceCardSettlementService#markRefunded}.
 */
public class C_POS_InvoiceCardSettlement_MarkRefunded extends JavaProcess implements IProcessPrecondition
{
	private final POSInvoiceCardSettlementService settlementService = SpringContextHolder.instance.getBean(POSInvoiceCardSettlementService.class);

	private static final ImmutableSet<POSInvoiceCardSettlementStatus> APPLICABLE_STATUSES = ImmutableSet.of(POSInvoiceCardSettlementStatus.CREDIT_REFUND_PENDING);

	@Param(parameterName = "Note", mandatory = true)
	private String note;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		return POSInvoiceCardSettlementProcessPreconditions.acceptIfStatusIn(context, settlementService, APPLICABLE_STATUSES);
	}

	@Override
	@RunOutOfTrx // the service locks and saves in its own transaction
	protected String doIt()
	{
		settlementService.markRefunded(POSInvoiceCardSettlementId.ofRepoId(getRecord_ID()), note);
		return MSG_OK;
	}
}
