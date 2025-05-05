package de.metas.impexp.format;

import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ImpFormatTypeTest
{
	@Test
	void getCellDelimiterChar()
	{
		assertThat(ImpFormatType.COMMA_SEPARATED.getCellDelimiterChar()).isEqualTo(',');
		assertThat(ImpFormatType.SEMICOLON_SEPARATED.getCellDelimiterChar()).isEqualTo(';');
		assertThat(ImpFormatType.TAB_SEPARATED.getCellDelimiterChar()).isEqualTo('\t');
		assertThatThrownBy(ImpFormatType.XML::getCellDelimiterChar)
				.isInstanceOf(AdempiereException.class)
				.hasMessageStartingWith("Cannot find delimiter for");
		assertThatThrownBy(ImpFormatType.FIXED_POSITION::getCellDelimiterChar)
				.isInstanceOf(AdempiereException.class)
				.hasMessageStartingWith("Cannot find delimiter for");
	}

	@Test
	void getContentType()
	{
		assertThat(ImpFormatType.FIXED_POSITION.getContentType()).isEqualTo("text/plain");
		assertThat(ImpFormatType.COMMA_SEPARATED.getContentType()).isEqualTo("text/csv");
		assertThat(ImpFormatType.SEMICOLON_SEPARATED.getContentType()).isEqualTo("text/csv");
		assertThat(ImpFormatType.TAB_SEPARATED.getContentType()).isEqualTo("text/tab-separated-values");
		assertThat(ImpFormatType.XML.getContentType()).isEqualTo("text/xml");
	}

	@Test
	void getFileExtensionIncludingDot()
	{
		assertThat(ImpFormatType.FIXED_POSITION.getFileExtensionIncludingDot()).isEqualTo(".txt");
		assertThat(ImpFormatType.COMMA_SEPARATED.getFileExtensionIncludingDot()).isEqualTo(".csv");
		assertThat(ImpFormatType.SEMICOLON_SEPARATED.getFileExtensionIncludingDot()).isEqualTo(".csv");
		assertThat(ImpFormatType.TAB_SEPARATED.getFileExtensionIncludingDot()).isEqualTo(".tsv");
		assertThat(ImpFormatType.XML.getFileExtensionIncludingDot()).isEqualTo(".xml");
	}

}