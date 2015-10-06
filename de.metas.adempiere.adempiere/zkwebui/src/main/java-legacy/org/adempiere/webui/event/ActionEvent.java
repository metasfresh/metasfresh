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
 * ActionEvent represents events associated with no data change.
 * 
 * @author  Niraj Sohun
 * @date    Jul 25, 2007
 */

public class ActionEvent 
{
	 /**
     * The object on which the Event initially occurred.
     */
	protected Object source;

    /**
     * Name of the property that changed. May be null, if not known.
     */
    private String propertyName;

    /**
     * Name of event (ON_CLICK, ...)
     */
    private String 	eventName;
    
    /**
     * Constructor
     * @param source - event source
     * @param propertyName - name of property that changed
     * @param eventName - name of event
     */
    public ActionEvent(Object source, String propertyName, String eventName)
    {
        this.source = source;
        this.propertyName = propertyName;
        this.eventName = eventName;
	}

    /**
     * returns name of property that changed
     */
	public String getPropertyName()
    {
        return propertyName;
    }

	/**
	 * returns source of event
	 */
    public Object getSource()
    {
        return source;
    }

    /**
     * returns name of event
     */
	public String getEventName() 
	{
		return eventName;
	}
}
