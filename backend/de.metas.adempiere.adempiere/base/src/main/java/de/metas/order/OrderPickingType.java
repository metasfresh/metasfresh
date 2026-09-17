/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.compiere.model.X_C_Workplace;

import javax.annotation.Nullable;
import java.util.Objects;

@AllArgsConstructor
public enum OrderPickingType implements ReferenceListAwareEnum
{
	Single(X_C_Workplace.ORDERPICKINGTYPE_Single),
	Multiple(X_C_Workplace.ORDERPICKINGTYPE_Multiple);

	public static final int AD_REFERENCE_ID = X_C_Workplace.PRIORITYRULE_AD_Reference_ID;

	private static final ReferenceListAwareEnums.ValuesIndex<OrderPickingType> index = ReferenceListAwareEnums.index(values());

	private final String code;

	@JsonCreator
	public static OrderPickingType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static OrderPickingType ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	public static boolean equals(@Nullable final OrderPickingType o1, @Nullable final OrderPickingType o2) {return Objects.equals(o1, o2);}

	@JsonValue
	public String getCode()
	{
		return code;
	}
}
