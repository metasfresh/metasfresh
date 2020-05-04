/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.client.schema;

public enum JsonProductPackageState
{
	ACTIVE, //
	FRAUD, //
	UNKNOWN //
	;

	public String toYesNoString()
	{
		if (this == ACTIVE)
		{
			return "Y";
		}
		else if (this == FRAUD)
		{
			return "N";
		}
		else
		{
			return null;
		}
	}

	public static JsonProductPackageState ofYesNoString(final String yesNoString)
	{
		if ("Y".equals(yesNoString))
		{
			return ACTIVE;
		}
		else if ("N".equals(yesNoString))
		{
			return FRAUD;
		}
		else
		{
			return UNKNOWN;
		}
	}

}
