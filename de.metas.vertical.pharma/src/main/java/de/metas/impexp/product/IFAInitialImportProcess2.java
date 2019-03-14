package de.metas.impexp.product;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.impexp.AbstractImportProcess;
import org.adempiere.impexp.IImportInterceptor;
import org.adempiere.impexp.product.MProductImportTableSqlUpdater;
import org.adempiere.impexp.product.ProductPriceCreateRequest;
import org.adempiere.impexp.product.ProductPriceImporter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMDAO;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_I_Product;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.service.ICountryDAO;
import de.metas.product.IProductDAO;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.ITaxDAO.TaxCategoryQuery;
import de.metas.tax.api.ITaxDAO.TaxCategoryQuery.VATType;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.vertical.pharma.model.I_I_Pharma_Product;
import de.metas.vertical.pharma.model.I_M_Product;
import de.metas.vertical.pharma.model.X_I_Pharma_Product;
import lombok.NonNull;

public class IFAInitialImportProcess2 extends AbstractImportProcess<I_I_Pharma_Product>
{
	// AbstractImportJavaProcess
	private final String DEACTIVATE_OPERATION_CODE = "2";
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);

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
		final String whereClause = getWhereClause();
		MProductImportTableSqlUpdater.builder()
				.whereClause(whereClause)
				.ctx(getCtx())
				.tableName(getImportTableName())
				.valueName(I_I_Pharma_Product.COLUMNNAME_A00PZN)
				.updateIPharmaProduct();
	}

	@Override
	protected ImportRecordResult importRecord(final IMutable<Object> state,
			final I_I_Pharma_Product importRecord,
			final boolean isInsertOnly) throws Exception
	{
		final org.compiere.model.I_M_Product existentProduct = productDAO.retrieveProductByValue(importRecord.getA00PZN());

		final String operationCode = importRecord.getA00SSATZ();
		if (DEACTIVATE_OPERATION_CODE.equals(operationCode) && existentProduct != null)
		{
			deactivateProduct(existentProduct);
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
				product = createProduct(importRecord);
			}
			else
			{
				product = updateProduct(importRecord, existentProduct);
			}

			importRecord.setM_Product_ID(product.getM_Product_ID());

			ModelValidationEngine.get().fireImportValidate(this, importRecord, importRecord.getM_Product(), IImportInterceptor.TIMING_AFTER_IMPORT);

			importPrices(importRecord);

			return newProduct ? ImportRecordResult.Inserted : ImportRecordResult.Updated;
		}

		return ImportRecordResult.Nothing;
	}

	private void deactivateProduct(@NonNull final org.compiere.model.I_M_Product product)
	{
		product.setIsActive(false);
		InterfaceWrapperHelper.save(product);
	}

	@Override
	protected void markImported(final I_I_Pharma_Product importRecord)
	{
		importRecord.setIsPriceCreated(true);
		importRecord.setI_IsImported(X_I_Pharma_Product.I_ISIMPORTED_Imported);
		importRecord.setProcessed(true);
		InterfaceWrapperHelper.save(importRecord);
	}

	private I_M_Product createProduct(@NonNull final I_I_Pharma_Product importRecord)
	{
		final I_M_Product product = newInstance(I_M_Product.class, importRecord);
		product.setValue(importRecord.getA00PZN());
		if (Check.isEmpty(importRecord.getA00PNAM()))
		{
			product.setName(importRecord.getA00PZN());
		}
		else
		{
			product.setName(importRecord.getA00PNAM());
		}
		if (!Check.isEmpty(importRecord.getA00PBEZ()))
		{
			product.setDescription(importRecord.getA00PBEZ());
		}
		if (!Check.isEmpty(importRecord.getA00GTIN()))
		{
			product.setUPC(importRecord.getA00GTIN());
		}

		setPackageFields(importRecord, product);
		setPharmaFields(importRecord, product);

		product.setProductType(X_I_Product.PRODUCTTYPE_Item);
		product.setC_UOM(Services.get(IUOMDAO.class).retrieveEachUOM(getCtx()));
		product.setM_Product_Category_ID(importRecord.getM_Product_Category_ID());

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
		if (!Check.isEmpty(importRecord.getA00PNAM()))
		{
			product.setName(importRecord.getA00PNAM());
		}
		if (!Check.isEmpty(importRecord.getA00PBEZ()))
		{
			product.setDescription(importRecord.getA00PBEZ());
		}
		if (!Check.isEmpty(importRecord.getA00GTIN()))
		{
			product.setUPC(importRecord.getA00GTIN());
		}

		if (importRecord.getM_Product_Category_ID() > 0)
		{
			product.setM_Product_Category_ID(importRecord.getM_Product_Category_ID());
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
			product.setPackageSize(importRecord.getA00PGMENG());
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
		if (isTFG != null)
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

	private void importPrices(@NonNull final I_I_Pharma_Product importRecord)
	{
		if (importRecord.getA01GDAT() != null)
		{
			createKAEP(importRecord);
			createAPU(importRecord);
			createAEP(importRecord);
			createAVP(importRecord);
			createUVP(importRecord);
			createZBV(importRecord);
		}
	}

	private void createKAEP(@NonNull final I_I_Pharma_Product importRecord)
	{
		final TaxCategoryQuery query = TaxCategoryQuery.builder()
				.type(extractTaxCategoryVATTYpe(importRecord))
				.countryId(Services.get(ICountryDAO.class).getDefaultCountryId())
				.build();

		final ProductPriceCreateRequest request = ProductPriceCreateRequest.builder()
				.useNewestPriceListversion(true)
				.price(importRecord.getA01KAEP())
				.priceListId(importRecord.getKAEP_Price_List_ID())
				.productId(importRecord.getM_Product_ID())
				.validDate(TimeUtil.asLocalDate(importRecord.getA01GDAT()))
				.taxCategoryId(taxDAO.findTaxCategoryId(query))
				.build();

		final ProductPriceImporter command = new ProductPriceImporter(request);
		command.createProductPrice_And_PriceListVersionIfNeeded();
	}

	private void createAPU(@NonNull final I_I_Pharma_Product importRecord)
	{
		final TaxCategoryQuery query = TaxCategoryQuery.builder()
				.type(extractTaxCategoryVATTYpe(importRecord))
				.countryId(Services.get(ICountryDAO.class).getDefaultCountryId())
				.build();

		final ProductPriceCreateRequest request = ProductPriceCreateRequest.builder()
				.useNewestPriceListversion(true)
				.price(importRecord.getA01APU())
				.priceListId(importRecord.getAPU_Price_List_ID())
				.productId(importRecord.getM_Product_ID())
				.validDate(TimeUtil.asLocalDate(importRecord.getA01GDAT()))
				.taxCategoryId(taxDAO.findTaxCategoryId(query))
				.build();

		final ProductPriceImporter command = new ProductPriceImporter(request);
		command.createProductPrice_And_PriceListVersionIfNeeded();
	}

	private void createAEP(@NonNull final I_I_Pharma_Product importRecord)
	{
		final TaxCategoryQuery query = TaxCategoryQuery.builder()
				.type(extractTaxCategoryVATTYpe(importRecord))
				.countryId(Services.get(ICountryDAO.class).getDefaultCountryId())
				.build();

		final ProductPriceCreateRequest request = ProductPriceCreateRequest.builder()
				.useNewestPriceListversion(true)
				.price(importRecord.getA01AEP())
				.priceListId(importRecord.getAEP_Price_List_ID())
				.productId(importRecord.getM_Product_ID())
				.validDate(TimeUtil.asLocalDate(importRecord.getA01GDAT()))
				.taxCategoryId(taxDAO.findTaxCategoryId(query))
				.build();

		final ProductPriceImporter command = new ProductPriceImporter(request);
		command.createProductPrice_And_PriceListVersionIfNeeded();
	}

	private void createAVP(@NonNull final I_I_Pharma_Product importRecord)
	{
		final TaxCategoryQuery query = TaxCategoryQuery.builder()
				.type(extractTaxCategoryVATTYpe(importRecord))
				.countryId(Services.get(ICountryDAO.class).getDefaultCountryId())
				.build();

		final ProductPriceCreateRequest request = ProductPriceCreateRequest.builder()
				.useNewestPriceListversion(true)
				.price(importRecord.getA01AVP())
				.priceListId(importRecord.getAVP_Price_List_ID())
				.productId(importRecord.getM_Product_ID())
				.validDate(TimeUtil.asLocalDate(importRecord.getA01GDAT()))
				.taxCategoryId(taxDAO.findTaxCategoryId(query))
				.build();

		final ProductPriceImporter command = new ProductPriceImporter(request);
		command.createProductPrice_And_PriceListVersionIfNeeded();
	}

	private void createUVP(@NonNull final I_I_Pharma_Product importRecord)
	{
		final TaxCategoryQuery query = TaxCategoryQuery.builder()
				.type(extractTaxCategoryVATTYpe(importRecord))
				.countryId(Services.get(ICountryDAO.class).getDefaultCountryId())
				.build();

		final ProductPriceCreateRequest request = ProductPriceCreateRequest.builder()
				.useNewestPriceListversion(true)
				.price(importRecord.getA01UVP())
				.priceListId(importRecord.getUVP_Price_List_ID())
				.productId(importRecord.getM_Product_ID())
				.validDate(TimeUtil.asLocalDate(importRecord.getA01GDAT()))
				.taxCategoryId(taxDAO.findTaxCategoryId(query))
				.build();

		final ProductPriceImporter command = new ProductPriceImporter(request);
		command.createProductPrice_And_PriceListVersionIfNeeded();
	}

	private void createZBV(@NonNull final I_I_Pharma_Product importRecord)
	{
		final TaxCategoryQuery query = TaxCategoryQuery.builder()
				.type(extractTaxCategoryVATTYpe(importRecord))
				.countryId(Services.get(ICountryDAO.class).getDefaultCountryId())
				.build();

		final ProductPriceCreateRequest request = ProductPriceCreateRequest.builder()
				.useNewestPriceListversion(true)
				.price(importRecord.getA01ZBV())
				.priceListId(importRecord.getZBV_Price_List_ID())
				.productId(importRecord.getM_Product_ID())
				.validDate(TimeUtil.asLocalDate(importRecord.getA01GDAT()))
				.taxCategoryId(taxDAO.findTaxCategoryId(query))
				.build();

		final ProductPriceImporter command = new ProductPriceImporter(request);
		command.createProductPrice_And_PriceListVersionIfNeeded();
	}

	private VATType extractTaxCategoryVATTYpe(@NonNull final I_I_Pharma_Product importRecord)
	{
		if (X_I_Pharma_Product.A01MWST_01.equals(importRecord.getA01MWST()))
		{
			return VATType.ReducedVAT;
		}
		else if (X_I_Pharma_Product.A01MWST_02.equals(importRecord.getA01MWST()))
		{
			return VATType.TaxExempt;
		}
		else
		{
			return VATType.RegularVAT;
		}
	}

	@Override
	protected void afterImport()
	{
		final String whereClause = I_I_Pharma_Product.COLUMNNAME_IsPriceCreated + " = 'N' ";
		MProductImportTableSqlUpdater.dbUpdateIsPriceCreated(whereClause, I_I_Pharma_Product.COLUMNNAME_IsPriceCreated);
	}
}
