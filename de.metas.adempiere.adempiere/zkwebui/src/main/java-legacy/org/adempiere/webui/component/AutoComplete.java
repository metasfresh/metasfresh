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

import java.util.Arrays;
import java.util.Iterator;

import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Comboitem;

/**
 * 	Auto-complete with combobox.
 * 	Based on ZK's Auto-complete
 * 
 * 	@author Niraj Sohun
 * 			Aug 20, 2007
 */

public class AutoComplete extends Combobox 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2642639623099513816L;

	/** comboItems	All menu labels	 */
	private String[] comboItems;
	
	/** strDescription	Description of menu items	 */
	private String[] strDescription;

	/**
	 * Set menu labels
	 * 
	 * @param vals	Menu labels
	 */
	
	public void setDict(String[] vals)
	{
		comboItems = vals;
		
		if (comboItems != null)
		{
			Arrays.sort(comboItems);
		}
	}
	
	/**
	 * Set description of menu items
	 * 
	 * @param vals	Description of menu items
	 */
	
	public void setDescription(String[] vals)
	{
		strDescription = vals;
	}
	
	/**
	 * 	Constructor
	 */
	
	public AutoComplete() 
	{
		if (comboItems != null)
			refresh("");
	}
	
	public AutoComplete(String value) 
	{
		super.setValue(value);
	}

	public void setValue(String value) 
	{
		super.setValue(value);
		refresh(value);
	}
	
	/**
	 * Event handler responsible to reducing number of items
	 * Method is invoked each time something is typed in the combobox
	 * 
	 * @param evt	The event
	 */
	
	public void onChanging(InputEvent evt) 
	{
		if (!evt.isChangingBySelectBack())
		{
			refresh(evt.getValue());
		}
	}
	
	/** 
	 * Refresh comboitem based on the specified value.
	*/	
	private void refresh(String val) 
	{
		if (comboItems == null || val == null) {
			super.getChildren().clear();
			return;
		}
		
		String compare = val.toLowerCase().trim();
		
		Iterator<?> it = getItems().iterator();
		for (int i = 0; i < comboItems.length; i++)
		{
			boolean match = false;
			if (compare.length() < 3)
			{
				match = comboItems[i].toLowerCase().startsWith(compare);
			}
			else
			{
				match = comboItems[i].toLowerCase().contains(compare);
			}
			if (match)
			{
				Comboitem comboitem = null;
				if (it != null && it.hasNext()) {
					comboitem = ((Comboitem)it.next());
			    } else {
			        it = null;
			        comboitem = new Comboitem();
			        super.appendChild(comboitem);
			    }

				comboitem.setLabel(comboItems[i]);
				comboitem.setDescription(strDescription[i]);				
			}
		}
		while (it != null && it.hasNext()) {
	      it.next();
	      it.remove();
	    }		
	}
}
