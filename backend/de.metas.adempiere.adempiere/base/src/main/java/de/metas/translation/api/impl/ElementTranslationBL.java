package de.metas.translation.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.Set;

import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.element.api.CreateADElementRequest;
import org.adempiere.ad.element.api.ElementChangedEvent;
import org.adempiere.ad.element.api.ElementChangedEvent.ChangedField;
import org.adempiere.ad.element.api.IADElementDAO;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Language;
import org.compiere.model.I_AD_Menu;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_Window;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.i18n.ILanguageDAO;
import de.metas.logging.LogManager;
import de.metas.menu.AdMenuId;
import de.metas.menu.IADMenuDAO;
import de.metas.translation.api.IElementTranslationBL;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAware;

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
	private static final Logger log = LogManager.getLogger(ElementTranslationBL.class);

	private static final String FUNCTION_Update_TRL_Tables_On_AD_Element_TRL_Update = "update_TRL_Tables_On_AD_Element_TRL_Update";
	private static final String FUNCTION_Update_Column_Translation_From_AD_Name_Element = "update_Column_Translation_From_AD_Element";
	private static final String FUNCTION_Update_FieldTranslation_From_AD_Name_Element = "update_FieldTranslation_From_AD_Name_Element";
	private static final String FUNCTION_Update_Window_Translation_From_AD_Element = "update_window_translation_from_ad_element";
	private static final String FUNCTION_Update_Tab_Translation_From_AD_Element = "update_tab_translation_from_ad_element";
	private static final String FUNCTION_Update_Menu_Translation_From_AD_Element = "update_menu_translation_from_ad_element";

	private static final String FUNCTION_Update_AD_Element_On_AD_Element_TRL_Update = "update_ad_element_on_ad_element_trl_update";

	private static final String FUNCTION_Update_AD_Element_Trl_From_AD_Tab_Trl = "update_ad_element_trl_from_ad_tab_trl";
	private static final String FUNCTION_Update_AD_Element_Trl_From_AD_Window_Trl = "update_ad_element_trl_from_ad_window_trl";
	private static final String FUNCTION_Update_AD_Element_Trl_From_AD_Menu_Trl = "update_ad_element_trl_from_ad_menu_trl";

	@Override
	public void updateTranslations(final ElementChangedEvent event)
	{
		// Update Columns, Fields, Parameters, Print Info translation tables
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		DB.executeFunctionCallEx(trxName, addUpdateFunctionCall(FUNCTION_Update_TRL_Tables_On_AD_Element_TRL_Update, event.getAdElementId(), event.getAdLanguage()), null);

		if (isBaseLanguage(event))
		{
			updateElementFromElementTrl(event.getAdElementId(), event.getAdLanguage());

			updateDependentADEntries(event);
		}
	}

	private boolean isBaseLanguage(final ElementChangedEvent event)
	{
		final String adLanguage = event.getAdLanguage();

		// when language is not set, consider it as base language
		if (adLanguage == null)
		{
			return true;
		}

		final I_AD_Language language = Services.get(ILanguageDAO.class).retrieveByAD_Language(adLanguage);
		Check.assumeNotNull(language, "AD_Language Not Null");

		return language.isBaseLanguage();
	}

	private String addUpdateFunctionCall(final String functionCall, final AdElementId adElementId, final String adLanguage)
	{
		// #1044
		// Add the prefix DDL so the statement will appear in the migration script
		// Usually, the select statements are not migrated ( see org.compiere.dbPort.Convert.logMigrationScript(String, String).dontLog())
		return MigrationScriptFileLoggerHolder.DDL_PREFIX + " select " + functionCall + "(" + adElementId.getRepoId() + "," + DB.TO_STRING(adLanguage) + ") ";
	}

	@Override
	public void updateColumnTranslationsFromElement(final AdElementId adElementId)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		DB.executeFunctionCallEx(trxName, addUpdateFunctionCallForApplicationDictionaryEntryTRL(FUNCTION_Update_Column_Translation_From_AD_Name_Element, adElementId), null);
	}

	@Override
	public void updateFieldTranslationsFromAD_Name(final AdElementId adElementId)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		DB.executeFunctionCallEx(trxName, addUpdateFunctionCallForApplicationDictionaryEntryTRL(FUNCTION_Update_FieldTranslation_From_AD_Name_Element, adElementId), null);
	}

	@Override
	public void updateTabTranslationsFromElement(final AdElementId adElementId)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		DB.executeFunctionCallEx(trxName, addUpdateFunctionCallForApplicationDictionaryEntryTRL(FUNCTION_Update_Tab_Translation_From_AD_Element, adElementId), null);
	}

	private void updateElementTranslationsFromTab(final AdElementId adElementId, final AdTabId adTabId)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		DB.executeFunctionCallEx(trxName, addUpdateFunctionCallForElementTRL(FUNCTION_Update_AD_Element_Trl_From_AD_Tab_Trl, adElementId, adTabId), null);
	}

	@Override
	public void updateWindowTranslationsFromElement(final AdElementId adElementId)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		DB.executeFunctionCallEx(trxName, addUpdateFunctionCallForApplicationDictionaryEntryTRL(FUNCTION_Update_Window_Translation_From_AD_Element, adElementId), null);
	}

	private void updateElementTranslationsFromWindow(final AdElementId adElementId, final AdWindowId adWindowId)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		DB.executeFunctionCallEx(trxName, addUpdateFunctionCallForElementTRL(FUNCTION_Update_AD_Element_Trl_From_AD_Window_Trl, adElementId, adWindowId), null);
	}

	private void updateElementTranslationsFromMenu(final AdElementId adElementId, final AdMenuId adMenuId)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		DB.executeFunctionCallEx(trxName, addUpdateFunctionCallForElementTRL(FUNCTION_Update_AD_Element_Trl_From_AD_Menu_Trl, adElementId, adMenuId), null);
	}

	@Override
	public void updateMenuTranslationsFromElement(final AdElementId adElementId)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		DB.executeFunctionCallEx(trxName, addUpdateFunctionCallForApplicationDictionaryEntryTRL(FUNCTION_Update_Menu_Translation_From_AD_Element, adElementId), null);
	}

	private String addUpdateFunctionCallForApplicationDictionaryEntryTRL(final String functionCall, final AdElementId adElementId)
	{
		return MigrationScriptFileLoggerHolder.DDL_PREFIX + " select " + functionCall + "(" + adElementId.getRepoId() + ") ";
	}

	private String addUpdateFunctionCallForElementTRL(final String functionCall, final AdElementId adElementId, final RepoIdAware applicationDictionaryEntryId)
	{
		return MigrationScriptFileLoggerHolder.DDL_PREFIX + " select " + functionCall + "(" + adElementId.getRepoId() + ", " + applicationDictionaryEntryId.getRepoId() + ") ";
	}

	private void updateElementFromElementTrl(final AdElementId adElementId, final String adLanguage)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		DB.executeFunctionCallEx(trxName, addUpdateFunctionCall(FUNCTION_Update_AD_Element_On_AD_Element_TRL_Update, adElementId, adLanguage), null);
	}

	@Override
	public void createAndAssignElementsToApplicationDictionaryEntries()
	{
		createAndLinkElementsForTabs();
		createAndLinkElementsForWindows();
		createAndLinkElementsForMenus();
	}

	private void createAndLinkElementsForTabs()
	{
		final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);
		final IADElementDAO adElementsRepo = Services.get(IADElementDAO.class);

		for (final AdTabId tabId : adWindowDAO.retrieveTabIdsWithMissingADElements())
		{
			final I_AD_Tab tab = adWindowDAO.getTabByIdInTrx(tabId);

			final AdElementId elementId = adElementsRepo.createNewElement(CreateADElementRequest.builder()
					.name(tab.getName())
					.printName(tab.getName())
					.description(tab.getDescription())
					.help(tab.getHelp())
					.tabCommitWarning(tab.getCommitWarning())
					.build());

			updateElementTranslationsFromTab(elementId, tabId);

			DYNATTR_AD_Tab_UpdateTranslations.setValue(tab, false);

			tab.setAD_Element_ID(elementId.getRepoId());
			save(tab);
		}
	}

	private void createAndLinkElementsForWindows()
	{
		final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);
		final IADElementDAO adElementsRepo = Services.get(IADElementDAO.class);

		final Set<AdWindowId> windowIdsWithMissingADElements = adWindowDAO.retrieveWindowIdsWithMissingADElements();

		for (final AdWindowId windowId : windowIdsWithMissingADElements)
		{
			final I_AD_Window window = adWindowDAO.getWindowByIdInTrx(windowId);

			final AdElementId elementId = adElementsRepo.createNewElement(CreateADElementRequest.builder()
					.name(window.getName())
					.printName(window.getName())
					.description(window.getDescription())
					.help(window.getHelp())
					.build());

			updateElementTranslationsFromWindow(elementId, windowId);

			DYNATTR_AD_Window_UpdateTranslations.setValue(window, false);

			window.setAD_Element_ID(elementId.getRepoId());
			save(window);
		}
	}

	private void createAndLinkElementsForMenus()
	{
		final IADMenuDAO adMenuDAO = Services.get(IADMenuDAO.class);
		final IADElementDAO adElementsRepo = Services.get(IADElementDAO.class);

		final Set<AdMenuId> menuIdsWithMissingADElements = adMenuDAO.retrieveMenuIdsWithMissingADElements();

		for (final AdMenuId menuId : menuIdsWithMissingADElements)
		{
			final I_AD_Menu menu = adMenuDAO.getById(menuId);

			final AdElementId elementId = adElementsRepo.createNewElement(CreateADElementRequest.builder()
					.name(menu.getName())
					.printName(menu.getName())
					.description(menu.getDescription())
					.webuiNameBrowse(menu.getWEBUI_NameBrowse())
					.webuiNameNew(menu.getWEBUI_NameNew())
					.webuiNameNewBreadcrumb(menu.getWEBUI_NameNewBreadcrumb()).build());

			updateElementTranslationsFromMenu(elementId, menuId);

			DYNATTR_AD_Menu_UpdateTranslations.setValue(menu, false);

			menu.setAD_Element_ID(elementId.getRepoId());
			save(menu);
		}
	}

	@Override
	public void updateDependentADEntries(final ElementChangedEvent event)
	{
		if (availableUpdatesForADColumn(event))
		{
			updateADColumns(event);
		}

		if (availableUpdatesForADProcessParas(event))
		{
			updateADProcessParams(event);
		}

		if (availableUpdatesForADField(event))
		{
			updateADFields(event);
		}

		if (availableUpdatesForPrintFormatItem(event))
		{
			updateADPrintFormatItems(event);
		}

		if (availableUpdatesForADTab(event))
		{
			updateADTabs(event);
		}

		if (availableUpdatesForADWindow(event))
		{

			updateADWindows(event);
		}

		if (availableUpdatesForADMenu(event))
		{
			updateADMenus(event);
		}
	}

	private boolean availableUpdatesForADColumn(final ElementChangedEvent event)
	{
		return event.isChangedAnyOf(
				ChangedField.Name,
				ChangedField.Description,
				ChangedField.Help,
				ChangedField.ColumnName);
	}

	private boolean availableUpdatesForADProcessParas(final ElementChangedEvent event)
	{
		return event.isChangedAnyOf(
				ChangedField.Name,
				ChangedField.Description,
				ChangedField.Help,
				ChangedField.ColumnName);
	}

	private boolean availableUpdatesForADField(final ElementChangedEvent event)
	{
		return event.isChangedAnyOf(
				ChangedField.Name,
				ChangedField.Description,
				ChangedField.Help);
	}

	private boolean availableUpdatesForPrintFormatItem(final ElementChangedEvent event)
	{
		return event.isChangedAnyOf(
				ChangedField.Name,
				ChangedField.PrintName);
	}

	private boolean availableUpdatesForADTab(final ElementChangedEvent event)
	{
		return event.isChangedAnyOf(
				ChangedField.Name,
				ChangedField.Description,
				ChangedField.Help,
				ChangedField.CommitWarning);
	}

	private boolean availableUpdatesForADWindow(final ElementChangedEvent event)
	{
		return event.isChangedAnyOf(
				ChangedField.Name,
				ChangedField.Description,
				ChangedField.Help);
	}

	private boolean availableUpdatesForADMenu(final ElementChangedEvent event)
	{
		return event.isChangedAnyOf(
				ChangedField.Name,
				ChangedField.Description,
				ChangedField.Help,
				ChangedField.WebuiNameBrowse,
				ChangedField.WebuiNameNew,
				ChangedField.WebuiNameNewBreadcrumb);
	}

	private void updateADMenus(final ElementChangedEvent event)
	{
		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(I_AD_Menu.Table_Name)
				.append(" SET ")
				.append("  ").append(I_AD_Menu.COLUMNNAME_Name).append(" = ").append(DB.TO_STRING(event.getName()))
				.append(", ").append(I_AD_Menu.COLUMNNAME_Description).append(" = ").append(DB.TO_STRING(event.getDescription()))
				.append(", ").append(I_AD_Menu.COLUMNNAME_WEBUI_NameBrowse).append(" = ").append(DB.TO_STRING(event.getWebuiNameBrowse()))
				.append(", ").append(I_AD_Menu.COLUMNNAME_WEBUI_NameNew).append(" = ").append(DB.TO_STRING(event.getWebuiNameNew()))
				.append(", ").append(I_AD_Menu.COLUMNNAME_WEBUI_NameNewBreadcrumb).append(" = ").append(DB.TO_STRING(event.getWebuiNameNewBreadcrumb()))
				.append(" WHERE AD_Element_ID = ").append(event.getAdElementId().getRepoId());

		final int updateResultsCounter = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		log.debug("Menus updated #{}", updateResultsCounter);
	}

	private void updateADWindows(final ElementChangedEvent event)
	{
		final StringBuilder sql = new StringBuilder("UPDATE AD_WINDOW SET Name=")
				.append(DB.TO_STRING(event.getName()))
				.append(", Description=").append(DB.TO_STRING(event.getDescription()))
				.append(", Help=").append(DB.TO_STRING(event.getHelp()))
				.append(" WHERE AD_Element_ID = ").append(event.getAdElementId().getRepoId());

		final int updateResultsCounter = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		log.debug("Windows updated #{}", updateResultsCounter);
	}

	private void updateADTabs(final ElementChangedEvent event)
	{
		final StringBuilder sql = new StringBuilder("UPDATE AD_Tab SET Name=")
				.append(DB.TO_STRING(event.getName()))
				.append(", Description=").append(DB.TO_STRING(event.getDescription()))
				.append(", Help=").append(DB.TO_STRING(event.getHelp()))
				.append(", ").append(I_AD_Element.COLUMNNAME_CommitWarning).append(" = ").append(DB.TO_STRING(event.getCommitWarning()))
				.append(" WHERE AD_Element_ID = ").append(event.getAdElementId().getRepoId());

		final int updateResultsCounter = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		log.debug("Tabs updated #{}", updateResultsCounter);
	}

	private void updateADPrintFormatItems(final ElementChangedEvent event)
	{
		final StringBuilder sql = new StringBuilder("UPDATE AD_PrintFormatItem pi SET PrintName=")
				.append(DB.TO_STRING(event.getPrintName()))
				.append(", Name=").append(DB.TO_STRING(event.getName()))
				.append(" WHERE IsCentrallyMaintained='Y'")
				.append(" AND EXISTS (SELECT * FROM AD_Column c ")
				.append(" WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=")
				.append(event.getAdElementId().getRepoId()).append(")");
		final int updateResultsCounter = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		log.debug("PrintFormatItem updated #{}", updateResultsCounter);
	}

	private void updateADFields(final ElementChangedEvent event)
	{
		final StringBuilder sql = new StringBuilder("UPDATE AD_Field SET Name=")
				.append(DB.TO_STRING(event.getName()))
				.append(", Description=").append(DB.TO_STRING(event.getDescription()))
				.append(", Help=").append(DB.TO_STRING(event.getHelp()))
				.append(" WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=")
				.append(event.getAdElementId().getRepoId())
				.append(")")
				.append(" AND ")
				.append(I_AD_Field.COLUMNNAME_AD_Name_ID).append(" IS NULL ")
				.append(")")
				.append(" OR ")
				.append("(")
				.append(I_AD_Field.COLUMNNAME_AD_Name_ID).append(" = ").append(event.getAdElementId().getRepoId())
				.append(")");
		final int updateResultsCounter = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		log.debug("Fields updated #{}", updateResultsCounter);
	}

	private void updateADProcessParams(final ElementChangedEvent event)
	{
		int updateResultsCounter = 0;

		// Parameter
		if (event.getColumnName() != null)
		{
			final StringBuilder sql = new StringBuilder("UPDATE AD_Process_Para SET ColumnName=")
					.append(DB.TO_STRING(event.getColumnName()))
					.append(", Name=").append(DB.TO_STRING(event.getName()))
					.append(", Description=").append(DB.TO_STRING(event.getDescription()))
					.append(", Help=").append(DB.TO_STRING(event.getHelp()))
					.append(", AD_Element_ID=").append(event.getAdElementId().getRepoId())
					.append(" WHERE UPPER(ColumnName)=")
					.append(DB.TO_STRING(event.getColumnName().toUpperCase()))
					.append(" AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL");
			updateResultsCounter = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		}

		final StringBuilder sql = new StringBuilder("UPDATE AD_Process_Para SET ColumnName=")
				.append(DB.TO_STRING(event.getColumnName()))
				.append(", Name=").append(DB.TO_STRING(event.getName()))
				.append(", Description=").append(DB.TO_STRING(event.getDescription()))
				.append(", Help=").append(DB.TO_STRING(event.getHelp()))
				.append(" WHERE AD_Element_ID=").append(event.getAdElementId().getRepoId())
				.append(" AND IsCentrallyMaintained='Y'");
		updateResultsCounter += DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		log.debug("Parameters updated #{}", updateResultsCounter);
	}

	private void updateADColumns(final ElementChangedEvent event)
	{
		final StringBuilder sql = new StringBuilder("UPDATE AD_Column SET ColumnName=")
				.append(DB.TO_STRING(event.getColumnName()))
				.append(", Name=").append(DB.TO_STRING(event.getName()))
				.append(", Description=").append(DB.TO_STRING(event.getDescription()))
				.append(", Help=").append(DB.TO_STRING(event.getHelp()))
				.append(" WHERE AD_Element_ID=").append(event.getAdElementId().getRepoId());
		final int updateResultsCounter = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		log.debug("Columns updated #{}", updateResultsCounter);
	}

}
