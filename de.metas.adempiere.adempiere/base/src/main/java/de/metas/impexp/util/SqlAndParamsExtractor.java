package de.metas.impexp.util;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import lombok.Builder;
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

@ToString
public final class SqlAndParamsExtractor<Context>
{
	@Getter
	private final String sql;

	@FunctionalInterface
	public interface ParametersExtractor<Context>
	{
		List<Object> extractParameters(Context context);
	}

	private final ImmutableList<ParametersExtractor<Context>> parametersExtractors;

	@Builder
	private SqlAndParamsExtractor(
			@NonNull final String sql,
			@NonNull final List<ParametersExtractor<Context>> parametersExtractors)
	{
		this.sql = sql;
		this.parametersExtractors = ImmutableList.copyOf(parametersExtractors);
	}

	public List<Object> extractParameters(@NonNull final Context context)
	{
		final List<Object> allParameters = new ArrayList<>();
		for (final ParametersExtractor<Context> extractor : parametersExtractors)
		{
			allParameters.addAll(extractor.extractParameters(context));
		}
		return allParameters;
	}
}
