package de.metas.procurement.webui.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.gwt.thirdparty.guava.common.base.Objects.ToStringHelper;

/*
 * #%L
 * de.metas.procurement.webui
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

@Entity
@Table(name = "sync_confirm")
@SuppressWarnings("serial")
public class SyncConfirm extends AbstractEntity
{
	private String entry_type;
	private String entry_uuid;
	private String server_event_id;
	private Date serverDateReceived;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Override
	protected void toString(final ToStringHelper toStringHelper)
	{
		toStringHelper
				.add("entry_type", entry_type)
				.add("entry_uuid", entry_uuid)
				.add("server_event_id", server_event_id)
				.add("serverDateReceived", serverDateReceived);
	}

	@PreUpdate
	@PrePersist
	public void updateCreatedUpdated()
	{
		if (dateCreated == null)
		{
			final Date now = new Date();
			dateCreated = now;
		}
	}

	public String getEntry_type()
	{
		return entry_type;
	}

	public void setEntry_type(String entry_type)
	{
		this.entry_type = entry_type;
	}

	public String getEntry_uuid()
	{
		return entry_uuid;
	}

	public void setEntry_uuid(String entry_uuid)
	{
		this.entry_uuid = entry_uuid;
	}

	public String getServer_event_id()
	{
		return server_event_id;
	}

	public void setServer_event_id(String server_event_uuid)
	{
		this.server_event_id = server_event_uuid;
	}

	public Date getDateCreated()
	{
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated)
	{
		this.dateCreated = dateCreated;
	}

	public Date getServerDateReceived()
	{
		return serverDateReceived;
	}

	public void setServerDateReceived(Date serverDateReceived)
	{
		this.serverDateReceived = serverDateReceived;
	}

}
