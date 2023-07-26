package de.metas.payment.esr.process;

import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.i18n.IMsgBL;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.process.JavaProcess;
import de.metas.util.Check;
import de.metas.util.Services;

public class ESR_Complete_Process extends JavaProcess
{
	private final IESRImportBL esrImportBL = Services.get(IESRImportBL.class);

	private int p_ESR_Import_ID;

	@Override
	protected void prepare()
	{
		if (I_ESR_Import.Table_Name.equals(getTableName()))
		{
			p_ESR_Import_ID = getRecord_ID();
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		if (p_ESR_Import_ID <= 0)
		{
			throw new FillMandatoryException(I_ESR_Import.COLUMNNAME_ESR_Import_ID);
		}

		final I_ESR_Import esrImport = InterfaceWrapperHelper.create(getCtx(), p_ESR_Import_ID, I_ESR_Import.class, get_TrxName());

		// 04582: making sure we will use the trxName of this process in our business logic
		Check.assume(get_TrxName().equals(InterfaceWrapperHelper.getTrxName(esrImport)), "TrxName {} of {} is equal to the process-TrxName {}",
				InterfaceWrapperHelper.getTrxName(esrImport),
				esrImport,
				get_TrxName());

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
			final I_ESR_Import esrImport = InterfaceWrapperHelper.create(getCtx(), p_ESR_Import_ID, I_ESR_Import.class, get_TrxName());
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
