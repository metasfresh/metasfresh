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
@ApiModel(description = "Note that given the respective use-case, either one of both properties migh be <code>null</code>, but not both at once.")
public class JsonResponseBPartner
{
	public static final String GROUP = "group";
	public static final String URL = "url";
	public static final String LANGUAGE = "language";
	public static final String PHONE = "phone";
	public static final String PARENT_ID = "parentId";
	public static final String COMPANY_NAME = "companyName";
	public static final String NAME = "name";
	public static final String EXTERNAL_ID = "externalId";
	public static final String METASFRESH_ID = "metasfreshId";
	public static final String CODE = "code";

	@ApiModelProperty( //
			allowEmptyValue = true, //
			dataType = "java.lang.Integer", //
			value = "This translates to <code>C_BPartner.C_BPartner_ID</code>. If set, the system will attempt a lookup.\n"
					+ "If the lookup succeeds and <code>code</code> and/or <code>name</code> is not empty, then the system will update the bPartner it looked up.\n"
					+ "If the lookup does not succeed, it will fail.")
	@JsonInclude(Include.NON_NULL)
	MetasfreshId metasfreshId;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			dataType = "java.lang.String", //
			value = "This translates to <code>C_BPartner.ExternalId</code>. If set, the system will attempt a lookup.\n"
					+ "If the lookup succeeds and <code>code</code> and/or <code>name</code> is not empty, then the system will update the bPartner it looked up.\n"
					+ "If <code>null</code>, or no bPartner was found, it will create a new BPartner.")
	@JsonInclude(Include.NON_NULL)
	JsonExternalId externalId;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "This translates to <code>C_BPartner.Value</code>. If set and <code>externalId<code> is empty, the system will attempt a lookup.\n"
					+ "If the lookup succeeds and <code>name</code> is not empty, then the system will update the bPartner it looked up.\n"
					+ "If <code>null</code>, or no bPartner was found, it will create a new BPartner.")
	@JsonInclude(Include.NON_NULL)
	String code;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "This translates to <code>C_BPartner.Name</code>.\n"
					+ "If this is empty, and a BPartner with the given <code>code</code> does not yet exist, then the request will fail.")
	@JsonInclude(Include.NON_NULL)
	String name;

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

	@JsonInclude(Include.NON_NULL)
	String url;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "Name of the business partner's group")
	@JsonInclude(Include.NON_NULL)
	String group;

	@JsonInclude(Include.NON_NULL)
	JsonChangeInfo changeInfo;

	@JsonCreator
	@Builder(toBuilder = true)
	private JsonResponseBPartner(
			@JsonProperty(METASFRESH_ID) @Nullable final MetasfreshId metasfreshId,
			@JsonProperty(EXTERNAL_ID) @Nullable final JsonExternalId externalId,

			@JsonProperty(CODE) @Nullable final String code,
			@JsonProperty(NAME) @Nullable final String name,
			@JsonProperty(COMPANY_NAME) @Nullable final String companyName,
			@JsonProperty(PARENT_ID) @Nullable final MetasfreshId parentId,
			@JsonProperty(PHONE) @Nullable final String phone,
			@JsonProperty(LANGUAGE) @Nullable final String language,
			@JsonProperty(URL) @Nullable final String url,
			@JsonProperty(GROUP) @Nullable final String group,
			@JsonProperty("changeInfo") @Nullable JsonChangeInfo changeInfo)
	{
		this.metasfreshId = metasfreshId;
		this.externalId = externalId;
		this.code = code;

		this.name = name;
		this.companyName = companyName;

		this.parentId = parentId;

		this.phone = phone;
		this.language = language;
		this.url = url;
		this.group = group;
		this.changeInfo = changeInfo;
	}
}
