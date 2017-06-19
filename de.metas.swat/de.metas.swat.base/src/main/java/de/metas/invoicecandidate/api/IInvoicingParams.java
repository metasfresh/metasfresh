package de.metas.invoicecandidate.api;

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
import java.sql.Timestamp;

/**
 * Invoicing Enqueueing & generating parameters.
 * 
 * @author tsa
 *
 */
public interface IInvoicingParams
{
	/**
	 * @return true if only those invoice candidates which were approved for invoicing shall be enqueued
	 */
	public abstract boolean isOnlyApprovedForInvoicing();

	/** @return true if the invoice generator shall do the best effort to consolidate the invoice candidates in a few number of invoices (idealy 1) */
	public abstract boolean isConsolidateApprovedICs();

	/** @return true if enqueuer shall ignore the scheduled DateToInvoice */
	public abstract boolean isIgnoreInvoiceSchedule();

	/**
	 * @return date invoiced to be set to all invoice candidates, right before enqueueing them
	 */
	public abstract Timestamp getDateInvoiced();

	/**
	 * @return DateAcct to be set to all invoice candidates, right before enqueueing them
	 */
	public abstract Timestamp getDateAcct();

	/**
	 * @return POReference to be set to all invoice candidates, right before enqueueing them
	 */
	public abstract String getPOReference();

	/**
	 * Gets total net amount to invoice checksum (i.e. sum of all IC's let net amount to invoice, without considering the currency).
	 * 
	 * This parameter is created and when invoice candidates to invoice workpackage is enqueued.
	 * 
	 * @return total net amount to invoice checksum
	 * @task http://dewiki908/mediawiki/index.php/08610_Make_sure_there_are_no_changes_in_enqueued_invoice_candidates_%28105439431951%29
	 */
	BigDecimal getCheck_NetAmtToInvoice();

	/**
	 * Advise the invoice generator to throw an exception if there is more than one invoice generated.
	 * 
	 * @return
	 */
	boolean isAssumeOneInvoice();

	/**
	 * Advice the invoice generator whether it should store the actual invoices in the result instance.
	 * <p>
	 * <b>note that we don't want to store the actual invoices in the result if there is a change to encounter memory problems</b>
	 * 
	 * @return
	 */
	boolean isStoreInvoicesInResult();
}
