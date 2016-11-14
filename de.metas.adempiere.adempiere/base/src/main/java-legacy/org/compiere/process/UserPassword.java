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
package org.compiere.process;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_User;
import org.compiere.model.MUser;
import org.compiere.util.DB;

/**
 * Reset Password
 * 
 * @author Jorg Janke
 * @version $Id: UserPassword.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class UserPassword extends SvrProcess
{
	private int			p_AD_User_ID = -1;
	private String 		p_OldPassword = null;
	private String 		p_NewPassword = null;
	private String		p_NewEMail = null;
	private String		p_NewEMailUser = null;
	private String		p_NewEMailUserPW = null;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("AD_User_ID"))
				p_AD_User_ID = para[i].getParameterAsInt();
			else if (name.equals("OldPassword"))
				p_OldPassword = (String)para[i].getParameter();
			else if (name.equals("NewPassword"))
				p_NewPassword = (String)para[i].getParameter();
			else if (name.equals("NewEMail"))
				p_NewEMail = (String)para[i].getParameter();
			else if (name.equals("NewEMailUser"))
				p_NewEMailUser = (String)para[i].getParameter();
			else if (name.equals("NewEMailUserPW"))
				p_NewEMailUserPW = (String)para[i].getParameter();
			else
				log.error("Unknown Parameter: " + name);
		}
	}	// prepare

	/**
	 * Perform process.
	 * 
	 * @return Message
	 * @throws Exception if not successful
	 */
	@Override
	protected String doIt() throws Exception
	{
		log.info("AD_User_ID=" + p_AD_User_ID + " from " + getAD_User_ID());

		final I_AD_User user = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User.class, getCtx(), get_TrxName())
				.addEqualsFilter(I_AD_User.COLUMNNAME_AD_User_ID, p_AD_User_ID)
				.create()
				.firstOnly(I_AD_User.class);
		log.debug("User=" + user);
		
		//	Do we need a password ?
		if (Check.isEmpty(p_OldPassword) && isOldPasswordRequired(p_AD_User_ID))		//	Password required
		{
			throw new IllegalArgumentException("@OldPasswordMandatory@");
		}
		//	is entered Password correct ?
		else if (!p_OldPassword.equals(user.getPassword()))
		{
			throw new IllegalArgumentException("@OldPasswordNoMatch@");
		}
		
		//	Change Super User
		if (p_AD_User_ID == 0)
		{
			String sql = "UPDATE AD_User SET Updated=now(), UpdatedBy=" + getAD_User_ID();
			if (!Check.isEmpty(p_NewPassword))
				sql += ", Password=" + DB.TO_STRING(p_NewPassword);
			if (!Check.isEmpty(p_NewEMail))
				sql += ", Email=" + DB.TO_STRING(p_NewEMail);
			if (!Check.isEmpty(p_NewEMailUser))
				sql += ", EmailUser=" + DB.TO_STRING(p_NewEMailUser);
			if (!Check.isEmpty(p_NewEMailUserPW))
				sql += ", EmailUserPW=" + DB.TO_STRING(p_NewEMailUserPW);
			sql += " WHERE AD_User_ID=0";
			
			DB.executeUpdateEx(sql, get_TrxName());
			return "OK";
		}
		else
		{
			if (!Check.isEmpty(p_NewPassword))
				user.setPassword(p_NewPassword);
			if (!Check.isEmpty(p_NewEMail))
				user.setEMail(p_NewEMail);
			if (!Check.isEmpty(p_NewEMailUser))
				user.setEMailUser(p_NewEMailUser);
			if (!Check.isEmpty(p_NewEMailUserPW))
				user.setEMailUserPW(p_NewEMailUserPW);
			//
			InterfaceWrapperHelper.save(user);
			return "OK";
		}
	}	// doIt

	private boolean isOldPasswordRequired(final int adUserId)
	{
		final MUser operator = MUser.get(getCtx(), getAD_User_ID());

		if (adUserId == IUserDAO.SYSTEM_USER_ID			//	change of System
				|| adUserId == IUserDAO.SUPERUSER_USER_ID		//	change of SuperUser
				|| !operator.isAdministrator())
		{
			return true;
		}
		
		return false;
	}
}	//	UserPassword

