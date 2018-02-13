package de.metas.translation.api;

import java.sql.SQLException;

import org.adempiere.util.ISingletonService;

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
	void updateTranslations(int elementId, String adLanguage);

	/**
	 * Update Fields translation for the fields that have the element given as parameter as AD_Name_ID, delete translation if AD_Name was deleted
	 * 
	 * @param ad_Element_ID
	 */
	void updateFieldTranslationsFromAD_Name(int ad_Element_ID);

}
