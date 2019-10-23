package de.metas.i18n;

import org.compiere.model.I_AD_Language;

import de.metas.util.ISingletonService;

public interface ILanguageDAO extends ISingletonService
{
	ADLanguageList retrieveAvailableLanguages();

	/**
	 * Retrieve Base AD_Language right from database.
	 *
	 * NOTE: implementation of this method is directly querying the database
	 *
	 * @return Base AD_Language; never return null
	 */
	String retrieveBaseLanguage();

	I_AD_Language retrieveByAD_Language(String adLanguage);

	/**
	 * Add all missing translations for all active languages and for all tables.
	 *
	 * @param ctx context
	 */
	void addAllMissingTranslations();

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

	void maintainTranslations(I_AD_Language language, String maintenanceMode);
}
