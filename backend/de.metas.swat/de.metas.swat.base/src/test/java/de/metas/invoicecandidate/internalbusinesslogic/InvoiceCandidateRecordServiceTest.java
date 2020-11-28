package de.metas.invoicecandidate.internalbusinesslogic;

import static de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateFixtureHelper.CURRENCY_ID;
import static de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateFixtureHelper.IC_UOM_ID;
import static de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateFixtureHelper.PRODUCT_ID;
import static de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateFixtureHelper.STOCK_UOM_ID;
import static de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateFixtureHelper.createRequiredMasterdata;
import static de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateFixtureHelper.loadJsonFixture;
import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_InOut;
import org.compiere.util.Env;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.inout.model.I_M_InOut;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
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

class InvoiceCandidateRecordServiceTest
{
	private static final BigDecimal FIVE = new BigDecimal("5");
	private static final BigDecimal TWENTY = new BigDecimal("20");
	private static final BigDecimal EIGHTY = new BigDecimal("80");
	private static final BigDecimal ONE_HUNDRET_NINETY = new BigDecimal("190");
	private static final BigDecimal TWO_HUNDRET = new BigDecimal("200");
	private static final BigDecimal FOUR_HUNDRET = new BigDecimal("400");
	private static final BigDecimal FOUR_HUNDRET_TWENTY = new BigDecimal("420");

	private UOMTestHelper uomConversionHelper;

	@BeforeAll
	static void initStatic()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@AfterAll
	static void afterAll()
	{
		validateSnapshots();
	}

	@BeforeEach
	void beforeEach()
	{
		final AdempiereTestHelper adempiereTestHelper = AdempiereTestHelper.get();
		adempiereTestHelper.init();

		uomConversionHelper = new UOMTestHelper(Env.getCtx());
	}

	@Test
	void ofRecord()
	{
		final I_C_UOM stockUomRecord = uomConversionHelper.createUOM(2);
		stockUomRecord.setC_UOM_ID(STOCK_UOM_ID.getRepoId());
		saveRecord(stockUomRecord);

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(stockUomRecord.getC_UOM_ID());
		productRecord.setM_Product_ID(PRODUCT_ID.getRepoId());
		saveRecord(productRecord);

		final I_C_UOM icUomRecord = uomConversionHelper.createUOM(2);
		icUomRecord.setC_UOM_ID(IC_UOM_ID.getRepoId());
		saveRecord(icUomRecord);

		final I_C_Currency currencyRecord = newInstance(I_C_Currency.class);
		currencyRecord.setC_Currency_ID(CURRENCY_ID.getRepoId());
		saveRecord(currencyRecord);

		final I_C_Invoice_Candidate icRecord = newInstance(I_C_Invoice_Candidate.class);
		icRecord.setIsSOTrx(true);
		icRecord.setM_Product_ID(productRecord.getM_Product_ID());
		icRecord.setC_Currency_ID(currencyRecord.getC_Currency_ID());
		icRecord.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_AfterDelivery);
		icRecord.setInvoicableQtyBasedOn(X_C_Invoice_Candidate.INVOICABLEQTYBASEDON_CatchWeight);
		icRecord.setQtyOrdered(TWENTY);
		icRecord.setQtyEntered(EIGHTY);
		icRecord.setC_UOM_ID(icUomRecord.getC_UOM_ID());
		saveRecord(icRecord);

		final I_M_InOut io = newInstance(I_M_InOut.class);
		io.setDocStatus(X_M_InOut.DOCSTATUS_Completed);
		saveRecord(io);

		final I_C_UOM shipmentUomRecord = uomConversionHelper.createUOM(2);
		saveRecord(shipmentUomRecord);

		final I_M_InOutLine iol = newInstance(I_M_InOutLine.class);
		iol.setM_Product_ID(productRecord.getM_Product_ID());
		iol.setM_InOut_ID(io.getM_InOut_ID());
		iol.setC_UOM_ID(shipmentUomRecord.getC_UOM_ID());
		iol.setMovementQty(TEN);
		saveRecord(iol);

