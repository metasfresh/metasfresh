/*
 * #%L
 * de-metas-common-bpartner
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

package de.metas.common.bpartner.v2.request.alberta;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
@ApiModel(description = "Contains the alberta bpartner details to be added or updated.")
public class JsonCompositeAlbertaBPartner
{
	@ApiModelProperty(position = 10)
	JsonAlbertaBPartner jsonAlbertaBPartner;

	@ApiModelProperty(position = 20)
	JsonBPartnerRole role;

	@ApiModelProperty(position = 30)
	JsonAlbertaPatient jsonAlbertaPatient;

	@ApiModelProperty(position = 40)
	List<JsonAlbertaCareGiver> jsonAlbertaCareGivers;

	private JsonCompositeAlbertaBPartner(
			final @Nullable @JsonProperty("jsonAlbertaBPartner") JsonAlbertaBPartner jsonAlbertaBPartner,
			final @Nullable @JsonProperty("role") JsonBPartnerRole role,
			final @Nullable @JsonProperty("jsonAlbertaPatient") JsonAlbertaPatient jsonAlbertaPatient,
			final @Nullable @JsonProperty("jsonAlbertaCareGivers") List<JsonAlbertaCareGiver> jsonAlbertaCareGivers)
	{
		this.jsonAlbertaBPartner = jsonAlbertaBPartner;
		this.role = role;
		this.jsonAlbertaPatient = jsonAlbertaPatient;
		this.jsonAlbertaCareGivers = jsonAlbertaCareGivers;
	}
}
