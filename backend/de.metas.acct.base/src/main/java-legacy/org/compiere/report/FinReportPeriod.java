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

import java.sql.Timestamp;

import org.compiere.util.DB;

/**
 *  Financial Report Periods
 *
 *  @author Jorg Janke
 *  @version $Id: FinReportPeriod.java,v 1.3 2006/08/03 22:16:52 jjanke Exp $
 */
public class FinReportPeriod
{
	/**
	 *	Constructor
	 * 	@param C_Period_ID period
	 * 	@param Name name
	 * 	@param StartDate period start date
	 * 	@param EndDate period end date
	 * 	@param YearStartDate year start date
	*/
	public FinReportPeriod (int C_Period_ID, String Name, Timestamp StartDate, Timestamp EndDate,
		Timestamp YearStartDate)
	{
		m_C_Period_ID = C_Period_ID;
		m_Name = Name;
		m_StartDate = StartDate;
		m_EndDate = EndDate;
		m_YearStartDate = YearStartDate;
	}	//

	private int 		m_C_Period_ID;
	private String 		m_Name;
	private Timestamp 	m_StartDate;
	private Timestamp 	m_EndDate;
	private Timestamp 	m_YearStartDate;


	/**
	 * 	Get Period Info
	 * 	@return BETWEEN start AND end
	 */
	public String getPeriodWhere ()
	{
		StringBuffer sql = new StringBuffer ("BETWEEN ");
		sql.append(DB.TO_DATE(m_StartDate))
			.append(" AND ")
			.append(DB.TO_DATE(m_EndDate));
		return sql.toString();
	}	//	getPeriodWhere

	/**
	 * 	Get Year Info
	 * 	@return BETWEEN start AND end
	 */
	public String getYearWhere ()
	{
		StringBuffer sql = new StringBuffer ("BETWEEN ");
		sql.append(DB.TO_DATE(m_YearStartDate))
			  .append(" AND ")
			  .append(DB.TO_DATE(m_EndDate));
		return sql.toString();
	}	//	getPeriodWhere

	/**
	 * 	Get Total Info
	 * 	@return <= end
	 */
	public String getTotalWhere ()
	{
		StringBuffer sql = new StringBuffer ("<= ");
		sql.append(DB.TO_DATE(m_EndDate));
		return sql.toString();
	}	//	getPeriodWhere

	/**
	 * 	Is date in period
	 * 	@param date date
	 * 	@return true if in period
	 */
	public boolean inPeriod (Timestamp date)
	{
		if (date == null)
			return false;
		if (date.before(m_StartDate))
			return false;
		if (date.after(m_EndDate))
			return false;
		return true;
	}	//	inPeriod

	/**
	 * 	Get Name
	 *	@return name
	 */
	public String getName()
	{
		return m_Name;
	}
	/**
	 * 	Get C_Period_ID
	 *	@return period
	 */
	public int getC_Period_ID()
	{
		return m_C_Period_ID;
	}
	/**
	 * 	Get End Date
	 *	@return end date
	 */
	public Timestamp getEndDate()
	{
		return m_EndDate;
	}
	/**
	 * 	Get Start Date
	 *	@return start date
	 */
	public Timestamp getStartDate()
	{
		return m_StartDate;
	}
	/**
	 * 	Get Year Start Date
	 *	@return year start date
	 */
	public Timestamp getYearStartDate()
	{
		return m_YearStartDate;
	}

	/**
	 * Get natural balance dateacct filter
	 * @param alias table name or alias name
	 * @return is balance sheet a/c and <= end or BETWEEN start AND end 
	 */
	public String getNaturalWhere(String alias) {
		String yearWhere = getYearWhere();
		String totalWhere = getTotalWhere();
		String bs = " EXISTS (SELECT C_ElementValue_ID FROM C_ElementValue WHERE C_ElementValue_ID = " + alias + ".Account_ID AND AccountType NOT IN ('R', 'E'))";
		String full = totalWhere + " AND ( " + bs + " OR TRUNC(" + alias + ".DateAcct) " + yearWhere + " ) ";
		
		return full;
	}

}	//	FinReportPeriod
