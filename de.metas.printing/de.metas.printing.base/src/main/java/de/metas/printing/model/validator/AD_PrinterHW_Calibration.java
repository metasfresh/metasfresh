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


import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.ModelValidator;

import de.metas.printing.Printing_Constants;
import de.metas.printing.model.I_AD_PrinterHW_Calibration;

@Validator(I_AD_PrinterHW_Calibration.class)
public class AD_PrinterHW_Calibration
{

	/**
	 * Sets the CalX and CalY columns to the value in pixels of the respective measurements
	 * 
	 * @param printerCalibration
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW })
	public void measurementChange(final I_AD_PrinterHW_Calibration printerCalibration)
	{
		if (!printerCalibration.isManualCalibration())
		{
			// negative -> up; positive -> down
			final BigDecimal calXmm = Printing_Constants.AD_PrinterHW_Calibration_JASPER_REF_X_MM.subtract(printerCalibration.getMeasurementX());

			// negative -> up; positive -> down
			final BigDecimal calYmm = Printing_Constants.AD_PrinterHW_Calibration_JASPER_REF_Y_MM.subtract(printerCalibration.getMeasurementY());

			printerCalibration.setCalX(mmToPixels(calXmm));
			printerCalibration.setCalY(mmToPixels(calYmm));
		}
	}

	private int mmToPixels(final BigDecimal mm)
	{
		final BigDecimal result = Printing_Constants.AD_PrinterHW_Calibration_JASPER_PIXEL_PER_MM.multiply(mm).setScale(0, RoundingMode.HALF_UP);

		return result.toBigInteger().intValue();
	}
}
