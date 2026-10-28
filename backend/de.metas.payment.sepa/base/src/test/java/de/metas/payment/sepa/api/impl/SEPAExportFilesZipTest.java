package de.metas.payment.sepa.api.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.banking.mandate.DirectDebitMandateType;
import de.metas.i18n.TranslatableStrings;
import de.metas.payment.sepa.api.CreatedSEPAExports.CreatedSEPAExport;
import de.metas.payment.sepa.api.SEPACreditTransferXML;
import de.metas.payment.sepa.model.I_SEPA_Export;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class SEPAExportFilesZipTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * Two files are packed into one ZIP named {@code <zipBaseName>.zip}, one entry {@code <DocumentNo>_<scheme>.xml} per file,
	 * all names without illegal file name characters; the warnings of both files are kept.
	 */
	@Test
	void zip_twoExports_oneEntryPerScheme() throws Exception
	{
		final SEPACreditTransferXML zip = SEPAExportFilesZip.zip("Lastschrift 2026/11", ImmutableList.of(
				file("1000001", DirectDebitMandateType.B2B, "<b2b/>", "warning B2B"),
				file("1000/002", DirectDebitMandateType.CORE, "<core/>", "warning CORE")));

		assertThat(zip.getFilename()).isEqualTo("Lastschrift 202611.zip");
		assertThat(zip.getContentType()).isEqualTo("application/zip");
		assertThat(zip.getWarnings()).extracting(warning -> warning.translate("de_DE")).containsExactly("warning B2B", "warning CORE");

		final Map<String, String> entries = new LinkedHashMap<>();
		try (final ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zip.getContent())))
		{
			ZipEntry entry;
			while ((entry = zis.getNextEntry()) != null)
			{
				entries.put(entry.getName(), new String(StreamUtils.copyToByteArray(zis), StandardCharsets.UTF_8));
			}
		}
		assertThat(entries).containsExactly(
				entry("1000001_B2B.xml", "<b2b/>"),
				entry("1000002_CORE.xml", "<core/>"));
	}

	private static SEPAExportFilesZip.CreatedSEPAExportFile file(
			final String documentNo,
			final DirectDebitMandateType scheme,
			final String content,
			final String warning)
	{
		final I_SEPA_Export export = newInstance(I_SEPA_Export.class);
		export.setDocumentNo(documentNo);

		final CreatedSEPAExport createdExport = CreatedSEPAExport.builder()
				.export(export)
				.scheme(scheme)
				.mandatesBySepaExportLineId(ImmutableMap.of())
				.build();
		final SEPACreditTransferXML xml = SEPACreditTransferXML.builder()
				.filename(documentNo + ".xml")
				.contentType("application/xml")
				.content(content.getBytes(StandardCharsets.UTF_8))
				.warning(TranslatableStrings.constant(warning))
				.build();
		return new SEPAExportFilesZip.CreatedSEPAExportFile(createdExport, xml);
	}
}
