package de.metas.invoicecandidate.exceptions;

import org.adempiere.exceptions.AdempiereException;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.Money;
import de.metas.quantity.Quantity;

/**
 * Exception thrown when we have an {@link I_C_Invoice_Candidate} which it cannot be invoiced fully (LineNetAmtToInvoice != PriceActual * Qty) and it's Qty is not ONE.
 *
 * @author tsa
 *
 * @task http://dewiki908/mediawiki/index.php/03908_Gutschriften_Verrechnung_%282013021410000034%29
 */
public class InvalidQtyForPartialAmtToInvoiceException extends AdempiereException
{
	public static final String MSG = "de.metas.invoicecandidate.exceptions.InvalidQtyForPartialAmtToInvoiceException";

	private static final long serialVersionUID = -5898986603226414781L;

	public InvalidQtyForPartialAmtToInvoiceException(
			final Quantity qtyToInvoice,
			final I_C_Invoice_Candidate cand,
			final Money netAmtToInvoice,
			final Money netAmtToInvoiceCalc)
	{
		super(buildMsg(qtyToInvoice, cand, netAmtToInvoice, netAmtToInvoiceCalc));
	}

	private static final String buildMsg(
			final Quantity qtyToInvoice,
			final I_C_Invoice_Candidate cand,
			final Money netAmtToInvoice,
			final Money netAmtToInvoiceCalc)
	{
		final StringBuilder sb = new StringBuilder("@" + MSG + "@");
		sb.append(" @").append(I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice).append("@:").append(qtyToInvoice);

		if (netAmtToInvoice != null || netAmtToInvoiceCalc != null)
		{
			sb.append(" @").append(I_C_Invoice_Candidate.COLUMNNAME_NetAmtToInvoice).append("@: ");
			sb.append(netAmtToInvoice == null ? "?" : netAmtToInvoice);
			sb.append("->");
			sb.append(netAmtToInvoiceCalc == null ? "?" : netAmtToInvoiceCalc);
		}

		if (cand != null)
		{
			sb.append(" (").append(cand.toString()).append(")");
		}

		return sb.toString();
	}
}
