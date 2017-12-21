package de.metas.impexp.product;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.impexp.AbstractImportProcess;
import org.adempiere.impexp.IImportInterceptor;
import org.adempiere.impexp.product.MProductImportTableSqlUpdater;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_I_Product;

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
	final private int C_UOM_ID = 100;
	final private int M_Product_Category_ID = 1000000;
	final private int C_TaxCategory_ID = 100;

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
				.price(new BigDecimal(importRecord.getA01KAEP()))
				.priceList(importRecord.getKAEP_Price_List())
				.product(importRecord.getM_Product())
				.validDate(importRecord.getA01GDAT())
				.build();

		createProductPrice_And_PriceListVersionIfNeeded(productPriceCtx);
	}

	private void createAPU(@NonNull final I_I_Pharma_Product importRecord)
	{
		final ProductPriceContext productPriceCtx = ProductPriceContext.builder()
				.price(new BigDecimal(importRecord.getA01APU()))
				.priceList(importRecord.getAPU_Price_List())
				.product(importRecord.getM_Product())
				.validDate(importRecord.getA01GDAT())
				.build();

		createProductPrice_And_PriceListVersionIfNeeded(productPriceCtx);
	}

	private void createAEP(@NonNull final I_I_Pharma_Product importRecord)
	{
		final ProductPriceContext productPriceCtx = ProductPriceContext.builder()
				.price(new BigDecimal(importRecord.getA01AEP()))
				.priceList(importRecord.getAEP_Price_List())
				.product(importRecord.getM_Product())
				.validDate(importRecord.getA01GDAT())
				.build();

		createProductPrice_And_PriceListVersionIfNeeded(productPriceCtx);
	}

	private void createAVP(@NonNull final I_I_Pharma_Product importRecord)
	{
		final ProductPriceContext productPriceCtx = ProductPriceContext.builder()
				.price(new BigDecimal(importRecord.getA01AVP()))
				.priceList(importRecord.getAVP_Price_List())
				.product(importRecord.getM_Product())
				.validDate(importRecord.getA01GDAT())
				.build();

		createProductPrice_And_PriceListVersionIfNeeded(productPriceCtx);
	}

	private void createUVP(@NonNull final I_I_Pharma_Product importRecord)
	{
		final ProductPriceContext productPriceCtx = ProductPriceContext.builder()
				.price(new BigDecimal(importRecord.getA01UVP()))
				.priceList(importRecord.getUVP_Price_List())
				.product(importRecord.getM_Product())
				.validDate(importRecord.getA01GDAT())
				.build();

		createProductPrice_And_PriceListVersionIfNeeded(productPriceCtx);
	}

	private void createZBV(@NonNull final I_I_Pharma_Product importRecord)
	{
		final ProductPriceContext productPriceCtx = ProductPriceContext.builder()
				.price(new BigDecimal(importRecord.getA01ZBV()))
				.priceList(importRecord.getZBV_Price_List())
				.product(importRecord.getM_Product())
				.validDate(importRecord.getA01GDAT())
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

		createProductPrice(productPriceCtx, plv);
	}

	private boolean isValidPriceRecord(@NonNull final ProductPriceContext productPriceCtx)
	{
		return productPriceCtx.getProduct() != null
				&& productPriceCtx.getPrice().signum() > 0
				&& productPriceCtx.getPriceList() != null
				&& productPriceCtx.getValidDate() != null;
	}

	private I_M_ProductPrice createProductPrice(@NonNull final ProductPriceContext productPriceCtx, @NonNull final I_M_PriceList_Version plv)
	{
		final BigDecimal price = productPriceCtx.getPrice();
		final I_M_ProductPrice pp = newInstance(I_M_ProductPrice.class, plv);
		pp.setM_PriceList_Version(plv);
		pp.setM_Product(productPriceCtx.getProduct());
		pp.setPriceLimit(price);
		pp.setPriceList(price);
		pp.setPriceStd(price);
		pp.setC_UOM(productPriceCtx.getProduct().getC_UOM());
		pp.setC_TaxCategory_ID(C_TaxCategory_ID);
		save(pp);

		return pp;
	}

	private I_M_PriceList_Version createPriceListVersion(@NonNull final I_M_PriceList priceList, @NonNull final Timestamp validFrom)
	{
		final I_M_PriceList_Version plv = newInstance(I_M_PriceList_Version.class, priceList);
		plv.setName(priceList.getName() + validFrom);
		plv.setValidFrom(validFrom);
		plv.setM_PriceList(priceList);
		save(plv);

		return plv;
	}
}
