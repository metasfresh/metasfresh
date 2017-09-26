package de.metas.storage.impl;

import java.util.Set;

import de.metas.storage.AbstractStorageSegment;
import de.metas.storage.IStorageAttributeSegment;
import de.metas.storage.IStorageSegment;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper=false)
public class ImmutableStorageSegment extends AbstractStorageSegment
{
	/**
	 * Never {@code null}. Empty collection means "not constrained on any products".
	 */
	@Singular
	Set<Integer> M_Product_IDs;
	
	/**
	 * Never {@code null}. Empty collection means "not constrained on any bPartners".
	 */
	@Singular
	Set<Integer> C_BPartner_IDs;
	
	/**
	 * Never {@code null}. Empty collection means "not constrained on any locators".
	 */
	@Singular
	Set<Integer> M_Locator_IDs;
	
	/**
	 * Never {@code null}. Empty collection means "not constrained on any attributes".
	 */
	@Singular
	Set<IStorageAttributeSegment> attributeSegments;
}
