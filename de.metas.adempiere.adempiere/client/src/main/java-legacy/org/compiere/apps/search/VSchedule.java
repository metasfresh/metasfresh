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
package org.compiere.apps.search;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;

import javax.swing.JScrollPane;

import org.compiere.model.MAssignmentSlot;
import org.compiere.model.ScheduleUtil;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

/**
 *	Visual and Control Part of Schedule.
 *  Contains Time and Schedule Panels
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: VSchedule.java,v 1.3 2006/07/30 00:51:27 jjanke Exp $
 */
public class VSchedule extends CPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 604084538933601239L;

	/**
	 *	Constructor
	 *  @param is InfoSchedule for call back
	 *  @param type Type of schedule TYPE_...
	 */
	public VSchedule (InfoSchedule is, int type)
	{
		m_type = type;
		m_model = new ScheduleUtil (Env.getCtx());
		schedulePanel.setType (m_type);
		schedulePanel.setTimePanel (timePanel);
		schedulePanel.setInfoSchedule (is);	//	for callback
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "VSchedule", e);
		}
	}	//	VSchedule

	/**	Day Display				*/
	static public final int	TYPE_DAY = Calendar.DAY_OF_MONTH;
	/**	Week Display			*/
	static public final int	TYPE_WEEK = Calendar.WEEK_OF_YEAR;
	/**	Month Display			*/
	static public final int	TYPE_MONTH = Calendar.MONTH;

	/** Type					*/
	private int				m_type = TYPE_DAY;
	/** Model					*/
	private ScheduleUtil		m_model = null;
	/**	 Start Date				*/
	private Timestamp		m_startDate;
	/**	End Date				*/
	private Timestamp		m_endDate;
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(VSchedule.class);

	private BorderLayout mainLayout = new BorderLayout();
	private VScheduleTimePanel timePanel = new VScheduleTimePanel();
	private VSchedulePanel schedulePanel = new VSchedulePanel();
	private JScrollPane schedulePane = new JScrollPane();

	/**
	 * 	Static init
	 *  <pre>
	 * 	timePanel (West)
	 *  schedlePanel (in schedulePane - Center)
	 *  </pre>
	 * 	@throws Exception
	 */
	private void jbInit() throws Exception
	{
		this.setLayout(mainLayout);
		this.add(timePanel, BorderLayout.WEST);
		//
	//	schedulePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		schedulePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		schedulePane.getViewport().add(schedulePanel, null);
		schedulePane.setPreferredSize(new Dimension(200, 200));
		schedulePane.setBorder(null);
		this.add(schedulePane, BorderLayout.CENTER);
	}	//	jbInit

	/**
	 * 	Recreate View
	 * 	@param S_Resource_ID Resource
	 * 	@param date Date
	 */
	public void recreate (int S_Resource_ID, Timestamp date)
	{
		//	Calculate Start Day
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		if (m_type == TYPE_WEEK)
			cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		else if (m_type == TYPE_MONTH)
			cal.set(Calendar.DAY_OF_MONTH, 1);
		m_startDate = new Timestamp(cal.getTimeInMillis());
		//	Calculate End Date
		cal.add(m_type, 1);
		m_endDate = new Timestamp (cal.getTimeInMillis());
		//
		log.config("(" + m_type + ") Resource_ID=" + S_Resource_ID + ": " + m_startDate + "->" + m_endDate);
		//	Create Slots
		MAssignmentSlot[] mas = m_model.getAssignmentSlots (S_Resource_ID, m_startDate, m_endDate, null, true, null);
		MAssignmentSlot[] mts = m_model.getDayTimeSlots ();
		//	Set Panels
		timePanel.setTimeSlots(mts);
		schedulePanel.setAssignmentSlots(mas, S_Resource_ID, m_startDate, m_endDate);
		//	Set Height
		schedulePanel.setHeight(timePanel.getPreferredSize().height);
	//	repaint();
	}	//	recreate

	/**
	 * 	Enable/disable to Create New Assignments
	 * 	@param createNew if true, allows to create new Assignments
	 */
	public void setCreateNew (boolean createNew)
	{
		schedulePanel.setCreateNew(createNew);
	}	//	setCreateNew

	/**
	 * 	Dispose
	 */
	public void dispose()
	{
		m_model = null;
		timePanel = null;
		if (schedulePanel != null)
			schedulePanel.dispose();
		schedulePanel = null;
		this.removeAll();
	}	//	dispose

	/**
	 * 	Get Start Date
	 * 	@return start date
	 */
	public Timestamp getStartDate()
	{
		return m_startDate;
	}	//	getStartDate

}	//	VSchedule
