/*
 * #%L
 * de-metas-common-ordercandidates
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

package de.metas.common.ordercandidates.v2.request;

import com.google.common.collect.ImmutableMap;
import de.pentabyte.springfox.ApiEnum;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum JsonOrderDocType
{
	@ApiEnum("Specifies if the order will be a standard one. A standard order will be created if no DocTYpe is specified.")
	SalesOrder("SO"),

	@ApiEnum("Specifies if the order is ReturnMaterial")
	ReturnMaterial("RM"),

	@ApiEnum("Specifies if the order is Quotation")
	Proposal("ON"),

	@ApiEnum("Specifies if the order will be prepaid")
	PrepayOrder("PR");

	@Getter
	private final String code;

	JsonOrderDocType(final String code)
	{
		this.code = code;
	}

	private static final Map<String, JsonOrderDocType> lookup;

	static
	{
		lookup = ImmutableMap.copyOf(Arrays.stream(JsonOrderDocType.values())
											 .collect(Collectors.toMap(JsonOrderDocType::getCode, item -> item)));
	}

	public static JsonOrderDocType ofCode(@NonNull final String code){
		final JsonOrderDocType orderDocType = lookup.get(code);

		if(orderDocType == null){
			throw new IllegalArgumentException("OrderDocType does not support code: " + code);
		}

		return orderDocType;
	}

}