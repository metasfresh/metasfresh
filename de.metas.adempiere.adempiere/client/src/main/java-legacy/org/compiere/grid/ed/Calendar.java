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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
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
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ButtonUI;

import org.adempiere.images.Images;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.plaf.CalendarUI;
import org.adempiere.plaf.VEditorUI;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.apps.AEnv;
import org.compiere.swing.CButton;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.ListComboBoxModel;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Language;

import com.google.common.collect.ImmutableList;

/**
 * Calendar Date & Time picker.
 * 
 * To create a new instance, please use {@link #builder()}.
 *
 * @author Jorg Janke
 */
public final class Calendar extends CDialog implements ActionListener, MouseListener, ChangeListener, KeyListener
{
	private static final long serialVersionUID = -1547404617639717922L;
	
	public static Builder builder()
	{
		return new Builder();
	}

	/**
	 *  Constructor
	 *  @param frame frame
	 *  @param title title
	 *  @param startTS start date/time
	 *  @param displayType DisplayType (Date, DateTime, Time)
	 */
	private Calendar (final Frame frame, final String title, final Timestamp startTS, final int displayType)
	{
		super (frame, title, true);
		log.info("startTS={}", startTS);
		log.info("displayType={}", displayType);
		
		m_displayType = DisplayType.isDate(displayType) ? displayType : DisplayType.Date;
		//
		try
		{
			jbInit();
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		}
		catch(Exception ex)
		{
			log.error("Calendar", ex);
		}
		
		//
		loadData(startTS);
	}
	
	/** Display Type				*/
	private final int m_displayType;
	/**	The Date					*/
	private GregorianCalendar	m_calendar;
	/** Is there a PM format		*/
	private boolean 			m_hasAM_PM = false;
	//
	private DayButton[] m_days;
	private DayButton m_today;
	/**	First Day of week			*/
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
	private static final transient Logger log = LogManager.getLogger(Calendar.class);
	//
	private CPanel monthPanel = new CPanel();
	private CComboBox<KeyNamePair> cMonth = new CComboBox<>();
	private JSpinner cYear = new JSpinner(new SpinnerNumberModel(2000, 1900,2100,1));
	private CPanel dayPanel = new CPanel();
	private CButton bMonthNext = new CButton();
	private CButton bMonthPrev = new CButton();
	private CPanel timePanel = new CPanel();
	
	private static final ImmutableList<String> HOURS12 = ImmutableList.copyOf(new String[] {
			"12"
			, " 1", " 2", " 3", " 4", " 5", " 6", " 7", " 8", " 9", "10"
			, "11"
	});
	private static final ImmutableList<String> HOURS24 = ImmutableList.copyOf(new String[] {
			"00", "01", "02", "03", "04", "05", "06", "07", "08", "09"
			, "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"
			, "20", "21", "22", "23"
	});

	private final CComboBox<String> fHour = new CComboBox<>();
	private final JSpinner fMinute = new JSpinner(new MinuteModel(5));	//	5 minute snap size
	private final JCheckBox cbPM = new JCheckBox();
	private final JLabel lTZ = new JLabel();
	private final CButton bOK = new CButton();

	/**
	 *	Static init
	 *  @throws Exception
	 */
	private void jbInit() throws Exception
	{
		final Image icon = Images.getImage2("Calendar16");
		if (icon != null)
		{
			setIconImage(icon);
		}
		
		final int componentHeight = VEditorUI.getVEditorHeight();

		this.setResizable(false);
		this.setUndecorated(false);
		this.addKeyListener(this);
		
		//
		final CPanel mainPanel = new CPanel(new BorderLayout(2, 2));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(4,4,4,2));
		setContentPane(mainPanel);

