package de.metas.procurement.webui.sync;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import de.metas.common.procurement.sync.protocol.dto.SyncProductSupply;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQPriceChangeEvent;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQQtyChangeEvent;
import de.metas.common.procurement.sync.protocol.dto.SyncWeeklySupply;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.GetAllBPartnersRequest;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.GetAllProductsRequest;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.GetInfoMessageRequest;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.PutProductSuppliesRequest;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.PutWeeklySupplyRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutRfQChangeRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutRfQChangeRequest.PutRfQChangeRequestBuilder;
import de.metas.procurement.webui.model.AbstractSyncConfirmAwareEntity;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.ProductSupply;
import de.metas.procurement.webui.model.Rfq;
import de.metas.procurement.webui.model.RfqQty;
import de.metas.procurement.webui.model.SyncConfirm;
import de.metas.procurement.webui.model.WeekSupply;
import de.metas.procurement.webui.repository.SyncConfirmRepository;
import de.metas.procurement.webui.service.IProductSuppliesService;
import de.metas.procurement.webui.sync.rabbitmq.SenderToMetasfresh;
import de.metas.procurement.webui.util.EventBusLoggingSubscriberExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/*
 * #%L
 * de.metas.procurement.webui
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Service
public class SenderToMetasfreshService implements ISenderToMetasfreshService
{
	private static final Logger logger = LoggerFactory.getLogger(SenderToMetasfreshService.class);

	private final TaskExecutor taskExecutor;
	private AsyncEventBus eventBus;

	private final SenderToMetasfresh senderToMetasfresh;
	private final IProductSuppliesService productSuppliesService;
	private final SyncConfirmRepository syncConfirmRepo;

	private final CountDownLatch initialSync = new CountDownLatch(1);

	public SenderToMetasfreshService(
			@Qualifier("asyncCallsTaskExecutor") final TaskExecutor taskExecutor,
			final SenderToMetasfresh senderToMetasfresh,
			final IProductSuppliesService productSuppliesService,
			final SyncConfirmRepository syncConfirmRepo)
	{
		this.taskExecutor = taskExecutor;
		this.senderToMetasfresh = senderToMetasfresh;
		this.productSuppliesService = productSuppliesService;
		this.syncConfirmRepo = syncConfirmRepo;
	}

	@PostConstruct
	private void init()
	{
		eventBus = new AsyncEventBus(taskExecutor, EventBusLoggingSubscriberExceptionHandler.of(logger));
		eventBus.register(this);

		final Runnable callback = initialSync::countDown;
		requestFromMetasfreshAllMasterdataAsync(callback);
	}

	@Override
	public void awaitInitialSyncComplete(final long timeout, final TimeUnit unit) throws InterruptedException
	{
		//noinspection ResultOfMethodCallIgnored
		initialSync.await(timeout, unit);
	}

	/**
	 * Sends a {@link SyncAllRequest} to metasfresh. The request will be processed by {@link #process(SyncAllRequest)}.
	 */
	@Override
	public void requestFromMetasfreshAllMasterdataAsync()
	{
		requestFromMetasfreshAllMasterdataAsync(null);
	}

	/**
	 * Like {@link #requestFromMetasfreshAllMasterdataAsync()}, with a callback instance to be invoked when the sync is complete.
	 *
	 * @param callback instance whose <code>run()</code> method shall be executed after the sync, also if the sync failed.
	 */
	public void requestFromMetasfreshAllMasterdataAsync(@Nullable final Runnable callback)
	{
		final SyncAllRequest request = SyncAllRequest.of(callback);
		logger.debug("Enqueuing: {}", request);
		eventBus.post(request);
	}

	@Subscribe
	public void process(final SyncAllRequest request)
	{
		logger.debug("syncAll: Start");

		//
		logger.debug("Requesting all bpartners from metasfresh");
		try
		{
			senderToMetasfresh.send(GetAllBPartnersRequest.INSTANCE);
		}
		catch (final Exception e)
		{
			logger.error("Failed requesting all bpartners", e);
		}

		//
		logger.debug("Requesting products from metasfresh");
		try
		{
			senderToMetasfresh.send(GetAllProductsRequest.INSTANCE);
		}
		catch (final Exception e)
		{
			logger.error("Failed requesting all products", e);
		}

		//
		logger.debug("Requesting info message from metasfresh");
		try
		{
			senderToMetasfresh.send(GetInfoMessageRequest.INSTANCE);
		}
		catch (final Exception e)
		{
			logger.error("Failed requesting info message", e);
		}

		logger.debug("syncAll: All requests done");

		//
		logger.debug("Calling request's callback if any");
		request.executeCallback();
	}

	@Override
	public void pushDailyReportsAsync(final List<ProductSupply> productSupplies)
	{
		if (productSupplies == null || productSupplies.isEmpty())
		{
			logger.debug("Nothing to enqueue");
			return;
		}

		final PutProductSuppliesRequest request = toPutProductSuppliesRequest(productSupplies);
		logger.debug("Enqueuing: {}", request);
		eventBus.post(request);
	}

	@Subscribe
	public void process(final PutProductSuppliesRequest request)
	{
		try
		{
			senderToMetasfresh.send(request);
		}
		catch (final Exception e)
		{
			logger.error("Caught exception trying to process SyncProductSuppliesRequest=" + request, e);
		}
	}

	private static PutProductSuppliesRequest toPutProductSuppliesRequest(final List<ProductSupply> productSupplies)
	{
		return PutProductSuppliesRequest.builder()
				.productSupplies(productSupplies.stream()
						.map(SenderToMetasfreshService::toSyncProductSupply)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private static SyncProductSupply toSyncProductSupply(final ProductSupply productSupply)
	{
		return SyncProductSupply.builder()
				.uuid(productSupply.getUuid())
				.bpartner_uuid(productSupply.getBpartnerUUID())
				.contractLine_uuid(productSupply.getContractLine() == null ? null : productSupply.getContractLine().getUuid())
				.product_uuid(productSupply.getProductUUID())
				.day(productSupply.getDay())
				.qty(productSupply.getQty())
				.version(productSupply.getVersion())
				.syncConfirmationId(productSupply.getSyncConfirmId())
				.build();
	}

	@Override
	public void pushWeeklyReportsAsync(final List<WeekSupply> weeklySupplies)
	{
		if (weeklySupplies.isEmpty())
		{
			logger.debug("Nothing to enqueue");
			return;
		}

		final PutWeeklySupplyRequest request = toPutWeeklySupplyRequest(weeklySupplies);
		logger.debug("Enqueuing: {}", request);
		eventBus.post(request);
	}

	@Subscribe
	public void process(final PutWeeklySupplyRequest request)
	{
		try
		{
			senderToMetasfresh.send(request);
		}
		catch (final Exception e)
		{
			logger.error("Caught exception trying to process SyncWeeklySupplyRequest=" + request, e);
		}
	}

	private static PutWeeklySupplyRequest toPutWeeklySupplyRequest(final List<WeekSupply> weekSupplies)
	{
		return PutWeeklySupplyRequest.builder()
				.weeklySupplies(weekSupplies.stream()
						.map(SenderToMetasfreshService::toSyncWeeklySupply)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private static SyncWeeklySupply toSyncWeeklySupply(final WeekSupply weeklySupply)
	{
		return SyncWeeklySupply.builder()
				.uuid(weeklySupply.getUuid())
				.version(weeklySupply.getVersion())
				.bpartner_uuid(weeklySupply.getBpartnerUUID())
				.product_uuid(weeklySupply.getProductUUID())
				.weekDay(weeklySupply.getDay())
				.trend(weeklySupply.getTrendAsString())
				.syncConfirmationId(weeklySupply.getSyncConfirmId())
				.build();
	}

	@Subscribe
	public void deadLetterChannel(final DeadEvent deadEvent)
	{
		logger.warn("Got a dead event: {}", deadEvent);
	}

	public static final class SyncAllRequest
	{
		public static SyncAllRequest of()
		{
			return new SyncAllRequest(null);
		}

		public static SyncAllRequest of(@Nullable final Runnable callback)
		{
			return new SyncAllRequest(callback);
		}

		private final Runnable callback;

		private SyncAllRequest(@Nullable final Runnable callback)
		{
			this.callback = callback;
		}

		public void executeCallback()
		{
			if (callback == null)
			{
				return;
			}

			callback.run();
		}
	}

	@Override
	public void pushRfqsAsync(final List<Rfq> rfqs)
	{
		if (rfqs.isEmpty())
		{
			return;
		}

		final PutRfQChangeRequest request = toPutRfQChangeRequest(rfqs);
		if (PutRfQChangeRequest.isEmpty(request))
		{
			logger.debug("No RfQ change requests to enqueue");
			return;
		}

		logger.debug("Enqueuing: {}", request);
		eventBus.post(request);
	}

	@Subscribe
	public void process(final PutRfQChangeRequest request)
	{
		try
		{
			senderToMetasfresh.send(request);
		}
		catch (final Exception e)
		{
			// thx http://stackoverflow.com/questions/25367566/severe-could-not-dispatch-event-eventbus-com-google-common-eventbus-subscriber
			logger.error("Caught exception trying to process {}", request, e);
		}

	}

	private PutRfQChangeRequest toPutRfQChangeRequest(final List<Rfq> rfqs)
	{
		final PutRfQChangeRequestBuilder changeRequest = PutRfQChangeRequest.builder();

		for (final Rfq rfq : rfqs)
		{
			final SyncRfQPriceChangeEvent priceChangeEvent = toSyncRfQPriceChangeEvent(rfq);
			changeRequest.priceChangeEvent(priceChangeEvent);

			for (final RfqQty rfqQty : rfq.getQuantities())
			{
				final SyncRfQQtyChangeEvent qtyChangeEvent = toSyncRfQPriceChangeEvent(rfq, rfqQty);
				changeRequest.qtyChangeEvent(qtyChangeEvent);
			}

		}

		return changeRequest.build();
	}

	private SyncRfQPriceChangeEvent toSyncRfQPriceChangeEvent(final Rfq rfqRecord)
	{
		final String rfq_uuid = rfqRecord.getUuid();
		final Product product = productSuppliesService.getProductById(rfqRecord.getProduct_id());

		return SyncRfQPriceChangeEvent.builder()
				.uuid(rfq_uuid)
				.rfq_uuid(rfq_uuid)
				.product_uuid(product.getUuid())
				.price(rfqRecord.getPricePromised())
				.syncConfirmationId(rfqRecord.getSyncConfirmId())
				.build();
	}

	private SyncRfQQtyChangeEvent toSyncRfQPriceChangeEvent(final Rfq rfq, final RfqQty rfqQtyReport)
	{
		final Product product = productSuppliesService.getProductById(rfq.getProduct_id());

		return SyncRfQQtyChangeEvent.builder()
				.uuid(rfqQtyReport.getUuid())
				.rfq_uuid(rfq.getUuid())
				.day(rfqQtyReport.getDatePromised())
				.product_uuid(product.getUuid())
				.qty(rfqQtyReport.getQtyPromised())
				.syncConfirmationId(rfqQtyReport.getSyncConfirmId())
				.build();
	}

	@Override
	public ISyncAfterCommitCollector syncAfterCommit()
	{
		if (!TransactionSynchronizationManager.isActualTransactionActive())
		{
			throw new RuntimeException("Not in transaction");
		}

		SyncAfterCommit instance = null;
		for (final TransactionSynchronization sync : TransactionSynchronizationManager.getSynchronizations())
		{
			if (sync instanceof SyncAfterCommit)
			{
				instance = (SyncAfterCommit)sync;
				logger.debug("Found SyncAfterCommit instance: {}", instance);
			}
		}

		if (instance == null)
		{
			instance = new SyncAfterCommit();
			TransactionSynchronizationManager.registerSynchronization(instance);

			logger.debug("Registered synchronization: {}", instance);
		}

		return instance;
	}

	/**
	 * Creates {@link SyncConfirm} records and invokes {@link ISenderToMetasfreshService}.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 */
	private final class SyncAfterCommit implements TransactionSynchronization, ISyncAfterCommitCollector
	{
		private final ArrayList<ProductSupply> productSupplies = new ArrayList<>();
		private final ArrayList<WeekSupply> weeklySupplies = new ArrayList<>();
		private final ArrayList<Rfq> rfqs = new ArrayList<>();

		@Override
		public ISyncAfterCommitCollector add(final ProductSupply productSupply)
		{
			createAndStoreSyncConfirmRecord(productSupply);
			productSupplies.add(productSupply);

			logger.debug("Enqueued {}", productSupply);
			return this;
		}

		@Override
		public ISyncAfterCommitCollector add(final WeekSupply weeklySupply)
		{
			createAndStoreSyncConfirmRecord(weeklySupply);
			weeklySupplies.add(weeklySupply);
			return this;
		}

		@Override
		public ISyncAfterCommitCollector add(final Rfq rfq)
		{
			createAndStoreSyncConfirmRecord(rfq);
			rfqs.add(rfq);
			return this;
		}

		/**
		 * Creates a new local DB record for the given <code>abstractEntity</code>.
		 */
		private void createAndStoreSyncConfirmRecord(final AbstractSyncConfirmAwareEntity abstractEntity)
		{
			final SyncConfirm syncConfirmRecord = new SyncConfirm();
			syncConfirmRecord.setEntryType(abstractEntity.getClass().getSimpleName());
			syncConfirmRecord.setEntryUuid(abstractEntity.getUuid());
			syncConfirmRecord.setEntryId(abstractEntity.getId());

			syncConfirmRepo.save(syncConfirmRecord);

			abstractEntity.setSyncConfirmId(syncConfirmRecord.getId());

		}

		@Override
		public void afterCommit()
		{
			publishToMetasfreshAsync();
		}

		public void publishToMetasfreshAsync()
		{
			logger.debug("Synchronizing: {}", this);

			//
			// Sync daily product supplies
			{
				final List<ProductSupply> productSupplies = ImmutableList.copyOf(this.productSupplies);
				this.productSupplies.clear();
				pushDailyReportsAsync(productSupplies);
			}

			//
			// Sync weekly product supplies
			{
				final List<WeekSupply> weeklySupplies = ImmutableList.copyOf(this.weeklySupplies);
				this.weeklySupplies.clear();
				pushWeeklyReportsAsync(weeklySupplies);
			}

			//
			// Sync RfQ changes
			{
				final List<Rfq> rfqs = ImmutableList.copyOf(this.rfqs);
				this.rfqs.clear();
				pushRfqsAsync(rfqs);
			}
		}
	}
}
