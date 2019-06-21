package de.metas.dataentry.data;

import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import de.metas.dataentry.DataEntryFieldId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

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

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DataEntryRecordFieldDateTime extends DataEntryRecordField<ZonedDateTime>
{
	@Getter
	private final ZonedDateTime value;

	public static DataEntryRecordFieldDateTime of(
			@NonNull final DataEntryFieldId dataEntryFieldId,
			@NonNull final DataEntryCreatedUpdatedInfo createdUpdatedInfo,
			@Nullable final ZonedDateTime value)
	{
		return new DataEntryRecordFieldDateTime(
				dataEntryFieldId,
				createdUpdatedInfo,
				value);
	}

	private DataEntryRecordFieldDateTime(
			@NonNull final DataEntryFieldId dataEntryFieldId,
			@NonNull final DataEntryCreatedUpdatedInfo createdUpdatedInfo,
			@Nullable final ZonedDateTime value)
	{
		super(dataEntryFieldId, createdUpdatedInfo);
		this.value = value;
	}
}
