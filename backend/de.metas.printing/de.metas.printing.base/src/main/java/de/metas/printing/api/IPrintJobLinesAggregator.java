package de.metas.printing.api;

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


import org.adempiere.util.lang.Mutable;
import org.compiere.util.Util.ArrayKey;

import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Print_Package;

/**
 * Aggregates {@link I_C_Print_Job_Line}s and produces {@link I_C_Print_Package}.
 * 
 * @author tsa
 * 
 */
public interface IPrintJobLinesAggregator
{

	/**
	 * Sets the print package into which the successive {@link #add(I_C_Print_Job_Line)} invocations will be aggregated. <br>
	 * Note that it is not mandatory to call this method. If it is omitted, this aggregator shall create a <code>printPackage</code> on the fly.
	 */
	void setPrintPackageToUse(I_C_Print_Package printPackage);

	/**
	 * Add another jobline to our aggregate (i.e. {@link I_C_Print_Package}). <br>
	 * Also use {@link de.metas.printing.model.I_AD_PrinterRouting} to determine which hardware-tray to print to as well as the tray and the media size <b>(currently hardcoded to A4!)</b>, to retrieve
	 * the calibration settings. Note that if there is no hardware-tray (e.g. PDF-printer), the method shall tolerate it.
	 * 
	 * @param jobLine the line to add
	 * @param preceedingKey may not be <code>null</code>, but the mutable's value may be null. contains the grouping key of the preceeding invocation (if any!) of this method. This mutable's value is set by this method.
	 */
	void add(I_C_Print_Job_Line jobLine, Mutable<ArrayKey> preceedingKey);

	/**
	 * Return the print package which contains {@link de.metas.printing.model.I_C_Print_PackageInfo I_C_Print_PackageInfo} and {@link de.metas.printing.model.I_C_PrintPackageData I_C_PrintPackageData}
	 * for all the {@link I_C_Print_Job_Line} that were previously added. Also flags those print job lines as <code>Processed</code>.
	 */
	I_C_Print_Package createPrintPackage();

}
