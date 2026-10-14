package de.metas.contracts.subscription.impl;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.Closeable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
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
import de.metas.contracts.impl.PlainSubscriptionDAO;
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

	/**
	 * {@link SubscriptionBL} gets its services when constructed, so register the replacement first.
	 */
	private void registerAndRecreateSubscriptionBL(final IShipmentSchedulePA shipmentSchedulePA)
	{
		Services.registerService(IShipmentSchedulePA.class, shipmentSchedulePA);
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

	private Iterator<I_C_SubscriptionProgress> deliveries(final int pageSize)
	{
		return Services.get(ISubscriptionDAO.class)
				.retrievePlannedAndDelayedDeliveries(Env.getCtx(), now, ITrx.TRXNAME_ThreadInherited, pageSize);
	}

	/**
	 * @return term A's entries A1, A2, A3 (open schedule on A1), then term B's entry B1
	 */
	private List<I_C_SubscriptionProgress> termAWithOpenScheduleOnFirstEntry_andTermB()
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

	private void assertTermAEntriesDelayed_termBEntryPlanned(final List<I_C_SubscriptionProgress> entries)
	{
		assertThat(statusOf(entries.get(0))).isEqualTo(X_C_SubscriptionProgress.STATUS_Delayed);
		assertThat(statusOf(entries.get(1))).isEqualTo(X_C_SubscriptionProgress.STATUS_Delayed);
		assertThat(statusOf(entries.get(2))).isEqualTo(X_C_SubscriptionProgress.STATUS_Delayed);
		assertThat(statusOf(entries.get(3))).isEqualTo(X_C_SubscriptionProgress.STATUS_Planned);
	}

	private List<I_C_SubscriptionProgress> fiveDelayedEntriesWithoutSchedules()
	{
		return fiveEntries(X_C_SubscriptionProgress.STATUS_Delayed);
	}

	private List<I_C_SubscriptionProgress> fivePlannedEntries()
	{
		return fiveEntries(X_C_SubscriptionProgress.STATUS_Planned);
	}

	private List<I_C_SubscriptionProgress> fiveEntries(final String status)
	{
		final List<I_C_SubscriptionProgress> entries = new ArrayList<>();
		for (int i = 0; i < 5; i++)
		{
			entries.add(entry(itemTerm(), status, -1, 1));
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

	@Test
	public void openSchedule_delaysEntryAndLaterEntriesOfContract()
	{
		final List<I_C_SubscriptionProgress> entries = termAWithOpenScheduleOnFirstEntry_andTermB();

		subscriptionBL.evalDeliveries(Env.getCtx());

		assertTermAEntriesDelayed_termBEntryPlanned(entries);
	}

	@Test
	public void delayedWithoutOpenSchedule_backToPlanned()
	{
		final I_C_SubscriptionProgress entry = entry(itemTerm(), X_C_SubscriptionProgress.STATUS_Delayed, -1, 1);
		schedule(entry, true); // processed => not open

		subscriptionBL.evalDeliveries(Env.getCtx());

		assertThat(statusOf(entry)).isEqualTo(X_C_SubscriptionProgress.STATUS_Planned);
	}

	@Test
	public void delayedWithoutSchedules_allBackToPlanned()
	{
		final List<I_C_SubscriptionProgress> entries = fiveDelayedEntriesWithoutSchedules();

		subscriptionBL.evalDeliveries(Env.getCtx());

		assertAllPlanned(entries);
	}

	@Test
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

	@Test
	public void openSchedule_delaysEntryAndLaterEntriesOfContract_acrossBatches()
	{
		final List<I_C_SubscriptionProgress> entries = termAWithOpenScheduleOnFirstEntry_andTermB();

		subscriptionBL.evalDeliveries(deliveries(2), 2); // batches [A1, A2], [B1, A3]

		assertTermAEntriesDelayed_termBEntryPlanned(entries);
	}

	@Test
	public void delayedWithoutSchedules_allBackToPlanned_acrossBatches()
	{
		final List<I_C_SubscriptionProgress> entries = fiveDelayedEntriesWithoutSchedules();

		subscriptionBL.evalDeliveries(deliveries(2), 2); // 2 + 2 + 1

		assertAllPlanned(entries);
	}

	@Test
	public void oneScheduleLookupPerBatch()
	{
		final CountingShipmentSchedulePA countingPA = new CountingShipmentSchedulePA();
		registerAndRecreateSubscriptionBL(countingPA);
		final List<I_C_SubscriptionProgress> entries = fivePlannedEntries();

		subscriptionBL.evalDeliveries(deliveries(2), 2);

		assertThat(countingPA.lookupCount.get()).isEqualTo(3);
		assertAllPlanned(entries);
	}

	@Test
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
		registerAndRecreateSubscriptionBL(new CountingShipmentSchedulePA()
		{
			@Override
			public ImmutableSet<Integer> retrieveRecordIdsWithUnprocessedSchedules(final int adTableId, final Collection<Integer> recordIds)
			{
				pulledAtLookup.add(pulled.get());
				return super.retrieveRecordIdsWithUnprocessedSchedules(adTableId, recordIds);
			}
		});

		subscriptionBL.evalDeliveries(countingIterator, 2);

		assertThat(pulledAtLookup).hasSize(3);
		for (int k = 1; k <= pulledAtLookup.size(); k++)
		{
			assertThat(pulledAtLookup.get(k - 1)).as("entries pulled at lookup #" + k).isLessThanOrEqualTo(2 * k);
		}
		assertThat(pulled.get()).isEqualTo(5);
	}

	@Test
	public void processingFails_deliveriesIteratorIsClosed()
	{
		fivePlannedEntries();
		final CloseTrackingSubscriptionDAO subscriptionDAO = new CloseTrackingSubscriptionDAO();
		Services.registerService(ISubscriptionDAO.class, subscriptionDAO);
		registerAndRecreateSubscriptionBL(new ShipmentSchedulePA()
		{
			@Override
			public ImmutableSet<Integer> retrieveRecordIdsWithUnprocessedSchedules(final int adTableId, final Collection<Integer> recordIds)
			{
				throw new AdempiereException("schedule lookup failed");
			}
		});

		assertThatThrownBy(() -> subscriptionBL.evalDeliveries(Env.getCtx()))
				.hasMessageContaining("schedule lookup failed");
		assertThat(subscriptionDAO.closeCount.get()).isEqualTo(1);
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

	private static class CloseTrackingSubscriptionDAO extends PlainSubscriptionDAO
	{
		final AtomicInteger closeCount = new AtomicInteger();

		@Override
		public Iterator<I_C_SubscriptionProgress> retrievePlannedAndDelayedDeliveries(final Properties ctx, final Timestamp date, final String trxName, final int pageSize)
		{
			return new CloseTrackingIterator(super.retrievePlannedAndDelayedDeliveries(ctx, date, trxName, pageSize), closeCount);
		}
	}

	private static class CloseTrackingIterator implements Iterator<I_C_SubscriptionProgress>, Closeable
	{
		private final Iterator<I_C_SubscriptionProgress> delegate;
		private final AtomicInteger closeCount;

		CloseTrackingIterator(final Iterator<I_C_SubscriptionProgress> delegate, final AtomicInteger closeCount)
		{
			this.delegate = delegate;
			this.closeCount = closeCount;
		}

		@Override
		public boolean hasNext()
		{
			return delegate.hasNext();
		}

		@Override
		public I_C_SubscriptionProgress next()
		{
			return delegate.next();
		}

		@Override
		public void close()
		{
			closeCount.incrementAndGet();
		}
	}
}
