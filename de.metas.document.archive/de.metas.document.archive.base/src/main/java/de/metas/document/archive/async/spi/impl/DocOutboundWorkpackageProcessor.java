package de.metas.document.archive.async.spi.impl;

/*
 * #%L
 * de.metas.document.archive.base
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

import java.util.List;

import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.util.Services;

import com.google.common.annotations.VisibleForTesting;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.document.archive.model.I_AD_Archive;
import de.metas.document.archive.spi.impl.DefaultModelArchiver;

/**
 * Process work packages from queue and:
 * <ul>
 * <li>archive the document
 * <li>record log (see {@link de.metas.document.archive.spi.impl.DocOutboundArchiveEventListener#onPdfUpdate(org.compiere.model.I_AD_Archive, org.compiere.model.I_AD_User)})
 * </ul>
 *
 * @author tsa
 *
 */
public class DocOutboundWorkpackageProcessor implements IWorkpackageProcessor
{
	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);

		final List<Object> records = queueDAO.retrieveItems(workpackage, Object.class, localTrxName);
		for (final Object record : records)
		{
			generateOutboundDocument(record);
		}
		return Result.SUCCESS;
	}

	private void generateOutboundDocument(final Object record)
	{
		final I_AD_Archive archive = createModelArchiver(record).archive();
		if (archive == null)
		{
			return;
		}

		Services.get(IArchiveEventManager.class).firePdfUpdate(archive, null); // user=null
	}
	
	@VisibleForTesting
	protected DefaultModelArchiver createModelArchiver(final Object record)
	{
		return DefaultModelArchiver.of(record);
	}
}
