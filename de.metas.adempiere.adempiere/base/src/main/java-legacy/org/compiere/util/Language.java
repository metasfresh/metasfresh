/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.util;

import java.awt.ComponentOrientation;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.print.attribute.standard.MediaSize;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;

/**
 * Language Management.
 *
 * @author Jorg Janke
 * @version $Id: Language.java,v 1.2 2006/07/30 00:52:23 jjanke Exp $
 */
public final class Language implements Serializable
{
	/**
	 * 
	 */
	/**************************************************************************
	 * Languages
	 * http://www.ics.uci.edu/pub/ietf/http/related/iso639.txt
	 * Countries
	 * http://www.iso.org/iso/country_codes/iso_3166_code_lists/english_country_names_and_code_elements.htm
	 *************************************************************************/

	/**
	 * 
	 */
	private static final long serialVersionUID = -964846521004545703L;
	/** Base Language */
	public static final String AD_Language_en_US = "en_US";
	/** Additional Languages */
	private static final String AD_Language_en_GB = "en_GB";
	private static final String AD_Language_en_AU = "en_AU";
	private static final String AD_Language_ca_ES = "ca_ES";
	private static final String AD_Language_hr_HR = "hr_HR";
	private static final String AD_Language_de_DE = "de_DE";
	private static final String AD_Language_de_CH = "de_CH"; // 03362
	private static final String AD_Language_it_IT = "it_IT";
	private static final String AD_Language_es_ES = "es_ES";
	private static final String AD_Language_es_MX = "es_MX";
	private static final String AD_Language_es_CO = "es_CO";
	private static final String AD_Language_es_DO = "es_DO";
	private static final String AD_Language_fr_FR = "fr_FR";
	private static final String AD_Language_fr_CA = "fr_CA";
	private static final String AD_Language_bg_BG = "bg_BG";
	private static final String AD_Language_th_TH = "th_TH";
	private static final String AD_Language_pl_PL = "pl_PL";
	private static final String AD_Language_zh_TW = "zh_TW";
	private static final String AD_Language_nl_NL = "nl_NL";
	private static final String AD_Language_no_NO = "no_NO";
	private static final String AD_Language_pt_BR = "pt_BR";
	private static final String AD_Language_ru_RU = "ru_RU";
	private static final String AD_Language_sl_SI = "sl_SI";
	private static final String AD_Language_sr_RS = "sr_RS";
	private static final String AD_Language_sv_SE = "sv_SE";
	private static final String AD_Language_vi_VN = "vi_VN";
	private static final String AD_Language_zh_CN = "zh_CN";
	private static final String AD_Language_da_DK = "da_DK";
	private static final String AD_Language_ms_MY = "ms_MY";
	private static final String AD_Language_fa_IR = "fa_IR";
	private static final String AD_Language_fi_FI = "fi_FI";
	private static final String AD_Language_ro_RO = "ro_RO";
	private static final String AD_Language_ja_JP = "ja_JP";
	private static final String AD_Language_in_ID = "in_ID";
	private static final String AD_Language_ar_TN = "ar_TN";
	private static final String AD_Language_hu_HU = "hu_HU";
	private static final String AD_Language_el_GR = "el_GR";

