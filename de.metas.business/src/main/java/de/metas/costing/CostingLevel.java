package de.metas.costing;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import org.adempiere.util.GuavaCollectors;
import org.compiere.model.X_C_AcctSchema;

import com.google.common.collect.ImmutableMap;

import lombok.Getter;

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

public enum CostingLevel
{
	Client(X_C_AcctSchema.COSTINGLEVEL_Client), //
	Organization(X_C_AcctSchema.COSTINGLEVEL_Organization), //
	BatchLot(X_C_AcctSchema.COSTINGLEVEL_BatchLot);

	@Getter
	private final String code;

	CostingLevel(final String code)
	{
		this.code = code;
	}

	public static CostingLevel forCode(final String code)
	{
		final CostingLevel type = code2type.get(code);
		if (type == null)
		{
			throw new NoSuchElementException("No " + CostingLevel.class + " found for code=" + code);
		}
		return type;
	}

	private static final ImmutableMap<String, CostingLevel> code2type = Stream.of(values())
			.collect(GuavaCollectors.toImmutableMapByKey(CostingLevel::getCode));
}
