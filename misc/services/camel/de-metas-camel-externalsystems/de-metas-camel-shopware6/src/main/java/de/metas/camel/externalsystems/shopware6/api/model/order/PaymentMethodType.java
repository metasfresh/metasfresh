/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6.api.model.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum PaymentMethodType
{
	CASH_PAYMENT("cash_payment"),
	PRE_PAYMENT("pre_payment"),
	INVOICE_PAYMENT("invoice_payment"),
	DEBIT_PAYMENT("debit_payment"),
	PAY_PAL_PAYMENT_HANDLER("pay_pal_payment_handler"),
	PAY_PAL_PUI_PAYMENT_HANDLER("pay_pal_pui_payment_handler");

	private final String value;

	@NonNull
	public static PaymentMethodType ofValue(@NonNull final String value)
	{
		return Arrays.stream(values())
				.filter(type -> type.getValue().equals(value))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("No PaymentMethodType found for value:" + value));
	}
}
