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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.compiere.model.MAllocationHdr;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

/**
 *	Reset (delete) Allocations	
 *	
 *  @author Jorg Janke
 *  @version $Id: AllocationReset.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class AllocationReset extends SvrProcess
{
	/** BP Group				*/
	private int			p_C_BP_Group_ID = 0;
	/** BPartner				*/
	private int			p_C_BPartner_ID = 0;
	/** Date Acct From			*/
	private Timestamp	p_DateAcct_From = null;
	/** Date Acct To			*/
	private Timestamp	p_DateAcct_To = null;
	/** Allocation directly		*/
	private int			p_C_AllocationHdr_ID = 0;
	/** Transaction				*/
	private Trx			m_trx = null;
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			log.debug("prepare - " + para[i]);
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_BP_Group_ID"))
				p_C_BP_Group_ID = para[i].getParameterAsInt();
			else if (name.equals("C_BPartner_ID"))
				p_C_BPartner_ID = para[i].getParameterAsInt();
			else if (name.equals("C_AllocationHdr_ID"))
				p_C_AllocationHdr_ID = para[i].getParameterAsInt();
			else if (name.equals("DateAcct"))
			{
				p_DateAcct_From = (Timestamp)para[i].getParameter();
				p_DateAcct_To = (Timestamp)para[i].getParameter_To();
			}
			else
				log.error("Unknown Parameter: " + name);
		}
	}	//	prepare
	
	/**
	 * 	Process
	 *	@return message
	 *	@throws Exception
	 */
	protected String doIt() throws Exception
	{
		log.info("C_BP_Group_ID=" + p_C_BP_Group_ID + ", C_BPartner_ID=" + p_C_BPartner_ID
			+ ", DateAcct= " + p_DateAcct_From + " - " + p_DateAcct_To
			+ ", C_AllocationHdr_ID=" + p_C_AllocationHdr_ID);

		m_trx = Trx.get(Trx.createTrxName("AllocReset"), true);
		int count = 0;

		if (p_C_AllocationHdr_ID != 0)
		{
			MAllocationHdr hdr = new MAllocationHdr(getCtx(), p_C_AllocationHdr_ID, m_trx.getTrxName());
			if (delete(hdr))
				count++;
			m_trx.close();
			return "@Deleted@ #" + count;
		}
		
		//	Selection
		StringBuffer sql = new StringBuffer("SELECT * FROM C_AllocationHdr ah "
			+ "WHERE EXISTS (SELECT * FROM C_AllocationLine al "
				+ "WHERE ah.C_AllocationHdr_ID=al.C_AllocationHdr_ID");
		if (p_C_BPartner_ID != 0)
			sql.append(" AND al.C_BPartner_ID=?");
		else if (p_C_BP_Group_ID != 0)
			sql.append(" AND EXISTS (SELECT * FROM C_BPartner bp "
					+ "WHERE bp.C_BPartner_ID=al.C_BPartner_ID AND bp.C_BP_Group_ID=?)");
		else
			sql.append(" AND AD_Client_ID=?");
		if (p_DateAcct_From != null)
			sql.append(" AND TRIM(ah.DateAcct) >= ?");
		if (p_DateAcct_To != null)
			sql.append(" AND TRIM(ah.DateAcct) <= ?");
		//	Do not delete Cash Trx
		sql.append(" AND al.C_CashLine_ID IS NULL)");
		//	Open Period
		sql.append(" AND EXISTS (SELECT * FROM C_Period p"
			+ " INNER JOIN C_PeriodControl pc ON (p.C_Period_ID=pc.C_Period_ID AND pc.DocBaseType='CMA') "
			+ "WHERE ah.DateAcct BETWEEN p.StartDate AND p.EndDate)");
		//
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql.toString(), m_trx.getTrxName());
			int index = 1;
			if (p_C_BPartner_ID != 0)
				pstmt.setInt(index++, p_C_BPartner_ID);
			else if (p_C_BP_Group_ID != 0)
				pstmt.setInt(index++, p_C_BP_Group_ID);
			else
				pstmt.setInt(index++, Env.getAD_Client_ID(getCtx()));
			if (p_DateAcct_From != null)
				pstmt.setTimestamp(index++, p_DateAcct_From);
			if (p_DateAcct_To != null)
				pstmt.setTimestamp(index++, p_DateAcct_To);
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				MAllocationHdr hdr = new MAllocationHdr(getCtx(), rs, m_trx.getTrxName());
				if (delete(hdr))
					count++;
			}
 		}
		catch (Exception e)
		{
			log.error(sql.toString(), e);
			m_trx.rollback();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		m_trx.close();
		return "@Deleted@ #" + count;
	}	//	doIt

	
	private boolean delete(MAllocationHdr hdr)
	{
	//	m_trx.start();
		boolean success = false;
		if (hdr.delete(true, m_trx.getTrxName()))
		{
			log.debug(hdr.toString());
			success = true;
		}
		if (success)
			success = m_trx.commit();
		else
			m_trx.rollback();
		return success;
	}	//	delete
	
	
	/**
	 * 	Set BPartner (may not be required
	 */
	private void setBPartner()
	{
		/**
		UPDATE C_AllocationLine al
		SET C_BPartner_ID=(SELECT C_BPartner_ID FROM C_Payment p WHERE al.C_Payment_ID=p.C_Payment_ID)
		WHERE C_BPartner_ID IS NULL AND C_Payment_ID IS NOT NULL;
		UPDATE C_AllocationLine al
		SET C_BPartner_ID=(SELECT C_BPartner_ID FROM C_Invoice i WHERE al.C_Invoice_ID=i.C_Invoice_ID)
		WHERE C_BPartner_ID IS NULL AND C_Invoice_ID IS NOT NULL;
		UPDATE C_AllocationLine al
		SET C_BPartner_ID=(SELECT C_BPartner_ID FROM C_Order o WHERE al.C_Order_ID=o.C_Order_ID)
		WHERE C_BPartner_ID IS NULL AND C_Order_ID IS NOT NULL;
		COMMIT
		**/
	}	//	setBPartner

}	//	AllocationReset
