package de.metas.invoicecandidate.internalbusinesslogic;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.math.BigDecimal;
import java.util.Optional;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_InOut;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.inout.model.I_M_InOut;
import de.metas.invoicecandidate.InvoiceCandidateIds;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lang.SOTrx;
import de.metas.product.ProductId;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.UomId;
import de.metas.uom.impl.UOMTestHelper;

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

class DeliveredQtysLoaderTest
{
	private static final BigDecimal FIVE = new BigDecimal("5");
	private static final BigDecimal TWENTY = new BigDecimal("20");
	private static final BigDecimal EIGHTY = new BigDecimal("80");
	private static final BigDecimal ONE_HUNDRET_NINETY = new BigDecimal("190");
	private static final BigDecimal TWO_HUNDRET = new BigDecimal("200");
	private static final BigDecimal FOUR_HUNDRET = new BigDecimal("400");
	private static final BigDecimal FOUR_HUNDRET_TWENTY = new BigDecimal("420");

	private UOMTestHelper uomConversionHelper;
	private I_C_UOM stockUomRecord;
	private I_C_UOM icUomRecord;
	private I_M_Product productRecord;
	private I_C_Invoice_Candidate icRecord;

	private I_C_InvoiceCandidate_InOutLine icIol1;
	private I_C_InvoiceCandidate_InOutLine icIol2;

	@BeforeEach
	void beforeEach()
	{
		final AdempiereTestHelper adempiereTestHelper = AdempiereTestHelper.get();
		adempiereTestHelper.init();

		uomConversionHelper = new UOMTestHelper(Env.getCtx());
	}

	@Test
	void loadDeliveredQtys_sales_standard()
	{
		createStandardData();

		final DeliveredData result = DeliveredDataLoader
				.builder()
				.soTrx(SOTrx.SALES)
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.invoiceCandidateId(InvoiceCandidateIds.ofRecord(icRecord))
				.icUomId(UomId.ofRepoId(icUomRecord.getC_UOM_ID()))
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.stockUomId(UomId.ofRepoId(stockUomRecord.getC_UOM_ID()))
				.deliveryQualityDiscount(Optional.empty())
				.negateQtys(false)
				.build()
				.loadDeliveredQtys();

		assertThat(result.getShipmentData().getDeliveredQtyItems())
				.extracting("qtyInStockUom.qty", "qtyNominal.qty", "qtyCatch.qty", "qtyOverride.qty")
				.contains(tuple(TEN, FOUR_HUNDRET, FOUR_HUNDRET_TWENTY, null),
						tuple(FIVE, TWO_HUNDRET, ONE_HUNDRET_NINETY, null));

		assertThat(result.getShipmentData().getQtyInStockUom().getUomId()).isEqualTo(UomId.ofRepoId(stockUomRecord.getC_UOM_ID()));
		assertThat(result.getShipmentData().getQtyInStockUom().toBigDecimal()).isEqualByComparingTo("15");

		assertThat(result.getShipmentData().getQtyNominal().getUomId()).isEqualTo(UomId.ofRepoId(icUomRecord.getC_UOM_ID()));
		assertThat(result.getShipmentData().getQtyNominal().toBigDecimal()).isEqualByComparingTo("60");

		assertThat(result.getShipmentData().getQtyCatch()).isNotNull();
		assertThat(result.getShipmentData().getQtyCatch().getUomId()).isEqualTo(UomId.ofRepoId(icUomRecord.getC_UOM_ID()));
		assertThat(result.getShipmentData().getQtyCatch().toBigDecimal()).isEqualByComparingTo("61"); // 42 + 19
	}

