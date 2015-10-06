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
package org.compiere.grid.ed;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.adempiere.images.Images;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.swing.CButton;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Language;

/**
 *  Pop up Calendar & Time
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: Calendar.java,v 1.3 2006/07/30 00:51:27 jjanke Exp $
 */
public class Calendar extends CDialog
	implements ActionListener, MouseListener, ChangeListener, KeyListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1547404617639717922L;

	/**
	 *  Mimimum Constructor for Date editor
	 *  @param frame frame
	 */
	public Calendar (Frame frame)
	{
		this (frame, Services.get(IMsgBL.class).getMsg(Env.getCtx(), "Calendar"), null, DisplayType.Date);
	}   //  Calendar

	/**
	 *  Constructor
	 *  @param frame frame
	 *  @param title title
	 *  @param startTS start date/time
	 *  @param displayType DisplayType (Date, DateTime, Time)
	 */
	public Calendar (Frame frame, String title, Timestamp startTS, int displayType)
	{
		super (frame, title, true);
		log.info(startTS==null ? "null" : startTS.toString() + " - DT=" + displayType);
		m_displayType = displayType;
		//
		try
		{
			jbInit();
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		}
		catch(Exception ex)
		{
			log.log(Level.SEVERE, "Calendar", ex);
		}
		//
		loadData(startTS);
	}	//	Calendar

	/** Display Type				*/
	private int					m_displayType;
	/**	The Date					*/
	private GregorianCalendar	m_calendar;
	/** Is there a PM format		*/
	private boolean 			m_hasAM_PM = false;
	//
	private CButton[] 			m_days;
	private CButton				m_today;
	/**	First Dat of week			*/
	private int 				m_firstDay;
	//
	private int					m_currentDay;
	private int					m_currentMonth;
	private int					m_currentYear;
	private int					m_current24Hour = 0;
	private int					m_currentMinute = 0;
	//
	private boolean				m_setting = true;
	/** Abort = ignore				*/
	private boolean             m_abort = true;
	/** Cancel = set null			*/
	private boolean				m_cancel = false;
	//
	private long				m_lastClick = System.currentTimeMillis();
	private int					m_lastDay = -1;
	//
	private static final Insets	ZERO_INSETS = new Insets(0,0,0,0);
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(Calendar.class);
	//
	private CPanel mainPanel = new CPanel();
	private CPanel monthPanel = new CPanel();
	private CComboBox cMonth = new CComboBox();
	private JSpinner cYear = new JSpinner(new SpinnerNumberModel(2000, 1900,2100,1));
	private BorderLayout mainLayout = new BorderLayout();
	private CPanel dayPanel = new CPanel();
	private GridLayout dayLayout = new GridLayout();
	private GridBagLayout monthLayout = new GridBagLayout();
	private CButton bNext = new CButton();
	private CButton bBack = new CButton();
	private CPanel timePanel = new CPanel();
	private CComboBox fHour = new CComboBox(getHours());
	private CLabel lTimeSep = new CLabel();
	private JSpinner fMinute = new JSpinner(new MinuteModel(5));	//	5 minute snap size
	private JCheckBox cbPM = new JCheckBox();
	private JLabel lTZ = new JLabel();
	private CButton bOK = new CButton();
	private GridBagLayout timeLayout = new GridBagLayout();

	/**
	 *	Static init
	 *  @throws Exception
	 */
	private void jbInit() throws Exception
	{
		this.addKeyListener(this);
		//
		mainPanel.setLayout(mainLayout);
		mainLayout.setHgap(2);
		mainLayout.setVgap(2);
		//mainPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(4,4,4,2));
		getContentPane().add(mainPanel);

		//	Month Panel
		monthPanel.setLayout(monthLayout);
		monthPanel.add(bBack,        new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		monthPanel.add(cYear,           new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
			,GridBagConstraints.SOUTHEAST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
		monthPanel.add(bNext,    new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		monthPanel.add(cMonth,  new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
			,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		monthPanel.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
		
		mainPanel.add(monthPanel, BorderLayout.NORTH);
		cMonth.addActionListener(this);
		cYear.addChangeListener(this);
		bBack.setIcon(Images.getImageIcon2("Parent16"));   // <
		bBack.setMargin(new Insets(0,0,0,0));
		bBack.addActionListener(this);
		bNext.setIcon(Images.getImageIcon2("Detail16"));   // >
		bNext.setMargin(new Insets(0,0,0,0));
		bNext.addActionListener(this);

		//	Day Panel
		dayPanel.setLayout(dayLayout);
		dayLayout.setColumns(7);
		dayLayout.setHgap(2);
		dayLayout.setRows(7);
		dayLayout.setVgap(2);
		dayPanel.setBackground(Color.white);
		dayPanel.setOpaque(true);
		mainPanel.add(dayPanel, BorderLayout.CENTER);

		//	Time Panel
		timePanel.setLayout(timeLayout);
		lTimeSep.setText(" : ");
		timePanel.add(fHour,     new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 6, 0, 0), 0, 0));
		timePanel.add(lTimeSep,     new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0
			,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		timePanel.add(fMinute,    new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		timePanel.add(cbPM,     new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
		timePanel.add(lTZ,      new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 4, 0, 0), 0, 0));
		timePanel.add(bOK,      new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 6, 0, 2), 0, 0));
		mainPanel.add(timePanel,  BorderLayout.SOUTH);
		fHour.addKeyListener(this);		//	Enter returns
		// JSpinner ignores KeyListener
		((JSpinner.DefaultEditor)fMinute.getEditor()).getTextField().addKeyListener(this);
		fMinute.addChangeListener(this);
		cbPM.addActionListener(this);
		cbPM.addKeyListener(this);
		bOK.setIcon(Images.getImageIcon2("Ok16"));
		bOK.setMargin(new Insets(0,1,0,1));
		bOK.addActionListener(this);
	}	//	jbInit

	/**
	 *  Window Events - requestFocus
	 *  @param e event
	 */
	@Override
	protected void processWindowEvent(WindowEvent e)
	{
	//	log.config( "Calendar.processWindowEvent", e);
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_OPENED)
		{
			if (m_displayType == DisplayType.Time)
				fHour.requestFocus();
			else if (m_today != null)
				m_today.requestFocus();
		}
	}   //  processWindowEvent

	
	/**************************************************************************
	 *	Load Data.
	 * 	- Years
	 *	- Month names
	 *	- Day Names
	 *  @param startTS time stamp
	 */
	private void loadData (Timestamp startTS)
	{
		final Language language = Env.getLanguage(Env.getCtx());
		final Locale loc = language.getLocale();
		m_calendar = new GregorianCalendar(loc);
		if (startTS == null)
			m_calendar.setTimeInMillis(System.currentTimeMillis());
		else
			m_calendar.setTime(startTS);
		
		// [ 1948445 ] Time field problem on daylight
		if (m_displayType == DisplayType.Time)
		{
			m_calendar.set(1970, 1, 1);
		}
		
		m_firstDay = m_calendar.getFirstDayOfWeek();
		
		//
		//	Short: h:mm a - HH:mm 	Long: h:mm:ss a z - HH:mm:ss z
		SimpleDateFormat formatTime = (SimpleDateFormat)DateFormat.getTimeInstance(DateFormat.SHORT, loc);
		m_hasAM_PM = formatTime.toPattern().indexOf('a') != -1;
		if (m_hasAM_PM)
			cbPM.setText(formatTime.getDateFormatSymbols().getAmPmStrings()[1]);
		else
			cbPM.setVisible(false);

		//	Years
		m_currentYear = m_calendar.get(java.util.Calendar.YEAR);
		cYear.setEditor(new JSpinner.NumberEditor(cYear, "0000"));
		cYear.setValue(new Integer(m_currentYear));

		//	Months		-> 0=Jan 12=_
		SimpleDateFormat formatDate = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.LONG, loc);
		String[] months = formatDate.getDateFormatSymbols().getMonths();
		for (int i = 0; i < months.length; i++)
		{
			KeyNamePair p = new KeyNamePair(i+1, months[i]);
			if (!months[i].equals(""))
				cMonth.addItem(p);
		}
		m_currentMonth = m_calendar.get(java.util.Calendar.MONTH) + 1;	//	Jan=0
		cMonth.setSelectedIndex(m_currentMonth-1);

		//	Week Days	-> 0=_  1=Su  .. 7=Sa
		String[] days = formatDate.getDateFormatSymbols().getShortWeekdays();	//	0 is blank, 1 is Sunday
		for (int i = m_firstDay; i < 7 + m_firstDay; i++)
		{
			int index = i > 7 ? i -7 : i;
			dayPanel.add(createWeekday(days[index]), null);
		}

		//	Days
		m_days = new CButton[6*7];
		m_currentDay = m_calendar.get(java.util.Calendar.DATE);
		for (int i = 0; i < 6; i++)		//	six weeks a month maximun
			for (int j = 0; j < 7; j++)	//	seven days
			{
				int index = i*7 + j;
				m_days[index] = createDay();
				dayPanel.add(m_days[index], null);
			}

		//	Today button
		m_days[m_days.length-1].setBackground(Color.green);
		m_days[m_days.length-1].setText("*");
		m_days[m_days.length-1].setToolTipText(Services.get(IMsgBL.class).getMsg(Env.getCtx(), "Today"));
		//	Cancel
		m_days[m_days.length-2].setBackground(Color.red);
		m_days[m_days.length-2].setText("x");
		m_days[m_days.length-2].setToolTipText(Services.get(IMsgBL.class).getMsg(Env.getCtx(), "Cancel"));

		//	Date/Time
		m_current24Hour = m_calendar.get(java.util.Calendar.HOUR_OF_DAY);
		m_currentMinute = m_calendar.get(java.util.Calendar.MINUTE);

		//	What to show
		timePanel.setVisible(m_displayType == DisplayType.DateTime || m_displayType == DisplayType.Time);
		monthPanel.setVisible(m_displayType != DisplayType.Time);
		dayPanel.setVisible(m_displayType != DisplayType.Time);
		lTZ.setVisible(m_displayType != DisplayType.Time);

		//	update UI from m_current...
		m_setting = false;
		setCalendar();
	}	//	loadData

	/**
	 *	Create Week Day Label
	 *  @param title Weedkay Title
	 *  @return week day
	 */
	private JLabel createWeekday (String title)
	{
		JLabel label = new JLabel(title);
		//label.setBorder(BorderFactory.createRaisedBevelBorder());
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setRequestFocusEnabled(false);
		label.setBackground(AdempierePLAF.getPrimary1());
		label.setForeground(Color.white);
		label.setOpaque(true);
		return label;
	}	//	createWeekday

	/**
	 *	Create Day Label
	 *  @return button
	 */
	private CButton createDay()
	{
		CButton button = new CButton();
		//button.setBorder(BorderFactory.createLoweredBevelBorder());
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setMargin(ZERO_INSETS);
		button.addActionListener(this);
		button.addMouseListener(this);
		button.addKeyListener(this);
		button.setFocusPainted(false);
		button.putClientProperty("Plastic.is3D", Boolean.FALSE);
		return button;
	}	//	createWeekday

	/**
	 * 	Create 12/25 hours
	 *  @return Array with hours as String
	 */
	private Object[] getHours()
	{
		final Language language = Env.getLanguage(Env.getCtx());
		final Locale loc = language.getLocale();
		
		//	Short: h:mm a - HH:mm 	Long: h:mm:ss a z - HH:mm:ss z
		SimpleDateFormat formatTime = (SimpleDateFormat)DateFormat.getTimeInstance(DateFormat.SHORT, loc);
		m_hasAM_PM = formatTime.toPattern().indexOf('a') != -1;
		//
		Object[] retValue = new Object[m_hasAM_PM ? 12 : 24];
		if (m_hasAM_PM)
		{
			retValue[0] = "12";
			for (int i = 1; i < 10; i++)
				retValue[i] = " " + String.valueOf(i);
			for (int i = 10; i < 12; i++)
				retValue[i] = String.valueOf(i);
		}
		else
		{
			for (int i = 0; i < 10; i++)
				retValue[i] = "0" + String.valueOf(i);
			for (int i = 10; i < 24; i++)
				retValue[i] = String.valueOf(i);
		}
		return retValue;
	}	//	getHours


	/**************************************************************************
	 *	Set Calandar from m_current variables and update UI
	 */
	private void setCalendar()
	{
		if (m_setting)
			return;
	//	log.config( "Calendar.setCalendar");

		//  --- Set Month & Year
		m_setting = true;
		cMonth.setSelectedIndex(m_currentMonth-1);
		cYear.setValue(new Integer(m_currentYear));
		m_setting = false;

		//  --- Set Day
		//	what is the first day in the selected month?
		m_calendar.set(m_currentYear, m_currentMonth-1, 1);		//	Month is zero based
		int dayOne = m_calendar.get(java.util.Calendar.DAY_OF_WEEK);
		int lastDate = m_calendar.getActualMaximum(java.util.Calendar.DATE);

		//	convert to index
		dayOne -= m_firstDay;
		if (dayOne < 0)
			dayOne += 7;
		lastDate += dayOne - 1;

		//	for all buttons but the last
		int curDay = 1;
		for (int i = 0; i < m_days.length-2; i++)
		{
			if (i >= dayOne && i <= lastDate)
			{
				if (m_currentDay == curDay)
				{
					m_days[i].setBackground(Color.blue);
					m_days[i].setForeground(Color.yellow);
					m_today = m_days[i];
					m_today.requestFocus();
				}
				else
				{
					m_days[i].setBackground(Color.white);
					m_days[i].setForeground(Color.black);
				}
				m_days[i].setText(String.valueOf(curDay++));
				m_days[i].setReadWrite(true);
			}
			else
			{
				m_days[i].setText("");
				m_days[i].setReadWrite(false);
				m_days[i].setBackground(AdempierePLAF.getFieldBackground_Inactive());
			}
		}

		//	Set Hour
		boolean pm = m_current24Hour > 11;
		int index = m_current24Hour;
		if (pm && m_hasAM_PM)
			index -= 12;
		if (index < 0 || index >= fHour.getItemCount())
			index = 0;
		fHour.setSelectedIndex(index);
		//	Set Minute
		int m = m_calendar.get(java.util.Calendar.MINUTE);
		fMinute.setValue(new Integer(m));
		//	Set PM
		cbPM.setSelected(pm);
		//	Set TZ
		TimeZone tz = m_calendar.getTimeZone();
		lTZ.setText(tz.getDisplayName(tz.inDaylightTime(m_calendar.getTime()), TimeZone.SHORT));

		//	Update Calendar
		m_calendar.set(m_currentYear, m_currentMonth-1, m_currentDay, m_current24Hour, m_currentMinute, 0);
		m_calendar.set(java.util.Calendar.MILLISECOND, 0);
	}	//	setCalendar

	/**
	 * 	Set Current Time from UI.
	 * 	- set m_current.. variables
	 */
	private void setTime()
	{
		//	Hour
		int h = fHour.getSelectedIndex();
		m_current24Hour = h;
		if (m_hasAM_PM && cbPM.isSelected())
			m_current24Hour += 12;
		if (m_current24Hour < 0 || m_current24Hour > 23)
			m_current24Hour = 0;

		//	Minute
		Integer ii = (Integer)fMinute.getValue();
		m_currentMinute = ii.intValue();
		if (m_currentMinute < 0 || m_currentMinute > 59)
			m_currentMinute = 0;
	}	//	setTime

	/**
	 *	Return Time stamp
	 *  @return date and time
	 */
	public Timestamp getTimestamp()
	{
	//	log.config( "Calendar.getTimeStamp");
		//	Set Calendar
		m_calendar.set(m_currentYear, m_currentMonth-1, m_currentDay, m_current24Hour, m_currentMinute, 0);
		m_calendar.set(java.util.Calendar.MILLISECOND, 0);

		//	Return value
		if (m_abort || m_cancel)
			return null;
		long time = m_calendar.getTimeInMillis();
		if (m_displayType == DisplayType.Date)
			time = new java.sql.Date(time).getTime();
		else if (m_displayType == DisplayType.Time)
			time = new Time(time).getTime();	//	based on 1970-01-01
		return new Timestamp(time);
	}	//	getTimestamp

	/**
	 * 	Cancel button pressed
	 *	@return true if canceled
	 */
	public boolean isCancel()
	{
		return m_cancel;
	}	//	isCancel
	
	/**************************************************************************
	 *	Action Listener for Month/Year combo & dat buttons.
	 *	- Double clicking on a date closes it
	 *  - set m_current...
	 *  @param e Event
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		if (m_setting)
			return;
	//	log.config( "Calendar.actionPerformed");
		setTime();

		if (e.getSource() == bOK)
		{
			m_abort = false;
			dispose();
			return;
		}
		else if (e.getSource() == bBack)
		{
			if (--m_currentMonth < 1)
			{
				m_currentMonth = 12;
				m_currentYear--;
			}
			m_lastDay = -1;
		}
		else if (e.getSource() == bNext)
		{
			if (++m_currentMonth > 12)
			{
				m_currentMonth = 1;
				m_currentYear++;
			}
			m_lastDay = -1;
		}
		else if (e.getSource() instanceof JButton)
		{
			JButton b = (JButton)e.getSource();
			String text = b.getText();
			//	Today - Set to today's date
			if (text.equals("*"))
			{
				m_calendar.setTime(new Timestamp(System.currentTimeMillis()));
				m_currentDay = m_calendar.get(java.util.Calendar.DATE);
				m_currentMonth = m_calendar.get(java.util.Calendar.MONTH) + 1;
				m_currentYear = m_calendar.get(java.util.Calendar.YEAR);
			}
			//	Cancel
			else if (text.equals("x"))
			{
				m_cancel = true;
				dispose();
				return;
			}
			//	we have a day
			else if (text.length() > 0)
			{
				m_currentDay = Integer.parseInt(text);
				long currentClick = System.currentTimeMillis();
				if (m_currentDay == m_lastDay
					&& currentClick-m_lastClick < 1000)		//  double click 1 second
				{
					m_abort = false;
					dispose();
					return;
				}
				m_lastClick = currentClick;
				m_lastDay = m_currentDay;
			}
		}
		else if (e.getSource() == cbPM)
		{
			setTime();
			m_lastDay = -1;
		}
		else
		{
			//	Set Month
			m_currentMonth = cMonth.getSelectedIndex()+1;
			m_lastDay = -1;
		}
		setCalendar();
	}	//	actionPerformed

	/**
	 *  ChangeListener (Year/Minute Spinner)
	 *  @param e Event
	 */
	@Override
	public void stateChanged(ChangeEvent e)
	{
		if (m_setting)
			return;

		//	Set Minute
		if (e.getSource() == fMinute)
		{
			setTime();
			return;
		}
		//	Set Year
		m_currentYear = ((Integer)cYear.getValue()).intValue();
		m_lastDay = -1;
		setCalendar();
	}   //  stateChanged

	
	/**************************************************************************
	 *  Mouse Clicked
	 *  @param e Evant
	 */
	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (e.getClickCount() == 2)
		{
			m_abort = false;
			dispose();
		}
	}   //  mouseClicked

	/**
	 * 	mousePressed
	 *	@param e
	 */
	@Override
	public void mousePressed(MouseEvent e) {}
	/**
	 * 	mouseEntered
	 *	@param e
	 */
	@Override
	public void mouseEntered(MouseEvent e) {}
	/**
	 * 	mouseExited
	 *	@param e
	 */
	@Override
	public void mouseExited(MouseEvent e) {}
	/**
	 * 	mouseReleased
	 *	@param e
	 */
	@Override
	public void mouseReleased(MouseEvent e) {}

	
	/**************************************************************************
	 * 	Key Released - Return on enter
	 *  @param e event
	 */
	@Override
	public void keyReleased(KeyEvent e)
	{
	//	System.out.println("Released " + e);
		//	Day Buttons
		if (e.getSource() instanceof JButton)
		{
			if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN)
			{
				if (++m_currentMonth > 12)
				{
					 m_currentMonth = 1;
					 m_currentYear++;
				}
				setCalendar();
				return;
			}
			if (e.getKeyCode() == KeyEvent.VK_PAGE_UP)
			{
				if (--m_currentMonth < 1)
				{
					 m_currentMonth = 12;
					 m_currentYear--;
				}
				setCalendar();
				return;
			}

			//	Arrows
			int offset = 0;
			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				offset = 1;
			else if (e.getKeyCode() == KeyEvent.VK_LEFT)
				offset = -1;
			else if (e.getKeyCode() == KeyEvent.VK_UP)
				offset = -7;
			else if (e.getKeyCode() == KeyEvent.VK_DOWN)
				offset = 7;
			if (offset != 0)
			{
				System.out.println(m_calendar.getTime() + "  offset=" + offset);
				m_calendar.add(java.util.Calendar.DAY_OF_YEAR, offset);
				System.out.println(m_calendar.getTime());

				m_currentDay = m_calendar.get(java.util.Calendar.DAY_OF_MONTH);
				m_currentMonth = m_calendar.get(java.util.Calendar.MONTH) + 1;
				m_currentYear = m_calendar.get(java.util.Calendar.YEAR);
				setCalendar();
				return;
			}
			//	something else
			actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, ""));
		}

		//	Pressed Enter anywhere
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			m_abort = false;
			setTime();
			dispose();
			return;
		}
		
		//	ESC = Cancel - teo_sarca, [ 1660164 ]
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			m_cancel = true;
			dispose();
			return;
		}

		//	Modified Hour/Miinute
		setTime();
		m_lastDay = -1;
	}	//	keyReleased

	/**
	 * 	keyTyped
	 *	@param e
	 */
	@Override
	public void keyTyped(KeyEvent e)
	{
	//	System.out.println("Typed " + e);
	}
	/**
	 * 	keyPressed
	 *	@param e
	 */
	@Override
	public void keyPressed(KeyEvent e)
	{
	//	System.out.println("Pressed " + e);
	}

}	//	Calendar

