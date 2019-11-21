package de.metas.inoutcandidate.invalidation.segments;

import java.util.Collections;
import java.util.Set;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/**
 * Plain Immutable {@link IShipmentScheduleSegment} implementation.
 *
 * @author tsa
 *
 */
@Value
public class ImmutableShipmentScheduleSegment implements IShipmentScheduleSegment
{
	public static ImmutableShipmentScheduleSegment copyOf(@NonNull final IShipmentScheduleSegment from)
	{
		return from instanceof ImmutableShipmentScheduleSegment
				? (ImmutableShipmentScheduleSegment)from
				: new ImmutableShipmentScheduleSegment(from);
	}

	Set<Integer> productIds;
	Set<Integer> bpartnerIds;
	Set<Integer> billBPartnerIds;
	Set<Integer> locatorIds;
	Set<ShipmentScheduleAttributeSegment> attributes;

	@Builder(toBuilder = true)
	private ImmutableShipmentScheduleSegment(
			@NonNull @Singular final Set<Integer> productIds,
			@NonNull @Singular final Set<Integer> bpartnerIds,
			@NonNull @Singular final Set<Integer> billBPartnerIds,
			@NonNull @Singular final Set<Integer> locatorIds,
			@NonNull @Singular final Set<ShipmentScheduleAttributeSegment> attributes)
	{
		this.productIds = Collections.unmodifiableSet(productIds);
		this.bpartnerIds = Collections.unmodifiableSet(bpartnerIds);
		this.billBPartnerIds = Collections.unmodifiableSet(billBPartnerIds);
		this.locatorIds = Collections.unmodifiableSet(locatorIds);
		this.attributes = Collections.unmodifiableSet(attributes);
	}

	private ImmutableShipmentScheduleSegment(final IShipmentScheduleSegment from)
	{
		this.productIds = Collections.unmodifiableSet(from.getProductIds());
		this.bpartnerIds = Collections.unmodifiableSet(from.getBpartnerIds());
		this.billBPartnerIds = Collections.unmodifiableSet(from.getBillBPartnerIds());
		this.locatorIds = Collections.unmodifiableSet(from.getLocatorIds());
		this.attributes = Collections.unmodifiableSet(from.getAttributes());
	}

	public static class ImmutableShipmentScheduleSegmentBuilder
	{
		public ImmutableShipmentScheduleSegmentBuilder anyBPartner()
		{
			bpartnerIds.clear();
			bpartnerId(ANY);
			return this;
		}

		public ImmutableShipmentScheduleSegmentBuilder anyProduct()
		{
			productIds.clear();
			productId(ANY);
			return this;
		}

		public ImmutableShipmentScheduleSegmentBuilder anyLocator()
		{
			locatorIds.clear();
			locatorId(ANY);
			return this;
		}
	}
}
