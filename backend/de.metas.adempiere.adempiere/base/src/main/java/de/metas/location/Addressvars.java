/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.location;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Addressvars
{
	BPartner("BP"),

	Contact("CON"),

	BPartnerName("BP_Name"),

	BPartnerGreeting("BP_GR"),

	City("C"),

	Region("R"),

	Country("CO"),

	Postal_Add("A"),

	Postal("P"),

	POBox("PB"),

	Address1("A1"),

	Address2("A2"),

	Address3("A3"),

	Address4("A4");

	@NonNull private final String name;
}
