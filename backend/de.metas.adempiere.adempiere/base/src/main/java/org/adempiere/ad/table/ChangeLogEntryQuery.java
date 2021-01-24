/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package org.adempiere.ad.table;

import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Value;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.table.api.AdTableId;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder(toBuilder = true)
public class ChangeLogEntryQuery
{
	@Nullable
	Integer recordId;

	@Nullable
	AdTableId adTableId;

	@Nullable
	AdColumnId adColumnId;

	@Nullable
	ImmutableSet<AdColumnId> adColumnIds;

	@Nullable
	Instant createdFrom;

	@Nullable
	Instant createdTo;

	public boolean isEmpty()
	{
		return recordId == null
				&& adTableId == null
				&& adColumnId == null
				&& Check.isEmpty(adColumnIds)
				&& createdFrom == null
				&& createdTo == null;
	}
}
