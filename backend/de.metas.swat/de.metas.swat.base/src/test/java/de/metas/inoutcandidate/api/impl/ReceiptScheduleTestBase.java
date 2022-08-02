package de.metas.inoutcandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.acct.api.IProductAcctDAO;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.business.BusinessTestHelper;
import de.metas.common.util.time.SystemTime;
import de.metas.document.dimension.DimensionFactory;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.InOutLineDimensionFactory;
import de.metas.document.dimension.OrderLineDimensionFactory;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.document.dimension.ReceiptScheduleDimensionFactory;
import de.metas.inoutcandidate.filter.GenerateReceiptScheduleForModelAggregateFilter;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.modelvalidator.InOutCandidateValidator;
import de.metas.inoutcandidate.modelvalidator.ReceiptScheduleValidator;
import de.metas.interfaces.I_C_DocType;
import de.metas.logging.LogManager;
import de.metas.order.impl.OrderEmailPropagationSysConfigRepository;
import de.metas.order.location.adapter.OrderLineDocumentLocationAdapterFactory;
import de.metas.organization.OrgId;
import de.metas.product.acct.api.ActivityId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@ExtendWith(AdempiereTestWatcher.class)
public abstract class ReceiptScheduleTestBase
{
	@BeforeAll
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();

		final List<DimensionFactory<?>> dimensionFactories = new ArrayList<>();
		dimensionFactories.add(new ReceiptScheduleDimensionFactory());
		dimensionFactories.add(new InOutLineDimensionFactory());
		SpringContextHolder.registerJUnitBean(new DimensionService(dimensionFactories));
		POJOWrapper.setDefaultStrictValues(false);

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		SpringContextHolder.registerJUnitBean(new OrderEmailPropagationSysConfigRepository(sysConfigBL));


