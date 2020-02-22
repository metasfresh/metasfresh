package de.metas.monitoring.adapter.apm;

import java.util.concurrent.Callable;

import org.springframework.stereotype.Service;

import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.Scope;
import co.elastic.apm.api.Span;
import co.elastic.apm.api.Transaction;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import lombok.NonNull;

/*
 * #%L
 * de.metas.monitoring
 * %%
 * Copyright (C) 2020 metas GmbH
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
public class APMPerformanceMonitoringService implements PerformanceMonitoringService
{
	@Override
	public <V> Result<V> monitorTransaction(
			@NonNull final Callable<V> callable,
			@NonNull final TransactionMetadata request)
	{
		final Transaction transaction = ElasticApm.startTransaction();
		try (final Scope scope = transaction.activate())
		{
			transaction.setName(request.getName());
			transaction.setType(request.getType().getCode());
			request.getLabels().forEach(transaction::addLabel);

			final V result = callable.call();
			return Result.ofResult(result);
		}
		catch (Exception e)
		{
			transaction.captureException(e);
			return Result.ofException(e);
		}
		finally
		{
			transaction.end();
		}

	}

	@Override
	public <V> Result<V> monitorSpan(
			@NonNull final Callable<V> callable,
			@NonNull final SpanMetadata request)
	{
		final Transaction transaction = ElasticApm.currentTransaction();
		final Span span = transaction.startSpan(request.getType(), request.getSubType(), request.getAction());

		try
		{
			span.setName(request.getName());
			request.getLabels().forEach(span::addLabel);

			final V result = callable.call();
			return Result.ofResult(result);
		}
		catch (Exception e)
		{
			span.captureException(e);
			return Result.ofException(e);
		}
		finally
		{
			span.end();
		}
	}
}
