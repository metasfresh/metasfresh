package org.adempiere.impexp.product;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.impexp.AbstractImportProcess;
import org.adempiere.impexp.IImportInterceptor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_I_Product;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_I_Product;

import de.metas.product.IProductDAO;
import de.metas.vertical.pharma.model.I_I_Pharma_Product;
import de.metas.vertical.pharma.model.I_M_Product;
import de.metas.vertical.pharma.model.X_I_Pharma_Product;
import lombok.NonNull;

public class PharmaProductImportProcess extends AbstractImportProcess<I_I_Pharma_Product>
{

	final IProductDAO productDAO = Services.get(IProductDAO.class);

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
		return I_I_Product.COLUMNNAME_ProductCategory_Value;
	}

	@Override
	protected I_I_Pharma_Product retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException
	{
		return new X_I_Pharma_Product(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		final String whereClause = getWhereClause();
		MProductImportTableSqlUpdater.builder()
				.whereClause(whereClause)
				.ctx(getCtx())
				.tableName(getImportTableName())
				.updateIPharmaProduct();
	}

	@Override
	protected ImportRecordResult importRecord(final IMutable<Object> state, final I_I_Pharma_Product importRecord) throws Exception
	{
		final org.compiere.model.I_M_Product existentProduct = productDAO.retrieveProductByValue(getCtx(), importRecord.getA00PZN());
		final I_M_Product product;
		final boolean newProduct;
		if (existentProduct == null || importRecord.getM_Product_ID() <= 0)
		{
			newProduct = true;
			product = createProduct(importRecord);
		}
		else
		{
			newProduct = false;
			product = updateProduct(importRecord, existentProduct);
		}

		importRecord.setM_Product_ID(product.getM_Product_ID());

		ModelValidationEngine.get().fireImportValidate(this, importRecord, importRecord.getM_Product(), IImportInterceptor.TIMING_AFTER_IMPORT);

		return newProduct ? ImportRecordResult.Inserted : ImportRecordResult.Updated;
	}

	private I_M_Product createProduct(@NonNull final I_I_Pharma_Product importRecord)
	{
		final I_M_Product product = newInstance(I_M_Product.class, importRecord);
		product.setValue(importRecord.getA00PZN());
		product.setName(importRecord.getA00PBEZ());
		product.setDescription(importRecord.getA00PNAM());
		product.setUPC(importRecord.getA00GTIN());
		product.setPackageSize(new BigDecimal(importRecord.getA00PGMENG()));
		product.setPackage_UOM_ID(importRecord.getPackage_UOM_ID());
		product.setIsColdChain(extractIsColdChain(importRecord));
		product.setIsPrescription(extractIsPrescription(importRecord));
		product.setIsNarcotic(extractIsNarcotic(importRecord));
		product.setIsTFG(extractIsTFG(importRecord));
		product.setProductType(X_I_Product.PRODUCTTYPE_Item);
		InterfaceWrapperHelper.save(product);

		return product;
	}

	private I_M_Product updateProduct(@NonNull final I_I_Pharma_Product importRecord, final org.compiere.model.I_M_Product existentProduct)
	{
		final I_M_Product product;
		if (existentProduct == null && importRecord.getM_Product() != null)
		{
			product = create(importRecord.getM_Product(), I_M_Product.class);
		}
		else
		{
			product = create(existentProduct, I_M_Product.class);
		}

		product.setValue(importRecord.getA00PZN());
		product.setName(importRecord.getA00PBEZ());
		product.setDescription(importRecord.getA00PNAM());
		product.setUPC(importRecord.getA00GTIN());
		product.setPackageSize(new BigDecimal(importRecord.getA00PGMENG()));
		product.setPackage_UOM_ID(importRecord.getPackage_UOM_ID());
		product.setIsColdChain(extractIsColdChain(importRecord));
		product.setIsPrescription(extractIsPrescription(importRecord));
		product.setIsNarcotic(extractIsNarcotic(importRecord));
		product.setIsTFG(extractIsTFG(importRecord));
		InterfaceWrapperHelper.save(product);

		return product;

	}

	private boolean extractIsColdChain(@NonNull final I_I_Pharma_Product record)
	{
		return X_I_Pharma_Product.A05KKETTE_01.equals(record.getA05KKETTE());
	}

	private boolean extractIsPrescription(I_I_Pharma_Product importRecord)
	{
		return (X_I_Pharma_Product.A02VSPFL_01.equals(importRecord.getA02VSPFL()) || X_I_Pharma_Product.A02VSPFL_02.equals(importRecord.getA02VSPFL()));
	}

	private boolean extractIsNarcotic(I_I_Pharma_Product importRecord)
	{
		return (X_I_Pharma_Product.A02BTM_01.equals(importRecord.getA02BTM()) || X_I_Pharma_Product.A02BTM_02.equals(importRecord.getA02BTM()));
	}

	private boolean extractIsTFG(I_I_Pharma_Product importRecord)
	{
		return X_I_Pharma_Product.A02TFG_01.equals(importRecord.getA02TFG());
	}
}
