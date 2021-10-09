package de.metas.impexp.product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.ModelValidationEngine;

import de.metas.impexp.processing.IImportInterceptor;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.product.IProductDAO;
import de.metas.product.impexp.MProductImportTableSqlUpdater;
import de.metas.util.Services;
import de.metas.vertical.pharma.model.I_I_Pharma_Product;
import de.metas.vertical.pharma.model.I_M_Product;
import de.metas.vertical.pharma.model.X_I_Pharma_Product;
import lombok.NonNull;

public class IFAInitialImportProcess2 extends SimpleImportProcessTemplate<I_I_Pharma_Product>
{
	// AbstractImportJavaProcess
	private final String DEACTIVATE_OPERATION_CODE = "2";
	private final IProductDAO productDAO = Services.get(IProductDAO.class);

	@Override
	public Class<I_I_Pharma_Product> getImportModelClass()
	{
		return I_I_Pharma_Product.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_Pharma_Product.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return org.compiere.model.I_M_Product.Table_Name;
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_Pharma_Product.COLUMNNAME_A01GDAT;
	}

	@Override
	protected I_I_Pharma_Product retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException
	{
		return new X_I_Pharma_Product(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		final ImportRecordsSelection selection = getImportRecordsSelection();

		MProductImportTableSqlUpdater.builder()
				.selection(selection)
				.ctx(getCtx())
				.tableName(getImportTableName())
				.valueName(I_I_Pharma_Product.COLUMNNAME_A00PZN)
				.updateIPharmaProduct();
	}

	@Override
	protected ImportRecordResult importRecord(@NonNull final IMutable<Object> state,
			@NonNull final I_I_Pharma_Product importRecord,
			final boolean isInsertOnly) throws Exception
	{
		final org.compiere.model.I_M_Product existentProduct = productDAO.retrieveProductByValue(importRecord.getA00PZN());

		final String operationCode = importRecord.getA00SSATZ();
		if (DEACTIVATE_OPERATION_CODE.equals(operationCode) && existentProduct != null)
		{
			IFAProductImportHelper.deactivateProduct(existentProduct);
			return ImportRecordResult.Updated;
		}
		else if (!DEACTIVATE_OPERATION_CODE.equals(operationCode))
		{
			final I_M_Product product;
			final boolean newProduct = existentProduct == null || importRecord.getM_Product_ID() <= 0;

			if (!newProduct && isInsertOnly)
			{
				// #4994 do not update entries
				return ImportRecordResult.Nothing;
			}
			if (newProduct)
			{
				product = IFAProductImportHelper.createProduct(importRecord);
			}
			else
			{
				product = IFAProductImportHelper.updateProduct(importRecord, existentProduct);
			}

			importRecord.setM_Product_ID(product.getM_Product_ID());

			ModelValidationEngine.get().fireImportValidate(this, importRecord, importRecord.getM_Product(), IImportInterceptor.TIMING_AFTER_IMPORT);

			IFAProductImportHelper.importPrices(importRecord, true);

			return newProduct ? ImportRecordResult.Inserted : ImportRecordResult.Updated;
		}

		return ImportRecordResult.Nothing;
	}

	@Override
	protected void markImported(@NonNull final I_I_Pharma_Product importRecord)
	{
		//set this to Yes because in initial import we don't want the prices to be copied
		importRecord.setIsPriceCopied(true);
		importRecord.setI_IsImported(X_I_Pharma_Product.I_ISIMPORTED_Imported);
		importRecord.setProcessed(true);
		InterfaceWrapperHelper.save(importRecord);
	}
}
