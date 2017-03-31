package de.metas.materialtracking.ait.setup;

import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.acct.api.IAcctSchemaDAO;
import org.adempiere.acct.api.impl.AcctSchemaDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.pricing.model.I_C_PricingRule;
import org.adempiere.pricing.spi.impl.rules.MockedPricingRule;
import org.adempiere.pricing.spi.impl.rules.PriceListVersion;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.flatrate.model.X_C_Flatrate_Conditions;
import de.metas.handlingunits.materialtracking.spi.impl.HUHandlingUnitsInfoFactory;
import de.metas.materialtracking.ait.helpers.Helper;
import de.metas.materialtracking.ait.helpers.MockedInvoicedSumProvider;
import de.metas.materialtracking.ch.lagerkonf.impl.HardcodedConfigProvider;
import de.metas.materialtracking.ch.lagerkonf.invoicing.impl.QualityInvoiceLineGroupsBuilderProvider;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityBasedSpiProviderService;
import de.metas.materialtracking.spi.IHandlingUnitsInfoFactory;
import de.metas.materialtracking.spi.IPPOrderMInOutLineRetrievalService;
import de.metas.materialtracking.spi.impl.PlainPPOrderMInOutLineRetrievalService;
import de.metas.product.IProductPA;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.impl.TaxBL;

