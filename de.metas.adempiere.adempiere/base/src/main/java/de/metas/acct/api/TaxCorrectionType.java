package de.metas.acct.api;

import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_AcctSchema;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public enum TaxCorrectionType
{
	NONE(X_C_AcctSchema.TAXCORRECTIONTYPE_None), //
	WRITEOFF_ONLY(X_C_AcctSchema.TAXCORRECTIONTYPE_Write_OffOnly), //
	DISCOUNT_ONLY(X_C_AcctSchema.TAXCORRECTIONTYPE_DiscountOnly), //
	WRITEOFF_AND_DISCOUNT(X_C_AcctSchema.TAXCORRECTIONTYPE_Write_OffAndDiscount) //
	;

	@Getter
	private final String code;

	TaxCorrectionType(@NonNull final String code)
	{
		this.code = code;
	}

	public static TaxCorrectionType ofCode(@NonNull final String code)
	{
		final TaxCorrectionType type = ofCodeOrNull(code);
		if (type == null)
		{
			throw new AdempiereException("No " + TaxCorrectionType.class + " found for code: " + code);
		}
		return type;
	}

	public static TaxCorrectionType ofCodeOrNull(@NonNull final String code)
	{
		return typesByCode.get(code);
	}

	private static final ImmutableMap<String, TaxCorrectionType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), TaxCorrectionType::getCode);

	public boolean isNone()
	{
		return this == NONE;
	}
	
	public boolean isDiscount()
	{
		return this == DISCOUNT_ONLY || this == WRITEOFF_AND_DISCOUNT; 
	}
	
	public boolean isWriteOff()
	{
		return this == WRITEOFF_ONLY || this == WRITEOFF_AND_DISCOUNT; 
	}
}
