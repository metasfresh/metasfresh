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
package org.compiere.model;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;

import org.adempiere.ad.language.ILanguageDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBNoConnectionException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Language;
import org.slf4j.Logger;

import com.google.common.base.Supplier;

import de.metas.logging.LogManager;

/**
 * Language Model
 *
 * @author Jorg Janke
 * @version $Id: MLanguage.java,v 1.4 2006/07/30 00:58:36 jjanke Exp $
 *
 *  @author Jorg Janke
 *  @version $Id: MLanguage.java,v 1.4 2006/07/30 00:58:36 jjanke Exp $
 * 
 * @author Teo Sarca, www.arhipac.ro
 * 			<li>BF [ 2444851 ] MLanguage should throw an exception if there is an error
 */
public class MLanguage extends X_AD_Language
{
	/**
	 *
	 */
	private static final long serialVersionUID = 6415602943484245447L;

	private static final Logger s_log = LogManager.getLogger(MLanguage.class);

	/**
	 * Get Language Model from Language
	 *
	 * @param ctx context
	 * @param lang language
	 * @return language
	 */
	public static MLanguage get(final Properties ctx, final Language lang)
	{
		return get(ctx, lang.getAD_Language());
	}	// getMLanguage

	/**
	 * Get Language Model from AD_Language
	 *
	 * @param ctx context
	 * @param AD_Language language e.g. en_US
	 * @return language or null
	 */
	public static MLanguage get(final Properties ctx, final String AD_Language)
	{
		return new Query(ctx, Table_Name, COLUMNNAME_AD_Language + "=?", null)
				.setParameters(new Object[] { AD_Language })
				.firstOnly();
	}	// get

	// metas: begin: base language
	/**
	 * Load the BaseLanguage from AD_Language table and set it to {@link Language} class using {@link Language#setBaseLanguage(Language)} method. If Env.getCtx() has no <code>#AD_Language</code> set,
	 * then
	 * this method also sets this context property.
	 */
	public static void setBaseLanguage()
	{
		Language.setBaseLanguage(baseLanguageSupplier);
		
		//
		// Try to initialize the base language, if possible.
		try
		{
			Language.getBaseLanguage();
		}
		catch (Exception e)
		{
			s_log.warn("Cannot initialize base language. Skip.", e);
		}
	}
	// metas: end
	
	private static final Supplier<Language> baseLanguageSupplier = new Supplier<Language>()
	{

		@Override
		public Language get()
		{
			// metas: 03362: Load BaseLanguage only if we have database connection.
			// Could happen, if we invoke this method in early steps of initialization/startup to not have database connection yet
			if (!DB.isConnected())
			{
				throw new DBNoConnectionException();
			}

			final String baseADLanguage = Services.get(ILanguageDAO.class).retrieveBaseLanguage();
			final Language language = Language.getLanguage(baseADLanguage);
			if (language == null)
			{
				// metas: 03362: If there is no Language object for database configured base language,
				// that is a big error and we need to throw an exception, instead of just returning
				throw new AdempiereException("No org.compiere.util.Language defined for " + baseADLanguage);
				// return;
			}
			s_log.info("Found base language: {}", language);

			final Properties ctx = Env.getCtx();
			if (Check.isEmpty(Env.getContext(ctx, Env.CTXNAME_AD_Language), true))
			{
				Env.setContext(ctx, Env.CTXNAME_AD_Language, language.getAD_Language());
			}
			
			return language;
		}

	};

	// /** Logger */
	// private static Logger s_log = CLogMgt.getLogger(MLanguage.class);

	/**************************************************************************
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param AD_Language_ID id
	 * @param trxName transaction
	 */
	public MLanguage(final Properties ctx, final int AD_Language_ID, final String trxName)
	{
		super(ctx, AD_Language_ID, trxName);
	}	// MLanguage

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MLanguage(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}	// MLanguage

	/**
	 * Create Language
	 *
	 * @param ctx context
	 * @param AD_Language language code
	 * @param Name name
	 * @param CountryCode country code
	 * @param LanguageISO language code
	 * @param trxName transaction
	 */
	private MLanguage(final Properties ctx, final String AD_Language, final String Name,
			final String CountryCode, final String LanguageISO, final String trxName)
	{
		super(ctx, 0, trxName);
		setAD_Language(AD_Language);	// en_US
		setIsBaseLanguage(false);
		setIsSystemLanguage(false);
		setName(Name);
		setCountryCode(CountryCode);	// US
		setLanguageISO(LanguageISO);	// en
	}	// MLanguage

