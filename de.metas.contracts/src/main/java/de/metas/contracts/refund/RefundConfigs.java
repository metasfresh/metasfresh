package de.metas.contracts.refund;

import java.util.Comparator;
import java.util.List;

import org.adempiere.util.Check;

import com.google.common.collect.ImmutableList;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.contracts
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

@UtilityClass
public class RefundConfigs
{
	public ImmutableList<RefundConfig> sortByMinQtyAsc(@NonNull final List<RefundConfig> refundConfigs)
	{
		// we need to look at the lowest minQty first, in order to "fill" it; only the "biggest" config is does not have the next config's minQty as ceiling
		final ImmutableList<RefundConfig> sortedConfigs = refundConfigs
				.stream()
				.sorted(Comparator.comparing(RefundConfig::getMinQty))
				.collect(ImmutableList.toImmutableList());
		return sortedConfigs;
	}

	public RefundConfig largestMinQty(@NonNull final List<RefundConfig> refundConfigs)
	{
		Check.assumeNotEmpty(refundConfigs, "The given refundConfigs may not be empty");

		return refundConfigs
				.stream()
				.max(Comparator.comparing(RefundConfig::getMinQty))
				.get();
	}
}
