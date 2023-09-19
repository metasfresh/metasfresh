/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.externalsystem.shopware6;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public enum OrderProcessingConfig implements ReferenceListAwareEnum
{
	NONE("N"),
	ORDER("O"),
	SHIPPING("S"),
	INVOICE("I");

	private final String code;
	private static final ReferenceListAwareEnums.ValuesIndex<OrderProcessingConfig> typesByCode = ReferenceListAwareEnums.index(values());

	@NonNull
	public static OrderProcessingConfig ofCode(@NonNull final String code)
	{
		return typesByCode.ofCode(code);
	}
}
