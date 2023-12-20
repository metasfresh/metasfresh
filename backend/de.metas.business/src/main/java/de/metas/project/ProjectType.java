/*
 * #%L
 * de.metas.business
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

package de.metas.project;

import de.metas.document.sequence.DocSequenceId;
import de.metas.organization.ClientAndOrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

import static de.metas.project.ProjectConstants.RESERVATION_PROJECT_TYPE;

@Value
@Builder
public class ProjectType
{
	@NonNull
	ClientAndOrgId clientAndOrgId;

	@NonNull
	ProjectTypeId id;

	@NonNull
	ProjectCategory projectCategory;

	@NonNull
	String name;

	@Nullable
	DocSequenceId docSequenceId;

	@NonNull
	RequestStatusCategoryId requestStatusCategoryId;

	public boolean isReservation()
	{
		return name.equals(RESERVATION_PROJECT_TYPE);
	}
}
