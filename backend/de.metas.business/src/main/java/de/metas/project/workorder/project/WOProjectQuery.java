/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.project.workorder.project;

import de.metas.common.util.EmptyUtil;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.lang.ExternalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Stream;

@Value
public class WOProjectQuery
{
	OrgId orgId;
	String value;
	ExternalId externalId;
	String externalProjectReferencePattern;

	@Builder
	public WOProjectQuery(
			@NonNull final OrgId orgId,
			@Nullable final String value,
			@Nullable final ExternalId externalId,
			@Nullable final String externalProjectReferencePattern)
	{
		this.orgId = orgId;
		
		this.value = value;
		this.externalId = externalId;
		this.externalProjectReferencePattern = externalProjectReferencePattern;

		final boolean missingFilterOptions = Stream.of(value, externalId, externalProjectReferencePattern)
				.filter(Objects::nonNull)
				.count() == 0;

		Check.errorIf(missingFilterOptions, "At least one of value, externalProjectReference or externalProjectReferencePattern need to be specified");

		Check.errorIf(EmptyUtil.isNotBlank(value) && EmptyUtil.isNotBlank(externalProjectReferencePattern), "ExternalProjectReference and ExternalProjectReferencePattern cannot be both specified at the same time");
	}
}
