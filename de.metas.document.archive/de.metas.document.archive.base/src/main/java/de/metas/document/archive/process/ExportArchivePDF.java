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


import java.util.logging.Level;

import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.process.SvrProcess;

import de.metas.adempiere.form.IClientUI;
import de.metas.document.archive.model.IArchiveAware;
import de.metas.document.archive.model.I_AD_Archive;

public class ExportArchivePDF extends SvrProcess implements ISvrProcessPrecondition
{
	@Override
	public boolean isPreconditionApplicable(final GridTab gridTab)
	{
		final GridField field = gridTab.getField(org.compiere.model.I_AD_Archive.COLUMNNAME_AD_Archive_ID);
		if (field == null)
		{
			log.log(Level.FINE, "No AD_Archive field found for {0}", gridTab);
			return false;
		}

		final Object value = field.getValue();
		if (value == null)
		{
			log.log(Level.FINE, "Null value found for {0}", field);
			return false;
		}

		if (!(value instanceof Number))
		{
			log.log(Level.FINE, "Invalid value {0} found for {1}", new Object[] { value, field });
			return false;
		}

		final int archiveId = ((Number)value).intValue();
		if (archiveId <= 0)
		{
			log.log(Level.FINE, "No value found for {1}", field);
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
		if (getTable_ID() <= 0 || getRecord_ID() <= 0)
		{
			throw new AdempiereException("No record found; getTable_ID()=" + getTable_ID() + ", getRecord_ID()=" + getRecord_ID());
		}

		final PO po = MTable.get(getCtx(), getTable_ID()).getPO(getRecord_ID(), get_TrxName());
		Check.assumeNotNull(po, "po with AD_Table_ID=" + getTable_ID() + " and Record_ID=" + getRecord_ID() + " not null");

		final IArchiveAware archiveAware = InterfaceWrapperHelper.create(po, IArchiveAware.class);
		final I_AD_Archive archive = archiveAware.getAD_Archive();

		final IArchiveBL archiveBL = Services.get(IArchiveBL.class);
		final byte[] data = archiveBL.getBinaryData(archive);
		final String contentType = archiveBL.getContentType(archive);
		final String filename = null;

		Services.get(IClientUI.class).download(data, contentType, filename);
		return "OK";
	}
}
