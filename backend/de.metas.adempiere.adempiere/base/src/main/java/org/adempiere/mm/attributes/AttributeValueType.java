package org.adempiere.mm.attributes;

import org.compiere.model.X_M_Attribute;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public enum AttributeValueType implements ReferenceListAwareEnum
{
	STRING(X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40), //
	NUMBER(X_M_Attribute.ATTRIBUTEVALUETYPE_Number), //
	DATE(X_M_Attribute.ATTRIBUTEVALUETYPE_Date), //
	LIST(X_M_Attribute.ATTRIBUTEVALUETYPE_List) //
	;

	@Getter
	private final String code;

	AttributeValueType(final String code)
	{
		this.code = code;
	}

	public static AttributeValueType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	private static final ValuesIndex<AttributeValueType> index = ReferenceListAwareEnums.index(values());
}
