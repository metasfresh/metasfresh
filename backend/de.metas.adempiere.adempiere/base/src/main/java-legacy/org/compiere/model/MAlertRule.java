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
package org.compiere.model;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Properties;

import de.metas.util.FileUtil;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.security.IUserRolePermissions;
import de.metas.security.RoleId;
import de.metas.security.permissions.Access;
import de.metas.util.Check;
import de.metas.util.StringUtils;


/**
 *	Alert Rule Model
 *
 *  @author Jorg Janke
 *  @version $Id: MAlertRule.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MAlertRule extends X_AD_AlertRule
{
	private static final long serialVersionUID = -1267260460210893262L;

	/**
	 * 	Standatd Constructor
	 *	@param ctx context
	 *	@param AD_AlertRule_ID id
	 *	@param trxName transaction
	 */
	public MAlertRule (Properties ctx, int AD_AlertRule_ID, String trxName)
	{
		super (ctx, AD_AlertRule_ID, trxName);
	}	//	MAlertRule

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MAlertRule (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MAlertRule

	/** Alert */
	private MAlert m_parent = null;

	/**
	 * Get parent
	 * @return parent alert
	 */
	public MAlert getParent() {
		if (m_parent == null || m_parent.get_ID() != getAD_Alert_ID())
			m_parent = new MAlert(getCtx(), getAD_Alert_ID(), get_TrxName());
		return m_parent;
	}

	/**
	 * Set parent alert.
	 * NOTE: is not setting AD_Alert_ID
	 * @param alert
	 */
	public void setParent(MAlert alert) {
		m_parent = alert;
	}

	/**
	 *	Get Sql
	 *	@return sql
	 * @deprecated Use {@link #getSql(boolean)} instead
	 */
	@Deprecated
	public String getSql()
	{
		return getSql(false);
	}

	/**
	 * Get Sql
	 * @param applySecurity apply role/client security
	 * @return sql
	 */
	public String getSql(boolean applySecurity)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ").append(getSelectClause())
			.append(" FROM ").append(getFromClause());
		if (getWhereClause() != null && getWhereClause().length() > 0)
			sql.append(" WHERE ").append(getWhereClause());
		String finalSQL = sql.toString();
		//
		// Apply Security:
		if (applySecurity) {
			MAlert alert = getParent();
			if (alert.isEnforceRoleSecurity()
					|| alert.isEnforceClientSecurity())
			{
				RoleId roleId = alert.getFirstRoleId();
				if (roleId == null)
					roleId = alert.getFirstUserRoleId();
				if (roleId != null)
				{
					final IUserRolePermissions role = Env.getUserRolePermissions(getCtx());
					finalSQL = role.addAccessSQL(finalSQL, null, IUserRolePermissions.SQL_FULLYQUALIFIED, Access.READ);
				}
			}
		}
		//
		if (getOtherClause() != null && getOtherClause().length() > 0)
			finalSQL += " " + getOtherClause();
		return finalSQL;
	}	//	getSql

	/**
	 * Create Report File
	 * @param extension file extension
	 * @return newly created File
	 */
	public File createReportFile(String extension)
	{
		if (Check.isEmpty(extension))
		{
			throw new IllegalArgumentException("Parameter extension cannot be empty");
		}
		String name = new SimpleDateFormat("yyyyMMddhhmm").format(new Timestamp(System.currentTimeMillis()))
						+"_"+StringUtils.stripDiacritics(getName().trim());
		File file = null;
		try
		{
			file = new File(FileUtil.getTempDir(), name+"."+extension);
			file.createNewFile();
			return file;
		}
		catch (Exception e)
		{
			file = null;
		}
		// Fallback
		String filePrefix = "Alert_"; // TODO: add AD_AlertRule.FileName (maybe)
		try
		{
			file = File.createTempFile(filePrefix, "."+extension);
		}
		catch (IOException e)
		{
			throw new AdempiereException(e);
		}
		return file;
	}

	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if (newRecord)
			setIsValid(true);
		if (isValid())
			setErrorMsg(null);
		return true;
	}	//	beforeSave

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		if (!success)
			return false;
		return updateParent();
	}

	@Override
	protected boolean afterDelete(boolean success) {
		if (!success)
			return false;
		return updateParent();
	}

	/**
	 * Update parent flags
	 * @return true if success
	 */
	private boolean updateParent() {
		final String sql_count = "SELECT COUNT(*) FROM "+Table_Name+" r"
							+" WHERE r."+COLUMNNAME_AD_Alert_ID+"=a."+MAlert.COLUMNNAME_AD_Alert_ID
								+" AND r."+COLUMNNAME_IsValid+"='N'"
								+" AND r.IsActive='Y'"
		;
		final String sql = "UPDATE "+MAlert.Table_Name+" a SET "
			+" "+MAlert.COLUMNNAME_IsValid+"=(CASE WHEN ("+sql_count+") > 0 THEN 'N' ELSE 'Y' END)"
			+" WHERE a."+MAlert.COLUMNNAME_AD_Alert_ID+"=?"
		;
		int no = DB.executeUpdateAndSaveErrorOnFail(sql, getAD_Alert_ID(), get_TrxName());
		return no == 1;
	}

	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MAlertRule[");
		sb.append(get_ID())
			.append("-").append(getName())
			.append(",Valid=").append(isValid())
			.append(",").append(getSql(false));
		sb.append ("]");
		return sb.toString ();
	}	//	toString

}	//	MAlertRule
