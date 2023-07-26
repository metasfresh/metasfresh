package de.metas.dataentry.data;

import java.math.BigDecimal;

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
@ToString
public class DataEntryRecordFieldNumber extends DataEntryRecordField<BigDecimal>
{
	@Getter
	private final BigDecimal value;

	public static DataEntryRecordFieldNumber of(
			@NonNull final DataEntryFieldId dataEntryFieldId,
			@NonNull final DataEntryCreatedUpdatedInfo createdUpdatedInfo,
			@Nullable final BigDecimal value)
	{
		return new DataEntryRecordFieldNumber(dataEntryFieldId, createdUpdatedInfo, value);
	}

	private DataEntryRecordFieldNumber(
			@NonNull final DataEntryFieldId dataEntryFieldRepoId,
			@NonNull final DataEntryCreatedUpdatedInfo createdUpdatedInfo,
			@Nullable final BigDecimal value)
	{
		super(dataEntryFieldRepoId, createdUpdatedInfo);
		this.value = value;
	}
}
