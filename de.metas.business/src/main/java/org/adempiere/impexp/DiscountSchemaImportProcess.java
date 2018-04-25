package org.adempiere.impexp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.impexp.inventory.MInventoryImportTableSqlUpdater;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ILotNumberDateAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_I_DiscountSchema;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_I_DiscountSchema;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import lombok.NonNull;

/**
 * Import {@link I_I_DiscountSchema} to {@link I_M_DiscountSchema}.
 *
 */
public class DiscountSchemaImportProcess extends AbstractImportProcess<I_I_DiscountSchema>
{
	private static final Logger logger = LogManager.getLogger(DiscountSchemaImportProcess.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	final ILotNumberDateAttributeDAO lotNumberDateAttributeDAO = Services.get(ILotNumberDateAttributeDAO.class);

	@Override
	public Class<I_I_DiscountSchema> getImportModelClass()
	{
		return I_I_DiscountSchema.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_DiscountSchema.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return I_M_DiscountSchema.Table_Name;
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		final String whereClause = getWhereClause();
		MInventoryImportTableSqlUpdater.builder()
				.whereClause(whereClause)
				.updateIInventory();
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_DiscountSchema.COLUMNNAME_ProductValue;
	}

	@Override
	protected I_I_DiscountSchema retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException
	{
		return new X_I_DiscountSchema(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	protected ImportRecordResult importRecord(@NonNull final IMutable<Object> state, @NonNull final I_I_DiscountSchema importRecord) throws Exception
	{
		//
		// Get previous values
		MDiscountSchemaImportContext context = (MDiscountSchemaImportContext)state.getValue();
		if (context == null)
		{
			context = new MDiscountSchemaImportContext();
			state.setValue(context);
		}
		final I_I_DiscountSchema previousImportRecord = context.getPreviousImportRecord();
		final int previousDiscountSchemaId = context.getPreviousM_DiscountSchema_ID();
		final String previousBPartnerValue = context.getPreviousBPartnerValue();
		context.setPreviousImportRecord(importRecord);

		final ImportRecordResult inventoryImportResult;

		final boolean firstImportRecordOrNewMInventory = previousImportRecord == null
				|| !Objects.equals(importRecord.getBPartner_Value(), previousBPartnerValue);

		if (firstImportRecordOrNewMInventory)
		{
			// create a new list because we are passing to a new inventory
			context.clearPreviousRecordsForSameDiscountSchema();
			inventoryImportResult = importDiscountSchema(importRecord);
		}
		else
		{
			if (previousDiscountSchemaId <= 0)
			{
				inventoryImportResult = importDiscountSchema(importRecord);
			}
			else if (importRecord.getM_DiscountSchema_ID() <= 0 || importRecord.getM_DiscountSchema_ID() == previousDiscountSchemaId)
			{
				inventoryImportResult = doNothingAndUsePreviousDiscountSchema(importRecord, previousImportRecord);
			}
			else
			{
				throw new AdempiereException("Same value or movement date as previous line but not same Inventory linked");
			}
		}

//		importInventoryLine(importRecord);
		context.collectImportRecordForSameDiscountSchema(importRecord);

		return inventoryImportResult;
	}

	private ImportRecordResult importDiscountSchema(@NonNull final I_I_DiscountSchema importRecord)
	{
		final ImportRecordResult inventoryImportResult;
		inventoryImportResult = importRecord.getM_DiscountSchema_ID() <= 0 ? ImportRecordResult.Inserted : ImportRecordResult.Updated;

		final I_M_DiscountSchema discountSchema;
		if (importRecord.getM_DiscountSchema_ID() <= 0)	// Insert new Inventory
		{
			discountSchema = createNewMDiscountSchems(importRecord);
		}
		else
		{
			discountSchema = importRecord.getM_DiscountSchema();
		}

		ModelValidationEngine.get().fireImportValidate(this, importRecord, discountSchema, IImportInterceptor.TIMING_AFTER_IMPORT);
		InterfaceWrapperHelper.save(discountSchema);
		importRecord.setM_DiscountSchema_ID(discountSchema.getM_DiscountSchema_ID());
		return inventoryImportResult;
	}

	private I_M_DiscountSchema createNewMDiscountSchems(I_I_DiscountSchema importRecord)
	{
		final I_M_DiscountSchema schema;
		schema = InterfaceWrapperHelper.create(getCtx(), I_M_DiscountSchema.class, ITrx.TRXNAME_ThreadInherited);
		schema.setAD_Org_ID(importRecord.getAD_Org_ID());
		schema.setDescription("I " + importRecord.getC_BPartner().getValue() + " " + importRecord.getM_Product().getValue());
		return schema;

	}

	/**
	 * reuse previous inventory
	 *
	 * @param importRecord
	 * @param previousImportRecord
	 * @return
	 */
	private ImportRecordResult doNothingAndUsePreviousDiscountSchema(@NonNull final I_I_DiscountSchema importRecord, @NonNull final I_I_DiscountSchema previousImportRecord)
	{
		importRecord.setM_DiscountSchema_ID(previousImportRecord.getM_DiscountSchema_ID());
		return ImportRecordResult.Nothing;
	}

}
