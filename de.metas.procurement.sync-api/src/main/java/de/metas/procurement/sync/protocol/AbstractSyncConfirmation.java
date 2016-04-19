package de.metas.procurement.sync.protocol;

import java.util.Date;

/*
 * #%L
 * de.metas.procurement.sync-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Base class for all server sync confirmations.
 * 
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public abstract class AbstractSyncConfirmation
{
	/** event ID on server */
	private String server_event_id;
	
	/** date when server received it */
	private Date dateReceived = new Date();
	
	public void setServer_event_id(String server_event_id)
	{
		this.server_event_id = server_event_id;
	}
	
	public String getServer_event_id()
	{
		return server_event_id;
	}
	
	public void setDateReceived(Date dateReceived)
	{
		this.dateReceived = dateReceived;
	}
	
	public Date getDateReceived()
	{
		return dateReceived;
	}
}
