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

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import org.compiere.util.CLogger;
import org.compiere.util.KeyNamePair;

/**
 *	Default Accounts for MAcctSchema
 *	
 *  @author Jorg Janke
 *  @author     victor.perez@e-evolution.com, www.e-evolution.com
 *    			<li>RF [ 2214883 ] Remove SQL code and Replace for Query http://sourceforge.net/tracker/index.php?func=detail&aid=2214883&group_id=176962&atid=879335
 *  @version $Id: MAcctSchemaDefault.java,v 1.3 2006/07/30 00:58:37 jjanke Exp $
 */
public class MAcctSchemaDefault extends X_C_AcctSchema_Default
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 199959007595802866L;

	/**
	 * 	Get Accounting Schema Default Info
	 *	@param ctx context
	 *	@param C_AcctSchema_ID id
	 *	@return defaults
	 */
	public static MAcctSchemaDefault get (Properties ctx, int C_AcctSchema_ID)
	{
		String whereClause = "C_AcctSchema_ID=?";
		return new Query(ctx,MAcctSchemaDefault.Table_Name,whereClause,null)
		.setParameters(new Object[]{C_AcctSchema_ID})
		.firstOnly();
	}	//	get
	
	/**	Logger							*/
	protected static CLogger			s_log = CLogger.getCLogger(MAcctSchemaDefault.class);

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param C_AcctSchema_ID parent
	 *	@param trxName transaction
	 */
	public MAcctSchemaDefault(Properties ctx, int C_AcctSchema_ID, String trxName)
	{
		super(ctx, C_AcctSchema_ID, trxName);
	}	//	MAcctSchemaDefault

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MAcctSchemaDefault(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MAcctSchemaDefault

	/**
	 * 	Get Realized Gain Acct for currency
	 *	@param C_Currency_ID currency
	 *	@return gain acct
	 */
	public int getRealizedGain_Acct (int C_Currency_ID)
	{
		MCurrencyAcct acct = MCurrencyAcct.get (this, C_Currency_ID);
		if (acct != null)
			return acct.getRealizedGain_Acct(); 
		return super.getRealizedGain_Acct();
	}	//	getRealizedGain_Acct
	
	/**
	 * 	Get Realized Loss Acct for currency
	 *	@param C_Currency_ID currency
	 *	@return loss acct
	 */
	public int getRealizedLoss_Acct (int C_Currency_ID) 
	{
		MCurrencyAcct acct = MCurrencyAcct.get (this, C_Currency_ID);
		if (acct != null)
			return acct.getRealizedLoss_Acct(); 
		return super.getRealizedLoss_Acct();
	}	//	getRealizedLoss_Acct

	/**
	 * 	Get Acct Info list 
	 *	@return list
	 */
	public ArrayList<KeyNamePair> getAcctInfo()
	{
		ArrayList<KeyNamePair> list = new ArrayList<KeyNamePair>();
		for (int i = 0; i < get_ColumnCount(); i++)
		{
			String columnName = get_ColumnName(i);
			if (columnName.endsWith("Acct"))
			{
				int id = ((Integer)get_Value(i));
				list.add(new KeyNamePair (id, columnName));
			}
		}
		return list;
	}	//	getAcctInfo

	/**
	 * 	Set Value (don't use)
	 *	@param columnName column name
	 *	@param value value
	 *	@return true if value set
	 */
	public boolean setValue (String columnName, Integer value)
	{
		return super.set_Value (columnName, value);
	}	//	setValue

	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		if (getAD_Org_ID() != 0)
			setAD_Org_ID(0);
		return true;
	}	//	beforeSave

}	//	MAcctSchemaDefault
