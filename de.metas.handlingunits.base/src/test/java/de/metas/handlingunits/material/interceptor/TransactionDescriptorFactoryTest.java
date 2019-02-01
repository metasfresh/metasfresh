package de.metas.handlingunits.material.interceptor;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.util.TimeUtil.asInstant;

import java.sql.Timestamp;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Transaction;
import org.junit.Before;
import org.junit.Test;

import de.metas.util.time.SystemTime;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class TransactionDescriptorFactoryTest
{

	private static final int M_WAREHOUSE_ID = 10;
	private I_M_Locator locator;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		locator = newInstance(I_M_Locator.class);
		locator.setM_Warehouse_ID(M_WAREHOUSE_ID);
		save(locator);
	}

	@Test
	public void ofRecord()
	{
		final Timestamp movementDate = SystemTime.asTimestamp();

		final I_M_InOutLine inoutLine = newInstance(I_M_InOutLine.class);
		save(inoutLine);

		final I_M_Transaction transactionRecord = newInstance(I_M_Transaction.class);
		transactionRecord.setM_InOutLine(inoutLine);
		transactionRecord.setMovementQty(TEN.negate());
		transactionRecord.setM_Locator(locator);
		transactionRecord.setMovementDate(movementDate);
		save(transactionRecord);

		// invoke the method under test
		final TransactionDescriptor result = new TransactionDescriptorFactory().ofRecord(transactionRecord);

		assertThat(result.getMovementQty()).isEqualByComparingTo("-10");
		assertThat(result.getInoutLineId().getRepoId()).isEqualTo(inoutLine.getM_InOutLine_ID());
		assertThat(result.getTransactionDate()).isEqualTo(asInstant(movementDate));
	}

}
