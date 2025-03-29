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
import org.compiere.model.I_M_Package;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.shipping.IHUPackageBL;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.util.Services;

@Validator(I_M_ShippingPackage.class)
public class M_ShippingPackage
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void destroyHUPackage(final I_M_ShippingPackage shippingPackage)
	{
		final I_M_Package mpackage = shippingPackage.getM_Package();
		if (mpackage == null)
		{
			// shall not happen, but is not severe
			return;
		}

		Services.get(IHUPackageBL.class).destroyHUPackage(mpackage);
	}
}
