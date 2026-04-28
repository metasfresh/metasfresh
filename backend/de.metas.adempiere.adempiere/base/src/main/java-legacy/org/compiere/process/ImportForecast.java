package org.compiere.process;

import org.compiere.model.I_I_Forecast;

/**
 * Import {@link org.compiere.model.I_I_Forecast} to {@link org.compiere.model.I_M_Forecast}.
 *
 */
public class ImportForecast extends AbstractImportJavaProcess<I_I_Forecast>
{
	public ImportForecast()
	{
		super(I_I_Forecast.class);
	}
}
