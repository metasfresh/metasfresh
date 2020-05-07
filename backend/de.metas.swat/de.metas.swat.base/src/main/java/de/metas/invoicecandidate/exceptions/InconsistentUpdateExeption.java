package de.metas.invoicecandidate.exceptions;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_User;
import org.compiere.util.Env;

/**
 * Model validators can throw this exception if they want to prohibit an update that would lead to inconsistencies. If
 * the update is performed by a scheduled process, that process can use the given <code>adUserToNotifyId</code> to
 * notify whoever is in charge of the problem.
 * 
 * @author ts
 * 
 */
public class InconsistentUpdateExeption extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7452910227523552339L;

	private final I_AD_User userToNotify;

	private final String adMessageHeadLineValue;

	public InconsistentUpdateExeption(
			final String adMessageHeadLineValue,
			final String adMessageValue,
			final Object[] messageParams,
			final I_AD_User userToNotify)
	{
		super(getLanguage(userToNotify), adMessageValue, messageParams);

		this.adMessageHeadLineValue = adMessageHeadLineValue;
		this.userToNotify = userToNotify;
	}

	private static String getLanguage(final I_AD_User userToNotify)
	{
		String lang = null;
		if (userToNotify.getC_BPartner_ID() > 0)
		{
			lang = userToNotify.getC_BPartner().getAD_Language();
		}
		if (lang == null)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(userToNotify);
			lang = Env.getAD_Language(ctx);
		}
		return lang;
	}

	public int getAdUserToNotifyId()
	{
		return userToNotify.getAD_User_ID();
	}

	public String getAD_Message_Value()
	{
		return adMessageHeadLineValue;
	}
}
