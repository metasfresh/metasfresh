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

package de.metas.common.procurement.sync.protocol.request_to_metasfresh;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.procurement.sync.protocol.RequestToMetasfresh;
import de.metas.common.procurement.sync.protocol.dto.SyncConfirmation;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Value
public class PutConfirmationToMetasfreshRequest extends RequestToMetasfresh
{
	public static PutConfirmationToMetasfreshRequest of(@NonNull final List<SyncConfirmation> syncConfirmations)
	{
		return PutConfirmationToMetasfreshRequest.builder().syncConfirmations(syncConfirmations).build();
	}

	String eventId;
	List<SyncConfirmation> syncConfirmations;

	@Builder
	@JsonCreator
	private PutConfirmationToMetasfreshRequest(
			@JsonProperty("eventId") final String eventId,
			@JsonProperty("syncConfirmations") @Singular final List<SyncConfirmation> syncConfirmations)
	{
		this.eventId = eventId != null ? eventId : UUID.randomUUID().toString();
		this.syncConfirmations = new ArrayList<>(syncConfirmations);
	}

	@JsonIgnore
	public boolean isEmpty()
	{
		return syncConfirmations.isEmpty();
	}

	@JsonIgnore
	public void add(SyncConfirmation syncConfirmation)
	{
		syncConfirmations.add(syncConfirmation);
	}

	@JsonIgnore
	public void clear()
	{
		syncConfirmations.clear();
	}
}
