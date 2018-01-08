package org.eevolution.model.validator;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_PP_Order_BOM;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.material.planning.pporder.IPPOrderBOMDAO;

@Validator(I_PP_Order_BOM.class)
public class PP_Order_BOM
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void deleteBOMLines(I_PP_Order_BOM orderBOM)
	{
		for (final I_PP_Order_BOMLine orderBOMLine : Services.get(IPPOrderBOMDAO.class).retrieveAllOrderBOMLines(orderBOM))
		{
			InterfaceWrapperHelper.delete(orderBOMLine);
		}
	}

}
