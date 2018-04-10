package de.metas.impexp.product;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.impexp.AbstractImportProcess;
import org.adempiere.impexp.IImportInterceptor;
import org.adempiere.impexp.product.MProductImportTableSqlUpdater;
import org.adempiere.impexp.product.ProductPriceCreateRequest;
import org.adempiere.impexp.product.ProductPriceImporter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.uom.api.IUOMDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_I_Product;
import org.compiere.process.MProductPriceCloningCommand;
import org.compiere.util.TimeUtil;

import de.metas.product.IProductDAO;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.ITaxDAO.TaxCategoryQuery;
import de.metas.tax.api.ITaxDAO.TaxCategoryQuery.VATType;
import de.metas.vertical.pharma.model.I_I_Pharma_Product;
import de.metas.vertical.pharma.model.I_M_Product;
import de.metas.vertical.pharma.model.X_I_Pharma_Product;
import lombok.NonNull;

public class PharmaProductImportProcess extends AbstractImportProcess<I_I_Pharma_Product>
{
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
		importRecord.setI_IsImported(X_I_Pharma_Product.I_ISIMPORTED_Imported);
		importRecord.setProcessed(true);
		InterfaceWrapperHelper.save(importRecord);
	}

	@Override
	protected void afterImport()
	{
		final List<I_M_PriceList_Version> versions = retrieveLatestPriceListVersion();
		versions.stream()
				.filter(plv -> plv.getM_Pricelist_Version_Base_ID() > 0)
				.forEach(plv -> {

					final MProductPriceCloningCommand productPriceCloning = MProductPriceCloningCommand.builder()
							.source_PriceList_Version_ID(plv.getM_Pricelist_Version_Base_ID())
							.target_PriceList_Version_ID(plv.getM_PriceList_Version_ID())
							.build();

					productPriceCloning.cloneProductPrice();
				});
	}

	private List<I_M_PriceList_Version> retrieveLatestPriceListVersion()
	{
		final List<I_M_PriceList_Version> priceListVersions = new ArrayList<>();

		final List<I_M_PriceList> matchedPriceList = retrievePriceLists();
		matchedPriceList.forEach(priceList -> {
			final I_M_PriceList_Version plv = Services.get(IPriceListDAO.class).retrieveLastCreatedPriceListVersion(priceList.getM_PriceList_ID());
			if (plv != null)
			{
				priceListVersions.add(plv);
			}
		});

		return priceListVersions;
	}

	private List<I_M_PriceList> retrievePriceLists()
	{
		final String whereClause = "1=1" + getWhereClause();
		final List<I_I_Pharma_Product> importRecords = retrieveImportRecords(I_I_Pharma_Product.class, whereClause);
		final List<I_M_PriceList> matchedPriceList = new ArrayList<>();
		importRecords.forEach(record -> {

			if (record.getAEP_Price_List() != null)
			{
				matchedPriceList.add(record.getAEP_Price_List());
			}

			if (record.getAPU_Price_List() != null)
			{
				matchedPriceList.add(record.getAPU_Price_List());
			}

			if (record.getAVP_Price_List() != null)
			{
				matchedPriceList.add(record.getAVP_Price_List());
			}

			if (record.getKAEP_Price_List() != null)
			{
				matchedPriceList.add(record.getKAEP_Price_List());
			}

			if (record.getUVP_Price_List() != null)
			{
				matchedPriceList.add(record.getUVP_Price_List());
			}

			if (record.getZBV_Price_List() != null)
			{
				matchedPriceList.add(record.getZBV_Price_List());
			}
		});
		return matchedPriceList;
	}

	private <T> List<T> retrieveImportRecords(final Class<T> clazz, final String whereClause)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final String trxName = ITrx.TRXNAME_None;
		final IQueryFilter<T> sqlFilter = TypedSqlQueryFilter.of(whereClause);
		return queryBL.createQueryBuilder(clazz, trxName)
				.addOnlyActiveRecordsFilter()
				.filter(sqlFilter)
				.create()
				.list();
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
		if (isNarcotic != null)
		{
			product.setIsTFG(isTFG);
		}
	}

	private Boolean extractIsColdChain(@NonNull final I_I_Pharma_Product record)
	{
		return record.getA05KKETTE() == null ? null : X_I_Pharma_Product.A05KKETTE_1.equals(record.getA05KKETTE());
	}

	private Boolean extractIsPrescription(@NonNull final I_I_Pharma_Product importRecord)
	{
		return importRecord.getA02VSPFL() == null ? null : (X_I_Pharma_Product.A02VSPFL_1.equals(importRecord.getA02VSPFL()) || X_I_Pharma_Product.A02VSPFL_2.equals(importRecord.getA02VSPFL()));
	}

	private Boolean extractIsNarcotic(@NonNull final I_I_Pharma_Product importRecord)
	{
		return importRecord.getA02BTM() == null ? null : (X_I_Pharma_Product.A02BTM_1.equals(importRecord.getA02BTM()) || X_I_Pharma_Product.A02BTM_2.equals(importRecord.getA02BTM()));
	}

	private Boolean extractIsTFG(@NonNull final I_I_Pharma_Product importRecord)
	{
		return importRecord.getA02TFG() == null ? null : X_I_Pharma_Product.A02TFG_1.equals(importRecord.getA02TFG());
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
				.build();

		final ProductPriceCreateRequest request = ProductPriceCreateRequest.builder()
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
				.build();

		final ProductPriceCreateRequest request = ProductPriceCreateRequest.builder()
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
				.build();

		final ProductPriceCreateRequest request = ProductPriceCreateRequest.builder()
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
				.build();

		final ProductPriceCreateRequest request = ProductPriceCreateRequest.builder()
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
				.build();

		final ProductPriceCreateRequest request = ProductPriceCreateRequest.builder()
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
				.build();

		final ProductPriceCreateRequest request = ProductPriceCreateRequest.builder()
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
		if (X_I_Pharma_Product.A01MWST_1.equals(importRecord.getA01MWST()))
		{
			return VATType.ReducedVAT;
		}
		else if (X_I_Pharma_Product.A01MWST_2.equals(importRecord.getA01MWST()))
		{
			return VATType.TaxExempt;
		}
		else
		{
			return VATType.RegularVAT;
		}
	}



}
