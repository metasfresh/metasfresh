package de.metas.material.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.List;

import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.CopyRecordFactory;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;

import de.metas.i18n.IMsgBL;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.forecast.ForecastCreatedEvent;
import de.metas.mforecast.IForecastDAO;
import de.metas.util.Services;
import lombok.NonNull;

@Interceptor(I_M_Forecast.class)
public class M_Forecast
{
	private static final String MSG_DOC_ACTION_NOT_ALLOWED_AFTER_COMPLETION = "M_Forecast_DocAction_Not_Allowed_After_Completion";

	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IForecastDAO forecastsRepo = Services.get(IForecastDAO.class);
	private final M_ForecastEventCreator forecastEventCreator;
	private final PostMaterialEventService materialEventService;

	public M_Forecast(
			@NonNull final ModelProductDescriptorExtractor productDescriptorFactory,
			@NonNull final PostMaterialEventService materialEventService)
	{
		forecastEventCreator = new M_ForecastEventCreator(productDescriptorFactory);
		this.materialEventService = materialEventService;
	}

	@Init
	public void init()
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
		final String message = msgBL.getMsg(Env.getCtx(), MSG_DOC_ACTION_NOT_ALLOWED_AFTER_COMPLETION);
		throw new AdempiereException(message);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void fireForecastCreatedEventOnComplete(@NonNull final I_M_Forecast forecast, @NonNull final DocTimingType timing)
	{
		final List<I_M_ForecastLine> forecastLines = forecastsRepo.retrieveLinesByForecastId(forecast.getM_Forecast_ID());
		if (forecastLines.isEmpty())
		{
			return;
		}

		final ForecastCreatedEvent forecastCreatedEvent = forecastEventCreator.createEventWithLinesAndTiming(forecastLines, timing);
		materialEventService.postEventAfterNextCommit(forecastCreatedEvent);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = {
			I_M_Forecast.COLUMNNAME_C_BPartner_ID,
			I_M_Forecast.COLUMNNAME_C_Period_ID,
			I_M_Forecast.COLUMNNAME_DatePromised,
			I_M_Forecast.COLUMNNAME_M_Warehouse_ID
	})
	public void updateForecastLines(@NonNull final I_M_Forecast forecast)
	{
		final List<I_M_ForecastLine> forecastLines = forecastsRepo.retrieveLinesByForecastId(forecast.getM_Forecast_ID());
		forecastLines.forEach(forecastLine -> updateForecastLineFromHeaderAndSave(forecastLine, forecast));
	}

	private void updateForecastLineFromHeaderAndSave(final I_M_ForecastLine forecastLine, final I_M_Forecast forecast)
	{
		forecastLine.setC_BPartner_ID(forecast.getC_BPartner_ID());
		forecastLine.setM_Warehouse_ID(forecast.getM_Warehouse_ID());
		forecastLine.setC_Period_ID(forecast.getC_Period_ID());
		forecastLine.setDatePromised(forecast.getDatePromised());
		saveRecord(forecastLine);
	}
}
