package de.metas.shipper.gateway.spi.model;

import org.adempiere.util.Check;

import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.shipper.gateway.api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Value
public class CountryCode
{
	String alpha2;
	String alpha3;

	@Builder
	public CountryCode(final String alpha2, final String alpha3)
	{
		Check.assumeNotEmpty(alpha2, "alpha2 is not empty");
		Check.assumeNotEmpty(alpha3, "alpha3 is not empty");

		this.alpha2 = alpha2;
		this.alpha3 = alpha3;
	}

}
