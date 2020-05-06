package de.metas.fresh.picking.form;

/**
 * Aim of implementations of this class is to aggregate multiple {@link PackingStates} and then to return the final {@link PackingStates}.
 * 
 * @author tsa
 * 
 */
public interface IPackingStateAggregator
{
	/**
	 * Sets default {@link PackingStates} to be used when no packing states were added.
	 * 
	 * @param stateDefault
	 */
	void setDefaultPackingState(PackingStates stateDefault);

	/**
	 * Gets default {@link PackingStates} which will be used when no packing states were added
	 * 
	 * @return
	 */
	PackingStates getDefaultPackingState();

	void addPackingState(final PackingStates packingState);

	/**
	 * Gets current {@link PackingStates}.
	 * 
	 * @return current {@link PackingStates}; never return null
	 */
	PackingStates getPackingState();
}
