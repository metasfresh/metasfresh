package de.metas.invoice.service;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import java.util.Date;

import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;

import de.metas.quantity.StockQtyAndUOMQty;

/**
 * {@link I_M_MatchInv} builder.
 *
 * @author tsa
 */
public interface IMatchInvBuilder
{
	/**
	 * Creates and process the {@link I_M_MatchInv}.
	 *
	 * @return <ul>
	 *         <li>true if the {@link I_M_MatchInv} was created and processed.
	 *         <li>false if there was NO need to create the matching.
	 *         </ul>
	 */
	boolean build();

	IMatchInvBuilder setContext(final Object contextProvider);

	IMatchInvBuilder setC_InvoiceLine(final I_C_InvoiceLine invoiceLine);

	IMatchInvBuilder setM_InOutLine(final I_M_InOutLine inoutLine);

	/**
	 * Sets if the matching creation shall be skipped if there exists at least one matching between invoice line and inout line.
	 *
	 * @param skipIfMatchingsAlreadyExist
	 */
	IMatchInvBuilder setSkipIfMatchingsAlreadyExist(final boolean skipIfMatchingsAlreadyExist);

	/**
	 * Set the exact quantity which shall be matched.
	 *
	 * When then quantity to be matched is specified, then the builder:
	 * <ul>
	 * <li>will NOT check and validate again previous matched quantities, i.e. {@link #setConsiderQtysAlreadyMatched(boolean)} will be ignored.
	 * <li>{@link #setAllowQtysOfOppositeSigns(boolean)} will not be considered because makes no sense.
	 * <li>regular invoice/credit memo and regular inout/material returns will NOT be checked if they are compatible.
	 * </ul>
	 */
	IMatchInvBuilder setQtyToMatchExact(final StockQtyAndUOMQty qtyToMatchExact);

	/**
	 * Sets if previous matched quantities shall be considered when calculating how much it can be allocated.
	 *
	 * NOTE:
	 * <ul>
	 * <li>by default, this option is enabled
	 * <li>this option has no effect if the quantity to be matched was specified (see {@link #setQtyToMatchExact(BigDecimal)})
	 * </ul>
	 *
	 * @param considerQtysAlreadyMatched
	 */
	IMatchInvBuilder setConsiderQtysAlreadyMatched(final boolean considerQtysAlreadyMatched);

	/**
	 * Sets if "quantity invoiced but not matched" and "quantity shipped/received but not matched" shall be matched when they are of opposite signs.
	 *
	 * If <code>false</code>, no matching will be performed.
	 *
	 * @param allowQtysOfOppositeSigns
	 */
	IMatchInvBuilder setAllowQtysOfOppositeSigns(final boolean allowQtysOfOppositeSigns);

	/**
	 * Sets DateTrx to be used (optional).
	 *
	 * @param dateTrx
	 */
	IMatchInvBuilder setDateTrx(final Date dateTrx);
}
