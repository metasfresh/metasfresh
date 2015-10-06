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
 * Contributor(s): Carlos Ruiz - globalqss                                    *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

/**
 * Table Validator Scripts
 * 
 * @author Carlos Ruiz
 * @version $Id: MTableScriptValidator.java
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL <li>BF [ 1885496 ] Performance NEEDS <li>BF [ 2819654 ] Table Script Validator SeqNo is not set
 *         https://sourceforge.net/tracker/?func=detail&atid=879332&aid=2819654&group_id=176962
 */
public class MTableScriptValidator extends X_AD_Table_ScriptValidator
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6272423660330749776L;

	/**************************************************************************
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param AD_Table_ScriptValidator_ID id
	 * @param trxName transaction
	 */
	public MTableScriptValidator(Properties ctx, int AD_Table_ScriptValidator_ID, String trxName)
	{
		super(ctx, AD_Table_ScriptValidator_ID, trxName);
	}	// MTableScriptValidator

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MTableScriptValidator(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MTableScriptValidator

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (getSeqNo() == 0)
		{
			final String sql = "SELECT COALESCE(MAX(SeqNo),0) + 10 FROM " + Table_Name
					+ " WHERE AD_Table_ID=? AND EventModelValidator=?";
			int seqNo = DB.getSQLValueEx(get_TrxName(), sql, getAD_Table_ID(), getEventModelValidator());
			setSeqNo(seqNo);
		}
		//
		return true;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("MTableScriptValidator[");
		sb.append(get_ID()).append("-").append(getAD_Table_ID()).append("-")
				.append(getEventModelValidator()).append("]");
		return sb.toString();
	}	// toString
}	// MTableScriptValidator