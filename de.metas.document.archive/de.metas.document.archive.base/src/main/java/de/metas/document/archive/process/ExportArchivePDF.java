package de.metas.document.archive.process;

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


import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.process.SvrProcess;

import de.metas.adempiere.form.IClientUI;
import de.metas.document.archive.model.IArchiveAware;
import de.metas.document.archive.model.I_AD_Archive;

public class ExportArchivePDF extends SvrProcess implements ISvrProcessPrecondition
{
	@Override
	public boolean isPreconditionApplicable(final PreconditionsContext context)
	{
		final Object model = context.getModel(Object.class);
		final IArchiveAware archiveAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(model, IArchiveAware.class);
		if(archiveAware == null)
		{
			log.debug("No AD_Archive field found for {}", context);
			return false;
		}

		final int archiveId = archiveAware.getAD_Archive_ID();
		if (archiveId <= 0)
		{
			log.debug("No AD_Archive_ID found for {}", archiveAware);
			return false;
		}

		return true;
	}

	@Override
	protected void prepare()
	{
	}

	@Override
	protected String doIt()
	{
		final IArchiveAware archiveAware = getRecord(IArchiveAware.class);
		final I_AD_Archive archive = archiveAware.getAD_Archive();
		Check.assumeNotNull(archive, "Parameter archive is not null");

		final IArchiveBL archiveBL = Services.get(IArchiveBL.class);
		final byte[] data = archiveBL.getBinaryData(archive);
		final String contentType = archiveBL.getContentType(archive);
		final String filename = null;

		Services.get(IClientUI.class).download(data, contentType, filename);
		return "OK";
	}
}
