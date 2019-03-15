package de.metas.ordercandidate.rest;

import static org.compiere.util.Util.coalesce;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class JsonProductInfo
{
	public enum Type
	{
		ITEM, SERVICE
	}

	/** This translates to {@code M_Product.Value}. */
	@ApiModelProperty(value = "This translates to <code>M_Product.Value</code>.")
	@JsonInclude(Include.NON_NULL)
	private String code;

	/**
	 * This translates to {@code M_Product.Name}.
	 * If this is empty, and a product with the given {@link #code} does not yet exist, then the request will fail.
	 */
	@JsonInclude(Include.NON_NULL)
	private String name;

	private Type type;

	/**
	 * This translates to <code>C_UOM.X12DE355</code>.
	 * The respective UOM needs to exist in metasfresh and it's ID is set as <code>M_Product.C_UOM_ID</code>.
	 * If this is empty, and a product with the given <code>code</code> does not yet exist, then the request will fail.
	 */
	@JsonInclude(Include.NON_NULL)
	private String uomCode;

	@JsonInclude(Include.NON_NULL)
	private SyncAdvise syncAdvise;

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonProductInfo(
			@JsonProperty("code") @Nullable final String code,
			@JsonProperty("name") @Nullable final String name,
			@JsonProperty("type") @NonNull final Type type,
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
