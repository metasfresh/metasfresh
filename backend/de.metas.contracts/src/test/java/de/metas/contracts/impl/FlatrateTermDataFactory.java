package de.metas.contracts.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.I_M_Product_Acct;
import org.compiere.model.X_C_Tax;
import org.compiere.model.X_M_Product;
import org.compiere.util.TimeUtil;

import de.metas.acct.api.AcctSchemaId;
import de.metas.adempiere.model.I_AD_User;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.money.CurrencyId;
import de.metas.product.ProductAndCategoryId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * *
 *
 * @author metas-dev <dev@metasfresh.com>
 */

public class FlatrateTermDataFactory
{
	private static final String city = "Berlin";
	private static final String valuePricingSystem = "Abo";

	@Builder(builderMethodName = "userNew")
	public static I_AD_User createADUser(@NonNull final I_C_BPartner bpartner, final String lastName, final String firstName, final boolean isBillToContact_Default, final boolean isShipToContact_Default)
	{
		final I_AD_User user = newInstance(I_AD_User.class, bpartner);
		user.setC_BPartner(bpartner);
		user.setLastname(lastName);
		user.setFirstname(firstName);
		user.setIsBillToContact_Default(isBillToContact_Default);
		user.setIsShipToContact_Default(isShipToContact_Default);

		save(user);
		return user;
	}

	@Builder(builderMethodName = "bpLocationNew")
	public static I_C_BPartner_Location createBPartnerLocation(@NonNull final I_C_BPartner bpartner, final boolean isBillTo_Default, final boolean isShipTo_Default, @NonNull final I_C_Country country)
	{
		final I_C_Location location = createLocation(bpartner, country);

		final I_C_BPartner_Location bpartnerLocation = newInstance(I_C_BPartner_Location.class, bpartner);
		bpartnerLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpartnerLocation.setC_Location(location);
		bpartnerLocation.setIsBillTo(isBillTo_Default);
		bpartnerLocation.setIsBillToDefault(isBillTo_Default);

		bpartnerLocation.setIsShipTo(isShipTo_Default);
		bpartnerLocation.setIsShipToDefault(isShipTo_Default);

		save(bpartnerLocation);
		return bpartnerLocation;
	}

	private static I_C_Location createLocation(@NonNull final I_C_BPartner bpartner, @NonNull final I_C_Country country)
	{
		final I_C_Location location = newInstance(I_C_Location.class, bpartner);
		location.setCity(city);
		location.setC_Country(country);
		save(location);
		return location;
	}

	@Builder(builderMethodName = "flatrateConditionsNew")
	public static I_C_Flatrate_Conditions createFlatrateConditions(final String name, final String invoiceRule,
			final String typeConditions, @NonNull final I_C_Calendar calendar, @NonNull final String onFlatrateTermExtend,
			@NonNull final I_M_PricingSystem pricingSystem, final String extensionType, final boolean isCreateNoInvoice)
	{
		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setM_PricingSystem_ID(pricingSystem == null ? null : pricingSystem.getM_PricingSystem_ID());
		conditions.setInvoiceRule(invoiceRule);
		conditions.setType_Conditions(typeConditions);
		conditions.setOnFlatrateTermExtend(onFlatrateTermExtend);
		conditions.setName(name);
		save(conditions);

		final I_C_Flatrate_Transition transition = flatrateTransitionNew()
				.conditions(conditions)
				.calendar(calendar)
				.deliveryInterval(1)
				.deliveryIntervalUnit(X_C_Flatrate_Transition.DELIVERYINTERVALUNIT_MonatE)
				.termDuration(1)
				.termDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_JahrE)
				.isAutoCompleteNewTerm(true)
				.extensionType(extensionType)
				.build();

		conditions.setC_Flatrate_Transition(transition);
		conditions.setProcessed(true);
		conditions.setDocStatus(X_C_Flatrate_Conditions.DOCSTATUS_Completed);
		conditions.setDocAction(X_C_Flatrate_Conditions.DOCACTION_Re_Activate);
		conditions.setIsCreateNoInvoice(isCreateNoInvoice);
		save(conditions);

