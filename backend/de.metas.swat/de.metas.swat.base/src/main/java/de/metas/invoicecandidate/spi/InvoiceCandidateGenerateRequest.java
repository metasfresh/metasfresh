package de.metas.invoicecandidate.spi;

import java.util.List;

import de.metas.lock.api.LockOwner;
import org.adempiere.model.InterfaceWrapperHelper;

import com.google.common.collect.ImmutableList;

import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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
 * Request for generating invoice candidates.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ToString
public final class InvoiceCandidateGenerateRequest
{
	public static InvoiceCandidateGenerateRequest of(
			@NonNull final IInvoiceCandidateHandler handler,
			@NonNull final Object model,
			@NonNull final LockOwner lockOwner)
	{
		return new InvoiceCandidateGenerateRequest(handler, model, lockOwner);
	}

	public static List<InvoiceCandidateGenerateRequest> ofAll(
			@NonNull final List<? extends IInvoiceCandidateHandler> handlers, 
			@NonNull final List<?> models,
			@NonNull final LockOwner lockOwner)
	{
		if (handlers.isEmpty() || models.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableList.Builder<InvoiceCandidateGenerateRequest> requests = ImmutableList.builder();
		for (final Object model : models)
		{
			for (final IInvoiceCandidateHandler handler : handlers)
			{
				requests.add(InvoiceCandidateGenerateRequest.of(handler, model, lockOwner));
			}
		}
		return requests.build();
	}

	private final IInvoiceCandidateHandler handler;
	private final Object model;
	private final LockOwner lockOwner;

	private InvoiceCandidateGenerateRequest(
			@NonNull final IInvoiceCandidateHandler handler,
			@NonNull final Object model,
			@NonNull final LockOwner lockOwner)
	{
		this.handler = handler;
		this.model = model;
		this.lockOwner = lockOwner;
	}

	public IInvoiceCandidateHandler getHandler()
	{
		return handler;
	}

	public <T> T getModel(final Class<T> modelClass)
	{
		return InterfaceWrapperHelper.create(model, modelClass);
	}

	@NonNull
	public ImmutableList<Object> getRecordsToLock()
	{
		return handler.getRecordsToLock(model);
	}
	
	@NonNull
	public LockOwner getLockOwner()
	{
		return lockOwner;
	}
}
