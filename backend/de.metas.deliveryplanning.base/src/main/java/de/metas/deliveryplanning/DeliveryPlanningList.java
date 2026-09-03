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
import de.metas.quantity.Quantity;
import de.metas.shipping.TransportDirection;
import de.metas.util.Check;
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
				// so every derived message, allocation order and printed line is reproducible
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
	 * The ids in the order the plannings of one delivery instruction are allocated in, so the same selection
	 * always yields the same allocation order.
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
		return list.isEmpty() || isMismatch(AggregationKeyField.Direction)
				? Optional.empty()
				: Optional.of(list.get(0).getTransportDirection());
	}

	/**
	 * The one {@link PoolEnd} the whole selection nets, or empty when it spans both ends or is empty.
	 * <p>
	 * Deliberately WEAKER than {@link #getSingleTransportDirection()}: {@code Incoming} and {@code Dropship} are
	 * two directions that {@link PoolEnd#forDirection} maps onto the same DISCHARGE end, so a selection holding
	 * both has no single direction yet still nets one well-defined end. Callers that only need to know which pair
	 * of quantity columns to net must ask this, not the direction - asking the direction would reject a mix that
	 * is perfectly computable.
	 */
	public Optional<PoolEnd> getSinglePoolEnd()
	{
		final ImmutableSet<PoolEnd> ends = list.stream()
				.map(DeliveryPlanning::getTransportDirection)
				.map(PoolEnd::forDirection)
				.collect(ImmutableSet.toImmutableSet());

		return ends.size() == 1 ? Optional.of(ends.iterator().next()) : Optional.empty();
	}

	/**
	 * The one value the whole selection carries for the given key field. Empty in all three cases the caller has
	 * to treat alike - the selection is empty, it disagrees with itself on this field, or the one value it agrees
	 * on is {@code null} (a field none of the plannings has set).
	 */
	public Optional<Object> getSingleAggregationKeyValue(@NonNull final AggregationKeyField field)
	{
		return list.isEmpty() || isMismatch(field)
				? Optional.empty()
				: Optional.ofNullable(field.extractValue(list.get(0)));
	}

	public boolean anyClosed() {return list.stream().anyMatch(DeliveryPlanning::isClosed);}

	/**
	 * Not the negation of {@link #anyClosed()}: a selection holding one open and one closed planning answers
	 * {@code true} to both.
	 */
	public boolean anyOpen() {return list.stream().anyMatch(deliveryPlanning -> !deliveryPlanning.isClosed());}

	public DeliveryPlanningList closedOnes() {return filter(DeliveryPlanning::isClosed);}

	/**
	 * The complement of {@link #closedOnes()}: what Re-Open refuses, as Close refuses the closed ones, so the two
	 * actions' preconditions partition every selection between them.
	 */
	public DeliveryPlanningList openOnes() {return filter(deliveryPlanning -> !deliveryPlanning.isClosed());}

	public boolean anyAllocated() {return list.stream().anyMatch(DeliveryPlanning::isAllocated);}

	public DeliveryPlanningList allocatedOnes() {return filter(DeliveryPlanning::isAllocated);}

	/**
	 * The complement of {@link #allocatedOnes()}: what Move refuses, as Add refuses the allocated ones, so the two
	 * actions' preconditions partition every selection between them.
	 */
	public DeliveryPlanningList unallocatedOnes() {return filter(deliveryPlanning -> !deliveryPlanning.isAllocated());}

	public DeliveryPlanningList withoutShipper() {return filter(DeliveryPlanning::isWithoutShipper);}

	/**
	 * The three-state delivered indicator of the delivery instruction this selection is the active allocations
	 * of (spec &sect; 5.7): {@code NotDelivered} when none of them is, {@code FullyDelivered} when every one is,
	 * {@code PartlyDelivered} otherwise - the normal intermediate state of a consolidated instruction, not an
	 * edge case. An empty selection (an instruction with no active allocation) answers {@code NotDelivered},
	 * vacuously - the same "no allocation's planning is delivered" condition a non-empty all-open selection
	 * answers.
	 * <p>
	 * The ONE place this is computed (rule 6, Task Q9): every write point that can change which plannings are
	 * delivered, or which plannings are actively allocated to the instruction, loads this list and calls this
	 * method, rather than re-deriving the three states inline - so a stored {@code DeliveredState} column
	 * cannot drift from this definition by having a second copy of it.
	 */
	public DeliveryInstructionDeliveredState getDeliveredState()
	{
		if (isEmpty())
		{
			return DeliveryInstructionDeliveredState.NotDelivered;
		}

		final boolean allDelivered = list.stream().allMatch(DeliveryPlanning::isDelivered);
		if (allDelivered)
		{
			return DeliveryInstructionDeliveredState.FullyDelivered;
		}

		final boolean anyDelivered = list.stream().anyMatch(DeliveryPlanning::isDelivered);
		return anyDelivered ? DeliveryInstructionDeliveredState.PartlyDelivered : DeliveryInstructionDeliveredState.NotDelivered;
	}

	private DeliveryPlanningList filter(@NonNull final Predicate<DeliveryPlanning> predicate)
	{
		return list.stream().filter(predicate).collect(collect());
	}

	/**
	 * The ONE distributable-pool rule (owner, 2026-09-02, "The distributable pool" - see the plan's Global
	 * Constraints), applied to whichever {@code end} the caller asks about: {@code QtyOrdered} minus what every
	 * OTHER planning of this order line claims - {@code coalesce(nullif(actual, 0), planned)} - so a zero actual
	 * (nothing recorded yet) falls back to the sibling's planned share instead of being read as a real zero that
	 * would inflate the pool by that sibling's whole planned amount.
	 * <p>
	 * {@code excludePlanningId} is the split's own target: {@code null} to include every planning in the sum
	 * (the target's own claim counts too, once it is allocated and therefore committed cargo), or that
	 * planning's id to leave its own claim out (unallocated: its share is still up for redistribution).
	 * <p>
	 * NOT floored at zero here - see {@link DeliveryPlanning} class javadoc and the caller: the clamp belongs to
	 * the SPLIT's use of this figure (a negative pool is not distributable), not to this shared calculation,
	 * which a display column may also read unclamped (an over-planned line legitimately shows a negative).
	 * <p>
	 * Pure in-memory arithmetic over already-loaded {@link DeliveryPlanning} rows - unit-tested without a
	 * database, which is the whole reason this pool lives here rather than inline in the service.
	 */
	public Quantity openPlanQty(@Nullable final DeliveryPlanningId excludePlanningId, @NonNull final PoolEnd end)
	{
		Check.assumeNotEmpty(list, "Cannot compute the distributable pool of an empty DeliveryPlanningList");

		final Quantity qtyOrdered = list.get(0).getQtyOrdered();

		Quantity claimed = null;
		for (final DeliveryPlanning deliveryPlanning : list)
		{
			if (excludePlanningId != null && excludePlanningId.equals(deliveryPlanning.getId()))
			{
				continue;
			}

			final Quantity effectiveQty = end.effectiveQty(deliveryPlanning);
			claimed = claimed == null ? effectiveQty : claimed.add(effectiveQty);
		}

		return claimed == null ? qtyOrdered : qtyOrdered.subtract(claimed);
	}

	/**
	 * {@code QtyTotalOpen} (owner, 2026-09-02, "the open quantity is a PAIR of fields"): {@code QtyOrdered} minus
	 * what has ACTUALLY been delivered so far on this order line, summed straight - unlike {@link #openPlanQty}
	 * there is no nullif/coalesce fallback here, because a zero actual for this figure means exactly what it
	 * says: nothing delivered yet. NOT floored at zero: an over-delivered line legitimately shows negative
	 * (D16) - the caller displays it, never clamps it.
	 */
	public Quantity qtyTotalOpen(@NonNull final PoolEnd end)
	{
		Check.assumeNotEmpty(list, "Cannot compute QtyTotalOpen of an empty DeliveryPlanningList");

		final Quantity qtyOrdered = list.get(0).getQtyOrdered();

		Quantity actualSum = null;
		for (final DeliveryPlanning deliveryPlanning : list)
		{
			final Quantity actual = end.actual(deliveryPlanning);
			actualSum = actualSum == null ? actual : actualSum.add(actual);
		}

		return qtyOrdered.subtract(actualSum);
	}

	/**
	 * {@code QtyTotalOpen}'s sibling figure: {@code QtyOrdered} minus the PLANNED quantities of every planning of
	 * this order line, summed straight - "how much of the order line nobody has planned yet". Not floored at
	 * zero for the same reason as {@link #qtyTotalOpen}: an over-planned line legitimately shows negative (D16).
	 */
	public Quantity qtyTotalOpenPlanned(@NonNull final PoolEnd end)
	{
		Check.assumeNotEmpty(list, "Cannot compute QtyTotalOpenPlanned of an empty DeliveryPlanningList");

		final Quantity qtyOrdered = list.get(0).getQtyOrdered();

		Quantity plannedSum = null;
		for (final DeliveryPlanning deliveryPlanning : list)
		{
			final Quantity planned = end.planned(deliveryPlanning);
			plannedSum = plannedSum == null ? planned : plannedSum.add(planned);
		}

		return qtyOrdered.subtract(plannedSum);
	}

	/**
	 * Which pair of quantity columns {@link #openPlanQty} nets - the load pair or the discharge pair (see the
	 * plan's Global Constraints, "The distributable pool", "Applied per end").
	 */
	public enum PoolEnd
	{
		LOAD(DeliveryPlanning::getPlannedLoadedQty, DeliveryPlanning::getActualLoadedQty),
		DISCHARGE(DeliveryPlanning::getPlannedDischargeQty, DeliveryPlanning::getActualDischargeQty);

		private final Function<DeliveryPlanning, Quantity> plannedExtractor;
		private final Function<DeliveryPlanning, Quantity> actualExtractor;

		PoolEnd(
				@NonNull final Function<DeliveryPlanning, Quantity> plannedExtractor,
				@NonNull final Function<DeliveryPlanning, Quantity> actualExtractor)
		{
			this.plannedExtractor = plannedExtractor;
			this.actualExtractor = actualExtractor;
		}

		/**
		 * A sibling's effective claim on the pool: its actual once one is recorded ({@code nullif(actual, 0)}),
		 * otherwise its planned figure.
		 */
		private Quantity effectiveQty(@NonNull final DeliveryPlanning deliveryPlanning)
		{
			final Quantity actual = actualExtractor.apply(deliveryPlanning);
			final Quantity planned = plannedExtractor.apply(deliveryPlanning);
			return actual != null && !actual.isZero() ? actual : planned;
		}

		/** This planning's own actual figure for this end - raw, no nullif fallback. */
		public Quantity actual(@NonNull final DeliveryPlanning deliveryPlanning)
		{
			return actualExtractor.apply(deliveryPlanning);
		}

		/** This planning's own planned figure for this end. */
		public Quantity planned(@NonNull final DeliveryPlanning deliveryPlanning)
		{
			return plannedExtractor.apply(deliveryPlanning);
		}

		/**
		 * Which end a planning's own {@code QtyTotalOpen}/{@code QtyTotalOpenPlanned} follow, decided by
		 * DIRECTION (owner, 2026-09-02, "receipt based unload, ship based load"): a receipt (incoming or
		 * dropship - the dropship purchase leg's own receipt) nets discharge, a shipment nets load.
		 */
		public static PoolEnd forDirection(@NonNull final TransportDirection transportDirection)
		{
			return transportDirection.isIncomingOrDropship() ? DISCHARGE : LOAD;
		}
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
