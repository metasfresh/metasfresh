package de.metas.money;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Currency;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

@UtilityClass
public class CurrencyIds
{
	/**
	 * Assumes that the given record has a {@link I_C_Currency#COLUMNNAME_C_Currency_ID} value and returns it as {@link CurrencyId}.
	 * Supposed to be helpful when working with legacy "record" code.
	 *
	 * @return never {@code null}
	 */
	public CurrencyId ofRecord(@NonNull final Object recordModel)
	{
		final Integer currencyRepoId = InterfaceWrapperHelper.getValueOrNull(recordModel, I_C_Currency.COLUMNNAME_C_Currency_ID);
		if (currencyRepoId == null)
		{
			throw new AdempiereException("Missing C_Currency_ID value in recordModel=" + recordModel);
		}
		return CurrencyId.ofRepoId(currencyRepoId);
	}
}
