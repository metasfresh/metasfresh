package de.metas.pricing.impexp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.impexp.AbstractImportProcess;
import org.adempiere.impexp.IImportInterceptor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_I_DiscountSchema;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_I_DiscountSchema;
import org.compiere.model.X_M_DiscountSchema;

import lombok.NonNull;

/**
 * Import {@link I_I_DiscountSchema} to {@link I_M_DiscountSchema}.
 *
 */
public class DiscountSchemaImportProcess extends AbstractImportProcess<I_I_DiscountSchema>
{
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
		MDiscountSchemaImportTableSqlUpdater.updateDiscountSchemaImportTable(getWhereClause());
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_DiscountSchema.COLUMNNAME_C_BPartner_ID;
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
		final int previousBPartnerId = context.getPreviousBPartnerId();
		context.setPreviousImportRecord(importRecord);

		final ImportRecordResult schemaImportResult;

		final boolean firstImportRecordOrNewDiscountSchema = previousImportRecord == null
				|| !Objects.equals(importRecord.getC_BPartner_ID(), previousBPartnerId);

		if (firstImportRecordOrNewDiscountSchema)
		{
			// create a new list because we are passing to a new discount
			context.clearPreviousRecordsForSameDiscountSchema();
			schemaImportResult = importDiscountSchema(importRecord);
		}
		else
		{
			if (previousDiscountSchemaId <= 0)
			{
				schemaImportResult = importDiscountSchema(importRecord);
			}
			else if (importRecord.getM_DiscountSchema_ID() <= 0 || importRecord.getM_DiscountSchema_ID() == previousDiscountSchemaId)
			{
				schemaImportResult = doNothingAndUsePreviousDiscountSchema(importRecord, previousImportRecord);
			}
			else
			{
				throw new AdempiereException("Same value or movement date as previous line but not same Inventory linked");
			}
		}

		importDiscountSchemaBreak(importRecord);
		context.collectImportRecordForSameDiscountSchema(importRecord);

		return schemaImportResult;
	}

	private ImportRecordResult importDiscountSchema(@NonNull final I_I_DiscountSchema importRecord)
	{
		final ImportRecordResult schemaImportResult;

		final I_M_DiscountSchema discountSchema;
		if (importRecord.getM_DiscountSchema_ID() <= 0)
		{
			discountSchema = createNewMDiscountSchemas(importRecord);
			schemaImportResult = ImportRecordResult.Inserted;
		}
		else
		{
			discountSchema = importRecord.getM_DiscountSchema();
			schemaImportResult = ImportRecordResult.Updated;
		}

		final I_C_BPartner bpartner = importRecord.getC_BPartner();
		bpartner.setM_DiscountSchema_ID(discountSchema.getM_DiscountSchema_ID());
		InterfaceWrapperHelper.save(bpartner);

		ModelValidationEngine.get().fireImportValidate(this, importRecord, discountSchema, IImportInterceptor.TIMING_AFTER_IMPORT);
		InterfaceWrapperHelper.save(discountSchema);

		importRecord.setM_DiscountSchema_ID(discountSchema.getM_DiscountSchema_ID());
		InterfaceWrapperHelper.save(importRecord);

		return schemaImportResult;
	}

	private I_M_DiscountSchema createNewMDiscountSchemas(I_I_DiscountSchema importRecord)
	{
		final I_M_DiscountSchema schema;
		schema = InterfaceWrapperHelper.create(getCtx(), I_M_DiscountSchema.class, ITrx.TRXNAME_ThreadInherited);
		schema.setAD_Org_ID(importRecord.getAD_Org_ID());
		schema.setValidFrom(SystemTime.asDayTimestamp());
		schema.setDiscountType(X_M_DiscountSchema.DISCOUNTTYPE_Breaks);
		schema.setIsQuantityBased(true);
		schema.setName("I " + importRecord.getM_DiscountSchema_ID());
		InterfaceWrapperHelper.save(schema);
		return schema;
	}

	/**
	 * reuse previous discount schema
	 *
	 * @param importRecord
	 * @param previousImportRecord
	 * @return
	 */
	private ImportRecordResult doNothingAndUsePreviousDiscountSchema(@NonNull final I_I_DiscountSchema importRecord, @NonNull final I_I_DiscountSchema previousImportRecord)
	{
		importRecord.setM_DiscountSchema_ID(previousImportRecord.getM_DiscountSchema_ID());
		InterfaceWrapperHelper.save(importRecord);
		return ImportRecordResult.Nothing;
	}

	private I_M_DiscountSchemaBreak importDiscountSchemaBreak(@NonNull final I_I_DiscountSchema importRecord)
	{
		I_M_DiscountSchemaBreak schemaBreak = importRecord.getM_DiscountSchemaBreak();
		if (schemaBreak == null)
		{
			schemaBreak = InterfaceWrapperHelper.create(getCtx(), I_M_DiscountSchemaBreak.class, ITrx.TRXNAME_ThreadInherited);
			schemaBreak.setM_DiscountSchema_ID(importRecord.getM_DiscountSchema_ID());
		}

		setDiscountSchemaBreakFields(importRecord, schemaBreak);

		ModelValidationEngine.get().fireImportValidate(this, importRecord, schemaBreak, IImportInterceptor.TIMING_AFTER_IMPORT);
		InterfaceWrapperHelper.save(schemaBreak);

		importRecord.setM_DiscountSchemaBreak_ID(schemaBreak.getM_DiscountSchemaBreak_ID());
		return schemaBreak;
	}

	private void setDiscountSchemaBreakFields(@NonNull final I_I_DiscountSchema importRecord, @NonNull final I_M_DiscountSchemaBreak schemaBreak)
	{
		schemaBreak.setSeqNo(10);
		schemaBreak.setBreakDiscount(importRecord.getBreakDiscount());
		schemaBreak.setBreakValue(importRecord.getBreakValue());
		//
		schemaBreak.setM_Product_ID(importRecord.getM_Product_ID());
		schemaBreak.setC_PaymentTerm_ID(importRecord.getC_PaymentTerm_ID());
		//
		setPricingFields(importRecord, schemaBreak);
	}

	private void setPricingFields(@NonNull final I_I_DiscountSchema importRecord, @NonNull final I_M_DiscountSchemaBreak schemaBreak)
	{
		schemaBreak.setIsPriceOverride(importRecord.isPriceOverride());
		schemaBreak.setPriceBase(importRecord.getPriceBase());
		schemaBreak.setBase_PricingSystem_ID(importRecord.getBase_PricingSystem_ID());
		schemaBreak.setPriceStd(importRecord.getPriceStd());
		schemaBreak.setStd_AddAmt(importRecord.getStd_AddAmt());
	}

}
