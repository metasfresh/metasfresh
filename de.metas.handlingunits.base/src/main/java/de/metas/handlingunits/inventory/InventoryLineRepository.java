package de.metas.handlingunits.inventory;

import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.isNew;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOrNew;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.compiere.util.Util.coalesceSuppliers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Inventory;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.InventoryLine.InventoryLineBuilder;
import de.metas.handlingunits.inventory.InventoryLineAggregator.AggregationType;
import de.metas.handlingunits.inventory.InventoryLineHU.InventoryLineHUId;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.model.I_M_InventoryLine_HU;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineId;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;

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

@Repository
public class InventoryLineRepository
{
	public InventoryLine getById(@NonNull final InventoryLineId inventoryLineId)
	{
		final I_M_InventoryLine inventoryLineRecord = load(inventoryLineId, I_M_InventoryLine.class);
		return ofRecord(inventoryLineRecord);
	}

	public ImmutableList<InventoryLine> getByInventoryId(@NonNull final InventoryId inventoryId)
	{
		final List<I_M_InventoryLine> inventoryLineRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_InventoryLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InventoryLine.COLUMNNAME_M_Inventory_ID, inventoryId)
				.orderBy(I_M_InventoryLine.COLUMNNAME_Line)
				.create()
				.list();

		final ImmutableList.Builder<InventoryLine> result = ImmutableList.builder();
		for (final I_M_InventoryLine inventoryLineRecord : inventoryLineRecords)
		{
			result.add(ofRecord(inventoryLineRecord));
		}

