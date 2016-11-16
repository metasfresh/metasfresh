package de.metas.document.archive.process;

import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.adempiere.form.IClientUI;
import de.metas.document.archive.model.IArchiveAware;
import de.metas.document.archive.model.I_AD_Archive;
import de.metas.process.ISvrProcessPrecondition;
import de.metas.process.SvrProcess;

public class ExportArchivePDF extends SvrProcess implements ISvrProcessPrecondition
{
	@Override
	public boolean isPreconditionApplicable(final PreconditionsContext context)
	{
		final Object model = context.getModel(Object.class);
		final IArchiveAware archiveAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(model, IArchiveAware.class);
		if(archiveAware == null)
		{
			log.debug("No AD_Archive field found for {}", context);
			return false;
		}

		final int archiveId = archiveAware.getAD_Archive_ID();
		if (archiveId <= 0)
		{
			log.debug("No AD_Archive_ID found for {}", archiveAware);
			return false;
		}

		return true;
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
