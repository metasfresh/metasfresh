package de.metas.document.impl;

import java.util.HashMap;
import java.util.Map;

import org.adempiere.ad.modelvalidator.IModelInterceptor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Pair;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;

import de.metas.document.ICounterDocBL;
import de.metas.document.async.spi.impl.CreateCounterDocPP;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.model.interceptor.CounterDocHandlerInterceptor;
import de.metas.document.spi.ICounterDocHandler;

/*
 * #%L
 * de.metas.swat.base
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

public class CounterDocBL implements ICounterDocBL
{

	private final Map<String, ICounterDocHandler> handlers = new HashMap<>();

	@Override
	public boolean isCreateCounterDocument(final Object document)
	{
		final Pair<ICounterDocHandler, IDocument> handlerandDocAction = getHandlerOrNull(document);

		return handlerandDocAction.getFirst().isCreateCounterDocument(handlerandDocAction.getSecond());
	}

	@Override
	public IDocument createCounterDocument(final Object document, final boolean async)
	{
		if (async)
		{
			CreateCounterDocPP.schedule(document);
			return null;
		}
		else
		{
			final Pair<ICounterDocHandler, IDocument> handlerandDocAction = getHandlerOrNull(document);
			return handlerandDocAction.getFirst().createCounterDocument(handlerandDocAction.getSecond());
		}
	}

	@Override
	public IModelInterceptor registerHandler(final ICounterDocHandler handler,
			final String tableName)
	{
		Check.assumeNotNull(handler, "Param 'handler' is not null");
		Check.assumeNotNull(tableName, "Param 'tableName' is not null");

		final ICounterDocHandler oldHandler = handlers.put(tableName, handler);
		Check.errorIf(oldHandler != null,
				"Can't register ICounterDocumentHandler {} for table name {}, because ICounterDocumentHandler {} was already regoistered",
				handler, tableName, oldHandler);

		final CounterDocHandlerInterceptor counterDocHandlerInterceptor = CounterDocHandlerInterceptor.builder()
				.setTableName(I_C_Order.Table_Name)
				.setAsync(true)
				.build();
		return counterDocHandlerInterceptor;
	}

	/**
	 * 
	 * @param document
	 * @return may return the {@link NullCounterDocumentHandler}, but never <code>null</code>.
	 */
	private Pair<ICounterDocHandler, IDocument> getHandlerOrNull(final Object document)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableNameOrNull(document);
		if (Check.isEmpty(tableName) || !handlers.containsKey(tableName))
		{
			return new Pair<ICounterDocHandler, IDocument>(NullCounterDocumentHandler.instance, null);
		}

		final IDocument docAction = Services.get(IDocumentBL.class).getDocumentOrNull(document);
		if (docAction == null)
		{
			return new Pair<ICounterDocHandler, IDocument>(NullCounterDocumentHandler.instance, null);
		}

		return new Pair<ICounterDocHandler, IDocument>(handlers.get(tableName), docAction);
	}
}
