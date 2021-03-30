/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.document.filter.provider;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DocumentFilterDescriptorsConstants
{
	public final int SORT_NO_DEFAULT_DATE = Integer.MIN_VALUE;
	public final int SORT_NO_DEFAULT_FILTERS_GROUP = 10000;
	public final int SORT_NO_INLINE_FILTERS = 11000;
	public final int SORT_NO_USER_QUERY_START = 20000;
	public final int SORT_NO_FULL_TEXT_SEARCH = 30000;
	public final int SORT_NO_GEO_LOCATION = 40000;
	public final int SORT_NO_FACT_ACCT = 50000;

	public final int SORT_NO_FACETS_START = Integer.MAX_VALUE / 10000 * 10000;
}
