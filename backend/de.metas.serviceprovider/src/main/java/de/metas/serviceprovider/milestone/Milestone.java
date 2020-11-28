/*
 * #%L
 * de.metas.serviceprovider.base
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

package de.metas.serviceprovider.milestone;

import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.Instant;

@Data
@Builder
public class Milestone
{
	@NonNull
	private OrgId orgId;

	@Nullable
	private MilestoneId milestoneId;

	@Nullable
	private String externalURL;

	@Nullable
	private String description;

	@NonNull
	private String name;

	@NonNull
	private String value;

	@Nullable
	private Instant dueDate;

	private boolean processed;
}
