package de.metas.business;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ProductType;
import de.metas.product.ResourceId;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_C_UOM;
import org.eevolution.model.I_PP_Product_BOMVersions;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.adempiere.model.InterfaceWrapperHelper.setValue;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class BusinessTestHelper
{
	/**
	 * Default precision
	 */
	private static final int UOM_Precision_0 = 0;

	/**
	 * Standard in metasfresh
	 */
	private final int UOM_Precision_3 = 3;

	public CountryId createCountry(@NonNull final String countryCode)
	{
		final I_C_Country record = newInstance(I_C_Country.class);
		record.setCountryCode(countryCode);
		POJOWrapper.setInstanceName(record, countryCode);
		saveRecord(record);
		return CountryId.ofRepoId(record.getC_Country_ID());
	}

	public I_C_UOM createUomKg()
	{
		final I_C_UOM uomKg = createUOM("Kg", X_C_UOM.UOMTYPE_Weigth, UOM_Precision_3);
		uomKg.setX12DE355(X12DE355.KILOGRAM.getCode());
		saveRecord(uomKg);
		return uomKg;
	}

	public I_C_UOM createUomEach()
	{
		final I_C_UOM uom = createUOM("Ea", X_C_UOM.UOMTYPE_Weigth, UOM_Precision_0);
		uom.setX12DE355(X12DE355.EACH.getCode());
		saveRecord(uom);
		return uom;
	}

	public I_C_UOM createUomPCE()
	{
		return createUOM("PCE", null, UOM_Precision_0);
	}

	public I_C_UOM createUOM(final String name, @Nullable final String uomType, final int stdPrecision)
	{
		final I_C_UOM uom = createUOM(name, stdPrecision, 0);
		uom.setUOMType(uomType);

		saveRecord(uom);
		return uom;
	}

	public I_C_UOM createUOM(final String name, final int stdPrecision, final int costingPrecission)
	{
		final I_C_UOM uom = createUOM(name);
		uom.setStdPrecision(stdPrecision);
		uom.setCostingPrecision(costingPrecission);
		saveRecord(uom);
		return uom;
	}

	public I_C_UOM createUOM(final String name)
	{
		final X12DE355 x12de355 = X12DE355.ofCode(name);
		return createUOM(name, x12de355);
	}

	public I_C_UOM createUOM(final String name, final X12DE355 x12de355)
	{
		final I_C_UOM uom = newInstanceOutOfTrx(I_C_UOM.class);
		POJOWrapper.setInstanceName(uom, name);
		uom.setName(name);
		uom.setUOMSymbol(name);
		uom.setX12DE355(x12de355 != null ? x12de355.getCode() : null);

		saveRecord(uom);

		return uom;
	}

	@NonNull
	public I_C_UOM createUOM(final String name, final int stdPrecision, final X12DE355 x12de355)
	{
		final I_C_UOM uom = createUOM(name, x12de355);
		uom.setStdPrecision(stdPrecision);

		saveRecord(uom);

		return uom;
	}

	public void createUOMConversion(@NonNull final CreateUOMConversionRequest request)
	{
		final IUOMConversionDAO uomConversionDAO = Services.get(IUOMConversionDAO.class);
		uomConversionDAO.createUOMConversion(request);
	}

	public CurrencyId getEURCurrencyId()
	{
		final PlainCurrencyDAO currenciesRepo = (PlainCurrencyDAO)Services.get(ICurrencyDAO.class);
		return currenciesRepo.getOrCreateByCurrencyCode(CurrencyCode.EUR).getId();
	}

	public ProductId createProductId(final String name, final I_C_UOM uom)
	{
		final I_M_Product product = createProduct(name, uom);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	public I_M_Product createProduct(final String name, @Nullable final I_C_UOM uom)
	{
		final BigDecimal weightKg = null; // N/A
		return createProduct(name, uom, weightKg);
	}

	public I_M_Product createProduct(final String name, final UomId uomId)
	{
		final BigDecimal weightKg = null; // N/A
		return createProduct(name, uomId, weightKg);
	}

	public I_M_Product createProduct(
			@NonNull final String name,
			@Nullable final I_C_UOM uom,
			@Nullable final BigDecimal weightKg)
	{
		final UomId uomId = uom == null ? null : UomId.ofRepoIdOrNull(uom.getC_UOM_ID());
		return createProduct(name, uomId, weightKg);
	}

	/**
	 * @param weightKg product weight (Kg); mainly used for packing materials
	 */
	public I_M_Product createProduct(
			@NonNull final String name,
			@Nullable final UomId uomId,
			@Nullable final BigDecimal weightKg)
	{
		final I_M_Product product = newInstanceOutOfTrx(I_M_Product.class);
		POJOWrapper.setInstanceName(product, name);
		product.setValue(name);
		product.setName(name);
		product.setC_UOM_ID(UomId.toRepoId(uomId));
		product.setProductType(ProductType.Item.getCode());
		product.setIsStocked(true);

		if (weightKg != null)
		{
			product.setWeight(weightKg);
		}
		saveRecord(product);

		return product;
	}

	public ProductCategoryId createProductCategory(@NonNull final String name, @Nullable final AttributeSetId attributeSetId)
	{
		final I_M_Product_Category category = newInstance(I_M_Product_Category.class);
		category.setName(name);
		category.setM_AttributeSet_ID(AttributeSetId.toRepoId(attributeSetId));
		save(category);

		return ProductCategoryId.ofRepoId(category.getM_Product_Category_ID());
	}

	public I_M_Product_Category createM_Product_Cagetory(@NonNull final String name, @NonNull final I_M_AttributeSet attributeSet)
	{
		final I_M_Product_Category category = newInstance(I_M_Product_Category.class);
		category.setName(name);
		category.setM_AttributeSet_ID(attributeSet.getM_AttributeSet_ID());
		save(category);

		return category;
	}

	public ProductId createProduct(@NonNull final String name,
			@Nullable final I_C_UOM uom,
			@Nullable final ProductCategoryId categoryId)
	{
		final I_M_Product product = createProduct(name, uom);

		product.setM_Product_Category_ID(ProductCategoryId.toRepoId(categoryId));
		save(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	public I_M_Product createProduct(@NonNull final String name,
			@Nullable final I_C_UOM uom,
			@NonNull final I_M_Product_Category category)
	{
		final I_M_Product product = createProduct(name, uom);

		product.setM_Product_Category_ID(category.getM_Product_Category_ID());
		save(product);
		return product;
	}

	public ResourceId createManufacturingResource(
			@NonNull final String name,
			@NonNull final I_C_UOM timeUOM)
	{
		final I_S_Resource resource = newInstance(I_S_Resource.class);
		resource.setName(name);
		resource.setIsManufacturingResource(true);
		saveRecord(resource);
		final ResourceId resourceId = ResourceId.ofRepoId(resource.getS_Resource_ID());

		final I_M_Product product = createProduct(name, timeUOM);
		product.setS_Resource_ID(resourceId.getRepoId());
		saveRecord(product);

		return resourceId;
	}

	/**
	 * Creates and saves a simple {@link I_C_BPartner}
	 */
	public I_C_BPartner createBPartner(final String nameAndValue)
	{
		final I_C_BPartner bpartner = newInstanceOutOfTrx(I_C_BPartner.class);
		POJOWrapper.setInstanceName(bpartner, nameAndValue);
		bpartner.setValue(nameAndValue);
		bpartner.setName(nameAndValue);
		saveRecord(bpartner);

		return bpartner;
	}

	public I_C_BPartner_Location createBPartnerLocation(final I_C_BPartner bpartner)
	{
		final I_C_BPartner_Location bpl = newInstance(I_C_BPartner_Location.class, bpartner);
		bpl.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpl.setIsShipTo(true);
		bpl.setIsBillTo(true);
		saveRecord(bpl);
		return bpl;
	}

	public I_C_BP_Group createStandardBPGroup()
	{
		final I_C_BP_Group bpGroupRecord = newInstanceOutOfTrx(I_C_BP_Group.class);
		bpGroupRecord.setC_BP_Group_ID(BPGroupId.STANDARD.getRepoId());
		bpGroupRecord.setName("Standard");
		bpGroupRecord.setIsDefault(true);
		setValue(bpGroupRecord, I_C_BP_Group.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH.getRepoId());

		saveRecord(bpGroupRecord);
		return bpGroupRecord;
	}

	public I_C_BP_Group createBPGroup(
			final String name,
			final boolean isDefault)
	{
		final I_C_BP_Group bpGroupRecord = newInstanceOutOfTrx(I_C_BP_Group.class);
		POJOWrapper.setInstanceName(bpGroupRecord, name);
		bpGroupRecord.setName(name);
		bpGroupRecord.setIsDefault(isDefault);
		setValue(bpGroupRecord, I_C_BP_Group.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH.getRepoId());

		saveRecord(bpGroupRecord);
		return bpGroupRecord;
	}

	public I_C_BP_BankAccount createBpBankAccount(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final CurrencyId currencyId,
			@Nullable final String iban)
	{
		final I_C_BP_BankAccount bpBankAccount = newInstance(I_C_BP_BankAccount.class);
		bpBankAccount.setIBAN(iban);
		bpBankAccount.setC_BPartner_ID(bPartnerId.getRepoId());
		bpBankAccount.setC_Currency_ID(currencyId.getRepoId());
		saveRecord(bpBankAccount);

		return bpBankAccount;
	}

	/**
	 * Calls {@link #createWarehouse(String, boolean)} with {@code isIssueWarehouse == false}
	 */
	public I_M_Warehouse createWarehouse(final String name)
	{
		final boolean isIssueWarehouse = false;
		return createWarehouse(name, isIssueWarehouse);
	}

	/**
	 * Creates a warehouse and one (default) locator.
	 */
	public I_M_Warehouse createWarehouse(
			final String name,
			final boolean isIssueWarehouse)
	{
		final I_M_Warehouse warehouse = newInstanceOutOfTrx(I_M_Warehouse.class);
		POJOWrapper.setInstanceName(warehouse, name);
		warehouse.setValue(name);
		warehouse.setName(name);
		warehouse.setIsIssueWarehouse(isIssueWarehouse);
		saveRecord(warehouse);

		final I_M_Locator locator = createLocator(name + "-default", warehouse);
		locator.setIsDefault(true);
		saveRecord(locator);

		return warehouse;
	}

	public I_M_Locator createLocator(
			final String name,
			final I_M_Warehouse warehouse)
	{
		final I_M_Locator locator = newInstanceOutOfTrx(I_M_Locator.class);
		POJOWrapper.setInstanceName(locator, name);
		locator.setIsDefault(true);
		locator.setValue(name);
		locator.setIsActive(true);
		locator.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		saveRecord(locator);
		return locator;
	}

	public void createDefaultBusinessRecords()
	{
		final I_C_TaxCategory noTaxCategoryFound = newInstanceOutOfTrx(I_C_TaxCategory.class);
		noTaxCategoryFound.setC_TaxCategory_ID(TaxCategoryId.NOT_FOUND.getRepoId());
		saveRecord(noTaxCategoryFound);

		final I_C_Tax noTaxFound = newInstanceOutOfTrx(I_C_Tax.class);
		noTaxFound.setC_Tax_ID(Tax.C_TAX_ID_NO_TAX_FOUND);
		noTaxFound.setC_TaxCategory_ID(TaxCategoryId.NOT_FOUND.getRepoId());
		saveRecord(noTaxFound);
	}

	public I_C_Currency createCurrency(final String symbol)
	{
		final I_C_Currency currencyRecord = newInstanceOutOfTrx(I_C_Currency.class);
		POJOWrapper.setInstanceName(currencyRecord, symbol);
		currencyRecord.setCurSymbol(symbol);
		currencyRecord.setISO_Code(CurrencyCode.EUR.toThreeLetterCode());
		currencyRecord.setIsActive(true);

		saveRecord(currencyRecord);
		return currencyRecord;
	}

	/**
	 * @deprecated please use {@link AdempiereTestHelper#createOrgWithTimeZone()} instead
	 */
	@Deprecated
	public static OrgId createOrgWithTimeZone()
	{
		return AdempiereTestHelper.createOrgWithTimeZone();
	}

	public I_PP_Product_BOMVersions createBOMVersions(final ProductId productId)
	{
		final I_PP_Product_BOMVersions bomVersions = InterfaceWrapperHelper.newInstance(I_PP_Product_BOMVersions.class);
		bomVersions.setM_Product_ID(productId.getRepoId());
		bomVersions.setName("BOM Versions");
		InterfaceWrapperHelper.save(bomVersions);
		return bomVersions;
	}
}
