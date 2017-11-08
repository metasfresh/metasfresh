package de.metas.storage.impl;

import java.util.Set;

import de.metas.storage.IStorageAttributeSegment;
import de.metas.storage.IStorageSegment;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

/**
 * Plain Immutable {@link IStorageSegment} implementation.
 *
 * @author tsa
 *
 */
@Builder
@Value
public class ImmutableStorageSegment implements IStorageSegment
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
	private final Set<IStorageAttributeSegment> attributeSegments;

	public static class ImmutableStorageSegmentBuilder
	{
		public ImmutableStorageSegmentBuilder anyC_BPartner_ID()
		{
			C_BPartner_ID(ANY);
			return this;
		}

		public ImmutableStorageSegmentBuilder anyM_Product_ID()
		{
			M_Product_ID(ANY);
			return this;
		}

		public ImmutableStorageSegmentBuilder anyM_Locator_ID()
		{
			M_Locator_ID(ANY);
			return this;
		}

	}
}
