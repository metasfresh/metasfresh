/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.order.process;

import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.eevolution.api.IProductBOMDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreatePOFromSOsNotPurchasedTest
{
	private IProductDAO productDAO;
	private IProductBOMDAO bomDAO;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		productDAO = Services.get(IProductDAO.class);
		bomDAO = mock(IProductBOMDAO.class);
	}

	private I_M_Product createProduct(final String value, final String name, final boolean isPurchased)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue(value);
		product.setName(name);
		product.setIsPurchased(isPurchased);
		product.setIsSold(true);
		InterfaceWrapperHelper.saveRecord(product);
		return product;
	}

	private I_C_OrderLine createOrderLine(final I_M_Product product)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
		orderLine.setM_Product_ID(product.getM_Product_ID());
		InterfaceWrapperHelper.saveRecord(orderLine);
		return orderLine;
	}

	@Nested
	class CollectNotPurchasedProducts
	{
		@Test
		void mixedLines_returnsOnlyNotPurchased()
		{
			// given
			final I_M_Product purchasedProduct = createProduct("P001", "Purchased Product", true);
			final I_M_Product notPurchasedProduct1 = createProduct("NP001", "Not Purchased One", false);
			final I_M_Product notPurchasedProduct2 = createProduct("NP002", "Not Purchased Two", false);

			final I_C_OrderLine lineWithPurchased = createOrderLine(purchasedProduct);
			final I_C_OrderLine lineWithNotPurchased1 = createOrderLine(notPurchasedProduct1);
			final I_C_OrderLine lineWithNotPurchased2 = createOrderLine(notPurchasedProduct2);

			final List<I_C_OrderLine> lines = Arrays.asList(lineWithPurchased, lineWithNotPurchased1, lineWithNotPurchased2);

			// when
			final LinkedHashMap<ProductId, String> result = C_Order_CreatePOFromSOs.collectNotPurchasedProducts(productDAO, bomDAO, false, lines);

			// then
			assertThat(result).hasSize(2);
			assertThat(result).containsKey(ProductId.ofRepoId(notPurchasedProduct1.getM_Product_ID()));
			assertThat(result).containsKey(ProductId.ofRepoId(notPurchasedProduct2.getM_Product_ID()));
			assertThat(result).doesNotContainKey(ProductId.ofRepoId(purchasedProduct.getM_Product_ID()));

			assertThat(result.get(ProductId.ofRepoId(notPurchasedProduct1.getM_Product_ID()))).isEqualTo("NP001 (Not Purchased One)");
			assertThat(result.get(ProductId.ofRepoId(notPurchasedProduct2.getM_Product_ID()))).isEqualTo("NP002 (Not Purchased Two)");

			// insertion order is preserved (NP001 encountered before NP002 in the input)
			assertThat(new ArrayList<>(result.keySet()))
					.containsExactly(
							ProductId.ofRepoId(notPurchasedProduct1.getM_Product_ID()),
							ProductId.ofRepoId(notPurchasedProduct2.getM_Product_ID()));
		}

		@Test
		void deduplicatesProductId()
		{
			// given - two lines with the same not-purchased product
			final I_M_Product notPurchasedProduct = createProduct("NP003", "Dedup Product", false);

			final I_C_OrderLine line1 = createOrderLine(notPurchasedProduct);
			final I_C_OrderLine line2 = createOrderLine(notPurchasedProduct);

			final List<I_C_OrderLine> lines = Arrays.asList(line1, line2);

			// when
			final LinkedHashMap<ProductId, String> result = C_Order_CreatePOFromSOs.collectNotPurchasedProducts(productDAO, bomDAO, false, lines);

			// then — same product appears only once (dedup by ProductId)
			assertThat(result).hasSize(1);
			assertThat(result).containsKey(ProductId.ofRepoId(notPurchasedProduct.getM_Product_ID()));
		}

		@Test
		void allPurchased_returnsEmpty()
		{
			// given
			final I_M_Product p1 = createProduct("P002", "Purchased A", true);
			final I_M_Product p2 = createProduct("P003", "Purchased B", true);

			final List<I_C_OrderLine> lines = Arrays.asList(createOrderLine(p1), createOrderLine(p2));

			// when
			final LinkedHashMap<ProductId, String> result = C_Order_CreatePOFromSOs.collectNotPurchasedProducts(productDAO, bomDAO, false, lines);

			// then
			assertThat(result).isEmpty();
		}

		@Test
		void orderLinesWithoutProduct_areSkipped()
		{
			// given — a line with product_id = 0 (no product set)
			final I_C_OrderLine lineWithNoProduct = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
			lineWithNoProduct.setM_Product_ID(0);
			InterfaceWrapperHelper.saveRecord(lineWithNoProduct);

			final List<I_C_OrderLine> lines = Arrays.asList(lineWithNoProduct);

			// when
			final LinkedHashMap<ProductId, String> result = C_Order_CreatePOFromSOs.collectNotPurchasedProducts(productDAO, bomDAO, false, lines);

			// then — lines without product are skipped (neither an offender nor a pass)
			assertThat(result).isEmpty();
		}

		@Test
		void bomParentWithIsPurchasedFalse_skippedWhenPurchaseBOMComponentsEnabled()
		{
			// given — BOM parent product: IsPurchased=false but has BOMs → Pass 2 will explode it,
			// so Pass 1 must NOT flag it as a not-purchased offender.
			final I_M_Product bomParent = createProduct("BOM001", "BOM Parent", false);
			final ProductId bomParentId = ProductId.ofRepoId(bomParent.getM_Product_ID());

			// BOM DAO mock: the BOM parent has BOMs
			when(bomDAO.hasBOMs(bomParentId)).thenReturn(true);

			final I_C_OrderLine bomParentLine = createOrderLine(bomParent);
			final List<I_C_OrderLine> lines = Arrays.asList(bomParentLine);

			// when — purchaseBOMComponents=true mirrors the Pass 2 flag being set
			final LinkedHashMap<ProductId, String> result = C_Order_CreatePOFromSOs.collectNotPurchasedProducts(productDAO, bomDAO, true, lines);

			// then — BOM parent is skipped; no false-positive abort
			assertThat(result).isEmpty();
		}
	}
}
