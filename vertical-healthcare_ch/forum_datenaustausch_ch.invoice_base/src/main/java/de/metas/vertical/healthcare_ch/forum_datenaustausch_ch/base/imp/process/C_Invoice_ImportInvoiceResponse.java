package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.adempiere.ad.service.IErrorManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.I_AD_PInstance;
import org.compiere.util.MimeType;
import org.springframework.context.annotation.Profile;

import de.metas.i18n.ITranslatableString;
import de.metas.invoice_gateway.spi.model.InvoiceId;
import de.metas.invoice_gateway.spi.model.imp.ImportInvoiceResponseRequest;
import de.metas.invoice_gateway.spi.model.imp.ImportedInvoiceResponse;
import de.metas.notification.INotificationBL;
import de.metas.notification.Recipient;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.CrossVersionServiceRegistry;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.export.invoice.InvoiceImportClientImpl;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.InvoiceResponseRepo;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.InvoiceResponseRepo.InvoiceResponseRepoException;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.ForumDatenaustauschChConstants;
import lombok.NonNull;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Profile(ForumDatenaustauschChConstants.PROFILE)
public class C_Invoice_ImportInvoiceResponse extends JavaProcess
{
	private static int WINDOW_ID_AD_PInstance_ID = 332; // FIXME Hardcoded

	private static final String MSG_NOT_ALL_FILES_IMPORTED = "de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process.C_Invoice_ImportInvoiceResponse.NotAllFilesImported";
	private static final String MSG_NOT_ALL_FILES_IMPORTED_NOTIFICATION = "de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process.C_Invoice_ImportInvoiceResponse.NotAllFilesImportedNotification";
	private static final String ATTATCHMENT_TAGNAME_AD_PINSTANCE_ID = "ImportAD_PInstance_ID";
	private static final String ATTATCHMENT_TAGNAME_TIME_MILLIS = "ImportTimeMillis";
	private static final String ATTATCHMENT_TAGNAME_FILE_ABSOLUTE_PATH = "ImportFileAbsolutePath";

	@Param(mandatory = true, parameterName = "InputDirectory")
	private String inputFilePath;

	@Param(mandatory = true, parameterName = "ImportFileWildCard")
	private String importFileWildcard;

	@Param(mandatory = true, parameterName = "OutputDirectory")
	private String outputFilePath;

	private final CrossVersionServiceRegistry crossVersionServiceRegistry = Adempiere.getBean(CrossVersionServiceRegistry.class);

	private final InvoiceResponseRepo importedInvoiceResponseRepo = Adempiere.getBean(InvoiceResponseRepo.class);

	private final INotificationBL notificationBL = Services.get(INotificationBL.class);

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final File inputDirectory = new File(inputFilePath);
		Check.assume(inputDirectory.isDirectory(), "Parameter InputDirectory does not denote a directory; InputDirectory={}", inputDirectory);
		Check.assume(inputDirectory.canRead(), "Parameter InputDirectory is a directory, but this process does not have read-access; InputDirectory={}", inputDirectory);
		Check.assume(inputDirectory.canWrite(), "Parameter InputDirectory is a directory, but this process does not have write-access; InputDirectory={}", inputDirectory);

		final File outputDirectory = new File(outputFilePath);
		Check.assume(outputDirectory.isDirectory(), "Parameter OutputDirectory does not denote a directory; OutputDirectory={}", outputDirectory);
		Check.assume(outputDirectory.canWrite(), "Parameter OutputDirectory is a directory, but this process doies not have write-access; OutputDirectory={}", outputDirectory);

		final FileFilter fileFilter = new WildcardFileFilter(importFileWildcard);
		final File[] filesToImport = inputDirectory.listFiles(fileFilter);

		final Mutable<Boolean> allFilesImported = new Mutable<Boolean>(true);

