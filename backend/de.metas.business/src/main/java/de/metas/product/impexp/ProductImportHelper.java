package de.metas.product.impexp;

import de.metas.common.util.CoalesceUtil;
import de.metas.impexp.processing.IImportInterceptor;
import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.product.impexp.ProductsCache.Product;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.UOMConversionRate;
import de.metas.uom.UomId;
import de.metas.uom.UpdateUOMConversionRequest;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_I_Product;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidationEngine;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Builder
		/* package */ class ProductImportHelper
{
	@NonNull private static final Logger log = LogManager.getLogger(ProductImportHelper.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IUOMConversionDAO uomConversionDAO = Services.get(IUOMConversionDAO.class);
	@NonNull private final ProductImportProcess process;

	public void importRecord(final ProductImportContext context)
	{
		final ProductsCache cache = context.getProductsCache();

		final Product product;
		if (!context.isCurrentProductIdSet())
		{
			final I_M_Product productRecord = createProductRecordNoSave(context.getCurrentImportRecord());
			if (Check.isEmpty(productRecord.getName()))
			{
				productRecord.setName(productRecord.getValue());
			}

			product = cache.newProduct(productRecord);
		}
		else
		// Update existing product
		{
			product = context.getCurrentProduct();
			updateExistingProductNotSave(product.getRecord(), context.getCurrentImportRecord());
		}

		ModelValidationEngine.get().fireImportValidate(process, context.getCurrentImportRecord(), product.getRecord(), IImportInterceptor.TIMING_AFTER_IMPORT);

		product.save();
		context.setCurrentProductId(product.getIdOrNull());
	}

	private void updateExistingProductNotSave(@NonNull final I_M_Product product,
											  @NonNull final I_I_Product from)
	{
		product.setValue(from.getValue());
		product.setName(CoalesceUtil.coalesce(from.getName(), from.getValue()));

		if (from.getDescription() != null)
		{
			product.setDescription(from.getDescription());
		}
		if (from.getPackage_UOM_ID() > 0)
		{
			product.setPackage_UOM_ID(from.getPackage_UOM_ID());
		}
		if (from.getPackageSize() != null)
		{
			product.setPackageSize(from.getPackageSize());
		}
		product.setIsSold(from.isSold());
		product.setIsStocked(from.isStocked());
		if (from.getUPC() != null)
		{
			product.setUPC(from.getUPC());
		}
		if (from.getSKU() != null)
		{
			product.setSKU(from.getSKU());
		}

		// Set UOM, product category, and classification
		product.setC_UOM_ID(from.getC_UOM_ID());
		product.setM_Product_Category_ID(from.getM_Product_Category_ID());
		product.setClassification(from.getClassification());

		// Set product type
		product.setProductType(from.getProductType());

		// Conditionally set volume, weight, net weight, shelf dimensions, and units per pallet if provided
		if (from.getVolume() != null)
		{
			product.setVolume(from.getVolume());
		}
		if (from.getWeight() != null)
		{
			product.setWeight(from.getWeight());
		}
		if (from.getNetWeight() != null)
		{
			product.setNetWeight(from.getNetWeight());
		}
		if (from.getShelfWidth() != null)
		{
			product.setShelfWidth(from.getShelfWidth());
		}
		if (from.getShelfHeight() != null)
		{
			product.setShelfHeight(from.getShelfHeight());
		}
		if (from.getShelfDepth() != null)
		{
			product.setShelfDepth(from.getShelfDepth());
		}
		if (from.getUnitsPerPallet() > 0)
		{
			product.setUnitsPerPallet(BigDecimal.valueOf(from.getUnitsPerPallet()));
		}

		product.setDiscontinued(from.isDiscontinued());
		if (from.getDiscontinuedBy() != null)
		{
			product.setDiscontinuedBy(from.getDiscontinuedBy());
		}

		// Set customs and raw material origin if not null
		if (from.getRawMaterialOrigin_ID() > 0)
		{
			product.setRawMaterialOrigin_ID(from.getRawMaterialOrigin_ID());
		}
		if (from.getM_CustomsTariff_ID() > 0)
		{
			product.setM_CustomsTariff_ID(from.getM_CustomsTariff_ID());
		}

		// Set product planning schema selector, if not null
		if (from.getM_ProductPlanningSchema_Selector() != null)
		{
			product.setM_ProductPlanningSchema_Selector(from.getM_ProductPlanningSchema_Selector());
		}

	}

	private de.metas.adempiere.model.I_M_Product createProductRecordNoSave(@NonNull final I_I_Product importRecord)
	{
		final de.metas.adempiere.model.I_M_Product product = InterfaceWrapperHelper.newInstance(de.metas.adempiere.model.I_M_Product.class);
		product.setAD_Org_ID(importRecord.getAD_Org_ID());
		//
		product.setValue(importRecord.getValue());
		product.setName(importRecord.getName());
		product.setDescription(importRecord.getDescription());
		product.setDocumentNote(importRecord.getDocumentNote());
		product.setHelp(importRecord.getHelp());
		product.setUPC(importRecord.getUPC());
		product.setExternalId(importRecord.getExternalId());
		product.setSKU(importRecord.getSKU());
		product.setC_UOM_ID(importRecord.getC_UOM_ID());
		product.setPackage_UOM_ID(importRecord.getPackage_UOM_ID());
		product.setPackageSize(importRecord.getPackageSize());
		product.setManufacturer_ID(importRecord.getManufacturer_ID());
		product.setM_Product_Category_ID(importRecord.getM_Product_Category_ID());
		product.setProductType(importRecord.getProductType());
		product.setImageURL(importRecord.getImageURL());
		product.setDescriptionURL(importRecord.getDescriptionURL());
		product.setIsSold(importRecord.isSold());
		product.setIsStocked(importRecord.isStocked());
		product.setNetWeight(importRecord.getNetWeight());
		product.setM_CustomsTariff_ID(importRecord.getM_CustomsTariff_ID());
		product.setRawMaterialOrigin_ID(importRecord.getRawMaterialOrigin_ID());
		product.setWeight(importRecord.getWeight());
		product.setM_ProductPlanningSchema_Selector(importRecord.getM_ProductPlanningSchema_Selector()); // #3406
		product.setTrademark(importRecord.getTrademark());
		product.setPZN(importRecord.getPZN());
		product.setIsCommissioned(importRecord.isCommissioned());

		return product;
	}    // MProduct

	public void createCatchWeightUOMConversion(@NonNull final I_I_Product importRecord)
	{
		final UomId fromUomId = UomId.ofRepoIdOrNull(importRecord.getQtyCU_UOM_ID());
		if (fromUomId == null)
		{
			throw new AdempiereException(I_I_Product.COLUMNNAME_QtyCU_UOM_ID + " is mandatory for catch weight products");
		}

		final UomId toUomId = UomId.ofRepoId(importRecord.getC_UOM_ID());

		if (fromUomId.equals(toUomId))
		{
			return;
		}

		final ProductId productId = ProductId.ofRepoId(importRecord.getM_Product_ID());
		final UOMConversionRate productRate = uomConversionDAO.getProductConversions(productId).getRateIfExists(fromUomId, toUomId).orElse(null);
		if (productRate != null)
		{
			final BigDecimal multiplierRate = extractMultiplierRate(importRecord);
			uomConversionDAO.updateUOMConversion(UpdateUOMConversionRequest.builder()
					.productId(productId)
					.fromUomId(fromUomId)
					.toUomId(toUomId)
					.fromToMultiplier(multiplierRate != null ? multiplierRate : productRate.getFromToMultiplier())
					.catchUOMForProduct(true) // make sure it's the catch UOM
					.build());
		}
		else
		{
			BigDecimal multiplierRate = extractMultiplierRate(importRecord);
			if (multiplierRate == null)
			{
				multiplierRate = uomConversionDAO.getGenericConversions().getRateIfExists(fromUomId, toUomId)
						.map(UOMConversionRate::getFromToMultiplier)
						.orElseThrow(() -> new AdempiereException("UOM Conversion rate not set and no generic conversion found for " + fromUomId + " -> " + toUomId + "!"));
			}

			uomConversionDAO.createUOMConversion(CreateUOMConversionRequest.builder()
					.productId(productId)
					.fromUomId(fromUomId)
					.toUomId(toUomId)
					.fromToMultiplier(multiplierRate)
					.catchUOMForProduct(true) // make sure it's the catch UOM
					.build());
		}
	}

	@Nullable
	private static BigDecimal extractMultiplierRate(final @NonNull I_I_Product importRecord)
	{
		return !InterfaceWrapperHelper.isNull(importRecord, I_I_Product.COLUMNNAME_UOM_MultiplierRate)
				? importRecord.getUOM_MultiplierRate()
				: null;
	}
}
