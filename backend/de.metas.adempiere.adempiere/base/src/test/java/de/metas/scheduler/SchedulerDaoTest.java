/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.scheduler;

import de.metas.process.AdProcessId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Scheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class SchedulerDaoTest
{
	private SchedulerDao schedulerDao;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		schedulerDao = new SchedulerDao();
	}

	@Test
	public void getSchedulerByProcessIdIfUnique_Test()
	{
		//given
		final I_AD_Process adProcessRecord = InterfaceWrapperHelper.newInstance(I_AD_Process.class);
		InterfaceWrapperHelper.save(adProcessRecord);

		final I_AD_Scheduler record = InterfaceWrapperHelper.newInstance(I_AD_Scheduler.class);
		record.setAD_Process_ID(adProcessRecord.getAD_Process_ID());
		InterfaceWrapperHelper.save(record);

		//when
		final I_AD_Scheduler result = schedulerDao.getSchedulerByProcessIdIfUnique(AdProcessId.ofRepoId(adProcessRecord.getAD_Process_ID()))
				.orElseThrow(() -> new AdempiereException("Something went wrong when executing test for SchedulerDaoTest class"));

		//then
		assertThat(result).isNotNull();
		assertThat(result.getAD_Process_ID()).isEqualTo(adProcessRecord.getAD_Process_ID());
	}
}
