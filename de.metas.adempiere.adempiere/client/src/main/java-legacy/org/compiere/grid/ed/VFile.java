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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.LookAndFeel;

import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.plaf.VEditorDialogButtonAlign;
import org.adempiere.ui.editor.ICopyPasteSupportEditor;
import org.adempiere.ui.editor.ICopyPasteSupportEditorAware;
import org.adempiere.ui.editor.NullCopyPasteSupportEditor;
import org.adempiere.util.Services;
import org.compiere.grid.ed.menu.EditorContextPopupMenu;
import org.compiere.model.GridField;
import org.compiere.swing.CTextField;
import org.slf4j.Logger;

import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;

import de.metas.logging.LogManager;

import org.compiere.util.Env;

/**
 * File/Path Selection
 *
 * @author Initial: Jirimuto
 * @version $Id: VFile.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 * 
 * @author Teo Sarca
 */
public class VFile extends JComponent
		implements VEditor, ActionListener, KeyListener
		, ICopyPasteSupportEditorAware
{
	/**
	 *
	 */
	private static final long serialVersionUID = -4665930745414194731L;

	/**
	 * Constructor
	 *
	 * @param columnName column name
	 * @param mandatory mandatory
	 * @param isReadOnly read only
	 * @param isUpdateable updateable
	 * @param allowFilesOnly <ul>
	 *            <li>if true, files only will be allowed
	 *            <li>if false, directory only will be allowed
	 *            </ul>
	 */
	public VFile(final String columnName, final boolean mandatory,
			final boolean isReadOnly, final boolean isUpdateable, final int fieldLength, final boolean allowFilesOnly)
	{
		super();
		super.setName(columnName);
		m_columnName = columnName;
		m_fieldLength = fieldLength;
		m_selectionMode = allowFilesOnly ? JFileChooser.FILES_ONLY : JFileChooser.DIRECTORIES_ONLY;
		
		final String columnNameLC = columnName.toLowerCase();
		if (columnNameLC.indexOf("open") != -1 || columnNameLC.indexOf("load") != -1)
		{
			m_dialogType = JFileChooser.OPEN_DIALOG;
		}
		else if (columnNameLC.indexOf("save") != -1)
		{
			m_dialogType = JFileChooser.SAVE_DIALOG;
		}
		else
		{
			m_dialogType = JFileChooser.CUSTOM_DIALOG;
		}
		
		//
		LookAndFeel.installBorder(this, "TextField.border");
		setLayout(new BorderLayout());

		//
		// Button
		{
			m_button = VEditorUtils.createActionButton("Open", m_text);
			m_button.addActionListener(this);
			VEditorDialogButtonAlign.addVEditorButtonUsingBorderLayout(getClass(), this, m_button);
		}
		
		//
		// Text
		VEditorUtils.setupInnerTextComponentUI(m_text);
		m_text.setEditable(true);
		m_text.setFocusable(true);
		m_text.addKeyListener(this);
		this.add(m_text, BorderLayout.CENTER);

		//
		// Size
		VEditorUtils.setupVEditorDimensionFromInnerTextDimension(this, m_text);

		// Editable
		if (isReadOnly || !isUpdateable)
		{
			setReadWrite(false);
		}
		else
		{
			setReadWrite(true);
		}
		setMandatory(mandatory);
		
		// Create and bind the context menu
		new EditorContextPopupMenu(this);
	}	// VFile

	/**
	 * Dispose
	 */
	@Override
	public void dispose()
	{
		m_text = null;
		m_button = null;
		m_field = null;
	}   // dispose

	/** The Text Field */
	private CTextField m_text = new CTextField(VLookup.DISPLAY_LENGTH);
	/** The Button */
	private VEditorActionButton m_button = null;
	/** Column Name */
	private final String m_columnName;
	private String m_oldText;
	private String m_initialText;
	/** Field Length */
	private final int m_fieldLength;
	/** Setting new value */
	private volatile boolean m_setting = false;
	/** Selection Mode */
	private final int m_selectionMode;
	/** Save/Open */
	private final int m_dialogType;
	private boolean mandatory = false;
	private boolean readWrite = true;
	/** Logger */
	private static final Logger log = LogManager.getLogger(VFile.class);

	/**
	 * Enable/disable
	 * 
	 * @param value true if ReadWrite
	 */
	@Override
	public void setReadWrite(final boolean value)
	{
		this.readWrite = value;
		
		m_text.setReadWrite(value);
		m_button.setReadWrite(value);
		
		setBackground(false);
	}	// setReadWrite

	/**
	 * IsReadWrite
	 * 
	 * @return value true if ReadWrite
	 */
	@Override
	public boolean isReadWrite()
	{
		return readWrite;
	}	// isReadWrite

	/**
	 * Set Mandatory (and back bolor)
	 * 
	 * @param mandatory true if mandatory
	 */
	@Override
	public void setMandatory(final boolean mandatory)
	{
		this.mandatory = mandatory;
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
		return mandatory;
	}	// isMandatory

	/**
	 * Set Background
	 * 
	 * @param color color
	 */
	@Override
	public void setBackground(final Color color)
	{
		if (!color.equals(m_text.getBackground()))
		{
			m_text.setBackground(color);
		}
	}	// setBackground

	/**
	 * Set Background based on editable / mandatory / error
	 * 
	 * @param error if true, set background to error color, otherwise mandatory/editable
	 */
	@Override
	public void setBackground(final boolean error)
	{
		if (error)
		{
			setBackground(AdempierePLAF.getFieldBackground_Error());
		}
		else if (!isReadWrite())
		{
			setBackground(AdempierePLAF.getFieldBackground_Inactive());
		}
		else if (isMandatory())
		{
			setBackground(AdempierePLAF.getFieldBackground_Mandatory());
		}
		else
		{
			setBackground(AdempierePLAF.getFieldBackground_Normal());
		}
	}   // setBackground

	/**
	 * Set Foreground
	 * 
	 * @param fg color
	 */
	@Override
	public void setForeground(final Color fg)
	{
		m_text.setForeground(fg);
	}   // setForeground

	/**
	 * Set Editor to value
	 * 
	 * @param value value
	 */
	@Override
	public void setValue(final Object value)
	{
		if (value == null)
		{
			m_oldText = "";
		}
		else
		{
			m_oldText = value.toString();
		}
		// only set when not updated here
		if (m_setting)
		{
			return;
		}
		m_text.setText(m_oldText);
		m_initialText = m_oldText;
	}	// setValue

	/**
	 * Property Change Listener
	 * 
	 * @param evt PropertyChangeEvent
	 */
	@Override
	public void propertyChange(final PropertyChangeEvent evt)
	{
		if (evt.getPropertyName().equals(org.compiere.model.GridField.PROPERTY))
		{
			setValue(evt.getNewValue());
		}

		// metas: request focus (2009_0027_G131)
		if (evt.getPropertyName().equals(org.compiere.model.GridField.REQUEST_FOCUS))
		{
			requestFocus();
			// metas end
		}

	}   // propertyChange

	/**
	 * Return Editor value
	 * 
	 * @return value
	 */
	@Override
	public Object getValue()
	{
		return m_text.getText();
	}	// getValue

	/**
	 * Return Display Value
	 * 
	 * @return display value
	 */
	@Override
	public String getDisplay()
	{
		return m_text.getText();
	}   // getDisplay

	/**
	 * ActionListener - Button - Start Dialog
	 * 
	 * @param e ActionEvent
	 */
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		final String m_value = m_text.getText();
		//
		log.info(m_value);
		//
		String fieldName = null;
		if (m_field != null)
		{
			fieldName = m_field.getHeader();
		}
		else
		{
			fieldName = Services.get(IMsgBL.class).translate(Env.getCtx(), m_columnName);
		}
		//
		final JFileChooser chooser = new JFileChooser(m_value);
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileSelectionMode(m_selectionMode);
		chooser.setDialogTitle(fieldName);
		chooser.setDialogType(m_dialogType);
		//
		int returnVal = -1;
		if (m_dialogType == JFileChooser.SAVE_DIALOG)
		{
			returnVal = chooser.showSaveDialog(this);
		}
		else if (m_dialogType == JFileChooser.OPEN_DIALOG)
		{
			returnVal = chooser.showOpenDialog(this);
		}
		else
		// if (m_dialogType == JFileChooser.CUSTOM_DIALOG)
		{
			returnVal = chooser.showDialog(this, fieldName);
		}
		if (returnVal != JFileChooser.APPROVE_OPTION)
		{
			return;
		}
		final File selectedFile = chooser.getSelectedFile();
		m_text.setText(selectedFile.getAbsolutePath());
		// Data Binding
		try
		{
			fireVetoableChange(m_columnName, m_oldText, m_text.getText());
		}
		catch (final PropertyVetoException pve)
		{
		}

	}	// actionPerformed

	/**
	 * Action Listener Interface
	 * 
	 * @param listener listener
	 */
	@Override
	public void addActionListener(final ActionListener listener)
	{
		m_text.addActionListener(listener);
	}   // addActionListener

	/**
	 * Action Listener Interface
	 * 
	 * @param listener
	 */
	public void removeActionListener(final ActionListener listener)
	{
		m_text.removeActionListener(listener);
	}   // removeActionListener

	/**
	 * Set Field/WindowNo
	 * 
	 * @param mField field
	 */
	@Override
	public void setField(final GridField mField)
	{
		m_field = mField;

		EditorContextPopupMenu.onGridFieldSet(this);
	}   // setField

	/** Grid Field */
	private GridField m_field = null;

	/**
	 * Get Field
	 * 
	 * @return gridField
	 */
	@Override
	public GridField getField()
	{
		return m_field;
	}   // getField

	@Override
	public void keyPressed(final KeyEvent e)
	{
	}

	@Override
	public void keyTyped(final KeyEvent e)
	{
	}

	/**
	 * Key Released. if Escape Restore old Text
	 * 
	 * @param e event
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(final KeyEvent e)
	{
		if (LogManager.isLevelFinest())
		{
			log.trace("Key=" + e.getKeyCode() + " - " + e.getKeyChar() + " -> " + m_text.getText());
		}
		// ESC
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			m_text.setText(m_initialText);
		}
		else if (e.getKeyChar() == KeyEvent.CHAR_UNDEFINED)
		{
			return;
		}
		m_setting = true;
		try
		{
			String clear = m_text.getText();
			if (clear.length() > m_fieldLength)
			{
				clear = clear.substring(0, m_fieldLength);
			}
			fireVetoableChange(m_columnName, m_oldText, clear);
		}
		catch (final PropertyVetoException pve)
		{
		}
		m_setting = false;
	}

	// metas
	@Override
	public boolean isAutoCommit()
	{
		return true;
	}

	@Override
	public final ICopyPasteSupportEditor getCopyPasteSupport()
	{
		return m_text == null ? NullCopyPasteSupportEditor.instance : m_text.getCopyPasteSupport();
	}
}	// VFile
