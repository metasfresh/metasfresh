package org.adempiere.ad.tab.model.interceptor;

import de.metas.quickinput.config.QuickInputConfigLayout;

public interface ADTabQuickInputLayoutValidator
{
	String getHandledTabQuickInputTableName();

	void validateTabQuickInputLayout(QuickInputConfigLayout layoutConfig);
}
