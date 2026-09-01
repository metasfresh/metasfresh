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
import de.metas.shipping.TransportDirection;
import de.metas.util.GuavaCollectors;
import de.metas.util.lang.RepoIdAware;
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
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * An immutable selection of delivery plannings, loaded once per invocation, with every selection predicate the
 * aggregation processes ask about answered against that in-memory list rather than its own query. The predicates
 * return WHICH rows are the odd ones out, not just how many.
 */
@EqualsAndHashCode
@ToString
public class DeliveryPlanningList implements Iterable<DeliveryPlanning>
{
	/** Earliest planned departure first, planning id as tie-break; a planning without an ETD sorts last. */
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
	 * The ids in the order the plannings of one delivery instruction are allocated - and therefore numbered - in,
	 * so the same selection always yields the same {@code LineNo} per planning.
	 */
	public ImmutableList<DeliveryPlanningId> getIdsInAllocationOrder()
	{
		return list.stream().map(DeliveryPlanning::getId).collect(ImmutableList.toImmutableList());
	}

	/**
	 * The one transport direction the whole selection shares, or empty when it spans more than one or is empty -
	 * the {@link AggregationKeyField#Direction} mismatch as a value rather than a flag, for callers that have to
	 * correlate on the direction itself.
	 */
	public Optional<TransportDirection> getSingleTransportDirection()
	{
		return list.stream()
				.map(DeliveryPlanning::getTransportDirection)
				.distinct()
				.count() == 1
				? Optional.of(list.get(0).getTransportDirection())
				: Optional.empty();
	}

	/**
	 * The one value the whole selection carries for the given key field. Empty in all three cases the caller has
	 * to treat alike - the selection is empty, it disagrees with itself on this field, or the one value it agrees
	 * on is {@code null} (a field none of the plannings has set).
	 */
	public Optional<Object> getSingleAggregationKeyValue(@NonNull final AggregationKeyField field)
	{
		return list.stream()
				.map(field::extractValue)
				.distinct()
				.count() == 1
				? Optional.ofNullable(field.extractValue(list.get(0)))
				: Optional.empty();
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

	/**
	 * The complement of {@link #allocatedOnes()}: what Move refuses, as Add refuses the allocated ones, so the two
	 * actions' preconditions partition every selection between them.
	 */
	public DeliveryPlanningList unallocatedOnes() {return filter(deliveryPlanning -> !deliveryPlanning.isAllocated());}

	public DeliveryPlanningList withoutShipper() {return filter(DeliveryPlanning::isWithoutShipper);}

	private DeliveryPlanningList filter(@NonNull final Predicate<DeliveryPlanning> predicate)
	{
		return list.stream().filter(predicate).collect(collect());
	}

	/**
	 * This list and the given one as ONE list, so a rule about what a delivery instruction would hold after a move
	 * can be answered against what it holds now together with what is being put on it. A planning in both is
	 * carried once, keyed on its id, which is what makes re-adding a planning the target already holds a no-op
	 * rather than a self-mismatch in {@link #aggregationKeyViolations()}.
	 */
	public DeliveryPlanningList union(@NonNull final DeliveryPlanningList other)
	{
		if (other.isEmpty())
		{
			return this;
		}
		if (isEmpty())
		{
			return other;
		}

		final LinkedHashMap<DeliveryPlanningId, DeliveryPlanning> byId = new LinkedHashMap<>();
		for (final DeliveryPlanning deliveryPlanning : list)
		{
			byId.put(deliveryPlanning.getId(), deliveryPlanning);
		}
		for (final DeliveryPlanning deliveryPlanning : other.list)
		{
			byId.putIfAbsent(deliveryPlanning.getId(), deliveryPlanning);
		}

		return ofCollection(byId.values());
	}

	/**
	 * Every field on which this selection disagrees, so one message can name them all at once. Empty means the
	 * selection can share a single delivery instruction. Ordered by {@link AggregationKeyField} declaration order.
	 */
	public ImmutableSet<AggregationKeyField> aggregationKeyViolations()
	{
		return Arrays.stream(AggregationKeyField.values())
				.filter(this::isMismatch)
				.collect(ImmutableSet.toImmutableSet());
	}

	private boolean isMismatch(@NonNull final AggregationKeyField field)
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
	 * Everything the delivery-instruction header can hold only one of, and which therefore has to match across the
	 * whole selection.
	 * <p>
	 * ADDING A FIELD HERE IS HALF THE CHANGE. The Add-to / Move-to target picker filters on the same set through
	 * the {@code AD_Val_Rule} "Delivery Instruction aggregation key matching", fed by one hidden process parameter
	 * per field -- except {@link #Direction}, which is fed by the pre-existing TransportDirection parameter. A
	 * field added here and not there leaves the picker offering targets this class then refuses -- the planner
	 * picks one and is told no, which is the defect that filtering was introduced to remove.
	 */
	public enum AggregationKeyField
	{
		Organisation(DeliveryPlanning::getOrgId, "Organisation"),
		Direction(DeliveryPlanning::getTransportDirection, "Direction"),
		Forwarder(DeliveryPlanning::getShipperId, "Forwarder"),
		Incoterms(DeliveryPlanning::getIncotermsId, "Incoterms"),
		IncotermLocation(DeliveryPlanning::getIncotermLocation, "IncotermLocation"),
		MeansOfTransportation(DeliveryPlanning::getMeansOfTransportationId, "MeansOfTransportation"),
		LoadingAddress(DeliveryPlanning::getLoadingLocationId, "LoadingAddress"),
		DeliveryAddress(DeliveryPlanning::getDeliveryLocationId, "DeliveryAddress");

		private static final String LABEL_PREFIX = "de.metas.deliveryplanning.CombineIntoDeliveryInstruction.Field.";

		private final Function<DeliveryPlanning, Object> valueExtractor;

		/**
		 * How this field is named in the rejection message. Carried on the enum constant so a field cannot be added
		 * without a label. The suffix is spelled out rather than taken from {@link #name()} because the key is an
		 * {@code AD_Message.Value} in the database, so renaming a constant must not move it.
		 */
		private final AdMessageKey label;

		AggregationKeyField(@NonNull final Function<DeliveryPlanning, Object> valueExtractor, @NonNull final String labelSuffix)
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

		/**
		 * A value read off this enum in the shape a process parameter carries it: a typed id as its repo id,
		 * a String with its apostrophes doubled, anything else unchanged.
		 * <p>
		 * The doubling is not cosmetic. These parameters exist only to be read by the target picker's
		 * {@code AD_Val_Rule}, which reaches them through {@code @Param@} substitution -- plain textual
		 * splicing into the rule's SQL, with no escaping anywhere on the path: {@code CtxName.getValueAsString}
		 * hands the value back verbatim, and the one available modifier ({@code QuotedIfNotDefault}) wraps in
		 * quotes without escaping. So an incoterm place that carries an apostrophe -- L'Aquila, O'Hare,
		 * Sant'Angelo, all ordinary in Europe -- would close the SQL string early and turn the picker's query
		 * into a syntax error: the planner opens Add to / Move to and gets a server error instead of a target
		 * list. Doubling is also what the statement converter expects, since it markers {@code ''} out before
		 * it tokenises quoted strings.
		 */
		@Nullable
		public static Object toProcessParameterValue(@Nullable final Object keyValue)
		{
			if (keyValue instanceof RepoIdAware)
			{
				return ((RepoIdAware)keyValue).getRepoId();
			}
			if (keyValue instanceof String)
			{
				return ((String)keyValue).replace("'", "''");
			}
			return keyValue;
		}
	}
}
