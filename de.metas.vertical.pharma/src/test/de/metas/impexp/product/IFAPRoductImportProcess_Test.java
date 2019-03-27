package de.metas.impexp.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.Mutable;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Test;

import de.metas.impexp.product.IFAProductImportTestHelper.IFAFlags;
import de.metas.vertical.pharma.model.I_I_Pharma_Product;

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

public class IFAPRoductImportProcess_Test
{
	private Properties ctx;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		ctx = Env.getCtx();
		IIFAProductFactory.createUOM("PCE");
	}

	@Test
	public void testAccountImport()
	{
		final List<I_I_Pharma_Product> importRecords = prepareImportRecords();

		final IFAProductImportProcess importProcess = new IFAProductImportProcess();
		importProcess.setCtx(ctx);

		importRecords.forEach(record -> importProcess.importRecord(new Mutable<>(), record, false /* isInsertOnly */));

		importRecords.forEach(ifaProduct -> IFAProductImportTestHelper.assertIFAProductImported(ifaProduct));
		
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
	 * <code>A00SSATZ	A00PZN		A00PNAM		A00PBEZ			productCategoryValue	A00GTIN		A00PGMENG	packageUOMCode	A05KKETTE	A02VSPFL	A02BTM	A02TFG	</code><br>
	 * <code>1			04811250	product1	productDesc1	productCateg1			36620			20			ST			01			02			01		01</code><br>
	 * <code>1			05811250	product2	productDesc2	productCateg2			36622			23			ST			00			01			00		00</code><br>
	 * <code>1			05834260	product3	productDesc3	productCateg3			42512			14			ST			00			00			02		00</code><br>
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
				.productCategoryValue("productCateg1")
				.A00GTIN("36620")
				.A00PGMENG("20")
				.packageUOMCode("ST")
				.A05KKETTE("01")
				.A02VSPFL("02")
				.A02BTM("01")
				.A02TFG("01")
				.build();
		records.add(ifaProduct);

		ifaProduct = IIFAProductFactory.builder()
				.A00SSATZ("1")
				.A00PZN("05811250")
				.A00PNAM("product2")
				.A00PBEZ("productDesc2")
				.productCategoryValue("productCateg2")
				.A00GTIN("36622")
				.A00PGMENG("23")
				.packageUOMCode("ST")
				.A05KKETTE("00")
				.A02VSPFL("01")
				.A02BTM("00")
				.A02TFG("00")
				.build();
		records.add(ifaProduct);

		ifaProduct = IIFAProductFactory.builder()
				.A00SSATZ("1")
				.A00PZN("05834260")
				.A00PNAM("product3")
				.A00PBEZ("productDesc3")
				.productCategoryValue("productCateg3")
				.A00GTIN("42512")
				.A00PGMENG("14")
				.packageUOMCode("ST")
				.A05KKETTE("00")
				.A02VSPFL("00")
				.A02BTM("02")
				.A02TFG("00")
				.build();
		records.add(ifaProduct);

		return records;
	}
}
