package de.metas.i18n.process;

import org.adempiere.util.Services;
import org.compiere.model.I_AD_Language;

import de.metas.i18n.ILanguageDAO;
import de.metas.process.JavaProcess;
import de.metas.process.Param;

/**
 * Created/Re-Create/Delete language translations for currently selected {@link I_AD_Language}.
 */
public class AD_Language_Maintenance extends JavaProcess
{
	private final transient ILanguageDAO languageDAO = Services.get(ILanguageDAO.class);

	@Param(parameterName = "MaintenanceMode", mandatory = true)
	private String p_MaintenanceMode;

	@Override
	protected String doIt()
	{
		final I_AD_Language m_language = getRecord(I_AD_Language.class);
		languageDAO.maintainTranslations(m_language, p_MaintenanceMode);
		return MSG_OK;
	}
}
