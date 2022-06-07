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

import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.images.Images;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.plaf.VEditorDialogButtonAlign;
import org.compiere.grid.ed.menu.EditorContextPopupMenu;
import org.compiere.model.GridField;
import org.compiere.swing.CMenuItem;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.NumberFormat;

/**
 *	Resource Assignment Entry
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: VAssignment.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class VAssignment extends JComponent
	implements VEditor, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1362298262975012883L;
	
	/**
	 *	IDE Constructor
	 */
	public VAssignment()
	{
		this (false, false, true);
	}	//	VAssigment

	/**
	 *	Create Resource Assigment.
	 *  <pre>
	 * 		Resource DateTimeFrom Qty UOM Button
	 *  </pre>
	 *  @param mandatory mandatory
	 *  @param isReadOnly read only
	 *  @param isUpdateable updateable
	 */
	public VAssignment (boolean mandatory, boolean isReadOnly, boolean isUpdateable)
	{
		super();
	//	super.setName(columnName);
		
		LookAndFeel.installBorder(this, "TextField.border");
		this.setLayout(new BorderLayout());
		
		//
		// Text
		VEditorUtils.setupInnerTextComponentUI(m_text);
		m_text.setEditable(false);
		m_text.setFocusable(false);
		m_text.setHorizontalAlignment(JTextField.LEADING);
		this.add(m_text, BorderLayout.CENTER);

		//
		// Button
		m_button = VEditorUtils.createActionButton("Assignment", m_text);
		m_button.addActionListener(this);
		VEditorDialogButtonAlign.addVEditorButtonUsingBorderLayout(getClass(), this, m_button);

		//
		// Size
		VEditorUtils.setupVEditorDimensionFromInnerTextDimension(this, m_text);

		//
		//	ReadWrite
		setMandatory(mandatory);
		if (isReadOnly || !isUpdateable)
			setReadWrite(false);
		else
			setReadWrite(true);

		//
		// Create and bind the context menu
		final EditorContextPopupMenu popupMenu = new EditorContextPopupMenu(this);
		menuEditor = new CMenuItem(Services.get(IMsgBL.class).getMsg(Env.getCtx(), "InfoResource"), Images.getImageIcon2("Zoom16"));
		menuEditor.addActionListener(this);
		popupMenu.add(menuEditor);
	}	//	VAssignment

	/**	Data Value				*/
	private Object				m_value = null;
	/** Get Info				*/
	private PreparedStatement	m_pstmt = null;

	/** The Text Field          */
	private JTextField			m_text = new JTextField (VLookup.DISPLAY_LENGTH);
	/** The Button              */
	private VEditorActionButton m_button = null;

	private CMenuItem 			menuEditor;

	private boolean				m_readWrite;
	private boolean				m_mandatory;

	/**	The Format				*/
	private DateFormat			m_dateFormat = DisplayType.getDateFormat(DisplayType.DateTime);
	private NumberFormat		m_qtyFormat = DisplayType.getNumberFormat(DisplayType.Quantity);
	private GridField m_mField;
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(VAssignment.class);
	
	/**
	 * 	Dispose resources
	 */
	@Override
	public void dispose()
	{
		try
		{
			if (m_pstmt != null)
				m_pstmt.close();
		}
		catch (Exception e)
		{
			log.error("VAssignment.dispose");
		}
		m_text = null;
		m_button = null;
	}	//	dispose

	/**
	 * 	Set Mandatory
	 * 	@param mandatory mandatory
	 */
	@Override
	public void setMandatory (boolean mandatory)
	{
		m_mandatory = mandatory;
		setBackground (false);
	}	//	setMandatory

	/**
	 * 	Get Mandatory
	 *  @return mandatory
	 */
	@Override
	public boolean isMandatory()
	{
		return m_mandatory;
	}	//	isMandatory

	/**
	 * 	Set ReadWrite
	 * 	@param rw read rwite
	 */
	@Override
	public void setReadWrite (boolean rw)
	{
		m_readWrite = rw;
		m_button.setReadWrite(rw);
		setBackground (false);
	}	//	setReadWrite

	/**
	 * 	Is Read Write
	 * 	@return read write
	 */
	@Override
	public boolean isReadWrite()
	{
		return m_readWrite;
	}	//	isReadWrite

	/**
	 * 	Set Foreground
	 * 	@param color color
	 */
	@Override
	public void setForeground (Color color)
	{
		m_text.setForeground(color);
	}	//	SetForeground

	/**
	 * 	Set Background
	 * 	@param error Error
	 */
	@Override
	public void setBackground (boolean error)
	{
		if (error)
			setBackground(AdempierePLAF.getFieldBackground_Error());
		else if (!m_readWrite)
			setBackground(AdempierePLAF.getFieldBackground_Inactive());
		else if (m_mandatory)
			setBackground(AdempierePLAF.getFieldBackground_Mandatory());
		else
			setBackground(AdempierePLAF.getInfoBackground());
	}	//	setBackground

	/**
	 * 	Set Background
	 * 	@param color Color
	 */
	@Override
	public void setBackground (Color color)
	{
		m_text.setBackground(color);
	}	//	setBackground

	/**
	 * 	Request Focus
	 */
	@Override
	public void requestFocus ()
	{
		m_text.requestFocus ();
	}	//	requestFocus

	/**************************************************************************
	 * 	Set/lookup Value
	 * 	@param value value
	 */
	@Override
	public void setValue(Object value)
	{
		if (value == m_value)
			return;
		m_value = value;
		int S_ResourceAssignment_ID = 0;
		if (m_value != null && m_value instanceof Integer)
			S_ResourceAssignment_ID = ((Integer)m_value).intValue();
		//	Set Empty
		if (S_ResourceAssignment_ID == 0)
		{
			m_text.setText("");
			return;
		}

		//	Statement
		if (m_pstmt == null)
			m_pstmt = DB.prepareStatement("SELECT r.Name,ra.AssignDateFrom,ra.Qty,uom.UOMSymbol "
				+ "FROM S_ResourceAssignment ra, S_Resource r, S_ResourceType rt, C_UOM uom "
				+ "WHERE ra.S_ResourceAssignment_ID=?"
				+ " AND ra.S_Resource_ID=r.S_Resource_ID"
				+ " AND r.S_ResourceType_ID=rt.S_ResourceType_ID"
				+ " and rt.C_UOM_ID=uom.C_UOM_ID", null);
		//
		try
		{
			m_pstmt.setInt(1, S_ResourceAssignment_ID);
			ResultSet rs = m_pstmt.executeQuery();
			if (rs.next())
			{
				StringBuffer sb = new StringBuffer(rs.getString(1));
				sb.append(" ").append(m_dateFormat.format(rs.getTimestamp(2)))
					.append(" ").append(m_qtyFormat.format(rs.getBigDecimal(3)))
					.append(" ").append(rs.getString(4).trim());
				m_text.setText(sb.toString());
			}
			else
				m_text.setText("<" + S_ResourceAssignment_ID + ">");
			rs.close();
		}
		catch (Exception e)
		{
			log.error("", e);
		}
	}	//	setValue

	/**
	 * 	Get Value
	 * 	@return value
	 */
	@Override
	public Object getValue()
	{
		return m_value;
	}	//	getValue

	/**
	 * 	Get Display Value
	 *	@return info
	 */
	@Override
	public String getDisplay()
	{
		return m_text.getText();
	}	//	getDisplay

	/*************************************************************************/

	/**
	 * 	Set Field - NOP
	 * 	@param mField MField
	 */
	@Override
	public void setField(GridField mField)
	{
		m_mField = mField;

		EditorContextPopupMenu.onGridFieldSet(this);
	}	//	setField

	@Override
	public GridField getField() {
		return m_mField;
	}
	
	/**
	 *  Action Listener Interface
	 *  @param listener listener
	 */
	@Override
	public void addActionListener(ActionListener listener)
	{
		//	m_text.addActionListener(listener);
	}   //  addActionListener

	/**
	 * 	Action Listener - start dialog
	 * 	@param e Event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (!m_button.isEnabled())
			return;

		throw new AdempiereException("legacy feature removed");
	}

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

	@Override
	public boolean isAutoCommit()
	{
		return true;
	}


}	//	VAssignment
