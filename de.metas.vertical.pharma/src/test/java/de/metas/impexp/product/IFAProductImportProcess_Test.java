package de.metas.impexp.product;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_AD_Language;
import org.compiere.model.I_C_Country;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.impexp.format.ImportTableDescriptorRepository;
import de.metas.impexp.processing.DBFunctionsRepository;
import de.metas.impexp.product.IFAProductImportTestHelper.IFAFlags;
import de.metas.pricing.PriceListId;
import de.metas.vertical.pharma.PharmaProductRepository;
import de.metas.vertical.pharma.model.I_I_Pharma_Product;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
		// needed to register the spring context with the Adempiere main class
		StartupListener.class, ShutdownListener.class,

		// needed so that the spring context can discover those components
		PharmaProductRepository.class,
		DBFunctionsRepository.class,
		ImportTableDescriptorRepository.class
})
public class IFAProductImportProcess_Test
{
	private Properties ctx;

	private int KAEP_Price_List_ID;
	private int APU_Price_List_ID;
	private int AEP_Price_List_ID;
	private int AVP_Price_List_ID;
	private int UVP_Price_List_ID;
	private int ZBV_Price_List_ID;

	private final BigDecimal A01KAEP = BigDecimal.valueOf(6);
	private final BigDecimal A01APU = BigDecimal.valueOf(2);
	private final BigDecimal A01AEP = BigDecimal.valueOf(1);
	private final BigDecimal A01AVP = BigDecimal.valueOf(3);
	private final BigDecimal A01UVP = BigDecimal.valueOf(4);
	private final BigDecimal A01ZBV = BigDecimal.valueOf(5);

	private final String adLanguage ="de_DE";
	private final String countryCode ="DE";

	@Before
	public void init()
	{
		final AdempiereTestHelper adempiereTestHelper = AdempiereTestHelper.get();
		adempiereTestHelper.init();
		adempiereTestHelper.setupContext_AD_Client_IfNotSet();
		ctx = Env.getCtx();
		IFAProductImportTestHelper.createUOM("PCE");
		createAD_Language();
		createCountry();

		KAEP_Price_List_ID = IFAProductImportTestHelper.createPriceList("KAEP");
		APU_Price_List_ID = IFAProductImportTestHelper.createPriceList("APU");
		AEP_Price_List_ID = IFAProductImportTestHelper.createPriceList("AEP");
		AVP_Price_List_ID = IFAProductImportTestHelper.createPriceList("AVP");
		UVP_Price_List_ID = IFAProductImportTestHelper.createPriceList("UVP");
		ZBV_Price_List_ID = IFAProductImportTestHelper.createPriceList("ZBV");
	}

	private void createAD_Language()
	{
		final IContextAware contextProvider = PlainContextAware.newOutOfTrx(ctx);
		final I_AD_Language language = InterfaceWrapperHelper.newInstance(I_AD_Language.class, contextProvider);
		language.setAD_Language(adLanguage);
		language.setCountryCode(countryCode);
		InterfaceWrapperHelper.save(language);

		Env.setContext(ctx, Env.CTXNAME_AD_Language, adLanguage);
	}

	private void createCountry()
	{
		final IContextAware contextProvider = PlainContextAware.newOutOfTrx(ctx);
		final I_C_Country country = InterfaceWrapperHelper.newInstance(I_C_Country.class, contextProvider);
		country.setAD_Language(adLanguage);
		country.setCountryCode(countryCode);
		InterfaceWrapperHelper.save(country);
	}

	@Test
	public void testIFAProductImport()
	{
		final List<I_I_Pharma_Product> importRecords = prepareImportRecords();

		final IFAProductImportProcess importProcess = new IFAProductImportProcess();
		importProcess.setCtx(ctx);

		importRecords.forEach(record -> importProcess.importRecord(new Mutable<>(), record, false /* isInsertOnly */));

		importRecords.forEach(ifaProduct -> {
			IFAProductImportTestHelper.assertIFAProductImported(ifaProduct);
			IFAProductImportTestHelper.assertPrices(ifaProduct, PriceListId.ofRepoId(KAEP_Price_List_ID), A01KAEP);
			IFAProductImportTestHelper.assertPrices(ifaProduct, PriceListId.ofRepoId(AEP_Price_List_ID), A01AEP);
			IFAProductImportTestHelper.assertPrices(ifaProduct, PriceListId.ofRepoId(APU_Price_List_ID), A01APU);
			IFAProductImportTestHelper.assertPrices(ifaProduct, PriceListId.ofRepoId(AVP_Price_List_ID), A01AVP);
			IFAProductImportTestHelper.assertPrices(ifaProduct, PriceListId.ofRepoId(UVP_Price_List_ID), A01UVP);
			IFAProductImportTestHelper.assertPrices(ifaProduct, PriceListId.ofRepoId(ZBV_Price_List_ID), A01ZBV);
		});

		// test flags
		IFAFlags flags = IFAFlags.builder()
				.isColdChain(true)
				.isPrescription(true)
				.isNarcotic(true)
				.isTFG(true)
				.build();
		IFAProductImportTestHelper.assertIFAProductFlags(importRecords.get(0), flags);

		flags = IFAFlags.builder()
				.isColdChain(false)
				.isPrescription(true)
				.isNarcotic(false)
				.isTFG(false)
				.build();
		IFAProductImportTestHelper.assertIFAProductFlags(importRecords.get(1), flags);

		flags = IFAFlags.builder()
				.isColdChain(false)
				.isPrescription(false)
				.isNarcotic(true)
				.isTFG(false)
				.build();
		IFAProductImportTestHelper.assertIFAProductFlags(importRecords.get(2), flags);

	}

