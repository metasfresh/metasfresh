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
 * Implementations of this interface are responsible to track changes of a group of invoice candidates.
 *
 * @author tsa
 *
 */
public interface IInvoiceCandidatesChangesChecker
{
	/** Checker which actualy tracks NOTHING */
	IInvoiceCandidatesChangesChecker NULL = NullInvoiceCandidatesChangesChecker.instance;

	IInvoiceCandidatesChangesChecker setTotalNetAmtToInvoiceChecksum(BigDecimal totalNetAmtToInvoiceChecksum);

	/**
	 * Sets the invoice candidates that needs to be tracked, right before any changes.
	 *
	 * This is our reference when checking for changes.
	 *
	 * The implementation will take a snapshot of those invoice candidates and later it will use to compare with their new version.
	 */
	IInvoiceCandidatesChangesChecker setBeforeChanges(final Iterable<I_C_Invoice_Candidate> candidates);

	/**
	 * Asserts there were no changes, when comparing given candidates with the ones which were set by {@link #setBeforeChanges(Iterable)}.
	 *
	 * On any changes found, the change will be logged to configured logger.
	 *
	 * @param candidates
	 * @throws AdempiereException if any changes where found
	 */
	void assertNoChanges(final Iterable<I_C_Invoice_Candidate> candidates);
}
