/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.pricing.trade_margin;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import de.metas.contracts.commission.model.I_C_Customer_Trade_Margin;
import de.metas.contracts.commission.model.I_C_Customer_Trade_Margin_Line;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@ExtendWith(SnapshotExtension.class)
public class CustomerTradeMarginRepositoryTest
{
	private CustomerTradeMarginRepository customerTradeMarginRepository;
	private Expect expect;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		customerTradeMarginRepository = new CustomerTradeMarginRepository();
	}

	@Test
	public void getById_Test()
	{
		final I_C_Customer_Trade_Margin settingsRecord = newInstance(I_C_Customer_Trade_Margin.class);
		settingsRecord.setName("name");
		settingsRecord.setCommission_Product_ID(1);
		settingsRecord.setPointsPrecision(2);
		saveRecord(settingsRecord);
		final CustomerTradeMarginId settingsId = CustomerTradeMarginId.ofRepoId(settingsRecord.getC_Customer_Trade_Margin_ID());

		final I_C_Customer_Trade_Margin_Line settingsLineRecord = newInstance(I_C_Customer_Trade_Margin_Line.class);
		settingsLineRecord.setC_Customer_Trade_Margin_ID(settingsId.getRepoId());
		settingsLineRecord.setMargin(10);
		saveRecord(settingsLineRecord);

		final CustomerTradeMargin customerTradeMargin = customerTradeMarginRepository.getById(settingsId);

		expect.serializer("orderedJson").toMatchSnapshot(customerTradeMargin);
	}
}