		uomConversionHelper.createUOMConversion(CreateUOMConversionRequest.builder()
				.fromUomId(UomId.ofRepoId(icUomRecord.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(shipmentUomRecord.getC_UOM_ID()))
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.fromToMultiplier(TEN)
				// .toFromMultiplier(new BigDecimal("0.1"))
				.build());

		final I_C_InvoiceCandidate_InOutLine icIol = newInstance(I_C_InvoiceCandidate_InOutLine.class);
		icIol.setC_Invoice_Candidate_ID(icRecord.getC_Invoice_Candidate_ID());
		icIol.setM_InOutLine_ID(iol.getM_InOutLine_ID());
		icIol.setQtyDelivered(iol.getMovementQty());
		icIol.setQtyDeliveredInUOM_Nominal(FOUR_HUNDRET);
		icIol.setQtyDeliveredInUOM_Catch(FOUR_HUNDRET_TWENTY);
		icIol.setC_UOM_ID(iol.getC_UOM_ID());
		saveRecord(icIol);

		final I_M_InOut io2 = newInstance(I_M_InOut.class);
		io2.setDocStatus(X_M_InOut.DOCSTATUS_Completed);
		saveRecord(io2);

		final I_M_InOutLine iol2 = newInstance(I_M_InOutLine.class);
		iol2.setM_Product_ID(productRecord.getM_Product_ID());
		iol2.setC_UOM_ID(shipmentUomRecord.getC_UOM_ID());
		iol2.setM_InOut_ID(io2.getM_InOut_ID());
		iol2.setMovementQty(FIVE);
		saveRecord(iol2);

		final I_C_InvoiceCandidate_InOutLine icIol2 = newInstance(I_C_InvoiceCandidate_InOutLine.class);
		icIol2.setC_Invoice_Candidate_ID(icRecord.getC_Invoice_Candidate_ID());
		icIol2.setM_InOutLine_ID(iol2.getM_InOutLine_ID());
		icIol2.setQtyDelivered(iol2.getMovementQty());
		icIol2.setQtyDeliveredInUOM_Nominal(TWO_HUNDRET);
		icIol2.setQtyDeliveredInUOM_Catch(ONE_HUNDRET_NINETY);
		icIol2.setC_UOM_ID(iol2.getC_UOM_ID());
		saveRecord(icIol2);

		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(icRecord.getC_Invoice_Candidate_ID());

		// invoke the method under test;
		final InvoiceCandidate result = new InvoiceCandidateRecordService().ofRecord(icRecord);

		assertThat(result.getId()).isEqualTo(invoiceCandidateId);

		final OrderedData orderedData = result.getOrderedData();
		assertThat(orderedData.getQtyInStockUom().getUomId()).isEqualTo(UomId.ofRepoId(stockUomRecord.getC_UOM_ID()));
		assertThat(orderedData.getQtyInStockUom().toBigDecimal()).isEqualByComparingTo(TWENTY);

		assertThat(orderedData.getQty().getUomId()).isEqualTo(UomId.ofRepoId(icUomRecord.getC_UOM_ID()));
		assertThat(orderedData.getQty().toBigDecimal()).isEqualByComparingTo(EIGHTY);

		final ShipmentData shippedData = result.getDeliveredData().getShipmentData();
		assertThat(shippedData.isEmpty()).isFalse();

		assertThat(shippedData.getDeliveredQtyItems())
				.extracting("qtyInStockUom.qty", "qtyNominal.qty", "qtyCatch.qty", "qtyOverride.qty")
				.contains(tuple(TEN, FOUR_HUNDRET, FOUR_HUNDRET_TWENTY, null),
						tuple(FIVE, TWO_HUNDRET, ONE_HUNDRET_NINETY, null));

		assertThat(shippedData.getQtyInStockUom()).isNotNull();
		assertThat(shippedData.getQtyInStockUom().getUomId()).isEqualTo(UomId.ofRepoId(stockUomRecord.getC_UOM_ID()));
		assertThat(shippedData.getQtyInStockUom().toBigDecimal()).isEqualByComparingTo("15");

		assertThat(shippedData.getQtyNominal()).isNotNull();
		assertThat(shippedData.getQtyNominal().getUomId()).isEqualTo(UomId.ofRepoId(icUomRecord.getC_UOM_ID()));
		assertThat(shippedData.getQtyNominal().toBigDecimal()).isEqualByComparingTo("60");

		assertThat(shippedData.getQtyCatch()).isNotNull();
		assertThat(shippedData.getQtyCatch().getUomId()).isEqualTo(UomId.ofRepoId(icUomRecord.getC_UOM_ID()));
		assertThat(shippedData.getQtyCatch().toBigDecimal()).isEqualByComparingTo("61"); // 42 + 19

		expect(result).toMatchSnapshot();
	}

