/**
 * 
 */
package de.metas.contracts;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 *<code>changeDate</code> the cancellation date. If this this date is before the term's "regular" EndDate, it is also used to find the correct {@link de.metas.contracts.model.I_C_Contract_Change} record
 * for the cancel conditions.<br>
 * <code>isCloseInvoiceCandidate</code> this value is forwarded to the given term's <code>IsCloseInvoiceCandidate</code> column and will determine what to do with invoice candidates for the term which were not
 *  yet (fully) invoiced. See {@link de.metas.contracts.invoicecandidate.FlatrateTermInvoiceCandidateHandler}<br>
 *<code>note></code> notice where additional infos are storred when cancelling the contract
 */
@Value
@Builder
public class ContractChangeParameters
{
	final Timestamp changeDate;
	final boolean isCloseInvoiceCandidate;
	final String terminationMemo;
	final String terminationReason;
}
