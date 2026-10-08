/*
 * #%L
 * metasfresh-material-event
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

package de.metas.material.event;

import com.google.common.collect.ImmutableList;
import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.Topic;
import de.metas.event.log.EventLogEntryCollector;
import de.metas.event.log.EventLogUserService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.eventbus.MaterialEventConverter;
import de.metas.material.event.eventbus.MetasfreshEventBusService;
import de.metas.material.event.simulation.DeactivateAllSimulatedCandidatesEvent;
import de.metas.material.event.tracking.AllEventsProcessedEvent;
import de.metas.material.event.tracking.AllEventsProcessedEventHandler;
import de.metas.material.event.tracking.EventProgress;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MaterialEventObserver}.
 */
public class MaterialEventObserverTest
{
	private MaterialEventObserver materialEventObserver;

	private RecordingPostMaterialEventService postMaterialEventService;

	@BeforeEach
	void init()
	{
		// needed because MaterialEventObserver resolves ITrxManager/ISysConfigBL via Services.get(..) in its field initializers,
		// and uses trxManager.getCurrentTrxListenerManagerOrAutoCommit() internally.
		AdempiereTestHelper.get().init();

		postMaterialEventService = new RecordingPostMaterialEventService();
		SpringContextHolder.registerJUnitBean(PostMaterialEventService.class, postMaterialEventService);

		materialEventObserver = new MaterialEventObserver();
	}

	@Test
	void reportEventProcessed_unobservedTrace_postsNothing()
	{
		// given: a traceId that was never passed to observe(traceId)
		final String traceId = "never-observed-trace-id";
		final EventDescriptor eventDescriptor = EventDescriptor.ofClientOrgAndTraceId(
				EventTestHelper.CLIENT_AND_ORG_ID,
				traceId);
		// deliberately an ordinary work event, not AllEventsProcessedEvent: this test is about the *unknown trace*,
		// so it must not pass merely because some particular event type is special-cased.
		final MaterialEvent processedEvent = DeactivateAllSimulatedCandidatesEvent.builder()
				.eventDescriptor(eventDescriptor)
				.build();

		// when
		materialEventObserver.reportEventProcessed(processedEvent);

		// then: nothing was posted
		assertThat(postMaterialEventService.getPostedEvents()).isEmpty();

		// and: the tracker holds no entry for that traceId
		assertThat(getTraceId2EventProgress(materialEventObserver)).doesNotContainKey(traceId);
	}

	/**
	 * {@link MaterialEventHandlerRegistry#onEvent(MaterialEvent)} calls {@code reportEventProcessed} for every
	 * event class that has at least one registered handler -- including {@link AllEventsProcessedEvent} itself, which
	 * always has the (real, production) {@link AllEventsProcessedEventHandler} registered. That bookkeeping event is
	 * not "work" that was ever enqueued for the trace; counting it as processed work pollutes
	 * {@link de.metas.material.event.tracking.EventProgress} with an entry the trace never asked about.
	 */
	@Test
	void allEventsProcessedEvent_isNotCountedAsWork()
	{
		// given: an observed trace with one ordinary work event still outstanding (enqueued, not yet processed)
		final String traceId = "observed-trace-with-outstanding-work";
		materialEventObserver.observe(traceId);

		final MaterialEvent workEvent = DeactivateAllSimulatedCandidatesEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientOrgAndTraceId(EventTestHelper.CLIENT_AND_ORG_ID, traceId))
				.build();
		materialEventObserver.reportEventEnqueued(workEvent);

		// and: a registry with the real AllEventsProcessedEventHandler registered, so dispatch is not vacuous
		final MaterialEventHandlerRegistry registry = new MaterialEventHandlerRegistry(
				Optional.of(ImmutableList.of(new AllEventsProcessedEventHandler(materialEventObserver))),
				new EventLogUserService(),
				materialEventObserver);

