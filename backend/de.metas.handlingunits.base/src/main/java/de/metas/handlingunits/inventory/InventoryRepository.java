
package de.metas.handlingunits.inventory;

import com.google.common.collect.ImmutableSet;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.model.I_M_InventoryLine_HU;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryAndLineId;
import de.metas.inventory.InventoryAndLineIdSet;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineId;
import de.metas.organization.IOrgDAO;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.impl.TableIdsCache;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Inventory;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
public class InventoryRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private InventoryLoaderAndSaver newLoaderAndSaver()
	{
		return InventoryLoaderAndSaver.builder()
				.queryBL(queryBL)
				.inventoryDAO(inventoryDAO)
				.uomDAO(uomDAO)
				.uomConversionBL(uomConversionBL)
				.warehouseDAO(warehouseDAO)
				.handlingUnitsBL(handlingUnitsBL)
				.docTypeDAO(docTypeDAO)
				.orgDAO(orgDAO)
				.build();
	}

	public Inventory getById(@NonNull final InventoryId inventoryId)
	{
		return newLoaderAndSaver().loadById(inventoryId);
	}

	I_M_Inventory getRecordById(@NonNull final InventoryId inventoryId)
	{
		return inventoryDAO.getById(inventoryId);
	}

	public Inventory toInventory(@NonNull final I_M_Inventory inventoryRecord)
	{
		return newLoaderAndSaver().toInventory(inventoryRecord);
	}

	public DocBaseAndSubType extractDocBaseAndSubTypeOrNull(@Nullable final I_M_Inventory inventoryRecord)
	{
		return newLoaderAndSaver().extractDocBaseAndSubTypeOrNull(inventoryRecord);
	}

	public final Map<InventoryAndLineId, I_M_InventoryLine> getInventoryLineRecordsByIds(@NonNull InventoryAndLineIdSet inventoryAndLineIds)
	{
		return newLoaderAndSaver().getLineRecords(inventoryAndLineIds);
	}

	/**
	 * The method might not belong here, but we often need this from outside the repo
	 */
	public InventoryLine toInventoryLine(@NonNull final I_M_InventoryLine inventoryLineRecord)
	{
		return newLoaderAndSaver().toInventoryLine(inventoryLineRecord);
	}

	public void saveInventoryLines(@NonNull final Inventory inventory)
	{
		newLoaderAndSaver().saveInventoryLines(inventory);
	}

	public void saveInventoryLine(
			@NonNull final InventoryLine inventoryLine,
			@NonNull final InventoryId inventoryId)
	{
		newLoaderAndSaver().saveInventoryLine(inventoryLine, inventoryId);
	}

	public void saveInventoryLineHURecords(
			@NonNull final InventoryLine inventoryLine,
			@NonNull final InventoryId inventoryId)
	{
		newLoaderAndSaver().saveInventoryLineHURecords(inventoryLine, inventoryId);
	}

	public Optional<Quantity> getFreshBookedQtyFromStorage(@NonNull final ProductId productId, @NonNull final UomId inventoryLineUOMId, @NonNull final HuId huId)
	{
		return newLoaderAndSaver().getFreshBookedQtyFromStorage(productId, inventoryLineUOMId, huId);
	}

	public void deleteInventoryLineHUs(@NonNull final InventoryLineId inventoryLineId)
	{
		newLoaderAndSaver().deleteInventoryLineHUs(inventoryLineId);
	}

	public Inventory createInventoryHeader(@NonNull final InventoryHeaderCreateRequest request)
	{
		final I_M_Inventory inventoryRecord = newInstance(I_M_Inventory.class);
		inventoryRecord.setAD_Org_ID(request.getOrgId().getRepoId());
		inventoryRecord.setDocStatus(DocStatus.Drafted.getCode());
		inventoryRecord.setDocAction(IDocument.ACTION_Complete);
		inventoryRecord.setMovementDate(TimeUtil.asTimestamp(request.getMovementDate()));
		inventoryRecord.setM_Warehouse_ID(request.getWarehouseId().getRepoId());
		inventoryRecord.setM_Picking_Job_ID(PickingJobId.toRepoId(request.getPickingJobId()));

		inventoryRecord.setC_Activity_ID(ActivityId.toRepoId(request.getActivityId()));
		inventoryRecord.setDescription(StringUtils.trimBlankToNull(request.getDescription()));

		if (request.getDocTypeId() != null)
		{
			inventoryRecord.setC_DocType_ID(request.getDocTypeId().getRepoId());
		}

		inventoryRecord.setPOReference(request.getPoReference());
		saveRecord(inventoryRecord);

		return toInventory(inventoryRecord);
	}

	public void createInventoryLine(@NonNull final InventoryLineCreateRequest request)
	{
		final I_M_Inventory inventory = getRecordById(request.getInventoryId());

		final I_M_InventoryLine inventoryLineRecord = newInstance(I_M_InventoryLine.class);

		inventoryLineRecord.setAD_Org_ID(inventory.getAD_Org_ID());
		inventoryLineRecord.setM_Inventory_ID(inventory.getM_Inventory_ID());

		inventoryLineRecord.setM_Product_ID(request.getProductId().getRepoId());
		if (request.getAttributeSetId() != null)
		{
			inventoryLineRecord.setM_AttributeSetInstance_ID(request.getAttributeSetId().getRepoId());
		}

		final UomId uomId = Quantity.getCommonUomIdOfAll(request.getQtyBooked(), request.getQtyCount());

		inventoryLineRecord.setQtyBook(request.getQtyBooked().toBigDecimal());
		inventoryLineRecord.setQtyCount(request.getQtyCount().toBigDecimal());
		inventoryLineRecord.setC_UOM_ID(uomId.getRepoId());
		inventoryLineRecord.setIsCounted(true);

		inventoryLineRecord.setM_Locator_ID(request.getLocatorId().getRepoId());

		if (request.getAggregationType() != null)
		{
			inventoryLineRecord.setHUAggregationType(request.getAggregationType().getCode());
		}

		saveRecord(inventoryLineRecord);

		if (!Check.isEmpty(request.getInventoryLineHUList()))
		{
			final InventoryLine inventoryLine = toInventoryLine(inventoryLineRecord)
					.withInventoryLineHUs(request.getInventoryLineHUList())
					.distributeQtyCountToHUs(request.getQtyCount());

			saveInventoryLineHURecords(inventoryLine, request.getInventoryId());
		}

		toInventory(inventory);
	}

	public Collection<I_M_InventoryLine> retrieveAllLinesForHU(@NonNull final HuId huId)
	{
		final InventoryAndLineIdSet inventoryAndLineIds = retrieveAllLineIdsForHU(huId);
		return newLoaderAndSaver().getLineRecords(inventoryAndLineIds).values();
	}

	private InventoryAndLineIdSet retrieveAllLineIdsForHU(final @NotNull HuId huId)
	{
		final List<I_M_HU> includedHUs = handlingUnitsBL.retrieveIncludedHUs(handlingUnitsBL.getById(huId));

		final ImmutableSet<HuId> includedHUIds = includedHUs.stream()
				.map(hu -> HuId.ofRepoId(hu.getM_HU_ID()))
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableSet<HuId> huIds = ImmutableSet.<HuId>builder()
				.addAll(includedHUIds)
				.add(huId)
				.build();

		final ImmutableSet<InventoryAndLineId> linesViaHUId = queryBL.createQueryBuilder(I_M_InventoryLine.class)
				.addInArrayFilter(I_M_InventoryLine.COLUMNNAME_M_HU_ID, huIds)
				.stream()
				.map(InventoryAndLineId::of)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableSet<InventoryAndLineId> linesViaHUInventoryLine = queryBL.createQueryBuilder(I_M_InventoryLine_HU.class)
				.addInArrayFilter(I_M_InventoryLine_HU.COLUMNNAME_M_HU_ID, huIds)
				.stream()
				.map(InventoryRepository::extractInventoryAndLineId)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableSet<InventoryAndLineId> linesViaHUAssignment = queryBL.createQueryBuilder(I_M_HU_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_AD_Table_ID, TableIdsCache.instance.getTableId(I_M_InventoryLine.Table_Name))
				.filter(queryBL.createCompositeQueryFilter(I_M_HU_Assignment.class)
						.setJoinOr()
						.addInArrayFilter(I_M_HU_Assignment.COLUMNNAME_M_HU_ID, huIds)
						.addInArrayFilter(I_M_HU_Assignment.COLUMNNAME_M_LU_HU_ID, huIds)
						.addInArrayFilter(I_M_HU_Assignment.COLUMNNAME_M_TU_HU_ID, huIds)
						.addInArrayFilter(I_M_HU_Assignment.COLUMNNAME_VHU_ID, huIds))
				.andCollect(I_M_HU_Assignment.COLUMNNAME_Record_ID, I_M_InventoryLine.class)
				.stream()
				.map(InventoryAndLineId::of)
				.collect(ImmutableSet.toImmutableSet());

		return InventoryAndLineIdSet.ofCollection(
				ImmutableSet.<InventoryAndLineId>builder()
						.addAll(linesViaHUId)
						.addAll(linesViaHUInventoryLine)
						.addAll(linesViaHUAssignment)
						.build()
		);
	}

	private static InventoryAndLineId extractInventoryAndLineId(final I_M_InventoryLine_HU inventoryLineHU)
	{
		return InventoryAndLineId.ofRepoIds(inventoryLineHU.getM_Inventory_ID(), inventoryLineHU.getM_InventoryLine_ID());
	}
}

