package de.metas.payment.esr.api;

import org.compiere.model.I_C_Invoice;

import de.metas.util.ISingletonService;

public interface IESRBL extends ISingletonService
{
	void createESRPaymentRequest(I_C_Invoice invoiceRecord);
}
