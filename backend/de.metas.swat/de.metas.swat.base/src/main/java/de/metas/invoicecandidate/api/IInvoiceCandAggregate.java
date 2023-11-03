package de.metas.invoicecandidate.api;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IAggregator;
import de.metas.quantity.StockQtyAndUOMQty;

import java.util.Collection;
import java.util.List;

/**
 * This interface declares an n:m relation between {@link I_C_Invoice_Candidate}s and {@link IInvoiceLineRW}s.
 *⚡ also consultet with teo. we'd like to leave it that way.
 * Note that we can expect the system to create one in an invoice run invoice either one <code>C_InvoiceLine</code> for every {@link IInvoiceLineRW} contained in a given aggregate instance or, in case
 * of an error, creating none of them and marking all affected invoice validates ass erroneous.
 *
 */
public interface IInvoiceCandAggregate
{
	/**
	 * @return source invoice candidates
	 */
	Collection<I_C_Invoice_Candidate> getAllCands();

	/**
	 * @return source invoice candidates for the given 'il'
	 */
	List<I_C_Invoice_Candidate> getCandsFor(IInvoiceLineRW il);

	Collection<IInvoiceLineRW> getAllLines();

	/**
	 *
	 * @return all lines that have the given 'ic' as source invoice candidate
	 * @throws IllegalArgumentException if no {@link IInvoiceLineRW} were matched for given <code>ic</code>
	 */
	List<IInvoiceLineRW> getLinesFor(I_C_Invoice_Candidate ic);

	/**
	 *
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
	 * <li>the method also sets the qty allocated between the given ic and il to be the {@link IInvoiceLineRW#getQtysToInvoice() QtysToInvoice} value of the given <code>il</code>. See
	 * {@link #getAllocatedQty(I_C_Invoice_Candidate, IInvoiceLineRW)}.
	 * </ul>
	 */
	void addAssociation(I_C_Invoice_Candidate ic, IInvoiceLineRW il, StockQtyAndUOMQty qtyAllocatedToAdd);

	/**
	 * Adds <code>ic</code> to <code>il</code> association if not already exists
	 *
	 * @return <code>true</code> if association was created; <code>false</code> if association was not created because already exists
	 * @see #isAssociated(I_C_Invoice_Candidate, IInvoiceLineRW)
	 * @see #addAssociation(I_C_Invoice_Candidate, IInvoiceLineRW, StockQtyAndUOMQty) 
	 */
	boolean addAssociationIfNotExists(I_C_Invoice_Candidate ic, IInvoiceLineRW il, StockQtyAndUOMQty qtyAllocatedToAdd);

	/**
	 * @return <code>true</code> isf the given <code>ic</code> was already associated with the given <code>il</code>
	 *
	 * @see #addAssociation(I_C_Invoice_Candidate, IInvoiceLineRW, StockQtyAndUOMQty) 
	 */
	boolean isAssociated(I_C_Invoice_Candidate ic, IInvoiceLineRW il);

	StockQtyAndUOMQty getAllocatedQty(I_C_Invoice_Candidate cand, IInvoiceLineRW ilVO);

	/**
	 * Update IC to IL allocated quantity by adding <code>qtyAllocatedToAdd</code>.
	 * <p>
	 * This method assumes an IC-IL association already exists and we are just adjusting the quantity which was allocated.
	 */
	void addAllocatedQty(I_C_Invoice_Candidate ic, IInvoiceLineRW il, StockQtyAndUOMQty qtyAllocatedToAdd);

	/**
	 * Negate amounts on all lines
	 */
	void negateLineAmounts();

}
