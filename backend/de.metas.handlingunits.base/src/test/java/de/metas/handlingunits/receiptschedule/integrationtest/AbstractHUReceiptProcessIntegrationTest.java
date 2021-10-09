package de.metas.handlingunits.receiptschedule.integrationtest;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.model.I_C_Order;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.spi.IReceiptScheduleProducer;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.IContextAware;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static de.metas.business.BusinessTestHelper.createBPartner;
import static de.metas.business.BusinessTestHelper.createBPartnerLocation;
import static de.metas.business.BusinessTestHelper.createWarehouse;

@ExtendWith(AdempiereTestWatcher.class)
public abstract class AbstractHUReceiptProcessIntegrationTest
{
	// Services
	protected HUTestHelper huTestHelper;

	//
	// Contexts
	protected IContextAware contextGlobal;

	// Config: Product
	protected ProductId productId;
	protected I_C_UOM productUOM;

	// Config: BPartner
	protected I_C_BPartner bpartner;
	protected I_C_BPartner_Location bpartnerLocation;

	// Config: Warehouse
	protected I_M_Warehouse warehouse;

	// Config: LU/TU config
	protected I_M_HU_PI piLU;
	protected I_M_HU_PI_Item piTU_Item;
	protected I_M_HU_PI_Item_Product piTU_Item_Product;
	//
	protected I_M_HU_PI piTU;
	protected I_M_HU_PI_Item piLU_Item;

	@BeforeEach
	public void init()
	{
		huTestHelper = new HUTestHelper(false);
		huTestHelper.init();

		//
		// Contexts
		contextGlobal = PlainContextAware.newOutOfTrxAllowThreadInherited(huTestHelper.getCtx());

		//
		// Product/UOM
		productId = ProductId.ofRepoId(huTestHelper.pTomato.getM_Product_ID());
		productUOM = huTestHelper.uomEach;

		//
		// BPartner
		bpartner = createBPartner("BPartner01");
		bpartnerLocation = createBPartnerLocation(bpartner);

		//
		// Warehouse & Locator
		warehouse = createWarehouse("Warehouse01");

		//
		// Handling Units Definition
		piTU = huTestHelper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			piTU_Item = huTestHelper.createHU_PI_Item_Material(piTU);
			piTU_Item_Product = huTestHelper.assignProduct(piTU_Item, productId, BigDecimal.TEN, productUOM);
		}

		piLU = huTestHelper.createHUDefinition("LU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		{
			piLU_Item = huTestHelper.createHU_PI_Item_IncludedHU(piLU, piTU, new BigDecimal("2"));
		}
	}

	@Test
	public void test()
	{
		step10_createOrders();
		step20_createReceiptSchedules();

		// TODO: this integration test is work in progress!!!

		// POJOLookupMap.get().dumpStatus();
	}

	//
	//
	protected List<I_C_Order> orders;
	protected List<I_C_OrderLine> orderLines;

	protected abstract void step10_createOrders();

	//
	//
	protected List<I_M_ReceiptSchedule> receiptSchedules;

	protected void step20_createReceiptSchedules()
	{
		step20_createReceiptSchedules_CreateReceiptSchedulesFromOrders();
		step20_createReceiptSchedules_ValidateCreatedReceiptSchedules();
	}

	protected void step20_createReceiptSchedules_CreateReceiptSchedulesFromOrders()
	{
		final IReceiptScheduleProducerFactory receiptScheduleProducerFactory = Services.get(IReceiptScheduleProducerFactory.class);
		final IReceiptScheduleProducer producer = receiptScheduleProducerFactory.createProducer(org.compiere.model.I_C_Order.Table_Name, false); // async=false

		receiptSchedules = new ArrayList<>();
		for (final I_C_Order order : orders)
		{
			final List<de.metas.inoutcandidate.model.I_M_ReceiptSchedule> previousSchedules = Collections.emptyList();
			final List<de.metas.inoutcandidate.model.I_M_ReceiptSchedule> orderReceiptSchedules = producer.createOrUpdateReceiptSchedules(order, previousSchedules);
			receiptSchedules.addAll(InterfaceWrapperHelper.createList(orderReceiptSchedules, I_M_ReceiptSchedule.class));
		}
	}

	protected abstract void step20_createReceiptSchedules_ValidateCreatedReceiptSchedules();

	//
	//
	// Helper methods
	//
	//

	@SuppressWarnings("SameParameterValue")
	protected <T> void assertSingletonAndGet(final String message, final List<T> list)
	{
		final String prefix = (message == null ? "" : message)
				+ "\nList: " + list
				+ "\n\nError: ";
		Assertions.assertNotNull(list, prefix + "list not null");
		Assertions.assertEquals(1, list.size(), prefix + "list shall have only one element");

		final T item = list.get(0);
		Assertions.assertNotNull(item, prefix + "item not null");

	}
}
