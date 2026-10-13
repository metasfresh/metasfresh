package de.metas.contracts.subscription.impl;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.common.util.time.SystemTime;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.contracts.subscription.ISubscriptionDAO;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.impl.ShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;

public class SubscriptionBL_EvalDeliveries_Test
{
	private final Timestamp now = SystemTime.asTimestamp();
	private SubscriptionBL subscriptionBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		subscriptionBL = new SubscriptionBL();
	}

	private I_C_Flatrate_Term term(final String productType)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setProductType(productType);
		saveRecord(product);
		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setM_Product_ID(product.getM_Product_ID());
		saveRecord(term);
		return term;
	}

	private I_C_Flatrate_Term itemTerm()
	{
		return term(X_M_Product.PRODUCTTYPE_Item);
	}

	private I_C_SubscriptionProgress entry(final I_C_Flatrate_Term term, final String status, final int eventDateDaysFromNow, final int seqNo)
	{
		final I_C_SubscriptionProgress sp = newInstance(I_C_SubscriptionProgress.class);
		sp.setC_Flatrate_Term_ID(term.getC_Flatrate_Term_ID());
		sp.setEventType(X_C_SubscriptionProgress.EVENTTYPE_Delivery);
		sp.setStatus(status);
		sp.setEventDate(TimeUtil.addDays(now, eventDateDaysFromNow));
		sp.setSeqNo(seqNo);
		saveRecord(sp);
		return sp;
	}

	private I_M_ShipmentSchedule schedule(final I_C_SubscriptionProgress entry, final boolean processed)
	{
		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setAD_Table_ID(getTableId(I_C_SubscriptionProgress.class));
		schedule.setRecord_ID(entry.getC_SubscriptionProgress_ID());
		schedule.setProcessed(processed);
		saveRecord(schedule);
		return schedule;
	}

	private String statusOf(final I_C_SubscriptionProgress entry)
	{
		refresh(entry);
		return entry.getStatus();
	}

	private Stream<I_C_SubscriptionProgress> stream(final int pageSize)
	{
		return Services.get(ISubscriptionDAO.class)
				.streamPlannedAndDelayedDeliveries(Env.getCtx(), now, ITrx.TRXNAME_ThreadInherited, pageSize);
	}

	// ------------------------------------------------------------------------------------------
	// fixtures shared by the "via evalDeliveries(ctx)" and the "across batches" variants

	private List<I_C_SubscriptionProgress> tc2Fixture_contractAEntries_thenContractB_b1()
	{
		final I_C_Flatrate_Term termA = itemTerm();
		final I_C_Flatrate_Term termB = itemTerm();
		final I_C_SubscriptionProgress a1 = entry(termA, X_C_SubscriptionProgress.STATUS_Planned, -3, 1);
		final I_C_SubscriptionProgress a2 = entry(termA, X_C_SubscriptionProgress.STATUS_Planned, -2, 2);
		final I_C_SubscriptionProgress a3 = entry(termA, X_C_SubscriptionProgress.STATUS_Planned, -1, 3);
		final I_C_SubscriptionProgress b1 = entry(termB, X_C_SubscriptionProgress.STATUS_Planned, -2, 1);
		schedule(a1, false);
		return ImmutableList.of(a1, a2, a3, b1);
	}

	private void assertTc2(final List<I_C_SubscriptionProgress> entries)
	{
		assertThat(statusOf(entries.get(0))).isEqualTo(X_C_SubscriptionProgress.STATUS_Delayed);
		assertThat(statusOf(entries.get(1))).isEqualTo(X_C_SubscriptionProgress.STATUS_Delayed);
		assertThat(statusOf(entries.get(2))).isEqualTo(X_C_SubscriptionProgress.STATUS_Delayed);
		assertThat(statusOf(entries.get(3))).isEqualTo(X_C_SubscriptionProgress.STATUS_Planned);
	}

	private List<I_C_SubscriptionProgress> tc4Fixture_fiveDelayedEntriesWithoutSchedules()
	{
		final List<I_C_SubscriptionProgress> entries = new ArrayList<>();
		for (int i = 0; i < 5; i++)
		{
			entries.add(entry(itemTerm(), X_C_SubscriptionProgress.STATUS_Delayed, -1, 1));
		}
		return entries;
	}

	private void assertAllPlanned(final List<I_C_SubscriptionProgress> entries)
	{
		for (final I_C_SubscriptionProgress entry : entries)
		{
			assertThat(statusOf(entry)).isEqualTo(X_C_SubscriptionProgress.STATUS_Planned);
		}
	}

	// ------------------------------------------------------------------------------------------
	// characterisation (green on the old code too)

	@Test // TC2
	public void openSchedule_delaysEntryAndLaterEntriesOfContract()
	{
		final List<I_C_SubscriptionProgress> entries = tc2Fixture_contractAEntries_thenContractB_b1();

		subscriptionBL.evalDeliveries(Env.getCtx());

		assertTc2(entries);
	}

	@Test // TC3
	public void delayedWithoutOpenSchedule_backToPlanned()
	{
		final I_C_SubscriptionProgress entry = entry(itemTerm(), X_C_SubscriptionProgress.STATUS_Delayed, -1, 1);
		schedule(entry, true); // processed => not open

		subscriptionBL.evalDeliveries(Env.getCtx());

		assertThat(statusOf(entry)).isEqualTo(X_C_SubscriptionProgress.STATUS_Planned);
	}

	@Test // TC4
	public void allEntriesProcessed()
	{
		final List<I_C_SubscriptionProgress> entries = tc4Fixture_fiveDelayedEntriesWithoutSchedules();

		subscriptionBL.evalDeliveries(Env.getCtx());

		assertAllPlanned(entries);
	}

	// ------------------------------------------------------------------------------------------
	// new behavior

	@Test // TC1
	public void serviceProductEntries_untouched()
	{
		final I_C_Flatrate_Term serviceTerm = term(X_M_Product.PRODUCTTYPE_Service);
		final I_C_SubscriptionProgress p1 = entry(serviceTerm, X_C_SubscriptionProgress.STATUS_Planned, -4, 1);
		final I_C_SubscriptionProgress p2 = entry(serviceTerm, X_C_SubscriptionProgress.STATUS_Planned, -3, 2);
		final I_C_SubscriptionProgress p3 = entry(serviceTerm, X_C_SubscriptionProgress.STATUS_Planned, -2, 3);
		final I_C_SubscriptionProgress delayed = entry(serviceTerm, X_C_SubscriptionProgress.STATUS_Delayed, -1, 4);

		subscriptionBL.evalDeliveries(Env.getCtx());

		assertThat(statusOf(p1)).isEqualTo(X_C_SubscriptionProgress.STATUS_Planned);
		assertThat(statusOf(p2)).isEqualTo(X_C_SubscriptionProgress.STATUS_Planned);
		assertThat(statusOf(p3)).isEqualTo(X_C_SubscriptionProgress.STATUS_Planned);
		assertThat(statusOf(delayed)).isEqualTo(X_C_SubscriptionProgress.STATUS_Delayed);
	}

	@Test // TC2 across batches (Review Focus 1)
	public void openSchedule_delaysEntryAndLaterEntriesOfContract_acrossBatches()
	{
		final List<I_C_SubscriptionProgress> entries = tc2Fixture_contractAEntries_thenContractB_b1();

		subscriptionBL.evalDeliveries(stream(2), 2); // order: A1, A2 | B1, A3 => A3 is in a later batch than A1

		assertTc2(entries);
	}

	@Test // TC4 across batches, last partial batch (Review Focus 2)
	public void allEntriesProcessed_acrossBatches()
	{
		final List<I_C_SubscriptionProgress> entries = tc4Fixture_fiveDelayedEntriesWithoutSchedules();

		subscriptionBL.evalDeliveries(stream(2), 2); // 2 + 2 + 1

		assertAllPlanned(entries);
	}

	@Test // TC6
	public void oneScheduleLookupPerBatch()
	{
		final CountingShipmentSchedulePA countingPA = new CountingShipmentSchedulePA();
		Services.registerService(IShipmentSchedulePA.class, countingPA);
		final List<I_C_SubscriptionProgress> entries = fivePlannedEntries();

		subscriptionBL.evalDeliveries(stream(2), 2);

		assertThat(countingPA.lookupCount.get()).isEqualTo(3);
		for (final I_C_SubscriptionProgress entry : entries)
		{
			assertThat(statusOf(entry)).isEqualTo(X_C_SubscriptionProgress.STATUS_Planned);
		}
	}

	@Test // TC7(a): the step does not collect the stream into a list
	public void holdsAtMostOneBatch()
	{
		final List<I_C_SubscriptionProgress> entries = fivePlannedEntries();

		final AtomicInteger pulled = new AtomicInteger();
		final Iterator<I_C_SubscriptionProgress> sourceIterator = entries.iterator();
		final Iterator<I_C_SubscriptionProgress> countingIterator = new Iterator<I_C_SubscriptionProgress>()
		{
			@Override
			public boolean hasNext()
			{
				return sourceIterator.hasNext();
			}

			@Override
			public I_C_SubscriptionProgress next()
			{
				pulled.incrementAndGet();
				return sourceIterator.next();
			}
		};

		final List<Integer> pulledAtLookup = new ArrayList<>();
		Services.registerService(IShipmentSchedulePA.class, new CountingShipmentSchedulePA()
		{
			@Override
			public ImmutableSet<Integer> retrieveRecordIdsWithUnprocessedSchedules(final int adTableId, final Collection<Integer> recordIds)
			{
				pulledAtLookup.add(pulled.get());
				return super.retrieveRecordIdsWithUnprocessedSchedules(adTableId, recordIds);
			}
		});

		final Stream<I_C_SubscriptionProgress> stream = StreamSupport.stream(
				Spliterators.spliteratorUnknownSize(countingIterator, Spliterator.ORDERED), false);
		subscriptionBL.evalDeliveries(stream, 2);

		assertThat(pulledAtLookup).hasSize(3);
		for (int k = 1; k <= pulledAtLookup.size(); k++)
		{
			assertThat(pulledAtLookup.get(k - 1)).as("entries pulled at lookup #" + k).isLessThanOrEqualTo(2 * k);
		}
		assertThat(pulled.get()).isEqualTo(5);
	}

	private List<I_C_SubscriptionProgress> fivePlannedEntries()
	{
		final List<I_C_SubscriptionProgress> entries = new ArrayList<>();
		for (int i = 0; i < 5; i++)
		{
			entries.add(entry(itemTerm(), X_C_SubscriptionProgress.STATUS_Planned, -1, 1));
		}
		return entries;
	}

	private static class CountingShipmentSchedulePA extends ShipmentSchedulePA
	{
		final AtomicInteger lookupCount = new AtomicInteger();

		@Override
		public ImmutableSet<Integer> retrieveRecordIdsWithUnprocessedSchedules(final int adTableId, final Collection<Integer> recordIds)
		{
			lookupCount.incrementAndGet();
			return super.retrieveRecordIdsWithUnprocessedSchedules(adTableId, recordIds);
		}
	}
}
