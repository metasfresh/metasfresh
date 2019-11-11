package de.metas.rest_api.bpartner.request;

import static de.metas.rest_api.bpartner.SwaggerDocConstants.READ_ONLY_SYNC_ADVISE_DOC;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.JsonInvoiceRule;
import de.metas.rest_api.MetasfreshId;
import de.metas.rest_api.SyncAdvise;
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
@ApiModel(description = "Note that given the respective use-case, either one of both properties migh be `null`, but not both at once.")
public class JsonRequestBPartner
{
	@ApiModelProperty(position = 10, required = false, //
			dataType = "java.lang.String", //
			value = "This translates to `C_BPartner.ExternalId`.")
	@JsonInclude(Include.NON_NULL)
	JsonExternalId externalId;

	@ApiModelProperty(position = 20, required = false, //
			value = "This translates to `C_BPartner.Value`.")
	@JsonInclude(Include.NON_NULL)
	String code;

	@ApiModelProperty(required = false, value = "If not specified but required (e.g. because a new partner is created), then `true` is assumed.")
	@JsonInclude(Include.NON_NULL)
	Boolean active;

	@ApiModelProperty(position = 30, required = false, //
			value = "This translates to `C_BPartner.Name`.\n"
					+ "If this is empty, and a BPartner with the given `name` does not yet exist, then the request will fail.")
	@JsonInclude(Include.NON_NULL)
	String name;

	@ApiModelProperty(position = 40, required = false, //
			value = "This translates to `C_BPartner.Name2`.")
	@JsonInclude(Include.NON_NULL)
	String name2;

	@ApiModelProperty(position = 50, required = false, //
			value = "This translates to `C_BPartner.Name3`.")
	@JsonInclude(Include.NON_NULL)
	String name3;

	@ApiModelProperty(position = 60, required = false, //
			value = "This translates to `C_BPartner.CompanyName`.\n"
					+ "If set, the the respective `C_BPartner` record will also have `IsCompany='Y'`")
	@JsonInclude(Include.NON_NULL)
	String companyName;

	@ApiModelProperty(position = 70, required = false, //
			value = "This translates to `C_BPartner.BPartner_Parent_ID`. It's a this bpartner's central/parent company",//
			dataType = "java.lang.Integer")
	@JsonInclude(Include.NON_NULL)
	MetasfreshId parentId;

	@ApiModelProperty(position = 80, required = false, //
			value = "This translates to `C_BPartner.Phone2`. It's this bpartner's central phone number")
	@JsonInclude(Include.NON_NULL)
	String phone;

	@ApiModelProperty(position = 90, required = false)
	@JsonInclude(Include.NON_NULL)
	String language;

	@ApiModelProperty(position = 100, required = false, //
			value = "Optional; if specified, it will be used, e.g. when an order is created for this business partner.")
	@JsonInclude(Include.NON_NULL)
	JsonInvoiceRule invoiceRule;

	@ApiModelProperty(position = 110, required = false)
	@JsonInclude(Include.NON_NULL)
	String url;

	@ApiModelProperty(position = 120, required = false)
	@JsonInclude(Include.NON_NULL)
	String url2;

	@ApiModelProperty(position = 130, required = false)
	@JsonInclude(Include.NON_NULL)
	String url3;

	@ApiModelProperty(position = 140, required = false, //
			value = "Name of the business partner's group")
	@JsonInclude(Include.NON_NULL)
	String group;

	@ApiModelProperty(position = 150, required = false, //
			value = READ_ONLY_SYNC_ADVISE_DOC)
	@JsonInclude(Include.NON_NULL)
	SyncAdvise syncAdvise;

	@JsonCreator
	@Builder(toBuilder = true)
	private JsonRequestBPartner(
			@JsonProperty("externalId") @Nullable final JsonExternalId externalId,
			@JsonProperty("code") @Nullable final String code,
			@JsonProperty("active") @Nullable final Boolean active,
			@JsonProperty("name") @Nullable final String name,
			@JsonProperty("name2") @Nullable final String name2,
			@JsonProperty("name3") @Nullable final String name3,
			@JsonProperty("companyName") @Nullable final String companyName,
			@JsonProperty("parentId") @Nullable final MetasfreshId parentId,
			@JsonProperty("phone") @Nullable final String phone,
			@JsonProperty("language") @Nullable final String language,
			@JsonProperty("invoiceRule") @Nullable final JsonInvoiceRule invoiceRule,
			@JsonProperty("url") @Nullable final String url,
			@JsonProperty("url2") @Nullable final String url2,
			@JsonProperty("url3") @Nullable final String url3,
			@JsonProperty("group") @Nullable final String group,
			@JsonProperty("syncAdvise") @Nullable final SyncAdvise syncAdvise)
	{
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
		this.invoiceRule = invoiceRule;

		this.url = url;
		this.url2 = url2;
		this.url3 = url3;
		this.group = group;
		this.syncAdvise = syncAdvise;
	}
}
