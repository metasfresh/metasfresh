package de.metas.material.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.CopyRecordFactory;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;

import de.metas.i18n.IMsgBL;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.forecast.ForecastCreatedEvent;
import lombok.NonNull;

@Interceptor(I_M_Forecast.class)
public class M_Forecast
{
	private static final String MSG_DOC_ACTION_NOT_ALLOWED_AFTER_COMPLETION = "M_Forecast_DocAction_Not_Allowed_After_Completion";
	static final M_Forecast INSTANCE = new M_Forecast();

	private M_Forecast()
	{
	}

	@Init
	public void init(final IModelValidationEngine engine)
	{
		CopyRecordFactory.enableForTableName(I_M_Forecast.Table_Name);
		CopyRecordFactory.registerCopyRecordSupport(I_M_Forecast.Table_Name, MForecastPOCopyRecordSupport.class);
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
	public void fireForecastCreatedEventOnComplete(@NonNull final I_M_Forecast forecast, @NonNull final DocTimingType timing)
	{
		final List<I_M_ForecastLine> forecastLines = retrieveForecastLines(forecast);
		if (forecastLines.isEmpty())
		{
			return;
		}

		final ForecastCreatedEvent forecastCreatedEvent = M_ForecastEventCreator.createEventWithLinesAndTiming(
				forecastLines,
				timing);

		final PostMaterialEventService materialEventService = Adempiere.getBean(PostMaterialEventService.class);
		materialEventService.postEventAfterNextCommit(forecastCreatedEvent);
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

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = {
			I_M_Forecast.COLUMNNAME_C_BPartner_ID,
			I_M_Forecast.COLUMNNAME_C_Period_ID,
			I_M_Forecast.COLUMNNAME_DatePromised,
			I_M_Forecast.COLUMNNAME_M_Warehouse_ID
	})
	public void updateForecastLines(@NonNull final I_M_Forecast forecast)
	{
		final List<I_M_ForecastLine> forecastLines = retrieveForecastLines(forecast);
		if (forecastLines.isEmpty())
		{
			return;
		}

		forecastLines.forEach( forecastLine ->
		{
			forecastLine.setC_BPartner(forecast.getC_BPartner());
			forecastLine.setM_Warehouse(forecast.getM_Warehouse());
			forecastLine.setC_Period(forecast.getC_Period());
			forecastLine.setDatePromised(forecast.getDatePromised());
			save(forecastLine);
		});
	}
}
