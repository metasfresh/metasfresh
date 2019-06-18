package de.metas.rest_api.bpartner;

import static de.metas.rest_api.bpartner.SwaggerDocConstants.BPARTER_SYNC_ADVISE_DOC;
import static de.metas.util.lang.CoalesceUtil.coalesce;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.MetasfreshId;
import de.metas.rest_api.SyncAdvise;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
public class JsonBPartnerLocation
{
	@JsonInclude(Include.NON_NULL)
	@ApiModelProperty(dataType = "java.lang.Integer")
	private MetasfreshId metasfreshId;

	@ApiModelProperty(allowEmptyValue = true, //
			dataType = "java.lang.String", //
			value = "This translates to `C_BPartner_Location.ExternalId`.\n"
					+ "Needs to be unique over all business partners (not only the one this location belongs to).")
	private JsonExternalId externalId;

//	@JsonInclude(Include.NON_NULL)
//	@ApiModelProperty(dataType = "java.lang.Integer")
//	private MetasfreshId metasfreshBPartnerId;

	private String address1;

	@JsonInclude(Include.NON_NULL)
	private String address2;

	private String poBox;

	private String postal;

	private String city;

	@JsonInclude(Include.NON_NULL)
	private String district;

	private String region;

	private String countryCode;

	@ApiModelProperty(allowEmptyValue = true, //
			value = "This translates to `C_BPartner_Location.GLN`.")
	private String gln;

	@ApiModelProperty(required = false, value = BPARTER_SYNC_ADVISE_DOC)
	@JsonInclude(Include.NON_NULL)
	SyncAdvise syncAdvise;

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonBPartnerLocation(
			@JsonProperty("metasfreshId") @Nullable final MetasfreshId metasfreshId,
			@JsonProperty("externalId") @Nullable final JsonExternalId externalId,
			//@JsonProperty("metasfreshBPartnerId") @Nullable final MetasfreshId metasfreshBPartnerId,
			@JsonProperty("address1") @Nullable final String address1,
			@JsonProperty("address2") @Nullable final String address2,
			@JsonProperty("postal") final String postal,
			@JsonProperty("poBox") final String poBox,
			@JsonProperty("district") final String district,
			@JsonProperty("region") final String region,
			@JsonProperty("city") final String city,
			@JsonProperty("countryCode") @Nullable final String countryCode,
			@JsonProperty("gln") @Nullable final String gln,
			@JsonProperty("syncAdvise") @Nullable final SyncAdvise syncAdvise)
	{
		this.metasfreshId = metasfreshId;
		this.gln = gln;
		this.externalId = externalId;
		//this.metasfreshBPartnerId = metasfreshBPartnerId;

		this.address1 = address1;
		this.address2 = address2;
		this.postal = postal;
		this.poBox = poBox;
		this.district = district;
		this.region = region;
		this.city = city;
		this.countryCode = countryCode; // mandatory only if we want to insert/update a new location

		this.syncAdvise = coalesce(syncAdvise, SyncAdvise.READ_ONLY);
	}
}
