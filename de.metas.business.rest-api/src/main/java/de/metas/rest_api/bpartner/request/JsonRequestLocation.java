package de.metas.rest_api.bpartner.request;

import static de.metas.rest_api.bpartner.SwaggerDocConstants.PARENT_SYNC_ADVISE_DOC;
import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import de.metas.rest_api.common.JsonExternalId;
import de.metas.rest_api.common.SyncAdvise;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
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

@ApiModel("Locations can be inserted/updated, or just looked up. For lookup, metasfresh tries first the `externalId` and then the `gln`.")
@Value
public class JsonRequestLocation
{
	@ApiModelProperty(allowEmptyValue = true, //
			dataType = "java.lang.String", //
			value = "This translates to `C_BPartner_Location.ExternalId`.\n"
					+ "Needs to be unique over all business partners (not only the one this location belongs to).")
	JsonExternalId externalId;

	@ApiModelProperty(required = false, value = "If not specified but required (e.g. because a new location is created), then `true` is assumed")
	@JsonInclude(Include.NON_NULL)
	Boolean active;

	@JsonInclude(Include.NON_EMPTY)
	String name;

	@JsonInclude(Include.NON_EMPTY)
	String address1;

	@JsonInclude(Include.NON_EMPTY)
	String address2;

	@JsonInclude(Include.NON_EMPTY)
	String address3;

	@JsonInclude(Include.NON_EMPTY)
	String address4;

	@JsonInclude(Include.NON_EMPTY)
	String poBox;

	@ApiModelProperty(allowEmptyValue = false, //
			value = "If specified, then metasfresh will attempt to lookup the `C_Postal` record.\n"
					+ "If there is one matching postal record, the system **will ignore** the following properties and instead use the postal record's values:\n"
					+ "* countryCode\n"
					+ "* city\n"
					+ "* region\n")
	@JsonInclude(Include.NON_EMPTY)
	String postal;

	@JsonInclude(Include.NON_EMPTY)
	String city;

	@ApiModelProperty(allowEmptyValue = true, //
			value = "If specified, then metasfresh will use this property (in addition to `postal`) as a filter criterion to look up `C_Postal` records.\n"
					+ "The property may be empty so a caller can explicitly tell metasfresh *not* to filter by district")
	@JsonInclude(Include.NON_EMPTY)
	String district;

	@JsonInclude(Include.NON_EMPTY)
	String region;

	@JsonInclude(Include.NON_EMPTY)
	String countryCode;

	@ApiModelProperty(allowEmptyValue = true/* we want to allow unsetting the GLN */,
			value = "This translates to `C_BPartner_Location.GLN`.")
	String gln;

	@ApiModelProperty(required = false)
	@JsonInclude(Include.NON_NULL)
	Boolean shipTo;

	@ApiModelProperty(required = false, value = "Only one location per request may have `shipToDefault == true`.\n"
			+ "If `true`, then " //
			+ "* `shipTo` is always be assumed to be `true` as well"
			+ "* another possibly exiting metasfresh location might be set to `shipToDefault = false`, even if it is not specified in this request.")
	@JsonInclude(Include.NON_NULL)
	Boolean shipToDefault;

	@ApiModelProperty(required = false)
	@JsonInclude(Include.NON_NULL)
	Boolean billTo;

	@ApiModelProperty(required = false, value = "Only one location per request may have `billToDefault == true`.\n"
			+ "If `true`, then " //
			+ "* `billTo` is always be assumed to be `true` as well"
			+ "* another possibly exiting metasfresh location might be set to `billToDefault = false`, even if it is not specified in this request.")
	@JsonInclude(Include.NON_NULL)
	Boolean billToDefault;

	@ApiModelProperty(position = 20, // shall be last
			required = false, value = "Sync advise about this location's individual properties.\n" + PARENT_SYNC_ADVISE_DOC)
	@JsonInclude(Include.NON_NULL)
	SyncAdvise syncAdvise;

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonRequestLocation(
			@JsonProperty("gln") @Nullable final String gln,
			@JsonProperty("externalId") @Nullable final JsonExternalId externalId,
			@JsonProperty("name") @Nullable final String name,
			@JsonProperty("active") @Nullable final Boolean active,
			@JsonProperty("address1") @Nullable final String address1,
			@JsonProperty("address2") @Nullable final String address2,
			@JsonProperty("address3") @Nullable final String address3,
			@JsonProperty("address4") @Nullable final String address4,
			@JsonProperty("postal") final String postal,
			@JsonProperty("poBox") final String poBox,
			@JsonProperty("district") final String district,
			@JsonProperty("region") final String region,
			@JsonProperty("city") final String city,
			@JsonProperty("countryCode") @Nullable final String countryCode,
			@JsonProperty("shipTo") @Nullable final Boolean shipTo,
			@JsonProperty("shipToDefault") @Nullable final Boolean shipToDefault,
			@JsonProperty("billTo") @Nullable final Boolean billTo,
			@JsonProperty("billToDefault") @Nullable final Boolean billToDefault,
			@JsonProperty("syncAdvise") @Nullable final SyncAdvise syncAdvise)
	{
		this.gln = gln;
		this.externalId = externalId;

		this.name = name;

		this.active = active;

		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		this.address4 = address4;

		this.postal = postal;
		this.poBox = poBox;
		this.district = district;
		this.region = region;
		this.city = city;
		this.countryCode = countryCode; // mandatory only if we want to insert/update a new location

		this.shipTo = shipTo;
		this.shipToDefault = shipToDefault;
		this.billTo = billTo;
		this.billToDefault = billToDefault;

		this.syncAdvise = syncAdvise;
	}
}
