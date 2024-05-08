/*
 * #%L
 * de.metas.monitoring
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.monitoring.adapter;

import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.stereotype.Service;

import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.HeaderInjector;
import co.elastic.apm.api.Scope;
import co.elastic.apm.api.Span;
import co.elastic.apm.api.Transaction;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringServiceUtil;
import lombok.NonNull;

@Service
public class APMPerformanceMonitoringService implements PerformanceMonitoringService
{
	@Override
	public <V> V monitorTransaction(
			@NonNull final Callable<V> callable,
			@NonNull final TransactionMetadata metadata)
	{
		final Transaction transaction;
		final Map<String, String> distrHeaders = metadata.getDistributedTransactionHeaders();
		if (distrHeaders.isEmpty())
		{
			
			transaction = ElasticApm.startTransaction();
		}
		else
		{
			transaction = ElasticApm.startTransactionWithRemoteParent(name -> distrHeaders.get(name));
		}

		try (final Scope scope = transaction.activate())
		{
			transaction.setName(metadata.getName());
			transaction.setType(metadata.getType().getCode());
			metadata.getLabels().forEach(transaction::addLabel);

			return callable.call();
		}
		catch (final Exception e)
		{
			transaction.captureException(e);
			throw PerformanceMonitoringServiceUtil.asRTE(e);
		}
		finally
		{
			transaction.end();
		}
	}

	@Override
	public <V> V monitorSpan(
			@NonNull final Callable<V> callable,
			@NonNull final SpanMetadata metadata)
	{
		final Transaction transaction = ElasticApm.currentTransaction();
		final Span span = transaction.startSpan(metadata.getType(), metadata.getSubType(), metadata.getAction());
		if (metadata.getDistributedHeadersInjector() != null)
		{
			final HeaderInjector headerInjector = (name, value) -> metadata.getDistributedHeadersInjector().accept(name, value);
			transaction.injectTraceHeaders(headerInjector);
		}
		span.setName(metadata.getName());
		metadata.getLabels().forEach(span::addLabel);

		try
		{
			return callable.call();
		}
		catch (final Exception e)
		{
			span.captureException(e);
			throw PerformanceMonitoringServiceUtil.asRTE(e);
		}
		finally
		{
			span.end();
		}
	}

}
