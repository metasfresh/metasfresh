package de.metas.material.model.interceptor;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.model.ModelValidator;

import de.metas.material.event.EventDescr;
import de.metas.material.event.ForecastEvent;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.forecast.Forecast.ForecastBuilder;
import lombok.NonNull;

@Interceptor(I_M_Forecast.class)
public class M_Forecast
{
	static final M_Forecast INSTANCE = new M_Forecast();

	private M_Forecast()
	{
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void fireEvent(@NonNull final I_M_Forecast forecast, final int timing)
	{
		final ForecastEvent forecastEvent = createForecastEvent(forecast, timing);

		final MaterialEventService materialEventService = Adempiere.getBean(MaterialEventService.class);

		final String trxName = InterfaceWrapperHelper.getTrxName(forecast);
		materialEventService.fireEventAfterCommit(forecastEvent, trxName);
	}

	private ForecastEvent createForecastEvent(
			@NonNull final I_M_Forecast forecast,
			final int timing)
	{
		final boolean deleted = timing == ModelValidator.TYPE_BEFORE_DELETE || !forecast.isActive();

		final ForecastBuilder forecastBuilder = M_ForecastEventUtil.createForecastBuilderWithLinesAndDeletedFlag(
				forecast,
				retrieveForecastLines(forecast),
				deleted);

		final ForecastEvent forecastEvent = ForecastEvent
				.builder()
				.forecast(forecastBuilder.build())
				.eventDescr(EventDescr.createNew(forecast))
				.build();
		return forecastEvent;
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
