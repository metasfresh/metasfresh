package de.metas.acct.gljournal_sap.quickinput;

import de.metas.quickinput.config.QuickInputConfigLayout;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SAPGLJournalLineQuickInputConfigValidatorTest
{
	private SAPGLJournalLineQuickInputConfigValidator configProvider;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		configProvider = new SAPGLJournalLineQuickInputConfigValidator();
	}

	@Nested
	class assertValidSysConfigValue
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
			configProvider.assertValidSysConfigValue(SAPGLJournalLineQuickInputConfigValidator.SYSCONFIG_LayoutConfig, layoutConfigString);
			QuickInputConfigLayout.parse(layoutConfigString).ifPresent(layoutConfig -> configProvider.validateTabQuickInputLayout(layoutConfig));
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
			Assertions.assertThatThrownBy(() -> configProvider.assertValidSysConfigValue(SAPGLJournalLineQuickInputConfigValidator.SYSCONFIG_LayoutConfig, layoutConfigString))
					.isInstanceOf(AdempiereException.class);

		}
	}
}
