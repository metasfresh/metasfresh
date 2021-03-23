package de.metas.payment.esr.dataimporter;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.Iterator;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.Adempiere;
import org.compiere.util.Env;

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
import de.metas.payment.esr.processor.impl.LoadESRImportFileWorkpackageProcessor;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;

import javax.annotation.Nullable;

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

	private static final AdMessageKey ESR_IMPORT_LOAD_FROM_FILE_INCONSITENT_TYPES = AdMessageKey.of("ESR_Import_LoadFromFile.InconsitentTypes");
	private static final AdMessageKey ESR_IMPORT_LOAD_FROM_FILE_CANT_GUESS_FILE_TYPE =  AdMessageKey.of("ESR_Import_LoadFromFile.CantGuessFileType");
	public static final  AdMessageKey LINES_ALREADY_EXIST_PLEASE_CHOOSE_A_NEW_ESR_MSG =  AdMessageKey.of("de.metas.payment.esr.dataimporter.ESRImportEnqueuer.LinesAlreadyExistChoseNewESR");

	public static final ESRImportEnqueuer newInstance()
	{
		return new ESRImportEnqueuer();
	}

	// services
	private final transient IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final transient IESRImportBL esrImportBL = Services.get(IESRImportBL.class);
	private final transient IESRImportDAO esrImportDAO = Services.get(IESRImportDAO.class);

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
		// Check/update data type
		checkUpdateDataType(esrImport, fromDataSource.getFilename());

		// Set the hash of the file in the esr header
		{
			final String esrHash = computeESRHashAndCheckForDuplicates(esrImport, fromDataSource.getContent());
			esrImport.setHash(esrHash);
			save(esrImport);
		}

		//
		// Delete ESR import lines
		esrImportDAO.deleteLines(esrImport);

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

	private void checkUpdateDataType(final I_ESR_Import esrImport, final String fileName)
	{
		if (Check.isEmpty(esrImport.getDataType()))
		{
			// see if the filename tells us which type to assume
			final String guessedType = ESRDataLoaderFactory.guessTypeFromFileName(fileName);
			if (Check.isEmpty(guessedType))
			{
				throw new AdempiereException(ESR_IMPORT_LOAD_FROM_FILE_CANT_GUESS_FILE_TYPE);
			}
			else
			{
				addLog("Assuming and updating type={} for ESR_Import={}", guessedType, esrImport);
				esrImport.setDataType(guessedType);
				save(esrImport);
			}
		}
		else
		{
			// see if the filename tells us that the user made a mistake
			final String guessedType = ESRDataLoaderFactory.guessTypeFromFileName(fileName);
			if (!Check.isEmpty(guessedType) && !guessedType.equalsIgnoreCase(esrImport.getDataType()))
			{
				// throw error, telling the user to check the ESI_import's type
				throw new AdempiereException(ESR_IMPORT_LOAD_FROM_FILE_INCONSITENT_TYPES);
			}
		}
	}

	private String computeESRHashAndCheckForDuplicates(final I_ESR_Import esrImport, final byte[] fileContent)
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
			final Properties ctx = InterfaceWrapperHelper.getCtx(esrImport);
			final Iterator<I_ESR_Import> esrImports = esrImportDAO.retrieveESRImports(ctx, esrImport.getAD_Org_ID());

			// will turn true if another identical hash was seen in the list of esr imports
			boolean seen = false;
			while (esrImports.hasNext())
			{
				if (esrHash.equals(esrImports.next().getHash()))
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