	/***
	 * System Languages.
	 * If you want to add a language, extend the array
	 * - or use the addLanguage() method.
	 **/
	private static final CopyOnWriteArrayList<Language> s_languages = new CopyOnWriteArrayList<>(new Language[] {
			new Language("English",
					AD_Language_en_US, Locale.US, null, null,
					MediaSize.NA.LETTER), 							    // Base Language
			// ordered by locale
			// Not predefined Locales - need to define decimal Point and date pattern (not sure about time)
			new Language("\uFE94\uFEF4\uFE91\uFEAE\uFECC\uFEDF\uFE8D (AR)",
					AD_Language_ar_TN, new Locale("ar", "TN"), Boolean.TRUE, "dd.MM.yyyy",
					MediaSize.ISO.A4),
			new Language("\u0411\u044A\u043B\u0433\u0430\u0440\u0441\u043A\u0438 (BG)",
					AD_Language_bg_BG, new Locale("bg", "BG"), Boolean.FALSE, "dd/MM/yyyy",
					MediaSize.ISO.A4),
			new Language("Catal\u00e0",
					AD_Language_ca_ES, new Locale("ca", "ES"), null, "dd/MM/yyyy",
					MediaSize.ISO.A4),
			new Language("Deutsch",
					AD_Language_de_DE, Locale.GERMANY, Boolean.FALSE, "dd.MM.yyyy",
					MediaSize.ISO.A4),
			// 03362 adding german language for Switzerland
			new Language("Deutsch (Schweiz)",
					AD_Language_de_CH, new Locale("de", "CH"), Boolean.FALSE, "dd.MM.yyyy",
					MediaSize.ISO.A4),
			new Language("Dansk",
					AD_Language_da_DK, new Locale("da", "DK"), Boolean.FALSE, "dd-MM-yyyy",
					MediaSize.ISO.A4),
			new Language("English (AU)",
					AD_Language_en_AU, new Locale("en", "AU"), null, "dd/MM/yyyy",
					MediaSize.ISO.A4),
			new Language("English (UK)",
					AD_Language_en_GB, Locale.UK, null, null,
					MediaSize.ISO.A4),
			new Language("Espa\u00f1ol",
					AD_Language_es_ES, new Locale("es", "ES"), Boolean.FALSE, "dd/MM/yyyy",
					MediaSize.ISO.A4),
			new Language("Espa\u00f1ol (MX)",
					AD_Language_es_MX, new Locale("es", "MX"), Boolean.TRUE, "dd/MM/yyyy",
					MediaSize.NA.LETTER),
			new Language("Espa\u00f1ol (CO)",
					AD_Language_es_CO, new Locale("es", "ES"), Boolean.FALSE, "dd/MM/yyyy",
					MediaSize.NA.LETTER),
			new Language("Espa\u00f1ol (VE)",
					AD_Language_es_ES, new Locale("es", "ES"), Boolean.FALSE, "dd/MM/yyyy",
					MediaSize.ISO.A4),
			new Language("Espa\u00f1ol (EC)",
					AD_Language_es_ES, new Locale("es", "ES"), Boolean.FALSE, "dd/MM/yyyy",
					MediaSize.ISO.A4),
			new Language("Espa\u00f1ol (DO)",
					AD_Language_es_DO, new Locale("es", "DO"), Boolean.TRUE, "dd/MM/yyyy",
					MediaSize.ISO.A4),
			new Language("\u0395\u03bb\u03bb\u03b7\u03bd\u03b9\u03ba\u03ac (GR)",
					AD_Language_el_GR, new Locale("el", "GR"), Boolean.FALSE, "dd/MM/yyyy",
					MediaSize.ISO.A4),
			new Language("Farsi",
					AD_Language_fa_IR, new Locale("fa", "IR"), Boolean.FALSE, "dd-MM-yyyy",
					MediaSize.ISO.A4),
			new Language("Finnish",
					AD_Language_fi_FI, new Locale("fi", "FI"), Boolean.TRUE, "dd.MM.yyyy",
					MediaSize.ISO.A4),
			new Language("Fran\u00e7ais",
					AD_Language_fr_FR, Locale.FRANCE, null, null, 		// dd.MM.yy
					MediaSize.ISO.A4),
			new Language("Fran\u00e7ais (CA)",
					AD_Language_fr_CA, new Locale("fr", "CA"), Boolean.TRUE, "MM/dd/yyyy", 	// MM/dd/yy
					MediaSize.NA.LETTER),
			new Language("Hrvatski",
					AD_Language_hr_HR, new Locale("hr", "HR"), null, "dd.MM.yyyy",
					MediaSize.ISO.A4),
			new Language("Indonesia Bahasa",
					AD_Language_in_ID, new Locale("in", "ID"), Boolean.FALSE, "dd-MM-yyyy",
					MediaSize.ISO.A4),
			new Language("Italiano",
					AD_Language_it_IT, Locale.ITALY, null, null, 		// dd.MM.yy
					MediaSize.ISO.A4),
			new Language("\u65e5\u672c\u8a9e (JP)",
					AD_Language_ja_JP, Locale.JAPAN, null, null,
					MediaSize.ISO.A4),
			new Language("Malaysian",
					AD_Language_ms_MY, new Locale("ms", "MY"), Boolean.FALSE, "dd-MM-yyyy",
					MediaSize.ISO.A4),
			new Language("Magyar (HU)",
					AD_Language_hu_HU, new Locale("hu", "HU"), Boolean.FALSE, "yyyy.MM.dd",
					MediaSize.ISO.A4),
			new Language("Nederlands",
					AD_Language_nl_NL, new Locale("nl", "NL"), Boolean.FALSE, "dd-MM-yyyy",
					MediaSize.ISO.A4),
			new Language("Norsk",
					AD_Language_no_NO, new Locale("no", "NO"), Boolean.FALSE, "dd/MM/yyyy",
					MediaSize.ISO.A4),
			new Language("Polski",
					AD_Language_pl_PL, new Locale("pl", "PL"), Boolean.FALSE, "dd-MM-yyyy",
					MediaSize.ISO.A4),
			new Language("Portuguese (BR)",
					AD_Language_pt_BR, new Locale("pt", "BR"), Boolean.FALSE, "dd/MM/yyyy",
					MediaSize.ISO.A4),
			new Language("Rom\u00e2n\u0103",
					AD_Language_ro_RO, new Locale("ro", "RO"), Boolean.FALSE, "dd.MM.yyyy",
					MediaSize.ISO.A4),
			new Language("\u0420\u0443\u0441\u0441\u043a\u0438\u0439 (Russian)",
					AD_Language_ru_RU, new Locale("ru", "RU"), Boolean.FALSE, "dd-MM-yyyy",
					MediaSize.ISO.A4),
			new Language("Slovenski",
					AD_Language_sl_SI, new Locale("sl", "SI"), null, "dd.MM.yyyy",
					MediaSize.ISO.A4),
			new Language("\u0421\u0440\u043F\u0441\u043A\u0438 (RS)",
					AD_Language_sr_RS, new Locale("sr", "RS"), null, "dd.MM.yyyy",
					MediaSize.ISO.A4),
			new Language("Svenska",
					AD_Language_sv_SE, new Locale("sv", "SE"), Boolean.FALSE, "yyyy-MM-dd",
					MediaSize.ISO.A4),
			new Language("\u0e44\u0e17\u0e22 (TH)",
					AD_Language_th_TH, new Locale("th", "TH"), Boolean.FALSE, "dd/MM/yyyy",
					MediaSize.ISO.A4),
			new Language("Vi\u1EC7t Nam",
					AD_Language_vi_VN, new Locale("vi", "VN"), Boolean.FALSE, "dd-MM-yyyy",
					MediaSize.ISO.A4),
			// Need to have (Windows) Asian Language Pack installed to view properly
			new Language("\u7b80\u4f53\u4e2d\u6587 (CN)",
					AD_Language_zh_CN, Locale.CHINA, null, "yyyy-MM-dd",
					MediaSize.ISO.A4),
			new Language("\u7e41\u9ad4\u4e2d\u6587 (TW)",
					AD_Language_zh_TW, Locale.TAIWAN, null, null, 		// dd.MM.yy
					MediaSize.ISO.A4)
	});

