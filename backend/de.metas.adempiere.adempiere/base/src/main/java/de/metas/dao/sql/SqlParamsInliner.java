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

package de.metas.dao.sql;

import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class SqlParamsInliner
{
	private final boolean failOnError;

	@Builder
	private SqlParamsInliner(
			final boolean failOnError)
	{
		this.failOnError = failOnError;
	}

	public String inline(@NonNull final String sql, @Nullable final Object... params)
	{
		final List<Object> paramsList = params != null && params.length > 0 ? Arrays.asList(params) : null;
		return inline(sql, paramsList);
	}

	public String inline(@NonNull final String sql, @Nullable final List<Object> params)
	{
		try
		{
			return inline0(sql, params);
		}
		catch (final Exception ex)
		{
			//noinspection ConstantConditions
			throw AdempiereException.wrapIfNeeded(ex)
					.setParameter("sql", sql)
					.setParameter("sqlParams", params)
					.appendParametersToMessage();
		}
	}

	private String inline0(@NonNull final String sql, @Nullable final List<Object> params)
	{
		final int paramsCount = params != null ? params.size() : 0;

		final int sqlLength = sql.length();
		final StringBuilder sqlFinal = new StringBuilder(sqlLength);

		boolean insideQuotes = false;
		int nextParamIndex = 0;
		for (int i = 0; i < sqlLength; i++)
		{
			final char ch = sql.charAt(i);

			if (ch == '?')
			{
				if (insideQuotes)
				{
					sqlFinal.append(ch);
				}
				else
				{
					if (params != null && nextParamIndex < paramsCount)
					{
						sqlFinal.append(DB.TO_SQL(params.get(nextParamIndex)));
					}
					else
					{
						// error: parameter index is invalid
						appendMissingParam(sqlFinal, nextParamIndex);
					}

					nextParamIndex++;
				}
			}
			else if (ch == '\'')
			{
				sqlFinal.append(ch);
				insideQuotes = !insideQuotes;
			}
			else
			{
				sqlFinal.append(ch);
			}
		}

		if (params != null && nextParamIndex < paramsCount)
		{
			appendExceedingParams(sqlFinal, nextParamIndex, paramsCount, params);
		}

		return sqlFinal.toString();
	}

	private void appendMissingParam(
			final StringBuilder sql,
			final int missingParamIndexZeroBased)
	{
		final int missingParamIndexOneBased = missingParamIndexZeroBased + 1;

		if (failOnError)
		{
			throw new AdempiereException("Missing SQL parameter with index=" + missingParamIndexOneBased);
		}

		sql.append("?missing").append(missingParamIndexOneBased).append("?");
	}

	private void appendExceedingParams(
			final StringBuilder sql,
			final int firstExceedingParamIndex,
			final int paramsCount,
			final List<Object> allParams)
	{
		if (failOnError)
		{
			throw new AdempiereException("Got more SQL params than needed: " + allParams.subList(firstExceedingParamIndex, allParams.size()));
		}

		sql.append(" -- Exceeding params: ");
		boolean firstExceedingParam = true;
		for (int i = firstExceedingParamIndex; i < paramsCount; i++)
		{
			if (firstExceedingParam)
			{
				firstExceedingParam = false;
			}
			else
			{
				sql.append(", ");
			}

			sql.append(DB.TO_SQL(allParams.get(i)));
		}
	}

}
