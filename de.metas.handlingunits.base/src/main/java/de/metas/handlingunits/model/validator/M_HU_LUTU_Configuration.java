package de.metas.handlingunits.model.validator;

/*
 * #%L
 * de.metas.handlingunits.base
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


import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.util.Services;

@Validator(I_M_HU_LUTU_Configuration.class)
public class M_HU_LUTU_Configuration
{
	/**
	 * Prevent changing the relevant fields from LU/TU Configuration after it was saved
	 *
	 * @param lutuConfiguration
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE)
	public void assertNotChanged(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		final ILUTUConfigurationFactory lutuConfigurationBL = Services.get(ILUTUConfigurationFactory.class);
		lutuConfigurationBL.assertNotChanged(lutuConfiguration);
	}
}
