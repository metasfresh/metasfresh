package de.metas.payment.esr.process;

import de.metas.i18n.IMsgBL;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.ESRImportId;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Check;
import de.metas.util.Services;

public class ESR_Complete_Process extends JavaProcess
{
	private final IESRImportBL esrImportBL = Services.get(IESRImportBL.class);

	ESRImportId esrImportId ;

	@Override
	protected void prepare()
	{
		if (I_ESR_Import.Table_Name.equals(getTableName()))
		{
			esrImportId = ESRImportId.ofRepoId(getRecord_ID());
		}
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final I_ESR_Import esrImport = esrImportBL.getById(esrImportId);

		Check.errorUnless(esrImport.isValid(), "The document can not be processed, since it is not valid.");

		final String description = getProcessInfo().getTitle() + " #" + getPinstanceId().getRepoId();
		esrImportBL.complete(esrImport, description);

		return "";
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (success)
		{
			final I_ESR_Import esrImport = esrImportBL.getById(esrImportId);
			final boolean processed = Services.get(IESRImportBL.class).isProcessed(esrImport);
			if (processed)
			{
				getResult().addSummary(Services.get(IMsgBL.class).parseTranslation(getCtx(), "@ESR_Complete_Process_postProcess@"));
			}
			else
			{
				getResult().addSummary(Services.get(IMsgBL.class).parseTranslation(getCtx(), "@" + ESRConstants.ERR_ESR_LINE_WITH_NO_PAYMENT_ACTION + "@"));
			}

		}
	}
}
