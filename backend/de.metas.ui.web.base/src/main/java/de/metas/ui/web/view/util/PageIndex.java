package de.metas.ui.web.view.util;

import de.metas.printing.esb.base.util.Check;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
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

@Value
public class PageIndex
{
	public static PageIndex ofFirstRowAndPageLength(final int firstRow, final int pageLength)
	{
		return new PageIndex(firstRow, pageLength);
	}

	public static PageIndex getPageContainingRow(final int rowIndex, final int pageLength)
	{
		Check.assume(rowIndex >= 0, "rowIndex >= 0");
		Check.assume(pageLength > 0, "pageLength > 0");

		final int page = rowIndex / pageLength;
		final int firstRow = page * pageLength;

		return ofFirstRowAndPageLength(firstRow, pageLength);
	}

	int firstRow;
	int pageLength;

	private PageIndex(final int firstRow, final int pageLength)
	{
		Check.assume(firstRow >= 0, "firstRow >= 0");
		Check.assume(pageLength > 0, "pageLength > 0");

		this.firstRow = firstRow;
		this.pageLength = pageLength;
	}
}
