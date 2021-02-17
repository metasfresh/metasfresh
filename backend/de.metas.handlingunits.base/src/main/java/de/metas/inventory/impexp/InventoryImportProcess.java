package de.metas.inventory.impexp;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Nullable;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.AttributeListValueCreateRequest;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_I_Inventory;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_I_Inventory;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.handlingunits.inventory.InventoryLineHU;
import de.metas.handlingunits.inventory.InventoryRepository;
import de.metas.handlingunits.inventory.draftlinescreator.DraftInventoryLinesCreator;
import de.metas.handlingunits.inventory.draftlinescreator.HUsForInventoryStrategies;
import de.metas.handlingunits.inventory.draftlinescreator.HuForInventoryLine;
import de.metas.handlingunits.inventory.draftlinescreator.HuForInventoryLineFactory;
import de.metas.handlingunits.inventory.draftlinescreator.LocatorAndProductStrategy;
import de.metas.handlingunits.model.I_M_Inventory;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.impexp.processing.ImportGroupResult;
import de.metas.impexp.processing.ImportProcessTemplate;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.inventory.AggregationType;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.IInventoryBL;
import de.metas.inventory.InventoryId;
import de.metas.inventory.impexp.InventoryImportProcess.InventoryGroupKey;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Import {@link I_I_Inventory} to {@link I_M_Inventory}.
 *
 */
public class InventoryImportProcess extends ImportProcessTemplate<I_I_Inventory, InventoryGroupKey>
{
	private static final Logger logger = LogManager.getLogger(InventoryImportProcess.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IInventoryBL inventoryBL = Services.get(IInventoryBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final InventoryRepository inventoryRepository = SpringContextHolder.instance.getBean(InventoryRepository.class);
	private final HuForInventoryLineFactory huForInventoryLineFactory = SpringContextHolder.instance.getBean(HuForInventoryLineFactory.class);

	private final Timestamp now = SystemTime.asDayTimestamp();

	@Override
	public Class<I_I_Inventory> getImportModelClass()
	{
		return I_I_Inventory.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_Inventory.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return I_M_Inventory.Table_Name;
	}

	@Override
	protected Map<String, Object> getImportTableDefaultValues()
	{
		return ImmutableMap.<String, Object> builder()
				.put(I_I_Inventory.COLUMNNAME_InventoryDate, de.metas.common.util.time.SystemTime.asDayTimestamp())
				.build();
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		final ImportRecordsSelection selection = getImportRecordsSelection();

		MInventoryImportTableSqlUpdater.updateInventoryImportTable(selection);

		final int countErrorRecords = MInventoryImportTableSqlUpdater.countRecordsWithErrors(selection);
		getResultCollector().setCountImportRecordsWithValidationErrors(countErrorRecords);
	}

	/**
	 * NOTE to dev: keep in sync with {@link InventoryGroupKey}.
	 */
	@Override
	protected String getImportOrderBySql()
	{
		return I_I_Inventory.COLUMNNAME_ExternalHeaderId
				+ ", " + I_I_Inventory.COLUMNNAME_AD_Org_ID
				+ ", " + I_I_Inventory.COLUMNNAME_WarehouseValue
				+ ", " + I_I_Inventory.COLUMNNAME_InventoryDate
				+ ", " + I_I_Inventory.COLUMNNAME_HUAggregationType
				//
				+ ", " + I_I_Inventory.COLUMNNAME_I_Inventory_ID;
	}

	/**
	 * NOTE to dev: keep in sync with {@link InventoryImportProcess#getImportOrderBySql()}
	 */
	@Value
	@Builder
	public static class InventoryGroupKey
	{
		@Nullable
		String externalHeaderId;

		@NonNull
		DocTypeId docTypeId;

		@NonNull
		OrgId warehouseOrgId;

		@NonNull
		WarehouseId warehouseId;

		@NonNull
		Timestamp inventoryDate;
	}

	@Override
	protected InventoryGroupKey extractImportGroupKey(final I_I_Inventory importRecord)
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(importRecord.getM_Warehouse_ID());
		if (warehouseId == null)
		{
			throw new FillMandatoryException(I_I_Inventory.COLUMNNAME_M_Warehouse_ID);
		}

		final OrgId warehouseOrgId = warehouseBL.getWarehouseOrgId(warehouseId);

		final DocTypeId docTypeId = getDocTypeId(importRecord, warehouseOrgId);

		return InventoryGroupKey.builder()
				.externalHeaderId(StringUtils.trimBlankToNull(importRecord.getExternalHeaderId()))
				.docTypeId(docTypeId)
				.warehouseOrgId(warehouseOrgId)
				.warehouseId(warehouseId)
				.inventoryDate(CoalesceUtil.coalesce(importRecord.getInventoryDate(), now))
				.build();
	}

	private DocTypeId getDocTypeId(
			@NonNull final I_I_Inventory importRecord,
			@NonNull final OrgId orgId)
	{
		final HUAggregationType huAggregationType = HUAggregationType.ofNullableCode(importRecord.getHUAggregationType());
		final DocBaseAndSubType docBaseAndSubType = getDocBaseAndSubType(huAggregationType);

		final int clientId = importRecord.getAD_Client_ID();

		return docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(docBaseAndSubType.getDocBaseType())
				.docSubType(docBaseAndSubType.getDocSubType())
				.adClientId(clientId)
				.adOrgId(orgId.getRepoId())
				.build());
	}

