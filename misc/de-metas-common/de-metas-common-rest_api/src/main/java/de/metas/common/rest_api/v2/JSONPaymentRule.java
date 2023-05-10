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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Arrays;

@Schema(enumAsRef = true, description = "JSONPaymentRule: \n" +
		"* `Paypal` - Specifies that the order will have paymentRule = Paypal\n" +
		"* `OnCredit` - Specifies that the order will have paymentRule = On Credit\n" +
		"* `Cash` - Specifies that the order will have paymentRule = Cash\n" +
		"* `CreditCard` - Specifies that the order will have paymentRule = CreditCard\n" +
		"* `DirectDeposit` - Specifies that the order will have paymentRule = DirectDeposit\n" +
		"* `Check` - Specifies that the order will have paymentRule = Check\n" +
		"* `Mixed` - Specifies that the order will have paymentRule = Mixed\n" +
		"* `DirectDebit` - Specifies that the order will have paymentRule = Direct Debit\n" +
		"")
public enum JSONPaymentRule
{
	Paypal("L"),

	OnCredit("P"),

	Cash("B"),

	CreditCard("K"),

	DirectDeposit("T"),

	Check("S"),

	Mixed("M"),

	DirectDebit("D");

	@Getter
	private final String code;

	JSONPaymentRule(final String code)
	{
		this.code = code;
	}

	@Nullable
	public static JSONPaymentRule ofCodeOrNull(@Nullable final String code)
	{
		if(de.metas.common.util.Check.isBlank(code))
		{
			return null;
		}
		return ofCode(code);
	}
	
	@NonNull
	public static JSONPaymentRule ofCode(@NonNull final String code)
	{
		return Arrays.stream(values())
				.filter(type -> type.getCode().equals(code))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("No JSONPaymentRule found for code:" + code));
	}

}
