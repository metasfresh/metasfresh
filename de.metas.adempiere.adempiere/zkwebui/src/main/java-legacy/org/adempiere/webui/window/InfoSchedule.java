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
package org.adempiere.webui.window;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.DatetimeBox;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListItem;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.panel.StatusBarPanel;
import org.adempiere.webui.panel.WSchedule;
import org.compiere.model.MAssignmentSlot;
import org.compiere.model.MResourceAssignment;
import org.compiere.model.ScheduleUtil;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Vbox;


/**
 *	Schedule - Resource availability & assigment.
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: InfoSchedule.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 * 
 *  Zk Port
 *  @author Low Heng Sin
 *  
 *  Zk Port
 *  @author		Elaine
 *  @version	InfoSchedule.java Adempiere Swing UI 3.4.1 
 */
public class InfoSchedule extends Window implements EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5948901371276429661L;

	/**
	 *  Constructor
	 *  @param mAssignment optional assignment
	 *  @param createNew if true, allows to create new assignments
	 */
	public InfoSchedule (MResourceAssignment mAssignment, boolean createNew)
	{
		super();
		setTitle(Msg.getMsg(Env.getCtx(), "InfoSchedule"));
		if (createNew)
			setAttribute("mode", "modal");
		else
			setAttribute("mode", "overlapped");
		this.setWidth("600px");
//		this.setHeight("600px");
		this.setClosable(true);
		this.setBorder("normal");
		this.setStyle("position: absolute");
		if (mAssignment == null)
			m_mAssignment = new MResourceAssignment(Env.getCtx(), 0, null);
		else
			m_mAssignment = mAssignment;
		if (mAssignment != null)
			log.info(mAssignment.toString());
		m_dateFrom = m_mAssignment.getAssignDateFrom();
		if (m_dateFrom == null)
			m_dateFrom = new Timestamp(System.currentTimeMillis());
		m_createNew = createNew;
		try
		{
			init();
			dynInit(createNew);
		}
		catch(Exception ex)
		{
			log.error("InfoSchedule", ex);
		}
		AEnv.showWindow(this);
	}	//	InfoSchedule

	/**
	 * 	IDE Constructor
	 */
	public InfoSchedule()
	{
		this (null, false);
	}	//	InfoSchedule

	/**	Resource 					*/
	private MResourceAssignment		m_mAssignment;
	/** Date						*/
	private Timestamp		m_dateFrom = null;
	/**	Loading						*/
	private boolean			m_loading = false;
	/**	 Ability to create new assignments	*/
	private boolean			m_createNew;
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(InfoSchedule.class);

	private Vbox mainLayout = new Vbox();
	private Grid parameterPanel = GridFactory.newGridLayout();
	private Label labelResourceType = new Label();
	private Listbox fieldResourceType = new Listbox();
	private Label labelResource = new Label();
	private Listbox fieldResource = new Listbox();
	// Elaine 2008/12/12
	private Button bPrevious = new Button();
	private Label labelDate = new Label();
	private DatetimeBox fieldDate = new DatetimeBox();
	private Button bNext = new Button();
	//
	private WSchedule schedulePane = new WSchedule(this);
	private StatusBarPanel statusBar = new StatusBarPanel();
	private ConfirmPanel confirmPanel = new ConfirmPanel(true);

	/**
	 * 	Static Layout
	 * 	@throws Exception
	 */
	private void init() throws Exception
	{
		this.appendChild(mainLayout);
		mainLayout.setHeight("100%");
		mainLayout.setWidth("100%");
		
		labelResourceType.setValue(Msg.translate(Env.getCtx(), "S_ResourceType_ID"));
		labelResource.setValue(Msg.translate(Env.getCtx(), "S_Resource_ID"));
		labelDate.setValue(Msg.translate(Env.getCtx(), "Date"));
		
		// Elaine 2008/12/12
		bPrevious.setLabel("<");
		bNext.setLabel(">");
		//
		
		mainLayout.appendChild(parameterPanel);
		
		Rows rows = new Rows();
		rows.setParent(parameterPanel);
		Row row = new Row();
		rows.appendChild(row);
		
		row.appendChild(labelResourceType);
		row.appendChild(fieldResourceType);				
		
		row = new Row();
		rows.appendChild(row);
		row.appendChild(labelResource);
		row.appendChild(fieldResource);
		
		// Elaine 2008/12/12
		row = new Row();
		rows.appendChild(row);		
		row.appendChild(labelDate);
		Hbox hbox = new Hbox();
		hbox.appendChild(bPrevious);
		hbox.appendChild(fieldDate);
		hbox.appendChild(bNext);
		row.appendChild(hbox);
		//
		
		mainLayout.appendChild(schedulePane);
		
		schedulePane.setWidth("100%");
		schedulePane.setHeight("400px");
		Div div = new Div();
		div.appendChild(confirmPanel);
		div.appendChild(statusBar);
		mainLayout.appendChild(div);
		
		fieldResourceType.setMold("select");
		fieldResource.setMold("select");
	}	//	jbInit

	/**
	 * 	Dynamic Init
	 *  @param createNew if true, allows to create new assignments
	 */
	private void dynInit (boolean createNew) 
	{
		//	Resource
		fillResourceType();
		fillResource();
		fieldResourceType.addEventListener(Events.ON_SELECT, this);
		fieldResource.addEventListener(Events.ON_SELECT, this);

		//	Date - Elaine 2008/12/12
		fieldDate.setValue(m_dateFrom);
		fieldDate.getDatebox().addEventListener(Events.ON_BLUR, this);
		fieldDate.getTimebox().addEventListener(Events.ON_BLUR, this);
//		fieldDate.addEventListener(Events.ON_BLUR, this);
		bPrevious.addEventListener(Events.ON_CLICK, this);
		bNext.addEventListener(Events.ON_CLICK, this);
		//
		
		//
		confirmPanel.addActionListener(Events.ON_CLICK, this);
		if (createNew) {
			Button btnNew = new Button();
	        btnNew.setName("btnNew");
	        btnNew.setId("New");
	        btnNew.setSrc("/images/New24.png");
	        
			confirmPanel.addComponentsLeft(btnNew);			
			btnNew.addEventListener(Events.ON_CLICK, this);
		}
		displayCalendar();
	}	//	dynInit

	/**
	 * 	Fill Resource Type (one time)
	 */
	private void fillResourceType()
	{
		//	Get ResourceType of selected Resource
		int S_ResourceType_ID = 0;
		if (m_mAssignment.getS_Resource_ID() != 0)
		{
			String sql = "SELECT S_ResourceType_ID FROM S_Resource WHERE S_Resource_ID=?";
			try
			{
				PreparedStatement pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, m_mAssignment.getS_Resource_ID());
				ResultSet rs = pstmt.executeQuery();
				if (rs.next())
					S_ResourceType_ID = rs.getInt(1);
				rs.close();
				pstmt.close();
			}
			catch (SQLException e)
			{
				log.error(sql, e);
			}
		}

		//	Get Resource Types
		String sql = Env.getUserRolePermissions().addAccessSQL(
			"SELECT S_ResourceType_ID, Name FROM S_ResourceType WHERE IsActive='Y' ORDER BY 2",
			"S_ResourceType", IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
		KeyNamePair defaultValue = null;
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				KeyNamePair pp = new KeyNamePair(rs.getInt(1), rs.getString(2));
				if (S_ResourceType_ID == pp.getKey())
					defaultValue = pp;
				fieldResourceType.appendItem(pp.getName(), pp.getKey());
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
		if (defaultValue != null) {
			int cnt = fieldResourceType.getItemCount();
			for(int i = 0; i < cnt; i++) {
				ListItem li = fieldResourceType.getItemAtIndex(i);
				Integer key = (Integer) li.getValue();
				if (key.intValue() == defaultValue.getKey()) {
					fieldResourceType.setSelectedItem(li);
					break;
				}
			}
		} else if (fieldResourceType.getItemCount() > 0) {
			fieldResourceType.setSelectedIndex(0);
		}
	}	//	fillResourceType

	/**
	 * 	Fill Resource Pick from Resource Type
	 */
	private void fillResource()
	{
		ListItem listItem = fieldResourceType.getSelectedItem();
		if (listItem == null)
			return;
		//	Get Resource Type
		KeyNamePair pp = new KeyNamePair((Integer)listItem.getValue(), listItem.getLabel());
		int S_ResourceType_ID = pp.getKey();

		KeyNamePair defaultValue = null;

		//	Load Resources
		m_loading = true;
		fieldResource.getChildren().clear();
		String sql = "SELECT S_Resource_ID, Name FROM S_Resource WHERE S_ResourceType_ID=? ORDER BY 2";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, S_ResourceType_ID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				pp = new KeyNamePair(rs.getInt(1), rs.getString(2));
				if (m_mAssignment.getS_Resource_ID() == pp.getKey())
					defaultValue = pp;
				fieldResource.appendItem(pp.getName(), pp.getKey());
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
		if (defaultValue != null) {
			int cnt = fieldResource.getItemCount();
			for(int i = 0; i < cnt; i++) {
				ListItem li = fieldResource.getItemAtIndex(i);
				Integer key = (Integer) li.getValue();
				if (key.intValue() == defaultValue.getKey()) {
					fieldResource.setSelectedItem(li);
					break;
				}
			}
		} else if ( fieldResource.getItemCount() > 0) {
			fieldResource.setSelectedIndex(0);
		}

		m_loading = false;
	}	//	fillResource

	/**
	 * 	Display Calendar for selected Resource, Time(day/week/month) and Date
	 */
	private void displayCalendar ()
	{
		//	Get Values
		ListItem listItem = fieldResource.getSelectedItem();
		if (listItem == null)
			return;
		KeyNamePair pp = new KeyNamePair((Integer)listItem.getValue(), listItem.getLabel());
		int S_Resource_ID = pp.getKey();
		m_mAssignment.setS_Resource_ID(S_Resource_ID);

		// Elaine 2008/12/12
		Date date = fieldDate.getValue();
		if (date == null) date = new Timestamp(System.currentTimeMillis());
//		Date date = m_dateFrom;
		//

		//	Set Info
		m_loading = true;
		schedulePane.recreate(S_Resource_ID, date);
		m_loading = false;
		invalidate();
	}	//	displayCalendar

	/**************************************************************************
	 * 	Dispose.
	 */
	public void dispose()
	{
		this.detach();
	}	//	dispose

	/*************************************************************************/

	/**
	 * 	Callback.
	 * 	Called from WSchedule after WAssignmentDialog finished
	 * 	@param assignment New/Changed Assignment
	 */
	public void mAssignmentCallback (MResourceAssignment assignment)
	{
		m_mAssignment = assignment;
		if (m_createNew)
			dispose();
		else
			displayCalendar();
	}	//	mAssignmentCallback

	/**
	 * 	Get Assignment
	 * 	@return Assignment
	 */
	public MResourceAssignment getMResourceAssignment()
	{
		return m_mAssignment;
	}	//	getMResourceAssignment

	public void onEvent(Event event) throws Exception {
		if (m_loading)
			return;

		if (event.getTarget().getId().equals("Ok"))
			dispose();
		else if (event.getTarget().getId().equals("Cancel"))
			dispose();
		//
		else if (event.getTarget() == fieldResourceType)
		{
			fillResource();
			displayCalendar();
		}
		// Elaine 2008/12/12
		else if (event.getTarget()== fieldResource 
				|| event.getTarget() == fieldDate.getDatebox() 
				|| event.getTarget() == fieldDate.getTimebox())
			displayCalendar();
		//
		else if (event.getTarget() == bPrevious)
			adjustDate(-1);
		else if (event.getTarget() == bNext)
			adjustDate(+1);
		//
		else if (event.getTarget().getId().equals("New"))
			doAdd();
		//
		
	}
	
	// Elaine 2008/12/12
	/**
	 * 	Adjust Date
	 * 	@param diff difference
	 */
	private void adjustDate (int diff)
	{
		Date date = fieldDate.getValue();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		
//		if (timePane.getSelectedIndex() == 0)
			cal.add(java.util.Calendar.DAY_OF_YEAR, diff);
//		else if (timePane.getSelectedIndex() == 1)
//			cal.add(java.util.Calendar.WEEK_OF_YEAR, diff);
//		else
//			cal.add(java.util.Calendar.MONTH, diff);
		//
		fieldDate.setValue(new Timestamp(cal.getTimeInMillis()));
		displayCalendar ();
	}	//	adjustDate
	//

	private void doAdd() {
		ListItem listItem = fieldResource.getSelectedItem();
		if (listItem == null)
			return;
		//	Get Resource Type
		KeyNamePair pp = new KeyNamePair((Integer)listItem.getValue(), listItem.getLabel());
		int S_Resource_ID = pp.getKey();
		
		ScheduleUtil schedule = new ScheduleUtil (Env.getCtx());
		Timestamp start = m_dateFrom;
		java.sql.Date startDate = new java.sql.Date(start.getTime());
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(startDate.getTime());
		start = new Timestamp(startDate.getTime());
		start = new Timestamp(cal.getTimeInMillis());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		start = new Timestamp(cal.getTimeInMillis());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Timestamp end = new Timestamp(cal.getTimeInMillis());
		MAssignmentSlot[] mas = schedule.getAssignmentSlots(S_Resource_ID, start, end, null, true, null);
		MAssignmentSlot[] mts = schedule.getDayTimeSlots ();
		
		MAssignmentSlot slot = null;
		for (int i = 0; i < mts.length; i++) {
			slot = mts[i];
			for(int j = 0; j < mas.length; j++) {
				if (mts[i].getStartTime().getTime() == mas[j].getStartTime().getTime()) {
					slot = null;
					break;
				}
				if (mas[j].getEndTime() != null) {
					if (mts[i].getStartTime().getTime() > mas[j].getStartTime().getTime()
						&& mts[i].getStartTime().getTime() < mas[j].getEndTime().getTime()) {
						slot = null;
						break;
					} else if (mts[i].getEndTime().getTime() > mas[j].getStartTime().getTime()
							&& mts[i].getEndTime().getTime() < mas[j].getEndTime().getTime()) {
						slot = null;
						break;
					} else if (mts[i].getStartTime().getTime() < mas[j].getStartTime().getTime()
						&& mts[i].getEndTime().getTime() >= mas[j].getEndTime().getTime()) {
						slot = null;
						break;
					}
				}
			}
			if (slot != null) 
				break;
		}
		if (slot != null) {
			MResourceAssignment ma = new MResourceAssignment(Env.getCtx(), 0, null);
			ma.setS_Resource_ID(S_Resource_ID);
			
			ma.setAssignDateFrom(TimeUtil.getDayTime(start, slot.getStartTime()));
			ma.setQty(new BigDecimal(1));
			WAssignmentDialog vad =  new WAssignmentDialog (ma, false, m_createNew);
			mAssignmentCallback(vad.getMResourceAssignment());		
		} else {
			FDialog.error(0, this, "No available time slot for the selected day.");
		}
	}

	public boolean isCreateNew() {
		return m_createNew;
	}

	/**
	 * Callback from WSchedule
	 * @param date
	 */
	public void dateCallback(Date date) {
		m_dateFrom = new Timestamp(date.getTime());
		fieldDate.setValue(m_dateFrom); // Elaine 2008/12/15
	}
	

	/**
SELECT o.DocumentNo, ol.Line, ol.Description
FROM C_OrderLine ol, C_Order o
WHERE ol.S_ResourceAssignment_ID=1
  AND ol.C_Order_ID=o.C_Order_ID
UNION
SELECT i.DocumentNo, il.Line, il.Description
FROM C_InvoiceLine il, C_Invoice i
WHERE il.S_ResourceAssignment_ID=1
  AND il.C_Invoice_ID=i.C_Invoice_ID
UNION
SELECT e.DocumentNo, el.Line, el.Description
FROM S_TimeExpenseLine el, S_TimeExpense e
WHERE el.S_ResourceAssignment_ID=1
  AND el.S_TimeExpense_ID=el.S_TimeExpense_ID
	 */
}	//	InfoSchedule
