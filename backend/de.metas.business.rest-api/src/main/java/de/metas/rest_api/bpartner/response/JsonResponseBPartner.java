package de.metas.rest_api.bpartner.response;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.rest_api.changelog.JsonChangeInfo;
import de.metas.rest_api.common.JsonExternalId;
import de.metas.rest_api.common.MetasfreshId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
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
@ApiModel(description = "Note that given the respective use-case, either one of both properties migh be `null`, but not both at once.")
public class JsonResponseBPartner
{
	public static final String GROUP_NAME = "group";
	public static final String URL = "url";
	public static final String URL_2 = "url2";
	public static final String URL_3 = "url3";
	public static final String LANGUAGE = "language";
	public static final String PHONE = "phone";
	public static final String PARENT_ID = "parentId";
	public static final String COMPANY_NAME = "companyName";
	public static final String NAME = "name";
	public static final String NAME_2 = "name2";
	public static final String NAME_3 = "name3";
	public static final String EXTERNAL_ID = "externalId";
	public static final String METASFRESH_ID = "metasfreshId";
	public static final String CODE = "code";
	public static final String GLOBAL_ID = "globalId";
	public static final String ACTIVE = "active";
	public static final String VENDOR = "vendor";
	public static final String CUSTOMER = "customer";
	public static final String VAT_ID = "vatId";

	private static final String CHANGE_INFO = "changeInfo";

	@ApiModelProperty( //
			allowEmptyValue = false, //
			dataType = "java.lang.Integer", //
			value = "This translates to `C_BPartner.C_BPartner_ID`.")
	@JsonProperty(METASFRESH_ID)
	@JsonInclude(Include.NON_NULL)
	MetasfreshId metasfreshId;

	@ApiModelProperty( //
			required = false, //
			dataType = "java.lang.String", //
			value = "This translates to `C_BPartner.ExternalId`.")
	@JsonProperty(EXTERNAL_ID)
	@JsonInclude(Include.NON_NULL)
	JsonExternalId externalId;

	@ApiModelProperty(required = false, value = "This translates to `C_BPartner.Value`.")
	@JsonProperty(CODE)
	String code;

	@ApiModelProperty(required = false, value = "This translates to `C_BPartner.GlobalId`.")
	@JsonProperty(GLOBAL_ID)
	String globalId;

	@ApiModelProperty(required = false, value = "This translates to `C_BPartner.IsActive`.")
	@JsonProperty(ACTIVE)
	boolean active;

	@ApiModelProperty(required = false, value = "This translates to `C_BPartner.Name`.")
	@JsonProperty(NAME)
	String name;

	@ApiModelProperty(required = false, value = "This translates to `C_BPartner.Name2`.")
	@JsonProperty(NAME_2)
	@JsonInclude(Include.NON_NULL)
	String name2;

	@ApiModelProperty(required = false, value = "This translates to `C_BPartner.Name3`.")
	@JsonProperty(NAME_3)
	@JsonInclude(Include.NON_NULL)
	String name3;

	@ApiModelProperty( //
			required = false, //
			value = "This translates to `C_BPartner.CompanyName`.\n"
					+ "If set, the the respective `C_BPartner` record will also have `IsCompany='Y'`")
	@JsonProperty(COMPANY_NAME)
	@JsonInclude(Include.NON_NULL)
	String companyName;

	@ApiModelProperty( //
			required = false, //
			value = "This translates to `C_BPartner.BPartner_Parent_ID`. It's a this bpartner's central/parent company",//
			dataType = "java.lang.Integer")
	@JsonProperty(PARENT_ID)
	@JsonInclude(Include.NON_NULL)
	MetasfreshId parentId;

	@ApiModelProperty( //
			required = false, //
			value = "This translates to `C_BPartner.Phone2`. It's this bpartner's central phone number")
	@JsonProperty(PHONE)
	@JsonInclude(Include.NON_NULL)
	String phone;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(LANGUAGE)
	String language;

	@ApiModelProperty(required = false, value = "This translates to `C_BPartner.URL`.")
	@JsonProperty(URL)
	@JsonInclude(Include.NON_NULL)
	String url;

	@ApiModelProperty(required = false, value = "This translates to `C_BPartner.URL2`.")
	@JsonProperty(URL_2)
	@JsonInclude(Include.NON_NULL)
	private String url2;

	@ApiModelProperty(required = false, value = "This translates to `C_BPartner.URL3`.")
	@JsonProperty(URL_3)
	@JsonInclude(Include.NON_NULL)
	private String url3;

	@ApiModelProperty( //
			required = false, //
			value = "Name of the business partner's group")
	@JsonProperty(GROUP_NAME)
	@JsonInclude(Include.NON_NULL)
	String group;

	@ApiModelProperty(required = false, value = "This translates to `C_BPartner.IsVendor`.")
	@JsonProperty(VENDOR)
	boolean vendor;

	@ApiModelProperty(required = false, value = "This translates to `C_BPartner.IsCustomer`.")
	@JsonProperty(CUSTOMER)
	boolean customer;

	@ApiModelProperty(required = false, value = "This translates to `C_BPartner.VATaxID`.")
	@JsonProperty(VAT_ID)
	String vatId;

	@ApiModelProperty(position = 9999) // shall be last
	@JsonProperty(CHANGE_INFO)
	@JsonInclude(Include.NON_NULL)
	JsonChangeInfo changeInfo;

	@JsonCreator
	@Builder(toBuilder = true)
	private JsonResponseBPartner(
			@JsonProperty(METASFRESH_ID) @NonNull final MetasfreshId metasfreshId,
			@JsonProperty(EXTERNAL_ID) @Nullable final JsonExternalId externalId,
			@JsonProperty(CODE) @Nullable final String code, // usually metasfresh makes sure it's not null; but in unit-tests it might be; also, let's just return the result here, and avoid throwing an NPE
			@JsonProperty(GLOBAL_ID) @Nullable final String globalId,
			@JsonProperty(ACTIVE) @NonNull final Boolean active,
			@JsonProperty(NAME) @NonNull final String name,
			@JsonProperty(NAME_2) @Nullable final String name2,
			@JsonProperty(NAME_3) @Nullable final String name3,
			@JsonProperty(COMPANY_NAME) @Nullable final String companyName,
			@JsonProperty(PARENT_ID) @Nullable final MetasfreshId parentId,
			@JsonProperty(PHONE) @Nullable final String phone,
			@JsonProperty(LANGUAGE) @Nullable final String language,
			@JsonProperty(URL) @Nullable final String url,
			@JsonProperty(URL_2) @Nullable final String url2,
			@JsonProperty(URL_3) @Nullable final String url3,
			@JsonProperty(GROUP_NAME) @Nullable final String group,
			@JsonProperty(VENDOR) @NonNull final Boolean vendor,
			@JsonProperty(CUSTOMER) @NonNull final Boolean customer,
			@JsonProperty(VAT_ID) @Nullable final String vatId,
			//
			@JsonProperty(CHANGE_INFO) @Nullable JsonChangeInfo changeInfo)
	{
		this.metasfreshId = metasfreshId;
		this.externalId = externalId;
		this.code = code;
		this.globalId = globalId;
		this.active = active;

		this.name = name;
		this.name2 = name2;
		this.name3 = name3;

		this.companyName = companyName;

		this.parentId = parentId;

		this.phone = phone;
		this.language = language;

		this.url = url;
		this.url2 = url2;
		this.url3 = url3;

		this.group = group;

		this.vendor = vendor;
		this.customer = customer;

		this.vatId = vatId;

		this.changeInfo = changeInfo;
	}
}
