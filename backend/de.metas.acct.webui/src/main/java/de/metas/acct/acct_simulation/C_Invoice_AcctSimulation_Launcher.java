package de.metas.acct.acct_simulation;

import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.view.ViewId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;

class C_Invoice_AcctSimulation_Launcher extends JavaProcess implements IProcessPrecondition
{
	private final AcctSimulationViewFactory viewsFactory = SpringContextHolder.instance.getBean(AcctSimulationViewFactory.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not a single row selection");
		}

		final InvoiceId invoiceId = context.getSingleSelectedRecordId(InvoiceId.class);
		final I_C_Invoice invoice = invoiceBL.getByIdIfExists(invoiceId).orElse(null);
		if (invoice == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not saved yet");
		}

		if (invoice.isSOTrx())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not a purchase invoice");
		}

		final DocStatus invoiceDocStatus = DocStatus.ofNullableCodeOrUnknown(invoice.getDocStatus());
		if (!invoiceDocStatus.isDraftedOrInProgress())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not draft");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final ViewId viewId = viewsFactory.createView(getRecordRef(), false).getViewId();
		getResult().setWebuiViewToOpen(ProcessExecutionResult.WebuiViewToOpen.builder()
				.viewId(viewId.getViewId())
				.target(ProcessExecutionResult.ViewOpenTarget.ModalOverlay)
				.build());
		return MSG_OK;
	}

}
