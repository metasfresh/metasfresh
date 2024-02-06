package org.adempiere.invoice.process;

import de.metas.document.DocBaseAndSubType;
import de.metas.document.IDocTypeDAO;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.impl.AdjustmentChargeCreateRequest;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import org.compiere.model.I_C_DocType;
import org.compiere.model.X_C_DocType;

public class CreateAdjustmentChargeFromInvoice extends JavaProcess implements IProcessPrecondition
{
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

	@Param(mandatory = true, parameterName = "C_DocType_ID")
	private int p_C_DocType_ID;

	@Override
	protected String doIt() throws Exception
	{
		final I_C_DocType docType = docTypeDAO.getRecordById(p_C_DocType_ID);

		final DocBaseAndSubType docBaseAndSubType = DocBaseAndSubType.of(X_C_DocType.DOCBASETYPE_ARInvoice, docType.getDocSubType());

		final AdjustmentChargeCreateRequest adjustmentChargeCreateRequest = AdjustmentChargeCreateRequest.builder()
				.invoiceID(InvoiceId.ofRepoId(getRecord_ID()))
				.docBaseAndSubTYpe(docBaseAndSubType)
				.build();

		invoiceBL.adjustmentCharge(adjustmentChargeCreateRequest);

		return MSG_OK;
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{

		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}
}
