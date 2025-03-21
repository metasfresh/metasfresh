/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.product.impexp;

import com.google.common.collect.ImmutableMap;
import de.metas.adempiere.model.I_M_Product;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.ProductPriceId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.ProductPrices;
import de.metas.product.IProductDAO;
import de.metas.product.IProductPlanningSchemaBL;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.I_M_ProductScalePrice;
import org.adempiere.util.lang.IMutable;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_I_Product;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.X_I_Product;
import org.compiere.model.X_M_ProductPrice;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

/**
 * Import {@link I_I_Product} to {@link I_M_Product}.
 *
 * @author tsa
 */
public class ProductImportProcess extends SimpleImportProcessTemplate<I_I_Product>
{
	private static final String PARAM_M_PriceList_Version_ID = "M_PriceList_Version_ID";

	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IProductPlanningSchemaBL productPlanningSchemaBL = Services.get(IProductPlanningSchemaBL.class);
	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final CompositeProductImportProcessListener listeners;
	private final ProductImportHelper productImporter;

	public ProductImportProcess()
	{
		this.listeners = CompositeProductImportProcessListener.ofCollection(SpringContextHolder.instance.getBeansOfType(ProductImportProcessListener.class));
		this.productImporter = ProductImportHelper.builder()
				.process(this)
				.build();
	}

	@Override
	public Class<I_I_Product> getImportModelClass()
	{
		return I_I_Product.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_Product.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return org.compiere.model.I_M_Product.Table_Name;
	}

	@Override
	protected Map<String, Object> getImportTableDefaultValues()
	{
		return ImmutableMap.<String, Object> builder()
				.put(I_I_Product.COLUMNNAME_ProductType, X_I_Product.PRODUCTTYPE_Item)
				.build();
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		final ImportRecordsSelection selection = getImportRecordsSelection();

		MProductImportTableSqlUpdater.builder()
				.selection(selection)
				.ctx(getCtx())
				.tableName(getImportTableName())
				.valueName(I_I_Product.COLUMNNAME_Value)
				.build()
				.updateIProduct();
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_Product.COLUMNNAME_ProductCategory_Value;
	}

