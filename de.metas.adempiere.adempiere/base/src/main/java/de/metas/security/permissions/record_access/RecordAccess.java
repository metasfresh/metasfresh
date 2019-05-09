package de.metas.security.permissions.record_access;

import org.adempiere.util.lang.impl.TableRecordReference;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.security.Principal;
import de.metas.security.permissions.Access;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

	@Builder(toBuilder = true)
	@JsonCreator
	private RecordAccess(
			@JsonProperty("recordRef") @NonNull final TableRecordReference recordRef,
			@JsonProperty("principal") @NonNull final Principal principal,
			@JsonProperty("permission") @NonNull final Access permission)
	{
		this.recordRef = recordRef;
		this.principal = principal;
		this.permission = permission;
	}

	public RecordAccess withRecordRef(@NonNull final TableRecordReference recordRef)
	{
		if (this.recordRef.equals(recordRef))
		{
			return this;
		}

		return toBuilder().recordRef(recordRef).build();
	}
}
