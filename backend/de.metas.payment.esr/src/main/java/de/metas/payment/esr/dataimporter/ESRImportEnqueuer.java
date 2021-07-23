package de.metas.payment.esr.dataimporter;

import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.api.IESRImportDAO;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportFile;
import de.metas.payment.esr.processor.impl.LoadESRImportFileWorkpackageProcessor;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.apache.commons.io.FileUtils;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
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
				final AttachmentEntryService attachmentEntryService = Adempiere.getBean(AttachmentEntryService.class);
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

		AttachmentEntry attachmentEntry = attachmentEntryService.getById(attachmentEntryId);

		if (esrImport.isArchiveFile())
		{
			createImportFilesFromZips(esrImport, in, attachmentEntry.getFilename());
		}

		else
		{

			createImportFileFromSingleAttachment(esrImport, in, attachmentEntry.getFilename());
		}
		//
		// Check/update data type
		//checkUpdateDataType(esrImport, fromDataSource.getFilename());

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

	private void createImportFileFromSingleAttachment(final I_ESR_Import esrImport, final ByteArrayInputStream in, final String filename)
	{
	}

	private void createImportFilesFromZips(@NonNull final I_ESR_Import esrImport, ByteArrayInputStream in, String filename)
	{

		final ZipInputStream zipStream = new ZipInputStream(in);

		try
		{
			final File unzipDir = Files.createTempDirectory("ZipFile" + "-")
					.toFile();
			unzipDir.deleteOnExit();
			final byte[] buffer = new byte[1024];

			ZipEntry zipEntry = zipStream.getNextEntry();
			while (zipEntry != null)
			{
				final File unzippedFile = getUnzippedFile(zipStream, unzipDir, buffer, zipEntry);

				final byte[] unzippedData = FileUtils.readFileToByteArray(unzippedFile);

				final AttachmentEntryId fromAttachmentEntryId;

				final I_ESR_ImportFile esrImportFile = esrImportDAO.createESRImportFile(esrImport);
				checkUpdateDataType(esrImportFile, unzippedFile.getName());
				computeESRHashAndCheckForDuplicates(esrImportFile, unzippedData);

				final AttachmentEntry attachmentEntry = attachmentEntryService.createNewAttachment(
						esrImportFile,
						unzippedFile.getName(),
						unzippedData);
				fromAttachmentEntryId = attachmentEntry.getId();

				esrImportFile.setAD_AttachmentEntry_ID(fromAttachmentEntryId.getRepoId());
				InterfaceWrapperHelper.save(esrImportFile);

				zipEntry = zipStream.getNextEntry();
			}
			zipStream.closeEntry();
			zipStream.close();
		}
		catch (final Exception ex)
		{
			throw new RuntimeException("Cannot unzip " + filename, ex);
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
				loggable.addLog("Assuming and updating type={} for ESR_Import={}", guessedType, esrImportFile);
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

	@NonNull
	private File getUnzippedFile(final ZipInputStream zipStream,
			final File unzipDir,
			final byte[] buffer,
			final ZipEntry zipEntry) throws IOException
	{
		final File file = newFile(unzipDir, zipEntry);

		if (zipEntry.isDirectory())
		{
			if (!file.isDirectory() && !file.mkdirs())
			{
				throw new IOException("Failed to create directory " + file);
			}
		}
		else
		{

			final File parent = file.getParentFile();
			if (!parent.isDirectory() && !parent.mkdirs())
			{
				throw new IOException("Failed to create directory " + parent);
			}

			// write file content
			final FileOutputStream fos = new FileOutputStream(file);
			int len;
			while ((len = zipStream.read(buffer)) > 0)
			{
				fos.write(buffer, 0, len);
			}
			fos.close();
		}

		return file;
	}

	public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException
	{
		File destFile = new File(destinationDir, zipEntry.getName());

		String destDirPath = destinationDir.getCanonicalPath();
		String destFilePath = destFile.getCanonicalPath();

		if (!destFilePath.startsWith(destDirPath + File.separator))
		{
			throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
		}

		return destFile;
	}

	private String computeESRHashAndCheckForDuplicates(final I_ESR_ImportFile esrImportFile, final byte[] fileContent)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(esrImportFile);
		final int orgRecordId = esrImportFile.getAD_Org_ID();

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

			final Iterator<I_ESR_ImportFile> esrImportFiless = esrImportDAO.retrieveESRImportFiles(ctx, orgRecordId);

			// will turn true if another identical hash was seen in the list of esr imports
			boolean seen = false;
			while (esrImportFiless.hasNext())
			{
				if (esrHash.equals(esrImportFiless.next().getHash()))
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