		//
		//	Month Panel
		{
			monthPanel.setLayout(new GridBagLayout());
			monthPanel.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
			monthPanel.setBackground(AdempierePLAF.getFormBackground());
			
			monthPanel.add(bMonthPrev,        new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
				,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			monthPanel.add(cMonth,  new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
					,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			monthPanel.add(bMonthNext,    new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
					,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			monthPanel.add(cYear,           new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
				,GridBagConstraints.SOUTHEAST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
			
			mainPanel.add(monthPanel, BorderLayout.NORTH);
			
			cMonth.setMinimumSize(new Dimension(30, componentHeight));
			cMonth.setMaximumSize(new Dimension(150, componentHeight));
			cMonth.addActionListener(this);
			//
			cYear.setMinimumSize(new Dimension(30, componentHeight));
			cYear.setMaximumSize(new Dimension(100, componentHeight));
			cYear.addChangeListener(this);
			//
			bMonthPrev.setIcon(Images.getImageIcon2("Parent16"));   // <
			bMonthPrev.setBorder(BorderFactory.createEmptyBorder());
			bMonthPrev.setBorderPainted(false);
			bMonthPrev.setContentAreaFilled(false);
			bMonthPrev.setMargin(new Insets(0, 0, 0, 0));
			bMonthPrev.setPreferredSize(new Dimension(componentHeight, componentHeight));
			bMonthPrev.addActionListener(this);
			//
			bMonthNext.setIcon(Images.getImageIcon2("Detail16"));   // >
			bMonthNext.setBorder(BorderFactory.createEmptyBorder());
			bMonthNext.setBorderPainted(false);
			bMonthNext.setContentAreaFilled(false);
			bMonthNext.setMargin(new Insets(0, 0, 0, 0));
			bMonthNext.setPreferredSize(new Dimension(componentHeight, componentHeight));
			bMonthNext.addActionListener(this);
		}

		//
		//	Day Panel
		{
			final GridLayout dayLayout = new GridLayout();
			dayLayout.setColumns(7);
			dayLayout.setHgap(2);
			dayLayout.setRows(7);
			dayLayout.setVgap(2);
			
			dayPanel.setLayout(dayLayout);
			dayPanel.setBackground(UIManager.getColor(CalendarUI.KEY_DayPanel_Background));
			dayPanel.setOpaque(true);
			mainPanel.add(dayPanel, BorderLayout.CENTER);
		}

		//
		//	Time Panel
		{
			final CLabel lTimeSep = new CLabel();
			lTimeSep.setText(" : ");
			
			timePanel.setLayout(new GridBagLayout());
			
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
			timePanel.add(Box.createHorizontalGlue(),      new GridBagConstraints(5, 0, 1, 1
					, 1.0, 0.0 // weightx, weighty
					,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			timePanel.add(bOK,      new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0
				,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 6, 0, 2), 0, 0));
			mainPanel.add(timePanel,  BorderLayout.SOUTH);
			
			fHour.setMinimumSize(new Dimension(20, componentHeight));
			fHour.setMaximumSize(new Dimension(30, componentHeight));
			fHour.setPreferredSize(new Dimension(45, componentHeight));
			fHour.addKeyListener(this);		//	Enter returns

			fMinute.setMinimumSize(new Dimension(20, componentHeight));
			fMinute.setMaximumSize(new Dimension(50, componentHeight));
			fMinute.setPreferredSize(new Dimension(45, componentHeight));
			((JSpinner.DefaultEditor)fMinute.getEditor()).getTextField().addKeyListener(this); // JSpinner ignores KeyListener
			fMinute.addChangeListener(this);
			
			cbPM.setMinimumSize(new Dimension(20, componentHeight));
			cbPM.setMaximumSize(new Dimension(50, componentHeight));
			cbPM.addActionListener(this);
			cbPM.addKeyListener(this);
			
			bOK.setIcon(Images.getImageIcon2("Ok16"));
			bOK.setMargin(new Insets(0,0,0,0));
			bOK.setBorder(BorderFactory.createEmptyBorder());
			bOK.setContentAreaFilled(false);
			bOK.setPreferredSize(new Dimension(componentHeight, componentHeight));
			bOK.addActionListener(this);
		}
	}	//	jbInit

	/**
	 *  Window Events - requestFocus
	 *  @param e event
	 */
	@Override
	protected void processWindowEvent(final WindowEvent e)
	{
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
	private void loadData (final Timestamp startTS)
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
		final SimpleDateFormat formatTime = (SimpleDateFormat)DateFormat.getTimeInstance(DateFormat.SHORT, loc);
		m_hasAM_PM = formatTime.toPattern().indexOf('a') != -1;
		if (m_hasAM_PM)
			cbPM.setText(formatTime.getDateFormatSymbols().getAmPmStrings()[1]);
		else
			cbPM.setVisible(false);

		//
		//	Years
		m_currentYear = m_calendar.get(java.util.Calendar.YEAR);
		cYear.setEditor(new JSpinner.NumberEditor(cYear, "0000"));
		cYear.setValue(m_currentYear);

		//
		//	Months		-> 0=Jan 12=_
		final SimpleDateFormat formatDate = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.LONG, loc);
		String[] months = formatDate.getDateFormatSymbols().getMonths();
		for (int monthNo = 0; monthNo < months.length; monthNo++)
		{
			final String monthName = months[monthNo];
			if ("".equals(monthName))
			{
				continue;
			}
			cMonth.addItem(new KeyNamePair(monthNo+1, monthName));
		}
		m_currentMonth = m_calendar.get(java.util.Calendar.MONTH) + 1;	//	Jan=0
		cMonth.setSelectedIndex(m_currentMonth - 1);

		//
		//	Week Days (labels) -> 0=_  1=Su  .. 7=Sa
		final String[] days = formatDate.getDateFormatSymbols().getShortWeekdays();	//	0 is blank, 1 is Sunday
		for (int i = m_firstDay; i < 7 + m_firstDay; i++)
		{
			int index = i > 7 ? i -7 : i;
			dayPanel.add(createWeekdayLabel(days[index]), null);
		}

		//
		//	Days
		m_currentDay = m_calendar.get(java.util.Calendar.DAY_OF_MONTH);
		m_days = new DayButton[6*7];
		for (int weekNo = 0; weekNo < 6; weekNo++)		//	six weeks a month maximum
		{
			for (int dayOfWeek = 0; dayOfWeek < 7; dayOfWeek++)	//	seven days
			{
				final int index = weekNo*7 + dayOfWeek;
				m_days[index] = createDay();
				dayPanel.add(m_days[index]);
			}
		}
		//	Today button
		m_days[m_days.length-1].setType(DayButtonType.SetCurrentDay);
		//	Cancel
		// metas-tsa: don't show the cancel button
		// m_days[m_days.length-2].setType(DayButtonType.Cancel);

		//	Date/Time
		m_current24Hour = m_calendar.get(java.util.Calendar.HOUR_OF_DAY);
		m_currentMinute = m_calendar.get(java.util.Calendar.MINUTE);
		fHour.setModel(new ListComboBoxModel<>(m_hasAM_PM ? HOURS12 : HOURS24));

		//
		//	What to show
		final boolean displayDate = m_displayType == DisplayType.DateTime || m_displayType == DisplayType.Date;
		final boolean displayTime = m_displayType == DisplayType.DateTime || m_displayType == DisplayType.Time;
		monthPanel.setVisible(displayDate);
		dayPanel.setVisible(displayDate);
		timePanel.setVisible(displayTime);
		lTZ.setVisible(m_displayType != DisplayType.Time);

		//	update UI from m_current...
		m_setting = false;
		updateDateToUI();
	}	//	loadData

	/**
	 *	Create Week Day Label
	 *  @param title Weekday Title
	 *  @return week day
	 */
	private JLabel createWeekdayLabel(final String title)
	{
		final JLabel weekLabel = new JLabel(title);
		weekLabel.setBorder(BorderFactory.createEmptyBorder());
		weekLabel.setHorizontalAlignment(SwingConstants.CENTER);
		weekLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		weekLabel.setRequestFocusEnabled(false);
		weekLabel.setBackground(UIManager.getColor(CalendarUI.KEY_DayPanel_WeekLabel_Background));
		weekLabel.setForeground(UIManager.getColor(CalendarUI.KEY_DayPanel_WeekLabel_Foreground));
		weekLabel.setOpaque(true);
		return weekLabel;
	}	//	createWeekday

	/**
	 *	Create Day Label
	 *  @return button
	 */
	private final DayButton createDay()
	{
		final DayButton button = new DayButton();
		button.addActionListener(this);
		button.addMouseListener(this);
		button.addKeyListener(this);

		return button;
	}

	/**************************************************************************
	 *	Set Calendar from m_current variables and update UI
	 */
	private void updateDateToUI()
	{
		if (m_setting)
			return;

		//
		// Set Month & Year
		m_setting = true;
		try
		{
			cMonth.setSelectedIndex(m_currentMonth - 1);
			cYear.setValue(m_currentYear);
		}
		finally
		{
			m_setting = false;
		}

		//
		//  Set Day
		{
			//	what is the first day in the selected month?
			m_calendar.set(m_currentYear, m_currentMonth - 1, 1);		// Month is zero based
			int dayOne = m_calendar.get(java.util.Calendar.DAY_OF_WEEK);
			int lastDay = m_calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
	
			//	convert to index
			dayOne -= m_firstDay;
			if (dayOne < 0)
				dayOne += 7;
			lastDay += dayOne - 1;
	
			//	for all buttons but the last
			int nextDay = 1;
			for (int i = 0; i < m_days.length - 2; i++)
			{
				final DayButton dayButton = m_days[i];
				if (i >= dayOne && i <= lastDay)
				{
					final int curDay = nextDay++;
					dayButton.setDayNo(curDay);
					if (m_currentDay == curDay)
					{
						dayButton.setType(DayButtonType.CurrentDay);
						m_today = dayButton;
						m_today.requestFocus();
					}
					else
					{
						dayButton.setType(DayButtonType.RegularDay);
					}
				}
				else
				{
					dayButton.setDayNo(-1);
					dayButton.setType(DayButtonType.Inactive);
				}
			}
		}

		//
		//	Set Hour/Minute/AM-PM/TimeZone to UI
		updateTimeToUI();

		//	Update Calendar
		updateCalendar();
	}	//	setCalendar
	
	/** Update {@link #m_calendar} from m_curret variables */
	private final void updateCalendar()
	{
		m_calendar.set(m_currentYear, m_currentMonth - 1, m_currentDay, m_current24Hour, m_currentMinute, 0);
		m_calendar.set(java.util.Calendar.MILLISECOND, 0);
	}

	/**
	 * 	Set Current Time from UI.
	 * 	- set m_current.. variables
	 */
	private void updateTimeFromUI()
	{
		//	Hour
		final int hour = fHour.getSelectedIndex();
		m_current24Hour = hour;
		if (m_hasAM_PM && cbPM.isSelected())
			m_current24Hour += 12;
		if (m_current24Hour < 0 || m_current24Hour > 23)
			m_current24Hour = 0;

		//	Minute
		final Integer minute = (Integer)fMinute.getValue();
		m_currentMinute = minute.intValue();
		if (m_currentMinute < 0 || m_currentMinute > 59)
			m_currentMinute = 0;
	}	//	setTime
	
	private void updateTimeToUI()
	{
		final boolean pm = m_current24Hour > 11;
		int hourIndex = m_current24Hour;
		if (pm && m_hasAM_PM)
			hourIndex -= 12;
		if (hourIndex < 0 || hourIndex >= fHour.getItemCount())
			hourIndex = 0;
		fHour.setSelectedIndex(hourIndex);
		//	Set Minute
		final int minute = m_calendar.get(java.util.Calendar.MINUTE);
		fMinute.setValue(new Integer(minute));
		//	Set PM
		cbPM.setSelected(pm);
		//	Set TZ
		final TimeZone tz = m_calendar.getTimeZone();
		lTZ.setText(tz.getDisplayName(tz.inDaylightTime(m_calendar.getTime()), TimeZone.SHORT));
	}

	/**
	 *	Return Time stamp
	 *  @return date and time
	 */
	public Timestamp getTimestamp()
	{
		//	Set Calendar
		updateCalendar();

		//	Return value
		if (m_abort || m_cancel)
		{
			return null;
		}
		
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
		
		updateTimeFromUI();

		if (e.getSource() == bOK)
		{
			m_abort = false;
			dispose();
			return;
		}
		else if (e.getSource() == bMonthPrev)
		{
			if (--m_currentMonth < 1)
			{
				m_currentMonth = 12;
				m_currentYear--;
			}
			m_lastDay = -1;
		}
		else if (e.getSource() == bMonthNext)
		{
			if (++m_currentMonth > 12)
			{
				m_currentMonth = 1;
				m_currentYear++;
			}
			m_lastDay = -1;
		}
		else if (e.getSource() instanceof DayButton)
		{
			final DayButton b = (DayButton)e.getSource();
			final DayButtonType type = b.getType();
			//	Today - Set to today's date
			if (type == DayButtonType.SetCurrentDay)
			{
				m_calendar.setTime(new Timestamp(System.currentTimeMillis()));
				m_currentDay = m_calendar.get(java.util.Calendar.DATE);
				m_currentMonth = m_calendar.get(java.util.Calendar.MONTH) + 1;
				m_currentYear = m_calendar.get(java.util.Calendar.YEAR);
			}
			//	Cancel
			else if (type == DayButtonType.Cancel)
			{
				m_cancel = true;
				dispose();
				return;
			}
			//	we have a day
			else if (b.isDayNo())
			{
				m_currentDay = b.getDayNo();
				final long currentClick = System.currentTimeMillis();
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
			updateTimeFromUI();
			m_lastDay = -1;
		}
		else
		{
			//	Set Month
			m_currentMonth = cMonth.getSelectedIndex()+1;
			m_lastDay = -1;
		}
		
		updateDateToUI();
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
			updateTimeFromUI();
			return;
		}
		
		//	Set Year
		m_currentYear = ((Integer)cYear.getValue()).intValue();
		m_lastDay = -1;
		
		updateDateToUI();
	}   //  stateChanged

	
	/**************************************************************************
	 *  Mouse Clicked
	 *  @param e Evant
	 */
	@Override
	public void mouseClicked(final MouseEvent e)
	{
		if (e.getClickCount() == 2)
		{
			m_abort = false;
			dispose();
		}
	}   //  mouseClicked

	
	/**************************************************************************
	 * 	Key Released - Return on enter
	 *  @param e event
	 */
	@Override
	public void keyReleased(final KeyEvent e)
	{
		//	Day Buttons
		if (e.getSource() instanceof DayButton)
		{
			int dayOffset = 0;
			int monthOffset = 0;
			if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN)
			{
				monthOffset = 1;
			}
			if (e.getKeyCode() == KeyEvent.VK_PAGE_UP)
			{
				monthOffset = -1;
			}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				dayOffset = 1;
			}
			else if (e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				dayOffset = -1;
			}
			else if (e.getKeyCode() == KeyEvent.VK_UP)
			{
				dayOffset = -7;
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				dayOffset = 7;
			}
			//
			if (dayOffset != 0 || monthOffset != 0)
			{
				m_calendar.add(java.util.Calendar.DAY_OF_YEAR, dayOffset);
				m_calendar.add(java.util.Calendar.MONTH, monthOffset);

				m_currentDay = m_calendar.get(java.util.Calendar.DAY_OF_MONTH);
				m_currentMonth = m_calendar.get(java.util.Calendar.MONTH) + 1;
				m_currentYear = m_calendar.get(java.util.Calendar.YEAR);
				updateDateToUI();
				return;
			}
		}
		
		//	Pressed Enter anywhere
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			m_abort = false;
			updateTimeFromUI();
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

		//	Modified Hour/Minute
		updateTimeFromUI();
		m_lastDay = -1;
	}	//	keyReleased

