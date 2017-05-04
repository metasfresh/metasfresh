/**
 * 
 */
package de.metas.callcenter.model;

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


import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_R_Group;
import org.compiere.model.I_R_Request;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/**
 * @author Teo Sarca, teo.sarca@gmail.com
 */
public class MRGroupProspect extends X_R_Group_Prospect
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8146063466656352670L;
	/** Lock expire time (minutes) */
	public static final int LOCK_EXPIRE_MIN = 24 * 60;
	
	public static MRGroupProspect get(Properties ctx, I_RV_R_Group_Prospect contact, String trxName)
	{
		String whereClause = COLUMNNAME_R_Group_ID+"=?"
		+" AND "+COLUMNNAME_C_BPartner_ID+"=?"
		+" AND "+COLUMNNAME_AD_User_ID+"=?";
		MRGroupProspect gp = new Query(ctx, Table_Name, whereClause, trxName)
		.setParameters(new Object[]{
				contact.getR_Group_ID(),
				contact.getC_BPartner_ID(),
				contact.getAD_User_ID()})
				.firstOnly();
		return gp;
	}
	
	public static MRGroupProspect get(Properties ctx, I_R_Request request, String trxName)
	{
		final String whereClause = COLUMNNAME_R_Group_ID+"=?"
		+" AND "+COLUMNNAME_C_BPartner_ID+"=?"
		+" AND "+COLUMNNAME_AD_User_ID+"=?";
		MRGroupProspect gp = new Query(ctx, Table_Name, whereClause, trxName)
		.setParameters(new Object[]{
				request.getR_Group_ID(),
				request.getC_BPartner_ID(),
				request.getAD_User_ID()})
				.firstOnly();
		return gp;
	}
	
	/**
	 * Check if contact already added
	 * @param ctx
	 * @param R_Group_ID bundle
	 * @param C_BPartner_ID partner
	 * @param AD_User_ID (ignored)
	 * @param trxName
	 * @return
	 */
	public static boolean existContact(Properties ctx, int R_Group_ID, int C_BPartner_ID, int AD_User_ID, String trxName)
	{
		final String whereClause = COLUMNNAME_R_Group_ID+"=?"
		+" AND "+COLUMNNAME_C_BPartner_ID+"=?"
//		+" AND "+COLUMNNAME_AD_User_ID+"=?"
		;
		boolean match = new Query(ctx, Table_Name, whereClause, trxName)
		.setParameters(new Object[]{R_Group_ID, C_BPartner_ID})
		.match();
		return match;
	}
	
	public static void linkRequest(Properties ctx, I_R_Request request, String trxName)
	{
		MRGroupProspect gp = get(ctx, request, trxName);
		if (gp == null)
			return; // TODO: throw error?
		
		gp.setR_Request_ID(request.getR_Request_ID());
		gp.unlockContact();
		gp.saveEx();
	}
	
	public MRGroupProspect(Properties ctx, int id, String trxName)
	{
		super(ctx, id, trxName);
	}
	public MRGroupProspect(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}
	
	/**
	 * Creates a new record.
	 */
	public MRGroupProspect(Properties ctx, int R_Group_ID, int C_BPartner_ID, int AD_User_ID, String trxName)
	{
		super(ctx, 0, trxName);
		setR_Group_ID(R_Group_ID);
		setC_BPartner_ID(C_BPartner_ID);
		setAD_User_ID(AD_User_ID);
	}
	
	
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		BundleUtil.updateCCM_Bundle_Status(getR_Group_ID(), get_TrxName());
		return true;
	}

	@Override
	protected boolean beforeDelete()
	{
		if (getR_Request_ID() > 0)
		{
			throw new AdempiereException("@R_Request_ID@");
		}
		expireLock();
		if (isLocked())
		{
			throw new AdempiereException("de.metas.callcenter.CannotDeleteLocked");
		}
		return true;
	}
	
	@Override
	protected boolean afterDelete(boolean success)
	{
		if (!success)
			return success;
		BundleUtil.updateCCM_Bundle_Status(getR_Group_ID(), get_TrxName());
		return true;
	}

	public void lockContact()
	{
		int AD_User_ID = Env.getAD_User_ID(getCtx());
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		setLocked(true);
		setLockedBy(AD_User_ID);
		setLockedDate(ts);
	}
	
	public void unlockContact()
	{
		setLocked(false);
		set_Value(COLUMNNAME_LockedBy, null);
		setLockedDate(null);
	}
	
	public boolean isExpired()
	{
		if (!isLocked())
			return true;

		Timestamp dateExpire = TimeUtil.addMinutess(getLockedDate(), LOCK_EXPIRE_MIN);
		Timestamp now = new Timestamp(System.currentTimeMillis());
		return dateExpire.before(now);
	}
	
	public void expireLock()
	{
		if (isLocked() && isExpired())
			unlockContact();
	}
	
	@Override
	public String toString()
	{
		String bundleName = DB.getSQLValueString(get_TrxName(),
				"SELECT "+I_R_Group.COLUMNNAME_Name+" FROM "+I_R_Group.Table_Name
				+" WHERE "+I_R_Group.COLUMNNAME_R_Group_ID+"=?",
				getR_Group_ID());
		String bpName = DB.getSQLValueString(get_TrxName(),
				"SELECT "+I_C_BPartner.COLUMNNAME_Value+"||'_'||"+I_C_BPartner.COLUMNNAME_Name
				+" FROM "+I_C_BPartner.Table_Name
				+" WHERE "+I_C_BPartner.COLUMNNAME_C_BPartner_ID+"=?",
				getC_BPartner_ID());
		return bundleName+"/"+bpName;
	}
}