	/** Base Language supplier */
	private static ExtendedMemorizingSupplier<Language> _baseLanguageSupplier = null;

	/** Logger */
	private static final transient Logger log = LogManager.getLogger(Language.class);

	/**************************************************************************
	 * Get Language.
	 * If language does not exist, create it on the fly assuming that it is valid
	 * 
	 * @param langInfo either language (en) or locale (en-US) or display name
	 * @return Name (e.g. Deutsch)
	 */
	public static Language getLanguage(final String langInfo)
	{
		String langInfoActual = langInfo;
		if (langInfoActual == null || langInfoActual.isEmpty())
		{
			langInfoActual = System.getProperty("user.language", "");
			log.warn("getLanguage: no langInfo provided. Considering user.language={}", langInfoActual);
		}

		// Search existing Languages
		for (final Language language : s_languages)
		{
			if (language.matchesLangInfo(langInfoActual))
			{
				return language;
			}
		}

		//
		// Create Language on the fly
		if (langInfoActual.length() == 5) 		// standard format <language>_<Country>
		{
			String language = langInfoActual.substring(0, 2);
			String country = langInfoActual.substring(3);
			Locale locale = new Locale(language, country);
			log.info("Adding Language={}, Country={}, Locale={}", language, country, locale);
			final Language languageNew = new Language(langInfoActual, langInfoActual, locale);
			s_languages.add(languageNew);

			return languageNew;
		}

		// Get the default one
		return getLoginLanguage();
	}   // getLanguage