	private static DocBaseAndSubType getDocBaseAndSubType(@Nullable final HUAggregationType huAggregationType)
	{
		if (huAggregationType == null)
		{
			return DocBaseAndSubType.of(X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory);
		}
		else
		{
			return AggregationType.getByHUAggregationType(huAggregationType).getDocBaseAndSubType();
		}
	}

	@Override
	protected I_I_Inventory retrieveImportRecord(final Properties ctx, final ResultSet rs)
	{
		return new X_I_Inventory(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	protected ImportGroupResult importRecords(
			@NonNull final InventoryGroupKey groupKey,
			@NonNull final List<I_I_Inventory> importRecords,
			@NonNull final IMutable<Object> stateHolder_NOTUSED)
	{
		final I_M_Inventory inventory = createInventoryHeader(groupKey);
		final InventoryId inventoryId = InventoryId.ofRepoId(inventory.getM_Inventory_ID());

		for (final I_I_Inventory importRecord : importRecords)
		{
			createInventoryLine(importRecord, inventoryId, groupKey.getWarehouseOrgId());
		}

		if (isCompleteDocuments())
		{
			documentBL.processEx(inventory, IDocument.ACTION_Complete);
		}

		return ImportGroupResult.countInserted(importRecords.size());
	}

	private I_M_Inventory createInventoryHeader(@NonNull final InventoryGroupKey groupKey)
	{
		final I_M_Inventory inventory = newInstance(I_M_Inventory.class);
		inventory.setExternalId(groupKey.getExternalHeaderId());
		inventory.setAD_Org_ID(groupKey.getWarehouseOrgId().getRepoId());
		inventory.setC_DocType_ID(groupKey.getDocTypeId().getRepoId());
		inventory.setM_Warehouse_ID(groupKey.getWarehouseId().getRepoId());
		inventory.setMovementDate(groupKey.getInventoryDate());
		saveRecord(inventory);
		return inventory;
	}

	private void createInventoryLine(
			@NonNull final I_I_Inventory importRecord,
			@NonNull final InventoryId inventoryId,
			@NonNull final OrgId orgId)
	{
		final LocatorId locatorId = warehouseDAO.getLocatorIdByRepoIdOrNull(importRecord.getM_Locator_ID());
		Check.assumeNotNull(locatorId, "locator shall be set");

		final ProductId productId = ProductId.ofRepoId(importRecord.getM_Product_ID());
		final I_C_UOM stockingUOM = productBL.getStockUOM(productId);
		final Quantity qtyCount = Quantity.of(importRecord.getQtyCount(), stockingUOM);
		final AttributeSetInstanceId asiId = extractASI(importRecord);
		final int chargeId = inventoryBL.getDefaultInternalChargeId();
		final HUAggregationType huAggregationType = HUAggregationType.ofNullableCode(importRecord.getHUAggregationType());

		final List<HuForInventoryLine> hus;
		if (HUAggregationType.MULTI_HU.equals(huAggregationType))
		{
			hus = getEligibleHUs(productId, asiId, locatorId);
		}
		else
		{
			hus = ImmutableList.of();
		}

		final Quantity qtyBooked = computeTotalQtyBooked(hus, qtyCount.getUOM(), UOMConversionContext.of(productId));

		final I_M_InventoryLine inventoryLineRecord = InterfaceWrapperHelper.newInstance(I_M_InventoryLine.class);
		inventoryLineRecord.setM_Inventory_ID(inventoryId.getRepoId());
		inventoryLineRecord.setAD_Org_ID(orgId.getRepoId());
		inventoryLineRecord.setExternalId(importRecord.getExternalLineId());
		inventoryLineRecord.setM_Locator_ID(locatorId.getRepoId());
		inventoryLineRecord.setM_Product_ID(productId.getRepoId());
		inventoryLineRecord.setM_AttributeSetInstance_ID(asiId.getRepoId());
		inventoryLineRecord.setCostPrice(importRecord.getCostPrice());
		inventoryLineRecord.setC_Charge_ID(chargeId);

		//
		// Qtys
		inventoryLineRecord.setHUAggregationType(HUAggregationType.toCodeOrNull(huAggregationType));
		Quantity.getCommonUomIdOfAll(qtyCount, qtyBooked); // just to make sure we are using same UOM
		inventoryLineRecord.setC_UOM_ID(qtyCount.getUomId().getRepoId());
		inventoryLineRecord.setQtyCount(qtyCount.toBigDecimal());
		inventoryLineRecord.setQtyBook(qtyBooked.toBigDecimal());
		inventoryLineRecord.setIsCounted(true);

		InterfaceWrapperHelper.saveRecord(inventoryLineRecord);
		logger.trace("Insert inventory line - {}", inventoryLineRecord);

		//
		final List<InventoryLineHU> inventoryLineHUs;
		if (!hus.isEmpty())
		{
			inventoryLineHUs = toInventoryLineHUs(hus);
		}
		else
		{
			inventoryLineHUs = ImmutableList.of(InventoryLineHU.builder()
					.huId(null) // will be created later, on inventory complete. this is just a placeholder
					.qtyCount(qtyCount)
					.qtyBook(qtyBooked)
					.build());
		}

		//
		final InventoryLine inventoryLine = inventoryRepository.toInventoryLine(inventoryLineRecord)
				.withInventoryLineHUs(inventoryLineHUs)
				.distributeQtyCountToHUs(qtyCount);

		inventoryRepository.saveInventoryLineHURecords(inventoryLine, inventoryId);

		//
		importRecord.setM_Inventory_ID(inventoryId.getRepoId());
		importRecord.setM_InventoryLine_ID(inventoryLineRecord.getM_InventoryLine_ID());
	}

	private List<InventoryLineHU> toInventoryLineHUs(final List<HuForInventoryLine> hus)
	{
		return hus.stream()
				.map(DraftInventoryLinesCreator::toInventoryLineHU)
				.collect(ImmutableList.toImmutableList());
	}

	private List<HuForInventoryLine> getEligibleHUs(
			@NonNull final ProductId productId,
			@NonNull final AttributeSetInstanceId asiId,
			@NonNull final LocatorId locatorId)
	{
		final LocatorAndProductStrategy husFinder = HUsForInventoryStrategies.locatorAndProduct()
				.huForInventoryLineFactory(huForInventoryLineFactory)
				//
				// .warehouseId(null)
				.locatorId(locatorId)
				.productId(productId)
				.asiId(asiId)
				//
				.build();

		return husFinder.streamHus()
				.collect(ImmutableList.toImmutableList());
	}

	private Quantity computeTotalQtyBooked(
			final List<HuForInventoryLine> hus,
			final I_C_UOM targetUOM,
			final UOMConversionContext conversionCtx)
	{
		return hus.stream()
				.map(HuForInventoryLine::getQuantityBooked)
				.map(qty -> uomConversionBL.convertQuantityTo(qty, conversionCtx, targetUOM))
				.reduce(Quantity::add)
				.orElseGet(() -> Quantity.zero(targetUOM));
	}

	@VisibleForTesting
	AttributeSetInstanceId extractASI(@NonNull final I_I_Inventory importRecord)
	{
		final ProductId productId = ProductId.ofRepoId(importRecord.getM_Product_ID());
		if (!productBL.isInstanceAttribute(productId))
		{
			return AttributeSetInstanceId.NONE;
		}

		final I_M_AttributeSetInstance asi = attributeSetInstanceBL.createASI(productId);
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID());

		if (!Check.isBlank(importRecord.getAttributeCode1()))
		{
			final AttributeCode attributeCode = AttributeCode.ofString(importRecord.getAttributeCode1().trim());
			attributeSetInstanceBL.setAttributeInstanceValue(asiId, attributeCode, importRecord.getAttributeValueString1());
		}
		if (!Check.isBlank(importRecord.getAttributeCode2()))
		{
			final AttributeCode attributeCode = AttributeCode.ofString(importRecord.getAttributeCode2().trim());
			attributeSetInstanceBL.setAttributeInstanceValue(asiId, attributeCode, importRecord.getAttributeValueString2());
		}
		if (!Check.isBlank(importRecord.getAttributeCode3()))
		{
			final AttributeCode attributeCode = AttributeCode.ofString(importRecord.getAttributeCode3().trim());
			attributeSetInstanceBL.setAttributeInstanceValue(asiId, attributeCode, importRecord.getAttributeValueString3());
		}
		if (!Check.isBlank(importRecord.getAttributeCode4()))
		{
			final AttributeCode attributeCode = AttributeCode.ofString(importRecord.getAttributeCode4().trim());
			attributeSetInstanceBL.setAttributeInstanceValue(asiId, attributeCode, importRecord.getAttributeValueString4());
		}

		//
		// Lot
		if (!Check.isBlank(importRecord.getLot()))
		{
			attributeSetInstanceBL.setAttributeInstanceValue(asiId, AttributeConstants.ATTR_LotNumber, importRecord.getLot());
		}

		//
		// BestBeforeDate
		if (importRecord.getHU_BestBeforeDate() != null)
		{
			attributeSetInstanceBL.setAttributeInstanceValue(asiId, AttributeConstants.ATTR_BestBeforeDate, importRecord.getHU_BestBeforeDate());
		}

		//
		// TE
		if (!Check.isBlank(importRecord.getTE()))
		{
			attributeSetInstanceBL.setAttributeInstanceValue(asiId, AttributeConstants.ATTR_TE, importRecord.getTE());
		}

		//
		// DateReceived
		if (importRecord.getDateReceived() != null)
		{
			attributeSetInstanceBL.setAttributeInstanceValue(asiId, AttributeConstants.ATTR_DateReceived, importRecord.getDateReceived());
		}

		//
		// SubProducerBPartner_Value
		{
			final AttributeListValue subProducerBPartneValue = getOrCreateSubproducerAttributeValue(importRecord);
			if (subProducerBPartneValue != null)
			{
				getCreateAttributeInstanceForSubproducer(asi, subProducerBPartneValue);
			}
		}

		attributeSetInstanceBL.setDescription(asi);
		InterfaceWrapperHelper.saveRecord(asi);

		return AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID());
	}

