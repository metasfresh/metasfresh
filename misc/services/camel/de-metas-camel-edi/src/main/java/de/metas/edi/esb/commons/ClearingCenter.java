/*
 * #%L
 * de-metas-edi-esb-camel
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.edi.esb.commons;

import de.metas.common.util.Check;
import lombok.NonNull;

import javax.annotation.Nullable;

public enum ClearingCenter
{
	CompuData,
	MetasfreshInHouseV1,
	MetasfreshInHouseV2,
	STEPcom,
	Edifact
	;

	@NonNull
	public static ClearingCenter ofValue(@Nullable final String value)
	{
		if (Check.isBlank(value) || "ecosio".equals(value))
		{
			return MetasfreshInHouseV2;
		}
		
		return valueOf(value);
	}
}
