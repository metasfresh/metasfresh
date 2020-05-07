package de.metas.printing.model.validator;

/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.ModelValidator;

import de.metas.printing.api.IPrinterBL;
import de.metas.printing.model.I_AD_PrinterHW_Calibration;
import de.metas.printing.model.I_AD_PrinterTray_Matching;
import de.metas.util.Services;

/**
 * 
 * @author ts
 * 
 */
@Validator(I_AD_PrinterTray_Matching.class)
public class AD_PrinterTray_Matching
{
	/**
	 * Creates a new {@link I_AD_PrinterHW_Calibration} for the printer and tray of a new <code>printerTrayMatching</code> and the media size <code>MediaSize.ISO.A4</code>.
	 * If such an AD_PrinterHW_Calibration already exists, the method does nothing.
	 * 
	 * @param printerTrayMatching
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_NEW_REPLICATION })
	public void createCalibbration(final I_AD_PrinterTray_Matching printerTrayMatching)
	{
		Services.get(IPrinterBL.class).createCalibrationIfNoneExists(printerTrayMatching);
	}

	
}
