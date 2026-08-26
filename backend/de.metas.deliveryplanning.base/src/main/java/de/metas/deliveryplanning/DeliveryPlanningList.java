/*
 * #%L
 * de.metas.deliveryplanning.base
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

package de.metas.deliveryplanning;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.AdMessageKey;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * An immutable selection of delivery plannings, loaded ONCE per invocation, with every selection predicate
 * the aggregation processes ask about answered against that in-memory list instead of its own query.
 * <p>
 * The predicates return WHICH rows are the odd ones out, not just how many - that is what the rejection
 * message, the grid highlight and the {@code doIt} assertion all need.
 */
@EqualsAndHashCode
@ToString
public class DeliveryPlanningList implements Iterable<DeliveryPlanning>
{
	/**
	 * Earliest planned departure first, the delivery planning id as the tie-break.
	 * <p>
	 * A planning without an ETD sorts last: it has no place in a departure order, and parking it behind the
	 * dated ones keeps the result reproducible rather than dependent on the query's encounter order - which is
	 * the whole point of sorting here.
	 */
	private static final Comparator<DeliveryPlanning> ALLOCATION_ORDER = Comparator
			.comparing(DeliveryPlanning::getEtd, Comparator.nullsLast(Comparator.<Instant>naturalOrder()))
			.thenComparingInt(deliveryPlanning -> deliveryPlanning.getId().getRepoId());

	public static final DeliveryPlanningList EMPTY = new DeliveryPlanningList(ImmutableList.of());

	private final ImmutableList<DeliveryPlanning> list;

	private DeliveryPlanningList(@NonNull final Collection<DeliveryPlanning> list)
	{
		this.list = list.stream()
				// so every derived message, allocation LineNo and printed line is reproducible
				.sorted(ALLOCATION_ORDER)
				.collect(ImmutableList.toImmutableList());
	}

	public static DeliveryPlanningList ofCollection(@NonNull final Collection<DeliveryPlanning> list)
	{
		return !list.isEmpty() ? new DeliveryPlanningList(list) : EMPTY;
	}

	public static DeliveryPlanningList of(final DeliveryPlanning... arr)
	{
		return ofCollection(ImmutableList.copyOf(arr));
	}

	public static Collector<DeliveryPlanning, ?, DeliveryPlanningList> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(DeliveryPlanningList::ofCollection);
	}

	public boolean isEmpty() {return list.isEmpty();}

	public int size() {return list.size();}

	public Stream<DeliveryPlanning> stream() {return list.stream();}

	@Override
	public @NonNull Iterator<DeliveryPlanning> iterator() {return list.iterator();}

	/**
	 * The ids in the order the plannings of one delivery instruction have to be allocated - and therefore
	 * numbered - in: earliest ETD, then planning id. The same selection always yields the same {@code LineNo}
	 * per planning, which the encounter order of a query would not.
	 *
	 * @see DeliveryPlanningRepository#createAllocations(de.metas.shipping.model.ShipperTransportationId, java.util.List)
	 */
	public ImmutableList<DeliveryPlanningId> getIdsInAllocationOrder()
	{
		return list.stream().map(DeliveryPlanning::getId).collect(ImmutableList.toImmutableList());
	}

	public boolean anyClosed() {return list.stream().anyMatch(DeliveryPlanning::isClosed);}

	/**
	 * Not the negation of {@link #anyClosed()}: a selection holding one open and one closed planning answers
	 * {@code true} to both.
	 */
	public boolean anyOpen() {return list.stream().anyMatch(deliveryPlanning -> !deliveryPlanning.isClosed());}

	public DeliveryPlanningList closedOnes() {return filter(DeliveryPlanning::isClosed);}

	public boolean anyAllocated() {return list.stream().anyMatch(DeliveryPlanning::isAllocated);}

	public DeliveryPlanningList allocatedOnes() {return filter(DeliveryPlanning::isAllocated);}

	public DeliveryPlanningList withoutShipper() {return filter(DeliveryPlanning::isWithoutShipper);}

	private DeliveryPlanningList filter(@NonNull final Predicate<DeliveryPlanning> predicate)
	{
		return list.stream().filter(predicate).collect(collect());
	}

	/**
	 * Every field on which this selection disagrees, so one message can name them all at once instead of
	 * reporting one field at a time. Empty means the selection can share a single delivery instruction.
	 * <p>
	 * The result is ordered by {@link AdmissibilityField} declaration order, so the message reads the same way
	 * every time.
	 */
	public ImmutableSet<AdmissibilityField> admissibilityMismatches()
	{
		return Arrays.stream(AdmissibilityField.values())
				.filter(this::isMismatch)
				.collect(ImmutableSet.toImmutableSet());
	}

	private boolean isMismatch(@NonNull final AdmissibilityField field)
	{
		// NULL counts as a value here, on purpose: SQL's count(DISTINCT col) ignores NULLs, which would make
		// "all rows have no forwarder" (admissible - they are all the same) indistinguishable from
		// "some rows have a forwarder and some do not" (a mismatch - they are not).
		final Set<Object> distinctValues = new HashSet<>();
		for (final DeliveryPlanning deliveryPlanning : list)
		{
			distinctValues.add(field.extractValue(deliveryPlanning));
			if (distinctValues.size() > 1)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Everything the delivery-instruction header can hold only one of, and which therefore has to match across
	 * the whole selection. Derived from the header columns the generation writes, so a column the header holds
	 * once joins the rule by construction.
	 */
	public enum AdmissibilityField
	{
		Organisation(DeliveryPlanning::getOrgId, "Organisation"),
		Direction(DeliveryPlanning::getType, "Direction"),
		Forwarder(DeliveryPlanning::getShipperId, "Forwarder"),
		Incoterms(DeliveryPlanning::getIncotermsId, "Incoterms"),
		IncotermLocation(DeliveryPlanning::getIncotermLocation, "IncotermLocation"),
		MeansOfTransportation(DeliveryPlanning::getMeansOfTransportationId, "MeansOfTransportation"),
		LoadingAddress(DeliveryPlanning::getLoadingLocationId, "LoadingAddress"),
		DeliveryAddress(DeliveryPlanning::getDeliveryLocationId, "DeliveryAddress");

		private static final String LABEL_PREFIX = "de.metas.deliveryplanning.CombineIntoDeliveryInstruction.Field.";

		private final Function<DeliveryPlanning, Object> valueExtractor;

		/**
		 * How this field is named in the message that rejects an inadmissible selection. Carried on the enum
		 * constant rather than in a lookup table beside it, so a field can never be added without a label and
		 * then silently drop out of a message whose whole job is to name every field at once.
		 * <p>
		 * The suffix is spelled out instead of taken from {@link #name()} on purpose: the key is an
		 * {@code AD_Message.Value} living in the database, so renaming a constant here must not move it.
		 */
		private final AdMessageKey label;

		AdmissibilityField(@NonNull final Function<DeliveryPlanning, Object> valueExtractor, @NonNull final String labelSuffix)
		{
			this.valueExtractor = valueExtractor;
			this.label = AdMessageKey.of(LABEL_PREFIX + labelSuffix);
		}

		@NonNull
		public AdMessageKey getLabel()
		{
			return label;
		}

		@Nullable
		Object extractValue(@NonNull final DeliveryPlanning deliveryPlanning)
		{
			return valueExtractor.apply(deliveryPlanning);
		}
	}
}
