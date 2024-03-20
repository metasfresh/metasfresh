package de.metas.acct.gljournal_sap.quickinput;

import de.metas.quickinput.config.QuickInputConfigLayout;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SAPGLJournalLineQuickInputConfigValidatorTest
{
	@Nested
	class assertValid
	{
		@ParameterizedTest
		@ValueSource(strings = {
				"",
				"PostingSign,GL_Account_ID,Amount",
				"PostingSign,GL_Account_ID,Amount,C_Tax_ID?",
				"PostingSign,GL_Account_ID,Amount,M_SectionCode_ID,C_Tax_ID?",
				"PostingSign,GL_Account_ID,Amount,C_Activity_ID,C_Tax_ID?",
				"PostingSign,GL_Account_ID,Amount,C_Activity_ID?,C_Tax_ID?",
		})
		void valid(final String layoutConfigString)
		{
			QuickInputConfigLayout.parse(layoutConfigString).ifPresent(SAPGLJournalLineQuickInputConfigValidator::assertValid);
		}

		@ParameterizedTest
		@ValueSource(strings = {
				"PostingSign,GL_Account_ID,Amount,UnknownField",
				"PostingSign,GL_Account_ID,Amount,C_Tax_ID??", // wrong syntax
				"Missing_PostingSign,GL_Account_ID,Amount", // missing mandatory field
				"PostingSign,Missing_GL_Account_ID,Amount", // missing mandatory field
				"PostingSign,GL_Account_ID,Missing_Amount", // missing mandatory field
				"PostingSign?,GL_Account_ID,Amount", // mandatory fields shall be mandatory
				"PostingSign,GL_Account_ID?,Amount", // mandatory fields shall be mandatory
				"PostingSign,GL_Account_ID,Amount?", // mandatory fields shall be mandatory
		})
		void not_valid(final String layoutConfigString)
		{
			final QuickInputConfigLayout layoutConfig = QuickInputConfigLayout.parse(layoutConfigString)
					.orElseThrow(() -> new AdempiereException("Layout config `" + layoutConfigString + "` was expected to be not empty"));
			Assertions.assertThatThrownBy(() -> SAPGLJournalLineQuickInputConfigValidator.assertValid(layoutConfig))
					.isInstanceOf(AdempiereException.class);
		}
	}
}
