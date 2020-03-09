package de.metas.security.permissions.record_access;

import javax.annotation.Nullable;

import org.adempiere.util.lang.impl.TableRecordReference;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.security.Principal;
import de.metas.security.permissions.Access;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class RecordAccess
{
	@JsonProperty("recordRef")
	TableRecordReference recordRef;

	@JsonProperty("principal")
	Principal principal;

	@JsonProperty("permission")
	Access permission;

	@JsonProperty("issuer")
	PermissionIssuer issuer;

	@JsonProperty("createdBy")
	UserId createdBy;

	@JsonProperty("description")
	String description;

	@JsonProperty("id")
	@NonFinal
	RecordAccessId id;

	@JsonProperty("parentId")
	RecordAccessId parentId;

	@JsonProperty("rootId")
	@NonFinal
	RecordAccessId rootId;

	@Builder(toBuilder = true)
	@JsonCreator
	private RecordAccess(
			@JsonProperty("recordRef") @NonNull final TableRecordReference recordRef,
			@JsonProperty("principal") @NonNull final Principal principal,
			@JsonProperty("permission") @NonNull final Access permission,
			@JsonProperty("issuer") @NonNull final PermissionIssuer issuer,
			@JsonProperty("createdBy") @NonNull final UserId createdBy,
			@JsonProperty("description") @Nullable final String description,
			//
			@JsonProperty("id") @Nullable final RecordAccessId id,
			@JsonProperty("parentId") @Nullable final RecordAccessId parentId,
			@JsonProperty("rootId") @Nullable final RecordAccessId rootId)
	{
		this.recordRef = recordRef;
		this.principal = principal;
		this.permission = permission;
		this.issuer = issuer;
		this.createdBy = createdBy;
		this.description = description;

		this.id = id;
		this.parentId = parentId;
		this.rootId = rootId;
	}

	void setId(@NonNull final RecordAccessId id)
	{
		this.id = id;
	}

	void setRootId(final RecordAccessId rootId)
	{
		this.rootId = rootId;
	}
}
