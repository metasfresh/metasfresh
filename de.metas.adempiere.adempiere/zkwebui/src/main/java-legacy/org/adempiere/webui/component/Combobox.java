/******************************************************************************
 * Product: Posterita Ajax UI 												  *
 * Copyright (C) 2007 Posterita Ltd.  All Rights Reserved.                    *
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
 * Posterita Ltd., 3, Draper Avenue, Quatre Bornes, Mauritius                 *
 * or via info@posterita.org or http://www.posterita.org/                     *
 *****************************************************************************/

package org.adempiere.webui.component;

import java.util.List;

import org.zkoss.zul.Comboitem;

/**
 *
 * @author  <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @date    Feb 25, 2007
 * @version $Revision: 0.10 $
 */
public class Combobox extends org.zkoss.zul.Combobox
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6278632602577424842L;

	public void setEnabled(boolean enabled)
    {
        this.setDisabled(!enabled);
    }
    
    public Comboitem appendItem(String label) 
    {
        ComboItem item = new ComboItem(label);
        item.setParent(this);
        return item;
    }

	public boolean isEnabled() {
		return !isDisabled();
	}
	
	/**
	 * remove all items, to ease porting of swing form
	 */
	public void removeAllItems() {
		int cnt = getItemCount();
		for (int i = cnt - 1; i >=0; i--) {
			removeItemAt(i);
		}
	}

	public void appendItem(String name, Object value) {
		ComboItem item = new ComboItem(name, value);
		this.appendChild(item);
	}
	
	 /** 
     * Set selected item for the list box based on the value of list item
     * set selected to none if no item found matching the value given or 
     * value is null
     * @param value Value of ListItem to set as selected
     */
    public void setValue(Object value)
    {
        setSelectedItem(null);
        
        Comboitem item = getComboitemByValue(value);
        if (item != null)
        	setSelectedItem(item);
    }
    
    /**
     * 
     * @param value
     * @return boolean
     */
    public boolean isSelected(Object value) 
    {
    	if (value == null)
    		return false;
    	
    	Comboitem item = getSelectedItem();
    	if (item == null)
    		return false;
    	
    	return item.getValue().equals(value);
    }
    
    /** Returns RS_NO_WIDTH|RS_NO_HEIGHT.
	 */
	protected int getRealStyleFlags() {
		return super.getRealStyleFlags() & 0x0006;
	}

	// metas: begin
	public Object getSelectedValue()
	{
    	Comboitem item = getSelectedItem();
    	if (item == null)
    		return null;
    	
    	return item.getValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comboitem> getItems()
	{
		return super.getItems();
	}
	
	private Comboitem getComboitemByValue(Object value)
	{
		if (value == null)
		{
			return null;
		}

		List<Comboitem> items = getItems();
		for (Comboitem item : items)
		{
			if (value.getClass() != item.getValue().getClass())
			{
				// if the classes of value and item are different convert both to String
				String stringValue = value.toString();
				String stringItem = item.getValue().toString();
				if (stringValue.equals(stringItem))
				{
					return item;
				}
			}
			else
			{
				if (value.equals(item.getValue()))
				{
					return item;
				}
			}
		}
		return null;
	}
	
	public boolean containsValue(Object value)
	{
		return getComboitemByValue(value) != null;
	}
	// metas: end
}