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

import com.google.common.collect.ImmutableList;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AccumulateAndProcessAfterCommitTest
{
	private static final String PROPERTY_NAME = "AccumulateAndProcessAfterCommitTest.items";

	private ITrxManager trxManager;
	private final List<ImmutableList<String>> processedLists = new ArrayList<>();

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		trxManager = Services.get(ITrxManager.class);
		processedLists.clear();
	}

	private void accumulate(final String item)
	{
		trxManager.accumulateAndProcessAfterCommit(PROPERTY_NAME, ImmutableList.of(item), processedLists::add);
	}

	@Test
	void itemsOfOneCommit_areProcessedTogether_inTheOrderTheyWereAdded()
	{
		trxManager.runInNewTrx(() -> {
			accumulate("a");
			accumulate("b");
			accumulate("c");
			assertThat(processedLists).isEmpty();
		});

		assertThat(processedLists).containsExactly(ImmutableList.of("a", "b", "c"));
	}

	/**
	 * A trx can be committed more than once; after a commit, the next DB access starts the same trx again.
	 */
	@Test
	void trxCommittedTwice_itemsAddedAfterTheFirstCommit_areProcessedAfterTheSecondCommit()
	{
		trxManager.runInNewTrx(localTrxName -> {
			accumulate("a");
			trxManager.commit(localTrxName);
			assertThat(processedLists).containsExactly(ImmutableList.of("a"));

			trxManager.getTrx(localTrxName).start(); // what the next DB access does
			accumulate("b");
			accumulate("c");
		});

		assertThat(processedLists).containsExactly(ImmutableList.of("a"), ImmutableList.of("b", "c"));
	}

	/**
	 * While the after-commit listeners run, the trx is not active, so items added by a processor are processed right away (no new listener, no loop).
	 */
	@Test
	void processorThatAddsItemsAgain_getsThemProcessedRightAway()
	{
		trxManager.runInNewTrx(() -> trxManager.accumulateAndProcessAfterCommit(PROPERTY_NAME, ImmutableList.of("a"), list -> {
			processedLists.add(list);
			accumulate("added-by-processor");
		}));

		assertThat(processedLists).containsExactly(ImmutableList.of("a"), ImmutableList.of("added-by-processor"));
	}

	@Test
	void withoutTrx_eachItemIsProcessedRightAway()
	{
		accumulate("a");
		accumulate("b");

		assertThat(processedLists).containsExactly(ImmutableList.of("a"), ImmutableList.of("b"));
	}
}
