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

package de.metas.common.bpartner.v2.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.changelog.JsonChangeInfo;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class JsonResponseLocation
{
	public static final String METASFRESH_ID = "metasfreshId";
	public static final String EXTERNAL_ID = "externalId";
	public static final String NAME = "name";
	public static final String BPARTNERNAME = "bpartnerName";
	public static final String ADDRESS_1 = "address1";
	public static final String ADDRESS_2 = "address2";
	public static final String ADDRESS_3 = "address3";
	public static final String ADDRESS_4 = "address4";
	public static final String POSTAL = "postal";
	public static final String PO_BOX = "poBox";
	public static final String DISTRICT = "district";
	public static final String REGION = "region";
	public static final String CITY = "city";
	public static final String COUNTRY_CODE = "countryCode";
	public static final String PHONE = "phone";
	public static final String EMAIL = "email";
	public static final String GLN = "gln";
	public static final String ACTIVE = "active";

	public static final String BILL_TO = "billTo";
	public static final String BILL_TO_DEFAULT = "billToDefault";
	public static final String SHIP_TO = "shipTo";
	public static final String SHIP_TO_DEFAULT = "shipToDefault";
	public static final String VISITORS_ADDRESS = "visitorsAddress";

	public static final String SETUP_PLACE_NO = "setupPlaceNo";
	public static final String HANDOVER_LOCATION = "handoverLocation";
	public static final String REMIT_TO = "remitTo";
	public static final String REPLICATION_LOOKUP_DEFAULT = "replicationLookupDefault";

	public static final String EPHEMERAL = "ephemeral";

	@ApiModelProperty(dataType = "java.lang.Integer")
	JsonMetasfreshId metasfreshId;

	@ApiModelProperty()
	boolean active;

	@ApiModelProperty("This translates to `C_BPartner_Location.Name`")
	String name;

	@ApiModelProperty("This translates to `C_BPartner_Location.BPartnerName`")
	String bpartnerName;

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

	String postal;

	String city;

	@JsonInclude(Include.NON_EMPTY)
	String district;

	@JsonInclude(Include.NON_EMPTY)
	String region;

	String countryCode;

	@JsonInclude(Include.NON_EMPTY)
	String phone;

	@JsonInclude(Include.NON_EMPTY)
	String email;

	@ApiModelProperty(allowEmptyValue = true, value = "This translates to `C_BPartner_Location.GLN`.")
	String gln;

	@ApiModelProperty()
	boolean shipTo;

	@ApiModelProperty()
	boolean shipToDefault;

	@ApiModelProperty()
	boolean billTo;

	@ApiModelProperty()
	boolean billToDefault;

	boolean ephemeral;

	@JsonInclude(Include.NON_EMPTY)
	String setupPlaceNo;

	@ApiModelProperty
	boolean remitTo;

	@ApiModelProperty
	boolean visitorsAddress;

	@ApiModelProperty
	boolean handoverLocation;

	@ApiModelProperty
	boolean replicationLookupDefault;

	@JsonInclude(Include.NON_NULL)
	@ApiModelProperty(position = 20) // shall be last
	JsonChangeInfo changeInfo;

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonResponseLocation(
			@JsonProperty(METASFRESH_ID) @NonNull final JsonMetasfreshId metasfreshId,
			@JsonProperty(NAME) @Nullable final String name,
			@JsonProperty(BPARTNERNAME) @Nullable final String bpartnerName,
			@JsonProperty(ACTIVE) @NonNull final Boolean active,
			@JsonProperty(ADDRESS_1) @Nullable final String address1,
			@JsonProperty(ADDRESS_2) @Nullable final String address2,
			@JsonProperty(ADDRESS_3) @Nullable final String address3,
			@JsonProperty(ADDRESS_4) @Nullable final String address4,
			@JsonProperty(POSTAL) final String postal,
			@JsonProperty(PO_BOX) final String poBox,
			@JsonProperty(DISTRICT) final String district,
			@JsonProperty(REGION) final String region,
			@JsonProperty(CITY) final String city,
			@JsonProperty(GLN) final String gln,
			@JsonProperty(COUNTRY_CODE) @Nullable final String countryCode,
			@JsonProperty(PHONE) @Nullable final String phone,
			@JsonProperty(EMAIL) @Nullable final String email,
			@JsonProperty(SHIP_TO) final boolean shipTo,
			@JsonProperty(SHIP_TO_DEFAULT) final boolean shipToDefault,
			@JsonProperty(BILL_TO) final boolean billTo,
			@JsonProperty(BILL_TO_DEFAULT) final boolean billToDefault,
			@JsonProperty(EPHEMERAL)  final boolean ephemeral,
			@JsonProperty(VISITORS_ADDRESS) final boolean visitorsAddress,

			@JsonProperty(SETUP_PLACE_NO) @Nullable final String setupPlaceNo,
			@JsonProperty(HANDOVER_LOCATION)  final boolean handoverLocation,
			@JsonProperty(REMIT_TO)  final boolean remitTo,
			@JsonProperty(REPLICATION_LOOKUP_DEFAULT)  final boolean replicationLookupDefault,

			@JsonProperty("changeInfo") @Nullable final JsonChangeInfo changeInfo)
	{
		this.metasfreshId = metasfreshId;
		this.gln = gln;

		this.active = active;

		this.name = name;

		this.bpartnerName = bpartnerName;

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
		this.phone = phone;
		this.email = email;

		this.billToDefault = billToDefault;
		this.billTo = billTo;
		this.shipToDefault = shipToDefault;
		this.shipTo = shipTo;
		this.visitorsAddress = visitorsAddress;

		this.setupPlaceNo = setupPlaceNo;
		this.remitTo = remitTo;
		this.handoverLocation = handoverLocation;
		this.replicationLookupDefault = replicationLookupDefault;

		this.ephemeral = ephemeral;
		
		this.changeInfo = changeInfo;
	}
}
