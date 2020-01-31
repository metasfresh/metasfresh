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

import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.model.InterfaceWrapperHelper;

import com.google.common.annotations.VisibleForTesting;

import de.metas.async.Async_Constants;
import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.document.archive.model.I_AD_Archive;
import de.metas.document.archive.model.X_C_Doc_Outbound_Log_Line;
import de.metas.document.archive.spi.impl.DefaultModelArchiver;
import de.metas.user.UserId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;

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
		final I_AD_Archive archive = createModelArchiver(record).archive();
		if (archive == null)
		{
			Loggables.addLog("Created *no* AD_Archive for record={}", record);
			return;
		}
		Loggables.addLog("Created AD_Archive_ID={} for record={}", archive.getAD_Archive_ID(), record);

		final String action = X_C_Doc_Outbound_Log_Line.ACTION_PdfExport; // this action is ported here. i'm not 100% sure it makes sense
		archiveEventManager.firePdfUpdate(archive, userId, action);
	}

	@VisibleForTesting
	protected DefaultModelArchiver createModelArchiver(@NonNull final Object record)
	{
		return DefaultModelArchiver.of(record);
	}
}
