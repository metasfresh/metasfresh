package de.metas.payment.esr.api;

import org.compiere.model.I_C_Invoice;

import de.metas.util.ISingletonService;

public interface IESRBL extends ISingletonService
{
	/**
	 * This method checks if the given {@code sourceModel} should receive ESR document reference strings (see package {@code de.metas.payment.esr.document}). The method assumes that the given PO has
	 * tableName = C_Invoice.
	 */
	boolean appliesForESRDocumentRefId(Object sourceModel);

	/**
	 * @return true if the payment request was created. Note that if it's not created the reason in logged to {@link de.metas.util.ILoggable}.
	 */
	boolean createESRPaymentRequest(I_C_Invoice invoiceRecord);
}
