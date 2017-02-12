package de.metas.edi.process;

/*
 * #%L
 * de.metas.edi
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

import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;

import de.metas.adempiere.form.IClientUI;
import de.metas.edi.api.IEDIDocumentBL;
import de.metas.edi.model.I_EDI_Document;
import de.metas.edi.process.export.IExport;
import de.metas.process.JavaProcess;

/**
 * EDI-Exports a single document.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class EDIExport extends JavaProcess
{
	private int recordId = -1;

	@Override
	protected void prepare()
	{
		recordId = getRecord_ID();
	}

	@Override
	protected String doIt()
	{
		//
		// Services
		final IEDIDocumentBL ediDocumentBL = Services.get(IEDIDocumentBL.class);

		final IExport<? extends I_EDI_Document> export = ediDocumentBL.createExport(getCtx(), getAD_Client_ID(), getTable_ID(), recordId, get_TrxName());
		final List<Exception> feedback = export.createExport();
		if (feedback == null || feedback.isEmpty())
		{
			return "OK";
		}

		final int windowNo = getProcessInfo().getWindowNo();
		final String errorTitle = buildAndTrlTitle(export.getTableIdentifier(), export.getDocument());
		final String errorMessage = ediDocumentBL.buildFeedback(feedback);
		Services.get(IClientUI.class).warn(windowNo, errorTitle, errorMessage);

		return "Error";
	}

	private String buildAndTrlTitle(final String tableNameIdentifier, final I_EDI_Document document)
	{
		final StringBuilder titleBuilder = new StringBuilder();
		titleBuilder.append("@").append(tableNameIdentifier).append("@ ").append(document.getDocumentNo());

		final String tableNameIdentifierTrl = Services.get(IMsgBL.class).parseTranslation(getCtx(), titleBuilder.toString());
		return tableNameIdentifierTrl;
	}
}