		//
		// Mimic ModelValidator behaviour
		// Services.get(IModelInterceptorRegistry.class).addModelInterceptor(ReceiptScheduleValidator.instance);
		InOutCandidateValidator.registerSSAggregationKeyDependencies();
	}

	protected IReceiptScheduleBL receiptScheduleBL;
	protected IReceiptScheduleDAO receiptScheduleDAO;

	// 07629 just adding to fix existing tests; TODO extend the tests
	// Background: the actual implementation makes a DB test, that's why we use jmockit here
	private IProductAcctDAO productAcctDAO; // 07629

	protected Properties ctx;
	/**
	 * Today (date+time)
	 */
	protected Timestamp date;
	/**
	 * Today (date)
	 */
	protected Timestamp date2;

	// Masterdata
	private OrgId orgId;
	protected BPartnerLocationId bpartner1;
	protected BPartnerLocationId bpartner2;
	protected I_M_Product product1_wh1;
	protected I_M_Product product2_wh1;
	protected I_M_Product product3_wh2;

	protected I_M_Warehouse warehouse1;
	protected I_M_Locator warehouse1_locator1;
	protected I_M_Warehouse warehouse2;
	protected I_M_Locator warehouse2_locator1;

	protected I_C_DocType receiptDocType;

	protected I_C_UOM priceUOM; // 07090

	// #653
	public I_M_Attribute attr_LotNumberDate;
	public I_M_Attribute attr_LotNumber;

	@BeforeEach
	public final void init()
	{
		AdempiereTestHelper.get().init(); // need to init this now

		final ReceiptScheduleProducerFactory receiptScheduleProducerFactory = new ReceiptScheduleProducerFactory(new GenerateReceiptScheduleForModelAggregateFilter(ImmutableList.of()));
		Services.registerService(IReceiptScheduleProducerFactory.class, receiptScheduleProducerFactory);

		// this is already done by HUTestHelper.init()
		// Services.get(IModelInterceptorRegistry.class).addModelInterceptor(ReceiptScheduleValidator.instance);
		ReceiptScheduleValidator.registerRSAggregationKeyDependencies(); // also, for our tests, we just need this, and not the whole MI!

		receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
		receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);

		ctx = Env.getCtx();
		date = SystemTime.asTimestamp();
		date2 = de.metas.common.util.time.SystemTime.asDayTimestamp();
		Env.setContext(ctx, Env.CTXNAME_Date, date2);

		// Master data
		final I_AD_Org org = InterfaceWrapperHelper.create(ctx, I_AD_Org.class, ITrx.TRXNAME_None);
		saveRecord(org);
		orgId = OrgId.ofRepoId(org.getAD_Org_ID());

		bpartner1 = createBPartner("BP1");
		bpartner2 = createBPartner("BP2");

		warehouse1 = createWarehouse("WH1");
		warehouse1_locator1 = createLocator(warehouse1);
		warehouse2 = createWarehouse("WH2");
		warehouse2_locator1 = createLocator(warehouse2);

		final I_C_UOM stockUOMRecord = BusinessTestHelper.createUOM("StockUOM");
		final UomId stockUomId = UomId.ofRepoId(stockUOMRecord.getC_UOM_ID());

		product1_wh1 = createProduct("P1", stockUomId, warehouse1_locator1);
		product2_wh1 = createProduct("P2", stockUomId, warehouse1_locator1);
		product3_wh2 = createProduct("P3", stockUomId, warehouse2_locator1);

		receiptDocType = InterfaceWrapperHelper.create(ctx, I_C_DocType.class, ITrx.TRXNAME_None);
		receiptDocType.setDocBaseType(X_C_DocType.DOCBASETYPE_MaterialReceipt);
		receiptDocType.setAD_Org_ID(0); // same org as the receipt schedules
		saveRecord(receiptDocType);

		priceUOM = InterfaceWrapperHelper.create(ctx, I_C_UOM.class, ITrx.TRXNAME_None);
		priceUOM.setName("priceUOM");
		priceUOM.setAD_Org_ID(0);
		saveRecord(priceUOM);

		// 07629 just adding to fix existing tests; TODO extend the tests
		productAcctDAO = Mockito.spy(IProductAcctDAO.class);
		Services.registerService(IProductAcctDAO.class, productAcctDAO);

		final List<DimensionFactory<?>> dimensionFactories = new ArrayList<>();
		dimensionFactories.add(new OrderLineDimensionFactory());
		dimensionFactories.add(new ReceiptScheduleDimensionFactory());
		dimensionFactories.add(new InOutLineDimensionFactory());

		final DimensionService dimensionService = new DimensionService(dimensionFactories);
		SpringContextHolder.registerJUnitBean(new DimensionService(dimensionFactories));

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		SpringContextHolder.registerJUnitBean(new OrderEmailPropagationSysConfigRepository(sysConfigBL));
		//
		final I_C_Activity activity = InterfaceWrapperHelper.newInstance(I_C_Activity.class, org);
		saveRecord(activity);
		final ActivityId activityId = ActivityId.ofRepoId(activity.getC_Activity_ID());
		Mockito.when(productAcctDAO.retrieveActivityForAcct(
				ArgumentMatchers.any(),
				ArgumentMatchers.eq(orgId),
				ArgumentMatchers.any()))
				.thenReturn(activityId);

		// #653
		attr_LotNumberDate = createM_Attribute(AttributeConstants.ATTR_LotNumberDate.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_Date, true);
		attr_LotNumber = createM_Attribute(AttributeConstants.ATTR_LotNumber.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);

		// Increase log level in case we have some errors
		LogManager.setLoggerLevel(org.adempiere.ad.trx.processor.api.LoggableTrxItemExceptionHandler.class, Level.DEBUG);

		setup();
	}

	protected void setup()
	{
		// nothing to do

	}

	protected Properties getCtx()
	{
		return ctx;
	}

	public BPartnerLocationId createBPartner(final String name)
	{
		final I_C_BPartner bpartner = BusinessTestHelper.createBPartner(name);
		final I_C_BPartner_Location bpLocation = BusinessTestHelper.createBPartnerLocation(bpartner);
		return BPartnerLocationId.ofRepoId(bpLocation.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());
	}

	public I_M_Product createProduct(
			final String productName,
			@NonNull final UomId stockUOMId,
			@Nullable final I_M_Locator locator)
	{
		final I_M_Product product = InterfaceWrapperHelper.create(ctx, I_M_Product.class, ITrx.TRXNAME_None);
		product.setValue(productName);
		product.setName(productName);

		if (locator != null)
		{
			product.setM_Locator_ID(locator.getM_Locator_ID());
		}

		product.setC_UOM_ID(stockUOMId.getRepoId());

		saveRecord(product);
		return product;
	}

	public I_M_Warehouse createWarehouse(final String name)
	{
		final I_M_Warehouse wh = InterfaceWrapperHelper.create(ctx, I_M_Warehouse.class, ITrx.TRXNAME_None);
		wh.setValue(name);
		wh.setName(name);
		wh.setAD_Org_ID(orgId.getRepoId());
		saveRecord(wh);
		return wh;
	}

	protected I_M_Locator createLocator(final I_M_Warehouse warehouse)
	{
		final I_M_Locator locator = InterfaceWrapperHelper.newInstance(I_M_Locator.class, warehouse);
		locator.setAD_Org_ID(warehouse.getAD_Org_ID());
		locator.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		locator.setValue(warehouse.getName() + "_Locator");
		locator.setX("X");
		locator.setY("Y");
		locator.setZ("Z");
		saveRecord(locator);
		return locator;
	}

	protected I_M_ReceiptSchedule createReceiptSchedule(
			final BPartnerLocationId bpartnerLocationId,
			final I_M_Warehouse warehouse,
			final Timestamp date,
			final I_M_Product product, final int qty)
	{
		final BigDecimal qtyBD = BigDecimal.valueOf(qty);

		final I_M_ReceiptSchedule receiptSchedule = InterfaceWrapperHelper.create(ctx, I_M_ReceiptSchedule.class, ITrx.TRXNAME_None);
		receiptSchedule.setAD_Org_ID(0);
		receiptSchedule.setAD_Table_ID(0);

		receiptSchedule.setC_BPartner_ID(bpartnerLocationId.getBpartnerId().getRepoId());
		receiptSchedule.setC_BPartner_Location_ID(bpartnerLocationId.getRepoId());
		receiptSchedule.setAD_User_ID(-1);

		receiptSchedule.setDateOrdered(date);

		receiptSchedule.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());

		receiptSchedule.setM_Product_ID(product.getM_Product_ID());
		// receiptSchedule.setC_UOM(productUOM);

		receiptSchedule.setQtyOrdered(qtyBD);
		receiptSchedule.setQtyToMove(qtyBD);

		final String headerAggregationKey = receiptScheduleBL.getHeaderAggregationKeyBuilder().buildKey(receiptSchedule);
		receiptSchedule.setHeaderAggregationKey(headerAggregationKey);

		saveRecord(receiptSchedule);

		return receiptSchedule;
	}

	protected I_C_Order createOrder()
	{
		final I_M_Warehouse warehouse = null; // no warehouse
		return createOrder(warehouse);
	}

	protected I_C_Order createOrder(@Nullable final I_M_Warehouse warehouse)
	{
		final I_C_Order order = InterfaceWrapperHelper.create(ctx, I_C_Order.class, ITrx.TRXNAME_None);
		order.setC_Order_ID(0);
		order.setAD_Org_ID(warehouse == null ? 0 : warehouse.getAD_Org_ID()); // 07629
		order.setAD_User_ID(0);
		order.setBill_BPartner_ID(0);
		order.setBill_Location_ID(0);
		order.setBill_User_ID(0);
		order.setC_BPartner_ID(bpartner1.getBpartnerId().getRepoId()); // needed to avoid an NPE in InOutGeneratedEventBus
		order.setC_BPartner_Location_ID(bpartner1.getRepoId());

		if (warehouse != null)
		{
			order.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		}

		// more if needed
		saveRecord(order);
		return order;
	}

	/**
	 * Creates a new order line for the given order and product.
	 */
	protected I_C_OrderLine createOrderLine(final I_C_Order order, final I_M_Product product)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class, order);
		orderLine.setAD_Org_ID(order.getAD_Org_ID());
		orderLine.setC_Order(order);

		//
		// Product & UOM
		orderLine.setM_Product_ID(product.getM_Product_ID());
		// orderLine.setC_UOM_ID(productUOM != null ? productUOM.getC_UOM_ID() : -1);
		// 07090: when setting a priceActual, we also need to specify a PriceUOM
		InterfaceWrapperHelper.create(orderLine, de.metas.interfaces.I_C_OrderLine.class).setPrice_UOM_ID(priceUOM.getC_UOM_ID());

		//
		// Quantities
		orderLine.setQtyEntered(BigDecimal.TEN);

		//
		// Prices
		orderLine.setPriceEntered(BigDecimal.TEN);
		orderLine.setPriceActual(BigDecimal.TEN);

		//
		// Warehouse
		orderLine.setM_Warehouse_ID(order.getM_Warehouse_ID());

		//
		// BPartner
		OrderLineDocumentLocationAdapterFactory.locationAdapter(orderLine).setFromOrderHeader(order);

		// more if needed

		//
		// Save & return
		saveRecord(orderLine);
		return orderLine;
	}

	public I_M_Attribute createM_Attribute(final String name,
			final String valueType,
			final boolean isInstanceAttribute)
	{
		final I_M_Attribute attr = InterfaceWrapperHelper.newInstance(I_M_Attribute.class, getCtx());
		attr.setValue(name);
		attr.setName(name);
		attr.setAttributeValueType(valueType);

		//
		// Assume all attributes active and non-mandatory
		attr.setIsActive(true);
		attr.setIsMandatory(false);

		//
		// Configure ASI usage
		attr.setIsInstanceAttribute(isInstanceAttribute);

		saveRecord(attr);
		return attr;
	}

}
