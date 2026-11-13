package de.metas.handlingunits;

import de.metas.inoutcandidate.api.impl.HUShipmentScheduleHeaderAggregationKeyBuilder;
import de.metas.inoutcandidate.api.impl.ReceiptScheduleHeaderAggregationKeyBuilder;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleHeaderAggregationKeyBuilder;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.agg.key.IAggregationKeyRegistry;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

/**
 * {@link IAggregationKeyRegistry} keeps its handlers in static state that {@link AdempiereTestHelper#init()} does not reset.
 * Verifies that the handlers registered by {@link HUTestHelper} are gone once the next test (possibly of another test class in the same JVM)
 * runs {@link AdempiereTestHelper#init()}, so they don't leak into it.
 */
class HUTestHelperAggregationKeyHandlersCleanupTest
{
	/**
	 * Matches no handler's model type, so any handler still registered fails with a {@link ClassCastException} when invoked.
	 */
	private static final Object PROBE_MODEL = new Object();

	@Test
	void inOutScheduleHandlers_areClearedByNextInit()
	{
		new HUTestHelper(); // default setup, with a transaction, as used by most tests
		assertHandlersRegistered(ReceiptScheduleHeaderAggregationKeyBuilder.REGISTRATION_KEY);
		assertHandlersRegistered(ShipmentScheduleHeaderAggregationKeyBuilder.REGISTRATION_KEY);

		AdempiereTestHelper.get().init(); // what the next test does first

		assertNoHandlersRegistered(ReceiptScheduleHeaderAggregationKeyBuilder.REGISTRATION_KEY);
		assertNoHandlersRegistered(ShipmentScheduleHeaderAggregationKeyBuilder.REGISTRATION_KEY);
	}

	@Test
	void fullHUModuleInterceptorHandlers_areClearedByNextInit()
	{
		new HUTestHelper()
		{
			// like the tests that use the full HU module interceptor (e.g. HUSnapshotDAOTest, HUPPOrderIssueProducerTest)
			@Override
			protected String createAndStartTransaction()
			{
				return ITrx.TRXNAME_None;
			}

			@Override
			protected void setupModuleInterceptors_HU()
			{
				setupModuleInterceptors_HU_Full();
			}
		};
		assertHandlersRegistered(HUShipmentScheduleHeaderAggregationKeyBuilder.REGISTRATION_KEY);

		AdempiereTestHelper.get().init(); // what the next test does first

		assertNoHandlersRegistered(HUShipmentScheduleHeaderAggregationKeyBuilder.REGISTRATION_KEY);
	}

	private static void assertHandlersRegistered(final String registrationKey)
	{
		assertThatThrownBy(() -> registry().getValuesForModel(registrationKey, PROBE_MODEL))
				.as("precondition: a handler is registered for %s", registrationKey)
				.isInstanceOf(ClassCastException.class);
	}

	private static void assertNoHandlersRegistered(final String registrationKey)
	{
		final List<Object> values;
		try
		{
			values = registry().getValuesForModel(registrationKey, PROBE_MODEL);
		}
		catch (final ClassCastException e)
		{
			fail("A handler leaked from the previous test is still registered for " + registrationKey, e);
			return;
		}
		assertThat(values).as("values for %s", registrationKey).isEmpty();
	}

	private static IAggregationKeyRegistry registry() {return Services.get(IAggregationKeyRegistry.class);}
}
