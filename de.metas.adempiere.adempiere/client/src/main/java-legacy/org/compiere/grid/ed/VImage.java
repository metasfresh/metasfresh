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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.util.logging.Level;

import javax.swing.JButton;

import org.compiere.model.GridField;
import org.compiere.model.MImage;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

/**
 *  Image Display of AD_Iamge_ID
 *
 *  @author  Jorg Janke
 *  @version $Id: VImage.java,v 1.6 2006/07/30 00:51:28 jjanke Exp $
 */
public class VImage extends JButton
	implements VEditor, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 368261613546756534L;

	/**
	 *  Image Editor
	 *  @param columnName column name
	 *  @param WindowNo window no
	 */
	public VImage (String columnName, int WindowNo)
	{
		super("-");
		m_columnName = columnName;
		m_WindowNo = WindowNo;
		super.addActionListener(this);
	}   //  VImage

	/**
	 *  Dispose
	 */
	@Override
	public void dispose()
	{
		m_mImage = null;
	}   //  dispose

	/** WindowNo                */
	private int     m_WindowNo;
	/** The Image Model         */
	private MImage  m_mImage = null;
	/** Mandatory flag          */
	private boolean m_mandatory = false;
	/** Column Name             */
	private String	m_columnName = "AD_Image_ID";
	private boolean displayImagePreview = true; // default=true, backward compatibility
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(VImage.class);

	/**
	 * Sets if the loaded image shall be displayed on button, as an icon.
	 * 
	 * @param displayImagePreview
	 */
	public void setDisplayImagePreview(final boolean displayImagePreview)
	{
		if (this.displayImagePreview == displayImagePreview)
		{
			return;
		}

		this.displayImagePreview = displayImagePreview;
		updateButtonUI();
	}

	/**
	 *  Set Value
	 *  @param value
	 */
	@Override
	public void setValue(final Object value)
	{
		log.log(Level.FINE, "={0}", value);
		
		int newValue = 0;
		if (value instanceof Integer)
		{
			newValue = ((Integer)value).intValue();
		}
		
		if (newValue <= 0)
		{
			m_mImage = null;
			updateButtonUI();
			return;
		}
		//  Get/Create Image
		if (m_mImage == null || newValue != m_mImage.get_ID())
		{
			m_mImage = MImage.get (Env.getCtx(), newValue);
		}
		
		//
		log.fine(m_mImage.toString());
		updateButtonUI();
	}   //  setValue
	
	private final void updateButtonUI()
	{
		// Case: no image
		if (m_mImage == null)
		{
			super.setText("-");
			super.setIcon(null);
			super.setToolTipText(null);
		}
		// Case: we have an image and we shall display it's preview
		else if (displayImagePreview)
		{
			super.setText(null);
			super.setIcon(m_mImage.getIcon());
			super.setToolTipText(m_mImage.getName());
			invalidate();
		}
		// Case: we have an image but we are not displaying it's preview
		else
		{
			super.setText(m_mImage.getName());
			super.setIcon(null); // no preview!
			super.setToolTipText(null);
			invalidate();
		}
	}

	/**
	 *  Get Value
	 *  @return value
	 */
	@Override
	public Object getValue()
	{
		if (m_mImage == null || m_mImage.get_ID() == 0)
			return null;
		return new Integer(m_mImage.get_ID());
	}   //  getValue

	/**
	 *  Get Display Value
	 *  @return image name
	 */
	@Override
	public String getDisplay()
	{
		return m_mImage.getName();
	}   //  getDisplay

	/**
	 *  Set ReadWrite
	 *  @param rw
	 */
	@Override
	public void setReadWrite (boolean rw)
	{
		if (isEnabled() != rw)
			setEnabled (rw);
	}   //  setReadWrite

	/**
	 *  Get ReadWrite
	 *  @return true if rw
	 */
	@Override
	public boolean isReadWrite()
	{
		return super.isEnabled();
	}   //  getReadWrite

	/**
	 *  Set Mandatory
	 *  @param mandatory
	 */
	@Override
	public void setMandatory (boolean mandatory)
	{
		m_mandatory = mandatory;
	}   //  setMandatory

	/**
	 *  Get Mandatory
	 *  @return true if mandatory
	 */
	@Override
	public boolean isMandatory()
	{
		return m_mandatory;
	}   //  isMandatory

	/**
	 *  Set Background - nop
	 *  @param color
	 */
	@Override
	public void setBackground(Color color)
	{
	}   //  setBackground

	/**
	 *  Set Background - nop
	 */
	public void setBackground()
	{
	}   //  setBackground

	/**
	 *  Set Background - nop
	 *  @param error
	 */
	@Override
	public void setBackground(boolean error)
	{
	}   //  setBackground

	/**
	 *  Property Change
	 *  @param evt
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		if (evt.getPropertyName().equals(org.compiere.model.GridField.PROPERTY))
			setValue(evt.getNewValue());
		
		// metas: request focus (2009_0027_G131) 
		if (evt.getPropertyName().equals(org.compiere.model.GridField.REQUEST_FOCUS))
			requestFocus();
		// metas end
		
	}   //  propertyChange

	/**
	 *  ActionListener - start dialog and set value
	 *  @param e
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		VImageDialog vid = new VImageDialog(Env.getWindow(m_WindowNo), m_mImage);
		vid.setVisible(true);
		int AD_Image_ID = vid.getAD_Image_ID();
		Integer newValue = null;
		if (AD_Image_ID != 0)
			newValue = new Integer (AD_Image_ID);
		//
		m_mImage = null;	//	force reload
		setValue(newValue);	//	set explicitly
		//
		try
		{
			fireVetoableChange(m_columnName, null, newValue);
		}
		catch (PropertyVetoException pve)	{}
	}   //  actionPerformed

	//	Field for Value Preference
	private GridField          m_mField = null;
	/**
	 *  Set Field/WindowNo for ValuePreference (NOP)
	 *  @param mField
	 */
	@Override
	public void setField (GridField mField)
	{
		m_mField = mField;
	}   //  setField

	@Override
	public GridField getField() {
		return m_mField;
	}

	// metas
	@Override
	public boolean isAutoCommit()
	{
		return true;
	}

}   //  VImage