	/**
	 * Is it the base language
	 * 
	 * @param langInfo either language (en) or locale (en-US) or display name
	 * @return true if base language
	 */
	public static boolean isBaseLanguage(final String langInfo)
	{
		if (langInfo == null)
		{
			// this can happen on startup
			return false;
		}

		final Language baseLanguage = getBaseLanguage();
		return baseLanguage.matchesLangInfo(langInfo);
	}

	/**
	 * Get Base Language
	 * 
	 * @return Base Language
	 * @throws AdempiereException if the base language was not already configured or it could not be determined
	 */
	public static Language getBaseLanguage()
	{
		if (_baseLanguageSupplier == null)
		{
			throw new AdempiereException("Base language supplier was not configured");
		}

		final Language baseLanguage = _baseLanguageSupplier.get();
		if (baseLanguage == null)
		{
			_baseLanguageSupplier.forget();
			throw new AdempiereException("BaseLanguage could not be determined");
		}
		return baseLanguage;
	}   // getBase

	/**
	 * Set the actual base language. Do not call this method because this is part of internal API.
	 * 
	 * @param languageSupplier base language supplier
	 */
	public static void setBaseLanguage(final Supplier<Language> languageSupplier)
	{
		Check.assumeNotNull(languageSupplier, "languageSupplier not null");
		_baseLanguageSupplier = ExtendedMemorizingSupplier.of(languageSupplier);
	}

	/**
	 * @return true if the base language configured
	 * @see #setBaseLanguage(Language)
	 */
	public static final boolean isBaseLanguageSet()
	{
		ExtendedMemorizingSupplier<Language> baseLanguageSupplier = _baseLanguageSupplier;
		return baseLanguageSupplier != null && baseLanguageSupplier.isInitialized();
	}

	/**
	 * Get Base Language code. (e.g. de_DE)
	 * 
	 * @return Base Language
	 * @throws AdempiereException if the base language was not already configured
	 */
	public static String getBaseAD_Language()
	{
		// return s_languages[0].getAD_Language();
		return getBaseLanguage().getAD_Language(); // metas
	}   // getBase

	/**
	 * Get Supported Language
	 * 
	 * @param locale Locale
	 * @return AD_Language (e.g. en_US)
	 */
	public static String getAD_Language(final Locale locale)
	{
		final Language language = getLanguage(locale);
		return language.getAD_Language();
	}

	public static Language getLanguage(final Locale locale)
	{
		if (locale != null)
		{
			// TODO: cache the link for later use
			final Language language = findLanguageByLocale(locale);
			if (language != null)
			{
				return language;
			}
		}

		//
		// Fallback to login language if any
		final Language loginLanguage = getLoginLanguage();
		if (loginLanguage != null)
		{
			return loginLanguage;
		}

		//
		// Fallback to base language
		return getBaseLanguage();
	}   // getLocale

