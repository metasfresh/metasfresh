/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.rest_api.v2.project;

import de.metas.money.CurrencyId;
import de.metas.pricing.PriceListVersionId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_PriceList;

import javax.annotation.Nullable;
import java.util.function.Function;

@UtilityClass
public class ValidateProjectHelper
{
	public static void assertCurrencyIdsMatch(
			@NonNull final CurrencyId currencyId,
			@Nullable final PriceListVersionId priceListVersionId,
			@NonNull final Function<PriceListVersionId, I_M_PriceList> priceListLoader)
	{
		if (priceListVersionId == null)
		{
			return;
		}

		final I_M_PriceList priceListRecord = priceListLoader.apply(priceListVersionId);

		if (priceListRecord == null)
		{
			return;
		}

		final CurrencyId priceListCurrencyId = CurrencyId.ofRepoId(priceListRecord.getC_Currency_ID());

		if (currencyId.getRepoId() != priceListCurrencyId.getRepoId())
		{
			throw new AdempiereException("Project currency does not match the price list's currency!")
					.appendParametersToMessage()
					.setParameter("Project.CurrencyId", currencyId)
					.setParameter("PriceList.CurrencyId", priceListCurrencyId);
		}
	}
}
