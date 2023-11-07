package de.metas.acct.gljournal_sap.quickinput;

import de.metas.acct.model.I_SAP_GLJournalLine;
import de.metas.quickinput.config.QuickInputConfigLayout;
import de.metas.sysconfig.SysConfigListener;
import lombok.NonNull;
import org.adempiere.ad.tab.model.interceptor.ADTabQuickInputLayoutValidator;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class SAPGLJournalLineQuickInputConfigValidator implements SysConfigListener, ADTabQuickInputLayoutValidator
{
	public static final String SYSCONFIG_LayoutConfig = "SAP_GLJournalLine.quickInput.layout";

	static void assertValid(@NonNull final QuickInputConfigLayout layoutConfig)
	{
		layoutConfig.assertFieldExistsAndIsMandatory(ISAPGLJournalLineQuickInput.COLUMNNAME_PostingSign);
		layoutConfig.assertFieldExistsAndIsMandatory(ISAPGLJournalLineQuickInput.COLUMNNAME_GL_Account_ID);
		layoutConfig.assertFieldExistsAndIsMandatory(ISAPGLJournalLineQuickInput.COLUMNNAME_Amount);
		layoutConfig.assertFieldNamesEligible(ISAPGLJournalLineQuickInput.COLUMNNAMES);
	}

	@Override
	public String getHandledTabQuickInputTableName() {return I_SAP_GLJournalLine.Table_Name;}

	@Override
	public void validateTabQuickInputLayout(@NonNull final QuickInputConfigLayout layoutConfig) {assertValid(layoutConfig);}

	@Override
	public void assertValidSysConfigValue(final String sysconfigName, @Nullable final String value)
	{
		if (SYSCONFIG_LayoutConfig.equals(sysconfigName))
		{
			QuickInputConfigLayout.parse(value).ifPresent(SAPGLJournalLineQuickInputConfigValidator::assertValid);
		}
	}
}
