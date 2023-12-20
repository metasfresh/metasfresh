package org.eevolution.model.validator;

import com.google.common.collect.ImmutableSet;
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
import org.adempiere.model.CopyRecordFactory;
import org.compiere.model.ModelValidator;
import org.eevolution.api.BOMType;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.api.impl.ProductBOMService;
import org.eevolution.api.impl.ProductBOMVersionsDAO;
import org.eevolution.callout.PP_Product_BOM_TabCallout;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMVersions;
import org.eevolution.model.impl.PP_Product_BOM_POCopyRecordSupport;

import java.sql.Timestamp;
import java.util.Optional;

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

	private final IProductBOMDAO bomDAO = Services.get(IProductBOMDAO.class);

	private static final AdMessageKey MSG_BOM_VERSIONS_NOT_MATCH = AdMessageKey.of("PP_Product_BOMVersions_BOM_Doesnt_Match");
	private static final AdMessageKey MSG_BOM_VERSIONS_OVERLAPPING = AdMessageKey.of("PP_Product_BOMVersions_Overlapping");
	private static final AdMessageKey MSG_VALID_TO_BEFORE_VALID_FROM = AdMessageKey.of("PP_Product_BOMVersions_ValidTo_Before_ValidFrom");

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
		CopyRecordFactory.registerCopyRecordSupport(I_PP_Product_BOM.Table_Name, PP_Product_BOM_POCopyRecordSupport.class);

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
			throw new AdempiereException(MSG_BOM_VERSIONS_NOT_MATCH)
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

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_PP_Product_BOM.COLUMNNAME_ValidFrom, I_PP_Product_BOM.COLUMNNAME_ValidTo })
	public void preventBOMVersionsOverlapping(final I_PP_Product_BOM productBom)
	{
		if (productBom.getValidTo() != null && productBom.getValidTo().before(productBom.getValidFrom()))
		{
			throw new AdempiereException(MSG_VALID_TO_BEFORE_VALID_FROM);
		}

		final ProductBOMVersionsId bomVersionsId = ProductBOMVersionsId.ofRepoId(productBom.getPP_Product_BOMVersions_ID());
		final BOMType bomType = BOMType.ofNullableCode(productBom.getBOMType());
		final Optional<I_PP_Product_BOM> latestBOMVersions = bomDAO.getLatestBOMRecordByVersionAndType(bomVersionsId, ImmutableSet.of(bomType));

		if (!latestBOMVersions.isPresent())
		{
			return;
		}
		else
		{
			final I_PP_Product_BOM latestBOMVersion = latestBOMVersions.get();

			// Check if the BOM being updated is the same as the latest BOM version
			if (productBom.getPP_Product_BOM_ID() != latestBOMVersion.getPP_Product_BOM_ID())
			{
				final Timestamp newValidFrom = productBom.getValidFrom();
				final Timestamp newValidTo = productBom.getValidTo();
				final Timestamp existingValidFrom = latestBOMVersion.getValidFrom();
				final Timestamp existingValidTo = latestBOMVersion.getValidTo();

				// Check for overlap
				if (isOverlapping(newValidFrom, newValidTo, existingValidFrom, existingValidTo))
				{
					throw new AdempiereException(MSG_BOM_VERSIONS_OVERLAPPING,
												 productBom.getName())
							.markAsUserValidationError();
				}
			}
		}
	}

	private boolean isOverlapping(final Timestamp newFrom, final Timestamp newTo, final Timestamp existingFrom, final Timestamp existingTo)
	{
		// Scenario 1: Both ValidTo dates are not null
		if (newTo != null && existingTo != null)
		{
			return newFrom.before(existingTo) || newTo.before(existingTo);
		}

		// Scenario 2: Only newTo is null (open-ended new range)
		if (newTo == null && existingTo != null)
		{
			return newFrom.before(existingTo);
		}

		// Scenario 3: Only existingTo is null (open-ended existing range)
		if (newTo != null)
		{
			return newTo.before(existingFrom) || newFrom.before(existingFrom);
		}

		// Scenario 4: Both ValidTo dates are null (both ranges are open-ended)
		return newFrom.before(existingFrom);
	}

}
