/*
 * #%L
 * de.metas.workflow.rest-api
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.workflow.rest_api.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.api.Params;

import javax.annotation.Nullable;

@Value
@Builder
public class WorkflowLauncher
{
	@NonNull MobileApplicationId applicationId;

	@NonNull WorkflowLauncherCaption caption;

	@Nullable WFProcessId startedWFProcessId;

	@Builder.Default
	@NonNull Params wfParameters = Params.EMPTY;

	boolean partiallyHandledBefore;
}
