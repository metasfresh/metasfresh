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
import de.metas.event.impl.ChannelRecordingRabbitTemplateFactory;
import de.metas.event.impl.ChannelRecordingRabbitTemplateFactory.Publish;
import de.metas.event.impl.EventBusFactory;
import de.metas.event.impl.EventBusMonitoringService;
import de.metas.event.impl.RabbitMQEnqueuer;
import de.metas.event.log.EventLogService;
import de.metas.event.log.EventLogsRepository;
import de.metas.event.remote.rabbitmq.RabbitMQDestinationResolver;
import de.metas.event.remote.rabbitmq.queues.default_queue.DefaultQueueConfiguration;
import de.metas.event.remote.rabbitmq.queues.material_dispo.MaterialEventsQueueConfiguration;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.eventbus.MaterialEventConverter;
import de.metas.material.event.eventbus.MetasfreshEventBusService;
import de.metas.material.event.simulation.DeactivateAllSimulatedCandidatesEvent;
import de.metas.material.event.tracking.AllEventsProcessedEvent;
import de.metas.monitoring.adapter.NoopPerformanceMonitoringService;
import de.metas.util.Services;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Material events of one commit must reach the (single, sequential) consumer in the order they were posted.
 * RabbitMQ only keeps the order within one channel, so they have to be sent on one channel.
 */
class PostMaterialEventServiceTest
{
	private ChannelRecordingRabbitTemplateFactory broker;
	private PostMaterialEventService postMaterialEventService;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		final MaterialEventConverter materialEventConverter = new MaterialEventConverter();

		broker = new ChannelRecordingRabbitTemplateFactory();
		final RabbitMQEnqueuer rabbitMQEnqueuer = new RabbitMQEnqueuer(
				broker.newRabbitTemplate(sentObject -> materialEventConverter.toMaterialEvent((Event)sentObject).getEventId()),
				new RabbitMQDestinationResolver(ImmutableList.of(new DefaultQueueConfiguration(), new MaterialEventsQueueConfiguration())));

		final EventBusFactory eventBusFactory = new EventBusFactory(
				new SimpleMeterRegistry(),
				rabbitMQEnqueuer,
				new EventBusMonitoringService(NoopPerformanceMonitoringService.INSTANCE),
				new EventLogService(new EventLogsRepository()));

		postMaterialEventService = new PostMaterialEventService(MetasfreshEventBusService.createDistributedServiceThatNeedsToSubscribe(
				materialEventConverter,
				eventBusFactory,
				new MaterialEventObserver()));
	}

	@Test
	void eventsOfOneCommit_areSentOnOneChannel_inTheOrderTheyWerePosted()
	{
		// given: three events of two different types, posted in one trx
		final MaterialEvent firstOfTypeA = newDeactivateAllSimulatedCandidatesEvent();
		final MaterialEvent onlyOfTypeB = newAllEventsProcessedEvent();
		final MaterialEvent secondOfTypeA = newDeactivateAllSimulatedCandidatesEvent();

		// when
		Services.get(ITrxManager.class).runInNewTrx(() -> {
			postMaterialEventService.enqueueEventAfterNextCommit(firstOfTypeA);
			postMaterialEventService.enqueueEventAfterNextCommit(onlyOfTypeB);
			postMaterialEventService.enqueueEventAfterNextCommit(secondOfTypeA);

			assertThat(broker.getPublishes()).as("nothing is sent before the commit").isEmpty();
		});

		// then
		final ImmutableList<Publish> publishes = broker.getPublishes();
		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(publishes)
				.as("sent in the order they were posted")
				.extracting(Publish::getBody)
				.containsExactly(firstOfTypeA.getEventId(), onlyOfTypeB.getEventId(), secondOfTypeA.getEventId());
		softly.assertThat(publishes)
				.as("all events of the commit are sent on one channel, so the broker keeps their order")
				.extracting(Publish::getChannelNumber)
				.containsOnly(publishes.get(0).getChannelNumber());
		softly.assertAll();
	}

	/**
	 * A trx can be committed more than once, e.g. by a process that commits after each record.
	 * After a commit, the next DB access starts the same trx again ({@code Trx.getConnection()}).
	 * The events posted after one commit must go out after the next one.
	 */
	@Test
	void trxCommittedTwice_eventsPostedAfterTheFirstCommit_areSentAfterTheSecondCommit()
	{
		final MaterialEvent beforeFirstCommit = newDeactivateAllSimulatedCandidatesEvent();
		final MaterialEvent afterFirstCommit_sameType = newDeactivateAllSimulatedCandidatesEvent();
		final MaterialEvent afterFirstCommit_otherType = newAllEventsProcessedEvent();

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.runInNewTrx(localTrxName -> {
			postMaterialEventService.enqueueEventAfterNextCommit(beforeFirstCommit);
			trxManager.commit(localTrxName);
			assertThat(broker.getPublishes()).extracting(Publish::getBody).containsExactly(beforeFirstCommit.getEventId());
			trxManager.getTrx(localTrxName).start(); // what the next DB access does

			postMaterialEventService.enqueueEventAfterNextCommit(afterFirstCommit_otherType);
			postMaterialEventService.enqueueEventAfterNextCommit(afterFirstCommit_sameType);
		});

		assertThat(broker.getPublishes()).extracting(Publish::getBody)
				.containsExactly(beforeFirstCommit.getEventId(), afterFirstCommit_otherType.getEventId(), afterFirstCommit_sameType.getEventId());
	}

	@Test
	void eventPostedWithoutTrx_isSentRightAway()
	{
		final MaterialEvent event = newDeactivateAllSimulatedCandidatesEvent();

		postMaterialEventService.enqueueEventAfterNextCommit(event);

		assertThat(broker.getPublishes()).extracting(Publish::getBody).containsExactly(event.getEventId());
	}

	private static MaterialEvent newDeactivateAllSimulatedCandidatesEvent()
	{
		return DeactivateAllSimulatedCandidatesEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(EventTestHelper.CLIENT_AND_ORG_ID))
				.build();
	}

	private static MaterialEvent newAllEventsProcessedEvent()
	{
		return AllEventsProcessedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(EventTestHelper.CLIENT_AND_ORG_ID))
				.build();
	}
}
