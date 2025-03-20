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

import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HUItemType;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM_Conversion;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Builder
public class CreatePackingInstructionsCommand
{
	private static final Logger log = LogManager.getLogger(CreatePackingInstructionsCommand.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	@NonNull private final ProductPackingInstructionsRequest request;

	public void execute()
	{
		// 1. Retrieve or create the Packing Instruction (M_HU_PI)
		final I_M_HU_PI_Item piItem = findOrCreatePIItem(request.getM_HU_PI_Value());

		// 2. Link Packing Instruction to Product
		linkProductToPackingInstruction(request, piItem);
	}

	/**
	 * Creates or retrieves a Packing Instruction (M_HU_PI)
	 */
	private I_M_HU_PI_Item findOrCreatePIItem(@NonNull final String M_HU_PI_Value)
	{
		I_M_HU_PI huPi = queryBL.createQueryBuilder(I_M_HU_PI.class)
				.addEqualsFilter(I_M_HU_PI.COLUMNNAME_Name, M_HU_PI_Value)
				.orderByDescending(I_M_HU_PI.COLUMNNAME_Created)
				.first();

		// create M_HU_Item with M_HU_PI_Version
		if (huPi == null)
		{
			final I_M_HU_PI piRecord = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_HU_PI.class);
			piRecord.setName(M_HU_PI_Value);
			piRecord.setIsActive(true);
			saveRecord(piRecord);

			final I_M_HU_PI_Version pivRecord = createHUPIVersion(M_HU_PI_Value, piRecord);

			return createPIItemHU(pivRecord);
		}

		I_M_HU_PI_Version tuPIVersion = handlingUnitsDAO.retrievePICurrentVersionOrNull(huPi);
		if (tuPIVersion == null)
		{
			tuPIVersion = createHUPIVersion(M_HU_PI_Value, huPi);
		}

		I_M_HU_PI_Item item = retrievePIItem(tuPIVersion);
		if (item == null)
		{
			item = createPIItemHU(tuPIVersion);
		}

		return item;
	}

	private I_M_HU_PI_Item retrievePIItem(@NonNull final I_M_HU_PI_Version version)
	{
		final List<I_M_HU_PI_Item> items = handlingUnitsDAO.retrieveAllPIItems(version);
		if (items.isEmpty() || items.size() > 1)
		{
			return null;
		}

		return items.get(0);

	}

	private @NotNull I_M_HU_PI_Version createHUPIVersion(final @NotNull String M_HU_PI_Value, final I_M_HU_PI piRecord)
	{
		final I_M_HU_PI_Version pivRecord = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_HU_PI_Version.class);
		pivRecord.setM_HU_PI_ID(piRecord.getM_HU_PI_ID());
		pivRecord.setName(M_HU_PI_Value);
		pivRecord.setHU_UnitType(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		pivRecord.setIsCurrent(true);
		pivRecord.setIsActive(true);
		saveRecord(pivRecord);
		return pivRecord;
	}

	/**
	 * Links a product to a Packing Instruction by creating an entry in M_HU_PI_Item_Product
	 * and  Handle Infinite Capacity Flag
	 */
	private void linkProductToPackingInstruction(@NonNull final ProductPackingInstructionsRequest request,
												 @NonNull final I_M_HU_PI_Item item)
	{
		I_M_HU_PI_Item_Product productHuPi = queryBL.createQueryBuilder(I_M_HU_PI_Item_Product.class)
				.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_Product_ID, request.getProductId())
				.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_ID, item.getM_HU_PI_Item_ID())
				.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_Qty, request.getQtyCU())
				.firstOnly();

		if (productHuPi == null)
		{
			productHuPi = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
			productHuPi.setM_Product_ID(request.getProductId().getRepoId());
			productHuPi.setM_HU_PI_Item_ID(item.getM_HU_PI_Item_ID());
			productHuPi.setQty(request.getQtyCU());
			if (request.getBPartnerId()!=null)
			{
				productHuPi.setC_BPartner_ID(request.getBPartnerId().getRepoId());
			}
			productHuPi.setValidFrom(SystemTime.asTimestamp());
		}

		productHuPi.setIsDefaultForProduct(request.isDefault());
		productHuPi.setIsInfiniteCapacity(request.isInfiniteCapacity());
		saveRecord(productHuPi);

		log.info("Linked Product {} to Packing Instruction {})", request.getProductId(), item.getM_HU_PI_Item_ID());
	}

	private I_M_HU_PI_Item createPIItemHU(final I_M_HU_PI_Version pivRecord)
	{
		final I_M_HU_PI_Item luPIItemRecord = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item.class);
		luPIItemRecord.setM_HU_PI_Version_ID(pivRecord.getM_HU_PI_Version_ID());
		luPIItemRecord.setItemType(HUItemType.HandlingUnit.getCode());
		luPIItemRecord.setQty(BigDecimal.ONE);
		saveRecord(luPIItemRecord);
		return luPIItemRecord;
	}
}
