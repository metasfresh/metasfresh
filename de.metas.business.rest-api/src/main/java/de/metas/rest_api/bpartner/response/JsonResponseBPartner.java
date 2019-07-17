package de.metas.rest_api.bpartner.response;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.MetasfreshId;
import de.metas.rest_api.changelog.JsonChangeInfo;
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
	public static final String GROUP = "group";
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
	public static final String ACTIVE = "active";

	@ApiModelProperty( //
			allowEmptyValue = false, //
			dataType = "java.lang.Integer", //
			value = "This translates to `C_BPartner.C_BPartner_ID`.")
	@JsonInclude(Include.NON_NULL)
	MetasfreshId metasfreshId;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			dataType = "java.lang.String", //
			value = "This translates to `C_BPartner.ExternalId`.")
	@JsonInclude(Include.NON_NULL)
	JsonExternalId externalId;

	@ApiModelProperty(allowEmptyValue = false, value = "This translates to `C_BPartner.Value`.")
	String code;

	@ApiModelProperty(allowEmptyValue = false, value = "This translates to `C_BPartner.IsActive`.")
	boolean active;

	@ApiModelProperty(allowEmptyValue = false, value = "This translates to `C_BPartner.Name`.")
	String name;

	@ApiModelProperty(allowEmptyValue = true, value = "This translates to `C_BPartner.Name2`.")
	@JsonInclude(Include.NON_NULL)
	String name2;

	@ApiModelProperty(allowEmptyValue = true, value = "This translates to `C_BPartner.Name3`.")
	@JsonInclude(Include.NON_NULL)
	String name3;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "This translates to `C_BPartner.CompanyName`.\n"
					+ "If set, the the respective `C_BPartner` record will also have `IsCompany='Y'`")
	@JsonInclude(Include.NON_NULL)
	String companyName;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "This translates to `C_BPartner.BPartner_Parent_ID`. It's a this bpartner's central/parent company",//
			dataType = "java.lang.Integer")
	@JsonInclude(Include.NON_NULL)
	MetasfreshId parentId;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "This translates to `C_BPartner.Phone2`. It's this bpartner's central phone number")
	@JsonInclude(Include.NON_NULL)
	String phone;

	@JsonInclude(Include.NON_NULL)
	String language;

	@ApiModelProperty(allowEmptyValue = true, value = "This translates to `C_BPartner.URL`.")
	@JsonInclude(Include.NON_NULL)
	String url;

	@ApiModelProperty(allowEmptyValue = true, value = "This translates to `C_BPartner.URL2`.")
	@JsonInclude(Include.NON_NULL)
	private String url2;

	@ApiModelProperty(allowEmptyValue = true, value = "This translates to `C_BPartner.URL3`.")
	@JsonInclude(Include.NON_NULL)
	private String url3;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "Name of the business partner's group")
	@JsonInclude(Include.NON_NULL)
	String group;

	@JsonInclude(Include.NON_NULL)
	@ApiModelProperty(position = 20) // shall be last
	JsonChangeInfo changeInfo;

	@JsonCreator
	@Builder(toBuilder = true)
	private JsonResponseBPartner(
			@JsonProperty(METASFRESH_ID) @NonNull final MetasfreshId metasfreshId,
			@JsonProperty(EXTERNAL_ID) @NonNull final JsonExternalId externalId,
			@JsonProperty(CODE) @NonNull final String code,
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
			@JsonProperty(GROUP) @Nullable final String group,
			@JsonProperty("changeInfo") @Nullable JsonChangeInfo changeInfo)
	{
		this.metasfreshId = metasfreshId;
		this.externalId = externalId;
		this.code = code;
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
		this.changeInfo = changeInfo;
	}
}
