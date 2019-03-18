package de.metas.ordercandidate.rest;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

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

// @JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
@ApiModel(description = "Note that given the respective use-case, either one of both properties migh be <code>null</code>, but not both at once.")
public class JsonBPartner
{
	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "This translates to <code>C_BPartner.ExternalId</code>. If set, the system will attempt a lookup.\n"
					+ "If the lookup succeeds and <code>code</code> and/or <code>name</code> is not empty, then the system will update the bPartner it looked up.\n"
					+ "If <code>null</code>, or not bPartner was found, it will create a new BPartner.")
	@JsonInclude(Include.NON_NULL)
	private String externalId;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "This translates to <code>C_BPartner.Value</code>. If set and <code>externalId<code> is empty, the system will attempt a lookup.\n"
					+ "If the lookup succeeds and <code>name</code> is not empty, then the system will update the bPartner it looked up.\n"
					+ "If <code>null</code>, or not bPartner was found, it will create a new BPartner.")
	@JsonInclude(Include.NON_NULL)
	private String code;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "This translates to <code>C_BPartner.Name</code>.\n"
					+ "If this is empty, and a BPartner with the given <code>code</code> does not yet exist, then the request will fail.")
	@JsonInclude(Include.NON_NULL)
	private String name;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "This translates to <code>C_BPartner.CompanyName</code>.\n"
					+ "If set, the the respective <code>C_BPartner</code> record will also have <code>IsCompany='Y'</code>")
	@JsonInclude(Include.NON_NULL)
	private String companyName;

	@JsonCreator
	@Builder
	private JsonBPartner(
			@JsonProperty("externalId") @Nullable final String externalId,
			@JsonProperty("code") @Nullable final String code,
			@JsonProperty("name") @Nullable final String name,
			@JsonProperty("companyName") @Nullable final String companyName)
	{
		this.externalId = externalId;
		this.code = code;
		this.name = name;
		this.companyName = companyName;

		// both externalId and code may be empty if this instance belongs to a JsonBPArtnerInfo that has a location with has a GLN.
		// errorIf(isEmpty(code, true) && isEmpty(externalId, true), "At least one of code and externalId need to be non-empty; name={}; companyName={}", name, companyName);
	}

}
