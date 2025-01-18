package de.metas.invoice;

public enum InvoicePaymentStatus
{
	NOT_PAID,
	PARTIALLY_PAID,
	FULLY_PAID,
	;

	public boolean isFullyPaid() {return this == FULLY_PAID;}

	public boolean isPartiallyPaid() {return this == PARTIALLY_PAID;}
}
