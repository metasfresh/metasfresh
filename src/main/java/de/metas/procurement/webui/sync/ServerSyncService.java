package de.metas.procurement.webui.sync;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import com.google.gwt.thirdparty.guava.common.base.Preconditions;
import com.google.gwt.thirdparty.guava.common.base.Throwables;
import com.google.gwt.thirdparty.guava.common.eventbus.AsyncEventBus;
import com.google.gwt.thirdparty.guava.common.eventbus.DeadEvent;
import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;

import de.metas.procurement.sync.IAgentSync;
import de.metas.procurement.sync.IServerSync;
import de.metas.procurement.sync.protocol.SyncBPartnersRequest;
import de.metas.procurement.sync.protocol.SyncInfoMessageRequest;
import de.metas.procurement.sync.protocol.SyncProductSuppliesRequest;
import de.metas.procurement.sync.protocol.SyncProductSupply;
import de.metas.procurement.sync.protocol.SyncProductsRequest;
import de.metas.procurement.sync.protocol.SyncWeeklySupply;
import de.metas.procurement.sync.protocol.SyncWeeklySupplyRequest;
import de.metas.procurement.webui.model.ProductSupply;
import de.metas.procurement.webui.model.WeekSupply;
import de.metas.procurement.webui.repository.ProductSupplyRepository;
import de.metas.procurement.webui.service.IProductSuppliesService;
import de.metas.procurement.webui.util.DateUtils;
import de.metas.procurement.webui.util.EventBusLoggingSubscriberExceptionHandler;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
@ManagedResource(description = "Server synchronization service")
public class ServerSyncService implements IServerSyncService
{
	private final transient Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired(required = true)
	private TaskExecutor taskExecutor;
	private AsyncEventBus eventBus;

	/** Server sync REST service client interface */
	@Autowired(required = true)
	@Lazy
	private IServerSync serverSync;

	@Autowired(required = true)
	@Lazy
	private IAgentSync agentSync;
	
	@Autowired
	@Lazy
	private ProductSupplyRepository productSuppliesRepo;
	@Autowired
	@Lazy
	private IProductSuppliesService productSuppliesService;

	private final CountDownLatch initialSync = new CountDownLatch(1);

	@PostConstruct
	private void init()
	{
		eventBus = new AsyncEventBus(taskExecutor, EventBusLoggingSubscriberExceptionHandler.of(logger));
		eventBus.register(this);

		syncAllAsync(new Runnable()
		{
			@Override
			public void run()
			{
				initialSync.countDown();
			}
		});
	}

	@ManagedOperation
	public void syncAllAsync()
	{
		final Runnable callback = null;
		syncAllAsync(callback);
	}

	public void syncAllAsync(final Runnable callback)
	{
		final SyncAllRequest request = SyncAllRequest.of(callback);
		logger.debug("Enqueuing: {}", request);
		eventBus.post(request);
	}

	@Override
	public void awaitInitialSyncComplete(long timeout, TimeUnit unit) throws InterruptedException
	{
		initialSync.await(timeout, unit);
	}

	@Subscribe
	public void process(final SyncAllRequest request)
	{
		logger.debug("syncAll: Start");

		//
		logger.debug("Fetching bpartners from server and import it");
		try
		{
			final SyncBPartnersRequest syncBPartnersRequest = new SyncBPartnersRequest();
			syncBPartnersRequest.getBpartners().addAll(serverSync.getAllBPartners());
			agentSync.syncBPartners(syncBPartnersRequest);
		}
		catch (Exception e)
		{
			logger.error("Failed importing bpartners", e);
		}

		//
		logger.debug("Fetching products from server and import it");
		try
		{
			final SyncProductsRequest syncProductsRequest = new SyncProductsRequest();
			syncProductsRequest.getProducts().addAll(serverSync.getAllProducts());
			agentSync.syncProducts(syncProductsRequest);
		}
		catch (Exception e)
		{
			logger.error("Failed importing products", e);
		}

		//
		logger.debug("Fetching info message from server and import it");
		try
		{
			final String infoMessage = serverSync.getInfoMessage();
			agentSync.syncInfoMessage(SyncInfoMessageRequest.of(infoMessage));
		}
		catch (Exception e)
		{
			logger.error("Failed importing info message", e);
		}

		logger.debug("syncAll: Done");

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

		final SyncProductSuppliesRequest request = createSyncProductSuppliesRequest(productSupplies);
		logger.debug("Enqueuing: {}", request);
		eventBus.post(request);
	}
	
