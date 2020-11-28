package de.metas.dunning;

import java.time.LocalDate;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;

import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.document.engine.IDocument;
import de.metas.dunning.api.IDunningBL;
import de.metas.dunning.api.IDunningDAO;
import de.metas.dunning.exception.DunningException;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.dunning.model.I_C_DunningDoc_Line;
import de.metas.dunning.model.I_C_DunningDoc_Line_Source;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class DunningDocDocumentHandler implements DocumentHandler
{
	@Override
	public String getSummary(@NonNull final DocumentTableFields docFields)
	{
		return extractDunningDoc(docFields).getDocumentNo();
	}

	@Override
	public String getDocumentInfo(@NonNull final DocumentTableFields docFields)
	{
		return getSummary(docFields);
	}

	@Override
	public int getDoc_User_ID(@NonNull final DocumentTableFields docFields)
	{
		return extractDunningDoc(docFields).getCreatedBy();
	}

	@Override
	public LocalDate getDocumentDate(@NonNull final DocumentTableFields docFields)
	{
		return TimeUtil.asLocalDate(extractDunningDoc(docFields).getDunningDate());
	}

	/**
	 * Sets the <code>Processed</code> flag of the given <code>dunningDoc</code> and sets the <code>IsDunningDocProcessed</code> flag of the dunning candidates that are the sources of dunning doc's
	 * lines.
	 * <p>
	 * Assumes that the given doc is not yet processed.
	 */
	@Override
	public String completeIt(@NonNull final DocumentTableFields docFields)
	{
		final I_C_DunningDoc dunningDocRecord = extractDunningDoc(docFields);
		dunningDocRecord.setDocAction(IDocument.ACTION_ReActivate);

		if (dunningDocRecord.isProcessed())
		{
			throw new DunningException("@Processed@=@Y@");
		}

		final IDunningDAO dunningDao = Services.get(IDunningDAO.class);
		final List<I_C_DunningDoc_Line> lines = dunningDao.retrieveDunningDocLines(dunningDocRecord);

		final IDunningBL dunningBL = Services.get(IDunningBL.class);

		for (final I_C_DunningDoc_Line line : lines)
		{
			for (final I_C_DunningDoc_Line_Source source : dunningDao.retrieveDunningDocLineSources(line))
			{
				dunningBL.processSourceAndItsCandidates(dunningDocRecord, source);
			}

			line.setProcessed(true);
			InterfaceWrapperHelper.save(line);
		}

		//
		// Delivery stop (https://github.com/metasfresh/metasfresh/issues/2499)
		dunningBL.makeDeliveryStopIfNeeded(dunningDocRecord);

		dunningDocRecord.setProcessed(true);

		return IDocument.STATUS_Completed;
	}

	@Override
	public void reactivateIt(@NonNull final DocumentTableFields docFields)
	{
		final I_C_DunningDoc dunningDocRecord = extractDunningDoc(docFields);
		dunningDocRecord.setProcessed(false);
		dunningDocRecord.setDocAction(IDocument.ACTION_Complete);
	}

	private static I_C_DunningDoc extractDunningDoc(@NonNull final DocumentTableFields docFields)
	{
		return InterfaceWrapperHelper.create(docFields, I_C_DunningDoc.class);
	}
}
