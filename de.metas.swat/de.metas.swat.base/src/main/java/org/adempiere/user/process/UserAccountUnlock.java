package org.adempiere.user.process;

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


import java.util.logging.Level;

import org.adempiere.model.POWrapper;
import org.compiere.model.MUser;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import de.metas.adempiere.model.I_AD_User;

public class UserAccountUnlock extends SvrProcess
{

	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
			{
			}
			else
			{
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
			}
		}
	} // prepare

	@Override
	protected String doIt() throws Exception
	{
		MUser userPO = new MUser(getCtx(), getRecord_ID(), get_TrxName());
		final I_AD_User user = POWrapper.create(userPO, I_AD_User.class);
		user.setLoginFailureCount(0);
		user.setIsAccountLocked(false);
		user.setLockedFromIP(null);
		user.setLoginFailureDate(null);
		userPO.saveEx();
		return "Ok";
	} // doIt

}
