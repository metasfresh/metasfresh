package de.metas.translation.api.impl;

import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.element.api.IElementBL;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_AD_Language;
import org.compiere.util.DB;

import de.metas.i18n.ILanguageDAO;
import de.metas.translation.api.IElementTranslationBL;
import de.metas.util.Check;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ElementTranslationBL implements IElementTranslationBL
{
	private static final String FUNCTION_Update_TRL_Tables_On_AD_Element_TRL_Update = "update_TRL_Tables_On_AD_Element_TRL_Update";
	private static final String FUNCTION_Update_FieldTranslation_From_AD_Name_Element = "update_FieldTranslation_From_AD_Name_Element";
	private static final String FUNCTION_Update_Window_Translation_From_AD_Element = "update_window_translation_from_ad_element";
	private static final String FUNCTION_Update_Tab_Translation_From_AD_Element = "update_tab_translation_from_ad_element";
	private static final String FUNCTION_Update_Menu_Translation_From_AD_Element = "update_menu_translation_from_ad_element";
	private static final String FUNCTION_Update_AD_Element_On_AD_Element_TRL_Update = "update_ad_element_on_ad_element_trl_update";

	private static final String FUNCTION_Update_AD_Element_Trl_From_AD_Tab_Trl = "update_ad_element_trl_from_ad_tab_trl";
	private static final String FUNCTION_Update_AD_Element_Trl_From_AD_Window_Trl = "update_ad_element_trl_from_ad_window_trl";
	private static final String FUNCTION_Update_AD_Element_Trl_From_AD_Menu_Trl = "update_ad_element_trl_from_ad_menu_trl";

	@Override
	public void updateTranslations(final AdElementId adElementId, final String adLanguage)
	{
		// Update Columns, Fields, Parameters, Print Info translation tables
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		DB.executeFunctionCallEx(trxName, addUpdateFunctionCall(FUNCTION_Update_TRL_Tables_On_AD_Element_TRL_Update, adElementId, adLanguage), null);

		final I_AD_Language language = Services.get(ILanguageDAO.class).retrieveByAD_Language(adLanguage);
		Check.assumeNotNull(language, "AD_Language Not Null");

		if (language.isBaseLanguage())
		{
			updateElementFromElementTrl(adElementId, adLanguage);
			Services.get(IElementBL.class).performUpdatesAfterSaveElement(adElementId);
		}

	}

	private String addUpdateFunctionCall(final String functionCall, final AdElementId adElementId, final String adLanguage)
	{
		// #1044
		// Add the prefix DDL so the statement will appear in the migration script
		// Usually, the select statements are not migrated ( see org.compiere.dbPort.Convert.logMigrationScript(String, String).dontLog())
		return MigrationScriptFileLoggerHolder.DDL_PREFIX + " select " + functionCall + "(" + adElementId.getRepoId() + "," + DB.TO_STRING(adLanguage) + ") ";
	}

	@Override
	public void updateFieldTranslationsFromAD_Name(int elementId)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		DB.executeFunctionCallEx(trxName, addUpdateFunctionCallForApplicationDictionaryEntryTRL(FUNCTION_Update_FieldTranslation_From_AD_Name_Element, elementId), null);
	}

	@Override
	public void updateTabTranslationsFromElement(int elementId)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		DB.executeFunctionCallEx(trxName, addUpdateFunctionCallForApplicationDictionaryEntryTRL(FUNCTION_Update_Tab_Translation_From_AD_Element, elementId), null);
	}

	@Override
	public void updateElementTranslationsFromTab(int elementId, int tabId)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		DB.executeFunctionCallEx(trxName, addUpdateFunctionCallForElementTRL(FUNCTION_Update_AD_Element_Trl_From_AD_Tab_Trl, elementId, tabId), null);
	}

	@Override
	public void updateWindowTranslationsFromElement(int elementId)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		DB.executeFunctionCallEx(trxName, addUpdateFunctionCallForApplicationDictionaryEntryTRL(FUNCTION_Update_Window_Translation_From_AD_Element, elementId), null);

	}

	@Override
	public void updateElementTranslationsFromWindow(int elementId, int windowId)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		DB.executeFunctionCallEx(trxName, addUpdateFunctionCallForElementTRL(FUNCTION_Update_AD_Element_Trl_From_AD_Window_Trl, elementId, windowId), null);
	}

	@Override
	public void updateElementTranslationsFromMenu(int elementId, int menuId)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		DB.executeFunctionCallEx(trxName, addUpdateFunctionCallForElementTRL(FUNCTION_Update_AD_Element_Trl_From_AD_Menu_Trl, elementId, menuId), null);
	}

	@Override
	public void updateMenuTranslationsFromElement(int elementId)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		DB.executeFunctionCallEx(trxName, addUpdateFunctionCallForApplicationDictionaryEntryTRL(FUNCTION_Update_Menu_Translation_From_AD_Element, elementId), null);
	}

	private String addUpdateFunctionCallForApplicationDictionaryEntryTRL(final String functionCall, int elementId)
	{
		return MigrationScriptFileLoggerHolder.DDL_PREFIX + " select " + functionCall + "(" + elementId + ") ";
	}

	private String addUpdateFunctionCallForElementTRL(final String functionCall, int elementId, int applicationDictionaryEntryId)
	{
		return MigrationScriptFileLoggerHolder.DDL_PREFIX + " select " + functionCall + "(" + elementId + ", " + applicationDictionaryEntryId + ") ";
	}

	@Override
	public void updateElementFromElementTrl(final AdElementId adElementId, final String adLanguage)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		{
			DB.executeFunctionCallEx(trxName, addUpdateFunctionCall(FUNCTION_Update_AD_Element_On_AD_Element_TRL_Update, adElementId, adLanguage), null);
		}
	}

}
