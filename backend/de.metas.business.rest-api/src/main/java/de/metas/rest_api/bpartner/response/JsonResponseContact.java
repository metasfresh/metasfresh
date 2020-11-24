package de.metas.rest_api.bpartner.response;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.rest_api.changelog.JsonChangeInfo;
import de.metas.rest_api.common.JsonExternalId;
import de.metas.rest_api.common.MetasfreshId;
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
public class JsonResponseContact
{
	public static final String PHONE = "phone";
	public static final String EMAIL = "email";
	public static final String LAST_NAME = "lastName";
	public static final String FIRST_NAME = "firstName";
	public static final String NAME = "name";
	public static final String GREETING = "greeting";
	public static final String CODE = "code";
	public static final String METASFRESH_BPARTNER_ID = "metasfreshBPartnerId";
	public static final String EXTERNAL_ID = "externalId";
	public static final String METASFRESH_ID = "metasfreshId";
	public static final String ACTIVE = "active";
	public static final String MOBILE_PHONE = "mobilePhone";
	public static final String FAX = "fax";
	public static final String DESCRIPTION = "description";
	public static final String NEWSLETTER = "newsletter";
	public static final String DEFAULT_CONTACT = "defaultContact";
	public static final String SHIP_TO_DEFAULT = "shipToDefault";
	public static final String BILL_TO_DEFAULT = "billToDefault";
	public static final String SALES_DEFAULT = "salesDefault";
	public static final String SALES = "sales";
	public static final String PURCHASE_DEFAULT = "purchaseDefault";
	public static final String PURCHASE = "purchase";
	public static final String SUBJECT_MATTER = "subjectMatter";

	@ApiModelProperty(allowEmptyValue = false, dataType = "java.lang.Long")
	MetasfreshId metasfreshId;

	@ApiModelProperty(dataType = "java.lang.String")
	JsonExternalId externalId;

	@ApiModelProperty(dataType = "java.lang.Integer")
	private MetasfreshId metasfreshBPartnerId;

	@ApiModelProperty("translated to `AD_User.Value`")
	@JsonInclude(Include.NON_NULL)
	String code;

	@ApiModelProperty(allowEmptyValue = false)
	boolean active;

	@ApiModelProperty(allowEmptyValue = false)
	String name;

	@JsonInclude(Include.NON_EMPTY)
	String greeting;

	@JsonInclude(Include.NON_EMPTY)
	String lastName;

	@JsonInclude(Include.NON_EMPTY)
	String firstName;

	@JsonInclude(Include.NON_EMPTY)
	String email;

	@JsonInclude(Include.NON_EMPTY)
	String phone;

	@JsonInclude(Include.NON_EMPTY)
	String mobilePhone;

	@JsonInclude(Include.NON_EMPTY)
	String fax;

	@JsonInclude(Include.NON_EMPTY)
	String description;

	@ApiModelProperty(allowEmptyValue = false)
	boolean newsletter;

	@ApiModelProperty(allowEmptyValue = false)
	boolean shipToDefault;

	@ApiModelProperty(allowEmptyValue = false)
	boolean billToDefault;

	@ApiModelProperty(allowEmptyValue = false)
	boolean defaultContact;

	@ApiModelProperty(allowEmptyValue = false)
	boolean sales;

	@ApiModelProperty(allowEmptyValue = false)
	boolean salesDefault;

	@ApiModelProperty(allowEmptyValue = false)
	boolean purchase;

	@ApiModelProperty(allowEmptyValue = false)
	boolean purchaseDefault;

	@ApiModelProperty(allowEmptyValue = false)
	boolean subjectMatter;

	@JsonInclude(Include.NON_NULL)
	@ApiModelProperty(position = 20) // shall be last
	JsonChangeInfo changeInfo;

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonResponseContact(
			@JsonProperty(METASFRESH_ID) @Nullable final MetasfreshId metasfreshId,
			@JsonProperty(EXTERNAL_ID) @Nullable final JsonExternalId externalId,
			@JsonProperty(METASFRESH_BPARTNER_ID) @Nullable final MetasfreshId metasfreshBPartnerId,
			@JsonProperty(ACTIVE) final boolean active,
			@JsonProperty(CODE) @Nullable final String code,
			@JsonProperty(NAME) final String name,
			@JsonProperty(GREETING) final String greeting,
			@JsonProperty(FIRST_NAME) final String firstName,
			@JsonProperty(LAST_NAME) final String lastName,
			@JsonProperty(EMAIL) final String email,
			@JsonProperty(PHONE) final String phone,

			@JsonProperty(MOBILE_PHONE) final String mobilePhone,
			@JsonProperty(FAX) final String fax,
			@JsonProperty(DESCRIPTION) final String description,
			@JsonProperty(NEWSLETTER) final boolean newsletter,

			@JsonProperty(SHIP_TO_DEFAULT) final boolean shipToDefault,
			@JsonProperty(BILL_TO_DEFAULT) final boolean billToDefault,
			@JsonProperty(DEFAULT_CONTACT) final boolean defaultContact,

			@JsonProperty(SALES) final boolean sales,
			@JsonProperty(SALES_DEFAULT) final boolean salesDefault,
			@JsonProperty(PURCHASE) final boolean purchase,
			@JsonProperty(PURCHASE_DEFAULT) final boolean purchaseDefault,
			@JsonProperty(SUBJECT_MATTER) final boolean subjectMatter,

			@JsonProperty("changeInfo") @Nullable JsonChangeInfo changeInfo)
	{
		this.defaultContact = defaultContact;
		this.billToDefault = billToDefault;
		this.shipToDefault = shipToDefault;
		this.sales = sales;
		this.salesDefault = salesDefault;
		this.purchase = purchase;
		this.purchaseDefault = purchaseDefault;
		this.subjectMatter = subjectMatter;

		this.newsletter = newsletter;
		this.description = description;
		this.fax = fax;
		this.mobilePhone = mobilePhone;
		this.metasfreshId = metasfreshId;
		this.externalId = externalId;
		this.metasfreshBPartnerId = metasfreshBPartnerId;

		this.active = active;

		this.code = code;
		this.name = name;
		this.greeting = greeting;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;

		this.changeInfo = changeInfo;
	}
}