	/** Locale */
	private Locale m_locale = null;
	/** Date Format */
	private SimpleDateFormat m_dateFormat = null;

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		return "MLanguage[" + getAD_Language() + "-" + getName()
				+ ",Language=" + getLanguageISO() + ",Country=" + getCountryCode()
				+ "]";
	}	// toString

	/**
	 * Get Locale
	 *
	 * @return Locale
	 */
	public Locale getLocale()
	{
		if (m_locale == null)
		{
			m_locale = new Locale(getLanguageISO(), getCountryCode());
		}
		return m_locale;
	}	// getLocale

	/**
	 * Get (Short) Date Format.
	 * The date format must parseable by org.compiere.grid.ed.MDocDate
	 * i.e. leading zero for date and month
	 *
	 * @return date format MM/dd/yyyy - dd.MM.yyyy
	 */
	public SimpleDateFormat getDateFormat()
	{
		if (m_dateFormat != null)
		{
			return m_dateFormat;
		}

		if (getDatePattern() != null)
		{
			m_dateFormat = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT, getLocale());
			try
			{
				m_dateFormat.applyPattern(getDatePattern());
			}
			catch (final Exception e)
			{
				log.error(getDatePattern() + " - " + e);
				m_dateFormat = null;
			}
		}

		if (m_dateFormat == null)
		{
			// Fix Locale Date format
			m_dateFormat = (SimpleDateFormat)DateFormat.getDateInstance
					(DateFormat.SHORT, getLocale());
			String sFormat = m_dateFormat.toPattern();
			// some short formats have only one M and d (e.g. ths US)
			if (sFormat.indexOf("MM") == -1 && sFormat.indexOf("dd") == -1)
			{
				String nFormat = "";
				for (int i = 0; i < sFormat.length(); i++)
				{
					if (sFormat.charAt(i) == 'M')
					{
						nFormat += "MM";
					}
					else if (sFormat.charAt(i) == 'd')
					{
						nFormat += "dd";
					}
					else
					{
						nFormat += sFormat.charAt(i);
					}
				}
				// System.out.println(sFormat + " => " + nFormat);
				m_dateFormat.applyPattern(nFormat);
			}
			// Unknown short format => use JDBC
			if (m_dateFormat.toPattern().length() != 8)
			{
				m_dateFormat.applyPattern("yyyy-MM-dd");
			}

			// 4 digit year
			if (m_dateFormat.toPattern().indexOf("yyyy") == -1)
			{
				sFormat = m_dateFormat.toPattern();
				String nFormat = "";
				for (int i = 0; i < sFormat.length(); i++)
				{
					if (sFormat.charAt(i) == 'y')
					{
						nFormat += "yy";
					}
					else
					{
						nFormat += sFormat.charAt(i);
					}
				}
				m_dateFormat.applyPattern(nFormat);
			}
		}
		//
		m_dateFormat.setLenient(true);
		return m_dateFormat;
	}   // getDateFormat

	/**
	 * Set AD_Language_ID
	 */
	private void setAD_Language_ID()
	{
		int AD_Language_ID = getAD_Language_ID();
		if (AD_Language_ID == 0)
		{
			final String sql = "SELECT COALESCE(MAX(AD_Language_ID), 999999) FROM AD_Language WHERE AD_Language_ID > 1000";
			AD_Language_ID = DB.getSQLValue(get_TrxName(), sql);
			setAD_Language_ID(AD_Language_ID + 1);
		}
	}	// setAD_Language_ID

	/**
	 * Before Save
	 *
	 * @param newRecord new
	 * @return true/false
	 */
	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		final String dp = getDatePattern();
		if (is_ValueChanged("DatePattern") && dp != null && dp.length() > 0)
		{
			if (dp.indexOf("MM") == -1)
			{
				throw new AdempiereException("@Error@ @DatePattern@ - No Month (MM)");
			}
			if (dp.indexOf("dd") == -1)
			{
				throw new AdempiereException("@Error@ @DatePattern@ - No Day (dd)");
			}
			if (dp.indexOf("yy") == -1)
			{
				throw new AdempiereException("@Error@ @DatePattern@ - No Year (yy)");
			}

			m_dateFormat = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT, getLocale());
			try
			{
				m_dateFormat.applyPattern(dp);
			}
			catch (final Exception e)
			{
				m_dateFormat = null;
				throw new AdempiereException("@Error@ @DatePattern@ - " + e.getMessage(), e);
			}
		}
		if (newRecord)
		{
			setAD_Language_ID();
		}
		return true;
	}	// beforeSae

	/**
	 * AfterSave
	 *
	 * @param newRecord new
	 * @param success success
	 * @return true if saved
	 */
	@Override
	protected boolean afterSave(final boolean newRecord, final boolean success)
	{
		final int no = TranslationTable.getActiveLanguages(true);
		log.debug("Active Languages=" + no);
		return true;
	}	// afterSave

}	//	MLanguage
