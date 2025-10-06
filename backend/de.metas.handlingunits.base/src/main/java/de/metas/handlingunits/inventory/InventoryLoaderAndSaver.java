package de.metas.handlingunits.inventory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.model.I_M_InventoryLine_HU;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryAndLineId;
import de.metas.inventory.InventoryAndLineIdSet;
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
import de.metas.util.GuavaCollectors;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static de.metas.common.util.CoalesceUtil.coalesceSuppliersNotNull;

@Builder
class InventoryLoaderAndSaver
{
	@NonNull private final IQueryBL queryBL;
	@NonNull private final IInventoryDAO inventoryDAO;
	@NonNull private final IUOMDAO uomDAO;
	@NonNull private final IUOMConversionBL uomConversionBL;
	@NonNull private final IWarehouseDAO warehouseDAO;
	@NonNull private final IHandlingUnitsBL handlingUnitsBL;
	@NonNull private final IDocTypeDAO docTypeDAO;
	@NonNull private final IOrgDAO orgDAO;

	private final HashMap<InventoryId, I_M_Inventory> inventoryRecordsByInventoryId = new HashMap<>();
	private final HashMap<InventoryId, List<I_M_InventoryLine>> inventoryLineRecordsByInventoryId = new HashMap<>();
	private final HashMap<InventoryLineId, List<I_M_InventoryLine_HU>> inventoryLineHURecordsByInventoryLineId = new HashMap<>();

	public Inventory loadById(@NonNull final InventoryId inventoryId)
	{
		warmUp(inventoryId);

		return toInventory(getRecordById(inventoryId));
	}

	private void warmUp(@NonNull final InventoryId inventoryId)
	{
		getRecordById(inventoryId);

		final ImmutableSet<InventoryLineId> inventoryLineIds = getLineRecords(inventoryId)
				.stream()
				.map(InventoryLoaderAndSaver::extractInventoryLineId)
				.collect(ImmutableSet.toImmutableSet());

		CollectionUtils.getAllOrLoad(this.inventoryLineHURecordsByInventoryLineId, inventoryLineIds, this::retrieveInventoryLineHURecords);
	}

