package de.metas.material.event.commons;

import org.adempiere.service.ClientId;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.organization.OrgId;
import lombok.NonNull;
import lombok.Value;

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
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class EventDescriptor
{
	public static EventDescriptor ofClientAndOrg(final int adClientId, final int adOrgId)
	{
		return new EventDescriptor(
				ClientId.ofRepoId(adClientId),
				OrgId.ofRepoId(adOrgId));
	}

	public static EventDescriptor ofClientAndOrg(@NonNull final ClientId adClientId, @NonNull final OrgId adOrgId)
	{
		return new EventDescriptor(adClientId, adOrgId);
	}

	@JsonProperty("clientId")
	ClientId clientId;
	@JsonProperty("orgId")
	OrgId orgId;

	@JsonCreator
	private EventDescriptor(
			@JsonProperty("clientId") @NonNull final ClientId clientId,
			@JsonProperty("orgId") @NonNull final OrgId orgId)
	{
		this.clientId = clientId;
		this.orgId = orgId;
	}
}
