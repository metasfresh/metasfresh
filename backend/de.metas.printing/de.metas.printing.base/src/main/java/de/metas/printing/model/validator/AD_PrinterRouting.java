package de.metas.printing.model.validator;

/*
 * #%L
 * de.metas.printing.base
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
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;

import de.metas.printing.model.I_AD_PrinterRouting;

@Interceptor(I_AD_PrinterRouting.class)
public class AD_PrinterRouting
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void validate(final I_AD_PrinterRouting printerRouting)
	{
		final String routingType = printerRouting.getRoutingType();
		if (I_AD_PrinterRouting.ROUTINGTYPE_LastPages.equals(routingType))
		{
			final int lastPages = printerRouting.getLastPages();
			if (lastPages <= 0)
			{
				throw new AdempiereException("@LastPages@ < 1");
			}
		}
	}
}
