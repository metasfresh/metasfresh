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
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.adempiere.images.Images;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.StatusBar;
import org.compiere.grid.ed.VDate;
import org.compiere.model.MResourceAssignment;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.slf4j.Logger;

import de.metas.i18n.Msg;
import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissions;
import de.metas.security.permissions.Access;


/**
 *	Schedule - Resource availability & assigment.
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: InfoSchedule.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class InfoSchedule extends CDialog
	implements ActionListener, ChangeListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7364837484557281167L;

	/**
	 *  Constructor
	 *  @param frame Parent
	 *  @param mAssignment optional assignment
	 *  @param createNew if true, allows to create new assignments
	 */
	public InfoSchedule (Frame frame, MResourceAssignment mAssignment, boolean createNew)
	{
		super(frame, Msg.getMsg(Env.getCtx(), "InfoSchedule"), frame != null && createNew);
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
			jbInit();
			dynInit(createNew);
		}
		catch(Exception ex)
		{
			log.error("InfoSchedule", ex);
		}
		AEnv.showCenterWindow(frame, this);
	}	//	InfoSchedule

	/**
	 * 	IDE Constructor
	 */
	public InfoSchedule()
	{
		this (null, null, false);
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

	private CPanel mainPanel = new CPanel();
	private BorderLayout mainLayout = new BorderLayout();
	private CPanel parameterPanel = new CPanel();
	private GridBagLayout parameterLayout = new GridBagLayout();
	private JLabel labelResourceType = new JLabel();
	private JComboBox fieldResourceType = new JComboBox();
	private JLabel labelResource = new JLabel();
	private JComboBox fieldResource = new JComboBox();
	private JButton bPrevious = new JButton();
	private JLabel labelDate = new JLabel();
	private VDate fieldDate = new VDate();
	private JButton bNext = new JButton();
	private JTabbedPane timePane = new JTabbedPane();
	private VSchedule daySchedule = new VSchedule(this, VSchedule.TYPE_DAY);
	private VSchedule weekSchedule = new VSchedule(this, VSchedule.TYPE_WEEK);
	private VSchedule monthSchedule = new VSchedule(this, VSchedule.TYPE_MONTH);
	private StatusBar statusBar = new StatusBar();
	private ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();

	/**
	 * 	Static Layout
	 * 	@throws Exception
	 */
	private void jbInit() throws Exception
	{
		setIconImage(Images.getImage2(InfoBuilder.ACTION_InfoSchedule + "16"));
		mainPanel.setLayout(mainLayout);
		parameterPanel.setLayout(parameterLayout);
		labelResourceType.setHorizontalTextPosition(SwingConstants.LEADING);
		labelResourceType.setText(Msg.translate(Env.getCtx(), "S_ResourceType_ID"));
		labelResource.setHorizontalTextPosition(SwingConstants.LEADING);
		labelResource.setText(Msg.translate(Env.getCtx(), "S_Resource_ID"));
		bPrevious.setMargin(new Insets(0, 0, 0, 0));
		bPrevious.setText("<");
		labelDate.setText(Msg.translate(Env.getCtx(), "Date"));
		bNext.setMargin(new Insets(0, 0, 0, 0));
		bNext.setText(">");
		getContentPane().add(mainPanel,  BorderLayout.CENTER);
		mainPanel.add(parameterPanel, BorderLayout.NORTH);
		parameterPanel.add(labelResourceType,    new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(8, 8, 0, 0), 0, 0));
		parameterPanel.add(fieldResourceType,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 8, 8, 4), 0, 0));
		parameterPanel.add(labelResource,    new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(8, 4, 0, 4), 0, 0));
		parameterPanel.add(fieldResource,      new GridBagConstraints(1, 1, 1, 1, 0.5, 0.0
			,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 8, 4), 0, 0));
		parameterPanel.add(bPrevious,   new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 8, 8, 0), 0, 0));
		parameterPanel.add(labelDate,   new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(8, 0, 0, 0), 0, 0));
		parameterPanel.add(fieldDate,   new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 8, 0), 0, 0));
		parameterPanel.add(bNext,   new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 8, 8), 0, 0));
		//
		mainPanel.add(new JScrollPane(timePane),  BorderLayout.CENTER);
		timePane.add(daySchedule,  Msg.getMsg(Env.getCtx(), "Day"));
		timePane.add(weekSchedule,  Msg.getMsg(Env.getCtx(), "Week"));
		timePane.add(monthSchedule,   Msg.getMsg(Env.getCtx(), "Month"));
	//	timePane.add(daySchedule,  Msg.getMsg(Env.getCtx(), "Day"));
	//	timePane.add(weekSchedule,  Msg.getMsg(Env.getCtx(), "Week"));
	//	timePane.add(monthSchedule,   Msg.getMsg(Env.getCtx(), "Month"));
		timePane.addChangeListener(this);
		//
		mainPanel.add(confirmPanel,  BorderLayout.SOUTH);
		//
		this.getContentPane().add(statusBar, BorderLayout.SOUTH);
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
		fieldResourceType.addActionListener(this);
		fieldResource.addActionListener(this);

		//	Date
		fieldDate.setValue(m_dateFrom);
		fieldDate.addActionListener(this);
		bPrevious.addActionListener(this);
		bNext.addActionListener(this);

		//	Set Init values
		daySchedule.setCreateNew(createNew);
		weekSchedule.setCreateNew(createNew);
		monthSchedule.setCreateNew(createNew);
		//
		confirmPanel.setActionListener(this);
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
			S_ResourceType_ID = DB.getSQLValue(null, sql, m_mAssignment.getS_Resource_ID());
		}

		//	Get Resource Types
		String sql = Env.getUserRolePermissions().addAccessSQL(
			"SELECT S_ResourceType_ID, Name FROM S_ResourceType WHERE IsActive='Y' ORDER BY 2",
			"S_ResourceType", IUserRolePermissions.SQL_NOTQUALIFIED,
			Access.READ);
		KeyNamePair defaultValue = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				KeyNamePair pp = new KeyNamePair(rs.getInt(1), rs.getString(2));
				if (S_ResourceType_ID == pp.getKey())
					defaultValue = pp;
				fieldResourceType.addItem(pp);
			}
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		if (defaultValue != null)
			fieldResourceType.setSelectedItem(defaultValue);
	}	//	fillResourceType

	/**
	 * 	Fill Resource Pick from Resource Type
	 */
	private void fillResource()
	{
		//	Get Resource Type
		KeyNamePair pp = (KeyNamePair)fieldResourceType.getSelectedItem();
		if (pp == null)
			return;
		int S_ResourceType_ID = pp.getKey();

		KeyNamePair defaultValue = null;

		//	Load Resources
		m_loading = true;
		fieldResource.removeAllItems();
		String sql = "SELECT S_Resource_ID, Name FROM S_Resource WHERE IsActive='Y' AND S_ResourceType_ID=? ORDER BY 2";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, S_ResourceType_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				pp = new KeyNamePair(rs.getInt(1), rs.getString(2));
				if (m_mAssignment.getS_Resource_ID() == pp.getKey())
					defaultValue = pp;
				fieldResource.addItem(pp);
			}
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		if (defaultValue != null)
			fieldResource.setSelectedItem(defaultValue);

		m_loading = false;
	}	//	fillResource

	/**
	 * 	Display Calendar for selected Resource, Time(day/week/month) and Date
	 */
	private void displayCalendar ()
	{
		//	Get Values
		KeyNamePair pp = (KeyNamePair)fieldResource.getSelectedItem();
		if (pp == null)
			return;
		int S_Resource_ID = pp.getKey();
		m_mAssignment.setS_Resource_ID(S_Resource_ID);
		Timestamp date = fieldDate.getTimestamp();
		int index = timePane.getSelectedIndex();
		log.info("Index=" + index + ", ID=" + S_Resource_ID + " - " + date);

		//	Set Info
		m_loading = true;
		if (index == 0)
			daySchedule.recreate (S_Resource_ID, date);
		else if (index == 1)
			weekSchedule.recreate (S_Resource_ID, date);
		else
			monthSchedule.recreate (S_Resource_ID, date);
		m_loading = false;
		repaint();
	}	//	displayCalendar

	/**************************************************************************
	 * 	Dispose.
	 */
	@Override
	public void dispose()
	{
		daySchedule.dispose();
		weekSchedule.dispose();
		monthSchedule.dispose();
		this.removeAll();
		super.dispose();
	}	//	dispose

	/**
	 * 	Action Listener
	 * 	@param e event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (m_loading)
			return;
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
			dispose();
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
			dispose();
		//
		else if (e.getSource() == fieldResourceType)
		{
			fillResource();
			displayCalendar();
		}
		//
		else if (e.getSource() == fieldResource || e.getSource() == fieldDate)
			displayCalendar();
		//
		else if (e.getSource() == bPrevious)
			adjustDate(-1);
		else if (e.getSource() == bNext)
			adjustDate(+1);
		//
		this.setCursor(Cursor.getDefaultCursor());
	}	//	actionPerformed

	/**
	 * 	Change Listener (Tab Pane)
	 * 	@param e event
	 */
	@Override
	public void stateChanged (ChangeEvent e)
	{
		displayCalendar();
	}	//	stateChanged

	/**
	 * 	Adjust Date
	 * 	@param diff difference
	 */
	private void adjustDate (int diff)
	{
		Timestamp date = fieldDate.getTimestamp();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		if (timePane.getSelectedIndex() == 0)
			cal.add(java.util.Calendar.DAY_OF_YEAR, diff);
		else if (timePane.getSelectedIndex() == 1)
			cal.add(java.util.Calendar.WEEK_OF_YEAR, diff);
		else
			cal.add(java.util.Calendar.MONTH, diff);
		//
		fieldDate.setValue(new Timestamp(cal.getTimeInMillis()));
		displayCalendar ();
	}	//	adjustDate

	/*************************************************************************/

	/**
	 * 	Callback.
	 * 	Called from VSchedulePanel after VAssignmentDialog finished
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