	/**
	 * 	keyTyped
	 *	@param e
	 */
	@Override
	public void keyTyped(KeyEvent e)
	{
		//nothing
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		//nothing
	}

	/**
	 * Calendar dialog builder
	 * 
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 */
	public static final class Builder
	{
		private Component parentComp;
		private String dialogTitle;
		private Timestamp date = null;
		private int displayType = DisplayType.Date;

		private Builder()
		{
			super();
		}
		
		/** Show the dialog and return the user selected date */
		public Timestamp buildAndGet()
		{
			final Frame parentFrame = getParentFrame();
			
			final Timestamp dateInitial = getDate();
			final Calendar calendarDialog = new Calendar(parentFrame, getDialogTitle(), dateInitial, getDisplayType());
			AEnv.showCenterWindow(parentFrame, calendarDialog); //
			if (calendarDialog.isCancel())
			{
				return dateInitial;
			}
			
			Timestamp datePicked = calendarDialog.getTimestamp();
			if (datePicked == null && !calendarDialog.isCancel())
			{
				datePicked = dateInitial;		// original
			}
			
			return datePicked;
		}
		
		// Container jc, Timestamp value, SimpleDateFormat format, int displayType, String title
		public Builder setParentComponent(final Component parentComp)
		{
			this.parentComp = parentComp;
			return this;
		}
		
