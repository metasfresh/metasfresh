/*
 * #%L
 * de-metas-common-pricing
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

package de.metas.common.pricing.v2.productprice;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.pentabyte.springfox.ApiEnum;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;

public enum TaxCategory
{
	@ApiEnum("Specifies in which type the tax category is. The internal name translates `C_TaxCategory.InternalName`.")
	NORMAL("Normal"),
	REDUCED("Reduced"),
	TAXFREE("TaxFree");

	@Getter
	private final String internalName;

	TaxCategory(final String internalName)
	{
		this.internalName = internalName;
	}
	
	private static final ImmutableMap<String, TaxCategory> typesByInternalName = Maps.uniqueIndex(Arrays.asList(values()), TaxCategory::getInternalName);

	public static TaxCategory ofInternalName(@NonNull final String internalName)
	{
		final TaxCategory type = typesByInternalName.get(internalName);
		if (type == null)
		{
			throw new RuntimeException("No " + TaxCategory.class + " found for internalName: " + internalName);
		}
		return type;
	}
}
