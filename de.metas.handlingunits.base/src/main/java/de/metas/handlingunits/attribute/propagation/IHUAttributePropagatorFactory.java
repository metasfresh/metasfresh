package de.metas.handlingunits.attribute.propagation;

import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.util.ISingletonService;

public interface IHUAttributePropagatorFactory extends ISingletonService
{
	/**
	 * Add {@link IHUAttributePropagator} to the registry
	 *
	 * @param propagator
	 */
	void registerPropagator(IHUAttributePropagator propagator);

	/**
	 * Gets {@link IHUAttributePropagator} for given attribute in attribute set.
	 *
	 * @param attributeSet
	 * @param attribute
	 * @return {@link IHUAttributePropagator} to be used; never return null
	 */
	IHUAttributePropagator getPropagator(IAttributeStorage attributeSet, I_M_Attribute attribute);

	/**
	 * Gets {@link IHUAttributePropagator}
	 *
	 * @param propagationType
	 * @return {@link IHUAttributePropagator} to be used; never return null
	 */
	IHUAttributePropagator getPropagator(String propagationType);

	/**
	 * Gets reversal {@link IHUAttributePropagator} for of given propagationType. See {@link IHUAttributePropagator#getReversalPropagationType()}
	 *
	 * @param propagationType
	 * @return reversal {@link IHUAttributePropagator} to be used; never return null
	 */
	IHUAttributePropagator getReversalPropagator(String propagationType);

	/**
	 * Gets reversal {@link IHUAttributePropagator} for of given propagator. See {@link IHUAttributePropagator#getReversalPropagationType()}
	 *
	 * @param propagator
	 * @return reversal {@link IHUAttributePropagator} to be used; never return null
	 */
	IHUAttributePropagator getReversalPropagator(IHUAttributePropagator propagator);
}
