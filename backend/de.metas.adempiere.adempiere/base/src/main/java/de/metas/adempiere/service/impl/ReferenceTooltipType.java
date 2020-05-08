/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.adempiere.service.impl;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_AD_Table;

/**
 * This controls what Tooltip value is used for Lists
 */
// TODO tbp: introduce this reflist to ad_table
public enum ReferenceTooltipType implements ReferenceListAwareEnum
{
	/**
	 * Tooltip shows nothing
	 */
	None(X_AD_Table.ACCESSLEVEL_All), //  // TODO tbp: introduce the ref list to ad table

	/**
	 * Tooltip shows Description field if it exists. Else nothing.
	 */
	Description("Description"), //  // TODO tbp: introduce the ref list to ad table

	/**
	 * Tooltip shows the table identifier.
	 */
	TableIdentifier("TableIdentifier"), //  // TODO tbp: introduce the ref list to ad table

	/**
	 * Tooltip shows the Description field if it is not null, else the table identifier.
	 */
	DescriptionFallbackToTableIdentifier("DescriptionFallbackToTableIdentifier"); //  // TODO tbp: introduce the ref list to ad table

	@Getter
	private final String code;

	ReferenceTooltipType(final String code)
	{
		this.code = code;
	}

	public static ReferenceTooltipType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public static ReferenceTooltipType ofNullableCode(final String code)
	{
		return index.ofNullableCode(code);
	}

	private static final ReferenceListAwareEnums.ValuesIndex<ReferenceTooltipType> index = ReferenceListAwareEnums.index(values());
}
