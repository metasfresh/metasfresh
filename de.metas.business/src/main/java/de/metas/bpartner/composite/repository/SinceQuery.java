package de.metas.bpartner.composite.repository;

import static de.metas.util.Check.assumeGreaterThanZero;

import java.time.Instant;

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
public class SinceQuery
{
	public static SinceQuery anyEntity(@Nullable final Instant sinceInstant, final int pageSize)
	{
		return new SinceQuery(SinceEntity.ALL, sinceInstant, pageSize);
	}

	public static SinceQuery onlyContacts(@Nullable final Instant sinceInstant, final int pageSize)
	{
		return new SinceQuery(SinceEntity.CONTACT_ONLY, sinceInstant, pageSize);
	}

	SinceEntity sinceEntity;

	Instant sinceInstant;

	int pageSize;

	private SinceQuery(
			@NonNull final SinceEntity sinceEntity,
			@NonNull final Instant sinceInstant,
			final int pageSize)
	{
		this.sinceEntity = sinceEntity;
		this.sinceInstant = sinceInstant;
		this.pageSize = assumeGreaterThanZero(pageSize, "pageSize");
	}
}