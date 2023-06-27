package org.eevolution.model.validator;

import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.i18n.AdMessageKey;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.api.impl.ProductBOMService;
import org.eevolution.api.impl.ProductBOMVersionsDAO;
import org.eevolution.callout.PP_Product_BOM_TabCallout;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMVersions;

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
	private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);

	private final ProductBOMVersionsDAO bomVersionsDAO;
	private final ProductBOMService productBOMService;

	public PP_Product_BOM(
			@NonNull final ProductBOMVersionsDAO bomVersionsDAO,
			@NonNull final ProductBOMService productBOMService)
	{
		this.bomVersionsDAO = bomVersionsDAO;
		this.productBOMService = productBOMService;
	}

	@Init
	public void init(final IModelValidationEngine engine)
	{
		CopyRecordFactory.enableForTableName(I_PP_Product_BOM.Table_Name);

		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(new org.eevolution.callout.PP_Product_BOM(bomVersionsDAO));
		Services.get(ITabCalloutFactory.class).registerTabCalloutForTable(I_PP_Product_BOM.Table_Name, PP_Product_BOM_TabCallout.class);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE })
	public void updateProduct(final I_PP_Product_BOM bom)
	{
		final ProductId productId = ProductId.ofRepoId(bom.getM_Product_ID());
		bomService.updateIsBOMFlag(productId);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_PP_Product_BOM.COLUMNNAME_PP_Product_BOMVersions_ID, I_PP_Product_BOM.COLUMNNAME_M_Product_ID })
	public void validateBOMVersions(final I_PP_Product_BOM bom)
	{
		final int productId = bom.getM_Product_ID();

		final ProductBOMVersionsId bomVersionsId = ProductBOMVersionsId.ofRepoId(bom.getPP_Product_BOMVersions_ID());

		final I_PP_Product_BOMVersions bomVersions = bomVersionsDAO.getBOMVersions(bomVersionsId);

		if (productId != bomVersions.getM_Product_ID())
		{
			throw new AdempiereException(AdMessageKey.of("PP_Product_BOMVersions_BOM_Doesnt_Match"))
					.markAsUserValidationError()
					.appendParametersToMessage()
					.setParameter("PP_Product_BOM", bom)
					.setParameter("PP_Product_BOMVersions", bomVersions);
		}

	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_PP_Product_BOM.COLUMNNAME_M_AttributeSetInstance_ID })
	public void validateBOMAttributes(final I_PP_Product_BOM productBom)
	{
		final ProductBOMVersionsId productBOMVersionsId = ProductBOMVersionsId.ofRepoId(productBom.getPP_Product_BOMVersions_ID());

		productPlanningDAO.retrieveProductPlanningForBomVersions(productBOMVersionsId)
				.forEach(productPlanning -> productBOMService.verifyBOMAssignment(productPlanning, productBom));
	}
}
