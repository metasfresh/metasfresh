package org.adempiere.ad.service.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.service.ISystemBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_System;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.adempiere.util.CacheCtx;
import de.metas.logging.LogManager;
import lombok.NonNull;

public class SystemBL implements ISystemBL
{

	
	private static final Logger logger = LogManager.getLogger(SystemBL.class);

	
	@Override
	@Cached(cacheName = I_AD_System.Table_Name)
	public I_AD_System get(@CacheCtx final Properties ctx)
	{
		// Retrieve AD_System record
		final I_AD_System system = Services.get(IQueryBL.class).createQueryBuilder(I_AD_System.class, ctx, ITrx.TRXNAME_None)
				.create()
				.firstOnly(I_AD_System.class);

		// guard agaist null (shall not happen)
		if (system == null)
		{
			return null;
		}

		

		//
		// If running on server side, update the system info
		// TODO i think we shall move this logic somewhere else where is obvious what we are doing and not hidden here.
//		if (Ini.getRunMode() == RunMode.BACKEND)
//		{
//			if (setInfo(system))
//			{
//				InterfaceWrapperHelper.save(system);
//			}
//		}

		return system;
	}
	
	/**
	 * 	Get Statistics Info
	 * 	@param recalc recalculate
	 *	@return statistics
	 */
	@Override
	public String getStatisticsInfo (boolean recalc)
	{
		String s = get(Env.getCtx()).getStatisticsInfo ();
		if (s == null || recalc)
		{
			String sql = "SELECT 'C'||(SELECT " + DB.TO_CHAR("COUNT(*)", DisplayType.Number, Env.getAD_Language(Env.getCtx())) + " FROM AD_Client)"
				+ "||'U'|| (SELECT " + DB.TO_CHAR("COUNT(*)", DisplayType.Number, Env.getAD_Language(Env.getCtx())) + " FROM AD_User)"
				+ "||'B'|| (SELECT " + DB.TO_CHAR("COUNT(*)", DisplayType.Number, Env.getAD_Language(Env.getCtx())) + " FROM C_BPartner)"
				+ "||'P'|| (SELECT " + DB.TO_CHAR("COUNT(*)", DisplayType.Number, Env.getAD_Language(Env.getCtx())) + " FROM M_Product)"
				+ "||'I'|| (SELECT " + DB.TO_CHAR("COUNT(*)", DisplayType.Number, Env.getAD_Language(Env.getCtx())) + " FROM C_Invoice)"
				+ "||'L'|| (SELECT " + DB.TO_CHAR("COUNT(*)", DisplayType.Number, Env.getAD_Language(Env.getCtx())) + " FROM C_InvoiceLine)"
				+ "||'M'|| (SELECT " + DB.TO_CHAR("COUNT(*)", DisplayType.Number, Env.getAD_Language(Env.getCtx())) + " FROM M_Transaction)"
				+ " FROM AD_System";
			s = DB.getSQLValueString(null, sql);
		}
		return s;
	}	//	getStatisticsInfo
	
	/**
	 * 	Get Profile Info
	 * 	@param recalc recalculate
	 *	@return profile
	 */
	@Override
	public String getProfileInfo (boolean recalc)
	{
		String s = get(Env.getCtx()).getProfileInfo();
		if (s == null || recalc)
		{
			final String sql = "SELECT Value FROM AD_Client WHERE IsActive='Y' ORDER BY AD_Client_ID DESC";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuffer sb = new StringBuffer();
			try
			{
				pstmt = DB.prepareStatement (sql, null);
				rs = pstmt.executeQuery ();
				while (rs.next ())
				{
					sb.append(rs.getString(1)).append('|');
				}
			}
			catch (SQLException e)
			{
				throw new DBException(e, sql);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null; pstmt = null;
			}
			s = sb.toString();
		}
		return s;
	}
	
	/**************************************************************************
	 * 	Set/Derive Info if more then a day old
	 * 	@return true if set
	 */
	@Override
	public boolean setInfo(@NonNull final I_AD_System system)
	{
		if (!TimeUtil.getDay(system.getUpdated()).before(TimeUtil.getDay(null)))
		{
			return false;
		}
		try
		{
			setInternalUsers();
			if (system.isAllowStatistics())
			{
				system.setStatisticsInfo(getStatisticsInfo(true));
				system.setProfileInfo(getProfileInfo(true));
			}
		}
		catch (Exception e)
		{
			system.setSupportUnits(9999);
			system.setInfo(e.getLocalizedMessage());
			logger.error("", e);
		}
		return true;
	}	//	setInfo
	
	/**
	 * 	Set Internal User Count
	 */
	private void setInternalUsers()
	{
		final I_AD_System system = get(Env.getCtx());
		
		final String sql = "SELECT COUNT(DISTINCT (u.AD_User_ID)) AS iu "
			+ "FROM AD_User u"
			+ " INNER JOIN AD_User_Roles ur ON (u.AD_User_ID=ur.AD_User_ID) "
			+ "WHERE u.AD_Client_ID<>11"			//	no Demo
			+ " AND u.AD_User_ID NOT IN (0,100)";	//	no System/SuperUser
		int internalUsers = DB.getSQLValue(null, sql);
		system.setSupportUnits(internalUsers);
	}	//	setInternalUsers



	/*
	 * Allow remember me feature
	 * ZK_LOGIN_ALLOW_REMEMBER_ME and SWING_ALLOW_REMEMBER_ME parameter allow the next values
	 *   U - Allow remember the username (default for zk)
	 *   P - Allow remember the username and password (default for swing)
	 *   N - None
	 *   
	 *	@return boolean representing if remember me feature is allowed
	 */
	public static final String SYSTEM_ALLOW_REMEMBER_USER = "U";
	public static final String SYSTEM_ALLOW_REMEMBER_PASSWORD = "P";

	@Override
	public boolean isRememberUserAllowed(@NonNull final String sysConfigKey) {
		String ca = Services.get(ISysConfigBL.class).getValue(sysConfigKey, SYSTEM_ALLOW_REMEMBER_PASSWORD);
		return (ca.equalsIgnoreCase(SYSTEM_ALLOW_REMEMBER_USER) || ca.equalsIgnoreCase(SYSTEM_ALLOW_REMEMBER_PASSWORD));
	}

	@Override
	public boolean isRememberPasswordAllowed(@NonNull final String sysConfigKey) {
		String ca = Services.get(ISysConfigBL.class).getValue(sysConfigKey, SYSTEM_ALLOW_REMEMBER_PASSWORD);
		return (ca.equalsIgnoreCase(SYSTEM_ALLOW_REMEMBER_PASSWORD));
	}
	
	
	
	@Override
	public boolean isValid()
	{
		final I_AD_System system = get(Env.getCtx());
		if (system.getName() == null || system.getName().length() < 2)
		{
			logger.warn("Name not valid: " + system.getName());
			return false;
		}
		if (system.getPassword() == null || system.getPassword().length() < 2)
		{
			logger.warn("Password not valid: " + system.getPassword());
			return false;
		}
		if (system.getInfo() == null || system.getInfo().length() < 2)
		{
			logger.warn("Need to run Migration once");
			return false;
		}
		return true;
	}	//	isValid

}
