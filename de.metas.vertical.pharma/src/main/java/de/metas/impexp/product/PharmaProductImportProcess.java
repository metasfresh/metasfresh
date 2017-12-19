package de.metas.impexp.product;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.impexp.AbstractImportProcess;
import org.adempiere.impexp.IImportInterceptor;
import org.adempiere.impexp.product.MProductImportTableSqlUpdater;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_I_Product;

import de.metas.product.IProductDAO;
import de.metas.vertical.pharma.model.I_I_Pharma_Product;
import de.metas.vertical.pharma.model.I_M_Product;
import de.metas.vertical.pharma.model.X_I_Pharma_Product;
import lombok.NonNull;

public class PharmaProductImportProcess extends AbstractImportProcess<I_I_Pharma_Product>
{

	// temporary defaults
	final private int C_UOM_ID = 100;
	final private int M_Product_Category_ID = 1000000;

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
		return I_I_Pharma_Product.COLUMNNAME_A00PZN;
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
				.valueName(I_I_Pharma_Product.COLUMNNAME_A00PZN)
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

	@Override
	protected void markImported(final I_I_Pharma_Product importRecord)
	{
		importRecord.setI_IsImported(X_I_Pharma_Product.I_ISIMPORTED_Imported);
		importRecord.setProcessed(true);
		InterfaceWrapperHelper.save(importRecord);
	}

	private I_M_Product createProduct(@NonNull final I_I_Pharma_Product importRecord)
	{
		final I_M_Product product = newInstance(I_M_Product.class, importRecord);
		product.setValue(importRecord.getA00PZN());
		if (Check.isEmpty(importRecord.getA00PBEZ()))
		{
			product.setName(importRecord.getA00PZN());
		}
		else
		{
			product.setName(importRecord.getA00PBEZ());
		}
		if (!Check.isEmpty(importRecord.getA00PNAM()))
		{
			product.setDescription(importRecord.getA00PNAM());
		}
		if (!Check.isEmpty(importRecord.getA00GTIN()))
		{
			product.setUPC(importRecord.getA00GTIN());
		}

		setPackageFields(importRecord, product);
		setPharmaFields(importRecord, product);

		// FIXME: use them as default values for this WIP
		product.setProductType(X_I_Product.PRODUCTTYPE_Item);
		product.setC_UOM_ID(C_UOM_ID);
		product.setM_Product_Category_ID(M_Product_Category_ID);

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
		if (!Check.isEmpty(importRecord.getA00PBEZ()))
		{
			product.setName(importRecord.getA00PBEZ());
		}
		if (!Check.isEmpty(importRecord.getA00PNAM()))
		{
			product.setDescription(importRecord.getA00PNAM());
		}
		if (!Check.isEmpty(importRecord.getA00GTIN()))
		{
			product.setUPC(importRecord.getA00GTIN());
		}

		setPackageFields(importRecord, product);
		setPharmaFields(importRecord, product);

		InterfaceWrapperHelper.save(product);
		return product;
	}

	private void setPackageFields(@NonNull final I_I_Pharma_Product importRecord, final I_M_Product product)
	{
		if (!Check.isEmpty(importRecord.getA00PGMENG(), true))
		{
			product.setPackageSize(new BigDecimal(importRecord.getA00PGMENG()));
		}
		if (importRecord.getPackage_UOM_ID() > 0)
		{
			product.setPackage_UOM_ID(importRecord.getPackage_UOM_ID());
		}
	}

	private void setPharmaFields(@NonNull final I_I_Pharma_Product importRecord, final I_M_Product product)
	{
		if (importRecord.getM_DosageForm_ID() > 0)
		{
			product.setM_DosageForm_ID(importRecord.getM_DosageForm_ID());
		}
		final Boolean isColdChain = extractIsColdChain(importRecord);
		if (isColdChain != null)
		{
			product.setIsColdChain(isColdChain);
		}
		final Boolean isPrescription = extractIsPrescription(importRecord);
		if (isPrescription != null)
		{
			product.setIsPrescription(isPrescription);

		}
		final Boolean isNarcotic = extractIsNarcotic(importRecord);
		if (isNarcotic != null)
		{
			product.setIsNarcotic(isNarcotic);
		}
		final Boolean isTFG = extractIsTFG(importRecord);
		if (isNarcotic != null)
		{
			product.setIsTFG(isTFG);
		}
	}

	private Boolean extractIsColdChain(@NonNull final I_I_Pharma_Product record)
	{
		return record.getA05KKETTE() == null ? null : X_I_Pharma_Product.A05KKETTE_01.equals(record.getA05KKETTE());
	}

	private Boolean extractIsPrescription(@NonNull final I_I_Pharma_Product importRecord)
	{
		return importRecord.getA02VSPFL() == null ? null : (X_I_Pharma_Product.A02VSPFL_01.equals(importRecord.getA02VSPFL()) || X_I_Pharma_Product.A02VSPFL_02.equals(importRecord.getA02VSPFL()));
	}

	private Boolean extractIsNarcotic(@NonNull final I_I_Pharma_Product importRecord)
	{
		return importRecord.getA02BTM() == null ? null : (X_I_Pharma_Product.A02BTM_01.equals(importRecord.getA02BTM()) || X_I_Pharma_Product.A02BTM_02.equals(importRecord.getA02BTM()));
	}

	private Boolean extractIsTFG(@NonNull final I_I_Pharma_Product importRecord)
	{
		return importRecord.getA02TFG() == null ? null : X_I_Pharma_Product.A02TFG_01.equals(importRecord.getA02TFG());
	}
}
