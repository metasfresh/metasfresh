package de.metas.common.ordercandidates.v1.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Schema(enumAsRef = true, description = "JSONPaymentRule: \n" +
		"* `Paypal` - Specifies that the order will have paymentRule = Paypal\n" +
		"* `OnCredit` - Specifies that the order will have paymentRule = On Credit\n" +
		"* `DirectDebit` - Specifies that the order will have paymentRule = Direct Debit\n" +
		"")
public enum JSONPaymentRule
{
	Paypal("L"),
	OnCredit("P"),
	DirectDebit("D");

	@Getter
	private final String code;

	JSONPaymentRule(final String code)
	{
		this.code = code;
	}

}