	private I_M_Inventory getRecordById(@NonNull final InventoryId inventoryId)
	{
		return inventoryRecordsByInventoryId.computeIfAbsent(inventoryId, inventoryDAO::getById);
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

		return Inventory.builder()
				.id(inventoryId)
				.orgId(orgId)
				.docBaseAndSubType(docBaseAndSubType)
				.movementDate(TimeUtil.asZonedDateTime(inventoryRecord.getMovementDate(), timeZone))
				.warehouseId(WarehouseId.ofRepoIdOrNull(inventoryRecord.getM_Warehouse_ID()))
				.description(inventoryRecord.getDescription())
				.activityId(ActivityId.ofRepoIdOrNull(inventoryRecord.getC_Activity_ID()))
				.docStatus(DocStatus.ofCode(inventoryRecord.getDocStatus()))
				.documentNo(inventoryRecord.getDocumentNo())
				.lines(getLineRecords(inventoryId)
						.stream()
						.map(this::toInventoryLine)
						.collect(ImmutableList.toImmutableList()))
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

	private static InventoryId extractInventoryId(@NonNull final I_M_InventoryLine record)
	{
		return InventoryId.ofRepoId(record.getM_Inventory_ID());
	}

	private static InventoryLineId extractInventoryLineId(@NonNull final I_M_InventoryLine record)
	{
		return InventoryLineId.ofRepoId(record.getM_InventoryLine_ID());
	}

	private static InventoryLineId extractInventoryLineIdOrNull(@NonNull final I_M_InventoryLine record)
	{
		return InventoryLineId.ofRepoIdOrNull(record.getM_InventoryLine_ID());
	}

	/**
	 * The method might not belong here, but we often need this from outside the repo
	 */
	public InventoryLine toInventoryLine(@NonNull final I_M_InventoryLine inventoryLineRecord)
	{
		final InventoryLineId inventoryLineId = extractInventoryLineIdOrNull(inventoryLineRecord);
		final List<I_M_InventoryLine_HU> inventoryLineHURecords = inventoryLineId != null
				? getInventoryLineHURecords(inventoryLineId)
				: ImmutableList.of();

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(inventoryLineRecord.getM_AttributeSetInstance_ID());
		final AttributesKey storageAttributesKey = AttributesKeys.createAttributesKeyFromASIStorageAttributes(asiId).orElse(AttributesKey.NONE);

		final LocatorId locatorId = warehouseDAO.getLocatorIdByRepoId(inventoryLineRecord.getM_Locator_ID());

		final I_C_UOM uom = uomDAO.getById(inventoryLineRecord.getC_UOM_ID());

		final InventoryLine.InventoryLineBuilder lineBuilder = InventoryLine.builder()
				.id(extractInventoryLineIdOrNull(inventoryLineRecord))
				.orgId(OrgId.ofRepoId(inventoryLineRecord.getAD_Org_ID()))
				.locatorId(locatorId)
				.productId(ProductId.ofRepoId(inventoryLineRecord.getM_Product_ID()))
				.asiId(asiId)
				.packingInstructions(extractPackingInstructions(inventoryLineRecord))
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

	private static InventoryLinePackingInstructions extractPackingInstructions(final @NonNull I_M_InventoryLine inventoryLineRecord)
	{
		final HUPIItemProductId tuPIItemProductId = HUPIItemProductId.ofRepoIdOrNone(inventoryLineRecord.getM_HU_PI_Item_Product_ID());
		final HuPackingInstructionsId luPIId = HuPackingInstructionsId.ofRepoIdOrNull(inventoryLineRecord.getM_LU_HU_PI_ID());

		if (tuPIItemProductId.isVirtualHU() && luPIId == null)
		{
			return InventoryLinePackingInstructions.VHU;
		}

		return InventoryLinePackingInstructions.builder()
				.tuPIItemProductId(tuPIItemProductId)
				.luPIId(luPIId)
				.build();
	}

	private static void updateRecord(@NonNull final I_M_InventoryLine lineRecord, final @NonNull InventoryLinePackingInstructions from)
	{
		lineRecord.setM_HU_PI_Item_Product_ID(from.isVHU() ? -1 : from.getTuPIItemProductId().getRepoId());
		lineRecord.setM_LU_HU_PI_ID(HuPackingInstructionsId.toRepoId(from.getLuPIId()));
	}

	private InventoryLineHU toInventoryLineHU(@NonNull final I_M_InventoryLine inventoryLineRecord)
	{
		final I_C_UOM uom = uomDAO.getById(inventoryLineRecord.getC_UOM_ID());

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
		final I_C_UOM uom = uomDAO.getById(inventoryLineHURecord.getC_UOM_ID());
		final Quantity qtyInternalUseConv;
		final Quantity qtyBookConv;
		final Quantity qtyCountConv;

		final BigDecimal qtyInternalUseBD = inventoryLineHURecord.getQtyInternalUse();
		if (qtyInternalUseBD != null && qtyInternalUseBD.signum() != 0)
		{
			final Quantity qtyInternalUse = Quantity.of(qtyInternalUseBD, uom);

			qtyInternalUseConv = uomConversionBL.convertQuantityTo(qtyInternalUse, uomConversionCtx, targetUomId);
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
			qtyBookConv = uomConversionBL.convertQuantityTo(qtyBook, uomConversionCtx, targetUomId);
			qtyCountConv = uomConversionBL.convertQuantityTo(qtyCount, uomConversionCtx, targetUomId);
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
		final HashMap<InventoryLineId, I_M_InventoryLine> existingInventoryLineRecords = getInventoryLineRecordsByIds(inventoryId, inventoryLineIds);

		for (final InventoryLine inventoryLine : lines)
		{
			final I_M_InventoryLine existingLineRecord = existingInventoryLineRecords.remove(inventoryLine.getId());
			saveInventoryLine(inventoryLine, inventoryId, existingLineRecord);
		}

		InterfaceWrapperHelper.deleteAll(existingInventoryLineRecords.values());
		// FIXME reset cache
	}

	public I_M_InventoryLine getInventoryLineRecordById(@NonNull final InventoryAndLineId inventoryAndLineId)
	{
		return getLineRecords(InventoryAndLineIdSet.of(inventoryAndLineId))
				.get(inventoryAndLineId);
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
				? getInventoryLineRecordById(InventoryAndLineId.of(inventoryId, inventoryLine.getId()))
				: null;

		saveInventoryLine(inventoryLine, inventoryId, existingLineRecord);
	}

	private void saveInventoryLine(
			@NonNull final InventoryLine inventoryLine,
			@NonNull final InventoryId inventoryId,
			@Nullable final I_M_InventoryLine existingLineRecord)
	{
		final I_M_InventoryLine lineRecord = existingLineRecord != null
				? existingLineRecord
				: InterfaceWrapperHelper.newInstance(I_M_InventoryLine.class);

		lineRecord.setAD_Org_ID(inventoryLine.getOrgId().getRepoId());
		lineRecord.setM_Inventory_ID(inventoryId.getRepoId());

		final AttributeSetInstanceId asiId = getOrCreateEffectiveASI(inventoryLine);
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
		// lineRecord.setQtyTU(BigDecimal.ZERO); // TODO

		updateInventoryLineRecordQuantities(lineRecord, inventoryLine);
		updateRecord(lineRecord, inventoryLine.getPackingInstructions());

		inventoryDAO.save(lineRecord);
		inventoryLine.setId(extractInventoryLineId(lineRecord));

		saveInventoryLineHURecords(inventoryLine, inventoryId);
	}

	private static @NotNull AttributeSetInstanceId getOrCreateEffectiveASI(final @NotNull InventoryLine inventoryLine)
	{
		return coalesceSuppliersNotNull(
				inventoryLine::getAsiId,
				() -> AttributesKeys.createAttributeSetInstanceFromAttributesKey(inventoryLine.getStorageAttributesKey()));
	}

	private static void updateInventoryLineRecordQuantities(
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
			final InventoryLineId inventoryLineId = inventoryLine.getIdNonNull();
			final OrgId orgId = inventoryLine.getOrgId();
			final ImmutableList<InventoryLineHU> inventoryLineHUs = inventoryLine.getInventoryLineHUs();

			saveInventoryLineHUs(inventoryLineHUs, inventoryId, inventoryLineId, orgId);
		}
	}

	private void saveInventoryLineHUs(
			@NonNull final ImmutableList<InventoryLineHU> inventoryLineHUs,
			@NotNull final InventoryId inventoryId,
			@NotNull final InventoryLineId inventoryLineId,
			@NotNull final OrgId orgId)
	{
		final HashMap<InventoryLineHUId, I_M_InventoryLine_HU> existingInventoryLineHURecords = getInventoryLineHURecords(inventoryLineId)
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(InventoryLoaderAndSaver::extractInventoryLineHUId));

		for (final InventoryLineHU lineHU : inventoryLineHUs)
		{
			I_M_InventoryLine_HU lineHURecord = lineHU.getId() != null
					? existingInventoryLineHURecords.remove(lineHU.getId())
					: null;
			if (lineHURecord == null)
			{
				lineHURecord = InterfaceWrapperHelper.newInstance(I_M_InventoryLine_HU.class);
			}

			lineHURecord.setAD_Org_ID(orgId.getRepoId());
			lineHURecord.setM_Inventory_ID(inventoryId.getRepoId());
			lineHURecord.setM_InventoryLine_ID(inventoryLineId.getRepoId());
			updateInventoryLineHURecord(lineHURecord, lineHU);

			InterfaceWrapperHelper.saveRecord(lineHURecord);
			lineHU.setId(extractInventoryLineHUId(lineHURecord));
		}

		// the pre-existing records that we did not see until now are not needed any more; delete them.
		InterfaceWrapperHelper.deleteAll(existingInventoryLineHURecords.values());
		// FIXME clear it from cache too
	}

	public Optional<Quantity> getFreshBookedQtyFromStorage(@NonNull final ProductId productId, @NonNull final UomId inventoryLineUOMId, @NonNull final HuId huId)
	{
		Optional<Quantity> bookedQty = Optional.empty();

		final I_M_HU hu = handlingUnitsBL.getById(huId);

		final IHUStorageFactory huStorageFactory = handlingUnitsBL.getStorageFactory();
		final List<IHUProductStorage> huProductStorages = huStorageFactory.getHUProductStorages(ImmutableList.of(hu), productId);
		if (!huProductStorages.isEmpty())
		{
			final IHUProductStorage huStorage = huProductStorages.get(0);

			final I_C_UOM inventoryLineUOM = uomDAO.getById(inventoryLineUOMId);

			final BigDecimal qtyInInventoryUOM = uomConversionBL.convertQty(
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

	private List<I_M_InventoryLine> getLineRecords(@NonNull final InventoryId inventoryId)
	{
		return CollectionUtils.getAllOrLoadReturningMap(this.inventoryLineRecordsByInventoryId, ImmutableSet.of(inventoryId), this::retrieveLineRecords)
				.get(inventoryId);
	}

	public Map<InventoryAndLineId, I_M_InventoryLine> getLineRecords(@NonNull final InventoryAndLineIdSet inventoryAndLineIds)
	{
		if (inventoryAndLineIds.isEmpty()) {return ImmutableMap.of();}

		final Collection<List<I_M_InventoryLine>> result = CollectionUtils.getAllOrLoad(
				this.inventoryLineRecordsByInventoryId,
				inventoryAndLineIds.getInventoryIds(),
				this::retrieveLineRecords
		);

		final ImmutableMap<InventoryAndLineId, I_M_InventoryLine> allLinesMap = result.stream()
				.flatMap(List::stream)
				.collect(ImmutableMap.toImmutableMap(InventoryAndLineId::of, Function.identity()));

		return inventoryAndLineIds.stream()
				.map(inventoryAndLineId -> {
					final I_M_InventoryLine line = allLinesMap.get(inventoryAndLineId);
					if (line == null)
					{
						throw new AdempiereException("No line found for " + inventoryAndLineId);
					}
					return GuavaCollectors.entry(inventoryAndLineId, line);
				})
				.collect(GuavaCollectors.toImmutableMap());
	}

	private HashMap<InventoryLineId, I_M_InventoryLine> getInventoryLineRecordsByIds(
			@NonNull final InventoryId inventoryId,
			@NonNull final Set<InventoryLineId> inventoryLineIds)
	{
		if (inventoryLineIds.isEmpty()) {return new HashMap<>();}
		return getLineRecords(inventoryId)
				.stream()
				.filter(inventoryLineRecord -> inventoryLineIds.contains(extractInventoryLineId(inventoryLineRecord)))
				.collect(GuavaCollectors.toHashMapByKey(InventoryLoaderAndSaver::extractInventoryLineId));
	}

	private Map<InventoryId, List<I_M_InventoryLine>> retrieveLineRecords(@NonNull final Set<InventoryId> inventoryIds)
	{
		if (inventoryIds.isEmpty()) {return ImmutableMap.of();}

		final Map<InventoryId, List<I_M_InventoryLine>> map = queryBL.createQueryBuilder(I_M_InventoryLine.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_InventoryLine.COLUMNNAME_M_Inventory_ID, inventoryIds)
				.orderBy(I_M_InventoryLine.COLUMNNAME_M_Inventory_ID)
				.orderBy(I_M_InventoryLine.COLUMNNAME_Line)
				.orderBy(I_M_InventoryLine.COLUMNNAME_M_InventoryLine_ID)
				.create()
				.stream()
				.collect(Collectors.groupingBy(InventoryLoaderAndSaver::extractInventoryId));

		return CollectionUtils.fillMissingKeys(map, inventoryIds, ImmutableList.of());
	}

	private static InventoryLineId extractInventoryLineId(@NonNull final I_M_InventoryLine_HU inventoryLineHURecord)
	{
		return InventoryLineId.ofRepoId(inventoryLineHURecord.getM_InventoryLine_ID());
	}

	private static InventoryLineHUId extractInventoryLineHUId(@NonNull final I_M_InventoryLine_HU inventoryLineHURecord)
	{
		return InventoryLineHUId.ofRepoId(inventoryLineHURecord.getM_InventoryLine_HU_ID());
	}

	private List<I_M_InventoryLine_HU> getInventoryLineHURecords(@NonNull final InventoryLineId inventoryLineId)
	{
		return CollectionUtils.getAllOrLoadReturningMap(this.inventoryLineHURecordsByInventoryLineId, ImmutableSet.of(inventoryLineId), this::retrieveInventoryLineHURecords)
				.get(inventoryLineId);
	}

	private Map<InventoryLineId, List<I_M_InventoryLine_HU>> retrieveInventoryLineHURecords(@NonNull final Collection<InventoryLineId> inventoryLineIds)
	{
		if (inventoryLineIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		@NotNull final Map<InventoryLineId, List<I_M_InventoryLine_HU>> map = queryBL.createQueryBuilder(I_M_InventoryLine_HU.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_InventoryLine_HU.COLUMNNAME_M_InventoryLine_ID, inventoryLineIds)
				.orderBy(I_M_InventoryLine_HU.COLUMN_M_HU_ID)
				.create()
				.stream()
				.collect(Collectors.groupingBy(InventoryLoaderAndSaver::extractInventoryLineId, Collectors.toList()));

		return CollectionUtils.fillMissingKeys(map, inventoryLineIds, ImmutableList.of());
	}

	public void deleteInventoryLineHUs(@NonNull final InventoryLineId inventoryLineId)
	{
		queryBL.createQueryBuilder(I_M_InventoryLine_HU.class)
				.addEqualsFilter(I_M_InventoryLine_HU.COLUMN_M_InventoryLine_ID, inventoryLineId)
				.create()
				.delete();
	}
}
