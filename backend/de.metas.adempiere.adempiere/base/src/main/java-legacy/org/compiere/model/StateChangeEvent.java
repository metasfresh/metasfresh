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

import com.google.common.base.MoreObjects;

/**
 * 
 * @author Low Heng Sin
 *
 */
@SuppressWarnings("serial")
public final class StateChangeEvent extends EventObject
{
	public static enum StateChangeEventType
	{
		DATA_REFRESH_ALL, DATA_REFRESH, DATA_NEW, DATA_DELETE, DATA_SAVE, DATA_IGNORE
	}

	private final StateChangeEventType eventType;

	/**
	 * 
	 * @param source
	 * @param eventType
	 */
	public StateChangeEvent(final GridTab source, final StateChangeEventType eventType)
	{
		super(source);
		this.eventType = eventType;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("eventType", eventType)
				.add("source", source)
				.toString();
	}

	@Override
	public GridTab getSource()
	{
		return (GridTab)super.getSource();
	}

	/**
	 * 
	 * @return event type (DATA_*)
	 */
	public StateChangeEventType getEventType()
	{
		return eventType;
	}
}
