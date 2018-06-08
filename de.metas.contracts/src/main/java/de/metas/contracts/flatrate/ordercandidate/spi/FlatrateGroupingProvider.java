package de.metas.contracts.flatrate.ordercandidate.spi;

/*
 * #%L
 * de.metas.contracts
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


import java.util.Collections;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.IOLCandGroupingProvider;

/**
 * 
 * 
 * @author ts
 * 
 */
public class FlatrateGroupingProvider implements IOLCandGroupingProvider
{
	/**
	 * This implementation returns a list containing the given candidate's
	 * <code>C_OLCand.C_Flatrate_Conditions_ID</code> value. That way is is made sure that two candidates with different
	 * flatrate condition won't end up in the same order line.
	 * 
	 * @param cand
	 * @return a list with the given <code>cand</code>'s
	 *         {@link de.metas.contracts.flatrate.interfaces.I_C_OLCand#getC_Flatrate_Conditions_ID()}.
	 *         
	 */
	@Override
	public List<Object> provideLineGroupingValues(final I_C_OLCand cand)
	{
		final de.metas.contracts.flatrate.interfaces.I_C_OLCand flatratesCand =
				InterfaceWrapperHelper.create(cand, de.metas.contracts.flatrate.interfaces.I_C_OLCand.class);

		return Collections.singletonList((Object)flatratesCand.getC_Flatrate_Conditions_ID());
	}

}
