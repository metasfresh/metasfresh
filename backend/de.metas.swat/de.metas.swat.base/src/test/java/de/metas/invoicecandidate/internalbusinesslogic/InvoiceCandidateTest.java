package de.metas.invoicecandidate.internalbusinesslogic;

import static de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateFixtureHelper.*;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import de.metas.order.InvoiceRule;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.util.JSONObjectMapper;
import de.metas.util.lang.Percent;

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

class InvoiceCandidateTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		createRequiredMasterdata();
	}

	@Test
	void purchase_serialize_deserialize()
	{
		final StockQtyAndUOMQty qtysTotal = StockQtyAndUOMQtys.create(TEN, PRODUCT_ID, HUNDRET, DELIVERY_UOM_ID);
		final StockQtyAndUOMQty qtysWithIssues = StockQtyAndUOMQtys.create(ONE, PRODUCT_ID, TEN, DELIVERY_UOM_ID);

		final OrderedData orderedData = OrderedData.builder()
				.orderFullyDelivered(false)
				.qtyInStockUom(Quantitys.create(new BigDecimal("15"), STOCK_UOM_ID))
				.qty(Quantitys.create(new BigDecimal("60"), DELIVERY_UOM_ID))
				.build();

		final DeliveredData deliveredData = DeliveredData.builder()
				.receiptData(ReceiptData.builder()
						.productId(PRODUCT_ID)
						.qtyTotalInStockUom(qtysTotal.getStockQty())
						.qtyTotalNominal(qtysTotal.getUOMQtyNotNull())
						.qtyWithIssuesInStockUom(qtysWithIssues.getStockQty())
						.qtyWithIssuesNominal(qtysWithIssues.getUOMQtyNotNull())
						.build())
				.build();

		final PickedData pickedData = PickedData.builder()
				.productId(PRODUCT_ID)
				.qtyPicked(Quantitys.create(BigDecimal.ZERO, STOCK_UOM_ID))
				.qtyPickedInUOM(Quantitys.create(BigDecimal.ZERO, DELIVERY_UOM_ID))
				.build();

		final InvoicedData invoicedData = InvoicedData.builder()
				.netAmount(Money.zero(CURRENCY_ID))
				.qtys(StockQtyAndUOMQtys.createZero(PRODUCT_ID, DELIVERY_UOM_ID))
				.build();

		final InvoiceCandidate invoiceCandidate = InvoiceCandidate.builder()
				.soTrx(SOTrx.PURCHASE)
				.id(InvoiceCandidateId.ofRepoId(10))
				.product(new InvoiceCandidateProduct(PRODUCT_ID, true/* stocked */))
				.uomId(IC_UOM_ID)
				.invoicableQtyBasedOn(InvoicableQtyBasedOn.NominalWeight)
				.orderedData(orderedData)
				.deliveredData(deliveredData)
				.pickedData(pickedData)
				.invoicedData(invoicedData)
				.invoiceRule(InvoiceRule.AfterDelivery)
				.build();

		final JSONObjectMapper<InvoiceCandidate> jsonMapper = JSONObjectMapper.forClass(InvoiceCandidate.class);
		final String invoiceCandidateAsString = jsonMapper.writeValueAsString(invoiceCandidate);
		final InvoiceCandidate deserializedInvoiceCandidate = jsonMapper.readValue(invoiceCandidateAsString);
		assertThat(deserializedInvoiceCandidate).isEqualTo(invoiceCandidate);
	}

	@Test
	void sales_serialize_deserialize()
	{
		final Quantity shippedQtyInStockUom = Quantitys.create(TEN, STOCK_UOM_ID);
		final Quantity shippedQtyNominal = Quantitys.create(new BigDecimal("40"), DELIVERY_UOM_ID);
		final Quantity shippedQtyCatch = Quantitys.create(new BigDecimal("43"), DELIVERY_UOM_ID);

		final OrderedData orderedData = OrderedData.builder()
				.orderFullyDelivered(false)
				.qtyInStockUom(Quantitys.create(new BigDecimal("15"), STOCK_UOM_ID))
				.qty(Quantitys.create(new BigDecimal("60"), DELIVERY_UOM_ID))
				.build();

		final DeliveredData deliveredData = DeliveredData.builder()
				.shipmentData(ShipmentData.builder()
						.productId(PRODUCT_ID)
						.deliveredQtyItem(DeliveredQtyItem.builder()
								.qtyInStockUom(shippedQtyInStockUom)
								.qtyNominal(shippedQtyNominal)
								.qtyCatch(shippedQtyCatch)
								.build())
						.qtyInStockUom(shippedQtyInStockUom)
						.qtyNominal(shippedQtyNominal)
						.qtyCatch(shippedQtyCatch)
						.build())
				.build();

		final PickedData pickedData = PickedData.builder()
				.productId(PRODUCT_ID)
				.qtyPicked(Quantitys.create(new BigDecimal("20"), STOCK_UOM_ID))
				.qtyPickedInUOM(Quantitys.create(new BigDecimal("70"), DELIVERY_UOM_ID))
				.build();

		final InvoicedData invoicedData = InvoicedData.builder()
				.netAmount(Money.zero(CURRENCY_ID))
				.qtys(StockQtyAndUOMQtys.createZero(PRODUCT_ID, DELIVERY_UOM_ID))
				.build();

		final InvoiceCandidate invoiceCandidate = InvoiceCandidate.builder()
				.soTrx(SOTrx.SALES)
				.id(InvoiceCandidateId.ofRepoId(10))
				.product(new InvoiceCandidateProduct(PRODUCT_ID, true/* stocked */))
				.uomId(IC_UOM_ID)
				.invoicableQtyBasedOn(InvoicableQtyBasedOn.NominalWeight)
				.orderedData(orderedData)
				.deliveredData(deliveredData)
				.pickedData(pickedData)
				.invoicedData(invoicedData)
				.invoiceRule(InvoiceRule.AfterDelivery)
				.build();

		final JSONObjectMapper<InvoiceCandidate> jsonMapper = JSONObjectMapper.forClass(InvoiceCandidate.class);
		final String invoiceCandidateAsString = jsonMapper.writeValueAsString(invoiceCandidate);
		final InvoiceCandidate deserializedInvoiceCandidate = jsonMapper.readValue(invoiceCandidateAsString);
		assertThat(deserializedInvoiceCandidate).isEqualTo(invoiceCandidate);
	}

	@Test
	void purchase_qualityIssues()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("purchase_qualityIssues");

		final ReceiptData receiptData = invoiceCandidate.getDeliveredData().getReceiptData();
		assertThat(receiptData.computeQualityDiscount(InvoicableQtyBasedOn.NominalWeight).toBigDecimal()).isEqualTo(TEN);

		final StockQtyAndUOMQty qtysDelivered = invoiceCandidate.computeQtysDelivered();
		assertThat(qtysDelivered.getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo(HUNDRET);
		assertThat(qtysDelivered.getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);
		assertThat(qtysDelivered.getStockQty().toBigDecimal()).isEqualByComparingTo(TEN);
		assertThat(qtysDelivered.getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();

		assertThat(toInvoiceData.getQtysRaw().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo(HUNDRET);
		assertThat(toInvoiceData.getQtysRaw().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);
		assertThat(toInvoiceData.getQtysRaw().getStockQty().toBigDecimal()).isEqualByComparingTo(TEN);
		assertThat(toInvoiceData.getQtysRaw().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);

		assertThat(toInvoiceData.getQtysCalc().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo(NINETY);
		assertThat(toInvoiceData.getQtysCalc().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);
		assertThat(toInvoiceData.getQtysCalc().getStockQty().toBigDecimal()).isEqualByComparingTo(NINE);
		assertThat(toInvoiceData.getQtysCalc().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);

		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo(NINETY);
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);
		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo(NINE);
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void purchase_qualityIssues_qualityDiscountOverride()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("purchase_qualityIssues");
		assertThat(invoiceCandidate.getDeliveredData().getReceiptData().computeQualityDiscount(InvoicableQtyBasedOn.NominalWeight).toBigDecimal()).isEqualTo(TEN); // guard

		invoiceCandidate.changeQualityDiscountOverride(Percent.of("20"));

		final StockQtyAndUOMQty qtysWithIssuesEffective = invoiceCandidate.getDeliveredData().getReceiptData()
				.computeQtysWithIssuesEffective(invoiceCandidate.getQualityDiscountOverride(), InvoicableQtyBasedOn.NominalWeight);

		assertThat(qtysWithIssuesEffective.getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo("20");
		assertThat(qtysWithIssuesEffective.getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);
		assertThat(qtysWithIssuesEffective.getStockQty().toBigDecimal()).isEqualByComparingTo("2");
		assertThat(qtysWithIssuesEffective.getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);

		final StockQtyAndUOMQty qtysDelivered = invoiceCandidate.computeQtysDelivered();
		assertThat(qtysDelivered.getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo(HUNDRET);
		assertThat(qtysDelivered.getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);
		assertThat(qtysDelivered.getStockQty().toBigDecimal()).isEqualByComparingTo(TEN);
		assertThat(qtysDelivered.getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();

		assertThat(toInvoiceData.getQtysRaw().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo(HUNDRET);
		assertThat(toInvoiceData.getQtysRaw().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);
		assertThat(toInvoiceData.getQtysRaw().getStockQty().toBigDecimal()).isEqualByComparingTo(TEN);
		assertThat(toInvoiceData.getQtysRaw().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);

		assertThat(toInvoiceData.getQtysCalc().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo("80");
		assertThat(toInvoiceData.getQtysCalc().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);
		assertThat(toInvoiceData.getQtysCalc().getStockQty().toBigDecimal()).isEqualByComparingTo("8");
		assertThat(toInvoiceData.getQtysCalc().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);

		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo("80");
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);
		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("8");
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void sales_no_shipments_yet_catch()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("sales_no_shipments_yet");

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();

		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().toBigDecimal()).isZero();
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);
		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isZero();
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void sales_no_shipments_yet_nominal()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("sales_no_shipments_yet");
		invoiceCandidate.changeQtyBasedOn(InvoicableQtyBasedOn.NominalWeight);
		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();

		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().toBigDecimal()).isZero();
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);
		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isZero();
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void sales_afterDelivery_nominalWeight()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("sales_withoutCatchWeight");

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();

		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo("40"); // delivered nominal qty
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);
		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("10"); // delivered qty in stock UOM
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void sales_afterDelivery_nominalWeight_unStockedProduct()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("sales_unStockedProduct");

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();

		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo("60"); // ordered qty
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);
		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("29"); // ordered qty in stock UOM
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void sales_afterDelivery_unStockedProduct_withQtyToInvoiceOverride()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("sales_unStockedProduct_withQtyToInvoiceOverride");

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();

		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("4"); //  qtyToInvoiceOverrideInStockUom
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo("8"); // qtyToInvoiceOverrideInStockUom converted to uomId via C_UOM_Conversion
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);
	}

	@Test
	void sales_afterDelivery_unStockedProduct_withImmediateAndQtyToInvoiceOverride()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("sales_unStockedProduct_withImmediateAndQtyToInvoiceOverride");

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();

		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("4"); //  qtyToInvoiceOverrideInStockUom
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo("8"); // qtyToInvoiceOverrideInStockUom converted to uomId via C_UOM_Conversion
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);
	}

	/** Verifies that if there is no catch weight, we fall back to the nominal weight. */
	@Test
	void sales_afterDelivery_missing_catchWeight()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("sales_withoutCatchWeight");
		invoiceCandidate.changeQtyBasedOn(InvoicableQtyBasedOn.CatchWeight);

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();

		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo("40"); // delivered nominal qty
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);
		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("10"); // delivered qty in stock UOM
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void sales_afterDelivery_catchWeight()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("sales_withCatchWeight");

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();

		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo("43"); // delivered catch qty
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);

		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("10"); // delivered qty in stock UOM
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void sales_afterDelivery_catchWeight_change_invoicableQtyBasedOn()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("sales_withCatchWeight");
		assertThat(invoiceCandidate.getInvoicableQtyBasedOn()).isEqualTo(InvoicableQtyBasedOn.CatchWeight); // guard

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo("43"); // delivered catch qty
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);

		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("10"); // delivered qty in stock UOM
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);

		// invoke the method under test
		invoiceCandidate.changeQtyBasedOn(InvoicableQtyBasedOn.NominalWeight);

		assertThat(invoiceCandidate.getInvoicableQtyBasedOn()).isEqualTo(InvoicableQtyBasedOn.NominalWeight);

		final ToInvoiceData toInvoiceDataAfterChange = invoiceCandidate.computeToInvoiceData();
		assertThat(toInvoiceDataAfterChange.getQtysEffective().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo("40"); // delivered nominal qty
		assertThat(toInvoiceDataAfterChange.getQtysEffective().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);

		assertThat(toInvoiceDataAfterChange.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("10"); // delivered qty in stock UOM
		assertThat(toInvoiceDataAfterChange.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void sales_afterDelivery_change_to_immediate()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("sales_withCatchWeight");

		invoiceCandidate.changeInvoiceRule(InvoiceRule.Immediate);

		assertThat(invoiceCandidate.getInvoiceRule()).isEqualTo(InvoiceRule.Immediate);

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo("60"); // ordered qty
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);

		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("15"); // ordered qty in stock UOM
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void sales_immediate_qty_ordered_negated()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("sales_qtyOrderedNegated");

		assertThat(invoiceCandidate.getOrderedData().getQtyInStockUom().toBigDecimal()).isEqualByComparingTo("-15"); // guard

		assertThat(invoiceCandidate.getInvoiceRule()).isEqualTo(InvoiceRule.Immediate);

		final StockQtyAndUOMQty qtysDelivered = invoiceCandidate.computeQtysDelivered();
		assertThat(qtysDelivered.getStockQty().toBigDecimal()).isEqualByComparingTo("-10");
		assertThat(qtysDelivered.getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo("-40");

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo("-60"); // delivered nominal qty
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);

		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("-15"); // delivered qty in stock UOM
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void sales_immediate_qtyQrdered_less_than_qtyDelivered()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("sales_qtyOrderedLessThanDelivered");
		assertThat(invoiceCandidate.getOrderedData().getQtyInStockUom().toBigDecimal()).isEqualByComparingTo("5"); // guard

		assertThat(invoiceCandidate.getInvoiceRule()).isEqualTo(InvoiceRule.Immediate);

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo("40"); // delivered nominal qty
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);

		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("10"); // delivered qty in stock UOM
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void sales_afterDelivery_catchWeight_partially_invoiced()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("sales_withCatchWeightPartiallyInvoiced");

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo("25"); // 43 - 18
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);

		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("5"); // delivered qty in stock UOM - already invoiced
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void sales_afterDelivery_catchWeight_qtyToInvoiceOverride()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("sales_withCatchWeight_withQtyToInvoiceOverride");

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo("46.8"); // 10/10 of 43 plus 1/5 of 19
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);

		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("11"); // qtyToInvoiceOverride
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void sales_afterDelivery_catchWeight_qtyToInvoiceUOM_Override()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("sales_withCatchWeight_withQtyToInvoiceUOM_Override");

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo("46.879");
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);

		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("15");
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void sales_immediate_catchWeight_qtyToInvoiceUOM_Override()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("sales_immediate_catchWeight_qtyToInvoiceUOM_Override");

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo("46.879");
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);

		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("15");
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void sales_immediate_catchWeight_qtyInvoicedOverride_less_than_qtyOrdered()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("sales_immediate_catchWeight_qtyInvoicedOverride_less_than_qtyOrdered");
		assertThat(invoiceCandidate.getQtyToInvoiceOverrideInStockUom()).isEqualByComparingTo("4"); // guard

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo("8");
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);

		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("4"); // qtyToInvoiceOverride
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void sales_afterOrderDelivered()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("sales_afterOrderDelivered");

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().toBigDecimal()).isZero();
		assertThat(toInvoiceData.getQtysEffective().getUOMQtyNotNull().getUomId()).isEqualTo(DELIVERY_UOM_ID);

		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isZero();
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}
}
