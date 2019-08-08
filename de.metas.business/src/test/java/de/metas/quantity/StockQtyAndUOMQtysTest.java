package de.metas.quantity;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.product.ProductId;

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

public class StockQtyAndUOMQtysTest
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
	void validate_inconsistent_stockUom()
	{
		final StockQtyAndUOMQty qtys = StockQtyAndUOMQty
				.builder()
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.stockQty(Quantity.of(ONE, uomRecord))
				.uomQty(Quantity.of(TEN, uomRecord))
				.build();

		assertThatThrownBy(() -> StockQtyAndUOMQtys.validate(qtys))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Product's stock UOM does not match stockQty's UOM");
	}

	@Test
	void validate_inconsistent_uom()
	{
		final StockQtyAndUOMQty qtys = StockQtyAndUOMQty
				.builder()
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.stockQty(Quantity.of(ONE, stockUomRecord))
				.uomQty(Quantity.of(TEN, stockUomRecord))
				.build();

		// this shall *not* throw an exception
		final StockQtyAndUOMQty result = StockQtyAndUOMQtys.validate(qtys);

		assertThat(result).isSameAs(qtys);
	}
}
