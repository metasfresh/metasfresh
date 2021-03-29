/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.rest_api.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.v1.SwaggerDocConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@ApiModel(description = "A BPartner with one contact (optional) and one location.")
@Value
public class JsonVendor
{
	@ApiModelProperty(required = true, value = SwaggerDocConstants.BPARTNER_IDENTIFIER_DOC)
	String bpartnerIdentifier;

	@ApiModelProperty(required = true, value = SwaggerDocConstants.LOCATION_IDENTIFIER_DOC)
	String locationIdentifier;

	@ApiModelProperty(value = SwaggerDocConstants.CONTACT_IDENTIFIER_DOC)
	String contactIdentifier;

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonVendor(@NonNull @JsonProperty("bpartnerIdentifier") final String bpartnerIdentifier,
			@Nullable @JsonProperty("locationIdentifier") final String locationIdentifier,
			@Nullable @JsonProperty("contactIdentifier") final String contactIdentifier)
	{

		this.bpartnerIdentifier = bpartnerIdentifier;
		this.locationIdentifier = locationIdentifier;
		this.contactIdentifier = contactIdentifier;
	}
}
