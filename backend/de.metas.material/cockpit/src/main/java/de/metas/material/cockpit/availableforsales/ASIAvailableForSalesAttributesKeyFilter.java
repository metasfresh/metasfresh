/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.material.cockpit.availableforsales;

import com.google.common.base.MoreObjects;
import de.metas.material.event.commons.AttributesKey;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.mm.attributes.keys.AttributesKeyPattern;
import org.compiere.model.I_MD_Available_For_Sales;

import javax.annotation.Nullable;

public final class ASIAvailableForSalesAttributesKeyFilter implements IQueryFilter<I_MD_Available_For_Sales>
{
	public static ASIAvailableForSalesAttributesKeyFilter matchingAttributes(@NonNull final AttributesKeyPattern attributesKeyPattern)
	{
		return new ASIAvailableForSalesAttributesKeyFilter(attributesKeyPattern);
	}

	private final AttributesKeyPattern attributesKeyPattern;

	private ASIAvailableForSalesAttributesKeyFilter(@NonNull final AttributesKeyPattern attributesKeyPattern)
	{
		this.attributesKeyPattern = attributesKeyPattern;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).addValue(attributesKeyPattern).toString();
	}

	@Override
	public boolean accept(@Nullable final I_MD_Available_For_Sales availableForSales)
	{
		// Guard against null, shall not happen
		if (availableForSales == null)
		{
			return false;
		}

		final AttributesKey expectedAttributesKey = AttributesKey.ofString(availableForSales.getStorageAttributesKey());

		return attributesKeyPattern.matches(expectedAttributesKey);
	}
}