package de.metas.mforecast;

import de.metas.document.engine.DocStatus;
import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.document.engine.IDocument;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Forecast;

/*
 * #%L
 * de.metas.business
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

class ForecastDocumentHandler implements DocumentHandler
{
	private static I_M_Forecast extractForecast(final DocumentTableFields docFields)
	{
		return InterfaceWrapperHelper.create(docFields, I_M_Forecast.class);
	}

	@Override
	public String getSummary(final DocumentTableFields docFields)
	{
		return extractForecast(docFields).getName();
	}

	@Override
	public String getDocumentInfo(final DocumentTableFields docFields)
	{
		return getSummary(docFields);
	}

	@Override
	public int getDoc_User_ID(final DocumentTableFields docFields)
	{
		return extractForecast(docFields).getCreatedBy();
	}

	@Override
	public InstantAndOrgId getDocumentDate(@NonNull final DocumentTableFields docFields)
	{
		final I_M_Forecast forecast = extractForecast(docFields);
		return InstantAndOrgId.ofTimestamp(forecast.getDatePromised(), OrgId.ofRepoId(forecast.getAD_Org_ID()));
	}

	@Override
	public DocStatus completeIt(final DocumentTableFields docFields)
	{
		final I_M_Forecast forecast = extractForecast(docFields);
		forecast.setDocAction(IDocument.ACTION_None);
		return DocStatus.Completed;
	}

	@Override
	public void reactivateIt(final DocumentTableFields docFields)
	{
		final I_M_Forecast forecast = extractForecast(docFields);
		forecast.setProcessed(false);
		forecast.setDocAction(IDocument.ACTION_Complete);
	}
}
