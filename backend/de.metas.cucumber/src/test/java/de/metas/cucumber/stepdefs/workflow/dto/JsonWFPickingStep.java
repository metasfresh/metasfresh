/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.workflow.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.metas.common.handlingunits.JsonHUQRCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonWFPickingStep
{
	@NonNull String pickingStepId;

	@NonNull MainPickFrom mainPickFrom;

	@Builder
	@Jacksonized
	public JsonWFPickingStep(
			@NonNull final String pickingStepId,
			@NonNull final MainPickFrom mainPickFrom)
	{
		this.pickingStepId = pickingStepId;
		this.mainPickFrom = mainPickFrom;
	}

	@Value
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class MainPickFrom
	{
		@NonNull JsonHUQRCode huQRCode;

		@Builder
		@Jacksonized
		public MainPickFrom(@NonNull final JsonHUQRCode huQRCode)
		{
			this.huQRCode = huQRCode;
		}
	}
}