	/**
	 * Verifies that we tolerate individual {@link I_C_InvoiceCandidate_InOutLine} that have no catch quantities.
	 * This can happen if we create shipments and a part of the quantity is on-the-fly-picked,
	 * and we have a quantity override value set in the shipment schedule.
	 */
	@Test
	void loadDeliveredQtys_sales_one_icIol_without_catch()
	{
		createStandardData();
		icIol1.setQtyDeliveredInUOM_Catch(null);
		saveRecord(icIol1);

		final DeliveredDataLoader deliveredQtysLoader = DeliveredDataLoader.builder()
				.invoiceCandidateId(InvoiceCandidateIds.ofRecord(icRecord))
				.soTrx(SOTrx.SALES)
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.icUomId(UomId.ofRepoId(icUomRecord.getC_UOM_ID()))
				.stockUomId(UomId.ofRepoId(stockUomRecord.getC_UOM_ID()))
				.deliveryQualityDiscount(Optional.empty())
				.negateQtys(false)
				.build();

		final DeliveredData result = deliveredQtysLoader.loadDeliveredQtys();

		assertThat(result.getShipmentData().getDeliveredQtyItems())
				.extracting("qtyInStockUom.qty", "qtyNominal.qty", "qtyCatch.qty", "qtyOverride.qty")
				.contains(tuple(TEN, FOUR_HUNDRET, null, null),
						tuple(FIVE, TWO_HUNDRET, ONE_HUNDRET_NINETY, null));

		assertThat(result.getShipmentData().getQtyInStockUom().getUomId()).isEqualTo(UomId.ofRepoId(stockUomRecord.getC_UOM_ID()));
		assertThat(result.getShipmentData().getQtyInStockUom().toBigDecimal()).isEqualByComparingTo("15");

		assertThat(result.getShipmentData().getQtyNominal().getUomId()).isEqualTo(UomId.ofRepoId(icUomRecord.getC_UOM_ID()));
		assertThat(result.getShipmentData().getQtyNominal().toBigDecimal()).isEqualByComparingTo("60"); // 40 + 20

		assertThat(result.getShipmentData().getQtyCatch()).isNotNull();
		assertThat(result.getShipmentData().getQtyCatch().getUomId()).isEqualTo(UomId.ofRepoId(icUomRecord.getC_UOM_ID()));
		assertThat(result.getShipmentData().getQtyCatch().toBigDecimal()).isEqualByComparingTo("19"); // null + 19
	}

	/**
	 * Verifies that we tolerate the case of <b>all</> {@link I_C_InvoiceCandidate_InOutLine} to have no catch quantities.
	 * This can happen if we create shipments without actually picked HUs and no catch-qty override;
	 * In this case, we fall back to the nominal quantity.
	 */
	@Test
	void loadDeliveredQtys_sales_all_icIol_without_catch()
	{
		createStandardData();
		icIol1.setQtyDeliveredInUOM_Catch(null);
		saveRecord(icIol1);
		icIol2.setQtyDeliveredInUOM_Catch(null);
		saveRecord(icIol2);

		final DeliveredDataLoader deliveredQtysLoader = DeliveredDataLoader.builder()
				.invoiceCandidateId(InvoiceCandidateIds.ofRecord(icRecord))
				.soTrx(SOTrx.SALES)
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.icUomId(UomId.ofRepoId(icUomRecord.getC_UOM_ID()))
				.stockUomId(UomId.ofRepoId(stockUomRecord.getC_UOM_ID()))
				.deliveryQualityDiscount(Optional.empty())
				.negateQtys(false)
				.build();

		final DeliveredData result = deliveredQtysLoader.loadDeliveredQtys();

		assertThat(result.getShipmentData().getDeliveredQtyItems())
				.extracting("qtyInStockUom.qty", "qtyNominal.qty", "qtyCatch.qty", "qtyOverride.qty")
				.contains(tuple(TEN, FOUR_HUNDRET, null, null),
						tuple(FIVE, TWO_HUNDRET, null, null));

		assertThat(result.getShipmentData().getQtyInStockUom().getUomId()).isEqualTo(UomId.ofRepoId(stockUomRecord.getC_UOM_ID()));
		assertThat(result.getShipmentData().getQtyInStockUom().toBigDecimal()).isEqualByComparingTo("15");

		assertThat(result.getShipmentData().getQtyNominal().getUomId()).isEqualTo(UomId.ofRepoId(icUomRecord.getC_UOM_ID()));
		assertThat(result.getShipmentData().getQtyNominal().toBigDecimal()).isEqualByComparingTo("60"); // 40 + 20

		assertThat(result.getShipmentData().getQtyCatch()).isNull();
	}

