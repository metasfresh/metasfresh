/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package org.adempiere.ad.trx.api.impl;

import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RunAfterCloseIfCommittedTest
{
	private ITrxManager trxManager;
	private final AtomicInteger runCount = new AtomicInteger();

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		trxManager = Services.get(ITrxManager.class);
		runCount.set(0);
	}

	@Test
	void trxCommitted_runsOnceAfterClose()
	{
		trxManager.runInNewTrx(() -> {
			trxManager.runAfterCloseIfCommitted(runCount::incrementAndGet);
			assertThat(runCount).hasValue(0);
		});

		assertThat(runCount).hasValue(1);
	}

	@Test
	void trxRolledBack_doesNotRun()
	{
		assertThatThrownBy(() -> trxManager.runInNewTrx(() -> {
			trxManager.runAfterCloseIfCommitted(runCount::incrementAndGet);
			throw new AdempiereException("rollback");
		})).isInstanceOf(AdempiereException.class);

		assertThat(runCount).hasValue(0);
	}
}
