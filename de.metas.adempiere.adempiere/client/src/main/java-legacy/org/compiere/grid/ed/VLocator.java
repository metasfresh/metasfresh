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
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComponent;
import javax.swing.LookAndFeel;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.plaf.VEditorDialogButtonAlign;
import org.adempiere.ui.editor.ICopyPasteSupportEditor;
import org.adempiere.ui.editor.ICopyPasteSupportEditorAware;
import org.adempiere.ui.editor.IRefreshableEditor;
import org.adempiere.ui.editor.IZoomableEditor;
import org.adempiere.ui.editor.NullCopyPasteSupportEditor;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.apps.AEnv;
import org.compiere.apps.AWindow;
import org.compiere.grid.ed.menu.EditorContextPopupMenu;
import org.compiere.model.GridField;
import org.compiere.model.MLocator;
import org.compiere.model.MLocatorLookup;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.model.MTable;
import org.compiere.model.MWarehouse;
import org.compiere.swing.CTextField;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

/**
 *	Warehouse Locator Control
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: VLocator.java,v 1.5 2006/07/30 00:51:27 jjanke Exp $
 */
public class VLocator extends JComponent
	implements VEditor, ActionListener, IRefreshableEditor, IZoomableEditor
	, ICopyPasteSupportEditorAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1953277256988665242L;
	
	/**
	 *  IDE Constructor
	 */
	public VLocator ()
	{
		this("M_Locator_ID", false, false, true, null, 0);
	}   //  VLocator

	/**
	 *	Constructor
	 *
	 * 	@param columnName ColumnName
	 *	@param mandatory mandatory
	 *	@param isReadOnly read only
	 *	@param isUpdateable updateable
	 *	@param mLocator locator (lookup) model
	 * 	@param WindowNo window no
	 */
	public VLocator(String columnName, boolean mandatory, boolean isReadOnly, boolean isUpdateable,
		MLocatorLookup mLocator, int WindowNo)
	{
		super();
		super.setName(columnName);
		m_columnName = columnName;
		m_mLocator = mLocator;
		m_WindowNo = WindowNo;
		//
		LookAndFeel.installBorder(this, "TextField.border");
		this.setLayout(new BorderLayout());

		//
		// Text
		VEditorUtils.setupInnerTextComponentUI(m_text);
		m_text.setEditable(true);
		m_text.setFocusable(true);
		m_text.addActionListener(this);
		this.add(m_text, BorderLayout.CENTER);

		//
		// Button
		{
			m_button = VEditorUtils.createActionButton("Locator", m_text);
			m_button.addActionListener(this);
			VEditorDialogButtonAlign.addVEditorButtonUsingBorderLayout(getClass(), this, m_button);
		}
		
		//
		// Size
		VEditorUtils.setupVEditorDimensionFromInnerTextDimension(this, m_text);

		//	ReadWrite
		if (isReadOnly || !isUpdateable)
			setReadWrite (false);
		else
			setReadWrite (true);
		setMandatory (mandatory);
		
		setDefault_Locator_ID(); // set default locator, teo_sarca [ 1661546 ]

		//
		// Create and bind the context menu
		new EditorContextPopupMenu(this);

	}	//	VLocator

	/**
	 *  Dispose
	 */
	@Override
	public void dispose()
	{
		m_text = null;
		m_button = null;
		m_mLocator = null;
	}   //  dispose

	private CTextField			m_text = new CTextField (VLookup.DISPLAY_LENGTH);
	private VEditorActionButton m_button = null;
	private MLocatorLookup		m_mLocator;
	private Object				m_value;
	//
	private final String m_columnName;
	private int m_WindowNo;
	private boolean mandatory = false;
	private boolean readWrite = true;
	/**	Logger			*/
	private static final Logger log = LogManager.getLogger(VLocator.class);
	private GridField m_mField;

	/**
	 *	Enable/disable
	 *  @param value r/w
	 */
	@Override
	public void setReadWrite (boolean value)
	{
		this.readWrite = value;
		m_button.setReadWrite(value);
		setBackground(false);
	}	//	setReadWrite

	/**
	 *	IsReadWrite
	 *  @return true if ReadWrite
	 */
	@Override
	public boolean isReadWrite()
	{
		return readWrite;
	}	//	isReadWrite

	/**
	 *	Set Mandatory (and back bolor)
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
	 * 	Request Focus
	 */
	@Override
	public void requestFocus ()
	{
		m_text.requestFocus ();
	}	//	requestFocus

	/**
	 *	Set Editor to value
	 *  @param value Integer
	 */
	@Override
	public void setValue(Object value)
	{
		setValue (value, false);
	}	//	setValue

	/**
	 * 	Set Value
	 *	@param value value
	 *	@param fire data binding
	 */
	private void setValue (Object value, boolean fire)
	{
		if (m_mLocator == null)
		{
			return ;
		}
		
		if (value != null)
		{
			m_mLocator.setOnly_Warehouse_ID (getOnly_Warehouse_ID ());
			m_mLocator.setOnly_Product_ID(getOnly_Product_ID());
			if (!m_mLocator.isValid(value))
				value = null;
		}
		//
		m_value = value;
		m_text.setText(m_mLocator.getDisplay(value));	//	loads value

		//	Data Binding
		try
		{
			fireVetoableChange(m_columnName, null, value);
		}
		catch (PropertyVetoException pve)
		{
		}
	}	//	setValue


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
		if (getM_Locator_ID() == 0)
			return null;
		return m_value;
	}	//	getValue
	
	/**
	 * 	Get M_Locator_ID
	 *	@return id
	 */
	public int getM_Locator_ID()
	{
		if (m_value != null 
			&& m_value instanceof Integer)
			return ((Integer)m_value).intValue();
		return 0;
	}	//	getM_Locator_ID

	/**
	 *  Return Display Value
	 *  @return display value
	 */
	@Override
	public String getDisplay()
	{
		return m_text.getText();
	}   //  getDisplay
	
	@Override
	public void refreshValue()
	{
		if (m_mLocator != null)
		{
			m_mLocator.refresh();
		}
	}

	/**
	 *	ActionListener
	 *  @param e ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		//	Warehouse/Product
		int only_Warehouse_ID = getOnly_Warehouse_ID();
		int only_Product_ID = getOnly_Product_ID();
		log.info("Only Warehouse_ID=" + only_Warehouse_ID
			+ ", Product_ID=" + only_Product_ID);

		//	Text Entry ok
		if (e.getSource() == m_text 
			&& actionText(only_Warehouse_ID, only_Product_ID))
			return;

		//	 Button - Start Dialog
		int M_Locator_ID = 0;
		if (m_value instanceof Integer)
			M_Locator_ID = ((Integer)m_value).intValue();
		//
		m_mLocator.setOnly_Warehouse_ID(only_Warehouse_ID);
		m_mLocator.setOnly_Product_ID(getOnly_Product_ID());
		VLocatorDialog ld = new VLocatorDialog(Env.getFrame(this),
			Services.get(IMsgBL.class).translate(Env.getCtx(), m_columnName),
			m_mLocator, M_Locator_ID, isMandatory(), only_Warehouse_ID);
		//	display
		ld.setVisible(true);
		m_mLocator.setOnly_Warehouse_ID(0);

		//	redisplay
		if (!ld.isChanged())
			return;
		setValue (ld.getValue(), true);
	}	//	actionPerformed

	/**
	 * 	Hit Enter in Text Field
	 * 	@param only_Warehouse_ID if not 0 restrict warehouse
	 * 	@param only_Product_ID of not 0 restricted product
	 * 	@return true if found
	 */
	private boolean actionText (int only_Warehouse_ID, int only_Product_ID)
	{
		String text = m_text.getText();
		log.debug(text);
		//	Null
		if (text == null || text.length() == 0)
		{
			if (isMandatory())
				return false;
			else
			{
				setValue (null, true);
				return true;
			}
		}
		if (text.endsWith("%"))
			text = text.toUpperCase();
		else
			text = text.toUpperCase() + "%";
		
		//	Look up - see MLocatorLookup.run
		StringBuffer sql = new StringBuffer("SELECT M_Locator_ID FROM M_Locator ")
			.append(" WHERE IsActive='Y' AND UPPER(Value) LIKE ")
			.append(DB.TO_STRING(text));
		if (getOnly_Warehouse_ID() != 0)
			sql.append(" AND M_Warehouse_ID=?");
		if (getOnly_Product_ID() != 0)
			sql.append(" AND (IsDefault='Y' ")	//	Default Locator
				.append("OR EXISTS (SELECT * FROM M_Product p ")	//	Product Locator
				.append("WHERE p.M_Locator_ID=M_Locator.M_Locator_ID AND p.M_Product_ID=?)")
				.append("OR EXISTS (SELECT * FROM M_Storage s ")	//	Storage Locator
				.append("WHERE s.M_Locator_ID=M_Locator.M_Locator_ID AND s.M_Product_ID=?))");
		String finalSql = Env.getUserRolePermissions().addAccessSQL(sql.toString(), "M_Locator", IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
		//
		int M_Locator_ID = 0;
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(finalSql, null);
			int index = 1;
			if (only_Warehouse_ID != 0)
				pstmt.setInt(index++, only_Warehouse_ID);
			if (only_Product_ID != 0)
			{
				pstmt.setInt(index++, only_Product_ID);
				pstmt.setInt(index++, only_Product_ID);
			}
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				M_Locator_ID = rs.getInt(1);
				if (rs.next())
					M_Locator_ID = 0;	//	more than one
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (SQLException ex)
		{
			log.error(finalSql, ex);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
		}
		catch (SQLException ex1)
		{
		}
		pstmt = null;
		if (M_Locator_ID == 0)
			return false;

		setValue (new Integer(M_Locator_ID), true);
		return true;
	}	//	actionText

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
	public void addMouseListener(final MouseListener l)
	{
		m_text.addMouseListener(l);
	}

	/**
	 *	Action - Zoom
	 */
	@Override
	public void actionZoom()
	{
		int AD_Window_ID = MTable.get(Env.getCtx(), MLocator.Table_ID).getAD_Window_ID();
		if (AD_Window_ID <= 0)
			AD_Window_ID = 139;	//	hardcoded window Warehouse & Locators
		log.info("");
		//
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		AWindow frame = new AWindow();
		
		MQuery zoomQuery = new MQuery();
		zoomQuery.addRestriction(MLocator.COLUMNNAME_M_Locator_ID, Operator.EQUAL, getValue());
		zoomQuery.setRecordCount(1);	//	guess
		
		if (!frame.initWindow(AD_Window_ID, zoomQuery))
			return;
		AEnv.addToWindowManager(frame);
		AEnv.showCenterScreen(frame);
		frame = null;
		setCursor(Cursor.getDefaultCursor());
	}	//	actionZoom

	/**
	 *  Set Field/WindowNo for ValuePreference (NOP)
	 *  @param mField Model Field
	 */
	@Override
	public void setField (org.compiere.model.GridField mField)
	{
		this.m_mField = mField;

		EditorContextPopupMenu.onGridFieldSet(this);
	}   //  setField

	@Override
	public GridField getField() {
		return m_mField;
	}

	/**
	 * 	Get Warehouse restriction if any.
	 *	@return	M_Warehouse_ID or 0
	 */
	private int getOnly_Warehouse_ID()
	{
		String only_Warehouse = Env.getContext(Env.getCtx(), m_WindowNo, "M_Warehouse_ID", true);
		int only_Warehouse_ID = 0;
		try
		{
			if (only_Warehouse != null && only_Warehouse.length () > 0)
				only_Warehouse_ID = Integer.parseInt (only_Warehouse);
		}
		catch (Exception ex)
		{
		}
		return only_Warehouse_ID;
	}	//	getOnly_Warehouse_ID

	/**
	 * 	Get Product restriction if any.
	 *	@return	M_Product_ID or 0
	 */
	private int getOnly_Product_ID()
	{
		if (!Env.isSOTrx(Env.getCtx(), m_WindowNo))
			return 0;	//	No product restrictions for PO
		//
		String only_Product = Env.getContext(Env.getCtx(), m_WindowNo, "M_Product_ID", true);
		int only_Product_ID = 0;
		try
		{
			if (only_Product != null && only_Product.length () > 0)
				only_Product_ID = Integer.parseInt (only_Product);
		}
		catch (Exception ex)
		{
		}
		return only_Product_ID;
	}	//	getOnly_Product_ID
	
	/**
	 * Set the default locator if this field is mandatory 
	 * and we have a warehouse restriction.
	 * 
	 * @since 3.1.4
	 */
	private void setDefault_Locator_ID()
	{
		// teo_sarca, FR [ 1661546 ] Mandatory locator fields should use defaults
		if (!isMandatory() || m_mLocator == null) {
			return;
		}
		int M_Warehouse_ID = getOnly_Warehouse_ID();
		if (M_Warehouse_ID <= 0) {
			return;
		}
		MWarehouse wh = MWarehouse.get(Env.getCtx(), M_Warehouse_ID);
		if (wh == null || wh.get_ID() <= 0) {
			return;
		}
		MLocator loc = wh.getDefaultLocator();
		if (loc == null || loc.get_ID() <= 0) {
			return;
		}
		setValue(Integer.valueOf(loc.get_ID()));
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
}	//	VLocator

/*****************************************************************************/
