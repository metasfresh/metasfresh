package de.metas.material.model.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.getTrxName;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.model.ModelValidator;

import de.metas.material.event.MaterialEventService;
import de.metas.material.event.forecast.ForecastEvent;
import lombok.NonNull;

@Interceptor(I_M_Forecast.class)
public class M_Forecast
{
	public static final M_Forecast INSTANCE = new M_Forecast();

	private M_Forecast()
	{
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_AFTER_CLOSE, // => send updated lines
			ModelValidator.TIMING_AFTER_COMPLETE, // => send lines
			ModelValidator.TIMING_AFTER_REACTIVATE, // delete
			ModelValidator.TIMING_AFTER_UNCLOSE, // send updated lines
			ModelValidator.TIMING_AFTER_REVERSEACCRUAL, // delete
			ModelValidator.TIMING_AFTER_REVERSECORRECT, // delete
			ModelValidator.TIMING_AFTER_VOID // delete
	})
	public void fireEvent2(@NonNull final I_M_Forecast forecast, final DocTimingType timing)
	{
		final List<I_M_ForecastLine> forecastLines = retrieveForecastLines(forecast);
		if(forecastLines.isEmpty())
		{
			return;
		}
		
		final ForecastEvent forecastEvent = M_ForecastEventCreator.createEventWithLinesAndTiming(
				forecastLines,
				timing);

		final MaterialEventService materialEventService = Adempiere.getBean(MaterialEventService.class);
		materialEventService.fireEventAfterNextCommit(forecastEvent, getTrxName(forecast));
	}

	private List<I_M_ForecastLine> retrieveForecastLines(@NonNull final I_M_Forecast forecast)
	{
		final List<I_M_ForecastLine> forecastLines = Services.get(IQueryBL.class).createQueryBuilder(I_M_ForecastLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ForecastLine.COLUMN_M_Forecast_ID, forecast.getM_Forecast_ID())
				.create()
				.list();
		return forecastLines;
	}
}
