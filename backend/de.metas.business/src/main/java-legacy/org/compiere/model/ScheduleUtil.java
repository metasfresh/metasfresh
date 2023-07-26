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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.i18n.Language;
import de.metas.i18n.Msg;
import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissions;
import de.metas.security.permissions.Access;
import de.metas.uom.LegacyUOMConversionUtils;

/**
 *	Scheduling Utilities.
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: ScheduleUtil.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class ScheduleUtil
{
	/**
	 *	Constructor
	 *  @param ctx context
	 */
	public ScheduleUtil (Properties ctx)
	{
		m_ctx = ctx;
	}	//	MSchedule

	private Properties 	m_ctx;
	private int			m_S_Resource_ID;
	private boolean 	m_isAvailable = true;
	private boolean 	m_isSingleAssignment = true;
	private int 		m_S_ResourceType_ID = 0;
	private int 		m_C_UOM_ID = 0;

	private Timestamp	m_startDate = null;
	private Timestamp	m_endDate = null;

	/**	Resource Type Name			*/
	private String		m_typeName = null;
	/**	Resource Type Start Time	*/
	private Timestamp	m_slotStartTime = null;
	/** Resource Type End Time		*/
	private Timestamp	m_slotEndTime = null;

	/**	Time Slots						*/
	private	MAssignmentSlot[] 	m_timeSlots = null;

	/**	Begin Timestamp		1/1/1970			*/
	public static final Timestamp	EARLIEST = new Timestamp(new GregorianCalendar(1970,Calendar.JANUARY,1).getTimeInMillis());
	/**	End Timestamp		12/31/2070			*/
	public static final Timestamp	LATEST = new Timestamp(new GregorianCalendar(2070,Calendar.DECEMBER,31).getTimeInMillis());

	/**	Logger			*/
	private static Logger log = LogManager.getLogger(ScheduleUtil.class);
	
	
	/**************************************************************************
	 * 	Get Assignments for timeframe.
	 *  <pre>
	 * 		- Resource is Active and Available
	 * 		- Resource UnAvailability
	 * 		- NonBusinessDay
	 * 		- ResourceType Available
	 *  </pre>
	 *  @param S_Resource_ID resource
	 *  @param start_Date start date
	 *  @param end_Date optional end date, need to provide qty to calculate it
	 *  @param qty optional qty in ResourceType UOM - ignored, if end date is not null
	 *  @param getAll if true return all errors
	 *	@param trxName transaction
	 *  @return Array of existing Assignments or null - if free
	 */
	@SuppressWarnings("unchecked")
	public MAssignmentSlot[] getAssignmentSlots (int S_Resource_ID,
		Timestamp start_Date, Timestamp end_Date,
		BigDecimal qty, boolean getAll, String trxName)
	{
		log.info(start_Date.toString());
		if (m_S_Resource_ID != S_Resource_ID)
			getBaseInfo (S_Resource_ID);
		//
		ArrayList<MAssignmentSlot> list = new ArrayList<MAssignmentSlot>();
		MAssignmentSlot ma = null;

		if (!m_isAvailable)
		{
			ma = new MAssignmentSlot (EARLIEST, LATEST,
				Msg.getMsg (m_ctx, "ResourceNotAvailable"), "", MAssignmentSlot.STATUS_NotAvailable);
			if (!getAll)
				return new MAssignmentSlot[] {ma};
			list.add(ma);
		}

		m_startDate = start_Date;
		m_endDate = end_Date;
		if (m_endDate == null)
			m_endDate = computeEndDate(m_ctx, m_startDate, m_C_UOM_ID, qty);
		log.debug( "- EndDate=" + m_endDate);


		//	Resource Unavailability -------------------------------------------
	//	log.debug( "- Unavailability -");
		String sql = "SELECT Description, DateFrom, DateTo "
		  + "FROM S_ResourceUnavailable "
		  + "WHERE S_Resource_ID=?"					//	#1
		  + " AND DateTo >= ?"						//	#2	start
		  + " AND DateFrom <= ?"					//	#3	end
		  + " AND IsActive='Y'";
		try
		{
	//		log.debug( sql, "ID=" + S_Resource_ID + ", Start=" + m_startDate + ", End=" + m_endDate);
			PreparedStatement pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, m_S_Resource_ID);
			pstmt.setTimestamp(2, m_startDate);
			pstmt.setTimestamp(3, m_endDate);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				ma = new MAssignmentSlot (TimeUtil.getDay(rs.getTimestamp(2)),
					TimeUtil.getNextDay(rs.getTimestamp(3)),	//	user entered date need to convert to not including end time
					Msg.getMsg (m_ctx, "ResourceUnAvailable"), rs.getString(1),
					MAssignmentSlot.STATUS_UnAvailable);
			//	log.debug( "- Unavailable", ma);
				if (getAll)
					createDaySlot (list, ma);
				else
					list.add(ma);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
			ma = new MAssignmentSlot (EARLIEST, LATEST,
				Msg.getMsg (m_ctx, "ResourceUnAvailable"), e.toString(),
				MAssignmentSlot.STATUS_UnAvailable);
		}
		if (ma != null && !getAll)
			return new MAssignmentSlot[] {ma};


		//	NonBusinessDay ----------------------------------------------------
	//	log.debug( "- NonBusinessDay -");
		//	"WHERE TRUNC(Date1) BETWEEN TRUNC(?) AND TRUNC(?)"   causes
		//	ORA-00932: inconsistent datatypes: expected NUMBER got TIMESTAMP
		sql = Env.getUserRolePermissions(m_ctx).addAccessSQL(
			"SELECT Name, Date1 FROM C_NonBusinessDay "
			+ "WHERE TRUNC(Date1) BETWEEN ? AND ?",
			"C_NonBusinessDay",
			IUserRolePermissions.SQL_NOTQUALIFIED,
			Access.READ);	// not qualified - RO
		try
		{
			Timestamp startDay = TimeUtil.getDay(m_startDate);
			Timestamp endDay = TimeUtil.getDay(m_endDate);
	//		log.debug( sql, "Start=" + startDay + ", End=" + endDay);
			PreparedStatement pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setTimestamp(1, startDay);
			pstmt.setTimestamp(2, endDay);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				ma = new MAssignmentSlot (TimeUtil.getDay(rs.getTimestamp(2)),
					TimeUtil.getNextDay(rs.getTimestamp(2)),	//	user entered date need to convert to not including end time
					Msg.getMsg(m_ctx, "NonBusinessDay"), rs.getString(1),
					MAssignmentSlot.STATUS_NonBusinessDay);
				log.trace("- NonBusinessDay " + ma);
				list.add(ma);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
			ma = new MAssignmentSlot (EARLIEST, LATEST,
				Msg.getMsg(m_ctx, "NonBusinessDay"), e.toString(),
				MAssignmentSlot.STATUS_NonBusinessDay);
		}
		if (ma != null && !getAll)
			return new MAssignmentSlot[] {ma};


		//	ResourceType Available --------------------------------------------
	//	log.debug( "- ResourceTypeAvailability -");
		sql = "SELECT Name, IsTimeSlot,TimeSlotStart,TimeSlotEnd, "	//	1..4
			+ "IsDateSlot,OnMonday,OnTuesday,OnWednesday,"			//	5..8
			+ "OnThursday,OnFriday,OnSaturday,OnSunday "			//	9..12
			+ "FROM S_ResourceType "
			+ "WHERE S_ResourceType_ID=?";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, m_S_ResourceType_ID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				m_typeName = rs.getString(1);
				//	TimeSlot
				if ("Y".equals(rs.getString(2)))
				{
					m_slotStartTime = TimeUtil.getDayTime (m_startDate, rs.getTimestamp(3));
					m_slotEndTime = TimeUtil.getDayTime (m_endDate, rs.getTimestamp(4));
					if (TimeUtil.inRange(m_startDate, m_endDate, m_slotStartTime, m_slotEndTime))
					{
						ma = new MAssignmentSlot (m_slotStartTime, m_slotEndTime,
							Msg.getMsg(m_ctx, "ResourceNotInSlotTime"), m_typeName,
							MAssignmentSlot.STATUS_NotInSlotTime);
						if (getAll)
							createTimeSlot (list,
								rs.getTimestamp(3), rs.getTimestamp(4));
					}
				}	//	TimeSlot

				//	DaySlot
				if ("Y".equals(rs.getString(5)))
				{
					if (TimeUtil.inRange(m_startDate, m_endDate,
						"Y".equals(rs.getString(6)), "Y".equals(rs.getString(7)), 				//	Mo..Tu
						"Y".equals(rs.getString(8)), "Y".equals(rs.getString(9)), "Y".equals(rs.getString(10)),	//  We..Fr
						"Y".equals(rs.getString(11)), "Y".equals(rs.getString(12))))
					{
						ma = new MAssignmentSlot (m_startDate, m_endDate,
							Msg.getMsg(m_ctx, "ResourceNotInSlotDay"), m_typeName,
							MAssignmentSlot.STATUS_NotInSlotDay);
						if (getAll)
							createDaySlot (list,
								"Y".equals(rs.getString(6)), "Y".equals(rs.getString(7)), 		//	Mo..Tu
								"Y".equals(rs.getString(8)), "Y".equals(rs.getString(9)), "Y".equals(rs.getString(10)),	//  We..Fr
								"Y".equals(rs.getString(11)), "Y".equals(rs.getString(12)));
					}
				}	//	DaySlot

			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
			ma = new MAssignmentSlot (EARLIEST, LATEST,
				Msg.getMsg(m_ctx, "ResourceNotInSlotDay"), e.toString(),
				MAssignmentSlot.STATUS_NonBusinessDay);
		}
		if (ma != null && !getAll)
			return new MAssignmentSlot[] {ma};

		//	Assignments -------------------------------------------------------
		sql = "SELECT S_ResourceAssignment_ID "
			+ "FROM S_ResourceAssignment "
			+ "WHERE S_Resource_ID=?"					//	#1
			+ " AND AssignDateTo >= ?"					//	#2	start
			+ " AND AssignDateFrom <= ?"				//	#3	end
			+ " AND IsActive='Y'";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, m_S_Resource_ID);
			pstmt.setTimestamp(2, m_startDate);
			pstmt.setTimestamp(3, m_endDate);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				MResourceAssignment mAssignment = 
					new MResourceAssignment(Env.getCtx(), rs.getInt(1), trxName);
				ma = new MAssignmentSlot (mAssignment);
				if (!getAll)
					break;
				list.add(ma);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
			ma = new MAssignmentSlot (EARLIEST, LATEST,
				Msg.translate(m_ctx, "S_R"), e.toString(),
				MAssignmentSlot.STATUS_NotConfirmed);
		}
		if (ma != null && !getAll)
			return new MAssignmentSlot[] {ma};

		/*********************************************************************/

		//	fill m_timeSlots (required for layout)
		createTimeSlots();

		//	Clean list - date range
		ArrayList<MAssignmentSlot> clean = new ArrayList<MAssignmentSlot>(list.size());
		for (int i = 0; i < list.size(); i++)
		{
			MAssignmentSlot mas = list.get(i);
			if ((mas.getStartTime().equals(m_startDate) || mas.getStartTime().after(m_startDate))
					&& (mas.getEndTime().equals(m_endDate) || mas.getEndTime().before(m_endDate)))
				clean.add(mas);
		}
		//	Delete Unavailability TimeSlots when all day assigments exist
		MAssignmentSlot[] sorted = new MAssignmentSlot[clean.size()];
		clean.toArray(sorted);
		Arrays.sort(sorted, new MAssignmentSlot());	//	sorted by start/end date
		list.clear();	//	used as day list
		clean.clear();	//	cleaned days
		Timestamp sortedDay = null;
		for (int i = 0; i < sorted.length; i++)
		{
			if (sortedDay == null)
				sortedDay = TimeUtil.getDay(sorted[i].getStartTime());
			if (sortedDay.equals(TimeUtil.getDay(sorted[i].getStartTime())))
				list.add(sorted[i]);
			else
			{
				//	process info list -> clean
				layoutSlots (list, clean);
				//	prepare next
				list.clear();
				list.add(sorted[i]);
				sortedDay = TimeUtil.getDay(sorted[i].getStartTime());
			}
		}
		//	process info list -> clean
		layoutSlots (list, clean);

		//	Return
		MAssignmentSlot[] retValue = new MAssignmentSlot[clean.size()];
		clean.toArray(retValue);
		Arrays.sort(retValue, new MAssignmentSlot());	//	sorted by start/end date
		return retValue;
	}	//	getAssignmentSlots

	/**
	 * 	Copy valid Slots of a day from list to clear and layout
	 * 	@param list list with slos of the day
	 * 	@param clean list with only valid slots
	 */
	@SuppressWarnings("unchecked")
	private void layoutSlots (ArrayList<MAssignmentSlot> list, ArrayList<MAssignmentSlot> clean)
	{
		int size = list.size();
	//	System.out.println("Start List=" + size + ", Clean=" + clean.size());
		if (size == 0)
			return;
		else if (size == 1)
		{
			MAssignmentSlot mas = list.get(0);
			layoutY (mas);
			clean.add (mas);
			return;
		}

		//	Delete Unavailability TimeSlots when all day assignments exist
		boolean allDay = false;
		for (int i = 0; !allDay && i < size; i++)
		{
			MAssignmentSlot mas = list.get(i);
			if (mas.getStatus() == MAssignmentSlot.STATUS_NotAvailable
				|| mas.getStatus() == MAssignmentSlot.STATUS_UnAvailable
				|| mas.getStatus() == MAssignmentSlot.STATUS_NonBusinessDay
				|| mas.getStatus() == MAssignmentSlot.STATUS_NotInSlotDay)
				allDay = true;

		}
		if (allDay)
		{
			//	delete Time Slot
			for (int i = 0; i < list.size(); i++)
			{
				MAssignmentSlot mas = list.get(i);
				if (mas.getStatus() == MAssignmentSlot.STATUS_NotInSlotTime)
					list.remove(i--);
			}
		}

		//	Copy & Y layout remaining
		for (int i = 0; i < list.size(); i++)
		{
			MAssignmentSlot mas = list.get(i);
			layoutY (mas);
			clean.add (mas);
		}

		//	X layout
		int maxYslots = m_timeSlots.length;
		int[] xSlots = new int[maxYslots];		//	number of parallel slots
		for (int i = 0; i < list.size(); i++)
		{
			MAssignmentSlot mas = list.get(i);
			for (int y = mas.getYStart(); y < mas.getYEnd(); y++)
				xSlots[y]++;
		}
		//	Max parallel X Slots
		int maxXslots = 0;
		for (int y = 0; y < xSlots.length; y++)
		{
			if (xSlots[y] > maxXslots)
				maxXslots = xSlots[y];
		}
		//	Only one column
		if (maxXslots < 2)
		{
			for (int i = 0; i < list.size(); i++)
			{
				MAssignmentSlot mas = list.get(i);
				mas.setX(0, 1);
			}
			return;
		}

		//	Create xy Matrix
		ArrayList[][] matrix = new ArrayList[maxXslots][maxYslots];
		//	Populate Matrix first column
		for (int y = 0; y < maxYslots; y++)
		{
			ArrayList<Object> xyList = new ArrayList<Object>();
			matrix[0][y] = xyList;
			//	see if one assignment fits into slot
			for (int i = 0; i < list.size(); i++)
			{
				MAssignmentSlot mas = list.get(i);
				if (y >= mas.getYStart() && y <= mas.getYEnd())
					xyList.add(mas);
			}
			//	initiate right columns
			for (int x = 1; x < maxXslots; x++)
				matrix[x][y] = new ArrayList<Object>();
		}	//	for all y slots

		/**
		 * 	(AB)()	->	(B)(A)	->	(B)(A)
		 *  (BC)()	->	(BC)()	->	(B)(C)
		 * 	- if the row above is empty, move the first one right
		 *  - else	- check col_1..x above and move any content if the same
		 *  		- if size > 0
		 * 				- if the element is is not the same as above,
		 * 					move to the first empty column on the right
		 */
		//	if in one column cell, there is more than one, move it to the right
		for (int y = 0; y < maxYslots; y++)
		{
			//	if an element is the same as the line above, move it there
			if (y > 0 && matrix[0][y].size() > 0)
			{
				for (int x = 1; x < maxXslots; x++)
				{
					if (matrix[x][y-1].size() > 0)	//	above slot is not empty
					{
						Object above = matrix[x][y-1].get(0);
						for (int i = 0; i < matrix[x][y].size(); i++)
						{
							if (above.equals(matrix[0][y].get(i)))	//	same - move it
							{
								matrix[x][y].add(matrix[0][y].get(i));
								matrix[0][y].remove(i--);
							}
						}
					}
				}
			}	//	if an element is the same as the line above, move it there

			//	we need to move items to the right
			if (matrix[0][y].size() > 1)
			{
				Object above = null;
				if (y > 0 && matrix[0][y-1].size() > 0)
					above = matrix[0][y-1].get(0);
				//
				for (int i = 0; i < matrix[0][y].size(); i++)
				{
					Object move = matrix[0][y].get(i);
					if (!move.equals(above))	//	we can move it
					{
						for (int x = 1; move != null && x < maxXslots; x++)
						{
							if (matrix[x][y].size() == 0)	//	found an empty slot
							{
								matrix[x][y].add(move);
								matrix[0][y].remove(i--);
								move = null;
							}
						}
					}
				}
			}	//	we need to move items to the right
		}	//	 for all y slots

		//	go through the matrix and assign the X position
		for (int y = 0; y < maxYslots; y++)
		{
			for (int x = 0; x < maxXslots; x++)
			{
				if (matrix[x][y].size() > 0)
				{
					MAssignmentSlot mas = (MAssignmentSlot)matrix[x][y].get(0);
					mas.setX(x, xSlots[y]);
				}
			}
		}
		//	clean up
		matrix = null;
	}	//	layoutSlots

	/**
	 * 	Layout Y axis
	 * 	@param mas assignment slot
	 */
	private void layoutY (MAssignmentSlot mas)
	{
		int timeSlotStart = getTimeSlotIndex(mas.getStartTime(), false);
		int timeSlotEnd = getTimeSlotIndex(mas.getEndTime(), true);
		if (TimeUtil.isAllDay(mas.getStartTime(), mas.getEndTime()))
			timeSlotEnd = m_timeSlots.length - 1;
		//
		mas.setY (timeSlotStart, timeSlotEnd);
	}	//	layoutY

	/**
	 * 	Return the Time Slot index for the time.
	 *  Based on start time and not including end time
	 * 	@param time time (day is ignored)
	 *  @param endTime if true, the end time is included
	 * 	@return slot index
	 */
	private int getTimeSlotIndex (Timestamp time, boolean endTime)
	{
		//	Just one slot
		if (m_timeSlots.length <= 1)
			return 0;
		//	search for it
		for (int i = 0; i < m_timeSlots.length; i++)
		{
			if (m_timeSlots[i].inSlot (time, endTime))
				return i;
		}
		log.error("MSchedule.getTimeSlotIndex - did not find Slot for " + time + " end=" + endTime);
		return 0;
	}	//	getTimeSlotIndex


	/**
	 * 	Get Basic Info
	 *  @param S_Resource_ID resource
	 */
	private void getBaseInfo (int S_Resource_ID)
	{
		//	Resource is Active and Available
		String sql = Env.getUserRolePermissions(m_ctx).addAccessSQL(
			"SELECT r.IsActive,r.IsAvailable,null,"	//	r.IsSingleAssignment,"
			+ "r.S_ResourceType_ID,rt.C_UOM_ID "
			+ "FROM S_Resource r, S_ResourceType rt "
			+ "WHERE r.S_Resource_ID=?"
			+ " AND r.S_ResourceType_ID=rt.S_ResourceType_ID",
			"r",
			IUserRolePermissions.SQL_FULLYQUALIFIED,
			Access.READ);
		//
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, S_Resource_ID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				if (!"Y".equals(rs.getString(1)))					//	Active
					m_isAvailable = false;
				if (m_isAvailable && !"Y".equals(rs.getString(2)))	//	Available
					m_isAvailable = false;
				m_isSingleAssignment = "Y".equals(rs.getString(3));
				//
				m_S_ResourceType_ID = rs.getInt(4);
				m_C_UOM_ID = rs.getInt(5);
			//	log.debug( "- Resource_ID=" + m_S_ResourceType_ID + ",IsAvailable=" + m_isAvailable);
			}
			else
				m_isAvailable = false;
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
			m_isAvailable = false;
		}
		m_S_Resource_ID = S_Resource_ID;
	}	//	getBaseInfo

	/**
	 * 	Create Unavailable Timeslots.
	 *  For every day from startDay..endDay create unavailable slots
	 *  for 00:00..startTime and endTime..24:00
	 *  @param list list to add time slots to
	 *  @param startTime start time in day
	 *  @param endTime end time in day
	 */
	private void createTimeSlot (ArrayList<MAssignmentSlot> list,
		Timestamp startTime, Timestamp endTime)
	{
	//	log.debug( "MSchedule.createTimeSlot");
		GregorianCalendar cal = new GregorianCalendar(Language.getLoginLanguage().getLocale());
		cal.setTimeInMillis(m_startDate.getTime());
		//	End Date for Comparison
		GregorianCalendar calEnd = new GregorianCalendar(Language.getLoginLanguage().getLocale());
		calEnd.setTimeInMillis(m_endDate.getTime());

		while (cal.before(calEnd))
		{
			//	00:00..startTime
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Timestamp start = new Timestamp (cal.getTimeInMillis());
			//
			GregorianCalendar cal_1 = new GregorianCalendar(Language.getLoginLanguage().getLocale());
			cal_1.setTimeInMillis(startTime.getTime());
			cal.set(Calendar.HOUR_OF_DAY, cal_1.get(Calendar.HOUR_OF_DAY));
			cal.set(Calendar.MINUTE, cal_1.get(Calendar.MINUTE));
			cal.set(Calendar.SECOND, cal_1.get(Calendar.SECOND));
			Timestamp end = new Timestamp (cal.getTimeInMillis());
			//
			MAssignmentSlot	ma = new MAssignmentSlot (start, end,
				Msg.getMsg(m_ctx, "ResourceNotInSlotTime"), "",
				MAssignmentSlot.STATUS_NotInSlotTime);
			list.add(ma);

			//	endTime .. 00:00 next day
			cal_1.setTimeInMillis(endTime.getTime());
			cal.set(Calendar.HOUR_OF_DAY, cal_1.get(Calendar.HOUR_OF_DAY));
			cal.set(Calendar.MINUTE, cal_1.get(Calendar.MINUTE));
			cal.set(Calendar.SECOND, cal_1.get(Calendar.SECOND));
			start = new Timestamp (cal.getTimeInMillis());
			//
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.add(Calendar.DAY_OF_YEAR, 1);
			end = new Timestamp (cal.getTimeInMillis());
			//
			ma = new MAssignmentSlot (start, end,
				Msg.getMsg(m_ctx, "ResourceNotInSlotTime"), "",
				MAssignmentSlot.STATUS_NotInSlotTime);
			list.add(ma);
		}
	}	//	createTimeSlot

	/**
	 * 	Create Unavailable Dayslots.
	 *  For every day from startDay..endDay create unavailable slots
	 *  @param list list to add Day slots to
	 *  @param OnMonday true if OK to have appointments (i.e. blocked if false)
	 *  @param OnTuesday true if OK
	 *  @param OnWednesday true if OK
	 *  @param OnThursday true if OK
	 *  @param OnFriday true if OK
	 *  @param OnSaturday true if OK
	 *  @param OnSunday true if OK
	 */
	private void createDaySlot (ArrayList<MAssignmentSlot> list,
		boolean OnMonday, boolean OnTuesday, boolean OnWednesday,
		boolean OnThursday, boolean OnFriday, boolean OnSaturday, boolean OnSunday)
	{
	//	log.debug( "MSchedule.createDaySlot");
		GregorianCalendar cal = new GregorianCalendar(Language.getLoginLanguage().getLocale());
		cal.setTimeInMillis(m_startDate.getTime());
		//	End Date for Comparison
		GregorianCalendar calEnd = new GregorianCalendar(Language.getLoginLanguage().getLocale());
		calEnd.setTimeInMillis(m_endDate.getTime());

		while (cal.before(calEnd))
		{
			int weekday = cal.get(Calendar.DAY_OF_WEEK);
			if ((!OnSaturday && weekday == Calendar.SATURDAY)
				|| (!OnSunday && weekday == Calendar.SUNDAY)
				|| (!OnMonday && weekday == Calendar.MONDAY)
				|| (!OnTuesday && weekday == Calendar.TUESDAY)
				|| (!OnWednesday && weekday == Calendar.WEDNESDAY)
				|| (!OnThursday && weekday == Calendar.THURSDAY)
				|| (!OnFriday && weekday == Calendar.FRIDAY))
			{
				//	00:00..00:00 next day
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				Timestamp start = new Timestamp (cal.getTimeInMillis());
				cal.add(Calendar.DAY_OF_YEAR, 1);
				Timestamp end = new Timestamp (cal.getTimeInMillis());

				MAssignmentSlot	ma = new MAssignmentSlot (start, end,
					Msg.getMsg(m_ctx, "ResourceNotInSlotDay"), "",
					MAssignmentSlot.STATUS_NotInSlotDay);
				list.add(ma);
			}
			else	//	next day
				cal.add(Calendar.DAY_OF_YEAR, 1);
		}
	}	//	createDaySlot

	/**
	 * 	Create a day slot for range
	 * 	@param list list
	 * 	@param ma assignment
	 */
	private void createDaySlot (ArrayList<MAssignmentSlot> list, MAssignmentSlot ma)
	{
	//	log.debug( "MSchedule.createDaySlot", ma);
		//
		Timestamp start = ma.getStartTime();
		GregorianCalendar calStart = new GregorianCalendar();
		calStart.setTime(start);
		calStart.set(Calendar.HOUR_OF_DAY, 0);
		calStart.set(Calendar.MINUTE, 0);
		calStart.set(Calendar.SECOND, 0);
		calStart.set(Calendar.MILLISECOND, 0);
		Timestamp end = ma.getEndTime();
		GregorianCalendar calEnd = new GregorianCalendar();
		calEnd.setTime(end);
		calEnd.set(Calendar.HOUR_OF_DAY, 0);
		calEnd.set(Calendar.MINUTE, 0);
		calEnd.set(Calendar.SECOND, 0);
		calEnd.set(Calendar.MILLISECOND, 0);
		//
		while (calStart.before(calEnd))
		{
			Timestamp xStart = new Timestamp(calStart.getTimeInMillis());
			calStart.add(Calendar.DAY_OF_YEAR, 1);
			Timestamp xEnd = new Timestamp(calStart.getTimeInMillis());
			MAssignmentSlot myMa = new MAssignmentSlot (xStart, xEnd,
				ma.getName(), ma.getDescription(), ma.getStatus());
			list.add(myMa);
		}
	}	//	createDaySlot

	/*************************************************************************/

	/**
	 * 	Get Day Time Slots for Date
	 *  @return "heading" or null
	 */
	public MAssignmentSlot[] getDayTimeSlots ()
	{
		return m_timeSlots;
	}	//	getDayTimeSlots

	/**
	 * 	Create Time Slots
	 */
	private void createTimeSlots()
	{
		//	development error
		if (m_typeName == null)
			throw new IllegalStateException("ResourceTypeName not set");

		ArrayList<MAssignmentSlot> list = new ArrayList<MAssignmentSlot>();
		MUOM uom = MUOM.get (m_ctx, m_C_UOM_ID);
		int minutes = LegacyUOMConversionUtils.convertToMinutes (m_ctx, m_C_UOM_ID, Env.ONE);
		log.info("Minutes=" + minutes);
		//
		if (minutes > 0 && minutes < 60*24)
		{
			//	Set Start Time
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(m_startDate);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			//	we have slots - create first
			if (m_slotStartTime != null)
			{
				long start = cal.getTimeInMillis();
				cal.setTime(TimeUtil.getDayTime(m_startDate, m_slotStartTime));	//	set to start time
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				list.add(new MAssignmentSlot(start, cal.getTimeInMillis()));
			}
			//	Set End Time
			GregorianCalendar calEnd = new GregorianCalendar();
			if (m_slotEndTime != null)
			{
				calEnd.setTime(TimeUtil.getDayTime(m_startDate, m_slotEndTime));
				calEnd.set(Calendar.SECOND, 0);
				calEnd.set(Calendar.MILLISECOND, 0);
			}
			else	//	No Slot - all day
			{
				calEnd.setTime(m_startDate);
				calEnd.set(Calendar.HOUR_OF_DAY, 0);
				calEnd.set(Calendar.MINUTE, 0);
				calEnd.set(Calendar.SECOND, 0);
				calEnd.set(Calendar.MILLISECOND, 0);
				calEnd.add(Calendar.DAY_OF_YEAR, 1);
			}
//System.out.println("Start=" + new Timestamp(cal.getTimeInMillis()));
//System.out.println("Endt=" + new Timestamp(calEnd.getTimeInMillis()));

			//	Set end Slot Time
			GregorianCalendar calEndSlot = new GregorianCalendar();
			calEndSlot.setTime(cal.getTime());
			calEndSlot.add(Calendar.MINUTE, minutes);

			while (cal.before(calEnd))
			{
				list.add(new MAssignmentSlot(cal.getTimeInMillis(), calEndSlot.getTimeInMillis()));
				//	Next Slot
				cal.add(Calendar.MINUTE, minutes);
				calEndSlot.add(Calendar.MINUTE, minutes);
			}
			//	create last slot
			calEndSlot.setTime(cal.getTime());
			calEndSlot.set(Calendar.HOUR_OF_DAY, 0);
			calEndSlot.set(Calendar.MINUTE, 0);
			calEndSlot.set(Calendar.SECOND, 0);
			calEndSlot.set(Calendar.MILLISECOND, 0);
			calEndSlot.add(Calendar.DAY_OF_YEAR, 1);	//	00:00 next day
			list.add(new MAssignmentSlot(cal.getTimeInMillis(), calEndSlot.getTimeInMillis()));
		}

		else	//	Day, ....
		{
			list.add (new MAssignmentSlot(TimeUtil.getDay(m_startDate), TimeUtil.getNextDay(m_startDate)));
		}

		//
		m_timeSlots = new MAssignmentSlot[list.size()];
		list.toArray(m_timeSlots);
	}	//	createTimeSlots

	/*************************************************************************/

	/**
	 * 	Get Resource ID. Set by getAssignmentSlots
	 * 	@return current resource
	 */
	public int getS_Resource_ID()
	{
		return m_S_Resource_ID;
	}	//	getS_Resource_ID

	/**
	 * 	Return Start Date. Set by getAssignmentSlots
	 * 	@return start date
	 */
	public Timestamp getStartDate ()
	{
		return m_startDate;
	}	//	getStartDate

	/**
	 * 	Return End Date. Set by getAssignmentSlots
	 * 	@return end date
	 */
	public Timestamp getEndDate ()
	{
		return m_endDate;
	}	//	getEndDate

	/**
	 * Calculate End Date based on start date and qty
	 * 
	 * @param ctx context
	 * @param startDate date
	 * @param C_UOM_ID UOM
	 * @param qty qty
	 * @return end date
	 */
	private static Timestamp computeEndDate(Properties ctx, Timestamp startDate, int C_UOM_ID, BigDecimal qty)
	{
		GregorianCalendar endDate = new GregorianCalendar();
		endDate.setTime(startDate);
		//
		int minutes = LegacyUOMConversionUtils.convertToMinutes(ctx, C_UOM_ID, qty);
		endDate.add(Calendar.MINUTE, minutes);
		//
		Timestamp retValue = new Timestamp(endDate.getTimeInMillis());
		// log.info( "TimeUtil.getEndDate", "Start=" + startDate
		// + ", Qty=" + qty + ", End=" + retValue);
		return retValue;
	}	// startDate

}	//	MSchedule
