package de.metas.acct.api;

import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_Fact_Acct;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.acct.base
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

public enum PostingType
{
	Actual(X_Fact_Acct.POSTINGTYPE_Actual), //
	Budget(X_Fact_Acct.POSTINGTYPE_Budget), //
	Commitment(X_Fact_Acct.POSTINGTYPE_Commitment), //
	Reservation(X_Fact_Acct.POSTINGTYPE_Reservation), //
	Statistical(X_Fact_Acct.POSTINGTYPE_Statistical), //
	ActualYearEnd(X_Fact_Acct.POSTINGTYPE_ActualYearEnd) //
	;

	@Getter
	private final String code;

	PostingType(final String code)
	{
		this.code = code;
	}

	public static PostingType ofCode(@NonNull final String code)
	{
		PostingType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + PostingType.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, PostingType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), PostingType::getCode);
}
