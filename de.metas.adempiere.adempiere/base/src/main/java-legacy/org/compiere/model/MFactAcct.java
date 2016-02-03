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
import java.util.Properties;

import org.adempiere.acct.api.IFactAcctBL;
import org.adempiere.acct.api.IFactAcctDAO;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Services;
import org.compiere.util.CLogger;
import org.compiere.util.DB;


/**
 *	Accounting Fact Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MFactAcct.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 *  
 *  @author Teo Sarca, http://www.arhipac.ro
 *  			<li>FR [ 2079083 ] Add MFactAcct.deleteEx method
 */
public class MFactAcct extends X_Fact_Acct
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 756203818233903671L;

	/**
	 * Delete Accounting
	 * @param AD_Table_ID table
	 * @param Record_ID record
	 * @param trxName transaction
	 * @return number of rows deleted
	 * @throws DBException on database exception
	 * @deprecated Please use {@link IFactAcctDAO#deleteForDocument(Object)}
	 */
	@Deprecated
	public static int deleteEx(int AD_Table_ID, int Record_ID, String trxName)
	throws DBException
	{
		final String sql = "DELETE FROM Fact_Acct WHERE AD_Table_ID=? AND Record_ID=?";
		int no = DB.executeUpdateEx(sql, new Object[]{AD_Table_ID, Record_ID}, trxName);
		s_log.fine("deleted - AD_Table_ID=" + AD_Table_ID + ", Record_ID=" + Record_ID + " - #" + no);
		return no;
	}

	/**	Static Logger	*/
	private static CLogger	s_log	= CLogger.getCLogger (MFactAcct.class);
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param Fact_Acct_ID id
	 *	@param trxName transaction
	 */
	public MFactAcct (Properties ctx, int Fact_Acct_ID, String trxName)
	{
		super (ctx, Fact_Acct_ID, trxName);
	}	//	MFactAcct

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MFactAcct (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MFactAcct

	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MFactAcct[");
		sb.append(get_ID()).append("-Acct=").append(getAccount_ID())
			.append(",Dr=").append(getAmtSourceDr()).append("|").append(getAmtAcctDr())
			.append(",Cr=").append(getAmtSourceCr()).append("|").append(getAmtAcctCr())
			.append ("]");
		return sb.toString ();
	}	//	toString

	/**
	 * Derive MAccount from record
	 *
	 * @return Valid Account Combination
	 * @deprecated Please use {@link IFactAcctBL#getAccount(I_Fact_Acct)}
	 */
	@Deprecated
	public MAccount getMAccount()
	{
		return Services.get(IFactAcctBL.class).getAccount(this);
	}

	/**
	 * Check if a document is already posted
	 * @param AD_Table_ID table
	 * @param Record_ID record
	 * @param trxName transaction
	 * @return boolean indicating if the document has already accounting facts
	 * @throws DBException on database exception
	 */
	public static boolean alreadyPosted(int AD_Table_ID, int Record_ID, String trxName) throws DBException
	{
		final String sql = "SELECT 1 FROM Fact_Acct WHERE AD_Table_ID=? AND Record_ID=?";
		int one = DB.getSQLValue(trxName, sql, new Object[]{AD_Table_ID, Record_ID});
		return (one == 1);
	}
	
}	//	MFactAcct