	/** Very similar to {@link #ofRecord()}, but has an ic with {@code InvoicableQtyBasedOn = CatchWeight}, but no icIol catchWeigth-quantities */
	@Test
	void ofRecord_no_catchWeightInfos()
	{
		final I_C_UOM stockUomRecord = uomConversionHelper.createUOM(2);
		stockUomRecord.setC_UOM_ID(STOCK_UOM_ID.getRepoId());
		saveRecord(stockUomRecord);

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(stockUomRecord.getC_UOM_ID());
		productRecord.setM_Product_ID(PRODUCT_ID.getRepoId());
		saveRecord(productRecord);

		final I_C_UOM icUomRecord = uomConversionHelper.createUOM(2);
		icUomRecord.setC_UOM_ID(IC_UOM_ID.getRepoId());
		saveRecord(icUomRecord);

		final I_C_Currency currencyRecord = newInstance(I_C_Currency.class);
		currencyRecord.setC_Currency_ID(CURRENCY_ID.getRepoId());
		saveRecord(currencyRecord);

		final I_C_Invoice_Candidate icRecord = newInstance(I_C_Invoice_Candidate.class);
		icRecord.setIsSOTrx(true);
		icRecord.setM_Product_ID(productRecord.getM_Product_ID());
		icRecord.setC_Currency_ID(currencyRecord.getC_Currency_ID());
		icRecord.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_AfterDelivery);
		icRecord.setInvoicableQtyBasedOn(X_C_Invoice_Candidate.INVOICABLEQTYBASEDON_CatchWeight);
		icRecord.setQtyOrdered(TWENTY);
		icRecord.setQtyEntered(EIGHTY);
		icRecord.setC_UOM_ID(icUomRecord.getC_UOM_ID());
		saveRecord(icRecord);

		final I_M_InOut io = newInstance(I_M_InOut.class);
		io.setDocStatus(X_M_InOut.DOCSTATUS_Completed);
		saveRecord(io);

		final I_C_UOM shipmentUomRecord = uomConversionHelper.createUOM(2);
		saveRecord(shipmentUomRecord);

		final I_M_InOutLine iol = newInstance(I_M_InOutLine.class);
		iol.setM_Product_ID(productRecord.getM_Product_ID());
		iol.setM_InOut_ID(io.getM_InOut_ID());
		iol.setC_UOM_ID(shipmentUomRecord.getC_UOM_ID());
		iol.setMovementQty(TEN);
		saveRecord(iol);