	private static final Language findLanguageByLocale(final Locale locale)
	{
		final String search_lang = locale.getLanguage();
		final String search_country = locale.getCountry();
		final String search_variant = locale.getVariant();

		Language foundForLangAndCountry = null;
		Language foundForLang = null;
		for (final Language language : s_languages)
		{
			final Locale languageLocale = language.getLocale();

			if (Objects.equals(languageLocale.getLanguage(), search_lang))
			{
				if (Objects.equals(languageLocale.getCountry(), search_country))
				{
					if (Objects.equals(languageLocale.getVariant(), search_variant))
					{
						// perfect match, return it
						return language;
					}
					else if (foundForLangAndCountry == null)
					{
						foundForLangAndCountry = language;
					}
				}
				else if (foundForLang == null)
				{
					foundForLang = language;
				}
			}
		}

		if (foundForLangAndCountry != null)
		{
			return foundForLangAndCountry;
		}

		if (foundForLang != null)
		{
			return foundForLang;
		}

		return null;
	}

	/**
	 * Get Language Name
	 * 
	 * @param langInfo either language (en) or locale (en-US) or display name
	 * @return Language Name (e.g. English)
	 */
	public static String getName(final String langInfo)
	{
		return getLanguage(langInfo).getName();
	}   // getAD_Language

	/**
	 * @return supported languages (AD_Language to Name, value name pairs)
	 */
	public static List<ValueNamePair> getValueNamePairs()
	{
		final ImmutableList.Builder<ValueNamePair> languageVNPs = ImmutableList.builder();
		for (final Language language : s_languages)
		{
			// NOTE: having the AD_Language as Value is important. Before considering to change that, check who is using this method!
			languageVNPs.add(ValueNamePair.of(language.getAD_Language(), language.getName()));
		}
		return languageVNPs.build();

	}

	/**************************************************************************
	 * Get Default Login Language
	 * 
	 * @return default Language
	 * @deprecated Please use {@link Env#getLanguage(java.util.Properties)} instead
	 */
	@Deprecated
	public static Language getLoginLanguage()
	{
		// metas: tsa: 02214: use #AD_Language from context because s_loginLanguage is not working on zk
		return Env.getLanguage(Env.getCtx());
		// return s_loginLanguage;
	}   // getLanguage

	/**************************************************************************
	 * Define Language
	 * 
	 * @param name - displayed value, e.g. English
	 * @param AD_Language - the code of system supported language, e.g. en_US
	 *            (might be different than Locale - i.e. if the system does not support the language)
	 * @param locale - the Locale, e.g. Locale.US
	 * @param decimalPoint true if Decimal Point - if null, derived from Locale
	 * @param javaDatePattern Java date pattern as not all locales are defined - if null, derived from Locale
	 * @param mediaSize default media size
	 */
	private Language(final String name, final String AD_Language, final Locale locale,
			final Boolean decimalPoint, final String javaDatePattern, final MediaSize mediaSize)
	{
		super();

		m_name = Preconditions.checkNotNull(name, "name");
		m_AD_Language = Preconditions.checkNotNull(AD_Language, "AD_Language");
		m_locale = Preconditions.checkNotNull(locale, "locale");
		//
		_decimalPoint = decimalPoint;
		_dateFormatPattern = javaDatePattern;
		_mediaSize = mediaSize == null ? MediaSize.ISO.A4 : mediaSize;
	}   // Language

	/**
	 * Define Language with A4 and default decimal point and date format
	 * 
	 * @param name - displayed value, e.g. English
	 * @param AD_Language - the code of system supported language, e.g. en_US
	 *            (might be different than Locale - i.e. if the system does not support the language)
	 * @param locale - the Locale, e.g. Locale.US
	 */
	private Language(final String name, final String AD_Language, final Locale locale)
	{
		this(name, AD_Language, locale, null, null, null);
	}	// Language

