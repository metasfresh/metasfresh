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
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

@Value
public class JsonResponseContact
{
	public static final String PHONE = "phone";
	public static final String EMAIL = "email";
	public static final String LAST_NAME = "lastName";
	public static final String FIRST_NAME = "firstName";
	public static final String BIRTHDAY = "birthday";
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
	public static final String INVOICE_EMAIL_ENABLED = "invoiceEmailEnabled";
	public static final String DEFAULT_CONTACT = "defaultContact";
	public static final String SHIP_TO_DEFAULT = "shipToDefault";
	public static final String BILL_TO_DEFAULT = "billToDefault";
	public static final String SALES_DEFAULT = "salesDefault";
	public static final String SALES = "sales";
	public static final String PURCHASE_DEFAULT = "purchaseDefault";
	public static final String PURCHASE = "purchase";
	public static final String SUBJECT_MATTER = "subjectMatter";
	public static final String ROLES = "roles";
	public static final String METASFRESH_LOCATION_ID = "metasfreshLocationId";
	public static final String EMAIL2 = "email2";
	public static final String EMAIL3 = "email3";
	public static final String PHONE2 = "phone2";
	public static final String TITLE = "title";
	public static final String POSITION = "position";

	@Schema
	JsonMetasfreshId metasfreshId;

	@Schema
	JsonMetasfreshId metasfreshBPartnerId;

	@Schema(description = "translated to `AD_User.Value`")
	@JsonInclude(Include.NON_NULL)
	String code;

	@Schema()
	boolean active;

	@Schema()
	String name;

	@JsonInclude(Include.NON_EMPTY)
	JsonResponseGreeting greeting;

	@JsonInclude(Include.NON_EMPTY)
	String lastName;

	@JsonInclude(Include.NON_EMPTY)
	String firstName;

	@JsonInclude(Include.NON_EMPTY)
	LocalDate birthday;

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

	@JsonInclude(Include.NON_EMPTY)
	String phone2;

	@JsonInclude(Include.NON_EMPTY)
	String title;

	@JsonInclude(Include.NON_EMPTY)
	JsonResponseContactPosition position;

	@Schema
	boolean newsletter;

	@Schema
	Boolean invoiceEmailEnabled;

	@Schema
	boolean shipToDefault;

	@Schema
	boolean billToDefault;

	@Schema
	boolean defaultContact;

	@Schema
	boolean sales;

	@Schema
	boolean salesDefault;

	@Schema
	boolean purchase;

	@Schema
	boolean purchaseDefault;

	@Schema
	boolean subjectMatter;

	@Schema
	@JsonInclude(Include.NON_EMPTY)
	List<JsonResponseContactRole> roles;

	@Schema
	JsonMetasfreshId metasfreshLocationId;

	@JsonInclude(Include.NON_EMPTY)
	String email2;

	@JsonInclude(Include.NON_EMPTY)
	String email3;

	@JsonInclude(Include.NON_NULL)
	@Schema // shall be last
	JsonChangeInfo changeInfo;

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonResponseContact(
			@JsonProperty(METASFRESH_ID) @Nullable final JsonMetasfreshId metasfreshId,
			@JsonProperty(METASFRESH_BPARTNER_ID) @Nullable final JsonMetasfreshId metasfreshBPartnerId,
			@JsonProperty(ACTIVE) final boolean active,
			@JsonProperty(CODE) @Nullable final String code,
			@JsonProperty(NAME) final String name,
			@JsonProperty(GREETING) final JsonResponseGreeting greeting,
			@JsonProperty(FIRST_NAME) final String firstName,
			@JsonProperty(LAST_NAME) final String lastName,
			@JsonProperty(BIRTHDAY) @Nullable final LocalDate birthday,
			@JsonProperty(EMAIL) final String email,
			@JsonProperty(PHONE) final String phone,
			@JsonProperty(EMAIL2) final String email2,
			@JsonProperty(EMAIL3) final String email3,
			@JsonProperty(PHONE2) final String phone2,
			@JsonProperty(TITLE) final String title,
			@JsonProperty(POSITION) @Nullable final JsonResponseContactPosition position,

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
			@JsonProperty(INVOICE_EMAIL_ENABLED) final Boolean invoiceEmailEnabled,
			@JsonProperty(ROLES) final List<JsonResponseContactRole> roles,
			@JsonProperty(METASFRESH_LOCATION_ID) @Nullable final JsonMetasfreshId metasfreshLocationId,

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
		this.metasfreshBPartnerId = metasfreshBPartnerId;

		this.active = active;

		this.code = code;
		this.name = name;
		this.greeting = greeting;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.email = email;
		this.phone = phone;
		this.invoiceEmailEnabled = invoiceEmailEnabled;
		this.roles = roles;
		this.email2 = email2;
		this.email3 = email3;

		this.changeInfo = changeInfo;
		this.metasfreshLocationId = metasfreshLocationId;

		this.phone2 = phone2;
		this.title = title;
		this.position = position;
	}
}
