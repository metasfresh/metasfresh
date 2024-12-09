package de.metas.ordercandidate.api;

import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
<<<<<<< HEAD
=======
import org.adempiere.ad.column.AdColumnId;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class OLCandAggregationColumn
{
	private final String columnName;
<<<<<<< HEAD
	private final int adColumnId;
=======
	private final AdColumnId adColumnId;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	private final int orderBySeqNo;
	private final boolean splitOrderDiscriminator;
	private final boolean groupByColumn;

	private final Granularity granularity;

	public static enum Granularity
	{
		Day, Week, Month;
	}

	@Builder
	private OLCandAggregationColumn(
			@NonNull final String columnName,
<<<<<<< HEAD
			final int adColumnId,
=======
			@NonNull final AdColumnId adColumnId,
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			final int orderBySeqNo,
			final boolean splitOrderDiscriminator,
			final boolean groupByColumn,
			final Granularity granularity)
	{
		Check.assumeNotEmpty(columnName, "columnName is not empty");
<<<<<<< HEAD
		Check.assume(adColumnId > 0, "adColumnId > 0");
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		this.columnName = columnName;
		this.adColumnId = adColumnId;
		this.orderBySeqNo = orderBySeqNo > 0 ? orderBySeqNo : -1;
		this.splitOrderDiscriminator = splitOrderDiscriminator;
		this.groupByColumn = groupByColumn;
		this.granularity = granularity;
	}

	public boolean isOrderByColumn()
	{
		return orderBySeqNo > 0;
	}
}
