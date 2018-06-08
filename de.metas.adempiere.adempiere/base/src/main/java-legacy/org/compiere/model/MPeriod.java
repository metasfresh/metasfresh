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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.acct.api.IAcctSchemaDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.PeriodClosedException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.IOrgDAO;
import org.adempiere.util.Services;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.adempiere.service.ICalendarBL;
import de.metas.adempiere.service.IPeriodBL;
import de.metas.adempiere.service.IPeriodDAO;
import de.metas.logging.LogManager;

/**
 *  Calendar Period Model
 *
 *	@author Jorg Janke
 *	@version $Id: MPeriod.java,v 1.4 2006/07/30 00:51:05 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 				<li>BF [ 1779438 ] Minor auto period control bug
 * 				<li>BF [ 1893486 ] Auto Period Control return that period is always open
 *
 *  @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * 			<li> FR [ 2520591 ] Support multiples calendar for Org 
 *			@see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962 
 */
public class MPeriod extends X_C_Period
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4342181292848531751L;


	/**
	 * Get Period from Cache
	 * @param ctx context
	 * @param C_Period_ID id
	 * @return MPeriod
	 */
	public static MPeriod get (Properties ctx, int C_Period_ID)
	{
		if (C_Period_ID <= 0)
			return null;
		//
		Integer key = new Integer(C_Period_ID);
		MPeriod retValue = s_cache.get (key);
		if (retValue != null)
			return retValue;
		//
		retValue = new MPeriod (ctx, C_Period_ID, null);
		if (retValue.get_ID () != 0)
			s_cache.put (key, retValue);
		return retValue;
	} 	//	get

	/**
	 * 	Find standard Period of DateAcct based on Client Calendar
	 *	@param ctx context
	 *	@param DateAcct date
	 *	@return active Period or null
	 *  @deprecated
	 */
	@Deprecated
	public static MPeriod get (Properties ctx, Timestamp DateAcct)
	{	
		return get(ctx, DateAcct, 0);
	}	//	get
	
	/**
	 * Find standard Period of DateAcct based on Client Calendar
	 * @param ctx context
	 * @param DateAcct date
	 * @param AD_Org_ID Organization
	 * @return active Period or null
	 */
	public static MPeriod get (Properties ctx, Timestamp DateAcct, int AD_Org_ID)
	{
		if (DateAcct == null)
		{
			return null;
		}
		int C_Calendar_ID = getC_Calendar_ID(ctx, AD_Org_ID);
        
        return findByCalendar(ctx, DateAcct, C_Calendar_ID, ITrx.TRXNAME_None);
	}	//	get

	/**
	 * 
	 * @param ctx
	 * @param DateAcct
	 * @param C_Calendar_ID
	 * @param trxName
	 * @return MPeriod
	 */
	public static MPeriod findByCalendar(Properties ctx, Timestamp DateAcct, int C_Calendar_ID, String trxName)
	{
		final IPeriodBL periodBL = Services.get(IPeriodBL.class);
		final ICalendarBL calendarBL = Services.get(ICalendarBL.class);
		
		final int AD_Client_ID = Env.getAD_Client_ID(ctx);
		//	Search in Cache first
		Iterator<MPeriod> it = s_cache.values().iterator();
		while (it.hasNext())
		{
			MPeriod period = it.next();
			if (period.getC_Calendar_ID() == C_Calendar_ID
					&& calendarBL.isStandardPeriod(period)
					&& periodBL.isInPeriod(period, DateAcct) 
					&& period.getAD_Client_ID() == AD_Client_ID)  // globalqss - CarlosRuiz - Fix [ 1820810 ] Wrong Period Assigned to Fact_Acct
				return period;
		}
		
		//	Get it from DB
	    MPeriod retValue = null;
		String sql = "SELECT * "
			+ "FROM C_Period "
			+ "WHERE C_Year_ID IN "
				+ "(SELECT C_Year_ID FROM C_Year WHERE C_Calendar_ID= ?)"
			+ " AND ? BETWEEN TRUNC(StartDate) AND TRUNC(EndDate)"
			+ " AND IsActive=? AND PeriodType=?";
        
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
            pstmt.setInt (1, C_Calendar_ID);
            pstmt.setTimestamp (2, TimeUtil.getDay(DateAcct));
            pstmt.setString(3, "Y");
            pstmt.setString(4, "S");
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				MPeriod period = new MPeriod(ctx, rs, trxName);
				Integer key = new Integer(period.getC_Period_ID());
				s_cache.put (key, period);
				if (calendarBL.isStandardPeriod(period))
				{
					retValue = period;
				}
			}
		}
		catch (SQLException e)
		{
			s_log.error("DateAcct=" + DateAcct, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		if (retValue == null)
			s_log.info("No Standard Period for " + DateAcct 
				+ " (AD_Client_ID=" + AD_Client_ID + ")");
		return retValue;
	}

	/**
	 * 	Find valid standard Period of DateAcct based on Client Calendar
	 *	@param ctx context
	 *	@param DateAcct date
	 *	@return C_Period_ID or 0
	 *  @deprecated
	 */
	@Deprecated
	public static int getC_Period_ID (Properties ctx, Timestamp DateAcct)
	{
		MPeriod period = get (ctx, DateAcct);
		if (period == null)
			return 0;
		return period.getC_Period_ID();
	}	//	getC_Period_ID
	
	/**
	 * 	Find valid standard Period of DateAcct based on Client Calendar
	 *	@param ctx context
	 * @param DateAcct date
	 * @param AD_Org_ID Organization
	 *	@return C_Period_ID or 0
	 */
	public static int getC_Period_ID (Properties ctx, Timestamp DateAcct, int AD_Org_ID)
	{
		MPeriod period = get (ctx, DateAcct, AD_Org_ID);
		if (period == null)
			return 0;
		return period.getC_Period_ID();
	}	//	getC_Period_ID

	/**
	 * 	Is standard Period Open for Document Base Type
	 *	@param ctx context
	 *	@param DateAcct date
	 *	@param DocBaseType base type
	 *	@return true if open
	 *  @deprecated
	 */
	@Deprecated
	public static boolean isOpen (Properties ctx, Timestamp DateAcct, String DocBaseType)
	{
		return isOpen(ctx, DateAcct,DocBaseType, 0 );
	}	//	isOpen
	
	/**
	 * 	Is standard Period Open for Document Base Type
	 *	@param ctx context
	 * @param DateAcct date
	 * @param DocBaseType base type
	 * @param AD_Org_ID Organization
	 * @return true if open
	 */
	public static boolean isOpen (Properties ctx, Timestamp DateAcct, String DocBaseType, int AD_Org_ID)
	{
		if (DateAcct == null)
		{
			s_log.warn("No DateAcct");
			return false;
		}
		if (DocBaseType == null)
		{
			s_log.warn("No DocBaseType");
			return false;
		}
		MPeriod period = MPeriod.get (ctx, DateAcct, AD_Org_ID);
		if (period == null)
		{
			s_log.warn("No Period for " + DateAcct + " (" + DocBaseType + ")");
			return false;
		}
		boolean open = period.isOpen(DocBaseType, DateAcct, AD_Org_ID);
		if (!open)
			s_log.warn(period.getName()
				+ ": Not open for " + DocBaseType + " (" + DateAcct + ")");
		return open;
	}	//	isOpen

	/**
	 * 	Find first Year Period of DateAcct based on Client Calendar
	 *	@param ctx context
	 *	@param DateAcct date
	 *	@return active first Period
	 *  @deprecated
	 */
	@Deprecated
	public static MPeriod getFirstInYear (Properties ctx, Timestamp DateAcct)
	{
		return getFirstInYear(ctx , DateAcct, 0);
	}	//	getFirstInYear
		
	/**
	 * 	Find first Year Period of DateAcct based on Client Calendar
	 *	@param ctx context
	 * @param DateAcct date
	 * @param AD_Org_ID TODO
	 *	@return active first Period
	 */
	public static MPeriod getFirstInYear (Properties ctx, Timestamp DateAcct, int AD_Org_ID)
	{
		MPeriod retValue = null;
		int C_Calendar_ID = MPeriod.get(ctx, DateAcct, AD_Org_ID).getC_Calendar_ID();

        String sql = "SELECT * "
                    + "FROM C_Period "
                    + "WHERE C_Year_ID IN "
                    + "(SELECT p.C_Year_ID "
                    + "FROM C_Year y"
                    + " INNER JOIN C_Period p ON (y.C_Year_ID=p.C_Year_ID) "
                    + "WHERE y.C_Calendar_ID=?"
                    + "     AND ? BETWEEN StartDate AND EndDate)"
                    + " AND IsActive=? AND PeriodType=? "
                    + "ORDER BY StartDate";
        
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt (1, C_Calendar_ID);
			pstmt.setTimestamp (2, DateAcct);
			pstmt.setString (3, "Y");
			pstmt.setString (4, "S");
			rs = pstmt.executeQuery();
			if (rs.next())	//	first only
				retValue = new MPeriod(ctx, rs, null);
		}
		catch (SQLException e)
		{
			s_log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return retValue;
	}	//	getFirstInYear

	/**	Cache							*/
	private static CCache<Integer,MPeriod> s_cache = new CCache<Integer,MPeriod>("C_Period", 10);
	
	/**	Logger							*/
	private static Logger			s_log = LogManager.getLogger(MPeriod.class); 
	
	/** Calendar 					   */
	private int 					m_C_Calendar_ID = 0;
	
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_Period_ID id
	 *	@param trxName transaction
	 */
	public MPeriod (Properties ctx, int C_Period_ID, String trxName)
	{
		super (ctx, C_Period_ID, trxName);
		if (C_Period_ID == 0)
		{
		//	setC_Period_ID (0);		//	PK
		//  setC_Year_ID (0);		//	Parent
		//  setName (null);
		//  setPeriodNo (0);
		//  setStartDate (new Timestamp(System.currentTimeMillis()));
			setPeriodType (PERIODTYPE_StandardCalendarPeriod);
		}
	}	//	MPeriod

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MPeriod (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MPeriod

	/**
	 * 	Parent constructor
	 *	@param year year
	 *	@param PeriodNo no
	 *	@param name name
	 *	@param startDate start
	 *	@param endDate end
	 */
	public MPeriod (MYear year, int PeriodNo, String name, 
		Timestamp startDate,Timestamp endDate)
	{
		this (year.getCtx(), 0, year.get_TrxName());
		setClientOrg(year);
		setC_Year_ID(year.getC_Year_ID());
		setPeriodNo(PeriodNo);
		setName(name);
		setStartDate(startDate);
		setEndDate(endDate);
	}	//	MPeriod
	
	/**
	 * Get Period Control
	 *
	 * @param DocBaseType Document Base Type
	 * @return period control or null
	 */
	private I_C_PeriodControl getPeriodControl(final String DocBaseType)
	{
		if (DocBaseType == null)
		{
			return null;
		}
		
		return Services.get(IPeriodDAO.class)
				.retrievePeriodControlsByDocBaseType(getCtx(), getC_Period_ID())
				.get(DocBaseType);
	}

	/**
	 * Is Period Open for Doc Base Type. The check includes <code>Period_OpenHistory</code> and <code>Period_OpenFuture</code> from the given <code>ad_Org_ID</code>'s accounting schema.
	 * 
	 * @param DocBaseType document base type
	 * @param dateAcct date; Applies only for "Auto Period Control": <li>if not null, date should be in auto period range (today - OpenHistory, today+OpenHistory) <li>if null, this period should be in
	 *            auto period range
	 * @param ad_Org_ID for which we shall retrieve the accounting schema.
	 * @return true if open
	 * @since 3.3.1b
	 */
	public boolean isOpen (final String DocBaseType, final Timestamp dateAcct, final int ad_Org_ID)
	{
		if (!isActive())
		{
			s_log.warn("Period not active: " + getName());
			return false;
		}
		
		DB.saveConstraints();
		try
		{
			DB.getConstraints().addAllowedTrxNamePrefix("POSave").incMaxTrx(1);
		
			// MAcctSchema as = MClient.get(getCtx(), getAD_Client_ID()).getAcctSchema();
			final I_C_AcctSchema as = Services.get(IAcctSchemaDAO.class).retrieveAcctSchema(getCtx(), getAD_Client_ID(), ad_Org_ID);
			if (as != null && as.isAutoPeriodControl())
			{
				Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
				Timestamp first = TimeUtil.addDays(today, - as.getPeriod_OpenHistory()); 
				Timestamp last = TimeUtil.addDays(today, as.getPeriod_OpenFuture());
				Timestamp date1, date2;
				if (dateAcct != null)
				{
					date1 = TimeUtil.trunc(dateAcct, TimeUtil.TRUNC_DAY);
					date2 = date1;
				}
				else
				{
					date1 = getStartDate();
					date2 = getEndDate();
				}
				//
				if (date1.before(first))
				{
					log.warn("Automatic Period Control:" + date1 + " before first day - " + first);
					return false;
				}
				if (date2.after(last))
				{
					log.warn("Automatic Period Control:" + date2 + " after last day - " + last);
					return false;
				}
				//	We are OK
				if (Services.get(IPeriodBL.class).isInPeriod(this, today))
				{
					as.setC_Period_ID(getC_Period_ID());
					InterfaceWrapperHelper.save(as);
				}
				return true;
			}
		
		}
		finally
		{
			DB.restoreConstraints();
		}

		//
		// Check standard Period Control
		if (DocBaseType == null)
		{
			log.warn(getName() + " - No DocBaseType");
			return false;
		}
		final I_C_PeriodControl pc = getPeriodControl(DocBaseType);
		if (pc == null)
		{
			log.warn(getName() + " - Period Control not found for " + DocBaseType);
			return false;
		}
		return X_C_PeriodControl.PERIODSTATUS_Open.equals(pc.getPeriodStatus());
	}	//	isOpen

	/**
	 * 	Before Save.
	 * 	Truncate Dates
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		//	Truncate Dates
		Timestamp date = getStartDate(); 
		if (date != null)
			setStartDate(TimeUtil.getDay(date));
		else
			return false;
		//
		date = getEndDate();
		if (date != null)
			setEndDate(TimeUtil.getDay(date));
		else
			setEndDate(TimeUtil.getMonthLastDay(getStartDate()));
		
		if (getEndDate().before(getStartDate()))
		{
			SimpleDateFormat df = DisplayType.getDateFormat(DisplayType.Date);
			throw new AdempiereException(df.format(getEndDate()) + " < " + df.format(getStartDate()));
		}
		
		MYear year = new MYear(getCtx(), getC_Year_ID(), get_TrxName());
		
		Query query = MTable.get(getCtx(), "C_Period")
		.createQuery("C_Year_ID IN (SELECT y.C_Year_ID from C_Year y WHERE" +
				"                   y.C_Calendar_ID =?)" +
				" AND (? BETWEEN StartDate AND EndDate" +
				" OR ? BETWEEN StartDate AND EndDate)" +
				" AND PeriodType=?",get_TrxName());
		query.setParameters(new Object[] {year.getC_Calendar_ID(),
				getStartDate(), getEndDate(),
				getPeriodType()});
		
		List<MPeriod> periods = query.list();
		
		for ( int i=0; i < periods.size(); i++)
		{
			if ( periods.get(i).getC_Period_ID() != getC_Period_ID() )
			{
				throw new AdempiereException("Period overlaps with: "	+ periods.get(i).getName());
			}
		}
		
		return true;
	}	//	beforeSave
	
	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return success
	 */
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (newRecord)
		{
			Services.get(IPeriodBL.class).createPeriodControls(this);
		}
		return success;
	}	//	afterSave
	
	
	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("MPeriod[");
		sb.append(get_ID())
				.append("-").append(getName())
				.append(", ").append(getStartDate()).append("-").append(getEndDate())
				.append("]");
		return sb.toString();
	}	// toString
	
	/**
	 * Conventient method for testing if a period is open
	 * @param ctx
	 * @param dateAcct
	 * @param docBaseType
	 * @throws PeriodClosedException if period is closed
	 * @see #isOpen(Properties, Timestamp, String)
	 * @deprecated
	 */
	@Deprecated
	public static void testPeriodOpen(Properties ctx, Timestamp dateAcct, String docBaseType)
	throws PeriodClosedException 
	{
		if (!MPeriod.isOpen(ctx, dateAcct, docBaseType)) {
			throw new PeriodClosedException(dateAcct, docBaseType);
		}
	}
	
	/**
	 * Conventient method for testing if a period is open
	 * @param ctx
	 * @param dateAcct
	 * @param docBaseType
	 * @param AD_Org_ID Organization
	 * @throws PeriodClosedException if period is closed
	 * @see #isOpen(Properties, Timestamp, String, int)
	 */
	public static void testPeriodOpen(Properties ctx, Timestamp dateAcct, String docBaseType, int AD_Org_ID)
	throws PeriodClosedException 
	{
		if (!MPeriod.isOpen(ctx, dateAcct, docBaseType, AD_Org_ID)) {
			throw new PeriodClosedException(dateAcct, docBaseType);
		}
	}
	
		/**
	 * Conventient method for testing if a period is open
	 * @param ctx
	 * @param dateAcct
	 * @param C_DocType_ID
	 * @throws PeriodClosedException
	 * @see {@link #isOpen(Properties, Timestamp, String)}
     * @deprecated
	 */
	@Deprecated
	public static void testPeriodOpen(Properties ctx, Timestamp dateAcct, int C_DocType_ID)
	throws PeriodClosedException
	{
		MDocType dt = MDocType.get(ctx, C_DocType_ID);
		testPeriodOpen(ctx, dateAcct, dt.getDocBaseType());
	}
	
	/**
	 * Conventient method for testing if a period is open
	 * @param ctx
	 * @param dateAcct
	 * @param C_DocType_ID
	 * @param AD_Org_ID Organization
	 * @throws PeriodClosedException
	 * @see {@link #isOpen(Properties, Timestamp, String, int)}
	 */
	public static void testPeriodOpen(Properties ctx, Timestamp dateAcct, int C_DocType_ID, int AD_Org_ID)
	throws PeriodClosedException
	{
		MDocType dt = MDocType.get(ctx, C_DocType_ID);
		testPeriodOpen(ctx, dateAcct, dt.getDocBaseType(),  AD_Org_ID);
	}
	
	/**
	 *  Get Calendar of Period
	 *  @return calendar
	 */
	public int getC_Calendar_ID()
	{
		if (m_C_Calendar_ID == 0)
		{
			MYear year = (MYear) getC_Year();
			if (year != null)
				m_C_Calendar_ID = year.getC_Calendar_ID();
			else
				log.error("@NotFound@ C_Year_ID=" + getC_Year_ID());
		}
		return m_C_Calendar_ID;
	}   //  getC_Calendar_ID
    
	/**
	 * Get Calendar for Organization
	 * @param ctx Context
	 * @param AD_Org_ID Organization
	 * @return
	 */
    public static int getC_Calendar_ID(final Properties ctx, final int AD_Org_ID)
    {	
        int C_Calendar_ID = 0;
        if (AD_Org_ID > 0)
        {
            I_AD_OrgInfo info = Services.get(IOrgDAO.class).retrieveOrgInfo(ctx, AD_Org_ID, ITrx.TRXNAME_None);
            C_Calendar_ID = info.getC_Calendar_ID();
        }
        
        if (C_Calendar_ID <= 0)
        {
            I_AD_ClientInfo cInfo = Services.get(IClientDAO.class).retrieveClientInfo(ctx);
            C_Calendar_ID = cInfo.getC_Calendar_ID();
        }
        
      return C_Calendar_ID;
    }   //  getC_Calendar_ID
    
}	//	MPeriod
