package de.metas.costing;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_M_CostElement;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

public enum CostElementType implements ReferenceListAwareEnum
{
	Material(X_M_CostElement.COSTELEMENTTYPE_Material), //
	Overhead(X_M_CostElement.COSTELEMENTTYPE_Overhead), //
	BurdenMOverhead(X_M_CostElement.COSTELEMENTTYPE_BurdenMOverhead), //
	OutsideProcessing(X_M_CostElement.COSTELEMENTTYPE_OutsideProcessing), //
	Resource(X_M_CostElement.COSTELEMENTTYPE_Resource) //
	;

	// public static final int AD_REFERENCE_ID = X_M_CostElement.COSTELEMENTTYPE_AD_Reference_ID;

	@Getter
	private final String code;

	private CostElementType(final String code)
	{
		this.code = code;
	}

	public static CostElementType ofNullableCode(final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static CostElementType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	private static final ReferenceListAwareEnums.ValuesIndex<CostElementType> index = ReferenceListAwareEnums.index(values());

	public boolean isMaterial()
	{
		return this == Material;
	}

	public boolean isActivityControlElement()
	{
		return Resource.equals(this)
				|| Overhead.equals(this)
				|| BurdenMOverhead.equals(this);
	}
}
