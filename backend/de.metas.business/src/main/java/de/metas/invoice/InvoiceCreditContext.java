package de.metas.invoice;

import javax.annotation.Nullable;

import de.metas.document.DocTypeId;
import de.metas.document.engine.IDocument;
import lombok.Builder;
import lombok.Value;

/**
 * Holds configuration parameters when crediting an invoice.
 */
@Value
@Builder
public class InvoiceCreditContext
{
	/**
	 * @return the DocType used to create the CreditMemo.
	 */
	@Nullable
	DocTypeId docTypeId;
	
	/**
	 * @return if <code>true</code>, then the credit memo is completed and the credit memo's <code>GrandTotal</code> is allocated against the given invoice, so that the given
	 *         invoice has <code>IsPaid='Y'</code> afterwards. If <code>false</code>, then the credit memo is only "prepared", so to that is can still be edited by a user.
	 */
	boolean completeAndAllocate;
	
	/**
	 * @return if <code>true</code>, then we copy <code>C_Invoice.C_Order_ID</code>, <code>C_Invoice.POReference</code> and <code>C_InvoiceLine.C_OrderLine_ID</code>s
	 */
	boolean referenceOriginalOrder;
	
	/**
	 * @return If <code>true</code>, then the credit memo and the invoice will reference each other via <code>Ref_CrediMemo_ID</code> (this is already the case) and if the credit memo
	 *         is completed later, it is automatically allocated against the invoice (also if it's completed right directly).<br>
	 *         Also, the credit memo will reference the invoice via <code>Ref_Invoice_ID</code>.
	 *         If <code>false</code>, then the original invoice is just used as a template, but not linked with the credit memo
	 */
	boolean referenceInvoice;
	
	/**
	 * @return if <code>true</code> then the credit memo is generated with <code>IsCreditedInvoiceReinvoicable='Y'</code>, which leads to the invoice candidates to be updated in a manner that allows
	 *         the credited quantities to be invoiced once more. Note that if the invoice's grand total is already partially allocated, this value will be override with <code>false</code>.
	 */
	boolean creditedInvoiceReinvoicable;

	/**
	 * @return if <code>true</code>, this means that this invoice can't be reversed, reactivated or voided after creation. In this case the invoice should also be completed.
	 */
	boolean fixedInvoice;
}
