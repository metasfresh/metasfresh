package de.metas.handlingunits.shipmentschedule.picked_tu;

/*
 * #%L
 * de.metas.handlingunits.base
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

import com.google.common.collect.ImmutableList;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Hands out the content of a TU (its CUs grouped by attribute combination) to the picked rows of that TU, in the order the
 * rows are given: every overlap of a row and a group is one {@link Part}. Pure arithmetic, no services.
 * <p>
 * Rows are given to the groups in order, so when the picks of one lot are interleaved with picks of another
 * (lot A, lot B, lot A) a row can land on the other lot's group. The catch weight of a part therefore comes from the
 * CUs of the group it lands on, never from the row: the weight follows the physical CU, not the pick order.
 * <p>
 * An instance is one walk over the groups: give it the rows one after the other.
 */
public final class TUPickedRowsAllocator
{
	private final ImmutableList<TUContentGroup> groups;
	private final Function<TUContentCU, Quantity> weightOfCU;
	private final Quantity[] consumedByGroup;
	private final Map<TUContentCU, Quantity> weightCache = new IdentityHashMap<>();
	private int groupIndex = 0;

	/**
	 * @param weightOfCU the weight of a CU; {@code null} if the product has no catch weight or the CU has none
	 *                   (e.g. picked by count). Asked only for the CUs that a row with a quantity covers.
	 */
	public TUPickedRowsAllocator(
			@NonNull final List<TUContentGroup> groups,
			@NonNull final Function<TUContentCU, Quantity> weightOfCU)
	{
		this.groups = ImmutableList.copyOf(groups);
		this.weightOfCU = weightOfCU;
		this.consumedByGroup = new Quantity[this.groups.size()];
		for (int i = 0; i < consumedByGroup.length; i++)
		{
			consumedByGroup[i] = this.groups.get(i).totalQty().toZero();
		}
	}

	/**
	 * A picked row: its quantity in the stock UOM and its catch weight.
	 */
	@Value
	public static class Row
	{
		@NonNull Quantity qty;
		/** {@code null} if the row has no catch weight UOM */
		@Nullable Quantity catchWeight;
	}

	/**
	 * The part of a row's quantity that becomes one candidate.
	 */
	@Value
	public static class Part
	{
		@NonNull TUContentGroup group;
		@NonNull Quantity qty;
		/** the CU the quantity is exactly; {@code null} if it cannot be attributed to one CU */
		@Nullable TUContentCU wholeCU;
		/** the part's share of the row's catch weight; {@code null} if the row's weight stays as it is */
		@Nullable Quantity catchWeight;
	}

	/**
	 * @return the parts of the row, empty for a row without quantity
	 */
	public List<Part> allocate(@NonNull final Row row)
	{
		final List<Part> partsWithoutWeight = new ArrayList<>();
		final List<Quantity> cuWeights = new ArrayList<>();
		Quantity rowRemaining = row.getQty();
		while (rowRemaining.signum() > 0)
		{
			final TUContentGroup group = groups.get(groupIndex);
			final Quantity groupRemaining = group.totalQty().subtract(consumedByGroup[groupIndex]);
			final Quantity partQty = rowRemaining.min(groupRemaining);
			final TUContentCU wholeCU = wholeCUOrNull(partQty);
			final boolean isGroupUsedUp = groupRemaining.compareTo(partQty) == 0;
			cuWeights.add(consume(partQty));
			partsWithoutWeight.add(new Part(group, partQty, wholeCU, null));
			rowRemaining = rowRemaining.subtract(partQty);
			if (isGroupUsedUp)
			{
				groupIndex++;
			}
		}

		final List<Quantity> catchWeights = catchWeightsOfParts(row, partsWithoutWeight, cuWeights);
		final ImmutableList.Builder<Part> parts = ImmutableList.builder();
		for (int i = 0; i < partsWithoutWeight.size(); i++)
		{
			final Part part = partsWithoutWeight.get(i);
			parts.add(new Part(part.getGroup(), part.getQty(), part.getWholeCU(), catchWeights.get(i)));
		}
		return parts.build();
	}

	/**
	 * @return the CU only if the current group has exactly one CU and the {@code partQty} are all of it, else null
	 */
	@Nullable
	private TUContentCU wholeCUOrNull(@NonNull final Quantity partQty)
	{
		final TUContentGroup group = groups.get(groupIndex);
		if (group.getCus().size() != 1 || !consumedByGroup[groupIndex].isZero())
		{
			return null;
		}
		final TUContentCU cu = group.getCus().get(0);
		return cu.getQty().compareTo(partQty) == 0 ? cu : null;
	}

