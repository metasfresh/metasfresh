package de.metas.pricing.service.impl;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_M_Product;
import de.metas.location.ICountryAreaBL;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.rules.price_list_version.PriceListVersionConfiguration;
import de.metas.pricing.service.IPricingBL;
import de.metas.pricing.service.ProductScalePriceService;
import de.metas.product.IProductPA;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.api.AttributeListValueCreateRequest;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.model.I_C_PricingRule;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_CountryArea;
import org.compiere.model.I_C_CountryArea_Assign;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.mockito.Mockito;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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

public class PricingTestHelper
{
	protected final IPricingBL pricingBL;

	public static final int C_Currency_ID_EUR = 102;
	public static final int C_Currency_ID_CHF = 318;
	public I_C_Country defaultCountry;

	private final I_M_PricingSystem defaultPricingSystem;
	private final I_M_PriceList defaultPriceList;
	private final I_M_PriceList_Version defaultPriceListVerion;

	private final I_M_Product defaultProduct;

	public I_M_Attribute attr_Country;
	public AttributeListValue attr_Country_DE;
	public AttributeListValue attr_Country_CH;

	public I_M_Attribute attr_Label;
	public AttributeListValue attr_Label_Bio;
	public final AttributeListValue attr_Label_NULL = null;

	private final TaxCategoryId taxCategoryId = TaxCategoryId.ofRepoId(1);

	public PricingTestHelper()
	{
		final IProductPA productPA = Mockito.mock(IProductPA.class);
		Services.registerService(IProductPA.class, productPA);

		SpringContextHolder.registerJUnitBean(ProductScalePriceService.newInstanceForUnitTesting());

		createPricingRules();

		defaultCountry = createCountryWithArea("DE", C_Currency_ID_EUR, ICountryAreaBL.COUNTRYAREAKEY_EU);

		defaultPricingSystem = createPricingSystem();
		defaultPriceList = createPriceList(defaultPricingSystem, defaultCountry);
		defaultPriceListVerion = createPriceListVersion(defaultPriceList);
		//
		final I_C_UOM defaultUOM = newInstance(I_C_UOM.class);
		saveRecord(defaultUOM);
		defaultProduct = createProduct("Product1", defaultUOM);
		//
		attr_Country = createM_Attribute("Country", X_M_Attribute.ATTRIBUTEVALUETYPE_List);
		attr_Country_CH = createM_AttributeValue(attr_Country, "CH");
		attr_Country_DE = createM_AttributeValue(attr_Country, "DE");
		//
		attr_Label = createM_Attribute("Label", X_M_Attribute.ATTRIBUTEVALUETYPE_List);
		attr_Label_Bio = createM_AttributeValue(attr_Label, "Bio");

		pricingBL = Services.get(IPricingBL.class);
	}

	protected List<String> getPricingRuleClassnamesToRegister()
	{
		return ImmutableList.of(
				// note: maybe consider to also add AttributePricing? it was added in soft_panda_uat..but maybe it's superflous here..
				de.metas.pricing.rules.price_list_version.PriceListVersionPricingRule.class.getName(),
				de.metas.pricing.rules.Discount.class.getName());
	}

	private void createPricingRules()
	{
		PriceListVersionConfiguration.reset();

		final List<String> classnames = getPricingRuleClassnamesToRegister();

		int nextSeqNo = 10;
		for (final String classname : classnames)
		{
			final I_C_PricingRule pricingRule = InterfaceWrapperHelper.create(Env.getCtx(), I_C_PricingRule.class, ITrx.TRXNAME_None);
			pricingRule.setName(classname);
			pricingRule.setClassname(classname);
			pricingRule.setIsActive(true);
			pricingRule.setSeqNo(nextSeqNo);
			InterfaceWrapperHelper.save(pricingRule);

			nextSeqNo += 10;
		}
	}

	public final I_C_Country createCountry(
			@NonNull final String countryCode,
			final int currencyId)
	{
		final I_C_Country country = newInstance(I_C_Country.class);
		country.setCountryCode(countryCode);
		country.setName(countryCode);
		country.setC_Currency_ID(currencyId);
		saveRecord(country);

		return country;
	}

	public final I_C_Country createCountryWithArea(
			@NonNull final String countryCode,
			final int currencyId,
			@NonNull final String countryAreaValue)
	{
		final I_C_Country country = createCountry(countryCode, currencyId);
		createArea(country, countryAreaValue);

		return country;
	}

	private void createArea(
			@NonNull final I_C_Country country,
			@NonNull final String countryAreaValue)
	{
		final I_C_CountryArea countryAreaRecord = newInstance(I_C_CountryArea.class);
		countryAreaRecord.setValue(countryAreaValue);
		saveRecord(countryAreaRecord);

		final I_C_CountryArea_Assign countryAreaAssignRecord = newInstance(I_C_CountryArea_Assign.class);
		countryAreaAssignRecord.setC_Country_ID(country.getC_Country_ID());
		countryAreaAssignRecord.setValidFrom(TimeUtil.getDay(0L));
		countryAreaAssignRecord.setC_CountryArea_ID(countryAreaRecord.getC_CountryArea_ID());
		saveRecord(countryAreaAssignRecord);
	}

