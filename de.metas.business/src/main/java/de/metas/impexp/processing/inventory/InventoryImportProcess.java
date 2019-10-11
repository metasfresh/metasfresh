package de.metas.impexp.processing.inventory;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.AttributeListValueCreateRequest;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ILotNumberDateAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_I_Inventory;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_I_Inventory;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableMap;

import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.impexp.processing.ImportGroupKey;
import de.metas.impexp.processing.ImportGroupResult;
import de.metas.impexp.processing.ImportProcessTemplate;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.inventory.IInventoryBL;
import de.metas.inventory.InventoryId;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.NonNull;

/**
 * Import {@link I_I_Inventory} to {@link I_M_Inventory}.
 *
 */
public class InventoryImportProcess extends ImportProcessTemplate<I_I_Inventory>
{
	private static final Logger logger = LogManager.getLogger(InventoryImportProcess.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final ILotNumberDateAttributeDAO lotNumberDateAttributeDAO = Services.get(ILotNumberDateAttributeDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IInventoryBL inventoryBL = Services.get(IInventoryBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

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
				.put(I_I_Inventory.COLUMNNAME_InventoryDate, SystemTime.asDayTimestamp())
				.build();
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		final ImportRecordsSelection selection = getImportRecordsSelection();

		MInventoryImportTableSqlUpdater.updateInventoryImportTable(selection);

		final int countErrorRecords = MInventoryImportTableSqlUpdater.countRecordsWithErrors(selection);
		getResultCollector().setCountImportRecordsWithErrors(countErrorRecords);
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_Inventory.COLUMNNAME_WarehouseValue
				+ ", " + I_I_Inventory.COLUMNNAME_InventoryDate
				+ ", " + I_I_Inventory.COLUMNNAME_I_Inventory_ID;
	}

	@Override
	protected ImportGroupKey extractImportGroupKey(final I_I_Inventory importRecord)
	{
		return ImportGroupKey.builder()
				.value("warehouse", Integer.toString(importRecord.getM_Warehouse_ID())) // importRecord.getWarehouseValue might be empty/null if the warehouse was looked up only via locatorValue
				.value("movementDate", TimeUtil.asLocalDate(importRecord.getInventoryDate()).toString())
				.build();
	}

	@Override
	protected I_I_Inventory retrieveImportRecord(final Properties ctx, final ResultSet rs)
	{
		return new X_I_Inventory(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	protected ImportGroupResult importRecords(
			@NonNull final List<I_I_Inventory> importRecords,
			@NonNull final IMutable<Object> stateHolder)
	{
		final I_M_Inventory inventory = createInventoryHeader(importRecords.get(0));
		final InventoryId inventoryId = InventoryId.ofRepoId(inventory.getM_Inventory_ID());

		for (final I_I_Inventory importRecord : importRecords)
		{
			createInventoryLine(importRecord, inventoryId);
		}

		if (isCompleteDocuments())
		{
			documentBL.processEx(inventory, IDocument.ACTION_Complete);
		}

		return ImportGroupResult.countInserted(importRecords.size());
	}

	private I_M_Inventory createInventoryHeader(@NonNull final I_I_Inventory importRecord)
	{
		final DocTypeId docTypeId = getDocTypeId(importRecord);

		final I_M_Inventory inventory = newInstance(I_M_Inventory.class);
		inventory.setExternalId(importRecord.getExternalHeaderId());
		inventory.setAD_Org_ID(importRecord.getAD_Org_ID());
		inventory.setDescription("I " + importRecord.getM_Warehouse_ID() + " " + importRecord.getInventoryDate());
		inventory.setC_DocType_ID(docTypeId.getRepoId());
		inventory.setM_Warehouse_ID(importRecord.getM_Warehouse_ID());
		inventory.setMovementDate(importRecord.getInventoryDate());
		saveRecord(inventory);
		return inventory;
	}

	private DocTypeId getDocTypeId(@NonNull final I_I_Inventory importRecord)
	{
		return docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory)
				.adClientId(importRecord.getAD_Client_ID())
				.adOrgId(importRecord.getAD_Org_ID())
				.build());
	}

	private void createInventoryLine(
			@NonNull final I_I_Inventory importRecord,
			@NonNull final InventoryId inventoryId)
	{
		final I_M_InventoryLine inventoryLine = InterfaceWrapperHelper.newInstance(I_M_InventoryLine.class);
		inventoryLine.setExternalId(importRecord.getExternalLineId());
		inventoryLine.setQtyCount(importRecord.getQtyCount());
		inventoryLine.setM_Inventory_ID(inventoryId.getRepoId());
		inventoryLine.setM_Locator_ID(importRecord.getM_Locator_ID());

		final ProductId productId = ProductId.ofRepoId(importRecord.getM_Product_ID());
		final UomId uomId = productBL.getStockUOMId(productId);
		inventoryLine.setM_Product_ID(productId.getRepoId());
		inventoryLine.setC_UOM_ID(uomId.getRepoId());

		final AttributeSetInstanceId asiId = extractASI(importRecord);
		inventoryLine.setM_AttributeSetInstance_ID(asiId.getRepoId());

		final int chargeId = inventoryBL.getDefaultInternalChargeId();
		inventoryLine.setC_Charge_ID(chargeId);

		inventoryLine.setIsCounted(true);

		InterfaceWrapperHelper.saveRecord(inventoryLine);
		logger.trace("Insert inventory line - {}", inventoryLine);

		//
		importRecord.setM_Inventory_ID(inventoryId.getRepoId());
		importRecord.setM_InventoryLine_ID(inventoryLine.getM_InventoryLine_ID());
	}

	private AttributeSetInstanceId extractASI(@NonNull final I_I_Inventory importRecord)
	{
		final ProductId productId = ProductId.ofRepoId(importRecord.getM_Product_ID());
		if (!productBL.isInstanceAttribute(productId))
		{
			return AttributeSetInstanceId.NONE;
		}

		final I_M_AttributeSetInstance asi = attributeSetInstanceBL.createASI(productId);

		//
		// Lot
		if (!Check.isEmpty(importRecord.getLot(), true))
		{
			final AttributeId lotNumberAttrId = lotNumberDateAttributeDAO.getLotNumberAttributeId();
			attributeSetInstanceBL.setAttributeInstanceValue(asi, lotNumberAttrId, importRecord.getLot());
		}

		//
		// BestBeforeDate
		if (importRecord.getHU_BestBeforeDate() != null)
		{
			final I_M_Attribute bestBeforeDateAttr = attributeDAO.retrieveAttributeByValue(AttributeConstants.ATTR_BestBeforeDate);
			attributeSetInstanceBL.setAttributeInstanceValue(asi, bestBeforeDateAttr, importRecord.getHU_BestBeforeDate());
		}

		//
		// TE
		if (!Check.isEmpty(importRecord.getTE(), true))
		{
			final I_M_Attribute TEAttr = attributeDAO.retrieveAttributeByValue(AttributeConstants.ATTR_TE);
			attributeSetInstanceBL.setAttributeInstanceValue(asi, TEAttr, importRecord.getTE());
		}

		//
		// DateReceived
		if (importRecord.getDateReceived() != null)
		{
			final I_M_Attribute dateReceivedAttr = attributeDAO.retrieveAttributeByValue(AttributeConstants.ATTR_DateReceived);
			attributeSetInstanceBL.setAttributeInstanceValue(asi, dateReceivedAttr, importRecord.getDateReceived());
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
		I_M_AttributeInstance attributeInstance = attributeDAO.retrieveAttributeInstance(asi, attributeId);
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
