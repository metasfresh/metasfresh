package de.metas.handlingunits.material.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.util.TimeUtil.asInstant;

import java.math.BigDecimal;
import java.sql.Timestamp;

import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.material.interceptor.transactionevent.TransactionDescriptor;
import de.metas.handlingunits.material.interceptor.transactionevent.TransactionDescriptorFactory;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Transaction;
import org.junit.Before;
import org.junit.Test;

import de.metas.inout.InOutLineId;
import de.metas.product.ProductId;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class TransactionDescriptorFactoryTest
{
	private TransactionDescriptorFactory transactionDescriptorFactory;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		transactionDescriptorFactory = new TransactionDescriptorFactory();
	}

	private LocatorId createLocator(WarehouseId warehouseId)
	{
		final I_M_Locator record = newInstance(I_M_Locator.class);
		record.setM_Warehouse_ID(warehouseId.getRepoId());
		saveRecord(record);
		return LocatorId.ofRepoId(warehouseId, record.getM_Locator_ID());
	}

	private InOutLineId createInOutLine()
	{
		final I_M_InOutLine inoutLine = newInstance(I_M_InOutLine.class);
		saveRecord(inoutLine);
		final InOutLineId inoutLineId = InOutLineId.ofRepoId(inoutLine.getM_InOutLine_ID());
		return inoutLineId;
	}

	@Test
	public void ofRecord()
	{
		final ProductId productId = ProductId.ofRepoId(11);

		final WarehouseId warehouseId = WarehouseId.ofRepoId(10);
		final LocatorId locatorId = createLocator(warehouseId);

		final InOutLineId inoutLineId = createInOutLine();

		final Timestamp movementDate = SystemTime.asTimestamp();

		final I_M_Transaction transactionRecord = newInstance(I_M_Transaction.class);
		transactionRecord.setM_Product_ID(productId.getRepoId());
		transactionRecord.setM_InOutLine_ID(inoutLineId.getRepoId());
		transactionRecord.setMovementQty(new BigDecimal("-10.234"));
		transactionRecord.setM_Locator_ID(locatorId.getRepoId());
		transactionRecord.setMovementDate(movementDate);
		save(transactionRecord);

		// invoke the method under test
		final TransactionDescriptor result = transactionDescriptorFactory.ofRecord(transactionRecord);

		assertThat(result.getProductId()).isEqualTo(productId);
		assertThat(result.getInoutLineId()).isEqualTo(inoutLineId);
		assertThat(result.getMovementQty()).isEqualByComparingTo("-10.234");
		assertThat(result.getWarehouseId()).isEqualTo(warehouseId);
		assertThat(result.getTransactionDate()).isEqualTo(asInstant(movementDate));
	}
}
