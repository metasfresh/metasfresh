package de.metas.contracts.subscription.order.restart;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.util.RelatedRecordsProvider.SourceRecordsKey;

/*
 * #%L
 * de.metas.contracts
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

public class RelatedSubscriptionsForOrderProviderTest
{

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void provideRelatedRecords()
	{
		final I_C_Order orderRecord1 = newInstance(I_C_Order.class);
		saveRecord(orderRecord1);

		final I_C_OrderLine orderLineRecord11 = newInstance(I_C_OrderLine.class);
		orderLineRecord11.setC_Order(orderRecord1);
		saveRecord(orderLineRecord11);

		final I_C_OrderLine orderLineRecord12 = newInstance(I_C_OrderLine.class);
		orderLineRecord12.setC_Order(orderRecord1);
		saveRecord(orderLineRecord12);

		final I_C_Flatrate_Term flatrateTermRecord112 = newInstance(I_C_Flatrate_Term.class);
		POJOWrapper.setInstanceName(flatrateTermRecord112, "flatrateTermRecord112");
		flatrateTermRecord112.setC_OrderLine_Term_ID(orderLineRecord11.getC_OrderLine_ID());
		flatrateTermRecord112.setC_Order_Term_ID(orderLineRecord11.getC_Order_ID());
		saveRecord(flatrateTermRecord112);

		final I_C_Flatrate_Term flatrateTermRecord111 = newInstance(I_C_Flatrate_Term.class);
		POJOWrapper.setInstanceName(flatrateTermRecord111, "flatrateTermRecord111");
		flatrateTermRecord111.setC_OrderLine_Term_ID(orderLineRecord11.getC_OrderLine_ID());
		flatrateTermRecord111.setC_Order_Term_ID(orderLineRecord11.getC_Order_ID());
		flatrateTermRecord111.setC_FlatrateTerm_Next(flatrateTermRecord112);
		saveRecord(flatrateTermRecord111);

		final TableRecordReference orderReference = TableRecordReference.of(orderRecord1);

		// invoke the method under test
		final IPair<SourceRecordsKey, List<ITableRecordReference>> //
		result = new RelatedSubscriptionsForOrdersProvider().provideRelatedRecords(ImmutableList.of(orderReference));

		final List<ITableRecordReference> records = result.getRight();
		assertThat(records).containsOnly(
				TableRecordReference.of(flatrateTermRecord111),
				TableRecordReference.of(flatrateTermRecord112));
	}

}
