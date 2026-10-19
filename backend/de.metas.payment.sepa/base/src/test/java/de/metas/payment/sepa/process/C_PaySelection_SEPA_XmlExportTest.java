package de.metas.payment.sepa.process;

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
	void createSummary_withWarnings_showsAllWarnings()
	{
		final SEPACreditTransferXML xml = xmlBuilder()
				.warning(TranslatableStrings.constant("warning 1"))
				.warning(TranslatableStrings.constant("warning 2"))
				.build();

		assertThat(C_PaySelection_SEPA_XmlExport.createSummary(xml, "de_CH")).isEqualTo("warning 1\nwarning 2");
	}

	private static SEPACreditTransferXML.SEPACreditTransferXMLBuilder xmlBuilder()
	{
		return SEPACreditTransferXML.builder()
				.filename("1000001.xml")
				.contentType("application/xml")
				.content(new byte[] {});
	}
}
