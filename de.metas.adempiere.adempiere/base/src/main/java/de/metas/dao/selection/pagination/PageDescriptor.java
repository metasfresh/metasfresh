package de.metas.dao.selection.pagination;

import static de.metas.util.Check.assumeNotEmpty;

import java.time.Instant;

import de.metas.util.lang.UIDStringUtil;
import lombok.Builder;
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
	PageIdentifier pageIdentifier;

	int offset;

	int pageSize;

	int totalSize;

	Instant selectionTime;

	public static PageDescriptor createNew(
			@NonNull final String querySelectionUUID,
			final int pageSize,
			final int totalSize,
			@NonNull final Instant selectionTime)
	{
		return new PageDescriptor(
				querySelectionUUID,
				UIDStringUtil.createNext(),
				0,
				pageSize,
				totalSize,
				selectionTime);
	}

	/** Create another page descriptor for the next page. */
	public PageDescriptor createNext()
	{
		return PageDescriptor.builder()
				.selectionUid(pageIdentifier.getSelectionUid())
				.pageUid(UIDStringUtil.createNext())
				.offset(offset + pageSize)
				.pageSize(pageSize)
				.totalSize(totalSize)
				.selectionTime(selectionTime)
				.build();
	}

	@Builder
	private PageDescriptor(
			@NonNull final String selectionUid,
			@NonNull final String pageUid,
			final int offset,
			final int pageSize,
			final int totalSize,
			@NonNull final Instant selectionTime)
	{
		assumeNotEmpty(selectionUid, "Param selectionUid may not be empty");
		assumeNotEmpty(pageUid, "Param selectionUid may not be empty");

		this.pageIdentifier = PageIdentifier.builder()
				.selectionUid(selectionUid)
				.pageUid(pageUid)
				.build();

		this.offset = offset;
		this.pageSize = pageSize;

		this.totalSize = totalSize;
		this.selectionTime = selectionTime;
	}

	public PageDescriptor withSize(final int adjustedSize)
	{
		if (pageSize == adjustedSize)
		{
			return this;
		}
		return PageDescriptor.builder()
				.selectionUid(pageIdentifier.getSelectionUid())
				.pageUid(pageIdentifier.getPageUid())
				.offset(offset)
				.pageSize(adjustedSize)
				.totalSize(totalSize)
				.selectionTime(selectionTime)
				.build();
	}
}
