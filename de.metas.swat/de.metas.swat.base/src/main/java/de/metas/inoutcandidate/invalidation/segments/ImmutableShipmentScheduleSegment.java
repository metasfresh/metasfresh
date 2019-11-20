package de.metas.inoutcandidate.invalidation.segments;

import java.util.Set;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

/**
 * Plain Immutable {@link IShipmentScheduleSegment} implementation.
 *
 * @author tsa
 *
 */
@Builder
@Value
public class ImmutableShipmentScheduleSegment implements IShipmentScheduleSegment
{
	/**
	 * Never {@code null}. Empty collection means "not constrained on any products".
	 */
	@Singular
	private final Set<Integer> M_Product_IDs;

	/**
	 * Never {@code null}. Empty collection means "not constrained on any bPartners".
	 */
	@Singular
	private final Set<Integer> C_BPartner_IDs;

	@Singular
	private final Set<Integer> Bill_BPartner_IDs;

	/**
	 * Never {@code null}. Empty collection means "not constrained on any locators".
	 */
	@Singular
	private final Set<Integer> M_Locator_IDs;

	/**
	 * Never {@code null}. Empty collection means "not constrained on any attributes".
	 */
	@Singular
	private final Set<ShipmentScheduleAttributeSegment> attributeSegments;

	public static class ImmutableShipmentScheduleSegmentBuilder
	{
		public ImmutableShipmentScheduleSegmentBuilder anyC_BPartner_ID()
		{
			C_BPartner_ID(ANY);
			return this;
		}

		public ImmutableShipmentScheduleSegmentBuilder anyM_Product_ID()
		{
			M_Product_ID(ANY);
			return this;
		}

		public ImmutableShipmentScheduleSegmentBuilder anyM_Locator_ID()
		{
			M_Locator_ID(ANY);
			return this;
		}
	}
}
