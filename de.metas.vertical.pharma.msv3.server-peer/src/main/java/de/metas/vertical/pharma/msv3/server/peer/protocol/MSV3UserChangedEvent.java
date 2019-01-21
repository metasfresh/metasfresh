package de.metas.vertical.pharma.msv3.server.peer.protocol;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.msv3.server
 * %%
 * Copyright (C) 2018 metas GmbH
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
@ToString(exclude = "password")
public class MSV3UserChangedEvent
{
	public static MSV3UserChangedEventBuilder prepareCreatedOrUpdatedEvent(@NonNull final MSV3MetasfreshUserId msv3MetasfreshUserId)
	{
		return _builder()
				.changeType(ChangeType.CREATED_OR_UPDATED)
				.msv3MetasfreshUserId(msv3MetasfreshUserId);
	}

	public static MSV3UserChangedEvent deletedEvent(@NonNull final MSV3MetasfreshUserId msv3MetasfreshUserId)
	{
		return _builder()
				.changeType(ChangeType.DELETED)
				.msv3MetasfreshUserId(msv3MetasfreshUserId)
				.build();
	}

	public static enum ChangeType
	{
		CREATED_OR_UPDATED, DELETED
	};

	@JsonProperty("msv3MetasfreshUserId")
	private MSV3MetasfreshUserId msv3MetasfreshUserId;

	@JsonProperty("changeType")
	private ChangeType changeType;

	@JsonProperty("username")
	private String username;

	@JsonProperty("password")
	private String password;

	@JsonProperty("bpartnerId")
	private Integer bpartnerId;

	@JsonProperty("bpartnerLocationId")
	private Integer bpartnerLocationId;

	@JsonCreator
	@Builder(builderMethodName = "_builder")
	private MSV3UserChangedEvent(
			@JsonProperty("msv3MetasfreshUserId") @NonNull final MSV3MetasfreshUserId msv3MetasfreshUserId,
			@JsonProperty("changeType") @NonNull final ChangeType changeType,
			@JsonProperty("username") @Nullable final String username,
			@JsonProperty("password") @Nullable final String password,
			@JsonProperty("bpartnerId") @Nullable final Integer bpartnerId,
			@JsonProperty("bpartnerLocationId") @Nullable final Integer bpartnerLocationId)
	{
		this.msv3MetasfreshUserId = msv3MetasfreshUserId;
		this.changeType = changeType;
		this.username = username;
		this.password = password;
		this.bpartnerId = bpartnerId;
		this.bpartnerLocationId = bpartnerLocationId;
	}
}