		final AllEventsProcessedEvent allEventsProcessedEvent = AllEventsProcessedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientOrgAndTraceId(EventTestHelper.CLIENT_AND_ORG_ID, traceId))
				.build();

		// when: the bookkeeping event is delivered through the registry, exactly like the real event bus would do
		deliverThroughRegistry(registry, allEventsProcessedEvent);

		// then: the handler still ran (proves dispatch is unaffected): it completed the trace's future
		final EventProgress eventProgress = getTraceId2EventProgress(materialEventObserver).get(traceId);
		assertThat(eventProgress).isNotNull();
		assertThat(eventProgress.getCompletableFuture()).isDone();

		// and: the bookkeeping event's own eventId was NOT recorded as tracked work for that trace
		assertThat(eventProgress.getEventId2Status()).doesNotContainKey(allEventsProcessedEvent.getEventId());

		// and: the trace is not treated as complete (the outstanding work event was never processed)
		assertThat(eventProgress.areAllEventsProcessed()).isFalse();
	}

	/**
	 * {@link EventProgress#areAllEventsProcessed()} is {@code eventId2Status.values().stream().allMatch(...)},
	 * which is vacuously {@code true} on an empty map. A progress for which nothing was ever enqueued must not be
	 * reported as "all processed".
	 */
	@Test
	void emptyProgress_isNotAllProcessed()
	{
		// a freshly observed trace's progress, before anything was enqueued for it
		assertThat(new EventProgress().areAllEventsProcessed()).isFalse();
	}

	/**
	 * Regression test for the normal completion path. Observes a trace, enqueues two ordinary work events,
	 * reports both processed, and verifies: exactly one completion signal is posted, the caller awaiting that trace
	 * is released, and -- since {@link MaterialEventObserver#awaitProcessing(String)} removes the entry in its
	 * {@code finally} block -- the tracking entry is gone afterwards.
	 * <p>
	 * The completion signal ({@link AllEventsProcessedEvent}) only releases the awaiter once it round-trips back
	 * through {@link MaterialEventHandlerRegistry#onEvent(MaterialEvent)} (as it would via the real, distributed
	 * event bus in production) and reaches the real {@link AllEventsProcessedEventHandler}. This test simulates that
	 * round-trip explicitly instead of asserting it away.
	 */
	@Test
	void normalCompletionPath_releasesAwaiterAndPostsOnce() throws Exception
	{
		// given: an observed trace with two outstanding work events
		final String traceId = "observed-trace-two-work-events";
		materialEventObserver.observe(traceId);

		final MaterialEvent workEvent1 = DeactivateAllSimulatedCandidatesEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientOrgAndTraceId(EventTestHelper.CLIENT_AND_ORG_ID, traceId))
				.build();
		final MaterialEvent workEvent2 = DeactivateAllSimulatedCandidatesEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientOrgAndTraceId(EventTestHelper.CLIENT_AND_ORG_ID, traceId))
				.build();

		materialEventObserver.reportEventEnqueued(workEvent1);
		materialEventObserver.reportEventEnqueued(workEvent2);

		final EventProgress eventProgress = getTraceId2EventProgress(materialEventObserver).get(traceId);
		assertThat(eventProgress).isNotNull();

		// and: a caller awaiting that trace's completion, on its own thread (awaitProcessing blocks)
		final CountDownLatch awaiterReleased = new CountDownLatch(1);
		final Thread awaiterThread = new Thread(() -> {
			materialEventObserver.awaitProcessing(traceId);
			awaiterReleased.countDown();
		});
		// daemon: in the very regression this test guards against (awaiter never released) the latch below fails fast,
		// but this thread would stay blocked in awaitProcessing for the full WaitTimeOutMS (5 min by default).
		awaiterThread.setDaemon(true);
		awaiterThread.start();

		// when: both outstanding work events are reported processed
		materialEventObserver.reportEventProcessed(workEvent1);
		materialEventObserver.reportEventProcessed(workEvent2);

		// then: exactly one completion signal was posted to the bus, for this trace
		final List<MaterialEvent> postedEvents = postMaterialEventService.getPostedEvents();
		assertThat(postedEvents).hasSize(1);
		assertThat(postedEvents.get(0)).isInstanceOf(AllEventsProcessedEvent.class);
		final AllEventsProcessedEvent allEventsProcessedEvent = (AllEventsProcessedEvent)postedEvents.get(0);
		assertThat(allEventsProcessedEvent.getTraceId()).isEqualTo(traceId);

		// and: the awaiter is NOT released yet -- posting to the bus alone doesn't complete the local future;
		// only the round-trip delivery back through the registry does (see below)
		assertThat(eventProgress.getCompletableFuture()).isNotDone();

		// when: the completion signal is delivered back, exactly as the real (distributed) event bus would do
		final MaterialEventHandlerRegistry registry = new MaterialEventHandlerRegistry(
				Optional.of(ImmutableList.of(new AllEventsProcessedEventHandler(materialEventObserver))),
				new EventLogUserService(),
				materialEventObserver);
		deliverThroughRegistry(registry, allEventsProcessedEvent);

		// then: the awaiting caller is released
		assertThat(awaiterReleased.await(10, TimeUnit.SECONDS)).isTrue();
		awaiterThread.join(TimeUnit.SECONDS.toMillis(10));

		// and: delivering the completion signal back did not cause a repost (still exactly one)
		assertThat(postMaterialEventService.getPostedEvents()).hasSize(1);

		// and: the tracking entry was removed once awaitProcessing returned
		assertThat(getTraceId2EventProgress(materialEventObserver)).doesNotContainKey(traceId);
	}

	/**
	 * Delivers {@code event} through {@code registry.onEvent(event)} the same way the real (distributed) event bus
	 * would: {@link de.metas.event.impl.EventBus} marks a to-be-logged event as "was logged" and wraps delivery with
	 * an {@link EventLogEntryCollector} thread-local before invoking the listener -- {@link EventLogUserService}
	 * (used inside {@link MaterialEventHandlerRegistry#onEvent(MaterialEvent)}) requires that thread-local to exist.
	 */
	private static void deliverThroughRegistry(
			@NonNull final MaterialEventHandlerRegistry registry,
			@NonNull final MaterialEvent event)
	{
		final Event busEvent = new MaterialEventConverter().fromMaterialEvent(event).withStatusWasLogged();
		try (final EventLogEntryCollector ignored = EventLogEntryCollector.createThreadLocalForEvent(busEvent))
		{
			registry.onEvent(event);
		}
	}

	/**
	 * {@link MaterialEventObserver} does not expose an accessor for its internal {@code traceId2EventProgress} map,
	 * and we must not add one to production code for this test. We therefore read the private field via reflection,
	 * following the same pattern already used elsewhere in this codebase's tests (e.g. {@code ServicesTest}).
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, EventProgress> getTraceId2EventProgress(@NonNull final MaterialEventObserver observer)
	{
		try
		{
			final Field field = MaterialEventObserver.class.getDeclaredField("traceId2EventProgress");
			field.setAccessible(true);
			return (Map<String, EventProgress>)field.get(observer);
		}
		catch (final ReflectiveOperationException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * A recording fake of {@link PostMaterialEventService}: captures every event that production code would otherwise
	 * post to the distributed event bus, instead of actually posting it.
	 * <p>
	 * We don't use Mockito here (not a test dependency of this module) -- {@link PostMaterialEventService} is a
	 * non-final class with non-final public methods, so a plain subclass override is enough.
	 */
	private static class RecordingPostMaterialEventService extends PostMaterialEventService
	{
		private final List<MaterialEvent> postedEvents = new ArrayList<>();

		public RecordingPostMaterialEventService()
		{
			super(createDummyMetasfreshEventBusService());
		}

		@Override
		public void enqueueEventAfterNextCommit(@NonNull final MaterialEvent event)
		{
			postedEvents.add(event);
		}

		public List<MaterialEvent> getPostedEvents()
		{
			return postedEvents;
		}

		/**
		 * {@link PostMaterialEventService}'s constructor requires a non-null {@link MetasfreshEventBusService}
		 * (it's {@code final} with a private constructor, so it can't be subclassed/faked directly). Since we
		 * override {@link #enqueueEventAfterNextCommit(MaterialEvent)} above and never delegate to the real
		 * implementation, none of its methods are ever invoked; it merely needs to exist to satisfy the constructor.
		 */
		private static MetasfreshEventBusService createDummyMetasfreshEventBusService()
		{
			final IEventBusFactory unusedEventBusFactory = new IEventBusFactory()
			{
				@Override
				public IEventBus getEventBus(final Topic topic) {throw new UnsupportedOperationException();}

				@Override
				public IEventBus getEventBusIfExists(final Topic topic) {throw new UnsupportedOperationException();}

				@Override
				public List<IEventBus> getAllEventBusInstances() {throw new UnsupportedOperationException();}

				@Override
				public void initEventBussesWithGlobalListeners() {throw new UnsupportedOperationException();}

				@Override
				public void destroyAllEventBusses() {throw new UnsupportedOperationException();}

				@Override
				public void registerGlobalEventListener(final Topic topic, final IEventListener listener) {throw new UnsupportedOperationException();}

				@Override
				public void addAvailableUserNotificationsTopic(final Topic topic) {throw new UnsupportedOperationException();}

				@Override
				public void registerUserNotificationsListener(final IEventListener listener) {throw new UnsupportedOperationException();}

				@Override
				public void unregisterUserNotificationsListener(final IEventListener listener) {throw new UnsupportedOperationException();}
			};

			return MetasfreshEventBusService.createLocalServiceThatIsReadyToUse(
					new MaterialEventConverter(),
					unusedEventBusFactory,
					new MaterialEventObserver());
		}
	}
}
