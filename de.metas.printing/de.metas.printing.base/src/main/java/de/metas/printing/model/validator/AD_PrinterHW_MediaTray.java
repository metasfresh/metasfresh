package de.metas.printing.model.validator;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.printing.api.IPrinterBL;
import de.metas.printing.model.I_AD_PrinterHW_MediaTray;

@Interceptor(I_AD_PrinterHW_MediaTray.class)
public class AD_PrinterHW_MediaTray
{
	/**
	 * This interceptor shall only fire if the given {@code printerTrayHW} was created with replication.
	 * If it was created via the REST endpoint, then the business logic is called directly.
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW_REPLICATION)
	public void createDefaultTrayMatchingIfNoneExists(final I_AD_PrinterHW_MediaTray printerTrayHW)
	{
		Services.get(IPrinterBL.class).createDefaultTrayMatching(printerTrayHW);
	}
}
