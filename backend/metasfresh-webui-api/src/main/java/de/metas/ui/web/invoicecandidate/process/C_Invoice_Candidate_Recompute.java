package de.metas.ui.web.invoicecandidate.process;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.IQuery;

import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.util.Services;

public class C_Invoice_Candidate_Recompute extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	final IInvoiceCandDAO invCandDAO = Services.get(IInvoiceCandDAO.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_C_Invoice_Candidate> queryFilterOrElseFalse = getProcessInfo().getQueryFilterOrElseFalse();

		final IQuery<I_C_Invoice_Candidate> query = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate.class).filter(queryFilterOrElseFalse).create();

		invCandDAO.invalidateCandsFor(query);

		return MSG_OK;
	}

}
