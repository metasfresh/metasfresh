/*
 * #%L
 * de-metas-common-filemaker
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

package de.metas.common.filemaker;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Map;

public class FileMakerDataHelper
{
	public static String getValue(@NonNull final GetValueRequest request)
	{
		final ROW row = request.getRow();
		final Map<String, Integer> fieldName2Index = request.getFieldName2Index();

		final Integer index = fieldName2Index.get(request.getFieldName());

		if (index == null || row.getCols() == null || row.getCols().isEmpty())
		{
			return null;
		}

		final COL col = row.getCols().get(index);

		if (col.getData() == null)
		{
			return null;
		}

		return col.getData().getValue();
	}

	public static BigDecimal getBigDecimalValue(@NonNull final GetValueRequest request)
	{
		final String valueStr = getValue(request);

		if (valueStr == null || valueStr.trim().isEmpty())
		{
			return null;
		}

		BigDecimal valueBigDecimal;

		try
		{
			valueBigDecimal = new BigDecimal(valueStr);
		}
		catch (final Exception e)
		{
			valueBigDecimal = null;
		}

		return valueBigDecimal;
	}

	@Builder
	@Getter
	public static class GetValueRequest
	{
		@NonNull
		private final ROW row;

		@NonNull
		private final Map<String, Integer> fieldName2Index;

		@NonNull
		private final String fieldName;
	}
}
