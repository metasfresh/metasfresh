package de.metas.handlingunits.picking.job.model;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.picking.job_schedule.model.ShipmentScheduleAndJobScheduleId;
import de.metas.handlingunits.picking.job_schedule.model.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.PPOrderId;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class ScheduledPackageableList implements Iterable<ScheduledPackageable>
{
	public static ScheduledPackageableList EMPTY = new ScheduledPackageableList(ImmutableList.of());

	private final ImmutableList<ScheduledPackageable> list;

	private ScheduledPackageableList(@NonNull final Collection<ScheduledPackageable> list)
	{
		this.list = list.stream()
				.sorted(Comparator.comparing(ScheduledPackageable::getId)) // keep them ordered by shipmentScheduleId/pickingJobScheduleId which is usually the order line ordering
				.collect(ImmutableList.toImmutableList());
	}

	public static ScheduledPackageableList ofCollection(final Collection<ScheduledPackageable> list)
	{
		return !list.isEmpty() ? new ScheduledPackageableList(list) : EMPTY;
	}

	public static ScheduledPackageableList of(final ScheduledPackageable... arr)
	{
		return ofCollection(ImmutableList.copyOf(arr));
	}

	public static Collector<ScheduledPackageable, ?, ScheduledPackageableList> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(ScheduledPackageableList::ofCollection);
	}

	public boolean isEmpty() {return list.isEmpty();}

	public int size() {return list.size();}

	public Stream<ScheduledPackageable> stream() {return list.stream();}

	@Override
	public @NonNull Iterator<ScheduledPackageable> iterator() {return list.iterator();}

	public Stream<ScheduledPackageableList> groupBy(Function<ScheduledPackageable, ?> classifier)
	{
		return list.stream()
				.collect(Collectors.groupingBy(classifier, LinkedHashMap::new, ScheduledPackageableList.collect()))
				.values()
				.stream();
	}

	public ShipmentScheduleAndJobScheduleIdSet getScheduleIds()
	{
		if (list.isEmpty()) {return ShipmentScheduleAndJobScheduleIdSet.EMPTY;}
		return list.stream().map(ScheduledPackageable::getId).sorted().collect(ShipmentScheduleAndJobScheduleIdSet.collect());
	}

	public <T> Optional<T> getSingleValue(@NonNull final Function<ScheduledPackageable, T> mapper)
	{
		if (list.isEmpty())
		{
			return Optional.empty();
		}

		final ImmutableList<T> values = list.stream()
				.map(mapper)
				.filter(Objects::nonNull)
				.distinct()
				.collect(ImmutableList.toImmutableList());

		if (values.isEmpty())
		{
			return Optional.empty();
		}
		else if (values.size() == 1)
		{
			return Optional.of(values.get(0));
		}
		else
		{
			//throw new AdempiereException("More than one value were extracted (" + values + ") from " + list);
			return Optional.empty();
		}
	}

	public OrgId getSingleOrgId() {return getSingleValue(ScheduledPackageable::getOrgId).orElseThrow(() -> new AdempiereException("No single org found in " + list));}

	public Optional<OrderId> getSingleSalesOrderId() {return getSingleValue(ScheduledPackageable::getSalesOrderId);}

	public @NonNull OrderAndLineId getSingleSalesOrderLineId()
	{
		return getSingleValue(ScheduledPackageable::getSalesOrderAndLineIdOrNull).orElseThrow(() -> new AdempiereException("No single sales order line found for " + list));
	}

	public Optional<InstantAndOrgId> getSinglePreparationDate() {return getSingleValue(ScheduledPackageable::getPreparationDate);}

	public Optional<InstantAndOrgId> getSingleDeliveryDate() {return getSingleValue(ScheduledPackageable::getDeliveryDate);}

	public Optional<BPartnerLocationId> getSingleCustomerLocationId() {return getSingleValue(ScheduledPackageable::getCustomerLocationId);}

	public Optional<String> getSingleCustomerAddress() {return getSingleValue(ScheduledPackageable::getCustomerAddress);}

	public Optional<BPartnerLocationId> getSingleHandoverLocationId() {return getSingleValue(ScheduledPackageable::getHandoverLocationId);}

	public ProductId getSingleProductId()
	{
		return getSingleValue(ScheduledPackageable::getProductId).orElseThrow(() -> new AdempiereException("No single product found in " + list));
	}

	public HUPIItemProductId getSinglePackToHUPIItemProductId()
	{
		return getSingleValue(ScheduledPackageable::getPackToHUPIItemProductId).orElseThrow(() -> new AdempiereException("No single PackToHUPIItemProductId found in " + list));
	}

	public Quantity getQtyToPick()
	{
		return list.stream()
				.map(ScheduledPackageable::getQtyToPick)
				.reduce(Quantity::add)
				.orElseThrow(() -> new AdempiereException("No QtyToPick found in " + list));
	}

	public Optional<ShipmentScheduleAndJobScheduleId> getSingleScheduleIdIfUnique()
	{
		final ShipmentScheduleAndJobScheduleIdSet scheduleIds = getScheduleIds();
		return scheduleIds.size() == 1 ? Optional.of(scheduleIds.iterator().next()) : Optional.empty();
	}

	public Optional<UomId> getSingleCatchWeightUomIdIfUnique()
	{
		final List<UomId> catchWeightUomIds = list.stream()
				.map(ScheduledPackageable::getCatchWeightUomId)
				// don't filter out null catch UOMs
				.distinct()
				.collect(Collectors.toList());
		return catchWeightUomIds.size() == 1 ? Optional.ofNullable(catchWeightUomIds.get(0)) : Optional.empty();
	}

	public Optional<PPOrderId> getSingleManufacturingOrderId() {return getSingleValue(ScheduledPackageable::getPickFromOrderId);}
}
