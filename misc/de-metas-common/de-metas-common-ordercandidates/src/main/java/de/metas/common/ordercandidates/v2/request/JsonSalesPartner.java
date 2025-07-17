/*
 * #%L
 * de-metas-common-ordercandidates
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

package de.metas.common.ordercandidates.v2.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.BPARTNER_IDENTIFIER_DOC;
import static de.metas.common.rest_api.v2.SwaggerDocConstants.BPARTNER_VALUE_DOC;

@Schema(description="Used to identify a bpartner's sales partner. Only one of the possible matching properties shall be set.")
@Value
public class JsonSalesPartner
{
	@Schema(description = BPARTNER_VALUE_DOC)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String salesPartnerCode;

	@Schema(required = true, description = BPARTNER_IDENTIFIER_DOC)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String salesPartnerIdentifier;

	@Builder
	public JsonSalesPartner(
			@JsonProperty("salesPartnerCode") @Nullable final String salesPartnerCode,
			@JsonProperty("salesPartnerIdentifier") @Nullable final String salesPartnerIdentifier)
	{
		if(salesPartnerCode != null && salesPartnerIdentifier != null)
		{
			throw new IllegalArgumentException("Only one of the possible matching properties shall be set.");
		}
		else if (salesPartnerCode == null && salesPartnerIdentifier == null)
		{
			throw new IllegalArgumentException("At least one argument must be different from null!");
		}

		this.salesPartnerCode = salesPartnerCode;
		this.salesPartnerIdentifier = salesPartnerIdentifier;
	}
}