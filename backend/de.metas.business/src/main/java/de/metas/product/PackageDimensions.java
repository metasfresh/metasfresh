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
import java.util.Collections;
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

	/**
	 * Compute TU package dimensions from a list of packed items using the given calculation mode.
	 *
	 * <p>If any item carries {@link #UNSPECIFIED} dimensions the result is {@link #UNSPECIFIED}.</p>
	 *
	 * @param mode  one of {@link PackageDimensionCalcMethod#Strapping}, {@link PackageDimensionCalcMethod#Repacking},
	 *              or {@link PackageDimensionCalcMethod#Nesting}
	 * @param items the items packed into the TU (each with per-unit dims and a quantity)
	 */
	public static PackageDimensions ofItems(
			@NonNull final PackageDimensionCalcMethod mode,
			@NonNull final List<PackageDimensionItem> items)
	{
		if (items.isEmpty())
		{
			return UNSPECIFIED;
		}

		// Guard: if any item has unspecified dims, the whole TU has no dims
		for (final PackageDimensionItem item : items)
		{
			if (item.getDims().isUnspecified())
			{
				return UNSPECIFIED;
			}
		}

		switch (mode)
		{
			case Strapping:
				return ofItemsStrapping(items);
			case Repacking:
				return ofItemsRepacking(items);
			case Nesting:
				return ofItemsNesting(items);
			default:
				throw new IllegalArgumentException("Unknown PackageDimensionCalcMethod: " + mode);
		}
	}

	/**
	 * Strapping: items are strapped together end-to-end along their smallest edge.
	 *
	 * <ul>
	 *   <li>Stacking axis (stored in {@code lengthInCM}) = Σ( min_edge(item) × qty(item) )</li>
	 *   <li>Mid edge ({@code heightInCM}) = max( mid_edge(item) ) across all items</li>
	 *   <li>Max edge ({@code widthInCM})  = max( max_edge(item) ) across all items</li>
	 * </ul>
	 *
	 * <p>For a single item this is equivalent to {@link #ofProductDimensionsAndQty}.</p>
	 */
	static PackageDimensions ofItemsStrapping(@NonNull final List<PackageDimensionItem> items)
	{
		int stackingAxisTotal = 0;
		int maxMidEdge = 0;
		int maxMaxEdge = 0;

		for (final PackageDimensionItem item : items)
		{
			final int qty = item.getQty().toBigDecimal().setScale(0, RoundingMode.CEILING).intValue();
			final List<Integer> sorted = sortedEdges(item.getDims());
			stackingAxisTotal += sorted.get(0) * qty;
			maxMidEdge = Math.max(maxMidEdge, sorted.get(1));
			maxMaxEdge = Math.max(maxMaxEdge, sorted.get(2));
		}

		return PackageDimensions.builder()
				.lengthInCM(stackingAxisTotal)
				.heightInCM(maxMidEdge)
				.widthInCM(maxMaxEdge)
				.build();
	}

	/**
	 * Repacking: items are repacked into a box.
	 *
	 * <ul>
	 *   <li>V = Σ( L × W × H × qty ) × 1.05</li>
	 *   <li>height = ⅔ × V^(1/3)</li>
	 *   <li>width  = ⅗ × √(V / height)</li>
	 *   <li>length = (V / height) / width</li>
	 * </ul>
	 *
	 * <p>All three edge values are rounded to the nearest integer cm.</p>
	 */
	static PackageDimensions ofItemsRepacking(@NonNull final List<PackageDimensionItem> items)
	{
		double rawVolume = 0.0;
		for (final PackageDimensionItem item : items)
		{
			final int qty = item.getQty().toBigDecimal().setScale(0, RoundingMode.CEILING).intValue();
			final PackageDimensions d = item.getDims();
			rawVolume += (double)d.getLengthInCM() * d.getWidthInCM() * d.getHeightInCM() * qty;
		}
		final double v = rawVolume * 1.05;

		final int height = (int)Math.round((2.0 / 3.0) * Math.cbrt(v));
		final int width = height > 0
				? (int)Math.round((3.0 / 5.0) * Math.sqrt(v / height))
				: 1;
		final int length = (height > 0 && width > 0)
				? (int)Math.round((v / height) / width)
				: 1;

		return PackageDimensions.builder()
				.lengthInCM(length)
				.widthInCM(width)
				.heightInCM(height)
				.build();
	}

	/**
	 * Nesting: the TU takes the dimensions of the item whose single largest edge is greatest.
	 *
	 * <p>Quantity is ignored for the comparison — only the edge size matters.</p>
	 */
	static PackageDimensions ofItemsNesting(@NonNull final List<PackageDimensionItem> items)
	{
		PackageDimensions winner = null;
		int winnerMaxEdge = Integer.MIN_VALUE;

		for (final PackageDimensionItem item : items)
		{
			final int maxEdge = Collections.max(
					sortedEdges(item.getDims()));
			if (maxEdge > winnerMaxEdge)
			{
				winnerMaxEdge = maxEdge;
				winner = item.getDims();
			}
		}

		return winner != null ? winner : UNSPECIFIED;
	}

	/**
	 * Returns the three edge values of the given dims sorted ascending: [min, mid, max].
	 */
	private static List<Integer> sortedEdges(@NonNull final PackageDimensions dims)
	{
		final List<Integer> edges = new ArrayList<>();
		edges.add(dims.getLengthInCM());
		edges.add(dims.getWidthInCM());
		edges.add(dims.getHeightInCM());
		edges.sort(null);
		return edges;
	}
}
