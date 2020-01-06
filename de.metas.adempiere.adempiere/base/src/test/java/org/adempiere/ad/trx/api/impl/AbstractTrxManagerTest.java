package org.adempiere.ad.trx.api.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxRunConfig;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableFail;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableSuccess;
import org.adempiere.ad.trx.api.ITrxRunConfig.TrxPropagation;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.Mutable;
import org.compiere.util.TrxRunnable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import lombok.NonNull;

public class AbstractTrxManagerTest
{
	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void rollback_NeverThrowsException()
	{
		final MockedTrxManager trxManager = new MockedTrxManager();
		final MockedTrx trx = trxManager.createTrx("TestTrx", false);

		trx.setOnRollbackException(new RuntimeException("test - fail on commit"));

		trx.start();
		trx.rollback();
	}

	@Test
	void runInNewTrx_withException()
	{
		final MockedTrxManager trxManager = new MockedTrxManager();

		assertThatThrownBy(
				() -> trxManager.runInNewTrx(() -> {
					throw new RuntimeException("something went wrong");
				}))
						.hasMessage("RuntimeException: something went wrong");

		final List<ITrx> removedTransactions = trxManager.getRemovedTransactions();
		assertThat(removedTransactions).hasSize(1);
		final MockedTrx trx = (MockedTrx)removedTransactions.get(0);
		assertThat(trx.isRollbackCalled()).isTrue();
		assertThat(trx.isCloseCalled()).isTrue();
		assertThat(trx.getCreatedSavepoints()).isEmpty();
		assertThat(trx.getReleasedSavepoints()).isEmpty();
	}

	@Test
	void runInNewTrx()
	{
		final MockedTrxManager trxManager = new MockedTrxManager();

		trxManager.runInNewTrx(() -> {
			/* nothing to do */ });

		final List<ITrx> removedTransactions = trxManager.getRemovedTransactions();
		assertThat(removedTransactions).hasSize(1);
		final MockedTrx trx = (MockedTrx)removedTransactions.get(0);
		assertThat(trx.isRollbackCalled()).isFalse();
		assertThat(trx.isCommitCalled()).isTrue();
		assertThat(trx.isCloseCalled()).isTrue();
		assertThat(trx.getCreatedSavepoints()).isEmpty();
		assertThat(trx.getReleasedSavepoints()).isEmpty();
	}

	@Test
	void runInNewTrx_nested()
	{
		final Runnable innerRunnable = () -> {
			/* nothing to do */ };
		final MockedTrxManager trxManager = new MockedTrxManager();

		final MockedTrx trx = invokedTrxManagerNested(trxManager, innerRunnable);

		final List<ITrx> removedTransactions = trxManager.getRemovedTransactions();
		assertThat(removedTransactions).hasSize(1);
		final MockedTrx removedTrx = (MockedTrx)removedTransactions.get(0);
		assertThat(removedTrx.getTrxName()).isEqualTo(trx.getTrxName());

		assertThat(removedTrx.isRollbackCalled()).isFalse();
		assertThat(removedTrx.isCloseCalled()).isTrue();
		assertThat(removedTrx.getCreatedSavepoints()).hasSize(1);
		assertThat(removedTrx.getReleasedSavepoints()).hasSize(1);
	}

	@Test
	void runInNewTrx_nested_exception()
	{
		final Runnable innerRunnable = () -> {
			throw new RuntimeException("something went wrong");
		};
		final MockedTrxManager trxManager = new MockedTrxManager();

		assertThatThrownBy(() -> invokedTrxManagerNested(trxManager, innerRunnable))
				.hasMessage("RuntimeException: something went wrong");

		final List<ITrx> removedTransactions = trxManager.getRemovedTransactions();

		assertThat(removedTransactions).hasSize(1);
		final MockedTrx removedTrx = (MockedTrx)removedTransactions.get(0);

		assertThat(removedTrx.isCommitCalled()).isFalse();
		assertThat(removedTrx.isRollbackCalled()).isTrue();
		assertThat(removedTrx.isCloseCalled()).isTrue();
		assertThat(removedTrx.getCreatedSavepoints()).hasSize(1);
		assertThat(removedTrx.getActiveSavepoints()).isEmpty();
	}

	private MockedTrx invokedTrxManagerNested(
			@NonNull final MockedTrxManager trxManager,
			@NonNull final Runnable innerRunnable)
	{
		final ITrxRunConfig outerTrxRunCfg = trxManager.newTrxRunConfigBuilder().build();
		final Mutable<MockedTrx> trx = new Mutable<>();

		trxManager.run(
				ITrx.TRXNAME_None,
				outerTrxRunCfg,
				localTrxName -> {
					trx.setValue((MockedTrx)trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.Fail));
					trxManager.run(
							ITrx.TRXNAME_ThreadInherited,
							innerRunnable);
				});

