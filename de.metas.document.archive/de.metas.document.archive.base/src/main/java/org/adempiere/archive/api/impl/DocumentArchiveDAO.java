package org.adempiere.archive.api.impl;

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


import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Archive;

import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;

public class DocumentArchiveDAO extends ArchiveDAO
{
	@Override
	public <T extends I_AD_Archive> T retrievePDFArchiveForModel(final Object model, final Class<T> archiveClass)
	{
		Check.assumeNotNull(model, "model not null");
		Check.assumeNotNull(archiveClass, "archiveClass not null");

		//
		// Services
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IDocOutboundDAO archiveDAO = Services.get(IDocOutboundDAO.class);

		final Object contextProvider = model;
		final int adTableId = InterfaceWrapperHelper.getModelTableId(model);
		final int recordId = InterfaceWrapperHelper.getId(model);

		final IQueryBuilder<I_C_Doc_Outbound_Log> docOutboundLogQueryBuilder = queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class, contextProvider)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Doc_Outbound_Log.COLUMN_AD_Table_ID, adTableId)
				.addEqualsFilter(I_C_Doc_Outbound_Log.COLUMN_Record_ID, recordId);

		final IQueryBuilder<I_C_Doc_Outbound_Log_Line> docOutboundLogLineQueryBuilder = docOutboundLogQueryBuilder
				.andCollectChildren(I_C_Doc_Outbound_Log_Line.COLUMN_C_Doc_Outbound_Log_ID, I_C_Doc_Outbound_Log_Line.class);

		archiveDAO.addPDFArchiveLogLineFilters(docOutboundLogLineQueryBuilder);

		final I_C_Doc_Outbound_Log_Line pdfLine = docOutboundLogLineQueryBuilder.create()
				.first();

		if (pdfLine == null)
		{
			return null;
		}
		final T archive = InterfaceWrapperHelper.create(pdfLine.getAD_Archive(), archiveClass);
		return archive;
	}
}
