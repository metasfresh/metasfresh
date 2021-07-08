package de.metas.invoicecandidate.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAggregator;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;

/**
 * Checker used to make sure we are enqueueing and invoicing exactly the same amount that user seen on screen.
 *
 * NOTE: when calculating the total net amount to invoice checksum we don't care about currency, we use the sum of {@link I_C_Invoice_Candidate#getNetAmtToInvoice()} as a checksum exclusivelly.
 *
 * @author tsa
 * task http://dewiki908/mediawiki/index.php/08610_Make_sure_there_are_no_changes_in_enqueued_invoice_candidates_%28105439431951%29
 */
@ToString
/* package */class ICNetAmtToInvoiceChecker implements IAggregator<BigDecimal, I_C_Invoice_Candidate>
{
	private static final String SYSCONFIG_FailIfNetAmtToInvoiceChecksumNotMatch = "de.metas.invoicecandidate.api.impl.ICNetAmtToInvoiceChecker.FailIfNetAmtToInvoiceChecksumNotMatch";

	// Parameters
	private BigDecimal _netAmtToInvoiceExpected = null;

	// Status
	private BigDecimal _netAmtToInvoice = BigDecimal.ZERO;
	private int _countInvoiceCandidates = 0;

	public ICNetAmtToInvoiceChecker setNetAmtToInvoiceExpected(final BigDecimal netAmtToInvoiceExpected)
	{
		this._netAmtToInvoiceExpected = netAmtToInvoiceExpected;
		return this;
	}

	@Override
	public void add(@NonNull final I_C_Invoice_Candidate ic)
	{
		final BigDecimal icLineNetAmt = ic.getNetAmtToInvoice();
		_netAmtToInvoice = _netAmtToInvoice.add(icLineNetAmt);
		_countInvoiceCandidates++;
	}

	@Override
	public BigDecimal getValue()
	{
		return _netAmtToInvoice;
	}

	/**
	 * Asserts aggregated net amount to invoice equals with given expected value (if set).
	 *
	 * The expected amount is set using {@link #setNetAmtToInvoiceExpected(BigDecimal)}.
	 *
	 * @see #assertExpectedNetAmtToInvoice(BigDecimal)
	 */
	public void assertExpectedNetAmtToInvoiceIfSet()
	{
		if (_netAmtToInvoiceExpected == null)
		{
			return;
		}

		assertExpectedNetAmtToInvoice(_netAmtToInvoiceExpected);
	}

	/**
	 * Asserts aggregated net amount to invoice equals with given expected value.
	 *
	 * If it's not equal, an error message will be logged to {@link ILoggable}.
	 *
	 * @throws AdempiereException if the checksums are not equal and {@link #isFailIfNetAmtToInvoiceChecksumNotMatch()}.
	 */
	public void assertExpectedNetAmtToInvoice(@NonNull final BigDecimal netAmtToInvoiceExpected)
	{
		Check.assume(netAmtToInvoiceExpected.signum() != 0, "netAmtToInvoiceExpected != 0");

		final BigDecimal netAmtToInvoiceActual = getValue();

		if (netAmtToInvoiceExpected.compareTo(netAmtToInvoiceActual) != 0)
		{
			final String errmsg = "NetAmtToInvoice checksum not match"
					+ "\n Expected: " + netAmtToInvoiceExpected
					+ "\n Actual: " + netAmtToInvoiceActual
					+ "\n Invoice candidates count: " + _countInvoiceCandidates;
			Loggables.addLog(errmsg);
			if (isFailIfNetAmtToInvoiceChecksumNotMatch())
			{
				throw new AdempiereException(errmsg);
			}
		}
	}

	/**
	 * @return true if we shall fail if the net amount to invoice checksum does not match.
	 */
	public final boolean isFailIfNetAmtToInvoiceChecksumNotMatch()
	{
		final boolean failIfNetAmtToInvoiceChecksumNotMatchDefault = true;
		return Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_FailIfNetAmtToInvoiceChecksumNotMatch, failIfNetAmtToInvoiceChecksumNotMatchDefault);
	}
}
