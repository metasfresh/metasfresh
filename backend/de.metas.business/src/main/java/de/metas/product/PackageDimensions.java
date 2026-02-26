/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Builder
@Value
public class PackageDimensions
{

	private static final int UNSPECIFIED_DIMENSION = -1;
	public static final PackageDimensions UNSPECIFIED = new PackageDimensions(UNSPECIFIED_DIMENSION, UNSPECIFIED_DIMENSION, UNSPECIFIED_DIMENSION);

	int lengthInCM;
	int widthInCM;
	int heightInCM;

	/**
	 * Note: dimensionsInCM may be <= 0 which can stand for "not specified".
	 */
	@Builder
	@Jacksonized
	private PackageDimensions(final int lengthInCM, final int widthInCM, final int heightInCM)
	{
		this.lengthInCM = lengthInCM;
		this.widthInCM = widthInCM;
		this.heightInCM = heightInCM;
	}

	@JsonIgnore
	public boolean isUnspecified()
	{
		return UNSPECIFIED.equals(this);
	}

	public static PackageDimensions ofProductDimensionsAndQty(@NonNull final PackageDimensions packageDimensions, @NonNull final Quantity qtyInStockingUOM)
	{
		final List<Integer> dimensions = new ArrayList<>();
		dimensions.add(packageDimensions.getHeightInCM());
		dimensions.add(packageDimensions.getWidthInCM());
		dimensions.add(packageDimensions.getLengthInCM());
		dimensions.sort(null);

		final int qtyRoundedUpInStockUOM = qtyInStockingUOM.toBigDecimal().setScale(0, RoundingMode.CEILING).intValue();
		return PackageDimensions.builder()
				.lengthInCM(dimensions.get(0) * qtyRoundedUpInStockUOM)
				.heightInCM(dimensions.get(1))
				.widthInCM(dimensions.get(2))
				.build();
	}
}
