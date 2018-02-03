package de.metas.ui.web.order.sales.purchasePlanning.view;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;

import de.metas.interfaces.I_C_BPartner_Product;
import de.metas.purchasecandidate.AvailabilityCheck.AvailabilityResult;
import de.metas.purchasecandidate.AvailabilityCheck.AvailabilityResult.Type;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.SalesOrderLineWithCandidates;
import de.metas.purchasecandidate.SalesOrderLines;
import de.metas.purchasecandidate.VendorProductInfo;
import mockit.Expectations;
import mockit.Mocked;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class PurchaseRowsLoaderTest
{
	@Mocked
	private SalesOrderLines salesOrderLines;

	private I_M_Product product;
	private I_C_Order order;
	private I_C_BPartner bPartnerVendor;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setUOMSymbol("testUOMSympol");
		save(uom);

		product = newInstance(I_M_Product.class);
		product.setC_UOM(uom);
		save(product);

		order = newInstance(I_C_Order.class);
		save(order);

		bPartnerVendor = newInstance(I_C_BPartner.class);
		bPartnerVendor.setName("bPartnerVendor.Name");
		save(bPartnerVendor);
	}

	@Test
	public void test()
	{

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setM_Product(product);
		orderLine.setC_Order(order);
		orderLine.setDatePromised(SystemTime.asTimestamp());
		save(orderLine);

		final I_C_BPartner_Product bPartnerProduct = newInstance(I_C_BPartner_Product.class);
		bPartnerProduct.setC_BPartner_Vendor(bPartnerVendor);
		bPartnerProduct.setM_Product(product);
		bPartnerProduct.setVendorProductNo("bPartnerProduct.VendorProductNo");
		bPartnerProduct.setProductName("bPartnerProduct.ProductName");
		save(bPartnerProduct);

		final PurchaseCandidate purchaseCandidate = createPurchaseCandidate(orderLine, bPartnerProduct);

		final ImmutableList<SalesOrderLineWithCandidates> salesOrderLinesWithPurchaseCandidates = //
				createSalesOrderLinesWithPurchaseCandidates(orderLine, purchaseCandidate);

		// @formatter:off
		new Expectations()
		{{
			salesOrderLines.getOrderedSalesOrderLines();
			result = salesOrderLinesWithPurchaseCandidates;
		}};	// @formatter:on

		final Multimap<PurchaseCandidate, AvailabilityResult> checkAvailabilityResult = ArrayListMultimap.create();
		checkAvailabilityResult.put(purchaseCandidate, AvailabilityResult.builder()
				.purchaseCandidate(purchaseCandidate)
				.qty(BigDecimal.TEN)
				.type(Type.AVAILABLE).build());

		// @formatter:off
		new Expectations()
		{{
			salesOrderLines.checkAvailability();
			result = checkAvailabilityResult;
		}};	// @formatter:on

		final PurchaseRowsLoader loader = PurchaseRowsLoader.builder()
				.salesOrderLines(salesOrderLines)
				.purchaseRowsFactory(new PurchaseRowFactory())
				.viewSupplier(() -> null)
				.build();

		final List<PurchaseRow> groupRows = loader.load();
		assertThat(groupRows).hasSize(1);
		final PurchaseRow groupRow = groupRows.get(0);
		assertThat(groupRow.getRowType()).isEqualTo(PurchaseRowType.GROUP);
		assertThat(groupRow.getIncludedRows()).hasSize(1);

		final PurchaseRow purchaseRow = groupRow.getIncludedRows().get(0);
		assertThat(purchaseRow.getRowType()).isEqualTo(PurchaseRowType.LINE);
		assertThat(purchaseRow.getIncludedRows()).isEmpty();

		loader.createAndAddAvailabilityResultRows();
		assertThat(purchaseRow.getIncludedRows()).hasSize(1);

		final PurchaseRow availabilityRow = purchaseRow.getIncludedRows().get(0);
		assertThat(availabilityRow.getRowType()).isEqualTo(PurchaseRowType.AVAILABILITY_DETAIL);
		assertThat(availabilityRow.getRowId().toDocumentId()).isNotEqualTo(purchaseRow.getRowId().toDocumentId());
	}

	private static PurchaseCandidate createPurchaseCandidate(
			final I_C_OrderLine orderLine,
			final I_C_BPartner_Product bPartnerProduct)
	{
		final VendorProductInfo vendorProductInfo = VendorProductInfo.fromDataRecord(bPartnerProduct);

		final PurchaseCandidate purchaseCandidate = PurchaseCandidate.builder()
				.orgId(20)
				.datePromised(orderLine.getDatePromised())
				.productId(orderLine.getM_Product_ID())
				.qtyRequired(orderLine.getQtyOrdered())
				.salesOrderId(orderLine.getC_Order_ID())
				.salesOrderLineId(orderLine.getC_OrderLine_ID())
				.uomId(orderLine.getM_Product().getC_UOM_ID())
				.vendorBPartnerId(vendorProductInfo.getVendorBPartnerId())
				.vendorProductInfo(vendorProductInfo)
				.warehouseId(30)
				.build();
		return purchaseCandidate;
	}

	private static ImmutableList<SalesOrderLineWithCandidates> createSalesOrderLinesWithPurchaseCandidates(
			final I_C_OrderLine orderLine,
			final PurchaseCandidate purchaseCandidate)
	{
		final SalesOrderLineWithCandidates salesOrderLineWithPurchaseCandidates //
				= SalesOrderLineWithCandidates.builder()
						.salesOrderLine(orderLine)
						.purchaseCandidate(purchaseCandidate)
						.build();

		final ImmutableList<SalesOrderLineWithCandidates> salesOrderLinesWithPurchaseCandidates //
				= ImmutableList.of(salesOrderLineWithPurchaseCandidates);
		return salesOrderLinesWithPurchaseCandidates;
	}
}
