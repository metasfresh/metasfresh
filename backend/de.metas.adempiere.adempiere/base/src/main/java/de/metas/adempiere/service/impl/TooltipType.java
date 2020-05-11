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
public enum TooltipType implements ReferenceListAwareEnum
{
	/**
	 * Tooltip shows Description field if it exists. Else nothing.
	 */
	Description(X_AD_Table.TOOLTIPTYPE_Description),

	/**
	 * Tooltip shows the table identifier.
	 */
	TableIdentifier(X_AD_Table.TOOLTIPTYPE_TableIdentifier),

	/**
	 * Tooltip shows the Description field if it is not null, else the table identifier.
	 */
	DescriptionFallbackToTableIdentifier(X_AD_Table.TOOLTIPTYPE_DescriptionFallbackToTableIdentifier);

	public static final TooltipType DEFAULT = DescriptionFallbackToTableIdentifier;

	@Getter
	private final String code;

	TooltipType(final String code)
	{
		this.code = code;
	}

	public static TooltipType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public static TooltipType ofNullableCode(final String code)
	{
		return index.ofNullableCode(code);
	}

	private static final ReferenceListAwareEnums.ValuesIndex<TooltipType> index = ReferenceListAwareEnums.index(values());
}
