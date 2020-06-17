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

package de.metas.lang;

import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;

import javax.annotation.Nullable;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

import de.metas.util.Check;

/**
 * Helps with columns that pack >=1 external ID string(s).
 * We currently have this case in  invoice lines, if multiple invoice candidates with different external IDs end up in the same invoice line.
 */
public class ExternalIdsUtil
{
	private static final String EXTERNAL_ID_DELIMITER = ";,;";

	public static String joinExternalIds(@NonNull final List<String> externalIds)
	{
		return externalIds
				.stream()
				.collect(Collectors.joining(EXTERNAL_ID_DELIMITER));
	}

	/**
	 * @return empty list if the given parameter is null or empty.
	 */
	public static List<String> splitExternalIds(@Nullable final String externalIds)
	{
		if (Check.isBlank(externalIds))
		{
			return ImmutableList.of();
		}
		return Splitter
				.on(EXTERNAL_ID_DELIMITER)
				.splitToList(externalIds);
	}

	public static int extractSingleRecordId(@NonNull final List<String> externalIds)
	{
		if (externalIds.size() != 1)
		{
			return -1;
		}
		final String externalId = CollectionUtils.singleElement(externalIds);
		final List<String> externalIdSegments = Splitter
				.on("_")
				.splitToList(externalId);
		if (externalIdSegments.isEmpty())
		{
			return -1;
		}

		final String recordIdStr = externalIdSegments.get(externalIdSegments.size() - 1);
		try
		{
			return Integer.parseInt(recordIdStr);
		}
		catch (NumberFormatException nfe)
		{
			return -1;
		}
	}
}
