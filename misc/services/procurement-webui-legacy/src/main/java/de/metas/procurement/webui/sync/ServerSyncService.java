package de.metas.procurement.webui.sync;

import com.google.gwt.thirdparty.guava.common.base.Preconditions;
import com.google.gwt.thirdparty.guava.common.base.Throwables;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.google.gwt.thirdparty.guava.common.eventbus.AsyncEventBus;
import com.google.gwt.thirdparty.guava.common.eventbus.DeadEvent;
import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import de.metas.common.procurement.sync.protocol.dto.SyncProductSupply;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQPriceChangeEvent;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQQtyChangeEvent;
import de.metas.common.procurement.sync.protocol.dto.SyncWeeklySupply;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.GetAllBPartnersRequest;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.GetAllProductsRequest;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.GetInfoMessageRequest;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.PutProductSuppliesRequest;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.PutWeeklySupplyRequest;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.PutWeeklySupplyRequest.PutWeeklySupplyRequestBuilder;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutRfQChangeRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutRfQChangeRequest.PutRfQChangeRequestBuilder;
import de.metas.procurement.webui.model.AbstractSyncConfirmAwareEntity;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.ProductSupply;
import de.metas.procurement.webui.model.Rfq;
import de.metas.procurement.webui.model.RfqQty;
import de.metas.procurement.webui.model.SyncConfirm;
import de.metas.procurement.webui.model.WeekSupply;
import de.metas.procurement.webui.repository.ProductSupplyRepository;
import de.metas.procurement.webui.repository.RfqRepository;
import de.metas.procurement.webui.repository.SyncConfirmRepository;
import de.metas.procurement.webui.service.IProductSuppliesService;
import de.metas.procurement.webui.sync.rabbitmq.SenderToMetasfresh;
import de.metas.procurement.webui.util.DateUtils;
import de.metas.procurement.webui.util.EventBusLoggingSubscriberExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
@ManagedResource(description = "Server synchronization service")
public class ServerSyncService implements IServerSyncService
{
	private final transient Logger logger = LoggerFactory.getLogger(getClass());

	private final TaskExecutor taskExecutor;

	private AsyncEventBus eventBus;

	private final SenderToMetasfresh senderToMetasfresh;

	private final ProductSupplyRepository productSuppliesRepo;

	private final IProductSuppliesService productSuppliesService;

	private final RfqRepository rfqRepo;

	private final SyncConfirmRepository syncConfirmRepo;

	private final CountDownLatch initialSync = new CountDownLatch(1);

	public ServerSyncService(
			final TaskExecutor taskExecutor,
			final SenderToMetasfresh senderToMetasfresh,
			final ProductSupplyRepository productSuppliesRepo,
			final IProductSuppliesService productSuppliesService,
			final RfqRepository rfqRepo,
			final SyncConfirmRepository syncConfirmRepo)
	{
		this.taskExecutor = taskExecutor;
		this.senderToMetasfresh = senderToMetasfresh;
		this.productSuppliesRepo = productSuppliesRepo;
		this.productSuppliesService = productSuppliesService;
		this.rfqRepo = rfqRepo;
		this.syncConfirmRepo = syncConfirmRepo;
	}

	@PostConstruct
	private void init()
	{
		eventBus = new AsyncEventBus(taskExecutor, EventBusLoggingSubscriberExceptionHandler.of(logger));
		eventBus.register(this);

		final Runnable callback = initialSync::countDown;
		syncAllAsync(callback);
	}

	/**
	 * Sends a {@link SyncAllRequest} to metasfresh. The request will be processed by {@link #process(SyncAllRequest)}.
	 */
	@ManagedOperation
	public void syncAllAsync()
	{
		final Runnable callback = null;
		syncAllAsync(callback);
	}

	/**
	 * Like {@link #syncAllAsync()}, with a callback instance to be invoked when the sync is complete.
	 *
	 * @param callback instance whose <code>run()</code> method shall be executed after the sync, also if the sync failed.
	 */
	public void syncAllAsync(@Nullable final Runnable callback)
	{
		final SyncAllRequest request = SyncAllRequest.of(callback);
		logger.debug("Enqueuing: {}", request);
		eventBus.post(request);
	}

