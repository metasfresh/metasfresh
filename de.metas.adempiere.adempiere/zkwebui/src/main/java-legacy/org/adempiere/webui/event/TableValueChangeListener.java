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

package org.adempiere.webui.event;

/**
 * Interface specifying the functions
 * that must be implemented to listen for a TableValueChangeEvent event
 * 
 * @author Andrew Kimball
 */
public interface TableValueChangeListener
{
	/**
	 * Respond to a TableValueChangeEvent event
	 * Notifies this listener that an event has occurred.
	 * To get the event, you have to register it first by use of 
	 * {@link org.adempiere.webui.component.WListItemRenderer#addTableValueChangeListener(TableValueChangeListener)}
	 * 
	 * @param event	The event that has occurred
	 */
	public void tableValueChange(TableValueChangeEvent event);
}
