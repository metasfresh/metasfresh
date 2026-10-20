package de.metas.payment.sepa.process;

import de.metas.i18n.Msg;
import de.metas.i18n.TranslatableStrings;
import de.metas.payment.sepa.api.SEPACreditTransferXML;
import de.metas.process.JavaProcess;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

	private static SEPACreditTransferXML.SEPACreditTransferXMLBuilder xmlBuilder()
	{
		return SEPACreditTransferXML.builder()
				.filename("1000001.xml")
				.contentType("application/xml")
				.content(new byte[] {});
	}
}
