package de.metas.vertical.pharma.msv3.server.stockAvailability;

import com.google.common.collect.ImmutableList;

import de.metas.vertical.pharma.msv3.server.types.PZN;
import de.metas.vertical.pharma.msv3.server.types.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.msv3.server
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
public class StockAvailabilityResponseItem
{
	PZN pzn;
	Quantity qty;

	/**
	 * If specified, ALL parts of the substitution article will be accepted (except for reason of substitution "suggestion").
	 * The solution excludes for complexity reasons that substitution for normal delivery may yield one share with and one share without substitution!
	 */
	StockAvailabilitySubstitution substitution;
	/** a maximum of 5 parts of the feedback whereby each type of type may only be used once. */
	ImmutableList<StockAvailabilityResponseItemPart> parts;

	@Builder
	private StockAvailabilityResponseItem(
			@NonNull final PZN pzn,
			@NonNull final Quantity qty,
			final StockAvailabilitySubstitution substitution,
			@NonNull @Singular final ImmutableList<StockAvailabilityResponseItemPart> parts)
	{
		this.pzn = pzn;
		this.qty = qty;
		this.substitution = substitution;
		this.parts = parts;

		// TODO: validate parts:
		// * max 5 are allowed (per protocol)
		// * Only one StockAvailabilityResponseItemPartType shall exist!
		// * sum of part's qtys shall be this.qty
	}
}
