package de.metas.ordercandidate.rest;

import lombok.Builder;
import lombok.Data;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Data
@Builder
@ApiModel(description="Note that given the respective use-case, either one of both properties migh be <code>null</code>, but not both at once.")
public class JsonBPartner
{
	@Nullable
	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "This translates to <code>C_BPartner.ExternalId</code>. If set, the system will attempt a lookup.\n"
					+ "If the lookup succeeds and <code>code</code> and/or <code>name</code> is not empty, then the system will update the bPartner it looked up.\n"
					+ "If <code>null</code>, or not bPartner was found, it will create a new BPartner.")
	private String externalId;

	@Nullable
	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "This translates to <code>C_BPartner.Value</code>. If set and <code>externalId<code> is empty, the system will attempt a lookup.\n"
					+ "If the lookup succeeds and <code>name</code> is not empty, then the system will update the bPartner it looked up.\n"
					+ "If <code>null</code>, or not bPartner was found, it will create a new BPartner.")
	private String code;

	@Nullable
	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "This translates to <code>C_BPartner.Name</code>.\n"
					+ "If this is empty, and a BPartner with the given <code>code</code> does not yet exist, then the request will fail.")
	private String name;

	@Nullable
	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "This translates to <code>C_BPartner.CompanyName</code>.\n"
					+ "If set, the the respective <code>C_BPartner</code> record will also have <code>IsCompany='Y'</code>")
	private String companyName;
}