		assertThat(trxManager.getActiveTransactionsList()).isEmpty();
		return trx.getValue();
	}

	@Test
	void runInNewTrx_nested_twice_2ndWithException_trxNamePrefixNull()
	{
		final String trxNamePrefix = ITrx.TRXNAME_None;
		perform_run_nested_twice_2ndWithException(trxNamePrefix);
	}

	@Test
	void runInNewTrx_nested_twice_2ndWithException_trxNamePrefixNotNull()
	{
		final String trxNamePrefix = this.getClass().getSimpleName();
		perform_run_nested_twice_2ndWithException(trxNamePrefix);
	}

	private void perform_run_nested_twice_2ndWithException(final String trxNamePrefix)
	{
		// @formatter:off
		final Runnable successfulInnerRunnable = () -> { /* nothing to do */ };
		final Runnable failingInnerRunnable = () -> { throw new RuntimeException("something went wrong"); };
		// @formatter:on

		final MockedTrxManager trxManager = new MockedTrxManager();

		final ITrxRunConfig outerTrxRunCfg = trxManager.newTrxRunConfigBuilder().setOnRunnableFail(OnRunnableFail.ROLLBACK).build();
		assertThat(outerTrxRunCfg.getTrxPropagation()).isEqualTo(TrxPropagation.REQUIRES_NEW); // guard

		final Mutable<MockedTrx> trx = new Mutable<>();

		assertThatThrownBy(
				() -> trxManager.run(
						trxNamePrefix,
						outerTrxRunCfg,
						localTrxName -> {

							trx.setValue((MockedTrx)trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.Fail));

							trxManager.run(ITrx.TRXNAME_ThreadInherited, successfulInnerRunnable);

							trxManager.run(ITrx.TRXNAME_ThreadInherited, failingInnerRunnable);
						}))
								.hasMessage("RuntimeException: something went wrong");

		final List<ITrx> removedTransactions = trxManager.getRemovedTransactions();

		assertThat(removedTransactions).hasSize(1);
		final MockedTrx removedTrx = (MockedTrx)removedTransactions.get(0);

		assertThat(removedTrx.isCommitCalled()).isFalse();
		assertThat(removedTrx.isRollbackCalled()).isTrue();
		assertThat(removedTrx.isCloseCalled()).isTrue();
		assertThat(removedTrx.getCreatedSavepoints()).hasSize(2);
		assertThat(removedTrx.getReleasedSavepoints()).hasSize(1);
		assertThat(removedTrx.getActiveSavepoints()).isEmpty();
	}

	@Test
	void run_withTrxNamePrefix_OnRunnableFailRollback_exception()
	{
		final MockedTrxManager trxManager = new MockedTrxManager();
		final String trxNamePrefix = this.getClass().getSimpleName();

		final ITrxRunConfig trxRunConfig = trxManager.newTrxRunConfigBuilder()
				.setTrxPropagation(TrxPropagation.REQUIRES_NEW).setOnRunnableSuccess(OnRunnableSuccess.COMMIT).setOnRunnableFail(OnRunnableFail.ROLLBACK)
				.build();

		assertThatThrownBy(() -> trxManager.run(
				trxNamePrefix,
				trxRunConfig,
				(TrxRunnable)trxName_IGNORED -> {
					throw new RuntimeException("something went wrong");
				}))
						.hasMessage("RuntimeException: something went wrong");

		final List<ITrx> removedTransactions = trxManager.getRemovedTransactions();

		assertThat(removedTransactions).hasSize(1);
		final MockedTrx removedTrx = (MockedTrx)removedTransactions.get(0);

		assertThat(removedTrx.isCloseCalled()).isTrue();
		assertThat(removedTrx.isCommitCalled()).isFalse();
		assertThat(removedTrx.isRollbackCalled()).isTrue();
		assertThat(removedTrx.getCreatedSavepoints()).isEmpty();
		assertThat(removedTrx.getReleasedSavepoints()).isEmpty();
		assertThat(removedTrx.getActiveSavepoints()).isEmpty();
	}

	@Test
	void getPropertyAndProcessAfterCommit()
	{
		final PlainTrxManager trxManager = new PlainTrxManager();
		final PlainTrx trx = trxManager.createTrx("TestTrx", false);

		final List<String> expectedValues = ImmutableList.of("value1", "value2", "value3");

		for (final String value : expectedValues)
		{
			final ValuesCollector collector = trx.getPropertyAndProcessAfterCommit(
					"propertyName",
					ValuesCollector::new,
					ValuesCollector::markProcessed);

			collector.collect(value);
		}

		//
		// Check collector before processing
		final ValuesCollector collector = trx.getProperty("propertyName");
		{
			assertThat(collector.getCollectedValues()).isEqualTo(expectedValues);
			assertThat(collector.isProcessed()).isFalse();
		}

		//
		// Simulate after commit
		trx.getTrxListenerManager().fireAfterCommit(trx);

		//
		// Check collector after processing
		{
			assertThat(collector.getCollectedValues()).isEqualTo(expectedValues);
			assertThat(collector.isProcessed()).isTrue();
		}
	}

	private static class ValuesCollector
	{
		private final List<String> values = new ArrayList<>();
		private boolean processed;

		public void collect(final String value)
		{
			values.add(value);
		}

		public List<String> getCollectedValues()
		{
			return new ArrayList<>(values);
		}

		public void markProcessed()
		{
			this.processed = true;
		}

		public boolean isProcessed()
		{
			return processed;
		}
	}
}
