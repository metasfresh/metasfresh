package de.metas.email;

import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Builds mail text messages from templates using the provided context values.
 * 
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public interface IMailTextBuilder
{
	/**
	 * @return true if the mail texts are HTMLs; false if they are plain text.
	 */
	boolean isHtml();

	/**
	 * Get parsed/translated mail header
	 *
	 * @return parsed/translated text
	 */
	String getMailHeader();

	/**
	 * Get parsed/translated all mail text parts.
	 *
	 * @return parsed/translated text
	 */
	String getFullMailText();

	/**
	 * Get parsed/translated Mail Text (part 1)
	 *
	 * @return parsed/translated text
	 */
	String getMailText();

	int getR_MailText_ID();

	/**
	 * Set BPartner for parse
	 */
	IMailTextBuilder setC_BPartner(final I_C_BPartner bpartner);

	/**
	 * Set BPartner for parse
	 */
	IMailTextBuilder setC_BPartner(final int C_BPartner_ID);

	/**
	 * @return actual partner that will be used
	 */
	I_C_BPartner getC_BPartner();

	/**
	 * Set User for parse
	 */
	IMailTextBuilder setAD_User(final I_AD_User user);

	/**
	 * Set User for parse
	 */
	IMailTextBuilder setAD_User(final int AD_User_ID);

	/**
	 * @return actual user that will be used
	 */
	I_AD_User getAD_User();

	/**
	 * Set record for parse
	 *
	 * @param record
	 */
	IMailTextBuilder setRecord(final Object record);

	/**
	 * Set record for parse
	 *
	 * @param record model
	 * @param analyse if set to true, search for BPartner/User
	 */
	IMailTextBuilder setRecord(final Object record, final boolean analyse);

	/**
	 * @return actual record that will be used
	 */
	Object getRecord();

	IMailTextBuilder setCustomVariable(final String name, final String value);

	/** Optionally, set the AD_Language to be used. If not set, the partner language will be used */
	IMailTextBuilder setAD_Language(String adLanguage);

	/**
	 * @return actual AD_Language that will be used
	 */
	String getAD_Language();

}
