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

package de.metas.project.workorder.data;

import de.metas.common.util.EmptyUtil;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.lang.ExternalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class ProjectQuery
{
	OrgId orgId;
	String value;
	ExternalId externalProjectReference;

	@Builder
	public ProjectQuery(
			@NonNull final OrgId orgId,
			@Nullable final String value,
			@Nullable final ExternalId externalProjectReference)
	{
		this.orgId = orgId;
		
		this.value = value;
		this.externalProjectReference = externalProjectReference;
		Check.errorIf(EmptyUtil.isBlank(value) && externalProjectReference == null, "At least one of value or externalProjectReference need to be specified");
	}
}
