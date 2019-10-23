package de.metas.dataentry.data;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.dataentry.DataEntrySubTabId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
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
public final class DataEntryRecordQuery
{
	ImmutableSet<DataEntrySubTabId> dataEntrySubTabIds;
	int recordId;

	@Builder
	private DataEntryRecordQuery(
			@NonNull @Singular final Set<DataEntrySubTabId> dataEntrySubTabIds,
			final int recordId)
	{
		Check.assumeNotEmpty(dataEntrySubTabIds, "dataEntrySubTabIds is not empty");
		Check.assumeGreaterOrEqualToZero(recordId, "recordId");

		this.dataEntrySubTabIds = ImmutableSet.copyOf(dataEntrySubTabIds);
		this.recordId = recordId;
	}

}
