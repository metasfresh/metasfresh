package de.metas.security.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.adempiere.model.GeneralCopyRecordSupport;
import org.compiere.model.GridField;
import org.compiere.model.PO;
import org.compiere.util.Env;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class AD_Role_POCopyRecordSupport extends GeneralCopyRecordSupport
{
	private static final AdMessageKey MSG_AD_Role_Name_Unique = AdMessageKey.of("AD_Role_Unique_Name");
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd:HH:mm:ss");

	@Override
	public Object getValueToCopy(final GridField gridField)
	{
		if (org.compiere.model.I_AD_Role.COLUMNNAME_Name.equals(gridField.getColumnName()))
		{
			return makeUniqueName();
		}
		else
		{
			return super.getValueToCopy(gridField);
		}
	}

	@Override
	public Object getValueToCopy(final PO to, final PO from, final String columnName)
	{
		if (org.compiere.model.I_AD_Role.COLUMNNAME_Name.equals(columnName))
		{
			return makeUniqueName();
		}
		else
		{
			return super.getValueToCopy(to, from, columnName);
		}
	}

	private static final String makeUniqueName()
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final IUserDAO userDAO = Services.get(IUserDAO.class);

		final Properties ctx = Env.getCtx();
		final int adUserId = Env.getAD_User_ID(ctx);
		final String adLanguage = Env.getAD_Language(ctx);

		final String timestampStr = DATE_FORMATTER.format(LocalDateTime.now());
		final String userName = userDAO.retrieveUserFullName(adUserId);

		// Create the name using the text from the specific AD_Message.
		final String nameUnique = msgBL.getMsg(adLanguage, MSG_AD_Role_Name_Unique, new String[] { userName, timestampStr });

		return nameUnique;
	}
}
