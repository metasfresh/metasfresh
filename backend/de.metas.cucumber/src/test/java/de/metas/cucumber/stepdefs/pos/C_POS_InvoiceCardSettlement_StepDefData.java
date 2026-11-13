package de.metas.cucumber.stepdefs.pos;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementId;
import de.metas.pos.repository.model.I_C_POS_InvoiceCardSettlement;

public class C_POS_InvoiceCardSettlement_StepDefData extends StepDefData<I_C_POS_InvoiceCardSettlement>
		implements StepDefDataGetIdAware<POSInvoiceCardSettlementId, I_C_POS_InvoiceCardSettlement>
{
	public C_POS_InvoiceCardSettlement_StepDefData()
	{
		super(I_C_POS_InvoiceCardSettlement.class);
	}

	@Override
	public POSInvoiceCardSettlementId extractIdFromRecord(final I_C_POS_InvoiceCardSettlement record)
	{
		return POSInvoiceCardSettlementId.ofRepoId(record.getC_POS_InvoiceCardSettlement_ID());
	}
}
