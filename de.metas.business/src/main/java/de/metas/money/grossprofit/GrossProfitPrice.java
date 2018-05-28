package de.metas.money.grossprofit;

import java.util.List;

import org.adempiere.util.lang.ExtendedMemorizingSupplier;

import de.metas.money.Money;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

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

@Value
@Builder
public class GrossProfitPrice
{
	boolean soTrx;

	@NonNull
	Money basePrice;

	@Singular
	List<GrossProfitComponent> profitCompponents;

	ExtendedMemorizingSupplier<Money> value = ExtendedMemorizingSupplier.of(() -> computeProfitPrice0());

	public Money compute()
	{
		return value.get();
	}

	private Money computeProfitPrice0()
	{
		Money intermediateResult = basePrice;
		for (final GrossProfitComponent profitComponent : profitCompponents)
		{
			intermediateResult = profitComponent.applyToInput(intermediateResult);
		}
		return intermediateResult;
	}
}
