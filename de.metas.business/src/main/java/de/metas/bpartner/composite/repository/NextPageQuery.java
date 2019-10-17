package de.metas.bpartner.composite.repository;

import static de.metas.util.Check.assumeNotEmpty;
import static de.metas.util.Check.isEmpty;

import javax.annotation.Nullable;

import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


@Value
public class NextPageQuery
{
	public static NextPageQuery anyEntityOrNull(@Nullable final String nextPageId)
	{
		if (isEmpty(nextPageId, true))
		{
			return null;
		}
		return new NextPageQuery(SinceEntity.ALL, nextPageId);
	}

	public static NextPageQuery onlyContactsOrNull(@Nullable final String nextPageId)
	{
		if (isEmpty(nextPageId, true))
		{
			return null;
		}
		return new NextPageQuery(SinceEntity.CONTACT_ONLY, nextPageId);
	}

	SinceEntity sinceEntity;

	String nextPageId;

	private NextPageQuery(
			@NonNull final SinceEntity sinceEntity,
			@NonNull final String nextPageId)
	{
		this.sinceEntity = sinceEntity;
		this.nextPageId = assumeNotEmpty(nextPageId, "nextPageId");
	}
}