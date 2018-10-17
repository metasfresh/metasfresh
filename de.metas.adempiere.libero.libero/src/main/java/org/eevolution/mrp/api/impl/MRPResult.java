package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Note;
import org.eevolution.mrp.api.IMutableMRPResult;

import de.metas.i18n.IMsgBL;
import de.metas.util.Services;

/* package */class MRPResult implements IMutableMRPResult
{
	/** tableName to "document statistics" */
	private final Map<String, MRPResultDocumentStatistics> documentCounters = new HashMap<String, MRPResultDocumentStatistics>();
	private final ReentrantLock documentCountersLock = new ReentrantLock();

	@Override
	public String getSummary(final Properties ctx)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final String txtCreated = msgBL.translate(ctx, "Created");
		final String txtDeleted = msgBL.translate(ctx, "Deleted");

		final StringBuilder result = new StringBuilder();
		for (final MRPResultDocumentStatistics documentStatistics : getDocumentStatistics())
		{
			if (documentStatistics.isZero())
			{
				continue;
			}

			final String tableName = documentStatistics.getTableName();
			final String documentName = msgBL.translate(ctx, tableName);

			result.append("<br> ")
					.append(documentName).append(": ")
					.append(txtCreated).append("=").append(documentStatistics.getCountCreated())
					.append(", ")
					.append(txtDeleted).append("=").append(documentStatistics.getCountDeleted());
		}

		return result.toString();
	}

	private void addDocumentCounters(final String tableName, final int countCreatedToAdd, final int countDeletedToAdd)
	{
		documentCountersLock.lock();
		try
		{
			MRPResultDocumentStatistics documentStatistics = documentCounters.get(tableName);
			if (documentStatistics == null)
			{
				documentStatistics = new MRPResultDocumentStatistics(tableName);
				documentCounters.put(tableName, documentStatistics);
			}

			documentStatistics.addCountCreated(countCreatedToAdd);
			documentStatistics.addCountDeleted(countDeletedToAdd);
		}
		finally
		{
			documentCountersLock.unlock();
		}
	}

	private List<MRPResultDocumentStatistics> getDocumentStatistics()
	{
		documentCountersLock.lock();
		try
		{
			final List<MRPResultDocumentStatistics> statistics = new ArrayList<MRPResultDocumentStatistics>(documentCounters.values());
			return Collections.unmodifiableList(statistics);
		}
		finally
		{
			documentCountersLock.unlock();
		}

	}

	@Override
	public void addSupplyDocument(final Object document)
	{
		if (document == null)
		{
			return;
		}

		final String tableName = InterfaceWrapperHelper.getModelTableName(document);
		final int countCreatedToAdd = 1;
		final int countDeletedToAdd = 0;
		addDocumentCounters(tableName, countCreatedToAdd, countDeletedToAdd);
	}

	@Override
	public void addDeletedDocuments(final String tableName, final int countToAdd)
	{
		if (countToAdd == 0)
		{
			return;
		}

		final int countCreatedToAdd = 0;
		final int countDeletedToAdd = countToAdd;
		addDocumentCounters(tableName, countCreatedToAdd, countDeletedToAdd);
	}

	@Override
	public void addNote(final I_AD_Note note)
	{
		if (note == null)
		{
			return;
		}

		final String tableName = I_AD_Note.Table_Name;
		final int countCreatedToAdd = 1;
		final int countDeletedToAdd = 0;
		addDocumentCounters(tableName, countCreatedToAdd, countDeletedToAdd);
	}

	@Override
	public void addDeletedNotes(final int countToAdd)
	{
		final String tableName = I_AD_Note.Table_Name;
		final int countCreatedToAdd = 0;
		final int countDeletedToAdd = countToAdd;
		addDocumentCounters(tableName, countCreatedToAdd, countDeletedToAdd);
	}
}
