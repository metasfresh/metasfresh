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
import de.metas.order.OrderLineId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.model.X_M_ShipperTransportation;
import de.metas.sscc18.ISSCC18CodeBL;
import de.metas.sscc18.SSCC18;
import de.metas.sscc18.impl.SSCC18CodeBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_Group;
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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;

import static org.adempiere.model.InterfaceWrapperHelper.delete;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

	/**
	 * When ALL order lines return LU count 0, the service must throw a user-visible error instead of silently adding nothing.
	 */
	@Test
	public void addPurchaseOrderLines_allLinesSkipped_throwsUserError()
	{
		final PurchaseOrderToShipperTransportationService serviceWithZeroLU = PurchaseOrderToShipperTransportationService.newInstanceForUnitTesting((o, ol) -> 0);

		final I_M_ShipperTransportation shipperTransportation = createShipperTransportation();
		final ShipperTransportationId transportationId = ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID());

		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("PartnerSkipAll", "addressSkipAll");
		final OrderId orderId = createOrder(bpartnerAndLocation);

		createOrderLine(
				orderId,
				StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1),
				Money.of(10, chf)
		);

		assertThatThrownBy(() -> serviceWithZeroLU.addPurchaseOrdersToShipperTransportation(
				transportationId,
				Collections.singletonList(orderId)))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> {
					final AdempiereException adEx = (AdempiereException)ex;
					assertThat(adEx.getErrorCode())
							.as("Exception must carry the expected AD_Message key as error code")
							.isEqualTo(PurchaseOrderToShipperTransportationService.MSG_NoLUPackingConfigForOrderLines.toAD_Message());
					assertThat(adEx.isUserValidationError())
							.as("Exception must be marked as user-validation error so the UI shows it as a user message")
							.isTrue();
				});
	}

	/**
	 * When SOME order lines return LU count 0 and others return > 0, the service must warn and add the rest — no exception.
	 */
	@Test
	public void addPurchaseOrderLines_someLinesSkipped_warnsAndAddsRest()
	{
		final PurchaseOrderToShipperTransportationService serviceWithPartialLU = PurchaseOrderToShipperTransportationService.newInstanceForUnitTesting(
				(order, orderLine) -> orderLine.getM_Product_ID() == product1.getRepoId() ? 0 : 1);

		final I_M_ShipperTransportation shipperTransportation = createShipperTransportation();
		final ShipperTransportationId transportationId = ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID());

		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("PartnerSkipSome", "addressSkipSome");
		final OrderId orderId = createOrder(bpartnerAndLocation);

		createOrderLine(
				orderId,
				StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1),
				Money.of(10, chf)
		);
		final I_C_OrderLine line2 = createOrderLine(
				orderId,
				StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(3), product2, uom1),
				Money.of(15, chf)
		);

		// Must not throw — some lines were added successfully
		serviceWithPartialLU.addPurchaseOrdersToShipperTransportation(
				transportationId,
				Collections.singletonList(orderId));

		final List<I_M_ShippingPackage> shippingPackages = Services.get(IShipperTransportationDAO.class)
				.retrieveShippingPackages(transportationId);

		// Exactly 1 package for product2 (line2); product1 (line1) was skipped
		assertThat(shippingPackages).hasSize(1);
		assertThat(shippingPackages.get(0).getC_OrderLine_ID())
				.as("Only line2 (product2) should produce a shipping package")
				.isEqualTo(line2.getC_OrderLine_ID());
	}

	/**
	 * The five transport-order date fields (ETD, ETA, ATD, ATA, B/L date) must auto-populate from the first assigned purchase order:
	 * ETA = PO.DatePromised, ETD = PO.PreparationDate (taken as already calculated on the order), ATD = ETD, ATA = ETA, B/L date = ATD.
	 * <p>
	 * me03 https://github.com/metasfresh/me03/issues/30956
	 */
	@Test
	public void defaultDates_appliedFromFirstPurchaseOrder()
	{
		final I_M_ShipperTransportation shipperTransportation = createShipperTransportation();
		final ShipperTransportationId transportationId = ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID());

		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("VendorDefaults", "addressDefaults");
		final OrderId orderId = createOrder(bpartnerAndLocation);

		createOrderLine(orderId, StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1), Money.of(10, chf));

		// the PO already carries its provisioning date (PreparationDate) as computed elsewhere; ETD is taken straight from it
		final I_C_Order order = load(orderId, I_C_Order.class);
		final Timestamp preparationDate = TimeUtil.asTimestamp(LocalDate.of(2019, 6, 1), orgDAO.getTimeZone(OrgId.ofRepoId(order.getAD_Org_ID())));
		order.setPreparationDate(preparationDate);
		save(order);

		service.addPurchaseOrdersToShipperTransportation(transportationId, Collections.singletonList(orderId));

		final Timestamp expectedEta = order.getDatePromised(); // ETA = promised arrival date
		final Timestamp expectedEtd = preparationDate;         // ETD = PO's PreparationDate (taken as-is)

		final I_M_ShipperTransportation reloaded = load(transportationId, I_M_ShipperTransportation.class);
		assertThat(reloaded.getETD()).as("ETD = PO.PreparationDate (taken as already calculated on the order)").isEqualTo(expectedEtd);
		assertThat(reloaded.getETA()).as("ETA = PO.DatePromised (promised arrival)").isEqualTo(expectedEta);
		assertThat(reloaded.getATD()).as("ATD = ETD").isEqualTo(expectedEtd);
		assertThat(reloaded.getATA()).as("ATA = ETA").isEqualTo(expectedEta);
		assertThat(reloaded.getBLDate()).as("B/L date = ATD").isEqualTo(expectedEtd);
	}

	/**
	 * ETD is taken from the purchase order's {@code PreparationDate} (its provisioning date, as already calculated on the order),
	 * independently of the {@code DatePromised} used for ETA.
	 */
	@Test
	public void defaultDates_etdUsesOrderPreparationDate()
	{
		final I_M_ShipperTransportation shipperTransportation = createShipperTransportation();
		final ShipperTransportationId transportationId = ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID());

		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("VendorPrep", "addressPrep");
		final OrderId orderId = createOrder(bpartnerAndLocation);
		createOrderLine(orderId, StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1), Money.of(10, chf));

		final I_C_Order order = load(orderId, I_C_Order.class);
		final Timestamp datePromised = TimeUtil.asTimestamp(LocalDate.of(2025, 3, 10), orgDAO.getTimeZone(OrgId.ofRepoId(order.getAD_Org_ID())));
		final Timestamp preparationDate = TimeUtil.asTimestamp(LocalDate.of(2025, 3, 1), orgDAO.getTimeZone(OrgId.ofRepoId(order.getAD_Org_ID())));
		order.setDatePromised(datePromised);
		order.setPreparationDate(preparationDate);
		save(order);

		service.addPurchaseOrdersToShipperTransportation(transportationId, Collections.singletonList(orderId));

		final I_M_ShipperTransportation reloaded = load(transportationId, I_M_ShipperTransportation.class);
		assertThat(reloaded.getETD()).as("ETD = the PO's PreparationDate").isEqualTo(preparationDate);
		assertThat(reloaded.getETA()).as("ETA = PO.DatePromised").isEqualTo(datePromised);
	}

	/**
	 * When the purchase order carries no {@code PreparationDate}, ETD (and the ATD/B-L date that cascade from it) are left unset;
	 * ETA is still defaulted from the PO's {@code DatePromised}.
	 */
	@Test
	public void defaultDates_noPreparationDate_etdLeftUnset()
	{
		final I_M_ShipperTransportation shipperTransportation = createShipperTransportation();
		final ShipperTransportationId transportationId = ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID());

		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("VendorNoPrep", "addressNoPrep");
		final OrderId orderId = createOrder(bpartnerAndLocation);
		createOrderLine(orderId, StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1), Money.of(10, chf));

		service.addPurchaseOrdersToShipperTransportation(transportationId, Collections.singletonList(orderId));

		final Timestamp expectedEta = load(orderId, I_C_Order.class).getDatePromised();
		final I_M_ShipperTransportation reloaded = load(transportationId, I_M_ShipperTransportation.class);
		assertThat(reloaded.getETD()).as("ETD left unset when the PO carries no PreparationDate").isNull();
		assertThat(reloaded.getATD()).as("ATD cascades from ETD, so also unset").isNull();
		assertThat(reloaded.getBLDate()).as("B/L date cascades from ATD, so also unset").isNull();
		assertThat(reloaded.getETA()).as("ETA still defaulted from PO.DatePromised").isEqualTo(expectedEta);
		assertThat(reloaded.getATA()).as("ATA = ETA").isEqualTo(expectedEta);
	}

	/**
	 * Only the FIRST assigned purchase order drives the defaults; assigning further orders (or re-editing) must not overwrite the values.
	 */
	@Test
	public void defaultDates_onlyFirstOrderWins()
	{
		final I_M_ShipperTransportation shipperTransportation = createShipperTransportation();
		final ShipperTransportationId transportationId = ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID());

		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("VendorFirstWins", "addressFirstWins");

		final OrderId order1 = createOrder(bpartnerAndLocation);
		createOrderLine(order1, StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1), Money.of(10, chf));
		final I_C_Order order1Record = load(order1, I_C_Order.class);
		final Timestamp firstOrderPreparationDate = TimeUtil.asTimestamp(LocalDate.of(2019, 5, 5), orgDAO.getTimeZone(OrgId.ofRepoId(order1Record.getAD_Org_ID())));
		order1Record.setPreparationDate(firstOrderPreparationDate);
		save(order1Record);

		// second order with a DIFFERENT PreparationDate
		final OrderId order2 = createOrder(bpartnerAndLocation);
		createOrderLine(order2, StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1), Money.of(10, chf));
		final I_C_Order order2Record = load(order2, I_C_Order.class);
		order2Record.setPreparationDate(TimeUtil.asTimestamp(LocalDate.of(2020, 1, 1), orgDAO.getTimeZone(OrgId.ofRepoId(order2Record.getAD_Org_ID()))));
		save(order2Record);

		// assign order1 first, then order2
		service.addPurchaseOrdersToShipperTransportation(transportationId, Collections.singletonList(order1));
		service.addPurchaseOrdersToShipperTransportation(transportationId, Collections.singletonList(order2));

		final I_M_ShipperTransportation reloaded = load(transportationId, I_M_ShipperTransportation.class);
		assertThat(reloaded.getETD())
				.as("ETD stays derived from the FIRST assigned order, not the second")
				.isEqualTo(firstOrderPreparationDate);
	}

	/**
	 * Regression-safety: the auto-defaulting must NOT run for a sales (Outgoing direction) transport order, so the existing sales flow is untouched.
	 */
	@Test
	public void defaultDates_notAppliedForSalesTransportOrder()
	{
		final I_M_ShipperTransportation shipperTransportation = createShipperTransportation();
		shipperTransportation.setTransportDirection(X_M_ShipperTransportation.TRANSPORTDIRECTION_Outgoing);
		save(shipperTransportation);
		final ShipperTransportationId transportationId = ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID());

		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("VendorSales", "addressSales");
		final OrderId orderId = createOrder(bpartnerAndLocation);
		createOrderLine(orderId, StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1), Money.of(10, chf));

		// give the PO a PreparationDate so a PURCHASE transport order would get a non-null ETD — proving the sales guard is what suppresses it
		final I_C_Order order = load(orderId, I_C_Order.class);
		order.setPreparationDate(TimeUtil.asTimestamp(LocalDate.of(2019, 6, 1), orgDAO.getTimeZone(OrgId.ofRepoId(order.getAD_Org_ID()))));
		save(order);

		service.addPurchaseOrdersToShipperTransportation(transportationId, Collections.singletonList(orderId));

		final I_M_ShipperTransportation reloaded = load(transportationId, I_M_ShipperTransportation.class);
		assertThat(reloaded.getETD()).as("ETD must not be defaulted on a sales transport order").isNull();
		assertThat(reloaded.getETA()).isNull();
		assertThat(reloaded.getATD()).isNull();
		assertThat(reloaded.getATA()).isNull();
		assertThat(reloaded.getBLDate()).isNull();
	}

	/**
	 * Assigning via {@code addOrderLinesToShipperTransportation} (which passes only a SELECTED subset of the PO's lines): ETD is
	 * still taken from the whole purchase order's {@code PreparationDate}, independent of which line subset is assigned.
	 */
	@Test
	public void defaultDates_viaAddOrderLines_usesPurchaseOrderPreparationDate()
	{
		final I_M_ShipperTransportation shipperTransportation = createShipperTransportation();
		final ShipperTransportationId transportationId = ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID());

		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("VendorSubset", "addressSubset");
		final OrderId orderId = createOrder(bpartnerAndLocation);

		final I_C_OrderLine line1 = createOrderLine(orderId, StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1), Money.of(10, chf));
		line1.setLine(10);
		save(line1);
		final I_C_OrderLine line2 = createOrderLine(orderId, StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product2, uom1), Money.of(10, chf));
		line2.setLine(20);
		save(line2);

		final I_C_Order order = load(orderId, I_C_Order.class);
		final Timestamp preparationDate = TimeUtil.asTimestamp(LocalDate.of(2019, 6, 1), orgDAO.getTimeZone(OrgId.ofRepoId(order.getAD_Org_ID())));
		order.setPreparationDate(preparationDate);
		save(order);

		// assign ONLY the second line — ETD must still come from the whole PO's PreparationDate
		service.addOrderLinesToShipperTransportation(transportationId, ImmutableSet.of(OrderLineId.ofRepoId(line2.getC_OrderLine_ID())));

		final Timestamp expectedEta = order.getDatePromised();

		final I_M_ShipperTransportation reloaded = load(transportationId, I_M_ShipperTransportation.class);
		assertThat(reloaded.getETA()).isEqualTo(expectedEta);
		assertThat(reloaded.getETD())
				.as("ETD taken from the PO's PreparationDate, independent of the assigned line subset")
				.isEqualTo(preparationDate);
	}

	/**
	 * Defaults are fill-only-if-unset: a date the user entered BEFORE the first PO was assigned is kept, while the remaining
	 * unset fields are still populated from the PO.
	 */
	@Test
	public void defaultDates_keepUserPresetField_fillOnlyUnset()
	{
		final I_M_ShipperTransportation shipperTransportation = createShipperTransportation();
		final ShipperTransportationId transportationId = ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID());

		final Timestamp presetEtd = TimeUtil.asTimestamp(LocalDate.of(2021, 5, 5), orgDAO.getTimeZone(OrgId.ofRepoId(shipperTransportation.getAD_Org_ID())));
		shipperTransportation.setETD(presetEtd);
		save(shipperTransportation);

		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("VendorPreset", "addressPreset");
		final OrderId orderId = createOrder(bpartnerAndLocation);
		createOrderLine(orderId, StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1), Money.of(10, chf));

		final I_C_Order order = load(orderId, I_C_Order.class);

		service.addPurchaseOrdersToShipperTransportation(transportationId, Collections.singletonList(orderId));

		final Timestamp expectedEta = order.getDatePromised(); // ETA = promised arrival date

		final I_M_ShipperTransportation reloaded = load(transportationId, I_M_ShipperTransportation.class);
		assertThat(reloaded.getETD()).as("pre-set ETD is kept, not overwritten").isEqualTo(presetEtd);
		assertThat(reloaded.getETA()).as("unset ETA still filled from the PO default (= DatePromised)").isEqualTo(expectedEta);
		assertThat(reloaded.getATD()).as("ATD = ETD, so it follows the user-preset ETD, not the PO's DatePromised").isEqualTo(presetEtd);
		assertThat(reloaded.getATA()).as("ATA = ETA").isEqualTo(expectedEta);
		assertThat(reloaded.getBLDate()).as("B/L date = ATD, which here equals the user-preset ETD").isEqualTo(presetEtd);
	}

	/**
	 * B/L date follows ATD (not ETD): when the user pre-sets ATD to a value different from ETD and leaves ETD/BLDate unset,
	 * the B/L date default must be the (kept) ATD, not the PO-derived ETD.
	 */
	@Test
	public void defaultDates_blDateFollowsPresetAtd_notEtd()
	{
		final I_M_ShipperTransportation shipperTransportation = createShipperTransportation();
		final ShipperTransportationId transportationId = ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID());

		// user pre-sets ATD only; ETD, ETA, ATA and B/L date are left unset
		final Timestamp presetAtd = TimeUtil.asTimestamp(LocalDate.of(2025, 1, 15), orgDAO.getTimeZone(OrgId.ofRepoId(shipperTransportation.getAD_Org_ID())));
		shipperTransportation.setATD(presetAtd);
		save(shipperTransportation);

		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("VendorBlAtd", "addressBlAtd");
		final OrderId orderId = createOrder(bpartnerAndLocation);
		createOrderLine(orderId, StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1), Money.of(10, chf));

		// the PO's PreparationDate is deliberately DIFFERENT from the preset ATD, so ETD (= PO's PreparationDate) and ATD (= preset) diverge
		final I_C_Order order = load(orderId, I_C_Order.class);
		final Timestamp poPreparationDate = TimeUtil.asTimestamp(LocalDate.of(2025, 2, 1), orgDAO.getTimeZone(OrgId.ofRepoId(order.getAD_Org_ID())));
		order.setPreparationDate(poPreparationDate);
		save(order);

		service.addPurchaseOrdersToShipperTransportation(transportationId, Collections.singletonList(orderId));

		final I_M_ShipperTransportation reloaded = load(transportationId, I_M_ShipperTransportation.class);
		assertThat(reloaded.getETD()).as("unset ETD is filled from the PO's PreparationDate").isEqualTo(poPreparationDate);
		assertThat(reloaded.getATD()).as("pre-set ATD is kept, not overwritten").isEqualTo(presetAtd);
		assertThat(reloaded.getBLDate())
				.as("B/L date = ATD (the kept preset), NOT the PO-derived ETD")
				.isEqualTo(presetAtd)
				.isNotEqualTo(poPreparationDate);
	}

	/**
	 * When several purchase orders are assigned in a SINGLE call, the "first order" that seeds the default dates must be the one
	 * with the EARLIEST {@code DatePromised} (ties broken by {@code C_Order_ID}), regardless of the encounter order of the passed
	 * collection or of the C_Order_IDs. Here the earliest-promised order is deliberately NOT the lowest-id one.
	 */
	@Test
	public void defaultDates_batchAssignment_firstIsEarliestPreparationDate()
	{
		final I_M_ShipperTransportation shipperTransportation = createShipperTransportation();
		final ShipperTransportationId transportationId = ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID());

		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("VendorBatch", "addressBatch");

		// order1: LOWEST C_Order_ID, and neither the earliest PreparationDate nor the earliest DatePromised => must NOT seed the dates
		final OrderId order1 = createOrder(bpartnerAndLocation);
		createOrderLine(order1, StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1), Money.of(10, chf));
		final I_C_Order order1Record = load(order1, I_C_Order.class);
		order1Record.setDatePromised(TimeUtil.asTimestamp(LocalDate.of(2021, 3, 3), orgDAO.getTimeZone(OrgId.ofRepoId(order1Record.getAD_Org_ID()))));
		order1Record.setPreparationDate(TimeUtil.asTimestamp(LocalDate.of(2019, 1, 1), orgDAO.getTimeZone(OrgId.ofRepoId(order1Record.getAD_Org_ID()))));
		save(order1Record);

		final OrderId order2 = createOrder(bpartnerAndLocation);
		createOrderLine(order2, StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1), Money.of(10, chf));
		final I_C_Order order2Record = load(order2, I_C_Order.class);
		order2Record.setDatePromised(TimeUtil.asTimestamp(LocalDate.of(2020, 2, 2), orgDAO.getTimeZone(OrgId.ofRepoId(order2Record.getAD_Org_ID()))));
		order2Record.setPreparationDate(TimeUtil.asTimestamp(LocalDate.of(2019, 2, 2), orgDAO.getTimeZone(OrgId.ofRepoId(order2Record.getAD_Org_ID()))));
		save(order2Record);

		// order3: HIGHEST C_Order_ID and the LATEST DatePromised, but the EARLIEST PreparationDate => it must seed the dates.
		// The three dates are deliberately crossed: sorting by DatePromised would pick order2, so this fixture fails unless the
		// comparator sorts on PreparationDate - the same field the seeded ETD is read from.
		final OrderId order3 = createOrder(bpartnerAndLocation);
		createOrderLine(order3, StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1), Money.of(10, chf));
		final I_C_Order order3Record = load(order3, I_C_Order.class);
		order3Record.setDatePromised(TimeUtil.asTimestamp(LocalDate.of(2022, 1, 1), orgDAO.getTimeZone(OrgId.ofRepoId(order3Record.getAD_Org_ID()))));
		final Timestamp order3PreparationDate = TimeUtil.asTimestamp(LocalDate.of(2018, 12, 1), orgDAO.getTimeZone(OrgId.ofRepoId(order3Record.getAD_Org_ID())));
		order3Record.setPreparationDate(order3PreparationDate);
		save(order3Record);

		// sanity: order3 has the HIGHEST C_Order_ID and the LATEST DatePromised, yet the earliest PreparationDate
		assertThat(order1.getRepoId()).isLessThan(order2.getRepoId()).isLessThan(order3.getRepoId());

		// assign all three at once, in a collection order that does NOT start with order3
		service.addPurchaseOrdersToShipperTransportation(transportationId, ImmutableSet.of(order3, order2, order1));

		final I_M_ShipperTransportation reloaded = load(transportationId, I_M_ShipperTransportation.class);
		assertThat(reloaded.getETD())
				.as("ETD is seeded by the earliest-PreparationDate order (order3), not the earliest DatePromised, the lowest C_Order_ID or the collection order")
				.isEqualTo(order3PreparationDate);
	}

	/**
	 * With NO order carrying a {@code PreparationDate}, the departure key cannot decide and every order lands in the nulls-last
	 * bucket - so {@code DatePromised} decides, not {@code C_Order_ID}. It still decides a real value: ETD stays unset (no order
	 * has a ready date), but ETA is seeded from the winning order's {@code DatePromised}, and picking that by insertion order
	 * would be arbitrary while the promised dates are right there.
	 */
	@Test
	public void defaultDates_allPreparationDatesNull_earliestDatePromisedWinsOverOrderId()
	{
		final I_M_ShipperTransportation shipperTransportation = createShipperTransportation();
		final ShipperTransportationId transportationId = ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID());

		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("VendorNullPrep", "addressNullPrep");

		// order1: LOWEST C_Order_ID and the LATER DatePromised => must NOT seed the dates
		final OrderId order1 = createOrder(bpartnerAndLocation);
		createOrderLine(order1, StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1), Money.of(10, chf));
		final I_C_Order order1Record = load(order1, I_C_Order.class);
		order1Record.setDatePromised(TimeUtil.asTimestamp(LocalDate.of(2021, 3, 3), orgDAO.getTimeZone(OrgId.ofRepoId(order1Record.getAD_Org_ID()))));
		order1Record.setPreparationDate(null);
		save(order1Record);

		// order2: HIGHER C_Order_ID but the EARLIER DatePromised => it must seed the dates
		final OrderId order2 = createOrder(bpartnerAndLocation);
		createOrderLine(order2, StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1), Money.of(10, chf));
		final I_C_Order order2Record = load(order2, I_C_Order.class);
		final Timestamp order2DatePromised = TimeUtil.asTimestamp(LocalDate.of(2019, 1, 1), orgDAO.getTimeZone(OrgId.ofRepoId(order2Record.getAD_Org_ID())));
		order2Record.setDatePromised(order2DatePromised);
		order2Record.setPreparationDate(null);
		save(order2Record);

		// sanity: order2 has the HIGHER C_Order_ID yet the earlier DatePromised, and neither has a PreparationDate
		assertThat(order1.getRepoId()).isLessThan(order2.getRepoId());
		assertThat(load(order1, I_C_Order.class).getPreparationDate()).isNull();
		assertThat(load(order2, I_C_Order.class).getPreparationDate()).isNull();

		service.addPurchaseOrdersToShipperTransportation(transportationId, ImmutableSet.of(order1, order2));

		final I_M_ShipperTransportation reloaded = load(transportationId, I_M_ShipperTransportation.class);
		assertThat(reloaded.getETA())
				.as("with every PreparationDate null the seeding order falls to DatePromised, not to the lowest C_Order_ID")
				.isEqualTo(order2DatePromised);
		assertThat(reloaded.getETD())
				.as("no order has a PreparationDate, so ETD stays unset")
				.isNull();
	}

	/**
	 * Same determinism contract as {@link #defaultDates_batchAssignment_firstIsEarliestPreparationDate()} but driven through
	 * {@code addOrderLinesToShipperTransportation} (the line-level entry point): when a single call carries lines from several
	 * purchase orders, the "first order" that seeds the default dates must be the one with the EARLIEST {@code PreparationDate}
	 * (ties broken by {@code C_Order_ID}), regardless of the encounter order of the passed line-id set.
	 */
	@Test
	public void defaultDates_viaAddOrderLines_batchAssignment_firstIsEarliestPreparationDate()
	{
		final I_M_ShipperTransportation shipperTransportation = createShipperTransportation();
		final ShipperTransportationId transportationId = ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID());

		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("VendorBatchLines", "addressBatchLines");

		// order1: LOWEST C_Order_ID, and neither the earliest PreparationDate nor the earliest DatePromised => must NOT seed the dates
		final OrderId order1 = createOrder(bpartnerAndLocation);
		final I_C_OrderLine line1 = createOrderLine(order1, StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1), Money.of(10, chf));
		final I_C_Order order1Record = load(order1, I_C_Order.class);
		order1Record.setDatePromised(TimeUtil.asTimestamp(LocalDate.of(2021, 3, 3), orgDAO.getTimeZone(OrgId.ofRepoId(order1Record.getAD_Org_ID()))));
		order1Record.setPreparationDate(TimeUtil.asTimestamp(LocalDate.of(2019, 1, 1), orgDAO.getTimeZone(OrgId.ofRepoId(order1Record.getAD_Org_ID()))));
		save(order1Record);

		final OrderId order2 = createOrder(bpartnerAndLocation);
		final I_C_OrderLine line2 = createOrderLine(order2, StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1), Money.of(10, chf));
		final I_C_Order order2Record = load(order2, I_C_Order.class);
		order2Record.setDatePromised(TimeUtil.asTimestamp(LocalDate.of(2020, 2, 2), orgDAO.getTimeZone(OrgId.ofRepoId(order2Record.getAD_Org_ID()))));
		order2Record.setPreparationDate(TimeUtil.asTimestamp(LocalDate.of(2019, 2, 2), orgDAO.getTimeZone(OrgId.ofRepoId(order2Record.getAD_Org_ID()))));
		save(order2Record);

		// order3: HIGHEST C_Order_ID and the LATEST DatePromised, but the EARLIEST PreparationDate => it must seed the dates.
		// The three dates are deliberately crossed: sorting by DatePromised would pick order2, so this fixture fails unless the
		// comparator sorts on PreparationDate - the same field the seeded ETD is read from.
		final OrderId order3 = createOrder(bpartnerAndLocation);
		final I_C_OrderLine line3 = createOrderLine(order3, StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1), Money.of(10, chf));
		final I_C_Order order3Record = load(order3, I_C_Order.class);
		order3Record.setDatePromised(TimeUtil.asTimestamp(LocalDate.of(2022, 1, 1), orgDAO.getTimeZone(OrgId.ofRepoId(order3Record.getAD_Org_ID()))));
		final Timestamp order3PreparationDate = TimeUtil.asTimestamp(LocalDate.of(2018, 12, 1), orgDAO.getTimeZone(OrgId.ofRepoId(order3Record.getAD_Org_ID())));
		order3Record.setPreparationDate(order3PreparationDate);
		save(order3Record);

		// sanity: order3 has the HIGHEST C_Order_ID and the LATEST DatePromised, yet the earliest PreparationDate
		assertThat(order1.getRepoId()).isLessThan(order2.getRepoId()).isLessThan(order3.getRepoId());

		// assign one line from each order at once, in a set order that does NOT start with order3's line
		service.addOrderLinesToShipperTransportation(transportationId, ImmutableSet.of(
				OrderLineId.ofRepoId(line3.getC_OrderLine_ID()),
				OrderLineId.ofRepoId(line2.getC_OrderLine_ID()),
				OrderLineId.ofRepoId(line1.getC_OrderLine_ID())));

		final I_M_ShipperTransportation reloaded = load(transportationId, I_M_ShipperTransportation.class);
		assertThat(reloaded.getETD())
				.as("ETD is seeded by the earliest-PreparationDate order (order3), not the earliest DatePromised, the lowest C_Order_ID or the line-id set order")
				.isEqualTo(order3PreparationDate);
	}

	private I_M_ShipperTransportation createShipperTransportation()
	{
		final I_M_Shipper shipper = createShipper();

		final I_M_ShipperTransportation shipperTransportation = newInstance(I_M_ShipperTransportation.class);
		shipperTransportation.setM_Shipper_ID(shipper.getM_Shipper_ID());

		shipperTransportation.setDateDoc(TimeUtil.asTimestamp(LocalDate.of(2019, 6, 6), orgDAO.getTimeZone(OrgId.ofRepoId(shipper.getAD_Org_ID()))));
		shipperTransportation.setTransportDirection(X_M_ShipperTransportation.TRANSPORTDIRECTION_Incoming); // purchase (inbound) transport order

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
		final I_C_BP_Group bpGroup = newInstance(I_C_BP_Group.class);
		save(bpGroup);

		final I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
		bpartnerRecord.setName(partnerName);
		bpartnerRecord.setC_BP_Group_ID(bpGroup.getC_BP_Group_ID());
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
