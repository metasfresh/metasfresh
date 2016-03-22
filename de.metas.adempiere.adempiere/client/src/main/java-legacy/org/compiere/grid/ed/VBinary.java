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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import org.compiere.model.GridField;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

/**
 * 	Binary Editor.
 * 	Shows length of data.
 *	
 *  @author Jorg Janke
 *  @version $Id: VBinary.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class VBinary extends JButton
	implements VEditor, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 298576564679201761L;

	/**
	 *  Binary Editor
	 *  @param columnName column name
	 *  @param WindowNo
	 */
	public VBinary (String columnName, int WindowNo)
	{
		super("");
		m_columnName = columnName; 
		m_WindowNo = WindowNo;
		super.addActionListener(this);
	}   //  VBinary

	/**
	 *  Dispose
	 */
	public void dispose()
	{
		m_data = null;
	}   //  dispose


	/** Column Name             */
	private String	m_columnName;
	/** WindowNo                */
	private int     m_WindowNo;
	/** Data					*/
	private Object	m_data = null;
	
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(VBinary.class);

	/**
	 *  Set Value
	 *  @param value
	 */
	public void setValue(Object value)
	{
		log.info("=" + value);
		m_data = value;
		if (m_data == null)
			setText("-");
		else
		{
			String text = "?";
			if (m_data instanceof byte[])
			{
				byte[] bb = (byte[])m_data;
				text = "#" + bb.length;
			}
			else
			{
				text = m_data.getClass().getName();
				int index = text.lastIndexOf('.');
				if (index != -1)
					text = text.substring(index+1);
			}
			setText(text);
		}
	}   //  setValue

	/**
	 *  Get Value
	 *  @return value
	 */
	public Object getValue()
	{
		return m_data;
	}   //  getValue

	/**
	 *  Get Display Value
	 *  @return image name
	 */
	public String getDisplay()
	{
		return getText();
	}   //  getDisplay

	/**
	 *  Set ReadWrite
	 *  @param rw
	 */
	public void setReadWrite (boolean rw)
	{
		if (isEnabled() != rw)
			setEnabled (rw);
	}   //  setReadWrite

	/**
	 *  Get ReadWrite
	 *  @return true if rw
	 */
	public boolean isReadWrite()
	{
		return super.isEnabled();
	}   //  getReadWrite

	/**
	 *  Set Mandatory
	 *  @param mandatory NOP
	 */
	public void setMandatory (boolean mandatory)
	{
	}   //  setMandatory

	/**
	 *  Get Mandatory
	 *  @return false
	 */
	public boolean isMandatory()
	{
		return false;
	}   //  isMandatory

	/**
	 *  Set Background - nop
	 *  @param color
	 */
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
	public void setBackground(boolean error)
	{
	}   //  setBackground

	/**
	 *  Property Change
	 *  @param evt
	 */
	public void propertyChange(PropertyChangeEvent evt)
	{
		log.info(evt.toString());
		if (evt.getPropertyName().equals(org.compiere.model.GridField.PROPERTY))
			setValue(evt.getNewValue());
		
		// metas: request focus (2009_0027_G131) 
		if (evt.getPropertyName().equals(org.compiere.model.GridField.REQUEST_FOCUS))
			requestFocus();
		// metas end

	}   //  propertyChange

	/**
	 *  ActionListener - start dialog and set value
	 *  @param e event
	 */
	public void actionPerformed (ActionEvent e)
	{
		JFileChooser fc = new JFileChooser("");
		fc.setMultiSelectionEnabled(false);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int option = 0;
		boolean save = m_data != null;
		if (save)
			option = fc.showSaveDialog(this);
		else
			option = fc.showOpenDialog(this);
		if (option != JFileChooser.APPROVE_OPTION)
			return;
		File file = fc.getSelectedFile();
		if (file == null)
			return;
		//
		log.info(file.toString());
		try
		{
			if (save)
			{
				FileOutputStream os = new FileOutputStream(file);
				byte[] buffer = (byte[])m_data;
				os.write(buffer);
				os.flush();
				os.close();
				log.info("Save to " + file + " #" + buffer.length);
			}
			else	//	load
			{
				FileInputStream is = new FileInputStream(file);
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024*8];   //  8kB
				int length = -1;
				while ((length = is.read(buffer)) != -1)
					os.write(buffer, 0, length);
				is.close();
				byte[] data = os.toByteArray();
				m_data = data;
				log.info("Load from " + file + " #" + data.length);
				os.close();
			}
		}
		catch (Exception ex)
		{
			log.warn("Save=" + save, ex);
		}
		
		try
		{
			fireVetoableChange(m_columnName, null, m_data);
		}
		catch (PropertyVetoException pve)	{}
	}   //  actionPerformed

	//	Field for Value Preference
	private GridField          m_mField = null;
	/**
	 *  Set Field/WindowNo for ValuePreference (NOP)
	 *  @param mField
	 */
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

}	//	VBinary
