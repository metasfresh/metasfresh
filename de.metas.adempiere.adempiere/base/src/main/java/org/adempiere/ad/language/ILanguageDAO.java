package org.adempiere.ad.language;

import java.util.List;
import java.util.Properties;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Language;

public interface ILanguageDAO extends ISingletonService
{
	List<I_AD_Language> retrieveAvailableLanguages(Properties ctx, int clientId);
	
	/**
	 * @param ctx
	 * @return all available AD_Languages, having the base language as first item
	 */
	List<String> retrieveAvailableAD_LanguagesForMatching(Properties ctx);

	/**
	 * Retrieve Base AD_Language right from database.
	 * 
	 * NOTE: implementation of this method is directly querying the database
	 * 
	 * @return Base AD_Language; never return null
	 */
	String retrieveBaseLanguage();

	I_AD_Language retrieveByAD_Language(Properties ctx, String adLanguage);

	/**
	 * Add all missing translations for all active languages and for all tables.
	 *
	 * @param ctx context
	 */
	void addAllMissingTranslations(Properties ctx);

	/**
	 * Add missing language translations
	 *
	 * @param language
	 * @return number of records inserted
	 */
	int addMissingTranslations(I_AD_Language language);

	/**
	 * Remove language translations
	 *
	 * @param language
	 * @return number of records deleted
	 */
	int removeTranslations(I_AD_Language language);
}
