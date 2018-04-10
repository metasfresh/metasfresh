/**
 * 
 */
package de.metas.tourplanning.model.validator;

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


import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.ModelValidator;

import de.metas.tourplanning.exceptions.BPartnerNotVendorException;
import de.metas.tourplanning.model.I_M_TourVersionLine;

/**
 * @author cg
 *
 */
@Interceptor(I_M_TourVersionLine.class)
public class M_TourVersionLine
{
	@ModelChange(
			timings = {
					ModelValidator.TYPE_BEFORE_NEW,
					ModelValidator.TYPE_BEFORE_CHANGE
			})
	public void checkIsToBeFetched(final I_M_TourVersionLine tourVersionLine)
	{
		final I_C_BPartner partner = tourVersionLine.getC_BPartner();
		if (partner != null && !partner.isVendor() && tourVersionLine.isToBeFetched())
		{
			throw new BPartnerNotVendorException(partner);
		}
	}
}
