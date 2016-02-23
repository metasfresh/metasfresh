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
import java.util.logging.Level;

import org.compiere.util.DB;

/**
 *	Distribution Run Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MDistributionRun.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MDistributionRun extends X_M_DistributionRun
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4355723603388382287L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_DistributionRun_ID id
	 *	@param trxName transaction
	 */
	public MDistributionRun (Properties ctx, int M_DistributionRun_ID, String trxName)
	{
		super (ctx, M_DistributionRun_ID, trxName);
	}	//	MDistributionRun

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MDistributionRun (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MDistributionRun
	
	/**	 Cached Lines					*/
	private MDistributionRunLine[] 	m_lines = null;
	
	/**
	 * 	Get active, non zero lines
	 *	@param reload true if reload
	 *	@return lines
	 */
	public MDistributionRunLine[] getLines (boolean reload)
	{
		if (!reload && m_lines != null) {
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		//
		String sql = "SELECT * FROM M_DistributionRunLine "
			+ "WHERE M_DistributionRun_ID=? AND IsActive='Y' AND TotalQty IS NOT NULL AND TotalQty<> 0 ORDER BY Line";
		ArrayList<MDistributionRunLine> list = new ArrayList<MDistributionRunLine>();
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt (1, getM_DistributionRun_ID());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new MDistributionRunLine(getCtx(), rs, get_TrxName()));
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
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
		m_lines = new MDistributionRunLine[list.size()];
		list.toArray (m_lines);
		return m_lines;
	}	//	getLines
	
}	//	MDistributionRun
