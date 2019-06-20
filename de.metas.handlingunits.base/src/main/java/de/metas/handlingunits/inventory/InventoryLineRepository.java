
package de.metas.handlingunits.inventory;

import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwares;
import static org.adempiere.model.InterfaceWrapperHelper.loadOrNew;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.compiere.util.Util.coalesceSuppliers;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.OrgId;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_UOM;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.InventoryLine.InventoryLineBuilder;
import de.metas.handlingunits.inventory.InventoryLineHU.InventoryLineHUId;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.model.I_M_InventoryLine_HU;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineId;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.GuavaCollectors;
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
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);
	private final IUOMConversionBL convBL = Services.get(IUOMConversionBL.class);
	private final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
	private final IProductBL productsService = Services.get(IProductBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public InventoryLine getById(@NonNull final InventoryLineId inventoryLineId)
	{
		final I_M_InventoryLine inventoryLineRecord = getInventoryLineRecordById(inventoryLineId);
		final List<I_M_InventoryLine_HU> inventoryLineHURecords = retrieveInventoryLineHURecords(inventoryLineId);
		return ofRecord(inventoryLineRecord, inventoryLineHURecords);
	}

	private I_M_InventoryLine getInventoryLineRecordById(@Nullable final InventoryLineId inventoryLineId)
	{
		return load(inventoryLineId, I_M_InventoryLine.class);
	}

	@Deprecated
	final I_M_InventoryLine getInventoryLineRecordFor(@NonNull final InventoryLine inventoryLine)
	{
		return getInventoryLineRecordById(inventoryLine.getId());
	}

	private HashMap<InventoryLineId, I_M_InventoryLine> getInventoryLineRecordsByIds(@Nullable final Set<InventoryLineId> inventoryLineIds)
	{
		return loadByRepoIdAwares(inventoryLineIds, I_M_InventoryLine.class)
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(record -> extractInventoryLineId(record)));
	}

	private static InventoryLineId extractInventoryLineId(@NonNull final I_M_InventoryLine record)
	{
		return InventoryLineId.ofRepoId(record.getM_InventoryLine_ID());
	}

	private static InventoryLineId extractInventoryLineIdOrNull(@NonNull final I_M_InventoryLine record)
	{
		return InventoryLineId.ofRepoIdOrNull(record.getM_InventoryLine_ID());
	}

	private I_M_InventoryLine_HU getOrNewInventoryLineHURecordById(@Nullable final InventoryLineHUId inventoryLineHUId)
	{
		return loadOrNew(inventoryLineHUId, I_M_InventoryLine_HU.class);
	}

	public InventoryLines getByInventoryId(@NonNull final InventoryId inventoryId)
	{
		final Collection<I_M_InventoryLine> inventoryLineRecords = retrieveLineRecords(inventoryId);
		final ImmutableSet<InventoryLineId> inventoryLineIds = inventoryLineRecords.stream().map(r -> extractInventoryLineId(r)).collect(ImmutableSet.toImmutableSet());

		final ListMultimap<InventoryLineId, I_M_InventoryLine_HU> inventoryLineHURecords = retrieveInventoryLineHURecords(inventoryLineIds);

		final List<InventoryLine> inventoryLines = inventoryLineRecords
				.stream()
				.map(inventoryLineRecord -> ofRecord(inventoryLineRecord, inventoryLineHURecords))
				.collect(ImmutableList.toImmutableList());

		return InventoryLines.ofList(inventoryLines);
	}

	public InventoryLine ofRecord(@NonNull final I_M_InventoryLine inventoryLineRecord)
	{
		final InventoryLineId inventoryLineId = extractInventoryLineIdOrNull(inventoryLineRecord);
		final List<I_M_InventoryLine_HU> inventoryLineHURecords = inventoryLineId != null
				? retrieveInventoryLineHURecords(inventoryLineId)
				: ImmutableList.of();

		return ofRecord(inventoryLineRecord, inventoryLineHURecords);
	}

	private InventoryLine ofRecord(
			@NonNull final I_M_InventoryLine inventoryLineRecord,
			@NonNull final ListMultimap<InventoryLineId, I_M_InventoryLine_HU> inventoryLineHURecordsMap)
	{
		final InventoryLineId inventoryLineId = extractInventoryLineIdOrNull(inventoryLineRecord);
		final List<I_M_InventoryLine_HU> inventoryLineHURecords = inventoryLineId != null
				? inventoryLineHURecordsMap.get(inventoryLineId)
				: ImmutableList.of();

		return ofRecord(inventoryLineRecord, inventoryLineHURecords);
	}

	private InventoryLine ofRecord(
			@NonNull final I_M_InventoryLine inventoryLineRecord,
			@NonNull final Collection<I_M_InventoryLine_HU> inventoryLineHURecords)
	{
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(inventoryLineRecord.getM_AttributeSetInstance_ID());

		final AttributesKey storageAttributesKey = AttributesKeys
				.createAttributesKeyFromASIStorageAttributes(asiId)
				.orElse(AttributesKey.NONE);

		final InventoryLineId inventoryLineId = extractInventoryLineId(inventoryLineRecord);

		final LocatorId locatorId = warehousesRepo.getLocatorIdByRepoIdOrNull(inventoryLineRecord.getM_Locator_ID());

		final InventoryLineBuilder lineBuilder = InventoryLine
				.builder()
				.id(inventoryLineId)
				.orgId(OrgId.ofRepoId(inventoryLineRecord.getAD_Org_ID()))
				.locatorId(locatorId)
				.productId(ProductId.ofRepoId(inventoryLineRecord.getM_Product_ID()))
				.asiId(asiId)
				.storageAttributesKey(storageAttributesKey)
				.inventoryId(InventoryId.ofRepoId(inventoryLineRecord.getM_Inventory_ID()));

		final HUAggregationType huAggregationType = HUAggregationType.ofNullableCode(inventoryLineRecord.getHUAggregationType());
		lineBuilder.huAggregationType(huAggregationType);

		if (HUAggregationType.SINGLE_HU.equals(huAggregationType))
		{
			final I_C_UOM uomRecord = inventoryLineRecord.getC_UOM();
			final InventoryLineHU inventoryLineHU = InventoryLineHU
					.builder()
					.qtyBook(Quantity.of(inventoryLineRecord.getQtyBook(), uomRecord))
					.qtyCount(Quantity.of(inventoryLineRecord.getQtyCount(), uomRecord))
					.huId(HuId.ofRepoIdOrNull(inventoryLineRecord.getM_HU_ID()))
					.build();

			lineBuilder.inventoryLineHU(inventoryLineHU);
		}
		else
		{
			// multiple HUs per line
			if (inventoryLineHURecords.isEmpty())
			{
				final I_C_UOM uomRecord = uomsRepo.getById(inventoryLineRecord.getC_UOM_ID());
				final InventoryLineHU inventoryLineHU = InventoryLineHU
						.builder()
						.qtyBook(Quantity.of(inventoryLineRecord.getQtyBook(), uomRecord))
						.qtyCount(Quantity.of(inventoryLineRecord.getQtyCount(), uomRecord))
						.huId(null)
						.build();
				lineBuilder.inventoryLineHU(inventoryLineHU);
			}
			else
			{
				for (final I_M_InventoryLine_HU inventoryLineHURecord : inventoryLineHURecords)
				{
					final InventoryLineHU inventoryLineHU = ofInventoryLineHURecord(
							inventoryLineRecord,
							inventoryLineHURecord);
					lineBuilder.inventoryLineHU(inventoryLineHU);
				}
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
		final UomId lineUomId = UomId.ofRepoId(inventoryLineRecord.getC_UOM_ID());
		final ProductId lineProductId = ProductId.ofRepoId(inventoryLineRecord.getM_Product_ID());
		final UOMConversionContext conversionCtx = UOMConversionContext.of(lineProductId);

		final I_C_UOM uomRecord = inventoryLineHURecord.getC_UOM();
		final Quantity qtyBook = Quantity.of(inventoryLineHURecord.getQtyBook(), uomRecord);
		final Quantity countQty = Quantity.of(inventoryLineHURecord.getQtyCount(), uomRecord);

		final InventoryLineHU inventoryLineHU = InventoryLineHU
				.builder()
				.id(InventoryLineHUId.ofRepoId(inventoryLineHURecord.getM_InventoryLine_HU_ID()))
				.qtyBook(convBL.convertQuantityTo(qtyBook, conversionCtx, lineUomId))
				.qtyCount(convBL.convertQuantityTo(countQty, conversionCtx, lineUomId))
				.huId(HuId.ofRepoIdOrNull(inventoryLineHURecord.getM_HU_ID()))
				.build();

		return inventoryLineHU;
	}

	public void save(@NonNull final InventoryLines inventoryLines)
	{
		final HashMap<InventoryLineId, I_M_InventoryLine> existingInventoryLineRecords = getInventoryLineRecordsByIds(inventoryLines.getInventoryLineIds());

		for (final InventoryLine inventoryLine : inventoryLines)
		{
			final I_M_InventoryLine existingLineRecord = existingInventoryLineRecords.get(inventoryLine.getId());
			save(inventoryLine, existingLineRecord);
		}
	}

	public void save(@NonNull final InventoryLine inventoryLine)
	{
		final I_M_InventoryLine existingLineRecord = inventoryLine.getId() != null
				? getInventoryLineRecordById(inventoryLine.getId())
				: null;

		save(inventoryLine, existingLineRecord);
	}

	private void save(@NonNull final InventoryLine inventoryLine, @Nullable final I_M_InventoryLine existingLineRecord)
	{
		final I_M_InventoryLine lineRecord;
		if (existingLineRecord != null)
		{
			lineRecord = existingLineRecord;
		}
		else
		{
			lineRecord = newInstance(I_M_InventoryLine.class);
		}

		lineRecord.setAD_Org_ID(inventoryLine.getOrgId().getRepoId());
		lineRecord.setM_Inventory_ID(inventoryLine.getInventoryId().getRepoId());

		final AttributesKey storageAttributesKey = inventoryLine.getStorageAttributesKey();

		final AttributeSetInstanceId asiId = coalesceSuppliers(
				() -> inventoryLine.getAsiId(),
				() -> AttributesKeys.createAttributeSetInstanceFromAttributesKey(storageAttributesKey));
		lineRecord.setM_AttributeSetInstance_ID(asiId.getRepoId());

		lineRecord.setM_Locator_ID(inventoryLine.getLocatorId().getRepoId());
		lineRecord.setM_Product_ID(inventoryLine.getProductId().getRepoId());

		final HUAggregationType huAggregationType = inventoryLine.getHuAggregationType();
		lineRecord.setHUAggregationType(HUAggregationType.toCodeOrNull(huAggregationType));
		if (inventoryLine.isSingleHUAggregation())
		{
			// inventoryLineRecord.setHUAggregationType(AggregationType.SINGLE_HU.getHuAggregationTypeCode());

			if (inventoryLine.getId() != null)
			{
				deleteInventoryLineHUs(inventoryLine.getId());
			}

			final InventoryLineHU hu = CollectionUtils.singleElement(inventoryLine.getInventoryLineHUs());
			lineRecord.setM_HU_ID(hu.getHuId().getRepoId());

			final Quantity countQty = hu.getQtyCount();
			lineRecord.setQtyCount(countQty.getAsBigDecimal());

			final Quantity qtyBook = hu.getQtyBook();
			lineRecord.setQtyBook(qtyBook.getAsBigDecimal());

			final UomId uomId = Quantity.getCommonUomIdOfAll(countQty, qtyBook);
			lineRecord.setC_UOM_ID(uomId.getRepoId());
		}
		else
		{
			// inventoryLineRecord.setHUAggregationType(AggregationType.MULTIPLE_HUS.getHuAggregationTypeCode());

			final ImmutableList<InventoryLineHU> inventoryLineHUs = inventoryLine.getInventoryLineHUs();
			if (inventoryLineHUs.isEmpty())
			{
				lineRecord.setQtyBook(ZERO);
				lineRecord.setQtyCount(ZERO);

				final int stockingUomId = productsService.getStockingUOMId(inventoryLine.getProductId()).getRepoId();
				lineRecord.setC_UOM_ID(stockingUomId);
			}
			else
			{
				final UomId countUomId = CollectionUtils.extractSingleElement(inventoryLineHUs, l -> l.getQtyCount().getUomId());
				final UomId bookUomId = CollectionUtils.extractSingleElement(inventoryLineHUs, l -> l.getQtyBook().getUomId());

				Quantity countQty = Quantity.zero(uomsRepo.getById(countUomId));
				Quantity qtyBook = Quantity.zero(uomsRepo.getById(bookUomId));

				for (final InventoryLineHU inventoryLineHU : inventoryLineHUs)
				{
					countQty = countQty.add(inventoryLineHU.getQtyCount());
					qtyBook = qtyBook.add(inventoryLineHU.getQtyBook());
				}
				lineRecord.setQtyBook(qtyBook.getAsBigDecimal());
				lineRecord.setQtyCount(countQty.getAsBigDecimal());

				final UomId uomId = Quantity.getCommonUomIdOfAll(qtyBook, countQty);
				lineRecord.setC_UOM_ID(uomId.getRepoId());
			}
		}

		saveRecord(lineRecord);
		inventoryLine.setId(extractInventoryLineId(lineRecord));

		if (!inventoryLine.isSingleHUAggregation())
		{
			lineRecord.setM_HU_ID(0);
			// inventoryLine.setM_HU_PI_Item_Product(null); // TODO
			// inventoryLine.setQtyTU(BigDecimal.ZERO); // TODO

			createOrUpdateInventoryLineHURecords(inventoryLine);
		}
	}

	private ImmutableList<I_M_InventoryLine_HU> createOrUpdateInventoryLineHURecords(@NonNull final InventoryLine inventoryLine)
	{
		final HashMap<InventoryLineHUId, I_M_InventoryLine_HU> existingInventoryLineHURecords = retrieveInventoryLineHURecords(inventoryLine.getId())
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(l -> InventoryLineHUId.ofRepoId(l.getM_InventoryLine_HU_ID())));

		final ImmutableList.Builder<I_M_InventoryLine_HU> result = ImmutableList.builder();

		for (final InventoryLineHU inventoryLineHU : inventoryLine.getInventoryLineHUs())
		{
			I_M_InventoryLine_HU inventoryLineHURecord = existingInventoryLineHURecords.remove(inventoryLineHU.getId());
			if (inventoryLineHURecord == null)
			{
				inventoryLineHURecord = getOrNewInventoryLineHURecordById(inventoryLineHU.getId());
			}

			inventoryLineHURecord.setAD_Org_ID(inventoryLine.getOrgId().getRepoId());
			inventoryLineHURecord.setM_InventoryLine_ID(inventoryLine.getId().getRepoId());

			inventoryLineHURecord.setM_HU_ID(HuId.toRepoId(inventoryLineHU.getHuId()));

			final Quantity countQty = inventoryLineHU.getQtyCount();
			inventoryLineHURecord.setQtyCount(countQty.getAsBigDecimal());

			final Quantity qtyBook = inventoryLineHU.getQtyBook();
			inventoryLineHURecord.setQtyBook(qtyBook.getAsBigDecimal());

			final UomId uomId = Quantity.getCommonUomIdOfAll(countQty, qtyBook);
			inventoryLineHURecord.setC_UOM_ID(uomId.getRepoId());

			result.add(inventoryLineHURecord);
		}

		// the pre-existing records that we did not see until now are not needed anymore; delete them.
		InterfaceWrapperHelper.deleteAll(existingInventoryLineHURecords.values());

		final ImmutableList<I_M_InventoryLine_HU> createdOrUpdatedInventoryLineHURecords = result.build();
		createdOrUpdatedInventoryLineHURecords.forEach(InterfaceWrapperHelper::saveRecord);

		return createdOrUpdatedInventoryLineHURecords;
	}

	private List<I_M_InventoryLine_HU> retrieveInventoryLineHURecords(@NonNull final InventoryLineId inventoryLineId)
	{
		return retrieveInventoryLineHURecords(ImmutableSet.of(inventoryLineId)).get(inventoryLineId);
	}

	private ImmutableListMultimap<InventoryLineId, I_M_InventoryLine_HU> retrieveInventoryLineHURecords(@NonNull final Collection<InventoryLineId> inventoryLineIds)
	{
		if (inventoryLineIds.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		return Services
				.get(IQueryBL.class)
				.createQueryBuilder(I_M_InventoryLine_HU.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_InventoryLine_HU.COLUMNNAME_M_InventoryLine_ID, inventoryLineIds)
				.orderBy(I_M_InventoryLine_HU.COLUMN_M_HU_ID)
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						record -> InventoryLineId.ofRepoId(record.getM_InventoryLine_ID()),
						record -> record));
	}

	public void deleteInventoryLineHUs(@NonNull final InventoryLineId inventoryLineId)
	{
		queryBL.createQueryBuilder(I_M_InventoryLine_HU.class)
				.addEqualsFilter(I_M_InventoryLine_HU.COLUMN_M_InventoryLine_ID, inventoryLineId)
				.create()
				.delete();
	}

	private List<I_M_InventoryLine> retrieveLineRecords(@NonNull final InventoryId inventoryId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_InventoryLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InventoryLine.COLUMNNAME_M_Inventory_ID, inventoryId)
				.orderBy(I_M_InventoryLine.COLUMNNAME_Line)
				.orderBy(I_M_InventoryLine.COLUMNNAME_M_InventoryLine_ID)
				.create()
				.list();
	}
}
