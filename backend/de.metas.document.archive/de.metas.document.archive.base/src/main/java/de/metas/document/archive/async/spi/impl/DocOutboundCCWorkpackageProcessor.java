package de.metas.document.archive.async.spi.impl;

import ch.qos.logback.classic.Level;
import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.model.I_AD_Archive;
import de.metas.document.archive.model.I_C_Doc_Outbound_Config;
import de.metas.document.archive.storage.cc.api.ICCAbleDocument;
import de.metas.document.archive.storage.cc.api.ICCAbleDocumentFactoryService;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.FileUtil;
import de.metas.util.IOStreamUtils;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import static de.metas.async.Async_Constants.SYS_Config_SKIP_WP_PROCESSOR_FOR_AUTOMATION;

/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

public class DocOutboundCCWorkpackageProcessor implements IWorkpackageProcessor
{
	private static final Logger logger = LogManager.getLogger(DocOutboundCCWorkpackageProcessor.class);
	
	public static final void scheduleOnTrxCommit(final org.compiere.model.I_AD_Archive archive)
	{
		SCHEDULER.schedule(archive);
	}

	private static final WorkpackagesOnCommitSchedulerTemplate<org.compiere.model.I_AD_Archive> //
	SCHEDULER = WorkpackagesOnCommitSchedulerTemplate.newModelScheduler(DocOutboundCCWorkpackageProcessor.class, org.compiere.model.I_AD_Archive.class)
			.setCreateOneWorkpackagePerModel(true);

	// services
	private final transient IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final transient org.adempiere.archive.api.IArchiveDAO archiveDAO = Services.get(org.adempiere.archive.api.IArchiveDAO.class);
	private final transient IArchiveStorageFactory archiveStorageFactory = Services.get(IArchiveStorageFactory.class);
	private final transient ICCAbleDocumentFactoryService ccAbleDocumentFactoryService = Services.get(ICCAbleDocumentFactoryService.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@Override
	public Result processWorkPackage(
			@NonNull final I_C_Queue_WorkPackage workpackage,
			@NonNull final String localTrxName)
	{
		//dev-note: temporary workaround until we get the jasper reports to work during cucumber tests
		if (sysConfigBL.getBooleanValue(SYS_Config_SKIP_WP_PROCESSOR_FOR_AUTOMATION, false))
		{
			Loggables.withLogger(logger, Level.INFO).addLog("SYS_Config_SKIP_WP_PROCESSOR_FOR_AUTOMATION=Y -> Skipping DocOutboundCCWorkpackageProcessor!");
			
			return Result.SUCCESS;
		}

		final List<I_AD_Archive> archives = queueDAO.retrieveAllItems(workpackage, I_AD_Archive.class);
		for (final I_AD_Archive archive : archives)
		{
			writeCCFile(archive);
		}
		return Result.SUCCESS;
	}

	private void writeCCFile(final I_AD_Archive archive)
	{
		final Object model = archiveDAO.retrieveReferencedModel(archive, Object.class);
		if (model == null)
		{
			// No model attached?
			// we shouldn't reach this point
			throw new AdempiereException("@NotFound@ @AD_Archive_ID@ @Record_ID@");
		}

		final ICCAbleDocument document = ccAbleDocumentFactoryService.createCCAbleDocument(model);
		Check.assumeNotNull(document, "ccDocument not null");

		//
		// Get Document Outbound Configuration
		final I_C_Doc_Outbound_Config config = Services.get(IDocOutboundDAO.class).retrieveConfigForModel(model);
		if (config == null)
		{
			throw new AdempiereException("@NotFound@ @C_Doc_Outbound_Config@ (" + model + ")");
		}

		final String ccPath = config.getCCPath();
		if (Check.isEmpty(ccPath, true))
		{
			// Doc Outbound config does not have CC Path set
			// we shouldn't reach this point
			throw new AdempiereException("@NotFound@ @CCPath@: " + config);
		}
		final File ccPathDir = new File(ccPath);
		if (!ccPathDir.exists() && !ccPathDir.mkdirs())
		{
			throw new AdempiereException("Cannot create directory: " + ccPathDir);
		}

		final String filename = document.getFileName();
		Check.assumeNotEmpty(filename, "filename shall not be empty for {}", document);
		final String filenameFixed = FileUtil.stripIllegalCharacters(filename);
		Check.assumeNotEmpty(filenameFixed, "filename shall be valid: {}", filename);

		final File ccFile = new File(ccPathDir, filenameFixed);

		copyArchiveToFile(archive, ccFile);
	}

	private void copyArchiveToFile(final I_AD_Archive archive, final File file)
	{
		final IArchiveStorage archiveStorage = archiveStorageFactory.getArchiveStorage(archive);
		final InputStream data = archiveStorage.getBinaryDataAsStream(archive);

		OutputStream out = null;
		try
		{
			out = new FileOutputStream(file, false); // append=false

			final int bufferSize = 1024 * 4;
			final OutputStream outBuffer = new BufferedOutputStream(out, bufferSize);
			IOStreamUtils.copy(outBuffer, data);
		}
		catch (final FileNotFoundException e)
		{
			throw new AdempiereException("@CCFileCreationError@ " + file, e);
		}
		catch (final SecurityException e)
		{
			throw new AdempiereException("@CCFileWriteAccessDenied@ " + file, e);
		}
		finally
		{
			IOStreamUtils.close(out, data);
		}
	}
}
