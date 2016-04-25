package de.metas.materialtracking.ait.helpers;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;

/*
 * #%L
 * de.metas.materialtracking.ait
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class BPartnerDriver
{
	public static I_C_BPartner_Location getCreateBPartnerLocation(final String countryName)
	{
		I_C_BPartner_Location bpl = Helper.retrieveExistingOrNull(countryName, I_C_BPartner_Location.class);
		if (bpl != null)
		{
			return bpl;
		}

		final I_C_Country country = Helper.retrieveExisting(countryName, I_C_Country.class);

		final I_C_Location l = InterfaceWrapperHelper.newInstance(I_C_Location.class, Helper.context);
		l.setC_Country_ID(country.getC_Country_ID());
		InterfaceWrapperHelper.save(l);

		bpl = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class, Helper.context);
		bpl.setC_Location_ID(l.getC_Location_ID());
		bpl.setName(countryName);
		InterfaceWrapperHelper.save(bpl);

		Helper.storeFirstTime(countryName, bpl);
		return bpl;
	}
}
