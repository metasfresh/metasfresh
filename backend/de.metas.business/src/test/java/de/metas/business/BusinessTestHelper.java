package de.metas.business;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import de.metas.organization.OrgId;
import de.metas.organization.StoreCreditCardNumberMode;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_UOM;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.product.ProductId;
import de.metas.product.ProductType;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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
public final class BusinessTestHelper
{
	/**
	 * Default precision
	 */
	private static final int UOM_Precision_0 = 0;

	/**
	 * Standard in ADempiere
	 */
	private static final int UOM_Precision_3 = 3;

	public static CountryId createCountry(@NonNull final String countryCode)
	{
		final I_C_Country record = newInstance(I_C_Country.class);
		record.setCountryCode(countryCode);
		POJOWrapper.setInstanceName(record, countryCode);
		saveRecord(record);
		return CountryId.ofRepoId(record.getC_Country_ID());
	}

	public static I_C_UOM createUomKg()
	{
		final I_C_UOM uomKg = createUOM("Kg", X_C_UOM.UOMTYPE_Weigth, UOM_Precision_3);
		uomKg.setX12DE355("KGM");
		saveRecord(uomKg);
		return uomKg;
	}

	public static I_C_UOM createUomEach()
	{
		return createUOM("Ea", X_C_UOM.UOMTYPE_Weigth, UOM_Precision_0);
	}

	public static I_C_UOM createUomPCE()
	{
		return createUOM("PCE", null, UOM_Precision_0);
	}

	public static I_C_UOM createUOM(final String name, final String uomType, final int stdPrecision)
	{
		final I_C_UOM uom = createUOM(name, stdPrecision, 0);
		uom.setUOMType(uomType);

		saveRecord(uom);
		return uom;
	}

	public static I_C_UOM createUOM(final String name, final int stdPrecision, final int costingPrecission)
	{
		final I_C_UOM uom = createUOM(name);
		uom.setStdPrecision(stdPrecision);
		uom.setCostingPrecision(costingPrecission);
		saveRecord(uom);
		return uom;
	}

	public static I_C_UOM createUOM(final String name)
	{
		final String x12de355 = name;
		return createUOM(name, x12de355);
	}

	public static I_C_UOM createUOM(final String name, final String x12de355)
	{
		final I_C_UOM uom = newInstanceOutOfTrx(I_C_UOM.class);
		POJOWrapper.setInstanceName(uom, name);
		uom.setName(name);
		uom.setUOMSymbol(name);
		uom.setX12DE355(x12de355);

		saveRecord(uom);

		return uom;
	}

	public static void createUOMConversion(@NonNull final CreateUOMConversionRequest request)
	{
		final IUOMConversionDAO uomConversionDAO = Services.get(IUOMConversionDAO.class);
		uomConversionDAO.createUOMConversion(request);
	}

	public static CurrencyId getEURCurrencyId()
	{
		final PlainCurrencyDAO currenciesRepo = (PlainCurrencyDAO)Services.get(ICurrencyDAO.class);
		return currenciesRepo.getOrCreateByCurrencyCode(CurrencyCode.EUR).getId();
	}

