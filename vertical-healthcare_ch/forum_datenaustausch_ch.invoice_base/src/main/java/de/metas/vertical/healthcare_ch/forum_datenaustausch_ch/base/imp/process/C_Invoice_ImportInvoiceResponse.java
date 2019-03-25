package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.compiere.Adempiere;
import org.compiere.util.MimeType;

import de.metas.invoice_gateway.spi.model.imp.ImportInvoiceResponseRequest;
import de.metas.invoice_gateway.spi.model.imp.ImportedInvoiceResponse;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.CrossVersionServiceRegistry;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.export.invoice.InvoiceImportClientImpl;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.InvoiceResponseRepo;
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

public class C_Invoice_ImportInvoiceResponse extends JavaProcess
{
	@Param(mandatory = true, parameterName = "InputDirectory")
	private String inputFilePath;

	@Param(mandatory = true, parameterName = "ImportFileWildCard")
	private String importFileWildcard;

	@Param(mandatory = true, parameterName = "OutputDirectory")
	private String outputFilePath;

	private CrossVersionServiceRegistry crossVersionServiceRegistry = Adempiere.getBean(CrossVersionServiceRegistry.class);

	private InvoiceResponseRepo importedInvoiceResponseRepo = Adempiere.getBean(InvoiceResponseRepo.class);

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

		for (final File fileToImport : filesToImport)
		{
			Services.get(ITrxManager.class).run(() -> {

				importSingleFile(fileToImport.toPath(), outputDirectory.toPath());
			});
		}

		return MSG_OK;
	}

	private void importSingleFile(
			@NonNull final Path fileToImport,
			@NonNull final Path outputDirectory)
	{
		final ImportInvoiceResponseRequest request = createRequest(fileToImport);

		final InvoiceImportClientImpl invoiceImportClientImpl = new InvoiceImportClientImpl(crossVersionServiceRegistry);
		final ImportedInvoiceResponse response = invoiceImportClientImpl.importInvoiceResponse(request);

		final ImportedInvoiceResponse responseWithTags = response.toBuilder()
				.additionalTag("ImportFileAbsolutePath", fileToImport.toAbsolutePath().toString())
				.additionalTag("ImportTimeMillis", Long.toString(SystemTime.millis()))
				.additionalTag("ImportAD_PInstance_ID", Integer.toString(getPinstanceId().getRepoId()))
				.build();

		importedInvoiceResponseRepo.save(responseWithTags);

		moveFile(fileToImport, outputDirectory);
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
		catch (IOException e)
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
		catch (IOException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}
}
