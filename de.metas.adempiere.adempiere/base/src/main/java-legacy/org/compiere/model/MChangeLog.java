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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.compiere.util.DB;

/**
 *	Change Log Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MChangeLog.java,v 1.3 2006/07/30 00:58:18 jjanke Exp $
 */
public class MChangeLog extends X_AD_ChangeLog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7156972859181201446L;


	/**
	 * 	Do we track changes for this table
	 *	@param AD_Table_ID table
	 *	@return true if changes are tracked
	 */
	public static boolean isLogged (int AD_Table_ID)
	{
		if (s_changeLog == null || s_changeLog.length == 0)
			fillChangeLog();
		//
		int index = Arrays.binarySearch(s_changeLog, AD_Table_ID);
		return index >= 0;
	}	//	trackChanges
	
	/**
	 *	Fill Log with tables to be logged 
	 */
	private static void fillChangeLog()
	{
		ArrayList<Integer> list = new ArrayList<Integer>(40);
		String sql = "SELECT t.AD_Table_ID FROM AD_Table t "
			+ "WHERE t.IsChangeLog='Y'"					//	also inactive
			+ " OR EXISTS (SELECT * FROM AD_Column c "
				+ "WHERE t.AD_Table_ID=c.AD_Table_ID AND c.ColumnName='EntityType') "
			+ "ORDER BY t.AD_Table_ID";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new Integer(rs.getInt(1)));
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//	Convert to Array
		s_changeLog = new int [list.size()];
		for (int i = 0; i < s_changeLog.length; i++)
		{
			Integer id = (Integer)list.get(i);
			s_changeLog[i] = id.intValue();
		}
		s_log.info("#" + s_changeLog.length);
	}	//	fillChangeLog

	/**	Change Log				*/
	private static int[]		s_changeLog = null;
	/**	Logger					*/
	private static Logger		s_log = LogManager.getLogger(MChangeLog.class);
	/** NULL Value				*/
	public static final String	NULL = "NULL";
	
	
	/**************************************************************************
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MChangeLog(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MChangeLog

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_ChangeLog_ID id
	 *	@param trxName transaction
	 */
	public MChangeLog (Properties ctx, int AD_ChangeLog_ID, String trxName)
	{
		super (ctx, 0, trxName);
	}	//	MChangeLog
	
	/**
	 *	Preserved for backward compatibility
	 *@deprecated
	 */
	public MChangeLog (Properties ctx, 
			int AD_ChangeLog_ID, String TrxName, int AD_Session_ID, 
			int AD_Table_ID, int AD_Column_ID, int Record_ID,
			int AD_Client_ID, int AD_Org_ID,
			Object OldValue, Object NewValue)
	{
		this(ctx, AD_ChangeLog_ID, TrxName, AD_Session_ID, AD_Table_ID,
				AD_Column_ID, Record_ID, AD_Client_ID, AD_Org_ID, OldValue,
				NewValue, (String) null /*event*/ );
	}	// MChangeLog

	/**
	 * 	Full Constructor
	 *	@param ctx context
	 *	@param AD_ChangeLog_ID 0 for new change log
	 *	@param TrxName transaction
	 *	@param AD_Session_ID session
	 *	@param AD_Table_ID table
	 *	@param AD_Column_ID column
	 *	@param Record_ID record
	 *	@param AD_Client_ID client
	 *	@param AD_Org_ID org
	 *	@param OldValue old
	 *	@param NewValue new
	 */
	public MChangeLog (Properties ctx, 
		int AD_ChangeLog_ID, String TrxName, int AD_Session_ID, 
		int AD_Table_ID, int AD_Column_ID, int Record_ID,
		int AD_Client_ID, int AD_Org_ID,
		Object OldValue, Object NewValue, String event)
	{
		this (ctx, 0, TrxName);	
		
		if (AD_ChangeLog_ID <= 0
				&& !DB.isUseNativeSequences(AD_Client_ID, Table_Name))
		{
			AD_ChangeLog_ID = DB.getNextID (AD_Client_ID, Table_Name, TrxName);
			if (AD_ChangeLog_ID <= 0)
				log.error("No NextID (" + AD_ChangeLog_ID + ")");
		}
		if (AD_ChangeLog_ID > 0)
		{
			setAD_ChangeLog_ID (AD_ChangeLog_ID);
		}
		else
		{
			// let the database to set it
			set_ValueNoCheck(COLUMNNAME_AD_ChangeLog_ID, null);
		}
		
		setTrxName(TrxName);
		setAD_Session_ID (AD_Session_ID);
		//
		setAD_Table_ID (AD_Table_ID);
		setAD_Column_ID (AD_Column_ID);
		setRecord_ID (Record_ID);
		//
		setClientOrg (AD_Client_ID, AD_Org_ID);
		//
		setOldValue (OldValue);
		setNewValue (NewValue);
		setEventChangeLog(event);
		//	EVENT / Release 3.3.1t_2007-12-05 ADempiere
		// Drop description from AD_ChangeLog - pass it to AD_Session to save disk space
		// setDescription(Adempiere.MAIN_VERSION + "_" + Adempiere.DATE_VERSION + " " + Adempiere.getImplementationVersion());
	}	//	MChangeLog

	
	/**
	 * 	Set Old Value
	 *	@param OldValue old
	 */
	public void setOldValue (Object OldValue)
	{
		if (OldValue == null)
			super.setOldValue (NULL);
		else
			super.setOldValue (OldValue.toString());
	}	//	setOldValue

	/**
	 * 	Is Old Value Null
	 *	@return true if null
	 */
	public boolean isOldNull()
	{
		String value = getOldValue();
		return value == null || value.equals(NULL);
	}	//	isOldNull
	
	/**
	 * 	Set New Value
	 *	@param NewValue new
	 */
	public void setNewValue (Object NewValue)
	{
		if (NewValue == null)
			super.setNewValue (NULL);
		else
			super.setNewValue (NewValue.toString());
	}	//	setNewValue
	
	/**
	 * 	Is New Value Null
	 *	@return true if null
	 */
	public boolean isNewNull()
	{
		String value = getNewValue();
		return value == null || value.equals(NULL);
	}	//	isNewNull
	
}	//	MChangeLog
