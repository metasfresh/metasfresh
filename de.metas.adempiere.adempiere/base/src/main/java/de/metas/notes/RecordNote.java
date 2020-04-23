/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.notes;

import de.metas.user.UserId;
import lombok.NonNull;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
public class RecordNote
{
	@NonNull UserId createdBy;

	@NonNull ZonedDateTime created;

	@NonNull String text;

	@NonNull ChatEntryId id;

	private RecordNote(@NonNull final UserId createdBy, @NonNull final ZonedDateTime created, @NonNull final String text, @NonNull final ChatEntryId id)
	{
		this.createdBy = createdBy;
		this.created = created;
		this.text = text;
		this.id = id;
	}

	@NonNull
	public static RecordNote of(@NonNull final UserId createdBy, @NonNull final ZonedDateTime created, @NonNull final String text, @NonNull final ChatEntryId id)
	{
		return new RecordNote(createdBy, created, text, id);
	}
}
