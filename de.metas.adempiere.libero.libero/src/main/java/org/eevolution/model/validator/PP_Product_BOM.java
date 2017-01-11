package org.eevolution.model.validator;

import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.annotations.Init;

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
import org.adempiere.model.CopyRecordFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.impl.PP_Product_BOM_POCopyRecordSupport;

@Validator(I_PP_Product_BOM.class)
public class PP_Product_BOM
{
	@Init
	public void init(final IModelValidationEngine engine)
	{

		CopyRecordFactory.enableForTableName(I_PP_Product_BOM.Table_Name);
		CopyRecordFactory.registerCopyRecordSupport(I_PP_Product_BOM.Table_Name, PP_Product_BOM_POCopyRecordSupport.class);
	}
	
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE })
	public void updateProduct(final I_PP_Product_BOM bom)
	{
		final I_M_Product product = bom.getM_Product();
		Services.get(IProductBOMBL.class).setIsBOM(product);
		InterfaceWrapperHelper.save(product);
	}

}
