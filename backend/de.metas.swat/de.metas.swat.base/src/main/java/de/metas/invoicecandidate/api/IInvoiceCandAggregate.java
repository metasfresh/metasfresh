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
import java.util.Collection;
import java.util.List;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IAggregator;

/**
 * This interface declares an n:m relation between {@link I_C_Invoice_Candidate}s and {@link IInvoiceLineRW}s.
 * 
 * Note that we can expect the system to create one in an invoice run invoice either one <code>C_InvoiceLine</code> for every {@link IInvoiceLineRW} contained in a given aggregate instance or, in case
 * of an error, creating none of them and marking all affected invoice validates ass erroneous.
 * 
 */
public interface IInvoiceCandAggregate
{
	/**
	 * @return source invoice candidates
	 */
	public Collection<I_C_Invoice_Candidate> getAllCands();

	/**
	 * 
	 * @param il
	 * @return source invoice candidates for the given 'il'
	 */
	public List<I_C_Invoice_Candidate> getCandsFor(IInvoiceLineRW il);

	public Collection<IInvoiceLineRW> getAllLines();

	/**
	 * 
	 * @param ic
	 * @return all lines that have the given 'ic' as source invoice candidate
	 * @throws IllegalArgumentException if no {@link IInvoiceLineRW} were matched for given <code>ic</code>
	 */
	public List<IInvoiceLineRW> getLinesFor(I_C_Invoice_Candidate ic);

	/**
	 * 
	 * @param ic
	 * @return all lines that have the given 'ic' as source invoice candidate; empty list is returned if no <code>ic</code> is matched
	 */
	List<IInvoiceLineRW> getLinesForOrEmpty(I_C_Invoice_Candidate ic);

	/**
	 * By calling this method, an {@link IAggregator} implementation adds the given ic and il to this aggregate and at the same time specifies that they belong together. Note that this method is
	 * assumed to be used by {@link IAggregator}s.
	 * <p>
	 * About possible relations
	 * <ul>
	 * <li>an aggregator may create more than one {@link IInvoiceLineRW} for one {@link I_C_Invoice_Candidate}, i.e.<br/>
	 * <code>{(c1, il1), (c1, il2)}</code> is a legal relation</li>
	 * <li>an aggregator might aggregate more than one I_C_Invoice_Candidate into one IInvoiceLine, i.e.<br/>
	 * <code>{(c1, il1), (c2, il1)}</code> (that's the original reason we called it "aggregator")</li>
	 * <li>it is also allowed to have a combination of the two, i.e. something like<br/>
	 * <code>{(c1, il1), (c1, il2), (c2, il1), (c2, il2)}</code></li>
	 * </ul>
	 * 
	 * Note
	 * <ul>
	 * <li>the method assumes that every (ic, il) pair is associated only once
	 * <li>the method also sets the qty allocated between the given ic and il to be the {@link IInvoiceLineRW#getQtyToInvoice() QtyToInvoice} value of the given <code>il</code>. See
	 * {@link #getAllocatedQty(I_C_Invoice_Candidate, IInvoiceLineRW)}.
	 * </ul>
	 * 
	 * @param ic
	 * @param il
	 */
	void addAssociation(I_C_Invoice_Candidate ic, IInvoiceLineRW il);

	/**
	 * Adds <code>ic</code> to <code>il</code> association if not already exists
	 * 
	 * @param ic invoice candidate
	 * @param il invoice line
	 * @return <code>true</code> if association was created; <code>false</code> if association was not created because already exists
	 * @see #isAssociated(I_C_Invoice_Candidate, IInvoiceLineRW)
	 * @see #addAssociation(I_C_Invoice_Candidate, IInvoiceLineRW)
	 */
	boolean addAssociationIfNotExists(I_C_Invoice_Candidate ic, IInvoiceLineRW il);

	/**
	 * 
	 * @param ic
	 * @param il
	 * @return <code>true</code> isf the given <code>ic</code> was already associated with the given <code>il</code>
	 * 
	 * @see #addAssociation(I_C_Invoice_Candidate, IInvoiceLineRW)
	 * @see #addAssociation(I_C_Invoice_Candidate, IInvoiceLineRW, BigDecimal)F
	 */
	boolean isAssociated(I_C_Invoice_Candidate ic, IInvoiceLineRW il);

	/**
	 * 
	 * @param cand
	 * @param ilVO
	 * @return
	 */
	public BigDecimal getAllocatedQty(I_C_Invoice_Candidate cand, IInvoiceLineRW ilVO);

	/**
	 * Update IC to IL allocated quantity by adding <code>qtyAllocatedToAdd</code>.
	 * 
	 * This method assumes an IC-IL association already exists and we are just adjusting the quantity which was allocated.
	 * 
	 * @param ic invoice candidate
	 * @param il invoice line
	 */
	void addAllocatedQty(final I_C_Invoice_Candidate ic, final IInvoiceLineRW il, final BigDecimal qtyAllocatedToAdd);

	/**
	 * Negate amounts on all lines
	 */
	public void negateLineAmounts();

}
