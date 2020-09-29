package de.metas.impexp.product;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

import javax.annotation.Nullable;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.X_I_Product;
import org.compiere.util.TimeUtil;

import de.metas.location.ICountryDAO;
import de.metas.pricing.service.ProductPriceCreateRequest;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.ITaxDAO.TaxCategoryQuery;
import de.metas.tax.api.ITaxDAO.TaxCategoryQuery.VATType;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.vertical.pharma.model.I_I_Pharma_Product;
import de.metas.vertical.pharma.model.I_M_Product;
import de.metas.vertical.pharma.model.X_I_Pharma_Product;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2019 metas GmbH
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

@UtilityClass
/* package */ class IFAProductImportHelper
{
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);

	final public void deactivateProduct(@NonNull final org.compiere.model.I_M_Product product)
	{
		product.setIsActive(false);
		InterfaceWrapperHelper.save(product);
	}

	final public I_M_Product createProduct(@NonNull final I_I_Pharma_Product importRecord)
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
		product.setC_UOM_ID(Services.get(IUOMDAO.class).getEachUOM().getC_UOM_ID());
		if (importRecord.getM_Product_Category_ID() > 0)
		{
			product.setM_Product_Category_ID(importRecord.getM_Product_Category_ID());
		}
		else
		{
			final @NonNull ProductCategoryId defaultProductCategoryId = Services.get(IProductDAO.class).getDefaultProductCategoryId();
			product.setM_Product_Category_ID(defaultProductCategoryId.getRepoId());
		}

		InterfaceWrapperHelper.save(product);

		return product;
	}

	final public I_M_Product updateProduct(@NonNull final I_I_Pharma_Product importRecord, @Nullable final org.compiere.model.I_M_Product existentProduct)
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

	private void setPackageFields(@NonNull final I_I_Pharma_Product importRecord, @NonNull final I_M_Product product)
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

	private void setPharmaFields(@NonNull final I_I_Pharma_Product importRecord, @NonNull final I_M_Product product)
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

		if (importRecord.getManufacturer_ID() > 0)
		{
			product.setManufacturer_ID(importRecord.getManufacturer_ID());
		}
	}

	@Nullable
	private Boolean extractIsColdChain(@NonNull final I_I_Pharma_Product record)
	{
		return record.getA05KKETTE() == null ? null : X_I_Pharma_Product.A05KKETTE_01.equals(record.getA05KKETTE());
	}

	@Nullable
	private Boolean extractIsPrescription(@NonNull final I_I_Pharma_Product importRecord)
	{
		return importRecord.getA02VSPFL() == null ? null : (X_I_Pharma_Product.A02VSPFL_01.equals(importRecord.getA02VSPFL()) || X_I_Pharma_Product.A02VSPFL_02.equals(importRecord.getA02VSPFL()));
	}

	@Nullable
	private Boolean extractIsNarcotic(@NonNull final I_I_Pharma_Product importRecord)
	{
		return importRecord.getA02BTM() == null ? null : (X_I_Pharma_Product.A02BTM_01.equals(importRecord.getA02BTM()) || X_I_Pharma_Product.A02BTM_02.equals(importRecord.getA02BTM()));
	}

	@Nullable
	private Boolean extractIsTFG(@NonNull final I_I_Pharma_Product importRecord)
	{
		return importRecord.getA02TFG() == null ? null : X_I_Pharma_Product.A02TFG_01.equals(importRecord.getA02TFG());
	}

	final public void importPrices(@NonNull final I_I_Pharma_Product importRecord, final boolean useNewestPriceListversion)
	{
		if (importRecord.getA01GDAT() != null)
		{
			createKAEP(importRecord, useNewestPriceListversion);
			createAPU(importRecord, useNewestPriceListversion);
			createAEP(importRecord, useNewestPriceListversion);
			createAVP(importRecord, useNewestPriceListversion);
			createUVP(importRecord, useNewestPriceListversion);
			createZBV(importRecord, useNewestPriceListversion);
		}
	}

	private void createKAEP(@NonNull final I_I_Pharma_Product importRecord, final boolean useNewestPriceListversion)
	{
		// skip it
		if (importRecord.getKAEP_Price_List_ID() <= 0)
		{
			return;
		}

		final TaxCategoryQuery query = TaxCategoryQuery.builder()
				.type(extractTaxCategoryVATTYpe(importRecord))
				.countryId(Services.get(ICountryDAO.class).getDefaultCountryId())
				.build();

		final ProductPriceCreateRequest request = ProductPriceCreateRequest.builder()
				.useNewestPriceListversion(useNewestPriceListversion)
				.price(importRecord.getA01KAEP())
				.priceListId(importRecord.getKAEP_Price_List_ID())
				.productId(importRecord.getM_Product_ID())
				.validDate(TimeUtil.asLocalDate(importRecord.getA01GDAT()))
				.taxCategoryId(taxDAO.findTaxCategoryId(query))
				.build();

		final ProductPriceImporter command = new ProductPriceImporter(request);
		command.createProductPrice_And_PriceListVersionIfNeeded();
	}

	private void createAPU(@NonNull final I_I_Pharma_Product importRecord, final boolean useNewestPriceListversion)
	{
		// skip it
		if (importRecord.getAPU_Price_List_ID() <= 0)
		{
			return;
		}

		final TaxCategoryQuery query = TaxCategoryQuery.builder()
				.type(extractTaxCategoryVATTYpe(importRecord))
				.countryId(Services.get(ICountryDAO.class).getDefaultCountryId())
				.build();

		final ProductPriceCreateRequest request = ProductPriceCreateRequest.builder()
				.useNewestPriceListversion(useNewestPriceListversion)
				.price(importRecord.getA01APU())
				.priceListId(importRecord.getAPU_Price_List_ID())
				.productId(importRecord.getM_Product_ID())
				.validDate(TimeUtil.asLocalDate(importRecord.getA01GDAT()))
				.taxCategoryId(taxDAO.findTaxCategoryId(query))
				.build();

		final ProductPriceImporter command = new ProductPriceImporter(request);
		command.createProductPrice_And_PriceListVersionIfNeeded();
	}

	private void createAEP(@NonNull final I_I_Pharma_Product importRecord, final boolean useNewestPriceListversion)
	{
		// skip it
		if (importRecord.getAEP_Price_List_ID() <= 0)
		{
			return;
		}

		final TaxCategoryQuery query = TaxCategoryQuery.builder()
				.type(extractTaxCategoryVATTYpe(importRecord))
				.countryId(Services.get(ICountryDAO.class).getDefaultCountryId())
				.build();

		final ProductPriceCreateRequest request = ProductPriceCreateRequest.builder()
				.useNewestPriceListversion(useNewestPriceListversion)
				.price(importRecord.getA01AEP())
				.priceListId(importRecord.getAEP_Price_List_ID())
				.productId(importRecord.getM_Product_ID())
				.validDate(TimeUtil.asLocalDate(importRecord.getA01GDAT()))
				.taxCategoryId(taxDAO.findTaxCategoryId(query))
				.build();

		final ProductPriceImporter command = new ProductPriceImporter(request);
		command.createProductPrice_And_PriceListVersionIfNeeded();
	}

	private void createAVP(@NonNull final I_I_Pharma_Product importRecord, final boolean useNewestPriceListversion)
	{
		// skip it
		if (importRecord.getAVP_Price_List_ID() <= 0)
		{
			return;
		}

		final TaxCategoryQuery query = TaxCategoryQuery.builder()
				.type(extractTaxCategoryVATTYpe(importRecord))
				.countryId(Services.get(ICountryDAO.class).getDefaultCountryId())
				.build();

		final ProductPriceCreateRequest request = ProductPriceCreateRequest.builder()
				.useNewestPriceListversion(useNewestPriceListversion)
				.price(importRecord.getA01AVP())
				.priceListId(importRecord.getAVP_Price_List_ID())
				.productId(importRecord.getM_Product_ID())
				.validDate(TimeUtil.asLocalDate(importRecord.getA01GDAT()))
				.taxCategoryId(taxDAO.findTaxCategoryId(query))
				.build();

		final ProductPriceImporter command = new ProductPriceImporter(request);
		command.createProductPrice_And_PriceListVersionIfNeeded();
	}

	private void createUVP(@NonNull final I_I_Pharma_Product importRecord, final boolean useNewestPriceListversion)
	{
		// skip it
		if (importRecord.getUVP_Price_List_ID() <= 0)
		{
			return;
		}

		final TaxCategoryQuery query = TaxCategoryQuery.builder()
				.type(extractTaxCategoryVATTYpe(importRecord))
				.countryId(Services.get(ICountryDAO.class).getDefaultCountryId())
				.build();

		final ProductPriceCreateRequest request = ProductPriceCreateRequest.builder()
				.useNewestPriceListversion(useNewestPriceListversion)
				.price(importRecord.getA01UVP())
				.priceListId(importRecord.getUVP_Price_List_ID())
				.productId(importRecord.getM_Product_ID())
				.validDate(TimeUtil.asLocalDate(importRecord.getA01GDAT()))
				.taxCategoryId(taxDAO.findTaxCategoryId(query))
				.build();

		final ProductPriceImporter command = new ProductPriceImporter(request);
		command.createProductPrice_And_PriceListVersionIfNeeded();
	}

	private void createZBV(@NonNull final I_I_Pharma_Product importRecord, final boolean useNewestPriceListversion)
	{
		// skip it
		if (importRecord.getZBV_Price_List_ID() <= 0)
		{
			return;
		}

		final TaxCategoryQuery query = TaxCategoryQuery.builder()
				.type(extractTaxCategoryVATTYpe(importRecord))
				.countryId(Services.get(ICountryDAO.class).getDefaultCountryId())
				.build();

		final ProductPriceCreateRequest request = ProductPriceCreateRequest.builder()
				.useNewestPriceListversion(useNewestPriceListversion)
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
}
