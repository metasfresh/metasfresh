package de.metas.rest_api.v2.ordercandidates.impl;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.TranslatableStrings;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2026 metas GmbH
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

@UtilityClass
public class OLCandBulkLineErrorCollector
{
	/**
	 * Maps every item in order. {@link OLCandProductNotFoundException}s are collected per line and thrown together
	 * as one {@link OLCandBulkCreateException} after the last line; any other exception stops the loop.
	 */
	public static <T, R> ImmutableList<R> mapAll(
			@NonNull final List<T> items,
			@NonNull final Function<T, R> mapper,
			@NonNull final Function<T, OLCandLineRef> lineRefProvider)
	{
		final ImmutableList.Builder<R> results = ImmutableList.builder();
		final List<Throwable> errors = new ArrayList<>();

		for (final T item : items)
		{
			try
			{
				results.add(mapper.apply(item));
			}
			catch (final OLCandProductNotFoundException e)
			{
				errors.add(toLineError(e, lineRefProvider.apply(item)));
			}
			catch (final RuntimeException e)
			{
				if (errors.isEmpty())
				{
					throw e;
				}
				errors.add(e);
				final OLCandBulkCreateException aggregate = new OLCandBulkCreateException(ImmutableList.copyOf(errors));
				aggregate.addSuppressed(e);
				throw aggregate;
			}
		}

		if (!errors.isEmpty())
		{
			throw new OLCandBulkCreateException(ImmutableList.copyOf(errors));
		}
		return results.build();
	}

	private static AdempiereException toLineError(final OLCandProductNotFoundException original, final OLCandLineRef ref)
	{
		final AdempiereException error = new AdempiereException(TranslatableStrings.constant(
				"Line " + orAbsent(ref.getLine())
						+ " (externalLineId=" + orAbsent(ref.getExternalLineId())
						+ ", externalHeaderId=" + orAbsent(ref.getExternalHeaderId())
						+ "): " + original.getMessage()));
		if (ref.getLine() != null)
		{
			error.setParameter("line", ref.getLine());
		}
		if (ref.getExternalLineId() != null)
		{
			error.setParameter("externalLineId", ref.getExternalLineId());
		}
		if (ref.getExternalHeaderId() != null)
		{
			error.setParameter("externalHeaderId", ref.getExternalHeaderId());
		}
		return error;
	}

	private static String orAbsent(final Object value)
	{
		return value != null ? value.toString() : "-";
	}
}
