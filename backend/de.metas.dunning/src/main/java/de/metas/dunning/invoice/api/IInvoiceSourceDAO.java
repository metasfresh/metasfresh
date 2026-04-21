package de.metas.dunning.invoice.api;

/*
 * #%L
 * de.metas.dunning
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

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;

import org.compiere.model.I_C_Invoice;

import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.model.I_C_Dunning_Candidate_Invoice_v1;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.util.ISingletonService;

/**
 * DAO methods related to {@link I_C_Invoice}s
 *
 * @author tsa
 *
 */
public interface IInvoiceSourceDAO extends ISingletonService
{
	/**
	 * Computes the invoice's due date from its payment term and {@code DateInvoiced}.
	 *
	 * <p>Contract: implementations MUST compute the value (typically via
	 * {@code paymentTermDueDate(C_PaymentTerm_ID, DateInvoiced)}); they MUST NOT
	 * simply return {@code invoice.getDueDate()}.
	 *
	 * <p>Rationale: this method is called by {@code PaymentTermBasedDueDateProvider}
	 * during invoice completion to derive the value that gets written into
	 * {@code C_Invoice.DueDate}. Reading the column would be circular (it is still
	 * {@code null} at that point) and silently break dunning for any invoice completed
	 * via {@code MInvoice.completeIt()}.
	 */
	Timestamp retrieveDueDate(org.compiere.model.I_C_Invoice invoice);

	int retrieveDueDays(PaymentTermId paymentTermId, Date dateInvoiced, Date date);

	Iterator<I_C_Dunning_Candidate_Invoice_v1> retrieveDunningCandidateInvoices(IDunningContext context);
}