/**
 *	Minute Spinner Model.
 *  Based on Number Model - uses snap size to determine next value.
 *  Allows to manually set any ninute, but return even snap value
 *  when spinner buttons are used.
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: Calendar.java,v 1.3 2006/07/30 00:51:27 jjanke Exp $
 */
class MinuteModel extends SpinnerNumberModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7328155195096551848L;

	/**
	 *  Constructor.
	 *  Creates Integer Spinner with minimum=0, maximum=59, stepsize=1
	 *  @param snapSize snap size
	 */
	public MinuteModel(int snapSize)
	{
		super(0,0,59, 1);	//	Integer Model
		m_snapSize = snapSize;
	}	//	MinuteModel

	/**	Snap size			*/
	private int		m_snapSize;

	/**
	 * 	Return next full snap value
	 *  @return next snap value
	 */
	@Override
	public Object getNextValue()
	{
		int minutes = ((Integer)getValue()).intValue();
		minutes += m_snapSize;
		if (minutes >= 60)
			minutes -= 60;
		//
		int steps = minutes / m_snapSize;
		return new Integer(steps * m_snapSize);
	}	//	getNextValue


	/**
	 * 	Return previous full step value
	 *  @return previous snap value
	 */
	@Override
	public Object getPreviousValue()
	{
		int minutes = ((Integer)getValue()).intValue();
		minutes -= m_snapSize;
		if (minutes < 0)
			minutes += 60;
		//
		int steps = minutes / m_snapSize;
		if (minutes % m_snapSize != 0)
			steps++;
		if (steps * m_snapSize > 59)
			steps = 0;
		return new Integer(steps * m_snapSize);
	}	//	getNextValue

}	//	MinuteModel
