
package de.metas.handlingunits.inventory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.inventory.InventoryLine.InventoryLineBuilder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.model.I_M_InventoryLine_HU;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineId;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Inventory;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static de.metas.common.util.CoalesceUtil.coalesceSuppliers;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwares;
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
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);
	private final IUOMConversionBL convBL = Services.get(IUOMConversionBL.class);
	private final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
	private final IHUStorageFactory huStorageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();
	private final IHandlingUnitsDAO huDAO = Services.get(IHandlingUnitsDAO.class);
	private final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private I_M_InventoryLine getInventoryLineRecordById(@Nullable final InventoryLineId inventoryLineId)
	{
		return load(inventoryLineId, I_M_InventoryLine.class);
	}

	@Deprecated
	public final I_M_InventoryLine getInventoryLineRecordFor(@NonNull final InventoryLine inventoryLine)
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

	public Inventory getById(@NonNull final InventoryId inventoryId)
	{
		final I_M_Inventory inventoryRecord = getRecordById(inventoryId);
		return toInventory(inventoryRecord);
	}

	I_M_Inventory getRecordById(@NonNull final InventoryId inventoryId)
	{
		final I_M_Inventory inventoryRecord = inventoryDAO.getById(inventoryId);
		return inventoryRecord;
	}

	public Inventory toInventory(@NonNull final I_M_Inventory inventoryRecord)
	{
		final InventoryId inventoryId = InventoryId.ofRepoId(inventoryRecord.getM_Inventory_ID());
		final DocBaseAndSubType docBaseAndSubType = extractDocBaseAndSubTypeOrNull(inventoryRecord); // shall not be null at this point
		if (docBaseAndSubType == null)
		{
			throw new AdempiereException("Failed extracting DocBaseType and DocSubType from " + inventoryRecord);
		}

		final OrgId orgId = OrgId.ofRepoId(inventoryRecord.getAD_Org_ID());
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);

		final Collection<I_M_InventoryLine> inventoryLineRecords = retrieveLineRecords(inventoryId);
		final ImmutableSet<InventoryLineId> inventoryLineIds = inventoryLineRecords.stream().map(r -> extractInventoryLineId(r)).collect(ImmutableSet.toImmutableSet());

		final ListMultimap<InventoryLineId, I_M_InventoryLine_HU> inventoryLineHURecords = retrieveInventoryLineHURecords(inventoryLineIds);

		final List<InventoryLine> inventoryLines = inventoryLineRecords
				.stream()
				.map(inventoryLineRecord -> toInventoryLine(inventoryLineRecord, inventoryLineHURecords))
				.collect(ImmutableList.toImmutableList());

		return Inventory.builder()
				.id(inventoryId)
				.orgId(OrgId.ofRepoId(inventoryRecord.getAD_Org_ID()))
				.docBaseAndSubType(docBaseAndSubType)
				.movementDate(TimeUtil.asZonedDateTime(inventoryRecord.getMovementDate(), timeZone))
				.warehouseId(WarehouseId.ofRepoIdOrNull(inventoryRecord.getM_Warehouse_ID()))
				.description(inventoryRecord.getDescription())
				.activityId(ActivityId.ofRepoIdOrNull(inventoryRecord.getC_Activity_ID()))
				.docStatus(DocStatus.ofCode(inventoryRecord.getDocStatus()))
				.documentNo(inventoryRecord.getDocumentNo())
				.lines(inventoryLines)
				.build();
	}

	public DocBaseAndSubType extractDocBaseAndSubTypeOrNull(@Nullable final I_M_Inventory inventoryRecord)
	{
		if (inventoryRecord == null)
		{
			return null; // nothing to extract
		}

		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(inventoryRecord.getC_DocType_ID());
		if (docTypeId == null)
		{
			return null; // nothing to extract
		}

		return docTypeDAO.getDocBaseAndSubTypeById(docTypeId);
	}

	/**
	 * The method might not belong here, but we often need this from outside the repo
	 */
	public InventoryLine toInventoryLine(@NonNull final I_M_InventoryLine inventoryLineRecord)
	{
		final InventoryLineId inventoryLineId = extractInventoryLineIdOrNull(inventoryLineRecord);
		final List<I_M_InventoryLine_HU> inventoryLineHURecords = inventoryLineId != null
				? retrieveInventoryLineHURecords(inventoryLineId)
				: ImmutableList.of();

		return toInventoryLine(inventoryLineRecord, inventoryLineHURecords);
	}

	private InventoryLine toInventoryLine(
			@NonNull final I_M_InventoryLine inventoryLineRecord,
			@NonNull final ListMultimap<InventoryLineId, I_M_InventoryLine_HU> inventoryLineHURecordsMap)
	{
		final InventoryLineId inventoryLineId = extractInventoryLineIdOrNull(inventoryLineRecord);
		final List<I_M_InventoryLine_HU> inventoryLineHURecords = inventoryLineId != null
				? inventoryLineHURecordsMap.get(inventoryLineId)
				: ImmutableList.of();

		return toInventoryLine(inventoryLineRecord, inventoryLineHURecords);
	}

	private InventoryLine toInventoryLine(
			@NonNull final I_M_InventoryLine inventoryLineRecord,
			@NonNull final Collection<I_M_InventoryLine_HU> inventoryLineHURecords)
	{
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(inventoryLineRecord.getM_AttributeSetInstance_ID());
		final AttributesKey storageAttributesKey = AttributesKeys.createAttributesKeyFromASIStorageAttributes(asiId).orElse(AttributesKey.NONE);

		final LocatorId locatorId = warehousesRepo.getLocatorIdByRepoIdOrNull(inventoryLineRecord.getM_Locator_ID());

		final I_C_UOM uom = uomsRepo.getById(inventoryLineRecord.getC_UOM_ID());

		final InventoryLineBuilder lineBuilder = InventoryLine.builder()
				.id(extractInventoryLineIdOrNull(inventoryLineRecord))
				.orgId(OrgId.ofRepoId(inventoryLineRecord.getAD_Org_ID()))
				.locatorId(locatorId)
				.productId(ProductId.ofRepoId(inventoryLineRecord.getM_Product_ID()))
				.asiId(asiId)
				.qtyCountFixed(Quantity.of(inventoryLineRecord.getQtyCount(), uom))
				.qtyBookFixed(Quantity.of(inventoryLineRecord.getQtyBook(), uom))
				.storageAttributesKey(storageAttributesKey);

		final HUAggregationType huAggregationType = HUAggregationType.ofNullableCode(inventoryLineRecord.getHUAggregationType());
		lineBuilder.huAggregationType(huAggregationType);

		lineBuilder.counted(inventoryLineRecord.isCounted());

		if (HUAggregationType.SINGLE_HU.equals(huAggregationType))
		{
			final InventoryLineHU inventoryLineHU = toInventoryLineHU(inventoryLineRecord);
			lineBuilder.inventoryLineHU(inventoryLineHU);
		}
		// multiple HUs per line
		else
		{
			if (inventoryLineHURecords.isEmpty())
			{
				final InventoryLineHU inventoryLineHU = toInventoryLineHU(inventoryLineRecord);
				lineBuilder.inventoryLineHU(inventoryLineHU);
			}
			else
			{
				final UOMConversionContext uomConversionCtx = UOMConversionContext.of(inventoryLineRecord.getM_Product_ID());
				final UomId targetUomId = UomId.ofRepoId(inventoryLineRecord.getC_UOM_ID());

				for (final I_M_InventoryLine_HU inventoryLineHURecord : inventoryLineHURecords)
				{
					final InventoryLineHU inventoryLineHU = toInventoryLineHU(inventoryLineHURecord, uomConversionCtx, targetUomId);
					lineBuilder.inventoryLineHU(inventoryLineHU);
				}
			}
		}

		return lineBuilder.build();
	}

	private InventoryLineHU toInventoryLineHU(@NonNull final I_M_InventoryLine inventoryLineRecord)
	{
		final I_C_UOM uom = uomsRepo.getById(inventoryLineRecord.getC_UOM_ID());

		final Quantity qtyInternalUse;
		final Quantity qtyBook;
		final Quantity qtyCount;

		final BigDecimal qtyInternalUseBD = inventoryLineRecord.getQtyInternalUse();
		if (qtyInternalUseBD != null && qtyInternalUseBD.signum() != 0)
		{
			qtyInternalUse = Quantity.of(qtyInternalUseBD, uom);
			qtyBook = null;
			qtyCount = null;
		}
		else
		{
			qtyInternalUse = null;

			if (inventoryLineRecord.getM_HU_ID() > 0)
			{
				// refresh bookedQty from HU
				final ProductId productId = ProductId.ofRepoId(inventoryLineRecord.getM_Product_ID());
				final HuId huId = HuId.ofRepoId(inventoryLineRecord.getM_HU_ID());
				final UomId uomId = UomId.ofRepoId(uom.getC_UOM_ID());

				qtyBook = getFreshBookedQtyFromStorage(productId, uomId, huId).orElse(Quantity.zero(uom));
			}
			else
			{

				qtyBook = Quantity.of(inventoryLineRecord.getQtyBook(), uom);
			}

			qtyCount = Quantity.of(inventoryLineRecord.getQtyCount(), uom);
		}

		return InventoryLineHU.builder()
				.huId(HuId.ofRepoIdOrNull(inventoryLineRecord.getM_HU_ID()))
				.huQRCode(HUQRCode.fromNullableGlobalQRCodeJsonString(inventoryLineRecord.getRenderedQRCode()))
				.qtyInternalUse(qtyInternalUse)
				.qtyBook(qtyBook)
				.qtyCount(qtyCount)
				.build();
	}

	private InventoryLineHU toInventoryLineHU(
			@NonNull final I_M_InventoryLine_HU inventoryLineHURecord,
			@NonNull final UOMConversionContext uomConversionCtx,
			@NonNull final UomId targetUomId)
	{
		final I_C_UOM uom = uomsRepo.getById(inventoryLineHURecord.getC_UOM_ID());
		final Quantity qtyInternalUseConv;
		final Quantity qtyBookConv;
		final Quantity qtyCountConv;

		final BigDecimal qtyInternalUseBD = inventoryLineHURecord.getQtyInternalUse();
		if (qtyInternalUseBD != null && qtyInternalUseBD.signum() != 0)
		{
			final Quantity qtyInternalUse = Quantity.of(qtyInternalUseBD, uom);

			qtyInternalUseConv = convBL.convertQuantityTo(qtyInternalUse, uomConversionCtx, targetUomId);
			qtyBookConv = null;
			qtyCountConv = null;
		}
		else
		{
			final Quantity qtyBook;
			if (inventoryLineHURecord.getM_HU_ID() > 0)
			{
				// refresh bookedQty from HU
				final ProductId productId = uomConversionCtx.getProductId();
				final HuId huId = HuId.ofRepoId(inventoryLineHURecord.getM_HU_ID());

				qtyBook = getFreshBookedQtyFromStorage(productId, targetUomId, huId).orElse(Quantity.zero(uom));
			}
			else
			{
				qtyBook = Quantity.of(inventoryLineHURecord.getQtyBook(), uom);
			}

			final Quantity qtyCount = Quantity.of(inventoryLineHURecord.getQtyCount(), uom);

			qtyInternalUseConv = null;
			qtyBookConv = convBL.convertQuantityTo(qtyBook, uomConversionCtx, targetUomId);
			qtyCountConv = convBL.convertQuantityTo(qtyCount, uomConversionCtx, targetUomId);
		}

		return InventoryLineHU.builder()
				.id(extractInventoryLineHUId(inventoryLineHURecord))
				.qtyInternalUse(qtyInternalUseConv)
				.qtyBook(qtyBookConv)
				.qtyCount(qtyCountConv)
				.huId(HuId.ofRepoIdOrNull(inventoryLineHURecord.getM_HU_ID()))
				.huQRCode(HUQRCode.fromNullableGlobalQRCodeJsonString(inventoryLineHURecord.getRenderedQRCode()))
				.build();
	}

	public void saveInventoryLines(@NonNull final Inventory inventory)
	{
		final InventoryId inventoryId = inventory.getId();
		saveInventoryLines(inventory.getLines(), inventoryId);
	}

	private void saveInventoryLines(
			@NonNull final List<InventoryLine> lines,
			@NonNull final InventoryId inventoryId)
	{
		final ImmutableSet<InventoryLineId> inventoryLineIds = extractInventoryLineIds(lines);
		final HashMap<InventoryLineId, I_M_InventoryLine> existingInventoryLineRecords = getInventoryLineRecordsByIds(inventoryLineIds);

		for (final InventoryLine inventoryLine : lines)
		{
			final I_M_InventoryLine existingLineRecord = existingInventoryLineRecords.remove(inventoryLine.getId());
			saveInventoryLine(inventoryLine, inventoryId, existingLineRecord);
		}

		InterfaceWrapperHelper.deleteAll(existingInventoryLineRecords.values());
	}

	private static ImmutableSet<InventoryLineId> extractInventoryLineIds(final List<InventoryLine> lines)
	{
		return lines.stream()
				.map(InventoryLine::getId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	public void saveInventoryLine(
			@NonNull final InventoryLine inventoryLine,
			@NonNull final InventoryId inventoryId)
	{
		final I_M_InventoryLine existingLineRecord = inventoryLine.getId() != null
				? getInventoryLineRecordById(inventoryLine.getId())
				: null;

		saveInventoryLine(inventoryLine, inventoryId, existingLineRecord);
	}

	private void saveInventoryLine(
			@NonNull final InventoryLine inventoryLine,
			@NonNull final InventoryId inventoryId,
			@Nullable final I_M_InventoryLine existingLineRecord)
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
		lineRecord.setM_Inventory_ID(inventoryId.getRepoId());

		final AttributesKey storageAttributesKey = inventoryLine.getStorageAttributesKey();

		final AttributeSetInstanceId asiId = coalesceSuppliers(
				() -> inventoryLine.getAsiId(),
				() -> AttributesKeys.createAttributeSetInstanceFromAttributesKey(storageAttributesKey));
		lineRecord.setM_AttributeSetInstance_ID(asiId.getRepoId());

		lineRecord.setM_Locator_ID(inventoryLine.getLocatorId().getRepoId());
		lineRecord.setM_Product_ID(inventoryLine.getProductId().getRepoId());

		final HUAggregationType huAggregationType = inventoryLine.getHuAggregationType();
		lineRecord.setHUAggregationType(HUAggregationType.toCodeOrNull(huAggregationType));

		final InventoryLineHU singleLineHU = inventoryLine.isSingleHUAggregation() ? inventoryLine.getSingleLineHU() : null;
		final HuId huId = singleLineHU != null ? singleLineHU.getHuId() : null;
		final HUQRCode huQRCode = singleLineHU != null ? singleLineHU.getHuQRCode() : null;
		lineRecord.setM_HU_ID(HuId.toRepoId(huId));
		lineRecord.setRenderedQRCode(huQRCode != null ? huQRCode.toGlobalQRCodeString() : null);
		// lineRecord.setM_HU_PI_Item_Product(null); // TODO
		// lineRecord.setQtyTU(BigDecimal.ZERO); // TODO

		updateInventoryLineRecordQuantities(lineRecord, inventoryLine);

		saveRecord(lineRecord);
		inventoryLine.setId(extractInventoryLineId(lineRecord));

		saveInventoryLineHURecords(inventoryLine, inventoryId);
	}

	private void updateInventoryLineRecordQuantities(
			@NonNull final I_M_InventoryLine lineRecord,
			@NonNull final InventoryLine from)
	{
		final UomId uomId;
		final BigDecimal qtyInternalUseBD;
		final BigDecimal qtyBookBD;
		final BigDecimal qtyCountBD;

		if (from.getInventoryType().isInternalUse())
		{
			final Quantity qtyInternalUse = from.getQtyInternalUse();

			uomId = qtyInternalUse.getUomId();
			qtyInternalUseBD = qtyInternalUse.toBigDecimal();
			qtyBookBD = BigDecimal.ZERO; // using ZERO instead of null because the column is mandatory in DB
			qtyCountBD = BigDecimal.ZERO; // using ZERO instead of null because the column is mandatory in DB
		}
		else
		{
			final Quantity qtyBook = from.getQtyBook();
			final Quantity qtyCount = from.getQtyCount();

			uomId = Quantity.getCommonUomIdOfAll(qtyCount, qtyBook);
			qtyInternalUseBD = null;
			qtyBookBD = qtyBook.toBigDecimal();
			qtyCountBD = qtyCount.toBigDecimal();
		}

		lineRecord.setQtyInternalUse(qtyInternalUseBD);
		lineRecord.setQtyBook(qtyBookBD);
		lineRecord.setQtyCount(qtyCountBD);
		lineRecord.setC_UOM_ID(uomId.getRepoId());
		lineRecord.setIsCounted(from.isCounted());
	}

	public void saveInventoryLineHURecords(
			@NonNull final InventoryLine inventoryLine,
			@NonNull final InventoryId inventoryId)
	{
		if (inventoryLine.isSingleHUAggregation())
		{
			if (inventoryLine.getId() != null)
			{
				deleteInventoryLineHUs(inventoryLine.getId());
			}
		}
		else
		{
			final HashMap<InventoryLineHUId, I_M_InventoryLine_HU> existingInventoryLineHURecords = retrieveInventoryLineHURecords(inventoryLine.getId())
					.stream()
					.collect(GuavaCollectors.toHashMapByKey(l -> extractInventoryLineHUId(l)));

			for (final InventoryLineHU lineHU : inventoryLine.getInventoryLineHUs())
			{
				I_M_InventoryLine_HU lineHURecord = existingInventoryLineHURecords.remove(lineHU.getId());
				if (lineHURecord == null)
				{
					lineHURecord = newInstance(I_M_InventoryLine_HU.class);
				}

				lineHURecord.setAD_Org_ID(inventoryLine.getOrgId().getRepoId());
				lineHURecord.setM_Inventory_ID(inventoryId.getRepoId());
				lineHURecord.setM_InventoryLine_ID(inventoryLine.getId().getRepoId());
				updateInventoryLineHURecord(lineHURecord, lineHU);

				saveRecord(lineHURecord);
				lineHU.setId(extractInventoryLineHUId(lineHURecord));
			}

			// the pre-existing records that we did not see until now are not needed anymore; delete them.
			InterfaceWrapperHelper.deleteAll(existingInventoryLineHURecords.values());
		}
	}

	public Optional<Quantity> getFreshBookedQtyFromStorage(@NonNull final ProductId productId, @NonNull final UomId inventoryLineUOMId, @NonNull final HuId huId)
	{
		Optional<Quantity> bookedQty = Optional.empty();

		final I_M_HU hu = huDAO.getById(huId);

		final List<IHUProductStorage> huProductStorages = huStorageFactory.getHUProductStorages(ImmutableList.of(hu), productId);

		if (!huProductStorages.isEmpty())
		{
			final IHUProductStorage huStorage = huProductStorages.get(0);

			final I_C_UOM inventoryLineUOM = uomsRepo.getById(inventoryLineUOMId);

			final BigDecimal qtyInInventoryUOM = convBL.convertQty(
					UOMConversionContext.of(productId),
					huStorage.getQty().toBigDecimal(),
					huStorage.getQty().getUOM(),
					inventoryLineUOM);

			bookedQty = Optional.of(Quantity.of(qtyInInventoryUOM, inventoryLineUOM));
		}

		return bookedQty;
	}

	private static void updateInventoryLineHURecord(
			@NonNull I_M_InventoryLine_HU record,
			@NonNull final InventoryLineHU fromLineHU)
	{
		// record.setAD_Org_ID(orgId.getRepoId());
		// record.setM_InventoryLine_ID(lineId.getRepoId());

		record.setM_HU_ID(HuId.toRepoId(fromLineHU.getHuId()));
		record.setRenderedQRCode(fromLineHU.getHuQRCode() != null ? fromLineHU.getHuQRCode().toGlobalQRCodeString() : null);

		updateInventoryLineHURecordQuantities(record, fromLineHU);
	}

	private static void updateInventoryLineHURecordQuantities(
			@NonNull final I_M_InventoryLine_HU lineRecord,
			@NonNull final InventoryLineHU from)
	{
		final UomId uomId;
		final BigDecimal qtyInternalUseBD;
		final BigDecimal qtyBookBD;
		final BigDecimal qtyCountBD;

		if (from.getInventoryType().isInternalUse())
		{
			final Quantity qtyInternalUse = from.getQtyInternalUse();

			uomId = qtyInternalUse.getUomId();
			qtyInternalUseBD = qtyInternalUse.toBigDecimal();
			qtyBookBD = BigDecimal.ZERO; // using ZERO instead of null because the column is mandatory in DB
			qtyCountBD = BigDecimal.ZERO; // using ZERO instead of null because the column is mandatory in DB
		}
		else
		{
			final Quantity qtyBook = from.getQtyBook();
			final Quantity qtyCount = from.getQtyCount();

			uomId = Quantity.getCommonUomIdOfAll(qtyCount, qtyBook);
			qtyInternalUseBD = null;
			qtyBookBD = qtyBook.toBigDecimal();
			qtyCountBD = qtyCount.toBigDecimal();
		}

		lineRecord.setQtyInternalUse(qtyInternalUseBD);
		lineRecord.setQtyBook(qtyBookBD);
		lineRecord.setQtyCount(qtyCountBD);
		lineRecord.setC_UOM_ID(uomId.getRepoId());
	}

	private static InventoryLineHUId extractInventoryLineHUId(@NonNull final I_M_InventoryLine_HU inventoryLineHURecord)
	{
		return InventoryLineHUId.ofRepoId(inventoryLineHURecord.getM_InventoryLine_HU_ID());
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

		return queryBL
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
		return queryBL
				.createQueryBuilder(I_M_InventoryLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InventoryLine.COLUMNNAME_M_Inventory_ID, inventoryId)
				.orderBy(I_M_InventoryLine.COLUMNNAME_Line)
				.orderBy(I_M_InventoryLine.COLUMNNAME_M_InventoryLine_ID)
				.create()
				.list();
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

	public Inventory createInventoryLine(@NonNull final InventoryLineCreateRequest request)
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

		return toInventory(inventory);
	}
}
