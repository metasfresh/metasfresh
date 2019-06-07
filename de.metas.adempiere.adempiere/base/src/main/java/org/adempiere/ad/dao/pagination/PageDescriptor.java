package org.adempiere.ad.dao.pagination;

import static de.metas.util.Check.assumeNotEmpty;

import de.metas.util.lang.UIDStringUtil;
import lombok.NonNull;
import lombok.Value;

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

@Value
public class PageDescriptor
{
	String selectionUid;

	String pageUid;

	int offset;

	int size;

	private static final String SEPARATOR = "_";

	public static PageDescriptor createNew(@NonNull final String selectionUid, final int size)
	{
		return new PageDescriptor(selectionUid, UIDStringUtil.createNext(), 0, size);
	}

	private PageDescriptor(
			@NonNull final String selectionUid,
			@NonNull final String pageUid,
			final int offset,
			final int size)
	{

		this.selectionUid = assumeNotEmpty(selectionUid, "Param selectionUid may not be empty");
		this.pageUid = pageUid;
		this.size = size;
		this.offset = offset;
//
//		// make sure we will be able
//		errorIf(selectionUid.contains(SEPARATOR), "Param selectionUid={} may not contain the separator {}",selectionUid, SEPARATOR);
//		errorIf(selectionUid.contains(SEPARATOR), "Param pageUid={} may not contain the separator {}",pageUid, SEPARATOR);
	}

	public PageDescriptor createNext()
	{
		return new PageDescriptor(selectionUid, UIDStringUtil.createNext(), offset + size, size);
	}

	public String getCombinedUid()
	{
		return selectionUid + SEPARATOR + pageUid;
	}

}
