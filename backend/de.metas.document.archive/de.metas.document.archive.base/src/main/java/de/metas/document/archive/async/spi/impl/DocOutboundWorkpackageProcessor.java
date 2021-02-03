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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.async.Async_Constants;
import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.document.archive.spi.impl.DefaultModelArchiver;
import de.metas.user.UserId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.archive.api.ArchiveResult;
import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.model.InterfaceWrapperHelper;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Process work packages from queue and:
 * <ul>
 * <li>archive the document
 * <li>record log
 * </ul>
 *
 * @author tsa
 */
public class DocOutboundWorkpackageProcessor implements IWorkpackageProcessor
{
	private final IArchiveEventManager archiveEventManager = Services.get(IArchiveEventManager.class);

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);

		final List<Object> records = queueDAO.retrieveItems(workpackage, Object.class, localTrxName);
		for (final Object record : records)
		{
			if (workpackage.getC_Async_Batch_ID() > 0)
			{
				InterfaceWrapperHelper.setDynAttribute(record, Async_Constants.C_Async_Batch, workpackage.getC_Async_Batch());
			}
			generateOutboundDocument(record, UserId.ofRepoIdOrNull(workpackage.getAD_User_ID()));
		}
		return Result.SUCCESS;
	}

	private void generateOutboundDocument(
			@NonNull final Object record,
			@Nullable final UserId userId)
	{
		final ArchiveResult archiveResult = DefaultModelArchiver.of(record).archive();
		if (archiveResult.isNoArchive())
		{
			Loggables.addLog("Created *no* AD_Archive for record={}", record);
		}
		else
		{
			Loggables.addLog("Created AD_Archive_ID={} for record={}", archiveResult.getArchiveRecord().getAD_Archive_ID(), record);
			archiveEventManager.firePdfUpdate(archiveResult.getArchiveRecord(), userId);
		}
	}
}
