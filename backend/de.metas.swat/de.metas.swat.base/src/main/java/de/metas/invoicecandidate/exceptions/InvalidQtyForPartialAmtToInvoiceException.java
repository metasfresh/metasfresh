package de.metas.invoicecandidate.exceptions;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

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

	/**
	 * 
	 */
	private static final long serialVersionUID = -5898986603226414781L;

	public InvalidQtyForPartialAmtToInvoiceException(final BigDecimal qtyToInvoice, final I_C_Invoice_Candidate cand,
			BigDecimal netAmtToInvoice, BigDecimal netAmtToInvoiceCalc)
	{
		super(buildMsg(qtyToInvoice, cand, netAmtToInvoice, netAmtToInvoiceCalc));
		// this.qtyToInvoice = qtyToInvoice;
		// this.cand = cand;
	}

	private static final String buildMsg(final BigDecimal qtyToInvoice, final I_C_Invoice_Candidate cand,
			BigDecimal netAmtToInvoice, BigDecimal netAmtToInvoiceCalc)
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