	/** Name */
	private final String m_name;
	/** Language (key) */
	private String m_AD_Language;
	/** Locale */
	private final Locale m_locale;
	//
	private Boolean _decimalPoint;
	private Boolean _leftToRight;

	private String _dateFormatPattern;
	private ThreadLocal<SimpleDateFormat> _dateFormatThreadLocal = null;

	private final MediaSize _mediaSize;

	/**
	 * Get Language Name.
	 * e.g. English
	 * 
	 * @return name
	 */
	public String getName()
	{
		return m_name;
	}   // getName

	/**
	 * Get Application Dictionary Language (system supported).
	 * e.g. en-US
	 * 
	 * @return AD_Language
	 */
	public String getAD_Language()
	{
		return m_AD_Language;
	}   // getAD_Language

	/**
	 * Set Application Dictionary Language (system supported).
	 * 
	 * @param AD_Language e.g. en-US
	 */
	void setAD_Language(final String AD_Language)
	{
		if (AD_Language != null)
		{
			final String adLanguageOld = m_AD_Language;
			m_AD_Language = AD_Language;

			log.info("Changed AD_Language {}->{} for {}", adLanguageOld, m_AD_Language, this);
		}
		else
		{
			log.warn("Skip setting AD_Language for {} because we got null parameter", this);
		}
	}   // getAD_Language

	/**
	 * Get Locale
	 * 
	 * @return locale
	 */
	public Locale getLocale()
	{
		return m_locale;
	}   // getLocale

	/**
	 * Get Language Code.
	 * e.g. en - derived from Locale
	 * 
	 * @return language code
	 */
	public String getLanguageCode()
	{
		return m_locale.getLanguage();
	}   // getLanguageCode

	/**
	 * Component orientation is Left To Right
	 * 
	 * @return true if left-to-right
	 */
	public boolean isLeftToRight()
	{
		if (_leftToRight == null)
		{
			// returns true if language not iw, ar, fa, ur
			_leftToRight = ComponentOrientation.getOrientation(m_locale).isLeftToRight();
		}
		return _leftToRight.booleanValue();
	}   // isLeftToRight

	/**
	 * Returns true if Decimal Point (not comma)
	 * 
	 * @return use of decimal point
	 */
	public boolean isDecimalPoint()
	{
		if (_decimalPoint == null)
		{
			final DecimalFormatSymbols dfs = new DecimalFormatSymbols(m_locale);
			_decimalPoint = dfs.getDecimalSeparator() == '.';
		}
		return _decimalPoint.booleanValue();
	}   // isDecimalPoint

	/**
	 * Is This the Base Language
	 * 
	 * @return true if base Language
	 */
	public boolean isBaseLanguage()
	{
		return this.equals(getBaseLanguage());
	}

	private String getDateFormatPattern()
	{
		if (_dateFormatPattern == null)
		{
			final SimpleDateFormat dateFormat = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT, m_locale);
			String sFormat = dateFormat.toPattern();

			// some short formats have only one M and d (e.g. ths US)
			if (sFormat.indexOf("MM") == -1 && sFormat.indexOf("dd") == -1)
			{
				String nFormat = "";
				for (int i = 0; i < sFormat.length(); i++)
				{
					if (sFormat.charAt(i) == 'M')
						nFormat += "MM";
					else if (sFormat.charAt(i) == 'd')
						nFormat += "dd";
					else
						nFormat += sFormat.charAt(i);
				}
				dateFormat.applyPattern(nFormat);
			}

			// Unknown short format => use JDBC
			if (dateFormat.toPattern().length() != 8)
			{
				dateFormat.applyPattern("yyyy-MM-dd");
			}

			// 4 digit year
			if (dateFormat.toPattern().indexOf("yyyy") == -1)
			{
				sFormat = dateFormat.toPattern();
				String nFormat = "";
				for (int i = 0; i < sFormat.length(); i++)
				{
					if (sFormat.charAt(i) == 'y')
						nFormat += "yy";
					else
						nFormat += sFormat.charAt(i);
				}
				dateFormat.applyPattern(nFormat);
			}

			this._dateFormatPattern = dateFormat.toPattern();
		}

