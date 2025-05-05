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

import com.google.common.annotations.VisibleForTesting;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.AdMessageKey;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.planning.ProductPlanning;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.compiere.util.TimeUtil;
import org.eevolution.api.BOMCreateRequest;
import org.eevolution.api.BOMVersionsCreateRequest;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.model.I_PP_Product_BOM;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;

@Service
public class ProductBOMService
{
	private static final AdMessageKey PP_PRODUCT_PLANNING_BOM_ATTR_ERROR = AdMessageKey.of("PP_Product_Planning_BOM_Attribute_Error");

	@VisibleForTesting
	static final AdMessageKey MSG_BOM_VERSIONS_OVERLAPPING = AdMessageKey.of("PP_Product_BOMVersions_Overlapping");

	private final IProductBOMDAO bomRepo = Services.get(IProductBOMDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);
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

	public void verifyBOMAssignment(@NonNull final ProductPlanning planning, @NonNull final I_PP_Product_BOM productBom)
	{
		if (!planning.isAttributeDependant())
		{
			return;
		}

		final AttributeSetInstanceId planningASIId = planning.getAttributeSetInstanceId();
		final AttributesKey planningAttributesKeys = AttributesKeys.createAttributesKeyFromASIStorageAttributes(planningASIId).orElse(AttributesKey.NONE);

		if (planningAttributesKeys.isNone())
		{
			return;
		}

		final AttributeSetInstanceId productBomASIId = AttributeSetInstanceId.ofRepoIdOrNone(productBom.getM_AttributeSetInstance_ID());
		final AttributesKey productBOMAttributesKeys = AttributesKeys.createAttributesKeyFromASIStorageAttributes(productBomASIId).orElse(AttributesKey.NONE);

		if (!productBOMAttributesKeys.contains(planningAttributesKeys))
		{
			throw new AdempiereException(PP_PRODUCT_PLANNING_BOM_ATTR_ERROR);
		}
	}

	public void verifyDefaultBOMFor(@NonNull final ProductId productId)
	{
		productBOMBL.verifyDefaultBOMProduct(productId);
	}

	public void assertNoOverlapping(final I_PP_Product_BOM startBOM)
	{
		final ArrayList<I_PP_Product_BOM> allBOMs = new ArrayList<>();
		allBOMs.add(startBOM);
		allBOMs.addAll(bomRepo.getSiblings(startBOM));

		// Make sure they are ordered by ValidFrom (IMPORTANT)
		allBOMs.sort(Comparator.comparing(I_PP_Product_BOM::getValidFrom));

		for (int idx = 1, lastIdx = allBOMs.size() - 1; idx <= lastIdx; idx++)
		{
			final I_PP_Product_BOM prevBOM = allBOMs.get(idx - 1);
			final I_PP_Product_BOM currBOM = allBOMs.get(idx);
			final Timestamp nextBOM_start = idx + 1 <= lastIdx ? allBOMs.get(idx + 1).getValidFrom() : null;

			final Timestamp prevBOM_start = prevBOM.getValidFrom();
			final Timestamp currBOM_start = currBOM.getValidFrom();
			final Timestamp prevBOM_end = CoalesceUtil.coalesce(prevBOM.getValidTo(), currBOM_start);
			final Timestamp currBOM_end = CoalesceUtil.coalesce(currBOM.getValidTo(), nextBOM_start);

			if (TimeUtil.isOverlapping(prevBOM_start, prevBOM_end, currBOM_start, currBOM_end))
			{
				throw new AdempiereException(MSG_BOM_VERSIONS_OVERLAPPING, startBOM.getName())
						.markAsUserValidationError();
			}
		}
	}

}
