/*
 * #%L
 * de.metas.shipper.gateway.nshift
 * %%
 * Copyright (C) 2025 metas GmbH
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
package de.metas.shipper.gateway.nshift.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Value
@Builder
public class JsonAddress {

    @JsonProperty("Kind")
    JsonAddressKind kind;

    @JsonProperty("Name1")
    String name1;

    @JsonProperty("Name2")
    String name2;

    @JsonProperty("Street1")
    String street1;

    @JsonProperty("Street2")
    String street2;

    @JsonProperty("State")
    String state;

    @JsonProperty("PostCode")
    String postCode;

    @JsonProperty("City")
    String city;

    @JsonProperty("POBox")
    String poBox;

    @JsonProperty("POPostCode")
    String poPostCode;

    @JsonProperty("POCity")
    String poCity;

    @JsonProperty("Phone")
    String phone;

    @JsonProperty("Mobile")
    String mobile;

    @JsonProperty("Email")
    String email;

    @JsonProperty("Attention")
    String attention;

    @JsonProperty("CustNo")
    String custNo;

    @JsonProperty("Fax")
    String fax;

    @JsonProperty("CountryCode")
    String countryCode;

    @JsonProperty("Province")
    String province;

    @JsonProperty("ERPRef")
    String erpRef;

    @JsonProperty("OpeningHours")
    String openingHours;

    @JsonProperty("VATNo")
    String vatNo;

    @JsonProperty("VOECNumber")
    String voecNumber;
}
