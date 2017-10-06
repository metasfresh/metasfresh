package de.metas.material.model.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.model.ModelValidator;

import com.google.common.collect.ImmutableList;

import de.metas.material.event.EventDescr;
import de.metas.material.event.ForecastEvent;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.forecast.Forecast.ForecastBuilder;

@Interceptor(I_M_ForecastLine.class)
public class M_ForecastLine
{
	static final M_ForecastLine INSTANCE = new M_ForecastLine();

	private M_ForecastLine()
	{
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE }, afterCommit = true)
	public void fireEvent(final I_M_ForecastLine forecastLine, final int timing)
	{
		final ForecastEvent forecastEvent = createForecastEvent(forecastLine, timing);

		final MaterialEventService materialEventService = Adempiere.getBean(MaterialEventService.class);

		final String trxName = InterfaceWrapperHelper.getTrxName(forecastLine);
		materialEventService.fireEventAfterCommit(forecastEvent, trxName);
	}

	private ForecastEvent createForecastEvent(final I_M_ForecastLine forecastLine, final int timing)
	{
		final boolean deleted = timing == ModelValidator.TYPE_BEFORE_DELETE || !forecastLine.isActive();

		final ForecastBuilder forecastBuilder = M_ForecastEventUtil.createForecastBuilderWithLinesAndDeletedFlag(
				forecastLine.getM_Forecast(),
				ImmutableList.of(forecastLine),
				deleted);

		final ForecastEvent forecastEvent = ForecastEvent
				.builder()
				.forecast(forecastBuilder.build())
				.eventDescr(EventDescr.createNew(forecastLine))
				.build();
		return forecastEvent;
	}
}
