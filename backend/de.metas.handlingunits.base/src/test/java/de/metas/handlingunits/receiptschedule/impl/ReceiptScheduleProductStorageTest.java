package de.metas.handlingunits.receiptschedule.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.Assume;

import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.handlingunits.storage.impl.AbstractProductStorageTest;

public class ReceiptScheduleProductStorageTest extends AbstractProductStorageTest
{
	private BPartnerId bpartnerId;
	private WarehouseId warehouseId;

	@Override
	protected void initialize()
	{
		super.initialize();

		bpartnerId = BPartnerId.ofRepoId(BusinessTestHelper.createBPartner("test").getC_BPartner_ID());
		warehouseId = WarehouseId.ofRepoId(BusinessTestHelper.createWarehouse("test").getM_Warehouse_ID());
	}

	@Override
	protected IProductStorage createStorage(final String qtyStr, final boolean reversal, final boolean outboundTrx)
	{
		Assume.assumeTrue("We are not supporting outboundTrx for ReceiptSchedules", !outboundTrx);
		Assume.assumeTrue("We are not supporting not reversal transactions only", !reversal);

		final BigDecimal qtyAbs = new BigDecimal(qtyStr);
		final BigDecimal qty = qtyAbs;

		final I_M_ReceiptSchedule schedule = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule.class, helper.contextProvider);
		schedule.setM_Warehouse_ID(warehouseId.getRepoId());
		schedule.setC_BPartner_ID(bpartnerId.getRepoId());
		schedule.setM_Product_ID(product.getM_Product_ID());
		schedule.setC_UOM_ID(uomEach.getC_UOM_ID());
		schedule.setQtyOrdered(qty);
		schedule.setQtyMoved(BigDecimal.ZERO);
		InterfaceWrapperHelper.save(schedule);

		// enabling string values because we want to make sure that only defined fields are used
		POJOWrapper.getWrapper(schedule).setStrictValues(true);

		final boolean enforceCapacity = true;
		return new ReceiptScheduleProductStorage(schedule, enforceCapacity);
	}

}
