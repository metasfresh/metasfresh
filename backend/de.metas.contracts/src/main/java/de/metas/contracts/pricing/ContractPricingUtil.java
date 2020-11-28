package de.metas.contracts.pricing;

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


import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.contracts.flatrate.interfaces.IFlatrateConditionsAware;
import de.metas.contracts.model.I_C_Flatrate_Conditions;

public final class ContractPricingUtil
{
	private ContractPricingUtil()
	{
	}

	/**
	 * Helper method to get the {@link I_C_Flatrate_Conditions} instance out of a referenced object, if there is any.
	 * 
	 * @param referencedObject
	 * @return
	 */
	public static I_C_Flatrate_Conditions getC_Flatrate_Conditions(final Object referencedObject)
	{
		if (referencedObject == null)
		{
			return null;
		}

		if (referencedObject instanceof IFlatrateConditionsAware)
		{
			return ((IFlatrateConditionsAware)(referencedObject)).getC_Flatrate_Conditions();
		}

		try
		{
			final IFlatrateConditionsAware flatrateConditionsProvider = InterfaceWrapperHelper.create(referencedObject, IFlatrateConditionsAware.class);
			return flatrateConditionsProvider.getC_Flatrate_Conditions();
		}
		catch (AdempiereException e)
		{
			return null;
		}
	}
}
