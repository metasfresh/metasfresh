package de.metas.payment.sepa.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.banking.mandate.DirectDebitMandateType;
import de.metas.i18n.Msg;
import de.metas.i18n.TranslatableStrings;
import de.metas.payment.sepa.api.CreatedSEPAExports.CreatedSEPAExport;
import de.metas.payment.sepa.api.SEPACreditTransferXML;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.process.JavaProcess;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_PaySelection;
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

class C_PaySelection_SEPA_XmlExportTest
{
	@Test
	void createSummary_withoutWarnings_isOK()
	{
		final SEPACreditTransferXML xml = xmlBuilder().build();

		assertThat(C_PaySelection_SEPA_XmlExport.createSummary(xml, "de_CH")).isEqualTo(JavaProcess.MSG_OK);
	}

	@Test
	void createSummary_withOneWarning_showsTheWarning()
	{
		final SEPACreditTransferXML xml = xmlBuilder()
				.warning(TranslatableStrings.constant("warning 1"))
				.build();

		assertThat(C_PaySelection_SEPA_XmlExport.createSummary(xml, "de_CH")).isEqualTo("warning 1");
	}

	@Test
	void createSummary_withWarnings_showsAllWarningsNumbered()
	{
		final SEPACreditTransferXML xml = xmlBuilder()
				.warning(TranslatableStrings.constant("warning 1"))
				.warning(TranslatableStrings.constant("warning 2"))
				.build();

		// the WebUI notification collapses line breaks, so each warning is numbered and separated by " · "
		assertThat(C_PaySelection_SEPA_XmlExport.createSummary(xml, "de_CH")).isEqualTo("1) warning 1 · 2) warning 2"); // U+00B7 middle dot
	}

	@Test
	void createSummary_warningWithAtSigns_survivesParseTranslation()
	{
		final String warning = "Business partner TestCreditor (Test Creditor AG), location info@muster.ch / buchhaltung@muster.ch: the address is incomplete.";
		final SEPACreditTransferXML xml = xmlBuilder()
				.warning(TranslatableStrings.constant(warning))
				.build();

		final String summary = C_PaySelection_SEPA_XmlExport.createSummary(xml, "de_CH");

		// JavaProcess runs the summary through parseTranslation, which would take "@muster.ch / buchhaltung@" for an AD_Message/AD_Element; "@@" stands for a plain "@"
		assertThat(summary).isEqualTo("Business partner TestCreditor (Test Creditor AG), location info@@muster.ch / buchhaltung@@muster.ch: the address is incomplete.");
		assertThat(Msg.parseTranslation("de_CH", summary)).isEqualTo(warning);
	}

	/**
	 * AC8, D4: two exports are delivered as one ZIP named after the pay selection, one entry {@code <DocumentNo>_<scheme>.xml} per export,
	 * all names without illegal file name characters; the warnings of both files are kept.
	 */
	@Test
	void zip_twoExports_oneEntryPerScheme() throws Exception
	{
		AdempiereTestHelper.get().init();
		final I_C_PaySelection paySelection = newInstance(I_C_PaySelection.class);
		paySelection.setName("Lastschrift 2026/11");

		final SEPACreditTransferXML zip = C_PaySelection_SEPA_XmlExport.zip(paySelection, ImmutableList.of(
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

	private static C_PaySelection_SEPA_XmlExport.CreatedSEPAExportFile file(
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
		final SEPACreditTransferXML xml = xmlBuilder()
				.filename(documentNo + ".xml")
				.content(content.getBytes(StandardCharsets.UTF_8))
				.warning(TranslatableStrings.constant(warning))
				.build();
		return new C_PaySelection_SEPA_XmlExport.CreatedSEPAExportFile(createdExport, xml);
	}

	private static SEPACreditTransferXML.SEPACreditTransferXMLBuilder xmlBuilder()
	{
		return SEPACreditTransferXML.builder()
				.filename("1000001.xml")
				.contentType("application/xml")
				.content(new byte[] {});
	}
}