		for (final File fileToImport : filesToImport)
		{
			trxManager.run(() -> {

				final boolean currentFileImported = importSingleFile(fileToImport.toPath(), outputDirectory.toPath());
				allFilesImported.setValue(allFilesImported.getValue() && currentFileImported);
			});
		}

		if (allFilesImported.getValue())
		{
			return MSG_OK; // all went fine; nothing more to do
		}

		if(getProcessInfo().isInvokedByScheduler())
		{
			// throw an exception so the problem is logged with the scheduler and a supervisor may be notified
			final ITranslatableString message = msgBL.getTranslatableMsgText(MSG_NOT_ALL_FILES_IMPORTED);
			throw new AdempiereException(message);
		}

		// return "OK" (i.e. don't throw an exception), but notify the user
		final UserNotificationRequest userNotificationRequest = UserNotificationRequest
				.builder()
				.recipient(Recipient.user(getAD_User_ID()))
				.contentADMessage(MSG_NOT_ALL_FILES_IMPORTED_NOTIFICATION)
				.contentADMessageParam(getPinstanceId().getRepoId())
				.targetAction(TargetRecordAction.ofRecordAndWindow(TableRecordReference.of(I_AD_PInstance.Table_Name, getPinstanceId()), WINDOW_ID_AD_PInstance_ID))
				.build();
		notificationBL.send(userNotificationRequest);

		return MSG_OK;
	}

	private boolean importSingleFile(
			@NonNull final Path fileToImport,
			@NonNull final Path outputDirectory)
	{
		try
		{
			final ImportInvoiceResponseRequest request = createRequest(fileToImport);

			final InvoiceImportClientImpl invoiceImportClientImpl = new InvoiceImportClientImpl(crossVersionServiceRegistry);
			final ImportedInvoiceResponse response = invoiceImportClientImpl.importInvoiceResponse(request);

			final ImportedInvoiceResponse responseWithTags = response.toBuilder()
					.additionalTag(ATTATCHMENT_TAGNAME_FILE_ABSOLUTE_PATH, fileToImport.toAbsolutePath().toString())
					.additionalTag(ATTATCHMENT_TAGNAME_TIME_MILLIS, Long.toString(SystemTime.millis()))
					.additionalTag(ATTATCHMENT_TAGNAME_AD_PINSTANCE_ID, Integer.toString(getPinstanceId().getRepoId()))
					.build();

			final InvoiceId invoiceId = importedInvoiceResponseRepo.save(responseWithTags);
			moveFile(fileToImport, outputDirectory);
			addLog("Imported invoice response file={} into C_Invoice_ID={}", fileToImport.getFileName().toString(), invoiceId.getRepoId());
			return true;
		}
		catch (final InvoiceResponseRepoException e)
		{
			addLog("InvoiceResponseRepoException while processing file {}; Message={};", fileToImport.getFileName().toString(), e.getMessage());
		}
		catch (final RuntimeException e)
		{
			final I_AD_Issue issue = Services.get(IErrorManager.class).createIssue(e);
			addLog("{} while processing file {}; AD_Issue_ID={}; Message={};", e.getClass().getSimpleName(), fileToImport.getFileName().toString(), issue.getAD_Issue_ID(), e.getMessage());
		}
		return false;
	}

	private ImportInvoiceResponseRequest createRequest(@NonNull final Path fileToImport)
	{
		try
		{
			final byte[] fileContent = Files.readAllBytes(fileToImport);
			final String fileName = fileToImport.getFileName().toString();

			return ImportInvoiceResponseRequest
					.builder()
					.data(fileContent)
					.fileName(fileName)
					.mimeType(MimeType.getMimeType(fileName))
					.build();
		}
		catch (final IOException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	private void moveFile(@NonNull final Path fileToMove, @NonNull final Path outputDirectory)
	{
		try
		{
			Files.move(fileToMove, outputDirectory.resolve(fileToMove.getFileName()));
		}
		catch (final IOException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}
}
