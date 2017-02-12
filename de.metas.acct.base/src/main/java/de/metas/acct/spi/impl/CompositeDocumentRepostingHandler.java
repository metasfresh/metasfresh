package de.metas.acct.spi.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.util.Services;
import org.compiere.process.DocAction;

import de.metas.acct.spi.IDocumentRepostingHandler;
import de.metas.document.engine.IDocActionBL;

/*
 * #%L
 * de.metas.acct.base
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

/**
 * Composite Document reposting handler
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class CompositeDocumentRepostingHandler implements IDocumentRepostingHandler
{

	// list of handlers to be used when the reposting process is called
	private final CopyOnWriteArrayList<IDocumentRepostingHandler> handlers = new CopyOnWriteArrayList<>();

	// add the handler in the list
	public void addHandler(final IDocumentRepostingHandler handler)
	{
		if (handler == null)
		{
			return;
		}

		handlers.addIfAbsent(handler);
	}

	@Override
	public List<DocAction> retrievePostedWithoutFactAcct(Properties ctx, Timestamp startTime)
	{
		final IDocActionBL docActionBL = Services.get(IDocActionBL.class);
		
		final List<DocAction> documentsPostedWithoutFactAcct = new ArrayList<>();

		// Retrieve the documents marked as posted but with no fact accounts from all the handlers
		for (final IDocumentRepostingHandler handler : handlers)
		{
			List<?> documents = handler.retrievePostedWithoutFactAcct(ctx, startTime);
			
			for(final Object document: documents)
			{
				documentsPostedWithoutFactAcct.add(docActionBL.getDocAction(document));
			}
			
		}

		return documentsPostedWithoutFactAcct;
	}

}