	public static ProductId createProductId(final String name, final I_C_UOM uom)
	{
		final I_M_Product product = createProduct(name, uom);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	public static I_M_Product createProduct(final String name, final I_C_UOM uom)
	{
		final BigDecimal weightKg = null; // N/A
		return createProduct(name, uom, weightKg);
	}

	public static I_M_Product createProduct(final String name, final UomId uomId)
	{
		final BigDecimal weightKg = null; // N/A
		return createProduct(name, uomId, weightKg);
	}

	public static I_M_Product createProduct(
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
	public static I_M_Product createProduct(
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

	/**
	 * Creates and saves a simple {@link I_C_BPartner}
	 */
	public static I_C_BPartner createBPartner(final String nameAndValue)
	{
		final I_C_BPartner bpartner = newInstanceOutOfTrx(I_C_BPartner.class);
		POJOWrapper.setInstanceName(bpartner, nameAndValue);
		bpartner.setValue(nameAndValue);
		bpartner.setName(nameAndValue);
		saveRecord(bpartner);

		return bpartner;
	}

	public static I_C_BPartner_Location createBPartnerLocation(final I_C_BPartner bpartner)
	{
		final I_C_BPartner_Location bpl = newInstance(I_C_BPartner_Location.class, bpartner);
		bpl.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpl.setIsShipTo(true);
		bpl.setIsBillTo(true);
		saveRecord(bpl);
		return bpl;
	}

	public static I_C_BP_Group createBPGroup(final String name, final boolean isDefault)
	{
		final I_C_BP_Group bpGroupRecord = newInstanceOutOfTrx(I_C_BP_Group.class);
		POJOWrapper.setInstanceName(bpGroupRecord, name);
		bpGroupRecord.setName(name);
		bpGroupRecord.setIsDefault(isDefault);
		InterfaceWrapperHelper.setValue(bpGroupRecord, I_C_BP_Group.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH.getRepoId());

		saveRecord(bpGroupRecord);
		return bpGroupRecord;
	}

	public static I_C_BP_BankAccount createBpBankAccount(@NonNull final BPartnerId bPartnerId, @NonNull final CurrencyId currencyId, @Nullable String iban)
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
	 *
	 * @param name
	 * @return
	 */
	public static I_M_Warehouse createWarehouse(final String name)
	{
		final boolean isIssueWarehouse = false;
		return createWarehouse(name, isIssueWarehouse);
	}

	/**
	 * Creates a warehouse and one (default) locator.
	 */
	public static I_M_Warehouse createWarehouse(final String name, final boolean isIssueWarehouse)
	{
		final org.adempiere.warehouse.model.I_M_Warehouse warehouse = newInstanceOutOfTrx(org.adempiere.warehouse.model.I_M_Warehouse.class);
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

	public static I_M_Locator createLocator(final String name, final I_M_Warehouse warehouse)
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

	public static void createDefaultBusinessRecords()
	{
		final I_C_TaxCategory noTaxCategoryFound = newInstanceOutOfTrx(I_C_TaxCategory.class);
		noTaxCategoryFound.setC_TaxCategory_ID(TaxCategoryId.NOT_FOUND.getRepoId());
		saveRecord(noTaxCategoryFound);

		final I_C_Tax noTaxFound = newInstanceOutOfTrx(I_C_Tax.class);
		noTaxFound.setC_Tax_ID(ITaxDAO.C_TAX_ID_NO_TAX_FOUND);
		noTaxFound.setC_TaxCategory(noTaxCategoryFound);
		saveRecord(noTaxFound);
	}

	public static I_C_Currency createCurrency(final String symbol)
	{
		final I_C_Currency currencyRecord = newInstanceOutOfTrx(I_C_Currency.class);
		POJOWrapper.setInstanceName(currencyRecord, symbol);
		currencyRecord.setCurSymbol(symbol);
		currencyRecord.setISO_Code(CurrencyCode.EUR.toThreeLetterCode());
		currencyRecord.setIsActive(true);

		saveRecord(currencyRecord);
		return currencyRecord;
	}

	public static OrgId createOrgWithTimeZone()
	{
		final I_AD_Org orgRecord = newInstanceOutOfTrx(I_AD_Org.class);
		saveRecord(orgRecord);

		final I_AD_OrgInfo orgInfoRecord = newInstanceOutOfTrx(I_AD_OrgInfo.class);
		orgInfoRecord.setAD_Org_ID(orgRecord.getAD_Org_ID());
		orgInfoRecord.setStoreCreditCardData(StoreCreditCardNumberMode.DONT_STORE.getCode());
		orgInfoRecord.setTimeZone("Europe/Berlin");
		saveRecord(orgInfoRecord);

		return OrgId.ofRepoId(orgRecord.getAD_Org_ID());
	}
}
