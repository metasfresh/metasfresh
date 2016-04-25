package de.metas.procurement.webui.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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

/**
 * A new record shall be stored each time when an instance of {@link AbstractSyncConfirmAwareEntity} is synched with the remote endpoint.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task https://metasfresh.atlassian.net/browse/FRESH-206
 */
@Entity
@Table(name = "sync_confirm")
@SuppressWarnings("serial")
public class SyncConfirm extends AbstractEntity
{
	private String entryType;

	@Column(nullable=true)
	private long entryId;

	/**
	 * See {@link #getEntryUuid()}
	 */
	private String entryUuid;

	private String serverEventId;

	/**
	 * See {@link #getDateConfirmed()}.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateConfirmed;

	/**
	 * See {@link #getDateConfirmReceived()}.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateConfirmReceived;

	@Override
	protected void toString(final ToStringHelper toStringHelper)
	{
		toStringHelper
				.add("entryType", entryType)
				.add("entryUuid", entryUuid)
				.add("serverEventId", serverEventId)
				.add("dateCreated", getDateCreated())
				.add("dateConfirmReceived", dateConfirmReceived);
	}

	public String getEntryType()
	{
		return entryType;
	}

	public void setEntryType(String entryType)
	{
		this.entryType = entryType;
	}

	/**
	 *
	 * @return the UUID of the entry this sync confirm record is about.
	 */
	public String getEntryUuid()
	{
		return entryUuid;
	}

	public void setEntryUuid(String entry_uuid)
	{
		this.entryUuid = entry_uuid;
	}

	public String getServerEventId()
	{
		return serverEventId;
	}

	public void setServerEventId(String serverEventId)
	{
		this.serverEventId = serverEventId;
	}

	/**
	 *
	 * @return the date when this record was created, which is also the date when the sync request was submitted towards the remote endpoint.
	 */
	@Override
	public Date getDateCreated()
	{
		return super.getDateCreated();
	}

	/**
	 *
	 * @return the date when the remote endpoint actually confirmed the data receipt.
	 */
	public Date getDateConfirmed()
	{
		return dateConfirmed;
	}

	public void setDateConfirmed(Date dateConfirmed)
	{
		this.dateConfirmed = dateConfirmed;
	}

	/**
	 *
	 * @return the date when our local endpoint received the remote endpoint's confirmation.
	 */
	public Date getDateConfirmReceived()
	{
		return dateConfirmReceived;
	}

	public void setDateConfirmReceived(Date dateConfirmReceived)
	{
		this.dateConfirmReceived = dateConfirmReceived;
	}

	public long getEntryId()
	{
		return entryId;
	}

	public void setEntryId(long entryId)
	{
		this.entryId = entryId;
	}

}
