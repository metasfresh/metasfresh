package org.adempiere.impexp.inventory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.impexp.AbstractImportProcess;
import org.adempiere.impexp.IImportInterceptor;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_I_Inventory;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_I_Inventory;
import org.slf4j.Logger;

import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import lombok.NonNull;

/**
 * Import {@link I_I_Inventory} to {@link I_M_Inventory}.
 *
 */
public class InventoryImportProcess extends AbstractImportProcess<I_I_Inventory>
{
	private static final Logger logger = LogManager.getLogger(InventoryImportProcess.class);
	private static final String PARAM_M_Locator_ID = "M_Locator_ID";

	private final IProductBL productBL = Services.get(IProductBL.class);

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
		return org.compiere.model.I_M_Inventory.Table_Name;
	}

//	@Override
//	protected Map<String, Object> getImportTableDefaultValues()
//	{
////		return ImmutableMap.<String, Object> builder()
////				.put(I_I_Inventory.COLUMNNAME_, X_I_Product.PRODUCTTYPE_Item)
////				.build();
//	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		final String whereClause = getWhereClause();
		MInventoryImportTableSqlUpdater.builder()
				.whereClause(whereClause)
				.ctx(getCtx())
				.locatorId(getM_Locator_ID())
				.updateIInventory();
	}

	private int getM_Locator_ID()
	{
		return getParameters().getParameterAsInt(PARAM_M_Locator_ID);
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_Inventory.COLUMNNAME_SerNo;
	}

	@Override
	protected I_I_Inventory retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException
	{
		return new X_I_Inventory(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}


	@Override
	protected ImportRecordResult importRecord(@NonNull final IMutable<Object> state, @NonNull final I_I_Inventory importRecord) throws Exception
	{
		//
		// Get previous values
		MInventoryImportContext context = (MInventoryImportContext)state.getValue();
		if (context == null)
		{
			context = new MInventoryImportContext();
			state.setValue(context);
		}
		final I_I_Inventory previousImportRecord = context.getPreviousImportRecord();
		final int previousMInventoryId = context.getPreviousM_Inventory_ID();
		final String previousWarehouseValue = context.getPreviousWarehouseValue();
		final Timestamp previousMovementDate = context.getPreviousMovementDate();
		context.setPreviousImportRecord(importRecord);

		final ImportRecordResult inventoryImportResult;

		final boolean firstImportRecordOrNewMInventory = previousImportRecord == null
				|| !Objects.equals(importRecord.getWarehouseValue(), previousWarehouseValue)
				|| !Objects.equals(importRecord.getMovementDate(), previousMovementDate);

		if (firstImportRecordOrNewMInventory)
		{
			// create a new list because we are passing to a new inventory
			context.clearPreviousRecordsForSameInventory();
			inventoryImportResult = importInventory(importRecord);
		}
		else
		{
			if (previousMInventoryId <= 0)
			{
				inventoryImportResult = importInventory(importRecord);
			}
			else if (importRecord.getM_Inventory_ID() <= 0 || importRecord.getM_Inventory_ID() == previousMInventoryId)
			{
				inventoryImportResult = doNothingAndUsePreviousInventory(importRecord, previousImportRecord);
			}
			else
			{
				throw new AdempiereException("Same value or movement date as previous line but not same Inventory linked");
			}
		}

		importInventoryLine(importRecord);
		context.collectImportRecordForSameInventory(importRecord);

		return inventoryImportResult;
	}

	private ImportRecordResult importInventory(@NonNull final I_I_Inventory importRecord)
	{
		final ImportRecordResult inventoryImportResult;
		inventoryImportResult = importRecord.getM_Inventory_ID() <= 0 ? ImportRecordResult.Inserted : ImportRecordResult.Updated;

		final I_M_Inventory inventory;
		if (importRecord.getM_Inventory_ID()  <= 0)	// Insert new Inventory
		{
			inventory = createNewMInventory(importRecord);
		}
		else
		{
			inventory = importRecord.getM_Inventory();
		}

		ModelValidationEngine.get().fireImportValidate(this, importRecord, inventory, IImportInterceptor.TIMING_AFTER_IMPORT);
		InterfaceWrapperHelper.save(inventory);
		importRecord.setM_Inventory_ID(inventory.getM_Inventory_ID());
		return inventoryImportResult;
	}

	private I_M_Inventory createNewMInventory(@NonNull final I_I_Inventory importRecord)
	{
		final I_M_Inventory inventory;
		inventory = InterfaceWrapperHelper.create(getCtx(), I_M_Inventory.class, ITrx.TRXNAME_ThreadInherited);
		inventory.setAD_Org_ID(importRecord.getAD_Org_ID());
		inventory.setDescription("I " + importRecord.getM_Warehouse_ID() + " " + importRecord.getMovementDate());
		inventory.setC_DocType_ID(getDocTypeIdForInternalUseInventory(importRecord));
		inventory.setM_Warehouse_ID(importRecord.getM_Warehouse_ID());
		return inventory;
	}

	private int getDocTypeIdForInternalUseInventory(@NonNull final I_I_Inventory importRecord)
	{
		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
		return docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory)
				.docSubType(X_C_DocType.DOCSUBTYPE_InternalUseInventory)
				.adClientId(importRecord.getAD_Client_ID())
				.adOrgId(importRecord.getAD_Org_ID())
				.build());
	}

	/**
	 * 	 reuse previous inventory
	 *
	 * @param importRecord
	 * @param previousImportRecord
	 * @return
	 */
	private ImportRecordResult doNothingAndUsePreviousInventory(@NonNull final I_I_Inventory importRecord, @NonNull final I_I_Inventory previousImportRecord)
	{
		importRecord.setM_Inventory_ID(previousImportRecord.getM_Inventory_ID());
		return ImportRecordResult.Nothing;
	}

	public I_M_InventoryLine importInventoryLine(@NonNull final I_I_Inventory importRecord)
	{
		final I_M_Inventory inventory = importRecord.getM_Inventory();

		I_M_InventoryLine inventoryLine = importRecord.getM_InventoryLine();
		if (inventoryLine != null)
		{
			if (inventoryLine.getM_Inventory_ID() <= 0)
			{
				inventoryLine.setM_Inventory(inventory);
			}
			else if (inventoryLine.getM_Inventory_ID() != inventory.getM_Inventory_ID())
			{
				throw new AdempiereException("Inventory of Inventory Line <> Inventory");
			}

			inventoryLine.setM_Locator(importRecord.getM_Locator());
			inventoryLine.setM_Product(importRecord.getM_Product());
			inventoryLine.setQtyInternalUse(importRecord.getQtyInternalUse());

			ModelValidationEngine.get().fireImportValidate(this, importRecord, inventoryLine, IImportInterceptor.TIMING_AFTER_IMPORT);
			InterfaceWrapperHelper.save(inventoryLine);
		}
		else
		{
			final int M_AttributeSetInstance_ID = extractM_AttributeSetInstance_ID(importRecord);
			inventoryLine = InterfaceWrapperHelper.create(getCtx(), I_M_InventoryLine.class, ITrx.TRXNAME_ThreadInherited);
			inventoryLine.setQtyInternalUse(importRecord.getQtyInternalUse());
			inventoryLine.setM_Inventory(inventory);
			inventoryLine.setM_Locator(importRecord.getM_Locator());
			inventoryLine.setM_Product(importRecord.getM_Product());
			inventoryLine.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);

			ModelValidationEngine.get().fireImportValidate(this, importRecord, inventoryLine, IImportInterceptor.TIMING_AFTER_IMPORT);
			InterfaceWrapperHelper.save(inventoryLine);
			logger.trace("Insert inventory line - {}", inventoryLine);

			importRecord.setM_InventoryLine_ID(inventoryLine.getM_InventoryLine_ID());
		}

		return inventoryLine;
	}

	private int extractM_AttributeSetInstance_ID(@NonNull final I_I_Inventory importRecord)
	{
		int M_AttributeSetInstance_ID = 0;
		if (importRecord.getLot() != null || importRecord.getSerNo() != null)
		{
			final I_M_Product product = InterfaceWrapperHelper.load(importRecord.getM_Product_ID(), I_M_Product.class);
			if (productBL.isInstanceAttribute(product))
			{
				final I_M_AttributeSet mas = productBL.getM_AttributeSet(product);
				final I_M_AttributeSetInstance masi = InterfaceWrapperHelper.newInstance(I_M_AttributeSetInstance.class);
				masi.setM_AttributeSet(mas);

				if (mas.isLot() && importRecord.getLot() != null)
				{
//					masi.setLot(importRecord.getLot(), importRecord.getM_Product_ID());
				}
				if (mas.isSerNo() && importRecord.getSerNo() != null)
				{
					masi.setSerNo(importRecord.getSerNo());
				}
				Services.get(IAttributeSetInstanceBL.class).setDescription(masi);
				InterfaceWrapperHelper.save(masi);
				M_AttributeSetInstance_ID = masi.getM_AttributeSetInstance_ID();
			}
		}

		return M_AttributeSetInstance_ID;
	}


}
