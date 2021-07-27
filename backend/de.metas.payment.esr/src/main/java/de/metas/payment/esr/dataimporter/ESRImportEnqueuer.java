package de.metas.payment.esr.dataimporter;

import com.google.common.io.ByteStreams;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.organization.OrgId;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.api.IESRImportDAO;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportFile;
import de.metas.payment.esr.processor.impl.LoadESRImportFileWorkpackageProcessor;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.FileUtil;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.adempiere.model.InterfaceWrapperHelper.save;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Helper class to enqueue a given {@link I_ESR_Import} to be imported.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class ESRImportEnqueuer
{
	private static final AdMessageKey ESR_IMPORT_LOAD_FROM_FILE_CANT_GUESS_FILE_TYPE = AdMessageKey.of("ESR_Import_LoadFromFile.CantGuessFileType");
	private static final AdMessageKey ESR_IMPORT_LOAD_FROM_FILE_INCONSITENT_TYPES = AdMessageKey.of("ESR_Import_LoadFromFile.InconsitentTypes");
	public static final AdMessageKey LINES_ALREADY_EXIST_PLEASE_CHOOSE_A_NEW_ESR_MSG = AdMessageKey.of("de.metas.payment.esr.dataimporter.ESRImportEnqueuer.LinesAlreadyExistChoseNewESR");

	public static final ESRImportEnqueuer newInstance()
	{
		return new ESRImportEnqueuer();
	}

	// services
	private final transient IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final transient IESRImportBL esrImportBL = Services.get(IESRImportBL.class);
	private final transient IESRImportDAO esrImportDAO = Services.get(IESRImportDAO.class);
	private final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
	private final Properties ctx = Env.getCtx();

	private I_ESR_Import esrImport;
	private ESRImportEnqueuerDataSource fromDataSource;

	private String asyncBatchName = "ESR Import";
	private String asyncBatchDesc = "ESR Import process";
	private PInstanceId pinstanceId;

	private ESRImportEnqueuerDuplicateFilePolicy duplicateFilePolicy;

	private ILoggable loggable = Loggables.nop();

	private ESRImportEnqueuer()
	{

	}

	public void execute()
	{
		final I_ESR_Import esrImport = getEsrImport();

		final int existingLines = esrImportDAO.countLines(esrImport, null);
		if (existingLines > 0)
		{
			final String msg = Services.get(IMsgBL.class).getMsg(getCtx(), LINES_ALREADY_EXIST_PLEASE_CHOOSE_A_NEW_ESR_MSG);
			throw new AdempiereException(msg);
		}

		final ESRImportEnqueuerDataSource fromDataSource = getFromDataSource();

		//
		// Create attachment (03928)
		// attaching the file first, so that it's available for our support, if anything goes wrong
		{
			final AttachmentEntryId fromAttachmentEntryId;
			if (fromDataSource.getAttachmentEntryId() == null)
			{
				final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
				final AttachmentEntry attachmentEntry = attachmentEntryService.createNewAttachment(
						esrImport,
						fromDataSource.getFilename(),
						fromDataSource.getContent());
				fromAttachmentEntryId = attachmentEntry.getId();
			}
			else
			{
				fromAttachmentEntryId = fromDataSource.getAttachmentEntryId();
			}

			esrImport.setAD_AttachmentEntry_ID(fromAttachmentEntryId.getRepoId());
			InterfaceWrapperHelper.save(esrImport);
		}

		//
		// Fetch data to be imported from attachment
		final AttachmentEntryId attachmentEntryId = fromDataSource.getAttachmentEntryId();

		final byte[] data = attachmentEntryService.retrieveData(attachmentEntryId);

		// there is no actual data
		if (data == null || data.length == 0)
		{
			return;
		}

		final ByteArrayInputStream in = new ByteArrayInputStream(data);

		final AttachmentEntry attachmentEntry = attachmentEntryService.getById(attachmentEntryId);

		if (esrImport.isArchiveFile())
		{
			createImportFilesFromZips(esrImport, in, attachmentEntry.getFilename());
		}

		else
		{

			createImportFileFromSingleAttachment(esrImport, data, attachmentEntry.getFilename());
		}

		//
		// Enqueue for importing async
		// NOTE: locking not required
		{
			final Properties ctx = getCtx();

			// Create Async Batch for tracking
			final I_C_Async_Batch asyncBatch = asyncBatchBL.newAsyncBatch()
					.setContext(ctx)
					.setC_Async_Batch_Type(ESRConstants.C_Async_Batch_InternalName)
					.setAD_PInstance_Creator_ID(getPinstanceId())
					.setName(getAsyncBatchName())
					.setDescription(getAsyncBatchDesc())
					.build();

			//
			final IWorkPackageQueue queue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, LoadESRImportFileWorkpackageProcessor.class);
			queue.newBlock()
					.setContext(ctx)
					.newWorkpackage()
					.setC_Async_Batch(asyncBatch) // set the async batch in workpackage in order to track it
					.addElement(esrImport)
					.build();
		}

	}

	private void createImportFileFromSingleAttachment(@NonNull final I_ESR_Import esrImport,
			final byte[] data,
			@NonNull final String filename)
	{
		final I_ESR_ImportFile esrImportFile = esrImportDAO.createESRImportFile(esrImport);
		checkUpdateDataType(esrImportFile, filename);

		final String hash = computeESRHashAndCheckForDuplicates(esrImportFile, data);
		esrImportFile.setHash(hash);

		final AttachmentEntry attachmentEntry = attachmentEntryService.createNewAttachment(
				esrImportFile,
				filename,
				data);
		final AttachmentEntryId attachmentEntryId = attachmentEntry.getId();

		esrImportFile.setAD_AttachmentEntry_ID(attachmentEntryId.getRepoId());
		esrImportFile.setFileName(filename);
		esrImportDAO.save(esrImportFile);
	}

	private void createImportFilesFromZips(@NonNull final I_ESR_Import esrImport,
			@NonNull final ByteArrayInputStream in,
			@NonNull final String filename)
	{
		final ZipInputStream zipStream = new ZipInputStream(in);

		try
		{
			ZipEntry zipEntry = zipStream.getNextEntry();
			while (zipEntry != null)
			{
				if (zipEntry.isDirectory())
				{
					zipEntry = zipStream.getNextEntry();
					continue;
				}
				final Resource unzippedFile = extractResource(zipStream, zipEntry);

				final InputStream inputStream = unzippedFile.getInputStream();
				final byte[] unzippedData = ByteStreams.toByteArray(inputStream);

				final String hash = computeESRHashAndCheckForDuplicates(OrgId.ofRepoId(esrImport.getAD_Org_ID()), unzippedData);

				final I_ESR_ImportFile esrImportFile = esrImportDAO.createESRImportFile(esrImport);
				esrImportFile.setHash(hash);
				checkUpdateDataType(esrImportFile, unzippedFile.getFilename());

				final AttachmentEntry attachmentEntry = attachmentEntryService.createNewAttachment(
						esrImportFile,
						unzippedFile.getFilename(),
						unzippedData);
				final AttachmentEntryId attachmentEntryId = attachmentEntry.getId();

				esrImportFile.setAD_AttachmentEntry_ID(attachmentEntryId.getRepoId());
				esrImportFile.setFileName(unzippedFile.getFilename());
				InterfaceWrapperHelper.save(esrImportFile);

				zipEntry = zipStream.getNextEntry();
			}
			zipStream.closeEntry();
			zipStream.close();
		}
		catch (final Exception ex)
		{
			// provide more info about why the file could not be unzipped
			throw new AdempiereException(ex);
		}
	}

	private void checkUpdateDataType(final I_ESR_ImportFile esrImportFile, final String fileName)
	{
		if (Check.isEmpty(esrImportFile.getDataType()))
		{
			// see if the filename tells us which type to assume
			final String guessedType = ESRDataLoaderFactory.guessTypeFromFileName(fileName);
			if (Check.isEmpty(guessedType))
			{
				throw new AdempiereException(ESR_IMPORT_LOAD_FROM_FILE_CANT_GUESS_FILE_TYPE);
			}
			else
			{
				addLog("Assuming and updating type={} for ESR_Import={}", guessedType, esrImportFile);
				esrImportFile.setDataType(guessedType);
				save(esrImportFile);
			}
		}
		else
		{
			// see if the filename tells us that the user made a mistake
			final String guessedType = ESRDataLoaderFactory.guessTypeFromFileName(fileName);
			if (!Check.isEmpty(guessedType) && !guessedType.equalsIgnoreCase(esrImportFile.getDataType()))
			{
				// throw error, telling the user to check the ESI_import's type
				throw new AdempiereException(ESR_IMPORT_LOAD_FROM_FILE_INCONSITENT_TYPES);
			}
		}
	}

	public Resource extractResource(
			@NonNull final ZipInputStream zipInputStream,
			@NonNull ZipEntry zipEntry)
	{
		try
		{
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			FileUtil.copy(zipInputStream, baos);

			return ZipFileResource.builder()
					.filename(new File(zipEntry.getName()).getName())
					.data(baos.toByteArray())
					.build();

		}
		catch (final IOException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}

	}

	private static class ZipFileResource extends AbstractResource
	{
		private final byte[] data;
		private final String filename;

		@Builder
		private ZipFileResource(
				@NonNull final byte[] data,
				@NonNull final String filename)
		{
			this.data = data;
			this.filename = filename;
		}

		@Override
		public String getFilename()
		{
			return filename;
		}

		@Override
		public String getDescription()
		{
			return null;
		}

		@Override
		public InputStream getInputStream()
		{
			return new ByteArrayInputStream(data);
		}
	}

	public static File newFile(@NonNull final File destinationDir, @NonNull final ZipEntry zipEntry)
			throws IOException
	{
		final File destFile = new File(destinationDir, zipEntry.getName());

		final String destDirPath = destinationDir.getCanonicalPath();
		final String destFilePath = destFile.getCanonicalPath();

		if (!destFilePath.startsWith(destDirPath + File.separator))
		{
			throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
		}

		return destFile;
	}

	private String computeESRHashAndCheckForDuplicates(@NonNull final OrgId orgId, final byte[] fileContent)
	{
		final String esrHash = esrImportBL.computeMD5Checksum(fileContent);

		//
		// Check for duplicates
		final String preventDuplicates = sysConfigBL.getValue(ESRConstants.SYSCONFIG_PreventDuplicateImportFiles);
		if (Check.isEmpty(preventDuplicates, true) || "-".equals(preventDuplicates))
		{
			// the sys config not defined. Functionality to work as before
		}
		else
		{
			final Iterator<I_ESR_ImportFile> esrImportFiles = esrImportDAO.retrieveActiveESRImportFiles(orgId);

			// will turn true if another identical hash was seen in the list of esr imports
			boolean seen = false;
			while (esrImportFiles.hasNext())
			{
				if (esrHash.equals(esrImportFiles.next().getHash()))
				{
					seen = true;
					break;
				}
			}

			if (seen)
			{
				// Warning: ask the user if we shall import the duplicate file
				if ("W".equalsIgnoreCase(preventDuplicates))
				{
					if (!getDuplicateFilePolicy().isImportDuplicateFile())
					{
						getDuplicateFilePolicy().onNotImportingDuplicateFile();
						throw new AdempiereException("File not imported - identical with previous file");
					}

				}
				// Error: inform the user we will not import the duplicate file
				else if ("E".equalsIgnoreCase(preventDuplicates))
				{
					getDuplicateFilePolicy().onNotImportingDuplicateFile();
					throw new AdempiereException("File not imported - identical with previous file");
				}
				else
				{
					throw new AdempiereException("Sysconfig " + ESRConstants.SYSCONFIG_PreventDuplicateImportFiles + " must be either W or E");
				}
			}
		}

		return esrHash;
	}

	private Properties getCtx()
	{
		return ctx;
	}

	public ESRImportEnqueuer esrImport(final I_ESR_Import esrImport)
	{
		this.esrImport = esrImport;
		return this;
	}

	@NonNull
	private I_ESR_Import getEsrImport()
	{
		return esrImport;
	}

	public ESRImportEnqueuer asyncBatchName(final String asyncBatchName)
	{
		this.asyncBatchName = asyncBatchName;
		return this;
	}

	private String getAsyncBatchName()
	{
		return asyncBatchName;
	}

	public ESRImportEnqueuer asyncBatchDesc(final String asyncBatchDesc)
	{
		this.asyncBatchDesc = asyncBatchDesc;
		return this;
	}

	private String getAsyncBatchDesc()
	{
		return asyncBatchDesc;
	}

	public ESRImportEnqueuer pinstanceId(@Nullable final PInstanceId pinstanceId)
	{
		this.pinstanceId = pinstanceId;
		return this;
	}

	private PInstanceId getPinstanceId()
	{
		return pinstanceId;
	}

	public ESRImportEnqueuer fromDataSource(final ESRImportEnqueuerDataSource fromDataSource)
	{
		this.fromDataSource = fromDataSource;
		return this;
	}

	@NonNull
	private ESRImportEnqueuerDataSource getFromDataSource()
	{
		return fromDataSource;
	}

	public ESRImportEnqueuer loggable(@NonNull final ILoggable loggable)
	{
		this.loggable = loggable;
		return this;
	}

	private void addLog(final String msg, final Object... msgParameters)
	{
		loggable.addLog(msg, msgParameters);
	}

	public ESRImportEnqueuer duplicateFilePolicy(final ESRImportEnqueuerDuplicateFilePolicy duplicateFilePolicy)
	{
		this.duplicateFilePolicy = duplicateFilePolicy;
		return this;
	}

	private ESRImportEnqueuerDuplicateFilePolicy getDuplicateFilePolicy()
	{
		return duplicateFilePolicy;
	}

}
