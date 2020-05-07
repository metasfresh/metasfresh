package org.compiere.report;

import de.metas.process.ProcessInfo;

/*
 * #%L
 * de.metas.swat.base
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


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * 
 * Use {@link IJasperServiceRegistry} to get an instance.
 *
 */
public interface IJasperService
{

	/**
	 * A {@link ProcessInfo} parameter with this name can be used to specify the number of copies (1 means one printout) in case using {@link org.compiere.model.PrintInfo} is not feasible.
	 */
	String PARAM_PrintCopies = "PrintCopies";

	/**
	 * 
	 * @param jasperPrint the document that will be printed
	 * @param pi The process parameters that are used to find the correct printer (printer routing) and to store the printout's doc exchange record. Also this method tries to extract the number of
	 *            copies by
	 *            <ul>
	 *            <li>Checking if any process parameter is <code>instanceof </code> {@link org.compiere.model.PrintInfo}. If that is the case, then that printinfo's value is used
	 *            <li>Checking if any process parameter has {@link #PARAM_PrintCopies} as it's name (equals). If that is the case, then that parameters value is used.
	 *            <p>
	 *            <b>IMPORTANT:</b> a {@link org.compiere.model.PrintInfo} parameter takes precedence over a {@link #PARAM_PrintCopies} parameter.
	 *            </ul>
	 *            If none of these two ways work, the method will print one copy (i.e. one printout).
	 * @param displayDialog if true, a dialog for printer selection is displayed before printing.
	 */
	void print(JasperPrint jasperPrint, ProcessInfo pi, boolean displayDialog);

	byte[] exportToPdf(JasperPrint jasperPrint) throws JRException;

	void setJrPrintExporter(JRExporter jrExporter);

}
