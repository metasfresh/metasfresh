/*
 * #%L
 * de.metas.business
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

package de.metas.tax.api;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_Tax;

import javax.annotation.Nullable;
import java.util.Arrays;

public enum SOPOType implements ReferenceListAwareEnum
{
	SALES_TAX(X_C_Tax.SOPOTYPE_SalesTax),
	PURCHASE_TAX(X_C_Tax.SOPOTYPE_PurchaseTax),
	BOTH(X_C_Tax.SOPOTYPE_Both);
	private static final ImmutableMap<String, SOPOType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), SOPOType::getCode);
	@Getter
	private final String code;

	SOPOType(@NonNull final String code)
	{
		this.code = code;
	}

	@Nullable
	public static SOPOType ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static SOPOType ofCode(@NonNull final String code)
	{
		final SOPOType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + SOPOType.class + " found for code: " + code);
		}
		return type;
	}

	@Nullable
	public static String toCodeOrNull(final SOPOType type)
	{
		return type != null ? type.getCode() : null;
	}

}