/*
 * #%L
 * de.metas.materialtracking.ait
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
@RunWith(ConcordionRunner.class)
public class SetupMasterdataFixture
{
	public void reset_the_state_from_earlier_tests() throws Throwable
	{
		Helper.clear();

		AdempiereTestHelper.get().init();

		Services.get(IQualityBasedSpiProviderService.class).setQualityBasedConfigProvider(new HardcodedConfigProvider());
		Services.get(IQualityBasedSpiProviderService.class).setQualityInvoiceLineGroupsBuilderProvider(new QualityInvoiceLineGroupsBuilderProvider());
		Services.get(IQualityBasedSpiProviderService.class).setInvoicedSumProvider(MockedInvoicedSumProvider.instance);

		Services.registerService(IPPOrderMInOutLineRetrievalService.class, new PlainPPOrderMInOutLineRetrievalService());

		Services.registerService(IHandlingUnitsInfoFactory.class, new HUHandlingUnitsInfoFactory());

		// Mock accounting schema retrieval (required for InvoiceCandidateWriter.setC_Activity_ID() )
		Services.registerService(IAcctSchemaDAO.class, new AcctSchemaDAO()
		{
			@Override
			public I_C_AcctSchema retrieveAcctSchema(final Properties ctx, final int ad_Client_ID, final int ad_Org_ID)
			{
				return null;
			}
		});
		// Mock tax retrieval (required for InvoiceCandidateWriter.setC_Tax_ID() )
		Services.registerService(ITaxBL.class, new TaxBL()
		{
			@Override
			public int getTax(final Properties ctx,
					final Object model,
					final int taxCategoryId,
					final int productId,
					final int chargeId,
					final Timestamp billDate,
					final Timestamp shipDate,
					final int adOrgId,
					final I_M_Warehouse warehouse,
					final int billC_BPartner_Location_ID,
					final int shipC_BPartner_Location_ID,
					final boolean isSOTrx,
					final String trxName)
			{
				return 0;
			}
		});

		final I_C_PricingRule plvPricingRule = InterfaceWrapperHelper.newInstance(I_C_PricingRule.class, Helper.context);
		plvPricingRule.setName("plvPricingRule");
		plvPricingRule.setSeqNo(10);
		plvPricingRule.setClassname(PriceListVersion.class.getName());
		plvPricingRule.setIsActive(true);
		InterfaceWrapperHelper.save(plvPricingRule);
	}

	public void set_up_AD_Org(final String name, final String ctxOrg)
	{
		final I_AD_Org org = InterfaceWrapperHelper.newInstance(I_AD_Org.class, Helper.context);
		org.setName(name);
		InterfaceWrapperHelper.save(org);

		if ("Y".equalsIgnoreCase(ctxOrg) || Boolean.parseBoolean(ctxOrg))
		{
			Env.setContext(Helper.context.getCtx(),
					Env.CTXNAME_AD_Org_ID, org.getAD_Org_ID());
			Env.setContext(Helper.context.getCtx(),
					Env.CTXNAME_AD_Org_Name, org.getName());
		}
	}

	public void set_up_C_DocType(final String name,
			final String baseType,
			final String subType)
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class, Helper.context);

		docType.setName(name);
		docType.setDocBaseType(baseType);
		docType.setDocSubType(subType);
		InterfaceWrapperHelper.save(docType);
		Helper.storeFirstTime(name, docType);
	}

	public void set_up_C_UOM(final String name,
			final String x12DE355,
			final String symbol,
			final String precision
			)
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class, Helper.context);

		uom.setName(name);
		uom.setUOMSymbol(symbol);

		uom.setStdPrecision(Integer.valueOf(precision));
		uom.setX12DE355(x12DE355);
		InterfaceWrapperHelper.save(uom);
		Helper.storeFirstTime(name, uom);
	}

	public void set_up_C_UOM_Conversion(final String nameUomFrom,
			final String nameUomTo,
			final String multiplyRate)
	{
		final I_C_UOM uomFrom = Helper.retrieveExisting(nameUomFrom, I_C_UOM.class);
		final I_C_UOM uomTo = Helper.retrieveExisting(nameUomTo, I_C_UOM.class);
		final I_C_UOM_Conversion uomConversion = InterfaceWrapperHelper.newInstance(I_C_UOM_Conversion.class, Helper.context);
		final BigDecimal bpMultiplyRate = new BigDecimal(multiplyRate);

		uomConversion.setC_UOM_ID(uomFrom.getC_UOM_ID());
		uomConversion.setC_UOM_To_ID(uomTo.getC_UOM_ID());
		uomConversion.setMultiplyRate(bpMultiplyRate);
		InterfaceWrapperHelper.save(uomConversion);
	}

	public void set_up_C_Country(final String name)
	{
		final I_C_Country country = InterfaceWrapperHelper.newInstance(I_C_Country.class, Helper.context);
		country.setName(name);
		InterfaceWrapperHelper.save(country);

		Helper.storeFirstTime(name, country);
	}

	public void set_up_C_TaxCategory(final String name)
	{
		final I_C_TaxCategory taxCategory = InterfaceWrapperHelper.newInstance(I_C_TaxCategory.class, Helper.context);
		taxCategory.setName(name);
		InterfaceWrapperHelper.save(taxCategory);
		Helper.storeFirstTime(name, taxCategory);
	}

	public void set_up_M_PricingSystem(final String name)
	{
		final I_M_PricingSystem pricingSystem = InterfaceWrapperHelper.newInstance(I_M_PricingSystem.class, Helper.context);
		pricingSystem.setName(name);
		InterfaceWrapperHelper.save(pricingSystem);

		Helper.storeFirstTime(name, pricingSystem);
	}

	public void set_up_M_PriceList(
			final String namePricingSystem,
			final String name,
			final String nameCountry,
			final String type)
	{
		final int precision = 2;

		final I_M_PricingSystem pricingSystem = Helper.retrieveExisting(namePricingSystem, I_M_PricingSystem.class);

		final I_C_Country country = Helper.retrieveExisting(nameCountry, I_C_Country.class);

		final org.compiere.model.I_M_PriceList priceList = InterfaceWrapperHelper.newInstance(org.compiere.model.I_M_PriceList.class, Helper.context);
		priceList.setM_PricingSystem(pricingSystem);
		priceList.setName(name);
		priceList.setC_Country(country);
		priceList.setPricePrecision(precision);

		if ("Sales".equalsIgnoreCase(type))
		{
			priceList.setIsSOPriceList(true);
		}
		else if ("Purchase".equalsIgnoreCase(type))
		{
			priceList.setIsSOPriceList(false);
		}
		else
		{
			Check.errorIf(true, "Invalid price list type {}", type);
		}
		InterfaceWrapperHelper.save(priceList);
		Helper.storeFirstTime(name, priceList);

	}

	public void set_up_M_Product(final String value, final String name, final String nameUom)
	{
		final IProductPA productPA = Services.get(IProductPA.class);

		final de.metas.adempiere.model.I_M_Product existingProduct = productPA.retrieveProduct(Helper.context.getCtx(), value,
				false,
				Helper.context.getTrxName());
		assertNull(existingProduct);

		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class, Helper.context);
		product.setValue(value);
		product.setName(name);

		if (!Check.isEmpty(nameUom, true))
		{
			final I_C_UOM uom = Helper.retrieveExisting(nameUom, I_C_UOM.class);
			product.setC_UOM_ID(uom.getC_UOM_ID());
			MockedPricingRule.INSTANCE.setC_UOM(product, uom); // just in case we want to use the mocked pricing rule with this product
		}

		InterfaceWrapperHelper.save(product);
		Helper.storeFirstTime(value, product);

	}

	public void set_up_MockedPricingRule_with_price_precision(final int pricePrecision, final List<Map<String, String>> table) throws Throwable
	{
		MockedPricingRule.INSTANCE.setPrecision(pricePrecision);

		final IProductPA productPA = Services.get(IProductPA.class);
		final PlainContextAware context = PlainContextAware.newOutOfTrx(Env.getCtx());

		for (final Map<String, String> tableRow : table)
		{
			final String value = tableRow.get("Value");
			final String price = tableRow.get("Price");

			final de.metas.adempiere.model.I_M_Product product = productPA.retrieveProduct(context.getCtx(), value,
					true, // throw ex if missing as it should already have been created
					context.getTrxName());

			MockedPricingRule.INSTANCE.setProductPrice(product, new BigDecimal(price));
		}
	}

	public void set_up_M_PriceList_Version(final String name,
			final String namePriceList,
			final String strValidFrom) throws Throwable
	{
		final Timestamp validFrom = Helper.parseTimestamp(strValidFrom);

		final I_M_PriceList priceList = Helper.retrieveExisting(namePriceList, I_M_PriceList.class);

		final I_M_PriceList_Version plv = InterfaceWrapperHelper.newInstance(I_M_PriceList_Version.class, Helper.context);
		plv.setName(name);
		plv.setM_PriceList_ID(priceList.getM_PriceList_ID());
		plv.setValidFrom(validFrom);
		plv.setProcessed(true);

		InterfaceWrapperHelper.save(plv);
		Helper.storeFirstTime(name, plv);
	}

	public void set_up_M_ProductPrice(final String namePriceListVersion,
			final String valueProduct,
			final String strPrice,
			final String nameTaxcategory)
	{
		final I_M_PriceList_Version plv = Helper.retrieveExisting(namePriceListVersion, I_M_PriceList_Version.class);
		final I_M_Product product = Helper.retrieveExisting(valueProduct, I_M_Product.class);
		final BigDecimal price = new BigDecimal(strPrice);
		final I_C_TaxCategory taxCategory = Helper.retrieveExisting(nameTaxcategory, I_C_TaxCategory.class);
		final I_M_ProductPrice productPrice = InterfaceWrapperHelper.newInstance(I_M_ProductPrice.class, Helper.context);

		productPrice.setM_PriceList_Version(plv);
		productPrice.setM_Product(product);
		productPrice.setPriceStd(price);
		productPrice.setC_UOM(product.getC_UOM());
		productPrice.setC_TaxCategory(taxCategory);
		InterfaceWrapperHelper.save(productPrice);
	}

	public void set_up_C_Flatrate_Conditions(final String name,
			final String type,
			final String namePricingSystem)
	{
		final I_M_PricingSystem ps = Helper.retrieveExisting(namePricingSystem, I_M_PricingSystem.class);

		final I_C_Flatrate_Conditions fc = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Conditions.class, Helper.context);
		fc.setName(name);
		fc.setType_Conditions(type);
		fc.setM_PricingSystem(ps);
		fc.setInvoiceRule(X_C_Flatrate_Conditions.INVOICERULE_Sofort);
		InterfaceWrapperHelper.save(fc);
		Helper.storeOverride(name, fc);
	}
}