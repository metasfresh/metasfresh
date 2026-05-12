package de.metas.invoice;

import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

/**
 * Tri-state intent flag for an invoice's "Partial vs Final" semantics.
 *
 * <ul>
 *   <li>{@link #Yes} — the user explicitly marked the invoice as Partial; more invoices are coming
 *       on the same order. Auto-close of the linked invoice candidate must be skipped.</li>
 *   <li>{@link #No} — the user explicitly marked the invoice as Final (the cap). Legacy
 *       qty-based close logic applies in {@code closePartiallyInvoiced_InvoiceCandidates}.</li>
 *   <li>{@link #NA} — no user intent stated (NULL on the DB column). Preserves pre-iter-3
 *       behaviour: legacy qty-based close logic applies.</li>
 * </ul>
 *
 * <p>Mapped to the underlying CHAR(1) {@code C_Invoice.IsPartialInvoice} /
 * {@code C_DocType.IsPartialInvoice} columns as: {@code Y → Yes}, {@code N → No},
 * {@code null/empty → NA}.
 *
 * <p>See me03 #29369 (iter-3 split-payment) for the design rationale.
 */
public enum IsPartialInvoice
{
	Yes,
	No,
	NA;

	@NonNull
	public static IsPartialInvoice fromCode(@Nullable final String code)
	{
		if (code == null || code.isEmpty())
		{
			return NA;
		}
		switch (code)
		{
			case "Y":
				return Yes;
			case "N":
				return No;
			default:
				throw new AdempiereException("Unknown IsPartialInvoice code: " + code);
		}
	}

	/**
	 * Type-safe factory that reads from raw PO storage. The PO layer for YesNo columns stores values as
	 * {@link Boolean} (Y → Boolean.TRUE, N → Boolean.FALSE, NULL → null). For values explicitly written
	 * via {@code setValue(..., "Y"/"N")} the raw may also be a {@link String}. Handles all three cases.
	 *
	 * @throws AdempiereException if the raw value is of an unexpected type
	 */
	@NonNull
	public static IsPartialInvoice fromValue(@Nullable final Object raw)
	{
		if (raw == null)
		{
			return NA;
		}
		if (raw instanceof Boolean)
		{
			return ((Boolean)raw) ? Yes : No;
		}
		if (raw instanceof String)
		{
			return fromCode((String)raw);
		}
		throw new AdempiereException("Unexpected IsPartialInvoice raw value: " + raw + " (type: " + raw.getClass().getName() + ")");
	}

	@Nullable
	public String toCode()
	{
		switch (this)
		{
			case Yes:
				return "Y";
			case No:
				return "N";
			case NA:
				return null;
			default:
				throw new AdempiereException("Unhandled IsPartialInvoice: " + this);
		}
	}

	public boolean isYes()
	{
		return this == Yes;
	}

	public boolean isNo()
	{
		return this == No;
	}

	public boolean isNA()
	{
		return this == NA;
	}
}
