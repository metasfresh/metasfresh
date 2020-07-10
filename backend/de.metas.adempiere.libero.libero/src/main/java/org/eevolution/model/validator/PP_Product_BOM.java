package org.eevolution.model.validator;

import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.model.CopyRecordFactory;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.callout.PP_Product_BOM_TabCallout;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.impl.PP_Product_BOM_POCopyRecordSupport;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Interceptor(I_PP_Product_BOM.class)
public class PP_Product_BOM
{
	private final IProductBOMBL bomService = Services.get(IProductBOMBL.class);

	@Init
	public void init(final IModelValidationEngine engine)
	{
		CopyRecordFactory.enableForTableName(I_PP_Product_BOM.Table_Name);
		CopyRecordFactory.registerCopyRecordSupport(I_PP_Product_BOM.Table_Name, PP_Product_BOM_POCopyRecordSupport.class);

		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(new org.eevolution.callout.PP_Product_BOM());
		Services.get(ITabCalloutFactory.class).registerTabCalloutForTable(I_PP_Product_BOM.Table_Name, PP_Product_BOM_TabCallout.class);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE })
	public void updateProduct(final I_PP_Product_BOM bom)
	{
		final ProductId productId = ProductId.ofRepoId(bom.getM_Product_ID());
		bomService.updateIsBOMFlag(productId);
	}
}
