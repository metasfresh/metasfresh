package de.metas.invoicecandidate.async.spi.impl;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.PO;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.util.Services;

public class InvoiceVoidAndRecreateWorkpackageProcessor implements IWorkpackageProcessor
{
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName)
	{
		final List<PO> invoicesOfPackage = queueDAO.retrieveItems(workPackage, PO.class, localTrxName);

		if (invoicesOfPackage.size() == 0)
		{
			throw new AdempiereException("Not item for current workpackage!");
		}

		for (final PO po : invoicesOfPackage)
		{
			final I_C_Invoice oldInvoice = InterfaceWrapperHelper.create(po, I_C_Invoice.class);

			final I_C_Invoice newInvoice = invoiceCandBL.voidAndRecreateInvoice(oldInvoice);
			if (newInvoice == null)
			{
				throw new AdempiereException("No invoice was created for invoice " + oldInvoice.getDocumentNo());
			}
		}
		return Result.SUCCESS;
	}
}
