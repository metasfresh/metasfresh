package de.metas.edi.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.metas.esb.edi.model.I_EDI_DesadvLine_Pack.*;

import de.metas.business.BusinessTestHelper;
import de.metas.esb.edi.model.I_EDI_DesadvLine_Pack;
import de.metas.handlingunits.generichumodel.HURepository;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.uom.IUOMDAO;

/*
 * #%L
 * de.metas.edi
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

public class DesadvBLTest
{
	private DesadvBL desadvBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		desadvBL = new DesadvBL(new HURepository());
	}

	// 9 CUs per COLI and 20.5 CUs => 3 COLIs
	@Test
	void setQty_isUOMForTUs()
	{
		final I_C_UOM coliUomRecord = BusinessTestHelper.createUOM("coli", IUOMDAO.X12DE355_COLI);
		final I_C_UOM eachUomRecord = BusinessTestHelper.createUOM("each", IUOMDAO.X12DE355_Each);

		final I_M_Product productRecord = BusinessTestHelper.createProduct("product", eachUomRecord);

		final ProductId productId = ProductId.ofRepoId(productRecord.getM_Product_ID());

		final I_EDI_DesadvLine_Pack desadvLinePackRecord = newInstance(I_EDI_DesadvLine_Pack.class);
		// desadvLinePackRecord.setQtyCU(new BigDecimal("9"));
		desadvLinePackRecord.setC_UOM_ID(coliUomRecord.getC_UOM_ID());
		desadvLinePackRecord.setQtyItemCapacity(new BigDecimal("9"));
		desadvLinePackRecord.setQtyCUsPerLU(new BigDecimal("99"));
		saveRecord(desadvLinePackRecord);

		// invoke the method under test
		desadvBL.setQty(
				productId,
				desadvLinePackRecord,
				Quantity.of("99999", eachUomRecord) /* qtyCUInStockUom */,

				StockQtyAndUOMQty.builder()
						.productId(productId)
						.stockQty(Quantity.of("20.5", eachUomRecord)) /* qtyCUsPerLUInStockUom */
						.uomQty(Quantity.of("4", coliUomRecord))
						.build());

		assertThat(desadvLinePackRecord)
				.extracting(
						COLUMNNAME_QtyCUsPerLU,
						COLUMNNAME_QtyCU,
						COLUMNNAME_MovementQty)
				.containsExactly(
						new BigDecimal("3"),
						new BigDecimal("1"),
						new BigDecimal("20.5"));
	}

}
