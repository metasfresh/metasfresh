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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.metas.common.handlingunits.JsonHUQRCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonWFDistributionStep
{
	@NonNull String id;

	@NonNull PickFromHU pickFromHU;

	@Builder
	@Jacksonized
	public JsonWFDistributionStep(
			@NonNull final String id,
			@NonNull final PickFromHU pickFromHU)
	{
		this.id = id;
		this.pickFromHU = pickFromHU;
	}

	@Value
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class PickFromHU
	{
		@NonNull JsonHUQRCode qrCode;

		@Builder
		@Jacksonized
		public PickFromHU(@NonNull final JsonHUQRCode qrCode)
		{
			this.qrCode = qrCode;
		}
	}
}
