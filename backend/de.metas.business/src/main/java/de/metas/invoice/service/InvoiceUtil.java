package de.metas.invoice.service;

import lombok.NonNull;

import javax.annotation.Nullable;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

import de.metas.util.Check;

/*
 * #%L
 * de.metas.business
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

public class InvoiceUtil
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
		if (Check.isEmpty(externalIds, true))
		{
			return ImmutableList.of();
		}
		return Splitter
				.on(EXTERNAL_ID_DELIMITER)
				.splitToList(externalIds);
	}
}
