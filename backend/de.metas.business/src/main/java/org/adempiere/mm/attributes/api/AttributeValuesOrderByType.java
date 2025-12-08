/*
 * #%L
 * de.metas.business
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

package org.adempiere.mm.attributes.api;

import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_M_Attribute;

import javax.annotation.Nullable;

@RequiredArgsConstructor
public enum AttributeValuesOrderByType implements ReferenceListAwareEnum
{
	Value(X_M_Attribute.ATTRIBUTEVALUESORDERBY_Suchschluessel),
	Name(X_M_Attribute.ATTRIBUTEVALUESORDERBY_Name);

	@NonNull private static final ReferenceListAwareEnums.ValuesIndex<AttributeValuesOrderByType> index = ReferenceListAwareEnums.index(values());

	@NonNull @Getter private final String code;

	@NonNull
	public static AttributeValuesOrderByType ofCode(@NonNull final String code) {return index.ofCode(code);}

	@Nullable
	public static AttributeValuesOrderByType ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	@JsonValue
	public String toJson() {return getCode();}
}
