package de.metas.bpartner.composite.repository;

import de.metas.organization.OrgId;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

import static de.metas.util.Check.assumeGreaterThanZero;

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
	public static SinceQuery anyEntity(@Nullable final Instant sinceInstant, final int pageSize, @Nullable final OrgId orgId, @Nullable final String externalSystem)
	{
		return new SinceQuery(SinceEntity.ALL, sinceInstant, pageSize, orgId, externalSystem);
	}

	public static SinceQuery onlyContacts(@Nullable final Instant sinceInstant, final int pageSize)
	{
		return new SinceQuery(SinceEntity.CONTACT_ONLY, sinceInstant, pageSize,null, null);
	}


	SinceEntity sinceEntity;

	Instant sinceInstant;

	int pageSize;

	@Nullable
	OrgId orgId;

	@Nullable
	String externalSystem;

	private SinceQuery(
			@NonNull final SinceEntity sinceEntity,
			@NonNull final Instant sinceInstant,
			final int pageSize,
			@Nullable final OrgId orgId,
			@Nullable final String externalSystem)
	{
		this.sinceEntity = sinceEntity;
		this.sinceInstant = sinceInstant;
		this.pageSize = assumeGreaterThanZero(pageSize, "pageSize");
		this.orgId = orgId;
		this.externalSystem = externalSystem;
	}
}
