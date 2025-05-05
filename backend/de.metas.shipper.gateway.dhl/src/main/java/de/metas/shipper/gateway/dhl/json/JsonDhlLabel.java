/*
 * #%L
 * de.metas.shipper.gateway.dhl
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

package de.metas.shipper.gateway.dhl.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.shipper.gateway.dhl.DhlConstants;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;

@Builder(toBuilder = true)
@JsonDeserialize(builder = JsonDhlLabel.JsonDhlLabelBuilder.class)
public record JsonDhlLabel(@NonNull @JsonProperty("b64") String b64,
						   @Nullable @JsonProperty("fileFormat") String fileFormat,
						   @Nullable @JsonProperty("printFormat") String printFormat)
{
	@NonNull
	public JsonDhlLabel withNoLabelData()
	{
		if (Check.isBlank(b64))
		{
			return this;
		}
		return toBuilder()
				.b64(DhlConstants.REMOVED)
				.build();
	}
}
