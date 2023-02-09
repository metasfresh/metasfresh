package org.adempiere.ad.trx.api;

import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class TrxEventTimingTest
{
	@Nested
	class canBeRegisteredWithinOtherTiming
	{
		@ParameterizedTest
		@EnumSource(TrxEventTiming.class)
		void anythingShallBeAllowedOnEventNone(TrxEventTiming timing) {assertThat(timing.canBeRegisteredWithinOtherTiming(TrxEventTiming.NONE)).isTrue();}

		@Nested
		class registerFor_AFTER_COMMIT
		{
			@Test
			void on_NONE() {assertThat(TrxEventTiming.AFTER_COMMIT.canBeRegisteredWithinOtherTiming(TrxEventTiming.NONE)).isTrue();}

			@Test
			void on_BEFORE_COMMIT() {assertThat(TrxEventTiming.AFTER_COMMIT.canBeRegisteredWithinOtherTiming(TrxEventTiming.BEFORE_COMMIT)).isTrue();}

			@Test
			void on_AFTER_COMMIT() {assertThat(TrxEventTiming.AFTER_COMMIT.canBeRegisteredWithinOtherTiming(TrxEventTiming.AFTER_COMMIT)).isFalse();}

			@Test
			void on_AFTER_CLOSE() {assertThat(TrxEventTiming.AFTER_COMMIT.canBeRegisteredWithinOtherTiming(TrxEventTiming.AFTER_CLOSE)).isFalse();}

			@Test
			void on_AFTER_ROLLBACK() {assertThat(TrxEventTiming.AFTER_COMMIT.canBeRegisteredWithinOtherTiming(TrxEventTiming.AFTER_ROLLBACK)).isFalse();}
		}
	}
}