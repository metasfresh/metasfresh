package de.metas.printing.adapter;

/*
 * #%L
 * de.metas.printing.base
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

import de.metas.printing.IMassPrintingService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.ArchiveInfo;
import org.adempiere.archive.api.ArchiveRequest;
import org.adempiere.archive.api.ArchiveResult;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public final class MassPrintingService implements IMassPrintingService
{
	private final IArchiveBL archiveService = Services.get(IArchiveBL.class);

	/**
	 * Exports the given data to PDF and creates an <code>AD_Archive</code> with <code>IsDirectPrint='Y'</code> (to trigger a <code>C_Printing_Queue</code> record being created) and
	 * <code>IsCreatePrintJob='Y'</code> to also trigger a direct <code>C_PrintJob</code> creation.
	 */
	@Override
	public void print(
			@NonNull final Resource reportData,
			@NonNull final ArchiveInfo archiveInfo)
	{
		final ArchiveResult archiveResult = archiveService.archive(ArchiveRequest.builder()
				.ctx(Env.getCtx())
				.trxName(ITrx.TRXNAME_ThreadInherited)
				.data(reportData)
				.force(true) // archive it even if AutoArchive says no
				.save(true)
				.isReport(archiveInfo.isReport())
				.recordRef(archiveInfo.getRecordRef())
				.processId(archiveInfo.getProcessId())
				.pinstanceId(archiveInfo.getPInstanceId())
				.archiveName(archiveInfo.getName())
				.bpartnerId(archiveInfo.getBpartnerId())
				//
				// Ask our printing service to printing it right now:
				.isDirectEnqueue(true)
				.isDirectProcessQueueItem(true)
				.copies(archiveInfo.getCopies())
				//
				.build());

		if (archiveResult.isNoArchive())
		{
			throw new AdempiereException("No archive created for " + reportData + " and " + archiveInfo);
		}
	}

}
