package de.metas.lock.process;

import de.metas.process.AdProcessId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_AD_Process;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2026 metas GmbH
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

class ProcessSingleInstanceLockServiceTest
{
	private ProcessSingleInstanceLockService service;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		service = new ProcessSingleInstanceLockService();
	}

	private static AdProcessId newProcess()
	{
		final I_AD_Process process = newInstance(I_AD_Process.class);
		process.setValue("TestProcess");
		process.setName("TestProcess");
		save(process);
		return AdProcessId.ofRepoId(process.getAD_Process_ID());
	}

	@Test
	void secondConcurrentAcquire_isRejected_untilTheFirstIsReleased()
	{
		final AdProcessId processId = newProcess();

		final Optional<IAutoCloseable> first = service.acquireFor(processId);
		assertThat(first).isPresent(); // first run gets the lock

		assertThat(service.acquireFor(processId)).isEmpty(); // a concurrent run is rejected

		first.get().close(); // first run finishes, releasing the lock

		final Optional<IAutoCloseable> afterRelease = service.acquireFor(processId);
		assertThat(afterRelease).isPresent(); // a later run can acquire again
		afterRelease.get().close();
	}

	@Test
	void differentProcesses_doNotBlockEachOther()
	{
		final Optional<IAutoCloseable> lockA = service.acquireFor(newProcess());
		final Optional<IAutoCloseable> lockB = service.acquireFor(newProcess());
		assertThat(lockA).isPresent();
		assertThat(lockB).isPresent(); // distinct AD_Process records → independent locks
		lockA.get().close();
		lockB.get().close();
	}
}
