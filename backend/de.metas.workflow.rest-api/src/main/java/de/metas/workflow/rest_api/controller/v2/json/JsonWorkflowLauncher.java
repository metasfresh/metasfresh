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

package de.metas.workflow.rest_api.controller.v2.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
@Builder
public class JsonWorkflowLauncher
{
	@NonNull String caption;

	@NonNull String wfProviderId;

	@Nullable String startedWFProcessId;

	@NonNull Map<String, Object> wfParameters;

	public static JsonWorkflowLauncher of(
			@NonNull final WorkflowLauncher workflowLauncher,
			@NonNull final JsonOpts jsonOpts)
	{
		final String adLanguage = jsonOpts.getAdLanguage();

		return builder()
				.caption(workflowLauncher.getCaption().translate(adLanguage))
				.wfProviderId(workflowLauncher.getProviderId().getAsString())
				.startedWFProcessId(WFProcessId.getAsStringOrNull(workflowLauncher.getStartedWFProcessId()))
				.wfParameters(workflowLauncher.getWfParameters().toJson(jsonOpts::convertValueToJson))
				.build();
	}
}
