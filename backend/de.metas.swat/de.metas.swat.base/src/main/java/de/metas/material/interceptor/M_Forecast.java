package de.metas.material.interceptor;

import de.metas.i18n.IMsgBL;
import de.metas.mforecast.IForecastDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.CopyRecordFactory;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Interceptor(I_M_Forecast.class)
@Component
public class M_Forecast
{
	private static final String MSG_DOC_ACTION_NOT_ALLOWED_AFTER_COMPLETION = "M_Forecast_DocAction_Not_Allowed_After_Completion";

	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IForecastDAO forecastsRepo = Services.get(IForecastDAO.class);

	public M_Forecast()
	{
		CopyRecordFactory.enableForTableName(I_M_Forecast.Table_Name);
		CopyRecordFactory.registerCopyRecordSupport(I_M_Forecast.Table_Name, MForecastPOCopyRecordSupport.class);
	}

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
