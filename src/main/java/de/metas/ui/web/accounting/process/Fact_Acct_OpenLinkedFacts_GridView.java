package de.metas.ui.web.accounting.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.DocumentId;

public class Fact_Acct_OpenLinkedFacts_GridView extends ViewBasedProcessTemplate implements IProcessPrecondition
{

	
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final IViewRow singleSelectedRow = getSingleSelectedRow();

		final DocumentId factAcctId = singleSelectedRow.getId();

		final ProcessExecutionResult result = getResult();

		new Fact_Acct_OpenLinkedFacts_ProcessHelper().openLinkedFactAccounts(factAcctId.toInt(), result);

		return MSG_OK;
	}

}
