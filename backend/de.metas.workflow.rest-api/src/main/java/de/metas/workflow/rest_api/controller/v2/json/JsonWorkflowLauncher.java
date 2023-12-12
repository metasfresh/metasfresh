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
import org.adempiere.util.api.Params;

import javax.annotation.Nullable;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
@Builder
public class JsonWorkflowLauncher
{
	@NonNull String applicationId;
	@NonNull String caption;
	@Nullable String startedWFProcessId;
	@NonNull Map<String, Object> wfParameters;
	boolean showWarningSign;

	public static JsonWorkflowLauncher of(
			@NonNull final WorkflowLauncher workflowLauncher,
			@NonNull final JsonOpts jsonOpts)
	{
		final String adLanguage = jsonOpts.getAdLanguage();

		final String caption = workflowLauncher.getCaption().translate(adLanguage);
		final WFProcessId startedWFProcessId = workflowLauncher.getStartedWFProcessId();
		final String applicationId = workflowLauncher.getApplicationId().getAsString();

		Params wfParameters = workflowLauncher.getWfParameters();
		if (startedWFProcessId == null)
		{
			wfParameters = wfParameters.withParameter(JsonWFProcessStartRequest.PARAM_ApplicationId, applicationId);
		}

		return builder()
				.applicationId(applicationId)
				.caption(caption)
				.startedWFProcessId(WFProcessId.getAsStringOrNull(startedWFProcessId))
				.wfParameters(wfParameters.toJson(jsonOpts::convertValueToJson))
				.showWarningSign(workflowLauncher.isPartiallyHandledBefore())
				.build();
	}
}
