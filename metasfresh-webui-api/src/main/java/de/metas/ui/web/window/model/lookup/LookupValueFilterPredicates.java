package de.metas.ui.web.window.model.lookup;

import java.util.function.Predicate;

import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.datatypes.LookupValue;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class LookupValueFilterPredicates
{
	public static final LookupValueFilterPredicate of(final String filter)
	{
		if (filter == null)
		{
			return MATCH_ALL;
		}

		final String filterNorm = filter.trim();
		if (filterNorm.isEmpty())
		{
			return MATCH_ALL;
		}

		return new ContainsLookupValueFilterPredicate(filterNorm);
	}

	public static interface LookupValueFilterPredicate extends Predicate<LookupValue>
	{
		@Override
		boolean test(LookupValue lookupValue);

		boolean isMatchAll();
	}

	public static final LookupValueFilterPredicate MATCH_ALL = new LookupValueFilterPredicate()
	{
		@Override
		public String toString()
		{
			return "MatchAll";
		};

		@Override
		public boolean test(final LookupValue lookupValue)
		{
			return true;
		}

		@Override
		public boolean isMatchAll()
		{
			return true;
		};
	};

	private static final class ContainsLookupValueFilterPredicate implements LookupValueFilterPredicate
	{
		private final String filterNormalized;

		private ContainsLookupValueFilterPredicate(final String filter)
		{
			super();
			filterNormalized = normalizeString(filter);
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper("ContainsIgnoreCase")
					.addValue(filterNormalized)
					.toString();
		}

		private static final String normalizeString(final String str)
		{
			return str.toLowerCase();
		}

		@Override
		public boolean test(final LookupValue lookupValue)
		{
			if (lookupValue == null)
			{
				return false;
			}

			final String displayName = lookupValue.getDisplayName();
			if (displayName == null)
			{
				return false;
			}

			final String displayNameNormalized = normalizeString(displayName);

			return displayNameNormalized.indexOf(filterNormalized) >= 0;
		}

		@Override
		public boolean isMatchAll()
		{
			return false;
		};
	}

}