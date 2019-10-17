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
import java.util.Properties;

import org.compiere.impexp.BankStatementMatcherInterface;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissions;
import de.metas.security.permissions.Access;

/**
 *	Bank Statement Matcher Algorithm
 *	
 *  @author Jorg Janke
 *  @version $Id: MBankStatementMatcher.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class MBankStatementMatcher extends X_C_BankStatementMatcher
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3756318777177414260L;

	/**
	 * 	Get Bank Statement Matcher Algorithms
	 * 	@param ctx context
	 *	@param trxName transaction
	 *	@return matchers
	 */
	public static MBankStatementMatcher[] getMatchers (Properties ctx, String trxName)
	{
		ArrayList<MBankStatementMatcher> list = new ArrayList<MBankStatementMatcher>();
		String sql = Env.getUserRolePermissions(ctx).addAccessSQL(
			"SELECT * FROM C_BankStatementMatcher ORDER BY SeqNo", 
			"C_BankStatementMatcher", IUserRolePermissions.SQL_NOTQUALIFIED, Access.READ);
		int AD_Client_ID = Env.getAD_Client_ID(ctx);
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				list.add (new MBankStatementMatcher(ctx, rs, trxName));
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		//	Convert		
		MBankStatementMatcher[] retValue = new MBankStatementMatcher[list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	getMatchers

	/** Static Logger					*/
	private static Logger 	s_log = LogManager.getLogger(MBankStatementMatcher.class);

	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_BankStatementMatcher_ID id
	 *	@param trxName transaction
	 */
	public MBankStatementMatcher(Properties ctx, int C_BankStatementMatcher_ID, String trxName)
	{
		super(ctx, C_BankStatementMatcher_ID, trxName);
	}	//	MBankStatementMatcher

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MBankStatementMatcher(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MBankStatementMatcher

	private BankStatementMatcherInterface	m_matcher = null;
	private Boolean							m_matcherValid = null;

	/**
	 * 	Is Matcher Valid
	 *	@return true if valid
	 */
	public boolean isMatcherValid()
	{
		if (m_matcherValid == null)
			getMatcher();
		return m_matcherValid.booleanValue();
	}	//	isMatcherValid

	/**
	 * 	Get Matcher 
	 *	@return Matcher Instance
	 */
	public BankStatementMatcherInterface getMatcher()
	{
		if (m_matcher != null 
			|| (m_matcherValid != null && m_matcherValid.booleanValue()))
			return m_matcher;
			
		String className = getClassname();
		if (className == null || className.length() == 0)
			return null;
		
		try
		{
			Class matcherClass = Class.forName(className);
			m_matcher = (BankStatementMatcherInterface)matcherClass.newInstance();
			m_matcherValid = Boolean.TRUE;
		}
		catch (Exception e)
		{
			log.error(className, e);
			m_matcher = null;
			m_matcherValid = Boolean.FALSE;
		}
		return m_matcher;
	}	//	getMatcher


}	//	MBankStatementMatcher
