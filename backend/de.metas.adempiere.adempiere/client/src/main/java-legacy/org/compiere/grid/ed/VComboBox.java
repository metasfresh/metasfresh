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

import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.swing.ComboBoxModel;

import org.compiere.model.MLocator;
import org.compiere.swing.CComboBox;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;

/**
 *  Combobox with KeyNamePair/ValueNamePair or Locator.
 *  <p>
 *  It has the same hight as a TextField
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: VComboBox.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class VComboBox extends CComboBox
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2024662772161020317L;

	/**
	 *  Constructor
	 */
	public VComboBox()
	{
		super();
//		common_init();
	}
	public VComboBox(Object[] items)
	{
		super(items);
//		common_init();
	}
	public VComboBox(ComboBoxModel model)
	{
		super(model);
//		common_init();
	}	//	VComboBox

	/**	Logger			*/
	private static final Logger log = LogManager.getLogger(VComboBox.class);
	
	/**
	 *  Common Setup
	 *
	private void common_init()
	{
		LookAndFeel.installColorsAndFont(this, "TextField.background", "TextField.foreground", "TextField.font");
		setForeground(AdempierePLAF.getTextColor_Normal());
		setBackground(AdempierePLAF.getFieldBackground_Normal());
		setPreferredSize(s_text.getPreferredSize());
	//	this.setKeySelectionManager(new ComboSelectionManager());
	}   //  common_init

	/** Reference Field         *
	private static  JTextField  s_text = new JTextField(VTextField.DISPLAY_SIZE);

	/**
	 *	Set Selected Item to key.
	 *		Find key value in list
	 *  @param key
	 */
	@Override
	public void setValue(Object key)
	{
		setValueAndReturnIfSet(key);
	}
	
	public boolean setValueAndReturnIfSet(Object key)
	{
		if (key == null)
		{
			this.setSelectedIndex(-1);
			return true;
		}

		final ComboBoxModel model = getModel();
		int size = model.getSize();
		for (int i = 0; i < size; i++)
		{
			Object element = model.getElementAt(i);
			String ID = null;
			if (element instanceof NamePair)
				ID = ((NamePair)element).getID();
			else if (element instanceof MLocator)
				ID = String.valueOf(((MLocator)element).getM_Locator_ID());
			else
				log.error("Element not NamePair - " + element.getClass().toString());

			if (key == null || ID == null)
			{
				if (key == null && ID == null)
				{
					setSelectedIndex(i);
					return true;
				}
			}
			else if (ID.equals(key.toString()))
			{
				setSelectedIndex(i);
				return true;
			}
		}
		
		// Value was not found, reseting the selected value
		setSelectedIndex(-1);
		setSelectedItem(null);
		return false;
	}	//	setValue

	/**
	 *  Set Selected item to key if exists
	 *  @param key
	 */
	public void setValue (int key)
	{
		setValue(String.valueOf(key));
	}   //  setValue

	@Override
    public NamePair getSelectedItem()
    {
    	return (NamePair)super.getSelectedItem();
    }

	/**
	 *	Get Value
	 *  @return key as Integer or String value
	 */
	@Override
	public Object getValue()
	{
		final NamePair p = getSelectedItem();
		if (p == null)
		{
			return null;
		}
		//
		if (p instanceof KeyNamePair)
		{
			if (p.getID() == null)	//	-1 return null
				return null;
			return new Integer(((KeyNamePair)p).getID());
		}
		return p.getID();
	}	//	getValue

	/**
	 *  Get Display
	 *  @return displayed string
	 */
	@Override
	public String getDisplay()
	{
		if (getSelectedIndex() == -1)
		{
			return "";
		}
		//
		final NamePair p = getSelectedItem();
		if (p == null)
		{
			return "";
		}
		
		return p.getName();
	}   //  getDisplay

	@Override
	public boolean isSelectionNone()
	{
		final Object selectedItem = getSelectedItem();
		if (selectedItem == null)
		{
			return true;
		}
		else if (KeyNamePair.EMPTY.equals(selectedItem))
		{
			return true;
		}
		else if (ValueNamePair.EMPTY.equals(selectedItem))
		{
			return true;
		}
		else
		{
			return super.isSelectionNone();
		}

	}

}	//	VComboBox
