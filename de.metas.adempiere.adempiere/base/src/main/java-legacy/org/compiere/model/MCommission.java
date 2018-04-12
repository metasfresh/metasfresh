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
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

/**
 *	Model for Commission.
 *	(has Lines)
 *	
 *  @author Jorg Janke
 *  @version $Id: MCommission.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 *  @author victor.perez@e-evolution.com www.e-evolution.com [ 1867477 ] http://sourceforge.net/tracker/index.php?func=detail&aid=1867477&group_id=176962&atid=879332
 *	FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
 */
public class MCommission extends X_C_Commission
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1786202619739310928L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_Commission_ID id
	 *	@param trxName transaction
	 */
	public MCommission(Properties ctx, int C_Commission_ID, String trxName)
	{
		super(ctx, C_Commission_ID, trxName);
		if (C_Commission_ID == 0)
		{
		//	setName (null);
		//	setC_BPartner_ID (0);
		//	setC_Charge_ID (0);
		//	setC_Commission_ID (0);
		//	setC_Currency_ID (0);
			//
			setDocBasisType (DOCBASISTYPE_Invoice);	// I
			setFrequencyType (FREQUENCYTYPE_Monthly);	// M
			setListDetails (false);
		}
	}	//	MCommission

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MCommission(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MCommission

	/**
	 * 	Get Lines
	 *	@return array of lines
	 */
	public MCommissionLine[] getLines()
	{
		//[ 1867477 ]
		//FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
		String whereClause = "IsActive='Y' AND C_Commission_ID=?";
		List<MCommissionLine> list  = new Query(getCtx(), MCommissionLine.Table_Name, whereClause, get_TrxName())
		.setParameters(new Object[]{getC_Commission_ID()})
		.setOrderBy("Line")
		.list(MCommissionLine.class);	
		//	Convert
		MCommissionLine[] retValue = new MCommissionLine[list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	getLines

	/**
	 * 	Set Date Last Run
	 *	@param DateLastRun date
	 */
	@Override
	public void setDateLastRun (Timestamp DateLastRun)
	{
		if (DateLastRun != null)
			super.setDateLastRun(DateLastRun);
	}	//	setDateLastRun

	/**
	 * 	Copy Lines From other Commission
	 *	@param otherCom commission
	 *	@return number of lines copied
	 */
	public int copyLinesFrom (MCommission otherCom)
	{
		if (otherCom == null)
			return 0;
		MCommissionLine[] fromLines = otherCom.getLines ();
		int count = 0;
		for (int i = 0; i < fromLines.length; i++)
		{
			MCommissionLine line = new MCommissionLine (getCtx(), 0, get_TrxName());
			PO.copyValues(fromLines[i], line, getAD_Client_ID(), getAD_Org_ID());
			line.setC_Commission_ID (getC_Commission_ID());
			if (line.save())
				count++;
		}
		if (fromLines.length != count)
			log.error("copyLinesFrom - Line difference - From=" + fromLines.length + " <> Saved=" + count);
		return count;
	}	//	copyLinesFrom

}	//	MCommission