	/**
	 * Hands out the next {@code qty} of the current group.
	 *
	 * @return the weight of that part of the group: the weights of the CUs it covers, a CU that is covered in part counted
	 * pro rata; null if one of the covered CUs has no weight
	 */
	@Nullable
	private Quantity consume(@NonNull final Quantity qty)
	{
		final Quantity before = consumedByGroup[groupIndex];
		final Quantity now = before.add(qty);
		consumedByGroup[groupIndex] = now;
		final Quantity weightUpToBefore = weightOfFirst(before);
		final Quantity weightUpToNow = weightOfFirst(now);
		return weightUpToBefore != null && weightUpToNow != null ? weightUpToNow.subtract(weightUpToBefore) : null;
	}

	/**
	 * @return the weight of the first {@code qty} of the current group, going through its CUs in order; null if a CU has no weight
	 */
	@Nullable
	private Quantity weightOfFirst(@NonNull final Quantity qty)
	{
		final TUContentGroup group = groups.get(groupIndex);
		Quantity cuStart = qty.toZero();
		Quantity weight = null;
		for (final TUContentCU cu : group.getCus())
		{
			final Quantity cuEnd = cuStart.add(cu.getQty());
			final Quantity taken = qty.min(cuEnd).subtract(cuStart);
			if (taken.signum() > 0)
			{
				final Quantity cuWeight = weightOf(cu);
				if (cuWeight == null)
				{
					return null;
				}
				final Quantity cuWeightTaken = taken.compareTo(cu.getQty()) == 0
						? cuWeight
						: cuWeight.multiply(taken.toBigDecimal()).divide(cu.getQty().toBigDecimal(), cuWeight.getUOMPrecision().toInt(), RoundingMode.HALF_UP);
				weight = weight == null ? cuWeightTaken : weight.add(cuWeightTaken);
			}
			cuStart = cuEnd;
		}
		if (weight == null) // nothing taken: a zero weight in the UOM of the group's weights
		{
			final Quantity firstCUWeight = weightOf(group.getCus().get(0));
			return firstCUWeight == null ? null : firstCUWeight.toZero();
		}
		return weight;
	}

	@Nullable
	private Quantity weightOf(@NonNull final TUContentCU cu)
	{
		if (!weightCache.containsKey(cu))
		{
			weightCache.put(cu, weightOfCU.apply(cu));
		}
		return weightCache.get(cu);
	}

	/**
	 * @return per part the weight of the CUs it stands for, if every part has it in the row's catch UOM; else the row's own weight
	 * split by quantity (or null, if the row stays one part).
	 * The choice is made per row: a lot's line can mix both sources if only some of its CUs have a net weight. The CU's net weight
	 * is the weight of the pick that made the CU (mobile picking sets it per picked CU); it is assumed that no pick is merged into an
	 * existing CU of the same lot.
	 */
	private static List<Quantity> catchWeightsOfParts(
			@NonNull final Row row,
			@NonNull final List<Part> parts,
			@NonNull final List<Quantity> cuWeights)
	{
		final Quantity rowWeight = row.getCatchWeight();
		if (rowWeight == null)
		{
			return nulls(parts.size());
		}
		final boolean isWeightOfEveryPartKnown = cuWeights.stream()
				.allMatch(weight -> weight != null && weight.getUomId().equals(rowWeight.getUomId()));
		if (isWeightOfEveryPartKnown)
		{
			return cuWeights;
		}
		return parts.size() > 1
				? splitByQty(rowWeight, parts.stream().map(Part::getQty).collect(ImmutableList.toImmutableList()))
				: nulls(parts.size());
	}

	private static List<Quantity> nulls(final int size)
	{
		final List<Quantity> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++)
		{
			list.add(null);
		}
		return list;
	}

	/**
	 * Splits a weight across the given quantities proportionally to them.
	 * The last one gets the remainder, so the shares always sum up to the weight.
	 *
	 * @return one share per quantity
	 */
	public static List<Quantity> splitByQty(@NonNull final Quantity totalWeight, @NonNull final List<Quantity> qtys)
	{
		final BigDecimal totalQty = qtys.stream()
				.map(Quantity::getAsBigDecimal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		final int scale = totalWeight.getUOMPrecision().toInt();

		final List<Quantity> shares = new ArrayList<>(qtys.size());
		Quantity allocated = totalWeight.toZero();
		for (int i = 0; i < qtys.size(); i++)
		{
			final Quantity share = i < qtys.size() - 1
					? totalWeight.multiply(qtys.get(i).toBigDecimal()).divide(totalQty, scale, RoundingMode.HALF_UP)
					: totalWeight.subtract(allocated);
			allocated = allocated.add(share);
			shares.add(share);
		}
		return shares;
	}
}

