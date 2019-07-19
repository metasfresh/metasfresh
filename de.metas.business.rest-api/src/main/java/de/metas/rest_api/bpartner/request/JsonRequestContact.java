package de.metas.rest_api.bpartner.request;

import static de.metas.rest_api.bpartner.SwaggerDocConstants.*;
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
public class JsonRequestContact
{
	@ApiModelProperty(dataType = "java.lang.String")
	JsonExternalId externalId;

	@JsonInclude(Include.NON_NULL)
	@ApiModelProperty(dataType = "java.lang.Integer")
	private MetasfreshId metasfreshBPartnerId;

	@JsonInclude(Include.NON_NULL)
	String code;

	@ApiModelProperty(required = false, value = "If not specified but required (e.g. because a new contact is created), then `true` is assumed")
	@JsonInclude(Include.NON_NULL)
	Boolean active;

	@JsonInclude(Include.NON_NULL)
	String name;

	@JsonInclude(Include.NON_NULL)
	String firstName;

	@JsonInclude(Include.NON_NULL)
	String lastName;

	@JsonInclude(Include.NON_NULL)
	String email;

	@JsonInclude(Include.NON_NULL)
	String phone;

	@JsonInclude(Include.NON_NULL)
	String fax;

	@JsonInclude(Include.NON_NULL)
	String mobilePhone;

	@ApiModelProperty(required = false, value = "If not specified but required (e.g. because a new contact is created), then `false` is assumed")
	@JsonInclude(Include.NON_NULL)
	Boolean defaultContact;

	@ApiModelProperty(required = false, value = "Only one location per request may have `shipToDefault == true`.\n"
			+ "If not specified but required (e.g. because a new contact is created), then `false` is assumed.\n"
			+ "If `true`, then " //
			+ "* another possibly exiting metasfresh contact might be set to `shipToDefault = false`, even if it is not specified in this request.")
	@JsonInclude(Include.NON_NULL)
	Boolean shipToDefault;

	@ApiModelProperty(required = false, value = "Only one location per request may have `billToDefault == true`.\n"
			+ "If not specified but required (e.g. because a new contact is created), then `false` is assumed.\n"
			+ "If `true`, then " //
			+ "* another possibly exiting metasfresh contact might be set to `billToDefault = false`, even if it is not specified in this request.")
	@JsonInclude(Include.NON_NULL)
	Boolean billToDefault;

	@ApiModelProperty(required = false, value = "If not specified but required (e.g. because a new contact is created), then `false` is assumed")
	@JsonInclude(Include.NON_NULL)
	Boolean newsletter;

	@JsonInclude(Include.NON_NULL)
	String description;

	@ApiModelProperty(required = false, value = "If not specified but required (e.g. because a new contact is created), then `false` is assumed")
	@JsonInclude(Include.NON_NULL)
	Boolean sales;

	@ApiModelProperty(required = false, value = "Only one location per request may have `salesDefault == true`.\n"
			+ "If not specified but required (e.g. because a new contact is created), then `false` is assumed.\n"
			+ "If `true`, then " //
			+ "* `sales` is always be assumed to be `true` as well"
			+ "* another possibly exiting metasfresh contact might be set to `salesDefault = false`, even if it is not specified in this request.")
	@JsonInclude(Include.NON_NULL)
	Boolean salesDefault;

	@ApiModelProperty(required = false, value = "If not specified but required (e.g. because a new contact is created), then `false` is assumed")
	@JsonInclude(Include.NON_NULL)
	Boolean purchase;

	@ApiModelProperty(required = false, value = "Only one location per request may have `purchaseDefault == true`.\n"
			+ "If not specified but required (e.g. because a new contact is created), then `false` is assumed.\n"
			+ "If `true`, then " //
			+ "* `purchase` is always be assumed to be `true` as well"
			+ "* another possibly exiting metasfresh contact might be set to `purchaseDefault = false`, even if it is not specified in this request.")
	@JsonInclude(Include.NON_NULL)
	Boolean purchaseDefault;

	@ApiModelProperty(required = false, value = "If not specified but required (e.g. because a new contact is created), then `false` is assumed")
	@JsonInclude(Include.NON_NULL)
	Boolean subjectMatter;

	@ApiModelProperty(position = 20, // shall be last
			required = false, value = "Sync advise about this contact's individual properties.\n" + PARENT_SYNC_ADVISE_DOC)
	@JsonInclude(Include.NON_NULL)
	SyncAdvise syncAdvise;

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonRequestContact(
			@JsonProperty("externalId") @Nullable final JsonExternalId externalId,
			@JsonProperty("metasfreshBPartnerId") @Nullable final MetasfreshId metasfreshBPartnerId,
			@JsonProperty("code") @Nullable final String code,
			@JsonProperty("active") @Nullable final Boolean active,
			@JsonProperty("name") final String name,

			@JsonProperty("firstName") final String firstName,
			@JsonProperty("lastName") final String lastName,
			@JsonProperty("email") final String email,
			@JsonProperty("phone") final String phone,

			@JsonProperty("newsletter") final Boolean newsletter,
			@JsonProperty("fax") final String fax,
			@JsonProperty("mobilePhone") final String mobilePhone,
			@JsonProperty("description") final String description,

			@JsonProperty("defaultContact") @Nullable final Boolean defaultContact,
			@JsonProperty("shipToDefault") @Nullable final Boolean shipToDefault,
			@JsonProperty("billToDefault") @Nullable final Boolean billToDefault,

			@JsonProperty("sales") @Nullable final Boolean sales,
			@JsonProperty("salesDefault") @Nullable final Boolean salesDefault,
			@JsonProperty("purchase") @Nullable final Boolean purchase,
			@JsonProperty("purchaseDefault") @Nullable final Boolean purchaseDefault,

			@JsonProperty("subjectMatter") @Nullable final Boolean subjectMatter,

			@JsonProperty("syncAdvise") @Nullable final SyncAdvise syncAdvise)
	{
		this.externalId = externalId;
		this.metasfreshBPartnerId = metasfreshBPartnerId;
		this.code = code;
		this.active = active;
		this.name = name;

		this.firstName = firstName;
		this.lastName = lastName;

		this.email = email;
		this.phone = phone;
		this.fax = fax;
		this.mobilePhone = mobilePhone;

		this.newsletter = newsletter;

		this.description = description;

		this.defaultContact = defaultContact;
		this.subjectMatter = subjectMatter;

		this.shipToDefault = shipToDefault;
		this.billToDefault = billToDefault;

		this.sales = sales;
		this.salesDefault = salesDefault;

		this.purchase = purchase;
		this.purchaseDefault = purchaseDefault;

		this.syncAdvise = syncAdvise;
	}
}
