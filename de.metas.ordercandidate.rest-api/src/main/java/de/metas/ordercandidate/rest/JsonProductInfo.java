package de.metas.ordercandidate.rest;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

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
public class JsonProductInfo
{

	public enum Type
	{
		ITEM, SERVICE
	}

	/** This translates to {@code M_Product.Value}. */
	@Nullable
	@ApiModelProperty(value="This translates to <code>M_Product.Value</code>.")
	private String code;

	/**
	 * This translates to {@code M_Product.Name}.
	 * If this is empty, and a product with the given {@link #code} does not yet exist, then the request will fail.
	 */
	@Nullable
	private String name;

	@NonNull
	private Type type;

	/**
	 * This translates to <code>C_UOM.X12DE355</code>.
	 * The respective UOM needs to exist in metasfresh and it's ID is set as <code>M_Product.C_UOM_ID</code>.
	 * If this is empty, and a product with the given <code>code</code> does not yet exist, then the request will fail.
	 */
	@Nullable
	private String uomCode;
}
