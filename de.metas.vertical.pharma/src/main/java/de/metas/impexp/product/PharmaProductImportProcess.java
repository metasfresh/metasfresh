package de.metas.impexp.product;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.impexp.AbstractImportProcess;
import org.adempiere.impexp.IImportInterceptor;
import org.adempiere.impexp.product.MProductImportTableSqlUpdater;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.uom.api.IUOMDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_I_Product;
import org.compiere.process.MProductPriceCloningCommand;

import de.metas.pricing.ProductPrices;
import de.metas.product.IProductDAO;
import de.metas.vertical.pharma.model.I_I_Pharma_Product;
import de.metas.vertical.pharma.model.I_M_Product;
import de.metas.vertical.pharma.model.X_I_Pharma_Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

public class PharmaProductImportProcess extends AbstractImportProcess<I_I_Pharma_Product>
{

	// temporary defaults
	final private int M_Product_Category_ID = 1000000; // FIXME : don't know yet from where to take it

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

		importPrices(importRecord);

		return newProduct ? ImportRecordResult.Inserted : ImportRecordResult.Updated;
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

		product.setProductType(X_I_Product.PRODUCTTYPE_Item);
		product.setC_UOM(Services.get(IUOMDAO.class).retrieveEachUOM(getCtx()));
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
		createKAEP(importRecord);
		createAPU(importRecord);
		createAEP(importRecord);
		createAVP(importRecord);
		createUVP(importRecord);
		createZBV(importRecord);
	}

	private void createKAEP(@NonNull final I_I_Pharma_Product importRecord)
	{
		final ProductPriceContext productPriceCtx = ProductPriceContext.builder()
				.price(importRecord.getA01KAEP())
				.priceList(importRecord.getKAEP_Price_List())
				.product(importRecord.getM_Product())
				.validDate(importRecord.getA01GDAT())
				.taxCategory(findTaxCategory(importRecord))
				.build();

		createProductPrice_And_PriceListVersionIfNeeded(productPriceCtx);
	}

	private void createAPU(@NonNull final I_I_Pharma_Product importRecord)
	{
		final ProductPriceContext productPriceCtx = ProductPriceContext.builder()
				.price(importRecord.getA01APU())
				.priceList(importRecord.getAPU_Price_List())
				.product(importRecord.getM_Product())
				.validDate(importRecord.getA01GDAT())
				.taxCategory(findTaxCategory(importRecord))
				.build();

		createProductPrice_And_PriceListVersionIfNeeded(productPriceCtx);
	}

	private void createAEP(@NonNull final I_I_Pharma_Product importRecord)
	{
		final ProductPriceContext productPriceCtx = ProductPriceContext.builder()
				.price(importRecord.getA01AEP())
				.priceList(importRecord.getAEP_Price_List())
				.product(importRecord.getM_Product())
				.validDate(importRecord.getA01GDAT())
				.taxCategory(findTaxCategory(importRecord))
				.build();

		createProductPrice_And_PriceListVersionIfNeeded(productPriceCtx);
	}

	private void createAVP(@NonNull final I_I_Pharma_Product importRecord)
	{
		final ProductPriceContext productPriceCtx = ProductPriceContext.builder()
				.price(importRecord.getA01AVP())
				.priceList(importRecord.getAVP_Price_List())
				.product(importRecord.getM_Product())
				.validDate(importRecord.getA01GDAT())
				.taxCategory(findTaxCategory(importRecord))
				.build();

		createProductPrice_And_PriceListVersionIfNeeded(productPriceCtx);
	}

	private void createUVP(@NonNull final I_I_Pharma_Product importRecord)
	{
		final ProductPriceContext productPriceCtx = ProductPriceContext.builder()
				.price(importRecord.getA01UVP())
				.priceList(importRecord.getUVP_Price_List())
				.product(importRecord.getM_Product())
				.validDate(importRecord.getA01GDAT())
				.taxCategory(findTaxCategory(importRecord))
				.build();

		createProductPrice_And_PriceListVersionIfNeeded(productPriceCtx);
	}

	private void createZBV(@NonNull final I_I_Pharma_Product importRecord)
	{
		final ProductPriceContext productPriceCtx = ProductPriceContext.builder()
				.price(importRecord.getA01ZBV())
				.priceList(importRecord.getZBV_Price_List())
				.product(importRecord.getM_Product())
				.validDate(importRecord.getA01GDAT())
				.taxCategory(findTaxCategory(importRecord))
				.build();

		createProductPrice_And_PriceListVersionIfNeeded(productPriceCtx);
	}

	@Builder
	@Getter
	private static class ProductPriceContext
	{
		private final org.compiere.model.I_M_Product product;
		private final I_M_PriceList priceList;
		@NonNull
		private final BigDecimal price;
		final Timestamp validDate;
		@NonNull
		final I_C_TaxCategory taxCategory;
	}

	private I_C_TaxCategory findTaxCategory(@NonNull final I_I_Pharma_Product importRecord)
	{
		final IQueryFilter<I_C_TaxCategory> filter = createTaxCategoryFilter(importRecord);
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_TaxCategory.class, importRecord)
				.filter(filter)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.orderBy(I_C_TaxCategory.COLUMNNAME_Name)
				.create()
				.first(I_C_TaxCategory.class);
	}

	private IQueryFilter<I_C_TaxCategory> createTaxCategoryFilter(@NonNull final I_I_Pharma_Product importRecord)
	{
		final ICompositeQueryFilter<I_C_TaxCategory> filter = Services.get(IQueryBL.class).createCompositeQueryFilter(I_C_TaxCategory.class);
		filter.setJoinAnd();
		if (extractIsDefaultTaxCategory(importRecord))
		{
			filter.addEqualsFilter(I_C_TaxCategory.COLUMN_IsDefault, true);
		}
		else if (extractIsReducedTaxCategory(importRecord))
		{
			filter.addEqualsFilter(I_C_TaxCategory.COLUMN_IsReduced, true);
		}
		else if (extractIsWithoutTaxCategory(importRecord))
		{
			filter.addEqualsFilter(I_C_TaxCategory.COLUMN_IsWithout, true);
		}
		else
		{
			filter.addEqualsFilter(I_C_TaxCategory.COLUMN_IsDefault, true);
		}

		return filter;
	}

	private boolean extractIsDefaultTaxCategory(@NonNull final I_I_Pharma_Product importRecord)
	{
		return X_I_Pharma_Product.A01MWST_0.equals(importRecord.getA01MWST());
	}

	private boolean extractIsReducedTaxCategory(@NonNull final I_I_Pharma_Product importRecord)
	{
		return X_I_Pharma_Product.A01MWST_1.equals(importRecord.getA01MWST());
	}

	private boolean extractIsWithoutTaxCategory(@NonNull final I_I_Pharma_Product importRecord)
	{
		return X_I_Pharma_Product.A01MWST_2.equals(importRecord.getA01MWST());
	}

	private void createProductPrice_And_PriceListVersionIfNeeded(@NonNull final ProductPriceContext productPriceCtx)
	{
		if (!isValidPriceRecord(productPriceCtx))
		{
			return;
		}

		final I_M_PriceList priceList = productPriceCtx.getPriceList();
		final Timestamp validDate = productPriceCtx.getValidDate();
		I_M_PriceList_Version plv = Services.get(IPriceListDAO.class).retrievePriceListVersionWithExactValidDate(priceList.getM_PriceList_ID(), validDate);

		if (plv == null)
		{
			plv = createPriceListVersion(priceList, validDate);
		}

		createProductPriceOrUpdateExistentOne(productPriceCtx, plv);
	}

	private boolean isValidPriceRecord(@NonNull final ProductPriceContext productPriceCtx)
	{
		return productPriceCtx.getProduct() != null
				&& productPriceCtx.getPrice().signum() > 0
				&& productPriceCtx.getPriceList() != null
				&& productPriceCtx.getValidDate() != null;
	}

	private I_M_ProductPrice createProductPriceOrUpdateExistentOne(@NonNull final ProductPriceContext productPriceCtx, @NonNull final I_M_PriceList_Version plv)
	{
		final I_C_TaxCategory taxCategory = productPriceCtx.getTaxCategory();
		final BigDecimal price = productPriceCtx.getPrice();
		I_M_ProductPrice pp = ProductPrices.retrieveMainProductPriceOrNull(plv, productPriceCtx.getProduct().getM_Product_ID());
		if (pp == null)
		{
			pp = newInstance(I_M_ProductPrice.class, plv);
		}
		pp.setM_PriceList_Version(plv);
		pp.setM_Product(productPriceCtx.getProduct());
		pp.setPriceLimit(price);
		pp.setPriceList(price);
		pp.setPriceStd(price);
		pp.setC_UOM(productPriceCtx.getProduct().getC_UOM());
		pp.setC_TaxCategory(taxCategory);
		save(pp);

		return pp;
	}

	private I_M_PriceList_Version createPriceListVersion(@NonNull final I_M_PriceList priceList, @NonNull final Timestamp validFrom)
	{
		final I_M_PriceList_Version plv = newInstance(I_M_PriceList_Version.class, priceList);
		plv.setName(priceList.getName() + validFrom);
		plv.setValidFrom(validFrom);
		plv.setM_PriceList(priceList);
		plv.setProcessed(true);
		save(plv);

		// now set the previous one as base list
		final I_M_PriceList_Version previousPlv = Services.get(IPriceListDAO.class).retrievePreviousVersionOrNull(plv);
		if (previousPlv != null)
		{
			plv.setM_Pricelist_Version_Base(previousPlv);
			save(plv);
		}

		return plv;
	}
}
