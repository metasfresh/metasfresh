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
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import org.slf4j.Logger;

import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;

import javax.swing.JComponent;
import javax.swing.LookAndFeel;

import org.adempiere.images.Images;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.plaf.VEditorDialogButtonAlign;
import org.adempiere.ui.editor.ICopyPasteSupportEditor;
import org.adempiere.ui.editor.ICopyPasteSupportEditorAware;
import org.adempiere.ui.editor.NullCopyPasteSupportEditor;
import org.adempiere.util.Services;
import org.compiere.grid.ed.menu.EditorContextPopupMenu;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MLocation;
import org.compiere.model.MLocationLookup;
import org.compiere.swing.CMenuItem;
import org.compiere.swing.CTextField;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Env;

/**
 *	Location Control (Address)
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: VLocation.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class VLocation extends JComponent
	implements VEditor, ActionListener
	, ICopyPasteSupportEditorAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -288409058154703379L;
	
	/**
	 *	Constructor
	 *
	 * 	@param columnName column name
	 * 	@param mandatory mandatory
	 * 	@param isReadOnly read only
	 * 	@param isUpdateable updateable
	 * 	@param mLocation location model
	 */
	public VLocation(String columnName, boolean mandatory, boolean isReadOnly, boolean isUpdateable, MLocationLookup mLocation)
	{
		this(
				(GridTab)null
				, columnName, mandatory, isReadOnly, isUpdateable, mLocation);
	}
	
	/**
	 *	Constructor
	 * 
	 *  @param gridTab
	 * 	@param columnName column name
	 * 	@param mandatory mandatory
	 * 	@param isReadOnly read only
	 * 	@param isUpdateable updateable
	 * 	@param mLocation location model
	 */
	public VLocation(GridTab gridTab, String columnName, boolean mandatory, boolean isReadOnly, boolean isUpdateable, MLocationLookup mLocation)
	{
		super();
		super.setName(columnName);
		m_GridTab = gridTab;
		m_columnName = columnName;
		m_mLocation = mLocation;
		//
		LookAndFeel.installBorder(this, "TextField.border");
		this.setLayout(new BorderLayout());

		//
		// Text
		VEditorUtils.setupInnerTextComponentUI(m_text);
		m_text.setEditable(false);
		m_text.setFocusable(false);
		this.add(m_text, BorderLayout.CENTER);

		//
		// Button
		m_button = VEditorUtils.createActionButton("Location", m_text);
		m_button.addActionListener(this);
		VEditorDialogButtonAlign.addVEditorButtonUsingBorderLayout(getClass(), this, m_button);

		//
		// Size
		VEditorUtils.setupVEditorDimensionFromInnerTextDimension(this, m_text);

		//
		//	Editable
		if (isReadOnly || !isUpdateable)
			setReadWrite (false);
		else
			setReadWrite (true);
		setMandatory (mandatory);
		
		//
		// Create and bind the context menu
		final EditorContextPopupMenu popupMenu = new EditorContextPopupMenu(this);
		mDelete = new CMenuItem(Services.get(IMsgBL.class).getMsg(Env.getCtx(), "Delete"), Images.getImageIcon2("Delete16"));
		mDelete.addActionListener(this);
		popupMenu.add(mDelete);
	}	//	VLocation

	/**
	 *  Dispose
	 */
	@Override
	public void dispose()
	{
		m_text = null;
		m_button = null;
		m_mLocation = null;
		m_GridField = null;
		m_GridTab = null;
	}   //  dispose

	/** The Text Field                  */
	private CTextField			m_text = new CTextField(VLookup.DISPLAY_LENGTH);
	/** The Button                      */
	private VEditorActionButton m_button = null;

	private MLocationLookup		m_mLocation;
	private MLocation			m_value;

	private final String m_columnName;
	private boolean mandatory = false;
	private boolean readWrite = true;
	/**	Logger			*/
	private static final Logger log = LogManager.getLogger(VLocation.class);

	//	Popup
	private CMenuItem 			mDelete;

	/** The Grid Tab * */
	private GridTab m_GridTab; // added for processCallout
	/** The Grid Field * */
	private GridField m_GridField; // added for processCallout
	
	/**
	 *	Enable/disable
	 *  @param value true if ReadWrite
	 */
	@Override
	public void setReadWrite (boolean value)
	{
		this.readWrite = value;
		
		m_button.setReadWrite (value);
		
		setBackground(false);
	}	//	setReadWrite

	/**
	 *	IsReadWrite
	 *  @return value true if ReadWrite
	 */
	@Override
	public boolean isReadWrite()
	{
		return readWrite;
	}	//	isReadWrite

	/**
	 *	Set Mandatory (and back color)
	 *  @param mandatory true if mandatory
	 */
	@Override
	public void setMandatory (boolean mandatory)
	{
		this.mandatory = mandatory;
		setBackground(false);
	}	//	setMandatory

	/**
	 *	Is it mandatory
	 *  @return true if mandatory
	 */
	@Override
	public boolean isMandatory()
	{
		return mandatory;
	}	//	isMandatory

	/**
	 *	Set Background
	 *  @param color color
	 */
	@Override
	public void setBackground (Color color)
	{
		if (!color.equals(m_text.getBackground()))
			m_text.setBackground(color);
	}	//	setBackground

	/**
	 *  Set Background based on editable / mandatory / error
	 *  @param error if true, set background to error color, otherwise mandatory/editable
	 */
	@Override
	public void setBackground (boolean error)
	{
		if (error)
			setBackground(AdempierePLAF.getFieldBackground_Error());
		else if (!isReadWrite())
			setBackground(AdempierePLAF.getFieldBackground_Inactive());
		else if (isMandatory())
			setBackground(AdempierePLAF.getFieldBackground_Mandatory());
		else
			setBackground(AdempierePLAF.getFieldBackground_Normal());
	}   //  setBackground

	/**
	 *  Set Foreground
	 *  @param fg color
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
		if (value == null)
		{
			m_value = null;
			m_text.setText(null);
		}
		else
		{
			m_value = m_mLocation.getLocation(value, null);
			if (m_value == null)
				m_text.setText("<" + value + ">");
			else
				m_text.setText(m_value.toString());
		}
	}	//	setValue

	/**
	 * 	Request Focus
	 */
	@Override
	public void requestFocus ()
	{
		m_text.requestFocus ();
	}	//	requestFocus

	/**
	 *  Property Change Listener
	 *  @param evt PropertyChangeEvent
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
	 *  @return value
	 */
	@Override
	public Object getValue()
	{
		if (m_value == null)
			return null;
		return new Integer(m_value.getC_Location_ID());
	}	//	getValue

	/**
	 *	Return Editor value
	 *  @return value
	 */
	public int getC_Location_ID()
	{
		if (m_value == null)
			return 0;
		return m_value.getC_Location_ID();
	}	//	getC_Location_ID

	/**
	 *  Return Display Value
	 *  @return display value
	 */
	@Override
	public String getDisplay()
	{
		return m_text.getText();
	}   //  getDisplay

	/**
	 *	ActionListener - Button - Start Dialog
	 *  @param e ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == mDelete)
		{
			m_value = null;        //  create new
		}
		
		//
		final VLocationDialog ld = new VLocationDialog(Env.getFrame(this), Services.get(IMsgBL.class).getMsg(Env.getCtx(), "Location"), m_value);
		ld.setVisible(true);
		Object oldValue = getValue();
		m_value = ld.getValue();
		//
		if (e.getSource() == mDelete)
			;
		else if (!ld.isChanged())
			return;

		//	Data Binding
		try
		{
			int C_Location_ID = 0;
			if (m_value != null)
				C_Location_ID = m_value.getC_Location_ID();
			Integer ii = new Integer(C_Location_ID);
			
			if (C_Location_ID > 0)
				fireVetoableChange(m_columnName, oldValue, ii);
			setValue(ii);
			if (ii.equals(oldValue) && m_GridTab != null && m_GridField != null)
			{
				//  force Change - user does not realize that embedded object is already saved.
				m_GridTab.processFieldChange(m_GridField);
			}
		}
		catch (PropertyVetoException pve)
		{
			log.error("VLocation.actionPerformed", pve);
		}

	}	//	actionPerformed

	/**
	 *  Action Listener Interface
	 *  @param listener listener
	 */
	@Override
	public void addActionListener(ActionListener listener)
	{
		m_text.addActionListener(listener);
	}   //  addActionListener
	
	@Override
	public synchronized void addMouseListener(MouseListener l)
	{
		m_text.addMouseListener(l);
	}

	/**
	 *  Set Field/WindowNo for ValuePreference (NOP)
	 *  @param mField Model Field
	 */
	@Override
	public void setField (org.compiere.model.GridField mField)
	{
		m_GridField = mField;

		EditorContextPopupMenu.onGridFieldSet(this);
	}   //  setField

	@Override
	public GridField getField()
	{
		return m_GridField;
	}

	// metas
	@Override
	public boolean isAutoCommit()
	{
		return true;
	}

	@Override
	public ICopyPasteSupportEditor getCopyPasteSupport()
	{
		return m_text == null ? NullCopyPasteSupportEditor.instance : m_text.getCopyPasteSupport();
	}

}	//	VLocation
