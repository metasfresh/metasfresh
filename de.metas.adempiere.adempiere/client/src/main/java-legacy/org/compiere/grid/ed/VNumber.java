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

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.logging.Level;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.text.Document;

import org.adempiere.plaf.VEditorDialogButtonAlign;
import org.adempiere.plaf.VEditorUI;
import org.adempiere.ui.editor.ICopyPasteSupportEditor;
import org.adempiere.ui.editor.ICopyPasteSupportEditorAware;
import org.adempiere.ui.editor.NullCopyPasteSupportEditor;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.apps.AEnv;
import org.compiere.grid.ed.menu.EditorContextPopupMenu;
import org.compiere.model.GridField;
import org.compiere.swing.CTextField;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import com.google.common.base.Strings;

/**
 *	Number Control
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: VNumber.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 			<li>BF [ 1739516 ] Warning on numeric field with range set
 * 			<li>BF [ 1834393 ] VNumber.setFocusable not working
 */
public final class VNumber extends JComponent
	implements VEditor2, ActionListener, KeyListener, FocusListener, VManagedEditor
	, ICopyPasteSupportEditorAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1473227631807352033L;

	/**	Number of Columns (12)		*/
	public final static int SIZE = 12;
	/** Automatically pop up calculator */
	public final static boolean AUTO_POPUP = false;

	/**
	 *  IDE Bean Constructor
	 */
	public VNumber()
	{
		this("Number", false, false, true, DisplayType.Number, "Number");
	}   //  VNumber

	/**
	 *	Create right aligned Number field.
	 *	no popup, if WindowNo == 0 (for IDs)
	 *  @param columnName column name
	 *  @param mandatory mandatory
	 *  @param isReadOnly read only
	 *  @param isUpdateable updateable
	 *  @param displayType display type
	 *  @param title title
	 */
	public VNumber(String columnName, boolean mandatory, boolean isReadOnly, boolean isUpdateable,
		int displayType, String title)
	{
		super();
		super.setName(columnName);
		m_columnName = columnName;
		m_title = title;
		setDisplayType(displayType);
		
		//
		LookAndFeel.installBorder(this, "TextField.border");
		this.setLayout(new BorderLayout());

		//
		// Text
		VEditorUtils.setupInnerTextComponentUI(m_text);
		m_text.setName(columnName);
		m_text.setHorizontalAlignment(JTextField.TRAILING);
		m_text.addKeyListener(this);
		m_text.addFocusListener(this);
		this.add(m_text, BorderLayout.CENTER);
		// 
		// Swing will search for other places and other components to consume the KeyEvent 
		// So the event is not getting where it should, so we're forcing it
		// Task: 06110
		m_text.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				commitEdit(true);
			}
		});

		//
		// Button
		{
			m_button = VEditorUtils.createActionButton("Calculator", m_text);
			m_button.addActionListener(this);
			VEditorDialogButtonAlign.addVEditorButtonUsingBorderLayout(getClass(), this, m_button);
		}

		//
		//  Size
		setColumns(SIZE, 0);
		
		//
		//	ReadWrite
		setMandatory(mandatory);
		if (isReadOnly || !isUpdateable)
			setReadWrite(false);
		else
			setReadWrite(true);

		//
		// Create and bind the context menu
		new EditorContextPopupMenu(this);
	} // VNumber

	/**
	 *  Dispose
	 */
	@Override
	public void dispose()
	{
		m_text = null;
		m_button = null;
		m_mField = null;
	}   //  dispose

	/**
	 *	Set Document
	 *  @param doc document
	 */
	protected void setDocument(Document doc)
	{
		m_text.setDocument(doc);
	}	//	getDocument

	private final String m_columnName;
	protected int m_displayType;	//  Currency / UoM via Context
	private DecimalFormat	m_format;
	private final String m_title;
	// metas: volatile to make sure the state is propagated between GUI threads
	private volatile boolean m_setting;
	private String			m_oldText;
	private String			m_initialText;

	private boolean			m_rangeSet = false;
	private Double			m_minValue;
	private Double			m_maxValue;
	private boolean			m_modified = false;
	
	/**  The Field                  */
	private CTextField		m_text = new CTextField(SIZE);	//	Standard
	/** The Button                  */
	private VEditorActionButton m_button = null;

	private GridField          m_mField = null;
	/**	Logger			*/
	private static final CLogger log = CLogger.getCLogger(VNumber.class);

	/**
	 * Select all the number text.
	 */
	public void selectAll()
	{
		m_text.selectAll();
	}

	/**
	 * 	Set no of Columns
	 *	@param columns columns
	 *  @param height 0 to use default
	 */
	public void setColumns (final int columns, final int height)
	{
		m_text.setPreferredSize(null);
		m_text.setMinimumSize(null);
		m_text.setColumns(columns);
		if (height > 0)
		{
			Dimension preferredSize = m_text.getPreferredSize();
			Dimension minimumSize = m_text.getMinimumSize();
			m_text.setPreferredSize(new Dimension(preferredSize.width, height));
			m_text.setMinimumSize(new Dimension(minimumSize.width, height));
		}
		else
		{
			final int editorHeight = VEditorUI.getVEditorHeight();
			final String prototypeText = Strings.repeat("0", SIZE);
			final CTextField prototypeTextField = new CTextField(prototypeText);
			m_text.setPreferredSize(new Dimension(prototypeTextField.getPreferredSize().width, editorHeight));
			m_text.setMinimumSize(new Dimension(prototypeTextField.getMinimumSize().width, editorHeight));
		}

		//
		// Set editor's size based on text component size
		this.setPreferredSize(m_text.getPreferredSize());		//	causes r/o to be the same length
		this.setMinimumSize(m_text.getMinimumSize());
		this.setMaximumSize(new Dimension(Integer.MAX_VALUE, m_text.getPreferredSize().height)); // don't allow the component to grow horizontally
	}	//	setColumns
	
	/**
	 *	Set Range with min & max
	 *  @param minValue min value
	 *  @param maxValue max value
	 *	@return true, if accepted
	 */
	public boolean setRange(Double minValue, Double maxValue)
	{
		m_rangeSet = true;
		m_minValue = minValue;
		m_maxValue = maxValue;
		return m_rangeSet;
	}	//	setRange

	/**
	 *	Set Range with min & max = parse US style number w/o Gouping
	 *  @param minValue min value
	 *  @param maxValue max value
	 *  @return true if accepted
	 */
	public boolean setRange(String minValue, String maxValue)
	{
		if (minValue == null || maxValue == null)
			return false;
		try
		{
			m_minValue = Double.valueOf(minValue);
			m_maxValue = Double.valueOf(maxValue);
		}
		catch (NumberFormatException nfe)
		{
			return false;
		}
		m_rangeSet = true;
		return m_rangeSet;
	}	//	setRange

	/**
	 * Set and check DisplayType
	 * 
	 * @param displayType display type
	 */
	public void setDisplayType(int displayType)
	{
		m_displayType = displayType;
		if (!DisplayType.isNumeric(displayType))
		{
			m_displayType = DisplayType.Number;
		}

		final DecimalFormat format = DisplayType.getNumberFormat(displayType);
		setDecimalFormat(format);
	}   // setDisplayType

	/**
	 *	Set ReadWrite
	 *  @param value value
	 */
	@Override
	public void setReadWrite (boolean value)
	{
		if (m_text.isReadWrite() != value)
			m_text.setReadWrite(value);
		
		m_button.setReadWrite(value);
		
		setFocusable(value == true);
	}	//	setReadWrite

	/**
	 *	IsReadWrite
	 *  @return true if rw
	 */
	@Override
	public boolean isReadWrite()
	{
		return m_text.isReadWrite();
	}	//	isReadWrite

	/**
	 *	Set Mandatory (and back bolor)
	 *  @param mandatory mandatory
	 */
	@Override
	public void setMandatory (boolean mandatory)
	{
		m_text.setMandatory(mandatory);
	}	//	setMandatory

	/**
	 *	Is it mandatory
	 *  @return true if mandatory
	 */
	@Override
	public boolean isMandatory()
	{
		return m_text.isMandatory();
	}	//	isMandatory

	/**
	 *	Set Background
	 *  @param color color
	 */
	@Override
	public void setBackground(Color color)
	{
		m_text.setBackground(color);
	}	//	setBackground

	/**
	 *	Set Background
	 *  @param error error
	 */
	@Override
	public void setBackground (boolean error)
	{
		m_text.setBackground(error);
	}	//	setBackground

	/**
	 *  Set Foreground
	 *  @param fg foreground
	 */
	@Override
	public void setForeground(Color fg)
	{
		m_text.setForeground(fg);
	}   //  setForeground

	/**
	 *	Set Editor to value
	 *  @param value value
	 */
	@Override
	public void setValue(Object value)
	{
		log.finest("Value=" + value);
		if (value == null)
			m_oldText = "";
		else
			m_oldText = m_format.format(value);
		// metas: begin
		m_oldTextObj = value;
		// make sure we have the same scale because else BigDecimal.equals method will return false
		if (m_oldTextObj instanceof BigDecimal && m_format != null && ((BigDecimal)m_oldTextObj).scale() < m_format.getMaximumFractionDigits())
			m_oldTextObj = ((BigDecimal)m_oldTextObj).setScale(m_format.getMaximumFractionDigits());
		// metas: end
		//	only set when not updated here
		if (m_setting)
			return;
		m_text.setText (m_oldText);
		m_initialText = m_oldText;
		m_initialTextObj = m_oldTextObj; // metas
		m_modified = false;
	}	//	setValue

	/**
	 * 	Request Focus
	 */
	@Override
	public void requestFocus ()
	{
		if (log.isLoggable(Level.FINE)) // 07068: logging when requestFocus is actually invoked
		{
			log.fine(this + ": Invoking requestFocus()");
		}
		m_text.requestFocus ();
	}	//	requestFocus
	
	/**
	 *  Property Change Listener
	 *  @param evt event
	 */
	@Override
	public void propertyChange (PropertyChangeEvent evt)
	{
		if (evt.getPropertyName().equals(org.compiere.model.GridField.PROPERTY))
			setValue(evt.getNewValue());

		// metas: request focus (2009_0027_G131)
		if (evt.getPropertyName().equals(org.compiere.model.GridField.REQUEST_FOCUS))
			requestFocus();
		// metas end
	}   //  propertyChange

	/**
	 *	Return Editor value
	 *  @return value value (big decimal or integer)
	 */
	@Override
	public Object getValue()
	{
		if (m_text == null || m_text.getText() == null || m_text.getText().length() == 0)
			return null;
		String value = m_text.getText();
		//	return 0 if text deleted
		if (value == null || value.length() == 0)
		{
			if (!m_modified)
				return null;
			if (m_displayType == DisplayType.Integer)
				return new Integer(0);
			return Env.ZERO;
		}
		if (value.equals(".") || value.equals(",") || value.equals("-"))
			value = "0";
		// arboleda - solve bug [ 1759771 ] Parse exception when you enter ".." in a numeric field
		if (value.equals("..")) {
			value = "0";
			m_text.setText(".");
		}
		try
		{
			Number number = m_format.parse(value);
			value = number.toString();      //	converts it to US w/o thousands
			BigDecimal bd = new BigDecimal(value);
			if (m_displayType == DisplayType.Integer)
				return new Integer(bd.intValue());
			if (bd.signum() == 0)
				return bd;
			return bd.setScale(m_format.getMaximumFractionDigits(), BigDecimal.ROUND_HALF_UP);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "Value=" + value, e);
		}
		m_text.setText(m_format.format(0));
		if (m_displayType == DisplayType.Integer)
			return new Integer(0);
		return Env.ZERO;
	}	//	getValue

	/**
	 *  Return Display Value
	 *  @return value
	 */
	@Override
	public String getDisplay()
	{
		return m_text.getText();
	}   //  getDisplay

	/**
	 * 	Get Title
	 *	@return title
	 */
	public String getTitle()
	{
		return m_title;
	}	//	getTitle

	/**
	 * 	Plus - add one.
	 * 	Also sets Value
	 *	@return new value
	 */
	public Object plus()
	{
		Object value = getValue();
		if (value == null)
		{
			if (m_displayType == DisplayType.Integer)
				value = new Integer(0);
			else
				value = Env.ZERO;
		}
		//	Add
		if (value instanceof BigDecimal)
			value = ((BigDecimal)value).add(Env.ONE);
		else
			value = new Integer(((Integer)value).intValue() + 1);
		//
		setValue(value);
		return value;
	}	//	plus
	
	/**
	 * 	Minus - subtract one, but not below minimum.
	 * 	Also sets Value
	 *	@param minimum minimum
	 *	@return new value
	 */
	public Object minus (int minimum)
	{
		Object value = getValue();
		if (value == null)
		{
			if (m_displayType == DisplayType.Integer)
				value = new Integer(minimum);
			else
				value = new BigDecimal(minimum);
			setValue(value);
			return value;
		}
		
		//	Subtract
		if (value instanceof BigDecimal)
		{
			BigDecimal bd = ((BigDecimal)value).subtract(Env.ONE);
			BigDecimal min = new BigDecimal(minimum);
			if (bd.compareTo(min) < 0)
				value = min;
			else
				value = bd;
		}
		else
		{
			int i = ((Integer)value).intValue();
			i--;
			if (i < minimum)
				i = minimum;
			value = new Integer(i);
		}
		//
		setValue(value);
		return value;
	}	//	minus
	
	/**************************************************************************
	 *	Action Listener
	 *  @param e event
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		if (e.getSource() == m_button)
		{
			try(final IAutoCloseable buttonDisabled = m_button.temporaryDisable())
			{
				String str = startCalculator(this, m_text.getText(), m_format, m_displayType, m_title);
				m_text.setText(str);
			}
			//
			try
			{
				fireVetoableChange (m_columnName, m_oldTextObj, getValue()); // metas
			}
			catch (PropertyVetoException pve)	{}
			m_text.requestFocus();
		}
	}	//	actionPerformed

	/**************************************************************************
	 *	Key Listener Interface
	 *  @param e event
	 */
	@Override
	public void keyTyped(KeyEvent e)    {}
	@Override
	public void keyPressed(KeyEvent e)  {}

	/**
	 *	Key Listener.
	 *		- Escape 		- Restore old Text
	 *		- firstChange	- signal change
	 *  @param e event
	 */
	@Override
	public void keyReleased(KeyEvent e)
	{
		log.finest("Key=" + e.getKeyCode() + " - " + e.getKeyChar()
			+ " -> " + m_text.getText());

		//  ESC
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			m_text.setText(m_initialText);
		
		m_modified = true;
		
		// cg: task: 06110 : start
		final boolean commit = e.getKeyCode() == KeyEvent.VK_ENTER; 
		commitEdit(commit);

	}	//	keyReleased

	
	private void commitEdit(final boolean commit)
	{
		m_setting = true;
		try
		{
			if (commit)
			{
				fireVetoableChange (m_columnName, m_oldTextObj, getValue()); // metas
				fireActionPerformed();
			}
			else	
			{				
				//	indicate change
				fireVetoableChange (m_columnName, m_oldTextObj, null); // metas
			}
		}
		catch (PropertyVetoException pve)
		{
			// nothing
		}
		finally
		{
			m_setting = false;
		}
	}
	// cg: task: 06110 : end
	
	/**
	 *	Focus Gained
	 *  @param e event
	 */
	@Override
	public void focusGained (FocusEvent e)
	{
		if (m_text != null)
			m_text.selectAll();
	}	//	focusGained

	/**
	 *	Data Binding to MTable (via GridController.vetoableChange).
	 *  @param e event
	 */
	@Override
	public void focusLost (FocusEvent e)
	{
		//	APanel - Escape
		// hengsin: bug [ 1890205 ]
		/*
		if (e.getOppositeComponent() instanceof AGlassPane)
		{
			m_text.setText(m_initialText);
			return;
		}*/
		commitChanges();
	}   //  focusLost

	@Override
	public void commitChanges() {
		Object oo = getValue();
		if (m_rangeSet)
		{
			String error = null;
			if (oo instanceof Integer)
			{
				Integer ii = (Integer)oo;
				if (ii  < m_minValue)
				{
					error = oo + " < " + m_minValue;
					oo = new Integer(m_minValue.intValue());
				}
				else if (ii > m_maxValue)
				{
					error = oo + " > " + m_maxValue;
					oo = new Integer(m_maxValue.intValue());
				}
			}
			else if (oo instanceof BigDecimal)
			{
				BigDecimal bd = (BigDecimal)oo;
				if (bd.doubleValue()  < m_minValue)
				{
					error = oo + " < " + m_minValue;
					oo = new BigDecimal(m_minValue);
				}
				else if (bd.doubleValue() > m_maxValue)
				{
					error = oo + " > " + m_maxValue;
					oo = new BigDecimal(m_maxValue);
				}
			}
			if (error != null)
				log.warning(error);
		}
		try
		{
			fireVetoableChange (m_columnName, m_initialTextObj, oo); // metas
			fireActionPerformed();
		}
		catch (PropertyVetoException pve)	
		{}
	}

	/**
	 *	Invalid Entry - Start Calculator
	 *  @param jc parent
	 *  @param value value
	 *  @param format format
	 *  @param displayType display type
	 *  @param title title
	 *  @return value
	 */
	public static String startCalculator(Container jc, String value,
		DecimalFormat format, int displayType, String title)
	{
		log.config("Value=" + value);
		BigDecimal startValue = new BigDecimal(0.0);
		try
		{
			if (value != null && value.length() > 0)
			{
				Number number = format.parse(value);
				startValue = new BigDecimal (number.toString());
			}
		}
		catch (ParseException pe)
		{
			log.info("InvalidEntry - " + pe.getMessage());
		}
		
		//	Find frame
		Frame frame = Env.getFrame(jc);
		//	Actual Call
		Calculator calc = new Calculator(frame, title,
			displayType, format, startValue);
		AEnv.showCenterWindow(frame, calc);
		BigDecimal result = calc.getNumber();
		log.config( "Result=" + result);
		//
		calc = null;
		if (result != null)
			return format.format(result);
		else
			return value;		//	original value
	}	//	startCalculator

	/**
	 *  Set Field/WindowNo for ValuePreference
	 *  @param mField field
	 */
	@Override
	public void setField (GridField mField)
	{
		m_mField = mField;

		EditorContextPopupMenu.onGridFieldSet(this);
	} // setField

	@Override
	public GridField getField() {
		return m_mField;
	}
	/*
	 * BF [ 1834393 ] VNumber.setFocusable not working
	 */
	@Override
	public void setFocusable(boolean value) {
		m_text.setFocusable(value);
	}

	
	/**************************************************************************
	 * 	Remove Action Listner
	 * 	@param l Action Listener
	 */
	public void removeActionListener(ActionListener l)
	{
		listenerList.remove(ActionListener.class, l);
	}	//	removeActionListener

	/**
	 * 	Add Action Listner
	 * 	@param l Action Listener
	 */
	@Override
	public void addActionListener(ActionListener l)
	{
		listenerList.add(ActionListener.class, l);
	}	//	addActionListener

	/**
	 * 	Fire Action Event to listeners
	 */
	protected void fireActionPerformed()
	{
		int modifiers = 0;
		AWTEvent currentEvent = EventQueue.getCurrentEvent();
		if (currentEvent instanceof InputEvent)
			modifiers = ((InputEvent)currentEvent).getModifiers();
		else if (currentEvent instanceof ActionEvent)
			modifiers = ((ActionEvent)currentEvent).getModifiers();
		ActionEvent ae = new ActionEvent (this, ActionEvent.ACTION_PERFORMED,
			"VNumber", EventQueue.getMostRecentEventTime(), modifiers);

		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying those that are interested in this event
		for (int i = listeners.length-2; i>=0; i-=2)
		{
			if (listeners[i]==ActionListener.class)
			{
				((ActionListener)listeners[i+1]).actionPerformed(ae);
			}
		}
	}	//	fireActionPerformed
	/**/

	@Override
	public boolean isDirty() {
		return m_modified;
	}

	@Override
	public void rollbackChanges() {
		m_text.setText (m_oldText);
		m_initialText = m_oldText;
		m_initialTextObj = m_oldTextObj;
		m_modified = false;
	}

// metas: begin
	private Object			m_oldTextObj;
	private Object			m_initialTextObj;
	
	@Override
	public boolean isAutoCommit()
	{
		return true;
	}
	
	public void setDecimalFormat(final DecimalFormat format)
	{
		this.m_format = format;
		m_text.setDocument(new MDocNumber(m_displayType, m_format, m_text, m_title));
	}

	public DecimalFormat getDecimalFormat()
	{
		return m_format;
	}

	// metas: end

	// metas
	@Override
	public void addMouseListener(MouseListener l)
	{
		m_text.addMouseListener(l);
	}

	/**
	 * This implementation always returns true.
	 */
	// task 05005
	@Override
	public boolean isRealChange(final PropertyChangeEvent e)
	{
		return true;
	}

	@Override
	public final ICopyPasteSupportEditor getCopyPasteSupport()
	{
		return m_text == null ? NullCopyPasteSupportEditor.instance : m_text.getCopyPasteSupport();
	}

} // VNumber
