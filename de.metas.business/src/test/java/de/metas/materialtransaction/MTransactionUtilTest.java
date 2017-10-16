package de.metas.materialtransaction;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.X_M_Transaction;
import org.junit.Before;
import org.junit.Test;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class MTransactionUtilTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void getEffectiveMovementQty_when_VendorReceiptsTransaction_return_qty()
	{
		final I_M_Transaction transaction = newInstance(I_M_Transaction.class);
		transaction.setMovementQty(BigDecimal.TEN);
		transaction.setMovementType(X_M_Transaction.MOVEMENTTYPE_VendorReceipts);
		assertThat(MTransactionUtil.getEffectiveMovementQty(transaction)).isEqualByComparingTo("10");
	}

	@Test
	public void getEffectiveMovementQty_when_VendorReturnsTransaction_return_negated_qty()
	{
		final I_M_Transaction transaction = newInstance(I_M_Transaction.class);
		transaction.setMovementQty(BigDecimal.TEN);
		transaction.setMovementType(X_M_Transaction.MOVEMENTTYPE_VendorReturns);
		assertThat(MTransactionUtil.getEffectiveMovementQty(transaction)).isEqualByComparingTo("-10");
	}

	@Test
	public void getEffectiveMovementQty_when_VendorReceiptsTransaction_return_negated_qty()
	{
		final I_M_Transaction transaction = newInstance(I_M_Transaction.class);
		transaction.setMovementQty(BigDecimal.TEN);
		transaction.setMovementType(X_M_Transaction.MOVEMENTTYPE_VendorReceipts);
		assertThat(MTransactionUtil.getEffectiveMovementQty(transaction)).isEqualByComparingTo("10");
	}
}