		return _dateFormatPattern;
	}

	/**
	 * Get (Short) Date Format.
	 * The date format must parseable by org.compiere.grid.ed.MDocDate
	 * i.e. leading zero for date and month
	 * 
	 * @return date format MM/dd/yyyy - dd.MM.yyyy
	 */
	public SimpleDateFormat getDateFormat()
	{
		if (_dateFormatThreadLocal == null)
		{
			_dateFormatThreadLocal = new ThreadLocal<>();
		}

		SimpleDateFormat dateFormat = _dateFormatThreadLocal.get();
		if (dateFormat == null)
		{
			final String dateFormatPattern = getDateFormatPattern();
			dateFormat = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT, m_locale);
			dateFormat.applyPattern(dateFormatPattern);
			dateFormat.setLenient(true);

			_dateFormatThreadLocal.set(dateFormat);
		}

		return dateFormat;
	}

	/**
	 * Get Date Time Format.
	 * Used for Display only
	 * 
	 * @return Date Time format MMM d, yyyy h:mm:ss a z -or- dd.MM.yyyy HH:mm:ss z
	 *         -or- j nnn aaaa, H' ?????? 'm' ????'
	 */
	public SimpleDateFormat getDateTimeFormat()
	{
		SimpleDateFormat retValue = (SimpleDateFormat)DateFormat.getDateTimeInstance(
				DateFormat.MEDIUM,  // dateStyle
				getTimeStyle(),  // timeStyle
				m_locale);
		return retValue;
	}	// getDateTimeFormat

	/**
	 * Get Time Format.
	 * Used for Display only
	 * 
	 * @return Time format h:mm:ss z or HH:mm:ss z
	 */
	public SimpleDateFormat getTimeFormat()
	{
		return (SimpleDateFormat)DateFormat.getTimeInstance(
				getTimeStyle(),  // dateStyle
				m_locale // timeStyle
		);
	}	// getTimeFormat

	/**
	 * Get default MediaSize
	 * 
	 * @return media size
	 */
	public MediaSize getMediaSize()
	{
		return _mediaSize;
	}	// getMediaSize

	/**
	 * String Representation
	 * 
	 * @return string representation
	 */
	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("name", m_name)
				.add("AD_Language", m_AD_Language)
				.add("locale", m_locale)
				.add("decimalPoint", isDecimalPoint())
				.toString();
	}

	@Override
	public int hashCode()
	{
		return m_AD_Language.hashCode();
	}	// hashcode

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (obj instanceof Language)
		{
			final Language other = (Language)obj;
			return Objects.equals(this.m_AD_Language, other.m_AD_Language);
		}
		return false;
	}	// equals

	private static int timeStyleDefault = DateFormat.MEDIUM;

	/**
	 * Sets default time style to be used when getting DateTime format or Time format.
	 * 
	 * @param timeStyle one of {@link DateFormat#SHORT}, {@link DateFormat#MEDIUM}, {@link DateFormat#LONG}.
	 */
	public static void setDefaultTimeStyle(final int timeStyle)
	{
		timeStyleDefault = timeStyle;
	}

	public static int getDefaultTimeStyle()
	{
		return timeStyleDefault;
	}

	public int getTimeStyle()
	{
		return getDefaultTimeStyle();
	}

	private boolean matchesLangInfo(final String langInfo)
	{
		if (langInfo == null || langInfo.isEmpty())
		{
			return false;
		}

		return langInfo.equals(getName())
				|| langInfo.equals(getAD_Language())
				|| langInfo.equals(getLanguageCode());
	}
}   // Language