		private final Frame getParentFrame()
		{
			return parentComp == null ? null : Env.getFrame(parentComp);
		}
		
		public Builder setDialogTitle(final String dialogTitle)
		{
			this.dialogTitle = dialogTitle;
			return this;
		}
		
		private final String getDialogTitle()
		{
			if(Check.isEmpty(dialogTitle))
			{
				return Services.get(IMsgBL.class).getMsg(Env.getCtx(), "Calendar");
			}
			return dialogTitle;
		}
	
		/** Sets initial date */
		public Builder setDate(final Timestamp date)
		{
			this.date = date;
			return this;
		}
		
		private final Timestamp getDate()
		{
			return date;
		}
		
		public Builder setDateFormat(final DateFormat dateFormat)
		{
			// not used
			// this.dateFormat = dateFormat;
			return this;
		}
		
		public Builder setDisplayType(final int displayType)
		{
			this.displayType = displayType;
			return this;
		}
		
		private final int getDisplayType()
		{
			return displayType;
		}
	}

	private static enum DayButtonType
	{
		Inactive,
		RegularDay,
		CurrentDay,
		SetCurrentDay,
		Cancel,
	}

	/**
	 * Day button
	 * @author metas-dev <dev@metasfresh.com>
	 */
	private static final class DayButton extends CButton 
	{
		private static final long serialVersionUID = 1L;
		
