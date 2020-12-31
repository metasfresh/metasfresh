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
package org.compiere.db;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.SwingConstants;

import org.adempiere.images.Images;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.plaf.VEditorUI;
import org.compiere.swing.CEditor;
import org.compiere.util.DB;

/**
 *  Connection Editor.
 *  A combo box and a button
 *
 *  @author     Jorg Janke
 *  @version    $Id: CConnectionEditor.java,v 1.2 2006/07/30 00:55:13 jjanke Exp $
 */
public class CConnectionEditor extends JComponent
	implements CEditor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 259945316129032408L;

	/**
	 *  Connection Editor creating new Connection
	 */
	public CConnectionEditor()
	{
		setName("ConnectionEditor");
		CConnectionEditor_MouseListener ml = new CConnectionEditor_MouseListener();
		
		final int height = VEditorUI.getVEditorHeight();
		
		//
		//  Text
		m_text.setEditable(false);
		m_text.setBorder(BorderFactory.createEmptyBorder());
		m_text.setMinimumSize(new Dimension(30, height));
		m_text.setMinimumSize(new Dimension(Integer.MAX_VALUE, height));
		m_text.addMouseListener(ml);
		
		//
		// Connection indicator
		btnConnection.setHorizontalAlignment(SwingConstants.CENTER);
		btnConnection.setVerticalAlignment(SwingConstants.CENTER);
		btnConnection.setIcon(Images.getImageIcon2("Database16"));
		btnConnection.setFocusable(false);
		btnConnection.setBorder(BorderFactory.createEmptyBorder());
		btnConnection.setOpaque(true);
		btnConnection.setPreferredSize(new Dimension(height, height));
		btnConnection.addMouseListener(ml);
		
		//
		// This component
		LookAndFeel.installBorder(this, "TextField.border");
		
		//
		// Layout
		setLayout(new BorderLayout(0,0));
		add(m_text, BorderLayout.CENTER);
		add(btnConnection, BorderLayout.EAST);
	}   //  CConnectionEditor

	/** Text field */
	private JTextField  m_text = new JTextField(10);
	/** Connection indicator */
	private JLabel btnConnection = new JLabel();
	/** The Value           */
	private CConnection m_value = null;
	/** ReadWrite           */
	private boolean     m_rw = true;
	/** Mandatory           */
	private boolean     m_mandatory = false;

	/**
	 *	Enable Editor
	 *  @param rw true, if you can enter/select data
	 */
	@Override
	public void setReadWrite (boolean rw)
	{
		m_rw = rw;
		setBackground(false);
	}   //  setReadWrite

	/**
	 *	Is it possible to edit
	 *  @return true, if editable
	 */
	@Override
	public boolean isReadWrite()
	{
		return m_rw;
	}   //  isReadWrite

	/**
	 *	Set Editor Mandatory
	 *  @param mandatory true, if you have to enter data
	 */
	@Override
	public void setMandatory (boolean mandatory)
	{
		m_mandatory = mandatory;
	}   //  setMandatory

	/**
	 *	Is Field mandatory
	 *  @return true, if mandatory
	 */
	@Override
	public boolean isMandatory()
	{
		return m_mandatory;
	}   //  isMandatory

	/**
	 *  Set Background based on editable / mandatory / error
	 *  @param error if true, set background to error color, otherwise mandatory/editable
	 */
	@Override
	public void setBackground (final boolean error)
	{
		Color c = null;
		if (error)
			c = AdempierePLAF.getFieldBackground_Error();
		else if (!m_rw)
			c = AdempierePLAF.getFieldBackground_Inactive();
		else if (m_mandatory)
			c = AdempierePLAF.getFieldBackground_Mandatory();
		else
			c = AdempierePLAF.getFieldBackground_Normal();
		setBackground(c);
	}   //  setBackground

	/**
	 *  Set Background color
	 *  @param color
	 */
	@Override
	public void setBackground (Color color)
	{
		m_text.setBackground(color);
		btnConnection.setBackground(color);
	}   //  setBackground

	/**
	 *	Set Editor to value
	 *  @param value value of the editor
	 */
	@Override
	public void setValue (final Object value)
	{
		if (value instanceof CConnection)
		{
			m_value = (CConnection)value;
		}
		else
		{
			m_value = null;
		}
		setDisplay();
	}   //  setValue

	/**
	 *	Return Editor value
	 *  @return current value
	 */
	@Override
	public CConnection getValue()
	{
		return m_value;
	}   //  getValue

	/**
	 *  Return Display Value
	 *  @return displayed String value
	 */
	@Override
	public String getDisplay()
	{
		if (m_value == null)
			return "";
		return m_value.getDbHost();
	}   //  getDisplay

	/**
	 *  Update Display with Connection info
	 */
	public void setDisplay()
	{
		m_text.setText(getDisplay());
		
		final boolean isDatabaseOK = m_value != null && m_value.isDatabaseOK();

		// Mark the text field as error if both AppsServer and DB connections are not established
		setBackground(!isDatabaseOK);

		// Mark the connection indicator button as error if any of AppsServer or DB connection is not established. 
		btnConnection.setBackground(isDatabaseOK ? AdempierePLAF.getFieldBackground_Normal() : AdempierePLAF.getFieldBackground_Error());
	}

	/**
	 * Add Action Listener
	 */
	public synchronized void addActionListener(final ActionListener l)
	{
	    listenerList.add(ActionListener.class, l);
	}   //  addActionListener

	/**
	 *  Fire Action Performed
	 */
	private void fireActionPerformed()
	{
		ActionEvent e = null;
		ActionListener[] listeners = listenerList.getListeners(ActionListener.class);
        for (int i = 0; i < listeners.length; i++) 
        {
       		if (e == null) 
       			e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "actionPerformed");
       		listeners[i].actionPerformed(e);
        }
	}   //  fireActionPerformed
	
	private void actionEditConnection()
	{
		if (!isEnabled() || !m_rw)
		{
			return;
		}
		
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		try
		{
			final CConnection connection = getValue();
			final CConnectionDialog dialog = new CConnectionDialog(connection);
			if (dialog.isCancel())
			{
				return;
			}
			
			final CConnection connectionNew = dialog.getConnection();
			setValue(connectionNew);
			DB.setDBTarget(connectionNew);
			fireActionPerformed();
		}
		finally
		{
			setCursor(Cursor.getDefaultCursor());
		}
		
	}

	/**
	 *  MouseListener
	 */
	private class CConnectionEditor_MouseListener extends MouseAdapter
	{
		/** Mouse Clicked - Open connection editor */
		@Override
		public void mouseClicked(final MouseEvent e)
		{
			if (m_active)
				return;
			m_active = true;
			try
			{
				actionEditConnection();
			}
			finally
			{
				m_active = false;
			}
		}

		private boolean m_active = false;
	}

}   //  CConnectionEditor
