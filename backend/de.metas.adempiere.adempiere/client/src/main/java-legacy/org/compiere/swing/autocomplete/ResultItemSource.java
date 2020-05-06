package org.compiere.swing.autocomplete;

import java.util.List;

import com.google.common.collect.ImmutableList;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2015 metas GmbH
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

/**
 * A source for providing result items for {@link JTextComponentAutoCompleter}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 * @see JTextComponentAutoCompleter#setSource(ResultItemSource)
 */
public interface ResultItemSource
{
	public static final ResultItemSource NULL = new ResultItemSource()
	{
		@Override
		public List<ResultItem> query(String searchText, int limit)
		{
			return ImmutableList.of();
		}
	};

	/**
	 * Query the underlying source and return the results.
	 * 
	 * NOTE: the <code>searchText</code> and <code>limit</code> are just hints and depends on the implementation they can be used or not. It's the responsibility of the calling API to check and filter
	 * only those that match.
	 * 
	 * @param searchText
	 * @param limit
	 * @return results
	 */
	List<? extends ResultItem> query(final String searchText, final int limit);
}
