package de.metas.handlingunits.inventory.interceptor;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.handlingunits.inventory.InventoryRepository;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.InventoryLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_UOM;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Stream;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Interceptor(I_M_InventoryLine.class)
@Component
public class M_InventoryLine
{
	private final InventoryRepository inventoryLineRepository;
	private final IHUStorageDAO huStorageDAO = Services.get(IHandlingUnitsBL.class).getStorageFactory().getHUStorageDAO();
	private final IUOMConversionBL uomConversionBL =  Services.get(IUOMConversionBL.class);

	public M_InventoryLine(@NonNull final InventoryRepository inventoryLineRepository)
	{
		this.inventoryLineRepository = inventoryLineRepository;
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_M_InventoryLine.COLUMNNAME_QtyCount)
	public void distributeQuantityToHUs(@NonNull final I_M_InventoryLine inventoryLineRecord)
	{
		final HUAggregationType huAggregationType = extractHUAggregationType(inventoryLineRecord);
		if (HUAggregationType.SINGLE_HU.equals(huAggregationType))
		{
			return; // nothing to do
		}
		else
		{
			final Quantity qtyCount = extractQtyCount(inventoryLineRecord);

			final InventoryLine inventoryLine = inventoryLineRepository
					.toInventoryLine(inventoryLineRecord)
					.distributeQtyCountToHUs(qtyCount);

			inventoryLineRepository.saveInventoryLineHURecords(inventoryLine);
		}
	}

	private Quantity extractQtyCount(final I_M_InventoryLine inventoryLineRecord)
	{
		final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

		final I_C_UOM uom = uomsRepo.getById(inventoryLineRecord.getC_UOM_ID());
		return Quantity.of(inventoryLineRecord.getQtyCount(), uom);
	}

	@Nullable
	private static HUAggregationType extractHUAggregationType(final I_M_InventoryLine inventoryLineRecord)
	{
		return HUAggregationType.ofNullableCode(inventoryLineRecord.getHUAggregationType());
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteInventoryLineHURecords(@NonNull final I_M_InventoryLine inventoryLineRecord)
	{
		final InventoryLineId inventoryLineId = InventoryLineId.ofRepoId(inventoryLineRecord.getM_InventoryLine_ID());
		inventoryLineRepository.deleteInventoryLineHUs(inventoryLineId);
	}

	@ModelChange(timings = {ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW},
			ifColumnsChanged = {I_M_InventoryLine.COLUMNNAME_M_HU_ID, I_M_InventoryLine.COLUMNNAME_M_Product_ID, I_M_InventoryLine.COLUMNNAME_C_UOM_ID})
	public void setQtyBookedFromHU(@NonNull final I_M_InventoryLine inventoryLineRecord)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(inventoryLineRecord.getM_Product_ID());
		final UomId inventoryLineUOMId = UomId.ofRepoIdOrNull(inventoryLineRecord.getC_UOM_ID());
		final HuId huId = HuId.ofRepoIdOrNull(inventoryLineRecord.getM_HU_ID());

		final boolean allIdsPresent = Stream.of(huId, productId, inventoryLineUOMId)
				.allMatch(Objects::nonNull);

		if (allIdsPresent)
		{
			final I_M_HU_Storage huStorage = huStorageDAO.retrieveStorage(inventoryLineRecord.getM_HU(), productId);

			if (huStorage != null)
			{
				final UomId storageUOMId = UomId.ofRepoId(huStorage.getC_UOM_ID());

				final BigDecimal qtyInInventoryUOM = uomConversionBL.convertQty(
						UOMConversionContext.of(productId),
						huStorage.getQty(),
						storageUOMId,
						inventoryLineUOMId);

				inventoryLineRecord.setQtyBook(qtyInInventoryUOM);
			}
		}
		else
		{
			inventoryLineRecord.setQtyBook(BigDecimal.ZERO);
		}
	}
}
