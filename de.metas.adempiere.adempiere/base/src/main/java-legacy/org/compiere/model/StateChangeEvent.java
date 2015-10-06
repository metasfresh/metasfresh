/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2007 Adempiere, Inc. All Rights Reserved.                    *
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
package org.compiere.model;

import java.util.EventObject;

/**
 * 
 * @author Low Heng Sin
 *
 */
public class StateChangeEvent extends EventObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8536782772491762290L;
	
	// Metas start: R.Craciunescu@metas.ro: 02280
	private final int eventType;
	// Metas end: R.Craciunescu@metas.ro: 02280

	/**
	 * 
	 * @param source
	 * @param eventType
	 */
	public StateChangeEvent(Object source, int eventType)
	{
		super(source);
		this.eventType = eventType;
	}
	
	public final static int DATA_REFRESH_ALL = 0;
	public final static int DATA_REFRESH = 1;
	public final static int DATA_NEW = 2;
	public final static int DATA_DELETE = 3;
	public final static int DATA_SAVE = 4;
	public final static int DATA_IGNORE = 5;

	// Metas start: R.Craciunescu@metas.ro: 02280
	/**
	 * 
	 * @return event type (DATA_*)
	 */
	public int getEventType()
	{
		return eventType;
	}
	// Metas end: R.Craciunescu@metas.ro: 02280
}