	public final I_M_Product createProduct(
			final String name,
			@NonNull final I_C_UOM uom)
	{
		final I_M_Product product = InterfaceWrapperHelper.create(Env.getCtx(), I_M_Product.class, ITrx.TRXNAME_None);
		product.setValue(name);
		product.setName(name);
		product.setM_Product_Category_ID(20);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		InterfaceWrapperHelper.save(product);
		return product;
	}

	public final I_M_PricingSystem createPricingSystem()
	{
		final I_M_PricingSystem pricingSystem = InterfaceWrapperHelper.create(Env.getCtx(), I_M_PricingSystem.class, ITrx.TRXNAME_None);
		pricingSystem.setName("Test_" + getClass().getName());
		InterfaceWrapperHelper.save(pricingSystem);
		return pricingSystem;
	}

	public final I_M_PriceList createPriceList(final I_M_PricingSystem pricingSystem, final I_C_Country country)
	{
		final I_M_PriceList priceList = InterfaceWrapperHelper.newInstance(I_M_PriceList.class, pricingSystem);
		priceList.setM_PricingSystem_ID(pricingSystem.getM_PricingSystem_ID());
		priceList.setC_Country_ID(country.getC_Country_ID());
		priceList.setC_Currency_ID(country.getC_Currency_ID());
		priceList.setIsSOPriceList(true);
		priceList.setPricePrecision(2);
		InterfaceWrapperHelper.save(priceList);
		return priceList;
	}

	public final I_M_PriceList_Version createPriceListVersion(final I_M_PriceList priceList)
	{
		final I_M_PriceList_Version plv = InterfaceWrapperHelper.newInstance(I_M_PriceList_Version.class, priceList);
		plv.setM_PriceList(priceList);
		plv.setValidFrom(TimeUtil.getDay(1970, 1, 1));
		InterfaceWrapperHelper.save(plv);
		return plv;
	}

	public final I_M_Attribute createM_Attribute(final String name, final String attributeValueType)
	{
		final I_M_Attribute attribute = InterfaceWrapperHelper.create(Env.getCtx(), I_M_Attribute.class, ITrx.TRXNAME_None);
		attribute.setValue(name);
		attribute.setName(name);
		attribute.setAttributeValueType(attributeValueType);
		InterfaceWrapperHelper.save(attribute);
		return attribute;
	}

	public final AttributeListValue createM_AttributeValue(final I_M_Attribute attribute, final String value)
	{
		return Services.get(IAttributeDAO.class).createAttributeValue(AttributeListValueCreateRequest.builder()
				.attributeId(AttributeId.ofRepoId(attribute.getM_Attribute_ID()))
				.value(value)
				.name(value)
				.build());
	}

	public final IAttributeSetInstanceAware asiAware(final I_M_AttributeSetInstance asi)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(Env.getCtx(), I_C_OrderLine.class, ITrx.TRXNAME_None);
		// orderLine.setM_Product(defaultProduct);
		orderLine.setM_AttributeSetInstance(asi);
		return InterfaceWrapperHelper.create(orderLine, IAttributeSetInstanceAware.class);
	}

	public final IEditablePricingContext createPricingContext()
	{
		final IEditablePricingContext pricingCtx = pricingBL.createPricingContext();
		pricingCtx.setPricingSystemId(PricingSystemId.ofRepoId(defaultPricingSystem.getM_PricingSystem_ID()));
		pricingCtx.setPriceListId(PriceListId.ofRepoId(defaultPriceList.getM_PriceList_ID()));
		pricingCtx.setPriceListVersionId(PriceListVersionId.ofRepoId(defaultPriceListVerion.getM_PriceList_Version_ID()));
		pricingCtx.setProductId(ProductId.ofRepoId(defaultProduct.getM_Product_ID()));

		return pricingCtx;
	}

	public final IEditablePricingContext createPricingContextWithASI(final I_M_AttributeSetInstance asi)
	{
		final IEditablePricingContext pricingCtx = createPricingContext();
		pricingCtx.setReferencedObject(asiAware(asi));
		return pricingCtx;
	}

	public ProductPriceBuilder newProductPriceBuilder()
	{
		return new ProductPriceBuilder(defaultPriceListVerion, defaultProduct)
				.setTaxCategoryId(getTaxCategoryId());
	}

	public IPricingResult calculatePrice(final IPricingContext pricingCtx)
	{
		return pricingBL.calculatePrice(pricingCtx);
	}

	public I_M_PricingSystem getDefaultPricingSystem()
	{
		return defaultPricingSystem;
	}

	public I_M_PriceList getDefaultPriceList()
	{
		return defaultPriceList;
	}

	public I_M_PriceList_Version getDefaultPriceListVerion()
	{
		return defaultPriceListVerion;
	}

	public I_M_Product getDefaultProduct()
	{
		return defaultProduct;
	}

	public TaxCategoryId getTaxCategoryId()
	{
		return taxCategoryId;
	}
}
