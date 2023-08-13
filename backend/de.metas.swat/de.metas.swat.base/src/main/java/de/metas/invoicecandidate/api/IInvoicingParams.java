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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import de.metas.invoicecandidate.api.impl.InvoicingParams;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/**
 * Invoicing Enqueueing & generating parameters.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public interface IInvoicingParams
{
	String PARA_OnlyApprovedForInvoicing = "OnlyApprovedForInvoicing";
	String PARA_IsConsolidateApprovedICs = "IsConsolidateApprovedICs";
	String PARA_IgnoreInvoiceSchedule = "IgnoreInvoiceSchedule";
	String PARA_DateInvoiced = I_C_Invoice_Candidate.COLUMNNAME_DateInvoiced;
	String PARA_SupplementMissingPaymentTermIds = "SupplementMissingPaymentTermIds";
	String PARA_DateAcct = I_C_Invoice_Candidate.COLUMNNAME_DateAcct;
	String PARA_POReference = I_C_Invoice_Candidate.COLUMNNAME_POReference;
	String PARA_Check_NetAmtToInvoice = "Check_NetAmtToInvoice";
	String PARA_IsUpdateLocationAndContactForInvoice = "IsUpdateLocationAndContactForInvoice";

	/**
	 * @return {@code true} if only those invoice candidates which were approved for invoicing shall be enqueued.
	 */
	boolean isOnlyApprovedForInvoicing();

	/**
	 * @return {@code true} if the invoice generator shall do the best effort to consolidate the invoice candidates in a few number of invoices (ideally 1.)
	 */
	boolean isConsolidateApprovedICs();

	/**
	 * @return {@code true} if the enqueuer shall ignore the scheduled DateToInvoice-
	 */
	boolean isIgnoreInvoiceSchedule();

	/**
	 * @return date invoiced to be set to all invoice candidates, right before enqueueing them.
	 */
	LocalDate getDateInvoiced();

	/**
	 * @return DateAcct to be set to all invoice candidates, right before enqueueing them.
	 */
	LocalDate getDateAcct();

	/**
	 * @return POReference to be set to all invoice candidates, right before enqueueing them.
	 */
	String getPOReference();

	/**
	 * @return {@code true} if invoice candidates with {@code C_Payment_Term_ID=null} shall get the payment term ID or some other ICs, right before enqueueing them.
	 */
	boolean isSupplementMissingPaymentTermIds();

	/**
	 * Gets total net amount to invoice checksum (i.e. sum of all IC's let net amount to invoice, without considering the currency).
	 * <p>
	 * This parameter is created and when invoice candidates to invoice workpackage is enqueued.
	 *
	 * @return total net amount to invoice checksum
	 * @task http://dewiki908/mediawiki/index.php/08610_Make_sure_there_are_no_changes_in_enqueued_invoice_candidates_%28105439431951%29
	 */
	BigDecimal getCheck_NetAmtToInvoice();

	/**
	 * @return true if the invoice generator is advised to throw an exception if there is more than one invoice generated.
	 */
	boolean isAssumeOneInvoice();

	/**
	 * Advice the invoice generator whether it should store the actual invoices in the result instance.
	 * <p>
	 * <b>note that we don't want to store the actual invoices in the result if there is a chance to encounter memory problems-</b>
	 */
	boolean isStoreInvoicesInResult();

	/**
	 * When this parameter is set on true, the invoices that are to be created will get the current users and locations of their business partners,
	 * regardless of the Bill_Location and Bill_User values set in the enqueued invoice candidates.
	 * The invoice candidates themselves will not be changed.
	 * Nevertheless, the Bill_Location_Override and Bill_User_Override will be respected if they are set in the
	 * invoice candidates.
	 */
	boolean isUpdateLocationAndContactForInvoice();

	default Map<String, ? extends Object> asMap()
	{
		final Builder<String, Object> result = ImmutableMap.<String, Object>builder();

		if (getCheck_NetAmtToInvoice() != null)
		{
			result.put(InvoicingParams.PARA_Check_NetAmtToInvoice, getCheck_NetAmtToInvoice()); // during enqueuing this result might be overwritten by a specific value
		}
		if (getDateAcct() != null)
		{
			result.put(InvoicingParams.PARA_DateAcct, getDateAcct());
		}
		if (getDateInvoiced() != null)
		{
			result.put(InvoicingParams.PARA_DateInvoiced, getDateInvoiced());
		}
		if (getPOReference() != null)
		{
			result.put(InvoicingParams.PARA_POReference, getPOReference());
		}

		result.put(InvoicingParams.PARA_IgnoreInvoiceSchedule, isIgnoreInvoiceSchedule());
		result.put(InvoicingParams.PARA_IsConsolidateApprovedICs, isConsolidateApprovedICs());
		result.put(InvoicingParams.PARA_IsUpdateLocationAndContactForInvoice, isUpdateLocationAndContactForInvoice());
		result.put(InvoicingParams.PARA_OnlyApprovedForInvoicing, isOnlyApprovedForInvoicing());
		result.put(InvoicingParams.PARA_SupplementMissingPaymentTermIds, isSupplementMissingPaymentTermIds());

		return result.build();
	}
}
