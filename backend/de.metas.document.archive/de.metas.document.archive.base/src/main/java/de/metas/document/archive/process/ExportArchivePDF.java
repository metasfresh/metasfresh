package de.metas.document.archive.process;

import de.metas.document.archive.model.IArchiveAware;
import de.metas.document.archive.model.I_AD_Archive;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.archive.AdArchive;
import org.adempiere.archive.ArchiveId;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.core.io.ByteArrayResource;

public class ExportArchivePDF extends JavaProcess implements IProcessPrecondition
{
	private final IArchiveBL archiveBL = Services.get(IArchiveBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		final Object model = context.getSelectedModel(Object.class);
		if (InterfaceWrapperHelper.isInstanceOf(model, I_AD_Archive.class))
		{
			return ProcessPreconditionsResolution.accept();
		}
		else
		{
			final IArchiveAware archiveAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(model, IArchiveAware.class);
			if (archiveAware == null)
			{
				log.debug("No AD_Archive field found for {}", context);
				return ProcessPreconditionsResolution.rejectWithInternalReason("no archive found");
			}

			final int archiveId = archiveAware.getAD_Archive_ID();
			if (archiveId <= 0)
			{
				log.debug("No AD_Archive_ID found for {}", archiveAware);
				return ProcessPreconditionsResolution.rejectWithInternalReason("no archive found");
			}

			return ProcessPreconditionsResolution.accept();
		}
	}

	@Override
	protected String doIt()
	{
		final AdArchive archive = getArchive();

		final ByteArrayResource data = archive.getArchiveDataAsResource();
		final String contentType = archive.getContentType();
		final String filename = String.valueOf(archive.getId().getRepoId());

		getResult().setReportData(data, filename, contentType);

		return MSG_OK;
	}

	@NonNull
	private AdArchive getArchive()
	{
		final ArchiveId archiveId;
		if (I_AD_Archive.Table_Name.equals(getTableName()))
		{
			archiveId = ArchiveId.ofRepoId(getRecord_ID());
		}
		else
		{
			final IArchiveAware archiveAware = getRecord(IArchiveAware.class);
			archiveId = ArchiveId.ofRepoId(archiveAware.getAD_Archive_ID());
		}

		return archiveBL.getById(archiveId);
	}
}
