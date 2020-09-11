package de.metas.edi.api.impl;

import static de.metas.esb.edi.model.I_EDI_DesadvLine.COLUMNNAME_QtyDeliveredInInvoiceUOM;
import static de.metas.esb.edi.model.I_EDI_DesadvLine.COLUMNNAME_QtyDeliveredInStockingUOM;
import static de.metas.esb.edi.model.I_EDI_DesadvLine.COLUMNNAME_QtyDeliveredInUOM;
import static de.metas.esb.edi.model.I_EDI_DesadvLine_Pack.COLUMNNAME_MovementQty;
import static de.metas.esb.edi.model.I_EDI_DesadvLine_Pack.COLUMNNAME_QtyCU;
import static de.metas.esb.edi.model.I_EDI_DesadvLine_Pack.COLUMNNAME_QtyCUsPerLU;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.business.BusinessTestHelper;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.esb.edi.model.I_EDI_DesadvLine_Pack;
import de.metas.handlingunits.generichumodel.HURepository;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;

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

class DesadvBLTest
{
	private DesadvBL desadvBL;
	private UomId coliUomId;
	private UomId eachUomId;
	private UomId kiloUomId;
	private ProductId productId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		desadvBL = new DesadvBL(new HURepository());

		eachUomId = UomId.ofRepoId(BusinessTestHelper.createUOM("each", X12DE355.EACH).getC_UOM_ID());
		coliUomId = UomId.ofRepoId(BusinessTestHelper.createUOM("coli", X12DE355.COLI).getC_UOM_ID());
		kiloUomId = UomId.ofRepoId(BusinessTestHelper.createUOM("kilo", X12DE355.KILOGRAM).getC_UOM_ID());

		final I_M_Product productRecord = BusinessTestHelper.createProduct("product", eachUomId);
		productId = ProductId.ofRepoId(productRecord.getM_Product_ID());

		// one PCE is two KGM
		BusinessTestHelper.createUOMConversion(CreateUOMConversionRequest.builder()
				.fromUomId(eachUomId)
				.toUomId(kiloUomId)
				.productId(productId)
				.catchUOMForProduct(true)
				.fromToMultiplier(new BigDecimal("2")).build());
	}

	// 9 CUs per COLI and 20.5 CUs => 3 COLIs
	@Test
	void setQty_isUOMForTUs()
	{
		final I_EDI_DesadvLine_Pack desadvLinePackRecord = newInstance(I_EDI_DesadvLine_Pack.class);
		// desadvLinePackRecord.setQtyCU(new BigDecimal("9"));
		desadvLinePackRecord.setC_UOM_ID(coliUomId.getRepoId());
		desadvLinePackRecord.setQtyItemCapacity(new BigDecimal("9"));
		desadvLinePackRecord.setQtyCUsPerLU(new BigDecimal("99"));
		saveRecord(desadvLinePackRecord);

		// invoke the method under test
		desadvBL.setQty(
				desadvLinePackRecord,
				productId,
				Quantitys.create("99999", eachUomId) /* qtyCUInStockUom */,

				StockQtyAndUOMQty.builder()
						.productId(productId)
						.stockQty(Quantitys.create("20.5", eachUomId)) /* qtyCUsPerLUInStockUom */
						.uomQty(Quantitys.create("4", coliUomId))
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

	/**
	 * have 10PCE fitting into 1COLI and 1PCE weighing 2KGM
	 * Create a desadvLine with 0PCE and 0COLI;
	 * Then add 9PCE and 20KGM (not 18 bc catch-weight)
	 * => expect the deadvLine to have 9PCE, 1COLI and 20KGM
	 */
	@Test
	void addOrSubtractInOutLineQty_iolQtyWithKGM()
	{
		// given
		final I_EDI_Desadv desadvRecord = newInstance(I_EDI_Desadv.class);
		saveRecord(desadvRecord);

		final I_EDI_DesadvLine desadvLineRecord = newInstance(I_EDI_DesadvLine.class);
		desadvLineRecord.setEDI_Desadv_ID(desadvRecord.getEDI_Desadv_ID());
		desadvLineRecord.setM_Product_ID(productId.getRepoId());
		desadvLineRecord.setQtyDeliveredInStockingUOM(BigDecimal.ZERO);
		desadvLineRecord.setC_UOM_ID(coliUomId.getRepoId());
		desadvLineRecord.setQtyDeliveredInUOM(BigDecimal.ZERO);
		desadvLineRecord.setC_UOM_Invoice_ID(kiloUomId.getRepoId());
		desadvLineRecord.setQtyItemCapacity(BigDecimal.TEN); // 10 PCE fit into 1 COLI
		saveRecord(desadvLineRecord);

		final StockQtyAndUOMQty inOutLineQty = StockQtyAndUOMQty.builder().productId(productId)
				.stockQty(Quantitys.create("9", eachUomId))
				.uomQty(Quantitys.create("20", kiloUomId)).build();

		// when
		desadvBL.addOrSubtractInOutLineQty(desadvLineRecord, inOutLineQty, true);

		// then
		assertThat(desadvLineRecord).extracting(COLUMNNAME_QtyDeliveredInStockingUOM, COLUMNNAME_QtyDeliveredInUOM, COLUMNNAME_QtyDeliveredInInvoiceUOM)
				.contains(new BigDecimal("9"), new BigDecimal("1"), new BigDecimal("20"));
	}

	/**
	 * have 1PCE weighing 2KGM
	 * Create a desadvLine with 0PCE and 0KGM;
	 * Then add 9PCE and 20KGM (not 18 bc catch-weight)
	 * => expect the deadvLine to have 9PCE and 18KGM
	 */
	@Test
	void addOrSubtractInOutLineQty_iolQtyWithPCE()
	{
		// given
		final I_EDI_Desadv desadvRecord = newInstance(I_EDI_Desadv.class);
		saveRecord(desadvRecord);

		final I_EDI_DesadvLine desadvLineRecord = newInstance(I_EDI_DesadvLine.class);
		desadvLineRecord.setEDI_Desadv_ID(desadvRecord.getEDI_Desadv_ID());
		desadvLineRecord.setM_Product_ID(productId.getRepoId());
		desadvLineRecord.setQtyDeliveredInStockingUOM(BigDecimal.ZERO);
		desadvLineRecord.setC_UOM_ID(eachUomId.getRepoId());
		desadvLineRecord.setQtyDeliveredInUOM(BigDecimal.ZERO);
		desadvLineRecord.setC_UOM_Invoice_ID(kiloUomId.getRepoId());
		desadvLineRecord.setQtyItemCapacity(BigDecimal.TEN); // 10 PCE fit into 1 COLI
		saveRecord(desadvLineRecord);

		final StockQtyAndUOMQty inOutLineQty = StockQtyAndUOMQty.builder().productId(productId)
				.stockQty(Quantitys.create("9", eachUomId))
				.uomQty(Quantitys.create("20", kiloUomId)).build();

		// when
		desadvBL.addOrSubtractInOutLineQty(desadvLineRecord, inOutLineQty, true);

		// then
		assertThat(desadvLineRecord).extracting(COLUMNNAME_QtyDeliveredInStockingUOM, COLUMNNAME_QtyDeliveredInUOM, COLUMNNAME_QtyDeliveredInInvoiceUOM)
				.contains(new BigDecimal("9"), new BigDecimal("9"), new BigDecimal("20"));
	}

}