		return conditions;
	}

	@Builder(builderMethodName = "flatrateTransitionNew")
	private static I_C_Flatrate_Transition createFlatrateTransition(final I_C_Flatrate_Conditions conditions, @NonNull final I_C_Calendar calendar, final int termDuration, final String termDurationUnit,
			final int deliveryInterval, final String deliveryIntervalUnit, final boolean isAutoCompleteNewTerm, final String extensionType)
	{
		final I_C_Flatrate_Transition transition = newInstance(I_C_Flatrate_Transition.class);
		transition.setC_Calendar_Contract(calendar);
		transition.setTermDuration(termDuration);
		transition.setTermDurationUnit(termDurationUnit);
		transition.setDeliveryInterval(deliveryInterval);
		transition.setDeliveryIntervalUnit(deliveryIntervalUnit);
		transition.setIsAutoCompleteNewTerm(isAutoCompleteNewTerm);
		transition.setExtensionType(extensionType);
		transition.setC_Flatrate_Conditions_Next(conditions);
		transition.setTermOfNotice(0);
		transition.setTermOfNoticeUnit(X_C_Flatrate_Transition.TERMOFNOTICEUNIT_TagE);
		transition.setProcessed(true);
		transition.setDocStatus(X_C_Flatrate_Transition.DOCSTATUS_Completed);
		transition.setDocAction(X_C_Flatrate_Transition.DOCACTION_Re_Activate);
		save(transition);
		return transition;
	}

	@Builder(builderMethodName = "bpartnerNew")
	public static I_C_BPartner createBpartner(final String bpValue, final boolean isCustomer)
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setValue(bpValue);
		bpartner.setIsCustomer(isCustomer);
		save(bpartner);
		return bpartner;
	}

	@Builder
	@Value
	public static class ProductAndPricingSystem
	{
		I_M_Product product;
		I_M_PricingSystem pricingSystem;
		I_M_PriceList priceList;
		I_M_PriceList_Version priceListVersion;
		I_C_TaxCategory taxCategory;
		I_C_Tax tax;

		public ProductAndCategoryId getProductAndCategoryId()
		{
			return product != null
					? ProductAndCategoryId.of(product.getM_Product_ID(), product.getM_Product_Category_ID())
					: null;
		}
	}

	@Builder(builderMethodName = "productAndPricingNew")
	public static ProductAndPricingSystem createProductAndPricing(
			final String productValue,
			final String productName,
			@NonNull final I_C_Country country,
			@NonNull final CurrencyId currencyId,
			@NonNull final Timestamp validFrom,
			final boolean isTaxInclcuded)
	{
		final I_C_TaxCategory taxCategory = createTaxCategory();

		final I_C_Tax tax = taxNew()
				.country(country)
				.taxCategory(taxCategory)
				.validFrom(TimeUtil.addDays(validFrom, -10))
				.build();

		final I_M_Product product = productNew()
				.value(productValue)
				.name(productName)
				.build();

		final I_M_PricingSystem pricingSystem = createPricingSystem();

		final I_M_PriceList priceList = priceListNew()
				.currencyId(currencyId)
				.country(country)
				.isTaxInclcuded(isTaxInclcuded)
				.pricingSystem(pricingSystem)
				.build();

		final I_M_PriceList_Version priceListVersion = priceListVersionNew()
				.priceList(priceList)
				.validFrom(validFrom)
				.build();

		productPriceNew()
				.priceListVersion(priceListVersion)
				.taxCategory(taxCategory)
				.product(product)
				.build();

		return ProductAndPricingSystem.builder()
				.taxCategory(taxCategory)
				.tax(tax)
				.product(product)
				.pricingSystem(pricingSystem)
				.priceList(priceList)
				.priceListVersion(priceListVersion)
				.build();
	}

	private static I_C_TaxCategory createTaxCategory()
	{
		final I_C_TaxCategory taxCategory = newInstance(I_C_TaxCategory.class);
		taxCategory.setName("test tax category");
		save(taxCategory);
		return taxCategory;
	}

	@Builder(builderMethodName = "taxNew")
	public static I_C_Tax createTax(@NonNull final I_C_TaxCategory taxCategory, @NonNull final I_C_Country country, @NonNull final Timestamp validFrom)
	{
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setC_Country(country);
		tax.setTo_Country(country);
		tax.setC_TaxCategory(taxCategory);
		tax.setIsDocumentLevel(true);
		tax.setIsSalesTax(true);
		tax.setName("test tax");
		tax.setRate(BigDecimal.valueOf(10));
		tax.setSOPOType(X_C_Tax.SOPOTYPE_Both);
		tax.setValidFrom(validFrom);
		save(tax);
		return tax;
	}

	@Builder(builderMethodName = "productNew")
	public static I_M_Product createProduct(final String value, final String name)
	{
		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);

		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue(value);
		product.setName(name);
		product.setM_Product_Category_ID(10);
		product.setC_UOM_ID(uomRecord.getC_UOM_ID());
		product.setProductType(X_M_Product.PRODUCTTYPE_Item);
		save(product);
		return product;
	}

	private static I_M_PricingSystem createPricingSystem()
	{
		final I_M_PricingSystem pricingSytem = newInstance(I_M_PricingSystem.class);
		pricingSytem.setValue(valuePricingSystem);
		pricingSytem.setName(valuePricingSystem);
		save(pricingSytem);
		return pricingSytem;
	}

	@Builder(builderMethodName = "priceListNew")
	public static I_M_PriceList createPriceList(
			@NonNull final I_M_PricingSystem pricingSystem,
			@NonNull final I_C_Country country,
			@NonNull final CurrencyId currencyId,
			final boolean isTaxInclcuded)
	{
		final I_M_PriceList priceList = newInstance(I_M_PriceList.class);
		priceList.setName(valuePricingSystem);
		priceList.setM_PricingSystem_ID(pricingSystem.getM_PricingSystem_ID());
		priceList.setIsSOPriceList(true);
		priceList.setIsTaxIncluded(isTaxInclcuded);
		priceList.setC_Country_ID(country.getC_Country_ID());
		priceList.setC_Currency_ID(currencyId.getRepoId());
		save(priceList);
		return priceList;
	}

	@Builder(builderMethodName = "priceListVersionNew")
	public static I_M_PriceList_Version createPriceListVersion(@NonNull final I_M_PriceList priceList, @NonNull final Timestamp validFrom)
	{
		final I_M_PriceList_Version priceListVersion = newInstance(I_M_PriceList_Version.class);
		priceListVersion.setM_PriceList(priceList);
		priceListVersion.setName(valuePricingSystem);
		priceListVersion.setValidFrom(validFrom);
		save(priceListVersion);
		return priceListVersion;
	}

	@Builder(builderMethodName = "productPriceNew")
	public static I_M_ProductPrice createProductPrice(
			@NonNull final I_M_Product product,
			@NonNull final I_C_TaxCategory taxCategory,
			@NonNull final I_M_PriceList_Version priceListVersion)
	{
		final int uomId = product.getC_UOM_ID();
		if (uomId <= 0)
		{
			throw new AdempiereException("Expected to have the UOM set for product, else the test will fail some time later: " + product);
		}

		final I_M_ProductPrice productPrice = newInstance(I_M_ProductPrice.class);
		productPrice.setM_Product_ID(product.getM_Product_ID());
		productPrice.setC_UOM_ID(uomId);
		productPrice.setC_TaxCategory_ID(taxCategory.getC_TaxCategory_ID());
		productPrice.setM_PriceList_Version_ID(priceListVersion.getM_PriceList_Version_ID());
		productPrice.setPriceLimit(BigDecimal.valueOf(2));
		productPrice.setPriceList(BigDecimal.valueOf(2));
		productPrice.setPriceStd(BigDecimal.valueOf(2));
		productPrice.setIsAttributeDependant(false);
		save(productPrice);
		return productPrice;
	}

	@Builder(builderMethodName = "productAcctNew")
	public static I_M_Product_Acct createProductAcct(final I_M_Product product, final AcctSchemaId acctSchemaId)
	{
		final I_M_Product_Acct productAcct = newInstance(I_M_Product_Acct.class);
		productAcct.setM_Product(product);
		productAcct.setC_AcctSchema_ID(AcctSchemaId.toRepoId(acctSchemaId));
		productAcct.setC_Activity(createActivity());
		save(productAcct);
		return productAcct;
	}

	private static I_C_Activity createActivity()
	{
		final I_C_Activity activity = newInstance(I_C_Activity.class);
		activity.setValue("Activity");
		activity.setName("Activity");
		save(activity);
		return activity;
	}
}
