/*
 * #%L
 * de.metas.payment.revolut
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

package de.metas.payment.revolut.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RevolutExportCSVColumn
{
	Name("Name"),
	RecipientType("Recipient type"),
	AccountNo("Account number"),
	RoutingNo("Sort code or Routing number"),
	IBAN("IBAN"),
	SwiftCode("BIC"),
	RecipientBankCountryName("Recipient bank country"),
	Currency("Currency"),
	Amount("Amount"),
	PaymentReference("Payment reference"),
	RecipientCountryName("Recipient country"),
	RegionName("State or province"),
	AddressLine1("Address line 1"),
	AddressLine2("Address line 2"),
	City("City"),
	PostalCode("Postal code"),
	;

	private final String value;
}
