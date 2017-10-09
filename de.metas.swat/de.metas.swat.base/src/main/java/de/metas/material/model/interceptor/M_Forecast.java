package de.metas.material.model.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.getTrxName;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;

import de.metas.i18n.IMsgBL;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.forecast.ForecastEvent;
import lombok.NonNull;

@Interceptor(I_M_Forecast.class)
public class M_Forecast
{
	private static final String MSG_DOC_ACTION_NOT_ALLOWED_AFTER_COMPLETION = "M_Forecast_DocAction_Not_Allowed_After_Completion";
	static final M_Forecast INSTANCE = new M_Forecast();

	private M_Forecast()
	{
	}

	/**
	 * 
	 * @param forecast
	 * @param timing
	 */
	@DocValidate(timings = {
			ModelValidator.TIMING_BEFORE_CLOSE,
			ModelValidator.TIMING_BEFORE_REACTIVATE,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_VOID
	})
	public void preventUnsupportedDocActions(@NonNull final I_M_Forecast forecast)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final String message = msgBL.getMsg(Env.getCtx(), MSG_DOC_ACTION_NOT_ALLOWED_AFTER_COMPLETION);
		throw new AdempiereException(message);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void fireForecastEventOnComplete(@NonNull final I_M_Forecast forecast, @NonNull final DocTimingType timing)
	{
		final List<I_M_ForecastLine> forecastLines = retrieveForecastLines(forecast);
		if (forecastLines.isEmpty())
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