	@Test
	void loadDeliveredQtys_shippedData_one_icIol_without_catch_fixed_via_override()
	{
		createStandardData();

		// set the override-qty in place of the catch-qty
		icIol1.setQtyDeliveredInUOM_Override(icIol1.getQtyDeliveredInUOM_Catch());
		icIol1.setQtyDeliveredInUOM_Catch(null);
		saveRecord(icIol1);

		final DeliveredDataLoader deliveredQtysLoader = DeliveredDataLoader.builder()
				.invoiceCandidateId(InvoiceCandidateIds.ofRecord(icRecord))
				.soTrx(SOTrx.SALES)
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.icUomId(UomId.ofRepoId(icUomRecord.getC_UOM_ID()))
				.stockUomId(UomId.ofRepoId(stockUomRecord.getC_UOM_ID()))
				.deliveryQualityDiscount(Optional.empty())
				.negateQtys(false)
				.build();

		final DeliveredData result = deliveredQtysLoader.loadDeliveredQtys();

		assertThat(result.getShipmentData().getDeliveredQtyItems())
				.extracting("qtyInStockUom.qty", "qtyNominal.qty", "qtyCatch.qty", "qtyOverride.qty")
				.contains(tuple(TEN, FOUR_HUNDRET, null, FOUR_HUNDRET_TWENTY),
						tuple(FIVE, TWO_HUNDRET, ONE_HUNDRET_NINETY, null));

		assertThat(result.getShipmentData().getQtyInStockUom().getUomId()).isEqualTo(UomId.ofRepoId(stockUomRecord.getC_UOM_ID()));
		assertThat(result.getShipmentData().getQtyInStockUom().toBigDecimal()).isEqualByComparingTo("15");

		assertThat(result.getShipmentData().getQtyNominal().getUomId()).isEqualTo(UomId.ofRepoId(icUomRecord.getC_UOM_ID()));
		assertThat(result.getShipmentData().getQtyNominal().toBigDecimal()).isEqualByComparingTo("62"); // the override-qty we set for icIol1 also overrides the nomimal qty

		assertThat(result.getShipmentData().getQtyCatch()).isNotNull();
		assertThat(result.getShipmentData().getQtyCatch().getUomId()).isEqualTo(UomId.ofRepoId(icUomRecord.getC_UOM_ID()));
		assertThat(result.getShipmentData().getQtyCatch().toBigDecimal()).isEqualByComparingTo("61"); // 42 + 19
	}

	@Test
	void loadDeliveredQtys_shippedData_one_icIol_without_catch_fixed_via_override_negate()
	{
		createStandardData();

		// set the override-qty in place of the catch-qty
		icIol1.setQtyDeliveredInUOM_Override(icIol1.getQtyDeliveredInUOM_Catch());
		icIol1.setQtyDeliveredInUOM_Catch(null);
		saveRecord(icIol1);

		final DeliveredDataLoader deliveredQtysLoader = DeliveredDataLoader.builder()
				.invoiceCandidateId(InvoiceCandidateIds.ofRecord(icRecord))
				.soTrx(SOTrx.SALES)
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.icUomId(UomId.ofRepoId(icUomRecord.getC_UOM_ID()))
				.stockUomId(UomId.ofRepoId(stockUomRecord.getC_UOM_ID()))
				.deliveryQualityDiscount(Optional.empty())
				.negateQtys(true)
				.build();

		final DeliveredData result = deliveredQtysLoader.loadDeliveredQtys();

		assertThat(result.getShipmentData().getDeliveredQtyItems())
				.extracting("qtyInStockUom.qty", "qtyNominal.qty", "qtyCatch.qty", "qtyOverride.qty")
				.contains(tuple(TEN.negate(), FOUR_HUNDRET.negate(), null, FOUR_HUNDRET_TWENTY.negate()),
						tuple(FIVE.negate(), TWO_HUNDRET.negate(), ONE_HUNDRET_NINETY.negate(), null));

		assertThat(result.getShipmentData().getQtyInStockUom().getUomId()).isEqualTo(UomId.ofRepoId(stockUomRecord.getC_UOM_ID()));
		assertThat(result.getShipmentData().getQtyInStockUom().toBigDecimal()).isEqualByComparingTo("-15");

		assertThat(result.getShipmentData().getQtyNominal().getUomId()).isEqualTo(UomId.ofRepoId(icUomRecord.getC_UOM_ID()));
		assertThat(result.getShipmentData().getQtyNominal().toBigDecimal()).isEqualByComparingTo("-62"); // the override-qty we set for icIol1 also overrides the nomimal qty

		assertThat(result.getShipmentData().getQtyCatch()).isNotNull();
		assertThat(result.getShipmentData().getQtyCatch().getUomId()).isEqualTo(UomId.ofRepoId(icUomRecord.getC_UOM_ID()));
		assertThat(result.getShipmentData().getQtyCatch().toBigDecimal()).isEqualByComparingTo("-61"); // 42 + 19
	}

