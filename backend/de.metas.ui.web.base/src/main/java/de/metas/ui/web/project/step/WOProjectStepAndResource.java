/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.ui.web.project.step;

import de.metas.project.ProjectId;
import de.metas.project.workorder.resource.WOProjectResource;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.step.WOProjectStep;
import de.metas.project.workorder.step.WOProjectStepId;
import lombok.NonNull;
import lombok.Value;

import java.time.Duration;

@Value(staticConstructor = "of")
public class WOProjectStepAndResource
{
	@NonNull WOProjectStep step;
	@NonNull WOProjectResource resource;

	@NonNull
	public WOProjectStepId getStepId() {return step.getWoProjectStepId();}

	@NonNull
	public WOProjectResourceId getResourceId() {return resource.getWoProjectResourceId();}

	@NonNull
	public ProjectId getProjectId() {return resource.getProjectId();}

	@NonNull
	public String getStepName() {return step.getName();}

	@NonNull
	public Duration getTotalHours() {return step.getWOPlannedResourceDuration();}

	@NonNull
	public Duration getResolvedHours() {return resource.getResolvedHours();}

	public boolean isNotFullyResolved() {return resource.isNotFullyResolved(step);}
}