		private DayButtonType type = DayButtonType.Inactive;
		private int dayNo = -1;

		private DayButton()
		{
			super();
		}
		
		@Override
		public void setUI(final ButtonUI ui)
		{
			super.setUI(ui);
			
			setBorder(BorderFactory.createEmptyBorder());
			setHorizontalTextPosition(SwingConstants.CENTER);
			setVerticalAlignment(SwingConstants.CENTER);
			setMargin(ZERO_INSETS);
			setFocusPainted(false);
			putClientProperty("Plastic.is3D", Boolean.FALSE);
			
			updateUIFromType();
		}
		
		public final void setType(final DayButtonType type)
		{
			Check.assumeNotNull(type, "type not null");
			if (this.type == type)
			{
				return;
			}
			this.type = type;
			updateUIFromType();
		}
		
		public final DayButtonType getType()
		{
			return type;
		}
		
		public boolean isDayNo()
		{
			return type == DayButtonType.CurrentDay || type == DayButtonType.RegularDay;
		}
		
		public void setDayNo(final int dayNo)
		{
			this.dayNo = dayNo;
			if (isDayNo())
			{
				this.setText(String.valueOf(dayNo));
			}
		}
		
		public int getDayNo()
		{
			if (isDayNo())
			{
				return dayNo;
			}
			return -1;
		}

