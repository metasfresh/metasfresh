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

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HUItemType;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.logging.LogManager;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Builder
class CreateTUPackingInstructionsCommand
{
	private static final LocalDate DEFAULT_ValidFrom = LocalDate.parse("2000-01-01");

	@NonNull private static final Logger log = LogManager.getLogger(CreateTUPackingInstructionsCommand.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO;

	@NonNull private final CreateTUPackingInstructionsRequest request;

	public void execute()
	{
		final I_M_HU_PI_Item piItem = getOrCreatePIItem();
		createHUPIItemProduct(piItem);
	}

	private I_M_HU_PI_Item getOrCreatePIItem()
	{
		I_M_HU_PI huPI = queryBL.createQueryBuilder(I_M_HU_PI.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_PI.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH)
				.addEqualsFilter(I_M_HU_PI.COLUMNNAME_Name, request.getName())
				.firstOnly();

		if (huPI == null)
		{
			huPI = InterfaceWrapperHelper.newInstance(I_M_HU_PI.class);
			huPI.setName(request.getName());
			huPI.setIsActive(true);
			saveRecord(huPI);

			final I_M_HU_PI_Version pivRecord = createTUPIVersion(huPI);
			return createPIItemMA(pivRecord);
		}
		else
		{
			final I_M_HU_PI_Version tuPIVersion = getOrCreateTUPIVersion(huPI);
			return getOrCreatePIItemMA(tuPIVersion);
		}
	}

	@NonNull
	private I_M_HU_PI_Version getOrCreateTUPIVersion(final I_M_HU_PI huPI)
	{
		final I_M_HU_PI_Version tuPIVersion = handlingUnitsDAO.retrievePICurrentVersionOrNull(huPI);
		return tuPIVersion == null ? createTUPIVersion(huPI) : tuPIVersion;
	}

	@NotNull
	private I_M_HU_PI_Version createTUPIVersion(@NonNull final I_M_HU_PI piRecord)
	{
		final I_M_HU_PI_Version pivRecord = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Version.class);
		pivRecord.setM_HU_PI_ID(piRecord.getM_HU_PI_ID());
		pivRecord.setName(piRecord.getName());
		pivRecord.setHU_UnitType(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		pivRecord.setIsCurrent(true);
		pivRecord.setIsActive(true);
		saveRecord(pivRecord);
		return pivRecord;
	}

	private void createHUPIItemProduct(@NonNull final I_M_HU_PI_Item item)
	{
		final Quantity qtyCU = request.getQtyCU();
		final IQueryBuilder<I_M_HU_PI_Item_Product> queryBuilder = queryBL.createQueryBuilder(I_M_HU_PI_Item_Product.class)
				.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_ID, item.getM_HU_PI_Item_ID())
				.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_Product_ID, request.getProductId())
				.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_C_UOM_ID, qtyCU.getUomId())
				.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_IsInfiniteCapacity, qtyCU.isInfinite())
				.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_C_BPartner_ID, request.getBpartnerId());
		if (!qtyCU.isInfinite())
		{
			queryBuilder.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_Qty, qtyCU.toBigDecimal());
		}

		I_M_HU_PI_Item_Product piItemProduct = queryBuilder.firstOnly();
		if (piItemProduct == null)
		{
			piItemProduct = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
			piItemProduct.setValidFrom(TimeUtil.asTimestamp(DEFAULT_ValidFrom));
			piItemProduct.setM_HU_PI_Item_ID(item.getM_HU_PI_Item_ID());
			piItemProduct.setM_Product_ID(request.getProductId().getRepoId());
			piItemProduct.setIsInfiniteCapacity(qtyCU.isInfinite());
			piItemProduct.setC_UOM_ID(qtyCU.getUomId().getRepoId());
			piItemProduct.setQty(qtyCU.isInfinite() ? BigDecimal.ZERO : qtyCU.toBigDecimal());
			piItemProduct.setC_BPartner_ID(BPartnerId.toRepoId(request.getBpartnerId()));
		}

		piItemProduct.setIsDefaultForProduct(request.isDefault());
		saveRecord(piItemProduct);

		log.info("Linked Product {} to Packing Instruction {})", request.getProductId(), item.getM_HU_PI_Item_ID());
	}

	private I_M_HU_PI_Item getOrCreatePIItemMA(@NonNull final I_M_HU_PI_Version tuPIVersion)
	{
		return retrievePIItemMA(tuPIVersion)
				.orElseGet(() -> createPIItemMA(tuPIVersion));
	}

	private Optional<I_M_HU_PI_Item> retrievePIItemMA(@NonNull final I_M_HU_PI_Version version)
	{
		return handlingUnitsDAO.retrieveFirstPIItem(version, HUItemType.Material, request.getBpartnerId());
	}

	private I_M_HU_PI_Item createPIItemMA(final I_M_HU_PI_Version pivRecord)
	{
		final I_M_HU_PI_Item piItem = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item.class);
		piItem.setM_HU_PI_Version_ID(pivRecord.getM_HU_PI_Version_ID());
		piItem.setItemType(HUItemType.Material.getCode());
		saveRecord(piItem);
		return piItem;
	}
}
