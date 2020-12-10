/*
 * #%L
 * de-metas-common-procurement
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.common.procurement.sync.protocol.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Used by the remote endpoint to confirm that the package was received.
 *
 * @author metas-dev <dev@metasfresh.com>
 * task https://metasfresh.atlassian.net/browse/FRESH-206
 */
@Data
public class SyncConfirmation
{
	/**
	 * Can be Set by the <b>receiver</b> to indicate the ID it sued to locally store the received data.
	 */
	private String serverEventId;

	/**
	 * Shall be set by the <b>receiver</b> to indicate when the receiver actually confirmed the receipt of the sync data.
	 */
	private Date dateConfirmed;

	/**
	 * Set by the <b>sender</b>, i.e. the party which waits for the confirmation.
	 * It can be seen as a correlation-ID.
	 */
	private long confirmId;

	@Builder
	@JsonCreator
	private SyncConfirmation(
			@JsonProperty("serverEventId") final String serverEventId,
			@JsonProperty("dateConfirmed") final Date dateConfirmed,
			@JsonProperty("confirmId") final long confirmId)
	{
		this.serverEventId = serverEventId;
		this.dateConfirmed = dateConfirmed;
		this.confirmId = confirmId;
	}
	//  */

	/**
	 * Shall be used by the remote/receiving endpoint to generate a confirmation instance.
	 *
	 * @param syncConfirmationId shall be taken from {@link IConfirmableDTO#getSyncConfirmationId()} which was received from the sending endpoint.<br>
	 *                           The sending endpoint will know to correlate this instance with the sync package it send by using the ID.
	 */
	public static SyncConfirmation forConfirmId(final long syncConfirmationId)
	{
		return SyncConfirmation.builder().confirmId(syncConfirmationId).build();
	}

	@Override
	public String toString()
	{
		return "SyncConfirmation [server_event_id=" + serverEventId + ", dateReceived=" + dateConfirmed + ", confirmId=" + confirmId + "]";
	}
}
