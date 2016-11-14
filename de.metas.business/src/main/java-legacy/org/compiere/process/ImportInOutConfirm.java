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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.compiere.model.MInOutLineConfirm;
import org.compiere.model.X_I_InOutLineConfirm;
import org.compiere.util.DB;


/**
 *	Import Confirmations
 *	
 *  @author Jorg Janke
 *  @version $Id: ImportInOutConfirm.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class ImportInOutConfirm extends SvrProcess
{
	/**	Client to be imported to		*/
	private int 			p_AD_Client_ID = 0;
	/**	Delete old Imported			*/
	private boolean			p_DeleteOldImported = false;
	/**	Import						*/
	private int				p_I_InOutLineConfirm_ID = 0;
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("AD_Client_ID"))
				p_AD_Client_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("DeleteOldImported"))
				p_DeleteOldImported = "Y".equals(para[i].getParameter());
			else
				log.error("Unknown Parameter: " + name);
		}
		p_I_InOutLineConfirm_ID = getRecord_ID();
	}	//	prepare

	/**
	 * 	doIt
	 *	@return info
	 */
	protected String doIt () throws Exception
	{
		log.info("I_InOutLineConfirm_ID=" + p_I_InOutLineConfirm_ID);
		StringBuffer sql = null;
		int no = 0;
		String clientCheck = " AND AD_Client_ID=" + p_AD_Client_ID;
		
		//	Delete Old Imported
		if (p_DeleteOldImported)
		{
			sql = new StringBuffer ("DELETE FROM I_InOutLineConfirm "
				  + "WHERE I_IsImported='Y'").append (clientCheck);
			no = DB.executeUpdate(sql.toString(), get_TrxName());
			log.debug("Deleted Old Imported =" + no);
		}

		//	Set IsActive, Created/Updated
		sql = new StringBuffer ("UPDATE I_InOutLineConfirm "
			+ "SET IsActive = COALESCE (IsActive, 'Y'),"
			+ " Created = COALESCE (Created, now()),"
			+ " CreatedBy = COALESCE (CreatedBy, 0),"
			+ " Updated = COALESCE (Updated, now()),"
			+ " UpdatedBy = COALESCE (UpdatedBy, 0),"
			+ " I_ErrorMsg = ' ',"
			+ " I_IsImported = 'N' "
			+ "WHERE I_IsImported<>'Y' OR I_IsImported IS NULL");
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.info("Reset=" + no);

		//	Set Client from Name
		sql = new StringBuffer ("UPDATE I_InOutLineConfirm i "
			+ "SET AD_Client_ID=COALESCE (AD_Client_ID,").append (p_AD_Client_ID).append (") "
			+ "WHERE (AD_Client_ID IS NULL OR AD_Client_ID=0)"
			+ " AND I_IsImported<>'Y'");
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.debug("Set Client from Value=" + no);

		//	Error Confirmation Line
		sql = new StringBuffer ("UPDATE I_InOutLineConfirm i "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Confirmation Line, '"
			+ "WHERE (M_InOutLineConfirm_ID IS NULL OR M_InOutLineConfirm_ID=0"
			+ " OR NOT EXISTS (SELECT * FROM M_InOutLineConfirm c WHERE i.M_InOutLineConfirm_ID=c.M_InOutLineConfirm_ID))"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("Invalid InOutLineConfirm=" + no);

		//	Error Confirmation No
		sql = new StringBuffer ("UPDATE I_InOutLineConfirm i "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Missing Confirmation No, '"
			+ "WHERE (ConfirmationNo IS NULL OR ConfirmationNo='')"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("Invalid ConfirmationNo=" + no);
		
		//	Qty
		sql = new StringBuffer ("UPDATE I_InOutLineConfirm i "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Target<>(Confirmed+Difference+Scrapped), ' "
			+ "WHERE EXISTS (SELECT * FROM M_InOutLineConfirm c "
				+ "WHERE i.M_InOutLineConfirm_ID=c.M_InOutLineConfirm_ID"
				+ " AND c.TargetQty<>(i.ConfirmedQty+i.ScrappedQty+i.DifferenceQty))"
			+ " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warn("Invalid Qty=" + no);
		
		commitEx();
		
		/*********************************************************************/
		
		PreparedStatement pstmt = null;
		sql = new StringBuffer ("SELECT * FROM I_InOutLineConfirm "
			+ "WHERE I_IsImported='N'").append (clientCheck)
			.append(" ORDER BY I_InOutLineConfirm_ID");
		no = 0;
		try
		{
			pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				X_I_InOutLineConfirm importLine = new X_I_InOutLineConfirm (getCtx(), rs, get_TrxName());
				MInOutLineConfirm confirmLine = new MInOutLineConfirm (getCtx(), 
					importLine.getM_InOutLineConfirm_ID(), get_TrxName());
				if (confirmLine.get_ID() == 0
					|| confirmLine.get_ID() != importLine.getM_InOutLineConfirm_ID())
				{
					importLine.setI_IsImported(false);
					importLine.setI_ErrorMsg("ID Not Found");
					importLine.save();
				}
				else
				{
					confirmLine.setConfirmationNo(importLine.getConfirmationNo());
					confirmLine.setConfirmedQty(importLine.getConfirmedQty());
					confirmLine.setDifferenceQty(importLine.getDifferenceQty());
					confirmLine.setScrappedQty(importLine.getScrappedQty());
					confirmLine.setDescription(importLine.getDescription());
					if (confirmLine.save())
					{
						//	Import
						importLine.setI_IsImported(true);
						importLine.setProcessed(true);
						if (importLine.save())
							no++;
					}
				}
			}
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(sql.toString(), e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		
		return "@Updated@ #" + no;
	}	//	doIt

}	//	ImportInOutConfirm
