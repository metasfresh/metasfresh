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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Map;

public class FileMakerDataHelper
{
	public static String getValue(@NonNull final GetValueRequest request)
	{
		final COL col = extractCOL(request);
		if (col == null)
		{
			return null;
		}
		if (col.getData() == null)
		{
			return null;
		}

		return col.getData().getValue();
	}

	@Nullable
	private static COL extractCOL(final @NonNull GetValueRequest request)
	{
		final ROW row = request.getRow();
		final Map<String, Integer> fieldName2Index = request.getFieldName2Index();

		final Integer index = fieldName2Index.get(request.getFieldName());

		if (index == null || row.getCols() == null || row.getCols().isEmpty())
		{
			return null;
		}

		return row.getCols().get(index);
	}

	public static ROW setValue(@NonNull final GetValueRequest request, @Nullable final String newValue)
	{
		final COL col = extractCOL(request);
		if (col == null)
		{
			throw new RuntimeException("Unable to find COL for request=" + request);
		}

		final Map<String, Integer> fieldName2Index = request.getFieldName2Index();
		final Integer index = fieldName2Index.get(request.getFieldName());

		final ROW rowToModify = request.getRow();

		final ArrayList<COL> cols = new ArrayList<>(rowToModify.getCols());
		cols.set(index, COL.of(newValue));

		return rowToModify.toBuilder().clearCols().cols(cols).build();
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
