package de.metas.procurement.webui;

import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import de.metas.common.procurement.sync.protocol.dto.SyncBPartner;
import de.metas.common.procurement.sync.protocol.dto.SyncProduct;
import de.metas.common.procurement.sync.protocol.dto.SyncProductSupply;
import de.metas.common.procurement.sync.protocol.dto.SyncWeeklySupply;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.PutProductSuppliesRequest;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.PutWeeklySupplyRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutRfQChangeRequest;
import de.metas.procurement.webui.util.DummyDataProducer;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutionException;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * An mocked implementation which records what was reported.
 */
public class MockedTestServerSync
{
	private static final Logger logger = LoggerFactory.getLogger(MockedTestServerSync.class);

	private final DummyDataProducer dummyDataProducer;

	private final LoadingCache<String, SettableFuture<SyncProductSupply>> productSupplies = CacheBuilder.newBuilder()
			.build(new CacheLoader<>()
			{
				@Override
				public SettableFuture<SyncProductSupply> load(final String uuid)
				{
					return SettableFuture.create();
				}
			});

	private final LoadingCache<String, SettableFuture<SyncWeeklySupply>> weeklySupplies = CacheBuilder.newBuilder()
			.build(new CacheLoader<>()
			{
				@Override
				public SettableFuture<SyncWeeklySupply> load(final String uuid)
				{
					return SettableFuture.create();
				}
			});

	public MockedTestServerSync(@NonNull final DummyDataProducer dummyDataProducer)
	{
		this.dummyDataProducer = dummyDataProducer;
	}

	public List<SyncBPartner> getAllBPartners()
	{
		return dummyDataProducer.getSyncBPartnersRequest().getBpartners();
	}

	public List<SyncProduct> getAllProducts()
	{
		return dummyDataProducer.getSyncProductsRequest().getProducts();
	}

	public void reportProductSupplies(final PutProductSuppliesRequest request)
	{
		logger.info("Got {}", request);

		for (final SyncProductSupply syncProductSupply : request.getProductSupplies())
		{
			setReportedProductSupply(syncProductSupply);
		}
	}

	private void setReportedProductSupply(final SyncProductSupply syncProductSupply)
	{
		try
		{
			final String uuid = syncProductSupply.getUuid();
			productSupplies.get(uuid).set(syncProductSupply);
		}
		catch (final ExecutionException e)
		{
			throw new RuntimeException(e.getCause());
		}
	}

	public SyncProductSupply getAndRemoveReportedProductSupply(final String uuid)
	{
		try
		{
			final SyncProductSupply productSupply = productSupplies.get(uuid).get(10, TimeUnit.SECONDS);
			productSupplies.invalidate(uuid);
			return productSupply;
		}
		catch (final Exception e)
		{
			throw new RuntimeException(e.getCause());
		}
	}

	public void reportWeekSupply(final PutWeeklySupplyRequest request)
	{
		logger.info("Got {}", request);
		for (final SyncWeeklySupply syncWeeklySupply : request.getWeeklySupplies())
		{
			setReportedWeeklySupply(syncWeeklySupply);
		}
	}

	private void setReportedWeeklySupply(final SyncWeeklySupply syncWeeklySupply)
	{
		try
		{
			final String uuid = syncWeeklySupply.getUuid();
			weeklySupplies.get(uuid).set(syncWeeklySupply);
		}
		catch (final ExecutionException e)
		{
			throw Throwables.propagate(e.getCause());
		}
	}

	public ListenableFuture<SyncWeeklySupply> getReportedWeeklySupply(final String uuid)
	{
		try
		{
			return weeklySupplies.get(uuid);
		}
		catch (final ExecutionException e)
		{
			throw Throwables.propagate(e.getCause());
		}
	}

	public SyncWeeklySupply getAndRemoveReportedWeeklySupply(final String uuid)
	{
		try
		{
			final SyncWeeklySupply syncWeeklySupply = weeklySupplies.get(uuid).get(10, TimeUnit.SECONDS);
			weeklySupplies.invalidate(uuid);
			return syncWeeklySupply;
		}
		catch (final Exception e)
		{
			throw Throwables.propagate(e);
		}
	}

	public String getInfoMessage()
	{
		return "";
	}

	public void reportRfQChanges(final PutRfQChangeRequest request)
	{
		logger.info("Got {}", request);
	}
}
