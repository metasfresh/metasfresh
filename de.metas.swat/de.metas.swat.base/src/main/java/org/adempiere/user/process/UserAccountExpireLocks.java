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


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUser;
import org.compiere.util.DB;
import org.compiere.util.TrxRunnable;

import de.metas.adempiere.model.I_AD_User;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

public class UserAccountExpireLocks extends SvrProcess
{
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
			{
			}
			else
			{
				log.error("prepare - Unknown Parameter: " + name);
			}
		}
	} // prepare

	@Override
	protected String doIt() throws Exception
	{
		log.info("UserAccountExpireLocks");

		final int accountLockExpire = MSysConfig.getIntValue("USERACCOUNT_LOCK_EXPIRE", 30);

		final String sql = "SELECT * FROM AD_User" + " WHERE IsAccountLocked = 'Y'";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int no = 0;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				final int AD_User_IDToUnlock = rs.getInt(MUser.COLUMNNAME_AD_User_ID);
				no = no + unlockUser(accountLockExpire, AD_User_IDToUnlock);
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;

		}

		return "Unlock accounts: " + no;

	} // doIt

	private int unlockUser(final int accountLockExpire, final int AD_User_IDToUnlock)
	{
		final int result[] = { 0 };
		
		Services.get(ITrxManager.class).run(new TrxRunnable()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				final I_AD_User user = InterfaceWrapperHelper.create(getCtx(), AD_User_IDToUnlock, I_AD_User.class, localTrxName);
				
				final Timestamp curentLogin = (new Timestamp(System.currentTimeMillis()));
				final long loginFailureTime = user.getLoginFailureDate().getTime();
				final long newloginFailureTime = loginFailureTime + (1000 * 60 * accountLockExpire);
				final Timestamp acountUnlock = new Timestamp(newloginFailureTime);
				
				if (curentLogin.compareTo(acountUnlock) > 0)
				{
					user.setLoginFailureCount(0);
					user.setIsAccountLocked(false);
					InterfaceWrapperHelper.save(user);
					result[0] = 1;
				}
			}
		});

		return result[0];
	}

}
