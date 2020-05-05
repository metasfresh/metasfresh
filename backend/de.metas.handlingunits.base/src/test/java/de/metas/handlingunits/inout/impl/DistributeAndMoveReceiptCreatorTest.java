package de.metas.handlingunits.inout.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.junit.Before;
import org.junit.Test;

import de.metas.acct.api.IProductAcctDAO;
import de.metas.handlingunits.inout.impl.DistributeAndMoveReceiptCreator.Result;
import de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.X_M_ReceiptSchedule;
import de.metas.product.IProductActivityProvider;
import de.metas.product.LotNumberQuarantineRepository;
import de.metas.product.ProductId;
import de.metas.util.Services;

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

public class DistributeAndMoveReceiptCreatorTest
{
	private DistributeAndMoveReceiptCreator distributeAndMoveReceiptCreator;

	private final ProductId productId = ProductId.ofRepoId(1);

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		Services.registerService(IProductActivityProvider.class, Services.get(IProductAcctDAO.class));

		distributeAndMoveReceiptCreator = new DistributeAndMoveReceiptCreator(new LotNumberQuarantineRepository());
	}

	@Test
	public void createDocumentsFor()
	{
		final I_M_Warehouse destWarehouse = newInstance(I_M_Warehouse.class);
		saveRecord(destWarehouse);

		final I_M_InOut receiptRecord = newInstance(I_M_InOut.class);
		receiptRecord.setIsSOTrx(false);
		saveRecord(receiptRecord);

		final I_M_Warehouse receiptWarehouse = newInstance(I_M_Warehouse.class);
		saveRecord(receiptWarehouse);

		final I_M_Locator receiptLineLocator = newInstance(I_M_Locator.class);
		receiptLineLocator.setM_Warehouse(receiptWarehouse);
		save(receiptLineLocator);

		final I_M_InOutLine receiptLineRecord = newInstance(I_M_InOutLine.class);
		receiptLineRecord.setM_InOut(receiptRecord);
		receiptLineRecord.setM_Locator_ID(receiptLineLocator.getM_Locator_ID());
		receiptLineRecord.setM_Product_ID(productId.getRepoId());
		saveRecord(receiptLineRecord);

		final I_M_ReceiptSchedule receiptSchedule = newInstance(I_M_ReceiptSchedule.class);
		receiptSchedule.setM_Warehouse_Dest_ID(destWarehouse.getM_Warehouse_ID());
		receiptSchedule.setOnMaterialReceiptWithDestWarehouse(X_M_ReceiptSchedule.ONMATERIALRECEIPTWITHDESTWAREHOUSE_CreateMovement);
		saveRecord(receiptSchedule);

		final I_M_ReceiptSchedule_Alloc receiptScheduleAllocRecord = newInstance(I_M_ReceiptSchedule_Alloc.class);
		receiptScheduleAllocRecord.setM_ReceiptSchedule(receiptSchedule);
		receiptScheduleAllocRecord.setM_InOutLine(receiptLineRecord);
		saveRecord(receiptScheduleAllocRecord);

		// invoke the method under test
		final Result result = distributeAndMoveReceiptCreator.createDocumentsFor(receiptRecord, null);

		assertThat(result.getDdOrders()).isEmpty();
		assertThat(result.getQuarantineDDOrders()).isEmpty();
		assertThat(result.getMovements()).isNotEmpty();
	}

}
