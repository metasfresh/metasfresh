package de.metas.dataentry.data;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.user.UserId;
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
public class DataEntryCreatedUpdatedInfo
{
	public static DataEntryCreatedUpdatedInfo createNew(
			@NonNull final UserId createdBy,
			@NonNull final ZonedDateTime created)
	{
		return new DataEntryCreatedUpdatedInfo(createdBy, created, createdBy, created);
	}

	public static DataEntryCreatedUpdatedInfo of(
			@NonNull final ZonedDateTime created,
			@NonNull final UserId createdBy,
			@NonNull final ZonedDateTime updated,
			@NonNull final UserId updatedBy)
	{
		return new DataEntryCreatedUpdatedInfo(createdBy, created, updatedBy, updated);
	}

	UserId createdBy;
	ZonedDateTime created;
	UserId updatedBy;
	ZonedDateTime updated;

	@Builder
	@JsonCreator
	private DataEntryCreatedUpdatedInfo(
			@NonNull @JsonProperty("createdBy") final UserId createdBy,
			@NonNull @JsonProperty("created") final ZonedDateTime created,
			@NonNull @JsonProperty("updatedBy") final UserId updatedBy,
			@NonNull @JsonProperty("updated") final ZonedDateTime updated)
	{
		this.createdBy = createdBy;
		this.created = created;
		this.updatedBy = updatedBy;
		this.updated = updated;
	}

	public DataEntryCreatedUpdatedInfo updated(
			@NonNull final UserId updatedBy,
			@NonNull final ZonedDateTime updated)
	{
		return new DataEntryCreatedUpdatedInfo(createdBy, created, updatedBy, updated);
	}

}
