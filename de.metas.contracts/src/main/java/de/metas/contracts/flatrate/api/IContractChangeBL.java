package de.metas.contracts.flatrate.api;

/*
 * #%L
 * de.metas.contracts
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


import java.sql.Timestamp;

import org.adempiere.util.ISingletonService;

import de.metas.contracts.model.I_C_Flatrate_Term;

public interface IContractChangeBL extends ISingletonService
{
	/**
	 * Cancels the given <code>term</code> at the given <code>date</code>.
	 * <p>
	 * <b>IMPORTANT:</b> Currently, we only create term-change orders if the term has a normal order to begin with!<br>
	 * In other words, a customer won't be charged if their {@link de.metas.contracts.model.I_C_Flatrate_Conditions#COLUMNNAME_IsNewTermCreatesOrder} value is <code>='N'</code>.
	 * 
	 * <p>
	 * Note that a canceled term won't be invoiced further, as the term's <code>C_Invoice_Candidate</code> will be updated and that their <code>QtyOrdered</code> value will be set to their
	 * <code>QtyInvoiced</code> value. However, existing invoices won't be touched by the cancellation of a term.
	 * 
	 * @param term the contract term that shall be canceled. If <code>term</code> already has a successor (i.e. {@link I_C_Flatrate_Term#getC_FlatrateTerm_Next_ID()} > 0), then the successor-term is
	 *            also canceled. After successful termination,
	 *            <ul>
	 *            <li>the term's {@link I_C_Flatrate_Term#COLUMNNAME_ContractStatus} is set to <code>Qu</code> ("quit")</li> <li>the <code>C_Invoice_Candidate</code> records that reference the term,
	 *            are invalidated.</li> <li>the term's {@link I_C_Flatrate_Term#COLUMNNAME_EndDate} is updated according to the <code>changeDate</code> parameter. If the term's StartDate is after
	 *            <code>changeDate</code>, then the EndDate will be set to the StartDate. If the terms EndDate is before <code>changeDate</code>, then the EndDate won't be updated</li>
	 *            </ul>
	 * @param changeDate the cancellation date. If this this date is before the term's "regular" EndDate, it is also used to find the correct {@link de.metas.contracts.model.I_C_Contract_Change} record
	 *            for the cancel conditions.
	 * @param isCloseInvoiceCandidate this value is forwarded to the given term's <code>IsCloseInvoiceCandidate</code> column and will determine what to do with invoice candidates for the term which were not
	 *            yet (fully) invoiced. See {@link de.metas.contracts.invoicecandidate.FlatrateTermInvoiceCandidateHandler}
	 * @throws SubscriptionChangeException if <code>changeDate</code> is before the term's EndDate and if there is no {@link de.metas.contracts.model.I_C_Contract_Change} record for that date
	 */
	void cancelContract(I_C_Flatrate_Term term, Timestamp changeDate,  boolean isCloseInvoiceCandidate);
	
	/**
	 * ending naturally a contract
	 * Actually is just setting the status to Ending contract 
	 * @param term
	 */
	void endContract(I_C_Flatrate_Term term);
}
