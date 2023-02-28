/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package org.eevolution.api.impl;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.AdMessageKey;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.eevolution.api.BOMCreateRequest;
import org.eevolution.api.BOMVersionsCreateRequest;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_Planning;
import org.springframework.stereotype.Service;

import static org.eevolution.exceptions.ExceptionConstants.PP_PRODUCT_PLANNING_BOM_ATTR_ERROR;

@Service
public class ProductBOMService
{
	private final IProductBOMDAO bomRepo = Services.get(IProductBOMDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final ProductBOMVersionsDAO bomVersionsDAO;

	public ProductBOMService(@NonNull final ProductBOMVersionsDAO bomVersionsDAO)
	{
		this.bomVersionsDAO = bomVersionsDAO;
	}

	@NonNull
	public I_PP_Product_BOM createBOM(@NonNull final BOMCreateRequest request)
	{
		final ProductId productId = request.getProductId();

		final ProductBOMVersionsId bomVersionsId = bomVersionsDAO.retrieveBOMVersionsId(productId)
				.orElseGet(() -> bomVersionsDAO.createBOMVersions(BOMVersionsCreateRequest.of(request)));

		final I_PP_Product_BOM createdBOM = bomRepo.createBOM(bomVersionsId, request);

		documentBL.processEx(createdBOM, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		return createdBOM;
	}

	public void verifyBOMAssignment(@NonNull final I_PP_Product_Planning planning, @NonNull final I_PP_Product_BOM productBom)
	{
		if (!planning.isAttributeDependant())
		{
			return;
		}

		final AttributeSetInstanceId planningASIId = AttributeSetInstanceId.ofRepoIdOrNone(planning.getM_AttributeSetInstance_ID());
		final AttributesKey planningAttributesKeys = AttributesKeys.createAttributesKeyFromASIStorageAttributes(planningASIId).orElse(AttributesKey.NONE);

		if (planningAttributesKeys.isNone())
		{
			return;
		}

		final AttributeSetInstanceId productBomASIId = AttributeSetInstanceId.ofRepoIdOrNone(productBom.getM_AttributeSetInstance_ID());
		final AttributesKey productBOMAttributesKeys = AttributesKeys.createAttributesKeyFromASIStorageAttributes(productBomASIId).orElse(AttributesKey.NONE);

		if (!productBOMAttributesKeys.contains(planningAttributesKeys))
		{
			throw new AdempiereException(AdMessageKey.of(PP_PRODUCT_PLANNING_BOM_ATTR_ERROR));
		}
	}
}
