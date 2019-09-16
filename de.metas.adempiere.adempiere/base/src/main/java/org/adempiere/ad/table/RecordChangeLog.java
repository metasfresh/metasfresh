package org.adempiere.ad.table;

import java.time.Instant;
import java.util.Objects;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Value
public class RecordChangeLog
{
	String tableName;

	ComposedRecordId recordId;

	UserId createdByUserId;

	Instant createdTimestamp;

	UserId lastChangedByUserId;

	Instant lastChangedTimestamp;

	ImmutableList<RecordChangeLogEntry> entries;

	public boolean hasChanges()
	{
		return !Objects.equals(createdByUserId, lastChangedByUserId)
				|| !Objects.equals(createdTimestamp, lastChangedTimestamp)
				|| !entries.isEmpty();
	}

	/**
	 * Both {@code createdByUserId} and {@code lastChangedByUserId} can be {@code null} if the respective DB columns have a value less than zero.
	 * This happens if there is no user-id in the context while a DB record is saved.
	 */
	@Builder
	private RecordChangeLog(
			@NonNull String tableName,
			@NonNull ComposedRecordId recordId,
			@Nullable UserId createdByUserId,
			@NonNull Instant createdTimestamp,
			@Nullable UserId lastChangedByUserId,
			@NonNull Instant lastChangedTimestamp,
			@NonNull @Singular ImmutableList<RecordChangeLogEntry> entries)
	{
		this.tableName = tableName;
		this.recordId = recordId;
		this.createdByUserId = createdByUserId;
		this.createdTimestamp = createdTimestamp;
		this.lastChangedByUserId = lastChangedByUserId;
		this.lastChangedTimestamp = lastChangedTimestamp;
		this.entries = entries;
	}

}