	@Nullable
	private AttributeListValue getOrCreateSubproducerAttributeValue(@NonNull final I_I_Inventory importRecord)
	{
		final String subproducerBPartnerValue = importRecord.getSubProducerBPartner_Value();
		if (Check.isEmpty(subproducerBPartnerValue, true))
		{
			return null;
		}

		final int subproducerBPartnerId = importRecord.getSubProducer_BPartner_ID();
		if (subproducerBPartnerId <= 0)
		{
			return null;
		}

		final I_M_Attribute subProducerAttribute = attributeDAO.retrieveAttributeByValue(AttributeConstants.ATTR_SubProducerBPartner_Value);

		final String subproducerBPartnerIdString = String.valueOf(subproducerBPartnerId);
		final AttributeListValue existingAttributeValue = attributeDAO.retrieveAttributeValueOrNull(subProducerAttribute, subproducerBPartnerIdString);
		if (existingAttributeValue != null)
		{
			return existingAttributeValue;
		}
		else
		{
			return attributeDAO.createAttributeValue(AttributeListValueCreateRequest.builder()
					.attributeId(AttributeId.ofRepoId(subProducerAttribute.getM_Attribute_ID()))
					.value(subproducerBPartnerIdString)
					.name(subproducerBPartnerValue)
					.build());
		}
	}

	private void getCreateAttributeInstanceForSubproducer(
			@NonNull final I_M_AttributeSetInstance asi,
			@NonNull final AttributeListValue attributeValue)
	{
		// M_Attribute_ID
		final AttributeId attributeId = attributeValue.getAttributeId();

		//
		// Get/Create/Update Attribute Instance
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(asi.getM_AttributeSetInstance_ID());
		I_M_AttributeInstance attributeInstance = attributeDAO.retrieveAttributeInstance(asiId, attributeId);
		if (attributeInstance == null)
		{
			attributeInstance = newInstance(I_M_AttributeInstance.class, asi);
		}
		attributeInstance.setM_AttributeSetInstance(asi);
		attributeInstance.setM_AttributeValue_ID(attributeValue.getId().getRepoId());
		attributeInstance.setM_Attribute_ID(attributeId.getRepoId());
		// the attribute is a list, but expect to store as number, the id of the partner
		attributeInstance.setValueNumber(new BigDecimal(attributeValue.getValue()));

		saveRecord(attributeInstance);
	}
}
