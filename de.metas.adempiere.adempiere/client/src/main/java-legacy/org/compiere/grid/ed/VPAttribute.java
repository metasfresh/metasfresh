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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.images.Images;
import org.adempiere.mm.attributes.api.IAttributeExcludeBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.plaf.VEditorDialogButtonAlign;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.grid.ed.menu.EditorContextPopupMenu;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeSetExclude;
import org.compiere.model.MPAttributeLookup;
import org.compiere.model.MProduct;
import org.compiere.swing.CMenuItem;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;

/**
 *  Product Attribute Set Instance Editor
 *
 *  @author Jorg Janke
 *  @version $Id: VPAttribute.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 *  
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 			<li>BF [ 1895041 ] NPE when move product with attribute set
 * 			<li>BF [ 1770177 ] Inventory Move Locator Error - integrated MGrigioni bug fix
 * 			<li>BF [ 2011222 ] ASI Dialog is reseting locator
 */
public class VPAttribute extends JComponent
	implements VEditor, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5634540697551031302L;


	/**
	 *	IDE Constructor
	 */
	public VPAttribute()
	{
		this (null, false, false, true, 0, null);
	}	//	VAssigment

	/**
	 *	Create Product Attribute Set Instance Editor.
	 *  @param mandatory mandatory
	 *  @param isReadOnly read only
	 *  @param isUpdateable updateable
	 * 	@param WindowNo WindowNo
	 * 	@param lookup Model Product Attribute
	 */
	public VPAttribute (boolean mandatory, boolean isReadOnly, boolean isUpdateable, 
		int WindowNo, MPAttributeLookup lookup)
	{
		this(null, mandatory, isReadOnly, isUpdateable, WindowNo, lookup);
	}
	
	/**
	 *	Create Product Attribute Set Instance Editor.
	 *  @param gridTab
	 *  @param mandatory mandatory
	 *  @param isReadOnly read only
	 *  @param isUpdateable updateable
	 * 	@param WindowNo WindowNo
	 * 	@param lookup Model Product Attribute
	 */
	public VPAttribute (GridTab gridTab, boolean mandatory, boolean isReadOnly, boolean isUpdateable, 
			int WindowNo, MPAttributeLookup lookup)
	{
		super();
		super.setName("M_AttributeSetInstance_ID");
		m_GridTab = gridTab; // added for processCallout
		final Properties ctx = Env.getCtx();
		final int tabNo = (gridTab == null ? Env.TAB_None : gridTab.getTabNo());
		attributeContext = VPAttributeWindowContext.of(ctx, WindowNo, tabNo);
		m_mPAttribute = lookup;

		//
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
		m_button = VEditorUtils.createActionButton("PAttribute", m_text);
		m_button.addActionListener(this);
		VEditorDialogButtonAlign.addVEditorButtonUsingBorderLayout(getClass(), this, m_button);

		//
		//  Size
		VEditorUtils.setupVEditorDimensionFromInnerTextDimension(this, m_text);

		//
		//	ReadWrite
		setMandatory(mandatory);
		if (isReadOnly || !isUpdateable)
			setReadWrite(false);
		else
			setReadWrite(true);

		//
		//	Popup
		final JPopupMenu popupMenu = new EditorContextPopupMenu(this);
		m_text.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				//	Double Click
				if (e.getClickCount() > 1)
				{
					actionButton();
				}
			}
		});
		//
		final CMenuItem menuEditor = new CMenuItem(Services.get(IMsgBL.class).getMsg(ctx, "PAttribute"), Images.getImageIcon2("Zoom16"));
		menuEditor.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				actionButton();
			}
		});
		popupMenu.add(menuEditor);

		//
		// Listener to change attributeContext when the value is set via putClientProperty method.
		this.addPropertyChangeListener(IVPAttributeContext.ATTR_NAME, new PropertyChangeListener()
		{
			@Override
			public void propertyChange(final PropertyChangeEvent evt)
			{
				final IVPAttributeContext attributeContextNew = (IVPAttributeContext)evt.getNewValue();
				VPAttribute.this.attributeContext  = attributeContextNew;
			}
		});
	}	//	VPAttribute

	/**	Data Value				*/
	private Object				m_value = new Object();
	/** The Attribute Instance	*/
	private MPAttributeLookup	m_mPAttribute;

	/** The Text Field          */
	private JTextField			m_text = new JTextField (VLookup.DISPLAY_LENGTH);
	/** The Button              */
	private VEditorActionButton m_button = null;

	private boolean				m_readWrite;
	private boolean				m_mandatory;
	/** Product Attributes context to be used */
	private IVPAttributeContext attributeContext;
	/** The Grid Tab * */
	private GridTab m_GridTab; // added for processCallout
	/** The Grid Field * */
	private GridField m_GridField; // added for processCallout
	
	/**	Calling Window Info				*/
	private int					m_AD_Column_ID = 0;
	private GridField m_mField;
	/**	No Instance Key					*/
	private static Integer		NO_INSTANCE = new Integer(0);
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(VPAttribute.class);
		

	/**
	 * 	Dispose resources
	 */
	@Override
	public void dispose()
	{
		m_text = null;
		m_button = null;
		m_mPAttribute.dispose();
		m_mPAttribute = null;
		m_GridField = null;
		m_GridTab = null;
	}	//	dispose

	/**
	 * 	Set Mandatory
	 * 	@param mandatory mandatory
	 */
	@Override
	public void setMandatory (boolean mandatory)
	{
		this.m_mandatory = mandatory;
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
	 * 	@param rw read write
	 */
	@Override
	public void setReadWrite (boolean rw)
	{
		this.m_readWrite = rw;
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

	
	/**************************************************************************
	 * 	Set/lookup Value
	 * 	@param value value
	 */
	@Override
	public void setValue(Object value)
	{
		if (value == null || NO_INSTANCE.equals(value))
		{
			m_text.setText("");
			m_value = value;
			return;
		}
		
		//	The same
		if (value.equals(m_value)) 
			return;
		//	new value
		log.debug("Value=" + value);
		m_value = value;
		m_text.setText(m_mPAttribute.getDisplay(value));	//	loads value
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

	
	/**************************************************************************
	 * 	Set Field
	 * 	@param mField MField
	 */
	@Override
	public void setField(GridField mField)
	{
		//	To determine behavior
		m_AD_Column_ID = mField.getAD_Column_ID();
		m_GridField = mField;
		
		m_mField = mField;
//		if (m_mField != null)
//			FieldRecordInfo.addMenu(this, popupMenu);
		
		//
		// (re)Initialize our attribute context because we want to make sure
		// it is using exactly the same context as the GridField it is using when it's exporting the values to context.
		// If we are not doing this it might be (in some cases) that different contexts are used
		// (e.g. process parameters panel, when using Product Attribute field won't find the M_Product_ID because it's in a different context instance)
		// For more info, see 08723.
		if (mField != null)
		{
			this.attributeContext = VPAttributeWindowContext.of(mField);
		}

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
	}   //  addActionListener

	/**
	 * 	Action Listener - start dialog
	 * 	@param e Event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == m_button)
		{
			actionButton();
		}
	}	//	actionPerformed
	
	private void actionButton()
	{
		if (!m_button.isEnabled())
		{
			return;
		}
		try(final IAutoCloseable buttonDisabled = m_button.temporaryDisable())
		{
			actionButton0();
		}
	}
	
	private void actionButton0()
	{
		Integer oldValue = (Integer)getValue ();
		int oldValueInt = oldValue == null ? 0 : oldValue.intValue ();
		int M_AttributeSetInstance_ID = oldValueInt;
		int M_Product_ID = attributeContext.getM_Product_ID();
		int M_ProductBOM_ID =attributeContext.getM_ProductBOM_ID();
		int M_Locator_ID = -1;

		log.info("M_Product_ID=" + M_Product_ID + "/" + M_ProductBOM_ID
			+ ",M_AttributeSetInstance_ID=" + M_AttributeSetInstance_ID
			+ ", AD_Column_ID=" + m_AD_Column_ID);
		
		final boolean productWindow = m_AD_Column_ID == 8418;		//	FIXME HARDCODED: M_Product.M_AttributeSetInstance_ID = 8418
		final boolean isProcessParameter = m_AD_Column_ID <= 0;
		final boolean isPureProductASI = !productWindow && !isProcessParameter;
		
		//	Exclude ability to enter ASI
		boolean exclude = true;
		if (M_Product_ID > 0)
		{
			MProduct product = MProduct.get(Env.getCtx(), M_Product_ID);
			int M_AttributeSet_ID = Services.get(IProductBL.class).getM_AttributeSet_ID(product);
			if (M_AttributeSet_ID > 0)
			{
				
				final IAttributeExcludeBL excludeBL = Services.get(IAttributeExcludeBL.class);
				final I_M_AttributeSet attributeSet = InterfaceWrapperHelper.create(Env.getCtx(), M_AttributeSet_ID, I_M_AttributeSet.class, ITrx.TRXNAME_None);
				final I_M_AttributeSetExclude asExclude = excludeBL.getAttributeSetExclude(attributeSet, m_AD_Column_ID, attributeContext.isSOTrx());
				if ((null == asExclude) || (!excludeBL.isFullExclude(asExclude)))
				{
					exclude = false;
				}
			}
		}
		
		boolean changed = false;
		if (M_ProductBOM_ID > 0)	//	Use BOM Component
			M_Product_ID = M_ProductBOM_ID;
		//	
		if (isPureProductASI && (M_Product_ID <= 0 || exclude))
		{
			changed = true;
			m_text.setText(null);
			M_AttributeSetInstance_ID = 0;
		}
		else
		{
			final VPAttributeDialog vad = new VPAttributeDialog (Env.getFrame (this), 
				M_AttributeSetInstance_ID,
				M_Product_ID,
				productWindow,
				m_AD_Column_ID,
				attributeContext);
			if (vad.isChanged())
			{
				m_text.setText(vad.getM_AttributeSetInstanceName());
				M_AttributeSetInstance_ID = vad.getM_AttributeSetInstance_ID();
				if (!productWindow && vad.getM_Locator_ID() > 0)
				{
					M_Locator_ID = vad.getM_Locator_ID();
				}
				changed = true;
			}
		}
		
		//	Set Value
		if (changed)
		{
			log.trace("Changed M_AttributeSetInstance_ID=" + M_AttributeSetInstance_ID);
			m_value = new Object();				//	force re-query display
			if (M_AttributeSetInstance_ID <= 0)
				setValue(null);
			else
				setValue(M_AttributeSetInstance_ID);
			
			// Change Locator
			if (m_GridTab != null && M_Locator_ID > 0)
			{
				log.trace("Change M_Locator_ID=" + M_Locator_ID);
				m_GridTab.setValue("M_Locator_ID", M_Locator_ID);
			}
			
			//
			// Fire events
			try
			{
				String columnName = "M_AttributeSetInstance_ID";
		 	 	if (m_GridField != null)
		 	 	{
		 	 		columnName = m_GridField.getColumnName();
		 	 	}
		 	 	fireVetoableChange(columnName, new Object(), getValue());
			}
			catch (PropertyVetoException pve)
			{
				log.error("", pve);
			}
			if (M_AttributeSetInstance_ID == oldValueInt && m_GridTab != null && m_GridField != null)
			{
				//  force Change - user does not realize that embedded object is already saved.
				m_GridTab.processFieldChange(m_GridField); 
			}
		}	//	change
	}


	/**
	 *  Property Change Listener
	 *  @param evt event
	 */
	@Override
	public void propertyChange (PropertyChangeEvent evt)
	{
		final String propertyName = evt.getPropertyName();
		if (propertyName.equals(org.compiere.model.GridField.PROPERTY))
		{
			setValue(evt.getNewValue());
		}
		// metas: request focus (2009_0027_G131) 
		else if (propertyName.equals(org.compiere.model.GridField.REQUEST_FOCUS))
		{
			requestFocus();
		}
		// metas end
	}   //  propertyChange

	// metas
	@Override
	public boolean isAutoCommit()
	{
		return true;
	}

	// metas
	@Override
	public void addMouseListener(MouseListener l)
	{
		m_text.addMouseListener(l);
	}
}	//	VPAttribute