		uomConversionHelper.createUOMConversion(CreateUOMConversionRequest.builder()
				.fromUomId(UomId.ofRepoId(icUomRecord.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(shipmentUomRecord.getC_UOM_ID()))
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.fromToMultiplier(TEN)
				// .toFromMultiplier(new BigDecimal("0.1"))
				.build());

		final I_C_InvoiceCandidate_InOutLine icIol = newInstance(I_C_InvoiceCandidate_InOutLine.class);
		icIol.setC_Invoice_Candidate_ID(icRecord.getC_Invoice_Candidate_ID());
		icIol.setM_InOutLine_ID(iol.getM_InOutLine_ID());
		icIol.setQtyDelivered(iol.getMovementQty());
		icIol.setQtyDeliveredInUOM_Nominal(FOUR_HUNDRET);
		icIol.setQtyDeliveredInUOM_Catch(null);
		icIol.setC_UOM_ID(iol.getC_UOM_ID());
		saveRecord(icIol);

		final I_M_InOut io2 = newInstance(I_M_InOut.class);
		io2.setDocStatus(X_M_InOut.DOCSTATUS_Completed);
		saveRecord(io2);

		final I_M_InOutLine iol2 = newInstance(I_M_InOutLine.class);
		iol2.setM_Product_ID(productRecord.getM_Product_ID());
		iol2.setC_UOM_ID(shipmentUomRecord.getC_UOM_ID());
		iol2.setM_InOut_ID(io2.getM_InOut_ID());
		iol2.setMovementQty(FIVE);
		saveRecord(iol2);

		final I_C_InvoiceCandidate_InOutLine icIol2 = newInstance(I_C_InvoiceCandidate_InOutLine.class);
		icIol2.setC_Invoice_Candidate_ID(icRecord.getC_Invoice_Candidate_ID());
		icIol2.setM_InOutLine_ID(iol2.getM_InOutLine_ID());
		icIol2.setQtyDelivered(iol2.getMovementQty());
		icIol2.setQtyDeliveredInUOM_Nominal(TWO_HUNDRET);
		icIol2.setQtyDeliveredInUOM_Catch(null);
		icIol2.setC_UOM_ID(iol2.getC_UOM_ID());
		saveRecord(icIol2);

		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(icRecord.getC_Invoice_Candidate_ID());

		// invoke the method under test;
		final InvoiceCandidate result = new InvoiceCandidateRecordService().ofRecord(icRecord);

		assertThat(result.getId()).isEqualTo(invoiceCandidateId);

		final OrderedData orderedData = result.getOrderedData();
		assertThat(orderedData.getQtyInStockUom().getUomId()).isEqualTo(UomId.ofRepoId(stockUomRecord.getC_UOM_ID()));
		assertThat(orderedData.getQtyInStockUom().toBigDecimal()).isEqualByComparingTo(TWENTY);

		assertThat(orderedData.getQty().getUomId()).isEqualTo(UomId.ofRepoId(icUomRecord.getC_UOM_ID()));
		assertThat(orderedData.getQty().toBigDecimal()).isEqualByComparingTo(EIGHTY);

		final ShipmentData shippedData = result.getDeliveredData().getShipmentData();
		assertThat(shippedData.isEmpty()).isFalse();

		assertThat(shippedData.getDeliveredQtyItems())
				.extracting("qtyInStockUom.qty", "qtyNominal.qty", "qtyCatch.qty", "qtyOverride.qty")
				.contains(tuple(TEN, FOUR_HUNDRET, null, null),
						tuple(FIVE, TWO_HUNDRET, null, null));

		assertThat(shippedData.getQtyInStockUom()).isNotNull();
		assertThat(shippedData.getQtyInStockUom().getUomId()).isEqualTo(UomId.ofRepoId(stockUomRecord.getC_UOM_ID()));
		assertThat(shippedData.getQtyInStockUom().toBigDecimal()).isEqualByComparingTo("15");

		assertThat(shippedData.getQtyNominal()).isNotNull();
		assertThat(shippedData.getQtyNominal().getUomId()).isEqualTo(UomId.ofRepoId(icUomRecord.getC_UOM_ID()));
		assertThat(shippedData.getQtyNominal().toBigDecimal()).isEqualByComparingTo("60");

		assertThat(shippedData.getQtyCatch()).isNull();

		expect(result).toMatchSnapshot();
	}

	@Test
	void save()
	{
		createRequiredMasterdata();
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("sales_withCatchWeight");
		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();

		// the same method can't create new records because it doesn'T make sure that *all* required columns are set
		final I_C_Invoice_Candidate icRecord = newInstance(I_C_Invoice_Candidate.class);
		icRecord.setC_Invoice_Candidate_ID(invoiceCandidate.getId().getRepoId());
		saveRecord(icRecord);

		// invoke the method under test;
		new InvoiceCandidateRecordService().updateRecord(invoiceCandidate, icRecord);

		final I_C_Invoice_Candidate resultRecord = icRecord;

		assertThat(resultRecord.getQtyToInvoice()).isEqualByComparingTo(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal());
		assertThat(resultRecord.getQtyToInvoiceBeforeDiscount()).isEqualByComparingTo(toInvoiceData.getQtysRaw().getStockQty().toBigDecimal());

		assertThat(resultRecord.getQtyToInvoiceInUOM_Calc()).isEqualByComparingTo(toInvoiceData.getQtysCalc().getUOMQtyOpt().get().toBigDecimal());
		assertThat(resultRecord.getQtyToInvoiceInUOM()).isEqualByComparingTo(toInvoiceData.getQtysEffective().getUOMQtyOpt().get().toBigDecimal());
	}
}
