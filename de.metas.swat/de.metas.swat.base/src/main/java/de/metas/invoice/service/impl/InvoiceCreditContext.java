package de.metas.invoice.service.impl;

import de.metas.invoice.service.IInvoiceCreditContext;
import lombok.Builder;

/**
 * Holds configuration parameters when crediting an invoice. See {@link IInvoiceCreditContext}
 *
 * @author al
 *
 */

public class InvoiceCreditContext implements IInvoiceCreditContext
{
	private final int C_DocType_ID;
	private final boolean completeAndAllocate;
	private final boolean isReferenceOriginalOrder;
	private final boolean isReferenceInvoice;
	private final boolean isCreditedInvoiceReinvoicable;

	/**
	 * @param C_DocType_ID see {@link #getC_DocType_ID()}
	 * @param completeAndAllocate see {@link #completeAndAllocate()}
	 * @param isReferenceOriginalOrder see {@link #isReferenceOriginalOrder()}
	 * @param isReferenceInvoice see {@link #isReferenceInvoice()}
	 * @param isCreditedInvoiceReinvoicable see {@link #isCreditedInvoiceReinvoicable()}
	 */
	@Builder
	private InvoiceCreditContext(final int C_DocType_ID,
			final boolean completeAndAllocate,
			final boolean referenceOriginalOrder,
			final boolean referenceInvoice,
			final boolean creditedInvoiceReinvoicable)
	{
		this.C_DocType_ID = C_DocType_ID;
		this.completeAndAllocate = completeAndAllocate;
		this.isReferenceOriginalOrder = referenceOriginalOrder;
		this.isReferenceInvoice = referenceInvoice;
		this.isCreditedInvoiceReinvoicable = creditedInvoiceReinvoicable;
	}

	@Override
	public int getC_DocType_ID()
	{
		return C_DocType_ID;
	}

	@Override
	public boolean completeAndAllocate()
	{
		return completeAndAllocate;
	}

	@Override
	public boolean isReferenceOriginalOrder()
	{
		return isReferenceOriginalOrder;
	}

	@Override
	public boolean isReferenceInvoice()
	{
		return isReferenceInvoice;
	}

	@Override
	public boolean isCreditedInvoiceReinvoicable()
	{
		return isCreditedInvoiceReinvoicable;
	}
}
