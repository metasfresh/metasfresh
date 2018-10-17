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

	void createESRPaymentRequest(I_C_Invoice invoiceRecord);
}
