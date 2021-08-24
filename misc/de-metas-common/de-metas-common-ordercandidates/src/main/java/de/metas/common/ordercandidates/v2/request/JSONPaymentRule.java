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

import de.pentabyte.springfox.ApiEnum;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;

public enum JSONPaymentRule
{

	@ApiEnum("Specifies that the order will have paymentRule = Paypal")
	Paypal("L"),

	@ApiEnum("Specifies that the order will have paymentRule = On Credit")
	OnCredit("P"),

	@ApiEnum("Specifies that the order will have paymentRule = Cash")
	Cash("B"),

	@ApiEnum("Specifies that the order will have paymentRule = CreditCard")
	CreditCard("K"),

	@ApiEnum("Specifies that the order will have paymentRule = DirectDeposit")
	DirectDeposit("T"),

	@ApiEnum("Specifies that the order will have paymentRule = Check")
	Check("S"),

	@ApiEnum("Specifies that the order will have paymentRule = Mixed")
	Mixed("M"),

	@ApiEnum("Specifies that the order will have paymentRule = Direct Debit")
	DirectDebit("D");

	@Getter
	private final String code;

	JSONPaymentRule(final String code)
	{
		this.code = code;
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
