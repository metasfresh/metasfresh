package de.metas.material.event.commons;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import de.metas.event.EventInfo;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.util.UUID;

/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2017 metas GmbH
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
@Value
@Builder(toBuilder = true)
@Jacksonized
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class EventDescriptor
{
	@NonNull String eventId;
	@NonNull ClientAndOrgId clientAndOrgId;
	@Nullable String traceId;

	public static EventDescriptor ofClientAndOrg(final int adClientId, final int adOrgId)
	{
		return ofClientAndOrg(ClientAndOrgId.ofClientAndOrg(adClientId, adOrgId));
	}

	public static EventDescriptor ofClientAndOrg(@NonNull final ClientId adClientId, @NonNull final OrgId adOrgId)
	{
		return ofClientAndOrg(ClientAndOrgId.ofClientAndOrg(adClientId, adOrgId));
	}

	public static EventDescriptor ofClientAndOrg(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		return builder()
				.eventId(newEventId())
				.clientAndOrgId(clientAndOrgId)
				.build();
	}

	public static EventDescriptor ofClientOrgAndTraceId(@NonNull final ClientAndOrgId clientAndOrgId, @Nullable final String traceId)
	{
		return builder()
				.eventId(newEventId())
				.clientAndOrgId(clientAndOrgId)
				.traceId(traceId)
				.build();
	}

	private static String newEventId()
	{
		return UUID.randomUUID().toString();
	}

	public ClientId getClientId()
	{
		return getClientAndOrgId().getClientId();
	}

	public OrgId getOrgId()
	{
		return getClientAndOrgId().getOrgId();
	}

	public EventDescriptor withNewEventId()
	{
		return toBuilder()
				.eventId(newEventId())
				.build();
	}
}