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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Check;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;

import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.plaf.VEditorDialogButtonAlign;
import org.adempiere.ui.editor.ICopyPasteSupportEditor;
import org.adempiere.ui.editor.ICopyPasteSupportEditorAware;
import org.adempiere.ui.editor.NullCopyPasteSupportEditor;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.grid.ed.menu.EditorContextPopupMenu;
import org.compiere.model.GridField;
import org.compiere.swing.CTextField;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;

/**
 * Date Edit.
 * Maintains data as Timestamp
 *
 * @author Jorg Janke
 * @version $Id: VDate.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class VDate extends JComponent
		implements VEditor
		, ActionListener
		, KeyListener
		, FocusListener
		, ICopyPasteSupportEditorAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5265412413586160999L;

	/**
	 * IDE Bean Constructor
	 */
	public VDate()
	{
		this(DisplayType.Date);
	}   // VDate

	/**
	 * Simple Constructor
	 * 
	 * @param displayType display Type
	 */
	public VDate(int displayType)
	{
		this("Date", false, false, true, displayType, "Date");
	}   // VDate

	/**
	 * Create right aligned Date field
	 * 
	 * @param columnName column name
	 * @param mandatory mandatory
	 * @param isReadOnly read only
	 * @param isUpdateable updateable
	 * @param displayType display type
	 * @param title title
	 */
	public VDate(String columnName, boolean mandatory, boolean isReadOnly, boolean isUpdateable, int displayType, String title)
	{
		super();
		super.setName(columnName);
		m_columnName = columnName;
		m_displayType = DisplayType.isDate(displayType) ? displayType : DisplayType.Date;
		m_title = title;
		
		//
		LookAndFeel.installBorder(this, "TextField.border");
		this.setLayout(new BorderLayout());
		this.setFocusable(false);

		//
		// Text
		VEditorUtils.setupInnerTextComponentUI(m_text);
		m_text.setHorizontalAlignment(JTextField.TRAILING);
		m_text.addFocusListener(this);
		m_text.addKeyListener(this);
		m_text.setCaret(new VOvrCaret());
		if (m_displayType == DisplayType.DateTime)
		{
			m_text.setColumns(20);
		}
		//
		// Swing will search for other places and other components to consume the KeyEvent
		// So the event is not getting where it should, so we're forcing it
		// Task 06110
		m_text.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				commitEdit();
			}
		});
		//
		this.add(m_text, BorderLayout.CENTER);
		setFormat();

		//
		// Button
		{
			m_button = VEditorUtils.createActionButton("Calendar", m_text);
			m_button.addActionListener(this);
			VEditorDialogButtonAlign.addVEditorButtonUsingBorderLayout(getClass(), this, m_button);
		}
		
		//
		// Size
		VEditorUtils.setupVEditorDimensionFromInnerTextDimension(this, m_text);

		// ReadWrite
		setMandatory(mandatory);
		if (isReadOnly || !isUpdateable)
			setReadWrite(false);
		else
			setReadWrite(true);
		
		// Create and bind the context menu
		new EditorContextPopupMenu(this);

		//
		// Install actions
		actionCancelEdit.installTo(this, WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}	// VDate

	/**
	 * Dispose
	 */
	@Override
	public void dispose()
	{
		m_text = null;
		m_button = null;
		m_mField = null;
	}   // dispose

	private final String m_columnName;
	private final int m_displayType;
	private final String m_title;
	/**
	 * This member is true while the method {@link #commitEdit()} is executed.
	 */
	private volatile boolean m_withinCommitEdit;
	private String m_oldText;
	private String m_initialText;
	//
	private SimpleDateFormat m_format;
	//
	private boolean m_readWrite;
	private boolean m_mandatory;

	/** The Text Field */
	private CTextField m_text = new CTextField(12);
	/** The Button */
	private VEditorActionButton m_button = null;

	private GridField m_mField = null;
	/** Logger */
	private static final transient Logger log = LogManager.getLogger(VDate.class);
	
	private final VEditorAction actionCancelEdit = new VEditorAction("cancelEdit", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0))
	{
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(final ActionEvent e)
		{
			cancelEdit();
		}
	};

	/**
	 * Set ReadWrite - field is always r/o for Time or DateTime
	 * 
	 * @param readWrite value
	 */
	@Override
	public void setReadWrite(final boolean readWrite)
	{
		m_readWrite = readWrite;
		// this.setFocusable(value);
		// editor

		m_text.setReadWrite(readWrite);		// sets Background

		// Don't show button if not ReadWrite
		m_button.setReadWrite(readWrite);
		
		actionCancelEdit.setEnabled(readWrite);
	}	// setReadWrite

	/**
	 * IsReadWrite
	 * 
	 * @return true if rw
	 */
	@Override
	public boolean isReadWrite()
	{
		return m_readWrite;
	}	// isReadWrite

	/**
	 * Set Mandatory (and background color)
	 * 
	 * @param mandatory mandatory
	 */
	@Override
	public void setMandatory(final boolean mandatory)
	{
		m_mandatory = mandatory;
		m_text.setMandatory(mandatory);
		setBackground(false);
	}	// setMandatory

	/**
	 * Is it mandatory
	 * 
	 * @return true if mandatory
	 */
	@Override
	public boolean isMandatory()
	{
		return m_mandatory;
	}	// isMandatory

	/**
	 * Set Background based on ReadWrite / mandatory / error
	 * 
	 * @param error if true, set background to error color, otherwise mandatory/ReadWrite
	 */
	@Override
	public void setBackground(boolean error)
	{
		if (error)
			m_text.setBackground(AdempierePLAF.getFieldBackground_Error());
		else if (!m_readWrite)
			m_text.setBackground(AdempierePLAF.getFieldBackground_Inactive());
		else if (m_mandatory)
			m_text.setBackground(AdempierePLAF.getFieldBackground_Mandatory());
		else
			m_text.setBackground(AdempierePLAF.getFieldBackground_Normal());
	}	// setBackground

	/**
	 * Set Foreground
	 * 
	 * @param fg color
	 */
	@Override
	public void setForeground(Color fg)
	{
		m_text.setForeground(fg);
	}   // setForeground

	/**
	 * Set Format. Required when Format/Locale changed.
	 */
	public void setFormat()
	{
		m_format = DisplayType.getDateFormat(m_displayType);

		m_text.setDocument(new MDocDate(m_format, m_text));
	}	// setFormat

	/**
	 * Request Focus
	 */
	@Override
	public void requestFocus()
	{
		if(m_text == null)
		{
			return;
		}
		m_text.requestFocus();
	}
	
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
	 * Set Editor to value
	 * 
	 * @param value timestamp/date or String to be parsed
	 */
	@Override
	public void setValue(final Object value)
	{
		log.trace("Value={}", value);
		
		final Timestamp valueAsTS = toTimestamp(value);
		setValueOld(valueAsTS);
		
		if (m_withinCommitEdit)
		{
			return;
		}
		
		final String valueOldAsString = getValueOldAsString();
		m_text.setText(valueOldAsString);
		m_initialText = valueOldAsString;
	}	// setValue

	// metas
	public boolean isValidDate()
	{
		if (getValue() == null)
			return false;
		return true;
	}

	/**
	 * Property Change Listener
	 * 
	 * @param evt event
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		if (evt.getPropertyName().equals(GridField.PROPERTY))
			setValue(evt.getNewValue());
		if (evt.getPropertyName().equals(org.compiere.model.GridField.REQUEST_FOCUS))
			requestFocus();
	}   // propertyChange

	/**
	 * Return Editor value
	 * 
	 * @return value
	 */
	public Timestamp getTimestamp()
	{
		if (m_text == null)
		{
			return null;
		}
		final String value = m_text.getText();

		return toTimestamp(value);
	}	// getValue

	/**
	 * Parses the given string into a {@link Timestamp}.
	 * 
	 * @param value date string to parse
	 * @return time stamp or null
	 */
	private Timestamp toTimestamp(final String value)
	{
		if (Check.isEmpty(value, true))
		{
			return null;
		}
		
		//
		try
		{
			final java.util.Date date = m_format.parse(value.trim());
			return TimeUtil.asTimestamp(date);
		}
		catch (ParseException pe)
		{
			log.debug(pe.getMessage(), pe);
		}
		
		return null;
	}
	
	private Timestamp toTimestamp(final Object value)
	{
		Timestamp valueAsTS = null;
		if (value == null || value.toString().isEmpty())
		{
			valueAsTS = null;
		}
		else if (value instanceof java.util.Date)
		{
			valueAsTS = TimeUtil.asTimestamp((java.util.Date)value);
		}
		else
		{
			final String valueAsString = value.toString();
			// String values - most likely in YYYY-MM-DD (JDBC format)
			try
			{
				valueAsTS = TimeUtil.asTimestamp(DisplayType.getDateFormat_JDBC().parse(valueAsString));
			}
			catch (ParseException pe0)
			{
				// Try local string format
				try
				{
					valueAsTS = TimeUtil.asTimestamp(m_format.parse(valueAsString));
				}
				catch (ParseException pe1)
				{
					log.error("Failed to convert " + value + " to timestamp", pe1);
					valueAsTS = null;
				}
			}
		}
		
		return valueAsTS;
	}

	/**
	 * Return Editor value (timestamp)
	 * 
	 * @return value as timestamp
	 */
	@Override
	public Timestamp getValue()
	{
		return getTimestamp();
	}	// getValue
	
	private final Timestamp getValueOld()
	{
		return toTimestamp(m_oldText);
	}
	
	private final String getValueOldAsString()
	{
		return m_oldText;
	}
	
	private final void setValueOld(final Timestamp valueOld)
	{
		m_oldText = valueOld == null ? null : m_format.format(valueOld);
	}
	
	private final void setValueOld(final String valueOldAsString)
	{
		m_oldText = valueOldAsString;
	}

	/**
	 * Return Display Value
	 * 
	 * @return display value
	 */
	@Override
	public String getDisplay()
	{
		if (m_text == null)
			return null;
		return m_text.getText();
	}   // getDisplay

	/**************************************************************************
	 * Action Listener (Button)
	 * 
	 * @param e event
	 */
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		if (e.getSource() == m_button)
		{
			try(final IAutoCloseable buttonDisabled = m_button.temporaryDisable())
			{
				final Timestamp valueOld = getValueOld();
				setValue(pickDate());

				fireVetoableChange(m_columnName, valueOld, getValue());
			}
			catch (PropertyVetoException pve)
			{
				// discard veto exceptions, because we can do nothing anyway
			}
			finally
			{
				m_text.requestFocus();
			}
		}
	}	// actionPerformed

	/**
	 * Action Listener Interface (Text)
	 * 
	 * @param listener listener
	 */
	// addActionListener

	/**************************************************************************
	 * Key Listener Interface
	 * 
	 * @param e Event
	 */
	@Override
	public void keyTyped(KeyEvent e)
	{
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		commitEdit();
	}
	
	private void cancelEdit()
	{
		// Skip if disposed
		if(m_text == null)
		{
			return;
		}
		
		m_text.setText(m_initialText);
		commitEdit();
	}

	private void commitEdit()
	{
		m_withinCommitEdit = true;
		try
		{
			// metas: begin: if the user clears the content of this field, we need to handle it as he
			// is trying to remove the date value
			final String valueAsText = getDisplay();
			if (Check.isEmpty(valueAsText, true)) // metas
			{
				fireVetoableChange(m_columnName, null, null); // metas
			}
			
			final Timestamp value = getValue();
			final Timestamp valueOld = getValueOld(); // task 08672: get the old value, also as timestamp. Otherwise, listeners will compare apples with cucumbers and will always fire without need.
			if (value == null) // format error - just indicate change
			{
				fireVetoableChange(m_columnName, valueOld, null);
			}
			else
			{
				fireVetoableChange(m_columnName, valueOld, value);
			}
			
			// task 08775: update m_oldText so on the next commitEdit() invocation we have it and therefore reasonable inform out change listeners about old and new value.
			// without this, every left/right arrow keystroke might trigger a refresh.
			setValueOld(m_text.getText());
		}
		catch (PropertyVetoException pve)
		{
			// nothing
		}
		finally
		{
			m_withinCommitEdit = false;
		}
	}

	// cg: task: 06110 : end
	@Override
	public void focusGained(FocusEvent e)
	{
	}	// focusGained

	/**
	 * Data Binding to to GridController.
	 * 
	 * @param e event
	 */
	@Override
	public void focusLost(final FocusEvent e)
	{
		// did not get Focus first
		if (e.isTemporary())
		{
			return;
		}
		
		//
		// If the text field is empty
		if (m_text == null || Check.isEmpty(m_text.getText(), false))
		{
			return;
		}
		
		final Object value = getValue();
		if (value == null && isMandatory())
		{
			final Timestamp ts = pickDate();
			if (ts != null)
			{
				setValue(ts);
			}
			else
			{
				final Timestamp valueOld = getValueOld();
				if (valueOld != null)
				{
					setValue(valueOld);
				}
				else
				{
					setValue(m_initialText);
				}
			}
		}
		else
		{
			setValue(value);
		}
	}	// focusLost
	
	private final Timestamp pickDate()
	{
		return Calendar.builder()
				.setParentComponent(this)
				.setDialogTitle(m_title)
				.setDateFormat(m_format)
				.setDisplayType(m_displayType)
				.setDate(getTimestamp())
				.buildAndGet();
	}

	/**
	 * Set Field/WindowNo for ValuePreference
	 * 
	 * @param mField MField
	 */
	@Override
	public void setField(GridField mField)
	{
		m_mField = mField;

		EditorContextPopupMenu.onGridFieldSet(this);
	}	// setField

	@Override
	public GridField getField()
	{
		return m_mField;
	}

	/**
	 * Set Enabled
	 *
	 * @param enabled enabled
	 */
	@Override
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		
		m_text.setEnabled(enabled);
		m_button.setReadWrite(enabled && m_readWrite);
	}	// setEnabled

	@Override
	public void addActionListener(ActionListener l)
	{
		listenerList.add(ActionListener.class, l);
	}	// addActionListener
	
	@Override
	public void setBackground(final Color bg)
	{
		m_text.setBackground(bg);
	}

	// metas
	@Override
	public boolean isAutoCommit()
	{
		return true;
	}

	// metas
	@Override
	public void addMouseListener(final MouseListener l)
	{
		m_text.addMouseListener(l);
	}

	@Override
	public void addKeyListener(final KeyListener l)
	{
		m_text.addKeyListener(l);
	}

	public int getCaretPosition()
	{
		return m_text.getCaretPosition();
	}

	public void setCaretPosition(final int position)
	{
		m_text.setCaretPosition(position);
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
			if (m_text.processKeyBinding(ks, e, condition, pressed))
			{
				return true;
			}
		}

		//
		// Fallback to super
		return super.processKeyBinding(ks, e, condition, pressed);
	}	
}	// VDate
