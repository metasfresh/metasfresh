package de.metas.procurement.sync.protocol;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

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
 * Used by the remote endpoint to confirm that the package was received.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task https://metasfresh.atlassian.net/browse/FRESH-206
 *
 */
@XmlRootElement(name = "SyncConfirmation")
public class SyncConfirmation
{
	/** event ID on server */
	private String serverEventId;

	/** date when server received it */
	private Date dateConfirmed;

	/**
	 * see {@link #getConfirmId()}
	 */
	private long confirmId;

	/**
	 * The default constructor is required to serialize and deserialize.<br>
	 * However, when developing, please use {@link #forConfirmId(long)} to get an instance, because you generally won't need an instance that has no <code>confirmId</code>.
	 */
	public SyncConfirmation()
	{
	}

	/**
	 * Shall be used by the remote/receiving endpoint to generate a confirmation instance.
	 *
	 * @param syncConfirmationId shall be taken from {@link AbstractSyncModel#getSyncConfirmationId()} which was received from the sending endpoint.<br>
	 *            The sending endpoint will know to correlate this instance with the sync package it send by using the ID.
	 * @return
	 */
	public static SyncConfirmation forConfirmId(final long syncConfirmationId)
	{
		final SyncConfirmation syncConfirmation = new SyncConfirmation();
		syncConfirmation.confirmId = syncConfirmationId;

		return syncConfirmation;
	}

	public void setServerEventId(String serverEventId)
	{
		this.serverEventId = serverEventId;
	}

	/**
	 * Can be Set by the <b>receiver</b> to indicate the ID it sued to locally store the received data.
	 *
	 * @return
	 */
	public String getServerEventId()
	{
		return serverEventId;
	}

	public void setDateConfirmed(Date dateConfirmed)
	{
		this.dateConfirmed = dateConfirmed;
	}

	/**
	 * Shall be set by the <b>receiver</b> to indicate when the receiver actually confirmed the receipt of the sync data.
	 *
	 * @return
	 */
	public Date getDateConfirmed()
	{
		return dateConfirmed;
	}

	/**
	 * Set by the <b>sender</b>, i.e. the party which waits for the confirmation.
	 * It can be seen as a correlation-ID.
	 *
	 * @return
	 */
	public long getConfirmId()
	{
		return confirmId;
	}

	@Override
	public String toString()
	{
		return "SyncConfirmation [server_event_id=" + serverEventId + ", dateReceived=" + dateConfirmed + ", confirmId=" + confirmId + "]";
	}

}
