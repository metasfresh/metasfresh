package de.metas.mforecast;

import de.metas.document.engine.DocStatus;
import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.document.engine.IDocument;
import de.metas.mforecast.impl.ForecastId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.util.TimeUtil;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

@RequiredArgsConstructor
class ForecastDocumentHandler implements DocumentHandler
{
	private final IForecastDAO forecastDAO;

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
	public int getC_Currency_ID(final DocumentTableFields docFields)
	{
		return -1;
	}

	@Override
	public BigDecimal getApprovalAmt(final DocumentTableFields docFields)
	{
		return BigDecimal.ZERO;
	}

	@Override
	public File createPDF(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String completeIt(final DocumentTableFields docFields)
	{
		final I_M_Forecast forecast = extractForecast(docFields);
		forecast.setDocAction(IDocument.ACTION_None);
		return IDocument.STATUS_Completed;
	}

	@Override
	public void approveIt(final DocumentTableFields docFields)
	{
	}

	@Override
	public void rejectIt(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void voidIt(final DocumentTableFields docFields)
	{
		final I_M_Forecast forecast = extractForecast(docFields);

		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(forecast.getDocStatus());
		if (docStatus.isClosedReversedOrVoided())
		{
			throw new AdempiereException("Document Closed: " + docStatus);
		}

		getLines(forecast).forEach(this::voidLine);

		forecast.setProcessed(true);
		forecast.setDocAction(IDocument.ACTION_None);
	}

	@Override
	public void unCloseIt(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void reverseCorrectIt(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void reverseAccrualIt(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void reactivateIt(final DocumentTableFields docFields)
	{
		final I_M_Forecast forecast = extractForecast(docFields);
		forecast.setProcessed(false);
		forecast.setDocAction(IDocument.ACTION_Complete);
	}

	@Override
	public LocalDate getDocumentDate(@NonNull final DocumentTableFields docFields)
	{
		final I_M_Forecast forecast = extractForecast(docFields);
		return TimeUtil.asLocalDate(forecast.getDatePromised());
	}

	private void voidLine(@NonNull final I_M_ForecastLine line)
	{
		line.setQty(BigDecimal.ZERO);
		line.setQtyCalculated(BigDecimal.ZERO);
		InterfaceWrapperHelper.save(line);
	}

	private List<I_M_ForecastLine> getLines(@NonNull final I_M_Forecast forecast)
	{
		return forecastDAO.retrieveLinesByForecastId(ForecastId.ofRepoId(forecast.getM_Forecast_ID()));
	}
}
