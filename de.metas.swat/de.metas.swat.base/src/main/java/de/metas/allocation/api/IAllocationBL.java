package de.metas.allocation.api;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.ISingletonService;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_Payment;

import de.metas.adempiere.model.I_C_Invoice;

public interface IAllocationBL extends ISingletonService
{
	/**
	 * Creates a new allocation builder.
	 *
	 * @param ctxProvider an object that the {@link InterfaceWrapperHelper} can use to get the <code>ctx</code>and <code>trxName</code>.
	 * @param implClazz the allocation builder implementation to use. Other modules can bring their own implementations. The implementing class needs to have a constructor with one <code>Object</code>
	 *            where the given <code>ctxProvider</code> will be passed.
	 *            <p>
	 *            <b>IMPORTANT: when using an class that is included in another class, then this included class needs to be <code>static</code></b>
	 * @return
	 */
	<T extends DefaultAllocationBuilder> T newBuilder(IContextAware ctxProvider, Class<T> implClazz);

	/**
	 * Convenience method that calls {@link #newBuilder(Object, Class)} with the {@link DefaultAllocationBuilder} class.
	 *
	 * @param ctxProvider
	 * @return
	 */
	DefaultAllocationBuilder newBuilder(IContextAware ctxProvider);

	/**
	 * This method creates an allocation between the given invoice and incoming payments that belong to the same C_BPartner, have the {@link I_C_Payment#isAutoAllocateAvailableAmt()} flag set and are
	 * not yet fully allocated.
	 *
	 * @param invoice the invoice to allocate against.
	 * @return the created an completed allocation or <code>null</code>, if the invoice is already fully paid, or is a PO-invoice, or is a credit memo.
	 * @task 04193
	 */
	I_C_AllocationHdr autoAllocateAvailablePayments(I_C_Invoice invoice);

	/**
	 *
	 * This method creates an allocation between the given invoice and incoming payment that belong to the same C_BPartner, have the {@link I_C_Payment#isAutoAllocateAvailableAmt()} flag set and is
	 * not yet fully allocated.
	 *
	 * @param invoice the invoice to allocate against.
	 * @param payment to allocate
	 * @param ignoreIsAutoAllocateAvailableAmt if <code>false</code> then we only create the allocation if the payment has {@link I_C_Payment#COLUMN_IsAutoAllocateAvailableAmt} <code>='Y'</code>.
	 * @return the created an completed allocation or <code>null</code>, if the invoice is already fully paid, or is a PO-invoice, or is a credit memo or payment and invoice are not matching
	 * @task 07783
	 * @return
	 */
	I_C_AllocationHdr autoAllocateSpecificPayment(org.compiere.model.I_C_Invoice invoice,
			I_C_Payment payment,
			boolean ignoreIsAutoAllocateAvailableAmt);

	/**
	 *
	 * @param allocationHdr
	 * @return <code>true</code> if the given allocationHdr is the reversal of another allocationHdr.
	 */
	boolean isReversal(I_C_AllocationHdr allocationHdr);
}
