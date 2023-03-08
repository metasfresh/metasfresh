package org.compiere.process;

import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.Services;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.compiere.model.I_AD_Tab;

public class AD_Window_CopyTabFrom extends JavaProcess
{
	private final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);

	@Param(parameterName = "Template_Tab_ID", mandatory = true)
	private AdTabId p_Template_Tab_ID;

	@Override
	protected String doIt()
	{
		final AdWindowId targetWindowId = AdWindowId.ofRepoId(getRecord_ID());
		final I_AD_Tab templateTab = adWindowDAO.getTabByIdInTrx(p_Template_Tab_ID);
		adWindowDAO.copyTabToWindow(templateTab, targetWindowId);
		return MSG_OK;
	}
}
