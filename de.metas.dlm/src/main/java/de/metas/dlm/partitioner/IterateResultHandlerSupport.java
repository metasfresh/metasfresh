package de.metas.dlm.partitioner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.ImmutablePair;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.dlm.partitioner.IIterateResultHandler.AddResult;
import de.metas.logging.LogManager;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Helper class that can be used within {@link IIterateResult} implementations.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class IterateResultHandlerSupport
{
	private static final transient Logger logger = LogManager.getLogger(IterateResultHandlerSupport.class);

	private List<IIterateResultHandler> handlers = new ArrayList<>();

	// as soon as we have >1 handler implementation, we can consider to e.g. have a map handler => last result instead of this boolean.
	private boolean handlerSignaledToStop = false;

	/**
	 * Invoke all handlers. If anoyone of them returns {@link AddResult#STOP} then it returns "STOP".
	 * Else If anoyone of them returns {@link AddResult#NOT_ADDED_CONTINUE} then it returns "NOT_ADDED_CONTINUE".
	 * Else it returns {@link AddResult#ADDED_CONTINUE}.
	 */
	public AddResult onRecordAdded(ITableRecordReference tableRecordReference, AddResult preliminaryResult)
	{
		final Set<AddResult> differentResults = handlers.stream().map(handler -> ImmutablePair.of(handler, handler.onRecordAdded(tableRecordReference, preliminaryResult)))
				.peek(pair -> logger.debug("handler returned result={}; handler={}", pair.getRight(), pair.getLeft()))
				.map(pair -> pair.getRight())
				.collect(Collectors.toSet());

		if (differentResults.contains(AddResult.STOP))
		{
			handlerSignaledToStop = true;
			return AddResult.STOP;
		}
		if (differentResults.contains(AddResult.NOT_ADDED_CONTINUE))
		{
			return AddResult.NOT_ADDED_CONTINUE;
		}
		return AddResult.ADDED_CONTINUE;
	}

	public boolean isHandlerSignaledToStop()
	{
		return handlerSignaledToStop;
	}

	/**
	 * Add another specific handler implementation.
	 * The handlers' {@link IIterateResultHandler#onRecordAdded(ITableRecordReference, de.metas.dlm.partitioner.IIterateResultHandler.AddResult)} methods will be called in the order they were added.
	 *
	 * @param handler
	 */
	public void registerListener(IIterateResultHandler handler)
	{
		handlers.add(handler);
	}

	public List<IIterateResultHandler> getRegisteredHandlers()
	{
		return ImmutableList.copyOf(handlers);
	}

	@Override
	public String toString()
	{
		return "IterateResultHandlerSupport [handlers=" + handlers + "]";
	}
}
