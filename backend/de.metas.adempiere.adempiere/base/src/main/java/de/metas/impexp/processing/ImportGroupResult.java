package de.metas.impexp.processing;

import de.metas.util.Check;
import lombok.Builder;
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
public class ImportGroupResult
{
	public static final ImportGroupResult ZERO = builder().build();
	public static final ImportGroupResult ONE_INSERTED = ImportGroupResult.builder().countInserted(1).build();
	public static final ImportGroupResult ONE_UPDATED = ImportGroupResult.builder().countUpdated(1).build();

	public static ImportGroupResult countInserted(final int countInserted)
	{
		return builder().countInserted(countInserted).build();
	}

	int countInserted;
	int countUpdated;

	@Builder
	private ImportGroupResult(
			final int countInserted,
			final int countUpdated)
	{
		Check.assumeGreaterOrEqualToZero(countInserted, "countInserted");
		Check.assumeGreaterOrEqualToZero(countUpdated, "countUpdated");

		this.countInserted = countInserted;
		this.countUpdated = countUpdated;
	}

}
