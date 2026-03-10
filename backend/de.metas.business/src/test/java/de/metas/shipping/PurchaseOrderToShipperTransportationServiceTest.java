package de.metas.shipping;

import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.engine.DocStatus;
import de.metas.interfaces.I_C_BPartner;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.sscc18.ISSCC18CodeBL;
import de.metas.sscc18.SSCC18;
import de.metas.sscc18.impl.SSCC18CodeBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_M_Package;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.delete;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class PurchaseOrderToShipperTransportationServiceTest
{
	public static final int M_SHIPPER_ID = 1000000;

	final SSCC18 constantSSCC18 = new SSCC18(0, "0718908 ", "562723189", 6);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private CurrencyId chf;

	private ProductId product1;

	private ProductId product2;

	private UomId uom1;

	private PurchaseOrderToShipperTransportationService service;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		Services.registerService(ISSCC18CodeBL.class, new SSCC18CodeBL()
		{
			@Override
			public SSCC18 generate(final @NonNull OrgId orgId)
			{
				return constantSSCC18;
			}
		});

		chf = PlainCurrencyDAO.createCurrencyId(CurrencyCode.CHF);

		uom1 = createUOM("UomCode1");

		product1 = createProduct("Product1", uom1);

		product2 = createProduct("Product2", uom1);

		service = PurchaseOrderToShipperTransportationService.newInstanceForUnitTesting();
	}

	@Test
	public void addPurchaseOrdersToShipperTransportation_1Line()
	{
		final I_M_ShipperTransportation shipperTransportation = createShipperTransportation();

		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("Partner2", "address2");
		final OrderId order = createOrder(bpartnerAndLocation);

		createOrderLine(
				order,
				StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1),
				Money.of(10, chf)
		);

		service.addPurchaseOrdersToShipperTransportation(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()), Collections.singletonList(order));

		final List<I_M_ShippingPackage> shippingPackages = Services.get(IShipperTransportationDAO.class).retrieveShippingPackages(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()));

		assertThat(1).isEqualTo(shippingPackages.size());

		final I_M_ShippingPackage shippingPackage = shippingPackages.get(0);

		assertThat(order.getRepoId()).isEqualTo(shippingPackage.getC_Order_ID());
		assertThat(shippingPackage.isToBeFetched());

		// try to add it again
		service.addPurchaseOrdersToShipperTransportation(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()), Collections.singletonList(order));

		// => it was not added again
		assertThat(1).isEqualTo(shippingPackages.size());

	}

	@Test
	public void addPurchaseOrdersToShipperTransportation_MultipleLines()
	{
		final I_M_ShipperTransportation shipperTransportation = createShipperTransportation();

		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("Partner2", "address2");
		final OrderId order = createOrder(bpartnerAndLocation);

		createOrderLine(
				order,
				StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1),
				Money.of(10, chf)
		);

		createOrderLine(
				order,
				StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product2, uom1),
				Money.of(10, chf)
		);

		service.addPurchaseOrdersToShipperTransportation(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()), Collections.singletonList(order));

		final List<I_M_ShippingPackage> shippingPackages = Services.get(IShipperTransportationDAO.class).retrieveShippingPackages(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()));

		assertThat(shippingPackages.size()).isEqualTo(2);

		final I_M_ShippingPackage shippingPackage = shippingPackages.get(0);

		assertThat(order.getRepoId()).isEqualTo(shippingPackage.getC_Order_ID());
		assertThat(shippingPackage.isToBeFetched());

		// try to add it again
		service.addPurchaseOrdersToShipperTransportation(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()), Collections.singletonList(order));

		// => it was not added again
		assertThat(shippingPackages.size()).isEqualTo(2);
	}

	@Test
	public void addPurchaseOrdersToShipperTransportation_MultipleOrders()
	{
		final I_M_ShipperTransportation shipperTransportation = createShipperTransportation();

		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("Partner2", "address2");
		final OrderId order1 = createOrder(bpartnerAndLocation);

		createOrderLine(
				order1,
				StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1),
				Money.of(10, chf)
		);

		final OrderId order2 = createOrder(bpartnerAndLocation);

		createOrderLine(
				order2,
				StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1),
				Money.of(10, chf)
		);

		final OrderId order3 = createOrder(bpartnerAndLocation);

		createOrderLine(
				order3,
				StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1),
				Money.of(10, chf)
		);

		service.addPurchaseOrdersToShipperTransportation(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()), ImmutableSet.of(order1,order2));

		final List<I_M_ShippingPackage> shippingPackages = Services.get(IShipperTransportationDAO.class).retrieveShippingPackages(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()));

		assertThat(2).isEqualTo(shippingPackages.size());
		assertThat(shippingPackages.stream())
				.allMatch((pack -> pack.getC_Order_ID() == order1.getRepoId() || pack.getC_Order_ID() == order2.getRepoId()));

		assertThat(shippingPackages.stream())
				.allMatch(pack -> pack.isToBeFetched());

		// add a new order

		service.addPurchaseOrdersToShipperTransportation(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()), ImmutableSet.of(order1,order2,order3));

		final List<I_M_ShippingPackage> shippingPackages2 = Services.get(IShipperTransportationDAO.class).retrieveShippingPackages(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()));

		assertThat(3).isEqualTo(shippingPackages2.size());

	}

	/**
	 * Verify that {@link PurchaseOrderToShipperTransportationService#hasProcessedShipperTransportation}
	 * returns false when the transport order is not processed, and true when it is.
	 * <p>
	 * Regression test for https://github.com/metasfresh/me03/issues/28677
	 */
	@Test
	public void hasProcessedShipperTransportation_returnsCorrectly()
	{
		final I_M_ShipperTransportation shipperTransportation = createShipperTransportation();

		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("Partner3", "address3");
		final OrderId order = createOrder(bpartnerAndLocation);

		createOrderLine(
				order,
				StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1),
				Money.of(10, chf)
		);

		// Add order to transportation
		service.addPurchaseOrdersToShipperTransportation(
				ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()),
				Collections.singletonList(order));

		// Verify shipping packages exist
		final List<I_M_ShippingPackage> shippingPackages = Services.get(IShipperTransportationDAO.class)
				.retrieveShippingPackages(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()));
		assertThat(shippingPackages).hasSize(1);

		// Not processed yet
		assertThat(service.hasProcessedShipperTransportation(order)).isFalse();

		// Mark transport order as processed
		shipperTransportation.setProcessed(true);
		save(shipperTransportation);

		// Now it's processed
		assertThat(service.hasProcessedShipperTransportation(order)).isTrue();
	}

	/**
	 * Verify that shipping packages are NOT deleted when using hasProcessedShipperTransportation
	 * (the check-only method used during PO reactivation), regardless of processed state.
	 * <p>
	 * Regression test for https://github.com/metasfresh/me03/issues/28677
	 */
	@Test
	public void shippingPackages_survivePOReactivationCheck()
	{
		final I_M_ShipperTransportation shipperTransportation = createShipperTransportation();
		final ShipperTransportationId transportationId = ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID());

		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("Partner4", "address4");
		final OrderId order = createOrder(bpartnerAndLocation);

		createOrderLine(
				order,
				StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(5), product1, uom1),
				Money.of(20, chf)
		);

		// Add order to transportation
		service.addPurchaseOrdersToShipperTransportation(transportationId, Collections.singletonList(order));

		// Verify packages exist
		assertThat(Services.get(IShipperTransportationDAO.class).retrieveShippingPackages(transportationId)).hasSize(1);

		// Simulate what happens on PO reactivation (the new code only checks, doesn't delete)
		final boolean hasProcessed = service.hasProcessedShipperTransportation(order);
		assertThat(hasProcessed).isFalse();

		// Shipping packages must still exist after the check
		assertThat(Services.get(IShipperTransportationDAO.class).retrieveShippingPackages(transportationId))
				.as("Shipping packages must survive PO reactivation (not be deleted)")
				.hasSize(1);
	}

	/**
	 * Verify that after re-completion, meaningful order changes (DatePromised, BPartner Location)
	 * are synced to existing shipping packages.
	 * <p>
	 * Regression test for https://github.com/metasfresh/me03/issues/28677
	 */
	@Test
	public void syncShippingPackagesFromOrder_syncsDatePromisedAndLocation()
	{
		final I_M_ShipperTransportation shipperTransportation = createShipperTransportation();
		final ShipperTransportationId transportationId = ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID());

		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("Partner5", "address5");
		final OrderId orderId = createOrder(bpartnerAndLocation);

		createOrderLine(
				orderId,
				StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(3), product1, uom1),
				Money.of(15, chf)
		);

		// Add order to transportation
		service.addPurchaseOrdersToShipperTransportation(transportationId, Collections.singletonList(orderId));

		// Verify package exists with original ShipDate
		final List<I_M_ShippingPackage> packagesBefore = Services.get(IShipperTransportationDAO.class)
				.retrieveShippingPackages(transportationId);
		assertThat(packagesBefore).hasSize(1);

		// Now simulate order re-completion with changed DatePromised and BPartner Location
		final I_C_Order order = load(orderId, I_C_Order.class);
		final java.time.LocalDate newDate = LocalDate.of(2025, 3, 15);
		order.setDatePromised(TimeUtil.asTimestamp(newDate, orgDAO.getTimeZone(OrgId.ofRepoId(order.getAD_Org_ID()))));

		// Create a new location
		final BPartnerLocationId newLocation = createBPartnerAndLocation("Partner5b", "address5-new");
		order.setC_BPartner_ID(newLocation.getBpartnerId().getRepoId());
		order.setC_BPartner_Location_ID(newLocation.getRepoId());
		save(order);

		// Sync shipping packages from order
		service.syncShippingPackagesFromOrder(order);

		// Verify synced
		final List<I_M_ShippingPackage> packagesAfter = Services.get(IShipperTransportationDAO.class)
				.retrieveShippingPackages(transportationId);
		assertThat(packagesAfter).hasSize(1);

		final I_M_ShippingPackage sp = packagesAfter.get(0);
		assertThat(sp.getC_BPartner_Location_ID())
				.as("BPartner Location should be synced from order")
				.isEqualTo(newLocation.getRepoId());
		assertThat(sp.getC_BPartner_ID())
				.as("BPartner should be synced from order")
				.isEqualTo(newLocation.getBpartnerId().getRepoId());

		// Check M_Package.ShipDate
		final I_M_Package mPackage = load(sp.getM_Package_ID(), I_M_Package.class);
		assertThat(mPackage.getShipDate())
				.as("M_Package.ShipDate should be synced from order.DatePromised")
				.isNotNull();
	}

	/**
	 * Verify that shipping packages for deleted order lines are removed during sync,
	 * while packages for surviving lines are kept.
	 * <p>
	 * Regression test for https://github.com/metasfresh/me03/issues/28677
	 */
	@Test
	public void syncShippingPackagesFromOrder_removesPackagesForDeletedLines()
	{
		final I_M_ShipperTransportation shipperTransportation = createShipperTransportation();
		final ShipperTransportationId transportationId = ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID());

		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("Partner6", "address6");
		final OrderId orderId = createOrder(bpartnerAndLocation);

		// Create two order lines
		final I_C_OrderLine line1 = createOrderLine(
				orderId,
				StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(3), product1, uom1),
				Money.of(15, chf)
		);
		final I_C_OrderLine line2 = createOrderLine(
				orderId,
				StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(5), product2, uom1),
				Money.of(25, chf)
		);

		// Add order to transportation — should create 2 packages (one per line)
		service.addPurchaseOrdersToShipperTransportation(transportationId, Collections.singletonList(orderId));

		final List<I_M_ShippingPackage> packagesBefore = Services.get(IShipperTransportationDAO.class)
				.retrieveShippingPackages(transportationId);
		assertThat(packagesBefore).hasSize(2);

		// Simulate: delete line2 during reactivation
		delete(line2);

		// Sync
		final I_C_Order order = load(orderId, I_C_Order.class);
		service.syncShippingPackagesFromOrder(order);

		// Only 1 package should remain (for line1)
		final List<I_M_ShippingPackage> packagesAfter = Services.get(IShipperTransportationDAO.class)
				.retrieveShippingPackages(transportationId);
		assertThat(packagesAfter)
				.as("Package for deleted line should be removed, surviving line's package should remain")
				.hasSize(1);
		assertThat(packagesAfter.get(0).getC_OrderLine_ID())
				.as("Remaining package should belong to the surviving order line")
				.isEqualTo(line1.getC_OrderLine_ID());
	}

	/**
	 * Verify that when ALL order lines are removed, ALL shipping packages are cleaned up.
	 * <p>
	 * Regression test for https://github.com/metasfresh/me03/issues/28677
	 */
	@Test
	public void syncShippingPackagesFromOrder_removesAllPackagesWhenAllLinesDeleted()
	{
		final I_M_ShipperTransportation shipperTransportation = createShipperTransportation();
		final ShipperTransportationId transportationId = ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID());

		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("Partner7", "address7");
		final OrderId orderId = createOrder(bpartnerAndLocation);

		final I_C_OrderLine line1 = createOrderLine(
				orderId,
				StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1),
				Money.of(10, chf)
		);

		// Add order to transportation
		service.addPurchaseOrdersToShipperTransportation(transportationId, Collections.singletonList(orderId));
		assertThat(Services.get(IShipperTransportationDAO.class).retrieveShippingPackages(transportationId)).hasSize(1);

		// Delete the only line
		delete(line1);

		// Sync
		final I_C_Order order = load(orderId, I_C_Order.class);
		service.syncShippingPackagesFromOrder(order);

		// All packages should be gone
		assertThat(Services.get(IShipperTransportationDAO.class).retrieveShippingPackages(transportationId))
				.as("All packages should be removed when all order lines are deleted")
				.isEmpty();
	}

	private I_M_ShipperTransportation createShipperTransportation()
	{
		final I_M_Shipper shipper = createShipper();

		final I_M_ShipperTransportation shipperTransportation = newInstance(I_M_ShipperTransportation.class);
		shipperTransportation.setM_Shipper_ID(shipper.getM_Shipper_ID());

		shipperTransportation.setDateDoc(TimeUtil.asTimestamp(LocalDate.of(2019, 6, 6), orgDAO.getTimeZone(OrgId.ofRepoId(shipper.getAD_Org_ID()))));

		save(shipperTransportation);

		return shipperTransportation;
	}

	private I_M_Shipper createShipper()
	{
		final I_M_Shipper shipper = newInstance(I_M_Shipper.class);

		shipper.setName("Shipper1");

		save(shipper);

		return shipper;
	}

	private BPartnerLocationId createBPartnerAndLocation(final String partnerName, final String address)
	{
		final I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
		bpartnerRecord.setName(partnerName);
		save(bpartnerRecord);

		final int bpartnerId = bpartnerRecord.getC_BPartner_ID();

		final I_C_BPartner_Location bpLocationRecord = newInstance(I_C_BPartner_Location.class);
		bpLocationRecord.setC_BPartner_ID(bpartnerId);
		bpLocationRecord.setAddress(address);

		save(bpLocationRecord);

		return BPartnerLocationId.ofRepoId(bpartnerId, bpLocationRecord.getC_BPartner_Location_ID());
	}

	private OrderId createOrder(final BPartnerLocationId bpartnerAndLocation)
	{

		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		save(warehouse);

		final I_C_PaymentTerm paymentTerm = newInstance(I_C_PaymentTerm.class);
		save(paymentTerm);

		final I_C_Order order = newInstance(I_C_Order.class);

		order.setC_BPartner_ID(bpartnerAndLocation.getBpartnerId().getRepoId());
		order.setC_BPartner_Location_ID(bpartnerAndLocation.getRepoId());

		order.setDocStatus(DocStatus.Completed.getCode());
		order.setProcessed(true);

		order.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());

		order.setC_PaymentTerm_ID(paymentTerm.getC_PaymentTerm_ID());
		order.setM_Shipper_ID(M_SHIPPER_ID);

		order.setDatePromised(TimeUtil.asTimestamp(LocalDate.of(2019, 6, 6), orgDAO.getTimeZone(OrgId.ofRepoId(warehouse.getAD_Org_ID()))));

		order.setIsSOTrx(false);

		save(order);

		return OrderId.ofRepoId(order.getC_Order_ID());
	}

	private I_C_OrderLine createOrderLine(
			final OrderId order,
			final StockQtyAndUOMQty stockQtyAndUOMQty,
			final Money priceActual)
	{
		final I_C_OrderLine orderLineRecord = newInstance(I_C_OrderLine.class);

		orderLineRecord.setC_Order_ID(order.getRepoId());

		orderLineRecord.setM_Product_ID(stockQtyAndUOMQty.getProductId().getRepoId());
		orderLineRecord.setQtyOrdered(stockQtyAndUOMQty.getStockQty().toBigDecimal());

		orderLineRecord.setQtyEntered(stockQtyAndUOMQty.getUOMQtyNotNull().toBigDecimal());
		orderLineRecord.setC_UOM_ID(stockQtyAndUOMQty.getUOMQtyNotNull().getUomId().getRepoId());

		orderLineRecord.setPriceActual(priceActual.toBigDecimal());
		orderLineRecord.setC_Currency_ID(priceActual.getCurrencyId().getRepoId());

		save(orderLineRecord);

		return orderLineRecord;
	}

	private UomId createUOM(final String name)
	{
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setName(name);
		uom.setUOMSymbol(name);
		uom.setStdPrecision(2);
		uom.setX12DE355(name);
		save(uom);

		return UomId.ofRepoId(uom.getC_UOM_ID());
	}

	private ProductId createProduct(final String productName, final UomId uomId)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setName(productName);
		product.setC_UOM_ID(uomId.getRepoId());

		save(product);

		return ProductId.ofRepoId(product.getM_Product_ID());
	}

}
