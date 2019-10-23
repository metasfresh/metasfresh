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
package org.compiere.report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import org.compiere.model.X_PA_ReportLineSet;
import org.compiere.util.DB;


/**
 *	Report Line Set Model
 *
 *  @author Jorg Janke
 *  @version $Id: MReportLineSet.java,v 1.3 2006/08/03 22:16:52 jjanke Exp $
 */
public class MReportLineSet extends X_PA_ReportLineSet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2540937629148624046L;

	/**
	 * 	Constructor
	 * 	@param ctx context
	 * 	@param PA_ReportLineSet_ID id
	 * 	@param trxName transaction
	 */
	public MReportLineSet (Properties ctx, int PA_ReportLineSet_ID, String trxName)
	{
		super (ctx, PA_ReportLineSet_ID, trxName);
		if (PA_ReportLineSet_ID == 0)
		{
		}
		else
			loadLines();
	}	//	MReportLineSet

	/**	Contained Lines			*/
	private MReportLine[]	m_lines = null;

	/**
	 * 	Load Lines
	 */
	private void loadLines()
	{
		ArrayList<MReportLine> list = new ArrayList<MReportLine>();
		String sql = "SELECT * FROM PA_ReportLine "
			+ "WHERE PA_ReportLineSet_ID=? AND IsActive='Y' "
			+ "ORDER BY SeqNo";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getPA_ReportLineSet_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				MReportLine line = new MReportLine (getCtx(), rs, get_TrxName());
				list.add(line);
				line.setParent(this);
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			try
			{
				if (pstmt != null)
					pstmt.close ();
			}
			catch (Exception e)
			{}
			pstmt = null;
		}
		//
		m_lines = new MReportLine[list.size()];
		list.toArray(m_lines);
		log.trace("ID=" + getPA_ReportLineSet_ID()
			+ " - Size=" + list.size());
	}	//	loadColumns

	/**
	 * 	Get Liness
	 *	@return array of lines
	 */
	public MReportLine[] getLiness()
	{
		return m_lines;
	}	//	getLines

	/**
	 * 	List Info
	 */
	public void list()
	{
		System.out.println(toString());
		if (m_lines == null)
			return;
		for (int i = 0; i < m_lines.length; i++)
			m_lines[i].list();
	}	//	list

	/**
	 * 	String representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MReportLineSet[")
			.append(get_ID()).append(" - ").append(getName())
			.append ("]");
		return sb.toString ();
	}

}	//	MReportLineSet
