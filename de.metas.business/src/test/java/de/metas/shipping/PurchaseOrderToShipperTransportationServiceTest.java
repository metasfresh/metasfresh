package de.metas.shipping;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
import de.metas.uom.UomId;
import de.metas.util.Services;

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

		chf = PlainCurrencyDAO.createCurrencyId(CurrencyCode.CHF);

		uom1 = createUOM("UomCode1");

		product1 = createProduct("Product1", uom1);

		product2 = createProduct("Product2", uom1);

		final PurchaseOrderToShipperTransportationRepository repo = new PurchaseOrderToShipperTransportationRepository();
		service = new PurchaseOrderToShipperTransportationService(repo);
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

		final IQueryFilter<de.metas.adempiere.model.I_C_Order> queryFilter = Services.get(IQueryBL.class)
				.createCompositeQueryFilter(de.metas.adempiere.model.I_C_Order.class)
				.addEqualsFilter(I_C_Order.COLUMNNAME_C_Order_ID, order);

		service.addPurchaseOrdersToShipperTransportation(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()), queryFilter);

		final List<I_M_ShippingPackage> shippingPackages = Services.get(IShipperTransportationDAO.class).retrieveShippingPackages(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()));

		assertThat(1).isEqualTo(shippingPackages.size());

		final I_M_ShippingPackage shippingPackage = shippingPackages.get(0);

		assertThat(order.getRepoId()).isEqualTo(shippingPackage.getC_Order_ID());
		assertThat(shippingPackage.isToBeFetched());

		// try to add it again
		service.addPurchaseOrdersToShipperTransportation(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()), queryFilter);

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

		final IQueryFilter<de.metas.adempiere.model.I_C_Order> queryFilter = Services.get(IQueryBL.class)
				.createCompositeQueryFilter(de.metas.adempiere.model.I_C_Order.class)
				.addEqualsFilter(I_C_Order.COLUMNNAME_C_Order_ID, order);

		service.addPurchaseOrdersToShipperTransportation(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()), queryFilter);

		final List<I_M_ShippingPackage> shippingPackages = Services.get(IShipperTransportationDAO.class).retrieveShippingPackages(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()));

		assertThat(1).isEqualTo(shippingPackages.size());

		final I_M_ShippingPackage shippingPackage = shippingPackages.get(0);

		assertThat(order.getRepoId()).isEqualTo(shippingPackage.getC_Order_ID());
		assertThat(shippingPackage.isToBeFetched());

		// try to add it again
		service.addPurchaseOrdersToShipperTransportation(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()), queryFilter);

		// => it was not added again
		assertThat(1).isEqualTo(shippingPackages.size());
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

		final IQueryFilter<de.metas.adempiere.model.I_C_Order> queryFilter = Services.get(IQueryBL.class)
				.createCompositeQueryFilter(de.metas.adempiere.model.I_C_Order.class)
				.addInArrayFilter(I_C_Order.COLUMNNAME_C_Order_ID, order1, order2);

		service.addPurchaseOrdersToShipperTransportation(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()), queryFilter);

		final List<I_M_ShippingPackage> shippingPackages = Services.get(IShipperTransportationDAO.class).retrieveShippingPackages(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()));

		assertThat(2).isEqualTo(shippingPackages.size());
		assertThat(shippingPackages.stream())
				.allMatch((pack -> pack.getC_Order_ID() == order1.getRepoId() || pack.getC_Order_ID() == order2.getRepoId()));

		assertThat(shippingPackages.stream())
				.allMatch(pack -> pack.isToBeFetched());

		// add a new order

		final IQueryFilter<de.metas.adempiere.model.I_C_Order> queryFilter2 = Services.get(IQueryBL.class)
				.createCompositeQueryFilter(de.metas.adempiere.model.I_C_Order.class)
				.addInArrayFilter(I_C_Order.COLUMNNAME_C_Order_ID, order1, order2, order3);

		service.addPurchaseOrdersToShipperTransportation(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()), queryFilter2);

		final List<I_M_ShippingPackage> shippingPackages2 = Services.get(IShipperTransportationDAO.class).retrieveShippingPackages(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()));

		assertThat(3).isEqualTo(shippingPackages2.size());

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