	private void createStandardData()
	{
		stockUomRecord = uomConversionHelper.createUOM(2);
		saveRecord(stockUomRecord);

		productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(stockUomRecord.getC_UOM_ID());
		saveRecord(productRecord);

		icUomRecord = uomConversionHelper.createUOM(2);
		saveRecord(icUomRecord);

		icRecord = newInstance(I_C_Invoice_Candidate.class);
		icRecord.setM_Product_ID(productRecord.getM_Product_ID());
		icRecord.setQtyOrdered(TWENTY);
		icRecord.setQtyEntered(EIGHTY);
		icRecord.setC_UOM_ID(icUomRecord.getC_UOM_ID());
		saveRecord(icRecord);

		final I_M_InOut io = newInstance(I_M_InOut.class);
		io.setDocStatus(X_M_InOut.DOCSTATUS_Completed);
		saveRecord(io);

		final I_M_InOutLine iol = newInstance(I_M_InOutLine.class);
		iol.setM_Product_ID(productRecord.getM_Product_ID());
		iol.setM_InOut_ID(io.getM_InOut_ID());
		iol.setMovementQty(TEN);
		saveRecord(iol);

		final I_C_UOM shipmentUomRecord = uomConversionHelper.createUOM(2);
		saveRecord(shipmentUomRecord);

		uomConversionHelper.createUOMConversion(CreateUOMConversionRequest.builder()
				.fromUomId(UomId.ofRepoId(icUomRecord.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(shipmentUomRecord.getC_UOM_ID()))
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.fromToMultiplier(TEN)
				// .toFromMultiplier(new BigDecimal("0.1"))
				.build());

		icIol1 = newInstance(I_C_InvoiceCandidate_InOutLine.class);
		icIol1.setC_Invoice_Candidate_ID(icRecord.getC_Invoice_Candidate_ID());
		icIol1.setM_InOutLine_ID(iol.getM_InOutLine_ID());
		icIol1.setC_UOM_ID(iol.getC_UOM_ID());
		icIol1.setQtyDelivered(iol.getMovementQty());
		icIol1.setQtyDeliveredInUOM_Nominal(FOUR_HUNDRET); // shall be converted to 40 in IC-UOM
		icIol1.setQtyDeliveredInUOM_Catch(FOUR_HUNDRET_TWENTY); // shall be converted to 42 in IC-UOM
		icIol1.setC_UOM_ID(shipmentUomRecord.getC_UOM_ID());
		saveRecord(icIol1);

		final I_M_InOut io2 = newInstance(I_M_InOut.class);
		io2.setDocStatus(X_M_InOut.DOCSTATUS_Completed);
		saveRecord(io2);

		final I_M_InOutLine iol2 = newInstance(I_M_InOutLine.class);
		iol2.setM_Product_ID(productRecord.getM_Product_ID());
		iol2.setM_InOut_ID(io2.getM_InOut_ID());
		iol2.setMovementQty(FIVE);
		saveRecord(iol2);

		icIol2 = newInstance(I_C_InvoiceCandidate_InOutLine.class);
		icIol2.setC_Invoice_Candidate_ID(icRecord.getC_Invoice_Candidate_ID());
		icIol2.setM_InOutLine_ID(iol2.getM_InOutLine_ID());
		icIol2.setC_UOM_ID(iol2.getC_UOM_ID());
		icIol2.setQtyDelivered(iol2.getMovementQty());
		icIol2.setQtyDeliveredInUOM_Nominal(TWO_HUNDRET); // shall be converted to 20 in IC-UOM
		icIol2.setQtyDeliveredInUOM_Catch(ONE_HUNDRET_NINETY); // shall be converted to 19 in IC-UOM
		icIol2.setC_UOM_ID(shipmentUomRecord.getC_UOM_ID());
		saveRecord(icIol2);
	}

}
