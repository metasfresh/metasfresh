/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.common.rest_api.v2;

import de.pentabyte.springfox.ApiEnum;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Arrays;

public enum JSONDeliveryRule
{

	@ApiEnum("Specifies that the order will have deliveryRule = After Receipt")
	AfterReceipt("R"),

	@ApiEnum("Specifies that the order will have deliveryRule = Availability")
	Availability("A"),

	@ApiEnum("Specifies that the order will have deliveryRule = Complete Line")
	CompleteLine("L"),

	@ApiEnum("Specifies that the order will have deliveryRule = Complete Order")
	CompleteOrder("O"),

	@ApiEnum("Specifies that the order will have deliveryRule = Force")
	Force("F"),

	@ApiEnum("Specifies that the order will have deliveryRule = Manual")
	Manual("M"),

	@ApiEnum("Specifies that the order will have deliveryRule = Mit NaechsterAbolieferung")
	MitNaechsterAbolieferung("S");

	@Getter
	private final String code;

	JSONDeliveryRule(final String code)
	{
		this.code = code;
	}

	@Nullable
	public static JSONDeliveryRule ofCodeOrNull(@Nullable final String code)
	{
		if(de.metas.common.util.Check.isBlank(code))
		{
			return null;
		}
		return ofCode(code);
	}
	
	@NonNull
	public static JSONDeliveryRule ofCode(@NonNull final String code)
	{
		return Arrays.stream(values())
				.filter(type -> type.getCode().equals(code))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("No JSONPaymentRule found for code:" + code));
	}

}
