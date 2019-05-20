package de.metas.translation.api;

import java.sql.SQLException;

import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.element.api.ElementChangedEvent;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.compiere.model.I_AD_Menu;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_Window;

import de.metas.util.ISingletonService;

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

public interface IElementTranslationBL extends ISingletonService
{
	ModelDynAttributeAccessor<I_AD_Menu, Boolean> DYNATTR_AD_Menu_UpdateTranslations = new ModelDynAttributeAccessor<>("AD_Menu_UpdateTranslations", Boolean.class);
	ModelDynAttributeAccessor<I_AD_Tab, Boolean> DYNATTR_AD_Tab_UpdateTranslations = new ModelDynAttributeAccessor<>("AD_Tab_UpdateTranslations", Boolean.class);
	ModelDynAttributeAccessor<I_AD_Window, Boolean> DYNATTR_AD_Window_UpdateTranslations = new ModelDynAttributeAccessor<>("AD_Window_UpdateTranslations", Boolean.class);

	/**
	 * Method used for updating the data in the following tables:
	 *
	 * <li>AD_Column_TRL,
	 * <li>AD_Process_Para_TRL,
	 * <li>AD_Field_TRL,
	 * <li>AD_PrintFormatItem_TRL
	 * </li>
	 *
	 * Specific columns are updated based on the similar columns from the table AD_Element_TRL, where the AD_Element_ID and the AD_Language are the given ones
	 *
	 * @param elementId
	 * @param adLanguage
	 * @throws SQLException
	 */
	void updateTranslations(ElementChangedEvent event);
	
	void updateColumnTranslationsFromElement(AdElementId adElementId);

	/**
	 * Update Fields translation for the fields that have the element given as parameter as AD_Name_ID, delete translation if AD_Name was deleted
	 *
	 * @param ad_Element_ID
	 */
	void updateFieldTranslationsFromAD_Name(AdElementId adElementId);

	void updateWindowTranslationsFromElement(AdElementId adElementId);

	void updateTabTranslationsFromElement(AdElementId adElementId);

	void updateMenuTranslationsFromElement(AdElementId adElementId);

	void updateDependentADEntries(ElementChangedEvent event);

	void createAndAssignElementsToApplicationDictionaryEntries();
}