	/**
	 * Build a test case for import<br>
	 * <br>
	 * <code>A00SSATZ	A01GDAT		A00PZN		A00PNAM		A00PBEZ			productCategoryValue	A00GTIN		A00PGMENG	packageUOMCode	A05KKETTE	A02VSPFL	A02BTM	A02TFG	A01KAEP	A01APU	A01AEP	A01AVP	A01UVP	A01ZBV</code><br>
	 * <code>1			20181115	04811250	product1	productDesc1	productCateg1			36620			20			ST			01			02			01		01		6		2		1		3		4		5		</code><br>
	 * <code>1			20181115	05811250	product2	productDesc2	productCateg2			36622			23			ST			00			01			00		00		6		2		1		3		4		5		</code><br>
	 * <code>1			20181115	05834260	product3	productDesc3	productCateg3			42512			14			ST			00			00			02		00		6		2		1		3		4		5		</code><br>
	 *
	 * @param lines
	 */
	private List<I_I_Pharma_Product> prepareImportRecords()
	{
		final List<I_I_Pharma_Product> records = new ArrayList<>();

		I_I_Pharma_Product ifaProduct = IIFAProductFactory.builder()
				.A00SSATZ("1")
				.A00PZN("04811250")
				.A00PNAM("product1")
				.A00PBEZ("productDesc1")
				.A01GDAT(Timestamp.valueOf("2018-11-15 00:00:00"))
				.productCategoryValue("productCateg1")
				.A00GTIN("36620")
				.A00PGMENG("20")
				.packageUOMCode("ST")
				.A05KKETTE("01")
				.A02VSPFL("02")
				.A02BTM("01")
				.A02TFG("01")
				.KAEP_Price_List_ID(KAEP_Price_List_ID)
				.A01KAEP(BigDecimal.valueOf(6))
				.AEP_Price_List_ID(AEP_Price_List_ID)
				.A01AEP(BigDecimal.valueOf(1))
				.APU_Price_List_ID(APU_Price_List_ID)
				.A01APU(BigDecimal.valueOf(2))
				.AVP_Price_List_ID(AVP_Price_List_ID)
				.A01AVP(BigDecimal.valueOf(3))
				.UVP_Price_List_ID(UVP_Price_List_ID)
				.A01UVP(BigDecimal.valueOf(4))
				.ZBV_Price_List_ID(ZBV_Price_List_ID)
				.A01ZBV(BigDecimal.valueOf(5))
				.build();
		records.add(ifaProduct);

		ifaProduct = IIFAProductFactory.builder()
				.A00SSATZ("1")
				.A00PZN("05811250")
				.A00PNAM("product2")
				.A00PBEZ("productDesc2")
				.A01GDAT(Timestamp.valueOf("2018-11-15 00:00:00"))
				.productCategoryValue("productCateg2")
				.A00GTIN("36622")
				.A00PGMENG("23")
				.packageUOMCode("ST")
				.A05KKETTE("00")
				.A02VSPFL("01")
				.A02BTM("00")
				.A02TFG("00")
				.KAEP_Price_List_ID(KAEP_Price_List_ID)
				.A01KAEP(BigDecimal.valueOf(6))
				.AEP_Price_List_ID(AEP_Price_List_ID)
				.A01AEP(BigDecimal.valueOf(1))
				.APU_Price_List_ID(APU_Price_List_ID)
				.A01APU(BigDecimal.valueOf(2))
				.AVP_Price_List_ID(AVP_Price_List_ID)
				.A01AVP(BigDecimal.valueOf(3))
				.UVP_Price_List_ID(UVP_Price_List_ID)
				.A01UVP(BigDecimal.valueOf(4))
				.ZBV_Price_List_ID(ZBV_Price_List_ID)
				.A01ZBV(BigDecimal.valueOf(5))
				.build();
		records.add(ifaProduct);

		ifaProduct = IIFAProductFactory.builder()
				.A00SSATZ("1")
				.A00PZN("05834260")
				.A00PNAM("product3")
				.A00PBEZ("productDesc3")
				.A01GDAT(Timestamp.valueOf("2018-11-15 00:00:00"))
				.productCategoryValue("productCateg3")
				.A00GTIN("42512")
				.A00PGMENG("14")
				.packageUOMCode("ST")
				.A05KKETTE("00")
				.A02VSPFL("00")
				.A02BTM("02")
				.A02TFG("00")
				.KAEP_Price_List_ID(KAEP_Price_List_ID)
				.A01KAEP(BigDecimal.valueOf(6))
				.AEP_Price_List_ID(AEP_Price_List_ID)
				.A01AEP(BigDecimal.valueOf(1))
				.APU_Price_List_ID(APU_Price_List_ID)
				.A01APU(BigDecimal.valueOf(2))
				.AVP_Price_List_ID(AVP_Price_List_ID)
				.A01AVP(BigDecimal.valueOf(3))
				.UVP_Price_List_ID(UVP_Price_List_ID)
				.A01UVP(BigDecimal.valueOf(4))
				.ZBV_Price_List_ID(ZBV_Price_List_ID)
				.A01ZBV(BigDecimal.valueOf(5))
				.build();
		records.add(ifaProduct);

		return records;
	}
}
