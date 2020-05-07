package de.metas.contracts.inoutcandidate;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.junit.Before;
import org.junit.Test;

import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.inoutcandidate.ShipmentScheduleSubscriptionReferenceProvider;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ShipmentScheduleReferencedLine;
import mockit.Expectations;
import mockit.Mocked;

/*
 * #%L
 * de.metas.contracts
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

public class ShipmentScheduleSubscriptionReferenceProviderTest
{

	@Mocked
	private IFlatrateBL flatrateBL;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		Services.registerService(IFlatrateBL.class, flatrateBL);
	}

	@Test
	public void test()
	{
		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		save(term);

		// @formatter:off
		new Expectations() {{ flatrateBL.getWarehouseId(term); result = 23;	}};
		// @formatter:on

		final I_C_SubscriptionProgress subscriptionLine = newInstance(I_C_SubscriptionProgress.class);
		subscriptionLine.setC_Flatrate_Term(term);
		save(subscriptionLine);

		final TableRecordReference reference = TableRecordReference.of(subscriptionLine);

		final I_M_ShipmentSchedule sched = newInstance(I_M_ShipmentSchedule.class);
		sched.setAD_Table_ID(reference.getAD_Table_ID());
		sched.setRecord_ID(reference.getRecord_ID());
		save(sched);

		final ShipmentScheduleReferencedLine result = new ShipmentScheduleSubscriptionReferenceProvider().provideFor(sched);
		assertThat(result).isNotNull();
		assertThat(result.getGroupId()).isEqualTo(term.getC_Flatrate_Term_ID());
		assertThat(result.getWarehouseId()).isEqualTo(23);
	}

}
