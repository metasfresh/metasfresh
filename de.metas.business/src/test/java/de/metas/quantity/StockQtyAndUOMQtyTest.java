package de.metas.quantity;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.product.ProductId;
import de.metas.util.JSONObjectMapper;

/*
 * #%L
 * de.metas.business
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

class StockQtyAndUOMQtyTest
{
	private I_C_UOM stockUomRecord;
	private I_M_Product productRecord;

	private I_C_UOM uomRecord;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		stockUomRecord = newInstance(I_C_UOM.class);
		saveRecord(stockUomRecord);

		productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(stockUomRecord.getC_UOM_ID());
		saveRecord(productRecord);

		uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);
	}

	@Test
	void serialize_deserialize()
	{
		final StockQtyAndUOMQty original = StockQtyAndUOMQty
				.builder()
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.stockQty(Quantity.of(ONE, stockUomRecord))
				.uomQty(Quantity.of(TEN, uomRecord))
				.build();

		final JSONObjectMapper<StockQtyAndUOMQty> jsonMapper = JSONObjectMapper.forClass(StockQtyAndUOMQty.class);
		final String serialized = jsonMapper.writeValueAsString(original);

		final StockQtyAndUOMQty deserialized = jsonMapper.readValue(serialized);

		assertThat(deserialized).isEqualTo(original);
	}

	@Test
	void serialize_deserialize_nullUomQty()
	{
		final StockQtyAndUOMQty original = StockQtyAndUOMQty
				.builder()
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.stockQty(Quantity.of(ONE, stockUomRecord))
				.uomQty(null)
				.build();

		final JSONObjectMapper<StockQtyAndUOMQty> jsonMapper = JSONObjectMapper.forClass(StockQtyAndUOMQty.class);
		final String serialized = jsonMapper.writeValueAsString(original);

		final StockQtyAndUOMQty deserialized = jsonMapper.readValue(serialized);

		assertThat(deserialized).isEqualTo(original);
	}

	@Test
	void test_toString_nullUomQty()
	{
		final StockQtyAndUOMQty stockQtyAndUOMQty = StockQtyAndUOMQty
				.builder()
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.stockQty(Quantity.of(ONE, stockUomRecord))
				.uomQty(null)
				.build();

		assertThat(stockQtyAndUOMQty.toString()).isEqualTo("StockQtyAndUOMQty(productId=ProductId(repoId=100002), stockQty=1 null, uomQty=null)");
	}
}
