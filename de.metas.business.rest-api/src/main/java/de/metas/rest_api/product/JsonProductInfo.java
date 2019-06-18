package de.metas.rest_api.product;

import static de.metas.util.lang.CoalesceUtil.coalesce;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class JsonProductInfo
{
	public enum Type
	{
		ITEM, SERVICE
	}

	@ApiModelProperty( //
			allowEmptyValue = false, //
			value = "Mandatory - This translates to `M_Product.Value`.")
	@JsonInclude(Include.NON_NULL)
	private String code;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "This translates to `M_Product.Name`.\n"
					+ "If this is empty, and a product with the given {@link #code} does not yet exist, then the request will fail.")
	@JsonInclude(Include.NON_NULL)
	private String name;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "This translates to `M_Product.ProductType`.\n"
					+ "If this is empty, and a product with the given {@link #code} does not yet exist, then the request will fail.")
	private Type type;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "This translates to `C_UOM.X12DE355`.\n"
			+"The respective UOM needs to exist in metasfresh and then its ID is set as `M_Product.C_UOM_ID`.\n"
			+ "If this property is empty, and a product with the given `code` does not yet exist, then the request will fail.")
	@JsonInclude(Include.NON_NULL)
	private String uomCode;

	@JsonInclude(Include.NON_NULL)
	private SyncAdvise syncAdvise;

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonProductInfo(
			@JsonProperty("code") @Nullable final String code,
			@JsonProperty("name") @Nullable final String name,
			@JsonProperty("type") @Nullable final Type type,
			@JsonProperty("uomCode") @Nullable final String uomCode,
			@JsonProperty("syncAdvise") @Nullable final SyncAdvise syncAdvise)
	{
		this.code = code;
		this.name = name;
		this.type = type;
		this.uomCode = uomCode;
		this.syncAdvise = coalesce(syncAdvise, SyncAdvise.READ_ONLY);
	}
}
