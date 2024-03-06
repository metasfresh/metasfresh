package de.metas.invoice_gateway.spi.model;

import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-invoice_gateway.spi
 * %%
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

@Value
public class GLN
{
	public static GLN of(@NonNull final String value)
	{
		return new GLN(value);
	}

	String value;

	private GLN(@NonNull final String value)
	{
		Check.assumeNotEmpty(value, "The given GLN value may not be empty");

		this.value = value.replaceAll(" ", "");
		Check.assume(this.value.matches("[0-9]{13}"), "A GLN value needs to consist of 13 digits; given value={}", value);
	}
}