		return result.build();
	}

	private InventoryLine ofRecord(@NonNull final I_M_InventoryLine inventoryLineRecord)
	{
		final AttributeSetInstanceId asiId = AttributeSetInstanceId
				.ofRepoIdOrNone(inventoryLineRecord.getM_AttributeSetInstance_ID());

		final AttributesKey storageAttributesKey = AttributesKeys
				.createAttributesKeyFromASIStorageAttributes(asiId)
				.orElse(AttributesKey.NONE);

		final InventoryLineId inventoryLineId = InventoryLineId.ofRepoId(inventoryLineRecord.getM_InventoryLine_ID());

		final InventoryLineBuilder lineBuilder = InventoryLine
				.builder()
				.id(inventoryLineId)
				.locatorId(LocatorId.ofRecord(inventoryLineRecord.getM_Locator()))
				.productId(ProductId.ofRepoId(inventoryLineRecord.getM_Product_ID()))
				.asiId(asiId)
				.storageAttributesKey(storageAttributesKey)
				.inventoryId(InventoryId.ofRepoId(inventoryLineRecord.getM_Inventory_ID()));

		final List<I_M_InventoryLine_HU> inventoryLineHURecords = createInventoryLineHUQuery(inventoryLineRecord)
				.list();

		final boolean singleHUAggregation = AggregationType.SINGLE_HU.getValue().equals(inventoryLineRecord.getHUAggregationType());
		lineBuilder.singleHUAggregation(singleHUAggregation);
		if (singleHUAggregation)
		{
			final I_C_UOM uomRecord = inventoryLineRecord.getC_UOM();
			final InventoryLineHU inventoryLineHU = InventoryLineHU
					.builder()
					.bookQty(Quantity.of(inventoryLineRecord.getQtyBook(), uomRecord))
					.countQty(Quantity.of(inventoryLineRecord.getQtyCount(), uomRecord))
					.huId(HuId.ofRepoId(inventoryLineRecord.getM_HU_ID()))
					.build();

			lineBuilder.inventoryLineHU(inventoryLineHU);
		}
		else
		{
			for (final I_M_InventoryLine_HU inventoryLineHURecord : inventoryLineHURecords)
			{
				lineBuilder.inventoryLineHU(
						ofInventoryLineHURecord(
								inventoryLineRecord,
								inventoryLineHURecord));
			}
		}

		return lineBuilder.build();
	}

	/**
	 * @param inventoryLineRecord needed because we want all quantities to be in the inventory line's UOM.
	 */
	private InventoryLineHU ofInventoryLineHURecord(
			@NonNull final I_M_InventoryLine inventoryLineRecord,
			@NonNull final I_M_InventoryLine_HU inventoryLineHURecord)
	{
		final IUOMConversionBL convBL = Services.get(IUOMConversionBL.class);

		final UomId lineUomId = UomId.ofRepoId(inventoryLineRecord.getC_UOM_ID());
		final ProductId lineProductId = ProductId.ofRepoId(inventoryLineRecord.getM_Product_ID());
		final UOMConversionContext conversionCtx = UOMConversionContext.of(lineProductId);

		final I_C_UOM uomRecord = inventoryLineHURecord.getC_UOM();
		final Quantity bookQty = Quantity.of(inventoryLineHURecord.getQtyBook(), uomRecord);
		final Quantity countQty = Quantity.of(inventoryLineHURecord.getQtyCount(), uomRecord);

		final InventoryLineHU inventoryLineHU = InventoryLineHU
				.builder()
				.id(InventoryLineHUId.ofRepoId(inventoryLineHURecord.getM_InventoryLine_HU_ID()))
				.bookQty(convBL.convertQuantityTo(bookQty, conversionCtx, lineUomId))
				.countQty(convBL.convertQuantityTo(countQty, conversionCtx, lineUomId))
				.huId(HuId.ofRepoId(inventoryLineHURecord.getM_HU_ID()))
				.build();

		return inventoryLineHU;
	}

	public void save(@NonNull final InventoryLine inventoryLine)
	{
		final I_M_Inventory inventoryRecord = load(inventoryLine.getInventoryId(), I_M_Inventory.class);

		final I_M_InventoryLine inventoryLineRecord = loadOrNew(inventoryLine.getId(), I_M_InventoryLine.class, inventoryRecord);
		inventoryLineRecord.setAD_Org_ID(inventoryRecord.getAD_Org_ID());
		inventoryLineRecord.setM_Inventory_ID(inventoryLine.getInventoryId().getRepoId());

		final AttributesKey storageAttributesKey = inventoryLine.getStorageAttributesKey();

		final AttributeSetInstanceId asiId = coalesceSuppliers(
				() -> inventoryLine.getAsiId(),
				() -> AttributesKeys.createAttributeSetInstanceFromAttributesKey(storageAttributesKey));
		inventoryLineRecord.setM_AttributeSetInstance_ID(asiId.getRepoId());

		inventoryLineRecord.setM_Locator_ID(inventoryLine.getLocatorId().getRepoId());
		inventoryLineRecord.setM_Product_ID(inventoryLine.getProductId().getRepoId());

		if (inventoryLine.isSingleHUAggregation())
		{
			inventoryLineRecord.setHUAggregationType(AggregationType.SINGLE_HU.getValue());
			if (!isNew(inventoryRecord))
			{
				createInventoryLineHUQuery(inventoryLineRecord).delete();
			}

			final InventoryLineHU hu = CollectionUtils.singleElement(inventoryLine.getInventoryLineHUs());
			inventoryLineRecord.setM_HU_ID(hu.getHuId().getRepoId());

			final Quantity countQty = hu.getCountQty();
			inventoryLineRecord.setQtyCount(countQty.getAsBigDecimal());

			final Quantity bookQty = hu.getBookQty();
			inventoryLineRecord.setQtyBook(bookQty.getAsBigDecimal());

			final UomId uomId = Quantity.getCommonUomIdOfAll(countQty, bookQty);
			inventoryLineRecord.setC_UOM_ID(uomId.getRepoId());
		}
		else
		{
			inventoryLineRecord.setHUAggregationType(AggregationType.MULTIPLE_HUS.getValue());

			final ImmutableList<InventoryLineHU> inventoryLineHUs = inventoryLine.getInventoryLineHUs();
			if (inventoryLineHUs.isEmpty())
			{
				inventoryLineRecord.setQtyBook(ZERO);
				inventoryLineRecord.setQtyCount(ZERO);

				final int stockingUomId = Services.get(IProductBL.class).getStockingUOMId(inventoryLine.getProductId()).getRepoId();
				inventoryLineRecord.setC_UOM_ID(stockingUomId);
			}
			else
			{
				final UomId countUomId = CollectionUtils.extractSingleElement(inventoryLineHUs, l -> l.getCountQty().getUomId());
				final UomId bookUomId = CollectionUtils.extractSingleElement(inventoryLineHUs, l -> l.getBookQty().getUomId());

				Quantity countQty = Quantity.zero(load(countUomId, I_C_UOM.class));
				Quantity bookQty = Quantity.zero(load(bookUomId, I_C_UOM.class));

				for (final InventoryLineHU inventoryLineHU : inventoryLineHUs)
				{
					countQty = countQty.add(inventoryLineHU.getCountQty());
					bookQty = bookQty.add(inventoryLineHU.getBookQty());
				}
				inventoryLineRecord.setQtyBook(bookQty.getAsBigDecimal());
				inventoryLineRecord.setQtyCount(countQty.getAsBigDecimal());

				final UomId uomId = Quantity.getCommonUomIdOfAll(bookQty, countQty);
				inventoryLineRecord.setC_UOM_ID(uomId.getRepoId());
			}
		}

		saveRecord(inventoryLineRecord);

		if (!inventoryLine.isSingleHUAggregation())
		{
			inventoryLineRecord.setM_HU_ID(0);
			// inventoryLine.setM_HU_PI_Item_Product(null); // TODO
			// inventoryLine.setQtyTU(BigDecimal.ZERO); // TODO

			final ImmutableList<I_M_InventoryLine_HU> createdOrUpdatedInventoryLineHURecords //
					= createOrUpdateInventoryLineHURecords(inventoryLine, inventoryLineRecord);

			createdOrUpdatedInventoryLineHURecords.forEach(InterfaceWrapperHelper::saveRecord);
		}
	}

	private ImmutableList<I_M_InventoryLine_HU> createOrUpdateInventoryLineHURecords(
			@NonNull final InventoryLine inventoryLine,
			@NonNull final I_M_InventoryLine inventoryLineRecord)
	{
		final Map<InventoryLineHUId, I_M_InventoryLine_HU> inventoryLineHURecords = createInventoryLineHUQuery(inventoryLineRecord)
				.stream()
				.collect(Collectors.toMap(
						l -> InventoryLineHUId.ofRepoId(l.getM_InventoryLine_HU_ID()),
						l -> l));

		final ImmutableList.Builder<I_M_InventoryLine_HU> result = ImmutableList.builder();

		for (final InventoryLineHU inventoryLineHU : inventoryLine.getInventoryLineHUs())
		{
			final I_M_InventoryLine_HU inventoryLineHURecord = coalesceSuppliers(
					() -> inventoryLineHURecords.remove(inventoryLineHU.getId()),
					() -> loadOrNew(inventoryLineHU.getId(), I_M_InventoryLine_HU.class, inventoryLineRecord));

			inventoryLineHURecord.setM_InventoryLine(inventoryLineRecord);

			inventoryLineHURecord.setM_HU_ID(inventoryLineHU.getHuId().getRepoId());

			final Quantity countQty = inventoryLineHU.getCountQty();
			inventoryLineHURecord.setQtyCount(countQty.getAsBigDecimal());

			final Quantity bookQty = inventoryLineHU.getBookQty();
			inventoryLineHURecord.setQtyBook(bookQty.getAsBigDecimal());

			final UomId uomId = Quantity.getCommonUomIdOfAll(countQty, bookQty);
			inventoryLineHURecord.setC_UOM_ID(uomId.getRepoId());

			result.add(inventoryLineHURecord);
		}

		// the pre-existing records that we did not see until now are not needed anymore; delete them.
		inventoryLineHURecords
				.values()
				.forEach(InterfaceWrapperHelper::delete);

		return result.build();
	}

	private IQuery<I_M_InventoryLine_HU> createInventoryLineHUQuery(@NonNull final I_M_InventoryLine inventoryLineRecord)
	{
		return Services
				.get(IQueryBL.class)
				.createQueryBuilder(I_M_InventoryLine_HU.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InventoryLine_HU.COLUMNNAME_M_InventoryLine_ID, inventoryLineRecord.getM_InventoryLine_ID())
				.create();
	}
}
