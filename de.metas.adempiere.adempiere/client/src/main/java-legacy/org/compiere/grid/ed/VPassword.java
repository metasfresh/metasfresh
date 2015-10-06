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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.model.GridField;
import org.compiere.swing.CPassword;

/**
 *	Data Binding:
 *		VEditors call fireVetoableChange(m_columnName, null, getText());
 *		GridController (for Single-Row) and VCellExitor (for Multi-Row)
 *      listen to Vetoable Change Listener (vetoableChange)
 *		then set the value for that column in the current row in the table
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: VPassword.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public final class VPassword extends CPassword
	implements VEditor, KeyListener, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1659042515884579907L;

	/**
	 *	IDE Bean Constructor for 30 character updateable field
	 */
	public VPassword()
	{
		this("Password", false, false, true, 30, 30, "");
	}	//	VPassword

	/**
	 *	Detail Constructor
	 *  @param columnName column name
	 *  @param mandatory mandatory
	 *  @param isReadOnly read only
	 *  @param isUpdateable updateable
	 *  @param displayLength display length
	 *  @param fieldLength field length
	 *  @param VFormat format
	 */
	public VPassword (String columnName, boolean mandatory, boolean isReadOnly, boolean isUpdateable,
		int displayLength, int fieldLength, String VFormat)
	{
		super (displayLength>VString.MAXDISPLAY_LENGTH ? VString.MAXDISPLAY_LENGTH : displayLength);
		super.setName(columnName);
		m_columnName = columnName;
		if (VFormat == null)
			VFormat = "";
		m_VFormat = VFormat;
		m_fieldLength = fieldLength;
		if (m_VFormat.length() != 0 || m_fieldLength != 0)
			setDocument(new MDocString(m_VFormat, m_fieldLength, this));
		if (m_VFormat.length() != 0)
			setCaret(new VOvrCaret());
		//
		setMandatory(mandatory);

		//	Editable
		if (isReadOnly || !isUpdateable)
		{
			setEditable(false);
			setBackground(AdempierePLAF.getFieldBackground_Inactive());
		}

		this.addKeyListener(this);
		this.addActionListener(this);

		setForeground(AdempierePLAF.getTextColor_Normal());
		setBackground(AdempierePLAF.getFieldBackground_Normal());
	}	//	VPassword

	/**
	 *  Dispose
	 */
	public void dispose()
	{
		m_mField = null;
	}   //  dispose

	private GridField				m_mField = null;

	private String				m_columnName;
	private String				m_oldText;
	private String				m_VFormat;
	private int					m_fieldLength;
	private volatile boolean	m_setting = false;

	/**
	 *	Set Editor to value
	 *  @param value value
	 */
	public void setValue (Object value)
	{
		if (value == null)
			m_oldText = "";
		else
			m_oldText = value.toString();
		if (!m_setting)
			setText (m_oldText);
	}	//	setValue

	/**
	 *  Property Change Listener
	 *  @param evt event
	 */
	public void propertyChange (PropertyChangeEvent evt)
	{
		if (evt.getPropertyName().equals(org.compiere.model.GridField.PROPERTY))
			setValue(evt.getNewValue());
	}   //  propertyChange

	/**
	 *	Return Editor value
	 *  @return value
	 */
	public Object getValue()
	{
		return String.valueOf(getPassword());
	}	//	getValue

	/**
	 *  Return Display Value
	 *  @return value
	 */
	public String getDisplay()
	{
		return String.valueOf(getPassword());
	}   //  getDisplay

	/**************************************************************************
	 *	Key Listener Interface
	 *  @param e event
	 */
	public void keyTyped(KeyEvent e)	{}
	public void keyPressed(KeyEvent e)	{}

	/**
	 *	Key Listener.
	 *  @param e event
	 */
	public void keyReleased(KeyEvent e)
	{
		String newText = String.valueOf(getPassword());
		m_setting = true;
		try
		{
			fireVetoableChange(m_columnName, m_oldText, newText);
		}
		catch (PropertyVetoException pve)	{}
		m_setting = false;
	}	//	keyReleased

	/**
	 *	Data Binding to MTable (via GridController)	-	Enter pressed
	 *  @param e event
	 */
	public void actionPerformed(ActionEvent e)
	{
		String newText = String.valueOf(getPassword());
		//  Data Binding
		try
		{
			fireVetoableChange(m_columnName, m_oldText, newText);
		}
		catch (PropertyVetoException pve)	{}
	}	//	actionPerformed

	/**
	 *  Set Field/WindowNo for ValuePreference
	 *  @param mField field
	 */
	public void setField (GridField mField)
	{
		m_mField = mField;
	}   //  setField

	@Override
	public GridField getField() {
		return m_mField;
	}


	// metas: Ticket#2011062310000013 
	public boolean isAutoCommit()
	{
		return false;
	}

}	//	VPassword