	@Override
	protected I_I_Product retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException
	{
		return new X_I_Product(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	private ProductImportContext createNewContext(final boolean insertOnly)
	{
		final ProductsCache productsCache = ProductsCache.builder()
				.productsRepo(productsRepo)
				.build();

		return ProductImportContext.builder()
				.productsCache(productsCache)
				.insertOnly(insertOnly)
				.build();
	}

	private ImportRecordResult importOrUpdateProduct(final ProductImportContext context)
	{
		final ImportRecordResult bpartnerImportResult;

		final boolean productExists = context.isCurrentProductIdSet();

		if (context.isInsertOnly() && productExists)
		{
			//  do not update existing entries
			return ImportRecordResult.Nothing;
		}

		bpartnerImportResult = productExists ? ImportRecordResult.Updated : ImportRecordResult.Inserted;

		productImporter.importRecord(context);
		return bpartnerImportResult;
	}

	@Override
	protected ImportRecordResult importRecord(
			final @NonNull IMutable<Object> state,
			final @NonNull I_I_Product importRecord,
			final boolean isInsertOnly)
	{
		ProductImportContext context = (ProductImportContext)state.getValue();
		final ImportRecordResult productImportResult;

		if (context == null || !context.isSameProduct(importRecord))
		{
			context = createNewContext(isInsertOnly);
			context.setCurrentImportRecord(importRecord);
			state.setValue(context);

			productImportResult = importOrUpdateProduct(context);
		}
		else
		{
			final ProductId previousProductId = context.getCurrentProductIdOrNull();
			context.setCurrentImportRecord(importRecord);

			final ProductId productId = ProductId.ofRepoIdOrNull(importRecord.getM_Product_ID());
			if (previousProductId == null)
			{
				productImportResult = importOrUpdateProduct(context);
			}
			else if (productId == null || ProductId.equals(productId, previousProductId))
			{
				importRecord.setM_Product_ID(previousProductId.getRepoId());
				productImportResult = ImportRecordResult.Nothing;
			}
			else
			{
				throw new AdempiereException("Same Product value as previous line but not same Product linked");
			}
		}

		final ProductId productId = ProductId.ofRepoIdOrNull(importRecord.getM_Product_ID());
		if (productId != null)
		{
			createUpdateProductPrice(importRecord);
			productPlanningSchemaBL.createDefaultProductPlanningsForAllProducts();
		}

		listeners.afterRecordImport(importRecord);

		return productImportResult;
	}

	private void createUpdateProductPrice(final I_I_Product imp)
	{
		//
		// Get M_PriceList_Version_ID
		int priceListVersionId = imp.getM_PriceList_Version_ID();
		if (priceListVersionId <= 0)
		{
			priceListVersionId = getParameters().getParameterAsInt(PARAM_M_PriceList_Version_ID, -1);
		}
		if (priceListVersionId <= 0)
		{
			return;
		}

		//
		// Get C_TaxCategory
		final int taxCategoryId = imp.getC_TaxCategory_ID();
		if (taxCategoryId <= 0)
		{
			return;
		}

		//
		// Get UOM
		final int uomId = imp.getC_UOM_ID();
		if (uomId <= 0)
		{
			return;
		}

		//
		// Get M_Product_ID
		final ProductId productId = ProductId.ofRepoIdOrNull(imp.getM_Product_ID());
		Check.assumeNotNull(productId, "M_Product shall be set in {}", imp);

		//
		// Get prices
		final BigDecimal priceList = imp.getPriceList();
		final BigDecimal priceStd = imp.getPriceStd();
		final BigDecimal priceLimit = imp.getPriceLimit();

		//
		// Get/Create Product Price record
		final PriceListVersionId plvId = PriceListVersionId.ofRepoId(priceListVersionId);
		final I_M_ProductPrice pp = Optional
				.ofNullable(ProductPrices.retrieveMainProductPriceOrNull(plvId, productId, TaxCategoryId.ofRepoIdOrNull(taxCategoryId)))
				.orElseGet(() -> newInstance(I_M_ProductPrice.class));

		pp.setM_PriceList_Version_ID(priceListVersionId);    // FK
		pp.setM_Product_ID(productId.getRepoId()); // FK
		pp.setC_TaxCategory_ID(taxCategoryId);
		pp.setC_UOM_ID(uomId);

		final InvoicableQtyBasedOn invoicableQtyBasedOn = InvoicableQtyBasedOn.ofNullableCodeOrNominal(imp.getInvoicableQtyBasedOn());
		pp.setInvoicableQtyBasedOn(invoicableQtyBasedOn.getCode());
		if (invoicableQtyBasedOn.isCatchWeight())
		{
			productImporter.createCatchWeightUOMConversion(imp);
		}

		save(pp);

		if (imp.isScalePrice())
		{
			pp.setUseScalePrice(X_M_ProductPrice.USESCALEPRICE_UseScalePriceStrict);

			final BigDecimal scalePriceBreak = imp.getQty();

			final I_M_ProductScalePrice productScalePrice = Optional
					.ofNullable(priceListDAO.retrieveScalePriceForExactBreak(ProductPriceId.ofRepoId(pp.getM_ProductPrice_ID()),
							scalePriceBreak))
					.orElseGet(() -> newInstance(I_M_ProductScalePrice.class));

			productScalePrice.setM_ProductPrice_ID(pp.getM_ProductPrice_ID());
			productScalePrice.setQty(scalePriceBreak);
			productScalePrice.setPriceLimit(priceLimit);
			productScalePrice.setPriceList(priceList);
			productScalePrice.setPriceStd(priceStd);

			save(productScalePrice);
		}

		else
		{
			pp.setPriceLimit(priceLimit);
			pp.setPriceList(priceList);
			pp.setPriceStd(priceStd);
		}

		save(pp);
	}
}
