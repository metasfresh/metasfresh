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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.BPARTNER_IDENTIFIER_DOC;
import static de.metas.common.rest_api.v2.SwaggerDocConstants.CONTACT_IDENTIFIER_DOC;
import static de.metas.common.rest_api.v2.SwaggerDocConstants.LOCATION_IDENTIFIER_DOC;

@ApiModel(description = "A BPartner with one contact (optional) and one location.\n"
		+ "Can be used multiple times in each order line candidate, for billTo-partner, shipTo-partner etc.\n" //
		+ "Note that given the respective use-case, either `bpartner.code`, `bpartner.externalId` or `location.gln` might be `null`, but not all at once.")
@Value
public class JsonRequestBPartnerLocationAndContact
{
	@ApiModelProperty(required = true, value = BPARTNER_IDENTIFIER_DOC)
	String bPartnerIdentifier;

	@ApiModelProperty(required = true, value = LOCATION_IDENTIFIER_DOC)
	String bPartnerLocationIdentifier;

	@ApiModelProperty(value = CONTACT_IDENTIFIER_DOC)
	String contactIdentifier;

	@JsonCreator
	@Builder
	public JsonRequestBPartnerLocationAndContact(
			@JsonProperty("bpartnerIdentifier") final String bPartnerIdentifier,
			@JsonProperty("bpartnerLocationIdentifier") final String bPartnerLocationIdentifier,
			@JsonProperty("contactIdentifier") final String contactIdentifier)
	{
		this.bPartnerIdentifier = bPartnerIdentifier;
		this.bPartnerLocationIdentifier = bPartnerLocationIdentifier;
		this.contactIdentifier = contactIdentifier;
	}

	@NonNull
	public String getBPartnerIdentifier()
	{
		return bPartnerIdentifier;
	}

	@NonNull
	public String getBPartnerLocationIdentifier()
	{
		return bPartnerLocationIdentifier;
	}
}
