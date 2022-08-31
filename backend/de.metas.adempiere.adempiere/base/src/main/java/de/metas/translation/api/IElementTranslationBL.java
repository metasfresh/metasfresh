package de.metas.translation.api;

import de.metas.util.ISingletonService;
import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.compiere.model.I_AD_Menu;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_Window;

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

	void propagateElementTrls(AdElementId adElementId, String adLanguage);

	void updateColumnTranslationsFromElement(AdElementId adElementId);

	/**
	 * Update Fields translation for the fields that have the element given as parameter as AD_Name_ID, delete translation if AD_Name was deleted
	 */
	void updateFieldTranslationsFromAD_Name(AdElementId adElementId);

	void updateWindowTranslationsFromElement(AdElementId adElementId);

	void updateTabTranslationsFromElement(AdElementId adElementId);

	void updateMenuTranslationsFromElement(AdElementId adElementId);

	void updateElementFromElementTrlIfBaseLanguage(AdElementId adElementId, String adLanguage);

	void createAndAssignElementsToApplicationDictionaryEntries();
}
