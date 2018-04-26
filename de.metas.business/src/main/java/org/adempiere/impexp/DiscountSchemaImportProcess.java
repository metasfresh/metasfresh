package org.adempiere.impexp;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
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
import org.compiere.model.X_M_DiscountSchemaBreak;

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

		final ImportRecordResult schemaImportResult;

		final boolean firstImportRecordOrNewDiscountSchema = previousImportRecord == null
				|| !Objects.equals(importRecord.getBPartner_Value(), previousBPartnerValue);

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
		final ImportRecordResult inventoryImportResult;
		inventoryImportResult = importRecord.getM_DiscountSchema_ID() <= 0 ? ImportRecordResult.Inserted : ImportRecordResult.Updated;

		final I_M_DiscountSchema discountSchema;
		if (importRecord.getM_DiscountSchema_ID() <= 0)
		{
			discountSchema = createNewMDiscountSchemas(importRecord);
			final I_C_BPartner bpartner = importRecord.getC_BPartner();
			bpartner.setM_DiscountSchema(discountSchema);
			InterfaceWrapperHelper.save(bpartner);
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

	private I_M_DiscountSchema createNewMDiscountSchemas(I_I_DiscountSchema importRecord)
	{
		final I_M_DiscountSchema schema;
		schema = InterfaceWrapperHelper.create(getCtx(), I_M_DiscountSchema.class, ITrx.TRXNAME_ThreadInherited);
		schema.setAD_Org_ID(importRecord.getAD_Org_ID());
		schema.setValidFrom(SystemTime.asDayTimestamp());
		schema.setDiscountType(X_M_DiscountSchema.DISCOUNTTYPE_Breaks);
		schema.setName("I " + importRecord.getM_DiscountSchema_ID());
		schema.setDescription("I " + importRecord.getC_BPartner().getValue() + " " + importRecord.getM_Product().getValue());
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
		return ImportRecordResult.Nothing;
	}

	private I_M_DiscountSchemaBreak importDiscountSchemaBreak(@NonNull final I_I_DiscountSchema importRecord)
	{
		final I_M_DiscountSchema schema = importRecord.getM_DiscountSchema();

		I_M_DiscountSchemaBreak schemaBreak = importRecord.getM_DiscountSchemaBreak();
		if (schemaBreak != null)
		{
			if (schemaBreak.getM_DiscountSchema_ID() <= 0)
			{
				schemaBreak.setM_DiscountSchema(schema);
			}
			else if (schemaBreak.getM_DiscountSchema_ID() != schema.getM_DiscountSchema_ID())
			{
				throw new AdempiereException("Discount schema of Discount schema break <> Discount schema");
			}

			setDiscountSchemaBreakFields(importRecord, schemaBreak);

			ModelValidationEngine.get().fireImportValidate(this, importRecord, schemaBreak, IImportInterceptor.TIMING_AFTER_IMPORT);
			InterfaceWrapperHelper.save(schemaBreak);
		}
		else
		{
			schemaBreak = InterfaceWrapperHelper.create(getCtx(), I_M_DiscountSchemaBreak.class, ITrx.TRXNAME_ThreadInherited);
			setDiscountSchemaBreakFields(importRecord, schemaBreak);

			ModelValidationEngine.get().fireImportValidate(this, importRecord, schemaBreak, IImportInterceptor.TIMING_AFTER_IMPORT);
			InterfaceWrapperHelper.save(schemaBreak);

			importRecord.setM_DiscountSchemaBreak_ID(schemaBreak.getM_DiscountSchemaBreak_ID());
		}

		return schemaBreak;
	}

	private void setDiscountSchemaBreakFields(@NonNull final I_I_DiscountSchema importRecord, @NonNull final I_M_DiscountSchemaBreak schemaBreak)
	{
		schemaBreak.setBase_PricingSystem(importRecord.getBase_PricingSystem());
		schemaBreak.setM_Product(importRecord.getM_Product());
		schemaBreak.setC_PaymentTerm(importRecord.getC_PaymentTerm());

		final BigDecimal priceFix = new BigDecimal(importRecord.getPriceStd());
		final BigDecimal discountBreak = new BigDecimal(importRecord.getBreakDiscount());

		if (importRecord.getStd_AddAmt().signum() > 0)
		{
			schemaBreak.setPriceBase(X_M_DiscountSchemaBreak.PRICEBASE_PricingSystem);
			schemaBreak.setPriceStd(priceFix);
		}
		else
		{
			schemaBreak.setPriceBase(X_M_DiscountSchemaBreak.PRICEBASE_Fixed);
		}

		schemaBreak.setBreakDiscount(discountBreak);
		schemaBreak.setStd_AddAmt(importRecord.getStd_AddAmt());
		schemaBreak.setBreakValue(importRecord.getQty());
	}

}
