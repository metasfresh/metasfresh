package de.metas.invoicecandidate.internalbusinesslogic;

import static de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateFixtureHelper.*;
import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.util.JSONObjectMapper;

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
	void serialize_deserialize()
	{
		final Quantity qtyInStockUom = Quantitys.create(TEN, STOCK_UOM_ID);
		final Quantity qtyNominal = Quantitys.create(new BigDecimal("40"), DELIVERY_UOM_ID);
		final Quantity qtyCatch = Quantitys.create(new BigDecimal("43"), DELIVERY_UOM_ID);

		final OrderedData orderedData = OrderedData.builder()
				.qtyInStockUom(Quantitys.create(new BigDecimal("15"), STOCK_UOM_ID))
				.qty(Quantitys.create(new BigDecimal("60"), DELIVERY_UOM_ID))
				.build();

		final DeliveredData deliveredData = DeliveredData.builder()
				.shipmentData(ShipmentData.builder()
						.productId(PRODUCT_ID)
						.shippedQtyItem(ShippedQtyItem.builder()
								.qtyInStockUom(qtyInStockUom)
								.qtyNominal(qtyNominal)
								.qtyCatch(qtyCatch)
								.build())
						.qtyInStockUom(qtyInStockUom)
						.qtyNominal(qtyNominal)
						.qtyCatch(qtyCatch)
						.build())
				.build();

		final InvoicedData invoicedData = InvoicedData.builder()
				.netAmount(Money.zero(CURRENCY_ID))
				.qtys(StockQtyAndUOMQtys.createZero(PRODUCT_ID, DELIVERY_UOM_ID))
				.build();

		final InvoiceCandidate invoiceCandidate = InvoiceCandidate.builder()
				.soTrx(SOTrx.SALES)
				.id(InvoiceCandidateId.ofRepoId(10))
				.productId(PRODUCT_ID)
				.uomId(DELIVERY_UOM_ID)
				.invoicableQtyBasedOn(InvoicableQtyBasedOn.NominalWeight)
				.orderedData(orderedData)
				.deliveredData(deliveredData)
				.invoicedData(invoicedData)
				.invoiceRule(InvoiceRule.AfterDelivery)
				.build();

		final JSONObjectMapper<InvoiceCandidate> jsonMapper = JSONObjectMapper.forClass(InvoiceCandidate.class);
		final String invoiceCandidateAsString = jsonMapper.writeValueAsString(invoiceCandidate);
		final InvoiceCandidate deserializedInvoiceCandidate = jsonMapper.readValue(invoiceCandidateAsString);
		assertThat(deserializedInvoiceCandidate).isEqualTo(invoiceCandidate);
	}

	@Test
	void afterDelivery_nominalWeight()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("withoutCatchWeight");

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();

		assertThat(toInvoiceData.getQtysEffective().getUomQty().toBigDecimal()).isEqualByComparingTo("40"); // delivered nominal qty
		assertThat(toInvoiceData.getQtysEffective().getUomQty().getUomId()).isEqualTo(DELIVERY_UOM_ID);
		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("10"); // delivered qty in stock UOM
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void afterDelivery_missing_catchWeight()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("withoutCatchWeight");

		assertThatThrownBy(() -> invoiceCandidate.changeQtyBasedOn(InvoicableQtyBasedOn.CatchWeight))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("missing qtyCatch");
	}

	@Test
	void afterDelivery_catchWeight()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("withCatchWeight");

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();

		assertThat(toInvoiceData.getQtysEffective().getUomQty().toBigDecimal()).isEqualByComparingTo("43"); // delivered catch qty
		assertThat(toInvoiceData.getQtysEffective().getUomQty().getUomId()).isEqualTo(DELIVERY_UOM_ID);

		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("10"); // delivered qty in stock UOM
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void afterDelivery_catchWeight_change_invoicableQtyBasedOn()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("withCatchWeight");
		assertThat(invoiceCandidate.getInvoicableQtyBasedOn()).isEqualTo(InvoicableQtyBasedOn.CatchWeight); // guard

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();
		assertThat(toInvoiceData.getQtysEffective().getUomQty().toBigDecimal()).isEqualByComparingTo("43"); // delivered catch qty
		assertThat(toInvoiceData.getQtysEffective().getUomQty().getUomId()).isEqualTo(DELIVERY_UOM_ID);

		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("10"); // delivered qty in stock UOM
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);

		// invoke the method under test
		invoiceCandidate.changeQtyBasedOn(InvoicableQtyBasedOn.NominalWeight);

		assertThat(invoiceCandidate.getInvoicableQtyBasedOn()).isEqualTo(InvoicableQtyBasedOn.NominalWeight);

		final ToInvoiceData toInvoiceDataAfterChange = invoiceCandidate.computeToInvoiceData();
		assertThat(toInvoiceDataAfterChange.getQtysEffective().getUomQty().toBigDecimal()).isEqualByComparingTo("40"); // delivered nominal qty
		assertThat(toInvoiceDataAfterChange.getQtysEffective().getUomQty().getUomId()).isEqualTo(DELIVERY_UOM_ID);

		assertThat(toInvoiceDataAfterChange.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("10"); // delivered qty in stock UOM
		assertThat(toInvoiceDataAfterChange.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void afterDelivery_change_to_immediate()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("withCatchWeight");

		invoiceCandidate.changeInvoiceRule(InvoiceRule.Immediate);

		assertThat(invoiceCandidate.getInvoiceRule()).isEqualTo(InvoiceRule.Immediate);

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();
		assertThat(toInvoiceData.getQtysEffective().getUomQty().toBigDecimal()).isEqualByComparingTo("60"); // ordered qty
		assertThat(toInvoiceData.getQtysEffective().getUomQty().getUomId()).isEqualTo(DELIVERY_UOM_ID);

		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("15"); // ordered qty in stock UOM
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void immediate_qty_ordered_negated()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("qtyOrderedNegated");

		assertThat(invoiceCandidate.getOrderedData().getQtyInStockUom().toBigDecimal()).isEqualByComparingTo("-15"); // guard

		assertThat(invoiceCandidate.getInvoiceRule()).isEqualTo(InvoiceRule.Immediate);

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();
		assertThat(toInvoiceData.getQtysEffective().getUomQty().toBigDecimal()).isEqualByComparingTo("-60"); // delivered nominal qty
		assertThat(toInvoiceData.getQtysEffective().getUomQty().getUomId()).isEqualTo(DELIVERY_UOM_ID);

		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("-15"); // delivered qty in stock UOM
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void immediate_qtyQrdered_less_than_qtyDelivered()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("qtyOrderedLessThanDelivered");
		assertThat(invoiceCandidate.getOrderedData().getQtyInStockUom().toBigDecimal()).isEqualByComparingTo("5"); // guard

		assertThat(invoiceCandidate.getInvoiceRule()).isEqualTo(InvoiceRule.Immediate);

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();
		assertThat(toInvoiceData.getQtysEffective().getUomQty().toBigDecimal()).isEqualByComparingTo("40"); // delivered nominal qty
		assertThat(toInvoiceData.getQtysEffective().getUomQty().getUomId()).isEqualTo(DELIVERY_UOM_ID);

		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("10"); // delivered qty in stock UOM
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void afterDelivery_catchWeight_partially_invoiced()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("withCatchWeightPartiallyInvoiced");

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();
		assertThat(toInvoiceData.getQtysEffective().getUomQty().toBigDecimal()).isEqualByComparingTo("25"); // 43 - 18
		assertThat(toInvoiceData.getQtysEffective().getUomQty().getUomId()).isEqualTo(DELIVERY_UOM_ID);

		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("5"); // delivered qty in stock UOM - already invoiced
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}

	@Test
	void afterDelivery_catchWeight_qtyToInvoiceOverride()
	{
		final InvoiceCandidate invoiceCandidate = loadJsonFixture("withCatchWeight_withQtyToInvoiceOverride");

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();
		assertThat(toInvoiceData.getQtysEffective().getUomQty().toBigDecimal()).isEqualByComparingTo("46.8"); // 10/10 of 43 plus 1/5 of 19
		assertThat(toInvoiceData.getQtysEffective().getUomQty().getUomId()).isEqualTo(DELIVERY_UOM_ID);

		assertThat(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal()).isEqualByComparingTo("11"); // qtyToInvoiceOverride
		assertThat(toInvoiceData.getQtysEffective().getStockQty().getUomId()).isEqualTo(STOCK_UOM_ID);
	}
}
