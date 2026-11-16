package de.metas.payment.sepa.api.impl;

/*
 * #%L
 * de.metas.payment.sepa.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

import de.metas.banking.mandate.DirectDebitMandateType;
import de.metas.payment.sepa.api.CreatedSEPAExports.CreatedSEPAExport;
import de.metas.payment.sepa.api.SEPACreditTransferXML;
import de.metas.util.FileUtil;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.MimeType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Packs the XML files of several SEPA exports of one pay selection into one ZIP.
 */
@UtilityClass
class SEPAExportFilesZip
{
	/**
	 * @return one ZIP named {@code <zipBaseName>.zip}, with one entry {@code <DocumentNo>_<scheme>.xml} per file and the warnings of all files
	 */
	SEPACreditTransferXML zip(@NonNull final String zipBaseName, @NonNull final List<CreatedSEPAExportFile> files)
	{
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final SEPACreditTransferXML.SEPACreditTransferXMLBuilder result = SEPACreditTransferXML.builder();
		try (final ZipOutputStream zip = new ZipOutputStream(out))
		{
			for (final CreatedSEPAExportFile file : files)
			{
				zip.putNextEntry(new ZipEntry(file.getZipEntryName()));
				zip.write(file.getXml().getContent());
				zip.closeEntry();

				result.warnings(file.getXml().getWarnings());
			}
		}
		catch (final IOException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}

		final String filename = FileUtil.stripIllegalCharacters(zipBaseName) + ".zip";
		return result
				.filename(filename)
				.contentType(MimeType.getMimeType(filename))
				.content(out.toByteArray())
				.build();
	}

	/**
	 * The XML file of one SEPA export.
	 */
	@Value
	static class CreatedSEPAExportFile
	{
		@NonNull CreatedSEPAExport createdExport;
		@NonNull SEPACreditTransferXML xml;

		/**
		 * @return {@code <DocumentNo>_<scheme>.xml}, or {@code <DocumentNo>.xml} for an export without a scheme
		 */
		String getZipEntryName()
		{
			final DirectDebitMandateType scheme = createdExport.getScheme();
			final String documentNo = createdExport.getExport().getDocumentNo();
			final String baseName = scheme != null ? documentNo + "_" + scheme.getCode() : documentNo;
			return FileUtil.stripIllegalCharacters(baseName) + ".xml";
		}
	}
}
