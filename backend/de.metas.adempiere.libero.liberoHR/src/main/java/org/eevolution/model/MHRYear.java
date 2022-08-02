/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
 * Copyright (C) 2003-2008 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Oscar Gómez  www.e-evolution.com                           *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *****************************************************************************/
package org.eevolution.model;

import de.metas.i18n.Msg;
import org.compiere.model.MPeriod;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 *	MHRYear Year for a Payroll
 *
 *  @author oscar.gomez@e-evolution.com, e-Evolution <a href="http://www.e-evolution.com">...</a>
 *			<li> Original contributor of Payroll Functionality
 *  @author victor.perez@e-evolution.com, e-Evolution <a href="http://www.e-evolution.com">...</a>
 * 			<li> FR [ 2520591 ] Support multiples calendar for Org 
 *			See <a href="http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962">...</a>
 *
 *    @author Cristina Ghita, www.arhipac.ro
 * 			<li> BUG [ 1932959 ]
 * 			See <a href="https://sourceforge.net/tracker/index.php?func=detail&aid=1932959&group_id=176962&atid=934929">...</a>
 */
public class MHRYear extends X_HR_Year
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7789699154024839462L;

	public MHRYear (final Properties ctx, final int HR_Year_ID, final String trxName)
	{
		super (ctx, HR_Year_ID, trxName);
		if (HR_Year_ID == 0)
		{
			setProcessing (false);	// N
		}		
	}	//	HRYear

	@SuppressWarnings("unused")
	public MHRYear (final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

		
	public boolean createPeriods()
	{
		int sumDays;
		final int C_Calendar_ID   = DB.getSQLValueEx(get_TrxName(), "SELECT C_Calendar_ID FROM C_Year WHERE C_Year_ID = ?", getC_Year_ID());
		if (C_Calendar_ID <= 0)
			return false;
		Timestamp StartDate;
		Timestamp EndDate = null ;
		final MHRPayroll payroll = new MHRPayroll(getCtx(), getHR_Payroll_ID(), get_TrxName());
		for (int period = 1; period <= getQty(); period++)
		{
			//arhipac: Cristina Ghita It is need this condition for a good generation periods
			//in case of correspondence between period and month
			if ((12 == getQty())&& (28 == getNetDays() || 29 == getNetDays() || 30 == getNetDays() || 31 == getNetDays()))
			{
				if (period >1)
				{
					StartDate = TimeUtil.addDays(EndDate, 1);
				}
				else 
				{
					StartDate = TimeUtil.addDays(getStartDate(),0);	
				}
				EndDate   = TimeUtil.getMonthLastDay(StartDate);
				
			}	
			else
			{
				sumDays   =  period == 1 ? 0 : (period-1) * (getNetDays()) ;
				StartDate = TimeUtil.addDays(getStartDate(),sumDays);
				EndDate   = TimeUtil.addDays(StartDate,getNetDays()-1);
			}
			final int C_Period_ID     = DB.getSQLValueEx(get_TrxName(),
					"SELECT C_Period_ID FROM C_Period p "
					+ " INNER JOIN C_Year y ON (p.C_Year_ID=y.C_Year_ID) "
					+ " WHERE "
					+ " ? BETWEEN p.startdate AND p.endDate"
					+ " AND y.C_Calendar_ID=?",
					EndDate, C_Calendar_ID);
			if(C_Period_ID <= 0)
				return false;

			final MPeriod m_period = MPeriod.get(getCtx(), C_Period_ID);
			final MHRPeriod HR_Period = new MHRPeriod(getCtx(), 0, get_TrxName());
			HR_Period.setAD_Org_ID(getAD_Org_ID());
			HR_Period.setHR_Year_ID(getHR_Year_ID());
			HR_Period.setHR_Payroll_ID(getHR_Payroll_ID());
			HR_Period.setName(StartDate.toString().substring(0, 10)+" "+Msg.translate(getCtx(), "To")+" "+EndDate.toString().substring(0, 10) );
			HR_Period.setDescription(Msg.translate(getCtx(), "HR_Payroll_ID")+" "+payroll.getName().trim()+" "+Msg.translate(getCtx(), "From")+ " "+period+" " +Msg.translate(getCtx(), "To")+" "+ StartDate.toString().substring(0, 10)+" al "+EndDate.toString().substring(0, 10));
			HR_Period.setPeriodNo(period);
			HR_Period.setC_Period_ID(C_Period_ID);
			HR_Period.setC_Year_ID(m_period.getC_Year_ID());
			HR_Period.setStartDate(StartDate);
			HR_Period.setEndDate(EndDate);
			HR_Period.setDateAcct(EndDate);
			HR_Period.setIsActive(true);
			HR_Period.saveEx();
		}
		return true;
	}	//	createPeriods
}	//	HRYear
