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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.text.DefaultCaret;
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
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.SwingUtils;
import org.slf4j.Logger;

import com.google.common.base.Strings;

import de.metas.logging.LogManager;

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
	implements VEditor2
	, VManagedEditor
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
		m_text.addKeyListener(textFieldKeyListener);
		m_text.addFocusListener(textFieldFocusListener);
		
		// Use DefaultCaret instead of com.jgoodies.looks.plastic.PlasticFieldCaret,
		// because there is a usability bug in com.jgoodies.looks.plastic.PlasticFieldCaret.focusGained which invokes later an selectAll() even if the current field is empty.
		// As a result, if in meantime (before the selectAll() is actually executed) we are filling the field (i.e. user presses a key) that text will be selected,
		// so when the user presses the next key, it will override the first one.
		m_text.setCaret(new DefaultCaret());
		
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
			m_button.addActionListener(new ActionListener()
			{
				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					onActionButtonPressed();
				}
			});
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
		
		actionCancelEdit.installTo(this, WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	} // VNumber

	/**
	 *  Dispose
	 */
	@Override
	public void dispose()
	{
		if (m_text != null)
		{
			m_text.removeFocusListener(textFieldFocusListener);
			m_text = null;
		}
		
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

	/**	Logger			*/
	private static final Logger log = LogManager.getLogger(VNumber.class);

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
	
	private final VEditorAction actionCancelEdit = new VEditorAction("cancelEdit", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0))
	{
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(final ActionEvent e)
		{
			cancelEdit();
		}
	};

	
	private boolean skipNextSelectAllOnFocusGained = false;
	/**
	 * Listen {@link #m_text}'s focus gain/lost events and
	 * <ul>
	 * <li>selects all text when the text component gains focus
	 * </ul>
	 */
	private FocusListener textFieldFocusListener = new FocusListener()
	{
		@Override
		public void focusGained(final FocusEvent e)
		{
			if(e.isTemporary())
			{
				return;
			}

			// Select all on focus gained
			if (!skipNextSelectAllOnFocusGained)
			{
				m_text.selectAll();
			}
			skipNextSelectAllOnFocusGained = false;
		}
		
		@Override
		public void focusLost(final FocusEvent e)
		{
			if(e.isTemporary())
			{
				return;
			}
			
			commitChanges();
		}
	};
	
	private final KeyListener textFieldKeyListener = new KeyAdapter()
	{
		@Override
		public void keyReleased(final KeyEvent e)
		{
			m_modified = true;

			// cg: task: 06110 : start
			final boolean commit = e.getKeyCode() == KeyEvent.VK_ENTER; 
			commitEdit(commit);
		}
	};

	/**
	 * Select all the number text.
	 */
	public void selectAll()
	{
		if(m_text == null)
		{
			return;
		}
		
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
	 *  @param readWrite value
	 */
	@Override
	public void setReadWrite (final boolean readWrite)
	{
		if (m_text.isReadWrite() != readWrite)
			m_text.setReadWrite(readWrite);
		
		m_button.setReadWrite(readWrite);
		
		setFocusable(readWrite == true);
		
		actionCancelEdit.setEnabled(readWrite);
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
	 *	Set Mandatory (and background color)
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
	public void setValue(final Object value)
	{
		log.trace("Value={}", value);
		
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
	public void requestFocus()
	{
		if (log.isDebugEnabled()) // 07068: logging when requestFocus is actually invoked
			log.debug(this + ": Invoking requestFocus()");
		
		if(m_text == null)
		{
			return;
		}
		
		m_text.requestFocus();
	}	//	requestFocus
	
	@Override
	public boolean requestFocusInWindow()
	{
		if(m_text == null)
		{
			return false;
		}
		return m_text.requestFocusInWindow();
	}
	
	/**
	 *  Property Change Listener
	 *  @param evt event
	 */
	@Override
	public void propertyChange(final PropertyChangeEvent evt)
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
			log.error("Value=" + value, e);
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
	 * Called when user presses the action button.
	 */
	private void onActionButtonPressed()
	{
		if(m_text == null)
		{
			return; // do nothing if disposed
		}
		
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
		catch (PropertyVetoException pve) {}
		m_text.requestFocus();
	}	//	actionPerformed
	
	private void cancelEdit()
	{
		m_text.setText(m_initialText);
		m_modified = true;
		commitEdit(false);
	}

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

	@Override
	public void commitChanges()
	{
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
				log.warn(error);
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
	static String startCalculator(final Container jc, final String value, final DecimalFormat format, final int displayType, final String title)
	{
		log.info("Value=" + value);
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
		final Frame frame = SwingUtils.getFrame(jc);
		//	Actual Call
		Calculator calc = new Calculator(frame, title, displayType, format, startValue);
		AEnv.showCenterWindow(frame, calc);
		BigDecimal result = calc.getNumber();
		log.info( "Result=" + result);
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
	public void setField (final GridField mField)
	{
		m_mField = mField;

		EditorContextPopupMenu.onGridFieldSet(this);
	} // setField

	@Override
	public GridField getField()
	{
		return m_mField;
	}
	
	@Override
	public void setFocusable(boolean value)
	{
		m_text.setFocusable(value); // BF [ 1834393 ] VNumber.setFocusable not working
	}

	
	/**************************************************************************
	 * 	Remove Action Listener
	 * 	@param l Action Listener
	 */
	public void removeActionListener(ActionListener l)
	{
		listenerList.remove(ActionListener.class, l);
	}	//	removeActionListener

	/**
	 * 	Add Action Listener
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
		final int modifiers;
		final AWTEvent currentEvent = EventQueue.getCurrentEvent();
		if (currentEvent instanceof InputEvent)
			modifiers = ((InputEvent)currentEvent).getModifiers();
		else if (currentEvent instanceof ActionEvent)
			modifiers = ((ActionEvent)currentEvent).getModifiers();
		else
			modifiers = 0;
		
		final ActionEvent ae = new ActionEvent (this, ActionEvent.ACTION_PERFORMED, "VNumber", EventQueue.getMostRecentEventTime(), modifiers);

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

	@Override
	public boolean isDirty()
	{
		return m_modified;
	}

	@Override
	public void rollbackChanges()
	{
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

	@Override
	protected final boolean processKeyBinding(final KeyStroke ks, final KeyEvent e, final int condition, final boolean pressed)
	{
		// Forward all key events on this component to the text field.
		// We have to do this for the case when we are embedding this editor in a JTable and the JTable is forwarding the key strokes to editing component.
		// Effect of NOT doing this: when in JTable, user presses a key (e.g. a digit) to start editing but the first key he pressed gets lost here.
		if (m_text != null && condition == WHEN_FOCUSED)
		{
			// Usually the text component does not have focus yet but it was requested, so, considering that:
			// * we are requesting the focus just to make sure
			// * we select all text (once) => as an effect, on first key pressed the editor content (which is selected) will be deleted and replaced with the new typing
			// * make sure that in the focus event which will come, the text is not selected again, else, if user is typing fast, the editor content will be only what he typed last.
			if(!m_text.hasFocus())
			{
				skipNextSelectAllOnFocusGained = true;
				m_text.requestFocus();
				
				if (m_text.getDocument().getLength() > 0)
				{
					m_text.selectAll();
				}
			}
			
			if (m_text.processKeyBinding(ks, e, condition, pressed))
			{
				return true;
			}
		}

		//
		// Fallback to super
		return super.processKeyBinding(ks, e, condition, pressed);
	}
} // VNumber
