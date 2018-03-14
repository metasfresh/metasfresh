package de.metas.ordercandidate.spi.impl;

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


import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Services;
import org.springframework.stereotype.Component;

import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.IOLCandValidator;

@Component
public class OLCandUOMValidator implements IOLCandValidator
{
	/**
	 * Validates the UOM conversion; we will need convertToProductUOM in order to get the QtyOrdered in the order line.
	 */
	@Override
	public boolean validate(I_C_OLCand olCand)
	{
		try
		{
			final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
			final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

			uomConversionBL.convertToProductUOM(InterfaceWrapperHelper.getCtx(olCand),
					olCandEffectiveValuesBL.getM_Product_Effective(olCand),
					olCandEffectiveValuesBL.getC_UOM_Effective(olCand),
					olCand.getQty());
		}
		catch (AdempiereException e)
		{
			olCand.setErrorMsg(e.getLocalizedMessage());
			olCand.setIsError(true);
			return false;
		}
		return true;
	}
}