		private void updateUIFromType()
		{
			if (type == DayButtonType.Inactive || type == null)
			{
				setContentAreaFilled(false);
				setText("");
				setToolTipText(null);
				setReadWrite(false);
			}
			else if (type == DayButtonType.RegularDay)
			{
				setContentAreaFilled(true);
				setBackground(UIManager.getColor(CalendarUI.KEY_DayButton_Regular_Background));
				setForeground(UIManager.getColor(CalendarUI.KEY_DayButton_Regular_Foreground));
				setText(String.valueOf(getDayNo()));
				setToolTipText(null);
				setReadWrite(true);
			}
			else if (type == DayButtonType.CurrentDay)
			{
				setContentAreaFilled(true);
				setBackground(UIManager.getColor(CalendarUI.KEY_DayButton_Current_Background));
				setForeground(UIManager.getColor(CalendarUI.KEY_DayButton_Current_Foreground));
				setText(String.valueOf(getDayNo()));
				setToolTipText(null);
				setReadWrite(true);
			}
			else if (type == DayButtonType.SetCurrentDay)
			{
				setContentAreaFilled(true);
				setBackground(UIManager.getColor(CalendarUI.KEY_DayButton_SetCurrent_Background));
				setForeground(UIManager.getColor(CalendarUI.KEY_DayButton_SetCurrent_Foreground));
				setText("*");
				setToolTipText(Services.get(IMsgBL.class).getMsg(Env.getCtx(), "Today"));
				setReadWrite(true);
			}
			else if (type == DayButtonType.Cancel)
			{
				setContentAreaFilled(true);
				setBackground(UIManager.getColor(CalendarUI.KEY_DayButton_Cancel_Background));
				setForeground(UIManager.getColor(CalendarUI.KEY_DayButton_Cancel_Foreground));
				setText("X");
				setToolTipText(Services.get(IMsgBL.class).getMsg(Env.getCtx(), "Cancel"));
				setReadWrite(true);
			}
			else
			{
				throw new IllegalStateException("Unknown type: " + type); // shall not happen
			}
		}
	}

	/**
	 * Minute Spinner Model.
	 * 
	 * Based on Number Model - uses snap size to determine next value. Allows to manually set any minute, but return even snap value when spinner buttons are used.
	 *
	 * @author Jorg Janke
	 */
	private static final class MinuteModel extends SpinnerNumberModel
	{
		private static final long serialVersionUID = -7328155195096551848L;

		/**
		 * Constructor. Creates Integer Spinner with minimum=0, maximum=59, stepsize=1
		 * 
		 * @param snapSize snap size
		 */
		public MinuteModel(final int snapSize)
		{
			super(0, 0, 59, 1);	// Integer Model
			m_snapSize = snapSize;
		}	// MinuteModel

		/** Snap size */
		private final int m_snapSize;

		/**
		 * Return next full snap value
		 * 
		 * @return next snap value
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
			return steps * m_snapSize;
		}	// getNextValue

		/**
		 * Return previous full step value
		 * 
		 * @return previous snap value
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
			return steps * m_snapSize;
		}	// getNextValue
	}	// MinuteModel

}	//	Calendar
