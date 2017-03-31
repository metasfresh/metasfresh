package de.metas.document.archive.process;

import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.adempiere.form.IClientUI;
import de.metas.document.archive.model.IArchiveAware;
import de.metas.document.archive.model.I_AD_Archive;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;

public class ExportArchivePDF extends JavaProcess implements IProcessPrecondition
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		final Object model = context.getSelectedModel(Object.class);
		final IArchiveAware archiveAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(model, IArchiveAware.class);
		if(archiveAware == null)
		{
			log.debug("No AD_Archive field found for {}", context);
			return ProcessPreconditionsResolution.reject("no archive found");
		}

		final int archiveId = archiveAware.getAD_Archive_ID();
		if (archiveId <= 0)
		{
			log.debug("No AD_Archive_ID found for {}", archiveAware);
			return ProcessPreconditionsResolution.reject("no archive found");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected void prepare()
	{
	}

	@Override
	protected String doIt()
	{
		final IArchiveAware archiveAware = getRecord(IArchiveAware.class);
		final I_AD_Archive archive = archiveAware.getAD_Archive();
		Check.assumeNotNull(archive, "Parameter archive is not null");

		final IArchiveBL archiveBL = Services.get(IArchiveBL.class);
		final byte[] data = archiveBL.getBinaryData(archive);
		final String contentType = archiveBL.getContentType(archive);
		final String filename = null;

		Services.get(IClientUI.class).download(data, contentType, filename);
		return "OK";
	}
}