	@ManagedOperation(description = "Pushes a particular product supply, identified by ID, from webui server to metasfresh server")
	public void pushReportProductSupplyById(final long product_supply_id)
	{
		final ProductSupply productSupply = productSuppliesRepo.findOne(product_supply_id);
		if(productSupply == null)
		{
			throw new RuntimeException("No product supply found for ID=" + product_supply_id);
		}
		
		final SyncProductSuppliesRequest request = createSyncProductSuppliesRequest(Arrays.asList(productSupply));
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
			
			final SyncProductSuppliesRequest request = createSyncProductSuppliesRequest(productSupplies);
			logger.debug("Pushing request: {}", request);
			process(request);
			logger.debug("Pushing request done");
		}
		catch (Exception e)
		{
			logger.error("Failed pushing product supplies for selection", e);
			throw Throwables.propagate(e);
		}
	}

	@Subscribe
	public void process(final SyncProductSuppliesRequest request)
	{
		try
		{
			serverSync.reportProductSupplies(request);
		}
		catch (final Exception e)
		{
			// thx http://stackoverflow.com/questions/25367566/severe-could-not-dispatch-event-eventbus-com-google-common-eventbus-subscriber
			logger.error("Caught exception trying to process SyncProductSuppliesRequest=" + request, e);
		}
	}

	private SyncProductSuppliesRequest createSyncProductSuppliesRequest(final List<ProductSupply> productSupplies)
	{
		final SyncProductSuppliesRequest request = new SyncProductSuppliesRequest();
		for (final ProductSupply productSupply : productSupplies)
		{
			final SyncProductSupply syncProductSupply = new SyncProductSupply();
			syncProductSupply.setUuid(productSupply.getUuid());
			syncProductSupply.setBpartner_uuid(productSupply.getBpartner().getUuid());
			syncProductSupply.setContractLine_uuid(productSupply.getContractLine() == null ? null : productSupply.getContractLine().getUuid());
			syncProductSupply.setProduct_uuid(productSupply.getProduct().getUuid());
			syncProductSupply.setDay(productSupply.getDay());
			syncProductSupply.setQty(productSupply.getQty());
			syncProductSupply.setVersion(productSupply.getVersion());

			request.getProductSupplies().add(syncProductSupply);
		}

		return request;
	}

	@Override
	public void reportWeeklySupplyAsync(final List<WeekSupply> weeklySupplies)
	{
		if (weeklySupplies.isEmpty())
		{
			logger.debug("Nothing to enqueue");
			return;
		}

		final SyncWeeklySupplyRequest request = creatSyncWeekSupplyRequest(weeklySupplies);
		logger.debug("Enqueuing: {}", request);
		eventBus.post(request);
	}

	@Subscribe
	public void process(final SyncWeeklySupplyRequest request)
	{
		try
		{
			serverSync.reportWeekSupply(request);
		}
		catch (final Exception e)
		{
			// thx http://stackoverflow.com/questions/25367566/severe-could-not-dispatch-event-eventbus-com-google-common-eventbus-subscriber
			logger.error("Caught exception trying to process SyncWeeklySupplyRequest=" + request, e);
		}
	}

	private SyncWeeklySupplyRequest creatSyncWeekSupplyRequest(final List<WeekSupply> weekSupplies)
	{
		final SyncWeeklySupplyRequest request = new SyncWeeklySupplyRequest();

		for (final WeekSupply weeklySupply : weekSupplies)
		{
			Preconditions.checkNotNull(weeklySupply, "week supply not null");
			Preconditions.checkNotNull(weeklySupply.getId(), "week supply is saved");

			final SyncWeeklySupply syncWeeklySupply = new SyncWeeklySupply();
			syncWeeklySupply.setUuid(weeklySupply.getUuid());
			syncWeeklySupply.setVersion(weeklySupply.getVersion());
			syncWeeklySupply.setBpartner_uuid(weeklySupply.getBpartner().getUuid());
			syncWeeklySupply.setProduct_uuid(weeklySupply.getProduct().getUuid());
			syncWeeklySupply.setWeekDay(weeklySupply.getDay());
			syncWeeklySupply.setTrend(weeklySupply.getTrend());

			request.getWeeklySupplies().add(syncWeeklySupply);
		}
		return request;
	}

	@Subscribe
	public void deadLetterChannel(final DeadEvent deadEvent)
	{
		logger.warn("Got a dead event: {}", deadEvent);
	}

	public static final class SyncAllRequest
	{
		public static final SyncAllRequest of()
		{
			final Runnable callback = null;
			return new SyncAllRequest(callback);
		}

		public static final SyncAllRequest of(Runnable callback)
		{
			return new SyncAllRequest(callback);
		}

		private final Runnable callback;

		private SyncAllRequest(Runnable callback)
		{
			super();
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
}
