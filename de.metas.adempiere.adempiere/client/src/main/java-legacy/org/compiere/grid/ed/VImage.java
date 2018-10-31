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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;

import javax.swing.Icon;
import javax.swing.JButton;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.model.GridField;
import org.compiere.model.MImage;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Env;

import de.metas.adempiere.form.IClientUI;

/**
 *  Image Display of AD_Iamge_ID
 *
 *  @author  Jorg Janke
 *  @version $Id: VImage.java,v 1.6 2006/07/30 00:51:28 jjanke Exp $
 */
public class VImage extends JButton
	implements VEditor
	//, ActionListener
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
	public VImage (final String columnName, final int WindowNo)
	{
		super("-");
		m_columnName = columnName;
		m_WindowNo = WindowNo;
		
		addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				onButtonPressed();
			}
		});
		
		setBackground(false);
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
	private final int m_WindowNo;
	/** The Image Model         */
	private MImage  m_mImage = null;
	/** Mandatory flag          */
	private boolean m_mandatory = false;
	private boolean readWrite = true;
	/** Column Name             */
	private final String m_columnName;
	private boolean displayImagePreview = true; // default=true, backward compatibility
	private Dimension previewMaxSize = null;
	
	/**	Logger			*/
	private static final transient Logger log = LogManager.getLogger(VImage.class);

	/**
	 * Sets if the loaded image shall be displayed on button, as an icon.
	 * 
	 * @param displayImagePreview
	 * @see #setPreviewMaxSize(Dimension)
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
	 * Sets image preview maximum size
	 * 
	 * @param previewMaxSize
	 * @see #setDisplayImagePreview(boolean)
	 */
	public void setPreviewMaxSize(final Dimension previewMaxSize)
	{
		if (Check.equals(this.previewMaxSize, previewMaxSize))
		{
			return;
		}
		
		this.previewMaxSize = (Dimension)previewMaxSize.clone();
		updateButtonUI();
	}

	/**
	 *  Set Value
	 *  @param value
	 */
	@Override
	public void setValue(final Object value)
	{
		log.debug("={}", value);
		
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
		log.debug(m_mImage.toString());
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
			final Dimension previewMaxSize = this.previewMaxSize == null ? null : (Dimension)this.previewMaxSize.clone();
			final Icon icon = m_mImage.getIcon(previewMaxSize);
			super.setText(null);
			super.setIcon(icon);
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
		return m_mImage == null ? "" : m_mImage.getName();
	}   //  getDisplay

	/**
	 *  Set ReadWrite
	 *  @param rw
	 */
	@Override
	public void setReadWrite (boolean rw)
	{
		if (this.readWrite == rw)
		{
			return;
		}
		
		setEnabled(rw);
		setBackground(false);
	}   //  setReadWrite

	/**
	 *  Get ReadWrite
	 *  @return true if rw
	 */
	@Override
	public boolean isReadWrite()
	{
		return readWrite;
	}   //  getReadWrite

	/**
	 *  Set Mandatory
	 *  @param mandatory
	 */
	@Override
	public void setMandatory (final boolean mandatory)
	{
		if (this.m_mandatory == mandatory)
		{
			return;
		}
		
		m_mandatory = mandatory;
		setBackground(false);
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
	}

	/**
	 *  Set Background
	 *  @param error
	 */
	@Override
	public void setBackground(boolean error)
	{
		if (error)
			super.setBackground(AdempierePLAF.getFieldBackground_Error());
		else if (!isReadWrite())
			super.setBackground(AdempierePLAF.getFieldBackground_Inactive());
		else if (isMandatory())
			super.setBackground(AdempierePLAF.getFieldBackground_Mandatory());
		else
			super.setBackground(AdempierePLAF.getFieldBackground_Normal());
	}

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
	 *  Start dialog and set value
	 */
	private final void onButtonPressed()
	{
		try
		{
			// Show the dialog
			final VImageDialog vid = new VImageDialog(Env.getWindow(m_WindowNo), m_WindowNo, m_mImage);
			vid.setVisible(true);

			// Do nothing if user canceled (i.e. closed the window)
			if (vid.isCanceled())
			{
				return;
			}
			
			final int AD_Image_ID = vid.getAD_Image_ID();
			final Integer newValue = AD_Image_ID > 0 ? AD_Image_ID : null;
			//
			m_mImage = null;	//	force reload
			setValue(newValue);	//	set explicitly
			//
			try
			{
				fireVetoableChange(m_columnName, null, newValue);
			}
			catch (PropertyVetoException pve)	{}
		}
		catch (Exception e)
		{
			Services.get(IClientUI.class).error(m_WindowNo, e);
		}
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
