/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import de.metas.i18n.ILanguageDAO;
import de.metas.i18n.Language;
import de.metas.logging.LogManager;

/**
 * Language Model
 *
 * @author Jorg Janke
 * @version $Id: MLanguage.java,v 1.4 2006/07/30 00:58:36 jjanke Exp $
 *
 * @author Jorg Janke
 * @version $Id: MLanguage.java,v 1.4 2006/07/30 00:58:36 jjanke Exp $
 * 
 * @author Teo Sarca, www.arhipac.ro
 *         <li>BF [ 2444851 ] MLanguage should throw an exception if there is an error
 */
public class MLanguage extends X_AD_Language
{
	/**
	 *
	 */
	private static final long serialVersionUID = 6415602943484245447L;

	private static final Logger s_log = LogManager.getLogger(MLanguage.class);

	// metas: begin: base language
	/**
	 * Load the BaseLanguage from AD_Language table and set it to {@link Language} class using {@link Language#setBaseLanguage(Language)} method. If Env.getCtx() has no <code>#AD_Language</code> set,
	 * then
	 * this method also sets this context property.
	 */
	public static void setBaseLanguage()
	{
		Language.setBaseLanguage(() -> Services.get(ILanguageDAO.class).retrieveBaseLanguage());

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

	public MLanguage(final Properties ctx, final int AD_Language_ID, final String trxName)
	{
		super(ctx, AD_Language_ID, trxName);
	}

	public MLanguage(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	public String toString()
	{
		return "MLanguage[" + getAD_Language() + "-" + getName()
				+ ",Language=" + getLanguageISO() + ",Country=" + getCountryCode()
				+ "]";
	}

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		if (is_ValueChanged(COLUMNNAME_DatePattern))
		{
			assertValidDatePattern(this);
		}

		return true;
	}

	private static final void assertValidDatePattern(I_AD_Language language)
	{
		final String datePattern = language.getDatePattern();

		if (Check.isEmpty(datePattern))
		{
			return; // OK
		}

		if (datePattern.indexOf("MM") == -1)
		{
			throw new AdempiereException("@Error@ @DatePattern@ - No Month (MM)");
		}
		if (datePattern.indexOf("dd") == -1)
		{
			throw new AdempiereException("@Error@ @DatePattern@ - No Day (dd)");
		}
		if (datePattern.indexOf("yy") == -1)
		{
			throw new AdempiereException("@Error@ @DatePattern@ - No Year (yy)");
		}

		final Locale locale = new Locale(language.getLanguageISO(), language.getCountryCode());
		final SimpleDateFormat dateFormat = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT, locale);
		try
		{
			dateFormat.applyPattern(datePattern);
		}
		catch (final Exception e)
		{
			throw new AdempiereException("@Error@ @DatePattern@ - " + e.getMessage(), e);
		}
	}
}
