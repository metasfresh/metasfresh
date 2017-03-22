/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
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
import javax.swing.SwingConstants;

import org.adempiere.images.Images;
import org.adempiere.mm.attributes.util.ASIEditingInfo;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.plaf.VEditorDialogButtonAlign;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.grid.ed.menu.EditorContextPopupMenu;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MPAttributeLookup;
import org.compiere.swing.CMenuItem;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.form.IClientUI;
import de.metas.logging.LogManager;

/**
 * Product Attribute Set Instance Editor
 *
 * @author Jorg Janke
 * @version $Id: VPAttribute.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <li>BF [ 1895041 ] NPE when move product with attribute set
 *         <li>BF [ 1770177 ] Inventory Move Locator Error - integrated MGrigioni bug fix
 *         <li>BF [ 2011222 ] ASI Dialog is reseting locator
 */
public class VPAttribute extends JComponent
		implements VEditor, ActionListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = 5634540697551031302L;

	/**
	 * IDE Constructor
	 */
	public VPAttribute()
	{
		this(null, false, false, true, 0, null);
	}	// VAssigment

	/**
	 * Create Product Attribute Set Instance Editor.
	 * 
	 * @param mandatory mandatory
	 * @param isReadOnly read only
	 * @param isUpdateable updateable
	 * @param WindowNo WindowNo
	 * @param lookup Model Product Attribute
	 */
	public VPAttribute(final boolean mandatory, final boolean isReadOnly, final boolean isUpdateable,
			final int WindowNo, final MPAttributeLookup lookup)
	{
		this(null, mandatory, isReadOnly, isUpdateable, WindowNo, lookup);
	}

	/**
	 * Create Product Attribute Set Instance Editor.
	 * 
	 * @param gridTab
	 * @param mandatory mandatory
	 * @param isReadOnly read only
	 * @param isUpdateable updateable
	 * @param WindowNo WindowNo
	 * @param lookup Model Product Attribute
	 */
	public VPAttribute(final GridTab gridTab, final boolean mandatory, final boolean isReadOnly, final boolean isUpdateable,
			final int WindowNo, final MPAttributeLookup lookup)
	{
		super();
		super.setName("M_AttributeSetInstance_ID");
		m_GridTab = gridTab; // added for processCallout
		final Properties ctx = Env.getCtx();
		final int tabNo = gridTab == null ? Env.TAB_None : gridTab.getTabNo();
		attributeContext = VPAttributeWindowContext.of(ctx, WindowNo, tabNo);
		m_mPAttribute = lookup;

		//
		LookAndFeel.installBorder(this, "TextField.border");
		setLayout(new BorderLayout());

		//
		// Text
		VEditorUtils.setupInnerTextComponentUI(m_text);
		m_text.setEditable(false);
		m_text.setFocusable(false);
		m_text.setHorizontalAlignment(SwingConstants.LEADING);
		this.add(m_text, BorderLayout.CENTER);

		//
		// Button
		m_button = VEditorUtils.createActionButton("PAttribute", m_text);
		m_button.addActionListener(this);
		VEditorDialogButtonAlign.addVEditorButtonUsingBorderLayout(getClass(), this, m_button);

		//
		// Size
		VEditorUtils.setupVEditorDimensionFromInnerTextDimension(this, m_text);

		//
		// ReadWrite
		setMandatory(mandatory);
		if (isReadOnly || !isUpdateable)
		{
			setReadWrite(false);
		}
		else
		{
			setReadWrite(true);
		}

		//
		// Popup
		final JPopupMenu popupMenu = new EditorContextPopupMenu(this);
		m_text.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(final MouseEvent e)
			{
				// Double Click
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
			public void actionPerformed(final ActionEvent e)
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
				attributeContext = attributeContextNew;
			}
		});
	}	// VPAttribute

	/** Data Value */
	private Object m_value = new Object();
	/** The Attribute Instance */
	private MPAttributeLookup m_mPAttribute;

	/** The Text Field */
	private JTextField m_text = new JTextField(VLookup.DISPLAY_LENGTH);
	/** The Button */
	private VEditorActionButton m_button = null;

	private boolean m_readWrite;
	private boolean m_mandatory;
	/** Product Attributes context to be used */
	private IVPAttributeContext attributeContext;
	/** The Grid Tab * */
	private GridTab m_GridTab; // added for processCallout
	/** The Grid Field * */
	private GridField m_GridField; // added for processCallout

	/** Calling Window Info */
	private GridField m_mField;
	/** No Instance Key */
	private static Integer NO_INSTANCE = new Integer(0);
	/** Logger */
	private static final transient Logger log = LogManager.getLogger(VPAttribute.class);

	/**
	 * Dispose resources
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
	}	// dispose

	/**
	 * Set Mandatory
	 * 
	 * @param mandatory mandatory
	 */
	@Override
	public void setMandatory(final boolean mandatory)
	{
		m_mandatory = mandatory;
		setBackground(false);
	}	// setMandatory

	/**
	 * Get Mandatory
	 * 
	 * @return mandatory
	 */
	@Override
	public boolean isMandatory()
	{
		return m_mandatory;
	}	// isMandatory

	/**
	 * Set ReadWrite
	 * 
	 * @param rw read write
	 */
	@Override
	public void setReadWrite(final boolean rw)
	{
		m_readWrite = rw;
		m_button.setReadWrite(rw);
		setBackground(false);
	}	// setReadWrite

	/**
	 * Is Read Write
	 * 
	 * @return read write
	 */
	@Override
	public boolean isReadWrite()
	{
		return m_readWrite;
	}	// isReadWrite

	/**
	 * Set Foreground
	 * 
	 * @param color color
	 */
	@Override
	public void setForeground(final Color color)
	{
		m_text.setForeground(color);
	}	// SetForeground

	/**
	 * Set Background
	 * 
	 * @param error Error
	 */
	@Override
	public void setBackground(final boolean error)
	{
		if (error)
		{
			setBackground(AdempierePLAF.getFieldBackground_Error());
		}
		else if (!m_readWrite)
		{
			setBackground(AdempierePLAF.getFieldBackground_Inactive());
		}
		else if (m_mandatory)
		{
			setBackground(AdempierePLAF.getFieldBackground_Mandatory());
		}
		else
		{
			setBackground(AdempierePLAF.getInfoBackground());
		}
	}	// setBackground

	/**
	 * Set Background
	 * 
	 * @param color Color
	 */
	@Override
	public void setBackground(final Color color)
	{
		m_text.setBackground(color);
	}	// setBackground

	/**************************************************************************
	 * Set/lookup Value
	 * 
	 * @param value value
	 */
	@Override
	public void setValue(final Object value)
	{
		if (value == null || NO_INSTANCE.equals(value))
		{
			m_text.setText("");
			m_value = value;
			return;
		}

		// The same
		if (value.equals(m_value))
		{
			return;
		}
		// new value
		log.debug("Value=" + value);
		m_value = value;
		m_text.setText(m_mPAttribute.getDisplay(value));	// loads value
	}	// setValue

	/**
	 * Get Value
	 * 
	 * @return value
	 */
	@Override
	public Object getValue()
	{
		return m_value;
	}	// getValue

	private int getM_AttributeSetInstance_ID()
	{
		final Integer value = (Integer)getValue();
		return value == null ? 0 : value.intValue();
	}

	@Override
	public String getDisplay()
	{
		return m_text.getText();
	}	// getDisplay

	@Override
	public void setField(final GridField mField)
	{
		// To determine behavior
		m_GridField = mField;

		m_mField = mField;
		// if (m_mField != null)
		// FieldRecordInfo.addMenu(this, popupMenu);

		//
		// (re)Initialize our attribute context because we want to make sure
		// it is using exactly the same context as the GridField it is using when it's exporting the values to context.
		// If we are not doing this it might be (in some cases) that different contexts are used
		// (e.g. process parameters panel, when using Product Attribute field won't find the M_Product_ID because it's in a different context instance)
		// For more info, see 08723.
		if (mField != null)
		{
			attributeContext = VPAttributeWindowContext.of(mField);
		}

		EditorContextPopupMenu.onGridFieldSet(this);
	}	// setField

	@Override
	public GridField getField()
	{
		return m_mField;
	}
	
	private String getTableName()
	{
		final GridField gridField = getField();
		return gridField == null ? null : gridField.getTableName();
	}

	private int getAD_Column_ID()
	{
		final GridField gridField = getField();
		return gridField == null ? -1 : gridField.getAD_Column_ID();
	}

	@Override
	public void addActionListener(final ActionListener listener)
	{
	}   // addActionListener

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		try
		{
			if (e.getSource() == m_button)
			{
				actionButton();
			}
		}
		catch (final Exception ex)
		{
			Services.get(IClientUI.class).error(attributeContext.getWindowNo(), ex);
		}
	}	// actionPerformed

	private void actionButton()
	{
		if (!m_button.isEnabled())
		{
			return;
		}
		try (final IAutoCloseable buttonDisabled = m_button.temporaryDisable())
		{
			actionButton0();
		}
	}

	private void actionButton0()
	{
		final ASIEditingInfo asiInfo = ASIEditingInfo.of(
				attributeContext.getM_Product_ID() // product
				, getM_AttributeSetInstance_ID() // ASI
				, getTableName(), getAD_Column_ID() // caller TableName/AD_Column_ID
				, attributeContext.isSOTrx() //
		);
		if (asiInfo.isExcludedAttributeSet())
		{
			return;
		}

		final VPAttributeDialog vad = new VPAttributeDialog( //
				Env.getFrame(this) //
				, asiInfo //
				, attributeContext //
		);
		if (!vad.isChanged())
		{
			return;
		}

		final int previousM_AttributeSetInstance_ID = asiInfo.getM_AttributeSetInstance_ID();
		final int newM_AttributeSetInstance_ID = vad.getM_AttributeSetInstance_ID();
		final int M_Locator_ID = vad.getM_Locator_ID();

		//
		// Actually set the value
		m_text.setText(vad.getM_AttributeSetInstanceName());
		m_value = new Object();				// force re-query display
		if (newM_AttributeSetInstance_ID <= 0)
		{
			setValue(null);
		}
		else
		{
			setValue(newM_AttributeSetInstance_ID);
		}
		// Change Locator
		if (m_GridTab != null && M_Locator_ID > 0)
		{
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
		catch (final PropertyVetoException pve)
		{
			log.error("", pve);
		}
		if (newM_AttributeSetInstance_ID == previousM_AttributeSetInstance_ID && m_GridTab != null && m_GridField != null)
		{
			// force Change - user does not realize that embedded object is already saved.
			m_GridTab.processFieldChange(m_GridField);
		}
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt)
	{
		final String propertyName = evt.getPropertyName();
		if (propertyName.equals(org.compiere.model.GridField.PROPERTY))
		{
			setValue(evt.getNewValue());
		}
		else if (propertyName.equals(org.compiere.model.GridField.REQUEST_FOCUS))
		{
			requestFocus();
		}
	}

	@Override
	public boolean isAutoCommit()
	{
		return true;
	}

	@Override
	public void addMouseListener(final MouseListener l)
	{
		m_text.addMouseListener(l);
	}
}	// VPAttribute