	@Override
	public void awaitInitialSyncComplete(final long timeout, final TimeUnit unit) throws InterruptedException
	{
		initialSync.await(timeout, unit);
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
	public void reportProductSuppliesAsync(final List<ProductSupply> productSupplies)
	{
		if (productSupplies == null || productSupplies.isEmpty())
		{
			logger.debug("Nothing to enqueue");
			return;
		}

		final PutProductSuppliesRequest request = createSyncProductSuppliesRequest(productSupplies);
		logger.debug("Enqueuing: {}", request);
		eventBus.post(request);
	}

	@ManagedOperation(description = "Pushes a particular product supply, identified by ID, from webui server to metasfresh server")
	public void pushReportProductSupplyById(final long product_supply_id)
	{
		final ProductSupply productSupply = productSuppliesRepo.findOne(product_supply_id);
		if (productSupply == null)
		{
			throw new RuntimeException("No product supply found for ID=" + product_supply_id);
		}

		final PutProductSuppliesRequest request = createSyncProductSuppliesRequest(Collections.singletonList(productSupply));
		logger.debug("Pushing request: {}", request);
		process(request);
		logger.debug("Pushing request done");
	}

	@ManagedOperation(description = "Pushes all product supply reports, identified by selection, from webui server to metasfresh server")
	public void pushReportProductSupplyForSelection(final long bpartner_id, final long product_id, final String dayFromStr, final String dayToStr)
	{
		try
		{
			final Date dayFrom = DateUtils.parseDayDate(dayFromStr);
			final Date dayTo = DateUtils.parseDayDate(dayToStr);

			final List<ProductSupply> productSupplies = productSuppliesService.getProductSupplies(bpartner_id, product_id, dayFrom, dayTo);
			if (productSupplies.isEmpty())
			{
				throw new RuntimeException("No supplies found");
			}

			final PutProductSuppliesRequest request = createSyncProductSuppliesRequest(productSupplies);
			logger.debug("Pushing request: {}", request);
			process(request);
			logger.debug("Pushing request done");
		}
		catch (final Exception e)
		{
			logger.error("Failed pushing product supplies for selection", e);
			throw Throwables.propagate(e);
		}
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

	private PutProductSuppliesRequest createSyncProductSuppliesRequest(final List<ProductSupply> productSupplies)
	{
		final PutProductSuppliesRequest.PutProductSuppliesRequestBuilder request = PutProductSuppliesRequest.builder();
		for (final ProductSupply productSupply : productSupplies)
		{
			final SyncProductSupply syncProductSupply = SyncProductSupply.builder()
					.uuid(productSupply.getUuid())
					.bpartner_uuid(productSupply.getBpartner().getUuid())
					.contractLine_uuid(productSupply.getContractLine() == null ? null : productSupply.getContractLine().getUuid())
					.product_uuid(productSupply.getProduct().getUuid())
					.day(DateUtils.toLocalDate(productSupply.getDay()))
					.qty(productSupply.getQty())
					.version(productSupply.getVersion())
					.syncConfirmationId(productSupply.getSyncConfirmId())
					.build();

			request.productSupply(syncProductSupply);
		}

		return request.build();
	}

	@Override
	public void reportWeeklySupplyAsync(final List<WeekSupply> weeklySupplies)
	{
		if (weeklySupplies.isEmpty())
		{
			logger.debug("Nothing to enqueue");
			return;
		}

		final PutWeeklySupplyRequest request = creatSyncWeekSupplyRequest(weeklySupplies);
		logger.debug("Enqueuing: {}", request);
		eventBus.post(request);
	}

	@ManagedOperation(description = "Pushes all weekly supply reports, identified by selection, from webui server to metasfresh server")
	public void pushWeeklySuppliesForSelection(final long bpartner_id, final long product_id, final String dayFromStr, final String dayToStr)
	{
		try
		{
			final Date dayFrom = DateUtils.parseDayDate(dayFromStr);
			final Date dayTo = DateUtils.parseDayDate(dayToStr);

			final List<WeekSupply> weeklySupplies = productSuppliesService.getWeeklySupplies(bpartner_id, product_id, dayFrom, dayTo);
			if (weeklySupplies.isEmpty())
			{
				throw new RuntimeException("No supplies found");
			}

			final PutWeeklySupplyRequest request = creatSyncWeekSupplyRequest(weeklySupplies);
			logger.debug("Pushing request: {}", request);
			process(request);
			logger.debug("Pushing request done");
		}
		catch (final Exception e)
		{
			logger.error("Failed pushing weekly supplies for selection", e);
			throw Throwables.propagate(e);
		}
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

	private PutWeeklySupplyRequest creatSyncWeekSupplyRequest(final List<WeekSupply> weekSupplies)
	{
		final PutWeeklySupplyRequestBuilder request = PutWeeklySupplyRequest.builder();

		for (final WeekSupply weeklySupply : weekSupplies)
		{
			Preconditions.checkNotNull(weeklySupply, "week supply not null");
			Preconditions.checkNotNull(weeklySupply.getId(), "week supply is saved");

			final SyncWeeklySupply syncWeeklySupply = SyncWeeklySupply.builder()
					.uuid(weeklySupply.getUuid())
					.version(weeklySupply.getVersion())
					.bpartner_uuid(weeklySupply.getBpartner().getUuid())
					.product_uuid(weeklySupply.getProduct().getUuid())
					.weekDay(DateUtils.toLocalDate(weeklySupply.getDay()))
					.trend(weeklySupply.getTrend())
					.syncConfirmationId(weeklySupply.getSyncConfirmId())
					.build();

			request.weeklySupply(syncWeeklySupply);
		}
		return request.build();
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
			final Runnable callback = null;
			return new SyncAllRequest(callback);
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

	private void reportRfQChangesAsync(final List<Rfq> rfqs, final List<RfqQty> rfqQuantities)
	{
		final PutRfQChangeRequest request = createSyncRfQChangeRequest(rfqs, rfqQuantities);
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

	private PutRfQChangeRequest createSyncRfQChangeRequest(final List<Rfq> rfqs, final List<RfqQty> rfqQuantities)
	{
		final PutRfQChangeRequestBuilder changeRequest = PutRfQChangeRequest.builder();

		for (final Rfq rfq : rfqs)
		{
			final SyncRfQPriceChangeEvent priceChangeEvent = createSyncRfQPriceChangeEvent(rfq);
			changeRequest.priceChangeEvent(priceChangeEvent);
		}

		for (final RfqQty rfqQty : rfqQuantities)
		{
			final SyncRfQQtyChangeEvent qtyChangeEvent = createSyncRfQQtyChangeEvent(rfqQty);
			changeRequest.qtyChangeEvent(qtyChangeEvent);
		}

		return changeRequest.build();
	}

	private SyncRfQPriceChangeEvent createSyncRfQPriceChangeEvent(final Rfq rfqRecord)
	{
		final String rfq_uuid = rfqRecord.getUuid();

		return SyncRfQPriceChangeEvent.builder()
				.uuid(rfq_uuid)
				.rfq_uuid(rfq_uuid)
				.product_uuid(rfqRecord.getProduct().getUuid())
				.price(rfqRecord.getPricePromised())
				.syncConfirmationId(rfqRecord.getSyncConfirmId())
				.build();
	}

	private SyncRfQQtyChangeEvent createSyncRfQQtyChangeEvent(final RfqQty rfqQtyReport)
	{
		final Rfq rfq = rfqQtyReport.getRfq();
		final Product product = rfq.getProduct();

		return SyncRfQQtyChangeEvent.builder()
				.uuid(rfqQtyReport.getUuid())
				.rfq_uuid(rfq.getUuid())
				.day(DateUtils.toLocalDate(rfqQtyReport.getDatePromised()))
				.product_uuid(product.getUuid())
				.qty(rfqQtyReport.getQtyPromised())
				.syncConfirmationId(rfqQtyReport.getSyncConfirmId())
				.build();
	}

	@ManagedOperation(description = "Pushes a particular RfQ, identified by ID, from webui server to metasfresh server")
	public void pushRfqById(final long rfq_id)
	{
		final Rfq rfq = rfqRepo.findOne(rfq_id);
		if (rfq == null)
		{
			throw new RuntimeException("No RfQ found for ID=" + rfq_id);
		}

		final PutRfQChangeRequest request = createSyncRfQChangeRequest(ImmutableList.of(rfq), rfq.getQuantities());
		logger.debug("Pushing request: {}", request);
		process(request);
		logger.debug("Pushing request done");
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
	 * Creates {@link SyncConfirm} records and invokes {@link IServerSyncService}.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 */
	private final class SyncAfterCommit extends TransactionSynchronizationAdapter implements ISyncAfterCommitCollector
	{
		private final List<ProductSupply> productSupplies = new ArrayList<>();
		private final List<WeekSupply> weeklySupplies = new ArrayList<>();
		private final List<Rfq> rfqs = new ArrayList<>();
		private final List<RfqQty> rfqQuantities = new ArrayList<>();

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

		@Override
		public ISyncAfterCommitCollector add(final RfqQty rfqQty)
		{
			createAndStoreSyncConfirmRecord(rfqQty);
			rfqQuantities.add(rfqQty);
			return this;
		}

		/**
		 * Creates a new local DB record for the given <code>abstractEntity</code>.
		 */
		private SyncConfirm createAndStoreSyncConfirmRecord(final AbstractSyncConfirmAwareEntity abstractEntity)
		{
			final SyncConfirm syncConfirmRecord = new SyncConfirm();
			syncConfirmRecord.setEntryType(abstractEntity.getClass().getSimpleName());
			syncConfirmRecord.setEntryUuid(abstractEntity.getUuid());
			syncConfirmRecord.setEntryId(abstractEntity.getId());

			syncConfirmRepo.save(syncConfirmRecord);

			abstractEntity.setSyncConfirmId(syncConfirmRecord.getId());

			return syncConfirmRecord;
		}

		@Override
		public void afterCommit()
		{
			logger.debug("Synchronizing: {}", this);

			//
			// Sync daily product supplies
			{
				final List<ProductSupply> productSupplies = ImmutableList.copyOf(this.productSupplies);
				this.productSupplies.clear();
				if (!productSupplies.isEmpty())
				{
					reportProductSuppliesAsync(productSupplies);
				}
			}

			//
			// Sync weekly product supplies
			{
				final List<WeekSupply> weeklySupplies = ImmutableList.copyOf(this.weeklySupplies);
				this.weeklySupplies.clear();
				if (!weeklySupplies.isEmpty())
				{
					reportWeeklySupplyAsync(weeklySupplies);
				}
			}

			//
			// Sync RfQ changes
			{
				final List<Rfq> rfqs = ImmutableList.copyOf(this.rfqs);
				final List<RfqQty> rfqQuantities = ImmutableList.copyOf(this.rfqQuantities);
				this.rfqs.clear();
				this.rfqQuantities.clear();
				reportRfQChangesAsync(rfqs, rfqQuantities);
			}
		}
	}
}
