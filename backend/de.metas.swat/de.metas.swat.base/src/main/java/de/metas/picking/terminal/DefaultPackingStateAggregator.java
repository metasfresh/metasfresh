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


import org.adempiere.util.Check;

import de.metas.picking.terminal.Utils.PackingStates;

/**
 * This implementation is handling {@link PackingStates#unpacked}, {@link PackingStates#partiallypacked} and {@link PackingStates#packed} states and computes the resulting states.
 * 
 * General rules for computing the final state are:
 * <ul>
 * <li>default state (in case there were no states added) is {@link PackingStates#unpacked}
 * <li>if all states are {@link PackingStates#unpacked}, resulting state will be {@link PackingStates#unpacked}
 * <li>if all states are {@link PackingStates#packed}, resulting state will be {@link PackingStates#packed}
 * <li>else resulting state will be {@link PackingStates#partiallypacked}
 * </ul>
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/05814_Picking_Slots_of_same_customer_not_same_color_%28105763320611%29
 */
public class DefaultPackingStateAggregator implements IPackingStateAggregator
{
	private PackingStates stateDefault;
	private PackingStates stateCurrent;

	public DefaultPackingStateAggregator()
	{
		super();

		this.stateDefault = PackingStates.unpacked;
		this.stateCurrent = null;
	}

	@Override
	public void setDefaultPackingState(final PackingStates stateDefault)
	{
		assertValidState(stateDefault);
		this.stateDefault = stateDefault;
	}

	@Override
	public PackingStates getDefaultPackingState()
	{
		return stateDefault;
	}

	@Override
	public void addPackingState(final PackingStates packingState)
	{
		// Ignore null values
		if (packingState == null)
		{
			return;
		}

		// This is our first state, so set it
		if (stateCurrent == null)
		{
			stateCurrent = packingState;
			return;
		}

		// New packing state is same as current.
		if (packingState.equals(stateCurrent))
		{
			return;
		}

		// Make sure given state is valid
		assertValidState(packingState);

		// Compute new current state
		// NOTE: we assume given state is not the same as current state, because that was checked before
		if (stateCurrent.equals(PackingStates.unpacked))
		{
			stateCurrent = PackingStates.partiallypacked;
		}
		else if (stateCurrent.equals(PackingStates.partiallypacked))
		{
			// if current state is Partially Packed, nothing will change that
			stateCurrent = PackingStates.partiallypacked;
		}
		else if (stateCurrent.equals(PackingStates.packed))
		{
			stateCurrent = PackingStates.partiallypacked;
		}
		else
		{
			// shall never happen
			throw new IllegalArgumentException("Current state is not supported: " + stateCurrent);
		}
	}

	private void assertValidState(final PackingStates state)
	{
		final boolean valid = PackingStates.unpacked.equals(state)
				|| PackingStates.partiallypacked.equals(state)
				|| PackingStates.packed.equals(state);
		if (!valid)
		{
			throw new IllegalArgumentException("Unsupported state: " + state);
		}
	}

	@Override
	public PackingStates getPackingState()
	{
		if (stateCurrent == null)
		{
			Check.assumeNotNull(stateDefault, "default state shall be set before");
			return stateDefault;
		}
		return stateCurrent;
	}

}
