/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.material.planning;

import lombok.NonNull;

import java.util.function.Predicate;

/**
 * Classifies a {@link ProductPlanning} row by the kind of supply it declares.
 *
 * A single {@link ProductPlanning} may match more than one usage (e.g. a row with
 * {@code isManufactured=Y} <i>and</i> {@code DD_NetworkDistribution_ID} set matches both
 * {@link #MANUFACTURING} and {@link #DISTRIBUTION}). That is intentional and mirrors
 * the pre-existing behavior where all matching advisors fire from the same row.
 */
public enum PlanningUsage
{
	MANUFACTURING(pp -> pp.isManufactured() && !pp.isPickingOrder()),
	DISTRIBUTION(pp -> pp.getDistributionNetworkId() != null),
	PURCHASING(ProductPlanning::isPurchased);

	@NonNull private final Predicate<ProductPlanning> predicate;

	PlanningUsage(@NonNull final Predicate<ProductPlanning> predicate)
	{
		this.predicate = predicate;
	}

	public boolean matches(@NonNull final ProductPlanning productPlanning)
	{
		return predicate.test(productPlanning);
	}
}
