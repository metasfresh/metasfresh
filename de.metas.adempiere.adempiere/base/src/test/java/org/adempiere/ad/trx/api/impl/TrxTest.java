package org.adempiere.ad.trx.api.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

public class TrxTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void test_rollback_NeverThrowsException()
	{
		final MockedTrxManager trxManager = new MockedTrxManager();
		final MockedTrx trx = trxManager.createTrx("TestTrx", false);

		trx.setOnRollbackException(new RuntimeException("test - fail on commit"));

		trx.start();
		trx.rollback();
	}

	@Test
	public void test_getPropertyAndProcessAfterCommit()
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
