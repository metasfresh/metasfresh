package org.adempiere.ad.security.impl;

import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;

import org.adempiere.model.GeneralCopyRecordSupport;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.MUser;
import org.compiere.model.PO;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_AD_Role;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class AD_Role_POCopyRecordSupport extends GeneralCopyRecordSupport
{
	@Override
	public void setSpecialColumnsName(PO to)
	{
		final String MSG_AD_Role_Name_Unique = "AD_Role_Unique_Name";

		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final Format formatter = new SimpleDateFormat("yyyyMMdd:HH:mm:ss");

		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String s = formatter.format(ts);
		String name = MUser.getNameOfUser(Env.getAD_User_ID(getCtx()));

		final String language = Env.getAD_Language(getCtx());
		// Create the name using the text from the specific AD_Message.
		String msg = msgBL.getMsg(language, MSG_AD_Role_Name_Unique, new String[] { name, s });

		// shall never happen if the message is set.
		if (msg.isEmpty())
		{
			super.setSpecialColumnsName(to);
		}

		to.set_CustomColumn(I_AD_Role.COLUMNNAME_Name, msg);
	}
}
