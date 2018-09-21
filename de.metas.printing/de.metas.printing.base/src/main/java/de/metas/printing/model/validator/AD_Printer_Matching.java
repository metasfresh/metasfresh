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
import de.metas.printing.model.I_AD_Printer_Matching;
import de.metas.util.Services;

/**
 * 
 */
@Validator(I_AD_Printer_Matching.class)
public class AD_Printer_Matching
{

	/**
	 * If the a matching's hardware printer is changed, then this method calls {@link IPrinterBL#updatePrinterTrayMatching(I_AD_Printer_Matching)} to make sure that the printer matching's tray
	 * matchings are consistent with the the HW printer.
	 * 
	 * @param printerMatching
	 */
	@ModelChange(
			timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_CHANGE_REPLICATION },
			ifColumnsChanged = I_AD_Printer_Matching.COLUMNNAME_AD_PrinterHW_ID)
	public void updateMatchingHardwareTray(final I_AD_Printer_Matching printerMatching)
	{
		Services.get(IPrinterBL.class).updatePrinterTrayMatching(printerMatching);
	}
}
