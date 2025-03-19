/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.handlingunits.impl;

import de.metas.handlingunits.HUItemType;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM_Conversion;
import org.slf4j.Logger;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Builder
public class CreatePackingInstructionsCommand
{
	private static final Logger log = LogManager.getLogger(CreatePackingInstructionsCommand.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ProductPackingInstructionsRequest request;

	public void execute()
	{
		// 1. Retrieve or create the Packing Instruction (M_HU_PI)
		final I_M_HU_PI piItem = findOrCreatePIItem(request.getM_HU_PI_Value());

		// 2. Link Packing Instruction to Product
		linkProductToPackingInstruction(request.getProductId(), piItem, request.isDefault(), request.isInfiniteCapacity());
	}

	@Value
	@Builder
	static class PIResult
	{
		@NonNull HuPackingInstructionsId piId;
		@NonNull String piName;
		@NonNull HuPackingInstructionsVersionId pivId;
	}

	/**
	 * Creates or retrieves a Packing Instruction (M_HU_PI)
	 */
	private I_M_HU_PI findOrCreatePIItem(@NonNull final String M_HU_PI_Value)
	{
		I_M_HU_PI huPi = queryBL.createQueryBuilder(I_M_HU_PI.class)
				.addEqualsFilter(I_M_HU_PI.COLUMNNAME_Name, M_HU_PI_Value)
				.first();

		// create M_HU_Item with M_HU_PI_Version
		if (huPi == null)
		{
			final I_M_HU_PI piRecord = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_HU_PI.class);
			piRecord.setName(M_HU_PI_Value);
			piRecord.setIsActive(true);
			saveRecord(piRecord);

			final I_M_HU_PI_Version pivRecord = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_HU_PI_Version.class);
			pivRecord.setM_HU_PI_ID(piRecord.getM_HU_PI_ID());
			pivRecord.setName(M_HU_PI_Value);
			pivRecord.setHU_UnitType(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
			pivRecord.setIsCurrent(true);
			pivRecord.setIsActive(true);
			InterfaceWrapperHelper.saveRecord(pivRecord);
		}

		return huPi;
	}

	/**
	 * Links a product to a Packing Instruction by creating an entry in M_HU_PI_Item_Product
	 * and  Handle Infinite Capacity Flag
	 */
	private void linkProductToPackingInstruction(@NonNull final ProductId productId,
												 @NonNull final I_M_HU_PI packingInstruction,
												 @NonNull final boolean isDefault,
												 @NonNull final boolean isInfiniteCapacity)
	{
		I_M_HU_PI_Item_Product productHuPi = queryBL.createQueryBuilder(I_M_HU_PI_Item_Product.class)
				.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_Product_ID, productId.getRepoId())
				.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_ID, packingInstruction.getM_HU_PI_ID())
				.first();

		if (productHuPi == null)
		{
			productHuPi = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
			productHuPi.setM_Product_ID(productId.getRepoId());
			productHuPi.setM_HU_PI_Item_ID(packingInstruction.getM_HU_PI_ID());
		}

		productHuPi.setIsDefaultForProduct(isDefault);
		productHuPi.setIsInfiniteCapacity(isInfiniteCapacity);
		InterfaceWrapperHelper.saveRecord(productHuPi);

		log.info("Linked Product {} to Packing Instruction {} (Default: {})", productId, packingInstruction.getName(), isDefault);
	}

	private I_M_HU_PI_Item createPIItem_IncludedHU(final PIResult lu, final PIResult tu, final int qtyTUsPerLU)
	{
		final I_M_HU_PI_Item luPIItemRecord = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item.class);
		luPIItemRecord.setM_HU_PI_Version_ID(lu.getPivId().getRepoId());
		luPIItemRecord.setItemType(HUItemType.HandlingUnit.getCode());
		luPIItemRecord.setQty(BigDecimal.valueOf(qtyTUsPerLU));
		luPIItemRecord.setIncluded_HU_PI_ID(tu.getPiId().getRepoId());
		saveRecord(luPIItemRecord);
		return luPIItemRecord;
	}

	/**
	 * Creates a UOM Conversion if it does not exist
	 */
	public void createUOMConversion(int fromUOM, int toUOM, BigDecimal multiplierRate)
	{
		if (fromUOM <= 0 || toUOM <= 0 || multiplierRate == null || multiplierRate.signum() == 0)
		{
			log.warn("Skipping UOM conversion: invalid parameters (fromUOM={}, toUOM={}, multiplierRate={})",
					fromUOM, toUOM, multiplierRate);
			return;
		}

		I_C_UOM_Conversion conversion = queryBL.createQueryBuilder(I_C_UOM_Conversion.class)
				.addEqualsFilter(I_C_UOM_Conversion.COLUMNNAME_C_UOM_ID, fromUOM)
				.addEqualsFilter(I_C_UOM_Conversion.COLUMNNAME_C_UOM_To_ID, toUOM)
				.first();

		if (conversion == null)
		{
			conversion = InterfaceWrapperHelper.newInstance(I_C_UOM_Conversion.class);
			conversion.setC_UOM_ID(fromUOM);
			conversion.setC_UOM_To_ID(toUOM);
			conversion.setMultiplyRate(multiplierRate);
			conversion.setIsActive(true);
			InterfaceWrapperHelper.saveRecord(conversion);

			log.info("Created new UOM conversion: {} -> {} with rate {}", fromUOM, toUOM, multiplierRate);
		}
		else
		{
			log.info("UOM conversion already exists: {} -> {} with rate {}", fromUOM, toUOM, conversion.getMultiplyRate());
		}
	}
}
