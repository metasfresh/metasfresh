package de.metas.picking.terminal;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import de.metas.picking.terminal.Utils.PackingStates;

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
