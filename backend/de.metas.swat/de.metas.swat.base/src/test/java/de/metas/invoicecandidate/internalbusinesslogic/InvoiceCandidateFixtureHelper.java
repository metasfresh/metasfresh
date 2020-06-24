package de.metas.invoicecandidate.internalbusinesslogic;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.math.BigDecimal;

import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.business.BusinessTestHelper;
import de.metas.money.CurrencyId;
import de.metas.product.ProductId;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.UomId;
import de.metas.util.JSONObjectMapper;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
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

public class InvoiceCandidateFixtureHelper
{

	public static final BigDecimal NINE = new BigDecimal("9");
	public static final BigDecimal NINETY = new BigDecimal("90");
	public static final BigDecimal HUNDRET = new BigDecimal("100");

	public static final UomId STOCK_UOM_ID = UomId.ofRepoId(100000);
	public static final UomId DELIVERY_UOM_ID = UomId.ofRepoId(110000);
	public static final UomId IC_UOM_ID = UomId.ofRepoId(110000); // ..for now..will change that

	public static final CurrencyId CURRENCY_ID = CurrencyId.ofRepoId(200000);

	public static final ProductId PRODUCT_ID = ProductId.ofRepoId(300000);

	private static JSONObjectMapper<InvoiceCandidate> jsonObjectMapper = JSONObjectMapper.forClass(InvoiceCandidate.class);

	public static void createRequiredMasterdata()
	{
		final I_C_UOM stockUomRecord = BusinessTestHelper.createUOM("STOCK_UOM");
		stockUomRecord.setC_UOM_ID(STOCK_UOM_ID.getRepoId());
		saveRecord(stockUomRecord);

		final I_M_Product productRecord = BusinessTestHelper.createProduct("PRODUCT", stockUomRecord);
		productRecord.setM_Product_ID(PRODUCT_ID.getRepoId());
		saveRecord(productRecord);

		final I_C_UOM icUOMRecord = BusinessTestHelper.createUOM("DELIVERY_UOM");
		icUOMRecord.setC_UOM_ID(DELIVERY_UOM_ID.getRepoId());
		saveRecord(icUOMRecord);

		BusinessTestHelper.createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.fromUomId(UomId.ofRepoId(stockUomRecord.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(icUOMRecord.getC_UOM_ID()))
				.fromToMultiplier(new BigDecimal("2"))
				.build());

		final I_C_Currency currencyRecord = newInstance(I_C_Currency.class);
		currencyRecord.setC_Currency_ID(CURRENCY_ID.getRepoId());
		saveRecord(currencyRecord);
	}

	public static InvoiceCandidate loadJsonFixture(@NonNull final String fixtureName)
	{
		final InputStream fixtureAsStream = InvoiceCandidateFixtureHelper.class
				.getResourceAsStream("/de/metas/invoicecandidate/internalbusinesslogic/invoiceCandidateTestFixtures/" + fixtureName + ".json");
		assertThat(fixtureAsStream).isNotNull();

		final InvoiceCandidate deserializedInvoiceCandidate = jsonObjectMapper.readValue(fixtureAsStream);
		assertThat(deserializedInvoiceCandidate).isNotNull();

		return deserializedInvoiceCandidate;
	}
}
