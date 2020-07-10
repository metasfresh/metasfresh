package de.metas.vertical.creditscore.creditpass.model;

/*
 * #%L
 * de.metas.vertical.creditscore.creditpass.model
 *
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

import de.metas.vertical.creditscore.base.spi.model.TransactionData;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class CreditPassTransactionData implements TransactionData
{

	private String firstName;

	private String lastName;

	private LocalDate dateOfBirth;

	private String gender;

	private String streetFull;

	private String zip;

	private String city;

	private String country;

	private String bankRoutingCode;

	private String accountNr;

	private String iban;

	private String creditCardNr;

	private String creditCardType;

	private String email;

	private String phoneNr;

	private String companyName;
}
