/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.organization;

import de.metas.common.util.time.SystemTime;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.ZoneId;

/**
 * Lightweight domain object combining {@code AD_Org} identity fields with the org's {@link OrgInfo}. Loaded
 * in bulk by {@link OrgRepository}. Production seed data and the WebUI org-creation flow both ensure every
 * {@code AD_Org} (including the {@code AD_Org_ID=0} sentinel) has a matching {@code AD_OrgInfo} row.
 */
@Builder
@Value
public class Org
{
	@NonNull OrgId orgId;
	@NonNull String name;
	@NonNull String value;
	@NonNull OrgInfo orgInfo;

	/**
	 * Returns the org's configured time zone, falling back to {@link SystemTime#zoneId()} when {@code AD_OrgInfo.TimeZone} is unset.
	 */
	@NonNull
	public ZoneId getTimeZone()
	{
		final ZoneId tz = orgInfo.getTimeZone();
		return tz != null ? tz : SystemTime.zoneId();
	}
}
