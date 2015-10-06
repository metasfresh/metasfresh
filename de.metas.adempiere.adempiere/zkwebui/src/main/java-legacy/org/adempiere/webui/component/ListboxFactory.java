/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin                                            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.webui.component;

/**
 * 
 * @author Low Heng Sin
 *
 */
public class ListboxFactory {

	/**
	 * Create new listbox instance that is in combo mode
	 * @return Listbox
	 */
	public static Listbox newDropdownListbox() {
		Listbox listbox = new Listbox();
		listbox.setMold("select");
		return listbox;
	}
	
	/**
	 * Create new data table instance for use in custom form.
	 * @return WListbox
	 */
	public static WListbox newDataTable() {
		WListbox dataTable = new WListbox();
		dataTable.setWidth("100%");
		dataTable.setHeight("100%");
		dataTable.setFixedLayout(true);
		dataTable.setVflex(true);
		
		return dataTable;
	}

	public static Listbox newDropdownListbox(String[] items) {
		Listbox listbox = newDropdownListbox();
		if (items != null && items.length > 0) {
			for(int i = 0; i < items.length; i++) {
				listbox.appendChild(new ListItem(items[i], items[i]));
			}
		}
		return listbox;
	}
}
