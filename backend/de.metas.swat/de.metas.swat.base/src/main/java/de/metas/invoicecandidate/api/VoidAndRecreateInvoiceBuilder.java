/**
 * 
 */
package de.metas.invoicecandidate.api;

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.form.IClientUI;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Async_Batch_Type;
import de.metas.async.model.I_C_Queue_Block;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.i18n.IMsgBL;
import de.metas.invoicecandidate.async.spi.impl.InvoiceVoidAndRecreateWorkpackageProcessor;
import de.metas.process.ProcessExecutionResult.RecordsToOpen.OpenTarget;
import de.metas.process.ProcessInfo;

/**
 * @author cg
 *
 */
public class VoidAndRecreateInvoiceBuilder
{
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);

	final IClientUI factory = Services.get(IClientUI.class);

	private boolean canWarnUser = true;

	private List<I_C_Invoice> invoices;

	private int AD_PInstance_ID;

	private final Properties ctx;

	private ProcessInfo m_pi;

	private I_C_Async_Batch_Type asyncBatchType;

	/**
	 * @return the m_pi
	 */
	public ProcessInfo getProcessInfo()
	{
		return m_pi;
	}

	/**
	 * @param m_pi the m_pi to set
	 */
	public VoidAndRecreateInvoiceBuilder setProcessInfo(ProcessInfo m_pi)
	{
		this.m_pi = m_pi;
		return this;
	}

	/**
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}

	private String status;

	public VoidAndRecreateInvoiceBuilder(Properties ctx)
	{
		super();

		this.ctx = ctx;
	}

	/**
	 * Set the list of invoices the needs to be recompute
	 * 
	 * @param invoices
	 */
	public VoidAndRecreateInvoiceBuilder setInvoices(List<I_C_Invoice> invoices)
	{
		this.invoices = invoices;
		return this;
	}

	/**
	 * @param aD_PInstance_ID the aD_PInstance_ID to set
	 */
	public VoidAndRecreateInvoiceBuilder setAD_PInstance_ID(int aD_PInstance_ID)
	{
		AD_PInstance_ID = aD_PInstance_ID;
		return this;
	}

	public VoidAndRecreateInvoiceBuilder setC_Async_Batch_Type(I_C_Async_Batch_Type asyncBatchType)
	{
		this.asyncBatchType = asyncBatchType;
		return this;
	}

	/**
	 * THis param is used in order to know when a warning message can be triggered
	 * 
	 * @param canWarnUser
	 */
	public VoidAndRecreateInvoiceBuilder setCanWarnUser(boolean canWarnUser)
	{
		this.canWarnUser = canWarnUser;
		return this;
	}

	public VoidAndRecreateInvoiceBuilder build()
	{
		voidAndrecreate();
		return this;
	}

	private boolean canVoidPaidInvoice(final I_C_Invoice invoice)
	{
		final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);

		final de.metas.adempiere.model.I_C_Invoice inv = InterfaceWrapperHelper.create(invoice, de.metas.adempiere.model.I_C_Invoice.class);

		final List<I_C_Payment> availablePayments = allocationDAO.retrieveInvoicePayments(inv);

		// if there is no payment and is paid (can be payed with credit memo, but no real payment), we can not void
		return !(invoice.isPaid() && availablePayments.isEmpty());
	}

	private void enqueInvoice(final I_C_Invoice invoice, final I_C_Async_Batch asyncBatch)
	{

		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);
		final IWorkPackageQueue queue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, InvoiceVoidAndRecreateWorkpackageProcessor.class);
		I_C_Queue_Block queueBlock = null;

		if (queueBlock == null)
		{
			queueBlock = queue.enqueueBlock(ctx);
		}

		final I_C_Queue_WorkPackage queueWorkpackage = queue.enqueueWorkPackage(queueBlock, IWorkPackageQueue.PRIORITY_AUTO); // priority=null=Auto/Default

		// set the async batch in workpackage in order to track it
		queueWorkpackage.setC_Async_Batch(asyncBatch);
		Services.get(IQueueDAO.class).save(queueWorkpackage);

		queue.enqueueElement(queueWorkpackage, invoice);
		queue.markReadyForProcessing(queueWorkpackage);
	}

	private void voidAndrecreate()
	{
		// Create Async Batch for tracking

		Check.assume(AD_PInstance_ID > 0, "Process instance must be greater then 0");
		Check.assume(asyncBatchType != null, "Async batch type can not be null!");
		Check.assume(m_pi != null, "ProcessInfo can not be null!");

		final I_C_Async_Batch asyncBatch = InterfaceWrapperHelper.create(ctx, I_C_Async_Batch.class, ITrx.TRXNAME_None);
		asyncBatch.setAD_PInstance_ID(AD_PInstance_ID);
		asyncBatch.setName("Void and recreate invoices");
		asyncBatch.setC_Async_Batch_Type(asyncBatchType);
		queueDAO.save(asyncBatch);
		// is very importing the order; first enque and then set the batch
		// otherwise, will be counted also the workpackage for the batch
		asyncBatchBL.enqueueAsyncBatch(asyncBatch);

		final StringBuilder builder = new StringBuilder();

		for (final I_C_Invoice invoice : invoices)
		{

			final Timestamp now = SystemTime.asTimestamp();
			if (!TimeUtil.isSameYear(invoice.getDateInvoiced(), now)
					&& (TimeUtil.getYearFromTimestamp(invoice.getDateInvoiced()) < TimeUtil.getYearFromTimestamp(now)))
			{
				if (canWarnUser)
				{
					factory.warn(0, "C_Invoice_VoidAndRecreateInvoice_CurrentYear");
				}
				status = "@Enqueued@ 0";
				return;

			}

			if (!canVoidPaidInvoice(invoice))
			{
				if (canWarnUser)
				{
					factory.warn(0, "C_Invoice_VoidAndRecreateInvoice_IsPaid");
					InterfaceWrapperHelper.delete(asyncBatch);
				}
				status = "@Enqueued@ 0";
				return;
			}

			enqueInvoice(invoice, asyncBatch);

			if (canWarnUser)
			{
				factory.info(0, Services.get(IMsgBL.class).getMsg(ctx, "C_Invoice_VoidAndRecreateInvoice_NewInvoice", new Object[]
					{ invoice.getDocumentNo() }));
			}

			getProcessInfo().getResult().setRecordToOpen(TableRecordReference.of(invoice), 0, OpenTarget.GridView);

			builder.append("@Enqueued@ @C_Invoice_ID@ ")
					.append(invoice.getDocumentNo())
					.append("<br>");
		}

		status = builder.toString();

	}
}
