package de.metas.acct.gljournal_sap.quickinput;

import de.metas.acct.model.I_SAP_GLJournalLine;
import de.metas.quickinput.config.QuickInputConfigLayout;
import lombok.NonNull;
import org.adempiere.ad.tab.model.interceptor.ADTabQuickInputLayoutValidator;
import org.springframework.stereotype.Component;

@Component
public class SAPGLJournalLineQuickInputConfigValidator implements ADTabQuickInputLayoutValidator
{
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
}
