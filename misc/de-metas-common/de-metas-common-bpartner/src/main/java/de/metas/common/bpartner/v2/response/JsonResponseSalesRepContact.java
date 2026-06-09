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
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class JsonResponseSalesRepContact
{
	public static final String SALES_REP_VALUE = "salesRepValue";
	public static final String SALES_REP_ID = "salesRepId";
	public static final String SALES_REP_PHONE = "salesRepPhone";
	public static final String SALES_REP_EMAIL = "salesRepEmail";
	public static final String SALES_REP_LASTNAME = "salesRepLastName";
	public static final String SALES_REP_FIRSTNAME = "salesRepFirstName";
	public static final String SALES_REP_NAME = "salesRepName";

	@ApiModelProperty(required = true, value = "This translates to `AD_User.AD_User_ID`.")
	@JsonProperty(SALES_REP_ID)
	JsonMetasfreshId salesRepId;

	@ApiModelProperty(required = true, value = "This translates to `AD_User.Value`.")
	@JsonProperty(SALES_REP_VALUE)
	String salesRepValue;

	@ApiModelProperty(required = true, value = "This translates to `AD_User.Phone`.")
	@JsonProperty(SALES_REP_PHONE)
	String phone;

	@ApiModelProperty(required = true, value = "This translates to `AD_User.Email`.")
	@JsonProperty(SALES_REP_EMAIL)
	String  email;

	@ApiModelProperty(required = true, value = "This translates to `AD_User.LastName`.")
	@JsonProperty(SALES_REP_LASTNAME)
	String lastName;

	@ApiModelProperty(required = true, value = "This translates to `AD_User.FirstName`.")
	@JsonProperty(SALES_REP_FIRSTNAME)
	String firstName;

	@ApiModelProperty(required = true, value = "This translates to `AD_User.Name`.")
	@JsonProperty(SALES_REP_NAME)
	String name;

	@JsonCreator
	@Builder
	private JsonResponseSalesRepContact(
			@JsonProperty(SALES_REP_ID) @NonNull final JsonMetasfreshId salesRepId,
			@JsonProperty(SALES_REP_VALUE) @Nullable final String salesRepValue,
			@JsonProperty(SALES_REP_LASTNAME) @Nullable final String lastName,
			@JsonProperty(SALES_REP_FIRSTNAME) @Nullable final String firstName,
			@JsonProperty(SALES_REP_NAME) @Nullable final String name,
			@JsonProperty(SALES_REP_EMAIL) @Nullable final String email,
			@JsonProperty(SALES_REP_PHONE) @Nullable final String phone)
	{
		this.salesRepId = salesRepId;
		this.salesRepValue = salesRepValue;
		this.lastName = lastName;
		this.firstName = firstName;
		this.name = name;
		this.email = email;
		this.phone = phone;
	}
}
